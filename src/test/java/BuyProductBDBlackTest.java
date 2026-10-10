import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class BuyProductBDBlackTest {

	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	private static final String BUYER = "buyerProduct@gmail.com";
	private static final String BUYER_FAKE = "buyerProductFake@gmail.com";
	private static final String OTHER = "otherProduct@gmail.com";
	private static final String SELLER = "sellerProduct@gmail.com";

	private Integer saleA;

	@Before
	public void setUp() {
		cleanAll();
		testDA.createUser(BUYER, "Buyer", 100f);
		testDA.createUser(OTHER, "Other", 0f);
		testDA.createUser(SELLER, "Seller", 0f);
		saleA = testDA.addSaleToSeller(SELLER, "prodA", 20f);
	}

	@After
	public void tearDown() {
		cleanAll();
	}

	private void cleanAll() {
		testDA.removeUserWithSales(BUYER);
		testDA.removeUserWithSales(BUYER_FAKE);
		testDA.removeUserWithSales(OTHER);
		testDA.removeUserWithSales(SELLER);
	}

	@Test
	public void test1() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, saleA);
		sut.close();

		assertTrue(result);
		assertEquals(80f, testDA.getUserMoney(BUYER), 0.001);
		assertEquals(BUYER, testDA.getSaleBuyer(saleA));
		assertTrue(testDA.hasBidalketa(saleA));
	}

	@Test
	public void test2() {
		Integer sale100 = testDA.addSaleToSeller(SELLER, "prod100", 100f);

		sut.open();
		boolean result = sut.buyProduct(BUYER, sale100);
		sut.close();

		assertTrue(result);
		assertEquals(0f, testDA.getUserMoney(BUYER), 0.001);
		assertEquals(BUYER, testDA.getSaleBuyer(sale100));
	}

	@Test
	public void test3() {
		sut.open();
		boolean result = sut.buyProduct(null, saleA);
		sut.close();

		assertFalse(result);
		assertNull(testDA.getSaleBuyer(saleA));
	}

	@Test
	public void test4() {
		sut.open();
		boolean result = sut.buyProduct(BUYER_FAKE, saleA);
		sut.close();

		assertFalse(result);
		assertNull(testDA.getSaleBuyer(saleA));
	}

	@Test
	public void test5() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, null);
		sut.close();

		assertFalse(result);
		assertEquals(100f, testDA.getUserMoney(BUYER), 0.001);
	}

	@Test
	public void test6() {
		sut.open();
		boolean result = sut.buyProduct(BUYER, -1);
		sut.close();

		assertFalse(result);
		assertEquals(100f, testDA.getUserMoney(BUYER), 0.001);
	}

	@Test
	public void test7() {
		testDA.setSaleBuyer(saleA, OTHER);

		sut.open();
		boolean result = sut.buyProduct(BUYER, saleA);
		sut.close();

		assertFalse(result);
		assertEquals(OTHER, testDA.getSaleBuyer(saleA));
		assertEquals(100f, testDA.getUserMoney(BUYER), 0.001);
	}

	@Test
	public void test8() {
		Integer sale10001 = testDA.addSaleToSeller(SELLER, "prod100.01", 100.01f);

		sut.open();
		boolean result = sut.buyProduct(BUYER, sale10001);
		sut.close();

		assertFalse(result);
		assertNull(testDA.getSaleBuyer(sale10001));
		assertEquals(100f, testDA.getUserMoney(BUYER), 0.001);
	}
}
