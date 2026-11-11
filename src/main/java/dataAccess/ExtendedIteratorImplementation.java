package dataAccess;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import domain.Sale;

public class ExtendedIteratorImplementation implements ExtendedIterator {
    private List<Sale> elements;
    private int currentIndex;

    public ExtendedIteratorImplementation(List<Sale> sales) {
        this.elements = sales;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < elements.size();
    }

    @Override
    public Object next() {
        if (!hasNext()) throw new NoSuchElementException();
        return elements.get(currentIndex++);
    }

    @Override
    public Object previous() {
    	if (hasPrevious()) {
            currentIndex--;
            return elements.get(currentIndex);
        }
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    @Override
    public void goFirst() {
        currentIndex = 0;
    }

    @Override
    public void goLast() {
    	this.currentIndex = elements.size();
    }
}