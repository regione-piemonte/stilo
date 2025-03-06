package it.eng.client.applet.cnProvider;

import javax.swing.JApplet;

public interface CommonNameProvider {
	public boolean sendCommonName(String commonName);
	public void saveOutputParameter(JApplet applet) throws Exception;
}
