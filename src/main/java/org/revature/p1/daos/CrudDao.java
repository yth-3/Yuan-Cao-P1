package org.revature.p1.daos;

import java.util.List;

public interface CruDao<T> {
    void create(T obj);

    void read(T obj);

    void update(T obj);

    List<T> findAll();
}
