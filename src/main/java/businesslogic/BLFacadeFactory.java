package businesslogic;

import configuration.ConfigXML;

public class BLFacadeFactory {

    public BLFacade createBLFacade() {
        ConfigXML config = ConfigXML.getInstance();
        
        if (config.isBusinessLogicLocal()) {
            // Retorna la implementación local de la lógica de negocio
            return new BLFacadeImplementation();
        } else {
            // Retorna la implementación basada en servicios web
            return new BLFacadeWebServiceClient();
        }
    }
}