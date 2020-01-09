/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Furniture;
import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author asus
 */
@WebServlet(name = "ECommerce_GetMember", urlPatterns = {"/ECommerce_GetMember"})
public class ECommerce_GetMember extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        /*Get email input from user*/
        String email = (String) session.getAttribute("memberEmail");

        
        //Client code for consuming webservice will be written here
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity").path("getMember")
                .queryParam("email", email);
        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response res = invocationBuilder.get();
        
        System.out.println("status: " + response.getStatus());
        
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
            Member mem = (Member) res.readEntity(Member.class);
            
//            out.println("Member's cumulative spending: "+mem.getCumulativeSpending());
//            out.println("Member's email: "+mem.getEmail());
//            out.println("Member's loyalty points: "+mem.getLoyaltyPoints());
//            out.println("Member's address: "+mem.getAddress());
//            out.println("Member's age: "+mem.getAge());
//            out.println("Member's city: "+mem.getCity());
//            out.println("Member's income: "+mem.getIncome());
//            out.println("Member's name: "+mem.getName());
//            out.println("Member's phone: "+mem.getPhone());

            session.setAttribute("member",mem);
            System.out.println("Member attribute set");
            
            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp");
            out.println();
        } else {
            System.out.println("failed");
        }
                
//        if (res.getStatus() == 200) {
//            Member memdata = res.readEntity(Member.class);
//            
//            request.setAttribute("user", memdata.);
//            RequestDispatcher rd = request.getRequestDispatcher("memberProfile.jsp");
//            rd.forward(request, response);
//            
//        } else {
//            out.println("No such User, Please check again!");
//        }
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

/*
Just for reference

public class ECommerce_FurnitureCategoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            String category = URLDecoder.decode(request.getParameter("cat"));
            Long countryID = (Long) session.getAttribute("countryID");

            List<Furniture> furniture = retrieveFurnitureByCategoryRESTful(countryID, category);
            session.setAttribute("furnitures", furniture);
            String categoryEncoded = URLEncoder.encode(category);

            if (furniture == null) {
                response.sendRedirect("/IS3102_Project-war/B/SG/furnitureCategory.jsp?cat=" + categoryEncoded + "&errorMsg=No furniture to be displayed.");
                return;
            }
            response.sendRedirect("/IS3102_Project-war/B/SG/furnitureCategory.jsp?cat=" + categoryEncoded);
        } catch (Exception ex) {
            out.println("\n\n " + ex.getMessage());
        }
    }

    public List<Furniture> retrieveFurnitureByCategoryRESTful(Long countryID, String category) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.furnitureentity").path("getFurnitureListByCategory")
                .queryParam("countryID", countryID)
                .queryParam("category", category);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();
        System.out.println("status: " + response.getStatus());

        if (response.getStatus() != 200) {
            return null;
        }

        List<Furniture> list = response.readEntity(new GenericType<List<Furniture>>() {
        });
        System.out.println("list size: " + list.size());
        return list;
    }

    */