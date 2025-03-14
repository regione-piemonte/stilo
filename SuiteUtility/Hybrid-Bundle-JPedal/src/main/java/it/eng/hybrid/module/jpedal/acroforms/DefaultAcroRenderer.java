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
  * DefaultAcroRenderer.java
  * ---------------
 */
package it.eng.hybrid.module.jpedal.acroforms;

import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.Javascript;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.objects.raw.FormObject;
import it.eng.hybrid.module.jpedal.objects.raw.FormStream;
import it.eng.hybrid.module.jpedal.objects.raw.PdfArrayIterator;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.parser.PdfStreamDecoder;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.ui.Display;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.util.ConvertToString;
import it.eng.hybrid.module.jpedal.util.FormUtils;
import it.eng.hybrid.module.jpedal.util.Options;
import it.eng.hybrid.module.jpedal.util.StringUtils;
import it.eng.hybrid.module.jpedal.util.SwingWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * Provides top level to forms handling, assisted by separate classes to
 * decode widgets (FormDecoder - default implements Swing set)
 * create Form widgets (implementation of FormFactory),
 * store and render widgets (GUIData),
 * handle Javascript and Actions (Javascript and ActionHandler)
 * and support for Signature object
 */
public class DefaultAcroRenderer implements AcroRenderer {

	public final static Logger logger = Logger.getLogger(DefaultAcroRenderer.class);
	
    final public static boolean isLazyInit=true;

    public static final boolean testActions=false;/**/

    private static final boolean showFormsDecoded=false;

    /** holds all the FORMS raw data from the PDF in order read from PDF **/
    //private List FacroFormDataList;

    /** holds all the ANNOTS raw data from the PDF in order read from PDF **/
    //private List[] AacroFormDataList;

    private Map currentItems = new HashMap();

    FormObject[] Fforms, Aforms;

    PdfObject AcroRes=null;

    PdfArrayIterator CO=null;
    PdfArrayIterator fieldList=null;
    PdfArrayIterator[] annotList=null;


    /**
     * flags used to debug code
     */
    final private static boolean showMethods = false;
    final private static boolean identifyType = false;
    final private static boolean debug = false;

    /**
     * flag to show we ignore forms
     */
    private boolean ignoreForms=false;

    /**
     * creates all GUI components from raw data in PDF and stores in GUIData instance
     */
    private FormFactory formFactory;

    /**holder for all data (implementations to support Swing and ULC)*/
    private GUIData compData=new SwingData();

    private Map proxyObjects = new HashMap();

    /**holds sig object so we can easily retrieve*/
    private Set sigObject=null;

    /**
     * flags for types of form
     */
    //public static final int ANNOTATION = 1;
    //public static final int FORM = 2;
    //public static final int XFAFORM = 3;

    private Map cachedObjs=new HashMap();

    /**
     * holds copy of object to access the mediaBox and cropBox values
     */
    private PdfPageData pageData;

    /**
     * number of 'A' annot and 'F' form fields in total for this document
     */
    //private int FformCount;

    /**
     * number of entries in acroFormDataList, each entry can have a button group of more that one button
     * 'A' annot and 'F' form - A is per page, F is total hence 3 variables
     */
    private int[] AfieldCount = null;

    private int ATotalCount,FfieldCount = 0;

    /**
     * number of pages in current PDF document
     */
    protected int pageCount = 0;

    /**
     * handle on object reader for decoding objects
     */
    private PdfObjectReader currentPdfFile;

    /**
     * parses and decodes PDF data into generic data for converting to widgets
     */
    private FormStream fDecoder;

    /**
     * handles events like URLS, EMAILS
     */
    private ActionHandler formsActionHandler;

    /**
     * allow user to trap own events - not currently used
     */
    //private LinkHandler linkHandler;

    /**
     * handles Javascript events
     */
    private Javascript javascript;


    /**
     * local copy of inset on Height for use in displaying page
     */
    private int insetH;
    //private Map formKids;

    private PdfObject acroObj;

    /*flag to show if XFA or FDF*/
    private boolean hasXFA=false;

    //private boolean JSInitialised_K;

    private PdfDecoder decode_pdf;

    /**
     * setup new instance but does not do anything else
     */
    public DefaultAcroRenderer() {}

    /**
     * reset handler (must be called Before page opened)
     * - null Object resets to default
     */
    public void resetHandler(Object userActionHandler,PdfDecoder decode_pdf, int type) {

        this.decode_pdf=decode_pdf;

        if(type==Options.FormsActionHandler){

            if (userActionHandler != null)
                formsActionHandler = (ActionHandler) userActionHandler;
            else
                formsActionHandler = new DefaultActionHandler();

            formsActionHandler.init(decode_pdf, javascript,this);

            if (formFactory != null){
                formFactory.reset(this, formsActionHandler);
                compData.resetDuplicates();
            }
        }
    }

    /**
     * make all components invisible on all pages by removing from Display
     */
    public void removeDisplayComponentsFromScreen() {

        if (showMethods)
            System.out.println("removeDisplayComponentsFromScreen ");

        if(compData!=null)
            compData.removeAllComponentsFromScreen();

    }

