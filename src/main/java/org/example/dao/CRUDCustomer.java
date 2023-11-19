package org.example.dao;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.cash.impl.CacheLFU;
import org.example.dao.pojo.Customer;
import org.example.dbconnect.DbConnector;
import org.example.proxy.annotation.GetCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDCustomer extends DbConnector {
    private static final Logger logger = LoggerFactory.getLogger(CRUDCustomer.class);

    protected static final String FORMAT_QUERY_GET_CUSTOMER_BY_ID = "SELECT * FROM test.customers where id = %s";
    protected static final String QUERY_GET_ALL_CUSTOMERS = "SELECT * FROM test.customers";
    protected static final String FORMAT_QUERY_ADD_A_CUSTOMER = "INSERT INTO test.customers (name, last_name, second_name, dock_num) VALUES ('%s', '%s', %s, %s) RETURNING id";

    @SneakyThrows
    public static List<Customer> getAllCustomers() {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement, QUERY_GET_ALL_CUSTOMERS);
        List<Customer> customers = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                customers.add(getCustomerById(resultSet.getInt(1)));
            }
        }
        return customers;
    }


    @SneakyThrows
    @GetCustomer
    public static Customer getCustomerById(int customerId) {
        logger.info("get from DB");
        Statement statement = getStatementPostgres();
        String query = String.format(FORMAT_QUERY_GET_CUSTOMER_BY_ID, customerId);
        ResultSet resultSet = getResultSetBySQLQuery(statement, query);
        Customer customer = null;
        if (resultSet != null) {
            resultSet.next();
            customer = new Customer(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        closeDbConnection();
        return customer;
    }

    @SneakyThrows
    public static int addCustomer(Customer customer) {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement,
                String.format(FORMAT_QUERY_ADD_A_CUSTOMER, customer.name, customer.lastName, customer.secondName, customer.numDocument));
        try {
            if (resultSet != null & resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (NullPointerException e) {
            System.out.println("Request for update account not return result");
        }
        System.out.println("Incorrect behavior when add account - " + customer);
        return 0;
    }
}
