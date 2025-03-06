// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.net.InetAddress;
import java.awt.EventQueue;
import java.net.PasswordAuthentication;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.net.Authenticator;

public class JAuthenticator extends Authenticator
{
    public static final String TITLE = "JAuthenticator.title";
    public static final String LABEL_SITE = "JAuthenticator.label.site";
    public static final String LABEL_REQ = "JAuthenticator.label.req";
    public static final String LABEL_USERID = "JAuthenticator.label.userID";
    public static final String LABEL_PASSWORD = "JAuthenticator.label.password";
    public static final String LABEL_CANCEL = "JAuthenticator.label.cancel";
    public static final String LABEL_OK = "JAuthenticator.label.ok";
    protected JDialog window;
    protected JButton cancelButton;
    protected JButton okButton;
    protected JLabel label1;
    protected JLabel label2;
    protected JTextField JUserID;
    protected JPasswordField JPassword;
    final Object lock;
    private boolean result;
    private volatile boolean wasNotified;
    private String userID;
    private char[] password;
    ActionListener okListener;
    ActionListener cancelListener;
    
    public JAuthenticator() {
        this.lock = new Object();
        this.okListener = new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                synchronized (JAuthenticator.this.lock) {
                    JAuthenticator.this.window.setVisible(false);
                    JAuthenticator.this.userID = JAuthenticator.this.JUserID.getText();
                    JAuthenticator.this.password = JAuthenticator.this.JPassword.getPassword();
                    JAuthenticator.this.JPassword.setText("");
                    JAuthenticator.this.result = true;
                    JAuthenticator.this.wasNotified = true;
                    JAuthenticator.this.lock.notifyAll();
                }
            }
        };
        this.cancelListener = new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                synchronized (JAuthenticator.this.lock) {
                    JAuthenticator.this.window.setVisible(false);
                    JAuthenticator.this.userID = null;
                    JAuthenticator.this.JUserID.setText("");
                    JAuthenticator.this.password = null;
                    JAuthenticator.this.JPassword.setText("");
                    JAuthenticator.this.result = false;
                    JAuthenticator.this.wasNotified = true;
                    JAuthenticator.this.lock.notifyAll();
                }
            }
        };
        this.initWindow();
    }
    
    protected void initWindow() {
        this.window = new JDialog((Frame)null, Resources.getString("JAuthenticator.title"), true);
        final Container contentPane = this.window.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(this.buildAuthPanel(), "Center");
        contentPane.add(this.buildButtonPanel(), "South");
        this.window.pack();
        this.window.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                JAuthenticator.this.cancelListener.actionPerformed(new ActionEvent(windowEvent.getWindow(), 1001, "Close"));
            }
        });
    }
    
    protected JComponent buildAuthPanel() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        final JPanel panel = new JPanel(layout);
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridwidth = 1;
        final JLabel label = new JLabel(Resources.getString("JAuthenticator.label.site"));
        label.setHorizontalAlignment(2);
        layout.setConstraints(label, gridBagConstraints);
        panel.add(label);
        gridBagConstraints.gridwidth = 0;
        (this.label1 = new JLabel("")).setHorizontalAlignment(2);
        layout.setConstraints(this.label1, gridBagConstraints);
        panel.add(this.label1);
        gridBagConstraints.gridwidth = 1;
        final JLabel label2 = new JLabel(Resources.getString("JAuthenticator.label.req"));
        label2.setHorizontalAlignment(2);
        layout.setConstraints(label2, gridBagConstraints);
        panel.add(label2);
        gridBagConstraints.gridwidth = 0;
        (this.label2 = new JLabel("")).setHorizontalAlignment(2);
        layout.setConstraints(this.label2, gridBagConstraints);
        panel.add(this.label2);
        gridBagConstraints.gridwidth = 1;
        final JLabel label3 = new JLabel(Resources.getString("JAuthenticator.label.userID"));
        label3.setHorizontalAlignment(2);
        layout.setConstraints(label3, gridBagConstraints);
        panel.add(label3);
        gridBagConstraints.gridwidth = 0;
        layout.setConstraints(this.JUserID = new JTextField(20), gridBagConstraints);
        panel.add(this.JUserID);
        gridBagConstraints.gridwidth = 1;
        final JLabel label4 = new JLabel(Resources.getString("JAuthenticator.label.password"));
        label4.setHorizontalAlignment(2);
        layout.setConstraints(label4, gridBagConstraints);
        panel.add(label4);
        gridBagConstraints.gridwidth = 0;
        (this.JPassword = new JPasswordField(20)).setEchoChar('*');
        this.JPassword.addActionListener(this.okListener);
        layout.setConstraints(this.JPassword, gridBagConstraints);
        panel.add(this.JPassword);
        return panel;
    }
    
    protected JComponent buildButtonPanel() {
        final JPanel panel = new JPanel();
        (this.cancelButton = new JButton(Resources.getString("JAuthenticator.label.cancel"))).addActionListener(this.cancelListener);
        panel.add(this.cancelButton);
        (this.okButton = new JButton(Resources.getString("JAuthenticator.label.ok"))).addActionListener(this.okListener);
        panel.add(this.okButton);
        return panel;
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
        synchronized (this.lock) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    JAuthenticator.this.label1.setText(Authenticator.this.getRequestingSite().getHostName());
                    JAuthenticator.this.label2.setText(Authenticator.this.getRequestingPrompt());
                    JAuthenticator.this.window.setVisible(true);
                }
            });
            this.wasNotified = false;
            while (!this.wasNotified) {
                try {
                    this.lock.wait();
                }
                catch (InterruptedException ex) {}
            }
            if (!this.result) {
                return null;
            }
            return new PasswordAuthentication(this.userID, this.password);
        }
    }
}
