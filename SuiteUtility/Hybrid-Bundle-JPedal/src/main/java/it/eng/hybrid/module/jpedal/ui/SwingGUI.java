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
 * SwingGUI.java
 * ---------------
 */
package it.eng.hybrid.module.jpedal.ui;

import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.hybrid.module.jpedal.acroforms.AcroRenderer;
import it.eng.hybrid.module.jpedal.acroforms.ActionHandler;
import it.eng.hybrid.module.jpedal.acroforms.FormFactory;
import it.eng.hybrid.module.jpedal.acroforms.GUIData;
import it.eng.hybrid.module.jpedal.constants.PropertyConstants;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.external.CustomMessageHandler;
import it.eng.hybrid.module.jpedal.fonts.FontMappings;
import it.eng.hybrid.module.jpedal.fonts.StandardFonts;
import it.eng.hybrid.module.jpedal.fonts.tt.TTGlyph;
import it.eng.hybrid.module.jpedal.io.JAIHelper;
import it.eng.hybrid.module.jpedal.io.LinearThread;
import it.eng.hybrid.module.jpedal.io.PageOffsets;
import it.eng.hybrid.module.jpedal.io.PaperSizes;
import it.eng.hybrid.module.jpedal.io.StatusBar;
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.objects.PdfFileInformation;
import it.eng.hybrid.module.jpedal.objects.PdfLayerList;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.objects.raw.FormObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.parser.DecodeStatus;
import it.eng.hybrid.module.jpedal.parser.DecoderOptions;
import it.eng.hybrid.module.jpedal.parser.ImageCommands;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PropertiesFile;
import it.eng.hybrid.module.jpedal.render.SwingDisplay;
import it.eng.hybrid.module.jpedal.ui.generic.GUIButton;
import it.eng.hybrid.module.jpedal.ui.generic.GUICombo;
import it.eng.hybrid.module.jpedal.ui.generic.GUISearchWindow;
import it.eng.hybrid.module.jpedal.ui.generic.GUIThumbnailPanel;
import it.eng.hybrid.module.jpedal.ui.popup.InvioLogMailPanel;
import it.eng.hybrid.module.jpedal.ui.popup.PreferenceFirmaMarcaPanel;
import it.eng.hybrid.module.jpedal.ui.popup.PreferenceInvioLogPanel;
import it.eng.hybrid.module.jpedal.ui.popup.PreferenceTimbroPanel;
import it.eng.hybrid.module.jpedal.ui.popup.PrintPanel;
import it.eng.hybrid.module.jpedal.ui.popup.SwingProperties;
import it.eng.hybrid.module.jpedal.ui.popup.TimbroPanel;
import it.eng.hybrid.module.jpedal.ui.swing.CommandListener;
import it.eng.hybrid.module.jpedal.ui.swing.FrameCloser;
import it.eng.hybrid.module.jpedal.ui.swing.SearchList;
import it.eng.hybrid.module.jpedal.ui.swing.SwingButton;
import it.eng.hybrid.module.jpedal.ui.swing.SwingCheckBoxMenuItem;
import it.eng.hybrid.module.jpedal.ui.swing.SwingCombo;
import it.eng.hybrid.module.jpedal.ui.swing.SwingID;
import it.eng.hybrid.module.jpedal.ui.swing.SwingMenuItem;
import it.eng.hybrid.module.jpedal.ui.swing.SwingOutline;
import it.eng.hybrid.module.jpedal.ui.swing.SwingSearchWindow;
import it.eng.hybrid.module.jpedal.util.BrowserLauncher;
import it.eng.hybrid.module.jpedal.util.ItextFunctions;
import it.eng.hybrid.module.jpedal.util.OffsetOptions;
import it.eng.hybrid.module.jpedal.util.Options;
import it.eng.hybrid.module.jpedal.util.SwingWorker;
import it.eng.hybrid.module.jpedal.util.WSClientUtils;
import it.eng.hybrid.module.jpedal.util.repositories.Vector_Int;
import it.eng.hybrid.module.jpedal.viewer.Commands;
import it.eng.hybrid.module.jpedal.viewer.Values;
import it.eng.hybrid.module.jpedal.viewer.Viewer;
import it.eng.proxyselector.frame.ProxyFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.itextpdf.text.pdf.PdfReader;
import com.sun.org.apache.xml.internal.serialize.Printer;

/**
 * <br>Description: Swing GUI functions in Viewer
 *
 *
 */
public class SwingGUI extends GUI implements GUIFactory {

	public final static Logger logger = Logger.getLogger(SwingGUI.class);
	
	private boolean previewOnSingleScroll =true;

	private Timer memoryMonitor=null;

	//flag for marks new thumbnail preview
	private boolean debugThumbnail=false;

	ScrollListener scrollListener;

	ScrollMouseListener scrollMouseListener;

	JScrollBar thumbscroll=null;

	private boolean isMultiPageTiff = false;

	static final private boolean enableShowSign = true;

	boolean finishedDecoding=false;
	static int startSize=30,expandedSize=400;


	public static void setStartSize(int startSize) {
		SwingGUI.startSize = startSize;
	}

	//use new GUI layout
	public boolean useNewLayout = true;

	public static String windowTitle;

	String pageTitle,bookmarksTitle, signaturesTitle,layersTitle;
	boolean hasListener=false;
	private boolean isSetup=false;
	int lastTabSelected=-1;
	public boolean messageShown=false;

	/** grabbing cursor */
	private Cursor grabCursor,grabbingCursor,panCursor,panCursorL,panCursorTL,panCursorT,panCursorTR,panCursorR,panCursorBR,panCursorB,panCursorBL;
	public final static int GRAB_CURSOR = 1;
	public final static int GRABBING_CURSOR = 2;
	public final static int DEFAULT_CURSOR = 3;
	public final static int PAN_CURSOR = 4;
	public final static int PAN_CURSORL = 5;
	public final static int PAN_CURSORTL = 6;
	public final static int PAN_CURSORT = 7;
	public final static int PAN_CURSORTR = 8;
	public final static int PAN_CURSORR = 9;
	public final static int PAN_CURSORBR = 10;
	public final static int PAN_CURSORB = 11;
	public final static int PAN_CURSORBL = 12;

	private PaperSizes paperSizes;

	/** Multibox for new GUI Layout*/
	//Component to contain memory, cursor and loading bars
	private JPanel multibox = new JPanel();
	public final static int CURSOR = 1;

	ButtonGroup layoutGroup = new ButtonGroup();
	ButtonGroup searchLayoutGroup = new ButtonGroup();
	ButtonGroup borderGroup = new ButtonGroup();

	/** Constants for glowing border */
	public int glowThickness = 11;
	public Color glowOuterColor = new Color(0.0f, 0.0f, 0.0f ,0.0f);
	public Color glowInnerColor = new Color(0.8f, 0.75f, 0.45f, 0.8f);


	/** Track whether both pages are properly displayed */
	private boolean pageTurnScalingAppropriate =true;

	/** Whether the left page drag or right page drag is drawing */
	private boolean dragLeft = false;
	private boolean dragTop = false;

	/**listener on buttons, menus, combboxes to execute options (one instance on all objects)*/
	private CommandListener currentCommandListener;

	/**holds OPEN, INFO,etc*/
	private JToolBar topButtons = new JToolBar();

	/**holds back/forward buttons at bottom of page*/
	private JToolBar navButtons = new JToolBar();

	/**holds rotation, quality, scaling and status*/
	private JToolBar comboBoxBar = new JToolBar();

	/**holds all menu entries (File, View, Help)*/
	private JMenuBar currentMenu =new JMenuBar();

	/**tell user on first form change it can be saved*/
	private boolean firstTimeFormMessage=true;

	/** visual display of current cursor co-ords on page*/
	private JLabel coords=new JLabel();

	/**root element to hold display*/
	private Container frame=new JFrame();

	/** alternative internal JFrame*/
	private JDesktopPane desktopPane=new JDesktopPane();

	/**flag to disable functions*/
	boolean isSingle=true;

	/**displayed on left to hold thumbnails, bookmarks*/
	private JTabbedPane navOptionsPanel=new JTabbedPane();

	/**split display between PDF and thumbnails/bookmarks*/
	private JSplitPane displayPane;


	/**Scrollpane for pdf panel*/
	private JScrollPane scrollPane = new JScrollPane();

	private Font headFont=new Font("SansSerif",Font.BOLD,14);

	private Font textFont1=new Font("SansSerif",Font.PLAIN,12);

	private Font textFont=new Font("Serif",Font.PLAIN,12);

	/**Interactive display object - needs to be added to PdfDecoder*/
	private StatusBar statusBar=new StatusBar(new Color(235, 154, 0));
	private StatusBar downloadBar=new StatusBar(new Color(185, 209, 0));

	private JLabel pageCounter1;

	//allow user to control messages in Viewer
	CustomMessageHandler customMessageHandler =null;

	public JTextField pageCounter2 = new JTextField(4);

	private JLabel pageCounter3;

	private JLabel optimizationLabel;

	private JTree signaturesTree;

	private JPanel layersPanel=new JPanel();

	/**user dir in which program can write*/
	private String user_dir = System.getProperty( "user.dir" );

	/**stop user forcing open tab before any pages loaded*/
	private boolean tabsNotInitialised=true;
	
	private JToolBar navToolBar = new JToolBar();
	private JToolBar pagesToolBar = new JToolBar();


	//Optional Buttons for menu Search
	public GUIButton nextSearch,previousSearch;

	//layers tab
	PdfLayerList layersObject;

	//Progress bar on nav bar
	private final JProgressBar memoryBar = new JProgressBar();

	//Component to display cursor position on page
	JToolBar cursor = new JToolBar();

	//Buttons on the function bar
	private GUIButton openButton;
	private GUIButton printButton;
	private GUIButton saveButton;
	private GUIButton timbroButton;
	private GUIButton signButton;
	private GUIButton markButton;
	private GUIButton signMarkButton;
	private GUIButton searchButton;
	private GUIButton docPropButton;
	private GUIButton infoButton;
	private GUIButton inviaLogButton;
	public GUIButton mouseMode;

	//Menu items for gui
	private JMenu fileMenu;
	private JMenu openMenu;
	private JMenuItem open;
	private JMenuItem openUrl;
	private JMenuItem save;
	private JMenuItem reSaveAsForms;
	private JMenuItem find;
	private JMenuItem documentProperties;
	private JMenuItem signPDF;
	private JMenuItem markPDF;
	private JMenuItem signMarkPDF;
	private JMenuItem print;
	private JMenuItem timbro;
	private JMenuItem originalFileMenu;
	private JMenuItem inviaLog;
	private JMenuItem exit;
	private JMenu editMenu;
	private JMenuItem copy;
	private JMenuItem selectAll;
	private JMenuItem deselectAll;
	private JMenuItem preferences;
	private JMenu viewMenu;
	private JMenu goToMenu;
	private JMenuItem firstPage;
	private JMenuItem backPage;
	private JMenuItem forwardPage;
	private JMenuItem lastPage;
	private JMenuItem goTo;
	private JMenuItem previousDocument;
	private JMenuItem nextDocument;
	private JMenu pageLayoutMenu;
	private JMenuItem single;
	private JMenuItem continuous;
	private JMenuItem facing;
	private JMenuItem continuousFacing;
	private JMenuItem pageFlow;
	private JMenuItem textSelect;
	private JCheckBoxMenuItem separateCover;
	private JMenuItem panMode;
	private JMenuItem fullscreen;
	private JMenu windowMenu;
	private JMenuItem cascade;
	private JMenuItem tile;
	private JMenu exportMenu;
	private JMenu pdfMenu;
	private JMenuItem onePerPage;
	private JMenuItem nup;
	private JMenuItem handouts;
	private JMenu contentMenu;
	private JMenuItem images;
	private JMenuItem text;
	private JMenuItem bitmap;
	private JMenu pageToolsMenu;
	private JMenuItem rotatePages;
	private JMenuItem deletePages;
	private JMenuItem addPage;
	private JMenuItem addHeaderFooter;
	private JMenuItem stampText;
	private JMenuItem stampImage;
	private JMenuItem crop;
	private JMenu helpMenu;
	private JMenuItem visitWebsite;
	private JMenuItem tipOfTheDay;
	private JMenuItem checkUpdates;
	private JMenuItem about;
	private JMenu preferenceMenu;
	private JMenuItem firmaMarcaPreference;
	private JMenuItem retePreference;
	private JMenuItem timbroPreference;
	private JMenuItem invioLogPreference;
	
	//	private Timer t;

	private  DefaultMutableTreeNode topLayer = new DefaultMutableTreeNode("Layers");


	public SwingGUI(PdfDecoder decode_pdf, Values commonValues, GUIThumbnailPanel thumbnails, PropertiesFile properties) {
		this.decode_pdf = decode_pdf;
		this.commonValues = commonValues;
		this.thumbnails = thumbnails;
		this.properties = properties;

		if (commonValues.isContentExtractor()) {
			titleMessage = "IDRsolutions Extraction Solution " + PdfDecoder.version + ' ';
			showOutlines = false;
		}

		//pass in SwingGUI so we can call via callback
		decode_pdf.addExternalHandler(this,Options.SwingContainer);


		/**
		 * setup display multiview display
		 */
		if (isSingle) {
			desktopPane.setBackground(frame.getBackground());
			//desktopPane.setSize( new Dimension(500, 600) );
			desktopPane.setVisible(true);
			if(frame instanceof JFrame)
				((JFrame)frame).getContentPane().add(desktopPane, BorderLayout.CENTER);
			else
				frame.add(desktopPane, BorderLayout.CENTER);
		}

	}

	public JComponent getDisplayPane() {
		return displayPane;
	}

	public JDesktopPane getMultiViewerFrames(){
		return desktopPane;
	}

	public void setPdfDecoder(PdfDecoder decode_pdf){
		this.decode_pdf = decode_pdf;
	}

	public void closeMultiViewerWindow(String selectedFile) {
		JInternalFrame[] allFrames = desktopPane.getAllFrames();
		for (JInternalFrame internalFrame : allFrames) {
			if (internalFrame.getTitle().equals(selectedFile)) {
				try {
					internalFrame.setClosed(true);
				} catch (PropertyVetoException e) {
				}
				break;
			}
		}
	}

	/**
	 * adjusty x co-ordinate shown in display for user to include
	 * any page centering
	 */
	public int AdjustForAlignment(int cx) {

		if(decode_pdf.getPageAlignment()== Display.DISPLAY_CENTERED){
			int width=decode_pdf.getBounds().width;
			int pdfWidth=decode_pdf.getPDFWidth();

			if(decode_pdf.getDisplayView()!=Display.SINGLE_PAGE)
				pdfWidth=(int)decode_pdf.getMaximumSize().getWidth();

			if(width>pdfWidth)
				cx=cx-((width-pdfWidth)/(2));
		}

		return cx;
	}

	public String getBookmark(String bookmark) {
		return tree.getPage(bookmark);
	}

	public void reinitialiseTabs(boolean showVisible) {
		//not needed
		if(commonValues.getModeOfOperation()==Values.RUNNING_PLUGIN)
			return;

		if(properties.getValue("ShowSidetabbar").toLowerCase().equals("true")){

			if(!isSingle)
				return;

			if(!showVisible)
				displayPane.setDividerLocation(startSize);

			lastTabSelected=-1;

			if(commonValues.isContentExtractor()){
				navOptionsPanel.removeAll();
				displayPane.setDividerLocation(0);
			} else if(!commonValues.isPDF()){
				navOptionsPanel.setVisible(false);
			} else{
				navOptionsPanel.setVisible(true);
				
				if(navOptionsPanel!=null){
					for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
						if(navOptionsPanel.getIconAt(jj)!=null && navOptionsPanel.getIconAt(jj) instanceof VTextIcon){
							navOptionsPanel.removePropertyChangeListener(((VTextIcon)navOptionsPanel.getIconAt(jj)) );
							((VTextIcon)navOptionsPanel.getIconAt(jj)).dispose();
						}
					}
					navOptionsPanel.removeAll();
					
				}
				
				/**
				 * add/remove optional tabs
				 */
//				int thumbnailsIndex = -1;
//				for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
//					if(navOptionsPanel.getIconAt(jj).toString().equals(pageTitle))
//						thumbnailsIndex=jj;
//				}
//				
//				if(thumbnailsIndex==-1){
//					VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, pageTitle, VTextIcon.ROTATE_LEFT);
//					navOptionsPanel.addTab(null, textIcon2, (Component) thumbnails);
//				} else {
//					System.out.println("pannello pagine da visualizzare " + thumbnails.isShownOnscreen() );
//					if(!thumbnails.isShownOnscreen()) {
//						navOptionsPanel.remove(thumbnailsIndex);
//					}
//				}
				
				if(!decode_pdf.hasOutline()){

					int outlineTab=-1;

					if(PdfDecoder.isRunningOnMac){
						for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
							if(navOptionsPanel.getTitleAt(jj).equals(bookmarksTitle))
								outlineTab=jj;
						}
					}else{
						for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
							if(navOptionsPanel.getIconAt(jj).toString().equals(bookmarksTitle))
								outlineTab=jj;
						}
					}

					if(outlineTab!=-1)
						navOptionsPanel.remove(outlineTab);

				} else if(properties.getValue("Bookmarkstab").toLowerCase().equals("true")){
					int outlineTab=-1;
					if(PdfDecoder.isRunningOnMac){
						//String tabName="";
						//see if there is an outlines tab
						for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
							if(navOptionsPanel.getTitleAt(jj).equals(bookmarksTitle))
								outlineTab=jj;
						}

						if(outlineTab==-1)
							navOptionsPanel.addTab(bookmarksTitle,(SwingOutline) tree);
					} else{
						//String tabName="";
						//see if there is an outlines tab
						for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
							if(navOptionsPanel.getIconAt(jj).toString().equals(bookmarksTitle))
								outlineTab=jj;
						}

