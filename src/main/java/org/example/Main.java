package org.example;

import org.example.dao.pojo.Customer;
import org.example.proxy.CacheAspect;
import org.example.service.ServiceImpl;

import java.util.List;

import static org.example.dao.CRUDCustomer.getAllCustomers;
import static org.example.dao.CRUDCustomer.getCustomerById;

public class Main {
    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        List<Customer> customers = getAllCustomers();
        System.out.println("Len - " + customers.size());
        Customer customer1 = service.getById(1);
        System.out.println("-------" + customer1);
        Customer customer2 = service.getById(1);
        System.out.println("-------" + customer2);
        customer2 = service.getById(2);
        customer2 = service.getById(3);
//        customer2 = service.getById(1);
        service.save(customer2);
        customer2 = service.getById(15);
        customer2 = service.getById(15);
        service.delete(15);

    }
}