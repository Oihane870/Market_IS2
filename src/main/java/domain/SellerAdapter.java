package domain; 

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SellerAdapter extends AbstractTableModel {

    private List<Sale> sales;
    
    // 1. Definimos exactamente los nombres de las columnas de la imagen objetivo
    private String[] columnNames = {"title", "description", "price"};

    public SellerAdapter(Seller seller) {
        if (seller != null && seller.getSales() != null) {
            this.sales = seller.getSales();
        }
    }

    @Override
    public int getRowCount() {
        return sales != null ? sales.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sale sale = sales.get(rowIndex);
        
        // 2. Mapeamos cada columna con el método getter correspondiente de Sale
        switch (columnIndex) {
            case 0: return sale.getTitle();       // Muestra el título ("futbol baloia", etc.)
            case 1: return sale.getDescription(); // Muestra la descripción
            case 2: return sale.getPrice();       // Muestra el precio (10.0, 20.0, etc.)
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
