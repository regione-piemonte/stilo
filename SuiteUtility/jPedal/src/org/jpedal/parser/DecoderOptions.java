/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2007, IDRsolutions and Contributors.
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

  * DecoderOptions.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package org.jpedal.parser;

import org.jpedal.Display;
import org.jpedal.DisplayOffsets;
import org.jpedal.PDFtoImageConvertor;
import org.jpedal.PdfDecoder;
import org.jpedal.constants.JPedalSettings;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.objects.FontData;
import org.jpedal.grouping.PdfGroupingAlgorithms;
import org.jpedal.io.PdfObjectReader;
import org.jpedal.objects.Javascript;
import org.jpedal.objects.PdfData;
import org.jpedal.objects.PdfPageData;
import org.jpedal.objects.acroforms.rendering.AcroRenderer;
import org.jpedal.render.SwingDisplay;
import org.jpedal.utils.LogWriter;

import java.awt.*;
import java.util.Map;

public class DecoderOptions {

    /**Set default page Layout*/
    private int pageMode = Display.SINGLE_PAGE;

    private DisplayOffsets displayOffsets=new DisplayOffsets();

    Javascript javascript = null;

    /**
     * The colour of the highlighting box around the text
     */
    public static Color highlightColor = new Color(10,100,170);

    /**
     * The colour of the text once highlighted
     */
    public static Color backgroundColor = null;

    Color nonDrawnPageColor=Color.WHITE;

    /**
     * page colour for PDF background
     */
    public Color altPageColor=Color.WHITE;
    public Color altTextColor=null;
    public Color altDisplayBackground=null;
    public int altColorThreshold=255;
    boolean changeTextAndLine = false;

    //non-static version
    private Integer instance_bestQualityMaxScaling=null;

    private int[] instance_formsNoPrint=null;

    private static int[] formsNoPrint=null;

    //page size for extraction
    static private String[] extactionPageSize=null;
    //non-static version
    private String[] instance_extactionPageSize=null;

    //page size override
    static private Boolean overridePageSize=null;
    //non-static version
    private Boolean instance_overridePageSize=null;

    //non-static version
    private Boolean instance_allowPagesSmallerThanPageSize=Boolean.FALSE;

    public boolean isXMLExtraction() {
        return isXMLExtraction;
    }

    public void setXMLExtraction(boolean XMLExtraction) {
        isXMLExtraction = XMLExtraction;
    }

    public DisplayOffsets getDisplayOffsets() {
        return displayOffsets;
    }

    public void setDisplayOffsets(DisplayOffsets displayOffsets) {
        this.displayOffsets = displayOffsets;
    }

    /**
     * flag to show if data extracted as text or XML
     */
    private boolean isXMLExtraction = true;

    public void setPageMode(int mode){
        pageMode = mode;
    }

    public int getPageMode(){
        return pageMode;
    }

    /**
     * show if page is an XFA form
     */
    public boolean hasJavascript() {

         return false;
         /**/
    }

    /**
     * switch on Javascript
     */
    private boolean useJavascript = true;

    /** use this to turn javascript on and off, default is on. */
    public void setJavaScriptUsed(boolean jsEnabled){
        useJavascript = jsEnabled;
    }


    public void disposeObjects() {

        if(javascript!=null)
            javascript.dispose();
        javascript=null;

    }

    /**
     * XML extraction is the default - pure text extraction is much faster
     */
    public void useXMLExtraction() {

        isXMLExtraction=true;
    }

    //<start-adobe>
    /**
     * returns object containing grouped text of last decoded page
     * - if no page decoded, a Runtime exception is thrown to warn user
     * Please see org.jpedal.examples.text for example code.
     *
     */
    public PdfGroupingAlgorithms getGroupingObject(int lastPageDecoded, PdfData textData,PdfPageData pageData) throws PdfException {

        if(lastPageDecoded==-1){

            throw new RuntimeException("No pages decoded - call decodePage(pageNumber) first");

        }else{

            //PUT BACK when we remove params
            //PdfData textData = getPdfData();
            if (textData == null)
                return null;
            else
                return new PdfGroupingAlgorithms(textData, pageData,isXMLExtraction);
        }
    }

