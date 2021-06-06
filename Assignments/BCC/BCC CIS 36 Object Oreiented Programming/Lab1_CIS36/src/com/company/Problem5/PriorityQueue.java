package com.company.Problem5;

import java.util.*;

public class PriorityQueue<T extends Comparable> implements Queue {

    private ArrayList<Comparable> arrayList;
    private int index;
    private Iterator<Comparable> iterator;

    /** Constructor method of the priority queue. returns an empty priority queue */
    public PriorityQueue() {
        this.arrayList = new ArrayList<>();
        this.index = 0;
        this.iterator = getIterator();
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        if(arrayList.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return arrayList.contains(o);
    }

    @Override
    public Iterator iterator() {
        return arrayList.iterator();
    }

    @Override
    public Object[] toArray() {
        Iterator<Comparable> tmp = getIterator();
        Object[] result = new Object[arrayList.size()];
        int i = 0;
        while(tmp.hasNext()) {
           result[i] = String.valueOf(tmp.next());
           i++;
        }
        return result;
    }

    @Override
    public Object[] toArray(Object[] a) {
        Object[] result = new Object[a.length];
        for(int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        return result;
    }

    @Override
    public boolean add(Object o) {
        return arrayList.add((Comparable) o);
    }

    @Override
    public boolean remove(Object o) {
        return arrayList.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return arrayList.addAll(c);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
        return arrayList.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return arrayList.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return arrayList.containsAll(c);
    }

    @Override
    public boolean offer(Object o) {
        if(o.getClass() == Comparable.class){
            return add(o);
        }
        return false;
    }

    @Override
    public Object remove() {
        for(int i = 0; i < arrayList.size()-1; i++) {
            if(arrayList.get(i).compareTo(arrayList.get(i+1)) < 0){
                index = i;
            }
        }
        Comparable result = arrayList.get(index);
        arrayList.remove(index);
        return result;
    }

    @Override
    public Object poll() {
        if(arrayList.size() == 0) {
            return null;
        }
        Object result = this.arrayList.get(0);
        this.remove(this.arrayList.get(0));
        return result;
    }

    @Override
    public Object element() {
        if(arrayList.size() == 0) {
            throw new NullPointerException();
        }
        return this.arrayList.get(0);

    }

    @Override
    public Object peek() {
        if(arrayList.size() == 0) {
            return null;
        }
        return this.arrayList.get(0);
    }


    /** Add methods takes in the comparable type as a parameter */
    public boolean Add(Comparable x) {
        return arrayList.add(x);
    }

    /** Removes the highest priority and returns it*/
    public Comparable Remove() {
        for(int i = 0; i < arrayList.size()-1; i++) {
            if(arrayList.get(i).compareTo(arrayList.get(i+1)) < 0){
                index = i;
            }
        }
        Comparable result = arrayList.get(index);
        arrayList.remove(index);
        return result;
    }

    /** Returns an iteratot of ther priority queue */
    public Iterator<Comparable> getIterator() {
        this.index = 0;
        iterator = new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(arrayList.size() > index) {
                    return true;
                }
                return false;
            }

            @Override
            public Comparable next() {
                index++;
                return arrayList.get(index-1);
            }
        };
        return iterator;
    }
}
