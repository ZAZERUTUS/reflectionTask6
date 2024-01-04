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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (((HttpServletRequest)request).getHeader("customToken") != null) {
            chain.doFilter(request, response);
        } else {
            response.getWriter().write("Unauthorized");
            ((HttpServletResponse) response).setStatus(401);
        }
    }
}
