/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
*
* 	This file is part of JPedal
*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


*
* ---------------
* SwingThumbnailPanel.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.fileOperation.clientws.MappaParametri;
import it.eng.fileOperation.clientws.MappaParametri.Parametro;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro.Pagine;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.util.DigestUtility;
import it.eng.hybrid.module.jpedal.util.ItextFunctions;
import it.eng.hybrid.module.jpedal.util.TimbroUtils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;


public class TimbroPanel extends GenericPanel {

	public final static Logger logger = Logger.getLogger(TimbroPanel.class);
	
	private static final long serialVersionUID = 6560623324914027314L;
	
	private int widthPanel = 400;
	private int heightPanel = 400;
   
	private JFrame windowDialog;
	
	private JButton close;
	private JButton timbroButton;
    
    private JCheckBox timbroPaginaCorrenteField = null;
    private JTextField paginaDaField = null;
    private JTextField paginaAField = null;
    private JLabel posizioneTimbroLabel = null;
    private JLabel rotazioneTimbroLabel = null;
    private JLabel tipoPaginaLabel = null;
    private JLabel paginaDaLabel = null;
    private JLabel paginaALabel = null;
    private JLabel labelErrore = null;
    private JComboBox listaPosizioniTimbro = null;
    private JComboBox listaRotazioniTimbro = null;
    private JComboBox listaTipiPagina = null;
    
    private ActionListener AL2=null;
    private ActionListener AL3=null;
    private ActionListener AL4=null;
    private ActionListener AL5=null;
    private FocusListener FL1=null;
    private FocusListener FL2=null;
   
    private KeyAdapter KA1=null;
    private KeyAdapter KA2=null;
    
    Font textFont=new Font("Serif",Font.PLAIN,12);
    
    private String testoTimbro;
    private String testoIntestazione;
    private String posizioneIntestazione;
    private String posizioneTestoInChiaro;
    private boolean timbroSingolo;
    private String codificaTimbro;
    private boolean addDigest;
    private boolean addDn;
    private boolean addData;
    private String algoritmo;
    private boolean moreLines;
    private String fontName;
    private int fontSize = 8;
   
        
	public TimbroPanel() {
		super();
	}
	
	public TimbroPanel(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public void show(SwingGUI swingGUI){

		windowDialog = new JFrame();
		//windowDialog.setModal(true);

		createOptionWindow(swingGUI);
		
        //windowDialog.setLocationRelativeTo(null);
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	public void createOptionWindow(final SwingGUI gui) {
		
		final PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		initDatiTimbro( preferenceManager );
        
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this, BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );
        
        windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_TIMBRO_TITLE ) );
        
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        pane = new JPanel(); 
       
        JScrollPane scroll = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel pannelloPosizioniTimbro = addPannelloOpzioniPosizioniTimbro( preferenceManager );
		aggiungiPannello( pannelloPosizioniTimbro, c, 0, 0, 1, GridBagConstraints.FIRST_LINE_START );
		JPanel pannelloPagine = addPannelloOpzioniPagine( preferenceManager );
		aggiungiPannello( pannelloPagine, c, 0, 1, 1, GridBagConstraints.FIRST_LINE_START );
		
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.weightx = c1.weighty = 1.0;
		add( pane, c1);
		
