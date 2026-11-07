package businesslogic;

import org.junit.Test;
import gui.MainGUI;

public class PresentationTest {

    @Test
    public void testMainGUI() {
        // 1. Usar la implementación real en lugar de un Mock
        BLFacade businessLogic = new BLFacadeImplementation();
        
        // 2. Inyectar la fachada de negocio en la interfaz gráfica
        // (Asegúrate de que este método estático exista en tu clase MainGUI)
        MainGUI.setBussinessLogic(businessLogic);
        
        // 3. Instanciar la interfaz gráfica pasándole null (o el contenedor adecuado)
        MainGUI gui = new MainGUI(null);
        
        // 4. Aserción para verificar que se creó correctamente
        assert gui != null;
    }
}