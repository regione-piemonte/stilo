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
* FormObject.java
* ---------------
*/
package org.jpedal.objects.raw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.jpedal.color.DeviceCMYKColorSpace;


import org.jpedal.objects.acroforms.actions.ActionHandler;
import org.jpedal.objects.acroforms.actions.DefaultActionHandler;

import java.io.File;

import org.jpedal.external.Options;
//<start-adobe><start-thin>
import org.jpedal.examples.viewer.gui.SwingGUI;
//<end-thin><end-adobe>
import org.jpedal.objects.acroforms.utils.ConvertToString;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.ObjectCloneFactory;
import org.jpedal.utils.StringUtils;

import javax.swing.*;

public class FormObject extends PdfObject{

    String nameUsed="";

    private boolean tested=false;

	private int formType = -1;

    Object guiComp=null;
	
    //rotation on page
    private int rawRotation=0;
    
    //show if a kid (which is linked to other objects
    private boolean isKid=false;

	//unknown CMAP as String
	private String EOPROPtype, Filter=null, Location=null, M, Reason, SubFilter;
	private byte[] rawEOPROPtype, rawFilter, rawLocation, rawM,rawReason, rawSubFilter;

    /**
     * the C color for annotations
     */
    private Color cColor;
    /**
     * the contents for any text display on the annotation
     */
    private String contents;
    /**
     * whether the annotation is being displayed or not by default
     */
    private boolean show = false;

    private Map OptValues=null; // values from Opt

    private boolean popupBuilt = false;

    private Object popupObj;

    /** 1 form flag indexes for the field flags */
    public static final int READONLY_ID = 1;
    /** 2 form flag indexes for the field flags */
    public static final int REQUIRED_ID = 2;
    /** 3 form flag indexes for the field flags */
    public static final int NOEXPORT_ID = 3;
    /** 13 form flag indexes for the field flags */
    public static final int MULTILINE_ID = 13;
    /** 14 form flag indexes for the field flags */
    public static final int PASSWORD_ID = 14;
    /** 15 form flag indexes for the field flags */
    public static final int NOTOGGLETOOFF_ID = 15;
    /** 16 form flag indexes for the field flags */
    public static final int RADIO_ID = 16;
    /** 17 form flag indexes for the field flags */
    public static final int PUSHBUTTON_ID = 17;
    /** 18 form flag indexes for the field flags */
    public static final int COMBO_ID = 18;
    /** 19 form flag indexes for the field flags */
    public static final int EDIT_ID = 19;
    /** 20 form flag indexes for the field flags */
    public static final int SORT_ID = 20;
    /** 21 form flag indexes for the field flags */
    public static final int FILESELECT_ID = 21;
    /** 22 form flag indexes for the field flags */
    public static final int MULTISELECT_ID = 22;
    /** 23 form flag indexes for the field flags */
    public static final int DONOTSPELLCHECK_ID = 23;
    /** 24 form flag indexes for the field flags */
    public static final int DONOTSCROLL_ID = 24;
    /** 25 form flag indexes for the field flags */
    public static final int COMB_ID = 25;
    /** 26 form flag indexes for the field flags */
    public static final int RICHTEXT_ID = 26;//same as RADIOINUNISON_ID (radio buttons)
    /** 26 form flag indexes for the field flags */
    public static final int RADIOINUNISON_ID = 26;//same as RICHTEXT_ID (text fields)
    /** 27 form flag indexes for the field flags */
    public static final int COMMITONSELCHANGE_ID = 27;

    /* variables for forms to check with the (Ff)  flags field
     * (1<<bit position -1), to get required result
     */
     final private static int READONLY_BIT=(1);//1
     final private static int REQUIRED_BIT=(1<<1);//2
     final private static int NOEXPORT_BIT=(1<<2);//4
     final private static int MULTILINE_BIT=(1<<12);//4096;
     final private static int PASSWORD_BIT=(1<<13);//8192;
     final private static int NOTOGGLETOOFF_BIT=(1<<14);//16384;
     final private static int RADIO_BIT=(1<<15);//32768;
     final private static int PUSHBUTTON_BIT=(1<<16);//65536;
     final private static int COMBO_BIT=(1<<17);//131072;
     final private static int EDIT_BIT=(1<<18);//262144;
     final private static int SORT_BIT=(1<<19);//524288;
     final private static int FILESELECT_BIT=(1<<20);//1048576
     final private static int MULTISELECT_BIT=(1<<21);//2097152
     final private static int DONOTSPELLCHECK_BIT=(1<<22);//4194304
     final private static int DONOTSCROLL_BIT=(1<<23);//8388608
     final private static int COMB_BIT=(1<<24);//16777216
     final private static int RADIOINUNISON_BIT=(1<<25);//33554432 //same as RICHTEXT_ID
     final private static int RICHTEXT_BIT=(1<<25);//33554432 //same as RADIOINUNISON_ID
     final private static int COMMITONSELCHANGE_BIT=(1<<26);//67108864
     
    protected String[] OptString=null;

	protected boolean isXFAObject = false;
    private String parentRef;
    private PdfObject parentPdfObj;
    private String selectedItem;

    private boolean textColorChanged = false;
    private float[] textColor;
    private Font textFont;
    private int textSize = -1;
    private String textString=null;

    private boolean appearancesUsed = false;
    private boolean offsetDownIcon = false;
    private boolean noDownIcon = false;
    private boolean invertDownIcon = false;

    private String onState;
    private String currentState;
    private String normalOnState;
    private BufferedImage normalOffImage = null;
    private BufferedImage normalOnImage;
    private BufferedImage rolloverOffImage = null;
    private BufferedImage rolloverOnImage;
    private BufferedImage downOffImage = null;
    private BufferedImage downOnImage;
    
    //flag used to handle POPUP internally
    public static final int POPUP = 1;

    private String layerName=null;
    
    private ActionHandler formHandler;

    private boolean[] Farray=null;
    
    protected Rectangle BBox=null;

    protected float[] C,QuadPoints,RD,Rect;

	protected boolean[] flags = null;

	boolean Open=true, H_Boolean=true; //note default it true but false in Popup!!

    boolean NeedAppearances=false;

	protected int F=-1,Ff=-1,MaxLen=-1, W=-1;

	protected int Q=-1; //default value

	int SigFlags=-1, StructParent=-1;

	protected int TI=-1;

    protected PdfObject A;

    //internal flag used to store status on additional actions when we decode
    private int popupFlag=0;


	protected PdfObject AA,AP=null, Cdict;

	private PdfObject BI;

	protected PdfObject BS;

	protected PdfObject D, IF;

    protected PdfObject RichMediaContent;

	/**
     * Filters the MK command and its properties
     * <p/>
     * appearance characteristics dictionary  (all optional)
     * R rotation on wiget relative to page
     * BC array of numbers, range between 0-1 specifiying the border color
     * number of array elements defines type of colorspace
     * 0=transparant
     * 1=gray
     * 3=rgb
     * 4=cmyk
     * BG same as BC but specifies wigets background color
     * <p/>
     * buttons only -
     * CA its normal caption text
     * <p/>
     * pushbuttons only -
     * RC rollover caption text
     * AC down caption text
     * I formXObject defining its normal icon
     * RI formXObject defining its rollover icon
     * IX formXObject defining its down icon
     * IF icon fit dictionary, how to fit its icon into its rectangle
     * (if specified must contain all following)
     * SW when it should be scaled to fit ( default A)
     * A always
     * B when icon is bigger
     * S when icon is smaller
     * N never
     * S type of scaling - (default P)
     * P keep aspect ratio
     * A ignore aspect ratio (fit exactly to width and hight)
     * A array of 2 numbers specifying its location when scaled keeping the aspect ratio
     * range between 0.0-1.0, [x y] would be positioned x acress, y up
     * TP positioning of text relative to icon - (integer)
     * 0=caption only
     * 1=icon only
     * 2=caption below icon
     * 3=caption above icon
     * 4=caption on right of icon
     * 5=caption on left of icon
     * 6=caption overlaid ontop of icon
     */
	private PdfObject MK;

	private PdfObject DC, DP, DR, DS, E, Fdict, Fo, FS,
			JS, K, Nobj, Next, O, PC, PI, PO, Popup,
			PV, R, Sig, Sound, U, V, Win, WP, WS, X;

	protected int[] ByteRange, I;

	protected byte[] rawAS, rawCert, rawContactInfo, rawContents, rawDstring, rawDA, rawDV, rawFstring, rawJS, rawH, rawN, rawNM, rawPstring, rawRC, rawS, rawSubj, rawT, rawTM, rawTU,
	rawURI, rawV,rawX;

    protected int FT=-1;

    protected String AS, Cert, ContactInfo, Contents, Dstring, DA, DV, Fstring, JSString, H, N, NM, Pstring, RC, S, Subj, T, TM, TU, URI, Vstring;

    private byte[][] Border, DmixedArray, Fields, State, rawXFAasArray;
    protected PdfObject Bl, OC, Off, On, P;

	private PdfObject XFAasStream;

    protected Object[] CO, Opt,Reference;

	protected byte[][] Kids;
	
	private String htmlName = null;

    public void setHTMLName(String name) {
		htmlName = name;
	}
	
	public String getHTMLName(){
		return htmlName;
	}

    public FormObject(String ref) {
        super(ref);
        objType=PdfDictionary.Form;
    }

    public FormObject(String ref, boolean flag) {
        super(ref);
        objType=PdfDictionary.Form;
        this.includeParent=flag;
    }


    public FormObject(int ref, int gen) {
       super(ref,gen);

       objType=PdfDictionary.Form;
    }


    public FormObject(int type) {
    	super(type);
    	objType=PdfDictionary.Form;
	}

	public FormObject() {
		super();
		objType=PdfDictionary.Form;
	}

    public FormObject(String ref, ActionHandler inFormHandler, int rot) {

	    super(ref);
		formHandler = inFormHandler;
        objType=PdfDictionary.Form;

        rawRotation=rot;
	}

    public FormObject(String ref, ActionHandler inFormHandler) {

	    super(ref);
		formHandler = inFormHandler;
        objType=PdfDictionary.Form;
	}

    public FormObject(String ref, int parentType) {
        super(ref);
        objType=PdfDictionary.Form;

        this.parentType=parentType;

    }
    
    public ActionHandler getHandler(){
    	return formHandler;
    }

    public void setHandler(ActionHandler inFormHandler) {
		formHandler = inFormHandler;
	}
    
	public boolean getBoolean(int id){

        switch(id){

            case PdfDictionary.H:
        	return H_Boolean;

            case PdfDictionary.NeedAppearances:
        	return NeedAppearances;

        case PdfDictionary.Open:
        	return Open;

            default:
            	return super.getBoolean(id);
        }

    }

    public void setBoolean(int id,boolean value){

        switch(id){

            case PdfDictionary.H:
        	H_Boolean=value;
        	break;

            case PdfDictionary.NeedAppearances:
        	NeedAppearances=value;
        	break;

        case PdfDictionary.Open:
        	Open=value;
        	break;



            default:
                super.setBoolean(id, value);
        }
    }

    /**
     * used internally to set status while parsing - should not be called
     * @param popup
     */
    public void setActionFlag(int popup) {
        popupFlag=popup;
	}

    /**
     * get status found during decode
     */
     public int getActionFlag() {
        return popupFlag;
    }

