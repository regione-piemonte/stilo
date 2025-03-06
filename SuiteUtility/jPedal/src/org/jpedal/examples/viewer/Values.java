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
* Values.java
* ---------------
*/
package org.jpedal.examples.viewer;

import it.eng.fileOperation.clientws.FileOperationResponse;

import java.io.File;

import javax.swing.JApplet;

import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.examples.viewer.fileProvider.FileOutputProvider;
import org.jpedal.examples.viewer.fileProvider.SalvaFileProvider;


/**provides access to values used by multiple classes*/
public class Values {
	
	/**Normal mode (works for webstart, application)*/
	public static final int RUNNING_NORMAL = 0;
	public static final int RUNNING_APPLET = 1;
	public static final int RUNNING_WEBSTART = 2;
	public static final int RUNNING_JSP = 3;
	public static final int RUNNING_PLUGIN = 4;

	/**tells software if running Viewer or ContentExtractor*/
	private boolean isContentExtractor;
	
	/**flag to show if itext is available*/
	private boolean isItextOnClasspath = false;
	
	/**flag to show if an encryption library is available*/
    private boolean isEncryptOnClasspath = false;
	
	/**flag to show if file opened is PDF or not*/
	private boolean isPDF=true;
	
	/**flag to show if the file opened is a Tiff with multiple pages or not*/
	private boolean isMultiTiff=false;
	
	/**allow common code to be aware if applet or webstart or JSP*/
	private int modeOfOperation=RUNNING_NORMAL;
	
	/**size of file for display*/
	private long size;
	
	/**directory to load files from*/
	private  String inputDir =null;
	private  File fileInputDir =null;
	private  String fileNameConvertito = null;
	
	/**current page number*/
	private int currentPage = 1;
	
	/**name of current file being decoded*/
	private String selectedFile = null;
	private File originalFile = null;
	
	/**flag to show that form values have been altered by user*/
	private boolean formsChanged;
	
	/**temp location to store output, if needed*/
	private String target;
	
	/**file separator used*/
	private String separator=null;
	
	/**uses hires images for display (uses more memory)*/
	private boolean useHiresImage=true;
	
	public int m_x1, m_y1, m_x2, m_y2;
	
	/**offsets to viewport if used*/
	public int dx,dy=0;
	
	/**scaling on viewport if used*/
	public double viewportScale=1;
	
	/**height of the viewport. Because everything is draw upside down we need this 
	 * to get true y value*/
	public int maxViewY=0;
		
	/**number of pages in current pdf 
	 * (inclusive so 2 page doc would have 2 with first page as 1)*/
	private int pageCount = 1;
	private int maxNoOfMultiViewers;
	
	/**boolean lock to stop multiple access*/
	public static boolean isProcessing = false;
	/**/
	//upescato
	/**boolean flag che identifica se l'istanza corrente di applet � un'istanza estesa*/
	private boolean isExtendedApplet;
	private JApplet currentApplet;
	
	/**array di byte per storare in memoria il file in corso di visualizzazione, al fine di consentirne un eventuale salvataggio su disco.*/
	private byte[] byteData;
	
	//private OpzioniTimbro opzioniTimbro;
	private Boolean timbroEnabled;
	private Boolean saveEnabled;
	private Boolean printEnabled;
	private Boolean signEnabled;
	private Boolean markEnabled;
	
	private FileOutputProvider fileOutputProvider;
	private PreferenceManager preferenceManager;
	private boolean weSigned = false;
	private FileOperationResponse responseVerificaFirme;
	
	private boolean externalSaveAs = false;
	private SalvaFileProvider salvaFileProvider;
	
	/**/
    public Values(){
        try{
            isItextOnClasspath = getClass().getResource("/com/itextpdf") != null;
        }catch(Exception e){
            isItextOnClasspath=false;
        }catch(Error e){
            isItextOnClasspath=false;
        }
        
        String altSP=System.getProperty("org.jpedal.securityprovider");
 	        
        try {
        	if(altSP==null)
        		altSP="/org/bouncycastle/";
        	
        	isEncryptOnClasspath = getClass().getResource(altSP) != null; 
        }catch(Exception e){
        	isEncryptOnClasspath=false;
        }catch(Error e){
        	isEncryptOnClasspath=false;
        }
    }

