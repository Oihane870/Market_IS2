package gui;

import businesslogic.BLFacade;
import dataAccess.ExtendedIterator;

import businesslogic.BLFactory;

import domain.Sale;

public class TestIteratorMain {

 
    public	static	void	main(String[]	args)	{	
//	the	BL	is	local	
        boolean	isLocal	=	true;	
        BLFacade	blFacade	=	new	BLFactory().getBusinessLogicFactory(isLocal);	
        ExtendedIterator	it =	blFacade.getSalesIterator("");
        Sale	s;	
        System.out.println("_____________________");	
        System.out.println("FROM	LAST	TO	FIRST");	
        it.goLast();	//	Go	to	last	element	
        while	(it.hasPrevious())	{	
            s	=	(Sale) it.previous();	
            System.out.println(s);	
        }	
        System.out.println();	
        System.out.println("_____________________");	
        System.out.println("FROM	FIRST	TO	LAST");	
        it.goFirst();	//	Go	to	first	element	
        while	(it.hasNext())	{	
            s	=	(Sale) it.next();	
            System.out.println(s);	
        }	
    }	
}
