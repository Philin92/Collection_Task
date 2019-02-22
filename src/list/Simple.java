package list;

import java.util.Collection;

public interface Simple<T> extends Iterable<T> {

    boolean add(T t);

    boolean addAll(Simple<? extends T> list);

    void remove(int index);

    T get(int index);

    int size();

    void update(int index, T t);
}