	/**
	 *flag to show isProcessing so Viewer can lock actions while decoding page
	 */
	public static boolean isProcessing() {
		return isProcessing;
	}
	
	/**
	 * set to show decoding page 
	 */
	public static void setProcessing(boolean isProcessing) {
		Values.isProcessing = isProcessing;
	}
	
	/**
	 * show if itext installed
	 */
	public boolean isItextOnClasspath() {
		return isItextOnClasspath;
	}
	
	public boolean isEncrypOnClasspath() {
		return isEncryptOnClasspath;
	}
	
	/**
	 * show if file is type PDF
	 */
	public boolean isPDF() {
		return isPDF;
	}
	
	/**
	 * set flag to show if file is PDF or other
	 */
	public void setPDF(boolean isPDF) {
		this.isPDF = isPDF;
	}
	
	/**
	 *get current page number (1 - pageCount)
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * set current page number (1 - pageCount)
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	/**
	 * get directory to use as input root
	 */
	public String getInputDir() {

        //<start-wrap>
        if(inputDir==null)
            inputDir = System.getProperty("user.dir");
        /**
        //<end-wrap>
        if(inputDir==null)
        inputDir= "";
        /**/

		return inputDir;
	}
	
	/**
	 * set directory to use as input root
	 */
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}
	
	/**
	 * get current filename
	 */
	public String getSelectedFile() {
		return selectedFile;
	}
	
	/**
	 * set current filename
	 */
	public void setSelectedFile(String selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	/**
	 * return if user has edited forms
	 */
	public boolean isFormsChanged() {
		return formsChanged;
	}
	
	/**
	 * set user has edited forms
	 */
	public void setFormsChanged(boolean formsChanged) {
		this.formsChanged = formsChanged;
	}
	
	/**
	 * get number of pages
	 */
	public int getPageCount() {
		return pageCount;
	}
	
	/**
	 * set number of pages
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * get current file size in kilobytes
	 */
	public long getFileSize() {
		return size;
	}
	
	/**
	 * set current file size in kilobytes
	 */
	public void setFileSize(long size) {
		this.size = size;
	}
	
	/**
	 * get local store on disk
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * set local store on disk
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * get platform file separator
	 */
	public String getSeparator() {

        //<start-wrap>
        if(separator==null)
            separator = System.getProperty("file.separator");
        /**
        //<end-wrap>
        if(separator==null)
        separator= "/";
        /**/


		return separator;
	}
	
	/**
	 * get modeOfOperation (RUNNING_NORMAL,RUNNING_APPLET,RUNNING_WEBSTART,RUNNING_JSP)
	 */
	public int getModeOfOperation() {
		return modeOfOperation;
	}
	
	/**
	 * set modeOfOperation (RUNNING_NORMAL,RUNNING_APPLET,RUNNING_WEBSTART,RUNNING_JSP)
	 */
	public void setModeOfOperation(int modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}
	
	/**
	 * flag to show if using images as hires
	 */
	public boolean isUseHiresImage() {
		return useHiresImage;
	}
	
	/**
	 * set to show images being used are hires and not downsampled
	 */
	public void setUseHiresImage(boolean useHiresImage) {
		this.useHiresImage = useHiresImage;
	}
	
	/**
	 * @return the isContentExtractor
	 */
	public boolean isContentExtractor() {
		return isContentExtractor;
	}

	/**
	 * @param isContentExtractor the isContentExtractor to set
	 */
	public void setContentExtractor(boolean isContentExtractor) {
		this.isContentExtractor = isContentExtractor;
	}

	public boolean isMultiTiff() {
		return isMultiTiff;
	}

	public void setMultiTiff(boolean isMultiTiff) {
		this.isMultiTiff = isMultiTiff;
	}

	public void setMaxMiltiViewers(int maxNoOfMultiViewers) {
		this.maxNoOfMultiViewers = maxNoOfMultiViewers;
	}
	
	public int getMaxMiltiViewers() {
		return maxNoOfMultiViewers;
	}

	public boolean isExtendedApplet() {
		return isExtendedApplet;
	}

	public void setExtendedApplet(boolean isExtendedApplet) {
		this.isExtendedApplet = isExtendedApplet;
	}

	public byte[] getByteData() {
		return byteData;
	}

	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}

