package milestone1;

public class ProductInventory {
	private final int pid;
	private final int quantity;
	private final Region region;
	
	public ProductInventory (int pid, int quantity, Region region) {
		this.pid = pid;
		this.quantity = quantity;
		this.region = region;
	}
	
	public ProductInventory (ProductInventory other) {
		this.pid = other.getPid();
		this.quantity = other.getQuantity();
		this.region = other.getRegion();
	}
	
	public int getPid() {
		return this.pid;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public Region getRegion() {
		return this.region;
	}
	
	
}
