package org.example;

import com.google.gson.GsonBuilder;
import org.example.dao.pojo.Customer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

//    public static ApplicationContext applicationContext;

    /**
     * Для добавления новых пользователей и обновления пример json
     * {"id":2,"name":"Милана","lastName":"Гусева","secondName":"Андреевич","numDocument":"HB1234567"}
     * @param args
     */
    public static void main(String[] args) {


//        applicationContext = ApplicationContextLocal.getApplicationContext();

//        applicationContext = new AnnotationConfigApplicationContext(ConfigApp.class);
//
//
//        DTOService<String> service = applicationContext.getBean(TestService.class);
//        System.out.println(service.getById(1));

//        DockMaker<Customer> pdfMaker = createDockMakerByType(TypePrinter.PDF);
//
//        ServiceImpl service = new ServiceImpl();
//        boolean trigger = true;
//        while (trigger) {
//            Integer selectPoint = getNumMethod();
//            Customer customer;
//            int id;
//
//            switch (selectPoint) {
//                case 0:
//                    trigger = false;
//                    break;
//                case 1:
//                    List<Customer> localList = service.getAll();
//                    System.out.println(localList);
//                    pdfMaker.generateDock("All customers", localList);
//                    break;
//                case 2:
//                    customer = service.getById(getIdCustomer());
//                    System.out.println(customer);
//                    pdfMaker.generateDock("Get customer by id", Collections.singletonList(customer));
//                    break;
//                case 3:
//                    customer = getCustomerFromJsonString();
//
//                    if (customer != null) {
//                        id = service.save(customer); //saved customer
//                        pdfMaker.generateDock("Add new customer",
//                                Collections.singletonList(service.getById(id)));
//                        System.out.println(customer);
//                    } else {
//                        System.out.println("Не корректный json");
//                    }
//                    break;
//                case 4:
//                    customer = getCustomerFromJsonString();
//
//                    if (customer != null) {
//                        id = service.update(customer);
//                        pdfMaker.generateDock("Update customer",
//                                Collections.singletonList(service.getById(id)));
//                        System.out.println("Пользователь обновлён - " + customer);
//                    } else {
//                        System.out.println("Не корректный json");
//                    }
//
//                    break;
//                case 5:
//                    id = getIdCustomer();
//                    customer = service.getById(id);
//                    if (service.delete(id) != 0) {
//                        System.out.println("Пользователь удалён");
//                        pdfMaker.generateDock("Delete customer",
//                                Collections.singletonList(customer));
//                    }
//                    break;
//            }
//        }
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