//	public OpzioniTimbro getOpzioniTimbro() {
//		return opzioniTimbro;
//	}
//
//	public void setOpzioniTimbro(OpzioniTimbro opzioniTimbro) {
//		this.opzioniTimbro = opzioniTimbro;
//	}

	public Boolean getSaveEnabled() {
		return saveEnabled;
	}

	public void setSaveEnabled(Boolean saveEnabled) {
		this.saveEnabled = saveEnabled;
	}

	public Boolean getPrintEnabled() {
		return printEnabled;
	}

	public void setPrintEnabled(Boolean printEnabled) {
		this.printEnabled = printEnabled;
	}

	public Boolean getSignEnabled() {
		return signEnabled;
	}

	public void setSignEnabled(Boolean signEnabled) {
		this.signEnabled = signEnabled;
	}

	public Boolean getMarkEnabled() {
		return markEnabled;
	}

	public void setMarkEnabled(Boolean markEnabled) {
		this.markEnabled = markEnabled;
	}

	public Boolean getTimbroEnabled() {
		return timbroEnabled;
	}

	public void setTimbroEnabled(Boolean timbroEnabled) {
		this.timbroEnabled = timbroEnabled;
	}

	public FileOutputProvider getFileOutputProvider() {
		return fileOutputProvider;
	}

	public void setFileOutputProvider(FileOutputProvider fileOutputProvider) {
		this.fileOutputProvider = fileOutputProvider;
	}

	public File getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(File originalFile) {
		this.originalFile = originalFile;
	}

	public File getFileInputDir() {
		return fileInputDir;
	}

	public void setFileInputDir(File fileInputDir) {
		this.fileInputDir = fileInputDir;
	}

	public String getFileNameConvertito() {
		return fileNameConvertito;
	}

	public void setFileNameConvertito(String fileNameConvertito) {
		this.fileNameConvertito = fileNameConvertito;
	}

	public JApplet getCurrentApplet() {
		return currentApplet;
	}

	public void setCurrentApplet(JApplet currentApplet) {
		this.currentApplet = currentApplet;
	}

	public void setWeSigned(boolean weSigned) {
		this.weSigned = weSigned;
	}	
	public boolean getWeSigned() {
		return weSigned;
	}	
	
	public PreferenceManager getPreferenceManager() {
		return preferenceManager;
	}

	public void setPreferenceManager(PreferenceManager preferenceManager) {
		this.preferenceManager = preferenceManager;
	}
	
	public FileOperationResponse getResponseVerificaFirme() {
		return responseVerificaFirme;
	}

	public void setResponseVerificaFirme(FileOperationResponse responseVerificaFirme) {
		this.responseVerificaFirme = responseVerificaFirme;
	}

	public void dispose(){
		
		inputDir =null;
		fileInputDir =null;
		fileNameConvertito = null;
		selectedFile = null;
		originalFile = null;
		
		target=null;
		separator=null;
		currentApplet=null;
		byteData=null;
		timbroEnabled=null;
		saveEnabled=null;
		printEnabled=null;
		signEnabled=null;
		markEnabled=null;
		fileOutputProvider=null;
		preferenceManager=null;
	}

	public void setExternalSaveAs(boolean externalSaveAs) {
		this.externalSaveAs = externalSaveAs;
	}

	public boolean isExternalSaveAs() {
		return externalSaveAs;
	}

	public void setSalvaFileProvider(SalvaFileProvider salvaFileProvider) {
		this.salvaFileProvider = salvaFileProvider;
	}

	public SalvaFileProvider getSalvaFileProvider() {
		return salvaFileProvider;
	}
}
