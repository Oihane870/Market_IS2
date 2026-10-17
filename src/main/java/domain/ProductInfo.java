package domain;

public class ProductInfo {

	private String title;
	private String description;
	private int status;
	private float price;

	public ProductInfo(String title, String description, int status, float price) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getStatus() {
		return status;
	}

	public float getPrice() {
		return price;
	}
}
