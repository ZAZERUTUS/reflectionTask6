package org.example;

import org.example.dao.pojo.Customer;
import org.example.proxy.CacheAspect;

import java.util.List;

import static org.example.dao.CRUDCustomer.getAllCustomers;
import static org.example.dao.CRUDCustomer.getCustomerById;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Customer> customers = getAllCustomers();
        System.out.println("Len - " + customers.size());
        System.out.println("-------1");
        Customer customer1 = getCustomerById(1);
        System.out.println("-------" + customer1);
        System.out.println("-------2");
        Customer customer2 = getCustomerById(1);
        System.out.println("-------" + customer2);
        customer2 = getCustomerById(2);
        customer2 = getCustomerById(3);
        customer2 = getCustomerById(1);


    }
}