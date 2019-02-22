package set;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CustomSet<T> implements Set<T>, Iterable<T> {

    private T[] backingArray; // ИСХОДНЫЕ ЭЛЕМНТЫ БУДЕМ ХРАНИТЬ В МАССИВЕ
    private int numElements; // РАЗМЕР МАССИВА

    public CustomSet() {

        backingArray = (T[]) new Object[10]; // ПЕРВОНОЧАЛЬНЫЙ РАЗМЕР МАССИВА НА 10 ЭЛЕМЕНТОВ
        numElements = 0;

    }

    public boolean add(T t){

        for(Object elem: backingArray){                    // ПРОГОНЯЕМ ЭЛЕМЕНТЫ МАССИВА
            if(elem == null ? t == null : elem.equals(t)){ // ПРОВЕРКА КАЖДОГО ЭЛЕМЕНТА НАШЕГО МАССИВА НА NULL И В СЛУЧАЕ ЕСЛИ ЭТО ТАК
                return false;                              // ЭЛЕМЕНТ С ЗНАЧЕНИИЕМ NULL НЕ ВСТАВИТСЯ В МАССИВ, В ТОЖЕ ВРЕМЯ В МАССИВ
            }                                              // НЕ ВСТАВИТСЯ ЭЛЕМЕНТ КОТОРЫЙ В НЁМ УЖЕ ЕСТЬ
        }

        if(numElements == backingArray.length){             // ЕСЛИ МАССИВ ПОЛНОСТЬЮ ЗАПОЛНЕН, СОЗДАЁМ НОВЫЙ МАССИВ, В 2 РАЗА БОЛЬШЕ,

            T[] newArray = Arrays.copyOf(backingArray,backingArray.length*2);   // И КОПИРУЕМ В НЕГО СТАРЫЙ МАССИВ
            newArray[numElements] = t;
            numElements++;                                  // УВЕЛИЧИВАЯ ИДЕКС, КУДА БУДЕТ ВСТАВЛЕН НОВЫЙ ЭЛЕМЕНТ
            backingArray = newArray;

            return true;
        }
        else {
            backingArray[numElements] = t;
            numElements++;
            return true;
        }
    }

    public boolean addAll(Collection<? extends T> c){   // ПЕРЕБОРОМ КОЛЛЕКЦИИ ПО ОЧЕРЕДИ ВСТАВЛЯЕМ ЭЛЕМЕНТЫ

        for(T elem: c){
            this.add(elem);
        }
        return true;

    }

    public void clear(){

        T[] newArray = (T[]) new Object[backingArray.length];
        numElements = 0;
        backingArray = newArray; // ПРИСВАИВАЕМ ССЫЛКУ СТАРОГО МАССИВА НА НОВЫЙ ПУСТОЙ МАССИВ

    }

    public boolean equals(Object o) { // РЕАЛИЗАЦИЯ КАК У ABSTRACT_SET

        if (o == this)                // ПРОВЕРКА НА РЕФЛЕКСИВНОСТЬ
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    public int hashCode(){

        int sum=0;
        for(T elem:backingArray){
            if(elem!=null){
                sum=sum+elem.hashCode();
            }
        }
        return sum;

    }

    public boolean contains(Object o){

        if(o == null)                               // ЕСЛИ ОБЪЕКТ РАВЕН NULL ВОЗВРАЩАЕМ FALSE
            return false;

        for(T backingElem: backingArray){           // ПРОХОДИМ ВСЮ КОЛЛЕКЦИЮ И ПРОВЕРЯЕМ ЭЛЕМЕНТЫ НА РАВЕНСТВО С ОБЪЕКТОМ
            if(o.equals(backingElem)){
                return true;
            }
        }

        return false;
    }

    public boolean containsAll(Collection<?> c){
        for(T elem:(Set<T>)c){
            if(!(this.contains(elem))){
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty(){
        if(numElements==0){
            return true;
        }else {
            return false;
        }
    }

    public boolean remove(Object o){            // ПРОХОДИМ ПО МАССИВУ И ПРОВЕРЯЕМ НА РАВЕНСТВО ЭЛЕМЕНТА МАССИВА И ОБЪЕКТА В ПАРАМЕТРАХ МЕТОДА
                                                // КОГДА НАХОДИМ ЕГО ПРОИЗВОДИМ КОПИРОВАНИЕ ВСЕХ ЭЛЕМЕНТОВ МАССИВА, КОТОРЫЕ НАХОДЯТСЯ ПРАВЕЕ ЕГО
                                                // В НАШ МАССИВ, НА ЕГО ИНДЕКС, ПОСЛЕДНИЙ ЭЛЕМЕНТ МАССИВА ЗАНУЛЯЕМ И УМЕЬХАЕМ КОЛ-ВО ЭЛЕМЕНТОВ НА 1
        int i = 0;
        for(Object elem: backingArray){
            if(o != null && o.equals(elem)){
                System.arraycopy(backingArray,i+1,backingArray,i,numElements-i-1);
                backingArray[numElements-1] = null;
                numElements = numElements - 1;
                return true;
            }
            i++;
        }
        return false;

    }

    public boolean removeAll(Collection<?> c){
        for(Object elem: c){
            this.remove(elem);
        }
        return true;
    }

    public boolean retainAll(Collection<?> c){

        int index = 0;
        boolean result = false;
        if(this.containsAll(c)){        // ЕСЛИ НАША КОЛЛЕКЦИЯ СОДЕРЖИТ ВСЕ ЭЛЕМЕНТЫ ПЕРЕДАННОЙ, ТО НИЧЕГО УДАЛАЯТЬ НЕ НАДО
            result = true;
        }

        while (index < numElements){
            T t = backingArray[index];
            if(t != null && !(c.contains(t))){      // ПРОВЕРКА НА ОТСУТСТВИЕ ЭЛЕМЕНТА МАССИВА В ПЕРЕДАННОЙ КОЛЛЕКЦИИ
                this.remove(t);                     // И ЕСЛИ ЭТО ПОДТВЕРЖДАЕТСЯ, УДАЛЯЕМ ЭЛЕМЕНТ
            }else {
                index++;
            }
        }

        return result;
    }

    public int size(){
        return numElements;
    }

    public <T> T[] toArray(T[] a) throws ArrayStoreException,NullPointerException{

        for(int i=0;i<numElements;i++){
            a[i] = (T)backingArray[i];
        }
        for(int j=numElements;j<a.length;j++){
            a[j] = null;
        }

        return a;
    }

    public Object[] toArray(){

        Object[] newArray = new Object[numElements];
        for(int i=0;i<numElements;i++){
            newArray[i] = backingArray[i];
        }
        return newArray;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T backingElem : backingArray) {
            if(backingElem!=null)
            sb.append(backingElem).append(" ");
        }
        return sb.toString();
    }

    public Iterator<T> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T>{

        private int currIndex;
        private T lastElement;

        public SetIterator() {
            currIndex = 0;
            lastElement = null;
        }

        @Override
        public boolean hasNext() {
            while(currIndex<=numElements && backingArray[currIndex]==null){
                currIndex++;
            }
            if(currIndex<=numElements){
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T element = backingArray[currIndex];
            currIndex++;
            lastElement = element;
            return element;
        }

        @Override
        public void remove() throws UnsupportedOperationException,IllegalStateException{
            if(lastElement!=null){
                CustomSet.this.remove(lastElement);
                numElements--;
            }else {
                throw new IllegalStateException();
            }
        }
    }
}
