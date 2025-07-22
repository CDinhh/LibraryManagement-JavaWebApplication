
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import mylib.DBUtils;


public class InventoryDAO {
    
    public int insertLogs(int bookId, int total, String des) {
        int result = 0;
        LocalDate currentDate = LocalDate.now();
        String sql="INSERT INTO [dbo].[inventory_logs] ([createAt] , [bookId], [total], [description])\n" +
                                                    "VALUES (?,?,?,?);";
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn=DBUtils.getConnection();
            if(cn!=null) {
                st=cn.prepareStatement(sql);
                st.setDate(1, Date.valueOf(currentDate));
                st.setInt(2, bookId);
                st.setInt(3, total);
                st.setString(4, des);
                result = st.executeUpdate();
                if(result > 0) {
                    System.out.println("inserted successfully !");
                } else {
                    System.out.println("fail to insert into inventory logs !");
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
}
