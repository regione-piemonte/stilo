/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2012, IDRsolutions and Contributors.
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
 * Viewer.java
 * ---------------
 */

package it.eng.hybrid.module.jpedal.viewer;

import it.eng.hybrid.module.jpedal.acroforms.ActionHandler;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.fileInputProvider.FileInputProvider;
import it.eng.hybrid.module.jpedal.fileInputProvider.FileInputResponse;
import it.eng.hybrid.module.jpedal.fileOutputProvider.FileOutputProvider;
import it.eng.hybrid.module.jpedal.fonts.FontMappings;
import it.eng.hybrid.module.jpedal.io.JAIHelper;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.objects.raw.OutlineObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.preferences.PropertiesFile;
import it.eng.hybrid.module.jpedal.ui.Display;
import it.eng.hybrid.module.jpedal.ui.MultiViewTransferHandler;
import it.eng.hybrid.module.jpedal.ui.SingleViewTransferHandler;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.ui.generic.GUIMouseHandler;
import it.eng.hybrid.module.jpedal.ui.generic.GUISearchWindow;
import it.eng.hybrid.module.jpedal.ui.generic.GUIThumbnailPanel;
import it.eng.hybrid.module.jpedal.ui.popup.TipOfTheDay;
import it.eng.hybrid.module.jpedal.ui.swing.SearchList;
import it.eng.hybrid.module.jpedal.ui.swing.SwingMouseListener;
import it.eng.hybrid.module.jpedal.ui.swing.SwingSearchWindow;
import it.eng.hybrid.module.jpedal.ui.swing.SwingThumbnailPanel;
import it.eng.hybrid.module.jpedal.util.Options;
import it.eng.hybrid.module.jpedal.util.Printer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Node;


/** PDF viewer
 *
 * If you are compiling, you will need to download all the examples source files from http://www.idrsolutions.com/how-to-view-pdf-files-in-java/
 *
 * Run directly from jar with java -cp jpedal.jar
 * or java -jar jpedal.jar
 *
 * Lots of tutorials on how to configure on our website
 *
 * If you want to implement your own
 * Very simple example at http://www.jpedal.org/gplSrc.html
 * But we would recommend you look at the full viewer as it is totally configurable and does everything for you.
 *
 * See also http://www.jpedal.org/javadoc settings to customise
 *
 * Fully featured GUI viewer and demonstration of JPedal's capabilities
 *
 * <br>This class provides the framework for the Viewer and calls other classes which provide the following
 * functions:-
 *
 * <br>Values commonValues - repository for general settings
 * Printer currentPrinter - All printing functions and access methods to see if printing active
 * PdfDecoder decode_pdf - PDF library and panel
 * ThumbnailPanel thumbnails - provides a thumbnail pane down the left side of page - thumbnails can be clicked on to goto page
 * PropertiesFile properties - saved values stored between sessions
 * SwingGUI currentGUI - all Swing GUI functions
 * SearchWindow searchFrame (not GPL) - search Window to search pages and goto references on any page
 * Commands currentCommands - parses and executes all options
 * SwingMouseHandler mouseHandler - handles all mouse and related activity
 */
public class Viewer {

	public final static Logger logger = Logger.getLogger(Viewer.class);
	
	/**control if messages appear*/
    public static boolean showMessages=true;

	/**repository for general settings*/
	public Values commonValues=new Values();

	/**All printing functions and access methods to see if printing active*/
	public Printer currentPrinter=new Printer();

	/**PDF library and panel*/
	public PdfDecoder decode_pdf = new PdfDecoder(true);

	/**encapsulates all thumbnail functionality - just ignore if not required*/
	public GUIThumbnailPanel thumbnails=new SwingThumbnailPanel(commonValues,decode_pdf);

	/**values saved on file between sessions*/
	private PropertiesFile properties=new PropertiesFile();
	
	/**general GUI functions*/
	public SwingGUI currentGUI=new SwingGUI(decode_pdf,commonValues,thumbnails,properties);

	/**search window and functionality*/
	private GUISearchWindow searchFrame=new SwingSearchWindow(currentGUI);
	
	/**timbro window and functionality*/
	//private GUITimbroWindow timbroFrame=new TimbroPanel(commonValues, currentGUI);

	
	
	public Commands currentCommands=new Commands(commonValues,currentGUI,decode_pdf,
			thumbnails,properties,searchFrame, currentPrinter);

	/**all mouse actions*/
//	protected GUIMouseHandler mouseHandler=new SwingMouseHandler(decode_pdf,currentGUI,commonValues,currentCommands);
	protected GUIMouseHandler mouseHandler=new SwingMouseListener(decode_pdf,currentGUI,commonValues,currentCommands);

	/**scaling values which appear onscreen*/
	protected String[] scalingValues;

	/**warn user if viewer not setup fully*/
	private boolean isSetup;

	//private Object[] restrictedMenus;

	/**Location of Preferences Files*/
	public final static String PREFERENCES_DEFAULT = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/Default.xml";
	public final static String PREFERENCES_NO_GUI = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/NoGUI.xml";
	public final static String PREFERENCES_NO_SIDE_BAR = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/NoSideTabOrTopButtons.xml";
	public final static String PREFERENCES_OPEN_AND_NAV_ONLY = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/OpenAndNavOnly.xml";
	public final static String PREFERENCES_PDFHELP = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/PDFHelp.xml";
	public final static String PREFERENCES_BEAN = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/Bean.xml";
	//custom
	public final static String PREFERENCES_ENGINEERING_BASE = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/EngineeringProps.xml";
	public final static String PREFERENCES_ENGINEERING_EXTENDED = "jar:/it/eng/hybrid/module/jpedal/viewer/resources/EngineeringPropsExtended.xml";
	
	/**tell software to exit on close - default is true*/
	public static boolean exitOnClose=true;

    /**used internally - please do not use*/
	private static String rawFile="";
    public static String file="";
    /**flag del tipo di applet attualmente aperta...*/
	private boolean isExtendedApplet;
	
    //<start-wrap>
    /**
    //<end-wrap>

	public static String message="${titleMessage}";
    
    private static int count=0;

    // setup and run client with hard-coded file
	public Viewer() {
		//enable error messages which are OFF by default
		
		PdfDecoder.showErrorMessages=true;

		properties.loadProperties();

     //clean up name
        String[] seps=new String[]{"/","\\"};
        for(int ii=0;ii<seps.length;ii++){
            int id=rawFile.lastIndexOf(seps[ii]);
            if(id!=-1)
                rawFile=rawFile.substring(id+1,rawFile.length());
        }

        file=rawFile;

        count++;
	} /**/


