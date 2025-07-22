/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookDAO;
import dto.Book;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;


public class ViewAllBooksForUpdateInventory extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        BookDAO d = new BookDAO();
        ArrayList<Book> allBookList = d.getAllBook();
        if(allBookList!= null && !allBookList.isEmpty()) {
            request.setAttribute("ALLBOOKLIST", allBookList);
            request.getRequestDispatcher("ManageInventory.jsp").forward(request, response);
        } else {
            System.out.println("Khong lay duoc danh sach book");
            request.getRequestDispatcher("AdminDashBoard.jsp").forward(request, response);
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
