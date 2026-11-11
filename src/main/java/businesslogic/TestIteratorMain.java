package businesslogic;



import dataAccess.ExtendedIterator;
import domain.Sale;

public class TestIteratorMain {

    public static void main(String[] args) {
        try {
            // 1. Obtenemos la lógica de negocio mediante la factoría
        	boolean isLocal = true;
            BLFactory factory = new BLFactory();
            BLFacade blFacade = factory.getBusinessLogicFactory(isLocal);

            // (Omitimos initializeBD() para evitar duplicados en la base de datos)

            // 2. Obtenemos el iterador extendido pasando una cadena vacía ""
            ExtendedIterator it = (ExtendedIterator) blFacade.getSalesIterator("");
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