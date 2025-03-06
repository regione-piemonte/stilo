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
* GUI.java
* ---------------
*/

package it.eng.hybrid.module.jpedal.ui;

import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.preferences.PropertiesFile;
import it.eng.hybrid.module.jpedal.ui.generic.GUIButton;
import it.eng.hybrid.module.jpedal.ui.generic.GUICombo;
import it.eng.hybrid.module.jpedal.ui.generic.GUIOutline;
import it.eng.hybrid.module.jpedal.ui.generic.GUIThumbnailPanel;
import it.eng.hybrid.module.jpedal.ui.swing.SwingOutline;
import it.eng.hybrid.module.jpedal.viewer.Values;
import it.eng.hybrid.module.jpedal.viewer.Viewer;

import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


/**any shared GUI code - generic and AWT*/
public class GUI {
	
	/**nav buttons - global so accessible to ContentExtractor*/
	public GUIButton first,fback,back,forward,fforward,end;
	
	public GUIButton singleButton,continuousButton,continuousFacingButton,facingButton,pageFlowButton;

    /**list for types - 
	 * "OTHER" MUST BE FIRST ITEM
	 * Try adding Link to the list to see links
	 */
	private String[] annotTypes={"Other","Text","FileAttachment"};

	//private Color[] annotColors={Color.RED,Color.BLUE,Color.BLUE};
	
	protected boolean hiResPrinting = false;
	
	//@annot - table of objects we wish to track
	protected Map objs;

    //flag if generated so we setup once for each file
    protected boolean bookmarksGenerated=false;

    public boolean useHiResPrinting() {
		return hiResPrinting;
	}

	public void setHiResPrinting(boolean hiResPrinting) {
		this.hiResPrinting = hiResPrinting;
	}
	
	public String getPropertiesFileLocation(){
		return properties.getConfigFile();
	}
	
	public void setPropertiesFileLocation(String file){
		properties.loadProperties(file);
	}
	
	public void setProperties(String item, boolean value){
		properties.setValue(item, String.valueOf(value));
	}
		
	public void setPreferences(int dpi, int search, int border, boolean scroll, int pageMode, boolean updateDefaultValue, int maxNoOfMultiViewers, boolean showDownloadWindow, boolean useHiResPrinting){
		
		//Set border config value and repaint
        SingleDisplay.CURRENT_BORDER_STYLE = border;
		properties.setValue("borderType", String.valueOf(border));
		
		//Set autoScroll default and add to properties file
		allowScrolling = scroll;
		properties.setValue("autoScroll", String.valueOf(scroll));
		
		//Dpi is taken into effect when zoom is called
		decode_pdf.getDPIFactory().setDpi(dpi);
		properties.setValue("resolution", String.valueOf(dpi));
		
		//@kieran Ensure valid value if not recognised
		if(pageMode<Display.SINGLE_PAGE || pageMode>Display.CONTINUOUS_FACING)
			pageMode = Display.SINGLE_PAGE;
		
		//Default Page Layout
		decode_pdf.setPageMode(pageMode);
		properties.setValue("pageMode", String.valueOf(pageMode));
		
		decode_pdf.repaint();
		
		//Set the search window
		String propValue = properties.getValue("searchWindowType");
		if(propValue.length()>0 && !propValue.equals(String.valueOf(search))){
            if(Viewer.showMessages)
			JOptionPane.showMessageDialog(null, Messages.getMessage("PageLayoutViewMenu.ResetSearch"));
        }
		properties.setValue("searchWindowType", String.valueOf(search));

		properties.setValue("automaticupdate", String.valueOf(updateDefaultValue));
		
		commonValues.setMaxMiltiViewers(maxNoOfMultiViewers);
		properties.setValue("maxmultiviewers", String.valueOf(maxNoOfMultiViewers));
		
		useDownloadWindow = showDownloadWindow;
		properties.setValue("showDownloadWindow", String.valueOf(showDownloadWindow));
		
		hiResPrinting = useHiResPrinting;
		properties.setValue("useHiResPrinting", String.valueOf(showDownloadWindow));
		
	}
	
	
	
