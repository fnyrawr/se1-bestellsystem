package system;

import java.util.Optional;

public interface Repository<T> {
    Optional<T> findById( long id );
    Optional<T> findById( String id );
    Iterable<T> findAll();
    long count();
    T save(T entity);
}
