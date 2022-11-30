package org.revature.p1.daos;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {
    void create(T obj) throws SQLException;

    T read(T obj);

    void update(T obj);

    void delete(T obj);

    List<T> findAll();
}
