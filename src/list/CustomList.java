package list;

import java.util.Collection;
import java.util.Iterator;

public class CustomList<T> implements Simple<T> {

    private T[] array;
    private int numElements;

    public CustomList() {
        array = (T[]) new Object[10];
        numElements = 0;
    }

    @Override
    public boolean add(T t) {

            if(t == null){
                return false;
            }

            if(numElements == array.length){

                T[] newArray = (T[]) new Object[numElements*2];

                System.arraycopy(array,0,newArray,0,numElements);
                newArray[numElements] = t;
                numElements++;
                array = newArray;

                return true;
            }
            else {
                array[numElements] = t;
                numElements++;
                return true;
            }

    }

    @Override
    public boolean addAll(Simple<? extends T> list) {
        if(list == null){
            return false;
        }else {
            for(T elem: list){
                this.add(elem);
            }
            return true;
        }
    }

    @Override
    public void remove(int index) {
        try {

            T[] temp = array;
            array = (T[]) new Object[temp.length-1];
            System.arraycopy(temp,0,array,0,index);
            System.arraycopy(temp,index+1,array,index,temp.length-index-1);

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public void update(int index, T t) {
        array[index] = t;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<>(array);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T backingElem : array) {
            if(backingElem!=null)
                sb.append(backingElem).append(" ");
        }
        return sb.toString();
    }
}
