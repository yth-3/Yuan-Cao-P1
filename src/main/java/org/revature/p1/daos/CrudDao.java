package org.revature.p1.daos;

import java.util.List;

public interface CrudDao<T> {
    void create(T obj);

    void read(T obj);

    void update(T obj);

    void delete(T obj);

    List<T> findAll();
}
