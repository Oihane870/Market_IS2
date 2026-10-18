package dataAccess;

import java.io.File;
import java.util.Date;

public class SaleCreatingData {
	public String title;
	public String description;
	public int status;
	public float price;
	public Date pubDate;
	public File file;

	public SaleCreatingData(String title, String description, int status, float price, Date pubDate, File file) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.price = price;
		this.pubDate = pubDate;
		this.file = file;
	}
}