    /**
     * initialise holders and variables and get a handle on data object
     * <p/>
     * Complicated as Annotations stored on a PAGE basis whereas FORMS stored on
     * a file basis
     */
    public void resetFormData(int insetW, int insetH, PdfPageData pageData, PdfObjectReader currentPdfFile, PdfObject acroObj) {

        this.insetH=insetH;
        this.currentPdfFile = currentPdfFile;
        this.pageData = pageData;

        this.acroObj=acroObj;

        //explicitly flush
        sigObject=null;

        //track inset on page
        compData.setPageData(pageData,insetW,insetH);

//        System.out.println("obj="+obj);
//        System.out.println("acroObj="+acroObj);
//
//        if(acroObj!=null){
//        System.out.println(acroObj.getMixedArray(PdfDictionary.Fields));
//        System.out.println(acroObj.getMixedArray(PdfDictionary.Fields).getTokenCount());
//        }
//        if((obj==null)!=(acroObj==null))
//        throw new RuntimeException("xx");

        if (acroObj == null) {

            FfieldCount = 0;

            fieldList=null;
        } else{

            //handle XFA
            PdfObject XFAasStream;
            PdfArrayIterator XFAasArray = null;

            XFAasStream=acroObj.getDictionary(PdfDictionary.XFA);
            if(XFAasStream==null){
                XFAasArray=acroObj.getMixedArray(PdfDictionary.XFA);

                //empty array
                if(XFAasArray!=null && XFAasArray.getTokenCount()==0)
                    XFAasArray=null;
            }

            hasXFA= XFAasStream!=null || XFAasArray!=null;

            /**
             * choose correct decoder for form data
             */
            if (hasXFA)
                fDecoder = new XFAFormStream(acroObj, currentPdfFile);
            else
                fDecoder = new FormStream();

            /**
             * now read the fields
             **/
            fieldList = acroObj.getMixedArray(PdfDictionary.Fields);
            CO = acroObj.getMixedArray(PdfDictionary.CO);

            if(fieldList!=null){
                FfieldCount = fieldList.getTokenCount();

                AcroRes=acroObj.getDictionary(PdfDictionary.DR);

                if(AcroRes!=null)
                    currentPdfFile.checkResolved(AcroRes);

            }else{
                FfieldCount=0;
                AcroRes=null;
            }

            //allow for indirect
            while(FfieldCount==1){

                String key=fieldList.getNextValueAsString(false);

                FormObject kidObject = new FormObject(key,formsActionHandler);
                currentPdfFile.readObject(kidObject);

                byte[][] childList =getKid(kidObject);

                if(childList==null)
                    break;

                fieldList=new PdfArrayIterator(childList);
                FfieldCount = fieldList.getTokenCount();
            }
        }

        resetContainers(true);

    }

    /**
     * initialise holders and variables and get a handle on data object
     * <p/>
     * Complicated as Annotations stored on a PAGE basis whereas FORMS stored on
     * a file basis
     */
    public void resetAnnotData(int insetW, int insetH, PdfPageData pageData, int page,
                               PdfObjectReader currentPdfFile, byte[][] currentAnnotList) {

        this.insetH=insetH;
        this.currentPdfFile = currentPdfFile;
        this.pageData = pageData;

        boolean resetToEmpty = true;

        //track inset on page
        compData.setPageData(pageData,insetW,insetH);

        if (currentAnnotList==null) {

            AfieldCount = null;
            ATotalCount=0;
            if(annotList!=null)
                annotList[page]=null;

            annotList=null;

        }else{

            int pageCount=pageData.getPageCount()+1;
            if(pageCount<=page)
                pageCount=page+1;
            if(annotList==null){
                annotList=new PdfArrayIterator[pageCount];
                AfieldCount=new int[pageCount];
            }else if(page>=annotList.length){

                PdfArrayIterator[] tempList=annotList;
                int[] tempCount=AfieldCount;
                AfieldCount=new int[pageCount];
                annotList=new PdfArrayIterator[pageCount];

                for(int ii=0;ii<tempList.length;ii++){
                    AfieldCount[ii]=tempCount[ii];
                    annotList[ii]=tempList[ii];
                }
            }else if(AfieldCount==null)
                AfieldCount=new int[pageCount];

            annotList[page]=new PdfArrayIterator(currentAnnotList);

            int size =annotList[page].getTokenCount();

            AfieldCount[page] = size;
            ATotalCount=ATotalCount+size;
            resetToEmpty = false;

            /**
             * choose correct decoder for form data
             */
            if(fDecoder==null){
                if (hasXFA)
                    fDecoder = new XFAFormStream(acroObj, currentPdfFile);
                else
                    fDecoder = new FormStream();
            }
        }

        resetContainers(resetToEmpty);

    }

    /**
     * flush or resize data containers
     */
    protected void resetContainers(boolean resetToEmpty) {
//System.out.println(this+" DefaultAcroRenderer.resetContainers()");
        if (debug)
            System.out.println("DefaultAcroRenderer.resetContainers()");

        /**form or reset Annots*/
        if (resetToEmpty) {

            //flush list
            currentItems.clear();

            compData.resetComponents(ATotalCount+FfieldCount, pageCount, false);
            proxyObjects.clear();
        }else{
            compData.resetComponents(ATotalCount+FfieldCount,pageCount,true);
            proxyObjects.clear();
        }

        if (formFactory == null) {
            formFactory = new SwingFormFactory(this, formsActionHandler);//,currentPdfFile);
//			System.out.println(this+" DefaultAcroRenderer.resetContainers() SWING FACTORY SET HERERER");
            //compData=new SwingData();
            //formFactory.setDataObjects(compData);
        } else {
            //to keep customers formfactory usable
            formFactory.reset(this, formsActionHandler);
            compData.resetDuplicates();
            //formFactory.setDataObjects(compData);
        }

        formsActionHandler.setActionFactory(formFactory.getActionFactory());

    }

