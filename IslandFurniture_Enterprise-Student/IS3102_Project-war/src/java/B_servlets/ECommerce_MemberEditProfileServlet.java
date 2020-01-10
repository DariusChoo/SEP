/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author asus
 */
@WebServlet(name = "ECommerce_MemberEditProfileServlet", urlPatterns = {"/ECommerce_MemberEditProfileServlet"})
public class ECommerce_MemberEditProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Entered edit profile servlet");
            //PrintWriter whateveryouwant = response.getWriter();
            System.out.println("Testing parameter" + request.getParameter("email"));
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity").path("editMember")
                    .queryParam("name", request.getParameter("name"))
                    .queryParam("email", request.getParameter("email"))
                    .queryParam("phone", request.getParameter("phone"))
                    .queryParam("city", request.getParameter("country"))
                    .queryParam("address", request.getParameter("address"))
                    .queryParam("securityQuestion", request.getParameter("securityQuestion"))
                    .queryParam("securityAnswer", request.getParameter("securityAnswer"))
                    .queryParam("age", request.getParameter("age"))
                    .queryParam("income", request.getParameter("income"))
                    .queryParam("serviceLevelAgreement", request.getParameter("serviceLevelAgreement"))
                    .queryParam("password", request.getParameter("password"));
            System.out.println("Skipped webtarget");
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response res = invocationBuilder.put(Entity.entity("", "application/json"));
            System.out.println("edit status: " + res.getStatus());
            if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
                System.out.println("edit success");
            } else {
                System.out.println("edit fail");
            }
        } catch (Exception ex) {
            System.out.println("Edit exception" + ex);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