    /**
	 * setup and run client, loading defaultFile on startup (please do not use)
	 * - use setupViewer();, openDefaultFile(defaultFile)
     *
     **/
    //<start-wrap>
    /**
    //<end-wrap>

     private String defaultFile=null;
     
    public void setupViewer(String defaultFile) {
      
        if(count==0)
            throw new RuntimeException("You cannot use wrapper to open PDFs");

     this.defaultFile=defaultFile;

	setupViewer();

	openDefaultFile();

    }/**/

	/**
	 * open the file passed in by user on startup (do not call directly)
	 */
	public SwingGUI getSwingGUI(){
		return currentGUI;
	}

    /**
     *
     * @param defaultFile
     * Allow user to open PDF file to display
     */
    //<start-wrap>
	public void openDefaultFile(String defaultFile) {

        
        //get any user set dpi
		String hiresFlag = System.getProperty("org.jpedal.hires");
		if(Commands.hires || hiresFlag != null)
			commonValues.setUseHiresImage(true);

		//get any user set dpi
		String memFlag=System.getProperty("org.jpedal.memory");
		if(memFlag!=null)
			commonValues.setUseHiresImage(false);
		
		//reset flag
		if(thumbnails.isShownOnscreen())
			thumbnails.resetToDefault();

		commonValues.maxViewY=0;// ensure reset for any viewport

		/**
		 * open any default file and selected page
		 */
		if(defaultFile!=null){

            //<start-wrap>
            File testExists=new File(defaultFile);
			boolean isURL=false;
			if(defaultFile.startsWith("http:")|| defaultFile.startsWith("jar:") || defaultFile.startsWith("file:")){
				logger.info("Opening http connection");
				isURL=true;
			}

			if((!isURL) && (!testExists.exists())){
				currentGUI.showMessageDialog(defaultFile+ '\n' +Messages.getMessage("PdfViewerdoesNotExist.message"));
			}else if((!isURL) &&(testExists.isDirectory())){
				currentGUI.showMessageDialog(defaultFile+ '\n' +Messages.getMessage("PdfViewerFileIsDirectory.message"));
			}else{
                commonValues.setFileSize(testExists.length() >> 10);

            //<end-wrap>

				commonValues.setSelectedFile(defaultFile);

				currentGUI.setViewerTitle(null);

                //<start-wrap>
				/**see if user set Page*/
				String page=System.getProperty("org.jpedal.page");
				String bookmark=System.getProperty("org.jpedal.bookmark");
				if(page!=null && !isURL){

					try{
						int pageNum=Integer.parseInt(page);

						if(pageNum<1){
							pageNum=-1;
							System.err.println(page+ " must be 1 or larger. Opening on page 1");
							logger.info(page+ " must be 1 or larger. Opening on page 1");
						}

						if(pageNum!=-1)
							openFile(testExists,pageNum);


					}catch(Exception e){
						System.err.println(page+ "is not a valid number for a page number. Opening on page 1");
						logger.info(page+ "is not a valid number for a page number. Opening on page 1");
					}
				}else if(bookmark!=null){
					openFile(testExists,bookmark);
				}else{
                    //<end-wrap>
					try {
						currentCommands.openFile(defaultFile);
					} catch (PdfException e) {
						logger.info("Errore nell'apertura del file " + e.getMessage());
					} catch (Exception e) {
						logger.info("Errore nell'apertura del file " + e.getMessage());
					}
			    //<start-wrap>
			    }
			}
            //<end-wrap>
		}

        //<start-wrap>
        /**
        //<end-wrap>
        executeCommand(Commands.SINGLE,null);
        /**/
	}
	
	/**
    *
    * @param defaultFile
    * Allow user to open PDF file to display
    */
   //<start-wrap>
	public void openDefaultFileAtPage(String defaultFile, int page) {
   /**
   //<end-wrap>
	private void openDefaultFileAtPage(String defaultFile, int page) {
   /**/

		//get any user set dpi
		String hiresFlag = System.getProperty("org.jpedal.hires");
		if(Commands.hires || hiresFlag != null)
			commonValues.setUseHiresImage(true);

		//get any user set dpi
		String memFlag=System.getProperty("org.jpedal.memory");
		if(memFlag!=null)
			commonValues.setUseHiresImage(false);
		
		//reset flag
		if(thumbnails.isShownOnscreen())
			thumbnails.resetToDefault();

		commonValues.maxViewY=0;// ensure reset for any viewport

		/**
		 * open any default file and selected page
		 */
		if(defaultFile!=null){

			File testExists=new File(defaultFile);
			boolean isURL=false;
			if(defaultFile.startsWith("http:")|| defaultFile.startsWith("jar:")){
				logger.info("Opening http connection");
				isURL=true;
			}

			if((!isURL) && (!testExists.exists())){
				currentGUI.showMessageDialog(defaultFile+ '\n' +Messages.getMessage("PdfViewerdoesNotExist.message"));
			}else if((!isURL) &&(testExists.isDirectory())){
				currentGUI.showMessageDialog(defaultFile+ '\n' +Messages.getMessage("PdfViewerFileIsDirectory.message"));
			}else{

				commonValues.setSelectedFile(defaultFile);
                //<start-wrap>
				commonValues.setFileSize(testExists.length() >> 10);
                //<end-wrap>
				currentGUI.setViewerTitle(null);

				openFile(testExists,page);

			}
		}
	}

    //<start-wrap>
    /**
	 * setup and run client
	 */
	public Viewer() {
		
		logger.info("Viewer");
		commonValues=new Values();
		decode_pdf = new PdfDecoder(true);
		
		currentGUI=new SwingGUI(decode_pdf,commonValues,thumbnails,properties);
		
		currentCommands=new Commands(commonValues,currentGUI,decode_pdf,
				thumbnails,properties,searchFrame, currentPrinter);
		
		mouseHandler=new SwingMouseListener(decode_pdf,currentGUI,commonValues,currentCommands);
		
        
		
		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;
		
		//String prefFile = System.getProperty("org.jpedal.Viewer.Prefs");
		//System.out.println("prefFile " + prefFile);
//		if(prefFile != null){
//			properties.loadProperties(prefFile);
//		}else{
//			properties.loadProperties();
//		}

		//properties.loadProperties();
		
	}
     //<end-wrap>
     //<start-wXXrap> //here when other files removed

