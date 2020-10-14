package com.veniture.plugins.tutorial.rest;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Path("/message")
public class MyRestResource  {


    /*
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage() throws IOException {
        return Response.ok(new MyRestResourceModel(sendGET())).build();
    }
*/

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://free.currconv.com/api/v7/convert?q=USD_TRY&compact=ultra&apiKey=30c96948cd0c8057c051";

    @GET
    @AnonymousAllowed
    public String sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return ".";
        }
    }

}
