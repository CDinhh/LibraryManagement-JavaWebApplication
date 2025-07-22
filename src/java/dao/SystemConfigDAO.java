/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ConfigSystem;
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
public class SystemConfigDAO {

    public ArrayList<ConfigSystem> getAll() {
        ArrayList<ConfigSystem> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select id,config_key,config_value,description\n"
                        + "from dbo.system_config";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        String key = table.getString("config_key");
                        double value = table.getDouble("config_value");
                        String description = table.getString("description");
                        ConfigSystem c = new ConfigSystem(id, key, value, description);
                        list.add(c);
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

    public double getValueByKey(String key) {
        double result=0;
        String sql = "SELECT config_value FROM system_config WHERE config_key = ?";
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                PreparedStatement st=cn.prepareStatement(sql);
                st.setString(1, key);
                ResultSet rs = st.executeQuery();
                while(rs.next()){
                    result=rs.getDouble("config_value");                   
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
    public int updateConfig(int id, String config_key, double config_value, String des) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "update [dbo].[system_config] set [config_key]=?, [config_value]=?, [description]=? where [id]=?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, config_key);
                st.setDouble(2, config_value);
                st.setString(3, des);
                st.setInt(4, id);
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
    public void addConfig() {
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert into [dbo].[system_config] ([config_key], [config_value], [description])\n"
                        + "values ('insert here',0,'insert here');";
                PreparedStatement st = cn.prepareStatement(sql);
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

            }
        }
    }
}

