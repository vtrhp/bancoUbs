package com.banco.ubs.heartbeat;

public final class Constants {
	 public static final String INSTANTIATION_NOT_ALLOWED = "Instantiation not allowed";

	    public static final class Broadcast {

	        public static final String MULTICAST_IP = "239.1.1.1";
	        public static final int MULTICAST_PORT = 9999;

	        private Broadcast() {
	            throw new IllegalStateException(INSTANTIATION_NOT_ALLOWED);
	        }
	    }

	    public static final class Schedule {
	        public static final long DOWNTIME_TOLERANCE_IN_MILLISECONDS = 15000;
	        public static final long DOWNTIME_TOLERANCE_DEAD_SERVICE_IN_MILLISECONDS = 30000;
	        public static final long PULSE_DELAY_IN_MILLISECONDS = 5000;
	        public static final int POOL_SIZE = 1;

	        private Schedule() {
	            throw new IllegalStateException(INSTANTIATION_NOT_ALLOWED);
	        }
	    }

	    private Constants() {
	        throw new IllegalStateException(INSTANTIATION_NOT_ALLOWED);
	    }

}
