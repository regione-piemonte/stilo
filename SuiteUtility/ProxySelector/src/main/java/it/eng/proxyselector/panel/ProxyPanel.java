package it.eng.proxyselector.panel;

import it.eng.proxyselector.ProxySelector;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ProxyPanel extends JPanel {

	JTextField lJTextFieldUsername;
	JPasswordField lJPasswordField;
	JTextField lJTextFieldDominio;
	JCheckBox lJCheckBoxNTLM;
	JTextField lJTextFieldProxy;
	JTextField lJTextFieldPorta;
	JTextField lJTextFieldScript;
	JCheckBox lJCheckBoxScript;
	
	public ProxyPanel(){
		super(new BorderLayout());
		final ProxyPanel instance = this;
		final Preferences lPreferences = Preferences.systemNodeForPackage(ProxySelector.class).node(ProxySelector.class.getName());
		JPanel labelPanel = new JPanel(new GridLayout(8, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(8, 1));
		add(labelPanel, BorderLayout.WEST);
		add(fieldPanel, BorderLayout.CENTER);
		JLabel lJLabelUsername = new JLabel("Username", JLabel.RIGHT);
		lJTextFieldUsername = new JTextField();
		lJTextFieldUsername.setColumns(10);
		lJLabelUsername.setLabelFor(lJTextFieldUsername);
		JLabel lJLabelPassword = new JLabel("Password", JLabel.RIGHT);
		lJPasswordField = new JPasswordField();
		lJPasswordField.setColumns(10);
		lJLabelPassword.setLabelFor(lJPasswordField);
		JLabel lJLabelDominio = new JLabel("Dominio", JLabel.RIGHT);
		lJTextFieldDominio = new JTextField();
		lJTextFieldDominio.setColumns(10);
		lJLabelDominio.setLabelFor(lJTextFieldDominio);
		JLabel lJLabelNTMLAuthentication = new JLabel("Usa NTLM", JLabel.RIGHT);
		lJCheckBoxNTLM = new JCheckBox();
		lJCheckBoxNTLM.setSelected(true);
		lJCheckBoxNTLM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageValueOfCheckBoxNTLM();
			}
			
		});
		lJLabelNTMLAuthentication.setLabelFor(lJCheckBoxNTLM);
		
		JLabel lJLabelProxy = new JLabel("Proxy", JLabel.RIGHT);
		lJTextFieldProxy = new JTextField();
		lJTextFieldProxy.setColumns(10);
		lJLabelProxy.setLabelFor(lJTextFieldProxy);
		JLabel lJLabelPorta = new JLabel("Porta", JLabel.RIGHT);
		lJTextFieldPorta = new JTextField();
		lJTextFieldPorta.setText(lPreferences.getInt("PORTA", 0)+"");
		lJLabelPorta.setLabelFor(lJTextFieldPorta);
		lJTextFieldPorta.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				try {
					int value = Integer.parseInt(e.getKeyChar()+"");
				} catch (Exception lException){
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		lJTextFieldPorta.setVerifyInputWhenFocusTarget(true);
		lJTextFieldPorta.setColumns(4);
		JLabel lJLabelUsaScript = new JLabel("Usa Script", JLabel.RIGHT);
		lJCheckBoxScript = new JCheckBox();
		lJCheckBoxScript.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageValueOfCheckBoxScript();
			}
			
		});
		lJLabelUsaScript.setLabelFor(lJCheckBoxScript);
		
		JLabel lJLabelScript = new JLabel("Script", JLabel.RIGHT);
		lJTextFieldScript = new JTextField();
		lJTextFieldScript.setColumns(20);
		lJLabelScript.setLabelFor(lJTextFieldScript);
		lJTextFieldScript.setEnabled(false);
		labelPanel.add(lJLabelProxy);
		fieldPanel.add(createPanelText(lJTextFieldProxy));
		labelPanel.add(lJLabelPorta);
		fieldPanel.add(createPanelText(lJTextFieldPorta));
		
		labelPanel.add(lJLabelUsaScript);
		fieldPanel.add(createPanelCheck(lJCheckBoxScript));
		labelPanel.add(lJLabelScript);
		fieldPanel.add(createPanelText(lJTextFieldScript));
		
		labelPanel.add(lJLabelUsername);
		fieldPanel.add(createPanelText(lJTextFieldUsername));
		labelPanel.add(lJLabelPassword);
		fieldPanel.add(createPanelText(lJPasswordField));
		
		labelPanel.add(lJLabelNTMLAuthentication);
		fieldPanel.add(createPanelCheck(lJCheckBoxNTLM));
		
		labelPanel.add(lJLabelDominio);
		fieldPanel.add(createPanelText(lJTextFieldDominio));
		loadConfiguration();
		
	}

	protected void manageValueOfCheckBoxNTLM() {
		lJTextFieldDominio.setEnabled(lJCheckBoxNTLM.isSelected());
	}

	private void loadConfiguration() {
		ProxyConfiguration lProxyConfiguration = ProxyDefaultHttpClient.getConfiguration();
		lJTextFieldProxy.setText(lProxyConfiguration.getProxy());
		lJTextFieldPorta.setText(lProxyConfiguration.getPort()+"");
		lJTextFieldUsername.setText(lProxyConfiguration.getUsername());
		lJPasswordField.setText(lProxyConfiguration.getPassword()!=null?new String(lProxyConfiguration.getPassword()):"");
		lJTextFieldDominio.setText(lProxyConfiguration.getDominio());
		lJCheckBoxNTLM.setSelected(lProxyConfiguration.isUseNTLM());
		lJCheckBoxScript.setSelected(lProxyConfiguration.isUseScript());
		lJTextFieldScript.setText(lProxyConfiguration.getScript());
		manageValueOfCheckBoxScript();
		manageValueOfCheckBoxNTLM();
	}

	protected JPanel createPanelText(final JTextField lJTextFieldProxy) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(lJTextFieldProxy);
		return p;
	}

	protected JPanel createPanelCheck(final JCheckBox lJTextFieldProxy) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(lJTextFieldProxy);
		return p;
	}

	public String getUsername(){
		return lJTextFieldUsername.getText();
	}
	public char[] getPassword(){
		return lJPasswordField.getPassword();
	}
	public String getDominio(){
		return lJTextFieldDominio.getText();
	}
	public String getProxy(){
		return lJTextFieldProxy.getText();
	}
	public int getPort(){
		return lJTextFieldPorta.getText().trim().equals("")?0:Integer.valueOf(lJTextFieldPorta.getText());
	}
	public boolean getUseNLTM(){
		return lJCheckBoxNTLM.isSelected();
	}

	public ProxyConfiguration getEditedConfiguration(){
		ProxyConfiguration lProxyConfiguration = new ProxyConfiguration();
		lProxyConfiguration.setDominio(getDominio());
		lProxyConfiguration.setPassword(getPassword());
		lProxyConfiguration.setPort(getPort());
		lProxyConfiguration.setProxy(getProxy());
		lProxyConfiguration.setUseNTLM(getUseNLTM());
		lProxyConfiguration.setUsername(getUsername());
		lProxyConfiguration.setScript(getScript());
		lProxyConfiguration.setUseScript(getUseScript());
		return lProxyConfiguration;
	}
	
	public boolean getUseScript() {
		return lJCheckBoxScript.isSelected();
	}

	public String getScript() {
		return lJTextFieldScript.getText();
	}

	protected void manageValueOfCheckBoxScript() {
		lJTextFieldProxy.setEnabled(!lJCheckBoxScript.isSelected());
		lJTextFieldPorta.setEnabled(!lJCheckBoxScript.isSelected());
		lJTextFieldScript.setEnabled(lJCheckBoxScript.isSelected());
	}
}
