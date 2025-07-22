
package dao;

import dto.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;


public class BookDAO {

    public ArrayList<Book> getLastestBook() {
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "  SELECT * FROM books WHERE status = 'active' \n" +
                        "	and YEAR(GETDATE())  - published_year  <= (\n" +
                        "			SELECT CAST(config_value AS INT)  "+
                        "                         FROM system_config WHERE config_key ='new_book_rule'\n" +
                        "						  ) \n" +
                        "	ORDER BY published_year DESC";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        String isbn = rs.getString("isbn");
                        String category = rs.getString("category");
                        int year = rs.getInt("published_year");
                        int totol_copy = rs.getInt("total_copies");
                        int available_copy = rs.getInt("available_copies");
                        String status = rs.getString("status");
                        String url = rs.getString("url");
                        Book b = new Book(id, title, author, isbn, category, year, totol_copy, available_copy, status, url);
                        list.add(b);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    
    public ArrayList<Book> getAllBook() { 
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
            try {
                cn = DBUtils.getConnection();
                if(cn!=null) {
                    String sql = "SELECT [id]\n" +
                            "      ,[title]\n" +
                            "      ,[author]\n" +
                            "      ,[isbn]\n" +
                            "      ,[category]\n" +
                            "      ,[published_year]\n" +
                            "      ,[total_copies]\n" +
                            "      ,[available_copies]\n" +
                            "      ,[status]\n" +
                            "      ,[url]\n" +
                            "  FROM [library_system].[dbo].[books]";
                    
                    ps = cn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    if(rs!=null)
                        while(rs.next()) {
                            int id = rs.getInt("id");
                            String title = rs.getString("title");
                            String author = rs.getString("author");
                            String isbn = rs.getString("isbn");
                            String category = rs.getString("category");
                            int year = rs.getInt("published_year");
                            int total_copy = rs.getInt("total_copies");
                            int available_copy = rs.getInt("available_copies");
                            String status = rs.getString("status");
                            String url = rs.getString("url");
                            Book b = new Book(id, title, author, isbn, category, year, total_copy, available_copy, status, url);
                            list.add(b);
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(rs != null) rs.close();
                    if(ps != null) ps.close();
                    if(cn != null) cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        return list;
    }
    
    
    public ArrayList<Book> searchBook(String value) {
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT * FROM books WHERE status = 'active' AND (title LIKE ? OR author LIKE ? OR category LIKE ?)";
                PreparedStatement st = cn.prepareStatement(sql);
                String q = "%" + value + "%";
                st.setString(1, q);
                st.setString(2, q);
                st.setString(3, q);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        String title = table.getString("title");
                        String author = table.getString("author");
                        String isbn = table.getString("isbn");
                        String category = table.getString("category");
                        int year = table.getInt("published_year");
                        int totol_copy = table.getInt("total_copies");
                        int available_copy = table.getInt("available_copies");
                        String status = table.getString("status");
                        String url = table.getString("url");
                        Book b = new Book(id, title, author, isbn, category, year, totol_copy, available_copy, status, url);
                        list.add(b);
                    }
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

    public Book getBookById(int id) {
        Book b = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select *\n"
                        + "from dbo.books\n"
                        + "where id = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        String isbn = rs.getString("isbn");
                        String category = rs.getString("category");
                        int year = rs.getInt("published_year");
                        int totol_copy = rs.getInt("total_copies");
                        int available_copy = rs.getInt("available_copies");
                        String status = rs.getString("status");
                        String url = rs.getString("url");
                        b = new Book(id, title, author, isbn, category, year, totol_copy, available_copy, status, url);

                    }
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

        return b;
    }

    public void decreaseAvailableCopies(int bookId) {
        String sql = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                PreparedStatement st=cn.prepareStatement(sql);
                st.setInt(1, bookId);
                st.executeUpdate();
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
    }
    public void increaseAvailableCopies(int bookId) {
        String sql = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                PreparedStatement st=cn.prepareStatement(sql);
                st.setInt(1, bookId);
                st.executeUpdate();
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
    }
    
    public int setBookStatus(String status,int bookId) {
        String sql = "  UPDATE books SET status=? where id = ?";
        Connection cn = null;
        PreparedStatement ps = null;
        int row = 0;
        try {
            cn=DBUtils.getConnection();
            if(cn!=null) {
                ps=cn.prepareStatement(sql);
                ps.setString(1, status.trim().toLowerCase());
                ps.setInt(2, bookId);
                row = ps.executeUpdate();
                if(row == 0) {
                    System.out.println("Khong cap nhap dc book id : " + bookId + ", status : " +status );
                    return row;
                }
                else {
                    System.out.println("Da cap nhap book id : " + bookId + ", status : " +status );
                    return row;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return row;
    }
    
    public int updateBook(int id, String title, String author, String isbn, String category,int published_year, int total_copies, int available_copies, String status, String url) {
        int row = 0;
        
        String sql = "UPDATE books SET title=?, author=?, isbn=?, category=?, published_year=?, "
                                + "total_copies=?, available_copies=?, status=?, url=? WHERE id=?";
        
        
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DBUtils.getConnection();
            if(cn!=null) {
                ps=cn.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, isbn);
                ps.setString(4, category);
                ps.setInt(5, published_year);
                ps.setInt(6, total_copies);
                ps.setInt(7, available_copies);
                ps.setString(8, status);
                ps.setString(9, url);
                ps.setInt(10, id);
                
                row = ps.executeUpdate();
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return row;
    }
    
    public int inSertBook(String title, String author, String isbn, String category, int year, int total, int avail, String status, String url) {
        int row = 0;
        String sql = "  insert into books "
                        + "(title,author,isbn,category,published_year,total_copies,available_copies,status,url)"
                        + " values(?,?,?,?,?,?,?,?,?)";
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn=DBUtils.getConnection();
            if(cn!=null) {
                ps=cn.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, isbn);
                ps.setString(4, category);
                ps.setInt(5, year);
                ps.setInt(6, total);
                ps.setInt(7, avail);
                ps.setString(8, status);
                ps.setString(9, url);
                
                row = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return row;
    }
    
   public int updateTotalAvaiCopies(int BookId, int quantity) {
        int result =0;
        Connection cn = null;
        PreparedStatement st = null;
        String sql="  update books set total_copies = total_copies + ?, available_copies = available_copies + ? where id = ?";
        try {
           cn = DBUtils.getConnection();
           if(cn!=null) {
               st=cn.prepareStatement(sql);
               st.setInt(1, quantity);
               st.setInt(2, quantity);
               st.setInt(3, BookId);
               result = st.executeUpdate();
               if(result > 0) {
                   System.out.println("Update Total/Available Copies sucessfully!");
               } else {
                   System.out.println("Update Total/Available Copies failed!");
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
            try {
                if(st!=null) st.close();
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
       }
        return result;
   }
   
   public int insertBookNullableReturnBookID(String title, String author, String isbn, String category, Integer year, int total, int avail, String status, String url) {
       int result = 0;
       Connection cn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
       String sql = "  insert into books "
                        + "(title,author,isbn,category,published_year,total_copies,available_copies,status,url)"
                        + " values(?,?,?,?,?,?,?,?,?)";;
       try {
           cn=DBUtils.getConnection();
           if(cn!=null) {
               ps=cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, title);
               ps.setString(2, author);
               ps.setString(3, isbn);
               ps.setObject(4, category, java.sql.Types.VARCHAR);
               ps.setObject(5, year, java.sql.Types.INTEGER); 
               ps.setInt(6, total);
               ps.setInt(7, avail);
               ps.setObject(8, status, java.sql.Types.VARCHAR);
               ps.setObject(9, url, java.sql.Types.VARCHAR);
               //set object de xu ly Null
               result=ps.executeUpdate();
               if(result>0) {
                   rs = ps.getGeneratedKeys();
                   if(rs.next()) 
                   result = rs.getInt(1);       
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try {
               if(rs!=null) rs.close();
               if(ps!=null) ps.close();
               if(cn!=null) cn.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
               
       return result;
   }
}