						if(outlineTab==-1){
							VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, bookmarksTitle, VTextIcon.ROTATE_LEFT);
							navOptionsPanel.addTab(null, textIcon2, (SwingOutline) tree);
						}
					}
				}

				String selectedFile = commonValues.getSelectedFile();
				boolean isFileFirmato = ItextFunctions.isFileFirmato( commonValues.getSelectedFile());
				if( markPDF!=null && markPDF.isVisible() ){
					markButton.setVisible( isFileFirmato );
					markPDF.setVisible( isFileFirmato );
				}

				PdfReader reader;

				if( selectedFile!=null && isFileFirmato) {

					DefaultMutableTreeNode root = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEROOT ) );

					boolean caCheck=true;
					boolean crlCheck=true;
					try {
						caCheck = commonValues.getPreferenceManager().getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK );
						logger.info( "caCheck: " + caCheck );
						crlCheck = commonValues.getPreferenceManager().getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK );
						logger.info( "crlCheck: " + crlCheck );
					} catch (Exception e) {
						logger.info("Errore nel recupero delle proprietà " + ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK + " e " +
								ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK);
					}
					
					Date dataRif = null;
					try {
						String dataRifString = commonValues.getPreferenceManager().getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA );
						logger.info( "dataRifString: " + dataRifString );
						String dataRifFormato = commonValues.getPreferenceManager().getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA_FORMATO );
						logger.info( "dataRifFormato: " + dataRifFormato );
						if( dataRifFormato!=null && dataRifString!=null ){
							SimpleDateFormat sdf = new SimpleDateFormat( dataRifFormato );
							dataRif = sdf.parse( dataRifString );
						}
						
					} catch (Exception e) {
						logger.info("Errore nel recupero delle proprietà " + ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA, e );
					}
					
					
					try {
						FileOperationResponse responseVerificaFirme=null;
						if( commonValues.getResponseVerificaFirme()==null){
							responseVerificaFirme = WSClientUtils.verificaFirme( commonValues.getPreferenceManager(), caCheck, crlCheck, dataRif, new File(selectedFile ) );
							commonValues.setResponseVerificaFirme( responseVerificaFirme );
						} else {
							logger.info("Considero la response di verifica firme in memoria");
							responseVerificaFirme = commonValues.getResponseVerificaFirme();
						}
						
						if( responseVerificaFirme!=null && 	responseVerificaFirme.getFileoperationResponse()!=null 
								&& responseVerificaFirme.getFileoperationResponse().getFileOperationResults()!=null 
								&& responseVerificaFirme.getFileoperationResponse().getFileOperationResults().getFileOperationResult()!=null ){
							List<AbstractResponseOperationType> fileOpResults = responseVerificaFirme.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
							for(AbstractResponseOperationType fileOpResult: fileOpResults){
								if( fileOpResult instanceof ResponseSigVerify){
									logger.info("Costruisco la gerarchia di firme");
									WSClientUtils.processHierarchy( root, ((ResponseSigVerify)fileOpResult).getSigVerifyResult() );
								}
							}
						}
					} catch (Exception e) {
						logger.info("Non posso costruire la rappresentazione grafica della firma", e );
					}
										
					try {
						reader = new PdfReader(commonValues.getSelectedFile());
//						AcroFields af = reader.getAcroFields();
//						List names = af.getSignatureNames();
//						for (int k = 0; k < names.size(); ++k) {
//							String name = (String)names.get(k);
//
//							logger.info("Signature name: " + name);
//							logger.info("Signature covers whole document: " + af.signatureCoversWholeDocument(name));
//							logger.info("Document revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());
//
//							PdfPKCS7 pkcs7 = af.verifySignature( name );
//							boolean isValid = pkcs7.verify();
//							DefaultMutableTreeNode sigName = new DefaultMutableTreeNode("Signature name: " + name + " " + isValid);
//							//root.add(sigName);
//
//							logger.info("Digest algorithm: " + pkcs7.getHashAlgorithm());
//							DefaultMutableTreeNode digestAlgorithm = new DefaultMutableTreeNode("Digest algorithm: " + pkcs7.getHashAlgorithm());
//							sigName.add(digestAlgorithm);
//
//							X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();
//							X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();
//							if( x500name!=null ){
//								DefaultMutableTreeNode subject = new DefaultMutableTreeNode("subject" );
//								sigName.add(subject);													
//								RDN[] cns = x500name.getRDNs(BCStyle.CN);
//								if( cns!=null && cns.length>0){
//									RDN cn = cns[0];
//									if( cn!=null && cn.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("CN=" + IETFUtils.valueToString(cn.getFirst().getValue() ) );
//										subject.add( subjectCn ); 
//									}
//								}
//								RDN[] ous = x500name.getRDNs(BCStyle.OU);
//								if( ous!=null && ous.length>0){
//									RDN ou = ous[0];
//									if( ou!=null && ou.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("OU=" + IETFUtils.valueToString(ou.getFirst().getValue() ) );
//										subject.add( subjectCn ); 
//									}
//								}
//								RDN[] os = x500name.getRDNs(BCStyle.O);
//								if( os!=null && os.length>0){
//									RDN o = os[0];
//									if( o!=null && o.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("O=" + IETFUtils.valueToString(o.getFirst().getValue() ) );
//										subject.add( subjectCn ); 
//									}
//								}
//								RDN[] cs = x500name.getRDNs(BCStyle.C);
//								if( cs!=null && cs.length>0){
//									RDN c = cs[0];
//									if( c!=null && c.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("C=" + IETFUtils.valueToString(c.getFirst().getValue() ) );
//										subject.add( subjectCn ); 
//									}
//								}
//							}
//							X500Name x500nameIssuer = new JcaX509CertificateHolder(cert).getIssuer();
//							if( x500nameIssuer!=null ){
//								DefaultMutableTreeNode issuer = new DefaultMutableTreeNode("Issuer" );
//								sigName.add(issuer);													
//								RDN[] cns = x500nameIssuer.getRDNs(BCStyle.CN);
//								if( cns!=null && cns.length>0){
//									RDN cn = cns[0];
//									if( cn!=null && cn.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("CN=" + IETFUtils.valueToString(cn.getFirst().getValue() ) );
//										issuer.add( subjectCn ); 
//									}
//								}
//								RDN[] ous = x500nameIssuer.getRDNs(BCStyle.OU);
//								if( ous!=null && ous.length>0){
//									RDN ou = ous[0];
//									if( ou!=null && ou.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("OU=" + IETFUtils.valueToString(ou.getFirst().getValue() ) );
//										issuer.add( subjectCn ); 
//									}
//								}
//								RDN[] os = x500nameIssuer.getRDNs(BCStyle.O);
//								if( os!=null && os.length>0){
//									RDN o = os[0];
//									if( o!=null && o.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("O=" + IETFUtils.valueToString(o.getFirst().getValue() ) );
//										issuer.add( subjectCn ); 
//									}
//								}
//								RDN[] cs = x500nameIssuer.getRDNs(BCStyle.C);
//								if( cs!=null && cs.length>0){
//									RDN c = cs[0];
//									if( c!=null && c.getFirst()!=null){
//										DefaultMutableTreeNode subjectCn = new DefaultMutableTreeNode("C=" + IETFUtils.valueToString(c.getFirst().getValue() ) );
//										issuer.add( subjectCn ); 
//									}
//								}
//							}
//
//							DefaultMutableTreeNode type = new DefaultMutableTreeNode("Type");
//							sigName.add(type);
//							String subFilter = pkcs7.getFilterSubtype().toString();
//							DefaultMutableTreeNode subFilterNode = new DefaultMutableTreeNode("Sub Filter: " + subFilter);
//							type.add(subFilterNode);
//
//							SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SS");
//							Calendar calendardate = pkcs7.getSignDate();
//							logger.info("Signed on: " + date_format.format(calendardate.getTime()));
//							DefaultMutableTreeNode signTime = new DefaultMutableTreeNode("Signed on: " + date_format.format(calendardate.getTime()));
//							sigName.add(signTime);
//
//							logger.info("Location: " + pkcs7.getLocation());
//							DefaultMutableTreeNode location = new DefaultMutableTreeNode("Location: " + pkcs7.getLocation());
//							sigName.add(location);
//							logger.info("Reason: " + pkcs7.getReason());
//							DefaultMutableTreeNode reason = new DefaultMutableTreeNode("Reason: " + pkcs7.getReason());
//							sigName.add(reason);
//
//							com.itextpdf.text.pdf.PdfDictionary sigDict = af.getSignatureDictionary(name);
//							PdfString contact = sigDict.getAsString(PdfName.CONTACTINFO);
//							logger.info("Contact info: " + contact);
//							DefaultMutableTreeNode contactNode = new DefaultMutableTreeNode("Contact: " + contact);
//							sigName.add(contactNode);
//
//							TimeStampToken ts = pkcs7.getTimeStampToken();
//							if (ts != null)
//								calendardate = pkcs7.getTimeStampDate();
//							if (!pkcs7.isTsp() && ts != null) {
//								boolean impr = pkcs7.verifyTimestampImprint();
//								DefaultMutableTreeNode timestamp = new DefaultMutableTreeNode("timestamp " + impr);
//								sigName.add(timestamp);
//								DefaultMutableTreeNode timestampInfo = new DefaultMutableTreeNode("timestamp info");
//								timestamp.add(timestampInfo);
//
//								String strdate="";
//								if (calendardate != null) {
//									strdate = date_format.format(calendardate.getTime());
//									logger.info("Timestamp date: " + strdate);
//								} 
//								DefaultMutableTreeNode data = new DefaultMutableTreeNode("Data e ora certificata " + strdate );
//								timestampInfo.add(data);
//								DefaultMutableTreeNode numeroSerieMarca = new DefaultMutableTreeNode("Numero di Serie della Marca " + ts.getTimeStampInfo().getSerialNumber() );
//								timestampInfo.add(numeroSerieMarca);
//								DefaultMutableTreeNode autoritaEmittente = new DefaultMutableTreeNode("Autorita Emittente " + ts.getTimeStampInfo().getTsa() );
//								timestampInfo.add(autoritaEmittente);
//								DefaultMutableTreeNode idPolicy = new DefaultMutableTreeNode("Identificativo della policy " + ts.getTimeStampInfo().getPolicy() );
//								timestampInfo.add(idPolicy);
//							}
//
//						}
//						if( names== null || names.size()==0)
//							removeTab( signaturesTitle );
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
//						catch (GeneralSecurityException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					//if(signaturesTree==null){
					signaturesTree = new JTree();

					SignaturesTreeCellRenderer treeCellRenderer = new SignaturesTreeCellRenderer();
					signaturesTree.setCellRenderer(treeCellRenderer);
					//}
					if(root.children().hasMoreElements()){
						((DefaultTreeModel)signaturesTree.getModel()).setRoot(root);
					} else {
						signaturesTree  = null;
					}

					checkTabShown(signaturesTitle, true);
				}else
					removeTab(signaturesTitle);

				
				/**
				 * add a control Panel to enable/disable layers
				 */
				//layers object
				layersObject=(PdfLayerList)decode_pdf.getJPedalObject(PdfDictionary.Layer);

				if(layersObject != null && layersObject.getLayersCount()>0){ 

					layersPanel.removeAll();

					layersPanel.setLayout(new BorderLayout());

					checkTabShown(layersTitle, false);

					/**
					 * add metadata to tab (Map of key values) as a Tree
					 */
					DefaultMutableTreeNode top = new DefaultMutableTreeNode("Info");

					Map metaData=layersObject.getMetaData();

					Iterator metaDataKeys=metaData.keySet().iterator();
					Object nextKey, value;
					while(metaDataKeys.hasNext()){

						nextKey=metaDataKeys.next();
						value=metaData.get(nextKey);

						top.add(new DefaultMutableTreeNode(nextKey+"="+value));

					}

					//add collapsed Tree at Top
					final JTree infoTree = new JTree(top);
					infoTree.setToolTipText("Double click to see any metadata");
					infoTree.setRootVisible(true);
					infoTree.collapseRow(0);
					layersPanel.add(infoTree, BorderLayout.NORTH);

					/**
					 * Display list of layers which can be recursive
					 * layerNames can contain comments or sub-trees as Object[] or String name of Layer
					 */
					final Object[] layerNames=layersObject.getDisplayTree();
					if(layerNames!=null){

						topLayer.removeAllChildren();

						final JTree layersTree = new JTree(topLayer);
						layersTree.setName("LayersTree");

						//Listener to redraw with altered layer
						layersTree.addTreeSelectionListener(new TreeSelectionListener() {

							public void valueChanged(TreeSelectionEvent e) {

								final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
										layersTree.getLastSelectedPathComponent();

								/* exit if nothing is selected */
								if (node == null)
									return;

								/* retrieve the full name of Layer that was selected */
								String rawName = (String)node.getUserObject();

								//and add path
								Object[] patentNames = ((DefaultMutableTreeNode)node.getParent()).getUserObjectPath();
								int size= patentNames.length;
								for(int jj=size-1;jj>0;jj--){ //note I ingore 0 which is root and work backwards
									rawName=rawName+PdfLayerList.deliminator+ patentNames[jj].toString();
								}

								final String name=rawName;

								//if allowed toggle and update display
								if(layersObject.isLayerName(name) && !layersObject.isLocked(name)){

									//toggle layer status when clicked
									Runnable updateAComponent = new Runnable() {

										public void run() {
											decode_pdf.setPDFCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
											//force refresh
											decode_pdf.invalidate();
											decode_pdf.updateUI();
											decode_pdf.validate();

											scrollPane.invalidate();
											scrollPane.updateUI();
											scrollPane.validate();

											//update settings on display and in PdfDecoder
											CheckNode checkNode=(CheckNode)node;

											if(!checkNode.isEnabled()){ //selection not allowed so display info message

												checkNode.setSelected(checkNode.isSelected());
												ShowGUIMessage.showstaticGUIMessage(new StringBuffer("This layer has been disabled because its parent layer is disabled"),"Parent Layer disabled");
											}else{
												boolean reversedStatus=!checkNode.isSelected();
												checkNode.setSelected(reversedStatus);
												layersObject.setVisiblity(name,reversedStatus);

												//may be radio buttons which disable others so sync values
												//before repaint
												syncTreeDisplay(topLayer,true);

												//decode again with new settings
												try {
													decode_pdf.decodePage(commonValues.getCurrentPage());
												} catch (Exception e) {
													e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
												}

											}
											//deselect so works if user clicks on same again to deselect
											layersTree.invalidate();
											layersTree.clearSelection();
											layersTree.repaint();
											decode_pdf.setPDFCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
										}
									};

									SwingUtilities.invokeLater(updateAComponent);

								}
							}
						});

						//build tree from values
						topLayer.removeAllChildren();
						addLayersToTree(layerNames, topLayer, true);

						layersTree.setRootVisible(true);
						layersTree.expandRow(0);
						layersTree.setCellRenderer(new CheckRenderer());
						layersTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

						layersPanel.add(layersTree, BorderLayout.CENTER);

					}

				} else
					removeTab(layersTitle);

				setBookmarks(false);
			}

			//@kieran this is temporary but works for now.
			//DisplayPane adjustment
			int orig = displayPane.getDividerLocation();
			displayPane.setDividerLocation(0);
			displayPane.validate();
			displayPane.setDividerLocation(orig);

		}
	}

	private void syncTreeDisplay(DefaultMutableTreeNode topLayer, boolean isEnabled) {
		int count=topLayer.getChildCount();

		boolean parentIsEnabled =isEnabled, isSelected;
		String childName="";
		TreeNode childNode;
		int ii=0;
		while(true){

			isEnabled= parentIsEnabled;
			isSelected=true;

			if(count==0)
				childNode=topLayer;
			else
				childNode=topLayer.getChildAt(ii);

			if(childNode instanceof CheckNode){

				CheckNode cc=(CheckNode)childNode;
				childName=(String)cc.getText();

				if(layersObject.isLayerName(childName)){

					if(isEnabled)
						isEnabled=!layersObject.isLocked(childName);

					isSelected=(layersObject.isVisible(childName));
					cc.setSelected(isSelected);
					cc.setEnabled(isEnabled);
				}
			}

			if(childNode.getChildCount()>0){

				Enumeration children=childNode.children();
				while(children.hasMoreElements())
					syncTreeDisplay((DefaultMutableTreeNode)children.nextElement(), (isEnabled && isSelected));
			}

			ii++;
			if(ii>=count)
				break;
		}
	}

	private void addLayersToTree(Object[] layerNames, DefaultMutableTreeNode topLayer, boolean isEnabled) {

		String name;

		DefaultMutableTreeNode currentNode=topLayer;
		boolean parentEnabled=isEnabled,parentIsSelected=true;

		for (Object layerName : layerNames) {

			//work out type of node and handle
			if (layerName instanceof Object[]) { //its a subtree

				DefaultMutableTreeNode oldNode = currentNode;

				addLayersToTree((Object[]) layerName, currentNode, isEnabled && parentIsSelected);

				currentNode = oldNode;
				//if(currentNode!=null)
				//currentNode= (DefaultMutableTreeNode) currentNode.getParent();

				isEnabled = parentEnabled;
			} else {

				//store possible recursive settings
				parentEnabled = isEnabled;

				if (layerName == null)
					continue;

				if (layerName instanceof String)
					name = (String) layerName;
				else //its a byte[]
					name = new String((byte[]) layerName);

				/**
				 * remove full path in name
				 */
				String title = name;
				int ptr = name.indexOf(PdfLayerList.deliminator);
				if (ptr != -1) {
					title = title.substring(0, ptr);

				}

				if (name.endsWith(" R")) { //ignore
				} else if (!layersObject.isLayerName(name)) { //just text

					currentNode = new DefaultMutableTreeNode(title);
					topLayer.add(currentNode);
					topLayer = currentNode;

					parentIsSelected = true;

					//add a node
				} else if (topLayer != null) {

					currentNode = new CheckNode(title);
					topLayer.add(currentNode);

					//see if showing and set box to match
					if (layersObject.isVisible(name)) {
						((CheckNode) currentNode).setSelected(true);
						parentIsSelected = true;
					} else
						parentIsSelected = false;

					//check locks and allow Parents to disable children
					if (isEnabled)
						isEnabled = !layersObject.isLocked(name);

					((CheckNode) currentNode).setEnabled(isEnabled);
				}
			}
		}
	}

	private void checkTabShown(String title, boolean refresh) {
		int outlineTab=-1;
		if(PdfDecoder.isRunningOnMac){

			//see if there is an outlines tab
			for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
				if(navOptionsPanel.getTitleAt(jj).equals(title))
					outlineTab=jj;
			}

			if(outlineTab==-1){
				if(title.equals(signaturesTitle) && properties.getValue("Signaturestab").toLowerCase().equals("true")){
					if(signaturesTree==null){
						signaturesTree = new JTree();

						SignaturesTreeCellRenderer treeCellRenderer = new SignaturesTreeCellRenderer();
						signaturesTree.setCellRenderer(treeCellRenderer);

					}
					navOptionsPanel.addTab(signaturesTitle, signaturesTree);
					navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, signaturesTitle);

				}else if(title.equals(layersTitle) && properties.getValue("Layerstab").toLowerCase().equals("true")){

					JScrollPane scrollPane=new JScrollPane();
					scrollPane.getViewport().add(layersPanel);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					navOptionsPanel.addTab(layersTitle, scrollPane);
					navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, layersTitle);

				}
			}

		} else{
			//see if there is an outlines tab
			for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
				if(navOptionsPanel.getIconAt(jj).toString().equals(title))
					outlineTab=jj;
			}
			if(outlineTab==-1 || refresh ){

				if(title.equals(signaturesTitle) && properties.getValue("Signaturestab").toLowerCase().equals("true")){  //stop spurious display of Sig tab
					VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, signaturesTitle, VTextIcon.ROTATE_LEFT);
					if( signaturesTree!=null && ((DefaultMutableTreeNode)((DefaultTreeModel)signaturesTree.getModel()).getRoot()).children().hasMoreElements() ) {
						if( outlineTab!=-1)
							navOptionsPanel.removeTabAt( outlineTab );
						navOptionsPanel.addTab(null, textIcon2, signaturesTree);
					}
				}else if(title.equals(layersTitle) && properties.getValue("Layerstab").toLowerCase().equals("true")){
					VTextIcon textIcon = new VTextIcon(navOptionsPanel, layersTitle, VTextIcon.ROTATE_LEFT);

					JScrollPane scrollPane=new JScrollPane();
					scrollPane.getViewport().add(layersPanel);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					navOptionsPanel.addTab(null, textIcon, scrollPane);
				}
				//navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, layersTitle);
			}
		}
	}

	private void removeTab(String title) {


		int outlineTab=-1;

		if(PdfDecoder.isRunningOnMac){
			//String tabName="";
			//see if there is an outlines tab
			for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
				if(navOptionsPanel.getTitleAt(jj).equals(title))
					outlineTab=jj;
			}
		}else{
			//String tabName="";
			//see if there is an outlines tab
			for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
				if(navOptionsPanel.getIconAt(jj).toString().equals(title))
					outlineTab=jj;
			}
		}

		if(outlineTab!=-1)
			navOptionsPanel.remove(outlineTab);

	}

	public void stopThumbnails() {

		if(!isSingle)
			return;

		if(thumbnails.isShownOnscreen()){
			/** if running terminate first */
			thumbnails.terminateDrawing();

			thumbnails.removeAllListeners();

		}
	}

	public void reinitThumbnails() {

		isSetup=false;

	}

	/**reset so appears closed*/
	public void resetNavBar() {
		if(!properties.getValue("consistentTabBar")
				.toLowerCase().equals("true")){

			if(!isSingle)
				return;

			displayPane.setDividerLocation(startSize);
			tabsNotInitialised=true;

			//also reset layers
			topLayer.removeAllChildren();

			//disable page view buttons until we know we have multiple pages
			if(!commonValues.isContentExtractor())
				setPageLayoutButtonsEnabled(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setNoPagesDecoded()
	 *
	 * Called when new file opened so we set flags here
	 */
	public void setNoPagesDecoded() {


		bookmarksGenerated=false;

		resetNavBar();

		//Ensure preview from last file doesn't appear
		if(scrollListener!=null)
			scrollListener.lastImage = null;

		pagesDecoded.clear();

	}

	public void setBackNavigationButtonsEnabled(boolean flag) {

		//		if(!isSingle)
		//		return;

		back.setEnabled(flag);
		first.setEnabled(flag);
		fback.setEnabled(flag);

	}

	public void setForwardNavigationButtonsEnabled(boolean flag) {

		//		if(!isSingle)
		//		return;

		forward.setEnabled(flag);
		end.setEnabled(flag);
		fforward.setEnabled(flag);


	}

	public void setPageLayoutButtonsEnabled(boolean flag) {

		if(!isSingle)
			return;

		continuousButton.setEnabled(flag);
		continuousFacingButton.setEnabled(flag);
		facingButton.setEnabled(flag);

		pageFlowButton.setEnabled(flag);


		Enumeration menuOptions=layoutGroup.getElements();

		//@kieran - added fix below. Can you recode without Enumeration
		//object please (several refs) so we can keep 1.4 compatability.

		//export menu is broken in standalone (works in IDE). Is this related?

		//we cannot assume there are values so trap to avoid exception
		if(menuOptions.hasMoreElements()){

			//first one is always ON
			((JMenuItem)menuOptions.nextElement()).setEnabled(true);

			//set other menu items
			while(menuOptions.hasMoreElements())
				((JMenuItem)menuOptions.nextElement()).setEnabled(flag);
		}

	}

	public void setSearchLayoutButtonsEnabled() {
		Enumeration menuOptions=searchLayoutGroup.getElements();

		//first one is always ON
		((JMenuItem)menuOptions.nextElement()).setEnabled(true);

		//set other menu items
		while(menuOptions.hasMoreElements()){
			((JMenuItem)menuOptions.nextElement()).setEnabled(true);
		}

	}

	public void alignLayoutMenuOption(int mode) {

		//reset rotation
		//rotation=0;
		//setSelectedComboIndex(Commands.ROTATION,0);

		int i=1;

		Enumeration menuOptions=layoutGroup.getElements();

		//cycle to correct value
		while(menuOptions.hasMoreElements() && i!=mode){
			menuOptions.nextElement();
			i++;
		}

		//choose item
		((JMenuItem)menuOptions.nextElement()).setSelected(true);
	}

	public void setDisplayMode(Integer mode) {

		if(mode.equals(GUIFactory.MULTIPAGE))
			isSingle=false;

	}

	public boolean isSingle() {
		return isSingle;
	}

	public Object getThumbnailPanel() {
		return thumbnails;
	}

	public Object getOutlinePanel() {
		return tree;  
	}

	/**used when clicking on thumbnails to move onto new page*/
	private class PageChanger implements ActionListener {

		int page;
		public PageChanger(int i){
			i++;
			page=i;
		}

		public void actionPerformed(ActionEvent e) {
			if((!Values.isProcessing())&&(commonValues.getCurrentPage()!=page)){

				//if loading on linearized thread, see if we can actually display
				if(!decode_pdf.isPageAvailable(page)){
					showMessageDialog("Page "+page+" is not yet loaded");
					return;
				}
				//                commonValues.setCurrentPage(page);
				//
				statusBar.resetStatus("");
				//
				//				//setScalingToDefault();
				//
				//				//decode_pdf.setPageParameters(getScaling(), commonValues.getCurrentPage());
				//
				//				decodePage(false);
				currentCommands.gotoPage(Integer.toString(page));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#initLayoutMenus(javax.swing.JMenu, java.lang.String[], int[])
	 */
	public void initLayoutMenus(JMenu pageLayout, String[] descriptions, int[] value) {

		int count=value.length;
		for(int i=0;i<count;i++){
			JCheckBoxMenuItem pageView=new JCheckBoxMenuItem(descriptions[i]);
			pageView.setBorder(BorderFactory.createEmptyBorder());
			layoutGroup.add(pageView);
			if(i==0)
				pageView.setSelected(true);

			if(pageLayout!=null){

				switch(value[i]){
				case Display.SINGLE_PAGE :
					single = pageView;
					single.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							currentCommands.executeCommand(Commands.SINGLE, null);
						}
					});
					pageLayout.add(single);
					break;
				case Display.CONTINUOUS :
					continuous = pageView;
					continuous.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							currentCommands.executeCommand(Commands.CONTINUOUS, null);
						}
					});
					pageLayout.add(continuous);
					break;
				case Display.FACING :
					facing = pageView;
					facing.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							currentCommands.executeCommand(Commands.FACING, null);
						}
					});
					pageLayout.add(facing);
					break;
				case Display.CONTINUOUS_FACING :
					continuousFacing = pageView;
					continuousFacing.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							currentCommands.executeCommand(Commands.CONTINUOUS_FACING, null);
						}
					});
					pageLayout.add(continuousFacing);
					break;


				case Display.PAGEFLOW :
					if(JAIHelper.isJAIOnClasspath()){
						pageFlow = pageView;
						pageFlow.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								currentCommands.executeCommand(Commands.PAGEFLOW, null);
							}
						});
						pageLayout.add(pageFlow);
					}
					break;

				}
			}
		}

		if(!isSingle)
			return;

		//default is off
		setPageLayoutButtonsEnabled(false);


	}

	/**
	 * show fonts on system displayed
	 */
	private static JScrollPane getFontsAliasesInfoBox(){

		JPanel details=new JPanel();

		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,300));
		scrollPane.getViewport().add(details);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		details.setOpaque(true);
		details.setBackground(Color.white);
		details.setEnabled(false);
		details.setLayout(new BoxLayout(details, BoxLayout.PAGE_AXIS));

		/**
		 * list of all fonts fonts
		 */
		StringBuilder fullList=new StringBuilder();


		for (Object nextFont : FontMappings.fontSubstitutionAliasTable.keySet()) {
			fullList.append(nextFont);
			fullList.append(" ==> ");
			fullList.append(FontMappings.fontSubstitutionAliasTable.get(nextFont));
			fullList.append('\n');
		}


		String xmlText=fullList.toString();
		if(xmlText.length()>0){

			JTextArea xml=new JTextArea();
			xml.setLineWrap(false);
			xml.setText(xmlText);
			details.add(xml);
			xml.setCaretPosition(0);
			xml.setOpaque(false);

			details.add(Box.createRigidArea(new Dimension(0,5)));
		}

		return scrollPane;
	}

	//Font tree Display pane
	JScrollPane fontScrollPane=new JScrollPane();


	boolean sortFontsByDir = true;

	//<link><a name="fontdetails" />
	/**
	 * show fonts on system displayed
	 */
	private JPanel getFontsFoundInfoBox(){

		//Create font list display area
		JPanel fontDetails=new JPanel(new BorderLayout());
		fontDetails.setBackground(Color.WHITE);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		fontScrollPane.setBackground(Color.WHITE);
		fontScrollPane.getViewport().setBackground(Color.WHITE);
		fontScrollPane.setPreferredSize(new Dimension(400,300));
		fontScrollPane.getViewport().add(fontDetails);
		fontScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fontScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//This allows the title to be centered above the filter box
		JPanel filterTitlePane = new JPanel();
		filterTitlePane.setBackground(Color.WHITE);
		JLabel filterTitle = new JLabel("Filter Font List");
		filterTitlePane.add(filterTitle);

		//Create buttons
		ButtonGroup bg = new ButtonGroup();
		JRadioButton folder = new JRadioButton("Sort By Folder");
		folder.setBackground(Color.WHITE);
		JRadioButton name = new JRadioButton("Sort By Name");
		name.setBackground(Color.WHITE);
		final JTextField filter = new JTextField();

		//Ensure correct display mode selected
		if(sortFontsByDir==true)
			folder.setSelected(true);
		else
			name.setSelected(true);

		bg.add(folder);
		bg.add(name);
		JPanel buttons = new JPanel(new BorderLayout());
		buttons.setBackground(Color.WHITE);
		buttons.add(filterTitlePane, BorderLayout.NORTH);
		buttons.add(folder, BorderLayout.WEST);
		buttons.add(filter, BorderLayout.CENTER);
		buttons.add(name, BorderLayout.EAST);

		folder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!sortFontsByDir){
					DefaultMutableTreeNode fontlist = new DefaultMutableTreeNode("Fonts");
					sortFontsByDir = true;
					fontlist = populateAvailableFonts(fontlist, filter.getText());
					displayAvailableFonts(fontlist);
				}
			}
		});

		name.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(sortFontsByDir){
					DefaultMutableTreeNode fontlist = new DefaultMutableTreeNode("Fonts");
					sortFontsByDir = false;
					fontlist = populateAvailableFonts(fontlist, filter.getText());
					displayAvailableFonts(fontlist);
				}
			}
		});

		filter.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				DefaultMutableTreeNode fontlist = new DefaultMutableTreeNode("Fonts");
				populateAvailableFonts(fontlist, ((JTextField)e.getSource()).getText());
				displayAvailableFonts(fontlist);
			}
			public void keyTyped(KeyEvent e) {}
		});

		//Start tree here
		DefaultMutableTreeNode top =
				new DefaultMutableTreeNode("Fonts");

		//Populate font list and build tree
		top = populateAvailableFonts(top, null);
		JTree fontTree = new JTree(top);
		//Added to keep the tree left aligned when top parent is closed
		fontDetails.add(fontTree, BorderLayout.WEST);


		//Peice it all together
		panel.add(buttons, BorderLayout.NORTH);
		panel.add(fontScrollPane, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(400,300));

		return panel;
	}

	private void displayAvailableFonts(DefaultMutableTreeNode fontlist){

		//Remove old font tree display panel
		fontScrollPane.getViewport().removeAll();

		//Create new font list display
		JPanel jp = new JPanel(new BorderLayout());
		jp.setBackground(Color.WHITE);
		jp.add(new JTree(fontlist), BorderLayout.WEST);

		//Show font tree
		fontScrollPane.getViewport().add(jp);
	}

	/**
	 * list of all fonts properties in sorted order
	 */
	private DefaultMutableTreeNode populateAvailableFonts(DefaultMutableTreeNode top, String filter){

		//get list
		if(FontMappings.fontSubstitutionTable!=null){
			Set fonts=FontMappings.fontSubstitutionTable.keySet();
			Iterator fontList=FontMappings.fontSubstitutionTable.keySet().iterator();

			int fontCount=fonts.size();
			ArrayList fontNames=new ArrayList(fontCount);

			while(fontList.hasNext())
				fontNames.add(fontList.next().toString());

			//sort
			Collections.sort(fontNames);

			//Sort and Display Fonts by Directory
			if(sortFontsByDir){

				List Location = new ArrayList();
				List LocationNode = new ArrayList();

				//build display
				for(int ii=0;ii<fontCount;ii++){
					Object nextFont=fontNames.get(ii);

					String current = ((String)FontMappings.fontSubstitutionLocation.get(nextFont));

					int ptr=current.lastIndexOf(System.getProperty("file.separator"));
					if(ptr==-1 && current.indexOf('/')!=-1)
						ptr=current.lastIndexOf('/');

					if(ptr!=-1)
						current = current.substring(0, ptr);

					if(filter==null || ((String) nextFont).toLowerCase().contains(filter.toLowerCase())){
						if(!Location.contains(current)){
							Location.add(current);
							DefaultMutableTreeNode loc = new DefaultMutableTreeNode(new DefaultMutableTreeNode(current));
							top.add(loc);
							LocationNode.add(loc);
						}

						DefaultMutableTreeNode FontTop = new DefaultMutableTreeNode(nextFont+" = "+FontMappings.fontSubstitutionLocation.get(nextFont));
						int pos = Location.indexOf(current);
						((DefaultMutableTreeNode)LocationNode.get(pos)).add(FontTop);


						//add details
						String loc=(String)FontMappings.fontPropertiesTable.get(nextFont+"_path");
						Integer type=(Integer)FontMappings.fontPropertiesTable.get(nextFont+"_type");

						Map properties=StandardFonts.getFontDetails(type, loc);
						if(properties!=null){

							for (Object key : properties.keySet()) {
								Object value = properties.get(key);

								//JLabel fontString=new JLabel(key+" = "+value);
								//fontString.setFont(new Font("Lucida",Font.PLAIN,10));
								//details.add(fontString);
								DefaultMutableTreeNode FontDetails = new DefaultMutableTreeNode(key + " = " + value);
								FontTop.add(FontDetails);

							}
						}
					}
				}
			}else{//Show all fonts in one list

				//build display
				for(int ii=0;ii<fontCount;ii++){
					Object nextFont=fontNames.get(ii);

					if(filter==null || ((String) nextFont).toLowerCase().contains(filter.toLowerCase())){
						DefaultMutableTreeNode FontTop = new DefaultMutableTreeNode(nextFont+" = "+FontMappings.fontSubstitutionLocation.get(nextFont));
						top.add(FontTop);

						//add details
						Map properties=(Map)FontMappings.fontPropertiesTable.get(nextFont);
						if(properties!=null){

							for (Object key : properties.keySet()) {
								Object value = properties.get(key);

								//JLabel fontString=new JLabel(key+" = "+value);
								//fontString.setFont(new Font("Lucida",Font.PLAIN,10));
								//details.add(fontString);
								DefaultMutableTreeNode FontDetails = new DefaultMutableTreeNode(key + " = " + value);
								FontTop.add(FontDetails);

							}
						}
					}
				}
			}
		}
		return top;
	}

	/**
	 * show fonts displayed
	 */
	private JScrollPane getFontInfoBox() {

		JPanel details = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400, 300));
		scrollPane.getViewport().add(details);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		details.setOpaque(true);
		details.setBackground(Color.white);
		details.setEnabled(false);
		details.setLayout(new BoxLayout(details, BoxLayout.PAGE_AXIS));

		/**
		 * list of fonts
		 */
		 String xmlTxt = decode_pdf.getInfo(PdfDictionary.Font);
		 String xmlText = "Font Substitution mode: ";

		 switch (FontMappings.getFontSubstitutionMode()) {
		 case (1):
			 xmlText = xmlText + "using file name";
		 break;
		 case (2):
			 xmlText = xmlText + "using PostScript name";
		 break;
		 case (3):
			 xmlText = xmlText + "using family name";
		 break;
		 case (4):
			 xmlText = xmlText + "using the full font name";
		 break;
		 default:
			 xmlText = xmlText + "Unknown FontSubstitutionMode";
			 break;
		 }

		 xmlText = xmlText + '\n';




		 if (xmlTxt.length() > 0) {

			 JTextArea xml = new JTextArea();
			 JLabel mode = new JLabel();

			 mode.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			 mode.setText(xmlText);
			 mode.setForeground(Color.BLUE);

			 xml.setLineWrap(false);
			 xml.setForeground(Color.BLACK);
			 xml.setText('\n' + xmlTxt);


			 details.add(mode);
			 details.add(xml);

			 xml.setCaretPosition(0);
			 xml.setOpaque(false);

			 //details.add(Box.createRigidArea(new Dimension(0,5)));
		 }

		 return scrollPane;
	}

	/**
	 * show fonts displayed
	 */
	private JScrollPane getImageInfoBox() {

		/**
		 * the generic panel details
		 */
		JPanel details = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400, 300));
		scrollPane.getViewport().add(details);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		details.setOpaque(true);
		details.setBackground(Color.white);
		details.setEnabled(false);
		details.setLayout(new BoxLayout(details, BoxLayout.PAGE_AXIS));

		/**
		 * list of Images (not forms) 
		 */
		String xmlTxt = decode_pdf.getInfo(PdfDictionary.Image);


		//and display in container
		if (xmlTxt.length() > 0) {

			JTextArea xml = new JTextArea();

			xml.setLineWrap(false);
			xml.setForeground(Color.BLACK);
			xml.setText('\n' + xmlTxt);

			details.add(xml);

			xml.setCaretPosition(0);
			xml.setOpaque(false);

			//details.add(Box.createRigidArea(new Dimension(0,5)));
		}

		return scrollPane;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getInfoBox()
	 */
	public void getInfoBox() {

		//<start-wrap>
		final JPanel details = new JPanel();
		details.setPreferredSize(new Dimension(400, 260));
		details.setOpaque(false);
		details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

		//general details
		JLabel header1 = new JLabel(Messages.getMessage("PdfViewerInfo.title"));
		header1.setOpaque(false);
		header1.setFont(headFont);
		header1.setAlignmentX(Component.CENTER_ALIGNMENT);
		details.add(header1);

		details.add(Box.createRigidArea(new Dimension(0, 15)));

		String xmlText = Messages.getMessage("PdfViewerInfo1");
		if (xmlText.length() > 0) {

			JTextArea xml = new JTextArea();
			xml.setFont(textFont1);
			xml.setOpaque(false);
			xml.setText(xmlText + "\n\nVersions\n JPedal: " + PdfDecoder.version + "          " + "Java: " + System.getProperty("java.version"));
			xml.setLineWrap(true);
			xml.setWrapStyleWord(true);
			xml.setEditable(false);
			details.add(xml);
			xml.setAlignmentX(Component.CENTER_ALIGNMENT);

		}

		ImageIcon logo = new ImageIcon(getClass().getResource("/it/eng/hybrid/module/jpedal/resources/logo.gif"));
		details.add(Box.createRigidArea(new Dimension(0, 25)));
		JLabel idr = new JLabel(logo);
		idr.setAlignmentX(Component.CENTER_ALIGNMENT);
		details.add(idr);

		final JLabel url = new JLabel("<html><center>" + Messages.getMessage("PdfViewerJpedalLibrary.Text")
				+ Messages.getMessage("PdfViewer.WebAddress"));
		url.setForeground(Color.blue);
		url.setHorizontalAlignment(JLabel.CENTER);
		url.setAlignmentX(Component.CENTER_ALIGNMENT);

		//@kieran - cursor
		url.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					details.setCursor(new Cursor(Cursor.HAND_CURSOR));
				url.setText("<html><center>" + Messages.getMessage("PdfViewerJpedalLibrary.Link")
						+ Messages.getMessage("PdfViewerJpedalLibrary.Text")
						+ Messages.getMessage("PdfViewer.WebAddress") + "</a></center>");
			}

			public void mouseExited(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					details.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				url.setText("<html><center>" + Messages.getMessage("PdfViewerJpedalLibrary.Text")
						+ Messages.getMessage("PdfViewer.WebAddress"));
			}

			public void mouseClicked(MouseEvent e) {
				try {
					BrowserLauncher.openURL(Messages.getMessage("PdfViewer.VisitWebsite"));
				} catch (IOException e1) {
					showMessageDialog(Messages.getMessage("PdfViewer.ErrorWebsite"));
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});

		details.add(url);
		details.add(Box.createRigidArea(new Dimension(0, 5)));

		details.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		showMessageDialog(details, Messages.getMessage("PdfViewerInfo3"), JOptionPane.PLAIN_MESSAGE);

		/**
        //<end-wrap>

        final JPanel details=new JPanel();
        details.setPreferredSize(new Dimension(400,260));
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        //general details
        JLabel header1=new JLabel("PDF Java Ebook Solution");
        header1.setOpaque(false);
        header1.setFont(headFont);
        header1.setAlignmentX(Component.CENTER_ALIGNMENT);
        details.add(header1);

        details.add(Box.createRigidArea(new Dimension(0,15)));

        String xmlText="This ebook was generated using the free PJES service offered by IDRSolutions. Go to our website to try it out yourself!";
        if(xmlText.length()>0){

        JTextArea xml=new JTextArea();
        xml.setFont(textFont1);
        xml.setOpaque(false);
        xml.setText(xmlText + "\n\nVersions\n PJES: "+PdfDecoder.version + "          " + "Java: " + System.getProperty("java.version"));
        xml.setLineWrap(true);
        xml.setWrapStyleWord(true);
        xml.setEditable(false);
        details.add(xml);
        xml.setAlignmentX(Component.CENTER_ALIGNMENT);

        }

       
        //@kieran - cursor
        url.addMouseListener(new MouseListener() {
        public void mouseEntered(MouseEvent e) {
        details.setCursor(new Cursor(Cursor.HAND_CURSOR));
        url.setText("<html><center><a href=\"http://www.idrsolutions.com\">PJES from www.idrsolutions.com</a></center>");
        }

        public void mouseExited(MouseEvent e) {
        details.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        url.setText("<html><center>PJES from www.idrsolutions.com");
        }

        public void mouseClicked(MouseEvent e) {
        try {
        BrowserLauncher.openURL("http://www.idrsolutions.com");
        } catch (IOException e1) {
        showMessageDialog(Messages.getMessage("PdfViewer.ErrorWebsite"));
        }
        }

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        });

        details.add(url);
        details.add(Box.createRigidArea(new Dimension(0,5)));

        details.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        showMessageDialog(details,"About",JOptionPane.PLAIN_MESSAGE);
        /**/
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#resetRotationBox()
	 */
	public void resetRotationBox() {

		PdfPageData currentPageData = decode_pdf.getPdfPageData();

		//>>> DON'T UNCOMMENT THIS LINE, causes major rotation issues, only useful for debuging <<<
		if (decode_pdf.getDisplayView() == Display.SINGLE_PAGE) {
			rotation = currentPageData.getRotation(commonValues.getCurrentPage());
		}
		//else
		//rotation=0;

		if (getSelectedComboIndex(Commands.ROTATION) != (rotation / 90)) {
			setSelectedComboIndex(Commands.ROTATION, (rotation / 90));
		} else if (!Values.isProcessing()) {
			decode_pdf.repaint();
		}
	}


	/**
	 * show document properties
	 */
	private JScrollPane getPropertiesBox(String file, String path, String user_dir, long size, int pageCount,int currentPage) {

		PdfFileInformation currentFileInformation=decode_pdf.getFileInformationData();

		/**get the Pdf file information object to extract info from*/
		if(currentFileInformation!=null){

			JPanel details=new JPanel();
			details.setOpaque(true);
			details.setBackground(Color.white);
			details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

			JScrollPane scrollPane=new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(400,300));
			scrollPane.getViewport().add(details);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			//general details
			JLabel header1=new JLabel(Messages.getMessage("PdfViewerGeneral"));
			header1.setFont(headFont);
			header1.setOpaque(false);
			details.add(header1);

			JLabel g1=new JLabel(Messages.getMessage("PdfViewerFileName")+file);
			g1.setFont(textFont);
			g1.setOpaque(false);
			details.add(g1);

			JLabel g2=new JLabel(Messages.getMessage("PdfViewerFilePath")+path);
			g2.setFont(textFont);
			g2.setOpaque(false);
			details.add(g2);

			JLabel g3=new JLabel(Messages.getMessage("PdfViewerCurrentWorkingDir")+ ' ' +user_dir);
			g3.setFont(textFont);
			g3.setOpaque(false);
			details.add(g3);

			JLabel g4=new JLabel(Messages.getMessage("PdfViewerFileSize")+size+" K");
			g4.setFont(textFont);
			g4.setOpaque(false);
			details.add(g4);

			JLabel g5=new JLabel(Messages.getMessage("PdfViewerPageCount")+pageCount);
			g5.setOpaque(false);
			g5.setFont(textFont);
			details.add(g5);


			String g6Text="PDF "+decode_pdf.getPDFVersion();

			//add in if Linearized
			if(this.decode_pdf.getJPedalObject(PdfDictionary.Linearized)!=null)
				g6Text=g6Text+" ("+Messages.getMessage("PdfViewerLinearized.text")+") ";

			JLabel g6=new JLabel(g6Text);
			g6.setOpaque(false);
			g6.setFont(textFont);
			details.add(g6);

			details.add(Box.createVerticalStrut(10));

			//general details
			JLabel header2=new JLabel(Messages.getMessage("PdfViewerProperties"));
			header2.setFont(headFont);
			header2.setOpaque(false);
			details.add(header2);

			//get the document properties
			String[] values=currentFileInformation.getFieldValues();
			String[] fields= PdfFileInformation.getFieldNames();

			//add to list and display
			int count=fields.length;

			JLabel[] displayValues=new JLabel[count];

			for(int i=0;i<count;i++){
				if(values[i].length()>0){

					displayValues[i]=new JLabel(fields[i]+" = "+values[i]);
					displayValues[i].setFont(textFont);
					displayValues[i].setOpaque(false);
					details.add(displayValues[i]);
				}
			}

			details.add(Box.createVerticalStrut(10));

			/**
			 * get the Pdf file information object to extract info from
			 */
			PdfPageData currentPageSize=decode_pdf.getPdfPageData();

			if(currentPageSize!=null){

				//general details
				JLabel header3=new JLabel(Messages.getMessage("PdfViewerCoords.text"));
				header3.setFont(headFont);
				details.add(header3);

				JLabel g7=new JLabel(Messages.getMessage("PdfViewermediaBox.text")+currentPageSize.getMediaValue(currentPage));
				g7.setFont(textFont);
				details.add(g7);

				JLabel g8=new JLabel(Messages.getMessage("PdfViewercropBox.text")+currentPageSize.getCropValue(currentPage));
				g8.setFont(textFont);
				details.add(g8);

				JLabel g9=new JLabel(Messages.getMessage("PdfViewerLabel.Rotation")+currentPageSize.getRotation(currentPage));
				g9.setFont(textFont);
				details.add(g9);

			}

			details.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

			return scrollPane;

		}else{
			return new JScrollPane();
		}
	}


	/**
	 * page info option
	 */
	private static JScrollPane getXMLInfoBox(String xmlText) {

		JPanel details=new JPanel();
		details.setLayout(new BoxLayout(details, BoxLayout.PAGE_AXIS));

		details.setOpaque(true);
		details.setBackground(Color.white);

		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,300));
		scrollPane.getViewport().add(details);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JTextArea xml=new JTextArea();

		xml.setRows(5);
		xml.setColumns(15);
		xml.setLineWrap(true);
		xml.setText(xmlText);
		details.add(new JScrollPane(xml));
		xml.setCaretPosition(0);
		xml.setOpaque(true);
		xml.setBackground(Color.white);

		return scrollPane;

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showDocumentProperties(java.lang.String, java.lang.String, long, int, int)
	 */
	public void showDocumentProperties(String selectedFile, String inputDir, long size, int pageCount,int currentPage) {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(Color.WHITE);

		if(selectedFile == null){
			showMessageDialog(Messages.getMessage("PdfVieweremptyFile.message"),Messages.getMessage("PdfViewerTooltip.pageSize"),JOptionPane.PLAIN_MESSAGE);
		}else{

			String filename=selectedFile;

			int ptr=filename.lastIndexOf('\\');
			if(ptr==-1)
				ptr=filename.lastIndexOf('/');

			String file=filename.substring(ptr+1,filename.length());


			String path=filename.substring(0,ptr+1);

			int ii=0;
			tabbedPane.add(getPropertiesBox(file, path,user_dir,size,pageCount,currentPage));
			tabbedPane.setTitleAt(ii++, Messages.getMessage("PdfViewerTab.Properties"));

			tabbedPane.add(getFontInfoBox());
			tabbedPane.setTitleAt(ii++, Messages.getMessage("PdfViewerTab.Fonts"));

			if(ImageCommands.trackImages){
				tabbedPane.add(getImageInfoBox());
				tabbedPane.setTitleAt(ii++, Messages.getMessage("PdfViewerTab.Images"));
			}

			tabbedPane.add(getFontsFoundInfoBox());
			tabbedPane.setTitleAt(ii++, "Available");

			tabbedPane.add(getFontsAliasesInfoBox());
			tabbedPane.setTitleAt(ii++, "Aliases");

			int nextTab=ii;

			/**
			 * add form details if applicable
			 */
			JScrollPane scroll=getFormList();

			if(scroll!=null){
				tabbedPane.add(scroll);
				tabbedPane.setTitleAt(nextTab, "Forms");
				nextTab++;
			}

			/**
			 * optional tab for new XML style info
			 */
			PdfFileInformation currentFileInformation=decode_pdf.getFileInformationData();
			String xmlText=currentFileInformation.getFileXMLMetaData();
			if(xmlText.length()>0){
				tabbedPane.add(getXMLInfoBox(xmlText));
				tabbedPane.setTitleAt(nextTab, "XML");
			}

			showMessageDialog(tabbedPane, Messages.getMessage("PdfViewerTab.DocumentProperties"), JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * provide list of forms
	 */
	private JScrollPane getFormList() {

		JScrollPane scroll=null;

		//get the form renderer
		AcroRenderer formRenderer=decode_pdf.getFormRenderer();

		if(formRenderer!=null){

			//get list of forms on page
			java.util.List formsOnPage=null;

			try {
				formsOnPage = formRenderer.getComponentNameList(commonValues.getCurrentPage());
			} catch (PdfException e) {

				logger.info("Exception "+e+" reading component list");
			}

			//allow for no forms
			if(formsOnPage!=null){

				int formCount=formsOnPage.size();

				JPanel formPanel=new JPanel();

				scroll=new JScrollPane();
				scroll.setPreferredSize(new Dimension(400,300));
				scroll.getViewport().add(formPanel);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


				/**
				 * create a JPanel to list forms and popup details
				 */

				formPanel.setLayout(new BoxLayout(formPanel,BoxLayout.Y_AXIS));
				JLabel formHeader = new JLabel("This page contains "+formCount+" form objects");
				formHeader.setFont(headFont);
				formPanel.add(formHeader);

				formPanel.add(Box.createRigidArea(new Dimension(10,10)));

				/** sort form names in alphabetical order */
				Collections.sort(formsOnPage);

				//get FormRenderer and Data objects
				AcroRenderer renderer = decode_pdf.getFormRenderer();
				if(renderer==null)
					return scroll;
				GUIData formData=renderer.getCompData();

				/**
				 * populate our list with details
				 */
				for (Object aFormsOnPage : formsOnPage) {

					// get name of form
					String formName = (String) aFormsOnPage;

					//swing component we map data into
					Component[] comp = (Component[]) formRenderer.getComponentsByName(formName);

					if (comp != null) {

						//number of components - may be several child items
						//int count = comp.length;

						//take value or first if array to check for types (will be same if children)
						FormObject formObj = null;

						//extract list of actual PDF references to display and get FormObject
						String PDFrefs = "PDF ref=";

						JLabel ref = new JLabel();

						//actual data read from PDF
						Object[] rawFormData = formData.getRawForm(formName);
						for (Object aRawFormData : rawFormData) {
							formObj = (FormObject) aRawFormData;
							PDFrefs = PDFrefs + ' ' + formObj.getObjectRefAsString();
						}

						ref.setText(PDFrefs);

						/** display the form component description */
						// int formComponentType = ((Integer) formData.getTypeValueByName(formName)).intValue();

						String formDescription = formName;
						JLabel header = new JLabel(formDescription);

						JLabel type = new JLabel();
						type.setText("Type=" +
								PdfDictionary.showAsConstant(formObj.getParameterConstant(PdfDictionary.Type)) +
								" Subtype=" + PdfDictionary.showAsConstant(formObj.getParameterConstant(PdfDictionary.Subtype)));


						/** get the current Swing component type */
						String standardDetails = "java class=" + comp[0].getClass();

						JLabel details = new JLabel(standardDetails);

						header.setFont(headFont);
						header.setForeground(Color.blue);

						type.setFont(textFont);
						type.setForeground(Color.blue);

						details.setFont(textFont);
						details.setForeground(Color.blue);

						ref.setFont(textFont);
						ref.setForeground(Color.blue);

						formPanel.add(header);
						formPanel.add(type);
						formPanel.add(details);
						formPanel.add(ref);

						/** not currently used or setup
                         JButton more = new JButton("View Form Data");
                         more.setFont(textFont);
                         more.setForeground(Color.blue);

                         more.addActionListener(new ShowFormDataListener(formName));
                         formPanel.add(more);

                         formPanel.add(new JLabel(" "));

                         /**/
					}
				}
			}
		}

		return scroll;
	}

	/**
	 * display form data in popup
	 *
	private class ShowFormDataListener implements ActionListener{

		private String formName;

		public ShowFormDataListener(String formName){
			this.formName=formName;
		}

		public void actionPerformed(ActionEvent e) {


			//will return Object or  Object[] if multiple items of same name
			Object[] values=decode_pdf.getFormRenderer().getCompData().getRawForm(formName);

			int count=values.length;

			JTabbedPane valueDisplay=new JTabbedPane();
			for(int jj=0;jj<count;jj++){

                FormObject form=(FormObject)values[jj];

				if(values[jj]!=null){
					String data=form.toString();
					JTextArea text=new JTextArea();
					text.setText(data);
					text.setWrapStyleWord(true);

					JScrollPane scroll=new JScrollPane();
					scroll.setPreferredSize(new Dimension(400,300));
					scroll.getViewport().add(text);
					scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					valueDisplay.add(form.getObjectRefAsString(),scroll);
				}
			}

			JOptionPane.showMessageDialog(getFrame(), valueDisplay,"Raw Form Data",JOptionPane.OK_OPTION);
		}

	}/**/

	GUISearchWindow searchFrame = null;
	boolean addSearchTab = false;
	boolean searchInMenu = false;

	/*
	 * Set Search Bar to be in the Left hand Tabbed pane
	 */
	public void searchInTab(GUISearchWindow searchFrame){
		this.searchFrame = searchFrame;

		if(commonValues.getSelectedFile()!=null){
			this.searchFrame.init(decode_pdf, commonValues);
			if(PdfDecoder.isRunningOnMac){
				if(thumbnails.isShownOnscreen())
					navOptionsPanel.addTab(Messages.getMessage("PdfViewerJPanel.search"),searchFrame.getContentPanel());
			}else{
				VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, Messages.getMessage("PdfViewerJPanel.search"), VTextIcon.ROTATE_LEFT);
				navOptionsPanel.addTab(null, textIcon2, searchFrame.getContentPanel());
			}
			addSearchTab = true;
		}
	}

	public void thumbnailsInPanel(){
		int thumbnailsIndex = -1;
		for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
			if(navOptionsPanel.getIconAt(jj).toString().equals(pageTitle))
				thumbnailsIndex=jj;
		}
		
		if(thumbnailsIndex==-1){
			VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, pageTitle, VTextIcon.ROTATE_LEFT);
			navOptionsPanel.addTab(null, textIcon2, (Component) thumbnails);
		} else {
			System.out.println("pannello pagine da visualizzare " + thumbnails.isShownOnscreen() );
			if(!thumbnails.isShownOnscreen()) {
				navOptionsPanel.remove(thumbnailsIndex);
			}
		}
	}

	JTextField searchText = null;
	SearchList results = null;
	public Commands currentCommands;

	JToggleButton options;
	JPopupMenu menu;

	private JToggleButton createMenuBarSearchOptions(){
		if(options==null){
			options = new JToggleButton(new ImageIcon(getURLForImage(iconLocation+"menuSearchOptions.png")));
			menu = new JPopupMenu();

			options.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						menu.show(((JComponent)e.getSource()), 0, ((JComponent)e.getSource()).getHeight());
					}
				}
			});
			options.setFocusable(false);
			options.setToolTipText(Messages.getMessage("PdfViewerSearch.Options"));
			//wholeWordsOnlyBox, caseSensitiveBox, multiLineBox, highlightAll

			//		JMenuItem openFull = new JMenuItem("Open Full Search Window");
			//		openFull.addActionListener(new ActionListener(){
			//			public void actionPerformed(ActionEvent e) {
			//				
			//			}
			//		});

			JCheckBoxMenuItem wholeWords = new JCheckBoxMenuItem(Messages.getMessage("PdfViewerSearch.WholeWords"));
			wholeWords.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					searchFrame.setWholeWords(((JCheckBoxMenuItem)e.getSource()).isSelected());
				}
			});

			JCheckBoxMenuItem caseSense = new JCheckBoxMenuItem(Messages.getMessage("PdfViewerSearch.CaseSense"));
			caseSense.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					searchFrame.setCaseSensitive(((JCheckBoxMenuItem)e.getSource()).isSelected());
				}
			});

			JCheckBoxMenuItem multiLine = new JCheckBoxMenuItem(Messages.getMessage("PdfViewerSearch.MultiLine"));
			multiLine.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					searchFrame.setMultiLine(((JCheckBoxMenuItem)e.getSource()).isSelected());
				}
			});

			//        menu.add(openFull);
			//        menu.addSeparator();
			menu.add(wholeWords);
			menu.add(caseSense);
			menu.add(multiLine);

			menu.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				}

				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					options.setSelected(false);
				}

				public void popupMenuCanceled(PopupMenuEvent e) {
					options.setSelected(false);
				}
			});
		}

		return options;
	}

	/*
	 * Set Search Bar to be in the Top Button Bar
	 */
	public void searchInMenu(GUISearchWindow searchFrame){
		this.searchFrame = searchFrame;
		searchInMenu = true;
		searchFrame.find(decode_pdf, commonValues);
		//		searchText.setPreferredSize(new Dimension(150,20));
		topButtons.add(searchText);
		topButtons.add(createMenuBarSearchOptions());
		addButton(GUIFactory.BUTTONBAR, Messages.getMessage("PdfViewerSearch.Previous"), iconLocation+"search_previous.gif", Commands.PREVIOUSRESULT);
		addButton(GUIFactory.BUTTONBAR, Messages.getMessage("PdfViewerSearch.Next"), iconLocation+"search_next.gif", Commands.NEXTRESULT);

		nextSearch.setVisible(false);
		previousSearch.setVisible(false);
	}

	public Commands getCommand(){
		return currentCommands;
	}
	
	

	public void clearRecentDocuments() {
		currentCommands.clearRecentDocuments();
	}

	private String iconLocation = "/it/eng/hybrid/module/jpedal/resources/";

	/**
	 * Pass a document listener to the page counter to watch for changes to the page number.
	 * This value is updated when the page is altered.
	 * 
	 * @param docListener :: A document listener to listen for changes to page number.
	 * 			New page number can be found in the insertUpdate method using
	 * 			DocumentEvent.getDocument().getText(int offset, int length)
	 */
	public void addPageChangeListener(DocumentListener docListener){
		if(pageCounter2!=null)
			pageCounter2.getDocument().addDocumentListener(docListener);
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#init(java.lang.String[], org.jpedal.examples.viewer.Commands, org.jpedal.examples.viewer.utils.Printer)
	 */
	public void init(String[] scalingValues,final Object currentCommands,Object currentPrinter) {

		//setup custom message and switch off error messages if used
		customMessageHandler =(CustomMessageHandler)(decode_pdf.getExternalHandler(Options.CustomMessageOutput));
		if(customMessageHandler!=null){
			PdfDecoder.showErrorMessages=false;
			Viewer.showMessages=false;
		}

		/**
		 * Set up from properties
		 */
		try{
			//Set viewer page inset
			String propValue = properties.getValue("pageInsets");
			if(propValue.length()>0)
				inset = Integer.parseInt(propValue);


			//Set whether to use hinting
			propValue = properties.getValue("useHinting");
			String propValue2 = System.getProperty("org.jpedal.useTTFontHinting");

			//check JVM flag first
			if (propValue2 != null) {
				//check if properties file conflicts
				if (propValue.length()>0 && !propValue2.toLowerCase().equals(propValue.toLowerCase()))
					JOptionPane.showMessageDialog(null,Messages.getMessage("PdfCustomGui.hintingFlagFileConflict"));

				if (propValue2.toLowerCase().equals("true"))
					TTGlyph.useHinting = true;
				else
					TTGlyph.useHinting = false;

				//check properties file
			} else if (propValue.length()>0 && propValue.toLowerCase().equals("true"))
				TTGlyph.useHinting = true;
			else
				TTGlyph.useHinting = false;


			//Set amount of pageFlow pages
			propValue = properties.getValue("pageFlowPages");
			if(propValue.length()>0)
				PageOffsets.PAGEFLOW_PAGES = Integer.parseInt(propValue);

			propValue = properties.getValue("changeTextAndLineart");
			if(propValue.length()>0 &&
					propValue.toLowerCase().equals("true"))
				((Commands)currentCommands).executeCommand(Commands.CHANGELINEART, new Object[]{Boolean.parseBoolean(propValue)});


			propValue = properties.getValue("vbgColor");
			if(propValue.length()>0)
				((Commands)currentCommands).executeCommand(Commands.SETPAGECOLOR, new Object[]{Integer.parseInt(propValue)});

			propValue = properties.getValue("sbbgColor");
			if(propValue.length()>0){
				//@KIERAN - This is a place holder for when this becomes active
			}//replaceDocCol

			propValue = properties.getValue("replaceDocumentTextColors");
			if(propValue.length()>0 &&
					propValue.toLowerCase().equals("true")){

				propValue = properties.getValue("vfgColor");
				if(propValue.length()>0)
					((Commands)currentCommands).executeCommand(Commands.SETTEXTCOLOR, new Object[]{Integer.parseInt(propValue)});

			}

			propValue = properties.getValue("TextColorThreshold");
			if(propValue.length()>0)
				((Commands)currentCommands).executeCommand(Commands.SETREPLACEMENTCOLORTHRESHOLD, new Object[]{Integer.parseInt(propValue)});

			//Set icon location
			propValue = properties.getValue("iconLocation");
			if(propValue.length()>0){
				iconLocation = propValue;
			}
			//Set amount of pageFlow pages
			propValue = properties.getValue("pageFlowExtraCache");
			if(propValue.length()>0)
				PageOffsets.PAGEFLOW_EXTRA_CACHE = Integer.parseInt(propValue);

			//Set amount of pageFlow pages
			propValue = properties.getValue("pageFlowReflection");
			if(propValue.length()>0 &&
					propValue.toLowerCase().equals("true"))
				PageOffsets.PAGEFLOW_REFLECTION = true;
			else
				PageOffsets.PAGEFLOW_REFLECTION = false;

			//Set amount of pageFlow pages
			propValue = properties.getValue("pageFlowSideSize");
			if(propValue.length()>0)
				PageOffsets.PAGEFLOW_SIDE_SIZE = Double.parseDouble(propValue);

			//Set amount of pageFlow pages
			propValue = properties.getValue("pageFlowReflectionHeight");
			if(propValue.length()>0)
				PageOffsets.PAGEFLOW_EXTRA_HEIGHT_FOR_REFLECTION = Double.parseDouble(propValue);

			//Set border config value and repaint
			propValue = properties.getValue("borderType");
			if(propValue.length()>0)
				SingleDisplay.CURRENT_BORDER_STYLE = Integer.parseInt(propValue);

			//Set autoScroll default and add to properties file
			propValue = properties.getValue("autoScroll");
			if(propValue.length()>0)
				allowScrolling = Boolean.getBoolean(propValue);

			//set confirmClose
			propValue = properties.getValue("confirmClose");
			if(propValue.length()>0)
				confirmClose = propValue.equals("true");

			//Dpi is taken into effect when zoom is called
			propValue = properties.getValue("resolution");
			if(propValue.length()>0)
				decode_pdf.getDPIFactory().setDpi(Integer.parseInt(propValue));

			//Allow cursor to change
			propValue = properties.getValue("allowCursorToChange");
			if(propValue.length()>0)
				if(propValue.toLowerCase().equals("true"))
					SingleDisplay.allowChangeCursor = true;
				else
					SingleDisplay.allowChangeCursor = false;

			//@kieran Ensure valid value if not recognised
			propValue = properties.getValue("pageMode");
			if(propValue.length()>0){
				int pageMode = Integer.parseInt(propValue);
				if(pageMode<Display.SINGLE_PAGE || pageMode>Display.PAGEFLOW)
					pageMode = Display.SINGLE_PAGE;
				//Default Page Layout
				decode_pdf.setPageMode(pageMode);
			}

			propValue = properties.getValue("maxmuliviewers");
			if(propValue.length()>0)
				commonValues.setMaxMiltiViewers(Integer.parseInt(propValue));

			propValue = properties.getValue("showDownloadWindow");
			if(propValue.length()>0)
				useDownloadWindow = Boolean.valueOf(propValue);

			propValue = properties.getValue("useHiResPrinting");
			if(propValue.length()>0)
				hiResPrinting = Boolean.valueOf(propValue);

			//@kieran - in this code, it will break if we add new value for all users.
			//could we recode these all defensively so  change one below to
			String val= properties.getValue("highlightBoxColor"); //empty string to old users
			if(val.length()>0)
				DecoderOptions.highlightColor = new Color(Integer.parseInt(val));

			//how it is at moment
			//PdfDecoder.highlightColor = new Color(Integer.parseInt(properties.getValue("highlightBoxColor")));

			////////////////////////////
			propValue = properties.getValue("highlightTextColor");
			if(propValue.length()>0)
				DecoderOptions.backgroundColor = new Color(Integer.parseInt(propValue));

			propValue = properties.getValue("invertHighlights");
			if(propValue.length()>0)
				SwingDisplay.invertHighlight = Boolean.valueOf(propValue);

			propValue = properties.getValue("showMouseSelectionBox");
			if(propValue.length()>0)
				PdfDecoder.showMouseBox = Boolean.valueOf(propValue);

			propValue = properties.getValue("enhancedViewerMode");
			if(propValue.length()>0)
				decode_pdf.useNewGraphicsMode = Boolean.valueOf(propValue);

			propValue = properties.getValue("enhancedFacingMode");
			if(propValue.length()>0)
				SingleDisplay.default_turnoverOn = Boolean.valueOf(propValue);

			propValue = properties.getValue("enhancedGUI");
			if(propValue.length()>0)
				useNewLayout = Boolean.valueOf(propValue);

			propValue = properties.getValue("highlightComposite");
			if(propValue.length()>0){
				float value = Float.parseFloat(propValue);
				if(value>1)
					value = 1;
				if(value<0)
					value = 0;
				PdfDecoder.highlightComposite = value;
			}

			propValue = properties.getValue("windowTitle");
			if (propValue.length()>0){
				windowTitle = propValue;
				propValue = properties.getValue("windowTitleVersion");
				if (propValue.length()>0)
					windowTitle += " - " + propValue;
			} else {
				windowTitle = Messages.getMessage("PdfViewer.titlebar")+ " - " +Messages.getMessage("PdfViewer.titlebarVersion");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		this.currentCommands = (Commands) currentCommands;

		/**
		 * single listener to execute all commands
		 */
		currentCommandListener=new CommandListener((Commands) currentCommands);

		/**
		 * set a title
		 */
		setViewerTitle(windowTitle);
		if(frame instanceof JFrame) {

			//Check if file location provided
			URL path = getURLForImage(iconLocation+"icon.png");
			if (path!=null) {
				try {
					BufferedImage fontIcon = ImageIO.read(path);
					((JFrame)frame).setIconImage(fontIcon);
				} catch(Exception e) {
				}
			}
		}

		/**arrange insets*/
		decode_pdf.setInset(inset,inset);

		//Add Background color to the panel to help break up view
		String propValue = properties.getValue("replacePdfDisplayBackground");
		if(propValue.length()>0 &&
				propValue.toLowerCase().equals("true")){
			//decode_pdf.useNewGraphicsMode = false;
			propValue = properties.getValue("pdfDisplayBackground");
			if(propValue.length()>0)
				((Commands)currentCommands).executeCommand(Commands.SETDISPLAYBACKGROUND, new Object[]{Integer.parseInt(propValue)});

			decode_pdf.setBackground(new Color(Integer.parseInt(propValue)));

		}else{
			logger.info("decode_pdf.getDecoderOptions().getDisplayBackgroundColor() " + decode_pdf.getDecoderOptions().getDisplayBackgroundColor());
			if(decode_pdf.getDecoderOptions().getDisplayBackgroundColor()!=null)
				decode_pdf.setBackground(decode_pdf.getDecoderOptions().getDisplayBackgroundColor());
			else {
				logger.info("decode_pdf.useNewGraphicsMode " + decode_pdf.useNewGraphicsMode);
				if (decode_pdf.useNewGraphicsMode)
					decode_pdf.setBackground(new Color(55,55,65));
				else
					decode_pdf.setBackground(new Color(190,190,190));
			}
		}
		/**
		 * setup combo boxes
		 */

		//set new default if appropriate
		String choosenScaling=System.getProperty("org.jpedal.defaultViewerScaling");

		//<start-wrap>
		/**
        //<end-wrap>
        choosenScaling="${pjesScaling}";
        /**/

		if(choosenScaling!=null){
			int total=scalingValues.length;
			for(int aa=0;aa<total;aa++){
				if(scalingValues[aa].equals(choosenScaling)){
					defaultSelection=aa;
					aa=total;
				}
			}
		}

		scalingBox=new SwingCombo(scalingValues);
		scalingBox.setBackground(Color.white);
		scalingBox.setEditable(true);
		scalingBox.setSelectedIndex(defaultSelection); //set default before we add a listener

		//if you enable, remember to change rotation and quality Comboboxes
		//scalingBox.setPreferredSize(new Dimension(85,25));

		rotationBox=new SwingCombo(rotationValues);
		rotationBox.setBackground(Color.white);
		rotationBox.setSelectedIndex(0); //set default before we add a listener



		/**
		 * add the pdf display to show page
		 **/
		JPanel containerForThumbnails=new JPanel();
		logger.info("isSingle " + isSingle);
		if(isSingle){
			previewOnSingleScroll = properties.getValue("previewOnSingleScroll").toLowerCase().equals("true");
			if(previewOnSingleScroll){
				thumbscroll = new JScrollBar(JScrollBar.VERTICAL,0,1,0, 1);
				//thumbscroll.setBorder(BorderFactory.createLineBorder(Color.cyan) );
				if (scrollListener == null){
					scrollListener = new ScrollListener();
					scrollMouseListener = new ScrollMouseListener();
				}
				thumbscroll.addAdjustmentListener(scrollListener);
				thumbscroll.addMouseListener(scrollMouseListener);
				//thumbscroll.addMouseMotionListener(scrollMouseListener);

				containerForThumbnails.setLayout(new BorderLayout());
				containerForThumbnails.add(thumbscroll,BorderLayout.EAST);
				//scrollPane.setBorder(BorderFactory.createLineBorder(Color.red) );
				scrollPane.getViewport().setVisible(true);
				scrollPane.getViewport().add(decode_pdf);
				//decode_pdf.setBorder(BorderFactory.createLineBorder(Color.green) );
				decode_pdf.setVisible(true);
				//decode_pdf.repaint();
				
				//scrollPane.add(decode_pdf);
				containerForThumbnails.add(scrollPane,BorderLayout.CENTER);
				//containerForThumbnails.add(decode_pdf,BorderLayout.CENTER);
				//containerForThumbnails.setBorder(BorderFactory.createLineBorder(Color.red) );
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			}else{
				scrollPane.getViewport().add(decode_pdf);

				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			}

			scrollPane.getVerticalScrollBar().setUnitIncrement(80);
			scrollPane.getHorizontalScrollBar().setUnitIncrement(80);

			//Keyboard control of next/previous page
			decode_pdf.addKeyListener(new KeyAdapter(){
				int count = 0;

				public void keyPressed(KeyEvent e) {
					final JScrollBar scroll = scrollPane.getVerticalScrollBar();

					if (count==0) {
						if (e.getKeyCode()==KeyEvent.VK_UP &&
								scroll.getValue()==scroll.getMinimum() &&
								getCurrentPage() > 1) {

							//change page
							((Commands)currentCommands).executeCommand(Commands.BACKPAGE, null);

							//update scrollbar so at bottom of page
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									scroll.setValue(scroll.getMaximum());
								}
							});

						} else if (e.getKeyCode()==KeyEvent.VK_DOWN &&
								(scroll.getValue()==scroll.getMaximum()-scroll.getHeight() || scroll.getHeight()==0) &&
								getCurrentPage() < decode_pdf.getPageCount()) {

							//change page
							((Commands)currentCommands).executeCommand(Commands.FORWARDPAGE, null);

							//update scrollbar so at top of page
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									scroll.setValue(scroll.getMinimum());
								}
							});
						}
					}

					count++;
				}

				public void keyReleased(KeyEvent e) {
					count = 0;
				}
			});

		}

		comboBoxBar.setBorder(BorderFactory.createEmptyBorder());
		comboBoxBar.setLayout(new FlowLayout(FlowLayout.LEADING));
		comboBoxBar.setFloatable(false);
		comboBoxBar.setFont(new Font("SansSerif", Font.PLAIN, 8));

		if(isSingle){
			/**
			 * Create a left-right split pane with tabs
			 * and add to main display
			 */

			navOptionsPanel.setTabPlacement(JTabbedPane.LEFT);
			navOptionsPanel.setOpaque(true);
			navOptionsPanel.setMinimumSize(new Dimension(0,100));
			navOptionsPanel.setName("NavPanel");

			pageTitle=Messages.getMessage("PdfViewerJPanel.thumbnails");
			bookmarksTitle=Messages.getMessage("PdfViewerJPanel.bookmarks");
			layersTitle=Messages.getMessage("PdfViewerJPanel.layers");
			signaturesTitle=Messages.getMessage("PdfCustomGui.Signaturestab");;

			if(previewOnSingleScroll)
				displayPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navOptionsPanel, containerForThumbnails);
			else
				displayPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navOptionsPanel, scrollPane);

			displayPane.setOneTouchExpandable(false);
			//displayPane.setBorder(BorderFactory.createLineBorder(Color.red) );
			//decode_pdf.setBorder(BorderFactory.createLineBorder(Color.green) );

			desktopPane.setBorder(BorderFactory.createLineBorder(Color.blue) );
			
			//update scaling when divider moved
			displayPane.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					
					//hack to get it to use current values instead of old values
					scrollPane.getViewport().setSize((scrollPane.getViewport().getWidth()+ (Integer) e.getOldValue() - (Integer) e.getNewValue()),
							scrollPane.getViewport().getHeight());

					desktopPane.setSize((desktopPane.getWidth()+ (Integer) e.getOldValue() - (Integer) e.getNewValue()),
							desktopPane.getHeight());

					zoom(false);
				}
			});

			if(!commonValues.isContentExtractor()){
				if(PdfDecoder.isRunningOnMac){
					navOptionsPanel.addTab(pageTitle,(Component) thumbnails);
					navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, pageTitle);

					if(thumbnails.isShownOnscreen()){
						navOptionsPanel.addTab(bookmarksTitle,(SwingOutline)tree);
						navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, bookmarksTitle);
					}

				}else{
					//if(thumbnails.isShownOnscreen()){
						VTextIcon textIcon1 = new VTextIcon(navOptionsPanel, pageTitle, VTextIcon.ROTATE_LEFT);
						navOptionsPanel.addTab(null, textIcon1, (Component) thumbnails);

						//navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, pageTitle);
					//}

					VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, bookmarksTitle, VTextIcon.ROTATE_LEFT);
					navOptionsPanel.addTab(null, textIcon2, (SwingOutline)tree);
					//navOptionsPanel.setTitleAt(navOptionsPanel.getTabCount()-1, bookmarksTitle);

				}

				//				p.setTabDefaults(defaultValues);

				displayPane.setDividerLocation(startSize);
			}else
				displayPane.setDividerLocation(0);

			addListnerNavOptionPanel();
			
		}

		/**
		 * setup global buttons
		 */
		//if(!commonValues.isContentExtractor()){
		first=new SwingButton();
		fback=new SwingButton();
		back=new SwingButton();
		forward=new SwingButton();
		fforward=new SwingButton();
		end=new SwingButton();

		//}

		snapshotButton=new SwingButton();


		singleButton=new SwingButton();
		continuousButton=new SwingButton();
		continuousFacingButton=new SwingButton();
		facingButton=new SwingButton();

		pageFlowButton=new SwingButton();

		openButton=new SwingButton();
		printButton=new SwingButton();
		saveButton=new SwingButton();
		timbroButton=new SwingButton();
		signButton=new SwingButton();
		markButton=new SwingButton();
		signMarkButton=new SwingButton();
		searchButton=new SwingButton();
		docPropButton=new SwingButton();
		infoButton=new SwingButton();
		inviaLogButton=new SwingButton();
		mouseMode=new SwingButton();

		previousSearch=new SwingButton();
		nextSearch=new SwingButton();

		/**
		 * set colours on display boxes and add listener to page number
		 */
		pageCounter2.setEditable(true);
		pageCounter2.setToolTipText(Messages.getMessage("PdfViewerTooltip.goto"));
		pageCounter2.setBorder(BorderFactory.createLineBorder(Color.black));
		pageCounter2.setColumns(4);
		pageCounter2.setMaximumSize(pageCounter2.getPreferredSize());

		pageCounter2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				String value=pageCounter2.getText().trim();

				((Commands)currentCommands).gotoPage(value);
			}

		});
		pageCounter2.setHorizontalAlignment(JTextField.CENTER);
		pageCounter2.setForeground(Color.black);
		setPageNumber();

		pageCounter3=new JLabel(Messages.getMessage("PdfViewerOfLabel.text")+ ' ');
		pageCounter3.setOpaque(false);

		/**
		 * create a menu bar and add to display
		 */
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		if(frame instanceof JFrame)
			((JFrame)frame).getContentPane().add(top, BorderLayout.NORTH);
		else
			frame.add(top, BorderLayout.NORTH);

		/** nav bar at bottom to select pages and setup Toolbar on it*/

		//navToolBar.setLayout(new FlowLayout());
		navToolBar.setLayout(new BoxLayout(navToolBar,BoxLayout.LINE_AXIS));
		navToolBar.setFloatable(false);

		//pagesToolBar.setLayout(new FlowLayout());
		pagesToolBar.setFloatable(false);

		navButtons.setBorder(BorderFactory.createEmptyBorder());
		navButtons.setLayout(new BorderLayout());
		navButtons.setFloatable(false);
		//comboBar.setFont(new Font("SansSerif", Font.PLAIN, 8));
		navButtons.setPreferredSize(new Dimension(5,24));


		/**
		 *setup menu and create options
		 */
		top.add(currentMenu, BorderLayout.NORTH);


		/**
		 * create other tool bars and add to display
		 */
		topButtons.setBorder(BorderFactory.createEmptyBorder());
		topButtons.setLayout(new FlowLayout(FlowLayout.LEADING));
		topButtons.setFloatable(false);
		topButtons.setFont(new Font("SansSerif", Font.PLAIN, 8));


		top.add(topButtons, BorderLayout.CENTER);


		if (!useNewLayout) {
			/**
			 * zoom,scale,rotation, status,cursor
			 */
			top.add(comboBoxBar, BorderLayout.SOUTH);
		}


		if(frame instanceof JFrame)
			((JFrame)frame).getContentPane().add(navButtons, BorderLayout.SOUTH);
		else
			frame.add(navButtons, BorderLayout.SOUTH);


		if(displayPane!=null){ //null in MultiViewer
			if(frame instanceof JFrame)
				((JFrame)frame).getContentPane().add(displayPane, BorderLayout.CENTER);
			else
				frame.add(displayPane, BorderLayout.CENTER);


		}



		/**
		 * Menu bar for using the majority of functions
		 */
		createMainMenu(true);


		//createSwingMenu(true);

		/**
		 * sets up all the toolbar items
		 */
		//<start-wrap>
		createOtherMenu();


		/**
		 * navigation toolbar for moving between pages
		 */
		createNavbar();

		//<start-wrap>
		addCursor();
		//<end-wrap>

		//		p.setButtonDefaults(defaultValues);

		//<link><a name="newbutton" />
		/**
		 * external/itext button option example adding new option to Export menu
		 * an icon is set wtih location on classpath
		 * 
		 * Make sure it exists at location and is copied into jar if recompiled
		 */
		
		/**
		 * external/itext menu option example adding new option to Export menu
		 * Tooltip text can be externalised in Messages.getMessage("PdfViewerTooltip.NEWFUNCTION")
		 * and text added into files in res package
		 */


		if(searchFrame!=null && searchFrame.getStyle()==SwingSearchWindow.SEARCH_MENU_BAR)
			searchInMenu(searchFrame);

		/**status object on toolbar showing 0 -100 % completion */
		initStatus();

		//		p.setDisplayDefaults(defaultValues);

		//Ensure all gui sections are displayed correctly
		//Issues found when removing some sections
		frame.invalidate();
		frame.validate();
		frame.repaint();

		/**
		 * Load properties
		 */
		try{
			loadProperties();
		}catch(Exception e){
			e.printStackTrace();
		}

		/**
		 * set display to occupy half screen size and display, add listener and
		 * make sure appears in centre
		 */
		if(commonValues.getModeOfOperation()!=Values.RUNNING_APPLET){

			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			int width = d.width / 2, height = d.height / 2;
			if(width<minimumScreenWidth)
				width=minimumScreenWidth;

			//allow user to alter size
			String customWindowSize=System.getProperty("org.jpedal.startWindowSize");
			if(customWindowSize!=null){

				StringTokenizer values=new StringTokenizer(customWindowSize,"x");

				if(values.countTokens()!=2)
					throw new RuntimeException("Unable to use value for org.jpedal.startWindowSize="+customWindowSize+"\nValue should be in format org.jpedal.startWindowSize=200x300");

				try{
					width=Integer.parseInt(values.nextToken().trim());
					height=Integer.parseInt(values.nextToken().trim());

				}catch(Exception ee){
					throw new RuntimeException("Unable to use value for org.jpedal.startWindowSize="+customWindowSize+"\nValue should be in format org.jpedal.startWindowSize=200x300");
				}
			}

			if (frame instanceof JFrame) {
				frame.setSize(width, height);
				((JFrame)frame).setLocationRelativeTo(null); //centre on screen
				((JFrame)frame).setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				//((JFrame)frame).addWindowListener(new FrameCloser((Commands) currentCommands, this,decode_pdf,(Printer)currentPrinter,thumbnails,commonValues,properties));
				
				frame.setVisible(true);
			}
		}

		/**Ensure Document is redrawn when frame is resized and scaling set to width, height or window*/
		frame.addComponentListener(new ComponentListener(){
			public void componentHidden(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				if(decode_pdf.getParent()!=null &&
						(getSelectedComboIndex(Commands.SCALING)<3 || decode_pdf.getDisplayView() == Display.FACING)) //always rezoom in facing mode for turnover
					zoom(false);
			}
			public void componentShown(ComponentEvent e) {}
		});



		//add a border
		if (decode_pdf.useNewGraphicsMode) {
			decode_pdf.setPDFBorder(new AbstractBorder() {

				public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
					Graphics2D g2 = (Graphics2D) g;

					int cornerDepth = (glowThickness / 2)+1;

					//left
					g2.setPaint( new GradientPaint( x- glowThickness, 0, glowOuterColor, x, 0, glowInnerColor) );
					g2.fillRect(x- glowThickness,y, glowThickness,height);

					//bottom left corner
					g2.setPaint( new GradientPaint( x- cornerDepth, y+height+ cornerDepth, glowOuterColor, x, y+height, glowInnerColor) );
					g2.fillRect(x- glowThickness,y+height, glowThickness, glowThickness);

					//below
					g2.setPaint( new GradientPaint( 0, y+height+ glowThickness, glowOuterColor, 0, y+height, glowInnerColor) );
					g2.fillRect(x,y+height,width, glowThickness);

					//bottom right corner
					g2.setPaint( new GradientPaint( x+width+ cornerDepth, y+height+ cornerDepth, glowOuterColor, x+width, y+height, glowInnerColor));
					g2.fillRect(x+width,y+height, glowThickness, glowThickness);

					//right
					g2.setPaint( new GradientPaint( x+width+ glowThickness, 0, glowOuterColor, x+width, 0, glowInnerColor) );
					g2.fillRect(x+width,y, glowThickness,height);

					//top right corner
					g2.setPaint( new GradientPaint( x+width+ cornerDepth, y- cornerDepth, glowOuterColor,x+width,y, glowInnerColor));
					g2.fillRect(x+width, y- glowThickness, glowThickness, glowThickness);

					//above
					g2.setPaint( new GradientPaint( 0, y- glowThickness, glowOuterColor, 0, y, glowInnerColor) );
					g2.fillRect(x,y- glowThickness,width, glowThickness);

					//top left corner
					g2.setPaint( new GradientPaint( x- cornerDepth,y- cornerDepth, glowOuterColor,x,y, glowInnerColor));
					g2.fillRect(x- glowThickness,y- glowThickness, glowThickness, glowThickness);

					//draw black over top
					g2.setPaint(Color.black);
					g2.drawRect(x, y, width, height);

				}
				public Insets getBorderInsets(Component c, Insets insets) {
					insets.set(glowThickness, glowThickness, glowThickness, glowThickness);
					return insets;
				}
			});

		} else {
			decode_pdf.setPDFBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
	}


	public PdfDecoder getPdfDecoder(){
		return decode_pdf;
	}



	public void handleTabbedPanes() {

		if(tabsNotInitialised)
			return;

		/**
		 * expand size if not already at size
		 */
		int currentSize=displayPane.getDividerLocation();
		int tabSelected=navOptionsPanel.getSelectedIndex();
		
		if(tabSelected==-1)
			return;

		if(currentSize==startSize){
			/**
			 * workout selected tab
			 */
			String tabName="";
			if(PdfDecoder.isRunningOnMac){
				tabName=navOptionsPanel.getTitleAt(tabSelected);
			}else
				tabName=navOptionsPanel.getIconAt(tabSelected).toString();

			if(tabName.equals(pageTitle)){
			//add if statement or comment out this section to remove thumbnails
				setupThumbnailPanel();
				thumbnails.generateOtherVisibleThumbnails(commonValues.getCurrentPage());

			}
				//else if(tabName.equals(bookmarksTitle)){
			setBookmarks(true);
			//}

			//			if(searchFrame!=null)
			//			searchFrame.find();

			displayPane.setDividerLocation(expandedSize);
		}else if(tabSelected==lastTabSelected)
			displayPane.setDividerLocation(startSize);

		lastTabSelected=tabSelected;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#addCursor()
	 */
	public void addCursor(){

		/**add cursor location*/
		if (!useNewLayout) {
			cursor.setBorder(BorderFactory.createEmptyBorder());
			cursor.setLayout(new FlowLayout(FlowLayout.LEADING));
			cursor.setFloatable(false);
			cursor.setFont(new Font("SansSerif", Font.ITALIC, 10));
			cursor.add(new JLabel(Messages.getMessage("PdfViewerToolbarCursorLoc.text")));

			cursor.add(initCoordBox());

			cursor.setPreferredSize(new Dimension(200,32));

			/**setup cursor*/
			topButtons.add(cursor);
		} else {
			initCoordBox();
		}

	}


	private boolean cursorOverPage=false;

	public void setMultibox(int[] flags) {



		//deal with flags
		if (flags.length > 1 && flags[0] == CURSOR) {
			//if no change, return
			if (cursorOverPage != (flags[1]==1))
				cursorOverPage = flags[1] == 1;
			else
				return;
		}

		//LOAD_PROGRESS:
		if (statusBar.isEnabled() && statusBar.isVisible() && !statusBar.isDone()) {
			multibox.removeAll();
			statusBar.getStatusObject().setSize(multibox.getSize());
			multibox.add(statusBar.getStatusObject(), BorderLayout.CENTER);

			multibox.repaint();
			return;
		}

		//CURSOR:
		if (cursor.isEnabled() && cursor.isVisible() && cursorOverPage && decode_pdf.isOpen()) {
			multibox.removeAll();
			multibox.add(coords, BorderLayout.CENTER);

			multibox.repaint();
			return;
		}

		//DOWNLOAD_PROGRESS:
		if (downloadBar.isEnabled() && downloadBar.isVisible() && !downloadBar.isDone() && (decode_pdf.isLoadingLinearizedPDF() || !decode_pdf.isOpen())) {
			multibox.removeAll();
			downloadBar.getStatusObject().setSize(multibox.getSize());
			multibox.add(downloadBar.getStatusObject(), BorderLayout.CENTER);

			multibox.repaint();
			return;
		}

		//MEMORY:
		if (memoryBar.isEnabled() && memoryBar.isVisible()) {
			multibox.removeAll();
			memoryBar.setSize(multibox.getSize());
			memoryBar.setForeground(new Color(125, 145, 255));
			multibox.add(memoryBar, BorderLayout.CENTER);

			multibox.repaint();
			return;
		}

	}

	/**setup keyboard shortcuts*/
	private void setKeyAccelerators(int ID,JMenuItem menuItem){

		int systemMask = java.awt.Event.CTRL_MASK;
		if(PdfDecoder.isRunningOnMac){
			systemMask = java.awt.Event.META_MASK;
		}


		switch(ID){

		case Commands.FIND:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F,systemMask));
			break;

		case Commands.SAVE:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
					systemMask));
			break;
		case Commands.PRINT:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
					systemMask));
			break;
		case Commands.TIMBRA:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
					systemMask));
			break;
		case Commands.EXIT:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
					systemMask));
			break;
		case Commands.DOCINFO:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D,
					systemMask));
			break;
		case Commands.OPENFILE:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
					systemMask));
			break;
		case Commands.OPENURL:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U,
					systemMask));
			break;
		case Commands.PREVIOUSDOCUMENT:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT,java.awt.event.KeyEvent.ALT_MASK | java.awt.event.KeyEvent.SHIFT_MASK));
			break;
		case Commands.NEXTDOCUMENT:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT,java.awt.event.KeyEvent.ALT_MASK | java.awt.event.KeyEvent.SHIFT_MASK));
			break;
		case Commands.FIRSTPAGE:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_HOME,systemMask));
			break;
		case Commands.BACKPAGE:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.KeyEvent.CTRL_MASK));
			break;
		case Commands.FORWARDPAGE:
			menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.KeyEvent.CTRL_MASK));
			break;
		case Commands.LASTPAGE:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_END,systemMask));
			break;
		case Commands.GOTO:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,systemMask | java.awt.event.KeyEvent.SHIFT_MASK));
			break;
		case Commands.BITMAP:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B,java.awt.event.KeyEvent.ALT_MASK));
			break;
		case Commands.COPY:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,systemMask));
			break;
		case Commands.SELECTALL:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,systemMask));
			break;
		case Commands.DESELECTALL:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,systemMask+java.awt.event.KeyEvent.SHIFT_DOWN_MASK));
			break;
		case Commands.PREFERENCES:
			menuItem.setAccelerator( KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K,systemMask));
			break;

		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#addButton(int, java.lang.String, java.lang.String, int)
	 */
	public void addButton(int line,String toolTip,String path,final int ID) {

		GUIButton newButton = new SwingButton();

		/**specific buttons*/
		switch(ID){
		case Commands.FIRSTPAGE:
			newButton=first;
			break;
		case Commands.FBACKPAGE:
			newButton=fback;
			break;
		case Commands.BACKPAGE:
			newButton=back;
			break;
		case Commands.FORWARDPAGE:
			newButton=forward;
			break;
		case Commands.FFORWARDPAGE:
			newButton=fforward;
			break;
		case Commands.LASTPAGE:
			newButton=end;
			break;
		case Commands.SNAPSHOT:
			newButton=snapshotButton;
			break;
		case Commands.SINGLE:
			newButton=singleButton;
			newButton.setName("SINGLE");
			break;
		case Commands.CONTINUOUS:
			newButton=continuousButton;
			newButton.setName("CONTINUOUS");
			break;
		case Commands.CONTINUOUS_FACING:
			newButton=continuousFacingButton;
			newButton.setName("CONTINUOUS_FACING");
			break;
		case Commands.FACING:
			newButton=facingButton;
			newButton.setName("FACING");
			break;
		case Commands.PAGEFLOW:
			newButton=pageFlowButton;
			newButton.setName("PAGEFLOW");
			break;
		case Commands.PREVIOUSRESULT:
			newButton=previousSearch;
			newButton.setName("PREVIOUSRESULT");
			break;
		case Commands.NEXTRESULT:
			newButton=nextSearch;
			newButton.setName("NEXTRESULT");
			break;
		case Commands.OPENFILE:
			newButton=openButton;
			newButton.setName("open");
			break;
		case Commands.PRINT:
			newButton=printButton;
			newButton.setName("print");
			break;
		case Commands.SAVE:
			newButton=saveButton;
			newButton.setName("save");
			break;
		case Commands.TIMBRA:
			newButton=timbroButton;
			newButton.setName("timbra");
			break;
		case Commands.SIGN:
			newButton=signButton;
			newButton.setName("sign");
			break;
		case Commands.MARK:
			newButton=markButton;
			newButton.setName("mark");
			break;
		case Commands.SIGNMARK:
			newButton=signMarkButton;
			newButton.setName("signMark");
			break;
		case Commands.FIND:
			newButton=searchButton;
			newButton.setName("search");
			break;
		case Commands.DOCINFO:
			newButton=docPropButton;
			break;
		case Commands.INFO:
			newButton=infoButton;
			break;
		case Commands.MOUSEMODE:
			newButton=mouseMode;
			newButton.setName("mousemode");
			break;
		case Commands.INVIALOG:
			newButton=inviaLogButton;
			newButton.setName("inviaLog");
			break;
		}

		//@kieran : This may be a good idea. See how you feel when time to commit.
		((SwingButton)newButton).addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					((SwingButton)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					((SwingButton)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		newButton.init(getURLForImage(path), ID,toolTip);
		
		//rimuovo i listener attuali
		 ActionListener[] listaListener=((AbstractButton) newButton).getActionListeners();
		 for (int i = 0; i < listaListener.length; i++) {
			 ((AbstractButton) newButton).removeActionListener(listaListener[i]);
		}
		//add listener
		((AbstractButton) newButton).addActionListener(currentCommandListener);

		int mode=commonValues.getModeOfOperation();

		//remove background for the applet as default L&F has a shaded toolbar
		if (mode==Values.RUNNING_APPLET)
			((AbstractButton) newButton).setContentAreaFilled(false);

		//add to toolbar

		if(line==BUTTONBAR || mode==Values.RUNNING_PLUGIN){
			topButtons.add((AbstractButton) newButton);

			//add spaces for plugin
			//if( (mode==Commands.LASTPAGE || mode==Commands.PAGEFLOW))
			if(mode==Values.RUNNING_PLUGIN && (mode==Commands.LASTPAGE || mode==Commands.PAGEFLOW))
				topButtons.add(Box.createHorizontalGlue());

		}else if(line==NAVBAR){
			navToolBar.add((AbstractButton)newButton);
		}else if(line==PAGES){
			pagesToolBar.add((AbstractButton)newButton,BorderLayout.CENTER);
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#addMenuItem(javax.swing.JMenu, java.lang.String, java.lang.String, int)
	 */
	public void addMenuItem(JMenu parentMenu,String text,String toolTip,final int ID) {
		addMenuItem(parentMenu,text,toolTip,ID,false);
	}

	public void addMenuItem(JMenu parentMenu,String text,String toolTip,final int ID,boolean isCheckBox) {

		SwingID menuItem;
		if (isCheckBox)
			menuItem = new SwingCheckBoxMenuItem(text);
		else
			menuItem = new SwingMenuItem(text);

		if(toolTip.length()>0)
			menuItem.setToolTipText(toolTip);
		menuItem.setID(ID);
		setKeyAccelerators(ID,(JMenuItem)menuItem);

		//add listener
		menuItem.addActionListener(currentCommandListener);

		switch(ID){
		case Commands.OPENFILE :
			open = (JMenuItem)menuItem;
			parentMenu.add(open);
			break;
		case Commands.OPENURL :
			openUrl = (JMenuItem)menuItem;
			parentMenu.add(openUrl);
			break;
		case Commands.SAVE :
			save = (JMenuItem)menuItem;
			parentMenu.add(save);
			break;
		case Commands.SAVEFORM :
			reSaveAsForms = (JMenuItem)menuItem;
			//add name to resave option so fest can get to it.
			reSaveAsForms.setName("resaveForms");
			parentMenu.add(reSaveAsForms);
			break;
		case Commands.FIND :
			find = (JMenuItem)menuItem;
			parentMenu.add(find);
			break;
		case Commands.DOCINFO :
			documentProperties = (JMenuItem)menuItem;
			parentMenu.add(documentProperties);
			break;
		case Commands.SIGN :
			signPDF = (JMenuItem)menuItem;
			parentMenu.add(signPDF);
			break;
		case Commands.MARK :
			markPDF = (JMenuItem)menuItem;
			parentMenu.add(markPDF);
			break;
		case Commands.SIGNMARK :
			signMarkPDF = (JMenuItem)menuItem;
			parentMenu.add(signMarkPDF);
			break;
		case Commands.BACKORIGINALFILE :
			originalFileMenu = (JMenuItem)menuItem;
			parentMenu.add(originalFileMenu);
			break;
		case Commands.PRINT :
			print = (JMenuItem)menuItem;
			parentMenu.add(print);
			break;
		case Commands.TIMBRA :
			timbro = (JMenuItem)menuItem;
			parentMenu.add(timbro);
			break;
		case Commands.EXIT :
			exit = (JMenuItem)menuItem;
			//set name to exit so fest can find it
			exit.setName("exit");
			parentMenu.add(exit);
			break;
		case Commands.INVIALOG :
			inviaLog = (JMenuItem)menuItem;
			inviaLog.setName("inviaLog");
			parentMenu.add(inviaLog);
			break;
		case Commands.COPY :
			copy = (JMenuItem)menuItem;
			parentMenu.add(copy);
			break;
		case Commands.SELECTALL :
			selectAll = (JMenuItem)menuItem;
			parentMenu.add(selectAll);
			break;
		case Commands.DESELECTALL :
			deselectAll = (JMenuItem)menuItem;
			parentMenu.add(deselectAll);
			break;
		case Commands.PREFERENCES :
			preferences = (JMenuItem)menuItem;
			parentMenu.add(preferences);
			break;
		case Commands.FIRSTPAGE :
			firstPage = (JMenuItem)menuItem;
			parentMenu.add(firstPage);
			break;
		case Commands.BACKPAGE :
			backPage = (JMenuItem)menuItem;
			parentMenu.add(backPage);
			break;
		case Commands.FORWARDPAGE :
			forwardPage = (JMenuItem)menuItem;
			parentMenu.add(forwardPage);
			break;
		case Commands.LASTPAGE :
			lastPage = (JMenuItem)menuItem;
			parentMenu.add(lastPage);
			break;
		case Commands.GOTO :
			goTo = (JMenuItem)menuItem;
			parentMenu.add(goTo);
			break;
		case Commands.PREVIOUSDOCUMENT :
			previousDocument = (JMenuItem)menuItem;
			parentMenu.add(previousDocument);
			break;
		case Commands.NEXTDOCUMENT :
			nextDocument = (JMenuItem)menuItem;
			parentMenu.add(nextDocument);
			break;
		case Commands.FULLSCREEN :
			fullscreen = (JMenuItem)menuItem;
			parentMenu.add(fullscreen);
			break;
		case Commands.MOUSEMODE :
			fullscreen = (JMenuItem)menuItem;
			parentMenu.add(fullscreen);
			break;
		case Commands.PANMODE :
			panMode = (JMenuItem)menuItem;
			parentMenu.add(panMode);
			break;
		case Commands.TEXTSELECT :
			textSelect = (JMenuItem)menuItem;
			parentMenu.add(textSelect);
			break;
		case Commands.SEPARATECOVER :
			separateCover = (JCheckBoxMenuItem)menuItem;
			boolean separateCoverOn = properties.getValue("separateCoverOn").toLowerCase().equals("true");
			separateCover.setState(separateCoverOn);
			SingleDisplay.default_separateCover = separateCoverOn;
			parentMenu.add(separateCover);
			break;
		case Commands.CASCADE :
			cascade = (JMenuItem)menuItem;
			parentMenu.add(cascade);
			break;
		case Commands.TILE :
			tile = (JMenuItem)menuItem;
			parentMenu.add(tile);
			break;
		case Commands.PDF :
			onePerPage = (JMenuItem)menuItem;
			parentMenu.add(onePerPage);
			break;
		case Commands.NUP :
			nup = (JMenuItem)menuItem;
			parentMenu.add(nup);
			break;
		case Commands.HANDOUTS :
			handouts = (JMenuItem)menuItem;
			parentMenu.add(handouts);
			break;
		case Commands.IMAGES :
			images = (JMenuItem)menuItem;
			parentMenu.add(images);
			break;
		case Commands.TEXT :
			this.text = (JMenuItem)menuItem;
			parentMenu.add(this.text);
			break;
		case Commands.BITMAP :
			bitmap = (JMenuItem)menuItem;
			parentMenu.add(bitmap); break;
		case Commands.ROTATE :
			rotatePages = (JMenuItem)menuItem;
			parentMenu.add(rotatePages); break;
		case Commands.DELETE :
			deletePages = (JMenuItem)menuItem;
			parentMenu.add(deletePages);
			break;
		case Commands.ADD :
			addPage = (JMenuItem)menuItem;
			parentMenu.add(addPage);
			break;
		case Commands.ADDHEADERFOOTER :
			addHeaderFooter = (JMenuItem)menuItem;
			parentMenu.add(addHeaderFooter);
			break;
		case Commands.STAMPTEXT :
			stampText = (JMenuItem)menuItem;
			parentMenu.add(stampText);
			break;
		case Commands.STAMPIMAGE :
			stampImage = (JMenuItem)menuItem;
			parentMenu.add(stampImage);
			break;
		case Commands.SETCROP :
			crop = (JMenuItem)menuItem;
			parentMenu.add(crop);
			break;
		case Commands.VISITWEBSITE :
			visitWebsite = (JMenuItem)menuItem;
			parentMenu.add(visitWebsite);
			break;
		case Commands.TIP :
			tipOfTheDay = (JMenuItem)menuItem;
			parentMenu.add(tipOfTheDay);
			break;
		case Commands.UPDATE :
			checkUpdates = (JMenuItem)menuItem;
			parentMenu.add(checkUpdates);
			break;
		case Commands.INFO :
			about = (JMenuItem)menuItem;
			parentMenu.add(about);
			break;
		case Commands.PREFERENCE_FIRMAMARCA :
			firmaMarcaPreference = (JMenuItem)menuItem;
			parentMenu.add(firmaMarcaPreference);
			break;
		case Commands.PREFERENCE_TIMBRO :
			timbroPreference = (JMenuItem)menuItem;
			parentMenu.add(timbroPreference);
			break;
		case Commands.PREFERENCE_RETE :
			retePreference = (JMenuItem)menuItem;
			parentMenu.add(retePreference);
			break;
		case Commands.PREFERENCE_INVIOLOG :
			invioLogPreference = (JMenuItem)menuItem;
			parentMenu.add(invioLogPreference);
			break;

		default :
			if (menuItem instanceof JMenuItem)
				parentMenu.add((JMenuItem)menuItem);
			else if (menuItem instanceof JCheckBoxMenuItem)
				parentMenu.add((JCheckBoxMenuItem)menuItem);
		}
	}

	/**
	 * @return the path of the directory containing overriding icons
	 */
	public String getIconLocation() {
		return iconLocation;
	}

	/**
	 * Retrieve the URL of the actual image to use f
	 * @param path Preferred name and location
	 * @return URL of file to use
	 */
	public URL getURLForImage(String path) {
		String file = path.substring(path.lastIndexOf('/')+1);
		URL url;

		//<start-wrap>

		//Check if file location provided
		path = path.substring(0, path.indexOf('.'))+".gif";
		File p = new File(path);
		url = getClass().getResource(path);

		//It's a file location check for gif
		if(p.exists()){
			try {
				url = p.toURI().toURL();
			} catch (MalformedURLException e) {

			}

		}

		if(url==null){
			path = path.substring(0, path.indexOf('.'))+".png";
			p = new File(path);
			url = getClass().getResource(path);

			//It's a file location check for png
			if(p.exists()){
				try {
					url = p.toURI().toURL();
				} catch (MalformedURLException e) {
				}
			}
		}

		if(url!=null){
			return url;
		}else{ //use default graphic
			//<end-wrap>
			path = "/it/eng/hybrid/module/jpedal/resources/" +file;
			url = getClass().getResource(path);
			return url;
			//<start-wrap>
		}
		//<end-wrap>
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#addCombo(java.lang.String, java.lang.String, int)
	 */
	public void addCombo(String title,String tooltip,int ID){

		GUICombo combo=null;
		switch (ID){
		case Commands.SCALING:
			combo=scalingBox;
			break;
		case Commands.ROTATION:
			combo=rotationBox;
			break;

		}

		combo.setID(ID);

		optimizationLabel = new JLabel(title);
		if(tooltip.length()>0)
			combo.setToolTipText(tooltip);


		//<start-wrap>
		/**
        //<end-wrap>
		topButtons.add((SwingCombo) combo);
        /**/

		//<start-wrap>
		if (useNewLayout) {
			topButtons.add((SwingCombo) combo);
		}else {
			comboBoxBar.add(optimizationLabel);
			comboBoxBar.add((SwingCombo) combo);
		}
		//<end-wrap>

		//add listener
		((SwingCombo)combo).addActionListener(currentCommandListener);

	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setViewerTitle(java.lang.String)
	 */
	public void setViewerTitle(String title) {

		//<start-wrap>
		/**
        //<end-wrap>
        // hard-coded for file
		if(org.jpedal.examples.viewer.Viewer.message==null)
			title=org.jpedal.examples.viewer.Viewer.file;
		else
			title=org.jpedal.examples.viewer.Viewer.message;
        /**/

		if(title!=null){

			//<start-demo>
			/**
            //<end-demo>
             title="("+dx+" days left) "+title;
             /**/

			//<end-full>
			//title="LGPL "+title;
			/**/
			if(frame instanceof JFrame)
				((JFrame)frame).setTitle(title);
		}else{

			String finalMessage="";

			if(titleMessage==null)
				finalMessage=(windowTitle+' ' + commonValues.getSelectedFile());
			else
				finalMessage=titleMessage+ commonValues.getSelectedFile();

			PdfObject linearObj=(PdfObject)decode_pdf.getJPedalObject(PdfDictionary.Linearized);
			if(linearObj!=null){
				LinearThread linearizedBackgroundReaderer = (LinearThread) decode_pdf.getJPedalObject(PdfDictionary.LinearizedReader);

				if(linearizedBackgroundReaderer !=null && linearizedBackgroundReaderer.isAlive())
					finalMessage=finalMessage+" (still loading)";
				else
					finalMessage=finalMessage+" (Linearized)";
			}

			//<start-demo>
			/**
            //<end-demo>
             finalMessage="("+dx+" days left) "+finalMessage;
             /**/

			//finalMessage="LGPL "+finalMessage;
			/**/
			if(commonValues.isFormsChanged())
				finalMessage="* "+finalMessage;

			if(frame instanceof JFrame)
				((JFrame)frame).setTitle(finalMessage);

		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#resetComboBoxes(boolean)
	 */
	public void resetComboBoxes(boolean value) {
		scalingBox.setEnabled(value);
		rotationBox.setEnabled(value);

	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#createPane(javax.swing.JTextPane, java.lang.String, boolean)
	 */
	public final JScrollPane createPane(JTextPane text_pane,String content, boolean useXML) throws BadLocationException {

		text_pane.setEditable(true);
		text_pane.setFont(new Font("Lucida", Font.PLAIN, 14));

		text_pane.setToolTipText(Messages.getMessage("PdfViewerTooltip.text"));
		Document doc = text_pane.getDocument();
		text_pane.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), Messages.getMessage("PdfViewerTitle.text")));
		text_pane.setForeground(Color.black);

		SimpleAttributeSet token_attribute = new SimpleAttributeSet();
		SimpleAttributeSet text_attribute = new SimpleAttributeSet();
		SimpleAttributeSet plain_attribute = new SimpleAttributeSet();
		StyleConstants.setForeground(token_attribute, Color.blue);
		StyleConstants.setForeground(text_attribute, Color.black);
		StyleConstants.setForeground(plain_attribute, Color.black);
		int pointer=0;

		/**put content in and color XML*/
		if((useXML)&&(content!=null)){
			//tokenise and write out data
			StringTokenizer data_As_tokens = new StringTokenizer(content,"<>", true);

			while (data_As_tokens.hasMoreTokens()) {
				String next_item = data_As_tokens.nextToken();

				if ((next_item.equals("<"))&&((data_As_tokens.hasMoreTokens()))) {

					String current_token = next_item + data_As_tokens.nextToken()+ data_As_tokens.nextToken();

					doc.insertString(pointer, current_token,token_attribute);
					pointer = pointer + current_token.length();

				} else {
					doc.insertString(pointer, next_item, text_attribute);
					pointer = pointer + next_item.length();
				}
			}
		}else
			doc.insertString(pointer,content, plain_attribute);

		//wrap in scrollpane
		JScrollPane text_scroll = new JScrollPane();
		text_scroll.getViewport().add( text_pane );
		text_scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		text_scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
		return text_scroll;
	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getSelectedComboIndex(int)
	 */
	public int getSelectedComboIndex(int ID) {

		switch (ID){
		case Commands.SCALING:
			return scalingBox.getSelectedIndex();
		case Commands.ROTATION:
			return rotationBox.getSelectedIndex();
		default:
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setSelectedComboIndex(int, int)
	 */
	public void setSelectedComboIndex(int ID,int index) {
		switch (ID){
		case Commands.SCALING:
			scalingBox.setSelectedIndex(index);
			break;
		case Commands.ROTATION:
			rotationBox.setSelectedIndex(index);
			break;

		}

	}

	/**
	 * return comboBox or nul if not (QUALITY, SCALING or ROTATION
	 * @param ID
	 * @return
	 */
	public GUICombo getCombo(int ID) {

		switch (ID){
		case Commands.SCALING:
			return scalingBox;
		case Commands.ROTATION:
			return rotationBox;

		}

		return null;

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setSelectedComboItem(int, java.lang.String)
	 */
	public void setSelectedComboItem(int ID,String index) {
		switch (ID){
		case Commands.SCALING:
			scalingBox.setSelectedItem(index + '%');
			break;
		case Commands.ROTATION:
			rotationBox.setSelectedItem(index);
			break;

		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getSelectedComboItem(int)
	 */
	public Object getSelectedComboItem(int ID) {

		switch (ID){
		case Commands.SCALING:
			return scalingBox.getSelectedItem();
		case Commands.ROTATION:
			return rotationBox.getSelectedItem();
		default:
			return null;

		}
	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#zoom()
	 */
	public void zoom(boolean Rotated) {
		scaleAndRotate(scaling, rotation);
	}

	/** all scaling and rotation should go through this. */
	private void scaleAndRotate(float scalingValue,int rotationValue){

		if (decode_pdf.getDisplayView() == Display.PAGEFLOW3D) {
			decode_pdf.setPageParameters(scaling, commonValues.getCurrentPage(),rotation);
			return;
		}

		//ignore if called too early
		if(!decode_pdf.isOpen() && currentCommands.isPDF())
			return;

		float width,height;

		if(isSingle){

			width = scrollPane.getViewport().getWidth()-inset-inset;
			height = scrollPane.getViewport().getHeight()-inset-inset;

		}else{
			width=desktopPane.getWidth();
			height=desktopPane.getHeight();
		}

		if(decode_pdf!=null){

			//get current location and factor out scaling so we can put back at same page
			//final float x= (decode_pdf.getVisibleRect().x/scaling);
			//final float y= (decode_pdf.getVisibleRect().y/scaling);

			//System.out.println(x+" "+y+" "+scaling+" "+decode_pdf.getVisibleRect());
			/** update value and GUI */
			int index=getSelectedComboIndex(Commands.SCALING);
			
			if(decode_pdf.getDisplayView()==Display.PAGEFLOW ||
					decode_pdf.getDisplayView()==Display.PAGEFLOW3D){

				//Ensure we only display in window mode
				setSelectedComboIndex(Commands.SCALING, 0);
				index = 0;

				//Disable scaling option
				scalingBox.setEnabled(false);
			}else if(decode_pdf.getDisplayView()!=Display.PAGEFLOW &&
					decode_pdf.getDisplayView()!=Display.PAGEFLOW3D){

				//No long pageFlow. enable scaling option
				scalingBox.setEnabled(true);
			}

			if(index==-1 && decode_pdf.getDisplayView()!=Display.PAGEFLOW){
				String numberValue=(String)getSelectedComboItem(Commands.SCALING);
				float zoom=-1;
				if((numberValue!=null)&&(numberValue.length()>0)){
					try{
						zoom= Float.parseFloat(numberValue);
					}catch(Exception e){
						zoom=-1;
						//its got characters in it so get first valid number string
						int length=numberValue.length();
						int ii=0;
						while(ii<length){
							char c=numberValue.charAt(ii);
							if(((c>='0')&&(c<='9'))|(c=='.'))
								ii++;
							else
								break;
						}

						if(ii>0)
							numberValue=numberValue.substring(0,ii);

						//try again if we reset above
						if(zoom==-1){
							try{
								zoom= Float.parseFloat(numberValue);
							}catch(Exception e1){zoom=-1;}
						}
					}
					if(zoom>1000){
						zoom=1000;
					}
				}

				//if nothing off either attempt, use window value
				if(zoom==-1){
					//its not set so use To window value
					index=defaultSelection;
					setSelectedComboIndex(Commands.SCALING, index);
				}else{
					scaling=decode_pdf.getDPIFactory().adjustScaling(zoom/100);

					setSelectedComboItem(Commands.SCALING, String.valueOf(zoom));
				}
			}

			int page = commonValues.getCurrentPage();
			logger.info("Page " + page + " isMultiPageTiff " + isMultiPageTiff);
			//Multipage tiff should be treated as a single page
			
			if(isMultiPageTiff)
				page = 1;

			//always check in facing mode with turnover on
			if (index != -1 || decode_pdf.getDisplayView() == Display.SINGLE_PAGE  || (decode_pdf.getDisplayView() == Display.FACING && currentCommands.getPages().getBoolean(Display.BoolValue.TURNOVER_ON))) {
				PdfPageData pageData = decode_pdf.getPdfPageData();
				int cw,ch,raw_rotation=0;

				if (decode_pdf.getDisplayView()==Display.FACING)
					raw_rotation=pageData.getRotation(page);

				boolean isRotated = (rotation+raw_rotation)%180==90;

				PageOffsets offsets = (PageOffsets)decode_pdf.getExternalHandler(Options.CurrentOffset);
				switch(decode_pdf.getDisplayView()) {
				case Display.CONTINUOUS_FACING:
					if (isRotated) {
						cw = offsets.getMaxH()*2;
						ch = offsets.getMaxW();
					}else{
						cw = offsets.getMaxW()*2;
						ch = offsets.getMaxH();
					}
					break;
				case Display.CONTINUOUS:
					if (isRotated) {
						cw = offsets.getMaxH();
						ch = offsets.getMaxW();
					}else{
						cw = offsets.getMaxW();
						ch = offsets.getMaxH();
					}
					break;
				case Display.FACING:
					int leftPage;

					if(currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER)) {
						leftPage = (page/2)*2;
						if (commonValues.getPageCount() == 2)
							leftPage = 1;
					} else {
						leftPage = (page);
						if ((leftPage & 1) == 0)
							leftPage--;
					}

					if (isRotated) {
						cw = pageData.getCropBoxHeight(leftPage);

						//if first or last page double the width, otherwise add other page width
						if (leftPage+1 > commonValues.getPageCount() || leftPage == 1)
							cw = cw * 2;
						else
							cw += pageData.getCropBoxHeight(leftPage+1);

						ch = pageData.getCropBoxWidth(leftPage);
						if (leftPage+1 <= commonValues.getPageCount() && ch < pageData.getCropBoxWidth(leftPage+1))
							ch = pageData.getCropBoxWidth(leftPage+1);
					}else{
						cw = pageData.getCropBoxWidth(leftPage);

						//if first or last page double the width, otherwise add other page width
						if (leftPage+1 > commonValues.getPageCount())
							cw = cw * 2;
						else
							cw += pageData.getCropBoxWidth(leftPage+1);

						ch = pageData.getCropBoxHeight(leftPage);
						if (leftPage+1 <= commonValues.getPageCount() && ch < pageData.getCropBoxHeight(leftPage+1))
							ch = pageData.getCropBoxHeight(leftPage+1);
					}
					break;
				default:
					if (isRotated) {
						cw = pageData.getCropBoxHeight(page);
						ch = pageData.getCropBoxWidth(page);
					}else{
						cw = pageData.getCropBoxWidth(page);
						ch = pageData.getCropBoxHeight(page);
					}
				}

				//Add space at the bottom for pageFlow
				if (decode_pdf.getDisplayView()==Display.PAGEFLOW)
					ch = PageOffsets.getPageFlowExtraHeight(ch);

				if(isSingle){

					if(displayPane!=null)
						width = width-displayPane.getDividerSize();

				}

				float x_factor=0,y_factor=0,window_factor=0;
				logger.info("width " + width + " height " + height );
				logger.info("cw " + cw + " ch " + ch );
				x_factor = width / cw;
				y_factor = height / ch;
				logger.info("x_factor " + x_factor + " y_factor "+ y_factor );

				if(x_factor<y_factor)
					window_factor = x_factor;
				else
					window_factor = y_factor;

				logger.info("index " + index);
				if(index!=-1){
					if(index<3){ //handle scroll to width/height/window
						if(index==0){//window
							scaling = window_factor;
						}else if(index==1)//height
							scaling = y_factor;
						else if(index==2)//width
							scaling = x_factor;
					}else{
						scaling=decode_pdf.getDPIFactory().adjustScaling(scalingFloatValues[index]);
					}
				}
				if (decode_pdf.getDisplayView() == Display.FACING) { //Enable turnover if both pages properly displayed
					if (scaling <= window_factor) {
						pageTurnScalingAppropriate = true;
					} else {
						pageTurnScalingAppropriate = false;
					}
				}

				if(thumbscroll!=null){
					if(decode_pdf.getDisplayView() == Display.SINGLE_PAGE && scaling<=window_factor){
						scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
						scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//						scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//						scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

						//decode_pdf.setVisible(true);
						thumbscroll.setVisible(true);
					}else{
						scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

						thumbscroll.setVisible(false);
					}
				}
			}

			//this check for 0 to avoid error  and replace with 1
			//PdfPageData pagedata = decode_pdf.getPdfPageData();
			//if((pagedata.getCropBoxHeight(commonValues.getCurrentPage())*scaling<100) &&//keep the page bigger than 100 pixels high
			//        (pagedata.getCropBoxWidth(commonValues.getCurrentPage())*scaling<100) && commonValues.isPDF()){//keep the page bigger than 100 pixels wide
			//    scaling=1;
			//    setSelectedComboItem(Commands.SCALING,"100");
			//}

			// THIS section commented out so altering scalingbox does NOT reset rotation
			//if(!scalingBox.getSelectedIndex()<3){
			/**update our components*/
			//resetRotationBox();
			//}

			//Ensure page rotation is taken into account
			//int pageRot = decode_pdf.getPdfPageData().getRotation(commonValues.getCurrentPage());
			//allow for clicking on it before page opened
			logger.info("scaling " + scaling + " page " + page + " rotation " + rotation);
			if( scaling<1)
				scaling=1;
			decode_pdf.setPageParameters(scaling, page,rotation);

			//Ensure the page is displayed in the correct rotation
			setRotation();

			//move to correct page
			//setPageNumber();
			//decode_pdf.setDisplayView(decode_pdf.getDisplayView(),Display.DISPLAY_CENTERED);

			//open new page
			//if((!commonValues.isProcessing())&&(commonValues.getCurrentPage()!=newPage)){

			//commonValues.setCurrentPage(newPage);
			//decodePage(false);
			//currentGUI.zoom();
			//}

			//ensure at same page location

			Runnable updateAComponent = new Runnable() {
				public void run() {
					//
					logger.info("nel run");
					decode_pdf.invalidate();
					decode_pdf.updateUI();
					decode_pdf.validate();

					scrollPane.invalidate();
					scrollPane.updateUI();
					scrollPane.validate();

					//move to correct page
					//scrollToPage is handled via the page change code so no need to do it here
					//					if(commonValues.isPDF())
					//					scrollToPage(commonValues.getCurrentPage());
					//scrollPane.getViewport().scrollRectToVisible(new Rectangle((int)(x*scaling)-1,(int)(y*scaling),1,1));
					//System.out.println("Scroll to page="+y+" "+(y*scaling)+" "+scaling);

				}
			};
			//boolean callAsThread=SwingUtilities.isEventDispatchThread();
			//if (callAsThread)
			//	scroll
			SwingUtilities.invokeLater(updateAComponent);
			//			else{

			//			//move to correct page
			//			if(commonValues.isPDF())
			//			scrollToPage(commonValues.getCurrentPage());

			//			scrollPane.updateUI();

			//			}
			//decode_pdf.invalidate();
			//scrollPane.updateUI();
			//decode_pdf.repaint();
			//scrollPane.repaint();
			//frame.validate();


		}


	}


	public void snapScalingToDefaults(float newScaling) {
		newScaling = decode_pdf.getDPIFactory().adjustScaling(newScaling /100);
		
		float width,height;

		if(isSingle){
			width = scrollPane.getViewport().getWidth()-inset-inset;
			height = scrollPane.getViewport().getHeight()-inset-inset;
		}else{
			width=desktopPane.getWidth();
			height=desktopPane.getHeight();
		}

		PdfPageData pageData = decode_pdf.getPdfPageData();
		int cw,ch,raw_rotation=0;

		if (decode_pdf.getDisplayView()==Display.FACING)
			raw_rotation=pageData.getRotation(commonValues.getCurrentPage());

		boolean isRotated = (rotation+raw_rotation)%180==90;

		PageOffsets offsets = (PageOffsets)decode_pdf.getExternalHandler(Options.CurrentOffset);
		switch(decode_pdf.getDisplayView()) {
		case Display.CONTINUOUS_FACING:
			if (isRotated) {
				cw = offsets.getMaxH()*2;
				ch = offsets.getMaxW();
			}else{
				cw = offsets.getMaxW()*2;
				ch = offsets.getMaxH();
			}
			break;
		case Display.CONTINUOUS:
			if (isRotated) {
				cw = offsets.getMaxH();
				ch = offsets.getMaxW();
			}else{
				cw = offsets.getMaxW();
				ch = offsets.getMaxH();
			}
			break;
		case Display.FACING:
			int leftPage;
			if (currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER)) {
				leftPage = (commonValues.getCurrentPage()/2)*2;
				if (commonValues.getPageCount() == 2)
					leftPage = 1;
			} else {
				leftPage = commonValues.getCurrentPage();
				if ((leftPage & 1)==0)
					leftPage--;
			}

			if (isRotated) {
				cw = pageData.getCropBoxHeight(leftPage);

				//if first or last page double the width, otherwise add other page width
				if (leftPage+1 > commonValues.getPageCount() || leftPage == 1)
					cw = cw * 2;
				else
					cw += pageData.getCropBoxHeight(leftPage+1);

				ch = pageData.getCropBoxWidth(leftPage);
				if (leftPage+1 <= commonValues.getPageCount() && ch < pageData.getCropBoxWidth(leftPage+1))
					ch = pageData.getCropBoxWidth(leftPage+1);
			}else{
				cw = pageData.getCropBoxWidth(leftPage);

				//if first or last page double the width, otherwise add other page width
				if (leftPage+1 > commonValues.getPageCount())
					cw = cw * 2;
				else
					cw += pageData.getCropBoxWidth(leftPage+1);

				ch = pageData.getCropBoxHeight(leftPage);
				if (leftPage+1 <= commonValues.getPageCount() && ch < pageData.getCropBoxHeight(leftPage+1))
					ch = pageData.getCropBoxHeight(leftPage+1);
			}
			break;
		default:
			if (isRotated) {
				cw = pageData.getCropBoxHeight(commonValues.getCurrentPage());
				ch = pageData.getCropBoxWidth(commonValues.getCurrentPage());
			}else{
				cw = pageData.getCropBoxWidth(commonValues.getCurrentPage());
				ch = pageData.getCropBoxHeight(commonValues.getCurrentPage());
			}
		}

		//Add space at the bottom for pageFlow
		if (decode_pdf.getDisplayView()==Display.PAGEFLOW)
			ch = PageOffsets.getPageFlowExtraHeight(ch);

		if(isSingle){
			if(displayPane!=null)
				width = width-displayPane.getDividerSize();
		}

		float x_factor,y_factor,window_factor;
		x_factor = width / cw;
		y_factor = height / ch;

		if(x_factor<y_factor) {
			window_factor = x_factor;
			x_factor = -1;
		} else {
			window_factor = y_factor;
			y_factor = -1;
		}

		if (getSelectedComboIndex(Commands.SCALING)!=0 &&
				((newScaling < window_factor * 1.1 && newScaling > window_factor *0.91) ||
						((window_factor > scaling && window_factor < newScaling) || (window_factor < scaling && window_factor > newScaling)))) {
			setSelectedComboIndex(Commands.SCALING, 0);
			scaling = window_factor;
		}

		else if (y_factor!=-1 &&
				getSelectedComboIndex(Commands.SCALING)!=1 &&
				((newScaling < y_factor * 1.1 && newScaling > y_factor * 0.91) ||
						((y_factor > scaling && y_factor < newScaling) || (y_factor < scaling && y_factor > newScaling)))) {
			setSelectedComboIndex(Commands.SCALING, 1);
			scaling = y_factor;
		}

		else if (x_factor!=-1 &&
				getSelectedComboIndex(Commands.SCALING)!=2 &&
				((newScaling < x_factor * 1.1 && newScaling > x_factor * 0.91) ||
						((x_factor > scaling && x_factor < newScaling) || (x_factor < scaling && x_factor > newScaling)))) {
			setSelectedComboIndex(Commands.SCALING, 2);
			scaling = x_factor;
		}

		else {
			setSelectedComboItem(Commands.SCALING, String.valueOf((int)decode_pdf.getDPIFactory().removeScaling(newScaling *100)));
			scaling = newScaling;
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#rotate()
	 */
	public void rotate() {
		rotation=Integer.parseInt((String) getSelectedComboItem(Commands.ROTATION));
		scaleAndRotate(scaling, rotation);
		decode_pdf.updateUI();

	}

	public void scrollToPage(int page){

		commonValues.setCurrentPage(page);

		if(commonValues.getCurrentPage()>0){

			int yCord =0;
			int xCord =0;

			if(decode_pdf.getDisplayView()!=Display.SINGLE_PAGE){
				if(decode_pdf.getDisplayView()==Display.PAGEFLOW){
					yCord = decode_pdf.getY();
					xCord = currentCommands.pages.getXCordForPage(commonValues.getCurrentPage(),scaling);
				}else{
					yCord = currentCommands.pages.getYCordForPage(commonValues.getCurrentPage(),scaling);
					xCord = 0;
				}
			}
			//System.out.println("Before="+decode_pdf.getVisibleRect()+" "+decode_pdf.getPreferredSize());

			PdfPageData pageData = decode_pdf.getPdfPageData();

			int ch = (int)(pageData.getCropBoxHeight(commonValues.getCurrentPage())*scaling);
			int cw = (int)(pageData.getCropBoxWidth(commonValues.getCurrentPage())*scaling);

			int centerH = xCord + ((cw-scrollPane.getHorizontalScrollBar().getVisibleAmount())/2);
			int centerV = yCord + (ch-scrollPane.getVerticalScrollBar().getVisibleAmount())/2;

			//PAGEFLOW works differently
			if(decode_pdf.getDisplayView()==Display.PAGEFLOW)
				centerH=xCord-decode_pdf.getInsetW();

			scrollPane.getHorizontalScrollBar().setValue(centerH);
			scrollPane.getVerticalScrollBar().setValue(centerV);



			//			decode_pdf.scrollRectToVisible(new Rectangle(0,(int) (yCord),(int)r.width-1,(int)r.height-1));
			//			decode_pdf.scrollRectToVisible(new Rectangle(0,(int) (yCord),(int)r.width-1,(int)r.height-1));

			//System.out.println("After="+decode_pdf.getVisibleRect()+" "+decode_pdf.getPreferredSize());

			//System.out.println("Scroll to page="+commonValues.getCurrentPage()+" "+yCord+" "+(yCord*scaling)+" "+scaling);
		}

		if(decode_pdf.getPageCount()>1 && !commonValues.isContentExtractor())
			setPageLayoutButtonsEnabled(true);

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#decodePage(boolean)
	 */
	public void decodePage(final boolean resizePanel){
		//Remove Image extraction outlines when page is changed
		currentCommands.getPages().setHighlightedImage(null);

		resetRotationBox();

		/** if running terminate first */
		if(thumbnails.isShownOnscreen())
			thumbnails.terminateDrawing();

//		if(thumbnails.isShownOnscreen()){
//
//			LinearThread linearizedBackgroundRenderer = (LinearThread) decode_pdf.getJPedalObject(PdfDictionary.LinearizedReader);
//
//			if(linearizedBackgroundRenderer==null ||
//					(linearizedBackgroundRenderer!=null && !linearizedBackgroundRenderer.isAlive()))
//				setupThumbnailPanel();
//			
//		}

		if(decode_pdf.getDisplayView()==Display.SINGLE_PAGE){
			pageCounter2.setForeground(Color.black);
			pageCounter2.setText(String.valueOf(commonValues.getCurrentPage()));
			pageCounter3.setText(Messages.getMessage("PdfViewerOfLabel.text") + ' ' + commonValues.getPageCount());
		}

		//Set textbox size
		int col = ("/"+commonValues.getPageCount()).length();
		if (decode_pdf.getDisplayView() == Display.FACING || decode_pdf.getDisplayView() == Display.CONTINUOUS_FACING)
			col = col * 2;
		if (col < 4)
			col = 4;
		if (col > 10)
			col = 10;
		pageCounter2.setColumns(col);
		pageCounter2.setMaximumSize(pageCounter2.getPreferredSize());
		navToolBar.invalidate();
		navToolBar.doLayout();


		//allow user to now open tabs
		tabsNotInitialised=false;

		boolean isContentExtractor=commonValues.isContentExtractor();
		decode_pdf.unsetScaling();

		/**ensure text and color extracted. If you do not need color, take out
		 * line for faster decode
		 */
		if(isContentExtractor)
			decode_pdf.setExtractionMode(PdfDecoder.TEXT);
		else
			decode_pdf.setExtractionMode(PdfDecoder.TEXT+PdfDecoder.TEXTCOLOR);


		//remove any search highlight
		decode_pdf.getTextLines().clearHighlights();


		//stop user changing scaling while decode in progress
		resetComboBoxes(false);
		if(!commonValues.isContentExtractor())
			setPageLayoutButtonsEnabled(false);

		if(!commonValues.isContentExtractor())
			Values.setProcessing(true);

		//SwingWorker worker = new SwingWorker() {

		//	public Object construct() {
		decode_pdf.setPDFCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {

			statusBar.updateStatus("Decoding Page",0);

			/**
			 * make sure screen fits display nicely
			 */
			//if ((resizePanel) && (thumbnails.isShownOnscreen()))
			//	zoom();

			//			if (Thread.interrupted())
			//				throw new InterruptedException();

			/**
			 * decode the page
			 */
			try {
				decode_pdf.decodePage(commonValues.getCurrentPage());

				//wait to ensure decoded
				decode_pdf.waitForDecodingToFinish();


				if(!decode_pdf.getPageDecodeStatus(DecodeStatus.ImagesProcessed)){

					String status = (Messages.getMessage("PdfViewer.ImageDisplayError")+
							Messages.getMessage("PdfViewer.ImageDisplayError1")+
							Messages.getMessage("PdfViewer.ImageDisplayError2")+
							Messages.getMessage("PdfViewer.ImageDisplayError3")+
							Messages.getMessage("PdfViewer.ImageDisplayError4")+
							Messages.getMessage("PdfViewer.ImageDisplayError5")+
							Messages.getMessage("PdfViewer.ImageDisplayError6")+
							Messages.getMessage("PdfViewer.ImageDisplayError7"));

					showMessageDialog(status);
				}

				/**
				 * see if lowres poor quality image and flag up if so
				 *
                        String imageDetailStr=decode_pdf.getInfo(PdfDictionary.Image);

                        //get iterator (each image is a single line)
                        StringTokenizer allImageDetails=new StringTokenizer(imageDetailStr,"\n");


                        while(allImageDetails.hasMoreTokens()){

                            String str=allImageDetails.nextToken();
                            StringTokenizer imageDetails=new StringTokenizer(str," ()");

                            //System.out.println(imageDetails.countTokens()+" ==>"+str);
                            //if single image check further
                            if(imageDetails.countTokens()>2){ //ignore forms
                                String imageName=imageDetails.nextToken();
                                String imageType=imageDetails.nextToken();
                                String imageW=imageDetails.nextToken().substring(2);
                                String imageH=imageDetails.nextToken().substring(2);
                                String bitsPerPixel=imageDetails.nextToken();
                                String dpi=imageDetails.nextToken().substring(4);

                                //we can also look at PDF creation tool
                                String[] metaData=decode_pdf.getFileInformationData().getFieldValues();

                                //test here and take action or set flag
                                if(Integer.parseInt(dpi)<144 && metaData[5].equals("iText 2.1.7 by 1T3XT")){
                                    System.out.println("Low resolution image will not print well in Java");
                                }
                            }
                        }

                        /**/

				/**
				 * Tell user if hinting is probably required
				 */
				if(decode_pdf.getPageDecodeStatus(DecodeStatus.TTHintingRequired)){

					String status = Messages.getMessage("PdfCustomGui.ttHintingRequired");

					showMessageDialog(status);
				}

				if(decode_pdf.getPageDecodeStatus(DecodeStatus.NonEmbeddedCIDFonts)){

					String status = ("This page contains non-embedded CID fonts \n" +
							decode_pdf.getPageDecodeStatusReport(DecodeStatus.NonEmbeddedCIDFonts)+
							"\nwhich may need mapping to display correctly.\n" +
							"See http://www.jpedal.org/support_FontSub.php");

					showMessageDialog(status);
				}
				//read values for page display
				PdfPageData page_data = decode_pdf.getPdfPageData();

				mediaW  = page_data.getMediaBoxWidth(commonValues.getCurrentPage());
				mediaH = page_data.getMediaBoxHeight(commonValues.getCurrentPage());
				mediaX = page_data.getMediaBoxX(commonValues.getCurrentPage());
				mediaY = page_data.getMediaBoxY(commonValues.getCurrentPage());

				cropX = page_data.getCropBoxX(commonValues.getCurrentPage());
				cropY = page_data.getCropBoxY(commonValues.getCurrentPage());
				cropW = page_data.getCropBoxWidth(commonValues.getCurrentPage());
				cropH = page_data.getCropBoxHeight(commonValues.getCurrentPage());

				//						resetRotationBox();

				//create custom annot icons
				if(decode_pdf.getExternalHandler(Options.UniqueAnnotationHandler)!=null){
					/**
					 * ANNOTATIONS code to create unique icons
					 *
					 * this code allows you to create a unique set on icons for any type of annotations, with
					 * an icons for every annotation, not just types.
					 */
					FormFactory formfactory = decode_pdf.getFormRenderer().getFormFactory();

					//swing needs it to be done with invokeLater
					if(formfactory.getType()== FormFactory.SWING){
						final Runnable doPaintComponent2 = new Runnable() {
							public void run() {

								createUniqueAnnotationIcons();

								//validate();
							}
						};
						SwingUtilities.invokeLater(doPaintComponent2);

					}else{
						createUniqueAnnotationIcons();
					}


				}

				statusBar.updateStatus("Displaying Page",0);

			} catch (Exception e) {
				System.err.println(Messages.getMessage("PdfViewerError.Exception")+ ' ' + e +
						' ' +Messages.getMessage("PdfViewerError.DecodePage"));
				e.printStackTrace();
				Values.setProcessing(false);
			}


			//tell user if we had a memory error on decodePage
			String status=decode_pdf.getPageDecodeReport();
			if((status.contains("java.lang.OutOfMemoryError"))&& PdfDecoder.showErrorMessages){
				status = (Messages.getMessage("PdfViewer.OutOfMemoryDisplayError")+
						Messages.getMessage("PdfViewer.OutOfMemoryDisplayError1")+
						Messages.getMessage("PdfViewer.OutOfMemoryDisplayError2")+
						Messages.getMessage("PdfViewer.OutOfMemoryDisplayError3")+
						Messages.getMessage("PdfViewer.OutOfMemoryDisplayError4")+
						Messages.getMessage("PdfViewer.OutOfMemoryDisplayError5"));

				showMessageDialog(status);

			}

			Values.setProcessing(false);

			//make sure fully drawn
			//decode_pdf.repaint();

			setViewerTitle(null); //restore title

			currentCommands.setPageProperties(getSelectedComboItem(Commands.ROTATION), getSelectedComboItem(Commands.SCALING));

//			if (decode_pdf.getPageCount()>0 && thumbnails.isShownOnscreen() && decode_pdf.getDisplayView()==Display.SINGLE_PAGE)
//				thumbnails.generateOtherVisibleThumbnails(commonValues.getCurrentPage());

		} catch (Exception e) {
			e.printStackTrace();
			Values.setProcessing(false);//remove processing flag so that the viewer can be exited.
			setViewerTitle(null); //restore title
		}

		selectBookmark();

		//Update multibox
		statusBar.setProgress(100);
		if (useNewLayout) {
			//                    ActionListener listener = new ActionListener(){
			//                        public void actionPerformed(ActionEvent e) {
			//                            setMultibox(new int[]{});
			//                        }
			//                    };
			//                    t = new Timer(800, listener);
			//                    t.setRepeats(false);
			//                    t.start();

			try{
				Thread.sleep(800);
			}catch(Exception e){
			}
			setMultibox(new int[]{});
		}

		//reanable user changing scaling
		resetComboBoxes(true);
		if(decode_pdf.getPageCount()>1 && !commonValues.isContentExtractor())
			setPageLayoutButtonsEnabled(true);

		addFormsListeners();

		
		


		logger.info("displayPane " + displayPane);
		logger.info("displayPane " + displayPane.getWidth() + "  " + displayPane.getComponentCount());
		logger.info("displayPane " + displayPane.getDividerSize() + "  " + displayPane.getOrientation());
		
		if(displayPane!=null){
			
			//reinitialiseTabs(displayPane.getDividerLocation() > startSize);
			createMainMenu(true);
			createOtherMenu();
			createNavbar();
			addCursor();
			loadProperties();
			if(commonValues.getSelectedFile()!=null){
				thumbnailsInPanel();
				thumbnails.setThumbnailsEnabled(true);
				thumbnails.resetToDefault();
			}

			searchInTab(searchFrame);
			
		}

		finishedDecoding=true;

		//ANNA
		scrollPane.updateUI();

		zoom(false);

		decode_pdf.setPDFCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		//return null;
		//}
		//};

		//worker.start();

		//zoom(false);
		/**/
	}

	//	<link><a name="listen" />

	/**this method adds listeners to GUI widgets to track changes*/
	public void addFormsListeners(){

		//rest forms changed flag to show no changes
		commonValues.setFormsChanged(false);

		/**see if flag set - not default behaviour*/
		boolean showMessage=false;
		String formsFlag=System.getProperty("org.jpedal.listenforms");
		if(formsFlag!=null)
			showMessage=true;

		//get the form renderer which also contains the processed form data.
		//if you want simple form data, also look at the ExtractFormDataAsObject.java example
		AcroRenderer formRenderer=decode_pdf.getFormRenderer();

		if(formRenderer==null)
			return;

		//get list of forms on page
		java.util.List formsOnPage=null;

		/**
		 * once you have the name you can also use
		 * formRenderer.getComponentsByName(String objectName)
		 * to get all componentonce you know the name - return Object[] as 
		 * can have multiple values (ie radio/check boxes)
		 * 
		 */
		try {
			formsOnPage = formRenderer.getComponentNameList(commonValues.getCurrentPage());
		} catch (PdfException e) {

			logger.info("Exception "+e+" reading component list");
		}

		//allow for no forms
		if(formsOnPage==null){

			if(showMessage)
				showMessageDialog(Messages.getMessage("PdfViewer.NoFields"));

			return;
		}

		int formCount=formsOnPage.size();

		JPanel formPanel=new JPanel();
		/**
		 * create a JPanel to list forms and tell user a box example
		 **/
		if(showMessage){
			formPanel.setLayout(new BoxLayout(formPanel,BoxLayout.Y_AXIS));
			JLabel formHeader = new JLabel("This page contains "+formCount+" form objects");
			formHeader.setFont(headFont);
			formPanel.add(formHeader);

			formPanel.add(Box.createRigidArea(new Dimension(10,10)));
			JTextPane instructions = new JTextPane();
			instructions.setPreferredSize(new Dimension(450,180));
			instructions.setEditable(false);
			instructions.setText("This provides a simple example of Forms handling. We have"+
					" added a listener to each form so clicking on it shows the form name.\n\n"+
					"Code is in addExampleListeners() in org.examples.viewer.Viewer\n\n"+
					"This could be easily be extended to interface with a database directly "+
					"or collect results on an action and write back using itext.\n\n"+
					"Forms have been converted into Swing components and are directly accessible"+
					" (as is the original data).\n\n"+
					"If you don't like the standard SwingSet you can replace with your own set.");
			instructions.setFont(textFont);
			formPanel.add(instructions);
			formPanel.add(Box.createRigidArea(new Dimension(10,10)));
		}

		/**
		 * pop-up to show forms on page
		 **/
		if(showMessage){
			final JDialog displayFrame =  new JDialog((JFrame)null,true);
			if(commonValues.getModeOfOperation()!=Values.RUNNING_APPLET){
				displayFrame.setLocationRelativeTo(null);
				displayFrame.setLocation(frame.getLocationOnScreen().x+10,frame.getLocationOnScreen().y+10);
			}

			JScrollPane scroll=new JScrollPane();
			scroll.getViewport().add(formPanel);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			displayFrame.setSize(500,500);
			displayFrame.setTitle("List of forms on this page");
			displayFrame.getContentPane().setLayout(new BorderLayout());
			displayFrame.getContentPane().add(scroll,BorderLayout.CENTER);

			JPanel buttonBar=new JPanel();
			buttonBar.setLayout(new BorderLayout());
			displayFrame.getContentPane().add(buttonBar,BorderLayout.SOUTH);

			// close option just removes display
			JButton no=new JButton(Messages.getMessage("PdfViewerButton.Close"));
			no.setFont(new Font("SansSerif", Font.PLAIN, 12));
			buttonBar.add(no,BorderLayout.EAST);
			no.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					displayFrame.dispose();
				}});

			/**show the popup*/
			displayFrame.setVisible(true);
		}

	}

	/**
	 *  put the outline data into a display panel which we can pop up
	 * for the user - outlines, thumbnails
	 *
	private void createOutlinePanels() {

		//boolean hasNavBars=false;

		// set up first 10 thumbnails by default. Rest created as needed.

		//add if statement or comment out this section to remove thumbnails
		setupThumbnailPanel();

		// add any outline

		setBookmarks(false);

		/**
	 * resize to show if there are nav bars
	 *
        if(hasNavBars){
            if(!thumbnails.isShownOnscreen()){
                if( !commonValues.isContentExtractor())
                navOptionsPanel.setVisible(true);
                displayPane.setDividerLocation(divLocation);
                //displayPane.invalidate();
                //displayPane.repaint();

            }
        }
	}/**/

	//	<start-thin>
	public void setupThumbnailPanel() {
		decode_pdf.addExternalHandler(thumbnails, Options.ThumbnailHandler);

		if(isSetup)
			return;

		isSetup=true;

		if(!commonValues.isContentExtractor() && thumbnails.isShownOnscreen()){

			int pages=decode_pdf.getPageCount();

			//setup and add to display

			thumbnails.setupThumbnails(pages,textFont, Messages.getMessage("PdfViewerPageLabel.text"),decode_pdf.getPdfPageData());

			//add listener so clicking on button changes to page - has to be in Viewer so it can update it
			Object[] buttons=thumbnails.getButtons();
			for(int i=0;i<pages;i++)
				((JButton)buttons[i]).addActionListener(new PageChanger(i));

			//add global listener
			thumbnails.addComponentListener();

		}
	}

	//	<end-thin>

	public void setBookmarks(boolean alwaysGenerate) {

		//ignore if not opened
		int currentSize=displayPane.getDividerLocation();

		if((currentSize==startSize)&& !alwaysGenerate)
			return;


		//ignore if already done and flag
		if(bookmarksGenerated){
			return;
		}
		bookmarksGenerated=true;

		org.w3c.dom.Document doc=decode_pdf.getOutlineAsXML();

		Node rootNode= null;
		if(doc!=null)
			rootNode= doc.getFirstChild();

		if(rootNode!=null){

			tree.reset(rootNode);

			//Listen for when the selection changes - looks up dests at present
			((JTree) tree.getTree()).addTreeSelectionListener(new TreeSelectionListener(){

				/** Required by TreeSelectionListener interface. */
				public void valueChanged(TreeSelectionEvent e) {

					DefaultMutableTreeNode node = tree.getLastSelectedPathComponent();

					if (node == null)
						return;

					/**get title and open page if valid*/
					//String title=(String)node.getUserObject();

					JTree jtree = ((JTree) tree.getTree());

					DefaultTreeModel treeModel = (DefaultTreeModel) jtree.getModel();

					List flattenedTree = new ArrayList();

					/** flatten out the tree so we can find the index of the selected node */
					getFlattenedTreeNodes((TreeNode) treeModel.getRoot(), flattenedTree);
					flattenedTree.remove(0); // remove the root node as we don't account for this

					int index = flattenedTree.indexOf(node);


					//String page = tree.getPageViaNodeNumber(index);
					String ref = tree.convertNodeIDToRef(index);

					PdfObject Aobj=decode_pdf.getOutlineData().getAobj(ref);

					//handle in our handler code
					if(Aobj!=null){
						/*int pageToDisplay=*/decode_pdf.getFormRenderer().getActionHandler().gotoDest(Aobj, ActionHandler.MOUSECLICKED, PdfDictionary.Dest );

						//align to viewer knows if page changed and set rotation to default for page
						//                        if(pageToDisplay!=-1){
						//                        	currentCommands.gotoPage(Integer.toString(pageToDisplay));
						//                            commonValues.setCurrentPage(pageToDisplay);
						//                            setSelectedComboIndex(Commands.ROTATION, decode_pdf.getPdfPageData().getRotation(pageToDisplay)/90); 
						//                        }
					}

					//                    if((page==null)||(page.length()==0))
					//                    page=tree.getPage(title);
					//
					//                    if(page!=null && page.length()>0){
					//						int pageToDisplay=Integer.parseInt(page);
					//
					//						if((!commonValues.isProcessing())&&(commonValues.getCurrentPage()!=pageToDisplay)){
					//							commonValues.setCurrentPage(pageToDisplay);
					//							/**reset as rotation may change!*/
					//
					//							decode_pdf.setPageParameters(getScaling(), commonValues.getCurrentPage());
					//							decodePage(false);
					//						}
					//
					//						//Point p= tree.getPoint(title);
					//						//if(p!=null)
					//						//	decode_pdf.ensurePointIsVisible(p);
					//
					//					}else{
					//						showMessageDialog(Messages.getMessage("PdfViewerError.NoBookmarkLink")+title);
					//						System.out.println("No dest page set for "+title);
					//					}
				}
			});

		}else{
			tree.reset(null);
		}
	}

	private static void getFlattenedTreeNodes(TreeNode theNode, List items) {
		// add the item
		items.add(theNode);

		// recursion
		for (Enumeration theChildren = theNode.children(); theChildren.hasMoreElements();) {
			getFlattenedTreeNodes((TreeNode) theChildren.nextElement(), items);
		}
	}

	private void selectBookmark() {
		if(decode_pdf.hasOutline()&&(tree!=null))
			tree.selectBookmark();

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#initStatus()
	 */
	public void initStatus() {
		decode_pdf.setStatusBarObject(statusBar);

		//<start-wrap>
		//and initialise the display
		if (!useNewLayout)
			comboBoxBar.add(statusBar.getStatusObject());
		else
			setMultibox(new int[]{});
		//<end-wrap>

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#initThumbnails(int, org.jpedal.utils.repositories.Vector_Int)
	 */
	public void initThumbnails(int itemSelectedCount, Vector_Int pageUsed) {

		navOptionsPanel.removeAll();
		if(thumbnails.isShownOnscreen())
			thumbnails.setupThumbnails(itemSelectedCount-1,pageUsed.get(),commonValues.getPageCount());

		if(PdfDecoder.isRunningOnMac){
			navOptionsPanel.add((Component) thumbnails,"Extracted items");
		}else{
			VTextIcon textIcon2 = new VTextIcon(navOptionsPanel, "Extracted items", VTextIcon.ROTATE_LEFT);
			navOptionsPanel.addTab(null, textIcon2, (Component) thumbnails);
		}

		displayPane.setDividerLocation(150);

	}

	//moved to method so we can call it from the already added actions listeners on the forms
	public void checkformSavedMessage(){
		String propValue = properties.getValue("showsaveformsmessage");
		boolean showSaveFormsMessage = false;

		if(propValue.length()>0)
			showSaveFormsMessage = propValue.equals("true");

		if(showSaveFormsMessage && firstTimeFormMessage && commonValues.isFormsChanged()==false){
			firstTimeFormMessage=false;

			JPanel panel =new JPanel();
			panel.setLayout(new GridBagLayout());
			final GridBagConstraints p = new GridBagConstraints();

			p.anchor=GridBagConstraints.WEST;
			p.gridx = 0;
			p.gridy = 0;
			String str=(Messages.getMessage("PdfViewerFormsWarning.ChangedFormsValue"));
			if(!commonValues.isItextOnClasspath())
				str=(Messages.getMessage("PdfViewerFormsWarning.ChangedFormsValueNoItext"));

			JCheckBox cb=new JCheckBox();
			cb.setText(Messages.getMessage("PdfViewerFormsWarning.CheckBox"));
			Font font = cb.getFont();

			JTextArea ta=new JTextArea(str);
			ta.setOpaque(false);
			ta.setFont(font);

			p.ipady=20;
			panel.add(ta, p);

			p.ipady=0;
			p.gridy = 1;
			panel.add(cb,p);

			JOptionPane.showMessageDialog(frame,panel);


			if(cb.isSelected())
				properties.setValue("showsaveformsmessage","false");

		}
		commonValues.setFormsChanged(true);
		setViewerTitle(null);
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setCoordText(java.lang.String)
	 */
	public void setCoordText(String string) {
		coords.setText(string);
	}

	private JLabel initCoordBox() {

		coords.setBackground(Color.white);
		coords.setOpaque(true);

		if (useNewLayout) {
			coords.setBorder(BorderFactory.createEtchedBorder());
			coords.setPreferredSize(memoryBar.getPreferredSize());
		}else {
			coords.setPreferredSize(new Dimension(120,20));
			coords.setBorder(BorderFactory.createLineBorder(Color.black,1));
		}

		coords.setText("  X: "+ " Y: " + ' ' + ' ');


		return coords;

	}

	//When page changes make sure only relevant navigation buttons are displayed
	public void hideRedundentNavButtons(){

		int maxPages = decode_pdf.getPageCount();
		if(commonValues.isMultiTiff()){
			maxPages = commonValues.getPageCount();
		}

		if (((decode_pdf.getDisplayView() == Display.FACING && currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER)) ||
				decode_pdf.getDisplayView() == Display.CONTINUOUS_FACING) 
				&& (maxPages & 1)==1)
			maxPages--;

		if(commonValues.getCurrentPage()==1)
			setBackNavigationButtonsEnabled(false);
		else
			setBackNavigationButtonsEnabled(true);

		if(commonValues.getCurrentPage()==maxPages)
			setForwardNavigationButtonsEnabled(false);
		else
			setForwardNavigationButtonsEnabled(true);

		//update single mode toolbar to be visible in only SINGLE if set
		if(thumbscroll!=null){
			if(decode_pdf.getDisplayView() == Display.SINGLE_PAGE){

				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				thumbscroll.setVisible(true);
			}else if (decode_pdf.getDisplayView() == Display.PAGEFLOW3D || decode_pdf.getDisplayView() == Display.PAGEFLOW){

				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				thumbscroll.setVisible(false);
			}else{

				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

				thumbscroll.setVisible(false);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setPageNumber()
	 */
	public void setPageNumber() {

		logger.info("setPageNumber:::::" +  SwingUtilities.isEventDispatchThread());
		if (SwingUtilities.isEventDispatchThread())
			setPageNumberWorker();
		else {
			Runnable r = new Runnable(){
				public void run() {
					setPageNumberWorker();
				}
			};
			SwingUtilities.invokeLater(r);
		}
	}

	public boolean isMultiPageTiff() {
		return isMultiPageTiff;
	}

	public void setMultiPageTiff(boolean isMultiPageTiff) {
		this.isMultiPageTiff = isMultiPageTiff;
	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setPageNumber()
	 */
	private void setPageNumberWorker() {

		if (!decode_pdf.isOpen() && !isMultiPageTiff) {
			pageCounter2.setText(" ");
		} else {

			if(previewOnSingleScroll && thumbscroll!=null){

				//hack because Listern does not f******g work on windows
				scrollListener.ignoreChange=true;
				thumbscroll.setMaximum(decode_pdf.getPageCount());
				scrollListener.ignoreChange=true;
				thumbscroll.setValue(commonValues.getCurrentPage()-1);
				scrollListener.ignoreChange=false;
				if(debugThumbnail)
					logger.info("setpage="+commonValues.getCurrentPage());
			}

			int currentPage = commonValues.getCurrentPage();
			logger.info("currentPage " + currentPage);
			if (decode_pdf.getDisplayView() == Display.FACING || decode_pdf.getDisplayView() == Display.CONTINUOUS_FACING) {
				if (decode_pdf.getPageCount() == 2)
					pageCounter2.setText("1/2");
				else if (currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER) || decode_pdf.getDisplayView() == Display.CONTINUOUS_FACING) {
					int base = currentPage & -2;
					if (base != decode_pdf.getPageCount() && base != 0)
						pageCounter2.setText(base + "/"+(base+1));
					else
						pageCounter2.setText(String.valueOf(currentPage));
				} else {
					int base = currentPage - (1 - (currentPage & 1));
					if (base != decode_pdf.getPageCount())
						pageCounter2.setText(base + "/" + (base+1));
					else
						pageCounter2.setText(String.valueOf(currentPage));
				}

			} else {
				pageCounter2.setText(String.valueOf(currentPage));
			}
			pageCounter3.setText(Messages.getMessage("PdfViewerOfLabel.text") + ' ' + commonValues.getPageCount()); //$NON-NLS-1$
			hideRedundentNavButtons();
		}
	}

	public int getPageNumber(){
		return commonValues.getCurrentPage();
	}

	/**
	 * note - to plugin put all on single line so addButton values over-riddern
	 */
	private void createNavbar() {

		List v = new ArrayList();

		//<start-pdfhelp>
		if(memoryMonitor==null){ //ensure only 1 instance
			memoryMonitor = new Timer(500, new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					//System.out.println("FREE " + Runtime.getRuntime().freeMemory() );
					int free = (int) (Runtime.getRuntime().freeMemory() / (1024 * 1024));
					//System.out.println("FREE " + free );
					//System.out.println("TOTAL " + Runtime.getRuntime().totalMemory() );
					int total = (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024));
					//System.out.println("TOTAL " + total );
					
					//this broke the image saving when it was run every time
					if(finishedDecoding){
						finishedDecoding=false;
					}

				
					//System.out.println((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000);
					memoryBar.setMaximum(total);
					//memoryBar.setValue(total-free);
					memoryBar.setStringPainted(false);
					//memoryBar.setStringPainted(true);
					//memoryBar.setString((total-free)+"M of "+total+ 'M');
					if( commonValues!=null && commonValues.getSelectedFile()!=null ){
						File f = new File(commonValues.getSelectedFile());
						double bytes = f.length();
						double kilobytes = (bytes / 1024);
						double megabytes = (kilobytes / 1024);
						//System.out.println(megabytes);
						memoryBar.setStringPainted(true);
						memoryBar.setString(megabytes + "M");
					}
				}
			});
			memoryMonitor.start();
		}

		if (!useNewLayout) {
			navButtons.add(memoryBar,BorderLayout.WEST);
		} else {
			multibox.setLayout(new BorderLayout());

			if(commonValues.getModeOfOperation()!=Values.RUNNING_PLUGIN)
				navButtons.add(multibox, BorderLayout.WEST);
		}
		//<end-pdfhelp>
		navButtons.add(Box.createHorizontalGlue());

		/**
		 * navigation toolbar for moving between pages
		 */
		navToolBar.add(Box.createHorizontalGlue());

		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.RewindToStart"),iconLocation+"start.gif",Commands.FIRSTPAGE);


		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.Rewind10"),iconLocation+"fback.gif",Commands.FBACKPAGE);


		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.Rewind1"),iconLocation+"back.gif",Commands.BACKPAGE);

		/**put page count in middle of forward and back*/
		pageCounter1 = new JLabel(Messages.getMessage("PdfViewerPageLabel.text"));
		pageCounter1.setOpaque(false);
		navToolBar.add(pageCounter1);
		//		pageCounter2.setMaximumSize(new Dimension(5,50));
		navToolBar.add(pageCounter2);
		navToolBar.add(pageCounter3);

		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.Forward1"),iconLocation+"forward.gif",Commands.FORWARDPAGE);

		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.Forward10"),iconLocation+"fforward.gif",Commands.FFORWARDPAGE);

		addButton(NAVBAR,Messages.getMessage("PdfViewerNavBar.ForwardLast"),iconLocation+"end.gif",Commands.LASTPAGE);


		navToolBar.add(Box.createHorizontalGlue());


		Dimension size;

		//<start-pdfhelp>
		size = new Dimension(110, 0);
		javax.swing.Box.Filler filler = new Box.Filler(size, size, size);
		navButtons.add(filler, BorderLayout.EAST);
		/**/

		if (useNewLayout)
			multibox.setPreferredSize(size);
		else
			memoryBar.setPreferredSize(size);
		//<end-pdfhelp>

		boolean[] defaultValues = new boolean[v.size()];
		for(int i=0; i!=v.size(); i++){
			if(v.get(i).equals(Boolean.TRUE)){
				defaultValues[i] = true;
			}else{
				defaultValues[i] = false;
			}
		}

		//		p.setNavDefaults(defaultValues);



		//on top in plugin
		if(commonValues.getModeOfOperation()==Values.RUNNING_PLUGIN)
			topButtons.add(navToolBar,BorderLayout.CENTER);
		else
			navButtons.add(navToolBar,BorderLayout.CENTER);

	}

	public void setPage(int page){

		if (((decode_pdf.getDisplayView() == Display.FACING && currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER)) ||
				decode_pdf.getDisplayView() == Display.CONTINUOUS_FACING) &&
				(page & 1) == 1 && page != 1) {
			page--;
		} else if (decode_pdf.getDisplayView() == Display.FACING  && !currentCommands.getPages().getBoolean(Display.BoolValue.SEPARATE_COVER) &&
				(page & 1) == 0) {
			page--;
		}

		commonValues.setCurrentPage(page);
		setPageNumber();
		//Page changed so save this page as last viewed
		setThumbnails();
	}

	public void resetPageNav() {
		pageCounter2.setText("");
		pageCounter3.setText("");
	}

	public void setRotation(){
		//PdfPageData currentPageData=decode_pdf.getPdfPageData();
		//rotation=currentPageData.getRotation(commonValues.getCurrentPage());

		//Broke files with when moving from rotated page to non rotated.
		//The pages help previous rotation
		//rotation = (rotation + (getSelectedComboIndex(Commands.ROTATION)*90));

		if(rotation > 360)
			rotation = rotation - 360;

		if(getSelectedComboIndex(Commands.ROTATION)!=(rotation/90)){
			setSelectedComboIndex(Commands.ROTATION, (rotation/90));
		}else if(!Values.isProcessing()){
			decode_pdf.repaint();
		}
	}

	public void setRotationFromExternal(int rot){
		rotation = rot;
		rotationBox.setSelectedIndex(rotation/90);
		if(!Values.isProcessing()){
			decode_pdf.repaint();
		}
	}

	public void setScalingFromExternal(String scale){

		if(scale.startsWith("Fit ")){ //allow for Fit Page, Fit Width, Fit Height
			scalingBox.setSelectedItem(scale);
		}else{
			scaling = Float.parseFloat(scale);
			scalingBox.setSelectedItem(scale + '%');
		}

		if(!Values.isProcessing()){
			decode_pdf.repaint();
		}
	}

	public void createMainMenu(boolean includeAll){

		disposeButton();
		disposeMenu();
		removeListeners( currentMenu);
		currentMenu.removeAll();
		
		first=new SwingButton();
		fback=new SwingButton();
		back=new SwingButton();
		forward=new SwingButton();
		fforward=new SwingButton();
		end=new SwingButton();
		
		singleButton=new SwingButton();
		continuousButton=new SwingButton();
		continuousFacingButton=new SwingButton();
		facingButton=new SwingButton();
		pageFlowButton=new SwingButton();
		snapshotButton=new SwingButton();
		
		openButton=new SwingButton();
		printButton=new SwingButton();
		saveButton=new SwingButton();
		timbroButton=new SwingButton();
		signButton=new SwingButton();
		markButton=new SwingButton();
		signMarkButton=new SwingButton();
		searchButton=new SwingButton();
		docPropButton=new SwingButton();
		infoButton=new SwingButton();
		inviaLogButton=new SwingButton();
		mouseMode=new SwingButton();

		previousSearch=new SwingButton();
		nextSearch=new SwingButton();
		
		String addSeparator = "";
		//<start-wrap>

		fileMenu = new JMenu(Messages.getMessage("PdfViewerFileMenu.text"));

		addToMainMenu(fileMenu);

		/**
		 * add open options
		 **/

		openMenu = new JMenu(Messages.getMessage("PdfViewerFileMenuOpen.text"));
		fileMenu.add(openMenu);

		addMenuItem(openMenu,Messages.getMessage("PdfViewerFileMenuOpen.text"),Messages.getMessage("PdfViewerFileMenuTooltip.open"),Commands.OPENFILE);

		addMenuItem(openMenu,Messages.getMessage("PdfViewerFileMenuOpenurl.text"),Messages.getMessage("PdfViewerFileMenuTooltip.openurl"),Commands.OPENURL);


		if( commonValues.getSelectedFile()!=null ) {
			if( commonValues.getSaveEnabled()!=null){
				addSeparator = commonValues.getSaveEnabled().toString() 
						+ properties.getValue("Resaveasforms")
						+ properties.getValue("Find");
			} else {
				addSeparator = properties.getValue("Save")
						+ properties.getValue("Resaveasforms")
						+ properties.getValue("Find");
			}
			if(addSeparator.length()>0 && addSeparator.toLowerCase().contains("true")){
				fileMenu.addSeparator();
			}
			addMenuItem(fileMenu,Messages.getMessage( "PdfViewerFileMenuSave.text" ),
					Messages.getMessage("PdfViewerFileMenuTooltip.save"),Commands.SAVE);

			//not set if I just run from jar as no IText....
			if(includeAll && commonValues.isItextOnClasspath())
				addMenuItem(fileMenu,
						Messages.getMessage("PdfViewerFileMenuResaveForms.text"),
						Messages.getMessage("PdfViewerFileMenuTooltip.saveForms"),
						Commands.SAVEFORM);

			// Remember to finish this off
			addMenuItem(fileMenu, Messages.getMessage("PdfViewerFileMenuFind.text"), Messages.getMessage("PdfViewerFileMenuTooltip.find"), Commands.FIND);

			addSeparator = properties.getValue("Documentproperties");
			if(addSeparator.length()>0 && addSeparator.toLowerCase().equals("true")){
				fileMenu.addSeparator();
			}
			addMenuItem(fileMenu,Messages.getMessage("PdfViewerFileMenuDocProperties.text"),
					Messages.getMessage("PdfViewerFileMenuTooltip.props"),Commands.DOCINFO);


			if( commonValues.getPrintEnabled()!=null){
				addSeparator = commonValues.getPrintEnabled().toString();
			} else {
				addSeparator = properties.getValue( PropertyConstants.PRINT );
			}

			if(addSeparator.length()>0 && addSeparator.toLowerCase().equals("true")){
				fileMenu.addSeparator();
			}
			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_PRINT_TITLE ),
					Messages.getMessage( MessageConstants.MENU_PRINT_TOOLTIP ),Commands.PRINT);

			if( commonValues.getTimbroEnabled()!=null && commonValues.getSelectedFile()!=null){
				addSeparator = Boolean.toString(commonValues.getTimbroEnabled() );
			} else {
				addSeparator = properties.getValue( PropertyConstants.TIMBRO ) ;
			}

			if(addSeparator.length()>0 && addSeparator.toLowerCase().contains("true")){
				fileMenu.addSeparator();
			}

			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_TIMBRO_TITLE ),
					Messages.getMessage(MessageConstants.MENU_TIMBRO_TOOLTIP), Commands.TIMBRA);
 
			if( (commonValues.getSignEnabled()!=null || commonValues.getMarkEnabled()!=null) && commonValues.getSelectedFile()!=null){
				addSeparator = Boolean.toString(commonValues.getMarkEnabled() || commonValues.getSignEnabled());
			} else {
				addSeparator = properties.getValue( PropertyConstants.SIGN ) + properties.getValue( PropertyConstants.MARK ) ;
			}

			if(addSeparator.length()>0 && addSeparator.toLowerCase().contains("true")){
				fileMenu.addSeparator();
			}

			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_FIRMA_TITLE ),
					Messages.getMessage( MessageConstants.MENU_FIRMA_TOOLTIP ),Commands.SIGN);

			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_MARCA_TITLE ),
					Messages.getMessage( MessageConstants.MENU_MARCA_TOOLTIP ),Commands.MARK);

			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_FIRMAMARCA_TITLE ),
					Messages.getMessage( MessageConstants.MENU_FIRMAMARCA_TOOLTIP ),Commands.SIGNMARK);
			
		}

		addSeparator = properties.getValue("Recentdocuments");
		if(addSeparator.length()>0 && addSeparator.toLowerCase().equals("true")){
			currentCommands.recentDocumentsOption(fileMenu);
		}

		addSeparator = properties.getValue("Exit");
		if(addSeparator.length()>0 && addSeparator.toLowerCase().equals("true")){
			fileMenu.addSeparator();
		}
		if( commonValues.getSelectedFile()!=null ) {
			addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_BACKTO_ORIGINALFILE_TITLE ),
				Messages.getMessage ( MessageConstants.MENU_BACKTO_ORIGINALFILE_TITLE ), Commands.BACKORIGINALFILE);
		}
		addMenuItem(fileMenu,Messages.getMessage( MessageConstants.MENU_INVIOLOG_TITLE ),
				Messages.getMessage( MessageConstants.MENU_INVIOLOG_TOOLTIP ),Commands.INVIALOG);
		
		addMenuItem(fileMenu,Messages.getMessage("PdfViewerFileMenuExit.text"),
				Messages.getMessage("PdfViewerFileMenuTooltip.exit"),Commands.EXIT);


		//EDIT MENU
		editMenu = new JMenu(Messages.getMessage("PdfViewerEditMenu.text"));
		addToMainMenu(editMenu);

		//if( commonValues.getSelectedFile()!=null ) {
			addMenuItem(editMenu,Messages.getMessage("PdfViewerEditMenuCopy.text"),
					Messages.getMessage("PdfViewerEditMenuTooltip.Copy"),Commands.COPY);
	
			addMenuItem(editMenu,Messages.getMessage("PdfViewerEditMenuSelectall.text"),
					Messages.getMessage("PdfViewerEditMenuTooltip.Selectall"),Commands.SELECTALL);
	
			addMenuItem(editMenu,Messages.getMessage("PdfViewerEditMenuDeselectall.text"),
					Messages.getMessage("PdfViewerEditMenuTooltip.Deselectall"),Commands.DESELECTALL);
		//}
		
		addSeparator = properties.getValue("Preferences");
		if(addSeparator.length()>0 && addSeparator.toLowerCase().equals("true")){
			editMenu.addSeparator();
		}
		addMenuItem(editMenu, Messages.getMessage("PdfViewerEditMenuPreferences.text"),
				Messages.getMessage("PdfViewerEditMenuTooltip.Preferences"), Commands.PREFERENCES);


		viewMenu = new JMenu(Messages.getMessage("PdfViewerViewMenu.text"));
		addToMainMenu(viewMenu);

		goToMenu = new JMenu(Messages.getMessage("GoToViewMenuGoto.text"));
		viewMenu.add(goToMenu);

		//if( commonValues.getSelectedFile()!=null ) {
			addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.FirstPage"),"",Commands.FIRSTPAGE);
	
			addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.BackPage"),"",Commands.BACKPAGE);
	
			addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.ForwardPage"),"",Commands.FORWARDPAGE);
	
			addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.LastPage"),"",Commands.LASTPAGE);
	
			addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.GoTo"),"",Commands.GOTO);
		//}

		addSeparator = properties.getValue("Previousdocument")
				+properties.getValue("Nextdocument");
		if(addSeparator.length()>0 && addSeparator.toLowerCase().contains("true")){
			goToMenu.addSeparator();
		}


		addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.PreviousDoucment"),"",Commands.PREVIOUSDOCUMENT);

		addMenuItem(goToMenu,Messages.getMessage("GoToViewMenuGoto.NextDoucment"),"",Commands.NEXTDOCUMENT);


		/**
		 * add page layout
		 **/
		//if(properties.getValue("PageLayoutMenu").toLowerCase().equals("true")){




		//<end-wrap>

		String[] descriptions={Messages.getMessage("PageLayoutViewMenu.SinglePage"),Messages.getMessage("PageLayoutViewMenu.Continuous"),Messages.getMessage("PageLayoutViewMenu.Facing"),Messages.getMessage("PageLayoutViewMenu.ContinousFacing"),Messages.getMessage("PageLayoutViewMenu.PageFlow")};
		int[] value={Display.SINGLE_PAGE, Display.CONTINUOUS,Display.FACING,Display.CONTINUOUS_FACING, Display.PAGEFLOW};

		if(isSingle)
			initLayoutMenus(pageLayoutMenu, descriptions, value);

		//<start-wrap>
		if(properties.getValue("separateCover").equals("true"))
			addMenuItem(viewMenu,Messages.getMessage("PdfViewerViewMenuSeparateCover.text"),Messages.getMessage("PdfViewerViewMenuTooltip.separateCover"),Commands.SEPARATECOVER, true);

		// addMenuItem(view,Messages.getMessage("PdfViewerViewMenuAutoscroll.text"),Messages.getMessage("PdfViewerViewMenuTooltip.autoscroll"),Commands.AUTOSCROLL);
		if(properties.getValue("panMode").equals("true"))
			addMenuItem(viewMenu,Messages.getMessage("PdfViewerViewMenuPanMode.text"),Messages.getMessage("PdfViewerViewMenuTooltip.panMode"),Commands.PANMODE);


		if(properties.getValue("textSelect").equals("true"))
			addMenuItem(viewMenu,Messages.getMessage("PdfViewerViewMenuTextSelectMode.text"),Messages.getMessage("PdfViewerViewMenuTooltip.textSelect"),Commands.TEXTSELECT);


		addSeparator = properties.getValue("Fullscreen");
		if(addSeparator.length()>0 && addSeparator.toLowerCase().contains("true")){
			goToMenu.addSeparator();
		}

		//full page mode
		addMenuItem(viewMenu,Messages.getMessage("PdfViewerViewMenuFullScreenMode.text"),Messages.getMessage("PdfViewerViewMenuTooltip.fullScreenMode"),Commands.FULLSCREEN);

		if (!isSingle) {
			windowMenu = new JMenu(Messages.getMessage("PdfViewerWindowMenu.text"));
			addToMainMenu(windowMenu);

			addMenuItem(windowMenu, Messages.getMessage("PdfViewerWindowMenuCascade.text"), "",	Commands.CASCADE);

			addMenuItem(windowMenu, Messages.getMessage("PdfViewerWindowMenuTile.text"), "", Commands.TILE);

		}


		/**
		 * items options if IText available
		 */
		if(commonValues.isItextOnClasspath()){
			pageToolsMenu = new JMenu(Messages.getMessage("PdfViewerPageToolsMenu.text"));
			addToMainMenu(pageToolsMenu);

			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuRotate.text"),"",Commands.ROTATE);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuDelete.text"),"",Commands.DELETE);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuAddPage.text"),"",Commands.ADD);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuAddHeaderFooter.text"),"",Commands.ADDHEADERFOOTER);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuStampText.text"),"",Commands.STAMPTEXT);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuStampImage.text"),"",Commands.STAMPIMAGE);
			addMenuItem(pageToolsMenu,Messages.getMessage("PdfViewerPageToolsMenuSetCrop.text"),"",Commands.SETCROP);

		}


		helpMenu = new JMenu(Messages.getMessage("PdfViewerHelpMenu.text"));
		addToMainMenu(helpMenu);
		
		addMenuItem(helpMenu,Messages.getMessage("PdfViewerHelpMenu.VisitWebsite"),"",Commands.VISITWEBSITE);
		addMenuItem(helpMenu,Messages.getMessage("PdfViewerHelpMenuTip.text"),"",Commands.TIP);
		addMenuItem(helpMenu,Messages.getMessage("PdfViewerHelpMenuUpdates.text"),"",Commands.UPDATE);
		addMenuItem(helpMenu,Messages.getMessage("PdfViewerHelpMenuabout.text"),Messages.getMessage("PdfViewerHelpMenuTooltip.about"),Commands.INFO);
		
		preferenceMenu = new JMenu( Messages.getMessage( MessageConstants.MENU_PREFERENCE_TITLE ) );
		addToMainMenu(preferenceMenu);
		
		addMenuItem( preferenceMenu, Messages.getMessage( MessageConstants.MENU_PREFERENCE_FIRMAMARCA_TITLE ),Messages.getMessage( MessageConstants.MENU_PREFERENCE_FIRMAMARCA_TOOLTIP ), Commands.PREFERENCE_FIRMAMARCA);
		addMenuItem( preferenceMenu, Messages.getMessage( MessageConstants.MENU_PREFERENCE_TIMBRO_TITLE ),Messages.getMessage( MessageConstants.MENU_PREFERENCE_TIMBRO_TOOLTIP ), Commands.PREFERENCE_TIMBRO );
		addMenuItem( preferenceMenu, Messages.getMessage( MessageConstants.MENU_PREFERENCE_RETE_TITLE ),Messages.getMessage(  MessageConstants.MENU_PREFERENCE_RETE_TOOLTIP ), Commands.PREFERENCE_RETE );
		addMenuItem( preferenceMenu, Messages.getMessage( MessageConstants.MENU_PREFERENCE_INVIOLOG_TITLE ),Messages.getMessage( MessageConstants.MENU_PREFERENCE_INVIOLOG_TOOLTIP ), Commands.PREFERENCE_INVIOLOG );
				