	/**handle for internal use*/
	protected PdfDecoder decode_pdf;
	
	/** location for divider with thumbnails turned on */
	protected static final int thumbLocation=200;
	
	/** minimum screen width to ensure menu buttons are visible */
	protected static final int minimumScreenWidth=700;
	
	/**track pages decoded once already*/
	protected HashMap pagesDecoded=new HashMap();

	/**allows user to toggle on/off text/image snapshot*/
	public  GUIButton snapshotButton;

	
	
	public int cropX;

	public int cropW;

	public int cropH;

	/**crop offset if present*/
	protected int mediaX,mediaY;

	public int mediaW;

	public int cropY;

	public int mediaH;
	
	/**Use Download Windom*/
	protected boolean useDownloadWindow = true;
	
	/**show if outlines drawn*/
	protected boolean hasOutlinesDrawn=false;
	
	/**XML structure of bookmarks*/
	protected GUIOutline tree=new SwingOutline();
	
	/**stops autoscrolling at screen edge*/
	protected boolean allowScrolling=true;

    /**confirms exit when closing the window*/
    protected boolean confirmClose=false;

	/** location for the divider when bookmarks are displayed */
	protected int divLocation=170;
	
	/**flag to switch bookmarks on or off*/
	protected boolean showOutlines=true;
	
	/**scaling values as floats to save conversion*/
	protected float[] scalingFloatValues={1.0f,1.0f,1.0f,.25f,.5f,.75f,1.0f,1.25f,1.5f,2.0f,2.5f,5.0f,7.5f,10.0f};
	
	/**page scaling to use 1=100%*/
	protected float scaling = 1;
	
	/** padding so that the pdf is not right at the edge */
	protected static int inset=25;
	
	/**store page rotation*/
	protected int rotation=0;
	
	/**scaling values as floats to save conversion*/
	protected String[] rotationValues={"0","90","180","270"};
	
	/**scaling factors on the page*/
	protected GUICombo rotationBox;

	/**allows user to set quality of images*/
	protected GUICombo qualityBox;
	
	/**scaling factors on the page*/
	protected GUICombo scalingBox;
	
	/**default scaling on the combobox scalingValues*/
	protected static int defaultSelection=0;

	/**title message on top if you want to over-ride JPedal default*/
	protected String titleMessage=null;
	
	protected Values commonValues;
	
	protected GUIThumbnailPanel thumbnails;
	
	protected PropertiesFile properties;
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#allowScrolling()
	 */
	public boolean allowScrolling() {
		return allowScrolling;
	}

    /* (non-Javadoc)
     * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#confirmClose()
     */
    public boolean confirmClose() {
        return confirmClose;
    }
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getAnnotTypes()
	 */
	public String[] getAnnotTypes() {
		
		return this.annotTypes;
	}
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#setAutoScrolling(boolean allowScrolling)
	 */
	public void setAutoScrolling(boolean allowScrolling) {
		this.allowScrolling=allowScrolling;
		
	}
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#toogleAutoScrolling()
	 */
	public void  toogleAutoScrolling(){
		allowScrolling=!allowScrolling;
	}
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getRotation()
	 */
	public int getRotation() {
		return rotation;
	}
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getScaling()
	 */
	public float getScaling() {
		return scaling;
	}
	
	public void setScaling(float s){
		scaling = s;
		scalingBox.setSelectedIndex((int)scaling);
	}
	
	/* (non-Javadoc)
	 * @see org.jpedal.examples.viewer.gui.swing.GUIFactory#getPDFDisplayInset()
	 */
	public static int getPDFDisplayInset() {
		return inset;
	}

	//<link><a name="handleAnnotations" />
	/** example code which sets up an individual icon for each annotation to display - only use
	 * if you require each annotation to have its own icon<p>
	 * To use this you ideally need to parse the annotations first -there is a method allowing you to
	 * extract just the annotations from the data.
	 */
	public void createUniqueAnnotationIcons() {


	}

