packeage businesslogic

import static org.mockito.Mockito.*;

public class PresentationTest {

    @Test
    public void testMainGUIWithMock() {
        // 1. Crear el mock de BLFacade usando Mockito
        BLFacade mockFacade = mock(BLFacade.class);
        
        // 2. Configurar el comportamiento del mock si es necesario
        // when(mockFacade.someMethod()).thenReturn(someValue);
        
        // 3. Pasar el mock directamente a la presentación o usar una factoría de pruebas
        MainGUI.setBusinessLogic(mockFacade);
        
        // La presentación funcionará aislada utilizando el objeto simulado (Mock)
        MainGUI gui = new MainGUI();
        assert gui != null;
    }
}