    /**
    public void setFloatNumber(int id,float value){

        switch(id){

	        case PdfDictionary.R:
	    		R=value;
	    		break;

            default:

                super.setFloatNumber(id,value);
        }
    }

    public float getFloatNumber(int id){

        switch(id){

            case PdfDictionary.R:
        		return R;

            default:

                return super.getFloatNumber(id);
        }
    }  /**/

    public PdfObject getDictionary(int id){

        switch(id){

            case PdfDictionary.A:
               return A;

            case PdfDictionary.AA:
                return AA;

            case PdfDictionary.AP:

            	if(AP==null)
            		AP=new FormObject();
	        	return AP;

            case PdfDictionary.BI:
	        	return BI;

	        case PdfDictionary.Bl:
	        	return Bl;

	        case PdfDictionary.BS:
	        	if(BS==null){
	        		if(parentPdfObj!=null){
            			PdfObject BSdic = parentPdfObj.getDictionary(PdfDictionary.BS);
            			if(BSdic!=null){
            				return (PdfObject)BSdic.clone();
            			}
            		}
					BS = new FormObject();
	        	}
	        	return BS;

	        case PdfDictionary.C:
	        	return Cdict;


	        //case PdfDictionary.C2:
	        	//return C2;

	        case PdfDictionary.D:
	        	return D;

	        case PdfDictionary.DC:
	        	return DC;

	        case PdfDictionary.DP:
	        	return DP;

            case PdfDictionary.DR:
	        	return DR;

	        case PdfDictionary.DS:
	        	return DS;

	        case PdfDictionary.E:
	        	return E;

	        case PdfDictionary.F:
	        	return Fdict;

	        case PdfDictionary.Fo:
	        	return Fo;
	        	
	        case PdfDictionary.FS:
	        	return FS;	

	        case PdfDictionary.JS:
                return JS;

	        //case PdfDictionary.I:
	        	//return I;
                
            case PdfDictionary.IF:
	        	return IF;    

	        case PdfDictionary.K:
	        	return K;

            case PdfDictionary.MK: //can't return null

            	if(MK==null){
            		if(parentPdfObj!=null){
            			PdfObject MKdic = parentPdfObj.getDictionary(PdfDictionary.MK);
            			if(MKdic!=null){
            				return (PdfObject)MKdic.clone();
            			}
            		}
            		MK=new MKObject();
            	}
            	return MK;

            case PdfDictionary.N:
	        	return Nobj;

            case PdfDictionary.Next:
            	return Next;

            case PdfDictionary.O:
	        	return O;

            case PdfDictionary.OC:
                return OC;

            case PdfDictionary.Off:

                //System.out.println("Off "+this.getObjectRefAsString()+" "+Off);
//                if(Off==null){
//                    System.out.println(otherValues);
//                    return (PdfObject) otherValues.get("Off");
//                }else
                    return Off;

            case PdfDictionary.On:
              //    System.out.println("On "+this.getObjectRefAsString()+" "+On);
//                if(On==null){
//                    System.out.println(otherValues);
//                    return (PdfObject) otherValues.get("On");
//                }else
            	    return On;

            case PdfDictionary.P:
	        	return P;

            case PdfDictionary.PC:
	        	return PC;

            case PdfDictionary.PI:
	        	return PI;

            case PdfDictionary.PO:
	        	return PO;

            case PdfDictionary.Popup:
	        	return Popup;

            case PdfDictionary.PV:
	        	return PV;

            case PdfDictionary.R:
	        	return R;

            case PdfDictionary.RichMediaContent:
	        	return RichMediaContent;

            case PdfDictionary.Sig:
	        	return Sig;

            case PdfDictionary.Sound:
	        	return Sound;

            case PdfDictionary.U:
	        	return U;

            case PdfDictionary.V:
	        	return V;

            case PdfDictionary.Win:
	        	return Win;

            case PdfDictionary.WP:
	        	return WP;

            case PdfDictionary.WS:
	        	return WS;

            case PdfDictionary.X:
                return X;

            case PdfDictionary.XFA:
                return XFAasStream;

            default:
                return super.getDictionary(id);
        }
    }

    public void setIntNumber(int id,int value){

        switch(id){

            case PdfDictionary.F:
	        	F=value;
	        break;

	        case PdfDictionary.Ff:
	        	Ff=value;
	        	commandFf(Ff);
	        break;

	        case PdfDictionary.Q: //correct alignment converted to Java value

	        	switch(value){

		        case 0:
		        	Q = JTextField.LEFT;
	        	break;

		        case 1:
		        	Q = JTextField.CENTER;
		        	break;

		        case 2:
		        	Q = JTextField.RIGHT;
		        	break;

		        default:
		        	Q = JTextField.LEFT;
		        	break;
	        	}

	        break;

	        case PdfDictionary.MaxLen:
	        	MaxLen=value;
	        	break;

	        case PdfDictionary.Rotate://store in MK so works for Annot
	        	if(MK==null)
            		MK=new MKObject();
                
                //factor in page rotation
                if(rawRotation==0)
	                MK.setIntNumber(PdfDictionary.R,value);
                else{


                    int diff=rawRotation-value;
                    if(diff<0)
                        diff=360+diff;

                    //if(diff!=0)
	                    MK.setIntNumber(PdfDictionary.R,diff);

                }

	        break;

            case PdfDictionary.SigFlags:
                SigFlags=value;
            break;

            case PdfDictionary.StructParent:
	        	StructParent=value;
	        break;

	        case PdfDictionary.TI:
	            TI=value;
	        break;

	        case PdfDictionary.W:
	            W=value;
	        break;

            default:
            	super.setIntNumber(id, value);
        }
    }

    public int getInt(int id){

        switch(id){

            case PdfDictionary.F:
        		return F;

        	case PdfDictionary.Ff:
                return Ff;

        	case PdfDictionary.MaxLen:
        		return MaxLen;

            case PdfDictionary.Q:
                return Q;

            case PdfDictionary.SigFlags:
                return SigFlags;

            case PdfDictionary.StructParent:
        		return StructParent;

        	case PdfDictionary.TI:
	            return TI;

        	case PdfDictionary.W:
	            return W;

            default:
            	return super.getInt(id);
        }
    }

    public void setDictionary(int id,PdfObject value){

    	value.setID(id);

        //if in AP array as other value store here
        if(currentKey!=null){

            //System.out.println("Other values---- "+id+" "+value+" "+objType);
            setOtherValues(id, value);
            return;
        }

        switch(id){

            case PdfDictionary.A:
        		A=value;
        	break;

            case PdfDictionary.AA:
        		AA=value;
        	break;

            case PdfDictionary.AP:
	        	AP=value;

	        	//copy across
	        	if(MK==null && AP!=null && AP.getDictionary(PdfDictionary.N)!=null)
            		MK=AP.getDictionary(PdfDictionary.N).getDictionary(PdfDictionary.MK);

			break;

            case PdfDictionary.BI:
	        	BI=value;
			break;

            case PdfDictionary.Bl:
	        	Bl=value;
			break;

	        case PdfDictionary.BS:
	        	BS=value;
			break;

	        case PdfDictionary.C:
	        	Cdict=value;
			break;

	        //case PdfDictionary.C2:
	        	//C2=value;
			//break;

	        case PdfDictionary.D:
	        	D=value;
			break;

	        case PdfDictionary.DC:
	        	DC=value;
			break;

	        case PdfDictionary.DP:
	        	DP=value;
			break;

            case PdfDictionary.DR:
	        	DR=value;
			break;

	        case PdfDictionary.DS:
	        	DS=value;
			break;

	        case PdfDictionary.E:
	        	E=value;
			break;

	        case PdfDictionary.F:
	        	Fdict=value;
			break;

	        case PdfDictionary.Fo:
	        	Fo=value;
			break;
			
	        case PdfDictionary.FS:
	        	FS=value;
			break;
			
	        case PdfDictionary.IF:
                IF=value;
            break;

	        case PdfDictionary.JS:
                JS=value;
            break;

	        case PdfDictionary.K:
	        	K=value;
			break;

			//case PdfDictionary.I:
	        	//I=value;
			//break;

            case PdfDictionary.MK:
            	MK=value;
            break;

            case PdfDictionary.N:
	        	Nobj=value;
			break;

            case PdfDictionary.Next:
	        	Next=value;
			break;

            case PdfDictionary.O:
	        	O=value;
			break;

            case PdfDictionary.OC:
                OC=value;
            break;

            case PdfDictionary.Off:
            	Off=value;
            break;

            case PdfDictionary.On:
            	On=value;
            break;

            case PdfDictionary.P:
	        	P=value;
			break;

            case PdfDictionary.PC:
	        	PC=value;
			break;

            case PdfDictionary.PI:
	        	PI=value;
			break;

            case PdfDictionary.PO:
	        	PO=value;
			break;

            case PdfDictionary.Popup:
                Popup=value;
			break;

            case PdfDictionary.PV:
	        	PV=value;
			break;

            case PdfDictionary.R:
	        	R=value;
			break;

            case PdfDictionary.RichMediaContent:
	        	RichMediaContent=value;
			break;

            case PdfDictionary.Sig:
	        	Sig=value;
			break;

            case PdfDictionary.Sound:
	        	Sound=value;
			break;

            case PdfDictionary.U:
	        	U=value;
			break;

            case PdfDictionary.V:
	        	V=value;
			break;

            case PdfDictionary.Win:
	        	Win=value;
			break;

            case PdfDictionary.WP:
	        	WP=value;
			break;

            case PdfDictionary.WS:
	        	WS=value;
			break;

            case PdfDictionary.X:
	        	X=value;
			break;

            case PdfDictionary.XFA:
            	XFAasStream=value;
    		break;

            default:
            	super.setDictionary(id, value);
        }
    }


    public int setConstant(int pdfKeyType, int keyStart, int keyLength, byte[] raw) {

        int PDFvalue =PdfDictionary.Unknown;

        int id=0,x=0,next;

        try{

            //convert token to unique key which we can lookup

            for(int i2=keyLength-1;i2>-1;i2--){

            	next=raw[keyStart+i2];

            	//System.out.println((char)next);
            	next=next-48;

                id=id+((next)<<x);

                x=x+8;
            }

            /**
             * not standard
             */
            switch(id){

//                case StandardFonts.CIDTYPE0:
//                    PDFvalue =StandardFonts.CIDTYPE0;
//                break;


                default:

//                	if(pdfKeyType==PdfDictionary.Encoding){
//                		PDFvalue=PdfCIDEncodings.getConstant(id);
//
//                		if(PDFvalue==PdfDictionary.Unknown){
//
//                			byte[] bytes=new byte[keyLength];
//
//                            System.arraycopy(raw,keyStart,bytes,0,keyLength);
//
//                			unknownValue=new String(bytes);
//                		}
//
//                		if(debug && PDFvalue==PdfDictionary.Unknown){
//                			System.out.println("Value not in PdfCIDEncodings");
//
//                           	 byte[] bytes=new byte[keyLength];
//
//                               System.arraycopy(raw,keyStart,bytes,0,keyLength);
//                               System.out.println("Add to CIDEncodings and as String");
//                               System.out.println("key="+new String(bytes)+" "+id+" not implemented in setConstant in PdfFont Object");
//
//                               System.out.println("final public static int CMAP_"+new String(bytes)+"="+id+";");
//
//                		}
//                	}else
                	PDFvalue=super.setConstant(pdfKeyType,id);

                    if(PDFvalue==-1){


                         if(debug){

                        	 byte[] bytes=new byte[keyLength];

                            System.arraycopy(raw,keyStart,bytes,0,keyLength);
                            System.out.println("key="+new String(bytes)+ ' ' +id+" not implemented in setConstant in "+this);

                            System.out.println("final public static int "+new String(bytes)+ '=' +id+ ';');

                        }

                    }

                    break;

            }

        }catch(Exception e){
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Exception: "+e.getMessage());
        }

        //System.out.println(pdfKeyType+"="+PDFvalue);
        switch(pdfKeyType){


    		default:
    			super.setConstant(pdfKeyType,id);

        }

        return PDFvalue;
    }

