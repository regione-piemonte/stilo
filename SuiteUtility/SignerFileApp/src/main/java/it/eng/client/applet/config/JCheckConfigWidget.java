package it.eng.client.applet.config;

import java.awt.event.ActionListener;

import it.eng.client.applet.WidgetInfo;

import javax.swing.JCheckBox;

public class JCheckConfigWidget extends JCheckBox implements IConfigWidget{
	private WidgetInfo widgetInfo;
	@Override
	public Object getValue() {
		return this.isSelected();
	}

	@Override
	public void setValue(Object val) {
		this.setSelected(Boolean.valueOf((String)val));
		
	}

	@Override
	public void initValue(Object val) {
		this.setSelected(Boolean.valueOf((String)val));
		
	}

	@Override
	public WidgetInfo getWidgetInfo() {
		// TODO Auto-generated method stub
		return widgetInfo;
	}

	@Override
	public void setWidgetInfo(WidgetInfo widgetInfo) {
		this.widgetInfo = widgetInfo;
		
	}

	@Override
	public void addListener(ActionListener listener) {
		addActionListener(listener);
	}

}
