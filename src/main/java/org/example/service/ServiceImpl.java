package org.example.service;

import org.example.dao.pojo.Customer;

import java.util.List;

import static org.example.dao.CRUDCustomer.*;

public class ServiceImpl implements DTOService<Customer> {

    @Override
    public Customer getById(int id) {
        return getCustomerById(id);
    }

    @Override
    public List<Customer> getAll() {
        return getAllCustomers();
    }

    @Override
    public int save(Customer entity) {
        return addCustomer(entity);
    }

    @Override
    public boolean update(Customer entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return deleteCustomer(id);
    }
}
