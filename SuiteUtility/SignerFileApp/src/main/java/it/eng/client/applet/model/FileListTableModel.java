package it.eng.client.applet.model;

import it.eng.common.bean.HashFileBean;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class FileListTableModel extends AbstractTableModel{

	List<HashFileBean> files = new ArrayList<HashFileBean>();
	
	String[] columnName = new String[]{"File","Download","Upload","Stato"};
	String[] longValues = new String[]{"70","10","10","10"}; 
		
	URL download = getClass().getResource("download.png");
	URL upload = getClass().getResource("upload.png");
	
	public void clear(){
		files = new ArrayList<HashFileBean>();
	}
	
	public void addFile(HashFileBean bean){
		files.add(bean);
		this.fireTableDataChanged();
	}

	public void setFiles(List<HashFileBean> files){
		this.files = files; 
		this.fireTableDataChanged();
	}
	
	public void updatestatus(int status,int row){
		files.get(row).setStatus(status);
		this.fireTableDataChanged();
	}
	
	
	public HashFileBean getFile(int index){
		return files.get(index);
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	public int getColumnCount() {
		return columnName.length;
	}


	public int getRowCount() {
		return files.size();
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if(col!=0){
			return ImageIcon.class;
		}else{
			return super.getColumnClass(col);
		}
	}	

	public boolean isValid(){
		boolean valid = true;
		for(HashFileBean file:files){
			if(file.getStatus()==0 || file.getStatus()==2){
				valid = false;
				break;
			}			
		}
		return valid;
	}
	
	
    public void initColumnSizes(JTable table,int totalwidth) {
        TableColumn column = null;
        for (int i = 1; i < columnName.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(70);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(totalwidth - 243);
    }
	

	public Object getValueAt(int row, int col){
		switch(col){
			case 0:
				
				return files.get(row).getFileName();
				
			case 1:
				
				return new ImageIcon(download);
				
			case 2:
				
				return new ImageIcon(upload);
				
			case 3:
				
				//Recupero la riga
				URL status =null;
				switch(files.get(row).getStatus()){
				
				case 1:
					status = getClass().getResource("status_ok.png");
					break;
				
				case 2:
					status = getClass().getResource("status_ko.png");
					break;
				
				default:
					status = getClass().getResource("status_empty.png");
					break;
				}
					
				ImageIcon icon = new ImageIcon(status);
				icon.setDescription("STATO");
				
				
				return icon;
			
			default:
				
				return "";
		}
	}
}