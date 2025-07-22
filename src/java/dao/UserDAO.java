/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Dan Huy
 */
public class UserDAO {

    public User getUserByEmail(String email) {
        User us = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [id],name,email,password,role,status\n"
                        + "from dbo.users\n"
                        + "where email='" + email + "'";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null && table.next()) {
                    int id = table.getInt("id");
                    String name = table.getString("name");
                    String password = table.getString("password");
                    String role = table.getString("role");
                    String status = table.getString("status");
                    us = new User(id, name, email, password, role, status);
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
        return us;
    }

    public int insertNewUser(String name, String email, String password) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert dbo.users values(?,?,?,'user','active')";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2, email);
                st.setString(3, password);
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
                e.printStackTrace();
            }
        }
        return result;
    }

    public User getUser(String email, String password) {
        User result = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [id],name,email,password,role,status\n"
                        + "from dbo.users\n"
                        + "where email=? and password COLLATE Latin1_General_CS_AS =? ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, email);
                st.setString(2, password);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    int id = table.getInt("id");
                    String name = table.getString("name");
                    String role = table.getString("role");
                    String status = table.getString("status");
                    result = new User(id, name, email, password, role, status);
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
        return result;
    }

    public int UpdateUser(int id, String name, String password) {
        int result = 0;
        Connection cn =null;
        PreparedStatement st = null;
        try{
            cn=DBUtils.getConnection();
            if(cn!=null){
                String sql = "UPDATE users SET name = ?, password = ? WHERE id = ?";
                st=cn.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2, password);
                st.setInt(3, id);
                result=st.executeUpdate();
            }
    }catch(Exception e){
        e.printStackTrace();
    }
        finally{
            try{
                if(st!=null) st.close();
                if(cn!=null) cn.close();
            }catch(Exception e){
                
            }
        }
        return result;
    }
    public User getUserById(int id) {
    User us = null;
    Connection cn = null;
    try {
        cn = DBUtils.getConnection();
        if (cn != null) {
            String sql = "SELECT id, name, email, password, role, status FROM users WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String status = rs.getString("status");
                us = new User(id, name, email, password, role, status);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (cn != null) cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return us;
}
     public ArrayList<User> getAllUSer() {
        ArrayList<User> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select u.id,u.name,u.email,u.status\n"
                        + "from[dbo].[users] u";
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet tb = st.executeQuery();
                while (tb.next()) {
                    User us = new User();
                    us.setId(tb.getInt("id"));
                    us.setName(tb.getString("name"));
                    us.setEmail(tb.getString("email"));
                    us.setStatus(tb.getString("status"));
                    list.add(us);
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
     public int disableAccount(int userID) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="update [dbo].[users] set [status]='blocked' where id=?";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setInt(1, userID);
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
    
    public int enableAccount(int userID) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql="update [dbo].[users] set [status]='active' where id=?";
                PreparedStatement st=cn.prepareStatement(sql);
                st.setInt(1, userID);
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
