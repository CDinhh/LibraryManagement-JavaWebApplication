/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Fine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Dan Huy
 */
public class FineDAO {

    public int paidFine(int fineId) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE fines SET paid_status = 'paid' WHERE id = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, fineId);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public int insertFine(int borrowId, double fineAmount) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO fines (borrow_id, fine_amount, paid_status) VALUES (?, ?, 'unpaid')";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, borrowId);
                st.setDouble(2, fineAmount);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public ArrayList<Fine> getAllFine() {
        ArrayList<Fine> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select f.*, u.name as user_name\n"
                        + "from [dbo].[fines] f\n"
                        + "join [dbo].[borrow_records] br on f.borrow_id=br.id\n"
                        + "join [dbo].[users] u on br.user_id =u.id ";
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet tb = st.executeQuery();
                while (tb.next()) {
                    Fine f = new Fine();
                    f.setId(tb.getInt("id"));
                    f.setUser_name(tb.getString("user_name"));
                    f.setBorrow_id(tb.getInt("borrow_id"));
                    f.setFine_amount(tb.getDouble("fine_amount"));
                    f.setPaid_status(tb.getString("paid_status"));
                    list.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {

            }
        }

        return list;
    }

    public int checkId(int borrow_id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="select * from [dbo].[fines] where [borrow_id]=?";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setInt(1, borrow_id);
                ResultSet tb=st.executeQuery();
                if(tb.next()){
                    result=1;
                }else{
                    result=0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
    public int updateFine(int borrow_id, double totalFine) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="update [dbo].[fines] set [fine_amount]=? where borrow_id=?";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setDouble(1, totalFine);
                st.setInt(2, borrow_id);
                result=st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
    
    public void AutoGenerateFine() {
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "  SELECT br.id AS borrow_id, br.due_date, br.status, br.return_date,\n"
                        + "               b.id AS book_id, f.paid_status\n"
                        + "        FROM borrow_records br\n"
                        + "        JOIN books b ON br.book_id = b.id\n"
                        + "        LEFT JOIN fines f ON br.id = f.borrow_id\n"
                        + "        WHERE br.return_date IS NULL\n"
                        + "          AND GETDATE() > br.due_date";
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet tb = st.executeQuery();
                SystemConfigDAO systemConfig = new SystemConfigDAO();
                BorrowrecordDAO borrowRecord = new BorrowrecordDAO();
                double finePerDay = systemConfig.getValueByKey("overdue_fine_per_day");
                while (tb.next()) {
                    int borrowID = tb.getInt("borrow_id");
                    LocalDate dueDate = tb.getDate("due_date").toLocalDate();
                    String currentStatus = tb.getString("status");
                    String paidStatus=tb.getString("paid_status");
                    long dayLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                    double totalFine=dayLate*finePerDay;
                    if (checkId(borrowID)==1 && paidStatus.equalsIgnoreCase("unpaid")){
                        updateFine(borrowID, totalFine);
                    }else if(checkId(borrowID)==0){
                        insertFine(borrowID, totalFine);
                    }
                    if (!currentStatus.equalsIgnoreCase("overdue")) {
                        int result=borrowRecord.overDueBook(borrowID);
                        if(result==0){
                            System.out.println("Loi khong doi duoc thanh overdue");
                        }
                    }
                }
                cn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();            
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