//		final Commands commands = currentCommands;
//		JMenu lExitMenu = new JMenu(Messages.getMessage("ExitMenu.text"));
//		final JMenuItem lMenuItemExit = new JMenuItem(Messages.getMessage("ExitMenu.text"));
//		lMenuItemExit.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				SwingWorker lSwingWorker = new SwingWorker() {
//					
//					@Override
//					public Object construct() {
//						commands.executeCommand(Commands.EXIT_MANAGED, null); 
//						logger.info("Uploadok vale " + commands.uploadOk);
//						if (commands.uploadOk){
//							logger.info("Chiudo la console");
//							commands.executeCommand(Commands.DRAW_OK, null); 
//							commands.uploadGestito = true;
//							JMenuItem lJMenuItem = (JMenuItem)e.getSource();
//							JPopupMenu fromParent = (JPopupMenu)lJMenuItem.getParent();  
//							JMenu lJMenu = (JMenu)fromParent.getInvoker(); 
//							Container lContainer = lJMenu.getParent();
//							while (!(lContainer instanceof JApplet)){
//								lContainer = lContainer.getParent();
//							}
//							JApplet lApplet = (JApplet)lContainer;
//							String callback = lApplet.getParameter("callbackToCall");
//							String lStrToInvoke = "javascript:" + callback + "('prova');";
//							System.out.println("Invoco " +lStrToInvoke);
//							try {
//								lApplet.getAppletContext().showDocument(new URL(lStrToInvoke));
//							} catch (MalformedURLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							lApplet.destroy();
//							System.exit(1);
//						
//					} else {
//						commands.executeCommand(Commands.DRAW_ERROR, null); 
//						int response = JOptionPane.showConfirmDialog(commands.lWaitDialog.getDialog(), "Upload non completato correttamente. Vuoi chiudere comunque?", "Attenzione", JOptionPane.YES_NO_OPTION);
//						if (response == 0) {
//							commands.uploadGestito = true;
//							JMenuItem lJMenuItem = (JMenuItem)e.getSource();
//							JPopupMenu fromParent = (JPopupMenu)lJMenuItem.getParent();  
//							JMenu lJMenu = (JMenu)fromParent.getInvoker(); 
//							Container lContainer = lJMenu.getParent();
//							while (!(lContainer instanceof JApplet)){
//								lContainer = lContainer.getParent();
//							}
//							JApplet lApplet = (JApplet)lContainer;
//							String callback = lApplet.getParameter("callbackToCall");
//							String lStrToInvoke = "javascript:" + callback + "('prova');";
//							System.out.println("Invoco " +lStrToInvoke);
//							try {
//								lApplet.getAppletContext().showDocument(new URL(lStrToInvoke));
//							} catch (MalformedURLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							lApplet.destroy();
//							System.exit(1);
//						}
//						
//						
//					}
//						return null;
//					}
//				};
//				lSwingWorker.start();
//				commands.executeCommand(Commands.DRAW_MANAGED, null); 				
//			}
//		});
//		lExitMenu.add(lMenuItemExit);
//		currentMenu.add(lExitMenu);
		//currentMenu.add(new ProxyMenu());
		//<end-wrap>

	}

	public void createOtherMenu(){
		addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.openFile"),iconLocation+"open.gif",Commands.OPENFILE);
		//<end-wrap>


		if(searchFrame!=null && searchFrame.getStyle()==SwingSearchWindow.SEARCH_EXTERNAL_WINDOW)
			addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.search"),iconLocation+"find.gif",Commands.FIND);



		//<start-wrap>
		//addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.properties"),iconLocation+"properties.gif",Commands.DOCINFO);
		//<end-wrap>

		if (!useNewLayout || commonValues.getModeOfOperation()==Values.RUNNING_PLUGIN)
			addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.about"),iconLocation+"about.gif",Commands.INFO);

		//<start-wrap>
		/**snapshot screen function*/
		//addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.snapshot"),iconLocation+"snapshot.gif",Commands.SNAPSHOT);
		//<end-wrap>

		if (useNewLayout) {
			JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
			sep.setPreferredSize(new Dimension(5,32));
			topButtons.add(sep);
		}

		/**
		 * combo boxes on toolbar
		 * */
		addCombo(Messages.getMessage("PdfViewerToolbarScaling.text"), Messages.getMessage("PdfViewerToolbarTooltip.zoomin"), Commands.SCALING);


		addCombo(Messages.getMessage("PdfViewerToolbarRotation.text"), Messages.getMessage("PdfViewerToolbarTooltip.rotation"), Commands.ROTATION);


		//<start-wrap>
		//<end-wrap>

		addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.mouseMode"),iconLocation+"mouse_select.png",Commands.MOUSEMODE);


		if (useNewLayout) {

			JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
			sep.setPreferredSize(new Dimension(5,32));
			topButtons.add(sep);
			//<start-wrap>
			/**
            //<end-wrap>
            addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.about"),iconLocation+"about.gif",Commands.INFO);
             /**/
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#addToMainMenu(javax.swing.JMenu)
	 */
	public void addToMainMenu(JMenu fileMenuList) {
		currentMenu.add(fileMenuList);
	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getFrame()
	 */
	public Container getFrame() {
		return frame;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getTopButtonBar()
	 */
	public JToolBar getTopButtonBar() {
		return topButtons;
	}

	public JToolBar getDisplaySettingsBar() {
		return comboBoxBar;
	}

	public JMenuBar getMenuBar() {
		return currentMenu;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showMessageDialog(java.lang.Object)
	 */
	public void showMessageDialog(Object message1){

		/**
		 * allow user to replace messages with our action
		 */
		boolean showMessage=true;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			showMessage= customMessageHandler.showMessage(message1);

		if(showMessage)
			JOptionPane.showMessageDialog(frame,message1);
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showMessageDialog(java.lang.Object, java.lang.String, int)
	 */
	public void showMessageDialog(Object message,String title,int type){

		/**
		 * allow user to replace messages with our action
		 */
		boolean showMessage=true;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			showMessage= customMessageHandler.showMessage(message);

		if(showMessage)
			JOptionPane.showMessageDialog(frame,message,title,type);
	}


	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showInputDialog(java.lang.Object, java.lang.String, int)
	 */
	public String showInputDialog(Object message, String title, int type) {

		/**
		 * allow user to replace messages with our action
		 */
		String returnMessage=null;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestInput(new Object[]{message,title, title});

		if(returnMessage==null)
			return JOptionPane.showInputDialog(frame, message, title, type);
		else
			return returnMessage;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showInputDialog(java.lang.String)
	 */
	public String showInputDialog(String message) {

		/**
		 * allow user to replace messages with our action
		 */
		String returnMessage=null;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestInput(new String[]{message});

		if(returnMessage==null)
			return 	JOptionPane.showInputDialog(frame,message);
		else
			return returnMessage;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showOptionDialog(java.lang.Object, java.lang.String, int, int, java.lang.Object, java.lang.Object[], java.lang.Object)
	 */
	public int showOptionDialog(Object displayValue, String message, int option, int type, Object icon, Object[] options, Object initial) {

		/**
		 * allow user to replace messages with our action
		 */
		int returnMessage=-1;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestConfirm(new Object[]{displayValue, message, String.valueOf(option), String.valueOf(type), icon, options, initial});

		if(returnMessage==-1)
			return JOptionPane.showOptionDialog(frame, displayValue,message,option,type, (Icon)icon, options,initial);
		else
			return returnMessage;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showConfirmDialog(java.lang.String, java.lang.String, int)
	 */
	public int showConfirmDialog(String message, String message2, int option) {

		/**
		 * allow user to replace messages with our action
		 */
		int returnMessage=-1;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestConfirm(new Object[]{message, message2, String.valueOf(option)});

		if(returnMessage==-1)
			return JOptionPane.showConfirmDialog(frame, message,message2,option);
		else
			return returnMessage;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showOverwriteDialog(String file,boolean yesToAllPresent)
	 */
	public int showOverwriteDialog(String file,boolean yesToAllPresent) {

		int n = -1;

		/**
		 * allow user to replace messages with our action and remove popup
		 */
		int returnMessage=-1;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestConfirm(new Object[]{file, String.valueOf(yesToAllPresent)});

		if(returnMessage!=-1)
			return returnMessage;

		if(yesToAllPresent){

			final Object[] buttonRowObjects = new Object[] {
					Messages.getMessage("PdfViewerConfirmButton.Yes"),
					Messages.getMessage("PdfViewerConfirmButton.YesToAll"),
					Messages.getMessage("PdfViewerConfirmButton.No"),
					Messages.getMessage("PdfViewerConfirmButton.Cancel")
			};

			n = JOptionPane.showOptionDialog(frame,
					file+ '\n' +Messages.getMessage("PdfViewerMessage.FileAlreadyExists")
					+ '\n' +Messages.getMessage("PdfViewerMessage.ConfirmResave"),
					Messages.getMessage("PdfViewerMessage.Overwrite"),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					buttonRowObjects,
					buttonRowObjects[0]);

		}else{
			n = JOptionPane.showOptionDialog(frame,
					file+ '\n' +Messages.getMessage("PdfViewerMessage.FileAlreadyExists")
					+ '\n' +Messages.getMessage("PdfViewerMessage.ConfirmResave"),
					Messages.getMessage("PdfViewerMessage.Overwrite"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,null,null);
		}

		return n;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showMessageDialog(javax.swing.JTextArea)
	 */
	public void showMessageDialog(JTextArea info) {

		/**
		 * allow user to replace messages with our action
		 */
		boolean showMessage=true;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			showMessage= customMessageHandler.showMessage(info);

		if(showMessage)
			JOptionPane.showMessageDialog(frame, info);

	}

	public void showItextPopup() {

		JEditorPane p = new JEditorPane(
				"text/html",
				"Itext is not on the classpath.<BR>"
						+ "JPedal includes code to take advantage of itext and<BR>"
						+ "provide additional functionality with options<BR>"
						+ "to spilt pdf files, and resave forms data<BR>"
						+ "\nItext website - <a href=http://itextpdf.com/>http://itextpdf.com</a>");
		p.setEditable(false);
		p.setOpaque(false);
		p.addHyperlinkListener( new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
					try {
						BrowserLauncher.openURL("http://itextpdf.com/");
					} catch (IOException e1) {
						showMessageDialog(Messages.getMessage("PdfViewer.ErrorWebsite"));
					}
				}
			}
		});


		/**
		 * allow user to replace messages with our action
		 */
		boolean showMessage=true;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			showMessage= customMessageHandler.showMessage(p);

		if(showMessage)
			showMessageDialog(p);

		// Hack for 13 to make sure the message box is large enough to hold the message
		/**
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(p);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);

        JDialog dialog = optionPane.createDialog(frame, "iText");
        dialog.pack();
        dialog.setSize(400,200);
        dialog.setVisible(true);
        /**/

	}

	public void showFirstTimePopup(){

		//allow user to disable
		boolean showMessage=(customMessageHandler!=null && customMessageHandler.showMessage("first time popup")) ||
				customMessageHandler==null;

		if(!showMessage || commonValues.getModeOfOperation()==Values.RUNNING_APPLET)
			return;

		try{
			final JPanel a = new JPanel();
			a.setLayout(new BoxLayout(a, BoxLayout.Y_AXIS));


			JLabel m1 = new JLabel(Messages.getMessage("PdfViewerGPL.message1"));
			JLabel m2 = new JLabel(Messages.getMessage("PdfViewerGPL.message2"));
			JLabel m3 = new JLabel(Messages.getMessage("PdfViewerGPL.message3"));

			m1.setFont(m1.getFont().deriveFont(Font.BOLD));

			m1.setAlignmentX(0.5f);
			m2.setAlignmentX(0.5f);
			m3.setAlignmentX(0.5f);

			a.add(m1);
			a.add(Box.createRigidArea(new Dimension(14,14)));
			a.add(m2);
			a.add(m3);
			a.add(Box.createRigidArea(new Dimension(10,10)));
			/**///to help comment out code not needed in gpl demos.

			MouseAdapter supportListener = new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if(SingleDisplay.allowChangeCursor)
						a.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				public void mouseExited(MouseEvent e) {
					if(SingleDisplay.allowChangeCursor)
						a.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				public void mouseClicked(MouseEvent e) {
					try {
						BrowserLauncher.openURL(Messages.getMessage("PdfViewer.SupportLink.Link"));
					} catch (IOException e1) {
						showMessageDialog(Messages.getMessage("PdfViewer.ErrorWebsite"));
					}
				}
			};

			JLabel img=new JLabel(new ImageIcon(getClass().getResource("/it/eng/hybrid/module/jpedal/resources/supportScreenshot.png")));
			img.setBorder(BorderFactory.createRaisedBevelBorder());
			img.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			img.addMouseListener(supportListener);
			a.add(img);

			JLabel supportLink=new JLabel("<html><center><u>"+Messages.getMessage("PdfViewer.SupportLink.Text1")+ ' ' +Messages.getMessage("PdfViewer.SupportLink.Text2")+"</u></html>");
			supportLink.setMaximumSize(new Dimension(245,60));
			supportLink.setForeground(Color.BLUE);
			supportLink.addMouseListener(supportListener);
			supportLink.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			a.add(supportLink);
			a.add(Box.createRigidArea(new Dimension(10,10)));

			JOptionPane.showMessageDialog(
					frame,
					a,
					Messages.getMessage("PdfViewerTitle.RunningFirstTime"),
					JOptionPane.PLAIN_MESSAGE);
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "caught an exception "+e);
			System.err.println(Messages.getMessage("PdfViewerFirstRunDialog.Error"));
		}catch(Error e){
			//JOptionPane.showMessageDialog(null, "caught an error "+e);
			System.err.println(Messages.getMessage("PdfViewerFirstRunDialog.Error"));
		}
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#showConfirmDialog(java.lang.Object, java.lang.String, int, int)
	 */
	public int showConfirmDialog(Object message, String title, int optionType, int messageType) {

		/**
		 * allow user to replace messages with our action
		 */
		int returnMessage=-1;

		//check user has not setup message and if we still show message
		if(customMessageHandler !=null)
			returnMessage= customMessageHandler.requestConfirm(new Object[]{message, title, String.valueOf(optionType), String.valueOf(messageType)});

		if(returnMessage==-1)
			return JOptionPane.showConfirmDialog(frame, message, title, optionType, messageType);
		else
			return returnMessage;
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#updateStatusMessage(java.lang.String)
	 */
	public void setDownloadProgress(String message,int percentage) {
		downloadBar.setProgress(message, percentage);

		if (useNewLayout)
			setMultibox(new int[]{});
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#updateStatusMessage(java.lang.String)
	 */
	public void updateStatusMessage(String message) {
		statusBar.updateStatus(message,0);


	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#resetStatusMessage(java.lang.String)
	 */
	public void resetStatusMessage(String message) {
		statusBar.resetStatus(message);

	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setStatusProgress(int)
	 */
	public void setStatusProgress(int size) {
		statusBar.setProgress(size);

		if (useNewLayout)
			setMultibox(new int[]{});
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#isPDFOutlineVisible()
	 */
	public boolean isPDFOutlineVisible() {
		return navOptionsPanel.isVisible();
	}

	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setPDFOutlineVisible(boolean)
	 */
	public void setPDFOutlineVisible(boolean visible) {
		navOptionsPanel.setVisible(visible);
	}

	public void setSplitDividerLocation(int size) {
		displayPane.setDividerLocation(size);
	}


	public void setQualityBoxVisible(boolean visible){
	}

	private void setThumbnails() {
		SwingWorker worker = new SwingWorker() {
			public Object construct() {

				if(thumbnails.isShownOnscreen()) {
					setupThumbnailPanel();

					if(decode_pdf.getDisplayView()==Display.SINGLE_PAGE)
						thumbnails.generateOtherVisibleThumbnails(commonValues.getCurrentPage());
				}

				return null;
			}
		};
		worker.start();
	}

	public void setSearchText(JTextField searchText) {
		this.searchText = searchText;
	}

	public void setResults(SearchList results) {
		this.results = results;
	}

	public SearchList getResults() {
		return results;
	}

	/**
	 * Method incorrectly named. New named method is getNavigationBar()
	 *
	 * @return JToolBar containing all navigation buttons
	 *
	public JToolBar getComboBar() {
		return navButtons;
	}/**/

	public JToolBar getNavigationBar() {
		return navButtons;
	}

	public JTabbedPane getSideTabBar() {
		return navOptionsPanel;
	}

	public ButtonGroup getSearchLayoutGroup() {
		return searchLayoutGroup;
	}

	public void setSearchFrame(GUISearchWindow searchFrame) {
		this.searchFrame = searchFrame;
	}

	//	<link><a name="exampledraw" />
	/**
	 * example of a custom draw object
	 *
	private static class ExampleCustomDrawObject implements JPedalCustomDrawObject {

		private boolean isVisible=true;

		private int page = 0;

		public int medX = 0;
		public int medY = 0;


		public ExampleCustomDrawObject(){

		}

		public ExampleCustomDrawObject(Integer option){

			if(option.equals(JPedalCustomDrawObject.ALLPAGES))
				page=-1;
			else throw new RuntimeException("Only valid setting is JPedalCustomDrawObject.ALLPAGES");
		}

		public int getPage(){
			return page;
		}


		public void print(Graphics2D g2, int x) {

			//custom code or just pass through
			if(page==x || page ==-1 || page==0)
				paint(g2);
		}

		public void paint(Graphics2D g2) {
			if(isVisible){

				//your code here

				//if you alter something, put it back
				Paint paint=g2.getPaint();

				//loud shape we can see
				g2.setPaint(Color.orange);
				g2.fillRect(100+medX,100+medY,100,100); // PDF co-ordinates due to transform

				g2.setPaint(Color.RED);
				g2.drawRect(100+medX,100+medY,100,100); // PDF co-ordinates due to transform

				//put back values
				g2.setPaint(paint);
			}
		}

        /**example onto rotated page
        public void paint(Graphics2D g2) {
                if(isVisible){

                    //your code here

                    AffineTransform aff=g2.getTransform();


                    //allow for 90 degrees - detect of G2
                    double[] matrix=new double[6];
                    aff.getMatrix(matrix);

                    //System.out.println("0="+matrix[0]+" 1="+matrix[1]+" 2="+matrix[2]+" 3="+matrix[3]+" 4="+matrix[4]+" 5="+matrix[5]);
                    if(matrix[1]>0 && matrix[2]>0){ //90

                        g2.transform(AffineTransform.getScaleInstance(-1, 1));
                        g2.transform(AffineTransform.getRotateInstance(90 *Math.PI/180));

                        //BOTH X and Y POSITIVE!!!!
                    g2.drawString("hello world", 60,60);
                    }else if(matrix[0]<0 && matrix[3]>0){ //180 degrees  (origin now top right)
                        g2.transform(AffineTransform.getScaleInstance(-1, 1));

                        g2.drawString("hello world", -560,60);//subtract cropW from first number to use standard values

                    }else if(matrix[1]<0 && matrix[2]<0){ //270

                        g2.transform(AffineTransform.getScaleInstance(-1, 1));
                        g2.transform(AffineTransform.getRotateInstance(-90 *Math.PI/180));

                        //BOTH X and Y NEGATIVE!!!!
                        g2.drawString("hello world", -560,-60); //subtract CropW and CropH if you want standard values
                    }else{ //0 degress
                        g2.transform(AffineTransform.getScaleInstance(1, -1));
                        // X ONLY POSITIVE!!!!
                        g2.drawString("hello world", 60,-60);
                    }

                    //restore!!!
                    g2.setTransform(aff);
                }
            }


		public void setVisible(boolean isVisible) {
			this.isVisible=isVisible;
		}

		public void setMedX(int medX) {
			this.medX = medX;
		}

		public void setMedY(int medY) {
			this.medY = medY;
		}
	}/**/

	public void removeSearchWindow(boolean justHide) {
		searchFrame.removeSearchWindow(justHide);
	}

	public void showPreferencesDialog() {
		SwingProperties p = new SwingProperties();
		p.showPreferenceWindow(this);
	}
	
	public void showPreferencesFirmaMarcaPanel() {
		PreferenceFirmaMarcaPanel pannello = new PreferenceFirmaMarcaPanel();
		String pannelloWidth = properties.getValue( PropertyConstants.PREFERENCE_FIRMAMARCA_WIDTH );
		String pannelloHeight = properties.getValue( PropertyConstants.PREFERENCE_FIRMAMARCA_HEIGHT );
		if( pannelloHeight!=null && pannelloWidth!=null){
			try{
			int width = Integer.parseInt( pannelloWidth );
			int height = Integer.parseInt( pannelloHeight );
			pannello = new PreferenceFirmaMarcaPanel( width, height );
			} catch(Exception e){
				pannello = new PreferenceFirmaMarcaPanel();
			}
		} else {
			pannello = new PreferenceFirmaMarcaPanel();
		} 
		if( pannello!=null )
			pannello.show(this);
	}
	
	public void showPreferencesTimbroPanel() {
		PreferenceTimbroPanel pannello = new PreferenceTimbroPanel();
		String pannelloWidth = properties.getValue( PropertyConstants.PREFERENCE_TIMBRO_WIDTH );
		String pannelloHeight = properties.getValue( PropertyConstants.PREFERENCE_TIMBRO_HEIGHT );
		if( pannelloHeight!=null && pannelloWidth!=null){
			try{
			int width = Integer.parseInt( pannelloWidth );
			int height = Integer.parseInt( pannelloHeight );
			pannello = new PreferenceTimbroPanel( width, height );
			} catch(Exception e){
				pannello = new PreferenceTimbroPanel();
			}
		} else {
			pannello = new PreferenceTimbroPanel();
		} 
		if( pannello!=null )
		pannello.show(this);
	}
	
	public void showPreferencesRetePanel() {
		String testUrl;
		try {
			testUrl = commonValues.getPreferenceManager().getConfiguration().getString( ConfigConstants.TEST_URL );
			new ProxyFrame(testUrl).setVisible(true);
		} catch (Exception e) {
			showMessageDialog(e.getMessage());
		}
		
//		PreferenceRetePanel pannello = null;
//		String pannelloWidth = properties.getValue( PropertyConstants.PREFERENCE_INVIOLOG_WIDTH );
//		String pannelloHeight = properties.getValue( PropertyConstants.PREFERENCE_INVIOLOG_HEIGHT );
//		if( pannelloHeight!=null && pannelloWidth!=null){
//			int width = Integer.parseInt( pannelloWidth );
//			int height = Integer.parseInt( pannelloHeight );
//			//pannello = new PreferenceRetePanel( width, height );
//		} else {
//			pannello = new PreferenceRetePanel();
//		} 
//		if( pannello!=null )
//			pannello.show(this);
	}
	
	public void showPreferencesInvioLogPanel() {
		PreferenceInvioLogPanel pannello = null;
		String pannelloWidth = properties.getValue( PropertyConstants.PREFERENCE_INVIOLOG_WIDTH );
		String pannelloHeight = properties.getValue( PropertyConstants.PREFERENCE_INVIOLOG_HEIGHT );
		if( pannelloHeight!=null && pannelloWidth!=null){
			int width = Integer.parseInt( pannelloWidth );
			int height = Integer.parseInt( pannelloHeight );
			pannello = new PreferenceInvioLogPanel( width, height );
		} else {
			pannello = new PreferenceInvioLogPanel();
		} 
		if( pannello!=null )
			pannello.show(this);
	}
	
	public void showInvioLogMailPanel() {
		InvioLogMailPanel pannello = null;
		String pannelloWidth = properties.getValue( PropertyConstants.INVIOLOG_WIDTH );
		String pannelloHeight = properties.getValue( PropertyConstants.INVIOLOG_HEIGHT );
		if( pannelloHeight!=null && pannelloWidth!=null){
			int width = Integer.parseInt( pannelloWidth );
			int height = Integer.parseInt( pannelloHeight );
			pannello = new InvioLogMailPanel(width, height);
		} else {
			pannello = new InvioLogMailPanel();
		} 		
		pannello.show(this);
		
	}
	
	public void showTimbroPanel() {
		TimbroPanel pannello = new TimbroPanel();
		String pannelloWidth = properties.getValue( PropertyConstants.TIMBRO_WIDTH );
		String pannelloHeight = properties.getValue( PropertyConstants.TIMBRO_HEIGHT );
		if( pannelloHeight!=null && pannelloWidth!=null){
			try{
			int width = Integer.parseInt( pannelloWidth );
			int height = Integer.parseInt( pannelloHeight );
			pannello = new TimbroPanel( width, height );
			} catch(Exception e){
				pannello = new TimbroPanel();
			}
		} else {
			pannello = new TimbroPanel();
		} 
		if( pannello!=null )
			pannello.show(this);
	}

	public void setFrame(Container frame) {
		this.frame = frame;

	}

	public void getRSSBox() {
		final JPanel panel = new JPanel();

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel label = new JLabel("Click on the link below to load a web browser and sign up to our RSS feed.");
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		labelPanel.add(label);
		labelPanel.add(Box.createHorizontalGlue());

		top.add(labelPanel);

		JPanel linkPanel = new JPanel();
		linkPanel.setLayout(new BoxLayout(linkPanel, BoxLayout.X_AXIS));
		linkPanel.add(Box.createHorizontalGlue());
		linkPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final JLabel url=new JLabel("<html><center>"+"http://www.jpedal.org/jpedal.rss");
		url.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		url.setForeground(Color.blue);
		url.setHorizontalAlignment(JLabel.CENTER);

		//@kieran - cursor
		url.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					panel.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
				url.setText("<html><center><a>http://www.jpedal.org/jpedal.rss</a></center>");
			}

			public void mouseExited(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					panel.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				url.setText("<html><center>http://www.jpedal.org/jpedal.rss");
			}

			public void mouseClicked(MouseEvent e) {
				try {
					BrowserLauncher.openURL("http://www.jpedal.org/jpedal.rss");
				} catch (IOException e1) {

					JPanel errorPanel = new JPanel();
					errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));

					JLabel errorMessage = new JLabel("Your web browser could not be successfully loaded.  " +
							"Please copy and paste the URL below, manually into your web browser.");
					errorMessage.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					errorMessage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

					JTextArea textArea = new JTextArea("http://www.jpedal.org/jpedal.rss");
					textArea.setEditable(false);
					textArea.setRows(5);
					textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					textArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

					errorPanel.add(errorMessage);
					errorPanel.add(textArea);

					showMessageDialog(errorPanel,"Error loading web browser",JOptionPane.PLAIN_MESSAGE);

				}
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});

		linkPanel.add(url);
		linkPanel.add(Box.createHorizontalGlue());
		top.add(linkPanel);

		JLabel image = new JLabel(new ImageIcon(getClass().getResource("/it/eng/hybrid/module/jpedal/resources/rss.png")));
		image.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		JPanel imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
		imagePanel.add(Box.createHorizontalGlue());
		imagePanel.add(image);
		imagePanel.add(Box.createHorizontalGlue());

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(top);
		panel.add(imagePanel);

		showMessageDialog(panel,"Subscribe to JPedal RSS Feed",JOptionPane.PLAIN_MESSAGE);
	}

	private void loadProperties(){

		try{
			Component[] c = comboBoxBar.getComponents();

			//		default value used to load props
			boolean set = false;
			String propValue = "";

			//Disable entire section
			propValue = properties.getValue("sideTabBarCollapseLength");
			if(propValue.length()>0){
				int value  =  Integer.parseInt(propValue);
				startSize = value;
				reinitialiseTabs(false);
				//properties.setValue("sideTabBarCollapseLength", String.valueOf(value));
			}

			propValue = properties.getValue("ShowMenubar");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			currentMenu.setEnabled(set);
			currentMenu.setVisible(set);
			//properties.setValue("ShowMenubar", String.valueOf(set));

			propValue = properties.getValue("ShowButtons");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			topButtons.setEnabled(set);
			topButtons.setVisible(set);
			//properties.setValue("ShowButtons", String.valueOf(set));

			propValue = properties.getValue("ShowDisplayoptions");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			comboBoxBar.setEnabled(set);
			comboBoxBar.setVisible(set);
			//properties.setValue("ShowDisplayoptions", String.valueOf(set));

			propValue = properties.getValue("ShowNavigationbar");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			navButtons.setEnabled(set);
			navButtons.setVisible(set);
			//properties.setValue("ShowNavigationbar", String.valueOf(set));

			logger.info("displayPane::: " + displayPane);
			if(displayPane!=null){
				propValue = properties.getValue("ShowSidetabbar");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				logger.info("Set " + set);
				if(!set)
					displayPane.setDividerSize(0);
				else
					displayPane.setDividerSize(5);
				displayPane.getLeftComponent().setEnabled(set);
				displayPane.getLeftComponent().setVisible(set);
				
				//properties.setValue("ShowSidetabbar", String.valueOf(set));
			}

			/**
			 * Items on nav pane
			 */
			propValue = properties.getValue("Firstbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			first.setEnabled(set);
			first.setVisible(set);

			propValue = properties.getValue("Back10bottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			fback.setEnabled(set);
			fback.setVisible(set);

			propValue = properties.getValue("Backbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			back.setEnabled(set);
			back.setVisible(set);

			propValue = properties.getValue("Gotobottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			pageCounter1.setEnabled(set);
			pageCounter1.setVisible(set);

			pageCounter2.setEnabled(set);
			pageCounter2.setVisible(set);

			pageCounter3.setEnabled(set);
			pageCounter3.setVisible(set);

			propValue = properties.getValue("Forwardbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			forward.setEnabled(set);
			forward.setVisible(set);

			propValue = properties.getValue("Forward10bottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			fforward.setEnabled(set);
			fforward.setVisible(set);

			propValue = properties.getValue("Lastbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			end.setEnabled(set);
			end.setVisible(set);

			propValue = properties.getValue("Singlebottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			//			singleButton.setEnabled(set);
			singleButton.setVisible(set);

			propValue = properties.getValue("Continuousbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			//			continuousButton.setEnabled(set);
			continuousButton.setVisible(set);

			propValue = properties.getValue("Continuousfacingbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			//			continuousFacingButton.setEnabled(set);
			continuousFacingButton.setVisible(set);

			propValue = properties.getValue("Facingbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			//			facingButton.setEnabled(set);
			facingButton.setVisible(set);


			propValue = properties.getValue("PageFlowbottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			//            pageFlowButton.setEnabled(set);
			pageFlowButton.setVisible(set);


			propValue = properties.getValue("Memorybottom");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			memoryBar.setEnabled(set);
			memoryBar.setVisible(set);



			/**
			 * Items on option pane
			 */
			propValue = properties.getValue("Scalingdisplay");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			scalingBox.setEnabled(set);
			scalingBox.setVisible(set);
			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarScaling.text"))){
						c[i].setEnabled(set);
						c[i].setVisible(set);
						//properties.setValue("Scalingdisplay", String.valueOf(set));
					}
				}
			}

			propValue = properties.getValue("Rotationdisplay");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			rotationBox.setEnabled(set);
			rotationBox.setVisible(set);
			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarRotation.text"))){
						c[i].setEnabled(set);
						c[i].setVisible(set);
						//properties.setValue("Rotationdisplay", String.valueOf(set));
					}
				}
			}

			propValue = properties.getValue("Imageopdisplay");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");

			if(qualityBox!=null){
				qualityBox.setVisible(set);
				qualityBox.setEnabled(set);
			}

			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarImageOp.text"))){
						c[i].setVisible(set);
						c[i].setEnabled(set);
						//properties.setValue("Imageopdisplay", String.valueOf(set));
					}
				}
			}

			propValue = properties.getValue("Progressdisplay");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			statusBar.setEnabled(set);
			//<start-wrap>
			logger.info("statusBar::::::: " + set);
			statusBar.setVisible(set);
			//<end-wrap>

			propValue = properties.getValue("Downloadprogressdisplay");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			downloadBar.setEnabled(set);
			//<start-wrap>
			downloadBar.setVisible(set);
			//<end-wrap>

			/**
			 * Items on button bar
			 */
			propValue = properties.getValue("Openfilebutton");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			openButton.setEnabled(set);
			openButton.setVisible(set);

			if( commonValues.getSelectedFile()!=null) {
				propValue = properties.getValue("Printbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				printButton.setEnabled(set);
				printButton.setVisible(set);
 
				propValue = properties.getValue( PropertyConstants.TIMBRO_BUTTON );
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				timbroButton.setEnabled(set);
				timbroButton.setVisible(set);

				propValue = properties.getValue("Signbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				signButton.setEnabled(set);
				signButton.setVisible(set);

				propValue = properties.getValue("Markbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				markButton.setEnabled(set);
				markButton.setVisible(set);

				propValue = properties.getValue("SignMarkbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				signMarkButton.setEnabled(set);
				signMarkButton.setVisible(set);

				propValue = properties.getValue("Savebutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				saveButton.setEnabled(set);
				saveButton.setVisible(set);

				propValue = properties.getValue("Searchbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				searchButton.setEnabled(set);
				searchButton.setVisible(set);
				
				propValue = properties.getValue("Propertiesbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				docPropButton.setEnabled(set);
				docPropButton.setVisible(set);

				propValue = properties.getValue("Aboutbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				infoButton.setEnabled(set);
				infoButton.setVisible(set);

				propValue = properties.getValue("Snapshotbutton");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				snapshotButton.setEnabled(set);
				snapshotButton.setVisible(set);
			}

			propValue = properties.getValue("CursorButton");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			cursor.setEnabled(set);
			cursor.setVisible(set);

			propValue = properties.getValue("MouseModeButton");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			mouseMode.setEnabled(set);
			mouseMode.setVisible(set);

			propValue = properties.getValue("InviaLogButton");
			set = propValue.length()>0 && propValue.toLowerCase().equals("true");
			inviaLogButton.setEnabled(set);
			inviaLogButton.setVisible(set);
			
			/**
			 * Items on signature tab
			 */
			if(PdfDecoder.isRunningOnMac){
				propValue = properties.getValue("Pagetab");
				set = (properties.getValue("Pagetab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(pageTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}

				propValue = properties.getValue("Bookmarkstab");
				set = (properties.getValue("Bookmarkstab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(bookmarksTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}

				propValue = properties.getValue("Layerstab");
				set = (properties.getValue("Layerstab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(layersTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}

				propValue = properties.getValue("Signaturestab");
				set = (properties.getValue("Signaturestab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(signaturesTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}else{
				propValue = properties.getValue("Pagetab");
				set = (properties.getValue("Pagetab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(pageTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}

				propValue = properties.getValue("Bookmarkstab");
				set = (properties.getValue("Bookmarkstab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(bookmarksTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}

				propValue = properties.getValue("Layerstab");
				set = (properties.getValue("Layerstab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(layersTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}


				propValue = properties.getValue("Signaturestab");
				set = (properties.getValue("Signaturestab").toLowerCase().equals("true") && navOptionsPanel.getTabCount()!=0);
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(signaturesTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			/**
			 * Items from the menu item
			 */
			if(fileMenu!=null){ //all of these will be null in 'Wrapper' mode so ignore

				propValue = properties.getValue("FileMenu");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				fileMenu.setEnabled(set);
				fileMenu.setVisible(set);


				propValue = properties.getValue("OpenMenu");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				openMenu.setEnabled(set);
				openMenu.setVisible(set);

				propValue = properties.getValue("Open");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				open.setEnabled(set);
				open.setVisible(set);


				propValue = properties.getValue("Openurl");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				openUrl.setEnabled(set);
				openUrl.setVisible(set);

				if( commonValues.getSelectedFile()!=null) {
					if( commonValues.getSaveEnabled()!=null ){
						propValue = commonValues.getSaveEnabled().toString();
						logger.info("Abilitazione funzionalità di salvataggio file dalle preferences " + propValue );
					} else {
						propValue = properties.getValue("Save");
						logger.info("Abilitazione funzionalità di salvataggio file dalla configurazione " + propValue );
					}
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					save.setEnabled(set);
					save.setVisible(set);
					if(set) {
						addButton(GUIFactory.BUTTONBAR, Messages.getMessage("PdfViewerFileMenuTooltip.save"), iconLocation+"save.gif", Commands.SAVE);
					}

					propValue = properties.getValue("Find");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					find.setEnabled(set); 
					find.setVisible(set);

					if( commonValues.getSelectedFile()!=null ){
						if( commonValues.getTimbroEnabled()!=null ){
							propValue = commonValues.getTimbroEnabled().toString();
							logger.info("Abilitazione funzionalità di timbratura dalle preferences " + propValue );
						} else {
							propValue = properties.getValue( PropertyConstants.TIMBRO );
							logger.info("Abilitazione funzionalità di timbratura dalla configurazione " + propValue );
						}
						set = propValue.length()>0 && propValue.toLowerCase().equals("true");
						timbro.setEnabled(set); 
						timbro.setVisible(set);
						//icona print
						if(set) {
							addButton(GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_TIMBRO_TOOLTIP ), iconLocation+"stamp.png", Commands.TIMBRA);
						}
					}
					
					if( documentProperties!=null ) {
						propValue = properties.getValue("Documentproperties");
						set = propValue.length()>0 && propValue.toLowerCase().equals("true");
						documentProperties.setEnabled(set); 
						documentProperties.setVisible(set);
						if(set) {
							addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.properties"),iconLocation+"properties.gif",Commands.DOCINFO);
						}
					}
					
					addButton(GUIFactory.BUTTONBAR,Messages.getMessage("PdfViewerToolbarTooltip.snapshot"),iconLocation+"snapshot.gif",Commands.SNAPSHOT);
				}

				//@SIGNING
				if(enableShowSign && signPDF!=null && commonValues.getSelectedFile()!=null){
					if( commonValues.getSignEnabled()!=null ){
						propValue = commonValues.getSignEnabled().toString();
						logger.info("Abilitazione funzionalità di firma dalle preferences " + propValue );
					} else {
						propValue = properties.getValue( PropertyConstants.SIGN );
						logger.info("Abilitazione funzionalità di firma dalla configurazione " + propValue );
					}
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					signPDF.setEnabled(commonValues.isItextOnClasspath() && commonValues.isEncrypOnClasspath()); 
					signPDF.setVisible(set);
					if(set) 
						addButton(GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_FIRMA_TOOLTIP ), iconLocation+"buttonsigner.png", Commands.SIGN);

					if( commonValues.getMarkEnabled()!=null ){
						propValue = commonValues.getMarkEnabled().toString();
						logger.info("Abilitazione funzionalità di marcatura dalle preferences " + propValue );
					} else {
						propValue = properties.getValue( PropertyConstants.MARK );
						logger.info("Abilitazione funzionalità di marcatura dalla configurazione " + propValue );
					}
					//set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					//TODO togliere questo codice se si vuole ripristinare la funzionalità di sola marcatura
					set = false;
					markPDF.setVisible(set);
					if(set) 
						addButton(GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_MARCA_TOOLTIP ), iconLocation+"buttonMarca.png", Commands.MARK);

					if( commonValues.getMarkEnabled()!=null && commonValues.getMarkEnabled()!=null){
						propValue = Boolean.toString(commonValues.getMarkEnabled() && commonValues.getSignEnabled());
						logger.info("Abilitazione funzionalità di firma e marcatura dalle preferences " + propValue );
					} else {
						propValue = properties.getValue( PropertyConstants.SIGN ) + properties.getValue( PropertyConstants.MARK );
						logger.info("Abilitazione funzionalità di firma e marcatura dalla configurazione " + propValue );
					}
					set = propValue.length()>0 && propValue.toLowerCase().contains("true");
					signMarkPDF.setVisible(set);
					if(set) 
						addButton(GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_FIRMAMARCA_TOOLTIP ), iconLocation+"buttonSignEMarca.png", Commands.SIGNMARK);

				}

				if( commonValues.getSelectedFile()!=null){
					if( commonValues.getPrintEnabled()!=null ){
						propValue = commonValues.getPrintEnabled().toString();
						logger.info("Abilitazione funzionalità di stampa dalle preferences " + propValue );
					} else {
						propValue = properties.getValue( PropertyConstants.PRINT );
						logger.info("Abilitazione funzionalità di stampa dalla configurazione " + propValue );
					}
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					print.setEnabled(set); 
					print.setVisible(set);
					//icona print
					if(set) {
						addButton(GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_PRINT_TOOLTIP ), iconLocation+"print.gif", Commands.PRINT);
					}
					
					File originalFile = commonValues.getOriginalFile();
					if( originalFile!=null && originalFile.getAbsolutePath().equalsIgnoreCase( commonValues.getSelectedFile() )){
						originalFileMenu.setVisible(false);
					} else {
						if( originalFile!=null && originalFile.getAbsolutePath().endsWith(".p7m") ){
							String originalFilePath = originalFile.getAbsolutePath();
							String selectedFilePath = commonValues.getSelectedFile();
							originalFilePath = originalFilePath.substring(0, originalFilePath.lastIndexOf(".p7m") );
							selectedFilePath = selectedFilePath.substring(0, selectedFilePath.lastIndexOf(".pdf") );
							if( originalFilePath.equalsIgnoreCase( selectedFilePath )){
								originalFileMenu.setVisible(false);
							} else {
								originalFileMenu.setVisible(true);
							}
						} else 
							originalFileMenu.setVisible(true);
					}
				}
				propValue = properties.getValue("Recentdocuments"); 
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				currentCommands.enableRecentDocuments(set);

				propValue = properties.getValue( PropertyConstants.INVIOLOG );
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				//if( LogWriter.log_name==null && LogWriter.logArray==null ){
					set = false;
				//}
				inviaLog.setEnabled(set); 
				inviaLog.setVisible(set);
				
				if(set) {
					addButton( GUIFactory.BUTTONBAR, Messages.getMessage( MessageConstants.MENU_INVIOLOG_TOOLTIP ),iconLocation+"mail-message-icon.png", Commands.INVIALOG );
				}
				
				propValue = properties.getValue("Exit");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				exit.setEnabled(set); 
				exit.setVisible(set);


				propValue = properties.getValue("EditMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");editMenu.setEnabled(set); editMenu.setVisible(set);

				propValue = properties.getValue("Copy");set = propValue.length()>0 && propValue.toLowerCase().equals("true");copy.setEnabled(set); copy.setVisible(set);

				propValue = properties.getValue("Selectall");set = propValue.length()>0 && propValue.toLowerCase().equals("true");selectAll.setEnabled(set); selectAll.setVisible(set);

				propValue = properties.getValue("Deselectall");set = propValue.length()>0 && propValue.toLowerCase().equals("true");deselectAll.setEnabled(set); deselectAll.setVisible(set);

				propValue = properties.getValue("Preferences");
				set = (propValue.length()>0 && propValue.toLowerCase().equals("true")) && (!properties.getValue("readOnly").toLowerCase().equals("true"));
				preferences.setEnabled(set);
				preferences.setVisible(set);


				propValue = properties.getValue("ViewMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");viewMenu.setEnabled(set); viewMenu.setVisible(set);

				propValue = properties.getValue("GotoMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");goToMenu.setEnabled(set); goToMenu.setVisible(set);

				propValue = properties.getValue("Firstpage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");firstPage.setEnabled(set); firstPage.setVisible(set);

				propValue = properties.getValue("Backpage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");backPage.setEnabled(set); backPage.setVisible(set);

				propValue = properties.getValue("Forwardpage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");forwardPage.setEnabled(set); forwardPage.setVisible(set);

				propValue = properties.getValue("Lastpage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");lastPage.setEnabled(set); lastPage.setVisible(set);

				propValue = properties.getValue("Goto");set = propValue.length()>0 && propValue.toLowerCase().equals("true");goTo.setEnabled(set); goTo.setVisible(set);

				propValue = properties.getValue("Previousdocument");set = propValue.length()>0 && propValue.toLowerCase().equals("true");previousDocument.setEnabled(set); previousDocument.setVisible(set);

				propValue = properties.getValue("Nextdocument");set = propValue.length()>0 && propValue.toLowerCase().equals("true");nextDocument.setEnabled(set); nextDocument.setVisible(set);


				if(pageLayoutMenu!=null){
					propValue = properties.getValue("PagelayoutMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");pageLayoutMenu.setEnabled(set); pageLayoutMenu.setVisible(set);
				}

				if(single!=null){
					propValue = properties.getValue("Single");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					single.setEnabled(set);
					single.setVisible(set);
				}

				if(continuous!=null){
					propValue = properties.getValue("Continuous");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					continuous.setEnabled(set);
					continuous.setVisible(set);
				}

				if(facing!=null){
					propValue = properties.getValue("Facing");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					facing.setEnabled(set);
					facing.setVisible(set);
				}

				if(continuousFacing!=null){
					propValue = properties.getValue("Continuousfacing");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					continuousFacing.setEnabled(set);
					continuousFacing.setVisible(set);
				}

				if(pageFlow!=null){

					propValue = properties.getValue("PageFlow");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					pageFlow.setEnabled(set);
					pageFlow.setVisible(set);
				}

				if(textSelect!=null){

					propValue = properties.getValue("textSelect");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					textSelect.setEnabled(set);
					textSelect.setVisible(set);
				}

				if(separateCover!=null){
					propValue = properties.getValue("separateCover");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					separateCover.setEnabled(set);
					separateCover.setVisible(set);
				}

				if(panMode!=null){

					propValue = properties.getValue("panMode");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					panMode.setEnabled(set);
					panMode.setVisible(set);
				}

				if(fullscreen!=null){

					propValue = properties.getValue("Fullscreen");
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					fullscreen.setEnabled(set);
					fullscreen.setVisible(set);
				}

				if(windowMenu!=null){

					propValue = properties.getValue("WindowMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");windowMenu.setEnabled(set); windowMenu.setVisible(set);

					propValue = properties.getValue("Cascade");set = propValue.length()>0 && propValue.toLowerCase().equals("true");cascade.setEnabled(set); cascade.setVisible(set);

					propValue = properties.getValue("Tile");set = propValue.length()>0 && propValue.toLowerCase().equals("true");tile.setEnabled(set); tile.setVisible(set);
				}

				if(commonValues.isItextOnClasspath()){
				}
				//cost copied from old version
				if(commonValues.getSelectedFile()!=null){
					if(reSaveAsForms!=null) {
						propValue = properties.getValue("Resaveasforms");
						set = propValue.length()>0 && propValue.toLowerCase().equals("true");
						reSaveAsForms.setEnabled(set);
						reSaveAsForms.setVisible(set);
					}
				}

				if(pageToolsMenu!=null){
					propValue = properties.getValue("PagetoolsMenu");set = propValue.length()>0 && propValue.toLowerCase().equals("true");pageToolsMenu.setEnabled(set); pageToolsMenu.setVisible(set);
					propValue = properties.getValue("Rotatepages");set = propValue.length()>0 && propValue.toLowerCase().equals("true");rotatePages.setEnabled(set); rotatePages.setVisible(set);
					propValue = properties.getValue("Deletepages");set = propValue.length()>0 && propValue.toLowerCase().equals("true");deletePages.setEnabled(set); deletePages.setVisible(set);
					propValue = properties.getValue("Addpage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");addPage.setEnabled(set); addPage.setVisible(set);
					propValue = properties.getValue("Addheaderfooter");set = propValue.length()>0 && propValue.toLowerCase().equals("true");addHeaderFooter.setEnabled(set); addHeaderFooter.setVisible(set);
					propValue = properties.getValue("Stamptext");set = propValue.length()>0 && propValue.toLowerCase().equals("true");stampText.setEnabled(set); stampText.setVisible(set);
					propValue = properties.getValue("Stampimage");set = propValue.length()>0 && propValue.toLowerCase().equals("true");stampImage.setEnabled(set); stampImage.setVisible(set);
					propValue = properties.getValue("Crop");set = propValue.length()>0 && propValue.toLowerCase().equals("true");crop.setEnabled(set); crop.setVisible(set);
				}
				//end copied
				
				propValue = properties.getValue("HelpMenu");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				helpMenu.setEnabled(set); 
				helpMenu.setVisible(set);
				
				propValue = properties.getValue("Visitwebsite");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				visitWebsite.setEnabled(set); 
				visitWebsite.setVisible(set);

				propValue = properties.getValue("Tipoftheday");
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				tipOfTheDay.setEnabled(set); 
				tipOfTheDay.setVisible(set);

				propValue = properties.getValue("Checkupdates");set = propValue.length()>0 && propValue.toLowerCase().equals("true");checkUpdates.setEnabled(set); checkUpdates.setVisible(set);

				propValue = properties.getValue("About");set = propValue.length()>0 && propValue.toLowerCase().equals("true");about.setEnabled(set); about.setVisible(set);


				propValue = properties.getValue( PropertyConstants.PREFERENCE_MENU );
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				preferenceMenu.setEnabled(set); 
				preferenceMenu.setVisible(set);
				
				Boolean firmaEnabled = commonValues.getSignEnabled();
				Boolean marcaEnabled = commonValues.getMarkEnabled();
				
				if(!firmaEnabled && !marcaEnabled){
					firmaMarcaPreference.setEnabled(false); 
					firmaMarcaPreference.setVisible(false);
				} else {
					propValue = properties.getValue( PropertyConstants.PREFERENCE_FIRMAMARCA );
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					firmaMarcaPreference.setEnabled(set); 
					firmaMarcaPreference.setVisible(set);
				}

				Boolean timbroEnabled = commonValues.getTimbroEnabled();
				if( !timbroEnabled ){
					timbroPreference.setEnabled(false); 
					timbroPreference.setVisible(false);
				} else {
					propValue = properties.getValue( PropertyConstants.PREFERENCE_TIMBRO );
					set = propValue.length()>0 && propValue.toLowerCase().equals("true");
					timbroPreference.setEnabled(set); 
					timbroPreference.setVisible(set);
				}
				
				propValue = properties.getValue( PropertyConstants.PREFERENCE_RETE );
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				retePreference.setEnabled(set); 
				retePreference.setVisible(set);
				
				propValue = properties.getValue( PropertyConstants.PREFERENCE_INVIOLOG );
				set = propValue.length()>0 && propValue.toLowerCase().equals("true");
				invioLogPreference.setEnabled(set); 
				invioLogPreference.setVisible(set);
				
				/*
				 * Ensure none of the menus start with a separator
				 */
				for(int k=0; k!=fileMenu.getMenuComponentCount(); k++){
					if(fileMenu.getMenuComponent(k).isVisible()){
						if(fileMenu.getMenuComponent(k) instanceof JSeparator){
							fileMenu.remove(fileMenu.getMenuComponent(k));
						}
						break;
					}
				}

				for(int k=0; k!=editMenu.getMenuComponentCount(); k++){
					if(editMenu.getMenuComponent(k).isVisible()){
						if(editMenu.getMenuComponent(k) instanceof JSeparator){
							editMenu.remove(editMenu.getMenuComponent(k));
						}
						break;
					}
				}

				for(int k=0; k!=viewMenu.getMenuComponentCount(); k++){
					if(viewMenu.getMenuComponent(k).isVisible()){
						if(viewMenu.getMenuComponent(k) instanceof JSeparator){
							viewMenu.remove(viewMenu.getMenuComponent(k));
						}
						break;
					}
				}

				for(int k=0; k!=goToMenu.getMenuComponentCount(); k++){
					if(goToMenu.getMenuComponent(k).isVisible()){
						if(goToMenu.getMenuComponent(k) instanceof JSeparator){
							goToMenu.remove(goToMenu.getMenuComponent(k));
						}
						break;
					}
				}

			}


			checkButtonSeparators();

		}catch(Exception ee){
			ee.printStackTrace();
		}

		//<start-demo>
		/**
        //<end-demo>

        /**/
	}

	public void alterProperty(String value, boolean set){
		Component[] c = comboBoxBar.getComponents();

		//Disable entire section
		if(value.equals("ShowMenubar")){
			currentMenu.setEnabled(set);
			currentMenu.setVisible(set);
			properties.setValue("ShowMenubar", String.valueOf(set));
		}
		if(value.equals("ShowButtons")){
			topButtons.setEnabled(set);
			topButtons.setVisible(set);
			properties.setValue("ShowButtons", String.valueOf(set));
		}
		if(value.equals("ShowDisplayoptions")){
			comboBoxBar.setEnabled(set);
			comboBoxBar.setVisible(set);
			properties.setValue("ShowDisplayoptions", String.valueOf(set));
		}
		if(value.equals("ShowNavigationbar")){
			navButtons.setEnabled(set);
			navButtons.setVisible(set);
			properties.setValue("ShowNavigationbar", String.valueOf(set));
		}
		if(value.equals("ShowSidetabbar")){
			if(!set)
				displayPane.setDividerSize(0);
			else
				displayPane.setDividerSize(5);
			displayPane.getLeftComponent().setEnabled(set);
			displayPane.getLeftComponent().setVisible(set);
			properties.setValue("ShowSidetabbar", String.valueOf(set));
		}

		/**
		 * Items on nav pane
		 */
		if(value.equals("Firstbottom")){
			first.setEnabled(set);
			first.setVisible(set);
		}
		if(value.equals("Back10bottom")){
			fback.setEnabled(set);
			fback.setVisible(set);
		}
		if(value.equals("Backbottom")){
			back.setEnabled(set);
			back.setVisible(set);
		}
		if(value.equals("Gotobottom")){
			pageCounter1.setEnabled(set);
			pageCounter1.setVisible(set);

			pageCounter2.setEnabled(set);
			pageCounter2.setVisible(set);

			pageCounter3.setEnabled(set);
			pageCounter3.setVisible(set);
		}
		if(value.equals("Forwardbottom")){
			forward.setEnabled(set);
			forward.setVisible(set);
		}
		if(value.equals("Forward10bottom")){
			fforward.setEnabled(set);
			fforward.setVisible(set);
		}
		if(value.equals("Lastbottom")){
			end.setEnabled(set);
			end.setVisible(set);
		}
		if(value.equals("Singlebottom")){
			//			singleButton.setEnabled(set);
			singleButton.setVisible(set);
		}
		if(value.equals("Continuousbottom")){
			//			continuousButton.setEnabled(set);
			continuousButton.setVisible(set);
		}
		if(value.equals("Continuousfacingbottom")){
			//			continuousFacingButton.setEnabled(set);
			continuousFacingButton.setVisible(set);
		}
		if(value.equals("Facingbottom")){
			//			facingButton.setEnabled(set);
			facingButton.setVisible(set);
		}
		if(value.equals("PageFlowbottom")){
			//            pageFlowButton.setEnabled(set);
			pageFlowButton.setVisible(set);
		}
		if(value.equals("Memorybottom")){
			memoryBar.setEnabled(set);
			memoryBar.setVisible(set);
		}


		/**
		 * Items on option pane
		 */
		if(value.equals("Scalingdisplay")){
			scalingBox.setEnabled(set);
			scalingBox.setVisible(set);
			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarScaling.text"))){
						c[i].setEnabled(set);
						c[i].setVisible(set);
						properties.setValue("Scalingdisplay", String.valueOf(set));
					}
				}
			}
		}
		if(value.equals("Rotationdisplay")){
			rotationBox.setEnabled(set);
			rotationBox.setVisible(set);
			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarRotation.text"))){
						c[i].setEnabled(set);
						c[i].setVisible(set);
						properties.setValue("Rotationdisplay", String.valueOf(set));
					}
				}
			}
		}
		if(value.equals("Imageopdisplay")){
			qualityBox.setVisible(set);
			qualityBox.setEnabled(set);
			for(int i=0; i!=c.length; i++){
				if(c[i] instanceof JLabel){
					if(((JLabel)c[i]).getText().equals(Messages.getMessage("PdfViewerToolbarImageOp.text"))){
						c[i].setVisible(set);
						c[i].setEnabled(set);
						properties.setValue("Imageopdisplay", String.valueOf(set));
					}
				}
			}
		}
		if(value.equals("Progressdisplay")){
			statusBar.setEnabled(set);
			//<start-wrap>
			statusBar.setVisible(set);
			//<end-wrap>
			properties.setValue("Progressdisplay", String.valueOf(set));
		}
		if(value.equals("Downloadprogressdisplay")){
			downloadBar.setEnabled(set);
			//<start-wrap>
			downloadBar.setVisible(set);
			//<end-wrap>
			properties.setValue("Downloadprogressdisplay", String.valueOf(set));
		}

		/**
		 * Items on button bar
		 */
		if(value.equals("Openfilebutton")){
			openButton.setEnabled(set);
			openButton.setVisible(set);
		}
		if(value.equals("Printbutton")){
			printButton.setEnabled(set);
			printButton.setVisible(set);
		}
		if(value.equals("Savebutton")){
			saveButton.setEnabled(set);
			saveButton.setVisible(set);
		}
		if(value.equals("Searchbutton")){
			searchButton.setEnabled(set);
			searchButton.setVisible(set);
		}
		if(value.equals("Propertiesbutton")){
			docPropButton.setEnabled(set);
			docPropButton.setVisible(set);
		}
		if(value.equals("Aboutbutton")){
			infoButton.setEnabled(set);
			infoButton.setVisible(set);
		}
		if(value.equals("Snapshotbutton")){
			snapshotButton.setEnabled(set);
			snapshotButton.setVisible(set);
		}

		if(value.equals("CursorButton")){
			cursor.setEnabled(set);
			cursor.setVisible(set);
		}

		if(value.equals("MouseModeButton")){
			mouseMode.setEnabled(set);
			mouseMode.setVisible(set);
		}

		/**
		 * Items on signature tab
		 */
		if(PdfDecoder.isRunningOnMac){
			if(value.equals("Pagetab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(pageTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Bookmarkstab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(bookmarksTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Layerstab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(layersTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Signaturestab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getTitleAt(i).equals(signaturesTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
		}else{
			if(value.equals("Pagetab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(pageTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Bookmarkstab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(bookmarksTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Layerstab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(layersTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
			if(value.equals("Signaturestab") && navOptionsPanel.getTabCount()!=0){
				for(int i=0; i<navOptionsPanel.getTabCount(); i++){

					if(navOptionsPanel.getIconAt(i).toString().equals(signaturesTitle) && !set){
						navOptionsPanel.remove(i);
					}
				}
			}
		}
		/**
		 * Items from the menu item
		 */
		if(value.equals("FileMenu")){fileMenu.setEnabled(set); fileMenu.setVisible(set);}
		if(value.equals("OpenMenu")){openMenu.setEnabled(set); openMenu.setVisible(set);}
		if(value.equals("Open")){open.setEnabled(set); open.setVisible(set);}
		if(value.equals("Openurl")){openUrl.setEnabled(set); openUrl.setVisible(set);}

		if(value.equals("Save")){save.setEnabled(set); save.setVisible(set);}

		//added check to code (as it may not have been initialised)
		if(value.equals("Resaveasforms") && reSaveAsForms!=null){ //will not be initialised if Itext not on path
			reSaveAsForms.setEnabled(set);
			reSaveAsForms.setVisible(set);
		}

		if(value.equals("Find")){find.setEnabled(set); find.setVisible(set);}
		if(value.equals("Documentproperties")){documentProperties.setEnabled(set); documentProperties.setVisible(set);}
		//@SIGNING
		if(enableShowSign && value.equals( PropertyConstants.SIGN )){
			signPDF.setEnabled(set); signPDF.setVisible(set);
		}

		if(value.equals( PropertyConstants.PRINT )){print.setEnabled(set); print.setVisible(set);}
		if(value.equals("Recentdocuments")){currentCommands.enableRecentDocuments(set);}
		if(value.equals("Exit")){exit.setEnabled(set); exit.setVisible(set);}

		if(value.equals("EditMenu")){editMenu.setEnabled(set); editMenu.setVisible(set);}
		if(value.equals("Copy")){copy.setEnabled(set); copy.setVisible(set);}
		if(value.equals("Selectall")){selectAll.setEnabled(set); selectAll.setVisible(set);}
		if(value.equals("Deselectall")){deselectAll.setEnabled(set); deselectAll.setVisible(set);}
		if(value.equals("Preferences")){preferences.setEnabled(set); preferences.setVisible(set);}

		if(value.equals("ViewMenu")){viewMenu.setEnabled(set); viewMenu.setVisible(set);}
		if(value.equals("GotoMenu")){goToMenu.setEnabled(set); goToMenu.setVisible(set);}
		if(value.equals("Firstpage")){firstPage.setEnabled(set); firstPage.setVisible(set);}
		if(value.equals("Backpage")){backPage.setEnabled(set); backPage.setVisible(set);}
		if(value.equals("Forwardpage")){forwardPage.setEnabled(set); forwardPage.setVisible(set);}
		if(value.equals("Lastpage")){lastPage.setEnabled(set); lastPage.setVisible(set);}
		if(value.equals("Goto")){goTo.setEnabled(set); goTo.setVisible(set);}
		if(value.equals("Previousdocument")){previousDocument.setEnabled(set); previousDocument.setVisible(set);}
		if(value.equals("Nextdocument")){nextDocument.setEnabled(set); nextDocument.setVisible(set);}

		if(value.equals("PagelayoutMenu")){pageLayoutMenu.setEnabled(set); pageLayoutMenu.setVisible(set);}
		if(value.equals("Single")){single.setEnabled(set); single.setVisible(set);}
		if(value.equals("Continuous")){continuous.setEnabled(set); continuous.setVisible(set);}
		if(value.equals("Facing")){facing.setEnabled(set); facing.setVisible(set);}
		if(value.equals("Continuousfacing")){continuousFacing.setEnabled(set); continuousFacing.setVisible(set);}

		//@kieran Remove when ready
		if(pageFlow!=null)
			if(value.equals("PageFlow")){pageFlow.setEnabled(set); pageFlow.setVisible(set);}
		if(panMode!=null)
			if(value.equals("panMode")){panMode.setEnabled(set); panMode.setVisible(set);}
		if(textSelect!=null)
			if(value.equals("textSelect")){textSelect.setEnabled(set); textSelect.setVisible(set);}
		if(value.equals("Fullscreen")){fullscreen.setEnabled(set); fullscreen.setVisible(set);}
		if(separateCover!=null)
			if(value.equals("separateCover")){separateCover.setEnabled(set); separateCover.setVisible(set);}

		if(windowMenu!=null){
			if(value.equals("WindowMenu")){windowMenu.setEnabled(set); windowMenu.setVisible(set);}
			if(value.equals("Cascade")){cascade.setEnabled(set); cascade.setVisible(set);}
			if(value.equals("Tile")){tile.setEnabled(set); tile.setVisible(set);}
		}
		if(commonValues.isItextOnClasspath()){
			if(value.equals("ExportMenu")){exportMenu.setEnabled(set); exportMenu.setVisible(set);}
			if(value.equals("PdfMenu")){pdfMenu.setEnabled(set); pdfMenu.setVisible(set);}
			if(value.equals("Oneperpage")){onePerPage.setEnabled(set); onePerPage.setVisible(set);}
			if(value.equals("Nup")){nup.setEnabled(set); nup.setVisible(set);}
			if(value.equals("Handouts")){handouts.setEnabled(set); handouts.setVisible(set);}

			if(value.equals("ContentMenu")){contentMenu.setEnabled(set); contentMenu.setVisible(set);}
			if(value.equals("Images")){images.setEnabled(set); images.setVisible(set);}
			if(value.equals("Text")){text.setEnabled(set); text.setVisible(set);}

			if(value.equals("Bitmap")){bitmap.setEnabled(set); bitmap.setVisible(set);}

			if(value.equals("PagetoolsMenu")){pageToolsMenu.setEnabled(set); pageToolsMenu.setVisible(set);}
			if(value.equals("Rotatepages")){rotatePages.setEnabled(set); rotatePages.setVisible(set);}
			if(value.equals("Deletepages")){deletePages.setEnabled(set); deletePages.setVisible(set);}
			if(value.equals("Addpage")){addPage.setEnabled(set); addPage.setVisible(set);}
			if(value.equals("Addheaderfooter")){addHeaderFooter.setEnabled(set); addHeaderFooter.setVisible(set);}
			if(value.equals("Stamptext")){stampText.setEnabled(set); stampText.setVisible(set);}
			if(value.equals("Stampimage")){stampImage.setEnabled(set); stampImage.setVisible(set);}
			if(value.equals("Crop")){crop.setEnabled(set); crop.setVisible(set);}
		}
		if(value.equals("HelpMenu")){helpMenu.setEnabled(set); helpMenu.setVisible(set);}
		if(value.equals("Visitwebsite")){visitWebsite.setEnabled(set); visitWebsite.setVisible(set);}
		if(value.equals("Tipoftheday")){tipOfTheDay.setEnabled(set); tipOfTheDay.setVisible(set);}
		if(value.equals("Checkupdates")){checkUpdates.setEnabled(set); checkUpdates.setVisible(set);}
		if(value.equals("About")){about.setEnabled(set); about.setVisible(set);}

		if(value.equals( PropertyConstants.PREFERENCE_MENU ) ){preferenceMenu.setEnabled(set); preferenceMenu.setVisible(set);}
		if(value.equals( PropertyConstants.PREFERENCE_FIRMAMARCA ) ){firmaMarcaPreference.setEnabled(set); firmaMarcaPreference.setVisible(set);}
		if(value.equals( PropertyConstants.PREFERENCE_TIMBRO ) ){timbroPreference.setEnabled(set); timbroPreference.setVisible(set);}
		if(value.equals( PropertyConstants.PREFERENCE_RETE ) ){retePreference.setEnabled(set); retePreference.setVisible(set);}
		if(value.equals( PropertyConstants.PREFERENCE_INVIOLOG ) ){invioLogPreference.setEnabled(set); invioLogPreference.setVisible(set);}
		
		checkButtonSeparators();
	}


	private void checkButtonSeparators() {
		/**
		 * Ensure the buttonBar doesn't start or end with a separator
		 */
		boolean before=false, after=false;
		JSeparator currentSep=null;
		for(int k=0; k!=topButtons.getComponentCount(); k++) {
			if (topButtons.getComponent(k) instanceof JSeparator){
				if (currentSep == null)
					currentSep = (JSeparator)topButtons.getComponent(k);
				else {
					if (!before || !after)
						currentSep.setVisible(false);
					else
						currentSep.setVisible(true);
					before = before || after;
					after = false;
					currentSep = (JSeparator)topButtons.getComponent(k);
				}
			} else {
				if (topButtons.getComponent(k).isVisible()) {
					if (currentSep == null)
						before=true;
					else
						after=true;
				}
			}
		}
		if (currentSep != null) {
			if (!before || !after)
				currentSep.setVisible(false);
			else
				currentSep.setVisible(true);
		}
	}

	public void getHelpBox() {
		final JPanel panel = new JPanel();

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel label = new JLabel("<html><p>Please click on this link for lots of tutorials and documentation</p>");
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		labelPanel.add(label);
		labelPanel.add(Box.createHorizontalGlue());

		top.add(labelPanel);

		JPanel linkPanel = new JPanel();
		linkPanel.setLayout(new BoxLayout(linkPanel, BoxLayout.X_AXIS));
		linkPanel.add(Box.createHorizontalGlue());
		linkPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final JLabel url=new JLabel("<html><center>"+"http://www.jpedal.org/support.php");
		url.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		url.setForeground(Color.blue);
		url.setHorizontalAlignment(JLabel.CENTER);

		//@kieran - cursor
		url.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					panel.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
				url.setText("<html><center><a>http://www.jpedal.org/support.php</a></center>");
			}

			public void mouseExited(MouseEvent e) {
				if(SingleDisplay.allowChangeCursor)
					panel.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				url.setText("<html><center>http://www.jpedal.org/support.php");
			}

			public void mouseClicked(MouseEvent e) {
				try {
					BrowserLauncher.openURL("http://www.jpedal.org/support.php");
				} catch (IOException e1) {

					JPanel errorPanel = new JPanel();
					errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));

					JLabel errorMessage = new JLabel("Your web browser could not be successfully loaded.  " +
							"Please copy and paste the URL below, manually into your web browser.");
					errorMessage.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					errorMessage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

					JTextArea textArea = new JTextArea("http://www.jpedal.org/support.php");
					textArea.setEditable(false);
					textArea.setRows(5);
					textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					textArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

					errorPanel.add(errorMessage);
					errorPanel.add(textArea);

					showMessageDialog(errorPanel,"Error loading web browser",JOptionPane.PLAIN_MESSAGE);

				}
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});

		linkPanel.add(url);
		linkPanel.add(Box.createHorizontalGlue());
		top.add(linkPanel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(top);

		showMessageDialog(panel,"JPedal Tutorials and documentation",JOptionPane.PLAIN_MESSAGE);
	}

	public void resetSignaturesTree(){
		removeTab(signaturesTitle);
		if(signaturesTree!=null){
			signaturesTree.setCellRenderer(null);
			signaturesTree.removeAll();
		}
		signaturesTree=null;
	}



	/**
	 * get Map containing Form Objects setup for Unique Annotations
	 * @return Map
	 */
	public Map getHotspots() {

		return objs;
	}

	public Point convertPDFto2D(int cx, int cy) {

		float scaling = getScaling();
		int inset = getPDFDisplayInset();
		int rotation = getRotation();

		if(decode_pdf.getDisplayView()!=Display.SINGLE_PAGE){
			//			cx=0;
			//			cy=0;
		} else if(rotation==90){
			int tmp=(cx-this.cropY);
			cx = (cy-this.cropX);
			cy =tmp;	
		}else if((rotation==180)){
			cx =-cx-(this.cropW+this.cropX);
			cy =(cy-this.cropY);
		}else if((rotation==270)){
			int tmp=-(this.cropH+this.cropY)-cx;
			cx =(this.cropW+this.cropX)+cy;
			cy =tmp;
		}else{
			cx = (cx-this.cropX);
			cy =(this.cropH+this.cropY)-cy;
		}

		cx=(int)((cx)*scaling);
		cy=(int)((cy)*scaling);

		if(decode_pdf.getPageAlignment()== Display.DISPLAY_CENTERED){
			int width=decode_pdf.getBounds().width;
			int pdfWidth=decode_pdf.getPDFWidth();

			if(decode_pdf.getDisplayView()!=Display.SINGLE_PAGE)
				pdfWidth=(int)decode_pdf.getMaximumSize().getWidth();

			if(width>pdfWidth)
				cx=cx+((width-pdfWidth)/(2));
		}

		cx=cx+inset;
		cy=cy+inset;

		return new Point(cx,cy);

	}

	/* used by JS to set values that dont need to save the new forms values */
	public boolean getFormsDirtyFlag() {
		return commonValues.isFormsChanged();
	}

	/* used by JS to set values that dont need to save the new forms values */
	public void setFormsDirtyFlag(boolean dirty) {
		commonValues.setFormsChanged(dirty);
	}

	public int getCurrentPage() {
		return commonValues.getCurrentPage();
	}

	public boolean getPageTurnScalingAppropriate() {
		return pageTurnScalingAppropriate;
	}

	public boolean getDragLeft() {
		return dragLeft;
	}

	public boolean getDragTop() {
		return dragTop;
	}

	public void setDragCorner(int a) {
		if (a == OffsetOptions.INTERNAL_DRAG_CURSOR_BOTTOM_LEFT ||
				a == OffsetOptions.INTERNAL_DRAG_CURSOR_TOP_LEFT ||
				a == OffsetOptions.INTERNAL_DRAG_BLANK)
			dragLeft = true;
		else
			dragLeft = false;

		if (a == OffsetOptions.INTERNAL_DRAG_CURSOR_TOP_LEFT ||
				a == OffsetOptions.INTERNAL_DRAG_CURSOR_TOP_RIGHT)
			dragTop = true;
		else
			dragTop = false;
	}

	public void setCursor(int type) {
		decode_pdf.setPDFCursor(getCursor(type));
	}

	public Cursor getCursor(int type) {
		switch (type) {
		case GRAB_CURSOR:
			if (grabCursor == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"grab32.png"));
				grabCursor = kit.createCustomCursor(img, new Point(8,8),"grab");
			}
			return grabCursor;

		case GRABBING_CURSOR:
			if (grabbingCursor == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"grabbing32.png"));
				grabbingCursor = kit.createCustomCursor(img, new Point(8,8),"grabbing");
			}
			return grabbingCursor;

		case PAN_CURSOR:
			if (panCursor == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"pan32.png"));
				panCursor = kit.createCustomCursor(img, new Point(10,10),"pan");
			}
			return panCursor;

		case PAN_CURSORL:
			if (panCursorL == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"panl32.png"));
				panCursorL = kit.createCustomCursor(img, new Point(11,10),"panl");
			}
			return panCursorL;

		case PAN_CURSORTL:
			if (panCursorTL == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"pantl32.png"));
				panCursorTL = kit.createCustomCursor(img, new Point(10,10),"pantl");
			}
			return panCursorTL;

		case PAN_CURSORT:
			if (panCursorT == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"pant32.png"));
				panCursorT = kit.createCustomCursor(img, new Point(10,11),"pant");
			}
			return panCursorT;

		case PAN_CURSORTR:
			if (panCursorTR == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"pantr32.png"));
				panCursorTR = kit.createCustomCursor(img, new Point(10,10),"pantr");
			}
			return panCursorTR;

		case PAN_CURSORR:
			if (panCursorR == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"panr32.png"));
				panCursorR = kit.createCustomCursor(img, new Point(10,10),"panr");
			}
			return panCursorR;

		case PAN_CURSORBR:
			if (panCursorBR == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"panbr32.png"));
				panCursorBR = kit.createCustomCursor(img, new Point(10,10),"panbr");
			}
			return panCursorBR;

		case PAN_CURSORB:
			if (panCursorB == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"panb32.png"));
				panCursorB = kit.createCustomCursor(img, new Point(10,10),"panb");
			}
			return panCursorB;

		case PAN_CURSORBL:
			if (panCursorBL == null) {
				Toolkit kit = Toolkit.getDefaultToolkit();
				Image img = kit.getImage(getURLForImage(iconLocation+"panbl32.png"));
				panCursorBL = kit.createCustomCursor(img, new Point(10,10),"panbl");
			}
			return panCursorBL;

		default:
			return Cursor.getDefaultCursor();

		}
	}

	public void rescanPdfLayers() {
		try {
			if (SwingUtilities.isEventDispatchThread()) {
				//refresh the layers tab so JS updates are carried across.
				syncTreeDisplay(topLayer,true);
				layersPanel.invalidate();
				layersPanel.repaint();
			} else {
				final Runnable doPaintComponent = new Runnable() {
					public void run() {
						//refresh the layers tab so JS updates are carried across.
						syncTreeDisplay(topLayer,true);
						layersPanel.invalidate();
						layersPanel.repaint();
					}
				};
				SwingUtilities.invokeAndWait(doPaintComponent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * generate thumbnails for display
	 */
	private class ScrollMouseListener extends MouseAdapter {

		//    	public void mouseClicked(MouseEvent e) {
		//
		//    		//this.
		//    		//if(debugThumbnail)
		//            System.out.println("clicked");
		//
		//            scrollListener.mousePressed=true;
		//        }

		//    	public void mouseExited(MouseEvent e) {
		//
		//    		//if(debugThumbnail)
		//            System.out.println("Exit");
		//
		//           // scrollListener.mousePressed=true;
		//        }
		//
		//    	public void mouseEntered(MouseEvent e) {
		//
		//    		//if(debugThumbnail)
		//            System.out.println("mouseEntered");
		//
		//            //scrollListener.mousePressed=true;
		//        }
		//
		//    	public void mouseDragged(MouseEvent e) {
		//
		//    		//if(debugThumbnail)
		//            System.out.println("mouseDragged");
		//
		//            scrollListener.mousePressed=true;
		//        }


		public void mousePressed(MouseEvent e) {

			if(debugThumbnail)
				System.out.println("pressed");

			scrollListener.mousePressed=true;

			//if(mousePressed)
			scrollListener.startTimer();

		}

		public void mouseReleased(MouseEvent e) {

			if(debugThumbnail)
				System.out.println("release");

			if(scrollListener.mousePressed)
				scrollListener.releaseAndUpdate();

			scrollListener.mousePressed=false;


		}
	}

	/**
	 * Class to handle thumbnail preview
	 */
	private class ScrollListener implements AdjustmentListener {

		java.util.Timer t = null;
		int pNum=-1,lastPageSent=-1;
		boolean mousePressed=false;
		boolean showLast=false;
		public BufferedImage lastImage;
		public boolean ignoreChange=false;

		private void stopTimer() {
			t=null;
		}
		
		private void startTimer() {
			//turn if off if running
			if (t != null)
				t.cancel();

			if(thumbnails.isShownOnscreen()){
				//restart - if its not stopped it will trigger page update
				TimerTask listener = new PageListener();
				t = new java.util.Timer();
				t.schedule(listener, 175);
			}
		}

		/**
		 * get page and start timer to creatye thumbnail
		 * If not moved, will draw this page
		 */
		public void adjustmentValueChanged(AdjustmentEvent e) {

			pNum=e.getAdjustable().getValue()+1;

			//Show loading image
			if (showLast) {
				//Use stored image
				((SingleDisplay)decode_pdf.getExternalHandler(Options.Display)).setPreviewThumbnail(lastImage, "Page "+pNum+" of "+decode_pdf.getPageCount());
				decode_pdf.repaint();
			} else if (lastImage != null) {
				//Create loading image
				BufferedImage img = new BufferedImage(lastImage.getWidth(), lastImage.getHeight(), lastImage.getType());
				Graphics2D g2 = (Graphics2D)img.getGraphics();

				//Draw last image
				g2.drawImage(lastImage,0,0,null);

				//Gray out
				g2.setPaint(new Color(0,0,0,130));
				g2.fillRect(0,0,lastImage.getWidth(),lastImage.getHeight());

				//Draw loading string
				String l = "Loading...";
				int textW = g2.getFontMetrics().stringWidth(l);
				int textH = g2.getFontMetrics().getHeight();
				g2.setPaint(Color.WHITE);
				g2.drawString(l,(lastImage.getWidth()/2)-(textW/2),(lastImage.getHeight()/2)+(textH/2));

				//Store and set to reuse
				lastImage = img;
				showLast = true;

				//Update
				((SingleDisplay)decode_pdf.getExternalHandler(Options.Display)).setPreviewThumbnail(img,"Page "+pNum+" of "+decode_pdf.getPageCount());
				decode_pdf.repaint();
			}


			//System.out.println(pNum);

			if(mousePressed)
				startTimer();
			else if(!ignoreChange)
				releaseAndUpdate();

			//reset	
			ignoreChange=false;

		}

		public synchronized void setThumbnail(){

			//if(mousePressed){

			if(lastPageSent!=pNum){

				lastPageSent=pNum;

				try{

					//decode_pdf.waitForDecodingToFinish();

					BufferedImage image=thumbnails.getImage(pNum);

					if(debugThumbnail)
						System.out.println(pNum+" "+image);

					//Store and turn off using stored image
					lastImage = image;
					showLast = false;
					((SingleDisplay)decode_pdf.getExternalHandler(Options.Display)).setPreviewThumbnail(image,"Page "+pNum+" of "+decode_pdf.getPageCount());

					decode_pdf.repaint();

				}catch(Exception ee){
				}
				//}
			}
		}

		public void releaseAndUpdate() {

			//turn if off if running
			if (t != null)
				t.cancel();

			//System.out.println("releaseAndUpdate");


			((SingleDisplay)decode_pdf.getExternalHandler(Options.Display)).setPreviewThumbnail(null,"Page "+pNum+" of "+decode_pdf.getPageCount());

			//if(pNum>0 && lastPage!=pNum){

			currentCommands.gotoPage(Integer.toString(pNum));
			//}else
			//  decode_pdf.clearScreen();

			decode_pdf.repaint();
		}

		/**
		 * used to update preview thumbnail to next page
		 */
		class PageListener extends TimerTask {
			public void run() {

				if(mousePressed)
					setThumbnail();
			}
		}
	}
	//custom
	PrintPanel printPanel=null;
	public Object printDialog(String[] printersList, String defaultPrinter, PdfDecoder decodePdf) {

		JDialog printDialog = new JDialog((JFrame)null, "Print", true);

		//get default resolution
		String propValue = properties.getValue("defaultDPI");
		int defaultDPI = -1;
		if (propValue != null && propValue.length()>0) {
			try {
				propValue = propValue.replaceAll("[^0-9]", "");
				defaultDPI = Integer.parseInt(propValue);
			} catch (Exception e) {
			}
		}


		printPanel = new PrintPanel(printersList,defaultPrinter, getPaperSizes(), defaultDPI, getPageNumber(), decodePdf);


		printDialog.getContentPane().add(printPanel);

		printDialog.setSize(670, 415);
		printDialog.setResizable(false);
		//printDialog.setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
		printDialog.setLocationRelativeTo(frame);
		printDialog.setName("printDialog");
		printDialog.setVisible(true);

		printDialog.remove(printPanel);

		return printPanel;
	}
	
	

	public PaperSizes getPaperSizes() {
		if (paperSizes==null)
			paperSizes = new PaperSizes(properties.getValue("defaultPagesize"));
		return paperSizes;
	}
	//custom

	public void dispose(){
		
		super.dispose();

		if( thumbnails!=null){
			stopThumbnails();
			thumbnails.dispose();
		}
		thumbnails=null;
		
		if( scrollListener!=null)
			scrollListener.stopTimer();
		scrollListener=null;
		scrollMouseListener=null;
		
		if(thumbscroll!=null){
			thumbscroll.removeAdjustmentListener(scrollListener);
			thumbscroll.removeAll();
		}
		thumbscroll=null;
		
		windowTitle=null;
		pageTitle=null;
		bookmarksTitle=null;
		signaturesTitle=null;
		layersTitle=null;

		grabCursor=null;grabbingCursor=null;panCursor=null;panCursorL=null;panCursorTL=null;panCursorT=null;panCursorTR=null;
		panCursorR=null;panCursorBR=null;panCursorB=null;panCursorBL=null;
		
		Enumeration menuOptions=layoutGroup.getElements();
		while(menuOptions.hasMoreElements()){
			Object el = menuOptions.nextElement();
			if( el instanceof AbstractButton )
				layoutGroup.remove( (AbstractButton)el );
		}
		layoutGroup=null;
		
		menuOptions=searchLayoutGroup.getElements();
		while(menuOptions.hasMoreElements()){
			Object el = menuOptions.nextElement();
			if( el instanceof AbstractButton )
				searchLayoutGroup.remove( (AbstractButton)el );
		}
		searchLayoutGroup=null;
		
		menuOptions=borderGroup.getElements();
		while(menuOptions.hasMoreElements()){
			Object el = menuOptions.nextElement();
			if( el instanceof AbstractButton )
				borderGroup.remove( (AbstractButton)el );
		}
		borderGroup=null;

		paperSizes=null;
		
		if( multibox!=null ){
			ComponentListener[] list = multibox.getComponentListeners();
			for(ComponentListener l : list){
				multibox.removeComponentListener(l);
			}
			multibox.removeAll();
		}
		multibox=null;
		
		glowOuterColor=null;
		glowInnerColor=null;
		
		if( currentCommandListener!=null)
			currentCommandListener.dispose();
		currentCommandListener=null;

		headFont=null;
		textFont1=null;
		textFont=null;
		statusBar=null;
		downloadBar=null;
		pageCounter1=null;
		customMessageHandler=null;
		pageCounter2=null;
		pageCounter3=null;
		optimizationLabel=null;
		
		mouseMode=null;

		disposeMenu();
		topButtons =null;
		navButtons =null;
		comboBoxBar=null;
		navToolBar =null;
		pagesToolBar =null;
		cursor=null;
		
		if( fontScrollPane!=null ){
			removeListeners(fontScrollPane);
			fontScrollPane.removeAll();
		}
		fontScrollPane=null;
		
		separateCover=null;
		
		if( topLayer!=null ){
			topLayer.removeAllChildren();
		}
		topLayer =null;
		
		if(currentMenu!=null){
			removeListeners( currentMenu );
			currentMenu.removeAll();
		}
		currentMenu =null;

		if(coords!=null){
			removeListeners( coords);
			coords.removeAll();
		}
		coords=null;

		if(frame!=null){
			frame.removeAll();
		}
		frame=null;

		if(desktopPane!=null){
			if( desktopPane.getTransferHandler() instanceof BaseTransferHandler){
				((BaseTransferHandler)desktopPane.getTransferHandler()).dispose();
			}
			desktopPane.setTransferHandler(null);
			desktopPane.removeAll();
		}
		desktopPane=null;

		if(navOptionsPanel!=null){
			for(int jj=0;jj<navOptionsPanel.getTabCount();jj++){
				if(navOptionsPanel.getIconAt(jj)!=null && navOptionsPanel.getIconAt(jj) instanceof VTextIcon){
					((VTextIcon)navOptionsPanel.getIconAt(jj)).dispose();
				}
			}
			navOptionsPanel.removeAll();
		}
		navOptionsPanel=null;

		if(scrollPane!=null){
			removeListeners( scrollPane );
			scrollPane.removeAll();
		}
		scrollPane =null;

		if(signaturesTree!=null){
			signaturesTree.setCellRenderer(null);
			signaturesTree.removeAll();
		}
		signaturesTree=null;

		if(layersPanel!=null){
			removeListeners( layersPanel );
			layersPanel.removeAll();
		}
		layersPanel=null;

		if(printPanel!=null){
			removeListeners( printPanel );
			printPanel.removeAll();
		}
		printPanel=null;
		
		user_dir =null;

		

		if( viewMenu!=null){
			removeListeners(viewMenu);
			viewMenu.removeAll();
		}
		viewMenu=null;
		
		if( menu!=null ){
			removeListeners( menu );
			menu.removeAll();
		}
		menu=null;
		
		//release memory at end
		if(memoryMonitor!=null){
			memoryMonitor.stop();
		}
		memoryMonitor=null;
		
		
		
		searchText=null;
		
		if( options!=null ){
			removeListeners( options );
			options.removeAll();
		}
		options = null;
		
		if( results!=null )
			results.dispose();
		results = null;
		
		if( searchFrame!=null ){
			searchFrame.dispose();
		}
		searchFrame = null;
		
		if( displayPane!=null ){
			removeListeners( displayPane );
			displayPane.removeAll();
		}
		displayPane=null;
		
		if( currentCommands!=null)
			currentCommands.dispose();
		currentCommands = null;
	}

	
	
	private void disposeMenu(){
		hasListener = false;
		
		if( fileMenu!=null ){
			removeListeners( fileMenu);
			fileMenu.removeAll();
		}
		fileMenu=null;
		
		if( openMenu!=null ){
			removeListeners(openMenu);
			openMenu.removeAll();
		}
		openMenu=null;
		
		if( editMenu!=null ){
			removeListeners(editMenu);
			editMenu.removeAll();
		}
		editMenu=null;
		
		if( goToMenu!=null ){
			removeListeners( goToMenu);
			goToMenu.removeAll();
		}
		goToMenu=null;
		
		if( pageLayoutMenu!=null ){
			removeListeners( pageLayoutMenu);
			pageLayoutMenu.removeAll();
		}
		pageLayoutMenu=null;
		
		if( windowMenu!=null ){
			removeListeners(windowMenu);
			windowMenu.removeAll();
		}
		windowMenu=null;
		
		if( exportMenu!=null ){
			removeListeners(exportMenu);
			exportMenu.removeAll();
		}
		exportMenu=null;
		
		if( pdfMenu!=null ){
			removeListeners(pdfMenu);
			pdfMenu.removeAll();
		}
		pdfMenu=null;
		
		if( contentMenu!=null ){
			removeListeners( contentMenu );
			contentMenu.removeAll();
		}
		contentMenu=null;
		
		if( pageToolsMenu!=null ){
			removeListeners(pageToolsMenu);
			pageToolsMenu.removeAll();
		}
		pageToolsMenu=null;
		
		if( helpMenu!=null ){
			removeListeners(helpMenu);
			helpMenu.removeAll();
		}
		helpMenu=null;
		
		if( preferenceMenu!=null ){
			removeListeners( preferenceMenu );
			preferenceMenu.removeAll();
		}
		preferenceMenu=null;
		
		open=null;
		openUrl=null;
		save=null;
		reSaveAsForms=null;
		find=null;
		documentProperties=null;
		signPDF=null;
		markPDF=null;
		signMarkPDF=null;
		print=null;
		timbro=null;
		originalFileMenu=null;
		inviaLog=null;
		exit=null;
		copy=null;
		selectAll=null;
		deselectAll=null;
		preferences=null;
		firstPage=null;
		backPage=null;
		forwardPage=null;
		lastPage=null;
		goTo=null;
		previousDocument=null;
		nextDocument=null;
		single=null;
		continuous=null;
		facing=null;
		continuousFacing=null;
		pageFlow=null;
		textSelect=null;
		panMode=null;
		fullscreen=null;
		cascade=null;
		tile=null;
		onePerPage=null;
		nup=null;
		handouts=null;
		images=null;
		text=null;
		bitmap=null;
		rotatePages=null;
		deletePages=null;
		addPage=null;
		addHeaderFooter=null;
		stampText=null;
		stampImage=null;
		crop=null;
		visitWebsite=null;
		tipOfTheDay=null;
		checkUpdates=null;
		about=null;
		firmaMarcaPreference=null;
		retePreference=null;
		timbroPreference=null;
		invioLogPreference=null;
		
		if( nextSearch!=null )
			removeListeners((JComponent)nextSearch);
		nextSearch=null;
		if( previousSearch!=null )
			removeListeners((JComponent)previousSearch);
		previousSearch=null;
		
		layersObject=null;
		
		if( openButton!=null )
			removeListeners((JComponent)openButton);
		openButton=null;
		
		if( printButton!=null )
			removeListeners((JComponent)printButton);
		printButton=null;
		
		if( saveButton!=null )
			removeListeners((JComponent)saveButton);
		saveButton=null;
		
		if( timbroButton!=null )
			removeListeners((JComponent)timbroButton);
		timbroButton=null;
		
		if( signButton!=null )
			removeListeners((JComponent)signButton);
		signButton=null;
		
		if( markButton!=null )
			removeListeners((JComponent)markButton);
		markButton=null;
		
		if( signMarkButton!=null )
			removeListeners((JComponent)signMarkButton);
		signMarkButton=null;
		
		if( searchButton!=null )
			removeListeners((JComponent)searchButton);
		searchButton=null;
		
		if( docPropButton!=null )
			removeListeners((JComponent)docPropButton);
		docPropButton=null;
		
		if( infoButton!=null )
			removeListeners((JComponent)infoButton);
		infoButton=null;
		
		if( inviaLogButton!=null )
			removeListeners((JComponent)inviaLogButton);
		inviaLogButton=null;
		
		if(topButtons!=null){
			removeListeners( topButtons);
			topButtons.removeAll();
			
		}
		
		if(navButtons!=null){
			removeListeners( navButtons);
			navButtons.removeAll();
		}
		
		if(comboBoxBar!=null){
			removeListeners( comboBoxBar );
			comboBoxBar.removeAll();
		}
		
		if(navToolBar!=null){
			removeListeners( navToolBar );
			navToolBar.removeAll();
		}
		
		if(pagesToolBar!=null){
			removeListeners( pagesToolBar);
			pagesToolBar.removeAll();
		}
		
		if(cursor!=null){
			//removeListeners( cursor );
			cursor.removeAll();
		}
	}
	
	private void addListnerNavOptionPanel(){
		if(!hasListener){
			hasListener=true;
			navOptionsPanel.addMouseListener(new MouseListener(){

				public void mouseClicked(MouseEvent mouseEvent) {
					handleTabbedPanes();
				}

				public void mousePressed(MouseEvent mouseEvent) {}

				public void mouseReleased(MouseEvent mouseEvent) {}

				public void mouseEntered(MouseEvent mouseEvent) {}

				public void mouseExited(MouseEvent mouseEvent) {}
			});

		}
	}

	public JTree getSignaturesTree() {
		return signaturesTree;
	}

	public void setSignaturesTree(JTree signaturesTree) {
		this.signaturesTree = signaturesTree;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	

}