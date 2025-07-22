
package controllers;

import dao.BookDAO;
import dao.InventoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UpdateInventory_AvailBook extends HttpServlet {
   

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String UPDATEINVENSTATUS = "";

        String bookIdRaw = request.getParameter("bookId");
        String quantityRaw = request.getParameter("quantity");
        String des = request.getParameter("description");
        
        if (bookIdRaw != null && !bookIdRaw.trim().isEmpty()
                && quantityRaw != null && !quantityRaw.trim().isEmpty()
                && des != null && !des.isEmpty()) {
            try {
                int bookId = Integer.parseInt(bookIdRaw.trim());
                int total = Integer.parseInt(quantityRaw.trim());
                

                InventoryDAO invendao = new InventoryDAO();
                int insertRow = invendao.insertLogs(bookId, total, des);

                if (insertRow > 0) {
                    BookDAO bookdao = new BookDAO();
                    int updateRow = bookdao.updateTotalAvaiCopies(bookId, total);

                    if (updateRow > 0) {
                        UPDATEINVENSTATUS = "Imported successfully!";
                    } else {
                        UPDATEINVENSTATUS = "Update available copies failed!";
                    }
                } else {
                    UPDATEINVENSTATUS = "Inventory log insert failed!";
                }

            } catch (NumberFormatException e) {
                UPDATEINVENSTATUS = "Invalid number format!";
            }
        } else {
            UPDATEINVENSTATUS = "Missing input data!";
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
