package milestone_xuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import milestone1.*;

public class milestone1_return_List {

	public class someThing {
		
		List<ShippingCost> ShippingCostList;
		ProductInventory inventory;
		public someThing(ProductInventory inventory, List<ShippingCost> scList) {
			super();
			this.inventory = inventory;
			this.scList = scList;
		}

		
	}

		public static  List<someThing> milestone1List(Order order) {
			
			// create a new List<someThing> which return a List<ProductInventory
			// List<ShippingCost>
			List<someThing> someThingList = new ArrayList<someThing>();
			
			// get a list of all Inventoris which has the product we want by
			// given product id
			List<ProductInventory> InventoryList = ProductInventoryExplorer.getProductInventory(order.getPid());
			
			// get a list of all shipping cost by given destination of given
			// product
			List<ShippingCost> scListByDes = ShippingCostExplorer.getShippingCost(order.getRegion());
			// create a hashMap to map key, value for List<ShippingCost>
			HashMap<Region, List<ShippingCost>> scMap = new HashMap<Region, List<ShippingCost>>();

			for (ShippingCost a : scListByDes) {

				// set the region equals shipFrom which is the region location
				// of Inventory
				Region fromRegion = a.getShipFrom();
				// iterate all ShippingCost objects
				if (!scMap.containsKey(fromRegion)) {
					// if the map dosent have the fromRegion value, so we add
					// the a to tmpScList and put it to map
					List<ShippingCost> tmpScList = new ArrayList<ShippingCost>();
					tmpScList.add(a);
					scMap.put(fromRegion, tmpScList);
				} else {
					// if the fromRegion is already there, means, to a same
					// location, we have different shipping plan
					scMap.get(fromRegion).add(a);

				}

			}
			// now, we need to put the scMap and List<ProductInventory> into
			// List<someThing>
			for (ProductInventory productInventory : InventoryList) {

				// we get ShippingList value by search key, since the key value
				// if fromRegion of a shippingCost
				// which is also should equal to
				// productInventory.getRegion().since every shipment should ship
				// from
				// a inventory location

				List<ShippingCost> listToSomeThing = scMap.get(productInventory.getRegion());

				// to check corner case to make sure the listToSomeThing is not
				// empty
				if (listToSomeThing != null && listToSomeThing.size() != 0) {

					// add Inventory, List<ShippingCost> to someThing class
					// a Inventory may have more than one shippingCost plan
					// e.g:Inventory A:{<planA>,<planB>,<planC>}
					someThingList.add(new someThing(productInventory, listToSomeThing));

				}

			}

			return someThingList;

		}

	}

