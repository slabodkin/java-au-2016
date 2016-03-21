package ru.spbau.mit;

/**
 * Created by vbv on 21.03.16.
 */
public interface Function2<T1, T2, R> {
    R apply(T1 arg1, T2 arg2);

    default <R_out> Function2<T1, T2, R_out> compose(final Function1<? super R, R_out> g) {
        return (arg1, arg2) -> g.apply(Function2.this.apply(arg1, arg2));
    }

    default Function1<T2, R> bind1(T1 arg1) {
        return arg2 -> Function2.this.apply(arg1, arg2);
    }

    default Function1<T1, R> bind2(T2 arg2) {
        return arg1 -> Function2.this.apply(arg1, arg2);
    }

    default <U1 extends T1, U2 extends T2> Function1<U1, Function1<U2, R>> curry() {
        return arg1 -> (Function1<U2, R>) arg2 -> Function2.this.apply(arg1, arg2);
    }

}
