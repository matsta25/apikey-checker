package com.matsta25.apikeychecker.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    private static final String API_KEY_HEADER_NAME = "X-API-Key";
    private static final String API_KEY_PARAM = "api_key";

    @Value("${apikey}")
    private String API_KEY_VALUE;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestUri = request.getRequestURI();

        LOGGER.info("Request method {} - URI : {}", request.getMethod(), requestUri);

        if (!verifyApiKey(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "API key is invalid!");
            LOGGER.error("Used API key which is invalid!");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean verifyApiKey(HttpServletRequest request) {
        return API_KEY_VALUE.equals(request.getHeader(API_KEY_HEADER_NAME))
                || API_KEY_VALUE.equals(request.getParameter(API_KEY_PARAM));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
