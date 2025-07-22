
package controllers;

import dao.BorrowrequestDAO;
import dto.BorrowRecord;
import dto.Request;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


public class ViewRequestStatusController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession s = request.getSession();
            User us = (User) s.getAttribute("User");
            int id = us.getId();
            if (id >= 0) {
                BorrowrequestDAO d = new BorrowrequestDAO();
                ArrayList<Request> rlist = d.getRequestStatus(id);
                ArrayList<BorrowRecord> blist = d.getBorrowHistory(id);
                //rlist co blist k co
                if ((rlist != null && !rlist.isEmpty()) && (blist == null || blist.isEmpty())) {
                    request.setAttribute("RequestStatus", rlist);
                    request.getRequestDispatcher("ViewRequestStatus.jsp").forward(request, response);
                //rlist k co blist co
                } else if ((rlist == null || rlist.isEmpty()) && (blist != null && !blist.isEmpty())) {
                    request.setAttribute("BorrowedHistory", blist);
                    request.getRequestDispatcher("ViewRequestStatus.jsp").forward(request, response);
                //ca 2 deu co
                } else if ((rlist != null && !rlist.isEmpty()) && (blist != null && !blist.isEmpty())) {
                    request.setAttribute("RequestStatus", rlist);
                    request.setAttribute("BorrowedHistory", blist);
                    request.getRequestDispatcher("ViewRequestStatus.jsp").forward(request, response);
                } else {
                    System.out.println("User khong co yeu cau va lich su muon");
                    request.getRequestDispatcher("ViewRequestStatus.jsp").forward(request, response);
                }
            } else {
                System.out.println("User no exist");
            }
        } catch (Exception e) {
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
