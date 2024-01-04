package org.example.dao;

import lombok.SneakyThrows;
import org.example.dao.pojo.Customer;
import org.example.dbconnect.DbConnector;
import org.example.proxy.annotation.AddCustomer;
import org.example.proxy.annotation.DeleteCustomer;
import org.example.proxy.annotation.GetCustomer;
import org.example.proxy.annotation.UpdCustomer;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс для работы с {@link Customer}
 */
public class CRUDCustomer extends DbConnector {
    private static final Logger logger = LoggerFactory.getLogger(CRUDCustomer.class);

    protected static final String FORMAT_QUERY_GET_CUSTOMER_BY_ID = "SELECT * FROM test.customers where id = %s";
    protected static final String QUERY_GET_ALL_CUSTOMERS = "SELECT * FROM test.customers";
    protected static final String FORMAT_QUERY_GET_ALL_CUSTOMERS_BY_PAGES = "SELECT * FROM test.customers ORDER BY id LIMIT %s OFFSET %s;";

    protected static final String FORMAT_QUERY_ADD_CUSTOMER = "INSERT INTO test.customers " +
            "(name, last_name, second_name, dock_num) " +
            "VALUES ('%s', '%s', '%s', '%s') RETURNING id;";

    protected static final String FORMAT_QUERY_DELETE_CUSTOMER = "DELETE FROM test.customers " +
            "WHERE id = %s RETURNING id;";
    protected static final String FORMAT_QUERY_ROF_UPDATE_CUSTOMER = "UPDATE test.customers " +
            "SET name = '%s', " +
            "last_name = '%s', " +
            "second_name = '%s', " +
            "dock_num = '%s' " +
            "WHERE id = '%s' RETURNING id;";

    @SneakyThrows
    public static List<Customer> getAllCustomers() {
        return getCustomersBySQL(QUERY_GET_ALL_CUSTOMERS);
    }

    @SneakyThrows
    public static List<Customer> getAllCustomersByPages(Integer rows, Integer page) {
        String sql = String.format(FORMAT_QUERY_GET_ALL_CUSTOMERS_BY_PAGES, rows, (page - 1) * rows);
        return getCustomersBySQL(sql);
    }

    private static List<Customer> getCustomersBySQL(String sql) throws SQLException {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement, sql);
        List<Customer> customers = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                customers.add(getCustomerById(resultSet.getInt(1)));
            }
        }
        return customers;
    }

    @GetCustomer
    @SneakyThrows
    public static Customer getCustomerById(int customerId) {
        Statement statement = getStatementPostgres();
        String query = String.format(FORMAT_QUERY_GET_CUSTOMER_BY_ID, customerId);
        ResultSet resultSet = getResultSetBySQLQuery(statement, query);
        Customer customer = null;
        if (resultSet != null && resultSet.next()) {
            customer = new Customer(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        closeDbConnection();
        logger.info("getCustomerById - " + customerId +
                ": " + customer);
        return customer;
    }

    @AddCustomer
    @SneakyThrows
    public static int addCustomer(Customer customer) {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement,
                String.format(FORMAT_QUERY_ADD_CUSTOMER,
                        customer.name,
                        customer.lastName,
                        customer.secondName,
                        customer.numDocument));
        try {
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (NullPointerException e) {
            logger.warn("Request for update account not return result");
        }
        logger.error("Incorrect behavior when add customer - " + customer);
        return 0;
    }


    /**
     * Коммент для проверки. Лесенкой не выстявлял аннотации
     * так как аннотации выставляю по одному порядку над всеми методами
     * @param customerId
     * @return
     */
    @DeleteCustomer
    @SneakyThrows
    public static int deleteCustomer(int customerId) {
        Statement statement = getStatementPostgres();
        ResultSet resultSet = getResultSetBySQLQuery(statement,
                String.format(FORMAT_QUERY_DELETE_CUSTOMER, customerId));
        try {
            if (resultSet.next()) {
                return customerId;
            }
        } catch (NullPointerException | PSQLException e) {
            logger.warn("Not found customer with id - " + customerId + " for delete.");
        }
        logger.error("Incorrect behavior when delete customer - " + customerId);
        return 0;
    }

    @UpdCustomer
    @SneakyThrows
    public static int updateCustomer(Customer customer) {
        Statement statement = getStatementPostgres();
        String formated = String.format(FORMAT_QUERY_ROF_UPDATE_CUSTOMER,
                customer.name,
                customer.lastName,
                customer.secondName,
                customer.numDocument,
                customer.id);
        logger.info("SQL for request- " + formated);

        ResultSet resultSet = getResultSetBySQLQuery(statement,
                formated);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (NullPointerException e) {
            logger.warn("Not found customer with id - " + customer.id + " - " + customer);
        }
        logger.error("Incorrect behavior when update customer - " + customer);
        return 0;
    }
}
