package datamodel;

/**
 * Class for entity type OrderItem. OrderItem is an object, that belongs to an order referring to an Article.
 *
 * @since 0.1.0
 * @version {@value package_info#Version}
 * @author fkate
 */
public class OrderItem {
    /**
     * article attribute, can be set only once
     */
    private final Article article;

    /**
     * unitsOrdered attribute
     */
    private int unitsOrdered;

    /**
     * Public constructor with article and unitsOrdered arguments.
     * @param article Article to be ordered.
     * @param unitsOrdered Amount of units ordered of the Article.
     */
    public OrderItem( Article article, int unitsOrdered ) {
        this.article = article;
        this.unitsOrdered = unitsOrdered;
    }

    /**
     * article getter.
     * @return Article that got placed as order item.
     */
    public Article getArticle() {
        return article;
    }

    /**
     * unitsOrdered getter.
     * @return integer amount of units that got ordered of the article.
     */
    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    /**
     * unitsOrdered setter. Set the amount of units to be ordered of the article.
     * @param units amount of units to be ordered of the article.
     */
    public void setUnitsOrdered( int units ) {
        this.unitsOrdered = units;
    }
}
