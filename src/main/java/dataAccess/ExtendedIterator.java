package dataAccess; 

import java.util.Iterator;

public interface ExtendedIterator extends Iterator {

    // Return the actual element and go to the previous 
    public Object previous();	

    // True if there is a previous element 
    public boolean hasPrevious();	

    // It is placed in the first element 
    public void goFirst();	

    // It is placed in the last element 
    public void goLast();	
}