package org.example.dao.pojo;


/**
 * Рандомные данные используются умышленно для проверки,
 * это не вредит качеству тестов
 */
public class CustomerForTest {

    int randNum = (int) (Math.random() * 150);
    Customer customer = new Customer(randNum,
            String.format("Name%s", randNum),
            String.format("LastName%s", randNum),
            String.format("SecondName%s", randNum),
            "HB1234567");

    public Customer getCustomer() {
        return (customer);
    }

    public String getRow() {
        return customer.id + " " +
                customer.name + " " +
                customer.lastName + " " +
                customer.secondName + " " +
                customer.numDocument;
    }

}