    /**
     * returns object containing grouped text from background grouping - Please
     * see org.jpedal.examples.text for example code
     * @param pdfBackgroundData
     *
     */
    public PdfGroupingAlgorithms getBackgroundGroupingObject(PdfData pdfBackgroundData, PdfPageData pageData) {

        PdfData textData = pdfBackgroundData;
        if (textData == null)
            return null;
        else
            return new PdfGroupingAlgorithms(textData, pageData, isXMLExtraction);
    }
    //<end-adobe>

    public Javascript getJS() {
        return javascript;
    }

    public void set(Map values) throws PdfException {
        //read values

        for (Object nextKey : values.keySet()) {
            //check it is valid
            if (nextKey instanceof Integer) {

                Integer key = (Integer) nextKey;
                Object rawValue = values.get(key);

                if (key.equals(JPedalSettings.UNDRAWN_PAGE_COLOR)) {
                    if (rawValue instanceof Integer) {

                        nonDrawnPageColor = new Color((Integer) rawValue);

                    } else
                        throw new PdfException("JPedalSettings.UNDRAWN_PAGE_COLOR expects a Integer value");

                } else if (key.equals(JPedalSettings.PAGE_COLOR)) {
                    if (rawValue instanceof Integer) {

                        altPageColor = new Color((Integer) rawValue);

                    } else
                        throw new PdfException("JPedalSettings.PAGE_COLOR expects a Integer value");

                } else if (key.equals(JPedalSettings.TEXT_COLOR)) {
                    if (rawValue instanceof Integer) {

                        altTextColor = new Color((Integer) rawValue);

                    } else
                        throw new PdfException("JPedalSettings.TEXT_COLOR expects a Integer value");

                } else if (key.equals(JPedalSettings.REPLACEMENT_COLOR_THRESHOLD)) {
                    if (rawValue instanceof Integer) {

                        altColorThreshold = ((Integer) rawValue);

                    } else
                        throw new PdfException("JPedalSettings.TEXT_COLOR expects a Integer value");

                }  else if (key.equals(JPedalSettings.DISPLAY_BACKGROUND)) {
                    if (rawValue instanceof Integer) {

                    	altDisplayBackground = new Color((Integer) rawValue);

                    } else
                        throw new PdfException("JPedalSettings.TEXT_COLOR expects a Integer value");

                }  else if (key.equals(JPedalSettings.CHANGE_LINEART)) {
                    if (rawValue instanceof Boolean) {
                    		changeTextAndLine = (Boolean) rawValue;

                    } else
                        throw new PdfException("JPedalSettings.CHANGE_LINEART expects a Boolean value");

                } else if (key.equals(JPedalSettings.EXTRACT_AT_BEST_QUALITY_MAXSCALING)) {

                    if (rawValue instanceof Integer) {

                        instance_bestQualityMaxScaling = (Integer) rawValue;

                    } else
                        throw new PdfException("JPedalSettings.EXTRACT_AT_BEST_QUALITY_MAXSCALING expects a Integer value");

                } else if (key.equals(JPedalSettings.EXTRACT_AT_PAGE_SIZE)) {

                    if (rawValue instanceof String[]) {

                        instance_extactionPageSize = (String[]) rawValue;

                    } else
                        throw new PdfException("JPedalSettings.EXTRACT_AT_PAGE_SIZE expects a String[] value");

                } else if (key.equals(JPedalSettings.IGNORE_FORMS_ON_PRINT)) {

                    if (rawValue instanceof int[]) {

                        instance_formsNoPrint = (int[]) rawValue;

                    } else
                        throw new PdfException("JPedalSettings.IGNORE_FORMS_ON_PRINT expects a int[] value");

                } else if (key.equals(JPedalSettings.PAGE_SIZE_OVERRIDES_IMAGE)) {

                    if (rawValue instanceof Boolean) {

                        instance_overridePageSize = (Boolean) rawValue;


                    } else
                        throw new PdfException("JPedalSettings.EXTRACT_AT_PAGE_SIZE expects a Boolean value");

                } else if (key.equals(JPedalSettings.ALLOW_PAGES_SMALLER_THAN_PAGE_SIZE)) {

                    if (rawValue instanceof Boolean) {

                        instance_allowPagesSmallerThanPageSize = (Boolean) rawValue;


                    } else
                        throw new PdfException("JPedalSettings.ALLOW_PAGES_SMALLER_THAN_PAGE_SIZE expects a Boolean value");
                    //expansion room here

                } else //all static values
                    setParameter(values, nextKey);

            } else
                throw new PdfException("Unknown or unsupported key (not Integer) " + nextKey);

        }
    }