  //return as constnt we can check
    public int getNameAsConstant(int id) {

        byte[] raw;

        switch(id){

            case PdfDictionary.FT:
                return FT;

            case PdfDictionary.H:
                raw=rawH;
                break;

            case PdfDictionary.N:
                raw=rawN;
                break;

            case PdfDictionary.S:
                raw=rawS;
                break;
                
            case PdfDictionary.X:
                raw=rawX;
                break;
                
            default:
                return super.getNameAsConstant(id);

        }

        if(raw==null)
            return super.getNameAsConstant(id);
        else
        	return PdfDictionary.generateChecksum(0,raw.length,raw);

    }

    public int getParameterConstant(int key) {

    	//System.out.println("Get constant for "+key +" "+this);
        switch(key){


            case PdfDictionary.Subtype:
                if(FT!=PdfDictionary.Unknown)
                    return FT;
                else
                    return super.getParameterConstant(key);
//
//            	//special cases first
//            	if(key==PdfDictionary.BaseEncoding && Encoding!=null && Encoding.isZapfDingbats)
//            		return StandardFonts.ZAPF;
//            	else if(key==PdfDictionary.BaseEncoding && Encoding!=null && Encoding.isSymbol)
//            		return StandardFonts.SYMBOL;
//            	else
//            		return BaseEncoding;
        default:
        	return super.getParameterConstant(key);

        }
    }

//    public void setStream(){
//
//        hasStream=true;
//    }


    public PdfArrayIterator getMixedArray(int id) {

    	switch(id){

            case PdfDictionary.Border:
                return new PdfArrayIterator(Border);

            case PdfDictionary.D:
                           return new PdfArrayIterator(DmixedArray);

            case PdfDictionary.Dest:
            	return new PdfArrayIterator(DmixedArray);

            case PdfDictionary.Fields:
                return new PdfArrayIterator(Fields);

            case PdfDictionary.State:
                return new PdfArrayIterator(State);

            case PdfDictionary.XFA:
                return new PdfArrayIterator(rawXFAasArray);

            default:
			return super.getMixedArray(id);
        }
	}

    public byte[] getTextStreamValueAsByte(int id) {

        switch(id){

            case PdfDictionary.Cert:
	        	return rawCert;

            case PdfDictionary.ContactInfo:
	        	return rawContactInfo;
	        	
            case PdfDictionary.Contents:
	        	return rawContents;

/**
	        case PdfDictionary.AC:
	            return rawAC;

	        case PdfDictionary.CA:
	            return rawCA;

	        case PdfDictionary.RC:
	            return rawRC;
*/
            default:
                return super.getTextStreamValueAsByte(id);

        }
    }

    public double[] getDoubleArray(int id) {

        switch(id){
            default:
            	return super.getDoubleArray(id);
        }
    }

    public void setDoubleArray(int id,double[] value) {

        switch(id){

//            case PdfDictionary.FontMatrix:
//                FontMatrix=value;
//            break;

            default:
            	super.setDoubleArray(id, value);
        }
    }

    public int[] getIntArray(int id) {

        switch(id){

        	case PdfDictionary.I:
            return deepCopy(I);

            case PdfDictionary.ByteRange:
            return deepCopy(ByteRange);

            default:
            	return super.getIntArray(id);
        }
    }

    public void setIntArray(int id,int[] value) {

        switch(id){

        case PdfDictionary.I:
        	I=value;
        break;

        case PdfDictionary.ByteRange:
        	ByteRange=value;
        break;

            default:
            	super.setIntArray(id, value);
        }
    }

    public void setMixedArray(int id,byte[][] value) {

        switch(id){

            case PdfDictionary.Border:
                Border=value;
            break;

            case PdfDictionary.Dest:
            	DmixedArray=value;
            break;

            case PdfDictionary.Fields:
                Fields=value;
            break;

            case PdfDictionary.State:
                State=value;
            break;

            case PdfDictionary.XFA:
                rawXFAasArray=value;
            break;


            default:
            	super.setMixedArray(id, value);
        }
    }

    public float[] getFloatArray(int id) {

        switch(id){

        case PdfDictionary.C:
        	return C;
        	
        case PdfDictionary.QuadPoints:
        	return QuadPoints;	

        case PdfDictionary.Rect:
        	return Rect;

        case PdfDictionary.RD:
        	return RD;

            default:
            	return super.getFloatArray(id);

        }
    }

    public void setFloatArray(int id,float[] value) {

        switch(id){

        	case PdfDictionary.C:
	            C=value;
    	        break;

        	case PdfDictionary.QuadPoints:
	            QuadPoints=value;
	        break;
	        
            case PdfDictionary.RD:
	            RD=value;
	        break;

	        case PdfDictionary.Rect:
                    Rect=value;
	        break;

            default:
            	super.setFloatArray(id, value);
        }
    }

    public void setName(int id,byte[] value) {

        switch(id){

	        case PdfDictionary.AS:
	            rawAS=value;
	    	break;
	    	
	        case PdfDictionary.DV:
	            rawDV=value;
        	break;

	        case PdfDictionary.Filter:
                rawFilter=value;
            break;

            case PdfDictionary.SubFilter:
                rawSubFilter=value;
            break;
            
            case PdfDictionary.FT:
	            //setup first time
                FT=PdfDictionary.generateChecksum(0,value.length,value);
	    	break;

	        case PdfDictionary.H:
	            rawH=value;

	            //set H flags
	    	break;

        	case PdfDictionary.N:
                rawN=value;
        	break;

            case PdfDictionary.S:
                rawS=value;
            break;
            
            case PdfDictionary.X:
                rawX=value;
            break;

            default:
                super.setName(id,value);

        }

    }

    public void setObjectArray(int id, Object[] objectValues) {

        switch(id){

            case PdfDictionary.CO:
                CO=objectValues;
                break;

            case PdfDictionary.Opt:
            	Opt=objectValues;
            	break;

            case PdfDictionary.Reference:
            	Reference=objectValues;
            	break;

            default:
                super.setObjectArray(id, objectValues);
                break;
        }
    }

    public Object[] getObjectArray(int id) {

        switch(id){

            case PdfDictionary.CO:
                return CO;

            case PdfDictionary.Opt:
            	return Opt;

            case PdfDictionary.Reference:
            	return Reference;

            default:
                return super.getObjectArray(id);
        }
    }

    public byte[][] getStringArray(int id) {

        switch(id){

            //case PdfDictionary.XFA:
              //              return deepCopy(rawXFAasArray);

            default:
            	return super.getStringArray(id);
        }
    }

    public void setStringArray(int id,byte[][] value) {

        switch(id){

            //case PdfDictionary.XFA:
              //  rawXFAasArray=value;

            default:
            	super.setStringArray(id, value);
        }

    }

    public void setTextStreamValue(int id,byte[] value) {

        switch(id){

            case PdfDictionary.Cert:
                rawCert=value;
            break;

            case PdfDictionary.ContactInfo:
                rawContactInfo=value;
            break;

            case PdfDictionary.Contents:
                rawContents=value;
            break;

            case PdfDictionary.D:
	            rawDstring=value;
        	break;

        	case PdfDictionary.DA:
	            rawDA=value;
        	break;

        	case PdfDictionary.DV:
	            rawDV=value;
        	break;

        	case PdfDictionary.EOPROPtype:
	            rawEOPROPtype=value;
        	break;
        	
            case PdfDictionary.F:
	            rawFstring=value;
        	break;

            case PdfDictionary.JS:
                rawJS=value;
        	break;
        	
        	
            case PdfDictionary.Location:
	            rawLocation=value;
	        break;

	        case PdfDictionary.M:
	            rawM=value;
	        break;

            case PdfDictionary.P:
	            rawPstring=value;
        	break;

	        case PdfDictionary.RC:
	            rawRC=value;
	            break;

            case PdfDictionary.Reason:
	            rawReason=value;
	        break;


        	case PdfDictionary.NM:
	            rawNM=value;
        	break;

        	case PdfDictionary.Subj:
	            rawSubj=value;
	            break;
	            
	        case PdfDictionary.T:
	            rawT=value;
	            T=null;
	        break;

	        case PdfDictionary.TM:
	            rawTM=value;
	        break;

	        case PdfDictionary.TU:
	            rawTU=value;
	        break;

	        case PdfDictionary.URI:
	        	rawURI=value;
	        break;

	        case PdfDictionary.V:
	        	rawV=value;
	        	Vstring=null; //can be reset
	        break;

            default:
                super.setTextStreamValue(id,value);

        }

    }

    public void setTextStreamValue(int id,String value) {

        switch(id){

	        case PdfDictionary.V:
	        	Vstring=value; //can be reset
	        	break;
	        case PdfDictionary.T:
	        	this.setTextStreamValue(id, StringUtils.toBytes(value));
	        	break;
            default:
                super.setTextStreamValue(id,value);

        }

    }

    public String getName(int id) {

        switch(id){

        case PdfDictionary.AS:

            //setup first time
             if(AS==null && rawAS!=null)
                 AS=new String(rawAS);

             return AS;

        case PdfDictionary.FT:

            //setup first time
            return null;

        case PdfDictionary.H:

            //setup first time
             if(H==null && rawH!=null)
                 H=new String(rawH);

             return H;

        case PdfDictionary.Filter:

            //setup first time
            if(Filter==null && rawFilter!=null)
                Filter=new String(rawFilter);

            return Filter;

            case PdfDictionary.SubFilter:

            //setup first time
            if(SubFilter==null && rawSubFilter!=null)
                SubFilter=new String(rawSubFilter);

            return SubFilter;
        case PdfDictionary.N:

            //setup first time
             if(N==null && rawN!=null)
                 N=new String(rawN);

             return N;

            case PdfDictionary.S:

           //setup first time
            if(S==null && rawS!=null)
                S=new String(rawS);

            return S;
            
            case PdfDictionary.X:

                //setup first time
                 if(rawX!=null)
                     return new String(rawX);

            default:
                return super.getName(id);

        }
    }

