package businesslogic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.ExtendedIterator;
import domain.Admin;
import domain.Eskaera;
import domain.Salaketa;
import domain.Sale;
import domain.Seller;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

@WebService
public interface BLFacade {

    @WebMethod
    public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException;

    @WebMethod
    public List<Sale> getSales(String desc);

    @WebMethod
    public ExtendedIterator getSalesIterator(String desc);

    @WebMethod
    public List<Sale> getPublishedSales(String desc, Date pubDate);

    @WebMethod
    public BufferedImage getFile(String fileName);

    @WebMethod
    public void initializeBD();

    @WebMethod
    public Image downloadImage(String imageName);

    @WebMethod
    public Seller isLogged(String log, String pass);

    @WebMethod
    public Seller isRegister(String erabiltzaile, String helbidea, String pass, String confirmPass);

    @WebMethod
    public boolean buyProduct(String buyerEmail, Integer saleNumber);

    @WebMethod
    public List<Sale> getPurchasedItems(String email);

    @WebMethod
    public boolean addMoney(String email, float zenbat);

    @WebMethod
    public float getMoney(String email);

    @WebMethod
    public List<Sale> getSoldItems(String email);

    @WebMethod
    public boolean reportSale(String userEmail, Integer saleNumber, String reason);

    @WebMethod
    public List<Sale> getReportedSales();

    @WebMethod
    public boolean resolveReport(Integer saleNumber, Salaketa sal, boolean aceptar);

    @WebMethod
    public boolean addErreklamazioa(Integer saleNumber, String reason, String buyerEmail);

    @WebMethod
    public List<Sale> getClaimedSales();

    @WebMethod
    public boolean resolveErreklamazioa(Integer saleNumber, boolean accept);

    @WebMethod
    public Admin isAdmin(String log, String pass);

    @WebMethod
    public boolean addToBasket(String buyerEmail, Integer saleNumber);

    @WebMethod
    public boolean removeFromBasket(String buyerEmail, Integer saleNumber);

    @WebMethod
    public List<Sale> getBasket(String buyerEmail);

    @WebMethod
    public boolean buyBasket(String buyerEmail);

    @WebMethod
    public boolean createEskaera(String buyerEmail, String title, String description);

    @WebMethod
    public boolean createEskaera(String buyerEmail, String title);

    @WebMethod
    public List<Eskaera> getOpenEskaerak();

    @WebMethod
    public boolean addEskaintza(Integer eskaeraId, String sellerEmail, float price, String message);

    @WebMethod
    public boolean acceptEskaintza(Integer eskaeraId, Integer eskaintzaId);

    @WebMethod
    public boolean confirmArrival(Integer saleNumber);

}
