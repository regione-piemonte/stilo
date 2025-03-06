package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.preferences.PropertiesFile;
import it.eng.hybrid.module.jpedal.ui.CheckNode;
import it.eng.hybrid.module.jpedal.ui.Display;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.util.SwingWorker;
import it.eng.hybrid.module.jpedal.viewer.Viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import org.w3c.dom.NodeList;


public class SwingProperties extends JPanel {
	
	Map reverseMessage =new HashMap();

	//Array of menu tabs.
	String[] menuTabs = {"ShowMenubar","ShowButtons","ShowDisplayoptions", "ShowNavigationbar", "ShowSidetabbar"};

	String propertiesLocation = "";
	
	PropertiesFile properties = null;
	PreferenceManager preferenceManager = null;

	//Window Components
	JFrame propertiesDialog;
	
	JButton confirm = new JButton(Messages.getMessage("PdfCustomGui.Save"));

	JButton cancel = new JButton(Messages.getMessage("PdfPreferences.Cancel"));

	JTabbedPane tabs = new JTabbedPane();

	//Settings Fields Components

	//DPI viewer value
	JTextField resolution;

	//Search window display style
	JComboBox searchStyle;

	//Show border around page
	JCheckBox border;

	//Show download window
	JCheckBox downloadWindow;

	//Use Hi Res Printing
	JCheckBox HiResPrint;

	//Use Hi Res Printing
	JCheckBox constantTabs;
	
	//Use enhanced viewer
	JCheckBox enhancedViewer;
	
	//Use enhanced viewer
	JCheckBox enhancedFacing;
	
	//Use enhanced viewer
	JCheckBox thumbnailScroll;
	
	//Use enhanced user interface
	JCheckBox enhancedGUI;
	
	//Use right click functionality
	JCheckBox rightClick;

    //Allow scrollwheel zooming
    JCheckBox scrollwheelZoom;

	//perform automatic update check
	JCheckBox update = new JCheckBox(Messages.getMessage("PdfPreferences.CheckForUpdate"));

	//max no of multiviewers
	JTextField maxMultiViewers;

	//inset value
	JTextField pageInsets;
	JLabel pageInsetsText;

    //window title
    JTextField windowTitle;
    JLabel windowTitleText;

	//icons Location
	JTextField iconLocation;
	JLabel iconLocationText;

    //Printer blacklist
	JTextField printerBlacklist;
	JLabel printerBlacklistText;

    //Default printer
    JComboBox defaultPrinter;
    JLabel defaultPrinterText;

    //Default pagesize
    JComboBox defaultPagesize;
    JLabel defaultPagesizeText;

    //Default resolution
    JTextField defaultDPI;
    JLabel defaultDPIText;

	JTextField pageFlowPages;
	JLabel pageFlowPagesText;
	
	JTextField sideTabLength;
	JLabel sideTabLengthText;
	
	JTextField pageFlowCache;
	JLabel pageFlowCacheText;
	
	JTextField pageFlowReflectionHeight;
	JLabel pageFlowReflectionHeightText;
	
	JTextField pageFlowSideSize;
	JLabel pageFlowSideSizeText;
	
	JCheckBox pageFlowReflection = new JCheckBox("Show PageFlow reflection");

    //Use parented hinting functions
    JCheckBox useHinting;

	//Set autoScroll when mouse at the edge of page
	JCheckBox autoScroll;

	//Set whether to prompt user on close
	JCheckBox confirmClose;

	//Set if we should open the file at the last viewed page
	JCheckBox openLastDoc;

	//Set default page layout
	JComboBox pageLayout = new JComboBox(new String[]{"Single Page","Continuous","Continuous Facing", "Facing", "PageFlow"});

	//Speech Options
    JComboBox voiceSelect;

    JLabel proxyHostLabel;
    JLabel proxyPortLabel;
    JLabel proxyUserLabel;
    JLabel proxyPasswordLabel;
    
    JTextField proxyHost;
    JTextField proxyPort;
    JTextField proxyUser;
    JTextField proxyPassword;
    
    JLabel appendModeLabel;
    JLabel acro6LayerLabel;
    JLabel renderModeLabel;
    JLabel certifyModeLabel;
    JLabel l2FontSizeLabel;
    JLabel l2TextLayerLabel;
    JLabel l4TextLayerLabel;
    JLabel imgPathLabel;
    JLabel bgImgPathLabel;
    JLabel bgImgScaleLabel;
    JLabel hashAlgorithmLabel;
    JLabel outputFileNameLabel;
    JLabel tsaUrlLabel;
    JLabel tsaUserLabel;
    JLabel tsaPasswordLabel;
    
    JCheckBox appendMode;
    JCheckBox acro6Layer;
    JTextField hashAlgorithm;
    JTextField renderMode;
    JTextField certifyMode;
    JTextField l2FontSizeLayer;
    JTextField l2TextLayer;
    JTextField l4TextLayer;
    JTextField imgPath;
    JTextField bgImgPath;
    JTextField bgImgScale;
    JTextField outputFileName;
    JTextField tsaUrl;
    JTextField tsaUser;
    JTextField tsaPassword;
   	
	JPanel highlightBoxColor = new JPanel();
	JPanel highlightTextColor = new JPanel();
	JPanel viewBGColor = new JPanel();
	JPanel pdfDecoderBackground = new JPanel();
//	JPanel sideBGColor = new JPanel();
	JPanel foreGroundColor = new JPanel();
	JCheckBox invertHighlight = new JCheckBox("Highlight Inverts Page");
	JCheckBox replaceDocTextCol = new JCheckBox("Replace Document Text Colors");
	JCheckBox replaceDisplayBGCol = new JCheckBox("Replace Display Background Color");
	
	JCheckBox changeTextAndLineArt = new JCheckBox("Change Color of Text and Line art");
	JCheckBox showMouseSelectionBox = new JCheckBox("Show Mouse Selection Box");
	JTextField highlightComposite = new JTextField(String.valueOf(PdfDecoder.highlightComposite));
	
//	private SwingGUI swingGUI;

	private Container parent;

	private boolean preferencesSetup=false;

	private JButton clearHistory;

	private JLabel historyClearedLabel;

	//Only allow numerical input to the field
	KeyListener numericalKeyListener = new KeyListener(){

		boolean consume = false;

		public void keyPressed(KeyEvent e) {
			consume = false;
			if((e.getKeyChar()<'0' || e.getKeyChar()>'9') && (e.getKeyCode()!=8 || e.getKeyCode()!=127))
				consume = true;
		}

		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {
			if(consume)
				e.consume();
		}

	};
	
	/**
	 * showPreferenceWindow()
	 *
	 * Ensure current values are loaded then display window.
	 * @param swingGUI 
	 */
	public void showPreferenceWindow(SwingGUI swingGUI){

		preferenceManager = swingGUI.getCommonValues().getPreferenceManager();
		
		if(parent instanceof JFrame){}
//			propertiesDialog = new JFrame(((JFrame)parent));
		else
			propertiesDialog = new JFrame();

        propertiesDialog.setAlwaysOnTop(true);

        if(!preferencesSetup){
			preferencesSetup=true;

			createPreferenceWindow(swingGUI);
		}

//        if(properties.getValue("readOnly").toLowerCase().equals("true")){
//			JOptionPane.showMessageDialog(
//					this,
//					"You do not have permission alter jPedal properties.\n"+
//					"Access to the properties window has therefore been disabled.",
//					"Can not write to properties file", JOptionPane.INFORMATION_MESSAGE);
//		}
//
//		
//		if(properties.isReadOnly()){
//			JOptionPane.showMessageDialog(
//					this, 
//					"Current properties file is read only.\n" +
//					"Any alteration can only be saved as another properties file.", 
//					"Properties file is read only", JOptionPane.INFORMATION_MESSAGE);
//			confirm.setEnabled(false);
//		}else{
//			confirm.setEnabled(true);
//		}
        confirm.setEnabled(true);
		
		//this.swingGUI = swingGUI;
		propertiesDialog.setLocationRelativeTo(parent);
		propertiesDialog.setVisible(true);
	}

