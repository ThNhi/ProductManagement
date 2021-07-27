/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.TblCategories_SE140736;
import dto.Products_SE140736;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class CustomerTableModelProducts_SE140736 extends AbstractTableModel{

    private String [] headeres; 
    private int[] indexes; 
    private Vector<Products_SE140736> data; 
    TblCategories_SE140736 dao = new TblCategories_SE140736();
    
    public void loadCategoriesComboBox(){
        try {
            dao.loadCategoriesComboBox();
        } catch (Exception e) {
        }
    }
    public Vector<Products_SE140736> getList(){
        return data;
    }
    
    public CustomerTableModelProducts_SE140736(String[] headeres, int[] indexes) {
        this.headeres = new String[headeres.length];
        for(int i = 0; i < headeres.length; i++){
            this.headeres[i] = headeres[i];
        }
        
        
        this.indexes = new int[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            this.indexes[i] = indexes[i];
        }
        
        data = new Vector<>();
    }

    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return headeres.length;
    }

     @Override
    public String getColumnName(int column) {
        if(column >= 0 || column < headeres.length){
            return headeres[column];
        }
        else return"";
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    if(rowIndex < 0 || rowIndex >= data.size() || columnIndex < 0 || columnIndex >= headeres.length){
            return null;
        }
        
        Products_SE140736 pro = data.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return pro.getProductID();
            case 1: return pro.getProductName();
            case 2: return pro.getUnit();
            case 3: return pro.getPrice();
            case 4: return pro.getQuantity();
            case 5: return pro.getCategoryid();
        }
        return null;
    }
    
}
