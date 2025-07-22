/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AddBookController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String statusMessage = null;
        try {
            String title = request.getParameter("addtitle");
            String author = request.getParameter("addauthor");
            String isbn = request.getParameter("addisbn");
            String category = request.getParameter("addcategory");
            String yearRaw = request.getParameter("addpublished_year");
            String totalRaw = request.getParameter("addtotal_copies");
            String availableRaw = request.getParameter("addavailable_copies");
            String status = request.getParameter("addstatus");
            String url = request.getParameter("addurl");


            if (title == null || author == null || isbn == null || category == null ||
                yearRaw == null || totalRaw == null || availableRaw == null || status == null ||
                title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty() ||
                yearRaw.isEmpty() || totalRaw.isEmpty() || availableRaw.isEmpty() || status.isEmpty()) {
                
                System.out.println("==== [Debug: Book Form Inputs] ====");
                System.out.println("title: " + title);
                System.out.println("author: " + author);
                System.out.println("isbn: " + isbn);
                System.out.println("category: " + category);
                System.out.println("yearRaw: " + yearRaw);
                System.out.println("totalRaw: " + totalRaw);
                System.out.println("availableRaw: " + availableRaw);
                System.out.println("status: " + status);
                System.out.println("====================================");

                
                request.setAttribute("SETBOOKSTATUS", "Missing required fields!");
                request.getRequestDispatcher("ViewAllBookController").forward(request, response);
                return;
            }


            int published_year = Integer.parseInt(yearRaw);
            int total_copies = Integer.parseInt(totalRaw);
            int available_copies = Integer.parseInt(availableRaw);
            
            BookDAO d =new BookDAO();
            int row = d.inSertBook(title, author, isbn, category, published_year, total_copies, available_copies, status, url);
            if (row == 0) {
                statusMessage = "Failed to add!";
                System.out.println("Them book that bai");
            } else {
                statusMessage = "Add book successfully!";
                System.out.println("Them sach thanh cong");
            } 
        } catch (NumberFormatException e) {
            statusMessage = "Invalid number format!";                    
        } catch (Exception e) {
            statusMessage = "Error: " + e.getMessage();
        }
        finally {
            request.setAttribute("SETBOOKSTATUS", statusMessage);
            request.getRequestDispatcher("ViewAllBookController").forward(request, response);
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
