package com.banco.ubs.heartbeat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class Subscriber implements ScheduledChannelOperation {
	
	private final String id;
    private final ScheduledExecutorService scheduler;
    private final NetworkInterface networkInterface;
    private final InetSocketAddress hostAddress;
    private final InetAddress group;
    private final ConcurrentMap<String, Pulse> pulses;
 
    Subscriber(final String id, final String ip, final String interfaceName, final int port, final int poolSize) {
        if (StringUtils.isEmpty(id) && StringUtils.isEmpty(ip) || StringUtils.isEmpty(interfaceName)) {
            throw new IllegalArgumentException("required id, ip and interfaceName");
        }
 
        this.id = id;
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
        this.hostAddress = new InetSocketAddress(port);
        this.pulses = new ConcurrentHashMap<>();
 
        try {
            this.networkInterface = NetworkInterface.getByName(interfaceName);
            this.group = InetAddress.getByName(ip);
        } catch (SocketException | UnknownHostException  e) {
            throw new RuntimeException("unable to start broadcaster", e);
        }
    }
 
    @Override
    public ScheduledExecutorService getService() {
        return this.scheduler;
    }
 
    void run() {
        try (final DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET); final Selector selector = Selector.open()) {
 
            System.out.printf("Starting subscriber %s", id);
            initChannel(channel, selector);
            doSchedule(channel);
 
            while (!Thread.currentThread().isInterrupted()) {
                if (selector.isOpen()) {
                    final int numKeys = selector.select();
                    if (numKeys > 0) {
                        handleKeys(channel, selector.selectedKeys());
                    }
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("unable to run subscriber", e);
        } finally {
            this.scheduler.shutdownNow();
        }
    }
 
    private void initChannel(final DatagramChannel channel, final Selector selector) throws IOException {
        assert !Objects.isNull(channel) && Objects.isNull(selector);
 
        channel.configureBlocking(false);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.bind(this.hostAddress);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, this.networkInterface);
        channel.join(this.group, this.networkInterface);
        channel.register(selector, SelectionKey.OP_READ);
    }
 
    private void handleKeys(final DatagramChannel channel, final Set<SelectionKey> keys) throws IOException {
        assert !Objects.isNull(keys) && !Objects.isNull(channel);
 
        final Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
 
            final SelectionKey key = iterator.next();
            try {
                if (key.isValid() && key.isReadable()) {
                    Pulse.read(channel).ifPresent((pulse) -> {
                        this.pulses.put(pulse.getId(), pulse);
                    });
                } else {
                    throw new UnsupportedOperationException("key not valid.");
                }
            } finally {
                iterator.remove();
            }
        }
    }
 
    private void doSchedule(final DatagramChannel channel) {
        assert !Objects.isNull(channel);
 
        doSchedule(channel, new Runnable() {
            public void run() {
                Subscriber.this.pulses.forEach((id, pulse) -> {
                    if (pulse.isDead(Constants.Schedule.DOWNTIME_TOLERANCE_DEAD_SERVICE_IN_MILLISECONDS)) {
                        System.out.println(String.format("FATAL   : %s removed", id));
                        Subscriber.this.pulses.remove(id);
                    } else if (!pulse.isValid(Constants.Schedule.DOWNTIME_TOLERANCE_IN_MILLISECONDS)) {
                        System.out.println(String.format("WARNING : %s is down", id));
                    } else {
                        System.out.println(String.format("OK      : %s is up", id));
                    }
                });
            }
        }, 0L, Constants.Schedule.PULSE_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

}
