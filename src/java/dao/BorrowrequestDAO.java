/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import dto.BorrowRecord;
import dto.Request;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.Global.getDate;
import mylib.DBUtils;

/**
 *
 * @author Dan Huy
 */
public class BorrowrequestDAO {

    public int insertRequest(int id, ArrayList<Book> list) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                for (Book book : list) {
                    int bookid = book.getId();
                    String day = new Date(System.currentTimeMillis()).toString();
                    String sql = "insert book_requests values(?,?,?,?)";
                    PreparedStatement st = cn.prepareStatement(sql);
                    st.setInt(1, id);
                    st.setInt(2, bookid);
                    st.setString(3, day);
                    st.setString(4, "pending");
                    result = st.executeUpdate();
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

            }
        }
        return result;
    }
    public boolean runAutoRejectProcedure() {
        boolean result = false;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="{call sp_AutoRejectExpiredRequests}";
                CallableStatement st=cn.prepareCall(sql);
                st.execute();
                result=true;
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
    public ArrayList<Request> getRequestStatus(int id) {
        ArrayList<Request> list = new ArrayList<>();
        String sql = "SELECT br.id, br.user_id, s.name, br.book_id, b.title, br.request_date, br.status \n" +
            "                 FROM book_requests br \n" +
            "                 JOIN books b ON br.book_id = b.id \n" +
            "				 JOIN users s on s.id = br.user_id\n" +
            "                 WHERE br.user_id = ?";
                    Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Request rq = new Request();
                    rq.setId(rs.getInt("id"));
                    rq.setUserId(rs.getInt("user_id"));
                    rq.setUserName(rs.getString("name"));
                    rq.setBookId(rs.getInt("book_id"));
                    rq.setBookName(rs.getString("title"));
                    rq.setRequestDate(rs.getDate("request_date").toLocalDate());
                    rq.setStatus(rs.getString("status"));
                    list.add(rq);
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

    public ArrayList<BorrowRecord> getBorrowHistory(int userId) {
        ArrayList<BorrowRecord> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.getConnection();
            if(cn!=null) {
                String sql = "  SELECT br.*,u.name,b.title FROM borrow_records br\n" +
                "			JOIN users u ON br.user_id = u.id\n" +
                "			JOIN books b ON br.book_id = b.id\n" +
                "			WHERE br.user_id=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, userId);
                rs = st.executeQuery();
                while(rs.next()) {
                    Date returnDate = rs.getDate("return_date");
                    
                    BorrowRecord br = new BorrowRecord(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getDate("borrow_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate(),
                            returnDate != null? returnDate.toLocalDate() : null,
                            rs.getString("status")
                    
                    );
                    list.add(br);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null) rs.close();
                if(st!=null) st.close();
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
    
    public int removeRequest(int id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "DELETE FROM book_requests WHERE id = ? AND status = 'pending'";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
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

    public ArrayList<Request> getAllRequest() {
        ArrayList<Request> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "  SELECT r.id, r.request_date, r.status,\n"
                        + "               u.id AS userId, u.name AS userName,\n"
                        + "               b.id AS bookId, b.title AS bookTitle\n"
                        + "        FROM book_requests r\n"
                        + "        JOIN users u ON r.user_id = u.id\n"
                        + "        JOIN books b ON r.book_id = b.id\n"
                        + "        ORDER BY r.request_date DESC";
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt("id"));
                    r.setRequestDate(rs.getDate("request_date").toLocalDate());
                    r.setStatus(rs.getString("status"));
                    r.setUserId(rs.getInt("userId"));
                    r.setUserName(rs.getString("userName"));
                    r.setBookId(rs.getInt("bookId"));
                    r.setBookName(rs.getString("bookTitle"));
                    list.add(r);
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
                e.printStackTrace();
            }
        }

        return list;
    }

    public int updateStatus(int requestID, String status) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="UPDATE book_requests SET status = ? WHERE id = ?;";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setString(1, status);
                st.setInt(2, requestID);
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
    public int removeRequest2(int id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "DELETE FROM book_requests WHERE id = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
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
    
    public int updateRequestDate(int requestID, LocalDate time) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="UPDATE book_requests SET request_date = ? WHERE id = ?;";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setDate(1, Date.valueOf(time));
                st.setInt(2, requestID);
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
}
