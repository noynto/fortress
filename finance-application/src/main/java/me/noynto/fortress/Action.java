package me.noynto.fortress;

public interface Action<A> {
    A execute() throws Failed;

    class Failed extends Exception {
        public Failed(String message) {
            super(message);
        }
    }
}
