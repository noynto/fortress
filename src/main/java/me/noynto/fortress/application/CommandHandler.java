package me.noynto.fortress.application;

public interface CommandHandler<R, C> {
    R handle(C b);
}