    /**
     * build forms display using standard swing components
     */
    public void createDisplayComponentsForPage(int page, PdfStreamDecoder current) {

        boolean debugNew=false;

        Map formsCreated=new HashMap();

        //check if we want to flatten forms
        String s = System.getProperty("org.jpedal.flattenForm");
        if(s!=null && s.toLowerCase().equals("true")){
            compData.setRasterizeForms(true);
        }

        if (showMethods)
            System.out.println("createDisplayComponentsForPage " + page);


        /**see if already done*/
        int id=compData.getStartComponentCountForPage(page);
        if (id == -1 || id == -999) {


            /**ensure space for all values*/
            compData.initParametersForPage(pageData,page,decode_pdf);

            //sync values
            if(formsActionHandler!=null){
                formsActionHandler.init(decode_pdf, javascript,this);
                formsActionHandler.setPageAccess(pageData.getMediaBoxHeight(page), insetH);
            }

            if(fDecoder != null)
                currentItems.clear();

            /**
             * think this needs to be revised, and different approach maybe storing, and reuse if respecified in file,
             * need to look at other files to work out solution.
             * files :-
             * lettreenvoi.pdf page 2+ no next page field
             * costena.pdf checkboxes not changable
             *
             * maybe if its just reset on multipage files
             */

            //list of forms done
            Map formsProcessed=new HashMap();

            int Acount=0;
            if(AfieldCount!=null && AfieldCount.length>page)
                Acount=AfieldCount[page];

            Fforms = new FormObject[FfieldCount];
            FormObject[] xfaFormList = null;
            Aforms = new FormObject[Acount];
            FormObject formObject;
            String objRef;
            int i, count;

            //scan list for all relevant values and add to array if valid
            //0 = xfa, 1 = forms, 2 = annots
            int decodeToForm = 2;
            for(int forms=0;forms<decodeToForm;forms++){

                i=0;

                if(forms==0){
                    count=0;
                    if(fieldList!=null){
                        fieldList.resetToStart();
                        count=fieldList.getTokenCount()-1;
                    }

                }else{
                    if(annotList!=null && annotList.length>page && annotList[page]!=null){
                        annotList[page].resetToStart();

                        //create lookup and array for values to set order correctly in HTML
                        if(formFactory.getType()==FormFactory.HTML){
                            Map annotOrder=new HashMap();

                            int count2=annotList[page].getTokenCount();
                            String val;
                            for(int ii=0;ii<count2;ii++){
                                val=annotList[page].getNextValueAsString(true);
                                annotOrder.put(val,String.valueOf(ii+1));
                            }

                            formFactory.setAnnotOrder(annotOrder);
                        }


                        annotList[page].resetToStart();
                    }
                    count=Acount-1;
                }

                for (int fieldNum =count; fieldNum >-1; fieldNum--) {

                    objRef=null;

                    if(forms==0){
                        if(fieldList!=null)
                            objRef=fieldList.getNextValueAsString(true);
                    }else{
                        if(annotList.length>page && annotList[page]!=null)
                            objRef=annotList[page].getNextValueAsString(true);

                    }

                    if(objRef==null || (objRef!=null && (formsProcessed.get(objRef)!=null || objRef.length()==0)))
                        continue;

                    formObject= (FormObject) cachedObjs.get(objRef);
                    if(formObject==null){

                        formObject = new FormObject(objRef,formsActionHandler,pageData.getRotation(page-1));
                        //formObject.setPDFRef((String)objRef);

                        if(objRef.charAt(objRef.length()-1)=='R'){
                            currentPdfFile.readObject(formObject);
                        }else{

                            //changed by Mark as cover <<>> as well as 1 0 R
                            formObject.setStatus(PdfObject.UNDECODED_REF);
                            formObject.setUnresolvedData(StringUtils.toBytes(objRef),id);
                            currentPdfFile.checkResolved(formObject);
                        }

                        cachedObjs.put(objRef,formObject);
                    }

                    byte[][] kids=formObject.getKeyArray(PdfDictionary.Kids);
                    if(kids!=null) //not 'proper' kids so process here
                        i = flattenKids(page, debugNew, formsProcessed, formObject, i, forms);
                    else
                        i = processFormObject(page, debugNew, formsProcessed, formObject, objRef, i, forms);
                }
            }

            /**
             * process XFA FORMS and add in values
             */
            if (hasXFA && fDecoder.hasXFADataSet()) {
                currentItems.clear();

                //needs reversing
                ///Users/markee/Downloads/write_test16_pdfform.pdf
                int size=Fforms.length;
                FormObject[] tmp=new FormObject[size];
                for(int ii=0;ii<size;ii++)
                    tmp[ii]=Fforms[size-ii-1];

                xfaFormList = ((XFAFormStream) fDecoder).createAppearanceString(tmp);

                compData.storeXFARefToForm(((XFAFormStream)fDecoder).getRefToFormArray());

            }

            List unsortedForms= new ArrayList();
            compData.setUnsortedListForPage(page,unsortedForms);

            //XFA, FDF FORMS then ANNOTS
            int readToForm = 3;
            for(int forms=0;forms<readToForm;forms++){

                count=0;

                if(forms==0){
                    if(xfaFormList!=null)
                        count=xfaFormList.length;
                }else if(forms==1){
                    //store current order of forms for printing
                    for (FormObject Fform : Fforms) {
                        if (Fform != null)
                            unsortedForms.add(Fform.getObjectRefAsString());
                    }

                    //sort forms into size order for display
                    Fforms = FormUtils.sortGroupLargestFirst(Fforms);
                    count=Fforms.length;
                }else{
                    //store current order of forms for printing
                    for (FormObject Aform : Aforms) {
                        if (Aform != null)
                            unsortedForms.add(Aform.getObjectRefAsString());
                    }

                    //sort forms into size order for display
                    Aforms = FormUtils.sortGroupLargestFirst(Aforms);
                    count=Aforms.length;
                }

                for (int k = 0; k <count; k++) {

                    if(forms==0)
                        formObject = xfaFormList[k];
                    else if(forms==1)
                        formObject = Fforms[k];
                    else
                        formObject =Aforms[k];

                    if (formObject != null && (formsCreated.get(formObject.getObjectRefAsString())==null) && page==formObject.getPageNumber()){// && !formObject.getObjectRefAsString().equals("216 0 R")){
                        //String OEPROPval=formObject.getTextStreamValue(PdfDictionary.EOPROPtype);
                        //NOTE: if this custom form needs redrawing more change ReadOnlyTextIcon.MAXSCALEFACTOR to 1;
                        if(formsRasterizedForDisplay() && current!=null){// || OEPROPval!=null){

                            //rasterize any flattened PDF forms here
                            try {
                                current.drawFlattenedForm(formObject);
                            }catch( PdfException e ){
                                //tell user and log
                                logger.info("Exception: "+e.getMessage());
                            }

                        }else {
                            createField(formObject, compData.getNextFreeField()); //now we turn the data into a Swing component
                            //set the raw data here so that the field names are the fully qualified names
                            compData.storeRawData(formObject); //store data so user can access

                            formsCreated.put(formObject.getObjectRefAsString(), "x");
                        }
                    }
                }
            }

            if(!formsRasterizedForDisplay()){

                //html add radio button ordering at end to make overlapping forms show correct
                if(formFactory.getType()==FormFactory.HTML){
                    formFactory.indexAllKids();
                }

                //finish off (some may have been picked up on other pages)
                //compData.completeFields(page,this);


            }
        }

    }

