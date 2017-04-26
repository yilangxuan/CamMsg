package milestone_xuan;

import milestone1.*;

public class ShippmentBuilder {

	class ShippingCost {
		Region fromRegion;
		Region toRegion;
		Method method;
		int cost;
		int days;

	}
	//these are given APIs.
	 
		public static ShippmentBuilder createNewShippmengBuilder(Order order){
			return null;
		}
		//transfer inventory to shipmentBuilder, the quantity of inventories will change
		public void transferToShipment(ProductInventory inventory, int i, int j){
			
		}
		
		//ship this order
		public void ship(){
			
		}
		//not to ship this order
		public void unableToShip(){
			
		}
		//cancel this order, everything in shipment will return to inventory
		public void cancel(){
			
		}
		/*e.g:we have a order, quantity=4
		run createNewShipmentBuilder(order). now, call 
		getRemainUnfulfilledQuantity, return value=4 since we havent do anything to shipment yet
		run transferToShipment(inventory,cost,3)
		now, we call getRemainUnfulfilledQuantity, return value=1 */
		public int getRemainingUnfilfilledQuantity(){
			return 0;
			
		}
		
	}
	

