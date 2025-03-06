package it.eng.client.applet.config;

import java.awt.event.ActionListener;

import it.eng.client.applet.WidgetInfo;

import javax.swing.JPasswordField;

import org.bouncycastle.util.encoders.Base64;

public class JPassConfigWidget extends JPasswordField implements IConfigWidget{
	
	private boolean encrypt=true;//TODO gestire caso in cui non codifico il valore
	/**
	 * 
	 */
	private static final long serialVersionUID = -2259753013781690830L;
	private WidgetInfo widgetInfo;
	
	@Override
	public Object getValue() {
		String ret=this.getText();
		if(encrypt){
			if(ret!=null ){
				ret=new String(Base64.encode(ret.getBytes())) ;
			} 
		}
		return ret;
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
	
	public static void main(String[] args) {
		//test codifica
		String test="this is a test123èò";
		String codificata=new String(Base64.encode(test.getBytes()));
		System.out.println("codificata:"+codificata);
		  codificata="c3VwZXJtYW4yNg==";
		String decodificata=new String(Base64.decode(codificata.getBytes()));
		System.out.println("decodificata:"+decodificata);
	}

	@Override
	public void addListener(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}
}
