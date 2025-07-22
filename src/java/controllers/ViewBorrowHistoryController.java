

package controllers;

import dao.BorrowrecordDAO;
import dao.FineDAO;
import dto.BorrowRecord;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;


public class ViewBorrowHistoryController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            BorrowrecordDAO d=new BorrowrecordDAO();
            FineDAO finedao=new FineDAO();
            finedao.AutoGenerateFine();
            ArrayList<BorrowRecord> list=d.getBorrowHistory();
            if(!list.isEmpty() && list!=null){
                request.setAttribute("RecordList", list);
                request.getRequestDispatcher("BorrowHistory.jsp").forward(request, response);
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
    }// </editor-fold>

}
