package system;

import datamodel.Order;

/**
 * Public interface of a Printer component that provides
 * methods and interfaces to print orders from the database.
 *
 * @author fkate
 * @since 0.1.0
 * @version 0.1.0
 *
 */

public interface Printer {

    /**
     * Print orders from an Iterable of the type Order.
     * @param orders Iterable object of Orders containing the orders to be printed.
     * @return StringBuffer with all orders which can be printed.
     */
    public StringBuffer printOrders( Iterable<Order> orders );

    /**
     * Print an order.
     * @param order Order object containing the order to be printed.
     * @return StringBuffer of the order which can be printed.
     */
    public StringBuffer printOrder( Order order );

    /**
     * Create a Formatter object.
     * @return Formatter object.
     */
    public Formatter createFormatter();

}