    public String getTextStreamValue(int id) {

        switch(id){

            case PdfDictionary.Cert:

            //setup first time
            if(Cert==null && rawCert!=null)
            	Cert= StringUtils.getTextString(rawCert, false);

            return Cert;

            case PdfDictionary.ContactInfo:

                //setup first time
                if(ContactInfo==null && rawContactInfo!=null)
                	ContactInfo= StringUtils.getTextString(rawContactInfo, false);

                return ContactInfo;

            case PdfDictionary.Contents:

                //setup first time
                if(Contents==null && rawContents!=null)
                	Contents= StringUtils.getTextString(rawContents, true);

                return Contents;

            case PdfDictionary.D:

                //setup first time
                if(Dstring==null && rawDstring!=null)
                	Dstring= StringUtils.getTextString(rawDstring, false);

                return Dstring;

        	case PdfDictionary.DA:

            //setup first time
            if(DA==null && rawDA!=null)
            	DA= StringUtils.getTextString(rawDA, false);

            return DA;

        	case PdfDictionary.DV:

                //setup first time
                if(DV==null && rawDV!=null)
                	DV= StringUtils.getTextString(rawDV, true);

                return DV;

        	case PdfDictionary.EOPROPtype:

                //setup first time
                if(EOPROPtype==null && rawEOPROPtype!=null)
                	EOPROPtype=new String(rawEOPROPtype);

                return EOPROPtype;
                

            case PdfDictionary.F:

                //setup first time
                if(Fstring==null && rawFstring!=null)
                	Fstring= StringUtils.getTextString(rawFstring, false);

                return Fstring;

        	case PdfDictionary.JS:

	            //setup first time
	            if(JSString==null && rawJS!=null)
	            	JSString= StringUtils.getTextString(rawJS, true);

	            return JSString;

            case PdfDictionary.NM:

	            //setup first time
	            if(NM==null && rawNM!=null)
	            	NM= StringUtils.getTextString(rawNM, false);

	            return NM;

            case PdfDictionary.Location:

	            //setup first time
	            if(Location==null && rawLocation!=null)
	            	Location=new String(rawLocation);
	            return Location;

	        case PdfDictionary.M:

	            //setup first time
	            if(M==null && rawM!=null)
	            	M=new String(rawM);
	            return M;

            case PdfDictionary.P:

                //setup first time
                if(Pstring==null && rawPstring!=null)
                	Pstring= StringUtils.getTextString(rawPstring, false);

                return Pstring;

            case PdfDictionary.RC:

	            //setup first time
	            if(RC==null && rawRC!=null)
	            	RC=new String(rawRC);
	            return RC;
	            
            case PdfDictionary.Reason:

	            //setup first time
	            if(Reason==null && rawReason!=null)
	            	Reason=new String(rawReason);
	            return Reason;
	            
            case PdfDictionary.Subj:
	            //setup first time
	            if(Subj==null && rawSubj!=null)
	            	Subj= StringUtils.getTextString(rawSubj, false);

	            return Subj;
	            
        	case PdfDictionary.T:
	            //setup first time
	            if(T==null && rawT!=null)
	            	T= StringUtils.getTextString(rawT, false);
	            
	            if(T==null && parentPdfObj!=null){
	            	return parentPdfObj.getTextStreamValue(PdfDictionary.T);
	            }
	            return T;

	        case PdfDictionary.TM:

	            //setup first time
	            if(TM==null && rawTM!=null)
	            	TM= StringUtils.getTextString(rawTM, false);

	            return TM;

	        case PdfDictionary.TU:

	            //setup first time
	            if(TU==null && rawTU!=null)
	            	TU= StringUtils.getTextString(rawTU, false);

	            return TU;


	        case PdfDictionary.URI:

	            //setup first time
	            if(URI==null && rawURI!=null)
	            	URI= StringUtils.getTextString(rawURI, false);

	            return URI;

	        case PdfDictionary.V:

	            //setup first time
	            if(Vstring==null && rawV!=null)
	            	Vstring= StringUtils.getTextString(rawV, true);
	            
	            return Vstring;

            default:
                return super.getTextStreamValue(id);

        }
    }

    /**
     * unless you need special fucntions,
     * use getStringValue(int id) which is faster
     */
    public String getStringValue(int id,int mode) {

        byte[] data=null;

        //get data
        switch(id){

//            case PdfDictionary.BaseFont:
//                data=rawBaseFont;
//                break;

        }


        //convert
        switch(mode){
            case PdfDictionary.STANDARD:

                //setup first time
                if(data!=null)
                    return new String(data);
                else
                    return null;


            case PdfDictionary.LOWERCASE:

                //setup first time
                if(data!=null)
                    return new String(data);
                else
                    return null;

            case PdfDictionary.REMOVEPOSTSCRIPTPREFIX:

                //setup first time
                if(data!=null){
                	int len=data.length;
                	if(len>6 && data[6]=='+'){ //lose ABCDEF+ if present
                		int length=len-7;
                		byte[] newData=new byte[length];
                		System.arraycopy(data, 7, newData, 0, length);
                		return new String(newData);
                	}else
                		return new String(data);
                }else
                    return null;

            default:
                throw new RuntimeException("Value not defined in getName(int,mode) in "+this);
        }
    }
    
    public boolean hasKeyArray(int id) {
    	switch(id){
    	case PdfDictionary.Kids:
    		if(Kids!=null && Kids.length>0){
    			return true;
    		}
    		return false;
    		
    	default:
    		return false;
    	}
    }

    public byte[][] getKeyArray(int id) {

        switch(id){

        case PdfDictionary.Kids:
            return deepCopy(Kids);



            default:
            	return super.getKeyArray(id);
        }
    }

    public void setKeyArray(int id,byte[][] value) {

        switch(id){

        case PdfDictionary.Kids:
            Kids=value;
        break;

            default:
            	super.setKeyArray(id, value);
        }

    }

    public boolean decompressStreamWhenRead() {
		return true;
	}

    /**
     * resolve what type of field <b>type</b> specifies
     * and return as String
     */
    public static String resolveType(int type) {

        if (type == PdfDictionary.Btn)
            return "Button";
        else if (type == PdfDictionary.Ch)
            return "Choice";
        else if (type == PdfDictionary.Tx)
            return "Text";
        else if (type == PdfDictionary.Popup)
            return "Popup";
        else if (type == PdfDictionary.Square)
            return "Square";
        else if (type == PdfDictionary.Text)
            return "Text Annot";


        return null;
    }

    /**
     * read and setup the form flags for the Ff entry
	 * <b>field</b> is the data to be used to setup the Ff flags
     */
    protected void commandFf(int flagValue) {
        /**use formObject.flags
         * to get flags representing field preferences the following are accessed by array address (bit position -1)
         *
         * <b>bit positions</b>
         * all
         * 1=readonly - if set there is no interaction
         * 2=required - if set the field must have a value when submit-form-action occures
         * 3=noexport - if set the field must not be exported by a submit-form-action
         *
         * Choice fields
         * 18=combo - set its a combobox, else a list box
         * 19=edit - defines a comboBox to be editable
         * 20=sort - defines list to be sorted alphabetically
         * 22=multiselect - if set more than one items can be selected, else only one
         * 23=donotspellcheck - (only used on editable combobox) don't spell check
         * 27=commitOnselchange - if set commit the action when selection changed, else commit when user exits field
         *
         * text fields
         * 13=multiline - uses multipul lines else uses a single line
         * 14=password - a password is intended
         * 21=fileselect -text in field represents a file pathname to be submitted
         * 23=donotspellcheck - don't spell check
         * 24=donotscroll - once the field is full don't enter anymore text.
         * 25=comb - (only if maxlen is present, (multiline, password and fileselect are CLEAR)), the text is justified across the field to MaxLen
         * 26=richtext - use richtext format specified by RV entry in field dictionary
         *
         * button fields
         * 15=notoggletooff - (use in radiobuttons only) if set one button must always be selected
         * 16=radio - if set is a set of radio buttons
         * 17=pushbutton - if set its a push button
         * 	if neither 16 nor 17 its a check box
         * 26=radiosinunison - if set all radio buttons with the same on state are turned on and off in unison (same behaviour as html browsers)
         */

            //System.out.println("flag value="+flag);

        flags = new boolean[32];
             /**/
            flags[READONLY_ID] = (flagValue & READONLY_BIT) == READONLY_BIT;
            flags[REQUIRED_ID] = (flagValue & REQUIRED_BIT) == REQUIRED_BIT;
            flags[NOEXPORT_ID] = (flagValue & NOEXPORT_BIT) == NOEXPORT_BIT;
            flags[MULTILINE_ID] = (flagValue & MULTILINE_BIT) == MULTILINE_BIT;
            flags[PASSWORD_ID] = (flagValue & PASSWORD_BIT) == PASSWORD_BIT;
            flags[NOTOGGLETOOFF_ID] = (flagValue & NOTOGGLETOOFF_BIT) == NOTOGGLETOOFF_BIT;
            flags[RADIO_ID] = (flagValue & RADIO_BIT) == RADIO_BIT;
            flags[PUSHBUTTON_ID] = (flagValue & PUSHBUTTON_BIT) == PUSHBUTTON_BIT;
            flags[COMBO_ID] = (flagValue & COMBO_BIT) == COMBO_BIT;
            flags[EDIT_ID] = (flagValue & EDIT_BIT) == EDIT_BIT;
            flags[SORT_ID] = (flagValue & SORT_BIT) == SORT_BIT;
            flags[FILESELECT_ID] = (flagValue & FILESELECT_BIT) == FILESELECT_BIT;
            flags[MULTISELECT_ID] = (flagValue & MULTISELECT_BIT) == MULTISELECT_BIT;
            flags[DONOTSPELLCHECK_ID] = (flagValue & DONOTSPELLCHECK_BIT) == DONOTSPELLCHECK_BIT;
            flags[DONOTSCROLL_ID] = (flagValue & DONOTSCROLL_BIT) == DONOTSCROLL_BIT;
            flags[COMB_ID] = (flagValue & COMB_BIT) == COMB_BIT;
            flags[RICHTEXT_ID] = (flagValue & RICHTEXT_BIT) == RICHTEXT_BIT;//same as RADIOINUNISON
            flags[RADIOINUNISON_ID] = (flagValue & RADIOINUNISON_BIT) == RADIOINUNISON_BIT;//same as RICHTEXT
            flags[COMMITONSELCHANGE_ID] = (flagValue & COMMITONSELCHANGE_BIT) == COMMITONSELCHANGE_BIT;
            
    }

	/**
	 * takes a value, and turns it into the color it represents
	 * e.g. (0.5)  represents gray (127,127,127)
	 * grey = array length 1, with one value
	 * rgb = array length 3, in the order of red,green,blue
	 * cmyk = array length 4, in the reverse order, (ie. k, y, m, c)
	 */
	public static  Color generateColor(float[] toks) {
//		0=transparant
//		1=gray
//		3=rgb
//		4=cmyk
		

		int i=-1;
		if(toks!=null)
			i=toks.length;

		Color newColor = null;
		if(i==0){
		    //LogWriter.writeFormLog("{stream} CHECK transparent color",debugUnimplemented);
		    newColor = new Color(0,0,0,0);//if num of tokens is 0 transparant, fourth variable my need to be 1
		
		}else if(i==1){
		    
		    float tok0 = toks[0];
		    
		    if(tok0<=1){
		    	newColor = new Color(tok0,tok0,tok0);
		    }else {
		    	newColor = new Color((int)tok0,(int)tok0,(int)tok0);
		    }
		    
		}else if(i==3){
		    if(debug)
		        System.out.println("rgb color="+toks[0]+ ' ' +toks[1]+ ' ' +toks[2]);
		    
		    float tok0 = toks[0];
		    float tok1 = toks[1];
		    float tok2 = toks[2];
		    
		    if(tok0<=1 && tok1<=1 && tok2<=1){
		    	newColor = new Color(tok0,tok1,tok2);
		    }else {
		    	newColor = new Color((int)tok0,(int)tok1,(int)tok2);
		    }

		}else if(i==4){
	       
		    DeviceCMYKColorSpace cs=new DeviceCMYKColorSpace();
            cs.setColor(new float[]{toks[3],toks[2],toks[1],toks[0]},4);
		    newColor =(Color) cs.getColor();

		}
		
		return newColor;
	}

