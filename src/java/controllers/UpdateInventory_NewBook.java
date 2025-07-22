

package controllers;

import dao.BookDAO;
import dao.InventoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UpdateInventory_NewBook extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String UPDATEINVENSTATUS = "";
       String bookName = request.getParameter("newBookName");
       String bookAuthor = request.getParameter("newBookAuthor");
       String ISBN = request.getParameter("newBookISBN");
       String totalRaw = request.getParameter("quantity");
       String des = request.getParameter("description");
       
       if(bookName != null && !bookName.isEmpty() &&
            bookAuthor != null && !bookAuthor.isEmpty() && 
               ISBN != null && !ISBN.isEmpty() &&
                    totalRaw != null && !totalRaw.isEmpty() &&
                            des != null && !des.isEmpty()) {
           
           try {
               int total = Integer.parseInt(totalRaw);
               String Cate = request.getParameter("newBookCate");
               String yearRaw = request.getParameter("newBookYear");//co the null => dung Integer
               Integer Year = (yearRaw != null && !yearRaw.isEmpty()) ? Integer.parseInt(yearRaw) : null; //khong null roi moi parse
               BookDAO bookd = new BookDAO();
               
               int bookIdResult = bookd.insertBookNullableReturnBookID(bookName, bookAuthor, ISBN, Cate, Year, total, total, null, null);
               
               if(bookIdResult > 0) {
                   InventoryDAO inventDao = new InventoryDAO();
                   int insertLogResult = inventDao.insertLogs(bookIdResult, total, des);
                   if (insertLogResult > 0) {
                       UPDATEINVENSTATUS = "Import Book succesfully !";
                   } else {
                       UPDATEINVENSTATUS = "Failed to insert Log (inserted Book!)";
                   } 
               } else {
                   UPDATEINVENSTATUS = "Imported Failed !";
               }
               
               
           } catch (NumberFormatException e) {
               UPDATEINVENSTATUS = "Invalid number format!";
           }
       } else {
           UPDATEINVENSTATUS = "Please fill in the required blank!";
       }
       request.setAttribute("UPDATEINVENSTATUS", UPDATEINVENSTATUS);
       request.getRequestDispatcher("ViewAllBooksForUpdateInventory").forward(request, response);
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