	/**
	 * setup and run client passing in paramter to show if
	 * running as applet, webstart or JSP (only applet has any effect
	 * at present)
	 */
	public Viewer(int modeOfOperation) {

//		commonValues=new Values();
//		decode_pdf = new PdfDecoder(true);
//		thumbnails=new SwingThumbnailPanel(commonValues,decode_pdf);
//		
//		properties=new PropertiesFile();
//		
//		currentGUI=new SwingGUI(decode_pdf,commonValues,thumbnails,properties);
//		
//		currentPrinter=new Printer();
//		searchFrame=new SwingSearchWindow(currentGUI);
		
		
		currentCommands=new Commands(commonValues,currentGUI,decode_pdf,thumbnails,properties,searchFrame, currentPrinter);
		
		mouseHandler=new SwingMouseListener(decode_pdf,currentGUI,commonValues,currentCommands);
		
       
		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;
		
		String prefFile = System.getProperty("org.jpedal.Viewer.Prefs");
		if(prefFile != null){
			properties.loadProperties(prefFile);
		}else{
			properties.loadProperties();
		}
		
		commonValues.setModeOfOperation(modeOfOperation);

		
	}
	
	public void setModeOperation(int modeOfOperation){
		commonValues.setModeOfOperation(modeOfOperation);
	}
	
	/**
	 * setup and run client passing in paramter that points to
	 * the preferences file we should use.
	 */
	public Viewer(String prefs) {

		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;
		
//		Example preference file can be found here. You will know when it's working
//		
//		// p = p.replaceAll("\\.", "/");
//	        URL u = Thread.currentThread().getContextClassLoader().getResource(
//	                p);
//	        ArrayList retValue = new ArrayList(0);
//	        String s = u.toString();
//
//	        logger.info("scanning " + s);
//
//	        if (s.startsWith("jar:") && s.endsWith(p)) {
//	            int idx = s.lastIndexOf(p);
//	            s = s.substring(0, idx); // isolate entry name
//
//	            logger.info("entry= " + s);
//	            try{
//	            	URL url = new URL(s);
//	            	// Get the jar file
//	            	JarURLConnection conn = (JarURLConnection) url.openConnection();
//	            	JarFile jar = conn.getJarFile();
//
//	            	for (Enumeration e = jar.entries(); e.hasMoreElements();) {
//	            		JarEntry entry = (JarEntry) e.nextElement();
//	            		if ((!entry.isDirectory())
//	            				& (entry.getName().startsWith(p))) { // this
//	            			// is how you can match
//	            			// to find your fonts.
//	            			// logger.info("Found a match!");
//	            			String fontName = entry.getName();
//	            			int i = fontName.lastIndexOf('/');
//	            			fontName = fontName.substring(i + 1);
//	            			retValue.add(fontName);
//	            		}
//	            	}
//	            }catch(Exception e){
//	            	
//	            }
//	        } else {
//	            // Does not start with "jar:"
//	            // Dont know - should not happen
//	            logger.info(p);
//	            System.exit(1);
//	        }
		
		try{
			properties.loadProperties(prefs);
		}catch(Exception e){
			System.err.println("Specified Preferrences file not found at "+prefs+". If this file is within a jar ensure filename has jar: at the begining.");
			System.exit(1);
		}
		
		
	}
	
	/**
	 * setup and run client passing in parameter that points to
	 * the preferences file we should use.
	 */
	public Viewer(Container rootContainer, String preferencesPath) {

		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;
		
		if(preferencesPath!=null && preferencesPath.length()>0){
			try{
				properties.loadProperties(preferencesPath);
			}catch(Exception e){
				System.err.println("Specified Preferrences file not found at "+preferencesPath+". If this file is within a jar ensure filename has jar: at the begining.");
				System.exit(1);
			}
		}else{
			properties.loadProperties();
		}
		setRootContainer(rootContainer);
		
		
	}
	
	/**
	 * Pass a document listener to the page counter to watch for changes to the page number.
	 * This value is updated when the page is altered.
	 * 
	 * @param docListener :: A document listener to listen for changes to page number.
	 * 			New page number can be found in the insertUpdate method using
	 * 			DocumentEvent.getDocument().getText(int offset, int length)
	 */
	public void addPageChangeListener(DocumentListener docListener){
		if(currentGUI!=null)
			currentGUI.addPageChangeListener(docListener);
	}
	
	public void setRootContainer(Container rootContainer){
		if(rootContainer==null)
			throw new RuntimeException("Null containers not allowed.");
		
		Container c = rootContainer;
		
		if((rootContainer instanceof JTabbedPane)){
			JPanel temp = new JPanel(new BorderLayout());
			rootContainer.add(temp);
			c = temp;
		}else if(rootContainer instanceof JScrollPane){
			JPanel temp = new JPanel(new BorderLayout());
			((JScrollPane)rootContainer).getViewport().add(temp);
			c = temp;
			
		}else if(rootContainer instanceof JSplitPane){
			throw new RuntimeException("To add the viewer to a split pane please pass through either JSplitPane.getLeftComponent() or JSplitPane.getRightComponent()");
		}
		
		if(!(rootContainer instanceof JFrame)){
			c.setLayout(new BorderLayout());
		}
		
		//Used to prevent infinite scroll issue as a preferred size has been set
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d.width / 2, height = d.height / 2;
		if(width<700)
			width=700;

		//allow user to alter size
		String customWindowSize=System.getProperty("org.jpedal.startWindowSize");
		if(customWindowSize!=null){

			StringTokenizer values=new StringTokenizer(customWindowSize,"x");

			logger.info("values.countTokens()" + values.countTokens());
			if(values.countTokens()!=2)
				throw new RuntimeException("Unable to use value for org.jpedal.startWindowSize="+customWindowSize+"\nValue should be in format org.jpedal.startWindowSize=200x300");

			try{
				width=Integer.parseInt(values.nextToken().trim());
				height=Integer.parseInt(values.nextToken().trim());

			}catch(Exception ee){
				throw new RuntimeException("Unable to use value for org.jpedal.startWindowSize="+customWindowSize+"\nValue should be in format org.jpedal.startWindowSize=200x300");
			}
		}
		
		c.setPreferredSize(new Dimension(width, height));
		
		currentGUI.setFrame(c);
		
		//setupViewer();
	}
    //<end-wrap>
	
	/**
	 * Should be called before setupViewer
	 */
	public void loadProperties(String props){
		properties.loadProperties(props);
	}
	
	/**
	 * Should be called before setupViewer
	 */
	public void loadProperties(InputStream is){
		properties.loadProperties(is);
	}
	