    /**
     * returns true if this formObject represents an XFAObject
     */
    public boolean isXFAObject(){
    	return isXFAObject;
    }

    public PdfObject duplicate() {
        FormObject newObject = new FormObject();
        
        newObject.formHandler=formHandler;

        //String
        newObject.AS = AS;
        newObject.currentState = currentState;
        newObject.contents = contents;
        newObject.Cert=Cert;
        newObject.ContactInfo = ContactInfo;
        newObject.contents = contents;
        newObject.Contents = Contents;
        newObject.currentState = currentState;
        newObject.DA=DA;
        newObject.Dstring=Dstring;
        newObject.DV=DV;
        newObject.Filter=Filter;
        newObject.Fstring=Fstring;
        newObject.H=H;
        newObject.JSString=JSString;
        newObject.layerName=layerName;
        newObject.Location=Location;
        newObject.M=M;
        newObject.N=N;
        newObject.NM=NM;
        newObject.normalOnState=normalOnState;
        //parentRef DO NOT SET
        newObject.Pstring=Pstring;
        newObject.onState = onState;
        newObject.ref = ref;
        newObject.RC=RC;
        newObject.Reason=Reason;
        newObject.S=S;
        newObject.selectedItem = selectedItem;
        newObject.SubFilter=SubFilter;
        newObject.Subj=Subj;
        newObject.T=T;
        newObject.TM=TM;
        newObject.TU=TU;
        newObject.textString = textString;
        newObject.URI=URI;
        newObject.Vstring=Vstring;
        
        //int
        newObject.display=display;
        newObject.F=F;
        newObject.Ff=Ff;
        newObject.formType=formType;
        newObject.FT=FT;
        newObject.MaxLen=MaxLen;
        newObject.pageNumber = pageNumber;
        newObject.popupFlag=popupFlag;
        newObject.Q=Q;
        newObject.rawRotation=rawRotation;
        newObject.SigFlags=SigFlags;
        newObject.StructParent=StructParent;
        newObject.textSize = textSize;
        newObject.TI=TI;
        newObject.W=W;
        
        //boolean
        newObject.appearancesUsed = appearancesUsed;
        newObject.offsetDownIcon = offsetDownIcon;
        newObject.noDownIcon = noDownIcon;
        newObject.invertDownIcon = invertDownIcon;
        newObject.show = show;
        newObject.H_Boolean=H_Boolean;
        newObject.NeedAppearances=NeedAppearances;
        newObject.isKid=isKid;
        newObject.isXFAObject=isXFAObject;
        newObject.Open=Open;
        newObject.popupBuilt=popupBuilt;
        
        //Font
        newObject.textFont = textFont;
        
        //Color
        newObject.cColor = cColor;
        
        //Object
        if(popupObj==null){
        	newObject.popupObj = null;
        }else {
        	//not sure how to clone a JComponent or ULCComponent 
        	newObject.popupObj=popupObj;
        }
        
        //String[]
        newObject.OptString = (OptString==null) ? null : OptString.clone();
        
        //boolean[]
        newObject.flags = flags==null ? null : flags.clone();
        newObject.Farray=Farray==null ? null : Farray.clone();

        //int[]
        newObject.I = I==null ? null : I.clone();
        
        //float[]
        newObject.C=C==null ? null : C.clone();
        newObject.QuadPoints=QuadPoints==null ? null : QuadPoints.clone();
        newObject.Rect=Rect==null ? null : Rect.clone();
        newObject.RD=RD==null ? null : RD.clone();
        newObject.textColor = textColor==null ? null : textColor.clone();

        //PdfObject
        newObject.A=A.duplicate();
        newObject.AA=AA.duplicate();
        newObject.AP=AP.duplicate();
        newObject.BS=BS.duplicate();
        newObject.BI=BI.duplicate();
        newObject.Bl=Bl.duplicate();
        newObject.Cdict=Cdict.duplicate();
        newObject.D=D.duplicate();
        newObject.DC=DC.duplicate();
        newObject.DP=DP.duplicate();
        newObject.DS=DS.duplicate();
        newObject.E=E.duplicate();
        newObject.Fdict=Fdict.duplicate();
        newObject.Fo=Fo.duplicate();
        newObject.FS=FS.duplicate();
        newObject.IF=IF.duplicate();
        newObject.JS=JS.duplicate();
        newObject.K=K.duplicate();
        newObject.MK=MK.duplicate();
        newObject.Next=Next.duplicate();
        newObject.Nobj=Nobj.duplicate();
        newObject.O=O.duplicate();
        newObject.OC=OC.duplicate();
        newObject.Off=Off.duplicate();
        newObject.On=On.duplicate();
        newObject.P=P.duplicate();
        //parentPdfObject Do NOT SET OR WE RECURSE INFINATLY
        newObject.PC=PC.duplicate();
        newObject.PI=PI.duplicate();
        newObject.PO=PO.duplicate();
        newObject.Popup=Popup.duplicate();
        newObject.PV=PV.duplicate();
        newObject.R=R.duplicate();
        newObject.Sig=Sig.duplicate();
        newObject.Sound=Sound.duplicate();
        newObject.U=U.duplicate();
        newObject.V=V.duplicate();
        newObject.Win=Win.duplicate();
        newObject.WP=WP.duplicate();
        newObject.WS=WS.duplicate();
        newObject.X=X.duplicate();
        newObject.XFAasStream=XFAasStream.duplicate();

        //Object[]
        newObject.CO=CO==null ? null : CO.clone();
        newObject.Opt=Opt==null ? null : Opt.clone();
        newObject.Reference=Reference==null ? null : Reference.clone();
        
        //byte[]
        newObject.rawAS=rawAS==null ? null : rawAS.clone();
        newObject.rawCert=rawCert==null ? null : rawCert.clone();
        newObject.rawContactInfo=rawContactInfo==null ? null : rawContactInfo.clone();
        newObject.rawContents=rawContents==null ? null : rawContents.clone();
        newObject.rawDA=rawDA==null ? null : rawDA.clone();
        newObject.rawDstring=rawDstring==null ? null : rawDstring.clone();
        newObject.rawDV=rawDV==null ? null : rawDV.clone();
        newObject.rawEOPROPtype=rawEOPROPtype==null ? null : rawEOPROPtype.clone();
        
        newObject.rawFilter=rawFilter==null ? null : rawFilter.clone();
        newObject.rawFstring=rawFstring==null ? null : rawFstring.clone();
        newObject.rawH=rawH==null ? null : rawH.clone();
        newObject.rawJS=rawJS==null ? null : rawJS.clone();
        newObject.rawLocation=rawLocation==null ? null : rawLocation.clone();
        newObject.rawM=rawM==null ? null : rawM.clone();
        newObject.rawN=rawN==null ? null : rawN.clone();
        newObject.rawNM=rawNM==null ? null : rawNM.clone();
        newObject.rawPstring=rawPstring==null ? null : rawPstring.clone();
        newObject.rawRC=rawRC==null ? null : rawRC.clone();
        newObject.rawReason=rawReason==null ? null : rawReason.clone();
        newObject.rawS=rawS==null ? null : rawS.clone();
        newObject.rawSubFilter=rawSubFilter==null ? null : rawSubFilter.clone();
        newObject.rawSubj=rawSubj==null ? null : rawSubj.clone();
        newObject.rawT=rawT==null ? null : rawT.clone();
        newObject.rawTM=rawTM==null ? null : rawTM.clone();
        newObject.rawTU=rawTU==null ? null : rawTU.clone();
        newObject.rawURI=rawURI==null ? null : rawURI.clone();
        newObject.rawV=rawV==null ? null : rawV.clone();
        newObject.rawX=rawX==null ? null : rawX.clone();
        
        //byte[][]
        newObject.Border = (Border==null ? null : ObjectCloneFactory.cloneDoubleArray(Border));
        newObject.DmixedArray = (DmixedArray==null ? null : ObjectCloneFactory.cloneDoubleArray(DmixedArray));
        newObject.Fields = (Fields==null ? null : ObjectCloneFactory.cloneDoubleArray(Fields));
        newObject.rawXFAasArray=rawXFAasArray==null ? null : ObjectCloneFactory.cloneDoubleArray(rawXFAasArray);
        newObject.State=State==null ? null : ObjectCloneFactory.cloneDoubleArray(State);
        
        //BUfferedImage
        newObject.normalOffImage = ObjectCloneFactory.deepCopy(normalOffImage);
        newObject.normalOnImage = ObjectCloneFactory.deepCopy(normalOnImage);
        newObject.rolloverOffImage = ObjectCloneFactory.deepCopy(rolloverOffImage);
        newObject.rolloverOnImage = ObjectCloneFactory.deepCopy(rolloverOnImage);
        newObject.downOffImage = ObjectCloneFactory.deepCopy(downOffImage);
        newObject.downOnImage = ObjectCloneFactory.deepCopy(downOnImage);

        //Map
        newObject.OptValues = ObjectCloneFactory.cloneMap(OptValues);
        
        return newObject;
    }

    /** overwrites all the values on this form with any values from the parent*/
    public void copyInheritedValuesFromParent(FormObject parentObj) {
    	if(parentObj==null)
    		return;
		
		if(pageNumber==-1 && parentObj.pageNumber!=-1)
		pageNumber=parentObj.pageNumber;
		
		formHandler=parentObj.formHandler;
		
		//byte[]
		if(rawAS==null)
			rawAS=parentObj.rawAS;
		if(rawDA==null)
			rawDA=parentObj.rawDA;
		if(rawDV==null)
			rawDV=parentObj.rawDV;
		if(rawJS==null)
			rawJS=parentObj.rawJS;
		if(rawNM==null)
			rawNM=parentObj.rawNM;
		if(rawTM==null)
			rawTM=parentObj.rawTM;
		if(rawTU==null)
			rawTU=parentObj.rawTU;
		if(rawV==null)
			rawV=parentObj.rawV;
		
		//before we copy the fieldName make sure the parent values are valid 
		if(parentObj.T==null && parentObj.rawT!=null){
	    	parentObj.T= StringUtils.getTextString(parentObj.rawT, false);
		}
		//copy fieldname, making sure to keep it fully qualified
	    if(parentObj.T!=null) {
	    	if(T==null && rawT!=null)
	    		T= StringUtils.getTextString(rawT, false);
	    	
	    	if(T!=null){
		    	//make sure the parent name has not already been added to the name
	    		if(!T.contains(parentObj.T)){
	    			T = parentObj.T+ '.' +T;
	    			rawT = StringUtils.toBytes(T);
	    		}//else we should already have the right name
	    		//NOTE dont just pass parent T and rawT values through as we read them vis getTextStreamValue(T) if needed
	    	}
		}	
		
		//PdfObject
		if(A==null)
			A = parentObj.A;
		if(AA==null)
			AA=parentObj.AA;
		if(AP==null)
			AP=parentObj.AP;
		if(D==null)
			D=parentObj.D;
		if(OC==null)
			OC=parentObj.OC;
		
		
		
		//float[]
		if(C==null)
			C = (parentObj.C==null) ? null : parentObj.C.clone();
		
		if(QuadPoints==null)
			QuadPoints= (parentObj.QuadPoints==null) ? null : parentObj.QuadPoints.clone();
		
		if(Rect==null)
			Rect = (parentObj.Rect==null) ? null : parentObj.Rect.clone();

		//int
		if(F==-1)
			F=parentObj.F;
        if(Ff==-1)
        	Ff=parentObj.Ff;
        if(Q==-1)
    		Q=parentObj.Q;
        if(MaxLen==-1)
    		MaxLen=parentObj.MaxLen;
        if(FT==-1)
        	FT=parentObj.FT;
        if(TI==-1)
        	TI=parentObj.TI;
        
        //boolean[]
        if(flags==null)
        	flags = (parentObj.flags==null) ? null : parentObj.flags.clone();

        //Object[]
        if(Opt==null)
        	Opt = (parentObj.Opt==null) ? null : parentObj.Opt.clone();
        if(CO==null)
            CO = (parentObj.CO==null) ? null : parentObj.CO.clone();


        //String
        if(textString==null)
        	textString = parentObj.textString;
        if(OptString==null)
        	OptString = parentObj.OptString;
        if(selectedItem==null)
        	selectedItem = parentObj.selectedItem;
	}

