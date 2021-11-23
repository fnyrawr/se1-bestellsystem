package system.impl;

import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;
import system.Calculator;

class CalculatorImpl implements Calculator {

    /**
     * Calculates the compound value of all orders
     *
     * @param orders list of orders
     * @return compound value of all orders
     */
    public long calculateValue( Iterable<Order> orders ) {
        long value = 0;
        for( Order order: orders ) {
            value = value + calculateValue( order );
        }
        return value;
    }

    /**
     * Calculates the compound VAT of all orders
     *
     * @param orders list of orders
     * @return compound VAT of all orders
     */
    public long calculateIncludedVAT( Iterable<Order> orders ) {
        long vat = 0;
        for( Order order: orders ) {
            vat = vat + calculateIncludedVAT( order );
        }
        return vat;
    }

    /**
     * Calculates the value of one order
     *
     * @param order order for which the value is calculated
     * @return value of the order
     */
    public long calculateValue( Order order ) {
        long value = 0;
        for ( OrderItem item: order.getItems() ) {
            value = value + item.getUnitsOrdered()*item.getArticle().getUnitPrice();
        }
        return value;
    }

    /**
     * Calculates the VAT of one order. VAT is based on the rate that
     * applies to the articles of each line item.
     *
     * @param order order for which the included VAT is calculated
     * @return compound VAT of all ordered items
     */
    public long calculateIncludedVAT( Order order ) {
        long vat = 0;
        for ( OrderItem item: order.getItems() ) {
            vat = vat + calculateIncludedVAT( item.getUnitsOrdered()*item.getArticle().getUnitPrice(), item.getArticle().getTax() );
        }
        return vat;
    }

    /*
     * private helper to calculate included VAT for a price based on
     * a TAX rate (as defined in Enum TAX).
     */
    public long calculateIncludedVAT( long price, TAX taxRate ) {
        // price / (100+TAX) * (TAX)
        long vat = Math.round( price / ( 100.0 + taxRate.rate() ) * ( taxRate.rate() ) );
        return vat;
    }
}
