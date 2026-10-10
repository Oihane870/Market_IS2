import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

public class BuyProductMockBlackTest {

	static DataAccess sut;

	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	private static final String BUYER = "buyerProduct@gmail.com";
	private static final String BUYER_FAKE = "buyerProductFake@gmail.com";

	private Seller buyer;
	private Seller other;
	private Sale saleA;      
	private Sale sale100;   
	private Sale sale10001; 
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);

		buyer = new Seller(BUYER, "Buyer", null);
		buyer.setMoney(100f);
		other = new Seller("otherProduct@gmail.com", "Other", null);
		Seller seller = new Seller("sellerProduct@gmail.com", "Seller", null);
		saleA = newSale(seller, 1, "prodA", 20f);
		sale100 = newSale(seller, 2, "prod100", 100f);
		sale10001 = newSale(seller, 3, "prod100.01", 100.01f);

		Mockito.when(db.find(Seller.class, BUYER)).thenReturn(buyer);
		Mockito.when(db.find(Seller.class, BUYER_FAKE)).thenReturn(null);
		Mockito.when(db.find(Sale.class, 1)).thenReturn(saleA);
		Mockito.when(db.find(Sale.class, 2)).thenReturn(sale100);
		Mockito.when(db.find(Sale.class, 3)).thenReturn(sale10001);
		Mockito.when(db.find(Sale.class, -1)).thenReturn(null);
		Mockito.when(db.find(Seller.class, null)).thenThrow(new IllegalArgumentException("Unexpected null argument"));
		Mockito.when(db.find(Sale.class, null)).thenThrow(new IllegalArgumentException("Unexpected null argument"));
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	private Sale newSale(Seller seller, int number, String title, float price) {
		Sale s = seller.addSale(title, "desc " + title, 0, price, new Date(), null);
		s.setSaleNumber(number);
		return s;
	}

	@Test
	public void test1() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, 1);
		sut.close();

		assertTrue(result);
		assertEquals(80f, buyer.getMoney(), 0.001);
		assertEquals(buyer, saleA.getBuyer());
		assertNotNull(saleA.getBidalketa());
	}

	@Test
	public void test2() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, 2);
		sut.close();

		assertTrue(result);
		assertEquals(0f, buyer.getMoney(), 0.001);
		assertEquals(buyer, sale100.getBuyer());
	}

	@Test
	public void test3() {
		sut.open();
		boolean result = sut.buyProduct(null, 1);
		sut.close();

		assertFalse(result);
		assertNull(saleA.getBuyer());
	}

	@Test
	public void test4() {
		sut.open();
		boolean result = sut.buyProduct(BUYER_FAKE, 1);
		sut.close();

		assertFalse(result);
		assertNull(saleA.getBuyer());
	}

	@Test
	public void test5() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, null);
		sut.close();

		assertFalse(result);
		assertEquals(100f, buyer.getMoney(), 0.001);
	}

	@Test
	public void test6() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, -1);
		sut.close();

		assertFalse(result);
		assertEquals(100f, buyer.getMoney(), 0.001);
	}

	@Test
	public void test7() {
		saleA.setBuyer(other);

		sut.open();
		boolean result = sut.buyProduct(BUYER, 1);
		sut.close();

		assertFalse(result);
		assertEquals(other, saleA.getBuyer());
		assertEquals(100f, buyer.getMoney(), 0.001);
	}

	@Test
	public void test8() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, 3);
		sut.close();

		assertFalse(result);
		assertNull(sale10001.getBuyer());
		assertEquals(100f, buyer.getMoney(), 0.001);
	}
}