    private static void setParameter(Map values, Object nextKey) throws PdfException {
        //check it is valid
        if(nextKey instanceof Integer){

            Integer key=(Integer) nextKey;
            Object rawValue=values.get(key);

            if(key.equals(JPedalSettings.INVERT_HIGHLIGHT)){
                //set mode if valid

                if(rawValue instanceof Boolean){
                    SwingDisplay.invertHighlight = (Boolean) rawValue;
                }else
                    throw new PdfException("JPedalSettings.INVERT_HIGHLIGHT expects an Boolean value");

            }else if(key.equals(JPedalSettings.TEXT_INVERTED_COLOUR)){
                //set colour if valid

                if(rawValue instanceof Color)
                    backgroundColor=(Color) rawValue;
                else
                    throw new PdfException("JPedalSettings.TEXT_INVERTED_COLOUR expects a Color value");

            }else if(key.equals(JPedalSettings.TEXT_HIGHLIGHT_COLOUR)){
                //set colour if valid

                if(rawValue instanceof Color)
                    highlightColor=(Color) rawValue;
                else
                    throw new PdfException("JPedalSettings.TEXT_HIGHLIGHT_COLOUR expects a Color value");

            }else if(key.equals(JPedalSettings.TEXT_PRINT_NON_EMBEDDED_FONTS)){

                if(rawValue instanceof Boolean){

                    Boolean value= (Boolean)rawValue;
                    PdfStreamDecoder.useTextPrintingForNonEmbeddedFonts = value;
                }else
                    throw new PdfException("JPedalSettings.TEXT_PRINT_NON_EMBEDDED_FONTS expects a Boolean value");

            }else if(key.equals(JPedalSettings.DISPLAY_INVISIBLE_TEXT)){

                if(rawValue instanceof Boolean){

                    Boolean value= (Boolean)rawValue;
                    TextDecoder.showInvisibleText = value;
                }else
                    throw new PdfException("JPedalSettings.DISPLAY_INVISIBLE_TEXT expects a Boolean value");

            }else if(key.equals(JPedalSettings.CACHE_LARGE_FONTS)){

                if(rawValue instanceof Integer){

                    Integer value= (Integer)rawValue;
                    FontData.maxSizeAllowedInMemory = value;
                }else
                    throw new PdfException("JPedalSettings.CACHE_LARGE_FONTS expects an Integer value");

            }else if(key.equals(JPedalSettings.IMAGE_HIRES)){
            }else if(key.equals(JPedalSettings.EXTRACT_AT_BEST_QUALITY_MAXSCALING)){

                if(rawValue instanceof Integer){

                    PDFtoImageConvertor.bestQualityMaxScaling = (Integer) rawValue;

                }else
                    throw new PdfException("JPedalSettings.EXTRACT_AT_BEST_QUALITY_MAXSCALING expects a Integer value");
                //expansion room here
            }else if(key.equals(JPedalSettings.EXTRACT_AT_PAGE_SIZE)){

                if(rawValue instanceof String[]){

                    extactionPageSize = (String[]) rawValue;

                }else
                    throw new PdfException("JPedalSettings.EXTRACT_AT_PAGE_SIZE expects a String[] value");
                //expansion room here

            }else if(key.equals(JPedalSettings.PAGE_SIZE_OVERRIDES_IMAGE)){

                if(rawValue instanceof Boolean){

                    overridePageSize = (Boolean) rawValue;


                }else
                    throw new PdfException("JPedalSettings.EXTRACT_AT_PAGE_SIZE expects a Boolean value");
                //expansion room here

            }else if(key.equals(JPedalSettings.IGNORE_FORMS_ON_PRINT)){

                if(rawValue instanceof int[]){

                    formsNoPrint = (int[]) rawValue;

                }else
                    throw new PdfException("JPedalSettings.IGNORE_FORMS_ON_PRINT expects a int[] value");

            }else if(key.equals(JPedalSettings.ALLOW_PAGES_SMALLER_THAN_PAGE_SIZE)){

                if(rawValue instanceof Boolean){

                    PDFtoImageConvertor.allowPagesSmallerThanPageSize = (Boolean) rawValue;

                }else
                    throw new PdfException("JPedalSettings.ALLOW_PAGES_SMALLER_THAN_PAGE_SIZE expects a Boolean value");


            }else
                throw new PdfException("Unknown or unsupported key "+key);

        }else
            throw new PdfException("Unknown or unsupported key (not Integer) "+nextKey);
    }

