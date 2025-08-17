//package com.raksa.app.config.filters;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class TraceIdFilter implements Filter {
//
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
//        try {
//            HttpServletRequest httpReq = (HttpServletRequest) request;
//            String traceId = httpReq.getHeader("X-Trace-Id");
//            if (traceId == null || traceId.isEmpty()) {
//                traceId = UUID.randomUUID().toString();
//            }
//            MDC.put("traceId", traceId);
//
//            chain.doFilter(request, response);
//        } finally {
//            MDC.clear();
//        }
//    }
//}
//