    /**
     * get actual object reg
     *
     * @deprecated use formObject.getObjectRefAsString();
     */
    public String getPDFRef() {
        return getObjectRefAsString();
    }

    /**
     * returns the alignment (Q)
     */
    public int getAlignment(){

        if(Q==-1)
        Q= JTextField.LEFT;
        
    	return Q;
    }
    
    public boolean hasColorChanged(){
    	return textColorChanged;
    }
    
    /** rests the color changed flag to false, to say that it has be refreshed on screen */
    public void resetColorChanged(){
    	textColorChanged = false;
    }

    /**
     * sets the text color for this form
     * 
     */
    public void setTextColor(float[] color) {
    	//JS made public so that javascript can access it
    	
    	//check if is javascript and convert to our float
    	if(color.length>0 && Float.isNaN(color[0])){//not-a-number
			float[] tmp = new float[color.length-1];
			System.arraycopy(color, 1, tmp, 0, color.length-1);
			color = tmp;
		}
    	
        textColor = color;
        
        //set flag to say that the text color has chnaged so we can update the forms.
        textColorChanged = true;
    }
    

    /**
     * set the text font for this form
     */
    public void setTextFont(Font font) {
        textFont = font;
    }

    /**
     * sets the text size for this form
     */
    protected void setTextSize(int size) {
        textSize = size;
    }

    /**
     * sets the child on state,
     * only applicable to radio buttons
     */
    public void setChildOnState(String curValue) {
        onState = curValue;
    }

    /**
     * sets the current state,
     * only applicable to check boxes
     */
    public void setCurrentState(String curValue) {
    	currentState = curValue;
    }

    /**
     * sets the text value
     */
    public void setTextValue(String text) {
    	//use empty string so that the raw pdf value does not get recalled.
    	if(text==null)
			text = "";
    	
    	textString = text;
    }

    /**
     * sets the selected item
     * only applicable to the choices fields
     */
    public void setSelectedItem(String curValue) {

        selectedItem = curValue;
       
    }

    /**
     * sets the field name for this field (used by XFA)
     */
    public void setFieldName(String field) {

    	T=null;

        //ensure we sync to low level value
        this.setTextStreamValue(PdfDictionary.T,StringUtils.toBytes(field));
    }
    
    /**
     * sets the parent for this field
     */
    public void setParent(String parent) {
    	setParent(parent,null,false);
    }
    
    /** sets the parent string for this field and stores the parent PDFObject passed 
     * in to be accessed locally and from getParent()
     * BEWARE :- this method will copy all relevent values from the parent is copyValuesFromParent is true
     */
    public void setParent(String parent,FormObject parentObj,boolean copyValuesFromParent){
    	if(copyValuesFromParent){
    		//copy all values from the parent here, then they can be overwritten in future.
    		copyInheritedValuesFromParent(parentObj);
    	}
    	
        this.parentRef = parent;
        if(parentObj!=null){
        	this.parentPdfObj = parentObj;
        }
    }
    
    public PdfObject getParentPdfObj(){
    	return parentPdfObj;
    }
    
    /**
     * gets the parent for this field
     */
    public String getParentRef() {

        //option to take from file as well
        if(parentRef==null && includeParent)
            return getStringKey(PdfDictionary.Parent);
        else
            return parentRef;
    }

    /**
	 * return the characteristic type
	 */
	private static boolean[] calcFarray(int flagValue) {
		
		if(flagValue==0)
			return new boolean[10];
		
		boolean[] Farray=new boolean[10];
		
		final int[] pow={0,1,2,4,8,16,32,64,128,256,512};
		for(int jj=1;jj<10;jj++){
			if(((flagValue & pow[jj])==pow[jj]))
				Farray[jj - 1] = true;
		}
		
		return Farray;
	}

    /**
     * sets the top index
     * for the choice fields
     */
    public void setTopIndex(int[] index) {
        
    	if(index==null)
    		TI=-1;
    	else if(index.length>0)
    		TI=index[0];
    	
    	I = index;
        
    }

    /**
     * return the bounding rectangle for this object
     */
    public Rectangle getBoundingRectangle(){

        float[] coords=getFloatArray(PdfDictionary.Rect);

        if(coords!=null){
            float x1=coords[0],y1=coords[1],x2=coords[2],y2=coords[3];


            if(x1>x2){
                float tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            if(y1>y2){
                float tmp = y1;
                y1 = y2;
                y2 = tmp;
            }

            int ix1 = (int)x1; //truncates to ensure form area fully contained
            int iy1 = (int)y1;

            int ix2 = (int)x2 + ((x2 - (int)x2 > 0) ? 1 : 0); //Always rounds up to ensure form area fully contained
            int iy2 = (int)y2 + ((y2 - (int)y2 > 0) ? 1 : 0);

            BBox = new Rectangle(ix1,iy1,ix2-ix1,iy2-iy1);
        }else
            BBox = new Rectangle(0,0,0,0);

        return BBox;
    }

   /**
     * sets the type this form specifies
     */
    public void setType(int type, boolean isXFA) {

        if(isXFA)
            FT=type;
    }

    /**
     * sets the flag <b>pos</b> to value of <b>flag</b>
     */
    public void setFlag(int pos, boolean flag) {
//@chris xfa this needs fixing, all references set wrong flag.
    	if(flags==null){
    		flags = new boolean[32];
    	}
        flags[pos] = flag;
    }

    /**
     * returns the flags array (Ff in PDF)  (indexs are the number listed)
     *  * all
     * <br>1=readonly - if set there is no interaction
     * <br>2=required - if set the field must have a value when submit-form-action occures
     * <br>3=noexport - if set the field must not be exported by a submit-form-action
     *<br>
     * <br>Choice fields
     * <br>18=combo - set its a combobox, else a list box
     * <br>19=edit - defines a comboBox to be editable
     * <br>20=sort - defines list to be sorted alphabetically
     * <br>22=multiselect - if set more than one items can be selected, else only one
     * <br>23=donotspellcheck - (only used on editable combobox) don't spell check
     * <br>27=commitOnselchange - if set commit the action when selection changed, else commit when user exits field
     *<br>
     * <br>text fields
     * <br>13=multiline - uses multipul lines else uses a single line
     * <br>14=password - a password is intended
     * <br>21=fileselect -text in field represents a file pathname to be submitted
     * <br>23=donotspellcheck - don't spell check
     * <br>24=donotscroll - once the field is full don't enter anymore text.
     * <br>25=comb - (only if maxlen is present, (multiline, password and fileselect are CLEAR)), the text is justified across the field to MaxLen
     * <br>26=richtext - use richtext format specified by RV entry in field dictionary
     *<br>
     * <br>button fields
     * <br>15=notoggletooff - (use in radiobuttons only) if set one button must always be selected
     * <br>16=radio - if set is a set of radio buttons
     * <br>17=pushbutton - if set its a push button, 
     * 	if neither 16 nor 17 its a check box
     * <br>26=radiosinunison - if set all radio buttons with the same on state are turned on and off in unison (same behaviour as html browsers)

     */
    public boolean[] getFieldFlags() {//otherwise known as Ff flags
    	if(flags==null)
    		flags = new boolean[32];
        return flags;
    }

    /** set the state which is defined as the On state for this form
     * <br>usually different for each child so that the selected child can be found by the state 
     */
    public void setNormalOnState(String state){
    	normalOnState = state;
    }

    /**
	 * @return whether or not appearances are used in this field
	 */
    public boolean isAppearanceUsed(){
        return appearancesUsed;
    }
 	
    /**
     * sets the image
     * <br>images are one of R rollover, N normal, D down and Off unselected, On selected
     * <br>for Normal selected the normal on state should also be set
     */
    public void setAppearanceImage(BufferedImage image, int imageType, int status) {
    	if(image==null)
    		//if null use opaque image
    		image = getOpaqueImage();

        switch(imageType){
            case PdfDictionary.D:
                if(status==PdfDictionary.On){
                    downOnImage = image;
                }else if(status==PdfDictionary.Off){
                    downOffImage = image;
                }else
                    throw new RuntimeException("Unknown status use PdfDictionary.On or PdfDictionary.Off");
            break;

            case PdfDictionary.N:
                if(status==PdfDictionary.On){
                	normalOnImage = image;
                }else if(status==PdfDictionary.Off){
                    normalOffImage=image;
                }else
                    throw new RuntimeException("Unknown status use PdfDictionary.On or PdfDictionary.Off");
            break;

            case PdfDictionary.R:
                if(status==PdfDictionary.On){
                	rolloverOnImage = image;
                }else if(status==PdfDictionary.Off){
                    rolloverOffImage=image;
                }else
                    throw new RuntimeException("Unknown status use PdfDictionary.On or PdfDictionary.Off");
            break;

            default:
                throw new RuntimeException("Unknown type use PdfDictionary.D, PdfDictionary.N or PdfDictionary.R");
        }
        
        appearancesUsed=true;
    }

    /**
     * sets the border color
     */
    public void setBorderColor(String nextField) {
    	
    	if(nextField!=null)
        	getDictionary(PdfDictionary.MK).setFloatArray(PdfDictionary.BC, generateFloatFromString(nextField));
    	
    }

    /**
     * sets the background color for this form
     */
    public void setBackgroundColor(String nextField) {
    	
    	if(nextField!=null)
        	getDictionary(PdfDictionary.MK).setFloatArray(PdfDictionary.BG, generateFloatFromString(nextField));
    		
    }
    
    /**
	 * takes a String <b>colorString</b>, and turns it into the color it represents
	 * e.g. (0.5)  represents gray (127,127,127)
	 * cmyk = 4 tokens in the order c, m, y, k
	 */
	private static  float[] generateFloatFromString(String colorString) {
//		0=transparant
//		1=gray
//		3=rgb
//		4=cmyk
		if(debug)
			System.out.println("CHECK generateColorFromString="+colorString);
		
		StringTokenizer tokens = new StringTokenizer(colorString,"[()] ,");
		
		float[] toks = new float[tokens.countTokens()];
		int i=0;
		while(tokens.hasMoreTokens()){
			
			String tok = tokens.nextToken();
			if(debug)
				System.out.println("token"+(i+1)+ '=' +tok+ ' ' +colorString);
			
			toks[i] = Float.parseFloat(tok);
			i++;
		}
		
		if(i==0)
			return null;
		else
			return toks;
	}

    /**
     * sets the normal caption for this form
     */
    public void setNormalCaption(String caption) {
        
        if(caption!=null){
        	getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.CA, StringUtils.toBytes(caption));
        }
    }

