package org.example.validator;

import org.example.dao.pojo.Customer;
import org.example.dao.pojo.CustomerForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerValidatorTest {

    @Test
    public void testWithValidData() {
        //Given
        Validator<Customer> validator = new CustomerValidator();
        Customer customer = new CustomerForTest().getCustomer();

        //When
        boolean actual = validator.isValid(customer);

        //Then
        Assertions.assertTrue(actual);
    }

    @Test
    public void testWithNotValidNumDock() {
        //Given
        Validator<Customer> validator = new CustomerValidator();
        Customer customer = new CustomerForTest().getCustomer();
        customer.numDocument = "HB22222";

        //When
        boolean actual = validator.isValid(customer);

        //Then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testWithNotValidName() {
        //Given
        Validator<Customer> validator = new CustomerValidator();
        Customer customer = new CustomerForTest().getCustomer();
        customer.name = "vlad sh";

        //When
        boolean actual = validator.isValid(customer);

        //Then
        Assertions.assertFalse(actual);
    }
}