/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Products_SE140736;
import ui.CustomerTableModelProducts_SE140736;
import ultis.DBUltis_SE140736;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class TblProducts_SE140736 {

    private String[] headeres = {"productID", "productName", "unit", "price", "quantity", "categoryid"};
    private int[] indexes = {0, 1, 2, 3, 4, 5};
    private CustomerTableModelProducts_SE140736 modelPro = new CustomerTableModelProducts_SE140736(headeres, indexes);

    public CustomerTableModelProducts_SE140736 getModelProducts() {
        return modelPro;
    }

    public TblProducts_SE140736() {
        try {
            loadData(); //nạp nguồn cho table
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() throws SQLException {
        modelPro.getList().clear(); 
        Connection con = null; 
        PreparedStatement ps = null; 
        ResultSet rs = null; 

        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String spl = "SELECT productID, productName, unit, price, quantity, categoryid FROM TblProducts";
                ps = con.prepareStatement(spl);
                rs = ps.executeQuery();
                while (rs.next()) { 
                    Products_SE140736 pro = new Products_SE140736(rs.getString("productID"),
                            rs.getString("productName"),
                            rs.getString("unit"),
                            rs.getFloat("price"),
                            rs.getInt("quantity"),
                            rs.getString("categoryid"));
                    modelPro.getList().add(pro); 
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

    public String loadCategoriesIntoProducts(String Categories) {
        StringTokenizer stk = new StringTokenizer(Categories, " - ");
        return stk.nextToken();
    }

    public int Insert(Products_SE140736 pro) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "Insert TblProducts Values(?,?,?,?,?,?)";
                // StringTokenizer stk = new StringTokenizer(pro.getCategoryid(), " - ");

                ps = con.prepareStatement(sql);
                ps.setString(1, pro.getProductID());
                ps.setString(2, pro.getProductName());
                // ps.setString(3, stk.nextToken());
                ps.setString(3, pro.getUnit());
                ps.setFloat(4, pro.getPrice());
                ps.setInt(5, pro.getQuantity());
                ps.setString(6, pro.getCategoryid());
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

    public int Save(Products_SE140736 pro) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "UPDATE TblProducts SET productName =?, unit =?, price = ?, quantity = ?, categoryid = ? WHERE productID = ?";
                ps = con.prepareStatement(sql);

                ps.setString(1, pro.getProductName());
                ps.setString(2, pro.getUnit());
                ps.setFloat(3, pro.getPrice());
                ps.setInt(4, pro.getQuantity());
                ps.setString(5, pro.getCategoryid());
                ps.setString(6, pro.getProductID());
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

    public int Delete(Products_SE140736 pro) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis_SE140736.openConnection();
            if (con != null) {
                String sql = "DELETE FROM dbo.TblProducts WHERE productID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, pro.getProductID());
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
//*******************************************************
    public boolean validDateProductID(String productID) {
        try {
            for (int i = 0; i < modelPro.getList().size(); i++) {
                if (modelPro.getList().get(i).getCategoryid().compareToIgnoreCase(productID) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
//*******************************************************
    public boolean validDate(String productID, String productName,
            String unit, String price, String quantity, Object categoryid) {
        if (!productID.toUpperCase().matches("X\\d{3}")) {
            JOptionPane.showMessageDialog(null, "Invalid Product ID");
            return false;
        }

        if (productName.isEmpty() || productName.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Product Name");
            return false;
        }

        if (unit.isEmpty() || unit.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Unit");
            return false;
        }

        if (price.isEmpty() || !price.matches("\\d+.?\\d*")) {
            JOptionPane.showMessageDialog(null, "Invalid Price");
            return false;
        }

        if (quantity.isEmpty() || !quantity.matches("\\d{1,5}")) {
            JOptionPane.showMessageDialog(null, "Invalid Quantity");
            return false;
        }

        if (categoryid == null) {
            JOptionPane.showMessageDialog(null, "Choose the categoryid");
            return false;
        }
        return true;
    }

    public boolean checkConstrainCategoriesID(String categoryID) {
        for (int i = 0; i < modelPro.getList().size(); i++) {
            if (categoryID.compareToIgnoreCase(modelPro.getList().get(i).getCategoryid()) == 0) {
                return false;
            }

        }
        return true;
    }

}