	private void saveGUIPreferences(SwingGUI gui){
		Component[] components = tabs.getComponents();
		for(int i=0; i!=components.length; i++){
			if(components[i] instanceof JPanel){
				Component[] panelComponets = ((JPanel)components[i]).getComponents();
				for(int j=0; j!=panelComponets.length; j++){
					if (panelComponets[j] instanceof JScrollPane) {
						Component[] scrollComponents = ((JScrollPane)panelComponets[j]).getComponents();
						for(int k=0; k!=scrollComponents.length; k++){
							if(scrollComponents[k] instanceof JViewport){
								Component[] viewportComponents = ((JViewport)scrollComponents[k]).getComponents();
								for(int l=0; l!=viewportComponents.length; l++){
									if(viewportComponents[l] instanceof JTree){
										JTree tree = ((JTree)viewportComponents[l]);
										CheckNode root = (CheckNode)tree.getModel().getRoot();
										if(root.getChildCount()>0){
											saveMenuPreferencesChildren(root, gui);
										}
									}
								}
							}
							
						}
					}
					if(panelComponets[j] instanceof JButton){
						JButton tempButton = ((JButton)panelComponets[j]);
						String value = ((String)reverseMessage.get(tempButton.getText().substring((Messages.getMessage("PdfCustomGui.HideGuiSection")+ ' ').length())));
						if(tempButton.getText().startsWith(Messages.getMessage("PdfCustomGui.HideGuiSection")+ ' ')){
							properties.setValue(value, "true");
							gui.alterProperty(value, true);
						}else{
							properties.setValue(value, "false");
							gui.alterProperty(value, false);
						}
					}
				}
			}
		}
	}

	private void saveMenuPreferencesChildren(CheckNode root, SwingGUI gui){
		for(int i=0; i!=root.getChildCount(); i++){
			CheckNode node = (CheckNode)root.getChildAt(i);
			String value = ((String)reverseMessage.get(node.getText()));
			if(node.isSelected()){
				properties.setValue(value, "true");
				gui.alterProperty(value, true);
			}else{
				properties.setValue(value, "false");
				gui.alterProperty(value, false);
			}

			if(node.getChildCount()>0){
				saveMenuPreferencesChildren(node, gui);
			}
		}
	}

	/**
	 * createPreferanceWindow(final GUI gui)
	 * Set up all settings fields then call the required methods to build the window
	 * 
	 * @param gui - Used to allow any changed settings to be saved into an external properties file.
	 * 
	 */
	private void createPreferenceWindow(final SwingGUI gui){
		
		//Get Properties file containing current preferences
		properties = gui.getProperties();
		//Get Properties file location
		propertiesLocation = gui.getPropertiesFileLocation();
		//LogWriter.writeLog("propertiesLocation " + propertiesLocation);
		
		//Set window title
		propertiesDialog.setTitle(Messages.getMessage("PdfPreferences.windowTitle"));
		
		update.setToolTipText(Messages.getMessage("PdfPreferences.update.toolTip"));
		invertHighlight.setText(Messages.getMessage("PdfPreferences.InvertHighlight"));
		showMouseSelectionBox.setText(Messages.getMessage("PdfPreferences.ShowSelectionBow"));
		invertHighlight.setToolTipText(Messages.getMessage("PdfPreferences.invertHighlight.toolTip"));
		showMouseSelectionBox.setToolTipText(Messages.getMessage("PdfPreferences.showMouseSelection.toolTip"));
		highlightBoxColor.setToolTipText(Messages.getMessage("PdfPreferences.highlightBox.toolTip"));
		highlightTextColor.setToolTipText(Messages.getMessage("PdfPreferences.highlightText.toolTip"));

        //@kieran
        //@removed by Mark as always misused
		//Set up the properties window gui components
		String propValue = properties.getValue("resolution");
		if(propValue.length()>0)
			resolution = new JTextField(propValue);
		else
			resolution = new JTextField(72);
		resolution.setToolTipText(Messages.getMessage("PdfPreferences.resolutionInput.toolTip"));

        propValue = properties.getValue("maxmultiviewers");
		if(propValue.length()>0)
			maxMultiViewers = new JTextField(propValue);
		else
			maxMultiViewers = new JTextField(20);
		maxMultiViewers.setToolTipText(Messages.getMessage("PdfPreferences.maxMultiViewer.toolTip"));

		searchStyle = new JComboBox(
				new String[]{Messages.getMessage("PageLayoutViewMenu.WindowSearch"),
						Messages.getMessage("PageLayoutViewMenu.TabbedSearch"),
						Messages.getMessage("PageLayoutViewMenu.MenuSearch")
						});
		searchStyle.setToolTipText(Messages.getMessage("PdfPreferences.searchStyle.toolTip"));
		
		pageLayout = new JComboBox(
				new String[]{Messages.getMessage("PageLayoutViewMenu.SinglePage"),
						Messages.getMessage("PageLayoutViewMenu.Continuous"),
						Messages.getMessage("PageLayoutViewMenu.Facing"),
						Messages.getMessage("PageLayoutViewMenu.ContinousFacing"),
						Messages.getMessage("PageLayoutViewMenu.PageFlow")});
		pageLayout.setToolTipText(Messages.getMessage("PdfPreferences.pageLayout.toolTip"));
		
		pageInsetsText = new JLabel(Messages.getMessage("PdfViewerViewMenu.pageInsets"));
		pageInsets = new JTextField();
		pageInsets.setToolTipText(Messages.getMessage("PdfPreferences.pageInsets.toolTip"));

        windowTitleText = new JLabel(Messages.getMessage("PdfCustomGui.windowTitle"));
        windowTitle = new JTextField();
        windowTitle.setToolTipText(Messages.getMessage("PdfPreferences.windowTitle.toolTip"));
		
		iconLocationText = new JLabel(Messages.getMessage("PdfViewerViewMenu.iconLocation"));
		iconLocation = new JTextField();
		iconLocation.setToolTipText(Messages.getMessage("PdfPreferences.iconLocation.toolTip"));

        defaultDPIText = new JLabel(Messages.getMessage("PdfViewerPrint.defaultDPI"));
        defaultDPI = new JTextField();
        defaultDPI.setToolTipText(Messages.getMessage("PdfPreferences.defaultDPI.toolTip"));
		
		pageFlowCacheText = new JLabel(Messages.getMessage("PdfCustomGui.PageFlowCache"));
		pageFlowCache = new JTextField();
		pageFlowCache.setToolTipText(Messages.getMessage("PdfPreferences.pageFlowCache.toolTip"));
		
		sideTabLengthText = new JLabel(Messages.getMessage("PdfCustomGui.SideTabLength"));
		sideTabLength = new JTextField();
		sideTabLength.setToolTipText(Messages.getMessage("PdfPreferences.sideTabLength.toolTip"));
		
		pageFlowPagesText = new JLabel(Messages.getMessage("PdfCustomGui.PageFlowPages"));
		pageFlowPages = new JTextField();
		pageFlowPages.setToolTipText(Messages.getMessage("PdfPreferences.pageFlowPages.toolTip"));
		
		pageFlowReflectionHeightText = new JLabel(Messages.getMessage("PdfCustomGui.PageFlowReflectionHeight"));
		pageFlowReflectionHeight = new JTextField();
		pageFlowReflectionHeight.setToolTipText(Messages.getMessage("PdfPreferences.pageFlowReflectionHeight.toolTip"));
		
		pageFlowSideSizeText = new JLabel(Messages.getMessage("PdfCustomGui.PageFlowSideSize"));
		pageFlowSideSize = new JTextField();
		pageFlowSideSize.setToolTipText(Messages.getMessage("PdfPreferences.pageFlowSideSize.toolTip"));
		
		pageFlowReflection = new JCheckBox(Messages.getMessage("PdfCustomGui.PageFlowReflection"));
		pageFlowReflection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pageFlowReflectionHeightText.setEnabled(((JCheckBox)e.getSource()).isSelected());
				pageFlowReflectionHeight.setEnabled(((JCheckBox)e.getSource()).isSelected());
			}
		});
		pageFlowReflection.setToolTipText(Messages.getMessage("PdfPreferences.pageFlowReflection.toolTip"));

        useHinting = new JCheckBox(Messages.getMessage("PdfCustomGui.useHinting"));
