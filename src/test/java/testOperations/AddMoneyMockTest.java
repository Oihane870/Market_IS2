package testOperations;

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
import domain.Mugimenduak;
import domain.Seller;

public class AddMoneyMockTest {

	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;

	@Mock
	protected EntityManager db;

	@Mock
	protected EntityTransaction et;

	@Mock
	protected Seller sellerMock;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();

		
		sut = new DataAccess(db);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	
	@Test
	public void test1_AddMoneySuccess() {
		String sellerMail = "sellerTest1@ehu.eus";
		float amount = 50.0f;

		
		Mockito.when(db.find(Seller.class, sellerMail)).thenReturn(sellerMock);

		try {
			sut.open();
			boolean result = sut.addMoney(sellerMail, amount);
			sut.close();

			
			assertTrue(result);

			
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(sellerMock, Mockito.times(1)).addMoney(amount);
			Mockito.verify(db, Mockito.times(1)).persist(Mockito.any(Mugimenduak.class));
			Mockito.verify(db, Mockito.times(1)).merge(sellerMock);
			Mockito.verify(et, Mockito.times(1)).commit();

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		}
	}

	
	@Test
	public void test2_SellerDoesNotExist() {
		String sellerMail = "nonexistent@ehu.eus";
		float amount = 50.0f;

		
		Mockito.when(db.find(Seller.class, sellerMail)).thenReturn(null);

		try {
			sut.open();
			boolean result = sut.addMoney(sellerMail, amount);
			sut.close();

			
			assertFalse(result);
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).rollback();
			Mockito.verify(et, Mockito.never()).commit();

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		}
	}

	
	@Test
	public void test3_InvalidAmountZeroOrNegative() {
		String sellerMail = "sellerTest1@ehu.eus";
		float invalidAmount = -10.0f;

	
		Mockito.when(db.find(Seller.class, sellerMail)).thenReturn(sellerMock);

		try {
			sut.open();
			boolean result = sut.addMoney(sellerMail, invalidAmount);
			sut.close();

			
			assertFalse(result);
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).rollback();
			Mockito.verify(sellerMock, Mockito.never()).addMoney(Mockito.anyFloat());
			Mockito.verify(et, Mockito.never()).commit();

		} catch (Exception e) {
			e.printStackTrace();
			fail("No debería haber lanzado ninguna excepción.");
		}
	}
}