		close = new JButton( Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL ) );
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		close.setToolTipText(Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL_TOOLTIP ) );
		
		timbroButton = new JButton( Messages.getMessage( MessageConstants.PANEL_TIMBRO_BUTTON_TIMBRA ) );
		timbroButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if( timbra(gui) ){
						JOptionPane.showMessageDialog(windowDialog,
								Messages.getMessage( MessageConstants.MSG_TIMBRO_SUCCESS ),
								"",
								JOptionPane.INFORMATION_MESSAGE );
					} else {
//						JOptionPane.showMessageDialog(windowDialog,
//								Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_GENERIC ),
//								"",
//								JOptionPane.INFORMATION_MESSAGE );
					}
				} catch (HeadlessException e) {
					JOptionPane.showMessageDialog(windowDialog,
							Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_GENERIC ) + ": " + e.getMessage(),
							"",
							JOptionPane.INFORMATION_MESSAGE );
				} catch (Exception e) {
					JOptionPane.showMessageDialog(windowDialog,
							Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_GENERIC ) + ": " + e.getMessage(),
							"",
							JOptionPane.ERROR_MESSAGE );
				}
				windowDialog.setVisible(false);
			}
		});
		timbroButton.setToolTipText( Messages.getMessage( MessageConstants.PANEL_TIMBRO_BUTTON_TIMBRA_TOOLTIP ) );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		getRootPane().setDefaultButton( timbroButton );
		buttonPanel.add(Box.createHorizontalGlue());
		
		cButton.gridx = 0;
		cButton.gridy = 0;
		cButton.insets = new Insets(5,5,15,5);
		buttonPanel.add( timbroButton, cButton);
		cButton.gridx = 2;
		buttonPanel.add(close, cButton);
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
    }
	
	private void initDatiTimbro(PreferenceManager preferenceManager){
		try {
			testoTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO );
		} catch (Exception e2) {
		}
		if(testoTimbro==null) {
			try {
				testoTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO );
			} catch (Exception e2) {
			}
		}
		
		String addDigestString=null;
		try {
			addDigestString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDIGEST  );
		} catch (Exception e2) {
		}
		if( addDigestString==null ) {
			try {
				addDigestString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDIGEST );
			} catch (Exception e2) {
			}
		}
		addDigest = Boolean.valueOf( addDigestString );

		String addDnString=null;
		try {
			addDnString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDN  );
		} catch (Exception e2) {
		}
		if( addDnString==null ) {
			try {
				addDnString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDN );
			} catch (Exception e2) {
			}
		}
		addDn = Boolean.valueOf( addDnString );
		
		String addDataString=null;
		try {
			addDataString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDATA  );
		} catch (Exception e2) {
		}
		if( addDataString==null ) {
			try {
				addDataString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDATA );
			} catch (Exception e2) {
			}
		}
		addData = Boolean.valueOf( addDataString );
		
		try {
			algoritmo = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ALGORITMO_DIGEST  );
		} catch (Exception e2) {
		}
		if(algoritmo==null) {
			try {
				algoritmo = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ALGORITMO_DIGEST );
			} catch (Exception e2) {
			}
		}
		
		String timbroSingoloString=null;
		try {
			timbroSingoloString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO  );
		} catch (Exception e2) {
		}
		if( timbroSingoloString==null ) {
			try {
				timbroSingoloString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO  );
			} catch (Exception e2) {
			}
		}
		timbroSingolo = Boolean.valueOf( timbroSingoloString );

		try {
			codificaTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_CODIFICA  );
		} catch (Exception e2) {
		}
		if( codificaTimbro==null ) {
			try {
				codificaTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_CODIFICA  );
			} catch (Exception e2) {
			}
		}

		try {
			testoIntestazione = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE );
		} catch (Exception e2) {
		}
		if( testoIntestazione==null ) {
			try {
				testoIntestazione = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE );
			} catch (Exception e2) {
			}
		}
		
		try {
			posizioneIntestazione = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE);
		} catch (Exception e2) {
		}
		if( posizioneIntestazione==null ) {
			try {
				posizioneIntestazione = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE);
			} catch (Exception e2) {
			}
		}
		
		try {
			posizioneTestoInChiaro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO );
		} catch (Exception e2) {
		}
		if( posizioneTestoInChiaro==null ) {
			try {
				posizioneTestoInChiaro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO );
			} catch (Exception e2) {
			}
		}
		
		String moreLinesString = null;
		try {
			moreLinesString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_MORE_LINES );
		} catch (Exception e2) {
		}
		if( moreLinesString==null ) {
			try {
				moreLinesString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MORE_LINES );
			} catch (Exception e2) {
			}
		}
		moreLines = Boolean.valueOf( moreLinesString );

		try {
			fontName = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME);
		} catch (Exception e2) {
		}
		if( fontName==null ) {
			try {
				fontName = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME);
			} catch (Exception e2) {
			}
		}
		
		try {
			fontSize = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE ) );
		} catch (Exception e2) {
		}
		if( fontSize==0 ) {
			try {
				fontSize = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE ) );
			} catch (Exception e2) {
			}
		}
		
	}
	
	private String getTesto(String testo, Hashtable<String,String> params){
		
//		Velocity.init();
//		VelocityContext context = new VelocityContext();
//		
//		Iterator<String> chiavi = params.keySet().iterator();
//		while ( chiavi.hasNext()) {
//			String chiave = chiavi.next();
//			context.put( chiave, params.get(chiave) );
//		}
//		
//		StringWriter writer = new StringWriter();
//	    Velocity.evaluate( context, writer, "", testo);
//	    return writer.toString();
		return "";
	}
	
	private boolean validaPosizioneTesto(PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, PosizioneRispettoAlTimbro posizioneTesto){
		if( posizioneTesto.equals( PosizioneRispettoAlTimbro.INLINEA ) )
			return true;
		else if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOPRA ) ) {
			if( rotazioneTimbro.equals( TipoRotazione.ORIZZONTALE ) ){
				if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX ) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN ))
					return true;
				else 
					return false;
			} else 
				return false;
		} else if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOTTO ) ){
			if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
				return true;
			} else {
				if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX ) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN ))
					return true;
				else 
					return false;
			}
		}
		return false;
	}
	
	private boolean controllaOpzioniTimbro(SwingGUI gui, PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, PosizioneRispettoAlTimbro posizioneIntestazione, PosizioneRispettoAlTimbro posizioneTestoInChiaro, boolean saveOption){
		if( posizioneIntestazione!=null ){ 
			boolean posizioneTestoValida = validaPosizioneTesto( posizioneTimbro, rotazioneTimbro, posizioneIntestazione);
			if( !posizioneTestoValida ){
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTO ),
						Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if( posizioneTestoInChiaro!=null ){ 
			boolean posizioneTestoValida = validaPosizioneTesto(posizioneTimbro, rotazioneTimbro, posizioneTestoInChiaro );
			if( !posizioneTestoValida ){
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTO ),
						Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		if( posizioneIntestazione!=null && posizioneTestoInChiaro!=null && posizioneIntestazione.equals(posizioneTestoInChiaro) ){
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTI ),
					Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE),
					JOptionPane.ERROR_MESSAGE); 
			return false;
		}
		
