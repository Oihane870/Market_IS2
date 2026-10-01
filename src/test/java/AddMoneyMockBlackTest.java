

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import domain.Seller;

public class AddMoneyMockBlackTest {

	static DataAccess sut;

	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	private Seller seller;
	private String sellerMail;
	private String sellerName;
	private float zenbat;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);

		sellerMail = "oiher@gmail.com";
		sellerName = "Oiher Test";
		seller = new Seller(sellerMail, sellerName, sellerName);
		zenbat = 20.0f;

		Mockito.when(db.find(Seller.class, seller.getEmail())).thenReturn(seller);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@Test
	// sut.addMoney: The seller ("oiher@gmail.com") exists in DB and zenbat > 0
	public void test1() {
		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertTrue(res);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.addMoney: The sellerMail parameter is null
	public void test2() {
		sellerMail = null;
		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.addMoney: The seller is not in DB
	public void test3() {
		sellerMail = "nonexistent@gmail.com";
		Mockito.when(db.find(Seller.class, sellerMail)).thenReturn(null);

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.addMoney: The sellerMail format is invalid
	public void test4() {
		sellerMail = "oiher.gmailcom";
		Mockito.when(db.find(Seller.class, sellerMail)).thenReturn(null);

		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.addMoney: zenbat <= 0 (zenbat = -20.0f)
	public void test5() {
		zenbat = -20.0f;
		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.addMoney: zenbat == 0
	public void test6() {
		zenbat = 0.0f;
		try {
			sut.open();
			boolean res = sut.addMoney(sellerMail, zenbat);
			sut.close();

			assertFalse(res);

		} catch (Exception e) {
			fail();
		}
	}
}