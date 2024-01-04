package org.example.servelet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.ApplicationContextLocal;
import org.example.dao.pojo.Customer;
import org.example.printer.DockMaker;
import org.example.printer.impl.PdfMakerClevertec;
import org.example.service.DTOService;
import org.example.service.ServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static org.example.config.ConfigHandler.getInstanceConfig;
import static org.example.servelet.HelperCustomerServelet.serErrorResponse;

@Component
@WebServlet(name = "PagingAllCustomer", value = "/customers")
public class CustomerFindAllPagination extends HttpServlet {
    ApplicationContext applicationContext;

    DTOService<Customer> service;
    HelperCustomerServelet helperCustomerServelet;
    DockMaker<Customer> pdfMaker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        applicationContext = ApplicationContextLocal.getApplicationContext();
        service = applicationContext.getBean(ServiceImpl.class);

        pdfMaker = applicationContext.getBean(PdfMakerClevertec.class);
        pdfMaker.setCustomPathForSave("/");
        helperCustomerServelet = new HelperCustomerServelet(pdfMaker);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int limit = getInstanceConfig().getConfig().getPageSize();
        int page = 1;

        try {
            limit = request.getParameter("limit") != null
                    ? Integer.parseInt(request.getParameter("limit"))
                    : getInstanceConfig().getConfig().getPageSize();

            page = request.getParameter("page") != null
                    ? Integer.parseInt(request.getParameter("page"))
                    : 1;

        } catch (NumberFormatException e) {
            serErrorResponse("Parameters limit and page mast be int.", "2222222", response);
        }


        List<Customer> customers = service.getAll(limit, page);

        helperCustomerServelet.setPdfInResponse(customers, response, "Get customer by id");
    }

}
