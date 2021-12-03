package system;

/**
 * Public interface of data repositories that store objects (entities)
 * of data model classes.
 *
 * @author fkate
 * @since 0.1.2
 * @version 0.1.2
 *
 */

import datamodel.Order;

public interface OrderBuilder {
    /**
     * Validate and save order to OrderRepository, if order is accepted.
     *
     * @param order order to accept.
     * @return true if order was validated and saved to OrderRepository.
     */
    public boolean accept( Order order );
    /**
     * Build orders in OrderRepository.
     *
     * @return chainable self‐reference.
     */
    public OrderBuilder build();
}