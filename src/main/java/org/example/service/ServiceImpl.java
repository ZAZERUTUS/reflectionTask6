package org.example.service;

import org.example.dao.pojo.Customer;
import org.example.validator.CustomerValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.dao.CRUDCustomer.addCustomer;
import static org.example.dao.CRUDCustomer.deleteCustomer;
import static org.example.dao.CRUDCustomer.getAllCustomers;
import static org.example.dao.CRUDCustomer.getAllCustomersByPages;
import static org.example.dao.CRUDCustomer.getCustomerById;
import static org.example.dao.CRUDCustomer.updateCustomer;

@Component
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
    public List<Customer> getAll(Integer rows, Integer page) {
        return getAllCustomersByPages(rows, page);
    }

    @Override
    public int save(Customer entity) {
        if (entity == null) {
            return 0;
        }
        return validator.isValid(entity) ? addCustomer(entity) : null;
    }

    @Override
    public int update(Customer entity) {
        int id;
        if (!validator.isValid(entity)) {
            return 0;
        }
        if (getCustomerById(entity.getId()) != null) {
            id = updateCustomer(entity);
        } else {
            id = addCustomer(entity);
        }
        return id;
    }

    @Override
    public int delete(int id) {
        return deleteCustomer(id);
    }
}
