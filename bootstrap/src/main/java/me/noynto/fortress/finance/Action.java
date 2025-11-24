package me.noynto.fortress.finance;

public interface Action<A> {
    A execute() throws Failed;

    class Failed extends Exception {
        public Failed(String message) {
            super(message);
        }

        public Failed(String message, Throwable cause) {
            super(message, cause);
        }

        public Failed(Throwable cause) {
            super(cause);
        }
    }
}
