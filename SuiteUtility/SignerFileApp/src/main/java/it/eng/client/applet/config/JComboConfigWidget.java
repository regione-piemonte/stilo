package it.eng.client.applet.config;

import it.eng.client.applet.WidgetInfo;

import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;

public class JComboConfigWidget extends JComboBox implements IConfigWidget{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2259753013781690830L;
	private WidgetInfo widgetInfo;
	
	@Override
	public Object getValue() {
		return this.getSelectedItem();
	}

	@Override
	public void setValue(Object val) {
		this.setSelectedItem(val);
		
	}

	@Override
	public void initValue(Object val) {
		 if(val instanceof Collection){
			 Collection col=(Collection)val;
			 for (Object object : col) {
				this.addItem(object);
			}
		 }else if(val instanceof Object[]){
			 Object[] lista=(Object[])val;
			 for (int i = 0; i < lista.length; i++) {
				 this.addItem(lista[i]);
			}
		 }
		
	}

	public WidgetInfo getWidgetInfo() {
		return widgetInfo;
	}

	public void setWidgetInfo(WidgetInfo widgetInfo) {
		this.widgetInfo = widgetInfo;
	}

	@Override
	public void addListener(ActionListener listener) {
		addActionListener(listener);
		
	}

}
