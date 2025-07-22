package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "index.jsp";
        try {
            String a = request.getParameter("action");
            System.out.println("Action " + a);
            if (a == null) {
                a = "home";
            }
            switch (a) {
                case "home":
                    url = "index.jsp";
                    break;
                case "loginpage":
                    url = "Login.jsp";
                    break;
                case "signuppage":
                    url = "Signup.jsp";
                    break;
                case "login":
                    url = "LoginController";
                    break;
                case "register":
                    url = "RegisterController";
                    break;
                case "admin":
                    url = "AdminDashBoard.jsp";
                    break;
                case "newbook":
                    url = "GetLastestBookController";
                    break;
                case "searchbook":
                    url = "SearchBookController";
                    break;
                case "viewdetail":
                    url = "ViewDetailBookController";
                    break;
                case "logout":
                    url = "LogoutController";
                    break;
                case "viewprofile":
                    url = "ViewProfile.jsp";
                    break;
                case "changeprofile":
                    url = "ChangeProfileController";
                    break;
                case "back to user":
                    url = "UserDashBoard.jsp";
                    break;
                case "view manage book":
                    url = "ViewAllBookController";
                    break;
                case "Borrow book":
                    url = "BorrowController";
                    break;
                case "edit book":
                    url = "EditBookController";
                    break;
                case "add book":
                    url = "AddBookController";
                    break;
                case "viewcart":
                    url = "ViewCart.jsp";
                    break;
                case "delete borrow":
                    url = "DeleteBorrowBookController";
                    break;
                case "Send borrow":
                    url = "SendBorrowRequestControler";
                    break;
                case "ViewRequestStatus":
                    url = "ViewRequestStatusController";
                    break;
                case "RemoveRequest":
                    url = "RemoveReuquestUserController";
                    break;
                case "View system config":
                    url = "ViewSystemConfigController";
                    break;
                case "Manage request":
                    url = "ViewUserRequestController";
                    break;
                case "Approve request":
                    request.setAttribute("action", a);
                    url = "AdminRequestActionController";
                    break;
                case "Reject request":
                    request.setAttribute("action", a);
                    url = "AdminRequestActionController";
                    break;
                case "Borrowed":
                    request.setAttribute("action", a);
                    url = "AdminRequestActionController";
                    break;
                case "Paid check":
                    url = "PaidCheckController";
                    break;
                case "Borrow record":
                    url = "ViewBorrowHistoryController";
                    break;
                case "View fine admin":
                    url = "ViewFineController";
                    break;
                case "Returned book":
                    url = "ReturnBookController";
                    break;
                case "manage inventory":
                    url = "ViewAllBooksForUpdateInventory";
                    break;
                case "updateinventory available book":
                    url = "UpdateInventory_AvailBook";
                    break;
                case "updateinventory new book":
                    url = "UpdateInventory_NewBook";
                    break;
                case "EditSystemConfig":
                    url = "EditSystemConfigController";
                    break;
                case "AddSystemConfig":
                    url = "AddSystemConfigController";
                    break;
                case "View_all_user": 
                    url="ViewUserAccountController";
                    break;
                case "Enable_user":
                    url="UserAccountManageController";
                    break;
                case "Disable_user":
                    url="UserAccountManageController";
                    break;
            }
        } finally {
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
