package milestone_xuan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import milestone1.Order;
import milestone1.ProductInventory;
import milestone1.Region;
import milestone1.ShippingCost;
import milestone_xuan.milestone1_return_List.someThing;

public class milestone2 {
	public void mileston2(List<Order> orderList) {
		// sort List<Order> first so we can fulfill most of orders
		Collections.sort(orderList, new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				// TODO Auto-generated method stub
				return o1.getQuantity() - o2.getQuantity();
			}
		});

		for (Order order : orderList) {

			// for each order, we have a someThing list from milestone1;
			List<someThing> milestone1List = milestone1_return_List
					.milestone1List(order);

			// create a shippmengBuilder so we can call its inside functions;
			ShippmentBuilder shipBuilder = ShippmentBuilder
					.createNewShippmengBuilder(order);
			// create a function which we can implement different functions

			MostOrderLessDayFunc(milestone1List, shipBuilder, order);

		}

	}

	// different sort ideas:1:first ShippingCost by cost first, then sort
	// someThing by days
	// you have a List<someThing> which has fast shipping days but reasonably
	// low cost(not guaranteed);
	// 2. sort days first,cost second=less day shipping(not guaranteed)
	// 3 sort days in ShippingCostList, sort days in List<someThing>=fastest
	// shipping(guaranteed)
	// 4 sort day in ShippingCostList, sort cost in List<someThing>=cheapest
	// shipping(guaranteed)

	private void MostOrderLessDayFunc(List<someThing> milestone1List,
			ShippmentBuilder shipBuilder, Order order) {

		ShippmentBuilder goShip = shipBuilder;

		Comparator<ShippingCost> sortByDays = new Comparator<ShippingCost>() {

			public int compare(ShippingCost o1, ShippingCost o2) {
				return o1.getDays() - o2.getDays();
			}

		};
		Queue<ShippingCost> scQueue = new PriorityQueue<ShippingCost>(
				sortByDays);

		// get a shippingCost list which cost less day to delivery
		List<someThing> fastSomething = milestone1List;

		for (int i = 0; i < fastSomething.size(); i++) {
			// we get the shippingCost list for i inventory
			List<ShippingCost> scFastList = fastSomething.get(i).ShippingCostList;

			// create a Comparator to sort by days.
			// Collections.sort(scFastList, new Comparator<ShippingCost>() {
			//
			// @Override
			// public int compare(ShippingCost o1, ShippingCost o2) {
			//
			// return o1.getDays() - o2.getDays();
			// }
			//
			// });
			Queue<ShippingCost> tmpQueue = new PriorityQueue<ShippingCost>(
					sortByDays);

			tmpQueue.addAll(scFastList);

			ShippingCost tmpList = scFastList.get(0);
			//you only get one ShippingCost since first one is most less cost days.
			scQueue.add(tmpList);
//			scQueue.add(tmpQueue.poll());

		}

		// create a hashmap to store scQueue as value and its region as key
		HashMap<Region, List<ShippingCost>> scQueueMap = new HashMap<Region, List<ShippingCost>>();

		while (!scQueue.isEmpty()) {
			ShippingCost popList = scQueue.poll();
			Region fromRegion = popList.getShipFrom();
			//if we using PriorityQueue above, for every inventory, we will only get first ShippingCost which is most less days
			//so, its 1 to 1 relation, we can just put it inventory with its shippingCost to map
			List<ShippingCost> addToMapList = new ArrayList<ShippingCost>();
			addToMapList.add(popList);
			
			scQueueMap.put(fromRegion, addToMapList);
			
			
//			if (!scQueueMap.containsKey(fromRegion)) {
//				List<ShippingCost> tempList = new ArrayList<ShippingCost>();
//				tempList.add(popList);
//				scQueueMap.put(fromRegion, tempList);
//
//			} else {
//				scQueueMap.get(fromRegion).add(popList);
//			}
			// now we have a HashMap which with sorted
			// List<ShippingCost>,key:region, value:List<ShippingCost>
			// close to we did in milestone1
		}

		
		// after sort shippingCost we get a List which for every class in
		// List<someThing>
		// so,we can get fastest shipingPlan which also fulfill most of
		// orders;
		// Collections.sort(fastSomething, new Comparator<someThing>() {
		//
		// @Override
		// public int compare(someThing o1, someThing o2) {
		//
		// return o1.scList.get(0).getDays() - o1.scList.get(0).getDays();
		// }
		//
		// });

		// O(n+m)
		// after we get a sorted fastSomething list by days.we need to call
		// these ship APIs 
		
		for (int i = 0; i < fastSomething.size(); i++) {
			// set the inventory we read to ship products
			ProductInventory goIventory = fastSomething.get(i).inventory;
			Region regionInMap = goIventory.getRegion();
			List<ShippingCost> goCostList = scQueueMap.get(regionInMap);

			int invenQuantity = goIventory.getQuantity();

			if (invenQuantity < goShip.getRemainingUnfilfilledQuantity()) {

				goShip.transferToShipment(goIventory, goCostList.get(0)
						.getCostPerItem(), goIventory.getQuantity());

			}

			// if we do not have more products inventory than remainings
			else {
				goShip.transferToShipment(goIventory, goCostList.get(0)
						.getCostPerItem(), goShip
						.getRemainingUnfilfilledQuantity());
				goShip.ship();
			}

		}
		// after iterate all inventories, still have remaining left, unable to
		// ship
		if (goShip.getRemainingUnfilfilledQuantity() > 0) {
			goShip.unableToShip();
			goShip.cancel();

		}

	}
}
