package system;

import datamodel.Order;
import datamodel.TAX;

/**
 * Public interface of a Calculator component that provides
 * methods and interfaces to calculate values and included tax rates.
 *
 * @author fkate
 * @since 0.1.0
 * @version 0.1.0
 *
 */

public interface Calculator {

    /**
     * Calculate the value of a list of Orders
     * @param orders Iterable object of Orders containing the orders to be calculated.
     * @return value of the orders as long.
     */
    public long calculateValue( Iterable<Order> orders );

    /**
     * Calculate the value of an Orders
     * @param order Order to be calculated.
     * @return value of the order as long.
     */
    public long calculateValue( Order order );

    /**
     * Calculate the included VAT of a list of Orders
     * @param orders Iterable object of Orders containing the orders to be calculated.
     * @return included VAT of the orders as long.
     */
    public long calculateIncludedVAT( Iterable<Order> orders );

    /**
     * Calculate the included VAT of an Order
     * @param order Order to be calculated.
     * @return included VAT of the order as long.
     */
    public long calculateIncludedVAT( Order order );

    /**
     * Calculate the included VAT of a price with a given tax rate
     * @param price given price as long.
     * @param taxRate tax rate as TAX.
     * @return included VAT of the article.
     */
    public long calculateIncludedVAT( long price, TAX taxRate);

}
