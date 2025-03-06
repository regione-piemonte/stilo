package it.eng.client.applet.config;

import java.awt.event.ActionListener;

import it.eng.client.applet.WidgetInfo;

/**
 *  rappresenta oggitti letti da configurazioen che pssono essere editati da interfaccia
 * @author Russo
 *
 */
public interface IConfigWidget {

	public Object getValue();
	public void setValue(Object val);
	public void initValue(Object val);
	public WidgetInfo getWidgetInfo();
	public void setWidgetInfo(WidgetInfo widgetInfo);
	public void addListener(ActionListener listener);
	
}
