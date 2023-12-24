package org.example.servelet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.pojo.Customer;
import org.example.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServCrudCustomerTest {

    @Mock
    private ServiceImpl service;

    @Mock
    private HelperCustomerServelet helperCustomerServelet;

    @InjectMocks
    private ServCrudCustomer servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест делал просто для освежения памяти по тестам с моками
     * В одном тесте допустима проверка взаимосвзяанных условий по этому и был сделан 1 тест вместо нескольких более дробных
     * @throws ServletException
     * @throws IOException
     */
    @Test
    void testDoGet() throws ServletException, IOException {
        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        Customer customer = new Customer(1, "name", "lastName", "secondName", "HB1234567");
        when(service.getById(1)).thenReturn(customer);

        //When
        servlet.doGet(request, response);

        //Then
        verify(service).getById(1);
        verify(helperCustomerServelet).setPdfInResponse(customer, response, "Get customer by id");
    }
}
