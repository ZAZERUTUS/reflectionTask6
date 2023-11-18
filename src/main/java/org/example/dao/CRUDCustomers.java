package org.example.dao;

import lombok.SneakyThrows;
import org.example.dao.pojo.Customer;
import org.example.dbconnect.DbConnector;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDCustomers extends DbConnector {
    protected static String formatCustomerById = "SELECT * FROM test.customers where id = %s";
    protected static String queryGetAllCustomers = "SELECT * FROM test.customers";

    @SneakyThrows
    public static synchronized List<Customer> getAllCustomers() {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement, queryGetAllCustomers);
        List<Customer> customers = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                customers.add(getCustomerById(resultSet.getInt(1)));
            }
        }
        return customers;
    }


    @SneakyThrows
    public static synchronized Customer getCustomerById(int customerId) {
        Statement statement = getStatementPostgres();
        String query = String.format(formatCustomerById, customerId);
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
}
