package businesslogic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

import dataAccess.ExtendedIterator;
import domain.Admin;
import domain.Eskaera;
import domain.Salaketa;
import domain.Sale;
import domain.Seller;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

public class BLFacadeWebServiceClient implements BLFacade {

    // Aquí sueles tener la referencia al Web Service real (Service y Port)
    // private BLFacade delegate; 

    public BLFacadeWebServiceClient() {
        // Inicialización de la conexión con el servidor web remoto si procede
    }

    @Override
    public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
        // delegate.createSale(...)
        return null; 
    }

    @Override
    public List<Sale> getSales(String desc) {
        // delegate.getSales(desc);
        return null;
    }

    @Override
    public ExtendedIterator getSalesIterator(String desc) {
        // delegate.getSalesIterator(desc);
        return null;
    }

    @Override
    public List<Sale> getPublishedSales(String desc, Date pubDate) {
        return null;
    }

    @Override
    public BufferedImage getFile(String fileName) {
        return null;
    }

    @Override
    public void initializeBD() {
        // delegate.initializeBD();
    }

    @Override
    public Image downloadImage(String imageName) {
        return null;
    }

    @Override
    public Seller isLogged(String log, String pass) {
        return null;
    }

    @Override
    public Seller isRegister(String erabiltzaile, String helbidea, String pass, String confirmPass) {
        return null;
    }

    @Override
    public boolean buyProduct(String buyerEmail, Integer saleNumber) {
        return false;
    }

    @Override
    public List<Sale> getPurchasedItems(String email) {
        return null;
    }

    @Override
    public boolean addMoney(String email, float zenbat) {
        return false;
    }

    @Override
    public float getMoney(String email) {
        return 0;
    }

    @Override
    public List<Sale> getSoldItems(String email) {
        return null;
    }

    @Override
    public boolean reportSale(String userEmail, Integer saleNumber, String reason) {
        return false;
    }

    @Override
    public List<Sale> getReportedSales() {
        return null;
    }

    @Override
    public boolean resolveReport(Integer saleNumber, Salaketa sal, boolean aceptar) {
        return false;
    }

    @Override
    public boolean addErreklamazioa(Integer saleNumber, String reason, String buyerEmail) {
        return false;
    }

    @Override
    public List<Sale> getClaimedSales() {
        return null;
    }

    @Override
    public boolean resolveErreklamazioa(Integer saleNumber, boolean accept) {
        return false;
    }

    @Override
    public Admin isAdmin(String log, String pass) {
        return null;
    }

    @Override
    public boolean addToBasket(String buyerEmail, Integer saleNumber) {
        return false;
    }

    @Override
    public boolean removeFromBasket(String buyerEmail, Integer saleNumber) {
        return false;
    }

    @Override
    public List<Sale> getBasket(String buyerEmail) {
        return null;
    }

    @Override
    public boolean buyBasket(String buyerEmail) {
        return false;
    }

    @Override
    public boolean createEskaera(String buyerEmail, String title, String description) {
        return false;
    }

    @Override
    public boolean createEskaera(String buyerEmail, String title) {
        return false;
    }

    @Override
    public List<Eskaera> getOpenEskaerak() {
        return null;
    }

    @Override
    public boolean addEskaintza(Integer eskaeraId, String sellerEmail, float price, String message) {
        return false;
    }

    @Override
    public boolean acceptEskaintza(Integer eskaeraId, Integer eskaintzaId) {
        return false;
    }

    @Override
    public boolean confirmArrival(Integer saleNumber) {
        return false;
    }
}