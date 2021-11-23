package system.impl;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import system.Repository;

import java.util.LinkedHashMap;
import java.util.Optional;

public class RepositoryImpl<T> implements Repository<T> {
    // LinkedHashMap keeps the insertion order consistent while HashMap won't
    protected LinkedHashMap<String,T> containerMap;

    public RepositoryImpl() {
        containerMap = new LinkedHashMap<String,T>();
    }

    public Optional<T> findById( long id ) {
        String sId = String.valueOf( id );
        return findById( sId );
    }

    public Optional<T> findById( String id ) {
        if( containerMap.containsKey( id ) )
            return Optional.of( containerMap.get( id ) );
        return Optional.empty();
    }

    public Iterable<T> findAll() {
        return ( Iterable<T> ) containerMap.values();
    }

    public long count() {
        return containerMap.size();
    }

    public T save( T entity ) {
        // if entity is a Customer object, then cast it to get the ID
        if( entity instanceof Customer ) {
            String id = String.valueOf( ((Customer) entity).getId() );
            containerMap.put( id, entity );
            return entity;
        }

        // if entity is an Article object, then cast it to get the ID
        if( entity instanceof Article) {
            String id = ((Article) entity).getId();
            containerMap.put( id, entity );
            return entity;
        }

        // if entity is an Order object, then cast it to get the ID
        if( entity instanceof Order) {
            String id = ((Order) entity).getId();
            containerMap.put( id, entity );
            return entity;
        }
        return null;
    }
}
