package system;

import datamodel.Order;

import java.io.IOException;

/**
 * Public interface of a Printer component that provides
 * methods and interfaces to print orders from the database.
 *
 * @author fkate
 * @since 0.1.0
 * @version 0.1.1
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
     * Print orders as table to a file.
     *
     * Conditions:
     * - creates new file or overwrites an existing file.
     * - not existing parts of the path are created, throws IOException
     * if this is not possible.
     *
     * @param orders list of orders to print.
     * @param filepath path and name of the output file.
     * @throws IOException for errors.
     */
    void printOrdersToFile( Iterable<Order> orders, String filepath ) throws IOException;

    /**
     * Create a Formatter object.
     * @return Formatter object.
     */
    public Formatter createFormatter();

}
