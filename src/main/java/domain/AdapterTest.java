package domain;

import businesslogic.BLFacade;

import businesslogic.BLFactory;

public class AdapterTest {

    public static void main(String[] args) {	
        // Usamos createBLFacade() que es el método real que existe en tu BLFacadeFactory
        boolean isLocal = true;
    	BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);	
        
        // Autenticar vendedor
        Seller s = blFacade.isLogged("Aitor Fernandez", "aurrera");	
        
        // Crear la ventana de la tabla y hacerla visible
        SellerTable st = new SellerTable(s);	
        st.setVisible(true);	
    }
}