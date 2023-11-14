package org.example;

import org.example.dao.pojo.Customer;

import java.util.List;

import static org.example.dao.CRUDCustomers.getAllCustomers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Customer> customers = getAllCustomers();
        System.out.println(customers);
        System.out.println("-------");
    }
}