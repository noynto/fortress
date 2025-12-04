package me.noynto.fortress;

public interface Provider {
    class NotAvailable extends RuntimeException {
        public NotAvailable(String message) {
            super(message);
        }
        public NotAvailable(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
