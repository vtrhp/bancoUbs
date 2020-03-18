package com.banco.ubs.heartbeat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public final class Pulse {
	
	 private final String id;
	    private final long instant;

	    static Optional<Pulse> read(final DatagramChannel channel) throws IOException {
	        assert !Objects.isNull(channel);

	        final ByteBuffer buffer = ByteBuffer.allocate(1024);
	        final SocketAddress address = channel.receive(buffer);

	        return (Objects.isNull(address)) ? Optional.empty() : Optional.of(new Pulse(new String(buffer.array())));
	    }

	    static void broadcast(final String name, final InetSocketAddress address, final DatagramChannel channel) throws IOException {
	        final ByteBuffer buffer = ByteBuffer.wrap(name.getBytes());
	        channel.send(buffer, address);
	    }

	    private Pulse(final String id) {
	        assert StringUtils.isNotEmpty(id);

	        this.id = id;
	        this.instant = System.currentTimeMillis();
	    }

	    String getId() {
	        return this.id;
	    }

	    long getInstant() {
	        return this.instant;
	    }

	    boolean isDead(final long tolerance) {
	        return System.currentTimeMillis() - this.instant >= tolerance;
	    }

	    boolean isValid(final long tolerance) {
	        return System.currentTimeMillis() - tolerance <= this.instant;
	    }

}
