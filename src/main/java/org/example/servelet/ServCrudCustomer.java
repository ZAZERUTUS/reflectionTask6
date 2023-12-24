package org.example.servelet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.pojo.Customer;
import org.example.printer.DockMaker;
import org.example.printer.TypePrinter;
import org.example.service.ServiceImpl;
import java.io.IOException;

import static org.example.printer.PrinterFactory.createDockMakerByType;
import static org.example.servelet.HelperCustomerServelet.parseCustomer;
import static org.example.servelet.HelperCustomerServelet.serErrorResponse;
import static org.example.servelet.HelperCustomerServelet.setIncorrectUserDataResponse;


@WebServlet(name = "CrudCustomer", value = "/customer")
public class ServCrudCustomer extends HttpServlet {
    ServiceImpl service = new ServiceImpl();
    DockMaker<Customer> pdfMaker;

    HelperCustomerServelet helperCustomerServelet;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        pdfMaker = createDockMakerByType(TypePrinter.PDF);
        pdfMaker.setCustomPathForSave("/");
        helperCustomerServelet = new HelperCustomerServelet(pdfMaker);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (id == null) {
            serErrorResponse("Please send \"id\" user for get in path(URL)", "2333333", response);
            return;
        }

        Customer customer = service.getById(Integer.parseInt(id));
        if (customer == null) {
            serErrorResponse("User not found", "2222222", response);
        } else {
            helperCustomerServelet.setPdfInResponse(customer, response, "Get customer by id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = parseCustomer(request, response);

        try {
            if (customer != null) {
                int id = service.save(customer);
                response.setStatus(201);
                helperCustomerServelet.setPdfInResponse(service.getById(id), response, "Add customer");
            } else {
                setIncorrectUserDataResponse(response);
            }
        } catch (Exception e) {
            setIncorrectUserDataResponse(response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null) {
            serErrorResponse("Please send \"id\" user for delete in path(URL)", "44444211", response);
            return;
        }
        Customer customer = service.getById(Integer.parseInt(id));

        if (customer != null) {
            service.delete(Integer.parseInt(id));
            helperCustomerServelet.setPdfInResponse(customer, response, "Delete customer");
        } else {
            serErrorResponse("Not found user with id - " + id, "1111111", response);

        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = parseCustomer(request, response);

        try {
            if (customer != null) {
                int id = service.update(customer);
                helperCustomerServelet.setPdfInResponse(service.getById(id), response, "Update customer");
            }
        } catch (Exception e) {
            setIncorrectUserDataResponse(response);
        }
    }
}
