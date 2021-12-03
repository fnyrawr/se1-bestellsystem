package system.impl;

import datamodel.Order;
import system.InventoryManager;
import system.RTE;

/**
 * Singleton component that manages Inventories in the InventoryManager.
 *
 * @author fkate
 * @since 0.1.1
 * @version 0.1.1
 *
 */

class InventoryManagerMOCK implements InventoryManager {
    /**
     * static singleton reference to OrderBuilderImpl instance (singleton pattern).
     */
    private static InventoryManagerMOCK inventoryManager_instance = null;

    /**
     * Provide access to RTE InventoryManagerMOCK singleton instance (singleton pattern).
     *
     * @param runtime dependency to resolve Repository dependencies.
     * @return
     */
    public static InventoryManagerMOCK getInstance( RTE.Runtime runtime ) {
        if( inventoryManager_instance == null ) {
            inventoryManager_instance = new InventoryManagerMOCK();
        }
        return inventoryManager_instance;
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
