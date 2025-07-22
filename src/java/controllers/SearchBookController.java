
package controllers;

import dao.BookDAO;
import dto.Book;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


public class SearchBookController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            HttpSession session=request.getSession();
            String search=request.getParameter("txtsearch");
            if(search!=null && !search.isEmpty()){               
                BookDAO d=new BookDAO();
                ArrayList<Book> list=d.searchBook(search);
                if(list.isEmpty() || list==null){
                    request.setAttribute("SEARCHERROR", "Book not found!");
                    if(session.getAttribute("User")!=null){
                        request.getRequestDispatcher("UserDashBoard.jsp").forward(request, response);
                    }else{
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }                   
                }else if(session.getAttribute("User")!=null){
                    request.setAttribute("SEARCHRESULT", list);
                    request.getRequestDispatcher("UserDashBoard.jsp").forward(request, response);
                }else{
                    request.setAttribute("SEARCHRESULT", list);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }else{
                request.getRequestDispatcher("GetLastestBookController").forward(request, response);
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
