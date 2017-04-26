package milestone1;

import java.util.*;


/*
 *  The class Picosts is created for storing different Shipping cost of ProductInventory 
 */
class Picosts {
	public final ProductInventory pi;
	public final List<ShippingCost> scList;
	public Picosts (ProductInventory pi, List<ShippingCost> scList) {
		this.pi = pi;
		this.scList = scList;
	}
	public ProductInventory getPI() {
		return this.pi;
	}
	public List<ShippingCost> getSCList() {
		return this.scList;
	}
}

/* The class ShippingPlan is created for storing the order and different Picosts.
 * The costs stores the all the ProductInventory having same pid as order.pid 
 */
class ShippingPlan {
	private final Order order;
	private final List<Picosts>  costs;
	ShippingPlan(Order order) {
		this.order = order;
		this.costs = new LinkedList<>();
	} 
	public List<Picosts> getCosts() {
		return this.costs;
	}
	public Order getOrder() {
		return this.order;
	}
	
}

/* 
 * The class InventoryMinDays stores the ProductInventory and its minimum days to 
 * deliver products to the region of the order 
 */
class InventoryMinDays {
	private final ProductInventory pi; 
	private final int minDays;
	public InventoryMinDays (ProductInventory pi, int minDays) {
		this.pi = pi;
		this.minDays = minDays;
	}
	public ProductInventory getProdcutInventory () {
		return this.pi;
	} 
	public int getMinDays () {
		return this.minDays;
	}
}


class OrderComparator implements Comparator<Order> {
	@Override 
	public int compare(Order o1, Order o2) {
		return o1.getQuantity() - o2.getQuantity();
	}
}



public class Main {
	
	public void myprint (ShippingPlan src) {
		System.out.println("Order: " + src.getOrder().getPid() + ", ToRegion = " + src.getOrder().getRegion());
		for (Picosts pit: src.getCosts()) {
			if (pit.pi.getRegion() == Region.north) {
				System.out.println("\tRegion: North");
			} else {
				System.out.println("\tRegion: Unknown");
			}
			for (ShippingCost scit: pit.scList) {
				System.out.println("\t\tCost: days = " + scit.getDays() + ", cost = " +scit.getCostPerItem());
			}
		}
	}
	
	public void myprint (ProductInventory pi, int num, int time) {
		System.out.println(pi.getPid() + "shipped: " + num + " remains: " + pi.getQuantity());
		System.out.println("Used time " + time);
	}
	
	
	
	public ShippingPlan solution1(Order order) {
		ShippingPlan sp = new ShippingPlan(order);
		Region toRegion = order.getRegion();
		List<ShippingCost> listOfShippingCosts = ShippingCostExplorer.getShippingCost(toRegion);
		Map<Region, List<ShippingCost>> mapRegionToShippingCosts = new HashMap<>();
		
		for (ShippingCost sc: listOfShippingCosts) {
			Region fromRegion = sc.getShipFrom();
			if (!mapRegionToShippingCosts.containsKey(fromRegion)) {
				List<ShippingCost> scList = new LinkedList<>();
				scList.add(sc);
				mapRegionToShippingCosts.put(fromRegion, scList);
			} else {
				mapRegionToShippingCosts.get(fromRegion).add(sc);
			}
		}
		
		List<ProductInventory> piList = ProductInventoryExplorer.getProductInventory(order.getPid());
		for (ProductInventory pi: piList) {
			List<ShippingCost> scList = mapRegionToShippingCosts.get(pi.getRegion());
			Picosts pic = new Picosts(pi, scList);
			if (scList != null && scList.size() != 0) {
				sp.getCosts().add(pic);
			}
		}
		return sp;
	}
	
	
	
//	public ShippingPlan solution1 (Order order) {
//		// initialize an object of ShippingPlan
//		ShippingPlan sp = new ShippingPlan(order);
//		
//		// get 'pid' of order
//		int pid = order.getPid();
//		
//		// get 'regionTo' of order
//		Region regionTo = order.getRegion();
//		
//		// get list of 'ShippingCost', which has 'regionTo' equal to 'order.regionTo' 
//		List<ShippingCost> listOfShippingCost = ShippingCostExplorer.getShippingCost(regionTo);
//		
//		// the map from 'fromRegion' to List of 'ShippingCost', all the shippinCost.toRegion equal to order.regionTo
//		Map<Region, List<ShippingCost>> fromRegionToShippingCost = new HashMap<>();
//		
//		// the for loop is aimed to build the 'fromRegionToShippingCost'
//		for (ShippingCost sc: listOfShippingCost) {  
//			Region shipFrom = sc.getShipFrom();
//			if (!fromRegionToShippingCost.containsKey(shipFrom)) {
//				List<ShippingCost> scList = new LinkedList<>();
//				scList.add(sc);
//				fromRegionToShippingCost.put(shipFrom, scList);
//			} else {
//				fromRegionToShippingCost.get(shipFrom).add(sc);
//			}
//		}
//		
//		// get list of 'ProductInventory' with 'pid'
//		List<ProductInventory> listOfProductInventory = ProductInventoryExplorer.getProductInventory(pid);
//		
//		for (ProductInventory pi: listOfProductInventory) {
//			List<ShippingCost> scList = fromRegionToShippingCost.get(pi.getRegion());
//			Picosts pic = new Picosts(pi, scList);
//			if (scList != null && scList.size() != 0) {
//				sp.getCosts().add(pic);
//			}
//		}
//		
//		return sp;
//	}
	
	public void solution2 (List<Order> orders) {
		
		// sort orders by quantity
		Collections.sort(orders, new Comparator<Order>(){
			@Override
			public int compare (Order o1, Order o2) {
				return o1.getQuantity() - o2.getQuantity();
			}
		});
		
		for (Order order: orders) {
			PriorityQueue<ProductInventory> pq = new PriorityQueue<>();
			ShippingPlan plan = solution1(order);
			if (plan.getCosts() == null || plan.getCosts().size() == 0) {
				continue;
			}
			int sumOfProduct = 0;
			int expectedDays = order.getExpectedDays();
			for (Picosts pics: plan.getCosts()) {
				List<ShippingCost> scList = pics.getSCList();
				if (scList == null || scList.size() == 0) {
					continue;
				}
				int minCost = Integer.MAX_VALUE;
				int minDays = Integer.MAX_VALUE;
				boolean inTime = false;
				for (ShippingCost sc: scList) {
					if (sc.getDays() < expectedDays) {
						inTime = true;
						minCost = Math.min(minDays, sc.getDays());
					}
				}
				
				
			}
		}
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("Hello, World");
		Input input = new Input();
		Main m = new Main(); 
		for (Order order: input.orders) {
			m.myprint(m.solution1(order));
		}
		
	}
	
}
