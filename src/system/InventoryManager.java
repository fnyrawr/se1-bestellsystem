package system;

import datamodel.Order;

/**
 * Singleton component that manages Inventories in the InventoryManager.
 *
 * @author fkate
 * @since 0.1.1
 * @version 0.1.1
 *
 */

public interface InventoryManager {
    /**
     * Order is fillable when all ordered items meet the condition:
     * {@code orderItem.unitsOrdered <= inventory(article).unitsInStore}.
     *
     * @param order to validate.
     * @return true if order is fillable from current inventory.
     */
    public boolean isFillable( Order order );
}
