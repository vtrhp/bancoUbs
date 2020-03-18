package com.banco.ubs.heartbeat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class Multicaster implements ScheduledChannelOperation {
	private final String id;
    private final ScheduledExecutorService scheduler;
    private final NetworkInterface networkInterface;
    private final InetSocketAddress multicastGroup;
 
    Multicaster(final String id, final String ip, final String interfaceName, final int port, final int poolSize) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(ip) || StringUtils.isEmpty(interfaceName)) {
            throw new IllegalArgumentException("required id, ip and interfaceName");
        }
 
        this.id = id;
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
        this.multicastGroup = new InetSocketAddress(ip, port);
 
        try {
            this.networkInterface = NetworkInterface.getByName(interfaceName);
        } catch (SocketException e) {
            throw new RuntimeException("unable to start broadcaster", e);
        }
    }
 
    @Override
    public ScheduledExecutorService getService() {
        return this.scheduler;
    }
 
    void run(final CountDownLatch endLatch) {
        assert !Objects.isNull(endLatch);
 
        try (DatagramChannel channel = DatagramChannel.open()) {
 
            initChannel(channel);
            doSchedule(channel);
 
            endLatch.await();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("unable to run broadcaster", e);
        } finally {
            this.scheduler.shutdownNow();
        }
    }
 
    private void doSchedule(final DatagramChannel channel) {
        assert !Objects.isNull(channel);
 
        doSchedule(channel, new Runnable() {
            public void run() {
                System.out.println(String.format("Multicasting for %s", Multicaster.this.id));
 
                try {
                    Multicaster.this.doBroadcast(channel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0L, Constants.Schedule.PULSE_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
    }
 
    private void initChannel(final DatagramChannel channel) throws IOException {
        assert !Objects.isNull(channel);
 
        channel.bind(null);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, this.networkInterface);
    }
 
    private void doBroadcast(final DatagramChannel channel) throws IOException {
        assert !Objects.isNull(channel);
 
        Pulse.broadcast(this.id, this.multicastGroup, channel);
    }
}
