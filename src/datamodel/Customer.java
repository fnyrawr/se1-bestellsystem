package datamodel;

import java.util.*;

/**
 * Class for entity type Customer. Customer is an individual (not a business) who creates and owns orders in the system.
 *
 * @since 0.1.0
 * @version {@value package_info#Version}
 * @author fkate
 */
public class Customer {

    /**
     * id attribute, {@code < 0} invalid, can be set only once
     */
    private long id = -1;

    /**
     * none-surname name parts, never null, mapped to ""
     */
    private String firstName = "";

    /**
     * surname, never null, mapped to ""
     */
    private String lastName = "";

    /**
     * contact information, multiple contacts are possible
     */
    private final List<String> contacts = new ArrayList<String>();

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Public constructor with name argument.
     * @param name single-String Customer name, e.g. "Eric Meyer"
     */
    public Customer( String name ) {
        setName(name);
    }

    /**
     * Id getter.
     * @return customer id, may be invalid {@code < 0} if unassigned
     */
    public long getId() {
        return id;
    }

    /**
     * Id setter. Id can only be set once, id is immutable after assignment.
     * @param id assignment if id is valid {@code >= 0} and id attribute is still unassigned {@code < 0}
     * @return chainable self-reference
     */
    public Customer setId( long id ) {
        if( this.id == -1 ) {
            this.id = id;
        }
        return this;
    }

    /**
     * Getter that returns single-String name from lastName and firstName attributes in format: {@code "lastName, firstName"} or {@code "lastName"} if {@code firstName} is empty.
     * @return single-String name
     */
    public String getName() {
        if( getFirstName().isEmpty() ) {
            return getLastName();
        }
        if( getLastName().isEmpty() ) {
            return  getFirstName();
        }
        return getLastName() + ", " + getFirstName();
    }

    /**
     * firstName getter.
     * @return value of firstName attribute, never null, mapped to ""
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * lastName getter.
     * @return value of lastName attribute, never null, mapped to ""
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter that splits single-String name (e.g. "Eric Meyer") into first- and lastName parts and assigns parts to corresponding attributes.
     * @param name single-String name to split into first- and lastName parts
     * @return chainable self-reference
     */
    public Customer setName( String name ) {
        if( name != null ) {
            splitName(name);
        }
        return this;
    }

    /**
     * firstName, lastName setter. Method maintains that both attributes are never null; null-arguments are ignored and don't change attributes.
     * @param first assigned to firstName attribute
     * @param last assigned to lastName attribute
     * @return chainable self-reference
     */
    public Customer setName( String first, String last ) {
        if( first != null ) {
            this.firstName = first;
        }
        if( last != null ) {
            this.lastName = last;
        }
        return this;
    }

    /**
     * Return number of contacts.
     * @return number of contactsContacts getter (as {@code String[]}).
     */
    public int contactsCount() {
        return contacts.size();
    }

    /**
     * Contacts getter (as {@code String[]}).
     * @return contacts (as {@code String[]})
     */
    public String[] getContacts() {
        String[] s = new String[contacts.size()];
        for(int i = 0; i < contacts.size(); i++) {
            s[i] = contacts.get(i);
        }
        return s;
    }

    /**
     * Contacts getter (as {@code String}).
     * @return contacts (as {@code String})
     */
    public String getContactsAsString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contacts.size(); i++) {
            sb.append(contacts.get(i));
            if(i < contacts.size()-1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Add new contact for Customer. Duplicate entries are ignored.
     * @param contact added when not already present
     * @return chainable self-reference
     */
    public Customer addContact( String contact ) {
        if( contact != null && contact != "" && !contacts.contains(contact) ) {
            contacts.add(contact);
        }
        return this;
    }

    /**
     * Delete i-th contact with constraint: {@code i >= 0} and {@code i < contacts.size()}, otherwise method has no effect.
     * @param i index in contacts
     */
    public void deleteContact( int i ) {
        if( i >= 0 && i < contacts.size() ) {
            contacts.remove(i);
        }
    }

    /**
     * Delete all contacts.
     */
    public void deleteAllContacts() {
        contacts.clear();
    }

    /**
     * Split single-String name into first- and last name.
     * @param name single-String name split into first- and last name
     */
    private void splitName( String name ) {
        if( name.contains(", ") ) {
            String[] s = name.split(", ");
            this.lastName = s[0];
            this.firstName = s[1];
            return;
        }
        if( name.contains("; ") ) {
            String[] s = name.split("; ");
            this.lastName = s[0];
            this.firstName = s[1];
            return;
        }
        if( name.contains(" ") ) {
            String[] s = name.split(" ");
            String tmpLn = s[0];
            if( s.length > 2 ) {
                for (int i = 1; i < s.length-2; i++) {
                    tmpLn = tmpLn + " " + s[i];
                }
            }
            this.lastName = s[s.length-1];
            this.firstName = tmpLn;
        }
    }

}