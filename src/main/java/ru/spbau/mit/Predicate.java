package ru.spbau.mit;

/**
 * Created by vbv on 21.03.16.
 */
interface Predicate<T> extends Function1<T, Boolean> {
    @Override
    Boolean apply(T arg);

    default <U extends T> Predicate<U> and(final Predicate<? super T> q) {
        return arg -> Predicate.this.apply(arg) && q.apply(arg);
    }

    default <U extends T> Predicate<U> or(final Predicate<? super T> q) {
        return arg -> Predicate.this.apply(arg) || q.apply(arg);
    }

    default <U extends T> Predicate<U> not() {
        return arg -> !Predicate.this.apply(arg);
    }

    Predicate<Object> ALWAYS_TRUE = new Predicate<Object>() {
        @Override
        public Boolean apply(Object arg) {
            return true;
        }
    };

    Predicate<Object> ALWAYS_FALSE = new Predicate<Object>() {
        @Override
        public Boolean apply(Object arg) {
            return false;
        }
    };


}
