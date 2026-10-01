

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class BuyBasketBDBlackTest {
	//sut:system under test
	static DataAccess sut=new DataAccess();
		 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
	
	@SuppressWarnings("unused")
	
	
	private static final String BUYER = "buyer1@gmail.com";
	private static final String BUYER2 = "buyer2@gmail.com";
    
	@Test
	public void test1() {
		
        testDA.createBuyerWithBasket(BUYER, 150f, 75f);
        
        
        try {
        	sut.open();
        	boolean result = sut.buyBasket(BUYER);
        	sut.close();
        	assertTrue(result);
        	
        }
        finally {
        	testDA.open();
            testDA.removeSeller(BUYER);
            testDA.removeSeller("seller1@gmail.com");
            testDA.close();
        }
	}
	@Test
	public void test2() {
		sut.open();
	    boolean result = sut.buyBasket(null);
	    sut.close();
	    assertFalse(result);
	}
	
	@Test
	public void test3() {
		
        try {
        	sut.open();
        	boolean result = sut.buyBasket("");
        	sut.close();
        	assertFalse(result);
        }
        finally {
        	testDA.open();
            testDA.close();
        }
	}
	
	
	
	
	@Test
    public void test4() {
		
        try {
            sut.open();
            boolean result = sut.buyBasket("buyer");
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller("buyer"); 
            testDA.close();
        }
    }
	

	@Test
    public void test5() {
		
        try {
            sut.open();
            boolean result = sut.buyBasket("@gmail.com");
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller("@gmail.com"); 
            testDA.close();
        }
    }
	
	
	@Test
    public void test6() {
        
        try {
            sut.open();
            boolean result = sut.buyBasket("buyer111@gmail.com");
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller("buyer111@gmail.com"); 
            testDA.close();
        }
    }
	@Test
    public void test7() {
        
        testDA.createBuyerWithNullBasket(BUYER2);
        
        try {
            sut.open();
            boolean result = sut.buyBasket(BUYER2);
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller(BUYER2); 
            testDA.close();
        }
    }
	
	@Test
    public void test8() {
        
        testDA.createBuyerWithEmptyBasket(BUYER2, 50f);
        
        try {
            sut.open();
            boolean result = sut.buyBasket(BUYER2);
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller(BUYER2); 
            testDA.close();
        }
    }
	
	@Test
    public void test9() {
        
        testDA.createBuyerWithMixedSellersBasket(BUYER2, 50f);
        
        try {
            sut.open();
            boolean result = sut.buyBasket(BUYER2);
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller(BUYER2); 
            testDA.removeSeller("seller1@gmail.com");
            testDA.removeSeller("seller2@gmail.com");
            testDA.close();
        }
    }
	
	@Test
    public void test10() {
        
        testDA.createBuyerWithBasket(BUYER2, 50f, 100f);
        
        try {
            sut.open();
            boolean result = sut.buyBasket(BUYER2);
            sut.close();

            assertFalse(result);
        } finally {
            testDA.open();
            testDA.removeSeller(BUYER2); 
            testDA.removeSeller("seller1@gmail.com");
            testDA.close();
        }
    }
	
	
	
	
		 
}
