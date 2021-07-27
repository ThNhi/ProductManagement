/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Users_SE140736;
import ui.CustomerTableModelUsers_SE140736;
import ultis.DBUltis_SE140736;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author DELL
 */
public class TblUsers_SE140736 {

    private Vector<Users_SE140736> listUsers = new Vector<Users_SE140736>();

    public Vector<Users_SE140736> getListUser() {
        return listUsers;
    }

    public void loadData() throws Exception {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "SELECT userID, fullName, password, status FROM tblUsers";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) { 
                    Users_SE140736 user = new Users_SE140736(rs.getString("userID"),
                            rs.getString("fullName"),
                            rs.getString("password"),
                            rs.getBoolean("status"));

                    listUsers.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }
        }

    }

    public boolean checkLogin(String userID, String password) {
        for (int i = 0; i < listUsers.size(); i++) {
            if (userID.equals(listUsers.get(i).getUserID()) && password.equals(listUsers.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

}