    /**
     * sets whether there should be a down looking icon
     */
    protected void setOffsetDownApp() {
        offsetDownIcon = true;
    }

    /**
     * sets whether a down icon should be used
     */
    protected void setNoDownIcon() {
        noDownIcon = true;
    }

    /**
     * sets whether to invert the normal icon for the down icon
     */
    protected void setInvertForDownIcon() {
        invertDownIcon = true;
    }
    
    /**
     * returns to rotation of this field object, 
     * currently in stamp annotations only
     *
     * deprecated use formObject.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R);
     *
    public int getRotation(){
    	
    	return getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R);
    }/**/

    /**
     * returns true if has normal of image
     */
    public boolean hasNormalOff() {
        return normalOffImage!=null;
    }

    /**
     * returns true if has rollover off image
     */
    public boolean hasRolloverOff() {
        return rolloverOffImage!=null;
    }

    /**
     * returns true if has down off image
     */
    public boolean hasDownOff() {
        return downOffImage!=null;
    }

    /**
     * returns true if has one or more down images set
     */
    public boolean hasDownImage() {
        return (downOnImage!=null || hasDownOff());
    }

    /**
     * returns true if has a rollover on image
     */
    public boolean hasRolloverOn() {
        return rolloverOnImage!=null;
    }

    /**
     * returns true if has a normal on image
     */
    public boolean hasNormalOn() {
        return normalOnImage!=null;
    }
    
    /** copy all values from <b>form</b> to <b>this</b> FormObject 
     * WARNING overrites current data, so shoudl be called before the rest of the form is setup.
     */
    public void overwriteWith(FormObject form) {
        if (form == null)
            return;

        if (form.parentRef != null)
            parentRef = form.parentRef;
        if (form.flags != null)
            flags = form.flags.clone();

        if (form.I != null)
            I = form.I.clone();
        if (form.selectedItem != null)
            selectedItem = form.selectedItem;
        
        if (form.ref != null)
            ref = form.ref;
        if (form.textColor != null)
            textColor = form.textColor.clone();
        if (form.textFont != null)
            textFont = form.textFont;
        if (form.textSize != -1)
            textSize = form.textSize;
        if (form.textString!=null)
            textString = form.textString;
        
        if (form.appearancesUsed)
            appearancesUsed = form.appearancesUsed;
        if (form.offsetDownIcon)
            offsetDownIcon = form.offsetDownIcon;
        if (form.noDownIcon)
            noDownIcon = form.noDownIcon;
        if (form.invertDownIcon)
            invertDownIcon = form.invertDownIcon;
        
        if (form.onState != null)
            onState = form.onState;
        if (form.currentState != null)
            currentState = form.currentState;
        if (form.normalOffImage != null)
            normalOffImage = form.normalOffImage;
        if (form.normalOnImage != null)
            normalOnImage = form.normalOnImage;
        if (form.rolloverOffImage != null)
            rolloverOffImage = form.rolloverOffImage;
        if (form.rolloverOnImage != null)
            rolloverOnImage = form.rolloverOnImage;
        if (form.downOffImage != null)
            downOffImage = form.downOffImage;
        if (form.downOnImage != null)
            downOnImage = form.downOnImage;
        if (form.pageNumber != -1)
            pageNumber = form.pageNumber;

        //annotations
        if (form.cColor != null)
            cColor = form.cColor;
        if (form.contents != null)
            contents = form.contents;
        if (form.show)
            show = form.show;

        //?? cloning
        AA=form.AA;
        AP=form.AP;
        BS=form.BS;
        D=form.D;
        OC=form.OC;

        C=(form.C==null) ? null : form.C.clone();
        
        QuadPoints=(form.QuadPoints==null) ? null : form.QuadPoints.clone();

        F=form.F;
        Ff=form.Ff;
        CO=(form.CO==null) ? null : form.CO.clone();
        Opt=(form.Opt==null) ? null : form.Opt.clone();
    	Q = form.Q;
    	MaxLen=form.MaxLen;
    	FT=form.FT;
        rawAS=(form.rawAS==null) ? null : form.rawAS.clone();
    	rawDA=(form.rawDA==null) ? null : form.rawDA.clone();
        rawDV=(form.rawDV==null) ? null : form.rawDV.clone();
    	rawJS=(form.rawJS==null) ? null : form.rawJS.clone();
    	rawNM=(form.rawNM==null) ? null : form.rawNM.clone();
    	rawTM=(form.rawTM==null) ? null : form.rawTM.clone();
    	rawTU=(form.rawTU==null) ? null : form.rawTU.clone();
    	rawV=(form.rawV==null) ? null : form.rawV.clone();
    	
    	//copy fieldname
   		T=form.T;
   		rawT=(form.rawT==null) ? null : form.rawT.clone();
    	
    	Rect=(form.Rect==null) ? null : form.Rect.clone();
    	
    	TI=form.TI;
    	
    	MK = (form.MK==null) ? null : (PdfObject)form.MK.clone();
    	
    }

    public Object getPopupObj(){
		return popupObj;
    }
    
	/**
     * See also  {@link FormObject#getUserName()}
     * @return the full field name for this form
     *
     * @deprecated use formObject.getTextStreamValue(PdfDictionary.T);
	 * NO LONGER USED INTERNALLY
     */
//	public String getFieldName() {
//
//		//ensure resolved
//		if(T==null)
//		this.getTextStreamValue(PdfDictionary.T);
//
//		return T;
//	}

	/**
	 * @return the currently selected State of this field at time of opening.
	 */
	public String getCurrentState() {
		return currentState;
	}

	/**
	 * @return the on state for this field
	 */
	public String getOnState() {
		return onState;
	}

	/**
	 * @return the characteristics for this field.
	 * <br>
	 * bit 1 is index 0 in []
	 * [0] 1 = invisible
	 * [1] 2 = hidden - dont display or print
	 * [2] 3 = print - print if set, dont if not
	 * [3] 4 = nozoom
	 * [4] 5= norotate
	 * [5] 6= noview
	 * [6] 7 = read only (ignored by wiget)
	 * [7] 8 = locked
	 * [8] 9 = togglenoview
	 * as on pdf 1.7 this became 10 bits long
	 * [9] 10 = LockedContents
	 */
	public boolean[] getCharacteristics() {

		//lazy initialisation
		if(Farray==null){
			
			if(F==-1)
				Farray=new boolean[10];
			else
				Farray=calcFarray(F);
			
		//	System.out.println("F="+F+" "+this.getPDFRef()+" "+characteristic+" display="+display+" "+characteristic[2]);
		}
		

        return Farray;
	}

	/**
	 * @return userName for this field (TU value)
     *
     *  use formObject.getTextStreamValue(PdfDictionary.TU);
	 */
//	public String getUserName() {
//
//		//ensure resolved   g
//		if(TU==null)
//		getTextStreamValue(PdfDictionary.TU);
//
//		return TU;
//	}

	/**
	 * @return the default text size for this field
	 */
	public int getTextSize() {
		return textSize;
	}

	/**
	 * @return the values map for this field,
	 * map that references the display value from the export values
	 */
	public Map getValuesMap(boolean keyFirst) {
		
		
		if(Opt!=null && OptValues==null){
							
			Object[] rawOpt=getObjectArray(PdfDictionary.Opt);	
			
			if(rawOpt!=null){
				
				String key,value;
				Object[] obj;

                for (Object aRawOpt : rawOpt) {

                    if (aRawOpt instanceof Object[]) { //2 items (see p678 in v1.6 PDF ref)
                        obj = (Object[]) aRawOpt;

                        if (keyFirst) {
                            key = StringUtils.getTextString((byte[]) obj[0], false);
                            value = StringUtils.getTextString((byte[]) obj[1], false);
                        } else {
                            key = StringUtils.getTextString((byte[]) obj[1], false);
                            value = StringUtils.getTextString((byte[]) obj[0], false);
                        }

                        if (OptValues == null)
                            OptValues = new HashMap();

                        OptValues.put(key, value);

                    }
                }
			}		
		}
		
		return OptValues;
	}

	/**
	 * @return the default value for this field
     *
     * use formObject.getTextStreamValue(PdfDictionary.DV);
	 */
	//public String getDefaultValue() {
		
	//	return getTextStreamValue(PdfDictionary.DV);
	//}

	/**
	 * @return the items array list (Opt)
	 */
	public String[] getItemsList() {
		
		if(OptString==null){
        Object[] rawOpt=getObjectArray(PdfDictionary.Opt);

        if(rawOpt!=null){
            int count=rawOpt.length;
            OptString=new String[count];


            for(int ii=0;ii<count;ii++){

                if(rawOpt[ii] instanceof Object[]){ //2 items (see p678 in v1.6 PDF ref)
                    Object[] obj=(Object[])rawOpt[ii];

						OptString[ii]= StringUtils.getTextString((byte[]) obj[1], false);

                }else if(rawOpt[ii] instanceof byte[]){
                    OptString[ii]= StringUtils.getTextString((byte[]) rawOpt[ii], false);

                }else if(rawOpt[ii]!=null){
                }
            }
        }
		}


        return OptString;
    }

    /**
	 * @return the selected Item for this field
	 */
	public String getSelectedItem() {
		
		if(selectedItem==null)
    		selectedItem=getTextStreamValue(PdfDictionary.V);
    	
		//if no value set but selection, use that
		if(selectedItem==null && I!=null ){
			String[] items= this.getItemsList();
	          int itemSelected=I[0];
	          if(items!=null && itemSelected>-1 && itemSelected<items.length)
	        	  return items[itemSelected];
	          else
	        	  return null;
		}else
			return selectedItem;
	}

	/**
	 * @return the top index, or item that is visible in the combobox or list first.
	 */
	public int[] getTopIndex() {

		if(I==null && TI!=-1){
			I=new int[1];
			I[0]=TI;
		}
		
		return I;
	}

	/**
	 * @return the text string for this field - if no value set but a default (DV value)
     * set, return that.
	 */
	public String getTextString() {
		if(textString==null)
			textString =getTextStreamValue(PdfDictionary.V);
		
		if(textString==null && getTextStreamValue(PdfDictionary.DV)!=null)
        	return getTextStreamValue(PdfDictionary.DV);
		else{
			if(textString!=null)
                textString = textString.replaceAll("\r", "\n").trim();
			else
				textString = "";
			
        	return textString;
		}
	}

	/**
	 * @return the maximum length of the text in the field
     *
     *  use formObject.getInt(PdfDictionary.MaxLen)
	 */
	//public int getMaxTextLength() {
		
	//	return MaxLen;
	//}

	/**
	 * @return the normal caption for this button,
	 * the caption displayed when nothing is interacting with the icon, and at all other times unless 
	 * a down and/or rollover caption is present
     *
     *  use formObject.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
	 */
	//public String getNormalCaption() {
		
	//	return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
	//}

	/**
	 * @return the down caption,
	 * caption displayed when the button is down/pressed
     *
     *  use formObject.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.AC);
	 */
	//public String getDownCaption() {
		
