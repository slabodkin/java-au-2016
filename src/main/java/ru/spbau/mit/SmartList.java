package ru.spbau.mit;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

public class SmartList<E> extends AbstractList<E> implements List<E> {
    private int size;
    private Object data;

    public SmartList() {
        size = 0;
        data = null;
    }
    public SmartList(Collection<E> col) {
        init(col);
    }
    private void init(Collection<E> col) {
        size = col.size();

        if (size == 0)
        {
            data = null;
            return;
        }
        if (size == 1) {
            for (E elt : col) {
                data = elt;
            }
            return;
        }
        if (size <= 5) {
            Object[] tempData = new Object[size];
            int i = 0;
            for (E elt : col) {
                tempData[i] = elt;
                i++;
            }
            data = tempData;
            return;
        }
        data = new ArrayList<>(col);
    }

    //private void init2to5()


    @Override
    public boolean add(E elt) {
        if (size == 0) {
            size++;
            data = elt;
            return true;
        }
        if (size == 1) {
            size++;
            Object[] tempData = new Object[size];
            tempData[0] = data;
            tempData[1] = elt;
            data = tempData;
            return true;
        }
        if (size < 5) {
            size++;
            Object[] tempData = new Object[size];
            Object[] oldData = (Object[]) data;
            for (int i = 0; i < size - 1; i++) {
                tempData[i] = oldData[i];
            }
            tempData[size - 1] = elt;
            data = tempData;
            return true;
        }
        if (size == 5) {
            size++;
            List<E> tempData = new ArrayList<>();
            Object[] oldData = (Object[]) data;
            for (int i = 0; i < size - 1; i++) {
                tempData.add((E) oldData[i]);
            }
            tempData.add(elt);
            data = tempData;
            return true;
        }
        size++;
        ((List<E>) data).add(elt);
        return true;
    }

    @Override
    public void add(int i, E elt) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 0) {
            size++;
            data = elt;
            return;
        }
        if (size == 1) {
            size++;
            Object[] tempData = new Object[size];
            tempData[i] = elt;
            tempData[1 - i] = data;
            data = tempData;
            return;
        }
        if (size < 5) {
            size++;
            Object[] tempData = new Object[size];
            Object[] oldData = (Object[]) data;
            for (int j = 0; j < i; j++) {
                tempData[j] = oldData[j];
            }
            for (int j = i + 1; j < size; j++) {
                tempData[j] = oldData[j - 1];
            }
            tempData[i] = elt;
            data = tempData;
            return;
        }
        if (size == 5) {
            size++;
            List<E> tempData = new ArrayList<>();
            Object[] oldData = (Object[]) data;
            for (int j = 0; j < i; j++) {
                tempData.add((E) oldData[j]);
            }
            tempData.add(elt);
            for (int j = i + 1; j < i; j++) {
                tempData.add((E) oldData[j-1]);
            }
            data = tempData;
            return;
        }
        size++;
        ((List<E>) data).add(i, elt);
        return;
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            return null;
        }

        E res = (E) get(i);
        if (size == 1) {
            size--;
            data = null;
            return res;
        }

        if (size == 2) {
            size--;
            data = ((Object[]) data)[1-i];
            return res;
        }

        if (size <= 5) {
            size--;
            Object[] tempData = new Object[size];
            Object[] oldData = (Object[]) data;
            for (int j = 0; j < i; j++) {
                tempData[j] = oldData[j];
            }
            for (int j = i; j < size; j++) {
                tempData[j] = oldData[j + 1];
            }
            data = tempData;
            return res;
        }
        if (size == 6) {
            size--;
            Object[] tempData = new Object[size];
            List<E> oldData = (List<E>) data;
            for (int j = 0; j < i; j++) {
                tempData[j] = oldData.get(j);
            }
            for (int j = i; j < size; j++) {
                tempData[j] = oldData.get(j + 1);
            }
            data = tempData;
            return res;
        }
        size--;
        return ((List<E>) data).remove(i);
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            return (E) data;
        }
        if (size <= 5) {
            return (E) ((Object[]) data)[i];
        }
        return (E) ((List<E>) data).get(i);
    }

    @Override
    public E set(int i, E elt) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        E res = get(i);
        if (size == 1) {
            data = elt;
            return res;
        }
        if (size <= 5) {
            ((Object[]) data)[i] = elt;
            return res;
        }
        ((List<E>) data).set(i, elt);
        return res;
    }


    public int size() {
        return size;
    }
}
