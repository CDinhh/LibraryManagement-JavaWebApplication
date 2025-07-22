
package controllers;

import dto.Book;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


public class DeleteBorrowBookController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            int id=Integer.parseInt(request.getParameter("txtid"));
            if(id>=0){
                HttpSession s=request.getSession();
                ArrayList<Book> list=(ArrayList<Book>)s.getAttribute("BorrowList");
                for(Book book:list){
                    if(book.getId()==id){
                        list.remove(book);
                        break;
                    }
                }
                s.setAttribute("BorrowList", list);
                request.setAttribute("DELETESTATUS", "Delete completed!");
                request.getRequestDispatcher("ViewCart.jsp").forward(request, response);
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
    }

}
