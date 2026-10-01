

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class AddMoneyBDBlackTest {

	// sut: system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private String sellerMail;
	private String sellerName;
	private float zenbat;

	@Before
	public void defaultValues() {
		sellerMail = "oiher@gmail.com";
		sellerName = "Oiher Test";
		zenbat = 20.0f;
	}

	@Test
	
	public void test1_AddMoneySuccess() {
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
			fail("No debería lanzar excepción.");
		} finally {
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
		}
	}

	@Test
	
	public void test2_EmailNull() {
		sellerMail = null;

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería lanzar excepción, debe retornar false.");
		}
	}

	@Test
	
	public void test3_SellerNotInDB() {
		sellerMail = "nonexistent@gmail.com";

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería lanzar excepción.");
		}
	}

	@Test
	
	public void test4_InvalidEmailFormat() {
		sellerMail = "oiher.gmailcom"; // Formato de email erróneo

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería lanzar excepción.");
		}
	}

	@Test
	
	public void test5_ZenbatNegativeOrZero() {
		zenbat = -20.0f;

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
			fail("No debería lanzar excepción.");
		} finally {
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
		}
	}

	@Test
	
	public void test6_ZenbatZero() {
		zenbat = 0.0f;

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
			fail("No debería lanzar excepción.");
		} finally {
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
		}
	}
}