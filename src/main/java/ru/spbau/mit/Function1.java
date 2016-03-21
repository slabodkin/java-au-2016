package ru.spbau.mit;

/**
 * Created by vbv on 21.03.16.
 */
public interface Function1<T, R> {
    R apply(T arg);

    default <R_out> Function1<T, R_out> compose(final Function1<? super R, R_out> g) {
        return arg -> g.apply(Function1.this.apply(arg));
    }
}
