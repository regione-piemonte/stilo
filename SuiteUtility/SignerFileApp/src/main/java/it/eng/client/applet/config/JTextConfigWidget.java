package it.eng.client.applet.config;

import java.awt.event.ActionListener;

import it.eng.client.applet.WidgetInfo;

import javax.swing.JTextField;

public class JTextConfigWidget extends JTextField implements IConfigWidget{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2259753013781690830L;
	private WidgetInfo widgetInfo;
	
	@Override
	public Object getValue() {
		return this.getText();
	}

	@Override
	public void setValue(Object val) {
		this.setText(""+val);
		
	}

	@Override
	public void initValue(Object val) {
		setValue(val);
		
	}
	public WidgetInfo getWidgetInfo() {
		return widgetInfo;
	}

	public void setWidgetInfo(WidgetInfo widgetInfo) {
		this.widgetInfo = widgetInfo;
	}

	@Override
	public void addListener(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}
}
