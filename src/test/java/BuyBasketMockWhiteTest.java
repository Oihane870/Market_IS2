

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Sale;
import domain.Seller;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

public class BuyBasketMockWhiteTest {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	private  Seller buyer; 
	private  String buyerEmail;
	

	@Before
    public  void init() {
		MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
	    
	    buyerEmail = "buyer1@gmail.com";
	    buyer = new Seller(buyerEmail, "Buyer", "test");
	    buyer.setMoney(100f);
	    
		
        Mockito.when(db.find(Seller.class, buyerEmail)).thenReturn(buyer);
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	
	@Test
	//sut.createSale:  Some of the parameters are null
	public void test1() {
		Mockito.when(db.find(Seller.class, buyerEmail))
        .thenThrow(new RuntimeException("fallo simulado en find"));
		Mockito.when(et.isActive()).thenReturn(true);

		sut.open();
		boolean result = sut.buyBasket(buyerEmail);
		sut.close();

		assertFalse(result);
		Mockito.verify(et).begin();
		Mockito.verify(et).rollback();
		Mockito.verify(et, Mockito.never()).commit();
	}
	
	@Test
	public void test2() {
		Mockito.doThrow(new RuntimeException("fallo simulado en begin"))
        .when(et).begin();
		Mockito.when(et.isActive()).thenReturn(false);

		sut.open();
		boolean result = sut.buyBasket(buyerEmail);
		sut.close();

		assertFalse(result);
		Mockito.verify(et, Mockito.never()).rollback();
		Mockito.verify(et, Mockito.never()).commit();
	
	}
	@Test
    // Caso 3: TRY2(F) IF5(T) — buyer ∉ BD
    public void test3() {
        Mockito.when(db.find(Seller.class, "buyerFake@gmail.com")).thenReturn(null);

        sut.open();
        boolean result = sut.buyBasket("buyerFake@gmail.com");
        sut.close();

        assertFalse(result);
    }

    @Test
    // Caso 4: IF10.1(T) — basket == null
    public void test4() {
        buyer.setBasket(null);

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertFalse(result);
    }

    @Test
    // Caso 5: IF10.2(T) — basket vacío
    public void test5() {
        // buyer ya se crea con basket vacío por defecto

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertFalse(result);
    }

    @Test
    // Caso 6: sellers distintos en la cesta
    public void test6() {
        Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
        Seller seller2 = new Seller("seller2@gmail.com", "Seller 2", null);
        Sale sale1 = seller1.addSale("prod1", "desc1", 0, 20f, new Date(), null);
        Sale sale2 = seller2.addSale("prod2", "desc2", 0, 30f, new Date(), null);

        buyer.getBasket().add(sale1);
        buyer.getBasket().add(sale2);

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertFalse(result);
    }

    @Test
    // Caso 7: mismo seller, saldo insuficiente
    public void test7() {
        buyer.setMoney(10f);
        Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
        Sale sale1 = seller1.addSale("prod1", "desc1", 0, 75f, new Date(), null);
        buyer.getBasket().add(sale1);

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertFalse(result);
    }

    @Test
    // Caso 8/10: 2 artículos, mismo seller, saldo suficiente
    public void test8() {
        Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
        Sale sale1 = seller1.addSale("prod1", "desc1", 0, 75f, new Date(), null);
        Sale sale2 = seller1.addSale("prod2", "desc2", 0, 15f, new Date(), null);
        buyer.getBasket().add(sale1);
        buyer.getBasket().add(sale2);

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertTrue(result);
        assertTrue(buyer.getBasket().isEmpty());
    }

    @Test
    // Caso 9: 1 artículo, mismo seller, saldo suficiente
    public void test9() {
        Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
        Sale sale1 = seller1.addSale("prod1", "desc1", 0, 75f, new Date(), null);
        buyer.getBasket().add(sale1);

        sut.open();
        boolean result = sut.buyBasket(buyerEmail);
        sut.close();

        assertTrue(result);
        assertTrue(buyer.getBasket().isEmpty());
    }
}
	
	
	
	
	
	
	