    private int flattenKids(int page, boolean debugNew, Map formsProcessed, FormObject formObject, int i, int forms) {

        byte[][] kidList=formObject.getKeyArray(PdfDictionary.Kids);
        int kidCount=kidList.length;

        //resize to fit
        if(forms==0){
            int oldCount=Fforms.length;
            FormObject[] temp=Fforms;
            Fforms=new FormObject[oldCount+kidCount-1];
            System.arraycopy(temp, 0, Fforms, 0, oldCount);
        }else{
            int oldCount=Aforms.length;
            FormObject[] temp=Aforms;
            Aforms=new FormObject[oldCount+kidCount-1];
            System.arraycopy(temp, 0, Aforms, 0, oldCount);
        }

        for (byte[] aKidList : kidList) { //iterate through all parts

            String key = new String(aKidList);

            //now we have inherited values, read
            FormObject childObj = new FormObject(key, null);

            //inherit values
            if (formObject != null)
                childObj.copyInheritedValuesFromParent(formObject);

            currentPdfFile.readObject(childObj);
            //childObj.setPDFRef(key);


            childObj.setHandler(formsActionHandler);
            childObj.setRef(key);

            //flag its a kid so we can align value sif needed
            childObj.setKid(true);

            if (childObj.getKeyArray(PdfDictionary.Kids) == null) {

                //test and then test with it commented out
                //check space not filled
                Object rectTocheck = childObj.toString(childObj.getFloatArray(PdfDictionary.Rect), true);
                if (rectTocheck != null && !addItem(rectTocheck)) {

                } else {
                    new FormStream().createAppearanceString(childObj, currentPdfFile);
                }


                i = processFormObject(page, debugNew, formsProcessed, childObj, key, i, forms);
            } else {


                //test and then test with it commented out
                //check space not filled
//                Object rectTocheck=childObj.toString(childObj.getFloatArray(PdfDictionary.Rect),true);
//                if (rectTocheck!=null && !addItem(rectTocheck)) {
//
//                }else{
//                    new FormStream().createAppearanceString(childObj,currentPdfFile,1);
//                }

                i = flattenKids(page, debugNew, formsProcessed, childObj, i, forms);
            }
        }
        return i;
    }

    /**
     private int findObjectType(FormObject formObject) {

     int subtype=PdfDictionary.Unknown;

     byte[][] kidList = getKid(formObject);
     boolean hasKids=kidList!=null && kidList.length>0, found=false;

     //while(hasKids && subtype== PdfDictionary.Unknown){
     //System.out.println(hasKids+" "+formObject.getObjectRefAsString());
     if (hasKids) {
     int kidCount=kidList.length;

     FormObject[] kids=new FormObject[kidCount];

     for(int ii=0;ii<kidCount;ii++){ //iterate through all parts

     kids[ii]=new FormObject(new String(kidList[ii]),null);
     currentPdfFile.readObject(kids[ii]);

     subtype=kids[ii].getParameterConstant(PdfDictionary.Subtype);
     if(subtype!=PdfDictionary.Unknown){ //exit on first match as should be all the same
     ii=kidCount;
     found=true;
     }
     }

     //none found so scan recursively
     /**if(!found){

     for(int ii=0;ii<kidCount;ii++){ //iterate through all parts

     subtype=findObjectType(kids[ii]);
     if(subtype!=PdfDictionary.Unknown){ //exit on first match as should be all the same
     ii=kidCount;
     found=true;
     }
     }
     }/**/
    /**
     }
     //}
     return subtype;
     }/**/

    private int processFormObject(int page, boolean debugNew, Map formsProcessed, FormObject formObject, Object objRef, int i, int forms) {
        boolean isOnPage=false;
        if(forms==0){ //check page
            PdfObject pageObj=formObject.getDictionary(PdfDictionary.P);

            byte[] pageRef=null;

            if(pageObj!=null)
                pageRef=pageObj.getUnresolvedData();

            if(pageRef==null || pageObj==null){

                String parent=formObject.getStringKey(PdfDictionary.Parent);

                if(parent!=null){
                    PdfObject parentObj = getParent(parent);

                    pageObj=parentObj.getDictionary(PdfDictionary.P);
                    if(pageObj!=null)
                        pageRef=pageObj.getUnresolvedData();
                }
            }

            if(pageRef==null){

                byte[][] kidList = getKid(formObject);

                boolean hasKids=kidList!=null && kidList.length>0;

                if (hasKids) {

                    int kidCount=kidList.length;

                    FormObject kidObject;
                    for(int jj=0;jj<kidCount;jj++){
                        String key=new String(kidList[jj]);

                        kidObject= (FormObject) cachedObjs.get(key);

                        if(kidObject==null){
                            kidObject = new FormObject(key,formsActionHandler);
                            //kidObject.setPageNumber(page);
                            //kidObject.setPDFRef((String)key);
                            currentPdfFile.readObject(kidObject);

                            cachedObjs.put(key, kidObject);

                        }

                        pageObj=kidObject.getDictionary(PdfDictionary.P);

                        if(pageObj!=null)
                            pageRef=pageObj.getUnresolvedData();

                        if(pageRef!=null)
                            jj=kidCount;
                    }
                }
            }

            int objPage=-1;
            if(pageRef!=null)
                objPage=currentPdfFile.convertObjectToPageNumber(new String(pageRef));

            isOnPage=objPage==page;

        }

        if(forms==1 || isOnPage){


            //if(objRef!=null && objRef.equals("48 0 R"))
            //              System.out.println("c "+forms+" "+isOnPage);

            //code to read the page number from the P (page) object of the form, but it is wrong.
//        	PdfObject pageObj = formObject.getDictionary(PdfDictionary.P);
//        	if(pageObj!=null){
//        		String pageObjRef = pageObj.getObjectRefAsString();
//        		int formPage = decode_pdf.getPageFromObjectRef(pageObjRef);
//        		if(formPage==-1)
//        			formObject.setPageNumber(page);
//        		else
//        			formObject.setPageNumber(formPage);
//        	}else 
            formObject.setPageNumber(page);

            //lose from cache
            cachedObjs.remove(objRef);

            String parent=formObject.getStringKey(PdfDictionary.Parent);

            //clone parent to allow inheritance of values or create new
            if(parent!=null){
                FormObject parentObj = getParent(parent);

                //inherited values
                if(parentObj!=null){
                    //all values are copied from the parent inside this call
                    formObject.setParent(parent,parentObj,true);
                }

                //remove kids from Form
                //formObject.setKeyArray(PdfDictionary.Kids,null);
            }

            //work around for jcentricity.pdf
            /**
             PdfObject A=formObject.getDictionary(PdfDictionary.A);
             if(A!=null){
             PdfArrayIterator rawD=A.getMixedArray(PdfDictionary.Dest);

             if(rawD!=null && rawD.getTokenCount()==1){
             String D=rawD.getNextValueAsString(true);

             if(D!=null && !D.endsWith(" R"))
             D=currentPdfFile.convertNameToRef(D);

             if(D!=null){

             Map map=currentPdfFile.readObject(new PdfObject(1),D,null);

             if(map!=null){
             Object aData=currentField.get("A");
             if(aData!=null && aData instanceof Map)
             ((Map)aData).put("D",map.get("D"));
             }
             }
             }
             }/**/

            fDecoder.createAppearanceString(formObject, currentPdfFile);

            if (formObject!= null){

                if(parent!=null)
                    formObject.setParent(parent);//parent object was added earlier

                if(forms==0)
                    Fforms[i++] = formObject;
                else
                    Aforms[i++] = formObject;

                //also flag
                if(objRef!=null)
                    formsProcessed.put(objRef, "x");
//moved to after createField so the fully qualified names are stored.
//keep this in case we have to return to old functioning.
//                compData.storeRawData(formObject); //store data so user can access
            }
        }
        return i;
    }

