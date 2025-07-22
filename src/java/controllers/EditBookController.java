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
import java.sql.Connection;
import java.sql.PreparedStatement;
import mylib.DBUtils;


public class EditBookController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String statusMessage = null;
        try {
            String idRaw = request.getParameter("id");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String isbn = request.getParameter("isbn");
            String category = request.getParameter("category");
            String yearRaw = request.getParameter("published_year");
            String totalRaw = request.getParameter("total_copies");
            String availableRaw = request.getParameter("available_copies");
            String status = request.getParameter("status");
            String url = request.getParameter("url");


            if (idRaw == null || title == null || author == null || isbn == null || category == null ||
                yearRaw == null || totalRaw == null || availableRaw == null || status == null ||
                idRaw.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty() ||
                yearRaw.isEmpty() || totalRaw.isEmpty() || availableRaw.isEmpty() || status.isEmpty()) {

                request.setAttribute("SETBOOKSTATUS", "Missing required fields!");
                request.getRequestDispatcher("ViewAllBookController").forward(request, response);
                return;
            }

            int id = Integer.parseInt(idRaw);
            int published_year = Integer.parseInt(yearRaw);
            int total_copies = Integer.parseInt(totalRaw);
            int available_copies = Integer.parseInt(availableRaw);

            
            BookDAO d = new BookDAO();
            int row = d.updateBook(id, title, author, isbn, category, published_year, total_copies, available_copies, status, url);

            if (row == 0) {
                statusMessage = "Failed to update!";
                System.out.println("Cap nhap sach that bai");
            } else {
                statusMessage = "Update successfully!";
                System.out.println("Cap nhap sach thanh cong");
            }

        } catch (NumberFormatException e) {
            statusMessage = "Invalid number format!";
        } catch (Exception e) {
            statusMessage = "Error: " + e.getMessage();
        } finally {
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
