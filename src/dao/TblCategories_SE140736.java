/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Categories_SE140736;
import ui.CustomerTableModelCategories_SE140736;
import ultis.DBUltis_SE140736;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class TblCategories_SE140736 {

    private String[] headeres = {"categoryID", " categoryName", "description"};
    private int[] indexes = {0, 1, 2};
    private CustomerTableModelCategories_SE140736 modelCate = new CustomerTableModelCategories_SE140736(headeres, indexes);
    private Vector<String> listCategoriesComboBox = new Vector();

    public CustomerTableModelCategories_SE140736 getModelCate() {
        return modelCate;
    }

    public TblCategories_SE140736() {
        try {
            loadData();
            loadCategoriesComboBox();
        } catch (Exception e) {
        }
    }

    public void loadData() throws Exception {
        modelCate.getList().clear();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUltis_SE140736.openConnection();

            if (con != null) {
                String sql = "SELECT categoryID, categoryName, description FROM TblCategories";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Categories_SE140736 cate = new Categories_SE140736(rs.getString("categoryID"),
                            rs.getString("categoryName"),
                            rs.getString("description"));
                    modelCate.getList().add(cate);
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

    public Vector<String> loadCategoriesComboBox() throws Exception {
        listCategoriesComboBox.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUltis_SE140736.openConnection();

            if (con != null) {
                String sql = "SELECT categoryID, categoryName FROM TblCategories";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    listCategoriesComboBox.add(rs.getString("categoryID") + "-" + rs.getString("categoryName"));
                }
                return listCategoriesComboBox;
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
        return null;
    }

    public Vector<String> getListCategoriesComboBox() {
        return listCategoriesComboBox;
    }

    public String getListCategoriesNameComboBox(String categoryid) {
        for (Categories_SE140736 cate : modelCate.getList()) {
            if (cate.getCategoryID().compareToIgnoreCase(categoryid) == 0) {
                return cate.getCategoryName();
            }

        }
        return "";
    }

    public int Insert(Categories_SE140736 cate) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "Insert TblCategories Values(?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, cate.getCategoryID());
                ps.setString(2, cate.getCategoryName());
                ps.setString(3, cate.getDescription());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public int Save(Categories_SE140736 cate) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "UPDATE TblCategories SET categoryName =?, description =? WHERE categoryID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, cate.getCategoryName());
                ps.setString(2, cate.getDescription());
                ps.setString(3, cate.getCategoryID());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;

    }

    public int Delete(Categories_SE140736 cate) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "DELETE FROM dbo.TblCategories WHERE categoryID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, cate.getCategoryID());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public boolean validDateCodeCategories(String categoryID) {
        try {
            for (int i = 0; i < modelCate.getList().size(); i++) {
                if (modelCate.getList().get(i).getCategoryID().compareToIgnoreCase(categoryID) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validDate(String categoryID, String categoryName, String description) {
        if (categoryID.isEmpty() || categoryID.length() > 10) {
            JOptionPane.showMessageDialog(null, "Invalid categoryID");
            return false;
        }
        if (categoryName.isEmpty() || categoryName.length() > 50) {
            JOptionPane.showMessageDialog(null, "Invalid categoryName");
            return false;
        }
        if (description.isEmpty() || description.length() > 200) {
            JOptionPane.showMessageDialog(null, "Invalid description");
            return false;
        }
        return true;

    }

}
