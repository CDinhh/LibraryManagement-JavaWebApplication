/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BorrowrequestDAO;
import dao.UserDAO;
import dto.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;

public class LoginController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String email = request.getParameter("txtemail");
            String rawpass = request.getParameter("txtpassword");
            String password =  Base64.getEncoder().encodeToString(rawpass.getBytes());
            System.out.println(password);
            if (email != null && password != null) {
                UserDAO d = new UserDAO();
                User us = d.getUser(email.trim(), password.trim());               
                if (us != null) {
                    if(us.getStatus().equalsIgnoreCase("blocked")){
                        request.setAttribute("LOGINERROR", "Your account have been blocked!");
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                        return;
                    }
                    HttpSession session=request.getSession();
                    session.setAttribute("User", us);
                    if(us.getRole().equalsIgnoreCase("admin")){
                        BorrowrequestDAO d1 = new BorrowrequestDAO();
                        boolean result = d1.runAutoRejectProcedure();
                        if (result) { 
                            System.out.println("Chay AutoRejectProcedure");
                        }else{
                            System.out.println("Khong chay duoc AutoRejectProcedure");
                        }
                        request.getRequestDispatcher("AdminDashBoard.jsp").forward(request, response);
                    }else{
                        request.getRequestDispatcher("UserDashBoard.jsp").forward(request, response);
                    }
                } else {
                        request.setAttribute("LOGINERROR", "Email or password is invalid !");
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            }else{
                request.setAttribute("LOGINERROR", "Mail or password cant be empty");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }         
        }catch(Exception e){
            e.printStackTrace();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