    private FormObject getParent(String parent) {

        Map objects=compData.getRawFormData();

        FormObject parentObj;
        parentObj=(FormObject)objects.get(parent);

        if(parentObj==null && parent!=null){ //not yet read so read and cache
            parentObj = new FormObject(parent,formsActionHandler);
            currentPdfFile.readObject(parentObj);

            //remove kids in Parent
            parentObj.setKeyArray(PdfDictionary.Kids,new byte[][]{{0}});

            objects.put(parent, parentObj);

        }
        return parentObj;
    }

    private byte[][] getKid(FormObject formObject) {

        int subtype=formObject.getParameterConstant(PdfDictionary.Subtype);
        if(subtype==PdfDictionary.Tx || subtype==PdfDictionary.Btn)
            return null;

        byte[][] kidList=formObject.getKeyArray(PdfDictionary.Kids);

        if(kidList!=null){
            String parentRef=formObject.getStringKey(PdfDictionary.Parent);

            PdfObject parentObj=(PdfObject) compData.getRawForm(parentRef)[0];

            if(parentObj!=null && parentObj.getKeyArray(PdfDictionary.Kids)!=null)
                kidList=null;
        }

        return kidList;
    }

    /**
     * get properties in form as object with getMethods.
     *
     * This will take either the Name or the PDFref
     *
     * (ie Box or 12 0 R)
     *
     * This returns an object[]

     * In the case of a PDF with radio buttons Box (12 0 R), Box (13 0 R), Box (14 0 R)
     * getFormDataAsObject(Box) would return an Object[] which is actually Object[3]
     * getFormDataAsObject(12 0 R) would return an Object[] which has a single value
     * any of these values can be NULL
     *
     */
    public Object[] getFormDataAsObject(String objectName) {

        return compData.getRawForm(objectName);
    }

     /**
     * display widgets onscreen for range (inclusive)
     */
    public void displayComponentsOnscreen(int startPage, int endPage) {

        //<start-adobe>
        //just incase not removed
        if (this.decode_pdf.getDisplayView() != Display.PAGEFLOW)
            this.removeDisplayComponentsFromScreen();
        //<end-adobe>

        if (showMethods)
            System.out.println(this + " displayComponentsOnscreen " + startPage + ' ' + endPage);

        //make sure this page is inclusive in loop
        endPage++;

        compData.displayComponents(startPage, endPage);

    }


