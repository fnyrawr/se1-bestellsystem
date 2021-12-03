package system;

import datamodel.Order;

public interface InventoryManager {
    public InventoryManager getInventoryManager();

    /**
     * Order is fillable when all ordered items meet the condition:
     * {@code orderItem.unitsOrdered <= inventory(article).unitsInStore}.
     *
     * @param order to validate.
     * @return true if order is fillable from current inventory.
     */
    public boolean isFillable( Order order );
}
