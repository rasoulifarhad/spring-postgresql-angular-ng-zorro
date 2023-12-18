package com.farhad.example.ngzorro.person.config;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CorsFilter extends OncePerRequestFilter {
    private static final String DOT = "*";

    final private String[] allowedOrigins = new String[] { "*" };

    private final HttpMethod[] allowedMethods = { POST, GET, PUT, DELETE };
    private final String[] allowedHeaders = { AUTHORIZATION, CONTENT_TYPE, "x-requested-with" };

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        final HttpServletResponse response = res;

        String method = req.getMethod();
        String origin = req.getHeader(ORIGIN);

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOriginsHeader(origin));
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, allowedMethods());
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowedHeaders());
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, TRUE.toString());

        if (OPTIONS.name()
                .equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(req, res);
        }

    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        log.info("CORS allowedOrigins: {}", Arrays.asList(allowedOrigins));
        log.info("CORS allowedMethods {}", allowedMethods());
        log.info("CORS allowedHeaders {}", allowedHeaders());
    }

    private String allowedHeaders() {
        return String.join(",", allowedHeaders);
    }

    private String allowedMethods() {
        return Stream.of(allowedMethods)
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    private String allowedOriginsHeader(String origin) {
        if (origin == null || allowedAnyOrigin()) {
            return DOT;
        }

        return Stream.of(allowedOrigins)
                .filter(allowedOrigin -> allowedOrigin.equals(origin))
                .collect(Collectors.joining());
    }

    private boolean allowedAnyOrigin() {
        return Arrays.asList(allowedOrigins)
                .contains(DOT);
    }

}
