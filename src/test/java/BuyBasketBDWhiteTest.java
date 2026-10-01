


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;

import testOperations.TestDataAccess;

public class BuyBasketBDWhiteTest {
	
	
 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	
	
	private static final String BUYER = "buyer1@gmail.com";
    private static final String BUYER_FAKE = "buyerFake@gmail.com";
    private static final String SELLER1 = "seller1@gmail.com";
    private static final String SELLER2 = "seller2@gmail.com";

    @Before
    public void setUp() {
        cleanAll();
    }

    @After
    public void tearDown() {
        cleanAll();
    }
    private void cleanAll() {
        testDA.open();
        testDA.removeSeller(BUYER);
        testDA.removeSeller(BUYER_FAKE);
        testDA.removeSeller(SELLER1);
        testDA.removeSeller(SELLER2);
        testDA.close();
    }
	
	
	
	@Test
    public void test3() {
        sut.open();
        boolean result = sut.buyBasket(BUYER_FAKE);
        sut.close();

        assertFalse(result);
    }
	
	@Test
	public void test4() {
		testDA.createBuyerWithNullBasket(BUYER);

        sut.open();
        boolean result = sut.buyBasket(BUYER);
        sut.close();

        assertFalse(result);
		
	}
	
	@Test
	public void test5() {
		testDA.createBuyerWithEmptyBasket(BUYER, 100f);

        sut.open();
        boolean result = sut.buyBasket(BUYER);
        sut.close();

        assertFalse(result);
	}
	
	@Test
	public void test6() {
		testDA.createBuyerWithMixedSellersBasket(BUYER, 100f);

        sut.open();
        boolean result = sut.buyBasket(BUYER);
        sut.close();

        assertFalse(result);
	}
	
	

	@Test
	public void test7() {
		testDA.createBuyerWithBasket(BUYER, 70f, 75f);

        sut.open();
        boolean result = sut.buyBasket(BUYER);
        sut.close();

        assertFalse(result);
	}
	
	@Test
	public void test8() {
		testDA.createBuyerWithBasket(BUYER, 150f, 75f, 15f);

        sut.open();
        boolean result = sut.buyBasket(BUYER);
        sut.close();

        assertTrue(result);
	}
	
	
	
	@Test
	public void test9() {
		testDA.createBuyerWithBasket(BUYER, 150f, 75f);

	    sut.open();
	    boolean result = sut.buyBasket(BUYER);
	    sut.close();

	    assertTrue(result);
	}
	
	
	
	
}
