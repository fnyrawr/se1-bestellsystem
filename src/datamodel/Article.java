package datamodel;

/**
 * Class for entity type Article. Article is an object, that belongs to orders.
 *
 * @since 0.1.0
 * @version {@value package_info#Version}
 * @author fkate
 */
public class Article {

    /**
     * id attribute, {@code = null} invalid, can be set only once
     */
    private String id = null;

    /**
     * description of an article, never null, mapped to ""
     */
    private String description = "";

    /**
     * unit price of an article, never null, mapped to ""
     */
    private long unitPrice = 0;

    /**
     * currency of an article, never null, mapped to EUR
     */
    private Currency currency = Currency.EUR;

    /**
     * tax of an article, never null, mapped German VAT
     */
    private TAX tax = TAX.GER_VAT;

    /**
     * Default constructor
     */
    public Article() {

    }

    /**
     * Public constructor with description and unitPrice argument.
     * @param descr description of an article
     * @param unitPrice unit price of an article
     */
    public Article( String descr, long unitPrice ) {
        setDescription( descr );
        setUnitPrice( unitPrice );
    }

    /**
     * Id getter.
     * @return article id, may be invalid {@code = null} if unassigned
     */
    public String getId() {
        return id;
    }

    /**
     * Id setter. Id can only be set once, id is immutable after assignment.
     * @param id assignment if id is valid {@code != 0 && != ""} and id attribute is still unassigned {@code == null}
     * @return chainable self-reference
     */
    public Article setId( String id ) {
        if( this.id == null && id != null && id != "" ) {
            this.id = id;
        }
        return this;
    }

    /**
     * Getter that returns a description of the article
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter that sets a description for the article
     * @param descr description for the article
     * @return chainable self-reference
     */
    public Article setDescription( String descr ) {
        this.description = descr;
        return this;
    }

    /**
     * Getter that returns the unit price of the article
     * @return long unitPrice
     */
    public long getUnitPrice() {
        return unitPrice;
    }

    /**
     * Setter that sets the unit price of the article
     * @param unitPrice unit price of the article, {@code null} is invalid
     * @return chainable self-reference
     */
    public Article setUnitPrice( long unitPrice ) {
        if( unitPrice >= 0 ) {
            this.unitPrice = unitPrice;
        }
        return this;
    }

    /**
     * Getter that returns the currency of the article
     * @return Currency currency
     */
    public Currency getCurrency() {
        return this.currency;
    }

    /**
     * Setter that sets the currency of the article
     * @param currency currency of the article, {@code null} is invalid
     * @return chainable self-reference
     */
    public Article setCurrency( Currency currency ) {
        if( currency != null ) {
            this.currency = currency;
        }
        return this;
    }

    /**
     * Getter that returns the tax rate of the article
     * @return TAX tax
     */
    public TAX getTax() {
        return tax;
    }

    /**
     * Setter that sets the tax rate of the article
     * @param tax tax rate of the article, {@code null} is invalid
     * @return chainable self-reference
     */
    public Article setTax(TAX tax) {
        if( tax != null ) {
            this.tax = tax;
        }
        return this;
    }
}