    /**
     * create a widget to handle fields
     */
    private void createField(final FormObject formObject, int formNum) {

        //if HTML create now, otherwise when needed
        boolean processFormsNow =!isLazyInit;
        if(formFactory.getType()!=FormFactory.SWING){
            processFormsNow=true;
        }


        /**/

        if (showMethods)
            System.out.println("createField " + formNum);// + ' ' + formObject);

        Integer widgetType; //no value set

        Object retComponent=null;

        int subtype=formObject.getParameterConstant(PdfDictionary.Subtype);//FT

        //<start-noform>

        //if sig object set global sig object so we can access later
        if(subtype == PdfDictionary.Sig){
            if(sigObject==null) //ensure initialised
                sigObject=new HashSet();

            sigObject.add(formObject);

        }

        //check if a popup is associated
        if(formObject.getDictionary(PdfDictionary.Popup)!=null){
            formObject.setActionFlag(FormObject.POPUP);
        }

        //flags used to alter interactivity of all fields
        boolean readOnly, required, noexport;

        boolean[] flags = formObject.getFieldFlags();//Ff
        if (flags != null) {
            //noinspection UnusedAssignment
            readOnly = flags[FormObject.READONLY_ID];
            //noinspection UnusedAssignment
            required = flags[FormObject.REQUIRED_ID];
            //noinspection UnusedAssignment
            noexport = flags[FormObject.NOEXPORT_ID];

            /*
                    boolean comb=flags[FormObject.COMB_ID];
                    boolean comminOnSelChange=flags[FormObject.COMMITONSELCHANGE_ID];
                    boolean donotScrole=flags[FormObject.DONOTSCROLL_ID];
                    boolean doNotSpellCheck=flags[FormObject.DONOTSPELLCHECK_ID];
                    boolean fileSelect=flags[FormObject.FILESELECT_ID];
                    boolean isCombo=flags[FormObject.COMBO_ID];
                    boolean isEditable=flags[FormObject.EDIT_ID];
                   boolean isMultiline=flags[FormObject.MULTILINE_ID];
                   boolean isPushButton=flags[FormObject.PUSHBUTTON_ID];
                   boolean isRadio=flags[FormObject.RADIO_ID];
                   boolean hasNoToggleToOff=flags[FormObject.NOTOGGLETOOFF_ID];
                   boolean hasPassword=flags[FormObject.PASSWORD_ID];
                   boolean multiSelect=flags[FormObject.MULTISELECT_ID];
                   boolean radioinUnison=flags[FormObject.RADIOINUNISON_ID];
                   boolean richtext=flags[FormObject.RICHTEXT_ID];
                   boolean sort=flags[FormObject.SORT_ID];
                */
        }

        if (debug) {
            if (flags != null) {
                System.out.println("FLAGS - pushbutton=" + flags[16] + " radio=" + flags[15] + " notoggletooff=" +
                        flags[14] + "\n multiline=" + flags[12] + " password=" + flags[13] +
                        "\n combo=" + flags[17] + " editable=" + flags[18] + " readOnly=" + readOnly +
                        "\n BUTTON=" + (subtype == PdfDictionary.Btn) + " TEXT=" + (subtype ==PdfDictionary.Tx) +
                        " CHOICE=" + (subtype == PdfDictionary.Ch) + " SIGNATURE=" + (subtype == PdfDictionary.Sig) +
                        "\n characteristic=" + ConvertToString.convertArrayToString(formObject.getCharacteristics()));
            } else {
                System.out.println("FLAGS - all false");
            }
        }

        if (debug && flags != null && (required || flags[19] || noexport || flags[20] || flags[21] || flags[23] || flags[25] || flags[25]))
            System.out.println("renderer UNTESTED FLAGS - readOnly=" + readOnly + " required=" + required + " sort=" + flags[19] + " noexport=" + noexport +
                    " fileSelect=" + flags[20] + " multiSelect=" + flags[21] + " donotScrole=" + flags[23] + " radioinUnison=" + flags[25] +
                    " richtext=" + flags[25]);

        /** setup field */
        if (subtype == PdfDictionary.Btn) {//----------------------------------- BUTTON  ----------------------------------------
            if(identifyType)
                System.out.println("button");
            //flags used for button types
            //20100212 (ms) Unused ones commented out
            boolean isPushButton = false, isRadio = false;// hasNoToggleToOff = false, radioinUnison = false;
            if (flags != null) {
                isPushButton = flags[FormObject.PUSHBUTTON_ID];
                isRadio = flags[FormObject.RADIO_ID];
                //hasNoToggleToOff = flags[FormObject.NOTOGGLETOOFF_ID];
                //radioinUnison = flags[FormObject.RADIOINUNISON_ID];
            }

            if (isPushButton) {

                widgetType=FormFactory.PUSHBUTTON;
                if(processFormsNow){
                    retComponent = formFactory.pushBut(formObject);
                }

            }else if(isRadio){
                widgetType=FormFactory.RADIOBUTTON;

                if(processFormsNow){
                    retComponent = formFactory.radioBut(formObject);
                }
            }else {
                widgetType=FormFactory.CHECKBOXBUTTON;

                if(processFormsNow){
                    retComponent = formFactory.checkBoxBut(formObject);
                }
            }

        } else {
            if (subtype ==PdfDictionary.Tx) { //-----------------------------------------------  TEXT --------------------------------------
                if(identifyType)
                    System.out.println("text");
                //flags used for text types
                // 20100212 (ms) commented out ones not used
                boolean isMultiline = false, hasPassword = false;// doNotScroll = false, richtext = false, fileSelect = false, doNotSpellCheck = false;
                if (flags != null) {
                    isMultiline = flags[FormObject.MULTILINE_ID];
                    hasPassword = flags[FormObject.PASSWORD_ID];
                    //doNotScroll = flags[FormObject.DONOTSCROLL_ID];
                    //richtext = flags[FormObject.RICHTEXT_ID];
                    //fileSelect = flags[FormObject.FILESELECT_ID];
                    //doNotSpellCheck = flags[FormObject.DONOTSPELLCHECK_ID];
                }

                if (isMultiline) {

                    if (hasPassword) {

                        widgetType=FormFactory.MULTILINEPASSWORD;

                        if(processFormsNow){
                            retComponent = formFactory.multiLinePassword(formObject);
                        }

                    } else {

                        widgetType=FormFactory.MULTILINETEXT;

                        if(processFormsNow){
                            retComponent = formFactory.multiLineText(formObject);
                        }
                    }
                } else {//singleLine

                    if (hasPassword) {

                        widgetType=FormFactory.SINGLELINEPASSWORD;

                        if(processFormsNow){
                            retComponent = formFactory.singleLinePassword(formObject);
                        }
                    } else {

                        widgetType=FormFactory.SINGLELINETEXT;

                        if(processFormsNow){
                            retComponent = formFactory.singleLineText(formObject);
                        }

                    }
                }
            }else if (subtype==PdfDictionary.Ch) {//----------------------------------------- CHOICE ----------------------------------------------
                if(identifyType)
                    System.out.println("choice");
                //flags used for choice types
                //20100212 (ms) Unused ones commented out
                boolean isCombo = false;// multiSelect = false, sort = false, isEditable = false, doNotSpellCheck = false, comminOnSelChange = false;
                if (flags != null) {
                    isCombo = flags[FormObject.COMBO_ID];
                    //multiSelect = flags[FormObject.MULTISELECT_ID];
                    //sort = flags[FormObject.SORT_ID];
                    //isEditable = flags[FormObject.EDIT_ID];
                    //doNotSpellCheck = flags[FormObject.DONOTSPELLCHECK_ID];
                    //comminOnSelChange = flags[FormObject.COMMITONSELCHANGE_ID];
                }

                if (isCombo) {// || (type==XFAFORM && ((XFAFormObject)formObject).choiceShown!=XFAFormObject.CHOICE_ALWAYS)){

                    widgetType=FormFactory.COMBOBOX;

                    if(processFormsNow){
                        retComponent = formFactory.comboBox(formObject);
                    }

                } else {//it is a list

                    widgetType=FormFactory.LIST;

                    if(processFormsNow){
                        retComponent = formFactory.listField(formObject);
                    }
                }
            } else if (subtype == PdfDictionary.Sig) {
                if(identifyType)
                    System.out.println("signature");

                widgetType=FormFactory.SIGNATURE;

                if(processFormsNow){
                    retComponent = formFactory.signature(formObject);
                }
            } else{//assume annotation if (formType == ANNOTATION) {
                if(identifyType)
                    System.out.println("annotation "+formObject.getObjectRefAsString()+ ' ' +formFactory);
                //<end-noform>

                widgetType=FormFactory.ANNOTATION;

                if(processFormsNow){
                    retComponent = formFactory.annotationButton(formObject);
                }
                //<start-noform>

//            } else {
//				if(identifyType)
//					System.out.println("else @@@@@@");
//
//				if (debug) {
//					if (flags != null) {
//						System.out.println("UNIMPLEMENTED field=FLAGS - pushbutton=" + flags[16] + " radio=" + flags[15] +
//								" multiline=" + flags[12] + " password=" + flags[13] + " combo=" + flags[17] +
//								" BUTTON=" + button + " TEXT=" + text + " CHOICE=" + choice);
//					} else
//						System.out.println("UNIMPLEMENTED field=BUTTON=" + button + " TEXT=" + text + " CHOICE=" + choice + " FLAGS=all false");
//				}
            }
        }

        //<end-noform>

        formObject.setFormType(widgetType);

        //set Component specific values such as Tooltip and mouse listener
        compData.completeField(formObject, formNum, widgetType, retComponent, currentPdfFile);
    }



