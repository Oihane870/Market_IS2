package domain;

import businesslogic.BLFacade;
import businesslogic.BLFacadeFactory;

public class AdapterTest {

    public static void main(String[] args) {	
        // Usamos createBLFacade() que es el método real que existe en tu BLFacadeFactory
        BLFacade blFacade = new BLFacadeFactory().createBLFacade();	
        
        // Autenticar vendedor
        Seller s = blFacade.isLogged("Aitor Fernandez", "aurrera");	
        
        // Crear la ventana de la tabla y hacerla visible
        SellerTable st = new SellerTable(s);	
        st.setVisible(true);	
    }
}