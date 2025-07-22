/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.BookDAO;
import dao.BorrowrecordDAO;
import dao.BorrowrequestDAO;
import dao.SystemConfigDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author Dan Huy
 */
public class AdminRequestActionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String action = (String) request.getAttribute("action");
            int requestId = Integer.parseInt(request.getParameter("requestID"));
            int bookId = Integer.parseInt(request.getParameter("bookID"));
            int userId = Integer.parseInt(request.getParameter("userID"));
            BookDAO bookDao = new BookDAO();
            BorrowrequestDAO requestDAO = new BorrowrequestDAO();
            BorrowrecordDAO recordDAO = new BorrowrecordDAO();
            SystemConfigDAO configDAO = new SystemConfigDAO();
            int success = 0;
            switch (action) {
                case "Approve request":
                    success = requestDAO.updateStatus(requestId, "approved");
                    if (success > 0) {
                        bookDao.decreaseAvailableCopies(bookId);
                    }
                    int r=requestDAO.updateRequestDate(requestId, LocalDate.now());
                    if(r==0){
                        System.out.println("Loi: khong the chinh request_date thanh ngay hom nay");
                    }
                    break;
                case "Reject request":
                    success = requestDAO.updateStatus(requestId, "rejected");
                    break;
                case "Borrowed":
                    double borrowdays = configDAO.getValueByKey("default_borrow_duration_days");
                    LocalDate now = LocalDate.now();
                    LocalDate dueDate = now.plusDays((long) borrowdays);
                    success = recordDAO.insertBorrowRecord(userId, bookId, now, dueDate);
                    if (success>0) {
                        int result=requestDAO.removeRequest2(requestId);
                    }
                    break;
            }
            if (success >= 0) {
                request.setAttribute("Msg", "Action successful: " + action);
            } else {
                request.setAttribute("Msg", "Action failed");
            }
            request.getRequestDispatcher("ViewUserRequestController").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
