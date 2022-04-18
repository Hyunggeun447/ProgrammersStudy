package com.kdt.progmrs.kdt.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
//@WebServlet(value = "/*", loadOnStartup = 1)  //web.xml 대신 연결 가능
public class TestServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("init Servlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.info("Got Request from {}", requestURI);

        PrintWriter writer = response.getWriter();
        writer.println("Hello Servlet");

    }
}
