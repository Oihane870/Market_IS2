package testOperations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.mockito.Mockito;

import businesslogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import domain.Seller;
import gui.MainGUI;

public class BuyBasketMockTest {
	static BLFacade appFacadeMock = Mockito.mock(BLFacade.class);
	public static void main(String args[]) {
		configureMockQuerySales();   
	    configureMockBasket();
	    configureMockBuyBasket();
		
		MainGUI sut = new MainGUI("buyer1@gmail.com");
		MainGUI.setBussinessLogic(appFacadeMock);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sut.setVisible(true);
	}
	public static void configureMockBasket() {
        Seller seller1 = new Seller("seller1@gmail.com", "Aitor", null);
        Date today = UtilDate.trim(new Date());

        List<Sale> basket = new Vector<Sale>();
        basket.add(new Sale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,
                today, null, seller1));
        basket.add(new Sale("salomon mendiko botak", "44 zenbakia, 3 ateraldi", 2, 20,
                today, null, seller1));

        // AJUSTAR: nombre real del método de BLFacade que devuelve la cesta
        Mockito.when(appFacadeMock.getBasket(anyString())).thenReturn(basket);
    }
	
	public static void configureMockBuyBasket() {
        Mockito.when(appFacadeMock.buyBasket(anyString())).thenReturn(true);
    }
	public static void configureMockQuerySales() {
	    Seller seller1 = new Seller("seller1@gmail.com", "Aitor", null);
	    Date today = UtilDate.trim(new Date());

	    List<Sale> sales = new Vector<Sale>();
	    sales.add(new Sale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,
	            today, null, seller1));
	    sales.add(new Sale("salomon mendiko botak", "44 zenbakia, 3 ateraldi", 2, 20,
	            today, null, seller1));

	    Mockito.when(appFacadeMock.getPublishedSales(anyString(), any(Date.class)))
	           .thenReturn(sales);
	}
	
	
	
}