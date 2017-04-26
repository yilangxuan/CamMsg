package milestone1;
import java.util.*;

public class ShippingCostExplorer {

	
	public static List<ShippingCost> getShippingCost(Region region) {
		Input input = new Input();
		List<ShippingCost> listOfShippingCost = new LinkedList<>();
		for (ShippingCost sc: input.scs) {
			if (sc.getShipTo() == region) {
				listOfShippingCost.add(sc);
			}
		}
		return listOfShippingCost;
	}
}
