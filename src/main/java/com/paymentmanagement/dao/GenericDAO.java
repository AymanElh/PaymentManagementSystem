package com.paymentmanagement.dao;

import java.util.List;

public interface GenericDAO<T> {
    T save(T t);
    T update(T t);
    boolean delete(int id);
    List<T> findAll();
    T findById(int id);
}