	/**
	 * initialise and run client (default as Application in own Frame)
	 */
	public void setupViewer() {
		//also allow messages to be suppressed with JVM option
        String flag=System.getProperty("org.jpedal.suppressViewerPopups");
        boolean suppressViewerPopups =false;

        if(flag!=null && flag.toLowerCase().equals("true"))
            suppressViewerPopups =true;

        /**
         *  set search window position here to ensure
         *  that gui has correct value
         */
        String searchType = properties.getValue("searchWindowType");
        if(searchType!=null && searchType.length() != 0){
            int type = Integer.parseInt(searchType);
            searchFrame.setStyle(type);
        }else
            searchFrame.setStyle(SwingSearchWindow.SEARCH_MENU_BAR);

        //Set search frame here
        currentGUI.setSearchFrame(searchFrame);

        /**switch on thumbnails if flag set*/
        String setThumbnail=System.getProperty("org.jpedal.thumbnail");
        if(setThumbnail!=null){
        	if(setThumbnail.equals("true"))
        		thumbnails.setThumbnailsEnabled(true);
	        else if(setThumbnail.equals("false"))
	        	thumbnails.setThumbnailsEnabled(false);
	    } else {//default
	    	thumbnails.setThumbnailsEnabled(true);
	    }
        
//        if(commonValues.getTimbroEnabled()!=null){
//        	timbroFrame.setTimbroEnabled(commonValues.getTimbroEnabled());
//        } else {
//        	String propValue = properties.getValue("Timbro");
//        	boolean set = propValue.length()>0 && propValue.toLowerCase().equals("true");
//        	timbroFrame.setTimbroEnabled( set);
//        }
//        //Set search frame here
//        currentGUI.setTimbroFrame(timbroFrame);
    
        
        /**
         * non-GUI initialisation
         **/

        //allow user to override messages
        //<link><a name="locale" />
        /**
         * allow user to define country and language settings
         *
         * you will need a file called messages_XX.properties in
         * org.jpedal.international.messages where XX is a valid Locale.
         *
         * You can also choose an alternative Lovation - see sample code below
         *
         *  You can manually set Java to use a Locale with this code
         *  (also useful to test)
         *
         *	Example here is Brazil (note no Locale files present for it)
         *
         * If you make and Locale files, we would be delighted to include them
         * in future versions of the software.
         *
         java.util.Locale aLocale = new java.util.Locale("br", "BR");

         java.util.Locale.setDefault(aLocale);
         */


        String customBundle=System.getProperty("org.jpedal.bundleLocation");
        //customBundle="org.jpedal.international.messages"; //test code
        
        if(customBundle!=null){

            BufferedReader input_stream = null;
            ClassLoader loader = Messages.class.getClassLoader();
            String fileName=customBundle.replaceAll("\\.","/")+ '_' +java.util.Locale.getDefault().getLanguage()+".properties";

            //also tests if locale file exists and tell user if not
            try{

                input_stream =new BufferedReader(new InputStreamReader(loader.getResourceAsStream(fileName)));

                input_stream.close();
                
            }catch(Exception ee){

                
                java.util.Locale.setDefault(new java.util.Locale("en", "EN"));
                currentGUI.showMessageDialog("No locale file "+fileName+" has been defined for this Locale - using English as Default"+
                        "\n Format is path, using '.' as break ie org.jpedal.international.messages");

            }

            ResourceBundle rb = ResourceBundle.getBundle(customBundle);
            //Messages.setBundle(ResourceBundle.getBundle(customBundle));
            init(rb);
            
        }else
            init(null);



        /**
         * gui setup, create gui, load properties
         */
        currentGUI.init(scalingValues,currentCommands,currentPrinter);

        //now done on first usage
        //p.createPreferenceWindow(currentGUI);

        mouseHandler.setupMouse();

        if(searchFrame.getStyle()==SwingSearchWindow.SEARCH_TABBED_PANE)
            currentGUI.searchInTab(searchFrame);
        
//        if( timbroFrame.isTimbroEnabled() )
//        	currentGUI.timbroInTab(timbroFrame);
        
        /**
         * setup window for warning if renderer has problem
         */
        decode_pdf.getDynamicRenderer().setMessageFrame(currentGUI.getFrame());

        String propValue = properties.getValue("showfirsttimepopup");
        boolean showFirstTimePopup = !suppressViewerPopups && propValue.length()>0 && propValue.equals("true");

        if(showFirstTimePopup){
            currentGUI.showFirstTimePopup();
            properties.setValue("showfirsttimepopup","false");
        }else if(!suppressViewerPopups)
            propValue = properties.getValue("showrhinomessage");


//        if(!suppressViewerPopups && properties != null && (propValue.length()>0 && propValue.equals("true"))){
//
//
//             currentGUI.showMessageDialog(Messages.getMessage("Beta release Javascript support\n" +
//             "OS version does not contain Javascript support - please look at full version"));
//
//             /**/
//            properties.setValue("showrhinomessage","false");
//        }

        if(!suppressViewerPopups && JAIHelper.isJAIused()){
            propValue = properties.getValue("showddmessage");
            if(properties != null && (propValue.length()>0 && propValue.equals("true"))){

                currentGUI.showMessageDialog(Messages.getMessage("PdfViewer.JAIWarning") +
                        Messages.getMessage("PdfViewer.JAIWarning1") +
                        Messages.getMessage("PdfViewer.JAIWarning2") +
                        Messages.getMessage("PdfViewer.JAIWarning3") +
                        Messages.getMessage("PdfViewer.JAIWarning4"));

                properties.setValue("showddmessage","false");
            }
        }

        //<start-wrap><start-pdfhelp>
        /**
         * check for itext and tell user about benefits
         */
        if(!suppressViewerPopups && !commonValues.isContentExtractor()){
            propValue = properties.getValue("showitextmessage");
            boolean showItextMessage = (propValue.length()>0 && propValue.equals("true"));

            if (!commonValues.isItextOnClasspath() && showItextMessage) {

//                currentGUI.showItextPopup();

                properties.setValue("showitextmessage","false");
            }
        }
        //<end-pdfhelp><end-wrap>

        if(currentGUI.isSingle()){
            TransferHandler singleViewTransferHandler = new SingleViewTransferHandler(commonValues, thumbnails, currentGUI, currentCommands);
            decode_pdf.setTransferHandler(singleViewTransferHandler);
        } else {
            TransferHandler multiViewTransferHandler = new MultiViewTransferHandler(commonValues, thumbnails, currentGUI, currentCommands);
            currentGUI.getMultiViewerFrames().setTransferHandler(multiViewTransferHandler);
        }

//		DefaultTransferHandler dth = new DefaultTransferHandler(commonValues, thumbnails, currentGUI, currentCommands);
//		decode_pdf.setTransferHandler(dth);

        boolean wasUpdateAvailable = false;


        propValue = properties.getValue("displaytipsonstartup");
        if(!suppressViewerPopups && !wasUpdateAvailable && propValue.length()>0 && propValue.equals("true")){
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    TipOfTheDay tipOfTheDay = new TipOfTheDay(currentGUI.getFrame(), "/it/eng/hybrid/module/jpedal/resources/tips", properties);
                    tipOfTheDay.setVisible(true);
                }
            });
        }
        
        //falg so we can warn user if thewy call executeCommand without it setup
        isSetup=true;
    }
	
	/**
	 * setup the viewer
	 */
	protected void init(ResourceBundle bundle) {

		/**
		 * load correct set of messages
		 */
		if(bundle==null){

			//load locale file
			try{
				//Messages.setBundle(ResourceBundle.getBundle("it.eng.hybrid.module.jpedal.messages"));
				Messages.setBundle(ResourceBundle.getBundle("messages"));
				
			}catch(Exception e){
				e.printStackTrace();
				logger.info("Exception "+e+" loading resource bundle.\n" +
						"Also check you have a file in it.eng.hybrid.module.jpedal.messages to support Locale="+java.util.Locale.getDefault());
			}

		}else{
            try{
			    Messages.setBundle(bundle);
            }catch(Exception ee){
                logger.info("Exception with bundle "+bundle);
                ee.printStackTrace();
            }
        }
		/**setup scaling values which ar displayed for user to choose*/
		this.scalingValues= new String[]{Messages.getMessage("PdfViewerScaleWindow.text"),Messages.getMessage("PdfViewerScaleHeight.text"),
				Messages.getMessage("PdfViewerScaleWidth.text"),
				"25%","50%","75%","100%","125%","150%","200%","250%","500%","750%","1000%"};

		/**
		 * setup display
		 */
		if(commonValues.isContentExtractor())
			if (SwingUtilities.isEventDispatchThread()) {

    			decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_LEFT_ALIGNED);

    		} else {
    			final Runnable doPaintComponent = new Runnable() {

    				public void run() {
    					decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_LEFT_ALIGNED);
    				}
    			};
    			SwingUtilities.invokeLater(doPaintComponent);
    		}
		//decode_pdf.setDisplayView(Display.SINGLE_PAGE,Display.DISPLAY_LEFT_ALIGNED);
		else
			if (SwingUtilities.isEventDispatchThread()) {

    			decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);

    		} else {
    			final Runnable doPaintComponent = new Runnable() {

    				public void run() {
    					decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);
    				}
    			};
    			SwingUtilities.invokeLater(doPaintComponent);
    		}
