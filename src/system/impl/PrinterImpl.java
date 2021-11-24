package system.impl;

import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import system.Calculator;
import system.Formatter;
import system.Printer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

class PrinterImpl implements Printer {

    private Calculator calc;
    private HashMap<Long, Integer> countCustomerOrders;
    private Formatter formatter;
    private Formatter.OrderTableFormatter otfmt;

    PrinterImpl( Calculator calculator ) {
        this.calc = calculator;
        this.formatter = createFormatter();
        this.otfmt = new OrderTableFormatterImpl( formatter, new Object[][] {
            // five column table with column specs: width and alignment ('[' left, ']' right)
            { 12, '[' }, { 20, '[' }, { 36, '[' }, { 10, ']' }, { 10, ']' }
        })
                .liner( "+-+-+-+-+-+" )		// print table header
                .hdr( "||", "Order-Id", "Customer", "Ordered Items", "Order", "MwSt." )
                .hdr( "||", "", "", "", "Value", "incl." )
                .liner( "+-+-+-+-+-+" )
                .liner( "||" );
        this.countCustomerOrders = new HashMap<Long, Integer>();
    }

    /**
     * Print list of orders using OrderTableFormatter.
     *
     * @param orders list of orders to print
     */
    public StringBuffer printOrders( Iterable<Order> orders ) {

        orders.forEach( ( order ) -> {
            printOrder( order );
        });

        long totalAllOrders = calc.calculateValue( orders );		// calculate value of all orders
        long totalVAT = calc.calculateIncludedVAT( orders );		// calculate compound VAT (MwSt.) for all orders

        otfmt			// finalize table with compound value and VAT (MwSt.) of all orders
                .lineTotal( totalAllOrders, totalVAT, Currency.EUR );

        return formatter.getBuffer();
    }


    /**
     * Append content of order to OrderTableFormatter table.
     * <p>Example of lines appended to table:<pre>
     * |8592356245 |Eric's order: |4 Teller (SKU-638035), 4x 6.49€     |  25.96€|  4.14€|
     * |           |              |8 Becher (SKU-693856), 8x 1.49€     |  11.92€|  1.90€|
     * |           |              |1 Buch "OOP" (SKU-425378), 79.95€   |  79.95€|  5.23€|
     * |           |              |4 Tasse (SKU-458362), 4x 2.99€      |  11.96€|  1.91€|
     * |           |              |------------------------------------|--------|-------|
     * |           |              |total:                              | 129.79€| 13.18€|
     * |           |              |                                    |========|=======|
     * |           |              |                                    |        |       |</pre>
     *
     * @param order order to print
     */
    public StringBuffer printOrder( Order order ) {
        // Order ID
        String id = order.getId();

        // Customer's [nth] order:
        long customerId = order.getCustomer().getId();
        if( countCustomerOrders.containsKey( customerId ) ) {
            countCustomerOrders.put( customerId, countCustomerOrders.get( customerId ) + 1 );
        } else {
            countCustomerOrders.put( customerId, 1 );
        }

        String customer = order.getCustomer().getFirstName();
        int customersOrderN = countCustomerOrders.get( customerId );
        switch ( customersOrderN ) {
            case 1:
                customer = customer + "'s order:";
                break;
            case 2:
                customer = customer + "'s 2nd order:";
                break;
            case 3:
                customer = customer + "'s 3rd order:";
                break;
            default:
                customer = customer + "'s " + customersOrderN + "th order:";
                break;
        }
        Iterator<OrderItem> items = order.getItems().iterator();
        // first row is different
        Boolean firstRow = true;

        while( items.hasNext() ) {
            OrderItem item = items.next();
            int itemCount = item.getUnitsOrdered();
            String itemName = item.getArticle().getDescription();
            String itemID = item.getArticle().getId();
            long unitPrice = item.getArticle().getUnitPrice();
            long itemValue = unitPrice * itemCount;
            StringBuffer formattedPrice = formatter.fmtPrice(unitPrice, item.getArticle().getCurrency());
            String price = "";
            if ( itemCount > 1 ) {
                price = itemCount + "x " + formattedPrice;
            } else {
                price = formattedPrice.toString();
            }
            long itemVat = calc.calculateIncludedVAT(itemValue, item.getArticle().getTax());
            if ( firstRow ) {
                otfmt.line( id, customer, itemCount + " " + itemName + " (" + itemID + "), " +
                        price, itemValue, itemVat);
                firstRow = false;
            } else {
                otfmt.line( "", "", itemCount + " " + itemName + " (" + itemID + "), " +
                        price, itemValue, itemVat);
            }
        }
        otfmt.liner( "| | |-|-|-|" );
        otfmt.line( "", "", "total:", calc.calculateValue( order ), calc.calculateIncludedVAT( order ) );
        otfmt.liner( "| | | |=|=|" );
        otfmt.liner( "| | | | | |" );

        return formatter.getBuffer();
    }

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
    @Override
    public void printOrdersToFile( Iterable<Order> orders, String filepath ) throws IOException {
        // create file if not exists yet
        File file = new File( filepath );
        if( !file.getParentFile().exists() )
            file.getParentFile().mkdirs();
        // write data to file
        BufferedWriter writer = new BufferedWriter( new FileWriter( filepath ) );
        writer.write( printOrders( orders ).toString() );
        writer.close();
    }

    /**
     * Create a Formatter object.
     * @return Formatter object.
     */
    @Override
    public Formatter createFormatter() {
        return new FormatterImpl();
    }
}
