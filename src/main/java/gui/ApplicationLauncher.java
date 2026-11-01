package gui;

import java.awt.Color;
import java.util.Locale;

import javax.swing.UIManager;

import configuration.ConfigXML;
import businesslogic.BLFacade;
import businesslogic.BLFacadeFactory; // Importamos la factoría
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationLauncher { 
    private static final Logger LOGGER = Logger.getLogger(ApplicationLauncher.class.getName());
    
    public static void main(String[] args) {

        ConfigXML c = ConfigXML.getInstance();      
        Locale.setDefault(new Locale(c.getLocale()));
        
        MainGUI a = new MainGUI("seller3@gmail.com");
        a.setVisible(true);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            // Centralizamos la creación utilizando el objeto factoría
            BLFacadeFactory factory = new BLFacadeFactory();
            BLFacade appFacadeInterface = factory.createBLFacade();
            
            MainGUI.setBussinessLogic(appFacadeInterface);
            
        } catch (Exception e) {
            a.jLabelSelectOption.setText("Error: "+e.toString());
            a.jLabelSelectOption.setForeground(Color.RED);   
            
            LOGGER.log(Level.SEVERE, "Error in ApplicationLauncher", e);
        }
    }
}