//		decode_pdf.setDisplayView(Display.SINGLE_PAGE,Display.DISPLAY_CENTERED);

		//pass through GUI for use in multipages and Javascript
		decode_pdf.addExternalHandler(currentGUI, Options.MultiPageUpdate);

		//used to test ability to replace Javascript with own engine
		//org.jpedal.objects.javascript.ExpressionEngine marksTest=new TestEngine();
		//decode_pdf.addExternalHandler(marksTest, Options.ExpressionEngine);

		/**debugging code to create a log*/
		//LogWriter.setupLogFile("v");
		//LogWriter.log_name =  "/mnt/shared/log.txt";

		/**/

		//make sure widths in data CRITICAL if we want to split lines correctly!!
		PdfDecoder.init(true);

		//<link><a name="customann" />
		/**
		 * ANNOTATIONS code 
		 * 
		 * replace Annotations with your own custom annotations using paint code
		 *
		 */



		//this allows the user to place fonts in the classpath and use these for display, as if embedded
		
		//set to extract all
		//COMMENT OUT THIS LINE IF USING JUST THE VIEWER
		decode_pdf.setExtractionMode(0,1); //values extraction mode,dpi of images, dpi of page as a factor of 72

		//don't extract text and images (we just want the display)

		/**/
		/**
		 * FONT EXAMPLE CODE showing JPedal's functionality to set values for
		 * non-embedded fonts.
		 *
		 * This allows sophisticated substitution of non-embedded fonts.
		 *
		 * Most font mapping is done as the fonts are read, so these calls must
		 * be made BEFORE the openFile() call.
		 */

		//<link><a name="fontmapping" />
		/**
		 * FONT EXAMPLE - Replace global default for non-embedded fonts.
		 *
		 * You can replace Lucida as the standard font used for all non-embedded and substituted fonts
		 * by using is code.
		 * Java fonts are case sensitive, but JPedal resolves currentGUI.frame, so you could
		 * use Webdings, webdings or webDings for Java font Webdings
		 */

                /** Removed to save time on startup - uncomment if it causes problems
		try{
			//choice of example font to stand-out (useful in checking results to ensure no font missed.
			//In general use Helvetica or similar is recommended
//			decode_pdf.setDefaultDisplayFont("SansSerif");
		}catch(PdfFontException e){ //if its not available catch error and show valid list

			logger.info(e.getMessage());

			//get list of fonts you can use
			String[] fontList =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			logger.info(Messages.getMessage("PdfViewerFontsFound.message"));
			logger.info("=====================\n");
			int count = fontList.length;
			for (int i = 0; i < count; i++) {
				Font f=new Font(fontList[i],1,10);
				logger.info(fontList[i]+" ("+Messages.getMessage("PdfViewerFontsPostscript.message")+ '=' +f.getPSName()+ ')');

			}
			System.exit(1);

		}/***/

		/**
		 * IMPORTANT note on fonts for EXAMPLES
		 *
		 * USEFUL TIP : The Viewer displays a list of fonts used on the
		 * current PDF page with the File > Fonts menu option.
		 *
		 * PDF allows the use of weights for fonts so Arial,Bold is a weight of
		 * Arial. This value is not case sensitive so JPedal would regard
		 * arial,bold and aRiaL,BoLd as the same.
		 *
		 * Java supports a set of Font families internally (which may have
		 * weights), while JPedals substitution facility uses physical True Type
		 * fonts so it is resolving each font weight separately. So mapping
		 * works differently, depending on which is being used.
		 *
		 * If you are using a font, which is named as arial,bold you can use
		 * either arial,bold or arial (and JPedal will then try to select the
		 * bold weight if a Java font is used).
		 *
		 * So for a font such as Arial,Bold JPedal will test for an external
		 * truetype font substitution (ie arialMT.ttf) mapped to Arial,Bold. BUT
		 * if the substitute font is a Java font an additional test will be made
		 * for a match against Arial if there is no match on Arial,Bold.
		 *
		 * If you want to map all Arial to equivalents to a Java font such as
		 * Times New Roman, just map Arial to Times New Roman (only works for
		 * inbuilt java fonts). Note if you map Arial,Bold to a Java font such
		 * as Times New Roman, you will get Times New Roman in a bold weight, if
		 * available. You cannot set a weight for the Java font.
		 *
		 * If you wish to substitute Arial but not Arial,Bold you should
		 * explicitly map Arial,Bold to Arial,Bold as well.
		 *
		 * The reason for the difference is that when using Javas inbuilt fonts
		 * JPedal can resolve the Font Family and will try to work out the
		 * weight internally. When substituting Truetype fonts, these only
		 * contain ONE weight so JPedal is resolving the Font and any weight as
		 * a separate font . Different weights will require separate files.
		 *
		 * Open Source version does not support all font capabilities.
		 */

		/**
		 * FONT EXAMPLE - Use fonts placed in jar for substitution (1.4 and above only)
		 *
		 * This allows users to store fonts in the jar and use these for
		 * substitution. Please see javadoc for full description of usage.
		 */
		//decode_pdf.addSubstituteFonts(fontPath,enforceMapping)

		//<link><a name="substitutedfont" />
		/**
		 * FONT EXAMPLE - Use fonts located on machine for substitution
		 *
		 * This code explains how to use JPedal to substitute fonts which are
		 * not embedded using fonts held in any font directory.
		 *
		 * It works as follows:-
		 *
		 * If the -Dorg.jpedal.fontdirs="C:/win/fonts/","/mnt/X11/fonts" is set to a
		 * comma-separated list of directories, any truetype fonts (with .ttf
		 * file ending) will be logged and added to the substitution table. So
		 * arialMT.ttf will be added as arialmt. If arialmt is used in the PDF
		 * but not embedded, JPedal will use this font file to render it.
		 *
		 * If a command line paramter is not appropriate, the call
		 * setFontDirs(String[] fontDirs) will achieve the same.
		 *
		 *
		 * If the name is not an exact match (ie you have arialMT which you wish
		 * to use to display arial, you can use the method
		 * setSubstitutedFontAliases(String[] name, String[] aliases) to convert
		 * it internally - see sample code at bottom of note.
		 *
		 * The Name is not case-sensitive.
		 *
		 * Spaces are important so TimesNewRoman and Times New Roman are
		 * degarded as 2 fonts.
		 *
		 * If you have 2 copies of arialMT.ttf in the scanned directories, the
		 * last one will be used.
		 *
		 * If the file was called arialMT,bold.ttf it is resolved as
		 * ArialMT,bold only.
		 *
		 */

		//mappings for non-embedded fonts to use
		FontMappings.setFontReplacements();
		
		//decode_pdf.setFontDirs(new String[]{"C:/windows/fonts/","C:/winNT/fonts/"});
		/**
		 * FONT EXAMPLE - Use Standard Java fonts for substitution
		 *
		 * This code tells JPedal to substitute fonts which are not embedded.
		 *
		 * The Name is not case-sensitive.
		 *
		 * Spaces are important so TimesNewRoman and Times New Roman are
		 * degarded as 2 fonts.
		 *
		 * If you have 2 copies of arialMT.ttf in the scanned directories, the
		 * last one will be used.
		 *
		 *
		 * If you wish to use one of Javas fonts for display (for example, Times
		 * New Roman is a close match for myCompanyFont in the PDF, you can the
		 * code below
		 *
		 * String[] aliases={"Times New Roman"};//,"helvetica","arial"};
		 * decode_pdf.setSubstitutedFontAliases("myCompanyFont",aliases);
		 *
		 * Here is is used to map Javas Times New Roman (and all weights) to
		 * TimesNewRoman.
		 *
		 * This can also be done with the command -org.jpedal.fontmaps="TimesNewRoman=Times New Roman","font2=pdfFont1"
		 */
		//String[] nameInPDF={"TimesNewRoman"};//,"helvetica","arial"};
		//decode_pdf.setSubstitutedFontAliases("Times New Roman",nameInPDF);

		//<link><a name="imageHandler" />
		/**
		 * add in external handlers for code - 2 examples supplied
		 *

        //org.jpedal.external.ImageHandler myExampleImageHandler=new org.jpedal.examples.handlers.ExampleImageDecodeHandler();
        org.jpedal.external.ImageHandler myExampleImageHandler=new org.jpedal.examples.handlers.ExampleImageDrawOnScreenHandler();

        decode_pdf.addExternalHandler(myExampleImageHandler, Options.ImageHandler);


         /**/
