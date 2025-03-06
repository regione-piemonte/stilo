package it.eng.client.applet.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DriverTableModel extends AbstractTableModel{

	List<File> driver = new ArrayList<File>();
	
	public List<File> getDriver() {
		return driver;
	}

	public void setDriver(List<File> driver) {
		this.driver = driver;
	}

	String[] columnName = new String[]{"Driver"};
	
	public void clear(){
		driver = new ArrayList<File>();
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
		

	public int getColumnCount() {
		return 1;
	}


	public int getRowCount() {
		return driver.size();
	}


	public Object getValueAt(int row, int col){
		return driver.get(row).getName();
	}
}