    public static void modifyJPedalParameters(Map values) throws PdfException {

        //read values

        for (Object nextKey : values.keySet()) {
            setParameter(values, nextKey);
        }
    }

    public Color getPageColor() {
        return altPageColor;
    }
    
    public Color getTextColor() {
        return altTextColor;
    }
    
    public int getReplacementColorThreshold() {
        return altColorThreshold;
    }
    
    public Color getDisplayBackgroundColor(){
    	return altDisplayBackground;
    }
    
    public boolean getChangeTextAndLine() {
    		return changeTextAndLine;
    }

    public Paint getNonDrawnPageColor() {
        return nonDrawnPageColor;
    }

    public Boolean getInstance_allowPagesSmallerThanPageSize() {
        return instance_allowPagesSmallerThanPageSize;
    }

    public Integer getInstance_bestQualityMaxScaling() {
        return instance_bestQualityMaxScaling;
    }

    public static int[] getFormsNoPrint() {
        return formsNoPrint;
    }

    public int[] getInstance_FormsNoPrint() {
        return instance_formsNoPrint;
    }

    public Boolean getPageSizeToUse() {
        Boolean overridePageSizeToUse = Boolean.FALSE;
        if(instance_overridePageSize != null)
            overridePageSizeToUse = instance_overridePageSize;
        else if(overridePageSize != null)
            overridePageSizeToUse = overridePageSize;

        return overridePageSizeToUse;

    }

    public float getImageDimensions(int pageIndex,PdfPageData pageData) {

        float multiplyer=-2;

        String overridePageSizeJVM = System.getProperty("org.jpedal.pageSizeOverridesImage");
        if (overridePageSizeJVM != null) {
            if (instance_overridePageSize != null)
                instance_overridePageSize = Boolean.parseBoolean(overridePageSizeJVM);
            else
                overridePageSize = Boolean.parseBoolean(overridePageSizeJVM);
        }

        String maxScalingJVM = System.getProperty("org.jpedal.pageMaxScaling");
        if(maxScalingJVM != null){
            try{
                if(instance_bestQualityMaxScaling != null)
                    instance_bestQualityMaxScaling = Integer.parseInt(maxScalingJVM);
                else
                    PDFtoImageConvertor.bestQualityMaxScaling = Integer.parseInt(maxScalingJVM);

            }catch(Exception e){
                //tell user and log
                if(LogWriter.isOutput())
                    LogWriter.writeLog("Exception: "+e.getMessage());
            }
        }

        String[] dims=null;
        String dimsJVM=System.getProperty("org.jpedal.pageSize");
        if(dimsJVM!=null)
            dims = dimsJVM.split("x");

        if(dims==null){

            if(instance_extactionPageSize!=null)
                dims=instance_extactionPageSize;
            else
                dims=extactionPageSize;
        }

        // prefered size of the extracted page
        float prefWidth = 0, prefHeight = 0;

        // parse values as ints, if any issues let know that prarameters are invalid
        if(dims!=null){
            if(dims.length==2){

                if(pageData.getRotation(pageIndex)==90 || pageData.getRotation(pageIndex)==270){
                    prefWidth = Float.parseFloat(dims[1]);
                    prefHeight = Float.parseFloat(dims[0]);
                }else{
                    prefWidth = Float.parseFloat(dims[0]);
                    prefHeight = Float.parseFloat(dims[1]);
                }
            }else{
                throw new RuntimeException("Invalid parameters in JVM option -DpageSize ");
            }
        }

        float dScaleW = 0, dScaleH;

        if(dims!=null){

            //Work out scalings for -DpageSize
            float crw = pageData.getCropBoxWidth(pageIndex);
            float crh = pageData.getCropBoxHeight(pageIndex);

            dScaleW = prefWidth/crw;
            dScaleH = prefHeight/crh;

            if(dScaleH<dScaleW)
                dScaleW = dScaleH;
        }

        Boolean overridePageSizeToUse = getPageSizeToUse();

        if(dims!=null && overridePageSizeToUse){

            multiplyer = dScaleW;

        }

        return multiplyer;
    }
}