//		useHinting.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//                if (useHinting.isSelected()) {
//                    JOptionPane.showMessageDialog(null,Messages.getMessage("PdfCustomGui.patentedHintingMessage"));
//                }
//			}
//		});
        useHinting.setToolTipText(Messages.getMessage("PdfPreferences.useHinting.toolTip"));
		
		autoScroll = new JCheckBox(Messages.getMessage("PdfViewerViewMenuAutoscrollSet.text"));
		autoScroll.setToolTipText("Set if autoscroll should be enabled / disabled");

        confirmClose = new JCheckBox(Messages.getMessage("PfdViewerViewMenuConfirmClose.text"));
        confirmClose.setToolTipText("Set if we should confirm closing the viewer");
		
		openLastDoc = new JCheckBox(Messages.getMessage("PdfViewerViewMenuOpenLastDoc.text"));
		openLastDoc.setToolTipText("Set if last document should be opened upon start up");
		
		border = new JCheckBox(Messages.getMessage("PageLayoutViewMenu.Borders_Show"));
		border.setToolTipText("Set if we should display a border for the page");
		
		downloadWindow = new JCheckBox(Messages.getMessage("PageLayoutViewMenu.DownloadWindow_Show"));
		downloadWindow.setToolTipText("Set if the download window should be displayed");
		
		HiResPrint = new JCheckBox(Messages.getMessage("Printing.HiRes"));
		HiResPrint.setToolTipText("Set if hi res printing should be enabled / disabled");
		
		constantTabs = new JCheckBox(Messages.getMessage("PdfCustomGui.consistentTabs"));
		constantTabs.setToolTipText("Set to keep sidetabs consistant between files");
		
		enhancedViewer = new JCheckBox(Messages.getMessage("PdfCustomGui.enhancedViewer"));
		enhancedViewer.setToolTipText("Set to use enahnced viewer mode");
		
		enhancedFacing = new JCheckBox(Messages.getMessage("PdfCustomGui.enhancedFacing"));
		enhancedFacing.setToolTipText("Set to turn facing mode to page turn mode");
		
		thumbnailScroll = new JCheckBox(Messages.getMessage("PdfCustomGui.thumbnailScroll"));
		thumbnailScroll.setToolTipText("Set to show thumbnail whilst scrolling");
		
		enhancedGUI = new JCheckBox(Messages.getMessage("PdfCustomGui.enhancedGUI"));
		enhancedGUI.setToolTipText("Set to enabled the enhanced gui");

        rightClick = new JCheckBox(Messages.getMessage("PdfCustomGui.allowRightClick"));
		rightClick.setToolTipText("Set to enable / disable the right click functionality");

        scrollwheelZoom = new JCheckBox(Messages.getMessage("PdfCustomGui.allowScrollwheelZoom"));
		scrollwheelZoom.setToolTipText("Set to enable zooming when scrolling with ctrl pressed");

		historyClearedLabel = new JLabel(Messages.getMessage("PageLayoutViewMenu.HistoryCleared"));
		historyClearedLabel.setForeground(Color.red);
		historyClearedLabel.setVisible(false);
		clearHistory = new JButton(Messages.getMessage("PageLayoutViewMenu.ClearHistory"));
		clearHistory.setToolTipText("Clears the history of previous files");
		clearHistory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gui.clearRecentDocuments();

				SwingWorker searcher = new SwingWorker() {
					public Object construct() {
						for (int i = 0; i < 6; i++) {
							historyClearedLabel.setVisible(!historyClearedLabel.isVisible());
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
							}
						}
						return null;
					}
				};

				searcher.start();
			}
		});
//		JButton save = new JButton(Messages.getMessage("PdfPreferences.SaveAs"));
//		save.setToolTipText("Save preferences in a new file");
		JButton reset = new JButton(Messages.getMessage("PdfPreferences.ResetToDefault"));
		reset.setToolTipText("Reset  and save preferences to program defaults");
		reset.setVisible( false );
		
		//Create JFrame
		propertiesDialog.getContentPane().setLayout(new BorderLayout());
		propertiesDialog.getContentPane().add(this,BorderLayout.CENTER);
		propertiesDialog.pack();
        if (PdfDecoder.isRunningOnMac)
		    propertiesDialog.setSize(600, 475);
        else
		    propertiesDialog.setSize(650, 450);

        confirm.setText(Messages.getMessage("PdfCustomGui.Save"));
    	cancel.setText(Messages.getMessage("PdfPreferences.Cancel"));
    	
		/*
		 * Listeners that are reqired for each setting field
		 */
		//Set properties and close the window
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setPreferences(gui);
                //if(Viewer.showMessages)
				//JOptionPane.showMessageDialog(null, Messages.getMessage("PdfPreferences.savedTo")+propertiesLocation+ '\n' +Messages.getMessage("PdfPreferences.restart"), "Restart Jpedal", JOptionPane.INFORMATION_MESSAGE);
				propertiesDialog.setVisible(false);
			}
		});
		confirm.setToolTipText("Save the preferences in the current loaded preferences file");
		//Close the window, don't save the properties
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				propertiesDialog.setVisible(false);
			}
		});
		cancel.setToolTipText("Leave preferences window without saving changes");
//		Save the properties into a new file
//		save.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				//The properties file used when jpedal opened
//				String lastProperties = gui.getPropertiesFileLocation();
//				
//				JFileChooser fileChooser = new JFileChooser();
//				
//				int i = fileChooser.showSaveDialog(propertiesDialog);
//				
//				if(i == JFileChooser.CANCEL_OPTION){
//					//Do nothing
//				}else if(i== JFileChooser.ERROR_OPTION){
//					//Do nothing
//				}else if(i == JFileChooser.APPROVE_OPTION){
//					File f = fileChooser.getSelectedFile();
//
//					if(f.exists())
//						f.delete();
//					
//					//Setup properties in the new location
//					gui.setPropertiesFileLocation(f.getAbsolutePath());
//					setPreferences(gui);
//				}
//				//Reset to the properties file used when jpedal opened
//				gui.setPropertiesFileLocation(lastProperties);
//			}
//		});
		//Reset the properties to JPedal defaults
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(propertiesDialog, Messages.getMessage("PdfPreferences.reset") , "Reset to Default", JOptionPane.YES_NO_OPTION);
				//The properties file used when jpedal opened
				if(result == JOptionPane.YES_OPTION){
					
					
//					String lastProperties = gui.getPropertiesFileLocation();

//					File f = new File(lastProperties);
//					if(f.exists()){
//						f.delete();
////						System.exit(1);
//					}
//
//					gui.getProperties().loadProperties(lastProperties);

                    if(Viewer.showMessages)
					JOptionPane.showMessageDialog(propertiesDialog, Messages.getMessage("PdfPreferences.restart"));
					propertiesDialog.setVisible(false);
				}
			}
		});
		
		
		highlightComposite.addKeyListener(new KeyListener(){

			boolean consume = false;

			public void keyPressed(KeyEvent e) {
				consume = false;
				if((((JTextField) e.getSource()).getText().contains(".") && e.getKeyChar()=='.') &&
						((e.getKeyChar()<'0' || e.getKeyChar()>'9') && (e.getKeyCode()!=8 || e.getKeyCode()!=127)))
					consume = true;
			}

			public void keyReleased(KeyEvent e) {}

			public void keyTyped(KeyEvent e) {
				if(consume)
					e.consume();
			}
			
		});
		highlightComposite.setToolTipText("Set the transparency of the highlight");
		
		resolution.addKeyListener(numericalKeyListener);
		maxMultiViewers.addKeyListener(numericalKeyListener);
		
		/**
		 * Set the current properties from the properties file
		 */
		setLayout(new BorderLayout());

//		JButtonBar toolbar = new JButtonBar(JButtonBar.VERTICAL);
		JPanel toolbar = new JPanel();
		
		BoxLayout layout = new BoxLayout(toolbar, BoxLayout.Y_AXIS);
		toolbar.setLayout(layout);

		//if(PdfDecoder.isRunningOnMac)
		//	toolbar.setPreferredSize(new Dimension(120,0));

		add(new ButtonBarPanel(toolbar), BorderLayout.CENTER);

		//toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		Dimension dimension = new Dimension(5,40);
		Box.Filler filler = new Box.Filler(dimension, dimension, dimension);

		confirm.setPreferredSize(cancel.getPreferredSize());

		if(properties.isReadOnly())
			confirm.setEnabled(false);
		else{
			confirm.setEnabled(true);
		}
		
		buttonPanel.add(reset);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(confirm);
		//buttonPanel.add(save);
		getRootPane().setDefaultButton(confirm);

		buttonPanel.add(filler);
		buttonPanel.add(cancel);
		buttonPanel.add(filler);

		//buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void setPreferences(SwingGUI gui){
		PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		try {
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_APPENDMODE, appendMode.isSelected());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER, acro6Layer.isSelected());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE, l2FontSizeLayer.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L2TEXT, l2TextLayer.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L4TEXT, l4TextLayer.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_IMGPATH, imgPath.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_BGIMGPATH, bgImgPath.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE, bgImgScale.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAURL, tsaUrl.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAUSER, tsaUser.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD, tsaPassword.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM, hashAlgorithm.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_RENDERMODE, renderMode.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE, certifyMode.getText());
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_OUTPUTFILENAME, outputFileName.getText());
			
			preferenceManager.reinitConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int borderStyle = 0;
		int pageMode = (pageLayout.getSelectedIndex()+1);
		if(pageMode<Display.SINGLE_PAGE || pageMode>Display.PAGEFLOW)
			pageMode = Display.SINGLE_PAGE;
		if(border.isSelected()){
			borderStyle = 1;
		}
		
		int hBox = highlightBoxColor.getBackground().getRGB();
		int hText = highlightTextColor.getBackground().getRGB();
		int vbg = viewBGColor.getBackground().getRGB();
		int pbg = pdfDecoderBackground.getBackground().getRGB();
		int vfg = foreGroundColor.getBackground().getRGB();
