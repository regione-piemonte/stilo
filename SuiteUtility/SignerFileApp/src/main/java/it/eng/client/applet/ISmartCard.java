package it.eng.client.applet;

import it.eng.common.bean.HashFileBean;

import java.io.File;
import java.util.List;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;

public interface ISmartCard {

 

	//public SmartCardConfigBean getConfig();
	public void selectedTab(int index);
	public NimRODLookAndFeel getLookFeel();
	public HashFileBean getBean();
	public List<HashFileBean> getHashfilebean();
	//public String getWsAuth();
	public String getBaseurl();
	public String getCookie();
	
	//TODO change me
	public File getFile();
	public void setFile(File file);
	
}
