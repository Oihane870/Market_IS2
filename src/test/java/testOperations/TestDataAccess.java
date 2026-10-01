package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Sale;
import domain.Seller;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeSeller(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Seller d = db.find(Seller.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Seller createSeller(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Seller seller=null;
			db.getTransaction().begin();
			try {
			    seller=new Seller(email,name, null);
				db.persist(seller);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return seller;
    }
	public boolean existSeller(String email) {
		return db.find(Seller.class, email) != null;
	}

		
	public void createBuyerWithNullBasket(String email) {
		open();
		removeSeller(email);
		close();
	    open();
	    db.getTransaction().begin();
	    Seller buyer = new Seller(email, null, null);
	    buyer.setBasket(null);
	    db.persist(buyer);
	    db.getTransaction().commit();
	    close();
	}
	
	public void createBuyerWithEmptyBasket(String email, float money) {
		open();
		removeSeller(email);
		close();
	    open();
	    db.getTransaction().begin();
	    Seller buyer = new Seller(email, null, null);
	    buyer.setMoney(money);
	    db.persist(buyer);
	    db.getTransaction().commit();
	    close();
	}
	
	
	public void createBuyerWithMixedSellersBasket(String buyerEmail, float money) {
		open();
		removeSeller(buyerEmail);
	    removeSeller("seller1@gmail.com");
	    removeSeller("seller2@gmail.com");
	    close();
	    open();
	    db.getTransaction().begin();

	    Seller buyer = new Seller(buyerEmail, null, null);
	    buyer.setMoney(money);

	    Seller seller1 = new Seller("seller1@gmail.com", "Antonio", null);
	    Seller seller2 = new Seller("seller2@gmail.com", "Patricia", null);

	    Sale sale1 = seller1.addSale("prod1", "desc1", 0, 20f, new Date(), null);
	    Sale sale2 = seller2.addSale("prod2", "desc2", 0, 30f, new Date(), null);

	    buyer.getBasket().add(sale1);
	    buyer.getBasket().add(sale2);

	    db.persist(seller1);
	    db.persist(seller2);
	    db.persist(buyer);

	    db.getTransaction().commit();
	    close();
	}
	
	
	public void createBuyerWithBasket(String buyerEmail, float money, float... prices) {
		
		 open();
		 removeSeller(buyerEmail);
		 removeSeller("seller1@gmail.com");
		 close();
		
		open();
	    db.getTransaction().begin();

	    Seller buyer = new Seller(buyerEmail, null, null);
	    buyer.setMoney(money);

	    Seller seller1 = new Seller("seller1@gmail.com", "Antonio", null);
	    for (int i = 0; i < prices.length; i++) {
	        Sale sale = seller1.addSale("prod" + i, "desc" + i, 0, prices[i], new Date(), null);
	        buyer.getBasket().add(sale);
	    }

	    db.persist(seller1);
	    db.persist(buyer);
	    db.getTransaction().commit();
	    close();
	}

	
		
		

		
}