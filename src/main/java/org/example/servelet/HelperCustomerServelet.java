package org.example.servelet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.pojo.Customer;
import org.example.printer.DockMaker;
import org.example.servelet.dto.ErrorGetUser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class HelperCustomerServelet {

    DockMaker<Customer> pdfMaker;

    public HelperCustomerServelet(DockMaker<Customer> pdfMaker) {
        this.pdfMaker = pdfMaker;
    }

    public void setPdfInResponse(List<Customer> customers, HttpServletResponse response, String headerForFile) throws IOException {
        String localPath = pdfMaker.generateDock(headerForFile, customers);

        try (
                FileInputStream fileInputStream = new FileInputStream(pdfMaker.getCustomPathForSave() + localPath);
                OutputStream os = response.getOutputStream()
        ) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=document.pdf");

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().write("Ошибка при скачивании файла.");
            response.getWriter().flush();
        }
    }

    public void setPdfInResponse(Customer customer, HttpServletResponse response, String headerForFile) throws IOException {
        setPdfInResponse(Collections.singletonList(customer), response, headerForFile);
    }

    public static void serErrorResponse(String text, String code, HttpServletResponse response) throws IOException {
        ErrorGetUser error = new ErrorGetUser(text, code);
        Gson gson = new Gson();
        String jsonDto = gson.toJson(error);
        response.getWriter().write(jsonDto);
        response.setStatus(400);
    }

    public static Customer parseCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            Customer customer = gson.fromJson(request.getReader(), Customer.class);
            return customer;
        } catch (Exception e) {
//            serErrorResponse("Parse error", "44444", response);
            return null;
        }
    }

    public static void setIncorrectUserDataResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String example = gson.toJson(new Customer("name", "lastName", "secondName", "HB1234567"));
        serErrorResponse("Incorrect format user for example: " + example, "4444444", response);
    }
}