//		int sbbg = sideBGColor.getBackground().getRGB();
		boolean changeTL = changeTextAndLineArt.isSelected();
		boolean isInvert = invertHighlight.isSelected();
		boolean replaceTextColors = replaceDocTextCol.isSelected();
		boolean replacePdfDisplayBackground = replaceDisplayBGCol.isSelected();
		boolean isBoxShown = showMouseSelectionBox.isSelected();
		
		/**
		 * set preferences from all but menu options
		 */
		properties.setValue("borderType", String.valueOf(borderStyle));
        properties.setValue("useHinting", String.valueOf(useHinting.isSelected()));
		properties.setValue("pageMode", String.valueOf(pageMode));
		properties.setValue("pageInsets", String.valueOf(pageInsets.getText()));
        properties.setValue("windowTitle", String.valueOf(windowTitle.getText()));
		String loc = iconLocation.getText();
		if(!loc.endsWith("/") && !loc.endsWith("\\"))
			loc = loc+ '/';
		properties.setValue("iconLocation", String.valueOf(loc));
		properties.setValue("pageFlowPages", String.valueOf(pageFlowPages.getText()));
		properties.setValue("pageFlowExtraCache", String.valueOf(pageFlowCache.getText()));
		properties.setValue("pageFlowReflection", String.valueOf(pageFlowReflection.isSelected()));
		properties.setValue("pageFlowSideSize", String.valueOf(pageFlowSideSize.getText()));
		properties.setValue("pageFlowReflectionHeight", String.valueOf(pageFlowReflectionHeight.getText()));
		properties.setValue("sideTabBarCollapseLength", String.valueOf(sideTabLength.getText()));
		properties.setValue("autoScroll", String.valueOf(autoScroll.isSelected()));
        properties.setValue("confirmClose", String.valueOf(confirmClose.isSelected()));
		properties.setValue("openLastDocument", String.valueOf(openLastDoc.isSelected()));
		properties.setValue("resolution", String.valueOf(resolution.getText()));
		properties.setValue("searchWindowType", String.valueOf(searchStyle.getSelectedIndex()));
		properties.setValue("automaticupdate", String.valueOf(update.isSelected()));
		properties.setValue("maxmultiviewers", String.valueOf(maxMultiViewers.getText()));
		properties.setValue("showDownloadWindow", String.valueOf(downloadWindow.isSelected()));
		properties.setValue("useHiResPrinting", String.valueOf(HiResPrint.isSelected()));
		properties.setValue("consistentTabBar", String.valueOf(constantTabs.isSelected()));
		properties.setValue("highlightComposite", String.valueOf(highlightComposite.getText()));
		properties.setValue("highlightBoxColor", String.valueOf(hBox));
		properties.setValue("highlightTextColor", String.valueOf(hText));
		properties.setValue("vbgColor", String.valueOf(vbg));
		properties.setValue("pdfDisplayBackground", String.valueOf(pbg));
		properties.setValue("vfgColor", String.valueOf(vfg));
		properties.setValue("replaceDocumentTextColors", String.valueOf(replaceTextColors));
		properties.setValue("replacePdfDisplayBackground", String.valueOf(replacePdfDisplayBackground));
//		properties.setValue("sbbgColor", String.valueOf(sbbg));
		properties.setValue("changeTextAndLineart", String.valueOf(changeTL));
		properties.setValue("invertHighlights", String.valueOf(isInvert));
		properties.setValue("showMouseSelectionBox", String.valueOf(isBoxShown));
		properties.setValue("allowRightClick", String.valueOf(rightClick.isSelected()));
		properties.setValue("allowScrollwheelZoom", String.valueOf(scrollwheelZoom.isSelected()));
		properties.setValue("enhancedViewerMode", String.valueOf(enhancedViewer.isSelected()));
		properties.setValue("enhancedFacingMode", String.valueOf(enhancedFacing.isSelected()));
		properties.setValue("previewOnSingleScroll", String.valueOf(thumbnailScroll.isSelected()));
		properties.setValue("enhancedGUI", String.valueOf(enhancedGUI.isSelected()));
        //properties.setValue("printerBlacklist", String.valueOf(printerBlacklist.getText()));
