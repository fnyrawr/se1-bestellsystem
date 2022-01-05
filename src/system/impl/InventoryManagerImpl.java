package system.impl;

import datamodel.Article;
import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import system.Formatter;
import system.InventoryManager;
import system.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InventoryManagerImpl implements InventoryManager {

    /**
     * dependency on ArticleRepository.
     */
    private final Repository<Article> articleRepository;


    /**
     * internal data structure to manage inventory (unitsInStore) for Article-id's.
     */
    private final HashMap<String,Integer> inventory = new HashMap<String,Integer>();


    private InventoryManagerImpl(Repository<Article> articleRepository) {
        this.articleRepository = articleRepository;
    }


    public static InventoryManagerImpl getInstance(Repository<Article> articleRepository) {
        return new InventoryManagerImpl(articleRepository);
    }


    /**
     * Return units in stock for given article.
     *
     * @param id article identifier.
     * @return units in stock of article.
     * @throws IllegalArgumentException if id is null or id does not exist.
     */
    @Override
    public int getUnitsInStock(String id) {
        if (id != null && inventory.containsKey(id)) {
            return inventory.get(id);
        } else {
            throw new IllegalArgumentException("id is null or does not exist");
        }
    }


    /**
     * Update inventory for article.
     *
     * @param id                  article identifier.
     * @param updatedUnitsInStock update with number (must be {@code >= 0}).
     * @throws IllegalArgumentException if id is null, id does not exist or unitsInStock is {@code < 0}).
     */
    @Override
    public void update(String id, int updatedUnitsInStock) {
        if (id != null && inventory.containsKey(id) && getUnitsInStock(id) >= 0 && updatedUnitsInStock >= 0) {
            inventory.replace(id, updatedUnitsInStock);
        } else {
            throw new IllegalArgumentException("id is null, does not exist, or unit is not in stock");
        }
    }


    /**
     * Test that order is fillable.
     * <p>
     * An order is fillable when all order items meet the condition:
     * {@code orderItem.unitsOrdered <= inventory(article).unitsInStock}.
     *
     * @param order to validate.
     * @return true if order is fillable from current inventory.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public boolean isFillable(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }

        boolean isFillable = true;
        Iterable<OrderItem> orderedItems = order.getItems();
        for (OrderItem item : orderedItems) {
            if (item.getUnitsOrdered() <= getUnitsInStock(item.getArticle().getId())) {
                isFillable = true;
            } else {
                isFillable = false;
                break;
            }
        }
        return isFillable;
    }


    /**
     * Fills order by deducting all order items from the inventory, if the
     * order is fillable. If the order is not fillable, inventory remains
     * unchanged (transactional behavior: all or none order item is filled).
     *
     * @param order to fill.
     * @return true if order has been filled, false otherwise.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public boolean fill(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }

        Iterable<OrderItem> orderedItems = order.getItems();
        if (isFillable(order)) {
            for (OrderItem item : orderedItems) {
                int inventoryUnits = inventory.get(item.getArticle().getId());
                int units = item.getUnitsOrdered();
                inventory.replace(item.getArticle().getId(), inventoryUnits - units);
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Optional<Article> findById(String id) {
        if (inventory.containsKey(id)) {
            return articleRepository.findById(id);
        }
        return Optional.empty();
    }


    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }


    @Override
    public long count() {
        return inventory.size();
    }


    /**
     * Create new article in InventoryManager (add to internal ArticleRepository).
     *
     * @param article article to create.
     * @throws IllegalArgumentException if article is null, has no valid id or already exists.
     */
    @Override
    public Article save( Article article ) {
        if( article == null )
            throw new IllegalArgumentException( "illegal article: null" );
        //
        String id = article.getId();
        if( id == null )
            throw new IllegalArgumentException( "illegal article.id: null" );
        //
        articleRepository.save( article );	// save, make sure to avoid duplicates
        //
        if( ! inventory.containsKey( id ) ) {
            this.inventory.put( id, Integer.valueOf( 0 ) );
        }
        return article;
    }


    /**
     * Print inventory as table.
     *
     * @return printed inventory (as table).
     */
    @Override
    public StringBuffer printInventory() {
        return printInventory(
                StreamSupport.stream( articleRepository.findAll().spliterator(), false )
        );
    }


    /**
     * Print inventory as table with sorting and limiting criteria.
     *
     * @param sortedBy  sorting criteria 1: byPrice; 2: byValue; 3: byUnits;
     *                  4: byDescription; 5: bySKU; else: unsorted
     * @param descending true if in descending order
     * @param limit     upper boundary of articles printed after sorting
     * @return printed inventory (as table).
     */
    @Override
    public StringBuffer printInventory(int sortedBy, boolean descending, Integer limit) {

        Stream<Article> articleStream = StreamSupport.stream(articleRepository.findAll().spliterator(), false);

        if (descending == false) {

            if (sortedBy == 1) {
                return printInventory(articleStream
                        .sorted(Comparator.comparingLong(a -> a.getUnitPrice()))
                        .limit(limit));
            }

            if (sortedBy == 2) {
                return printInventory(articleStream
                        .sorted(Comparator.comparingLong(a -> a.getUnitPrice() * inventory.get(a.getId())))
                        .limit(limit));
            }

            if (sortedBy == 3) {
                return printInventory(articleStream
                        .sorted(Comparator.comparingInt(a -> inventory.get(a.getId())))
                        .limit(limit));
            }

            if (sortedBy == 4) {
                return printInventory(articleStream
                        .sorted(Comparator.comparing(a -> a.getDescription()))
                        .limit(limit));
            }

            if (sortedBy == 5) {
                return printInventory(articleStream
                        .sorted(Comparator.comparing(a -> a.getId()))
                        .limit(limit));
            }

        }

        else {

            if (sortedBy == 1) {
                return printInventory(articleStream
                        .sorted((a1, a2) -> calculateUnitPriceToInteger(a2).compareTo(calculateUnitPriceToInteger(a1)))
                        .limit(limit));
            }

            if (sortedBy == 2) {
                return printInventory(articleStream
                        .sorted((a1, a2) -> calculateValueToInteger(a2).compareTo(calculateValueToInteger(a1)))
                        .limit(limit));
            }

            if (sortedBy == 3) {
                return printInventory(articleStream
                        .sorted((a1, a2) -> inventory.get(a2.getId()).compareTo(inventory.get(a1.getId())))
                        .limit(limit));
            }

            if (sortedBy == 4) {
                return printInventory(articleStream
                        .sorted((a1, a2) -> a2.getDescription().compareTo(a1.getDescription()))
                        .limit(limit));
            }

            if (sortedBy == 5) {
                return printInventory(articleStream
                        .sorted((a1, a2) -> a2.getId().compareTo(a1.getId()))
                        .limit(limit));
            }

        }

        return null;
    }


    private StringBuffer printInventory( Stream<Article> articleStream ) {
        //
        Formatter formatter = new FormatterImpl();
        Formatter.TableFormatter tfmt = new TableFormatterImpl( formatter, new Object[][] {
                // five column table with column specs: width and alignment ('[' left, ']' right)
                { 12, '[' }, { 32, '[' }, { 12, ']' }, { 10, ']' }, { 14, ']' }
        })
                .liner( "+-+-+-+-+-+" )		// print table header
                .hdr( "||", "Inv.-Id", "Article / Unit", "Unit", "Units", "Value" )
                .hdr( "||", "", "", "Price", "in-Stock", "(in €)" )
                .liner( "+-+-+-+-+-+" )
                ;
        //
        long totalValue = articleStream
                .map( a -> {
                    long unitsInStock = this.inventory.get( a.getId() ).intValue();
                    long value = a.getUnitPrice() * unitsInStock;
                    tfmt.hdr( "||",
                            a.getId(),
                            a.getDescription(),
                            formatter.fmtPrice( a.getUnitPrice(), a.getCurrency()).toString(),
                            Long.toString( unitsInStock ),
                            formatter.fmtPrice( value, a.getCurrency() ).toString()
                    );
                    return value;
                })
                .reduce( 0L, (a, b) -> a + b );
        //
        String inventoryValue = formatter.fmtPrice( totalValue, Currency.EUR ).toString();
        tfmt
                .liner( "+-+-+-+-+-+" )
                .hdr( "", "", "Invent", "ory Value:", inventoryValue )
        ;
        //
        return tfmt.getFormatter().getBuffer();
    }


    private Integer calculateValueToInteger(Article article) {
        return (int) article.getUnitPrice() * inventory.get(article.getId());
    }


    private Integer calculateUnitPriceToInteger(Article article) {
        return (int) article.getUnitPrice();
    }
}