//<link><a name="customMessageOutput" />
        /**
         * divert all message to our custom code
         *

        CustomMessageHandler myExampleCustomMessageHandler =new ExampleCustomMessageHandler();

        decode_pdf.addExternalHandler(myExampleCustomMessageHandler, Options.CustomMessageOutput);
   
         /**/

	}

	/**private boolean showMenu(String input){
		//Check for disabled options

		if(restrictedMenus!=null)
			for(int i=0; i!=restrictedMenus.length; i++)
				if(((String)restrictedMenus[i]).toLowerCase().equals(input.toLowerCase()))
					return true;
		return false;
	}/**/

	/**
	 * create items on drop down menus
	 */
	protected void createSwingMenu(boolean includeAll) {
		currentGUI.createMainMenu(includeAll);
	}

	/** main method to run the software as standalone application */
	public static void main(String[] args) {

		/**
		 * set the look and feel for the GUI components to be the
		 * default for the system it is running on
		 */
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}catch (Exception e) { 
			logger.info("Exception " + e + " setting look and feel");
		}
		
		Viewer current = new Viewer();
		
		PreferenceManager preferenceManager = new PreferenceManager();
		preferenceManager.initConfig(args);
		current.commonValues.setPreferenceManager(preferenceManager);
		
		boolean logEnabled = false;
		try {
			String logEnabledString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_LOGENABLED);
			logEnabled = Boolean.valueOf( logEnabledString );
			System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGENABLED + ": " + logEnabled);
		} catch (Exception e) {}
		
		if( logEnabled ){
			
			boolean logFileEnabled = false;
			try {
				logFileEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGFILEENABLED);
				System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_LOGFILEENABLED );
			}
			boolean logArrayEnabled = false;
			try {
				logArrayEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGARRAYENABLED);
				System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_LOGARRAYENABLED );
			}
			if( logFileEnabled ){
				String userLogDirPath = System.getProperty("user.home")+"/log";
				File userLogDir = new File(userLogDirPath);
				if( !userLogDir.exists() )
					userLogDir.mkdir();
				String userLogFilePath = System.getProperty("user.home")+"/log/jpedal.log";
				File fileLog = new File(userLogFilePath);
				if( fileLog.exists() ) 
					fileLog.delete();
				logger.info("File di logging " + userLogFilePath);
			}
			
		}
		
		Security.addProvider(new BouncyCastleProvider());
		logger.info("Caricamento provider in corso!");
		
		
		String props = null;
		try {
			props = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEPROPERTIES );
			logger.info("Proprietà " + ConfigConstants.PROPERTY_FILEPROPERTIES + " " + props );
		} catch (Exception e) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_FILEPROPERTIES );
		}
		
		if (props != null) {
			logger.info("Carico la configurazione " + props);
			current.loadProperties(props);
		} else {
			current.loadProperties(Viewer.PREFERENCES_ENGINEERING_EXTENDED);
		}
		//current.loadProperties(Viewer.PREFERENCES_ENGINEERING_EXTENDED);
		
		boolean readOnly=true;
		try {
			String readOnlyString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_READONLY ) ;
			if( readOnlyString!=null )
				readOnly  = Boolean.valueOf( readOnlyString ); 
			logger.info("Visualizzazione in sola lettura? " + readOnly);
		} catch (Exception e) {
			logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_READONLY );
		}
		
		if( !readOnly ) {
			try {
				String printEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PRINTENABLED );
				logger.info("Proprietà " + ConfigConstants.PROPERTY_PRINTENABLED + ": " + printEnabled);
				if( printEnabled!=null ){
					current.commonValues.setPrintEnabled( Boolean.valueOf( printEnabled ) );
				}
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_PRINTENABLED );
			}
		} else {
			current.commonValues.setPrintEnabled( false );
		}
		
		if( !readOnly ) {
			try {
				String saveEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SAVEENABLED);
				logger.info("Proprietà " + ConfigConstants.PROPERTY_SAVEENABLED + ": " + saveEnabled);
				if( saveEnabled!=null ){
					current.commonValues.setSaveEnabled( Boolean.valueOf( saveEnabled ) );
				}
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_SAVEENABLED );
			}
		} else {
			current.commonValues.setSaveEnabled( false );
		}
		
		if( !readOnly ) {
			try {
				String signEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SIGNENABLED );
				logger.info("Proprietà " + ConfigConstants.PROPERTY_SIGNENABLED + ": " + signEnabled);
				if( signEnabled!=null ){
					current.commonValues.setSignEnabled( Boolean.valueOf( signEnabled ) );
				}
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_SIGNENABLED );
			}
		} else {
			current.commonValues.setSignEnabled( false );
		}
		
		if( !readOnly ) {
			try {
				String markEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MARKENABLED );
				logger.info("Proprietà " + ConfigConstants.PROPERTY_MARKENABLED + ": " + markEnabled);
				if( markEnabled!=null ){
					current.commonValues.setMarkEnabled( Boolean.valueOf( markEnabled ) );
				}
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_MARKENABLED );
			}
		} else {
			current.commonValues.setMarkEnabled( false );
		}
		
		if( !readOnly ) {
			try {
				String timbroEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ENABLED ) ;
				logger.info("Proprietà " + ConfigConstants.TIMBRO_PROPERTY_ENABLED + ": " + timbroEnabled);
				if( timbroEnabled!=null ){
					current.commonValues.setTimbroEnabled( Boolean.valueOf( timbroEnabled ) );
				}
				
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_ENABLED );
			}
		}else {
			current.commonValues.setTimbroEnabled( false );
		}
			
		current.setupViewer();
		
		//<start-wrap>
