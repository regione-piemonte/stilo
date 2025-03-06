package it.eng.proxyselector.frame;

import it.eng.proxyselector.ProxySelector;
import it.eng.proxyselector.ProxyUtils;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import it.eng.proxyselector.panel.ProxyPanel;
import it.eng.proxyselector.preferences.ProxyPreferences;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Proxy;
import java.net.URI;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;

public class ProxyFrame extends JFrame {
	
	private String mStrAddress;
	private static Logger mLogger = Logger.getLogger(ProxyFrame.class);

	public ProxyFrame(String pStrAddress){
		super("Impostazioni di rete");
		mLogger.debug("Tolto lo useSystemProxyes");
		System.clearProperty("java.net.useSystemProxies");
		mStrAddress = pStrAddress;
		final ProxyFrame instance = this;
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ProxyPanel lProxyPanel = new ProxyPanel();
		getContentPane().add(lProxyPanel, BorderLayout.NORTH);
		JButton lJButton = new JButton();
		lJButton.setText("Test connessione");
		lJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProxyDefaultHttpClient.getClientToUse();
				int porta = lProxyPanel.getPort();
				if (!StringUtils.isEmpty(lProxyPanel.getProxy())){
					if (lProxyPanel.getUseScript()){
						PacProxySelector pacProxySelector = new PacProxySelector(
								new UrlPacScriptSource(lProxyPanel.getScript()));
						if (lProxyPanel.getUseNLTM()){
							ProxyUtils.setProxyScriptNTLM(pacProxySelector, mStrAddress, lProxyPanel.getUsername(), lProxyPanel.getPassword(), 
									lProxyPanel.getDominio(), null);
						} else {
							ProxyUtils.setProxyScript(pacProxySelector, mStrAddress, lProxyPanel.getUsername(), lProxyPanel.getPassword());
						}
					} else {
						if (lProxyPanel.getUseNLTM()){
							ProxyUtils.setProxyNTLM(lProxyPanel.getProxy(), 
									porta, lProxyPanel.getUsername(), 
									lProxyPanel.getPassword(), lProxyPanel.getDominio());
						} else {
							ProxyUtils.setProxy(lProxyPanel.getProxy(), 
									porta, lProxyPanel.getUsername(), 
									lProxyPanel.getPassword());
						}
					}
				} else {
					ProxyUtils.resetProxy();
				}
				try {
					List<java.net.Proxy> listProxy = java.net.ProxySelector.getDefault().select(new URI(mStrAddress));
					for (Proxy lProxy : listProxy){
						mLogger.debug("Proxy: " );
						mLogger.debug(lProxy.address());
						mLogger.debug(lProxy.type().name());
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(instance, "Impossibile recuperare le impostazioni proxy per l'indirizzo " + mStrAddress + ": " + e1.getMessage());
				}
				String debug = (lProxyPanel.getUseNLTM()?"NTLM usato ":"") + (StringUtils.isNotEmpty(lProxyPanel.getProxy())?("proxy = " + lProxyPanel.getProxy() 
						+ " porta = " + porta  + " username = " + lProxyPanel.getUsername()
						+ (lProxyPanel.getUseNLTM()?" dominio = " + lProxyPanel.getDominio():"") + "\n"):"NO PROXY") ;
				try {
					
					String resultConnection = ProxySelector.testDiConnessione(mStrAddress);
					JOptionPane.showMessageDialog(instance, debug  + "Host " + mStrAddress + " Raggiunto: " + resultConnection);
				} catch (Exception e1) {
					mLogger.error("Errore " + e1.getMessage(), e1);
					JOptionPane.showMessageDialog(instance, debug  + "Host " + mStrAddress + " Non Raggiunto: errore " + e1.getMessage());
				} 
			}
		});
		JPanel p = new JPanel();
		p.add(lJButton);
		getContentPane().add(p, BorderLayout.EAST);
		JButton lButtonOk = new JButton("Ok");
		JPanel lJPanelOk = new JPanel();
		lJPanelOk.add(lButtonOk);
		getContentPane().add(lJPanelOk, BorderLayout.WEST);
		lButtonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int porta = lProxyPanel.getPort();
				if (!StringUtils.isEmpty(lProxyPanel.getProxy())){
					if (lProxyPanel.getUseScript()){
						PacProxySelector pacProxySelector = new PacProxySelector(
								new UrlPacScriptSource(lProxyPanel.getScript()));
						if (lProxyPanel.getUseNLTM()){
							ProxyUtils.setProxyScriptNTLM(pacProxySelector, mStrAddress, lProxyPanel.getUsername(), lProxyPanel.getPassword(), 
									lProxyPanel.getDominio(), null);
						} else {
							ProxyUtils.setProxyScript(pacProxySelector, mStrAddress, lProxyPanel.getUsername(), lProxyPanel.getPassword());
						}
					} else {
						if (lProxyPanel.getUseNLTM()){
							ProxyUtils.setProxyNTLM(lProxyPanel.getProxy(), 
									porta, lProxyPanel.getUsername(), 
									lProxyPanel.getPassword(), lProxyPanel.getDominio());
						} else {
							ProxyUtils.setProxy(lProxyPanel.getProxy(), 
									porta, lProxyPanel.getUsername(), 
									lProxyPanel.getPassword());
						}
					}
				} else {
					ProxyUtils.resetProxy();
				}
				ProxyConfiguration lProxyConfiguration = lProxyPanel.getEditedConfiguration();
				ProxyPreferences.saveConfiguration(lProxyConfiguration);
				ProxyDefaultHttpClient.setConfiguration(lProxyConfiguration);
				instance.dispose();
			}
		});
		pack();
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
}
