package org.example.servelet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/customers"})
public class FilterAuthExample implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("3r4t56787653424418493682374ygbferhjf");    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (((HttpServletRequest)request).getHeader("customToken") != null) {
            // Если пользователь авторизован, пропускаем запрос дальше по цепочке фильтров и сервлетов
            chain.doFilter(request, response);
            System.out.println("dqwdqwdfrrefsrepoeiqgkqdwwfeer435t34535i");
        } else {
            // Если пользователь не авторизован, отправляем ошибку 401 (Unauthorized)
            response.getWriter().write("Unauthorized");
            ((HttpServletResponse) response).setStatus(401);
            System.out.println("dqwdqwdfrrefsrepoeiqgkqi");
        }
    }
}