    /**
     * return the component associated with this objectName (returns null if no match). Names are case-sensitive.
     * Please also see method getComponentNameList(int pageNumber),
     * if objectName is null then all components will be returned
     */
    public Object[] getComponentsByName(String objectName) {

        /**make sure all forms decoded*/
        for (int p = 1; p < this.pageCount + 1; p++) //add init method and move scaling/rotation to it
            createDisplayComponentsForPage(p,null);

        if (showMethods)
            System.out.println("getComponentNameList " + objectName);

        return compData.getComponentsByName(objectName);

    }

    /**
     * return a List containing the names of  forms on a specific page which has been decoded.
     * <p/>
     * <p/>
     *
     * @throws PdfException An exception is thrown if page not yet decoded
     */
    public List getComponentNameList() throws PdfException {

        if (showMethods)
            System.out.println("getComponentNameList");

        if (ATotalCount==0 && FfieldCount==0)// || compData.trackPagesRendered == null)
            return null;

        //must be switched off!
        boolean rasterizeForms=compData.formsRasterizedForDisplay();
        compData.setRasterizeForms(false);

        /**make sure all forms decoded*/
        for (int p = 1; p < this.pageCount + 1; p++)
            createDisplayComponentsForPage(p,null);

        //restore
        compData.setRasterizeForms(rasterizeForms);

        return getComponentNameList(-1);

    }

    /**
     * return a List containing the names of  forms on a specific page which has been decoded.
     *
     */
    public List getComponentNameList(int pageNumber) throws PdfException {

        if (showMethods)
            System.out.println("getComponentNameList " + pageNumber);

        /**make sure all forms decoded on page*/
        if(pageNumber!=-1){
            int id=compData.getStartComponentCountForPage(pageNumber);
            if(id==-1){
                createDisplayComponentsForPage(pageNumber,null);
            }
        }
        
        if(FfieldCount==0 && ATotalCount==0)
            return null;
        else
            return compData.getComponentNameList(pageNumber);


    }

    /**
     * setup object which creates all GUI objects
     */
    public void setFormFactory(FormFactory newFormFactory) {
        //if (showMethods)
//			System.out.println(this+" setFormFactory " + newFormFactory);

        formFactory = newFormFactory;

        formsActionHandler.setActionFactory(formFactory.getActionFactory());

        /**
         * allow user to create custom structure to hold data
         */
        compData=formFactory.getCustomCompData();

        //pass in Javascript
        compData.setJavascript(javascript);

        //formFactory.setDataObjects(compData);

    }


    /**
     * does nothing or FORM except set type, resets annots and last values
     */
    public void openFile(int pageCount) {

        if (showMethods)
            System.out.println("openFile " + pageCount);

        this.pageCount = pageCount;

        //flush data
        compData.reset(null);
        proxyObjects.clear();

        compData.flushFormData();

        cachedObjs.clear();
    }

    /**return form data in a Map
     public Map getSignatureObject(String ref) {

     Map certObj=this.currentPdfFile.readObject(new PdfObject(ref), ref, null);

     Object sigRef=certObj.get("V");

     if(sigRef == null)
     return null;

     Map fields=new HashMap();
     fields.put("Name", "x");
     fields.put("Reason", "x");
     fields.put("Location", "x");
     fields.put("M", "x");
     fields.put("Cert", "x");

     //allow for ref or direct
     if(sigRef instanceof String){
     certObj=currentPdfFile.readObject(new PdfObject((String)sigRef), (String)sigRef, fields);
     }else
     certObj=(Map)sigRef;

     //make string into strings
     Iterator strings=fields.keySet().iterator();
     while(strings.hasNext()){
     Object fieldName=strings.next();
     byte[] value=(byte[]) certObj.get(fieldName);
     if(value!=null && !fieldName.equals("Cert"))
     certObj.put(fieldName, PdfReader.getTextString(value));

     }

     return certObj;
     }/**/

    /**
     * get GUIData object with all widgets
     */
    public GUIData getCompData() {
        return compData;
    }

    /**return Signature as iterator with one or more objects or null*/
    public Iterator getSignatureObjects() {
        if(sigObject==null)
            return null;
        else
            return sigObject.iterator();
    }

    public ActionHandler getActionHandler() {
        return formsActionHandler;
    }

    public FormFactory getFormFactory() {
        return formFactory;
    }

    public Map getRawFormData(){
        return compData.getRawFormData();
    }

    public void setIgnoreForms(boolean ignoreForms) {
        this.ignoreForms=ignoreForms;
    }

    public boolean ignoreForms() {
        return ignoreForms;
    }

    public void dispose() {

        pageData=null;

        AfieldCount = null;

        fDecoder=null;

        if( formsActionHandler!=null )
        	formsActionHandler.dispose();
        formsActionHandler=null;

        //linkHandler=null;

        javascript=null;


        Fforms=null;

        Aforms=null;

        fieldList=null;
        annotList=null;

        formFactory=null;

        compData=null;
        proxyObjects.clear();

        sigObject=null;

        cachedObjs=null;

        pageData=null;

        currentPdfFile=null;

        fDecoder=null;

        //linkHandler=null;

        acroObj=null;

    }

    public Javascript getJavaScriptObject() {
        return javascript;
    }

