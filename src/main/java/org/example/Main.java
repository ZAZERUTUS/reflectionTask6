package org.example;

import com.google.gson.GsonBuilder;
import org.example.dao.pojo.Customer;
import org.example.printer.DockMaker;
import org.example.printer.TypePrinter;
import org.example.printer.impl.PdfMakerClevertec;
import org.example.service.ServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.example.printer.PrinterFactory.createDockMakerByType;

public class Main {

    /**
     * Для добавления новых пользователей и обновления пример json
     * {"id":2,"name":"Милана","lastName":"Гусева","secondName":"Андреевич","numDocument":"HB1234567"}
     * @param args
     */
    public static void main(String[] args) {

        DockMaker<Customer> dockMaker = createDockMakerByType(TypePrinter.PDF);
        ServiceImpl service = new ServiceImpl();
        boolean trigger = true;
        while (trigger) {
            Integer selectPoint = getNumMethod();
            Customer customer;

            switch (selectPoint) {
                case 0:
                    trigger = false;
                    break;
                case 1:
                    List<Customer> localList = service.getAll();
                    System.out.println(localList);
                    dockMaker.generateDock("All customers", localList);
                    break;
                case 2:
                    Customer customerLocal = service.getById(getIdCustomer());
                    System.out.println(customerLocal);
                    dockMaker.generateDock("Get customer by id", Collections.singletonList(customerLocal));
                    break;
                case 3:
                    customer = getCustomerFromJsonString();

                    System.out.println(customer != null ?
                            service.save(customer) :
                            "Не корректный json");
                    break;
                case 4:
                    customer = getCustomerFromJsonString();

                    System.out.println(customer != null ?
                            "Пользователь обновлён - " + service.update(customer) :
                            "Не корректный json");
                    break;
                case 5:
                    System.out.println("Пользователь удалён - " + service.delete(getIdCustomer()) != null);
                    break;
            }
        }
    }

    protected static Integer getNumMethod() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Выберите тип операции (введите число) или 0 для выхода\n" +
                    "1 - getAll\n" +
                    "2 - getById\n" +
                    "3 - save\n" +
                    "4 - update\n" +
                    "5 - delete");
            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите корректное число или 0 для выхода");
            }
        }
    }

    protected static int getIdCustomer() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите id пользователя: ");
            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите корректное число");
            }
        }
    }

    protected static Customer getCustomerFromJsonString() {
        String json;
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите строку json для добавления/обновления пользователя:");
            try {
                json = in.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Пожалуйста проверьте и введите снова");
            }
        }
        try {
            return new GsonBuilder().create().fromJson(json, Customer.class);
        } catch (Exception e) {
            System.out.println("Не корректная строка json");
            return null;
        }
    }
}