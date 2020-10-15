package store;


import java.util.Collection;


public interface IPsqlStoreBase<T> {

    Collection<T> findAllEntity();

    void save(T post);



    T findById(int id);

}