	public void setDpi(int dpi) {
		decode_pdf.getDPIFactory().setDpi(dpi);
	}
	
	public boolean isUseDownloadWindow() {
		return useDownloadWindow;
	}

	public void setUseDownloadWindow(boolean useDownloadWindow) {
		this.useDownloadWindow = useDownloadWindow;
	}

	public PropertiesFile getProperties() {
		return properties;
	}
	
	
	
	public Values getCommonValues() {
		return commonValues;
	}

	public void setCommonValues(Values commonValues) {
		this.commonValues = commonValues;
	}

	public void dispose(){
		
		disposeButton();

	    annotTypes=null;
	    objs=null;

	    if( decode_pdf!=null )
			decode_pdf.dispose();
		decode_pdf=null;
		
		pagesDecoded=null;
		
		tree=null;
		scalingFloatValues=null;
		rotationValues=null;
		
		rotationBox=null;
		qualityBox=null;
		scalingBox=null;
		
		titleMessage=null;
				
		if( commonValues!=null)
			commonValues.dispose();
		commonValues=null;
		
		if( properties!=null ) 
			properties.dispose();
		properties=null;
		
	}
	
	protected void disposeButton(){
		if( snapshotButton!=null)
			removeListeners( (JComponent)snapshotButton );
		snapshotButton=null;
		
		if( first!=null ){
			removeListeners( (JComponent)first );
		}
		first=null;
		
		if( fback!=null ){
			removeListeners( (JComponent)fback );
		}
		fback=null;
		
		if( back!=null ){
			removeListeners( (JComponent)back );
		}
		back=null;
		
		if( forward!=null ){
			removeListeners( (JComponent)forward );
		}
		forward=null;
		
		if( fforward!=null ){
			removeListeners( (JComponent)fforward );
		}
		fforward=null;
		
		if( end!=null ){
			removeListeners( (JComponent)end );
		}
		end=null;
		
		if( singleButton!=null ){
			removeListeners( (JComponent)singleButton );
		}
		singleButton=null;
		
		if( continuousButton!=null ){
			removeListeners( (JComponent)continuousButton );
		}
		continuousButton=null;
		
		if( continuousFacingButton!=null ){
			removeListeners( (JComponent)continuousFacingButton );
		}
		continuousFacingButton=null;

		if( facingButton!=null ){
			removeListeners( (JComponent)facingButton );
		}
		facingButton=null;
		
		if( pageFlowButton!=null ){
			removeListeners( (JComponent)pageFlowButton );
		}
		pageFlowButton=null;
	}
	
	protected static void removeListeners(JComponent component){
		if( component instanceof JButton)
			for(ActionListener l : ((JButton) component).getActionListeners()){
				((JButton) component).removeActionListener(l);
			}
		
		ComponentListener[] list = component.getComponentListeners();
		for(ComponentListener l : list){
			component.removeComponentListener(l);
		}
		MouseListener[] list1 = component.getMouseListeners();
		for(MouseListener l : list1){
			component.removeMouseListener(l);
		}
		MouseMotionListener[] list2 = component.getMouseMotionListeners();
		for(MouseMotionListener l : list2){
			component.removeMouseMotionListener(l);
		}
		MouseWheelListener[] list3 = component.getMouseWheelListeners();
		for(MouseWheelListener l : list3){
			component.removeMouseWheelListener(l);
		}
		FocusListener[] list4 = component.getFocusListeners();
		for(FocusListener l : list4){
			component.removeFocusListener(l);
		}
		KeyListener[] list5 = component.getKeyListeners();
		for(KeyListener l : list5){
			component.removeKeyListener(l);
		}
		PropertyChangeListener[] list6 = component.getPropertyChangeListeners();
		for(PropertyChangeListener l : list6){
			component.removePropertyChangeListener(l);
		}
		
	}
	
}
