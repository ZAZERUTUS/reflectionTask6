package org.example.service;

import org.example.dao.pojo.Customer;
import org.example.validator.CustomerValidator;

import java.util.List;

import static org.example.dao.CRUDCustomer.addCustomer;
import static org.example.dao.CRUDCustomer.deleteCustomer;
import static org.example.dao.CRUDCustomer.getAllCustomers;
import static org.example.dao.CRUDCustomer.getCustomerById;
import static org.example.dao.CRUDCustomer.updateCustomer;


public class ServiceImpl implements DTOService<Customer> {

    private final CustomerValidator validator = new CustomerValidator();
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
        return validator.isValid(entity) ? addCustomer(entity) : null;
    }

    @Override
    public boolean update(Customer entity) {
        return validator.isValid(entity) ? updateCustomer(entity) : null;
    }

    @Override
    public boolean delete(int id) {
        return deleteCustomer(id);
    }
}
