package milestone1;

import java.util.*;

public class Input {
	public final List<Order> orders;
	public final List<ProductInventory> pis;
	public final List<ShippingCost> scs;
	
	public Input(){
		orders = new LinkedList<Order>();
		orders.add(new Order(1, 6, 4, Region.center, 0.3));
		orders.add(new Order(1, 3, 2, Region.west, 0.0));
		orders.add(new Order(1, 4, 0, Region.east, 0.2));
		orders.add(new Order(3, 100, 0, Region.center, 0.1));
		orders.add(new Order(2, 6, 4, Region.center, 0.3));
		pis = new LinkedList<>();
	    pis.add(new ProductInventory (1, 7, Region.north));
	    pis.add(new ProductInventory (3, 70, Region.north));
	    pis.add(new ProductInventory (3, 20, Region.north));
	    pis.add(new ProductInventory (3, 40, Region.east));
	    pis.add(new ProductInventory (3, 30, Region.north));
	    scs = new LinkedList<>();
	    scs.add(new ShippingCost(Region.north, Region.west, Method.express, 3, 10));
	    scs.add(new ShippingCost(Region.north, Region.west, Method.ground, 1, 15));
	    scs.add(new ShippingCost(Region.north, Region.east, Method.ground, 2, 20));
	    scs.add(new ShippingCost(Region.north, Region.center, Method.express, 2, 5));
	}	
	
}
