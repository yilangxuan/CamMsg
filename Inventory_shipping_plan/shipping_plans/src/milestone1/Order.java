package milestone1;


public class Order {
	private final int pid;
	private final int quantity;
	private final int expectedDays;
	private final Region region;
	private final double date;
	
	public Order(int pid, int quantity, int expected_days, Region region, double date) {
		this.pid = pid;
		this.quantity = quantity;
		this.expectedDays = expected_days;
		this.region = region;
		this.date = date;
	}
	
	public Order (Order other) {
		this.pid = other.getPid();
		this.quantity = other.getQuantity();
		this.expectedDays = other.getExpectedDays();
		this.region = other.getRegion();
		this.date = other.getDate();
	} 
	
	public int getPid() {
		return pid;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getExpectedDays() {
		return expectedDays;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public double getDate() {
		return date;
	}
	
}
