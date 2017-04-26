package milestone1;

import java.util.*;

public class ProductInventoryExplorer {

	public static List<ProductInventory> getProductInventory(int pid) {
		Input input = new Input();
		List<ProductInventory> piList = new LinkedList<>();
		for (ProductInventory pi: input.pis) {
			if (pi.getPid() == pid) {
				piList.add(pi);
			}
		}
		return piList;
	}
}
