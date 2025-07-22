

package controllers;

import dao.BorrowrequestDAO;
import dto.Book;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


public class SendBorrowRequestControler extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            HttpSession s=request.getSession();
            User us=(User)s.getAttribute("User");
            ArrayList<Book> list=(ArrayList<Book>)s.getAttribute("BorrowList");
            if(us!=null && list!=null){
                BorrowrequestDAO d=new BorrowrequestDAO();
                int id=us.getId();
                int result=d.insertRequest(id, list);
                if(result>=1){
                    s.removeAttribute("BorrowList");
                    request.setAttribute("BorrowComplete", "Your request have been send");
                    request.getRequestDispatcher("UserDashBoard.jsp").forward(request, response);
                }else{
                    System.out.println("Loi insert request");
                }               
            }else{
                System.out.println("Loi khong lay duoc data o send book request");
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
