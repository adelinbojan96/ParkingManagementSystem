package com.example;

import java.util.List;

public interface CrudRepository<ID,E> {
    void save(E e);
    List<E> getAll();
}