//        if (((String)defaultPrinter.getSelectedItem()).startsWith("System Default"))
//            properties.setValue("defaultPrinter", "");
//        else
//            properties.setValue("defaultPrinter", String.valueOf(defaultPrinter.getSelectedItem()));
//        properties.setValue("defaultDPI", String.valueOf(defaultDPI.getText()));
//        properties.setValue("defaultPagesize", String.valueOf(defaultPagesize.getSelectedItem()));
		//Save all options found in a tree
		//saveGUIPreferences(gui);
	}

	class ButtonBarPanel extends JPanel {

		private Component currentComponent;
		
//		Switch between idependent and properties dependent 
		//private boolean newPreferencesCode = true;

		public ButtonBarPanel(JPanel toolbar) {
			setLayout(new BorderLayout());
			
			//Add scroll pane as too many options
			JScrollPane jsp = new JScrollPane();
			jsp.getViewport().add(toolbar);
			jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			
			add(jsp, BorderLayout.WEST);
			
			ButtonGroup group = new ButtonGroup();

            addButton(Messages.getMessage("PdfPreferences.NetworkTitle"), "/it/eng/hybrid/module/jpedal/resources/interface.png", createNetworkSettings(), toolbar, group);

            addButton(Messages.getMessage("PdfPreferences.SignatureTitle"), "/it/eng/hybrid/module/jpedal/resources/buttonsigner.png", createSignatureSettings(), toolbar, group);

		}

		private JPanel makePanel(String title) {
			JPanel panel = new JPanel(new BorderLayout());
			JLabel topLeft = new JLabel(title);
			topLeft.setFont(topLeft.getFont().deriveFont(Font.BOLD));
			topLeft.setOpaque(true);
			topLeft.setBackground(panel.getBackground().brighter());
			
//			JLabel topRight = new JLabel("( "+propertiesLocation+" )");
//			topRight.setOpaque(true);
//			topRight.setBackground(panel.getBackground().brighter());
			
			JPanel topbar = new JPanel(new BorderLayout());
			topbar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			topbar.setFont(topbar.getFont().deriveFont(Font.BOLD));
			topbar.setOpaque(true);
			topbar.setBackground(panel.getBackground().brighter());
			
			topbar.add(topLeft, BorderLayout.WEST);
//			topbar.add(topRight, BorderLayout.EAST);
			
			panel.add(topbar, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(400, 300));
			panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			return panel;
		}


        /*
		 * Creates a pane holding all General settings
		 */
		private JPanel createGeneralSettings(){

			/**
			 * Set values from Properties file
			 */
			String propValue = properties.getValue("resolution");
			if(propValue.length()>0)
				resolution.setText(propValue);

            propValue = properties.getValue("useHinting");
            if(propValue.length()>0 && propValue.equals("true"))
                useHinting.setSelected(true);
            else
                useHinting.setSelected(false);

            propValue = properties.getValue("autoScroll");
			if(propValue.equals("true"))
				autoScroll.setSelected(true);
			else
				autoScroll.setSelected(false);

            propValue = properties.getValue("confirmClose");
            if(propValue.equals("true"))
                confirmClose.setSelected(true);
            else
                confirmClose.setSelected(false);

            propValue = properties.getValue("automaticupdate");
			if(propValue.equals("true"))
				update.setSelected(true);
			else
				update.setSelected(false);

			propValue = properties.getValue("openLastDocument");
			if(propValue.equals("true"))
				openLastDoc.setSelected(true);
			else
				openLastDoc.setSelected(false);

			JPanel panel = makePanel(Messages.getMessage("PdfPreferences.GeneralTitle"));

			JPanel pane = new JPanel();
            JScrollPane scroll = new JScrollPane(pane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;

			c.insets = new Insets(5,0,0,5);
			c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 0;
			JLabel label = new JLabel(Messages.getMessage("PdfPreferences.GeneralSection"));
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			pane.add(label, c);

            c.gridy++;

            c.insets = new Insets(10,0,0,5);
			c.gridx = 0;
			JLabel label2 = new JLabel(Messages.getMessage("PdfViewerViewMenu.Resolution"));
			label2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pane.add(label2, c);

			c.insets = new Insets(10,0,0,0);
			c.weightx = 1;
			c.gridx = 1;
			resolution.setEnabled( !properties.isReadOnly() );
			pane.add(resolution, c);

            c.gridy++;

            c.gridwidth = 2;
            c.gridx = 0;
            useHinting.setMargin(new Insets(0,0,0,0));
            useHinting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            useHinting.setEnabled( !properties.isReadOnly() );
			pane.add(useHinting, c);

            c.gridy++;

            c.gridwidth = 2;
			c.gridx = 0;
			autoScroll.setMargin(new Insets(0,0,0,0));
			autoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			autoScroll.setEnabled( !properties.isReadOnly() );
			pane.add(autoScroll, c);

            c.gridy++;

			c.gridwidth = 2;
			c.gridx = 0;
			confirmClose.setMargin(new Insets(0,0,0,0));
			confirmClose.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			confirmClose.setEnabled( !properties.isReadOnly() );
			pane.add(confirmClose, c);

            c.gridy++;

            c.insets = new Insets(15,0,0,5);
			c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label3 = new JLabel(Messages.getMessage("PdfPreferences.StartUp"));
			label3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label3.setFont(label3.getFont().deriveFont(Font.BOLD));
			pane.add(label3, c);

            c.gridy++;

            c.insets = new Insets(10,0,0,0);
            c.weighty = 0;
            c.weightx = 1;
            c.gridwidth = 2;
            c.gridx = 0;
            update.setMargin(new Insets(0,0,0,0));
            update.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            update.setEnabled( !properties.isReadOnly() );
			pane.add(update, c);

            c.gridy++;

			c.gridwidth = 2;
			c.gridx = 0;
			openLastDoc.setMargin(new Insets(0,0,0,0));
			openLastDoc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			openLastDoc.setEnabled( !properties.isReadOnly() );
			pane.add(openLastDoc, c);

            c.gridy++;

            c.gridwidth = 2;
			c.gridx = 0;
			JPanel clearHistoryPanel = new JPanel();
			clearHistoryPanel.setLayout(new BoxLayout(clearHistoryPanel, BoxLayout.X_AXIS));
			clearHistoryPanel.add(clearHistory);
			clearHistoryPanel.add(Box.createHorizontalGlue());

			clearHistoryPanel.add(historyClearedLabel);
			clearHistoryPanel.add(Box.createHorizontalGlue());
			clearHistory.setEnabled( !properties.isReadOnly() );
			pane.add(clearHistoryPanel, c);

            c.gridy++;

			c.weighty = 1;
			c.gridx = 0;
			pane.add(Box.createVerticalGlue(), c);

			panel.add(scroll, BorderLayout.CENTER);

			return panel;
		}


        /*
		 * Creates a pane holding all Page Display settings (e.g Insets, borders, display modes, etc)
		 */
		private JPanel createPageDisplaySettings(){

			/**
			 * Set values from Properties file
			 */
			String propValue = properties.getValue("enhancedViewerMode");
			if(propValue.length()>0 && propValue.equals("true"))
				enhancedViewer.setSelected(true);
			else
				enhancedViewer.setSelected(false);

            propValue = properties.getValue("borderType");
            if(propValue.length()>0)
                if(Integer.parseInt(propValue)==1)
                    border.setSelected(true);
                else
                    border.setSelected(false);

			propValue = properties.getValue("pageInsets");
			if(propValue!=null && propValue.length() != 0)
				pageInsets.setText(propValue);
			else
				pageInsets.setText("25");

            propValue = properties.getValue("pageMode");
			if(propValue.length()>0){
				int mode = Integer.parseInt(propValue);
				if(mode<Display.SINGLE_PAGE || mode>Display.PAGEFLOW)
					mode = Display.SINGLE_PAGE;

				pageLayout.setSelectedIndex(mode-1);
			}

			propValue = properties.getValue("enhancedFacingMode");
			if(propValue.length()>0 && propValue.equals("true"))
				enhancedFacing.setSelected(true);
			else
				enhancedFacing.setSelected(false);
			
			propValue = properties.getValue("previewOnSingleScroll");
			if(propValue.length()>0 && propValue.equals("true"))
				thumbnailScroll.setSelected(true);
			else
				thumbnailScroll.setSelected(false);

			propValue = properties.getValue("pageFlowPages");
			if(propValue!=null && propValue.length() != 0)
				pageFlowPages.setText(propValue);
			else
				pageFlowPages.setText("8");

			propValue = properties.getValue("pageFlowExtraCache");
			if(propValue!=null && propValue.length() != 0)
				pageFlowCache.setText(propValue);
			else
				pageFlowCache.setText("10");

			propValue = properties.getValue("pageFlowSideSize");
			if(propValue!=null && propValue.length() != 0)
				pageFlowSideSize.setText(propValue);
			else
				pageFlowSideSize.setText("0.75");

			propValue = properties.getValue("pageFlowReflection");
			if(propValue.equals("true")){
				pageFlowReflection.setSelected(true);
				pageFlowReflectionHeight.setEnabled(true);
				pageFlowReflectionHeightText.setEnabled(true);
			}else{
				pageFlowReflection.setSelected(false);
				pageFlowReflectionHeight.setEnabled(false);
				pageFlowReflectionHeightText.setEnabled(false);
			}

			propValue = properties.getValue("pageFlowReflectionHeight");
			if(propValue!=null && propValue.length() != 0)
				pageFlowReflectionHeight.setText(propValue);
			else
				pageFlowReflectionHeight.setText("0.25");

			JPanel panel = makePanel(Messages.getMessage("PdfPreferences.PageDisplayTitle"));

			JPanel pane = new JPanel();
			JScrollPane scroll = new JScrollPane(pane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;

			c.insets = new Insets(5,0,0,5);
			c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 0;
			JLabel label = new JLabel(Messages.getMessage("PdfPreferences.GeneralSection"));
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			pane.add(label, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,0);
			c.gridwidth = 2;
			c.gridx = 0;
			enhancedViewer.setMargin(new Insets(0,0,0,0));
			enhancedViewer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			enhancedViewer.setEnabled( !properties.isReadOnly() );
			pane.add(enhancedViewer, c);

            c.gridy++;

            c.gridwidth = 2;
			c.gridx = 0;
			border.setMargin(new Insets(0,0,0,0));
			border.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			border.setEnabled( !properties.isReadOnly() );
			pane.add(border, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,0);
			c.gridwidth = 2;
			c.gridx = 0;
			pageInsetsText.setEnabled( !properties.isReadOnly() );
			pane.add(pageInsetsText, c);
			c.gridwidth = 2;
			c.gridx = 1;
			pageInsets.setEnabled( !properties.isReadOnly() );
			pane.add(pageInsets, c);

            c.gridy++;

            c.insets = new Insets(15,0,0,5);
			c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label2 = new JLabel(Messages.getMessage("PdfPreferences.DisplayModes"));
			label2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label2.setFont(label2.getFont().deriveFont(Font.BOLD));
			pane.add(label2, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,5);
			c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label1 = new JLabel(Messages.getMessage("PageLayoutViewMenu.PageLayout"));
			label1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pane.add(label1, c);

			c.insets = new Insets(5,0,0,0);
			c.weightx = 1;
			c.gridx = 1;
			pageLayout.setEnabled( !properties.isReadOnly() );
			pane.add(pageLayout, c);

            c.gridy++;

            
            c.gridwidth = 2;
            c.gridx = 0;
            thumbnailScroll.setMargin(new Insets(0,0,0,0));
            thumbnailScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            thumbnailScroll.setEnabled( !properties.isReadOnly() );
			pane.add(thumbnailScroll, c);
            
            c.gridy++;

			c.weighty = 1;
			c.gridx = 0;
			pane.add(Box.createVerticalGlue(), c);
			panel.add(scroll, BorderLayout.CENTER);

			return panel;
		}



        /*
		 * Creates a pane holding all Interface settings (e.g Search Style, icons, etc)
		 */
		private JPanel createInterfaceSettings(){

			/**
			 * Set values from Properties file
			 */
			String propValue = properties.getValue("enhancedGUI");
			if(propValue.length()>0 && propValue.equals("true"))
				enhancedGUI.setSelected(true);
            else
                enhancedGUI.setSelected(false);

            propValue = properties.getValue("allowRightClick");
            if(propValue.length()>0 && propValue.equals("true"))
                rightClick.setSelected(true);
            else
                rightClick.setSelected(false);

            propValue = properties.getValue("allowScrollwheelZoom");
            if(propValue.length()>0 && propValue.equals("true"))
                scrollwheelZoom.setSelected(true);
            else
                scrollwheelZoom.setSelected(false);

            propValue = properties.getValue("windowTitle");
            if (propValue!=null && propValue.length() != 0)
                windowTitle.setText(propValue);

            propValue = properties.getValue("iconLocation");
            if(propValue!=null && propValue.length() != 0)
                iconLocation.setText(propValue);
            else
                iconLocation.setText("/it/eng/hybrid/module/jpedal/resources/");

            propValue = properties.getValue("searchWindowType");
            if(propValue.length()>0)
                searchStyle.setSelectedIndex(Integer.parseInt(propValue));
            else
                searchStyle.setSelectedIndex(0);

            propValue = properties.getValue("maxmultiviewers");
            if (propValue != null && propValue.length()>0)
                maxMultiViewers.setText(propValue);

            propValue = properties.getValue("sideTabBarCollapseLength");
            if(propValue!=null && propValue.length() != 0)
                sideTabLength.setText(propValue);
            else
                sideTabLength.setText("30");

            propValue = properties.getValue("consistentTabBar");
            if(propValue.length()>0 && propValue.equals("true"))
                constantTabs.setSelected(true);
            else
                constantTabs.setSelected(false);
            
            String showBox = properties.getValue("showMouseSelectionBox");
            if(showBox.length()>0 && showBox.toLowerCase().equals("true"))
                showMouseSelectionBox.setSelected(true);
            else
            	showMouseSelectionBox.setSelected(false);

            JPanel panel = makePanel(Messages.getMessage("PdfPreferences.InterfaceTitle"));

            JTabbedPane tabs = new JTabbedPane();

			JPanel pane = new JPanel();
			JScrollPane scroll = new JScrollPane(pane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;

            c.insets = new Insets(5,0,0,5);
            c.gridwidth = 1;
            c.gridy = 0;
            c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label = new JLabel(Messages.getMessage("PdfPreferences.GeneralTitle"));
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			pane.add(label, c);

            c.gridy++;

			c.insets = new Insets(5,0,0,5);
            c.gridx = 0;
            c.gridwidth = 2;
            enhancedGUI.setMargin(new Insets(0,0,0,0));
            enhancedGUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            enhancedGUI.setEnabled( !properties.isReadOnly() );
			pane.add(enhancedGUI, c);

            c.gridy++;

            c.insets = new Insets(3,0,0,0);
            c.gridwidth=1;
            c.gridx=0;
            windowTitleText.setEnabled( !properties.isReadOnly() );
			pane.add(windowTitleText, c);
            c.gridx = 1;
            windowTitle.setEnabled( !properties.isReadOnly() );
			pane.add(windowTitle, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,0);
			c.gridwidth = 1;
			c.gridx = 0;
			iconLocationText.setEnabled( !properties.isReadOnly() );
			pane.add(iconLocationText, c);
			c.gridx = 1;
			iconLocation.setEnabled( !properties.isReadOnly() );
			pane.add(iconLocation, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,5);
			c.gridx = 0;
			JLabel label5 = new JLabel(Messages.getMessage("PageLayoutViewMenu.SearchLayout"));
			label5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pane.add(label5, c);

			c.insets = new Insets(5,0,0,0);
			c.weightx = 1;
			c.gridx = 1;
			searchStyle.setEnabled( !properties.isReadOnly() );
			pane.add(searchStyle, c);

            c.gridy++;

            
            c.insets = new Insets(15,0,0,5);
            c.gridwidth = 1;
            c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label1 = new JLabel(Messages.getMessage("PdfPreferences.SideTab"));
			label1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label1.setFont(label1.getFont().deriveFont(Font.BOLD));
			pane.add(label1, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,0);
			c.gridwidth = 1;
			c.gridx = 0;
			sideTabLengthText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			sideTabLengthText.setEnabled( !properties.isReadOnly() );
			pane.add(sideTabLengthText, c);

			c.insets = new Insets(5,0,0,0);
			c.weightx = 1;
			c.gridx = 1;
			sideTabLength.setEnabled( !properties.isReadOnly() );
			pane.add(sideTabLength, c);

            c.gridy++;

            c.insets = new Insets(5,0,0,0);
            c.gridwidth = 2;
			c.gridx = 0;
			constantTabs.setMargin(new Insets(0,0,0,0));
			constantTabs.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			constantTabs.setEnabled( !properties.isReadOnly() );
			pane.add(constantTabs, c);

            c.gridy++;

            c.weighty = 1;
			c.gridx = 0;
			pane.add(Box.createVerticalGlue(), c);
			//pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0.3f,0.5f,1f), 1), "Display Settings"));

            tabs.add(Messages.getMessage("PdfPreferences.AppearanceTab"), scroll);

            
            JPanel pane2 = new JPanel();
            JScrollPane scroll2 = new JScrollPane(pane2);
            scroll2.setBorder(BorderFactory.createEmptyBorder());
			pane2.setLayout(new GridBagLayout());
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;


            c.insets = new Insets(5,0,0,5);
            c.gridwidth = 1;
            c.gridy = 0;
            c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label3 = new JLabel(Messages.getMessage("PdfPreferences.GeneralTitle"));
			label3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label3.setFont(label3.getFont().deriveFont(Font.BOLD));
			pane2.add(label3, c);

            c.gridy++;

            c.gridwidth = 2;
			c.gridx = 0;
			rightClick.setMargin(new Insets(0,0,0,0));
			rightClick.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			rightClick.setEnabled( !properties.isReadOnly() );
			pane2.add(rightClick, c);

            c.gridy++;

            c.gridwidth = 2;
			c.gridx = 0;
			scrollwheelZoom.setMargin(new Insets(0,0,0,0));
			scrollwheelZoom.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			scrollwheelZoom.setEnabled( !properties.isReadOnly() );
			pane2.add(scrollwheelZoom, c);
            
            c.gridy++;

            c.insets = new Insets(0,0,0,5);
            c.gridwidth = 1;
            c.gridx = 0;
            showMouseSelectionBox.setEnabled( !properties.isReadOnly() );
			pane2.add(showMouseSelectionBox, c);

            c.gridy++;

            c.weighty = 1;
			c.gridx = 0;
			pane2.add(Box.createVerticalGlue(), c);
			//pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0.3f,0.5f,1f), 1), "Display Settings"));

            tabs.add(Messages.getMessage("PdfPreferences.Mouse"), scroll2);
            
            JPanel pane3 = new JPanel();
            JScrollPane scroll3 = new JScrollPane(pane3);
            scroll3.setBorder(BorderFactory.createEmptyBorder());
			pane3.setLayout(new GridBagLayout());
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;


            c.insets = new Insets(5,0,0,5);
            c.gridwidth = 1;
            c.gridy = 0;
            c.weighty = 0;
			c.weightx = 0;
			c.gridx = 0;
			JLabel label6 = new JLabel(Messages.getMessage("PdfPreferences.GeneralTitle"));
			label6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			label6.setFont(label6.getFont().deriveFont(Font.BOLD));
			pane3.add(label6, c);

            
			panel.add(tabs, BorderLayout.CENTER);

			return panel;
		}
		
		private JPanel createNetworkSettings(){
			
            JPanel panel = makePanel(Messages.getMessage("PdfPreferences.NetworkTitle"));

            JPanel pane = new JPanel();
			JScrollPane scroll = new JScrollPane(pane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
			pane.setLayout(new GridBagLayout());
			
            JTabbedPane tabs = new JTabbedPane();
            tabs.add(Messages.getMessage("PdfPreferences.ProxyTitle"), scroll);
            
            GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(1,5,1,5);
			
			int y=0;
			proxyHostLabel = aggiungiLabel(pane, proxyHostLabel, "proxyHostLabel", Messages.getMessage("PdfPreferences.Network.proxyHost"), c, 0, y, 1);
			
			String proxyHostProperty ="";
//			try {
//				proxyHostProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PROXY_HOST );
//			} catch (Exception e2) {
//			}
			proxyHost = aggiungiFieldTesto( pane, proxyHost, 20, "proxyHost", proxyHostProperty, true, c, 1, y++, 1);
			
			JLabel proxyPortLabel = new JLabel();
			proxyPortLabel.setText(Messages.getMessage("PdfPreferences.Network.proxyPort"));		
			proxyPortLabel.setName("proxyPortLabel");
			c.gridx = 0;
			c.gridy=1;
            c.gridwidth = 1;
			pane.add(proxyPortLabel, c);
			
			c.gridx = 1;
			c.gridy=1;
            c.gridwidth = 1;
			proxyPort = new JTextField();
			proxyPort.setEditable( true);
			proxyPort.setName("proxyPort");
			String proxyPortProperty ="";
//			try {
//				proxyPortProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PROXY_PORT );
//			} catch (Exception e2) {
//			}
			proxyPort.setText( proxyPortProperty );
			pane.add(proxyPort, c);
			
			JLabel proxyUserLabel = new JLabel();
			proxyUserLabel.setText(Messages.getMessage("PdfPreferences.Network.proxyUser"));	
			proxyUserLabel.setName("proxyUserLabel");
			c.gridx = 0;
			c.gridy=2;
            c.gridwidth = 1;
			pane.add(proxyUserLabel, c);
			
			c.gridx = 1;
			c.gridy=2;
            c.gridwidth = 1;
			proxyUser = new JTextField();
			proxyUser.setEditable( true);
			proxyUser.setName("proxyUser");
			String proxyUserProperty ="";
//			try {
//				proxyUserProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PROXY_USER );
//			} catch (Exception e2) {
//			}
			proxyUser.setText(proxyUserProperty);
			pane.add(proxyUser, c);
            
			JLabel proxyPasswordLabel = new JLabel();
			proxyPasswordLabel.setText(Messages.getMessage("PdfPreferences.Network.proxyPassword"));			
			proxyPasswordLabel.setName("proxyPasswordLabel");
			c.gridx = 0;
			c.gridy=3;
			c.gridwidth = 1;
			pane.add(proxyPasswordLabel, c);
			
			c.gridx = 1;
			c.gridy=3;
			c.gridwidth = 1;
			proxyPassword = new JPasswordField();
			proxyPassword.setEditable( true);
			proxyPassword.setName("proxyPassword");
			String proxyPasswordProperty ="";
//			try {
//				proxyPasswordProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PROXY_PASSWORD );
//			} catch (Exception e2) {
//			}
			proxyPassword.setText(proxyPasswordProperty);
			pane.add(proxyPassword, c);
           
			c.gridy++;
			c.weighty = 1;
			c.gridx = 0;
			pane.add(Box.createVerticalGlue(), c);
			
			panel.add(tabs, BorderLayout.CENTER);
			
            return panel;
		}
		
		private JPanel createSignatureSettings(){

            JPanel panel = makePanel(Messages.getMessage("PdfPreferences.SignatureTitle"));
            JTabbedPane tabs = new JTabbedPane();
            
            JPanel pane = new JPanel();
            JScrollPane scroll = new JScrollPane(pane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            
            tabs.add(Messages.getMessage("PdfPreferences.SignatureTitle"), scroll);
            
            pane.setLayout(new GridBagLayout());
			
			int y=0;
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;

			c.insets = new Insets(5,5,5,5);
		
			appendModeLabel = aggiungiLabel( pane, appendModeLabel, "appendModeLabel", Messages.getMessage("PdfPreferences.Signature.appendMode"), c, 0, y, 1);
			
			boolean appendModeProperty=false;
			try {
				appendModeProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_APPENDMODE );
			} catch (Exception e2) {
			}
			appendMode = aggiungiCheckBox( pane, appendMode, "appendMode", "", appendModeProperty, true, SwingConstants.LEFT, c, 1, y++, 1);
			
			acro6LayerLabel = aggiungiLabel( pane, acro6LayerLabel, "acro6LayerLabel", Messages.getMessage("PdfPreferences.Signature.acro6Layer"), c, 0, y, 1);
						
			boolean acro6LayerProperty=false;
			try {
				acro6LayerProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER );
			} catch (Exception e2) {
			}
			acro6Layer = aggiungiCheckBox( pane, acro6Layer, "acro6Layer", "", acro6LayerProperty, true, SwingConstants.LEFT, c, 1, y++, 1);
						
			renderModeLabel = aggiungiLabel( pane, renderModeLabel, "renderModeLabel", Messages.getMessage("PdfPreferences.Signature.renderMode"), c, 0, y, 1);
			
			String renderModeProperty ="";
			try {
				renderModeProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_RENDERMODE );
			} catch (Exception e2) {
			}
			renderMode = aggiungiFieldTesto( pane, renderMode, 30, "renderMode", renderModeProperty, true, c, 1, y++, 1);
			
			l2FontSizeLabel = aggiungiLabel( pane, l2FontSizeLabel, "l2FontSizeLabel", Messages.getMessage("PdfPreferences.Signature.l2FontSizeLayer"), c, 0, y, 1);
			
			String l2FontSizeLayerProperty ="";
			try {
				l2FontSizeLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE );
			} catch (Exception e2) {
			}
			l2FontSizeLayer = aggiungiFieldTesto( pane, l2FontSizeLayer, 30, "l2FontSizeLayer", l2FontSizeLayerProperty, true, c, 1, y++, 1);
			
			l2TextLayerLabel = aggiungiLabel( pane, l2TextLayerLabel, "l2TextLayerLabel", Messages.getMessage("PdfPreferences.Signature.l2TextLayer"), c, 0, y, 1);
			
			String l2TextLayerProperty ="";
			try {
				l2TextLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXT );
			} catch (Exception e2) {
			}
			l2TextLayer = aggiungiFieldTesto( pane, l2TextLayer, 30, "l2TextLayer", l2TextLayerProperty, true, c, 1, y++, 1);
			
			l4TextLayerLabel = aggiungiLabel( pane, l4TextLayerLabel, "l4TextLayerLabel", Messages.getMessage("PdfPreferences.Signature.l4TextLayer"), c, 0, y, 1);
			
			String l4TextLayerProperty ="";
			try {
				l4TextLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L4TEXT );
			} catch (Exception e2) {
			}
			l4TextLayer = aggiungiFieldTesto( pane, l4TextLayer, 30, "l4TextLayer", l4TextLayerProperty, true, c, 1, y++, 1);
			
			hashAlgorithmLabel = aggiungiLabel( pane, hashAlgorithmLabel, "hashAlgorithmLabel", Messages.getMessage("PdfPreferences.Signature.hashAlgorithm"), c, 0, y, 1);
			
			String hashAlgorithmProperty ="";
			try {
				hashAlgorithmProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM );
			} catch (Exception e2) {
			}
			hashAlgorithm = aggiungiFieldTesto( pane, hashAlgorithm, 30, "hashAlgorithm", hashAlgorithmProperty, true, c, 1, y++, 1);
			
			certifyModeLabel = aggiungiLabel( pane, certifyModeLabel, "certifyModeLabel", Messages.getMessage("PdfPreferences.Signature.certifyMode"), c, 0, y, 1);
			
			String certifyModeProperty ="";
			try {
				certifyModeProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE );
			} catch (Exception e2) {
			}
			certifyMode = aggiungiFieldTesto( pane, certifyMode, 30, "certifyMode", certifyModeProperty, true, c, 1, y++, 1);
			
			imgPathLabel = aggiungiLabel( pane, imgPathLabel, "imgPathLabel", Messages.getMessage("PdfPreferences.Signature.imgPath"), c, 0, y, 1);
			
			String imgPathProperty ="";
			try {
				imgPathProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_IMGPATH );
			} catch (Exception e2) {
			}
			imgPath = aggiungiFieldTesto( pane, imgPath, 30, "imgPath", imgPathProperty, true, c, 1, y++, 1);
			
			bgImgPathLabel = aggiungiLabel( pane, bgImgPathLabel, "bgImgPathLabel", Messages.getMessage("PdfPreferences.Signature.bgImgPath"), c, 0, y, 1);
			
			String bgImgPathProperty ="";
			try {
				bgImgPathProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGPATH );
			} catch (Exception e2) {
			}
			bgImgPath = aggiungiFieldTesto( pane, bgImgPath, 30, "bgImgPath", bgImgPathProperty, true, c, 1, y++, 1);
			
			bgImgScaleLabel = aggiungiLabel( pane, bgImgScaleLabel, "bgImgScaleLabel", Messages.getMessage("PdfPreferences.Signature.bgImgScale"), c, 0, y, 1);
			
			String bgImgScaleProperty ="";
			try {
				bgImgScaleProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE );
			} catch (Exception e2) {
			}
			bgImgScale = aggiungiFieldTesto( pane, bgImgScale, 30, "bgImgScale", bgImgScaleProperty, true, c, 1, y++, 1);
			
			
			outputFileNameLabel = aggiungiLabel( pane, outputFileNameLabel, "outputFileNameLabel", Messages.getMessage("PdfPreferences.Signature.outputFileName"), c, 0, y, 1);
		
			String outputFileNameProperty ="";
			try {
				outputFileNameProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_OUTPUTFILENAME );
			} catch (Exception e2) {
			}
			outputFileName = aggiungiFieldTesto( pane, outputFileName, 30, "outputFileName", outputFileNameProperty, true, c, 1, y++, 1);
			
			c.gridy++;

			c.weighty = 1;
			c.gridx = 0;
			pane.add(Box.createVerticalGlue(), c);
			
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;

			c.insets = new Insets(5,5,5,5);
			
			JPanel pane1 = new JPanel();
            JScrollPane scroll1 = new JScrollPane(pane1);
            scroll1.setBorder(BorderFactory.createEmptyBorder());
            
            tabs.add(Messages.getMessage("PdfPreferences.MarkTitle"), scroll1);
            
            pane1.setLayout(new GridBagLayout());
            
			tsaUrlLabel = aggiungiLabel( pane1, tsaUrlLabel, "tsaUrlLabel", Messages.getMessage("PdfPreferences.Signature.tsaUrl"), c, 0, y, 1);
			
			String tsaUrlProperty ="";
			try {
				tsaUrlProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAURL );
			} catch (Exception e2) {
			}
			tsaUrl = aggiungiFieldTesto( pane1, tsaUrl, 30, "tsaUrl", tsaUrlProperty, true, c, 1, y++, 1);
			
			tsaUserLabel = aggiungiLabel( pane1, tsaUserLabel, "tsaUserLabel", Messages.getMessage("PdfPreferences.Signature.tsaUser"), c, 0, y, 1);
			
			String tsaUserProperty ="";
			try {
				tsaUserProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAUSER );
			} catch (Exception e2) {
			}
			tsaUser = aggiungiFieldTesto( pane1, tsaUser, 30, "tsaUser", tsaUserProperty, true, c, 1, y++, 1);
			
			tsaPasswordLabel = aggiungiLabel( pane1, tsaPasswordLabel, "tsaPasswordLabel", Messages.getMessage("PdfPreferences.Signature.tsaPassword"), c, 0, y, 1);
			
			String tsaPasswordProperty ="";
			try {
				tsaPasswordProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD );
			} catch (Exception e2) {
			}
			tsaPassword = aggiungiFieldTesto( pane1, tsaPassword, 30, "tsaPassword", tsaPasswordProperty, true, c, 1, y++, 1);
			
			c.gridy++;
			c.weighty = 1;
			c.gridx = 0;
			pane1.add(Box.createVerticalGlue(), c);
			
			panel.add(tabs, BorderLayout.CENTER);
            return panel;
		}


         /*
		 * Creates a pane holding all Printing settings
		 */
		
		private void  addMenuToTree(int tab, NodeList nodes, CheckNode top, java.util.List previous){
			
			for(int i=0; i!=nodes.getLength(); i++){
				
				if(i<nodes.getLength()){
					String name = nodes.item(i).getNodeName();
					if(!name.startsWith("#")){
						//Node to add
						CheckNode newLeaf = new CheckNode(Messages.getMessage("PdfCustomGui."+name));
						newLeaf.setEnabled(true);
						//Set to reversedMessage for saving of preferences
						reverseMessage.put(Messages.getMessage("PdfCustomGui."+name), name);
						String propValue = properties.getValue(name);
						//Set if should be selected
						if(propValue.length()>0 && propValue.equals("true")){
							newLeaf.setSelected(true);
						}else{
							newLeaf.setSelected(false);
						}
						
						//If has child nodes
						if(nodes.item(i).hasChildNodes()){
							//Store this top value
							previous.add(top);
							//Set this node to ned top
							top.add(newLeaf);
							//Add new menu to tree
							addMenuToTree(tab, nodes.item(i).getChildNodes(), newLeaf, previous);
						}else{
							//Add to current top
							top.add(newLeaf);
						}
					}
				}
			}
		}



		private void show(Component component) {
			if (currentComponent != null) {
				remove(currentComponent);
			}

			add("Center", currentComponent = component);
			revalidate();
			repaint();
		}

		private void addButton(String title, String iconUrl, final Component component, JPanel bar, ButtonGroup group) {
			Action action = new AbstractAction(title, new ImageIcon(getClass().getResource(iconUrl))) {
				public void actionPerformed(ActionEvent e) {
					show(component);
				}
			};

			JToggleButton button = new JToggleButton(action);
			button.setBorder( BorderFactory.createEmptyBorder() );
			button.setVerticalTextPosition(JToggleButton.BOTTOM);
		    button.setHorizontalTextPosition(JToggleButton.CENTER);
		    
			button.setContentAreaFilled(false);
			if(PdfDecoder.isRunningOnMac)
				button.setHorizontalAlignment(AbstractButton.LEFT);
			
			//Center buttons
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			bar.add(button);

			group.add(button);

			if (group.getSelection() == null) {
				button.setSelected(true);
				show(component);
			}
		}


    }

	public void setParent(Container parent) {
		this.parent = parent;
	}

	public void dispose() {
		
		this.removeAll();
		
		reverseMessage =null;
		
		menuTabs=null;
		propertiesLocation  =null;
		
		if(propertiesDialog!=null)
			propertiesDialog.removeAll();
		propertiesDialog=null;
		
		confirm  =null;

		cancel  =null;

		if(tabs!=null)
			tabs.removeAll();
		tabs=null;

		resolution=null;

		searchStyle=null;

		border =null;

		downloadWindow =null;

		HiResPrint =null;

		constantTabs=null;

		enhancedViewer=null;
		
		enhancedFacing=null;
		
		thumbnailScroll=null;
		
		enhancedGUI=null;
		
		rightClick=null;

        scrollwheelZoom=null;
		
		update  =null;

		maxMultiViewers =null;

		pageInsets =null;
		pageInsetsText = null;

        windowTitle = null;
        windowTitleText = null;
		
		iconLocation = null;
		iconLocationText = null;

        printerBlacklist = null;
        printerBlacklistText = null;

        defaultPrinter = null;
        defaultPrinterText = null;

        defaultPagesize = null;
        defaultPagesizeText = null;

        defaultDPI = null;
        defaultDPIText = null;

		pageFlowCacheText = null;
		pageFlowCache = null;
		
		sideTabLength = null;
		sideTabLengthText = null;
		
		pageFlowPagesText = null;
		pageFlowPages = null;
		
		pageFlowReflectionHeightText = null;
		pageFlowReflectionHeight = null;
		
		pageFlowSideSizeText = null;
		pageFlowSideSize = null;
		
		pageFlowReflection = null;

        useHinting = null;
		
		autoScroll =null;

        confirmClose = null;

		openLastDoc =null;
		
		pageLayout =null;

		if(highlightBoxColor!=null)
			highlightBoxColor.removeAll();
		highlightBoxColor  =null;
		
		if(highlightTextColor!=null)
			highlightTextColor.removeAll();
		highlightTextColor =null;
		
		if(viewBGColor!=null)
			viewBGColor.removeAll();
		viewBGColor =null;
		
		if(pdfDecoderBackground!=null)
			pdfDecoderBackground.removeAll();
		pdfDecoderBackground =null;
		
		if(foreGroundColor!=null)
			foreGroundColor.removeAll();
		foreGroundColor =null;
		
//		if(sideBGColor!=null)
//			sideBGColor.removeAll();
//		sideBGColor =null;
		
		
		if(invertHighlight!=null)
			invertHighlight.removeAll();
		invertHighlight =null;
		
		if(replaceDocTextCol!=null)
			replaceDocTextCol.removeAll();
		replaceDocTextCol =null;
		
		if(replaceDisplayBGCol!=null)
			replaceDisplayBGCol.removeAll();
		replaceDisplayBGCol =null;
		
		if(changeTextAndLineArt!=null)
			changeTextAndLineArt.removeAll();
		changeTextAndLineArt =null;
		
		showMouseSelectionBox = null;
		
		if(highlightComposite!=null)
			highlightComposite.removeAll();
		highlightComposite =null;
		
		if(propertiesDialog!=null)
			propertiesDialog.removeAll();
		parent =null;

		clearHistory =null;

		historyClearedLabel =null;
		
		preferenceManager = null;
		
	}
	
	private static boolean hasFreetts()
	{
		return false;
		/**/
	}
	
	private JLabel aggiungiLabel(JPanel panel,JLabel label, String name, String text, GridBagConstraints c, int x, int y, int width){
		label = new JLabel();
		//label.setBorder( BorderFactory.createLineBorder(Color.black) );
		label.setText(text);		
		label.setName(name);		
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(1,5,1,5);
		panel.add(label, c);
		return label;
	}
	
	private JTextField aggiungiFieldTesto(JPanel panel, JTextField field, int size,  String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width){
		field=new JTextField(size);
		//field.setBorder( BorderFactory.createLineBorder(Color.black) );
		if( text!=null)
			field.setText( text );
		field.setEditable( editable);
		field.setName(name);
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(1,5,1,5);
		panel.add(field, c);
		return field;
	}
	
	private JCheckBox aggiungiCheckBox(JPanel panel, JCheckBox checkBox, String name, String text, boolean selected, boolean enabled, 
			int textPosition, GridBagConstraints c, int x, int y, int width){
		checkBox=new JCheckBox();
		//checkBox.setBorder( BorderFactory.createLineBorder(Color.black) );
		checkBox.setName( name );
		checkBox.setSelected(selected);
		checkBox.setEnabled( enabled );
		checkBox.setText(text);
		checkBox.setHorizontalTextPosition( textPosition );
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(1,5,1,5);
		panel.add( checkBox, c );
		return checkBox;
	}
	
	private void aggiungiCombo(JPanel panel, JComboBox combo, String[] comboValues, String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width){
		combo.setName( name );
		int indiceSelezionato = 0;
		//combo.setBorder( BorderFactory.createLineBorder(Color.black) );
		for( int i = 0; i<comboValues.length; i++){
			if( comboValues[i].equalsIgnoreCase( text ) )
				indiceSelezionato = i;
		}
		combo.setSelectedIndex(indiceSelezionato);
		combo.setEnabled( editable);
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(1,5,1,5);
		panel.add(combo, c);
	}
	
}