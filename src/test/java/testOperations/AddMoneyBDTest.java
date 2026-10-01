package testOperations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import dataAccess.DataAccess;

public class AddMoneyBDTest {

    static DataAccess sut = new DataAccess();
    static TestDataAccess testOp = new TestDataAccess();

    /**
     * CP1: Añadir dinero a un vendedor existente con una cantidad válida (> 0).
     * Debe devolver true y actualizar el saldo.
     */
    @Test
    public void test1_AddMoneySuccess() {
        String sellerMail = "sellerTestMoney@ehu.eus";
        String sellerName = "Seller Test Money";
        float amountToAdd = 50.0f;

        // 1. Configurar la base de datos (crear vendedor de prueba)
        testOp.open();
        testOp.createSeller(sellerMail, sellerName); // Asumiendo que tienes un método helper addSeller
        testOp.close();

        try {
            // 2. Ejcutar el método a probar (SUT)
            sut.open();
            boolean result = sut.addMoney(sellerMail, amountToAdd);
            sut.close();

            // 3. Verificar el resultado
            assertTrue(result);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Se produjo una excepción no esperada: " + e.getMessage());
        } finally {
            // 4. Limpiar los datos de prueba
            testOp.open();
            testOp.removeSeller(sellerMail);
            testOp.close();
        }
    }

    /**
     * CP2: Intentar añadir dinero a un vendedor que NO existe en la base de datos.
     * Debe devolver false sin lanzar excepción.
     */
    @Test
    public void test2_SellerDoesNotExist() {
        String nonExistingMail = "nonexistent@ehu.eus";
        float amountToAdd = 20.0f;

        try {
            sut.open();
            boolean result = sut.addMoney(nonExistingMail, amountToAdd);
            sut.close();

            // Debe devolver false porque el usuario no existe
            assertFalse(result);

        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar una excepción, sino devolver false.");
        }
    }

    /**
     * CP3: Intentar añadir una cantidad inválida (menor o igual a cero).
     * Debe devolver false.
     */
    @Test
    public void test3_InvalidAmountZeroOrNegative() {
        String sellerMail = "sellerTestMoney2@ehu.eus";
        String sellerName = "Seller Test Money 2";
        float invalidAmount = -10.0f; // o 0.0f

        testOp.open();
        testOp.createSeller(sellerMail, sellerName);
        testOp.close();

        try {
            sut.open();
            boolean result = sut.addMoney(sellerMail, invalidAmount);
            sut.close();

            // Debe devolver false porque la cantidad no es mayor a 0
            assertFalse(result);

        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción al pasar una cantidad negativa.");
        } finally {
            testOp.open();
            testOp.removeSeller(sellerMail);
            testOp.close();
        }
    }
}