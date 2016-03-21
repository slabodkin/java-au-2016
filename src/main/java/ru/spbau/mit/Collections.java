package ru.spbau.mit;

/**
 * Created by vbv on 21.03.16.
 */
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Collections {
    //private Collections() {}

    public static <T, R> Iterable<R> map(Function1<? super T, ? extends R> f, Iterable<T> col) {
        ArrayList<R> res = new ArrayList<>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            res.add(f.apply(it.next()));
        }
        return res;
    }

    public static <T extends R, R> Iterable<R> filter(Predicate<? super T> p, Iterable<T> col) {
        ArrayList<R> res = new ArrayList<>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            T tmp = it.next();
            if (p.apply(tmp)) {
                res.add(tmp);
            }
        }
        return res;
    }

    public static <T extends R, R> Iterable<R> takeWhile(Predicate<? super T> p, Iterable<T> col) {
        ArrayList<R> res = new ArrayList<>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            T tmp = it.next();
            if (!p.apply(tmp)) {
                break;
            } else {
                res.add(tmp);
            }
        }
        return res;
    }

    public static <T extends R, R> Iterable<R> takeUnless(Predicate<? super T> p, Iterable<T> col) {
        ArrayList<R> res = new ArrayList<>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            T tmp = it.next();
            if (!p.apply(tmp)) {
                res.add(tmp);
            } else {
                break;
            }
        }
        return res;
    }

    public static <R1 extends R, T, R> R foldl(Function2<? super R1,
            ? super T, ? extends R1> f, R1 start, Iterable<T> col) {
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            R1 tmp = f.apply(start, it.next());
            start = tmp;
        }
        return start;
    }

    public static <R1 extends R, T, R> R foldr(Function2<? super T,
            ? super R1, ? extends R1> f, R1 start, Iterable<T> col) { //reversed arguments of f
        ArrayList<T> assessoir = new ArrayList<>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            assessoir.add(it.next());
        }
        for (int i = assessoir.size() - 1; i >= 0; i--) {
            R1 tmp = f.apply(assessoir.get(i), start);
            start = tmp;
        }
        return start;
    }
}
