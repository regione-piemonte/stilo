package it.eng.client.applet;

import it.eng.client.applet.config.IConfigWidget;
import it.eng.client.applet.config.JCheckConfigWidget;
import it.eng.client.applet.config.JComboConfigWidget;
import it.eng.client.applet.config.JPassConfigWidget;
import it.eng.client.applet.config.JTextConfigWidget;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.bouncycastle.util.encoders.Base64;
/**
 * factory per creare i widget a partire dal file di configurazione
 * 
 * @author Russo
 *
 */
public class WidgetFactory {
	/**
	 * costruisce il widget (combo text o altro) a partire dal nome del widget specificato nella config
	 * i widget sono identificati dal fatto che il nome della prop inizia per widget.
	 * 
	 * @param name
	 * @return
	 */
	public static IConfigWidget buildWidget(String name){
		Configuration metaConfig=PreferenceManager.getMetaConfig();
		//Configuration metaConfig
		IConfigWidget ret=null;
		//recupero la lista delle metaprop associate al widget
		String propNameSenzaWidget=name.substring("widget.".length());
		ArrayList<String> metaInfoList=buildArrayFromIter(metaConfig.getKeys(propNameSenzaWidget)); 
		//prendo solo quelli visibili && metaInfoList.contains(propNameSenzaWidget+"."+WidgetProp.VISIBLE)
		if(metaInfoList!=null ){

			//costruisco il bean a partire dalle sue prop di config
			WidgetInfo winfo=buildWidgetInfo(metaConfig, propNameSenzaWidget);
			Object val=null;
			switch(winfo.getType())
			{
			case  TEXT: 
				ret=new JTextConfigWidget();
				//init widget value
				try {
					val=PreferenceManager.getString(winfo.getPropValue(),"");
					ret.initValue(val);
					((JTextConfigWidget)ret).setEditable(winfo.isAllowEdit());
				} catch (Exception e) {
					//ignore
					e.printStackTrace();
				}
				break;
			case  CHECKBOX: 
				ret=new JCheckConfigWidget();
				//init widget value
				try {
					val=PreferenceManager.getString(winfo.getPropValue(),"");
					ret.initValue(val);
				} catch (Exception e) {
					//ignore
					e.printStackTrace();
				}
				break;	
				
			case  PASSWORD: 
				ret=new JPassConfigWidget();
				//init widget value
				try {
					 
					val=PreferenceManager.getString(winfo.getPropValue());
					//suppose encrypt per default
					if(val!=null && !val.equals("")){
						ret.initValue(new String(Base64.decode(val.toString().getBytes())));
					}
					((JPassConfigWidget)ret).setEditable(winfo.isAllowEdit());
				} catch (Exception e) {
					//ignore
					e.printStackTrace();
				}
				break;
				
			case COMBO:
				ret=new JComboConfigWidget();
				try {
					//fixme now work only with string
					if(winfo.getPropValue()!=null){ 
						String[]options=PreferenceManager.getStringArray(winfo.getOptionsProp());
						ret.initValue(options);
						if(options.length>0){
							val=PreferenceManager.getString(winfo.getPropValue(),options[0]);
							//TODO controlla che  sia contenuto in options!
							ret.setValue(val);
						}
						((JComboConfigWidget)ret).setEditable(winfo.isAllowEdit());
					}
				} catch (Exception e) {
					//ignore
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			ret.setWidgetInfo(winfo);
		}
		 
		return ret;
	}

	private static WidgetInfo buildWidgetInfo(Configuration metaConfig,String baseProp){
		//LogWriter.writeLog("winfo building base Prop:"+baseProp);
		WidgetInfo winfo= new WidgetInfo();
		//visible
		winfo.setVisible(metaConfig.getBoolean(baseProp+"."+WidgetProp.VISIBLE.value(), false));
		//allowedit
		winfo.setAllowEdit(metaConfig.getBoolean(baseProp+"."+WidgetProp.ALLOW_EDIT.value(), false));
		//type
		WidgetType wtype=WidgetType.fromValue(metaConfig.getString(baseProp+"."+WidgetProp.TYPE.value(), WidgetType.TEXT.name()));
		winfo.setType(wtype);
		//prop che contiene il valore
		winfo.setPropValue(metaConfig.getString(baseProp+"."+WidgetProp.VALUE_PROP.value(), ""));
		//options per le combo
		winfo.setOptionsProp(metaConfig.getString(baseProp+"."+WidgetProp.OPTIONSPROP.value(),""));
		//if encrypt value usato pre le pass
		winfo.setEncrypt(metaConfig.getBoolean(baseProp+"."+WidgetProp.ENCRYPT.value(), false));
		//LogWriter.writeLog("winfo end building:"+baseProp);
		return winfo;
	}

	private static ArrayList<String> buildArrayFromIter(Iterator<String> metaInfo){ 
		ArrayList<String> copy = new ArrayList<String>();
		while (metaInfo!=null && metaInfo.hasNext())
			copy.add(metaInfo.next());

		return copy;
	}
}
