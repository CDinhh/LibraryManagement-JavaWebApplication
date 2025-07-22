/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookDAO;
import dto.Book;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ViewDetailBookController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            String idRaw = request.getParameter("id");
            if(idRaw!=null && !idRaw.isEmpty()) {
                int id=Integer.parseInt(request.getParameter("id"));           
                BookDAO d=new BookDAO();
                Book b=d.getBookById(id);
                if(b!=null){
                    request.setAttribute("book", b);
                    request.getRequestDispatcher("ViewBookDetail.jsp").forward(request, response);
                }else{
                    System.out.println("Khong view duoc sach");
                    response.sendRedirect("index.jsp");
                }
            } else {
                System.out.println("Loi khong lay duoc parameter id sach");
                response.sendRedirect("index.jsp");
            }
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Loi khong view duoc sach");
            response.sendRedirect("index.jsp");
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
