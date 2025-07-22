package dao;

import dto.BorrowRecord;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import mylib.DBUtils;

public class BorrowrecordDAO {

    public int insertBorrowRecord(int userId, int bookId, LocalDate borrowDate, LocalDate dueDate) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO borrow_records(user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, 'borrowed')";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, userId);
                st.setInt(2, bookId);
                st.setDate(3, Date.valueOf(borrowDate));
                st.setDate(4, Date.valueOf(dueDate));
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

    public int returnBook(int borrowId, int bookId) {
        String sql = "update borrow_records set status = 'returned', return_date = ? where id = ?";
        int result = 0;
        LocalDate currentDate = LocalDate.now();
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                st = cn.prepareStatement(sql);
                st.setDate(1, Date.valueOf(currentDate));
                st.setInt(2, borrowId);
                result = st.executeUpdate();
                if (result > 0) {
                    BookDAO d = new BookDAO();
                    d.increaseAvailableCopies(bookId);
                    System.out.println("Da tra sach va tang available copy + 1");
                } else {
                    System.out.println("Tra sach that bai");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public int overDueBook(int borrow_id) {
        int reslut = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE borrow_records SET status = 'overdue' WHERE id = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, borrow_id);
                reslut = st.executeUpdate();
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
        return reslut;
    }

    public ArrayList<BorrowRecord> getBorrowHistory() {
        ArrayList<BorrowRecord> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet tb = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select br.*, u.name, b.title\n"
                        + "from [dbo].[borrow_records] br\n"
                        + "join [dbo].[users] u on br.user_id=u.id\n"
                        + "join [dbo].[books] b on br.book_id=b.id";
                st = cn.prepareStatement(sql);
                tb = st.executeQuery();
                while (tb.next()) {
                    BorrowRecord d = new BorrowRecord();
                    d.setBook_id(tb.getInt("book_id"));
                    d.setBook_name(tb.getString("title"));
                    d.setBorrow_date(tb.getDate("borrow_date").toLocalDate());
                    d.setDue_date(tb.getDate("due_date").toLocalDate());
                    d.setId(tb.getInt("id"));
                    if (tb.getDate("return_date") == null) {
                        d.setReturn_date(null);
                    } else {
                        d.setReturn_date(tb.getDate("return_date").toLocalDate());
                    }
                    d.setUser_id(tb.getInt("user_id"));
                    d.setUser_name(tb.getString("name"));
                    d.setStatus(tb.getString("status"));
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (tb != null) {
                    tb.close();
                }
                if (st != null) {
                    st.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public BorrowRecord getBorrowRecordById(int id) {
        BorrowRecord br = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet tb = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select * from [dbo].[borrow_records] where id=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, id);
                tb = st.executeQuery();
                while (tb.next()) {
                    br = new BorrowRecord();
                    br.setBook_id(tb.getInt("book_id"));
                    br.setBorrow_date(tb.getDate("borrow_date").toLocalDate());
                    br.setDue_date(tb.getDate("due_date").toLocalDate());
                    br.setId(tb.getInt("id"));
                    if (tb.getDate("return_date") == null) {
                        br.setReturn_date(null);
                    } else {
                        br.setReturn_date(tb.getDate("return_date").toLocalDate());
                    }
                    br.setUser_id(tb.getInt("user_id"));
                    br.setStatus(tb.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (tb != null) {
                    tb.close();
                }
                if (st != null) {
                    st.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return br;
    }

}
