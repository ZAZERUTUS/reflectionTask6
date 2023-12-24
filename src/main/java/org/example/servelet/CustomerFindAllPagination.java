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
import java.util.List;

import static org.example.config.ConfigHandler.getInstanceConfig;
import static org.example.printer.PrinterFactory.createDockMakerByType;
import static org.example.servelet.HelperCustomerServelet.serErrorResponse;

@WebServlet(name = "PagingAllCustomer", value = "/customers")
public class CustomerFindAllPagination extends HttpServlet {

    ServiceImpl service = new ServiceImpl();
    HelperCustomerServelet helperCustomerServelet;
    DockMaker<Customer> pdfMaker;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        pdfMaker = createDockMakerByType(TypePrinter.PDF);
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