	//	return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.AC);
	//}

	/**
	 * @return the rollover caption,
	 * the caption displayed when the user rolls the mouse cursor over the button
     *
     * deprecated use formObject.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.RC);
	 *
	public String getRolloverCaption() {
		
		return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.RC);
	}/**/

	/**
	 * @return the position of the view of the text in this field
	 * 
	 * positioning of text relative to icon - (integer)
     * 	0=caption only
     * 	1=icon only
     * 	2=caption below icon
     * 	3=caption above icon
     * 	4=caption on right of icon
     * 	5=caption on left of icon
     * 	6=caption overlaid ontop of icon
     */
	public int getTextPosition() {
		
		return getDictionary(PdfDictionary.MK).getInt(PdfDictionary.TP);
	}

	/**
	 * @return the default state of this field,
	 * the state to return to when the field is reset
     *
     *  use formObject.getName(PdfDictionary.AS);
	 */
//	public String getDefaultState() {
//
//		if(AS==null)
//			this.getName(PdfDictionary.AS);
//
//		return AS;
//
//	}

	/**
	 * @return the normal on state for this field
	 */
	public String getNormalOnState() {
		return normalOnState;
	}
	
	/**
	 * @return the normal off image for this field
	 */
	public BufferedImage getNormalOffImage() {
		
		return normalOffImage;
	}

	/**
	 * @return the normal On image for this field
	 */
	public BufferedImage getNormalOnImage() {
		
		return normalOnImage;
	}

	/**
	 * @return if this field has not got a down icon
	 */
	public boolean hasNoDownIcon() {
		return noDownIcon;
	}

	/**
	 * @return whether this field has a down icon as an offset of the normal icon
	 */
	public boolean hasOffsetDownIcon() {
		return offsetDownIcon;
	}

	/**
	 * @return whether this field has a down icon as an inverted image of the normal icon
	 */
	public boolean hasInvertDownIcon() {
		return invertDownIcon;
	}

	/**
	 * @return the down off image for this field
	 */
	public BufferedImage getDownOffImage() {
		return downOffImage;
	}

	/**
	 * @return the down on image for this field
	 */
	public BufferedImage getDownOnImage() {
		return downOnImage;
	}

	/**
	 * @return the rollover image for this field,
	 * the image displayed when the user moves the mouse over the field
	 */
	public BufferedImage getRolloverOffImage() {
		return rolloverOffImage;
	}

	/**
	 * @return the rollover on image,
	 * the image displayed when the user moves the mouse over the field, when in the on state
	 */
	public BufferedImage getRolloverOnImage() {
		return rolloverOnImage;
	}

	/**
	 * @return the text font for this field
	 */
	public Font getTextFont() {
		if(textFont==null){
			textFont = new Font("Arial",Font.PLAIN,8);
		}
		return textFont;
	}

	/**
	 * @return the text color for this field
	 */
	public Color getTextColor() {
		return generateColor(textColor);
	}

	/**
	 * @return the border color for this field
     *
     * @deprecated use FormObject.generateColor(formObject.getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BC));
	 */
	public Color getBorderColor() {
		
		return generateColor(getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BC));
	}

	/**
	 * @return the border style for this field
     *
     * deprecated use formObject.getDictionary(pdfDictionary.BS);
	 *
	public PdfObject getBorder() {
		return BS;
	}/**/

	/**
	 * @return the background color for this field
     * deprecated - use FormObject.generateColor(formObj.getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BG));
	 *
	public Color getBackgroundColor() {
    	
		return generateColor(getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BG));
	} /**/

	/**
	 * return true if the popup component has been built
	 */
	public boolean isPopupBuilt() {
		return popupBuilt;
	}

	/**
	 * store the built popup component for use next time
	 * and set popupBuilt to true.
	 */
	public void setPopupBuilt(Object popup) {
		if(popup==null)
			return;
		
		popupObj = popup;
		popupBuilt = true;
	}

    /**
     * @return
     */
    public String getLayerName() {
    	
    	//lazy initialisation
    	if(this.layerName==null){
			PdfObject OC=this.getDictionary(PdfDictionary.OC);
			
			if(OC!=null)
				this.layerName=OC.getName(PdfDictionary.Name);
		}
    	
        return layerName;
    }
    
    /**JS stores if any of the form values have changed acessed via hasFormChanged()*/
	private boolean formChanged = false;
	
	/**JS has the form fields changed */
	public boolean hasValueChanged(){
		return formChanged;
	}
	
	/** flags up this forms value as being changed so that it will be updated to the view */
	public void setFormChanged() {
		formChanged = true;
	}
	
	/**JS resets the form changed flag to indicate the values have been updated */
	public void resetFormChanged() {
		formChanged = false;
	}
	
	/** @oldJS
	 * returns the current value for this field,
	 * if text field the text string,
	 * if choice field the selected item
	 * if button field the normal caption
	 * @return
	 */
	public String getValue(){

        int subtype=getParameterConstant(PdfDictionary.Subtype);

        switch(subtype){
		case PdfDictionary.Tx:
			if(textString==null)
				textString = getTextStreamValue(PdfDictionary.V);

			//check if we have a null value and if so return an emtpy string instead,
            //as JS assumes text values are always string values.
			if(textString==null)
				return "";
			else 
				return textString;
			
        case PdfDictionary.Ch:

        	if(selectedItem==null)
        		selectedItem=getTextStreamValue(PdfDictionary.V);

            return selectedItem;

        case PdfDictionary.Btn:

            return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
            
        case PdfDictionary.Sig:

            return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);

        default:// to catch all the annots

        	return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
		}
		//NOTE - Do not return empty string if value is null, as affects 0.00 values.
	}
	
	/**@oldJS
	 * sets the value of this field dependent on which type of field it is 
	 */
	public void setValue(String inVal){//need to kept as java strings
		boolean preFormChanged = formChanged;
		String CA;

        int subtype=getParameterConstant(PdfDictionary.Subtype);
        
        switch(subtype){
		case PdfDictionary.Tx:
			
			String curVal = getTextStreamValue(PdfDictionary.V);
			//if the current value is the same as the new value, our job is done.
			if(curVal!=null && curVal.equals(inVal))
				break;
			
			if(textString != null && textString.equals(inVal)){
				break;
			}
			
			//use empty string so that the raw pdf value does not get recalled.
			if(inVal==null)
				inVal = "";
			textString = inVal;
			
			formChanged = true;
			break;

        case PdfDictionary.Ch:

        	if(selectedItem==null)
        		selectedItem=getTextStreamValue(PdfDictionary.V);

            if(selectedItem != null && selectedItem.equals(inVal)){
				break;
			}
			selectedItem = inVal;
			formChanged = true;
			break;

        case PdfDictionary.Btn:

        	CA=getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
    		if(CA != null && CA.equals(inVal)){
				break;
			}
            getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.CA,StringUtils.toBytes(inVal));
           
			formChanged = true;
			break;

        default:

        	CA=getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
            if(CA != null && CA.equals(inVal)){
				break;
			}
            
            getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.CA,StringUtils.toBytes(inVal));
            
			formChanged = true;
			
		}
		
		if(formHandler!=null && formChanged && !preFormChanged)
			formHandler.C(this);
	}
	
	/**@oldJs
	 * defines the thickness of the border when stroking.
	 */
	public void setLineWidth(int lineWidth){

		if(BS==null)
			BS=new FormObject();
		
		BS.setIntNumber(PdfDictionary.W, lineWidth);
		
	}
	
	/**JS Controls whether the field is hidden or visible on screen and in print. Values are:
	 * visible 0,
	 * hidden 1,
	 * noPrint 2,
	 * noView 3.
	 * 
	 * GetCharacteristics will add this value into its array when called.
	 */
	public int display = -1;

	/**@oldJs
	 * added for backward compatibility or old adobe files. 
	 */
	public void setBorderWidth(int width){ setLineWidth(width); } 
	
	/*
	change; p373 js_api
	Example;
	changeEx
	commitKey;
	fieldFull;
	keyDown;
	modifier;
	name;
	rc;
	richChange;
	richChangeEx;
	richValue;
	selEnd;
	selStart;
	shift;
	source;
	target;
	targetName;
	type;
	value;
	 */
	/**@oldJS
	 * Verifies the current keystroke event before the data is committed. It can be used to check target 
	 * form field values to verify, for example, whether character data was entered instead of numeric 
	 * data. JavaScript sets this property to true after the last keystroke event and before the field 
	 * is validated.
	 */
	public static boolean willCommit(){
		//CHRIS javascript unimplemented willcommit
		return true;
	}
	
	/**JS shows if the display value has changed, if it has we need to check what to and change
	 * <br>call getCharacteristics() to get the new display values and that will reset the display flag 
	 */
	public boolean hasDisplayChanged() {
		boolean checkChange = (display!=-1);
		if(checkChange)
			return true;
		else 
			return false;
	}
	
	/**@oldJS
	 * added to return this for event.target from javascript 
	 */
	public Object getTarget(){ return this; }
	
	/**@oldJS
	 * JS returns the normal caption associated to this button
	 */
	public String buttonGetCaption(){ return buttonGetCaption(0); }
	
	/**@oldJS
	 * returns the caption associated with this button,
	 * @param nFace - 
	 * 0  normal caption (default)
	 * 1  down caption
	 * 2  rollover caption
	 */
	public String buttonGetCaption(int nFace){
		switch(nFace){
		case 1: return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.AC);
		case 2: return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.RC);
		default: return getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
		}
	}
	
	/**@oldJS
	 * sets this buttons normal caption to <b>cCaption</b> 
	 */
	public void buttonSetCaption(String cCaption){ buttonSetCaption(cCaption,0);}
	
	/**@oldJS
	 * sets this buttons caption to <b>cCaption</b>, it sets the caption defined by <b>nFace</b>.
	 * @param nFace -
	 * 0  (default) normal caption
	 * 1  down caption
	 * 2  rollover caption
	 */
	public void buttonSetCaption(String cCaption,int nFace){
		switch(nFace){
		case 1: getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.AC, StringUtils.toBytes(cCaption));
		break;
		case 2: getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.RC, StringUtils.toBytes(cCaption));
		break;
		default: getDictionary(PdfDictionary.MK).setTextStreamValue(PdfDictionary.CA, StringUtils.toBytes(cCaption));
		}
	}
	
	/**@oldJS
	 * returns the background color for the annotation objects 
	 */
	public Object getfillColor(){
		return generateColor(getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BG));
	}
	

    public boolean isKid() {
        return isKid;
    }

    public void setKid(boolean kid) {
        isKid=kid;
    }

	public void setFormType(int widgetType) {
		formType = widgetType;
	}
	
	/**
	 * look at FormFactory.LIST etc for full list of types
	 */
	public int getFormType(){
		return formType;
	}
	
	/** returns an Opaque BufferedImage for use when appearance Streams are null */
    public static BufferedImage getOpaqueImage(){
	    return new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    }
    

    public String getNameUsed() {
        return nameUsed;
    }

    public void setNameUsed(String name) {
        nameUsed=name;
    }

    public boolean testedForDuplicates() {
        return tested;
    }

    public void testedForDuplicates(boolean b) {
        tested=b;
    }

    public void setGUIComponent(Object guiComp) {
        this.guiComp=guiComp;
    }

    public Object getGUIComponent() {
        return guiComp;
    }
}