    /**
     * get Iterator with list of all Annots on page or
     * return null if no Annots  - no longer needs
     * call to decodePage beforehand as checks itself
     */
    public PdfArrayIterator getAnnotsOnPage(final int page) {


        //idiot test (if I can make this mistake lets trap it)
        if(page<1 || page>decode_pdf.getPageCount())
            throw new RuntimeException("Page "+page+" out of range (1 - "+decode_pdf.getPageCount()+ ')');

        //check annots decoded - will just return if done
        if (isLazyInit || SwingUtilities.isEventDispatchThread()){

            createDisplayComponentsForPage(page,null);

        }else {
//            final Runnable doPaintComponent = new Runnable() {
//                public void run() {
//                    decode_pdf.waitForDecodingToFinish();
//                    createDisplayComponentsForPage(page,null);
//                }
//            };
            // SwingUtilities.invokeLater(doPaintComponent);
            new SwingWorker() {
                public Object construct() {
                    //System.out.println("create forms");

                    decode_pdf.waitForDecodingToFinish();
                    createDisplayComponentsForPage(page,null);

                    //  System.out.println("done");
                    return null;
                }
            }.start();
        }

        if(annotList!=null && annotList.length>page && annotList[page]!=null){
            annotList[page].resetToStart();
            return annotList[page];
        }else{
            return null;
        }
    }

    /**
     * checks if the string <b>toAdd</b> is already in the list,
     * if not adds it and returns true,
     * returns false if the item should not be added
     */
    private boolean addItem(Object toAdd){
        if(toAdd==null)
            return false;
        //toAdd = Strip.removeArrayDeleminators(toAdd.toString());

        if(!currentItems.containsKey(toAdd)){
            currentItems.put(toAdd,"x");
            return true;
        }else {
            return false;
        }
    }

    /**JS check all forms to see if any have been updated and if so update them */
    public void updateChangedForms() {
        // Get the raw form objects from the component data
        Map forms = compData.getRawFormData();

        //if some forms exist iterate over them
        if(forms.size()>1){
            Iterator formsIter = forms.keySet().iterator();
            FormObject form;
            while(formsIter.hasNext()){
                form = (FormObject) forms.get(formsIter.next());
                if (form != null){

                    //check to see if the text color has changed
                    if(form.hasColorChanged()){
                        //update to the new color
                        compData.setTextColor(form.getObjectRefAsString(), form.getTextColor());
                        compData.invalidate(form.getTextStreamValue(PdfDictionary.T));

                        // reset color changed, so we dant do it again unless needed
                        form.resetColorChanged();
                    }

                    // if the form has changed reload the values into the display object
                    if(form.hasValueChanged()){
                        //dont set unformatted value here
                        String objRef = form.getObjectRefAsString();
                        compData.setUnformattedValue(objRef, compData.getValue(objRef));
                        //dont set the last valid value as were not sure if it is valid

                        //change the values within the componentData
                        compData.setValue(form.getObjectRefAsString(), form.getValue(), false, false);


                        compData.invalidate(form.getTextStreamValue(PdfDictionary.T));

                        // reset values changed so we dant do it again unless needed
                        form.resetFormChanged();
                    }

                    //check to see if the display has chnaged
                    if(form.hasDisplayChanged()){

                        boolean[] characteristic = form.getCharacteristics();
                        //check if the form should be hidden, or shown now
                        if (characteristic[0] || characteristic[1] || characteristic[5]) {
                            compData.setCompVisible(form.getObjectRefAsString(),false);
                        }else {
                            compData.setCompVisible(form.getObjectRefAsString(),true);
                        }
                    }
                }
            }
        }
    }

    /**JS returns an array of names, for which this parent object is extended into. */
    public String[] getChildNames(String name){
        return compData.getChildNames(name);
    }

    /**CO if present */
    public PdfArrayIterator getCalculationOrder(){
        return CO;
    }

    /**JS return the first field by the given name, name should never be NULL */
    public Object getField(String name){
        if(name==null)
            return null;

            return compData.getRawForm(name);
    }

    /**JS resets all the fields within this form. */
    public void resetForm(){ resetForm(null);}

    /**JS resets the fields within this form.
     * @param aFields - defines which fields to reset, or all fields if null.
     */
    public void resetForm(String[] aFields){
        formsActionHandler.getActionFactory().reset(aFields);
    }

    //<start-adobe><start-thin>
    public int getPageNum(){

        SwingGUI swingGUI=((SwingGUI)decode_pdf.getExternalHandler(Options.SwingContainer));
        if(swingGUI!=null)
            return swingGUI.getCurrentPage();
        else
            return 1;
    }
    //<end-thin><end-adobe>

    public void setPageNum(int page){
        //change the page to the page defined, using Unknown as this is from javascript so we dont care.

        //page is indexes so add 1 for actual page
        page++;

        //<start-adobe><start-thin>
        formsActionHandler.changeTo(null, page, null, null,true);
        //<end-thin><end-adobe>
    }

    public void setZoomType(String newType){

        //<start-adobe><start-thin>
        if(newType.equals("FitPage"))
            formsActionHandler.changeTo(null, -1, null, -3,true);
        else if(newType.equals("FitWidth"))
            formsActionHandler.changeTo(null, -1, null, -1,true);
        else if(newType.equals("FitHeight"))
            formsActionHandler.changeTo(null, -1, null, -2,true);
//		else if(newType.equals("FitVisibleWidth"))//check p263 of "javascript for acrobat API reference.pdf"
//			;
//		else if(newType.equals("Preferred"))
//			;
//		else if(newType.equals("ReflowWidth"))
//			;
//		else if(newType.equals("NoVary"))
//			;
        else {

        }

        //<end-thin><end-adobe>
    }

    //<start-adobe><start-thin>
    /*JS used to see if the forms need to be saved or not */
    public boolean getDirty(){
        SwingGUI swingGUI=((SwingGUI)decode_pdf.getExternalHandler(Options.SwingContainer));
        if(swingGUI!=null)
            return swingGUI.getFormsDirtyFlag();
        else
            return false;
    }

    /*JS sets the flag that defines if the forms should be saved or not*/
    public void setDirty(boolean dirty){
        SwingGUI swingGUI=((SwingGUI)decode_pdf.getExternalHandler(Options.SwingContainer));
        if(swingGUI!=null)
            swingGUI.setFormsDirtyFlag(dirty);
    }

    //<end-thin><end-adobe>

    public boolean hasFormsOnPage(int page) {

        boolean hasAnnots=(annotList!=null && annotList.length>page && annotList[page]!=null);
        boolean hasForm=(hasXFA && fDecoder.hasXFADataSet())||fieldList!=null;
        return hasAnnots || hasForm;
    }

    public PdfObject getFormResources() {
        return AcroRes;
    }

    public boolean formsRasterizedForDisplay() {
        return compData.formsRasterizedForDisplay();
    }
}
