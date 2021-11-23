package datamodel;

import java.util.*;
import java.util.stream.*;

/**
 * Class for entity type Order. Order is an object, that belongs to a customer.
 *
 * @since 0.1.0
 * @version {@value package_info#Version}
 * @author fkate
 */
public class Order {

    /**
     * id attribute, {@code = null} invalid, can be set only once
     */
    private String id = null;

    /**
     * customer attribute, {@code = null} invalid, can be set only once
     */
    private final Customer customer;

    /**
     * creationDate attribute, {@code = null} invalid, can be set only once
     */
    private final Date creationDate;

    /**
     * items attribute, ArrayList of OrderItem, can be set only once
     */
    private final List<OrderItem> items = new ArrayList<OrderItem>();

    /**
     * Public constructor with customer argument.
     * @param customer Customer owning the order, {@code != null}
     */
    public Order( Customer customer ) {
        if( customer == null ) throw new IllegalArgumentException();
        this.customer = customer;
        creationDate = new Date( System.currentTimeMillis() );
    }

    /**
     * Id getter.
     * @return order id, may be invalid {@code = null} if unassigned
     */
    public String getId() {
        return id;
    }

    /**
     * Id setter. Id can only be set once, id is immutable after assignment.
     * @param id assignment if id is valid {@code != null && != ""} and id attribute is still unassigned {@code = null}
     * @return chainable self-reference
     */
    public Order setId( String id ) {
        if( this.id == null && id != null && id != "" ) {
            this.id = id;
        }
        return this;
    }

    /**
     * customer getter.
     * @return customer owning the order
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * creation date getter.
     * @return timestamp (Date object) of the order creation
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * creation date setter
     * @deprecated not working since creation date is set in constructor and final variables can't be set outside of the constructor.
     * @param dateAsLong date as long
     * @return chainable self-reference
     */
    public Order setCreationDate( long dateAsLong ) {
        // cannot assign a value to a final variable
        return this;
    }

    /**
     * get items count
     * @return amount of items in the order
     */
    public int itemsCount() {
        return items.size();
    }

    /**
     * get items as Iterable
     * @return items as Iterable
     */
    public Iterable<OrderItem> getItems() {
        return ( Iterable<OrderItem> ) items;
    }

    /**
     * get items as Array
     * @return items as Array
     */
    public OrderItem[] getItemsAsArray() {
        OrderItem[] itm = new OrderItem[itemsCount()];
        for( int i=0; i<itemsCount(); i++ ) {
            itm[i] = items.get(i);
        }
        return itm;
    }

    /**
     * get items as Stream
     * @return items as Stream
     */
    public Stream<OrderItem> getItemsAsStream() {
        return items.stream();
    }

    /**
     * add item to order
     * @param article Article to add {@code != null}
     * @param units amount of units to add {@code > 0}
     * @return chainable self-reference
     */
    public Order addItem( Article article, int units ) {
        if( article == null || units <= 0 ) throw new IllegalArgumentException();
        items.add( new OrderItem( article, units) );
        return this;
    }

    /**
     * delete an item from the order
     * @param i position to delete {@code >= 0 && < items.size()}
     */
    public void deleteItem(int i) {
        if( i >= 0 && i < items.size() ) {
            items.remove(i);
        }
    }

    /**
     * delete all items from the order
     */
    public void deleteAllItems() {
        items.clear();
    }
}
