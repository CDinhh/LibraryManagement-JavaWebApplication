
package controllers;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;


public class ChangeProfileController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            String name=request.getParameter("txtname");
            String raw=request.getParameter("txtpassword");
            String password =  Base64.getEncoder().encodeToString(raw.getBytes());
            HttpSession s=request.getSession();
            User us=(User)s.getAttribute("User");
            int id=us.getId();
            UserDAO d=new UserDAO();
            int result=d.UpdateUser(id, name, password);
            if(result>=1){
                us=d.getUserById(id);
                s.setAttribute("User", us);
                request.setAttribute("UPDATESTATUS", "Update successfull!");
                request.getRequestDispatcher("ViewProfile.jsp").forward(request, response);
            }else{
                request.setAttribute("UPDATESTATUS", "Update failed!");
                request.getRequestDispatcher("ViewProfile.jsp").forward(request, response);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
