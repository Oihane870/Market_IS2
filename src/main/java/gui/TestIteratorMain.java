package gui;

import businesslogic.BLFacade;
import businesslogic.BLFacadeFactory;
import businesslogic.ExtendedIterator;
import domain.Sale;

public class TestIteratorMain {

    public static void main(String[] args) {
        try {
            // Obtenemos la lógica de negocio mediante la factoría creada en el apartado 1
            BLFacadeFactory factory = new BLFacadeFactory();
            BLFacade blFacade = factory.createBLFacade();

            // Obtenemos el iterador extendido para una descripción dada (ej. "")
            ExtendedIterator it = blFacade.getSalesIterator("");
            Sale s;

            System.out.println("_____________________");
            System.out.println("FROM LAST TO FIRST");
            it.goLast(); // Ir al último elemento
            while (it.hasPrevious()) {
                s = (Sale) it.previous();
                System.out.println(s);
            }

            System.out.println();
            System.out.println("_____________________");
            System.out.println("FROM FIRST TO LAST");
            it.goFirst(); // Ir al primer elemento
            while (it.hasNext()) {
                s = (Sale) it.next();
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
