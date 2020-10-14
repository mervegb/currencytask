package com.veniture.plugins.tutorial.servlet;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Scanned
public class CurrencyServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CurrencyServlet.class);

    private static final String CurrencyTemplate = "/templates/currency.vm";

    @JiraImport
    private final TemplateRenderer templateRenderer;


    public CurrencyServlet(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }


     private String GET_URL="http://localhost:8080/rest/myrestresource/1.0/message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            StringBuffer responseStringBuffer = new StringBuffer();

             while ((inputLine = in.readLine()) != null) {
                responseStringBuffer.append(inputLine);
            }

             in.close();

            Map<String, Object> context = new HashMap<>();
            context.put("lira",responseStringBuffer);

            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            templateRenderer.render(CurrencyTemplate,context,response.getWriter());



    }


}
