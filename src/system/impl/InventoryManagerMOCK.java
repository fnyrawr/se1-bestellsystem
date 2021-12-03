package system.impl;

import datamodel.Order;
import system.InventoryManager;

/**
 * Singleton component that manages Inventories in the InventoryManager.
 *
 * @author fkate
 * @since 0.1.0
 * @version 0.1.0
 *
 */

public class InventoryManagerMOCK implements InventoryManager {
    public InventoryManager getInventoryManager() {
        return this;
    }

    /**
     * Order is fillable when all ordered items meet the condition:
     * {@code orderItem.unitsOrdered <= inventory(article).unitsInStore}.
     *
     * @param order to validate.
     * @return true if order is fillable from current inventory.
     */
    public boolean isFillable( Order order ) {
        return true;
    }
}