//		if( gui.getRotation()!=0 ){
//			int response = JOptionPane.showConfirmDialog(this,
//					Messages.getMessage( MessageConstants.MSG_TIMBRO_PDFROTATED ),
//					Messages.getMessage( MessageConstants.MSG_WARNING_TITLE ),
//					JOptionPane.OK_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE);
//			if(response!=JOptionPane.OK_OPTION) 
//				return false;
//		}
		
		return true;
	}
	
	private JPanel addPannelloOpzioniPosizioniTimbro(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelPosizioneTimbro = new JPanel(new GridBagLayout());
		GridBagConstraints cPosizioneTimbro = new GridBagConstraints();
		posizioneTimbroLabel = aggiungiLabel(panelPosizioneTimbro, posizioneTimbroLabel, "posizioneTimbroLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE ), 
				cPosizioneTimbro, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String[] posizioniTimbroSupportate = { PosizioneTimbroNellaPagina.ALTO_DX.value(), PosizioneTimbroNellaPagina.ALTO_SN.value(), 
				PosizioneTimbroNellaPagina.BASSO_DX.value(), PosizioneTimbroNellaPagina.BASSO_SN.value()};
		String posizioneTimbro = null;
		boolean editable = false;
		try {
			posizioneTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE );
			editable = false;
		} catch (Exception e2) {
		}
		if( posizioneTimbro==null ) {
			try {
				posizioneTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaPosizioniTimbro = new JComboBox(posizioniTimbroSupportate);
		aggiungiCombo(panelPosizioneTimbro, listaPosizioniTimbro, posizioniTimbroSupportate, "posizioniTimbro", posizioneTimbro, editable, cPosizioneTimbro, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		rotazioneTimbroLabel = aggiungiLabel(panelPosizioneTimbro, rotazioneTimbroLabel, "rotazioneTimbroLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ROTAZIONE ), 
				cPosizioneTimbro, 0, 2, 1, 5,  margineCampi, GridBagConstraints.LINE_START);
		
		String[] rotazioniSupportate = { TipoRotazione.ORIZZONTALE.value(), TipoRotazione.VERTICALE.value()}; 
		String rotazioneTimbro = null;
		try {
			rotazioneTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE );
			editable = false;
		} catch (Exception e2) {
		}
		if( rotazioneTimbro==null ) {
			try {
				rotazioneTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaRotazioniTimbro = new JComboBox( rotazioniSupportate );
		aggiungiCombo(panelPosizioneTimbro, listaRotazioniTimbro, rotazioniSupportate, "rotazioneTimbro", rotazioneTimbro, editable, cPosizioneTimbro, 1, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
				
		return panelPosizioneTimbro;
	}
	
	private JPanel addPannelloOpzioniPagine(PreferenceManager preferenceManager){
		int margineCampi = 5;
		
		JPanel panelPagina = new JPanel(new GridBagLayout());
		GridBagConstraints cPagina = new GridBagConstraints();
		tipoPaginaLabel = aggiungiLabel(panelPagina, tipoPaginaLabel, "tipoPaginaLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_TIPOPAGINA ), 
				cPagina, 0, 0, 4, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String[] tipiPagine = { "--", TipoPagina.PRIMA.value(), TipoPagina.ULTIMA.value(),  TipoPagina.TUTTE.value()};
		String tipoPagina = null;
		boolean editable = false;
		try {
			tipoPagina = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TIPO_PAGINA );
			editable = false;
		} catch (Exception e2) {
		}
		if( tipoPagina==null ) {
			try {
				tipoPagina = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TIPO_PAGINA );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaTipiPagina = new JComboBox(tipiPagine);
		aggiungiCombo(panelPagina, listaTipiPagina, tipiPagine, "tipiPagine", tipoPagina, editable, cPagina, 0, 1, 2, 5, margineCampi, GridBagConstraints.LINE_START );
		AL2 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (listaTipiPagina.getSelectedIndex() > 0 ){
					timbroPaginaCorrenteField.setSelected( false );
					paginaDaField.setText("");
					paginaAField.setText("");
					labelErrore.setText("");
				}
			}
		};
		listaTipiPagina.addActionListener(AL2);
		
		Boolean paginaCorrente=null;
		String paginaCorrenteString=null;
		try {
			paginaCorrenteString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_CORRENTE );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaCorrenteString==null ) {
			try {
				paginaCorrenteString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_CORRENTE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		paginaCorrente = Boolean.valueOf( paginaCorrenteString );
		timbroPaginaCorrenteField = aggiungiCheckBox( panelPagina, timbroPaginaCorrenteField, "paginaCorrente", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINACORRENTE ), 
				paginaCorrente, editable, SwingConstants.LEFT, cPagina, 2, 1, 2, 5, margineCampi, GridBagConstraints.LINE_START);
		
		AL3 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (timbroPaginaCorrenteField.isSelected() ) {
					paginaDaField.setText("");
					paginaAField.setText("");
					listaTipiPagina.setSelectedIndex(0);
					labelErrore.setText("");
				}
			}
		};
		timbroPaginaCorrenteField.addActionListener(AL3);
		
		paginaDaLabel = aggiungiLabel(panelPagina, paginaDaLabel, "paginaDaLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINADA ), 
				cPagina, 0, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		Integer paginaDa=0;
		String paginaDaS="";
		try {
			paginaDa = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_DA ) );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaDa==0 ) {
			try {
				paginaDa = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_DA ) );
				editable = true;
			} catch (Exception e2) {
			}
		}
		if( paginaDa>0 )
			paginaDaS = String.valueOf( paginaDa.toString() );
		
		paginaDaField = aggiungiFieldTesto( panelPagina, paginaDaField, 3, "paginaDaField", paginaDaS, editable, cPagina, 1, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		paginaALabel = aggiungiLabel(panelPagina, paginaALabel, "paginaALabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINAA ), 
				cPagina, 2, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		Integer paginaA=0;
		String paginaAS="";
		try {
			paginaA = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_A ) );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaA==0 ) {
			try {
				paginaA = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_A ) );
				editable = true;
			} catch (Exception e2) {
			}
		}
		if( paginaA>0 )
			paginaAS = String.valueOf( paginaA.toString() );
		
		paginaAField = aggiungiFieldTesto( panelPagina, paginaAField, 3, "paginaAField", paginaAS, editable, cPagina, 3, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		labelErrore = aggiungiLabel( panelPagina, labelErrore, "labelErrore", "", cPagina, 0, 4, 4, 5, margineCampi, GridBagConstraints.LINE_START);
		KA1 = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c >= '0' && c <= '9') {
                		//paginaDaField.setEditable(true);
                		labelErrore.setText("");
                } else if  ( c != KeyEvent.VK_BACK_SPACE && 
				         c != KeyEvent.VK_DELETE && c != KeyEvent.VK_ENTER) {
                		paginaDaField.setText("");
                		labelErrore.setText("Campo di tipo numerico");
                }
			}
		};
		AL4 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ( paginaDaField.getText()!=null &&  !paginaDaField.getText().equalsIgnoreCase("")) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaDaField.getText()!=null && !paginaDaField.getText().equalsIgnoreCase("") && 
						(paginaAField.getText()==null ||  paginaAField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"A Pagina\" non valorizzato");
				}
			}
		};
		FL1 = new FocusListener(){
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if ( paginaDaField.getText()!=null &&  !paginaDaField.getText().equalsIgnoreCase("")) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaDaField.getText()!=null && !paginaDaField.getText().equalsIgnoreCase("") && 
						(paginaAField.getText()==null ||  paginaAField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText( "Campo \"A Pagina\" non valorizzato" );
				}
			}
		};
		KA2 = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c >= '0' && c <= '9') {
                		labelErrore.setText("");
                } else if (c != KeyEvent.VK_BACK_SPACE && 
				         c != KeyEvent.VK_DELETE && c != KeyEvent.VK_ENTER) {
                		paginaAField.setText("");
                		labelErrore.setText("Campo di tipo numerico");
                }
			}
		};
		AL5 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ( paginaAField.getText()!=null &&  !paginaAField.getText().equalsIgnoreCase("") ) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaAField.getText()!=null && !paginaAField.getText().equalsIgnoreCase("") && 
						(paginaDaField.getText()==null ||  paginaDaField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"Da Pagina\" non valorizzato");
				}
			}
		};
		FL2 = new FocusListener(){
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if ( paginaAField.getText()!=null &&  !paginaAField.getText().equalsIgnoreCase("") ) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaAField.getText()!=null && !paginaAField.getText().equalsIgnoreCase("") && 
						(paginaDaField.getText()==null ||  paginaDaField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"Da Pagina\" non valorizzato");
				}
			}
		};
		paginaDaField.addKeyListener(KA1);
		paginaAField.addKeyListener(KA2);
		paginaDaField.addFocusListener(FL1);
		paginaAField.addFocusListener(FL2);
		paginaDaField.addActionListener(AL4);
		paginaAField.addActionListener(AL5);
		
		return panelPagina;
	}
	
	public void dispose(){

		if(pane!=null)
			pane.removeAll();
		pane = null;
		
	    timbroButton = null;

	    timbroPaginaCorrenteField = null;
	    paginaDaField = null;
	    paginaAField = null;
	    posizioneTimbroLabel = null;
	    rotazioneTimbroLabel = null;
	    tipoPaginaLabel = null;
	    paginaDaLabel = null;
	    paginaALabel = null;
	    labelErrore = null;
	    listaPosizioniTimbro = null;
	    listaRotazioniTimbro = null;
	    listaTipiPagina = null;
	    
	    AL2=null;
	    AL3=null;
	    AL4=null;
	    AL5=null;
	    FL1=null;
	    FL2=null;
	    KA1=null;
	    KA2=null;
	    
	    textFont=null;
	}
	
	public boolean timbra( SwingGUI gui) throws Exception {
		
		PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		String selectedFile = gui.getCommonValues().getSelectedFile();
		logger.info("File da timbrare " + selectedFile);
		File fileDaTimbrare = new File( selectedFile );
		
		String outputFileNameString = "_timbrato";
		try {
			outputFileNameString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_OUTPUTFILENAME );
			if( outputFileNameString==null )
				outputFileNameString = "_timbrato";
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_OUTPUTFILENAME );
		}
		String outputFileName = fileDaTimbrare.getName().replace(".pdf", outputFileNameString+".pdf");
		logger.info("outputFileName " + outputFileName);
		
		int maxLenghtTimbro = 1850;
		try {
			String maxLenghtTimbroString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MAX_LENGHT_TIMBRO );
			maxLenghtTimbro = Integer.parseInt( maxLenghtTimbroString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MAX_LENGHT_TIMBRO );
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MAX_LENGHT_TIMBRO + "=" + maxLenghtTimbro);
		
		int margineSup = 20;
		try {
			String margineSupString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MARGINE_SUP );
			margineSup = Integer.parseInt( margineSupString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MARGINE_SUP);
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MARGINE_SUP + "=" + margineSup);
		
		int margineInf = 20;
		try {
			String margineInfString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MARGINE_INF );
			margineInf = Integer.parseInt( margineInfString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MARGINE_INF );
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MARGINE_INF + "=" + margineInf);
		
		int margineDx = 20;
		try {
			String margineDxString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MARGINE_DX );
			margineDx = Integer.parseInt( margineDxString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MARGINE_DX );
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MARGINE_DX + "=" + margineDx);
		
		int margineSn = 20;
		try {
			String margineSnString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MARGINE_SN );
			margineSn = Integer.parseInt( margineSnString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MARGINE_SN );
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MARGINE_SN + "=" + margineSn);
		
		int margineTimbroTesto = 10;
		try {
			String margineTimbroTestoString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MARGINE_TIMBRO_TESTO );
			margineTimbroTesto = Integer.parseInt( margineTimbroTestoString );
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_MARGINE_TIMBRO_TESTO );
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_MARGINE_TIMBRO_TESTO + "=" + margineTimbroTesto);

		logger.info( "moreLines? " + moreLines );
		
		String fontNameTimbro = BaseFont.COURIER;
		int fontSizeTimbro = 8;
		if( !moreLines ){
			fontNameTimbro = this.fontName;
			fontSizeTimbro = this.fontSize;
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE + "=" + fontSizeTimbro);
		logger.info( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME + "=" + fontNameTimbro);
						
		TimbroUtils timbroUtils = new TimbroUtils( maxLenghtTimbro,fontSizeTimbro,margineSup,margineInf,margineDx,margineSn, margineTimbroTesto,fontNameTimbro );

		logger.info( ConfigConstants.TIMBRO_PROPERTY_CODIFICA + "=" + this.codificaTimbro);
		CodificaTimbro codificaTimbro = CodificaTimbro.fromValue(this.codificaTimbro);
		logger.info( ConfigConstants.TIMBRO_PROPERTY_CODIFICA + "=" + codificaTimbro);
		TipoRotazione rotazioneTimbro = TipoRotazione.fromValue((String)listaRotazioniTimbro.getSelectedItem());
		logger.info( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE + "=" + rotazioneTimbro);
		PosizioneTimbroNellaPagina posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.fromValue((String)listaPosizioniTimbro.getSelectedItem());
		logger.info( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE + "=" + posizioneTimbroNellaPagina);
		String testo = testoTimbro;
		logger.info( ConfigConstants.TIMBRO_PROPERTY_TESTO + "=" + testo);
		
		if( this.addDigest ){
			DigestEncID encId = DigestEncID.BASE_64;
			DigestAlgID algId = DigestAlgID.fromValue( algoritmo );
			String digest = DigestUtility.getDigest( fileDaTimbrare, algId , encId);
			testo += " " + digest;
		}
		if( this.addData ){
			try {
				String formatoData = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_FORMATTAZIONE_DATA );
				SimpleDateFormat sdf = new SimpleDateFormat( formatoData );
				String dataCorrente = sdf.format( new Date() );
				testo += " " + dataCorrente;
			} catch (Exception e1) {
				logger.info("Errore nell'aggiunta della data" );
			}
		}
		if( this.addDn ){
			//verifico se il file da timbrare è firmato
			boolean isFileFirmato = ItextFunctions.isFileFirmato( selectedFile );
			logger.info("Il file da firmare è firmato? " + isFileFirmato );
			if( isFileFirmato ){
				PdfReader reader;
				try {
					reader = new PdfReader( selectedFile );
					AcroFields af = reader.getAcroFields();
					List names = af.getSignatureNames();
					for (int k = 0; k < names.size(); ++k) {
						String name = (String)names.get(k);
						logger.info("Signature name: " + name);
						PdfPKCS7 pkcs7 = af.verifySignature( name );
						X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();
						String dn = cert.getSubjectDN().getName();
						logger.info("Signature dn: " + dn);
						testo += " " + dn;
					}
				} catch (IOException e1) {
					logger.info("Errore " + e1.getMessage(), e1 );
				}
				
			}
		}
		Hashtable<String, String> params = new Hashtable<String, String>();
		
		String listaParametriTimbro = "";
		try {
			listaParametriTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PARAMETER_LIST);
			logger.info( ConfigConstants.TIMBRO_PROPERTY_PARAMETER_LIST + "=" + listaParametriTimbro);
		} catch (Exception e1) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_PARAMETER_LIST );
		}
		if( listaParametriTimbro!=null ){
			MappaParametri mappaParametriTimbro = new MappaParametri();
			StringTokenizer tokenizer = new StringTokenizer(listaParametriTimbro, ";");
			while(tokenizer.hasMoreTokens()){
				String param = tokenizer.nextToken();
				StringTokenizer tokenizerParam = new StringTokenizer(param, "=");
				Parametro p = new Parametro();
				if( tokenizerParam.hasMoreTokens() ) {
					String chiave = tokenizerParam.nextToken();
					logger.info("chiave " + chiave);
					p.setChiave( chiave );
				}
				if( tokenizerParam.hasMoreTokens() ) {
					String valore  = tokenizerParam.nextToken();
					logger.info("valore " + valore);
					p.setValore( valore );
				}
				mappaParametriTimbro.getParametro().add(p);
			}
			
			if( mappaParametriTimbro!=null ){
				List<Parametro> listaParametri = mappaParametriTimbro.getParametro();
				for(Parametro parametro : listaParametri){
					params.put( parametro.getChiave(), parametro.getValore() );
				}
			} 
		}
		testo = getTesto( testo, params);
		logger.info( ConfigConstants.TIMBRO_PROPERTY_TESTO + "=" + testo);
		
		logger.info( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE + "=" + testoIntestazione);
		PosizioneRispettoAlTimbro posizioneIntestazione = null;
		if( !this.posizioneIntestazione.equalsIgnoreCase("--")) { 
			posizioneIntestazione = PosizioneRispettoAlTimbro.fromValue( this.posizioneIntestazione );
			logger.info( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE + "=" + posizioneIntestazione);
		}
		PosizioneRispettoAlTimbro posizioneTestoInChiaro = null;
		if( !this.posizioneTestoInChiaro.equalsIgnoreCase("--")) {
			posizioneTestoInChiaro = PosizioneRispettoAlTimbro.fromValue( this.posizioneTestoInChiaro  );
			logger.info( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO + "=" + posizioneTestoInChiaro);
		}
		logger.info( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO + "=" + timbroSingolo);
		
		PaginaTimbro paginaTimbro = new PaginaTimbro();
		if( timbroPaginaCorrenteField.isSelected() ) {
			Pagine pagine = new Pagine();
			pagine.getPagina().add( gui.getCommonValues().getCurrentPage() );
			logger.info("Timbro la pagina corrente, le altre opzioni sulla pagina da timbrare verranno ignorate" );
			paginaTimbro.setPagine(pagine);
		} else if( paginaDaField.getText()!=null && !paginaDaField.getText().equalsIgnoreCase("") 
				&& paginaAField.getText()!=null && !paginaAField.getText().equalsIgnoreCase("") )  {
			Pagine pagine = new Pagine();
			try{
				int paginaDa = Integer.parseInt( paginaDaField.getText() );
				int paginaA = Integer.parseInt( paginaAField.getText() );
				pagine.setPaginaA( paginaA );
				pagine.setPaginaDa( paginaDa );
				logger.info("Timbro le pagine da " + paginaDa + " a " + paginaA + ", le altre opzioni sulla pagina da timbrare verranno ignorate" );
				paginaTimbro.setPagine(pagine);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog( this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_PAGINAINT ),
						Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		} else if( listaTipiPagina.getSelectedIndex()>0 ){
			String tipoPaginaSelezionato = (String)listaTipiPagina.getSelectedItem();
			if( tipoPaginaSelezionato!=null && !tipoPaginaSelezionato.equalsIgnoreCase("--") ) {
				TipoPagina tipoPagina = TipoPagina.fromValue(tipoPaginaSelezionato);
				logger.info("Timbro le pagine in base al tipo " + tipoPagina + ", le altre opzioni sulla pagina da timbrare verranno ignorate" );
				paginaTimbro.setTipoPagina( tipoPagina  );
			}
		} else {
			JOptionPane.showMessageDialog( this,
					Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_PAGINAUNDEFINED ),
					Messages.getMessage(  MessageConstants.MSG_ERROR_GENERAL_TITLE ),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(controllaOpzioniTimbro(gui, posizioneTimbroNellaPagina, rotazioneTimbro, posizioneIntestazione, posizioneTestoInChiaro, false)){
			try {
				File fileTimbrato = timbroUtils.timbraPdf( fileDaTimbrare, 
						codificaTimbro, testo, paginaTimbro, 
						posizioneTimbroNellaPagina, rotazioneTimbro, posizioneTestoInChiaro, 
						timbroSingolo, testoIntestazione, posizioneIntestazione, 
						fileDaTimbrare.getParentFile() + "/output/", outputFileName, moreLines);
				
				if (fileTimbrato != null ) {
					
					String ext=fileTimbrato.getName().toLowerCase();
					boolean isValid=((ext.endsWith(".pdf"))||(ext.endsWith(".fdf"))||
							(ext.endsWith(".tif"))||(ext.endsWith(".tiff"))||
							(ext.endsWith(".png"))||
							(ext.endsWith(".jpg"))||(ext.endsWith(".jpeg")));
					if(isValid){
						/** save path so we reopen her for later selections */
							gui.getCommonValues().setInputDir(fileTimbrato.getParent());
							gui.getCommonValues().setSelectedFile(fileTimbrato.getAbsolutePath());
							gui.getCommand().openFile(fileTimbrato.getAbsolutePath());
							if(gui.getCommonValues().isExtendedApplet()) {
								byte[] data = IOUtils.toByteArray(FileUtils.openInputStream(fileTimbrato));
								gui.getCommonValues().setByteData(data);
							}
							return true;
					} else{
						
					}
				}
				
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				throw e;
			} catch (UnsupportedEncodingException e) {
				//e.printStackTrace();
				throw e;
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
		return false;
	}

}
