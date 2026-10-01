

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

public class BuyBasketMockBlackTest {
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	private String buyer1Email;
	private String buyer2Email;
	private Seller buyer1;
	private Seller buyer2;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
	    
	    buyer1Email = "buyer1@gmail.com";
		buyer1 = new Seller(buyer1Email, "Buyer 1", "test");
		buyer1.setMoney(100f);
		Mockito.when(db.find(Seller.class, buyer1Email)).thenReturn(buyer1);

	    
		buyer2Email = "buyer2@gmail.com";
		buyer2 = new Seller(buyer2Email, "Buyer 2", "test");
		buyer2.setMoney(100f);
		Mockito.when(db.find(Seller.class, buyer2Email)).thenReturn(buyer2);


	    
	}
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	@Test
	public void test1() {
			
			Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
			Sale sale1 = seller1.addSale("prod1", "desc1", 0, 20f, new Date(), null);
			float moneyBefore = buyer1.getMoney();
			buyer1.getBasket().add(sale1);

			sut.open();
			boolean result = sut.buyBasket(buyer1Email);
			sut.close();

			assertTrue(result);
			assertTrue(buyer1.getBasket().isEmpty());
			assertEquals((moneyBefore - sale1.getPrice()), buyer1.getMoney(), 0.001f);
			assertEquals(buyer1, sale1.getBuyer());
			Mockito.verify(et).commit();
			Mockito.verify(et, Mockito.never()).rollback();
		
		
	}
	@Test
	public void test2() {
		
			sut.open();
			boolean result = sut.buyBasket(null);
			sut.close();
			assertFalse(result);
			Mockito.verify(et, Mockito.never()).commit();
		
		
	}
	@Test
	public void test3() {
		
			sut.open();
			boolean result = sut.buyBasket("");
			sut.close();
			assertFalse(result);
			Mockito.verify(et, Mockito.never()).commit();

		
	}
	@Test
	public void test4() {
	
			sut.open();
			boolean result = sut.buyBasket("buyer");
			sut.close();
			assertFalse(result);
			Mockito.verify(et, Mockito.never()).commit();

		
	}
	@Test
	public void test5() {
		
			sut.open();
			boolean result = sut.buyBasket("@gmail.com");
			sut.close();
			assertFalse(result);
			Mockito.verify(et, Mockito.never()).commit();

		
	}
	@Test
	public void test6() {
		
			sut.open();
			boolean result = sut.buyBasket("buyer111@gmail.com");
			sut.close();
			assertFalse(result);
			Mockito.verify(et).rollback();
			Mockito.verify(et, Mockito.never()).commit();
		
		
	}
	@Test
	public void test7() {
		
			buyer2.setBasket(null);
			sut.open();
			boolean result = sut.buyBasket(buyer2Email);
			sut.close();
			assertFalse(result);
			Mockito.verify(et).rollback();
			Mockito.verify(et, Mockito.never()).commit();
		
	}
	@Test
	public void test8() {
		
			
			sut.open();
			boolean result = sut.buyBasket(buyer2Email);
			sut.close();
			assertFalse(result);
			Mockito.verify(et).rollback();
			Mockito.verify(et, Mockito.never()).commit();
		
	}
	@Test
	public void test9() {
		
		
			
			Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
		    Seller seller2 = new Seller("seller2@gmail.com", "Seller 2", null);
		    Sale sale1 = seller1.addSale("prod1", "desc1", 0, 20f, new Date(), null);
		    Sale sale2 = seller2.addSale("prod2", "desc2", 0, 30f, new Date(), null);

		    buyer2.getBasket().add(sale1);
		    buyer2.getBasket().add(sale2);
		    
		    sut.open();
			boolean result = sut.buyBasket("buyer1@gmail.com");
			sut.close();
			assertFalse(result);
			assertEquals(2, buyer2.getBasket().size());
			assertEquals(100f, buyer2.getMoney(), 0.001f);
			Mockito.verify(et).rollback();
			Mockito.verify(et, Mockito.never()).commit();
		
		
	}
	@Test
	public void test10() {
		buyer2.setMoney(10f);
	
		Seller seller1 = new Seller("seller1@gmail.com", "Seller 1", null);
		Sale sale1 = seller1.addSale("prod1", "desc1", 0, 20f, new Date(), null);
		buyer2.getBasket().add(sale1);
			
		sut.open();
		boolean result = sut.buyBasket("buyer1@gmail.com");
		sut.close();
		
		assertFalse(result);
		assertEquals(10f, buyer2.getMoney(), 0.001f);
		assertEquals(1, buyer2.getBasket().size());
		Mockito.verify(et).rollback();
		Mockito.verify(et, Mockito.never()).commit();
		
		
	}
	
}
