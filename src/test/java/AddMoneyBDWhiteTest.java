



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class AddMoneyBDWhiteTest {


	static DataAccess sut = new DataAccess();

	
	static TestDataAccess testDA = new TestDataAccess();

	private String sellerMail;
	private String sellerName;

	@Before
	public void defaultValues() {
		sellerMail = "oiher@gmail.com";
		sellerName = "Oiher Test";
	}

	
	@Test
	public void testPath1_SellerNull() {
		float zenbat = 20.0f;

		
		testDA.open();
		if (testDA.existSeller(sellerMail)) {
			testDA.removeSeller(sellerMail);
		}
		testDA.close();

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			
			assertFalse(res);

			
			testDA.open();
			boolean exist = testDA.existSeller(sellerMail);
			assertFalse(exist);
			testDA.close();

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		}
	}

	
	@Test
	public void testPath2_SellerExists_InvalidAmount() {
		float zenbat = -20.0f;

		
		testDA.open();
		testDA.createSeller(sellerMail, sellerName);
		testDA.close();

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			
			assertFalse(res);

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		} finally {
			
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
		}
	}

	
	@Test
	public void testPath3_SellerExists_ValidAmount_Success() {
		float zenbat = 20.0f;

		
		testDA.open();
		testDA.createSeller(sellerMail, sellerName);
		testDA.close();

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			
			assertTrue(res);

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		} finally {
			
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
		}
	}
}