//		if (args.length > 0){
//			current.openDefaultFile(args[0]);
//            
//        }else if(current.properties.getValue("openLastDocument").toLowerCase().equals("true")){
//			if(current.properties.getRecentDocuments()!=null
//					&& current.properties.getRecentDocuments().length>1){
//
//				int lastPageViewed = Integer.parseInt(current.properties.getValue("lastDocumentPage"));
//
//				if(lastPageViewed<0)
//					lastPageViewed=1;
//
//				current.openDefaultFileAtPage(current.properties.getRecentDocuments()[0],lastPageViewed);
//			}
//            
//		}
		/**
		//<end-wrap>
		current.openDefaultFile();
		/**/
		//openDefaultFile("");
		openFile(current);
	}
	
	private static void openFile(Viewer current){
		PreferenceManager preferenceManager = current.commonValues.getPreferenceManager();
		String fileInputProviderClass = null;
        try {
        	fileInputProviderClass = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEINPUT_PROVIDER );
        	logger.info("Verrà utilizzato il fileInputProvider " + fileInputProviderClass);
        } catch (Exception e2) {
        	logger.info("Errore - Non è stato possibile recuperare il fileInputProvider da utilizzare");
        	return;
        }
        if( fileInputProviderClass==null ){
        	logger.info("Errore - Non è stato configurato il fileInputProvider da utilizzare");
			return;
        }
        	
        FileInputProvider fileInputProvider = null;
        try {
			Class cls = Class.forName(fileInputProviderClass);
			fileInputProvider = (FileInputProvider) cls.newInstance();
		} catch (InstantiationException e) {
			logger.info("Errore nel recupero del fileInputProvider: " + e.getMessage());
			return;
		} catch (IllegalAccessException e) {
			logger.info("Errore nel recupero del fileInputProvider: " + e.getMessage());
			return;
		}catch (ClassNotFoundException e) {
			logger.info("Errore nel recupero del fileInputProvider: " + e.getMessage());
			return;
		}
        
        String fileOutputProviderClass = null;
        try {
        	fileOutputProviderClass = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOUTPUT_PROVIDER );
        	logger.info("Verrà utilizzato il fileOutputProvider " + fileOutputProviderClass);
        } catch (Exception e2) {
        	logger.info("Errore - Non è stato possibile recuperare il fileOutputProvider da utilizzare");
        }
        if( fileOutputProviderClass==null ){
        	logger.info("Errore - Non è stato configurato il fileOutputProvider da utilizzare");
			//return;
        } else {
	        FileOutputProvider fileOutputProvider = null;
	        try {
				Class cls = Class.forName(fileOutputProviderClass);
				fileOutputProvider = (FileOutputProvider) cls.newInstance();
				fileOutputProvider.saveOutputParameter(preferenceManager);
				current.commonValues.setFileOutputProvider(fileOutputProvider);
			} catch (InstantiationException e) {
				logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
				return;
			} catch (IllegalAccessException e) {
				logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
				return;
			}catch (ClassNotFoundException e) {
				logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
				return;
			} catch (Exception e) {
				logger.info("Errore nel salvataggio dei parametri del fileOutputProvider: " + e.getMessage());
				return;
			}
        }
        
        FileInputResponse fileInputProviderResponse;
		try {
			fileInputProviderResponse = fileInputProvider.getFile(preferenceManager);
			byte[] fileContent = fileInputProviderResponse.getFileContent();
			String filename = fileInputProviderResponse.getFileName();
			File file = fileInputProviderResponse.getFile();
			current.commonValues.setFileInputDir( file.getParentFile() );
			if( fileContent!=null && filename!=null)
				current.executeCommand( Commands.OPENFILE,new Object[]{fileContent, filename});
			else if( file!=null)
				current.executeCommand( Commands.OPENFILE,new Object[]{file});
		} catch (Exception e) {
			logger.info("Errore: " + e.getMessage());
		}
	}

	/**
	 * General code to open file at specified boomark - do not call directly
	 *  
	 * @param file File the PDF to be decoded
	 * @param bookmark - if not present, exception will be thrown
	 */
	private void openFile(File file, String bookmark) {

		try{

			boolean fileCanBeOpened=currentCommands.openUpFile(file.getCanonicalPath());

            Object bookmarkPage=null;

            int page=-1;

			//reads tree and populates lookup table
            if(decode_pdf.getOutlineAsXML()!=null){
                Node rootNode= decode_pdf.getOutlineAsXML().getFirstChild();
                if(rootNode!=null)
                    bookmarkPage=currentGUI.getBookmark(bookmark);

                if(bookmarkPage!=null)
                    page=Integer.parseInt((String)bookmarkPage);
            }

            //it may be a named destination ( ie bookmark=Test1)
            if(bookmarkPage==null){
                bookmarkPage=decode_pdf.getIO().convertNameToRef(bookmark); 

                if(bookmarkPage!=null){

                    //read the object
                    PdfObject namedDest=new OutlineObject((String)bookmarkPage);
                    decode_pdf.getIO().readObject(namedDest);

                    //still needed to init viewer
                    if(fileCanBeOpened)
                        currentCommands.processPage();

                    //and generic open Dest code
                    decode_pdf.getFormRenderer().getActionHandler().gotoDest(namedDest, ActionHandler.MOUSECLICKED, PdfDictionary.Dest );
                }
            }

			if(bookmarkPage==null)
				throw new PdfException("Unknown bookmark "+bookmark);


            if(page>-1){
                commonValues.setCurrentPage(page);
                if(fileCanBeOpened)
                    currentCommands.processPage();
            }
		}catch(Exception e){
			System.err.println("Exception " + e + " processing file");


			Values.setProcessing(false);
		}
	}

	/**
	 * General code to open file at specified page - do not call directly
	 *  
	 * @param file File the PDF to be decoded
	 * @param page int page number to show the user
	 */
	private void openFile(File file, int page) {

		try{
			boolean fileCanBeOpened=currentCommands.openUpFile(file.getCanonicalPath());

			commonValues.setCurrentPage(page);

			if(fileCanBeOpened)
				currentCommands.processPage();
		}catch(Exception e){
			System.err.println("Exception " + e + " processing file"); 


			Values.setProcessing(false);
		}
	}
	
	/**
	 * Execute Jpedal functionality from outside of the library using this method.
	 * EXAMPLES
	 *    commandID = Commands.OPENFILE, args = {"/PDFData/Hand_Test/crbtrader.pdf}"
	 *    commandID = Commands.OPENFILE, args = {byte[] = {0,1,1,0,1,1,1,0,0,1}, "/PDFData/Hand_Test/crbtrader.pdf}"
	 *    commandID = Commands.ROTATION, args = {"90"}
	 *    commandID = Commands.OPENURL,  args = {"http://www.cs.bham.ac.uk/~axj/pub/papers/handy1.pdf"}
	 *
	 * @param commandID :: static int value from Commands to spedify which command is wanted
	 * @param args :: arguements for the desired command
	 * 
	 */
	public Object executeCommand(int commandID, Object[] args){
		/**
		 * far too easy to miss this step (I did!) so warn user
		 */
//		if(!isSetup){
//			throw new RuntimeException("You must call viewer.setupViewer(); before you call any commands");
//		}
		//custom
		commonValues.setExtendedApplet(this.isExtendedApplet());
		return currentCommands.executeCommand(commandID, args);
	}
	
	public SearchList getSearchResults(){
		return currentCommands.getSearchList();
	}
	
	public boolean isProcessing(){
		return Values.isProcessing();
	}
	
	/**
     * Allows external helper classes to be added to JPedal to alter default functionality.
     * <br><br>If Options.FormsActionHandler is the type then the <b>newHandler</b> should be
     * of the form <b>org.jpedal.objects.acroforms.ActionHandler</b>
     * <br><br>If Options.JPedalActionHandler is the type then the <b>newHandler</b> should be
     * of the form <b>Map</b> which contains Command Integers, mapped onto their respective
     * <b>org.jpedal.examples.viewer.gui.swing.JPedalActionHandler</b> implementations.  For example,
     * to create a custom help action, you would add to your map, Integer(Commands.HELP) ->  JPedalActionHandler.
     * For a tutorial on creating custom actions in the Viewer, see
     * <b>http://www.jpedal.org/support.php</b>
     *
     * @param newHandler
     * @param type
     */
    public void addExternalHandler(Object newHandler, int type) {
    	decode_pdf.addExternalHandler(newHandler, type);
    }

	public boolean isExtendedApplet() {
		return isExtendedApplet;
	}

	public void setExtendedApplet(boolean isExtendedApplet) {
		this.isExtendedApplet = isExtendedApplet;
	}
	
	public void dispose(){
		
		currentPrinter=null;
		
		if( decode_pdf!=null )
			decode_pdf.dispose();
        decode_pdf = null;
		
        if( thumbnails!=null)
        	thumbnails.dispose();
        thumbnails=null;
        
        if( properties!=null )
        	properties.dispose();
		properties=null;
		
		if( searchFrame!=null )
			searchFrame.dispose();
		searchFrame=null;
		
//		if( timbroFrame!=null )
//			timbroFrame.dispose();
//		timbroFrame=null;
		
		if(mouseHandler!=null)
			mouseHandler.dispose();
		mouseHandler=null;
		
		scalingValues=null;
		rawFile=null;
		
		//commonValues.setCurrentApplet(null);
        commonValues.setFileOutputProvider(null);
        if( commonValues!=null )
			commonValues.dispose();
		commonValues=null;
        
        if( currentCommands!=null)
        	currentCommands.dispose();
        currentCommands=null;
        
        if( currentGUI!=null )
        	currentGUI.dispose();
        currentGUI=null;
        
        //Messages.dispose();
	}

}
