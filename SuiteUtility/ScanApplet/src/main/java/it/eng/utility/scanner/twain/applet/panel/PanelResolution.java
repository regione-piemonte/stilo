package it.eng.utility.scanner.twain.applet.panel;

import it.eng.utility.scanner.twain.LogWriter;
import it.eng.utility.scanner.twain.PreferenceKeys;
import it.eng.utility.scanner.twain.PreferenceManager;
import it.eng.utility.scanner.twain.applet.enums.TipoScansioneEnum;
import it.eng.utility.scanner.twain.message.MessageKeys;
import it.eng.utility.scanner.twain.message.Messages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelResolution extends GenericPanel {

	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JLabel resolutionLabel;
	private JTextField resolutionField;
	
	private JLabel tipoScansioneLabel;	
	private JCheckBox tipoScansioneCheckBox;
	
	private JButton savePreference;
	private JButton close;
	
	public PanelResolution(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public PanelResolution() {
		super();
	}
	
	public void show( Container container){

		windowDialog = new JDialog();
		windowDialog.setModal(true);

		initialize();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );
		 
        windowDialog.setTitle( Messages.getMessage( MessageKeys.PANEL_SAVERESOLUTION_TITLE ) );
        
        windowDialog.setLocationRelativeTo( container );
        windowDialog.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		
this.setPreferredSize(new Dimension(300, 170));
		
		GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
		pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int y=0;
		
		resolutionLabel = aggiungiLabel(pane, resolutionLabel, "resolutionLabel", Messages.getMessage( MessageKeys.PANEL_SAVERESOLUTION_FILED_SETRESOLUTION), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);		
		String resolutionProperty ="";
		try {
			resolutionProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_USER);
			if (resolutionProperty == null){
				resolutionProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT );
			}
		} catch (Exception e2) {
			LogWriter.writeLog("Errore ", e2);
		}
		resolutionField = aggiungiFieldTesto( pane, resolutionField, 20, "resolutionField", resolutionProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		tipoScansioneLabel = aggiungiLabel(pane, tipoScansioneLabel, "tipoScansioneLabel", Messages.getMessage( MessageKeys.PANEL_SAVERESOLUTION_FILED_SETTIPOSCANSIONE ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START );
		
		String tipoScansioneProperty = "0";
		try {
			tipoScansioneProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SCANNER_TIPOSCANSIONE_USER) == null ? "0" : PreferenceManager.getString( PreferenceKeys.PROPERTY_SCANNER_TIPOSCANSIONE_USER);
		} catch (Exception e2) {
			LogWriter.writeLog("Errore ", e2);
		}
		tipoScansioneCheckBox = aggiungiCheckBox(pane, tipoScansioneCheckBox, "tipoScansioneCheckBox", "                                                          ", tipoScansioneProperty.equalsIgnoreCase(TipoScansioneEnum.BLACKWHITE.getTipoScansioneCode()), true, SwingConstants.RIGHT, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
	
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,5,5);
		add( pane, c1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		savePreference = aggiungiBottone( buttonPanel, savePreference, "savePreference", Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE ), Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE ), true, cButton, 0, 0, 1, 5, 5 );
		savePreference.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salvaConfigurazione();
			}
		});
		
		close = aggiungiBottone( buttonPanel, close, "cancel", Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA ), 
				Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA_TOOLTIP ), true, cButton, 1, 0, 1, 5, 5);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
		
	}

	public void salvaConfigurazione(){
		try {
			int resolution =  Integer.parseInt(resolutionField.getText());
			String tipoScansione = tipoScansioneCheckBox.isSelected() ? TipoScansioneEnum.BLACKWHITE.getTipoScansioneCode() : TipoScansioneEnum.COLOR.getTipoScansioneCode();
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_USER, resolution);
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SCANNER_TIPOSCANSIONE_USER, tipoScansione);
			PreferenceManager.reinitConfig();
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_INVIOLOG_SAVE_SUCCESS ),
					"",
					JOptionPane.INFORMATION_MESSAGE );
			windowDialog.setVisible(false);
		}catch (NumberFormatException e){
			LogWriter.writeLog("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_SAVERESOLUTION_ERROR_RESOLUTION),
					"",
					JOptionPane.ERROR_MESSAGE );
		}catch (Exception e) {
			LogWriter.writeLog("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_ERROR),
					"",
					JOptionPane.ERROR_MESSAGE );
			windowDialog.setVisible(false);
		}
	}
}  
