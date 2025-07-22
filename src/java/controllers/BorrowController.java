

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
import java.util.List;


public class BorrowController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            HttpSession s=request.getSession();
            String id=request.getParameter("txtid").trim();
                  
           if(id!=null) {
               BookDAO d = new BookDAO();
               Book b = d.getBookById(Integer.parseInt(id)); 
               if(b!=null) {
                   ArrayList<Book> list =(ArrayList<Book>) s.getAttribute("BorrowList"); //check session coi co list borrow chua
                   if(list == null) list = new ArrayList<>(); //chua co thi tao moi
                   
                   boolean found = false;                    
                   for(Book book : list) {
                       if(book.getId()==b.getId()) { //ktra quyen sach vua bam borrow co trong borrow list chua
                           found = true; 
                           break; 
                       }
                   }
                   
                   if (found) { //co roi thi ko dc muon nua
                        request.setAttribute("BORROWERROR", "You can't borrow the same book twice!");
                   } else { //chua co thi them moi vao list, day len session
                        list.add(b);
                        s.setAttribute("BorrowList", list);
                   }
               }
           }
           //forward qua search de giu lai danh sach book
           request.getRequestDispatcher("SearchBookController").forward(request, response);

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
