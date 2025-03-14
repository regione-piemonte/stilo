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
  * PdfFont.java
  * ---------------
 */
package org.jpedal.fonts;

//standard java

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfFontException;
import org.jpedal.fonts.glyph.PdfJavaGlyphs;
import org.jpedal.io.ObjectStore;
import org.jpedal.io.PdfObjectReader;
import org.jpedal.objects.raw.PdfArrayIterator;
import org.jpedal.objects.raw.CIDEncodings;
import org.jpedal.objects.raw.FontObject;
import org.jpedal.objects.raw.PdfObject;
import org.jpedal.objects.raw.PdfDictionary;

import org.jpedal.utils.LogWriter;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 * contains all generic pdf font data for fonts.<P>
 *
 */
public class PdfFont implements Serializable {

    public Font javaFont=null;

    private Rectangle BBox=null;

    boolean handleOddSapFontMapping =false;

    /** used to track if CID font is double byte or not by looking at first 2 values*/
    boolean isFirstScan=true;

    int isDouble=-1; //-1=unset

    //value to use if no width set for this
    final static private int noWidth=-1;

    //workaroud for type3 fonts which contain both Hex and Denary Differences tables
    protected boolean containsHexNumbers=false, allNumbers=false;

    protected String embeddedFontName=null,embeddedFamilyName=null,copyright=null;

    private float missingWidth=noWidth;

    boolean isSingleByte=false;

    protected boolean isFontVertical=false;

    /**cached value for last width value returned*/
    private float lastWidth=noWidth;

    public PdfJavaGlyphs glyphs=new PdfJavaGlyphs();

    /**cache for translate values*/
    private String[] cachedValue=new String[256];

    //unmapped font name
    private String rawFontName=null;

    //used by HTML
    private float currentWidth;

    //used by HTML to translate non-standard glyfs to correct values
    Map nonStandardMappings=new HashMap(256);

    public boolean hasDoubleBytes;

    static{
        setStandardFontMappings();
    }

    public PdfFont(){}

    /**read in a font and its details for generic usage*/
    public void createFont(String fontName) throws Exception{}

    /**get handles onto Reader so we can access the file*/
    public PdfFont(PdfObjectReader current_pdf_file) {

        init(current_pdf_file);

    }

    private static void setStandardFontMappings(){

        int count=StandardFonts.files_names.length;

        for(int i=0;i<count;i++){
            String key=StandardFonts.files_names_bis[i].toLowerCase();
            String value=StandardFonts.javaFonts[i].toLowerCase();

            if((!key.equals(value))&&(!FontMappings.fontSubstitutionAliasTable.containsKey(key)))
                FontMappings.fontSubstitutionAliasTable.put(key,value);

        }

        for(int i=0;i<count;i++){

            String key=StandardFonts.files_names[i].toLowerCase();
            String value=StandardFonts.javaFonts[i].toLowerCase();
            if((!key.equals(value))&&(!FontMappings.fontSubstitutionAliasTable.containsKey(key)))
                FontMappings.fontSubstitutionAliasTable.put(key,value);
            StandardFonts.javaFontList.put(StandardFonts.files_names[i],"x");
        }


    }


    protected String substituteFont=null;

    protected boolean renderPage=false;

    private static final float xscale =(float)0.001;

    /**embedded encoding*/
    protected int embeddedEnc=StandardFonts.STD;

    /**holds lookup to map char differences in embedded font*/
    protected String[] diffs;

    /**flag to show if Font included embedded data*/
    public boolean isFontEmbedded=false;

    /*show if font stream CID*/
    protected boolean TTstreamisCID=false;

    /**String used to reference font (ie F1)*/
    protected String fontID="";

    /**number of glyphs - 65536 for CID fonts*/
    protected int maxCharCount=256;

    /**show if encoding set*/
    protected boolean hasEncoding=true;

    /**font type*/
    protected int fontTypes;

    protected String substituteFontFile=null;

    /**lookup to track which char is space. -1 means none set*/
    private int spaceChar = -1;

    /**holds lookup to map char differences*/
    String[] diffTable;

    private int[] diffCharTable;

    protected Map diffLookup=null;

    /**lookup for which of each char for embedded fonts which we can flush*/
    private float[] widthTable ;

    /**size to use for space if not defined (-1 is no setting)*/
    private float possibleSpaceWidth=noWidth;

    /**handle onto file access*/
    protected PdfObjectReader currentPdfFile;

    /**loader to load data from jar*/
    protected ClassLoader loader = this.getClass().getClassLoader();

    /**FontBBox for font*/
    public double[] FontMatrix={0.001d,0d,0d,0.001d,0,0};

    /**font bounding box*/
    public float[] FontBBox= { 0f, 0f, 1000f, 1000f };


    float ascent=0;
    float descent=0;

    /**
     * flag to show
     * Gxxx, Bxxx, Cxxx.
     */
    protected boolean isHex = false ;

    /**holds lookup to map char values*/
    private String[] unicodeMappings;

    /**encoding pattern used for font. -1 means not set*/
    protected int fontEnc = -1;

    /**flag to show type of font*/
    protected boolean isCIDFont=false;

    /**lookup CID index mappings*/
    private String[] CMAP;

    /** CID font encoding*/
    private String CIDfontEncoding;

    /**default width for font*/
    private float defaultWidth=1f;

    protected boolean isFontSubstituted=false;

    protected int italicAngle=0;

    boolean hasMatrixSet=false, hasFBoxSet=false;

    /**test if cid.jar present first time we need it*/
    private static boolean isCidJarPresent;

    /**
     * used to show truetype used for type 0 CID
     */
    public boolean isFontSubstituted() {
        return isFontSubstituted;
    }

    /**Method to add the widths of a CID font*/
    private void setCIDFontWidths(String values) {

        //remove first and last []
        values = values.substring(1, values.length() - 1).trim();

        //trap for empty values
        if(values.length()==0)
            return;

        widthTable=new float[65536];

        //set all values to -1 so I can spot ones with no value
        for(int ii=0;ii<65536;ii++)
            widthTable[ii]=noWidth;

        StringTokenizer widthValues = new StringTokenizer(values, " []",true);
        String currentValue ="",lastValue="",nextValue="";

        int pointer=0,lastPointer=0;

        while(widthValues.hasMoreTokens()){
            nextValue = widthValues.nextToken();
            if(!nextValue.equals(" "))
                break;
        }

        boolean isDone=false;

        while (true) {

            if(isDone)
                break;

            if(nextValue.equals("R")){ //allow for special odd case  0 1449 0 R and read data directly

                String ref=lastValue+' '+currentValue+" R";

                int number=Integer.parseInt(lastValue);
                int generation=Integer.parseInt(currentValue);

                pointer=lastPointer;

                //get the list directly from 14449 0 R
                FontObject widthObj=new FontObject(ref);
                byte[] data=currentPdfFile.getObjectReader().readObjectAsByteArray(widthObj, currentPdfFile.getObjectReader().isCompressed(number,generation),number,generation);

                //and parse
                StringTokenizer widthValues2 = new StringTokenizer(new String(data), " []");
                String val;

                while(widthValues2.hasMoreTokens()){
                    val = widthValues2.nextToken();
                    widthTable[pointer]=Float.parseFloat(val)/1000f;
                    pointer++;
                }


                //read next value
                if(widthValues.hasMoreTokens()){
                    while(widthValues.hasMoreTokens()){
                        nextValue = widthValues.nextToken();
                        if(!nextValue.equals(" "))
                            break;
                    }
                }else
                    isDone=true;

            }else{

                lastValue= currentValue;
                currentValue=nextValue;

                lastPointer=pointer;
                pointer = Integer.parseInt(currentValue);

                while(true){
                    currentValue = widthValues.nextToken();
                    if(!currentValue.equals(" "))
                        break;
                }

                //process either range or []
                if (currentValue.equals("[")) {
                    while(true){

                        while(true){
                            currentValue = widthValues.nextToken();
                            if(!currentValue.equals(" "))
                                break;
                        }

                        if(currentValue.equals("]"))
                            break;

                        widthTable[pointer]=Float.parseFloat(currentValue)/1000f;
                        pointer++;

                    }

                    if(widthValues.hasMoreTokens()){
                        while(widthValues.hasMoreTokens()){
                            nextValue = widthValues.nextToken();
                            if(!nextValue.equals(" "))
                                break;
                        }
                    }else
                        isDone=true;

                } else {

                    int endPointer = 1 + Integer.parseInt(currentValue);

                    lastValue=currentValue;
                    while(true){
                        currentValue = widthValues.nextToken();
                        if(!currentValue.equals(" "))
                            break;
                    }

                    if(widthValues.hasMoreTokens()){
                        while(true && widthValues.hasMoreTokens()){
                            nextValue = widthValues.nextToken();
                            if(!nextValue.equals(" "))
                                break;
                        }
                    }else
                        isDone=true;

                    if(!nextValue.equals("R")){

                        for (int ii = pointer; ii < endPointer; ii++)
                            widthTable[ii]=Float.parseFloat(currentValue)/1000f;
                    }

                }
            }
        }
    }


    /**flag if CID font*/
    final public boolean isCIDFont() {

        return isCIDFont;
    }

    /**set number of glyphs to 256 or 65536*/
    final protected void init(PdfObjectReader current_pdf_file){

        this.currentPdfFile = current_pdf_file;

        //setup font size and initialise objects
        if(isCIDFont)
            maxCharCount=65536;

        glyphs.init(maxCharCount,isCIDFont);
    }


    /**return unicode value for this index value */
    final public String getUnicodeMapping(int char_int){
        if(unicodeMappings==null)
            return null;
        else
            return  unicodeMappings[char_int];
    }

    /**store encoding and load required mappings*/
    final protected void putFontEncoding(int enc) {

        if(enc==StandardFonts.WIN && getBaseFontName().equals("Symbol")){
            putFontEncoding(StandardFonts.SYMBOL);
            enc=StandardFonts.SYMBOL;
        }

        /**save encoding */
        fontEnc=enc;

        StandardFonts.checkLoaded(enc);

    }


    /**return the mapped character*/
    final public String getUnicodeValue(String displayValue,int rawInt){

        String textValue=getUnicodeMapping(rawInt);

        if(textValue==null)
            textValue=displayValue;

        //map out lignatures
        if(displayValue.length()>0){
            int displayChar=displayValue.charAt(0);

            switch(displayChar){

                case 173:
                    if(fontEnc==StandardFonts.WIN || fontEnc==StandardFonts.STD)
                        textValue="-";
                    break;

                case 64256:
                    textValue="ff";
                    break;

                case 64257:
                    textValue="fi";
                    break;

                case 64260:
                    textValue="ffl";
                    break;
            }
        }

        return textValue;
    }

    /**
     * convert value read from TJ operand into correct glyph<br>Also check to
     * see if mapped onto unicode value
     */
    final public String getGlyphValue(int rawInt) {

        if(cachedValue[rawInt]!=null)
            return cachedValue[rawInt];

        String return_value = null;

        /***/
        if(isCIDFont){

            //	test for unicode
            String unicodeMappings=getUnicodeMapping(rawInt);
            if(unicodeMappings!=null)
                return_value=unicodeMappings;

            if(return_value == null){

                //get font encoding
                String fontEncoding =CIDfontEncoding;

                if(diffTable!=null){
                    return_value =diffTable[rawInt];
                }else if(fontEncoding!=null){
                    if(fontEncoding.startsWith("Identity-")){
                        return_value= String.valueOf((char) rawInt);
                    }else if(CMAP!=null){
                        String newChar=CMAP[rawInt];

                        if(newChar!=null)
                            return_value=newChar;
                    }
                }

                if(return_value==null)
                    return_value= String.valueOf(((char) rawInt));
            }

        }else
            return_value=getStandardGlyphValue(rawInt);

        //save value for next time
        cachedValue[rawInt]=return_value;

        return return_value;

    }


    /**
     * read translation table
     * @throws PdfFontException
     */
    final private String handleCIDEncoding(PdfObject Encoding, PdfObject fontObject) throws PdfFontException
    {
        BufferedReader CIDstream=null;

        int encodingType=Encoding.getGeneralType(PdfDictionary.Encoding);
        String encodingName=CIDEncodings.getNameForEncoding(encodingType);
        //see if any general value (ie /UniCNS-UTF16-H not predefined in spec)
        if(encodingName==null){
            if(encodingType==PdfDictionary.Identity_H)
                encodingName="Identity-H";
            else if(encodingType==PdfDictionary.Identity_V){
                encodingName="Identity-V";
                isFontVertical=true;
            }else
                encodingName=Encoding.getGeneralStringValue();
        }

        String CMapName=Encoding.getName(PdfDictionary.CMapName);
        if(CMapName!=null){

            byte[] stream=currentPdfFile.readStream(Encoding,true,true,false, false,false, Encoding.getCacheName(currentPdfFile.getObjectReader()));
            encodingName=CMapName;
            CIDstream=new BufferedReader(new StringReader(new String(stream)));
        }

        boolean isIdentity=(encodingType==PdfDictionary.Identity_H || encodingType==PdfDictionary.Identity_V );

        //System.out.println("Name="+encodingName+" "+encodingType);

        /**allow for odd file created by SAP*/
        //baseline_screens/idoq/UAT 9001001438 INV_I58234.pdf

        if(!isIdentity && fontObject!=null){
            String name=fontObject.getName(PdfDictionary.BaseFont);
            if(name!=null && name.contains("Identity")){
                isIdentity=true;
                handleOddSapFontMapping =true;
                isSingleByte=true;
            }

        }

        //investigate for daeja/Armando
        //flag up some single file CIDs (bit of a hack for the moment)
        //if(encodingType==CIDEncodings.CMAP_90ms_RKSJ_H){
        //isSingleByte=true;
        //}

        /**put encoding in lookup table*/
        if(CIDstream==null)
            CIDfontEncoding=encodingName;

        /** if not 2 standard encodings
         * 	load CMAP
         */
        if(isIdentity){

            glyphs.setIsIdentity(true);

        }else{

            //there are a large number of CMAP vtables provided by Adobe which I have put in cid.jar
            //this code detects if present and reads the required table
            //I also put a font in cid.jar which I think is probably a mistake now (we do not need it)

            //test if cid.jar present on first time needed and throw exception if not
            if(!isCidJarPresent  && CIDstream==null){
                isCidJarPresent=true;
                InputStream in=PdfFont.class.getResourceAsStream("/org/jpedal/res/cid/00_ReadMe.pdf");
                if(in==null)
                    throw new PdfFontException("cid.jar not on classpath");
            }

            glyphs.setIsIdentity(false);

            CMAP=new String[65536];
            glyphs.CMAP_Translate=new int[65536];

            //load standard if not embedded
            //try{

                //20120718 commented out by Mark as cid.jar now works (and breaks existing files like customers3/japanese.pdf)
                //if(CIDstream==null)
                //CIDstream =new BufferedReader
                //(new InputStreamReader(loader.getResourceAsStream("org/jpedal/res/cid/" + encodingName), "Cp1252"));
//            } catch (Exception e) {
//                e.printStackTrace();
//                if(LogWriter.isOutput())
//                    LogWriter.writeLog("1.Problem reading encoding for CID font "+fontID+" encoding="+encodingName+" Check CID.jar installed");
//            }

            //read values into lookup table
            if (CIDstream != null) {
                readCIDCMap(CIDstream);
            }
        }

        if(CIDstream!=null){
            try{
                CIDstream.close();
            } catch (Exception e) {
                if(LogWriter.isOutput())
                    LogWriter.writeLog("2.Problem reading encoding for CID font "+fontID+ ' ' +encodingName+" Check CID.jar installed");
            }
        }

        return CMapName;
    }

    private void readCIDCMap(BufferedReader CIDstream) {

        String line = "";
        int begin, end, entry;
        boolean inDefinition = false,inMap=false;

        while (true) {

            try{
                line = CIDstream.readLine();
                //System.out.println(line);
            } catch (Exception e) {
                if(LogWriter.isOutput())
                    LogWriter.writeLog("[PDF] Error reading line from font");
            }

            if (line == null)
                break;

            if (line.contains("endcidrange"))
                inDefinition=false;
            else if (line.contains("endcidchar"))
                inMap=false;

            if (inDefinition == true) {
                StringTokenizer CIDentry =new StringTokenizer(line, " <>[]");

                //flag if multiple values
                boolean multiple_values = false;
                if (line.indexOf('[') != -1)
                    multiple_values = true;

                //first 2 values define start and end
                begin = Integer.parseInt(CIDentry.nextToken(), 16);
                end = Integer.parseInt(CIDentry.nextToken(), 16);
                entry = Integer.parseInt(CIDentry.nextToken(), 16);

                //put into array
                for (int i = begin; i < end + 1; i++) {
                    if (multiple_values == true) {
                        //put either single values or range
                        entry =Integer.parseInt(CIDentry.nextToken(), 16);
                        CMAP[i]= String.valueOf((char) entry);
                    } else {
                        CMAP[i]= String.valueOf((char) entry);
                        entry++;
                    }
                }
            } else if (inMap == true) {

                try{
                    StringTokenizer CIDentry =new StringTokenizer(line, " <>[]");

                    //System.out.println("line="+line+" "+CIDentry.countTokens());
                    if(CIDentry.countTokens()==2){
                        //flag if multiple values
                        //boolean multiple_values = false;
                        //if (line.indexOf('[') != -1)
                        //	multiple_values = true;

                        //first 2 values define start and end
                        begin = Integer.parseInt(CIDentry.nextToken(), 16);
                        end = Integer.parseInt(CIDentry.nextToken());
                        //entry = Integer.parseInt(CIDentry.nextToken(), 16);

                        //put into array
                        //for (int i = begin; i < end + 1; i++) {
                        //if (multiple_values == true) {
                        //put either single values or range
                        //entry =Integer.parseInt(CIDentry.nextToken(), 16);
                        //CMAP[i]= String.valueOf((char) entry);
                        //} else {

                        glyphs.CMAP_Translate[begin]= end;
                        //entry++;
                        //}
                        //}
                    }else{
                    }
                }catch(Exception ef){
                    ef.getStackTrace();
                }
            }


            if (line.contains("begincidrange"))
                inDefinition = true;
            else if (line.contains("begincidchar"))
                inMap = true;
        }
    }

    /**
     * convert value read from TJ operand into correct glyph<br> Also check to
     * see if mapped onto unicode value
     */
    private String getStandardGlyphValue(int char_int) {

        //get possible unicode values
        String unicode_char = getUnicodeMapping(char_int);

        //handle if unicode
        if (unicode_char != null)// & (mapped_char==null))
            return unicode_char;

        //not unicode so get mapped char
        String return_value = "", mapped_char;

        //get font encoding
        int font_encoding = getFontEncoding( true);

        mapped_char = getMappedChar(char_int,true);

        // handle if differences first then standard mappings
        if (mapped_char != null) { //convert name into character

            // First check if the char has been mapped specifically for this
            String char_mapping =StandardFonts.getUnicodeName(this.fontEnc +mapped_char);

            if (char_mapping != null)
                return_value = char_mapping;
            else {

                char_mapping =StandardFonts.getUnicodeName(mapped_char);

                if (char_mapping != null)
                    return_value = char_mapping;
                else {

                    if(mapped_char.length()==1){
                        return_value = mapped_char;
                    }else if (mapped_char.length() > 1) {
                        char c = mapped_char.charAt(0);
                        char c2 = mapped_char.charAt(1);
                        if (c == 'B' || c == 'C' || c == 'c' || c == 'G' ) {
                            mapped_char = mapped_char.substring(1);
                            try {
                                int val =(isHex)
                                        ? Integer.valueOf(mapped_char, 16) : Integer.parseInt(mapped_char);
                                return_value = String.valueOf((char) val);
                            } catch (Exception e) {
                                return_value = "";
                            }
                        } else
                            return_value = "";

                        //allow for hex number
                        boolean isHex=((c>=48 && c<=57)||(c>=97 && c<=102) || (c>=65 && c<=70))&&
                                ((c2>=48 && c2<=57)||(c2>=97 && c2<=102) || (c2>=65 && c2<=70));

                        if(return_value.length()==0 && this.fontTypes ==StandardFonts.TYPE3 && mapped_char.length()==2 && isHex){

                            return_value= String.valueOf((char) Integer.parseInt(mapped_char, 16));

                        }

                        //handle some odd mappings in Type3 and other cases
                        if(return_value.length()==0){

                            if(fontTypes==StandardFonts.TYPE3)// && !StandardFonts.isValidGlyphName(char_mapping))
                                return_value= String.valueOf((char) char_int);
                            else if(diffTable!=null && diffTable[char_int]!=null && fontEnc==StandardFonts.WIN){ //hack for odd file

                                return_value=diffTable[char_int];
                                if(return_value.indexOf('_')!=-1)
                                    return_value = return_value.replaceAll("_", "");
                            }
                        }

                    } else
                        return_value = "";
                }
            }
        } else if (font_encoding > -1) //handle encoding
            return_value=StandardFonts.getEncodedChar(font_encoding,char_int);

        return return_value;
    }


    /**set the font being used or try to approximate*/
    public final Font getJavaFont(int size) {

        int style =Font.PLAIN;
        boolean isJavaFontInstalled = false;
        String weight =null,mappedName=null,font_family_name=glyphs.fontName;

        String testFont=font_family_name;
        if(font_family_name!=null)
            testFont=font_family_name.toLowerCase();

        //System.out.print(testFont);
        if(testFont.equals("arialmt")){
            testFont="arial";
            font_family_name=testFont;
        }else if(testFont.equals("arial-boldmt")){
            testFont="arial Bold";
            font_family_name=testFont;
        }
        //	System.out.print(testFont+" "+font_family_name);
        //pick up any weight in type 3 font or - standard font mapped to Java
        //int pointer = font_family_name.indexOf(",");
        //if ((pointer == -1))//&&(StandardFonts.javaFontList.get(font_family_name)!=null))
        //	pointer = font_family_name.indexOf("-");

//		if (pointer != -1) {
//
//		    //see if present with ,
//			mappedName=(String) FontMappings.fontSubstitutionAliasTable.get(testFont);
//
//			weight =testFont.substring(pointer + 1, testFont.length());
//
//			if (weight.indexOf("bold") != -1)
//				style = Font.BOLD;
//			else if (weight.indexOf("roman") != -1)
//				style = Font.ROMAN_BASELINE;
//
//			if (weight.indexOf("italic") != -1)
//				style = style+Font.ITALIC;
//			else if (weight.indexOf("oblique") != -1)
//				style = style+Font.ITALIC;
//
//			font_family_name = font_family_name.substring(0, pointer);
//
//		}

        //remap if not type 3 match
        //if(mappedName==null)
        //mappedName=(String) FontMappings.fontSubstitutionAliasTable.get(testFont);

        if(mappedName!=null){
            font_family_name=mappedName;
            testFont=font_family_name.toLowerCase();
        }

        //see if installed
        if(PdfJavaGlyphs.fontList !=null){
            int count = PdfJavaGlyphs.fontList.length;
            for (int i = 0; i < count; i++) {
                System.out.println(PdfJavaGlyphs.fontList[i]+"<>"+testFont);
                if ((PdfJavaGlyphs.fontList[i].contains(testFont))) {
                    isJavaFontInstalled = true;
                    font_family_name=PdfJavaGlyphs.fontList[i];
                    i = count;
                }
            }
        }

        /**approximate display if not installed*/
        if (isJavaFontInstalled == false) {

            //try to approximate font
            if(weight==null){

                //pick up any weight
                String test = font_family_name.toLowerCase();
                if (test.contains("heavy"))
                    style = Font.BOLD;
                else if (test.contains("bold"))
                    style = Font.BOLD;
                else if (test.contains("roman"))
                    style = Font.ROMAN_BASELINE;

                if (test.contains("italic"))
                    style = style+Font.ITALIC;
                else if (test.contains("oblique"))
                    style = style+Font.ITALIC;

            }

            //	font_family_name = defaultFont;
        }

        if(isJavaFontInstalled)
            return new Font(font_family_name, style, size);
        else{

            if(LogWriter.isOutput())
                LogWriter.writeLog("No match with "+glyphs.getBaseFontName()+ ' ' + ' ' +testFont+ ' ' +weight+ ' ' +style);

            return null;
        }

    }

    /**set the font used for default from Java fonts on system
     * - check it is a valid font (otherwise it will default to Lucida anyway)
     */
    public final void setDefaultDisplayFont(String fontName) {

        glyphs.defaultFont=fontName;

    }

    /**
     * Returns the java font, initializing it first if it hasn't been used before.
     */
    public final Font getJavaFontX(int size) {

        //allow user to totally over-ride
        //passing in this allows user to reset any global variables
        //set in this method as well.
        //Helper is a static instance of the inteface JPedalHelper
        if(PdfDecoder.Helper!=null){
            Font f=PdfDecoder.Helper.getJavaFontX(this,size);
            //if you want to implement JPedalHelper but not
            //use this function, just return null
            if(f!=null) {
                return f;
            }

        }

        //noinspection MagicConstant
        return new Font(glyphs.font_family_name, glyphs.style, size);

    }

    /**
     * reset font handle
     *
     public final void unsetUnscaledFont() {
     unscaledFont=null;
     }*/

    /**
     * get font name as a string from ID (ie Tf /F1) and load if one of Adobe 14
     */
    final public String getFontName() {

        //check if one of 14 standard fonts and load if needed
        StandardFonts.loadStandardFontWidth(glyphs.fontName);

        return glyphs.fontName;
    }

    /**
     * get the copyright information
     */
    final public String getCopyright() {
        return copyright;
    }

    /**
     * get raw font name which may include +xxxxxx
     */
    final public String getBaseFontName() {

        return glyphs.getBaseFontName();
    }

    /**
     * set raw font name which may include +xxxxxx
     */
    final public void setBaseFontName(String fontName) {

        glyphs.setBaseFontName(fontName);
    }


    /**
     * get width of a space
     */
    final public float getCurrentFontSpaceWidth() {

        float width;

        //allow for space mapped onto other value
        int space_value =spaceChar;

        if (space_value !=-1)
            width = getWidth(space_value);
        else
            width=  possibleSpaceWidth; //use shortest width as a guess

        //allow for no value
        if (width ==noWidth || width == 0)
            width = 0.2f;

        return width;
    }

    final protected int getFontEncoding( boolean notNull) {
        int result = fontEnc;

        if (result == -1 && notNull)
            result = StandardFonts.STD;

        return result;
    }

    /** Returns width of the specified character<br>
     *  Allows for no value set*/
    final public float getWidth( int charInt) {

        //if -1 return last value fetched
        if(charInt==-1)
            return lastWidth;

        //try embedded font first (indexed by number)
        float width =noWidth;

        if(widthTable!=null && charInt!=-1)
            width =  widthTable[charInt];

        if (width == noWidth) {

            if(isCIDFont){
                width= defaultWidth;

            }else{

                //try standard values which are indexed under NAME of char
                String charName = getMappedChar( charInt,false);

                if((charName!=null)&&(charName.equals(".notdef")))
                    charName=StandardFonts.getUnicodeChar(getFontEncoding( true) , charInt);

                Float value =StandardFonts.getStandardWidth(glyphs.logicalfontName , charName);

                //allow for remapping of base 14 with no width
                if(value==null && rawFontName!=null){

                    //check loaded
                    StandardFonts.loadStandardFontWidth(rawFontName);

                    //try again
                    value =StandardFonts.getStandardWidth(rawFontName , charName);

                }

                if (value != null)
                    width= value;
                else{
                    if(missingWidth!=noWidth)
                        width=missingWidth*xscale;
                    else
                        width=0;
                }
            }
        }

        //cache value so we can reread
        lastWidth=width;

        return width;
    }

    /**generic CID code
     * @throws PdfFontException */
    public void createCIDFont(PdfObject pdfObject, PdfObject Descendent) throws PdfFontException{

        cachedValue=new String[65536];

        String CMapName=null;

        /**read encoding values*/
        PdfObject Encoding=pdfObject.getDictionary(PdfDictionary.Encoding);
        if(Encoding!=null){
            CMapName=handleCIDEncoding(Encoding, pdfObject);
        }

        //handle to unicode mapping
        PdfObject ToUnicode=pdfObject.getDictionary(PdfDictionary.ToUnicode);
        if(ToUnicode!=null){
            UnicodeReader uniReader=new UnicodeReader(currentPdfFile.readStream(ToUnicode,true,true,false, false,false, ToUnicode.getCacheName(currentPdfFile.getObjectReader())));
            unicodeMappings=uniReader.readUnicode();
            hasDoubleBytes=uniReader.hasDoubleByteValues();
        }

        /**read widths*/
        //@speed may need optimising - done as string for moment
        String widths=Descendent.getName(PdfDictionary.W);

        //allow for vertical
        String verticalWidths=Descendent.getName(PdfDictionary.W2);
        if(verticalWidths!=null){
            widths=verticalWidths;
        }

        if(widths!=null)
            setCIDFontWidths(widths);

        /**set default width*/
        int Width=Descendent.getInt(PdfDictionary.DW);
        if(Width>=0)
            defaultWidth=(Width)/1000f;

        //it looks like in this case it uses average of values
        //but not enough data
        if(handleOddSapFontMapping){
            defaultWidth=.5f;
        }

        /**set CIDtoGIDMap*/
        PdfObject CIDToGID=Descendent.getDictionary(PdfDictionary.CIDToGIDMap);
        if(CIDToGID!=null){
            byte[] stream=currentPdfFile.readStream(CIDToGID,true,true,false, false,false, null);

            if(stream!=null){

                int j=0,count=stream.length;
                int[] CIDToGIDMap=new int[count/2];
                for(int i=0;i<count;i=i+2){
                    CIDToGIDMap[j]= (((stream[i] & 255)<<8)+(stream[i+1] & 255));
                    j++;
                }
                glyphs.setGIDtoCID(CIDToGIDMap);
            }else{ // must be identity

                //only set if not also a /value
                if(CIDToGID.getParameterConstant(PdfDictionary.CIDToGIDMap)==PdfDictionary.Unknown)
                    CMapName=handleCIDEncoding(new FontObject(PdfDictionary.Identity_H),null);
            }
        }

        //code is unfinished by MArk - I was originally going to map onto font in
        //cid.jar but now think that is wrong
        //
        //Needs researching and doing properly

        String ordering=null;
        PdfObject CIDSystemInfo=Descendent.getDictionary(PdfDictionary.CIDSystemInfo);
        if(CIDSystemInfo!=null)
            ordering=CIDSystemInfo.getTextStreamValue(PdfDictionary.Ordering);

        if(ordering!=null){
            if(CIDToGID==null && ordering.contains("Identity")){

            }else if(ordering.contains("Japan")){

//                char[] c=new char[]{12498,12521,12462,12494,35282,12468,32,80,114,111,32,87,54};
//
//				substituteFontName=new String(c);
//                substituteFontFile="/Library/Fonts/"+substituteFontName+".otf";
//
                // substituteFontFile="/Users/markee/Library/Fonts/MS Mincho.ttf";
                // System.out.println(substituteFontFile);

                this.TTstreamisCID=false;

                //System.err.println("Unsupported font encoding "+ordering);

            }else if(ordering.contains("Korean")){
                //System.err.println("Unsupported font encoding "+ordering);

            }else if(ordering.contains("Chinese")){
                //System.err.println("Chinese "+ordering);
            }else if(ordering.equals("Identity")){
                glyphs.setIsIdentity(true);
            }

            if(substituteFontFile!=null && LogWriter.isOutput())
                LogWriter.writeLog("Using font "+substituteFontFile+" for "+ordering);

        }

        /**set other values*/
        if (Descendent != null) {

            PdfObject FontDescriptor = Descendent.getDictionary(PdfDictionary.FontDescriptor);

            /**read other info*/
            if(FontDescriptor!=null) {
                setBoundsAndMatrix(FontDescriptor);
                setName( FontDescriptor, fontID);
            }
        }
    }

    /**
     *
     */
    final protected void selectDefaultFont() {
        /**load fonts for specific encoding*
         *
         //get list of fonts and see if installed
         String[] fontList =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
         System.out.println(substituteFontFile+" "+this.substituteFontName);
         Hiragino Kaku Gothic Pro
         Hiragino Kaku Gothic Std
         Hiragino Maru Gothic Pro
         Hiragino Mincho Pro

         defaultFont="Hiragino Mincho Pro";

         int count = fontList.length;
         for (int i = 0; i < count; i++) {
         System.out.println(fontList[i]);
         //if (fontList[i].equals(font_family_name)) {
         //	isFontInstalled = true;
         //	i = count;
         //}
         }
         */
    }

    /**read in width values*/
    public void readWidths(PdfObject pdfObject, boolean setSpace) throws Exception{

        // place font data in fonts array

        //variable to get shortest width as guess for space
        float shortestWidth=0;
        int count=0;

        //read first,last char, widths and put last into array for fast access
        float[] floatWidthValues=pdfObject.getFloatArray(PdfDictionary.Widths);

        if (floatWidthValues != null) {

            widthTable = new float[maxCharCount];

            //set all values to noWidth so I can spot ones with no value
            for(int ii=0;ii<maxCharCount;ii++)
                widthTable[ii]=noWidth;

            int firstCharNumber =pdfObject.getInt(PdfDictionary.FirstChar);
            int lastCharNumber = pdfObject.getInt(PdfDictionary.LastChar);

            //scaling factor to convert type 3 to 1000 spacing
            float ratio=(float) (1f/FontMatrix[0]);
            if(ratio<0)
                ratio=-ratio;

            float nextValue,widthValue;

            int j=0,widthCount=floatWidthValues.length;

            for (int i = firstCharNumber; i < lastCharNumber+1; i++) {

                if(j<widthCount){

                    nextValue=floatWidthValues[j];

                    if(fontTypes==StandardFonts.TYPE3) //convert to 1000 scale unit
                        widthValue =nextValue/ratio;
                    else
                        widthValue =nextValue*xscale;


                    //track shortest width
                    if(widthValue>0){
                        shortestWidth=shortestWidth+widthValue;
                        count++;
                    }

                    widthTable[i]=widthValue;

                }else
                    widthTable[i]=0;


                j++;
            }
        }

        //save guess for space as half average char
        if(setSpace && count>0)
            possibleSpaceWidth=shortestWidth/(2*count);

    }

    /**read in a font and its details from the pdf file*/
    public void createFont(PdfObject pdfObject, String fontID, boolean renderPage, ObjectStore objectStore, Map substitutedFonts) throws Exception{

        //generic setup
        init(fontID, renderPage);

        /**
         * get FontDescriptor object - if present contains metrics on glyphs
         */
        PdfObject pdfFontDescriptor=pdfObject.getDictionary(PdfDictionary.FontDescriptor);

        setName(pdfObject, fontID);
        setEncoding(pdfObject, pdfFontDescriptor);

    }

    protected void setName(PdfObject pdfObject, String fontID) {

        /**
         * get name of font
         */
        // Get fontName
        String baseFontName= pdfObject.getName(PdfDictionary.BaseFont);
        if(baseFontName==null)
            baseFontName= pdfObject.getName(PdfDictionary.FontName);
        if (baseFontName == null)
            baseFontName = this.fontID;
        //if(PdfStreamDecoder.runningStoryPad) //remove spaces and unwanted chars

        if(baseFontName.contains("#20"))
            baseFontName= cleanupFontName(baseFontName);
        //System.out.println("baseFontName="+baseFontName);

        glyphs.setBaseFontName(baseFontName);

        /**
         * get name less any suffix (needs abcdef+ removed from start)
         **/
        String truncatedName= pdfObject.getStringValue(PdfDictionary.BaseFont, PdfDictionary.REMOVEPOSTSCRIPTPREFIX);
        if(truncatedName==null)
            truncatedName= pdfObject.getStringValue(PdfDictionary.FontName, PdfDictionary.REMOVEPOSTSCRIPTPREFIX);
        if (truncatedName == null)
            truncatedName = this.fontID;

        if(truncatedName.contains("#20") || truncatedName.contains("#2D"))
            truncatedName= cleanupFontName(truncatedName);
        //if(PdfStreamDecoder.runningStoryPad) //remove spaces and unwanted chars
        //	truncatedName= cleanupFontName(truncatedName);

        glyphs.fontName=truncatedName;

        if(truncatedName.equals("Arial-BoldMT")){
            glyphs.logicalfontName="Arial,Bold";
            StandardFonts.loadStandardFontWidth(glyphs.logicalfontName);
        }else if(truncatedName.equals("ArialMT")){
            glyphs.logicalfontName="Arial";
            StandardFonts.loadStandardFontWidth(glyphs.logicalfontName);
        }else
            glyphs.logicalfontName=truncatedName;

    }

    /**
     * used by PDF2HTML to ensure name unique
     * @param newName
     */
    public void resetNameForHTML(String newName){
        glyphs.fontName=newName;
        glyphs.baseFontName=newName;
    }

    protected void setEncoding(PdfObject pdfObject, PdfObject pdfFontDescriptor) {


        //handle to unicode mapping
        PdfObject ToUnicode=pdfObject.getDictionary(PdfDictionary.ToUnicode);

        if(ToUnicode!=null)
            unicodeMappings=new UnicodeReader(currentPdfFile.readStream(ToUnicode,true,true,false, false,false, ToUnicode.getCacheName(currentPdfFile.getObjectReader()))).readUnicode();

        //handle encoding
        PdfObject Encoding=pdfObject.getDictionary(PdfDictionary.Encoding);

        if (Encoding != null)
            handleFontEncoding(pdfObject,Encoding);
        else
            handleNoEncoding(0,pdfObject);

        if(pdfFontDescriptor!=null){

            int fontFlag = 0;
            if(pdfFontDescriptor!=null)
                fontFlag =pdfFontDescriptor.getInt(PdfDictionary.Flags);

            //reset to defaults
            glyphs.remapFont=false;

            int flag= fontFlag;
            if((flag & 4)==4)
                glyphs.remapFont=true;

            //set missingWidth
            missingWidth=pdfFontDescriptor.getInt(PdfDictionary.MissingWidth);

        }
    }

    protected void setBoundsAndMatrix(PdfObject pdfFontDescriptor) {
        /**
         * get any dimensions if present
         */
        if(pdfFontDescriptor!=null){
            double[] newFontmatrix=pdfFontDescriptor.getDoubleArray(PdfDictionary.FontMatrix);
            if(newFontmatrix!=null)
                FontMatrix=newFontmatrix;

            float[] newFontBBox=pdfFontDescriptor.getFloatArray(PdfDictionary.FontBBox);
            if(newFontBBox!=null)
                FontBBox=newFontBBox;

            //set ascent and descent
            float value=pdfFontDescriptor.getFloatNumber(PdfDictionary.Ascent);
            if(value!=0)
                ascent=value;

            value=pdfFontDescriptor.getFloatNumber(PdfDictionary.Descent);
            if(value!=0)
                descent=value;



        }
    }


    protected void init(String fontID, boolean renderPage) {

        if (renderPage && PdfJavaGlyphs.fontList == null) {

            //synchronized (this.getClass()) {

            //Recheck
            if (renderPage && PdfJavaGlyphs.fontList == null) {

                //Make sure lowercase
                String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
                for (int i = 0; i < fontList.length; i++) {
                    fontList[i] = fontList[i].toLowerCase();
                }

                PdfJavaGlyphs.fontList = fontList;
            }
            //}
        }

        this.fontID = fontID;
        this.renderPage = renderPage;
    }

    /**
     *
     */
    private int  handleNoEncoding(int encValue, PdfObject pdfObject) {

        int enc=pdfObject.getGeneralType(PdfDictionary.Encoding);

        if(enc==StandardFonts.ZAPF){
            putFontEncoding(StandardFonts.ZAPF);
            glyphs.defaultFont="Zapf Dingbats"; //replace with single default
            StandardFonts.checkLoaded(StandardFonts.ZAPF);

            encValue=StandardFonts.ZAPF;

        }else if(enc==StandardFonts.SYMBOL){
            putFontEncoding(StandardFonts.SYMBOL);
            encValue=StandardFonts.SYMBOL;
        }else
            putFontEncoding(StandardFonts.STD); //default to standard

        hasEncoding=false;

        return encValue;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * handle font encoding and store information
     */
    private void handleFontEncoding(PdfObject pdfObject, PdfObject Encoding){

        int subType=pdfObject.getParameterConstant(PdfDictionary.Subtype);

        int encValue =getFontEncoding( false);
        if (encValue == -1) {
            if (subType == StandardFonts.TRUETYPE)
                encValue = StandardFonts.MAC;
            else
                encValue = StandardFonts.STD;
        }


        /**
         * handle differences from main encoding
         */
        PdfArrayIterator Diffs=Encoding.getMixedArray(PdfDictionary.Differences);
        if (Diffs != null && Diffs.getTokenCount()>0) {

            glyphs.setIsSubsetted(true);

            //needed to workout if values hex of base10
            //as we have examples with both

            //guess if hex or base10 by looking for numbers
            //if it has a number it must be base10
            byte[][] rawData=null;

            if(Encoding!=null)
                rawData=Encoding.getByteArray(PdfDictionary.Differences);


            if(rawData!=null){

                containsHexNumbers=true;
                allNumbers=true;
                for (byte[] aRawData : rawData) {

                    if (aRawData != null && aRawData[0] == '/') {

                        int length = aRawData.length;
                        byte[] data = aRawData;
                        char c, charCount = 0;

                        if (length == 3 && containsHexNumbers) {
                            for (int jj = 1; jj < 3; jj++) {

                                c = (char) data[jj];
                                if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F'))
                                    charCount++;
                            }
                        }
                        if (charCount != 2) {
                            containsHexNumbers = false;
                            //System.out.println("Failed on="+new String(data)+"<");
                            //ii=rawData.length;
                        }


                        if (allNumbers) {
                            if (length < 4) {
                                for (int jj = 2; jj < length; jj++) {

                                    c = (char) data[jj];
                                    if ((c >= '0' && c <= '9')) {
                                    } else {
                                        allNumbers = false;
                                        jj = length;
                                    }
                                }
                            }
                        }

                        //exit if poss
                        //if(!containsHexNumbers){
                        //    ii=rawData.length;
                        //}
                        /**/
                    }
                }
            }


            int pointer = 0,type;
            while (Diffs.hasMoreTokens()) {

                type=Diffs.getNextValueType();

                if(type==PdfArrayIterator.TYPE_KEY_INTEGER){
                    pointer=Diffs.getNextValueAsInteger();
                }else{

                    if(type==PdfArrayIterator.TYPE_VALUE_INTEGER){

                        if(diffCharTable==null)
                            diffCharTable = new  int[maxCharCount];

                        //save so we can rempa glyph to get correct value for embedded font
                        diffCharTable[pointer]=Diffs.getNextValueAsInteger(false);

                    }

                    putMappedChar( pointer,Diffs.getNextValueAsFontChar(pointer, containsHexNumbers, allNumbers));
                    pointer++;
                }

                //allow for spurious values added which should not be there
                if(pointer==256)
                    break;
            }

            //get flag
            isHex=Diffs.hasHexChars();

            //pick up space
            int spaceChar=Diffs.getSpaceChar();
            if(spaceChar!=-1)
                this.spaceChar=spaceChar;
        }

        /**
         * setup Encoding
         */
        int EncodingType=PdfDictionary.Unknown;

        if(Encoding!=null){
            hasEncoding=true;

            //see if general value first ie /WinAnsiEncoding
            int newEncodingType=Encoding.getGeneralType(PdfDictionary.Encoding);

            //check object for value
            if(newEncodingType==PdfDictionary.Unknown){
                if(getBaseFontName().equals("ZapfDingbats"))
                    newEncodingType=StandardFonts.ZAPF;
                else
                    newEncodingType=Encoding.getParameterConstant(PdfDictionary.BaseEncoding);
            }

            if(newEncodingType!=PdfDictionary.Unknown)
                EncodingType=newEncodingType;
            else
                EncodingType=handleNoEncoding(encValue,pdfObject);

        }

        putFontEncoding(EncodingType);

    }

    /**
     * used by PDF2HTML5 where encoding different from index in TJ command and we need to remap via glyf  name
     * (ie sample_pdf_htmls/thoughtcorp/Simple Relational Contracts.pdf)
     * @param glypName
     * @return
     */
    final public int getDiffChar(String glypName){

        int value=-1;

        Integer newVal= (Integer) nonStandardMappings.get(glypName);
        if(newVal!=null){
            value=newVal;
        }

        return value;
    }

    /** Insert a new mapped char in the name mapping table */
    final protected void putMappedChar(int charInt, String mappedChar) {

        if(diffTable==null){
            diffTable = new  String[maxCharCount];
            diffLookup=new HashMap();
        }

        if(charInt>255 && maxCharCount==256){ //hack for odd file
            //System.out.println(charInt+" mappedChar="+mappedChar+"<");

            //if(1==1)
            //throw new RuntimeException("xxx");

        }else if(diffTable[charInt]==null && mappedChar!=null && !mappedChar.startsWith("glyph")){


            diffTable[charInt]= mappedChar;

            diffLookup.put(mappedChar, charInt);
        }
    }

    /**
     * return char mapped onto value in Differences or null
     * @param charInt
     * @return
     */
    public String getDiffMapping(int charInt){
        if(diffTable==null)
            return null;
        else
            return diffTable[charInt];
    }

    /** Returns the char glyph corresponding to the specified code for the specified font. */
    public final String getMappedChar(int charInt,boolean remap) {

        String result =null;

        //check differences
        if(diffTable!=null)
            result =diffTable[charInt];

        if((remap)&&(result!=null)&&(result.equals(".notdef")))
            result=" ";

        //check standard encoding
        if (result == null && charInt<335)
            result =StandardFonts.getUnicodeChar(getFontEncoding( true) , charInt);

        //all unused win values over 40 map to bullet
        if(result==null &&  charInt>40 && getFontEncoding(true)==StandardFonts.WIN ) {
            if(charInt==173)
                result="hyphen";
            else
                result="bullet";
        }

        //check embedded stream as not in baseFont encoding
        if(isFontEmbedded && result==null){

            //diffs from embedded 1C file
            if(diffs!=null)
                result =diffs[charInt];


            //Embedded encoding (which can be different from the encoding!)
            if (result == null && charInt<335)
                result =StandardFonts.getUnicodeChar(this.embeddedEnc , charInt);

        }

        return result;
    }

    public final String getEmbeddedChar(int charInt) {

        String embeddedResult=null;

        //check embedded stream as not in baseFont encoding
        if(isFontEmbedded){

            //diffs from embedded 1C file
            if(diffs!=null)
                embeddedResult =diffs[charInt];

            //Embedded encoding (which can be different from the encoding!)
            if ((embeddedResult == null) && charInt<256)
                embeddedResult =StandardFonts.getUnicodeChar(this.embeddedEnc , charInt);

        }

        return embeddedResult;
    }

    /**
     * gets type of font (ie 3 ) so we can call type
     * specific code.
     * @return int of type
     */
    final public int getFontType() {
        return fontTypes;
    }

    /**
     * name of font used to display
     */
    public String getSubstituteFont() {
        return this.substituteFontFile;
    }

    /**
     * test if there is a valid value
     */
    public boolean isValidCodeRange(int rawInt) {
        if(CMAP==null)
            return false;
        else{
            //System.out.println(CMAP[rawInt]+"<<"+rawInt);
            return (CMAP[rawInt]!=null);
        }
    }

    /**used in generic renderer*/
    public float getGlyphWidth(String charGlyph, int rawInt, String displayValue) {

        if(this.fontTypes==StandardFonts.TRUETYPE){
            return glyphs.getTTWidth(charGlyph,rawInt, displayValue,false);

        }else{
            return 0;
        }
    }


    /**set subtype (only used by generic font*/
    public void setSubtype(int fontType) {
        this.fontTypes=fontType;

    }

    /**used by JPedal internally for font substitution*/
    public void setSubstituted(boolean value) {
        this.isFontSubstituted=value;

    }

    public PdfJavaGlyphs getGlyphData() {

        //glyphs.setHasWidths(this.hasWidths());
        glyphs.setHasWidths(true);
        return glyphs;
    }

    public Font setFont(String font, int textSize) {
        return glyphs.setFont(font,textSize);  //To change body of created methods use File | Settings | File Templates.
    }

    public boolean is1C() {
        return glyphs.is1C();  //To change body of created methods use File | Settings | File Templates.
    }

    public boolean isFontSubsetted() {
        return glyphs.isSubsetted;
    }

    public void setValuesForGlyph(int rawInt, String charGlyph, String displayValue, String embeddedChar) {
        glyphs.setValuesForGlyph(rawInt, charGlyph, displayValue, embeddedChar);

    }

    /**
     * remove unwanted chars from string name
     */
    private static String cleanupFontName(String baseFontName) {


        // baseFontName=baseFontName.toLowerCase();

        int length=baseFontName.length();

        StringBuilder cleanedName=new StringBuilder(length);
        char c;

        for(int aa=0;aa<length;aa++){
            c=baseFontName.charAt(aa);

            if(c==' ' || c=='-'){

            }else if(c=='#' && baseFontName.charAt(aa+1)=='2' && (baseFontName.charAt(aa+2)=='0' || baseFontName.charAt(aa+2)=='D')){

                //add in the hyphen
                if( baseFontName.charAt(aa+2)=='D')
                    cleanedName.append('-');

                aa=aa+2;

            }else
                cleanedName.append(c);
        }

        //if(baseFontName.indexOf("#20")!=-1)
        //System.out.println(baseFontName+"<>"+cleanedName.toString());

        return cleanedName.toString();
    }

    public int getItalicAngle() {

        return italicAngle;
    }

    /**
     * get bounding box to highlight
     * @return
     */
    public Rectangle getBoundingBox() {

        if(BBox==null){
            //if one of standard fonts, use value from afm file
            float[] standardBB=StandardFonts.getFontBounds(getFontName());

            if(standardBB==null){
                if(!isFontEmbedded) //use default as we are displaying in Lucida
                    BBox=new Rectangle(0,0,1000,1000);
                else
                    BBox=new Rectangle((int)(FontBBox[0]),(int)(FontBBox[1]),(int)(FontBBox[2]-FontBBox[0]),(int)(FontBBox[3]-FontBBox[1]));
            }else
                BBox=new Rectangle((int)(standardBB[0]),(int)(standardBB[1]),(int)(standardBB[2]-standardBB[0]),(int)(standardBB[3]-standardBB[1]));
        }

        return BBox;
    }

    public void setRawFontName(String baseFont) {
        rawFontName=baseFont;
    }

    /**
     * workout spaces (if any) to add into content for a gap
     * from user settings, space info in pdf
     */
    public static String getSpaces(
            float currentGap,
            float spaceWidth,
            float currentThreshold) {
        String space = "";

        if (spaceWidth > 0) {
            if ((currentGap > spaceWidth) && (currentThreshold<1 || currentGap > spaceWidth*currentThreshold)) {
                while (currentGap >= spaceWidth) {
                    space = ' ' + space;
                    currentGap = currentGap - spaceWidth;
                }
            } else if (currentGap > spaceWidth * currentThreshold) {
                //ensure a gap of at least space_thresh_hold
                space = space + ' ';
            }
        }


        return space;
    }

    public int getDiffChar(int index){
        if(diffCharTable==null)
            return 0;
        else
            return diffCharTable[index];
    }

    public float[] getFontBounds() {
        return this.FontBBox;
    }

    public boolean isFontVertical() {
        return isFontVertical;
    }

    /**
     * @param currentWidth
     */
    public void setCurrentWidth(float currentWidth) {
        this.currentWidth=currentWidth;
    }

    /**
     * width of last decoded glyf (used for HTML)
     */
    public float getCurrentWidth() {
        return currentWidth;
    }

    public Object getFontID() {
        return fontID;
    }

    public boolean isSingleByte() {
        return isSingleByte;
    }

    /**
     * workaround to handle issue with some odd SAP files
     * Please do not use.
     * @return
     */
    public boolean isBrokenFont()
    {
        return this.handleOddSapFontMapping;
    }

    /**
     * Used by HTML code to tell whether to use CID range remapping or not
     * @return Whether a 'ToUnicode' dictionary is provided.
     */
    public boolean hasToUnicode()
    {
        return unicodeMappings != null;
    }

    /**
     * returns width or value in widthTable (-1 if no width set)
     * idx=-1 returns default
     */
    public float getDefaultWidth(int idx) {
        if(idx==-1){
            return defaultWidth;
        }else{

            if(widthTable==null){
                return -1;
            }else{
                return widthTable[idx];
            }
        }
    }

    public float getAscent() {
        return ascent;
    }

    public float getDescent() {
        return descent;
    }

    /**
     * work out if 1 or 2 bytes for each char
     * @param firstVal
     * @param secondByte
     * @param secondByteIsEscaped
     * @return
     *
     * 0=false
     * 1=true
     * -1=unset
     */
    public int isDoubleBytes(int firstVal, int secondByte, boolean secondByteIsEscaped) {

        if(hasDoubleBytes){
            return 1;
        }else if(isFirstScan ){ //don't bother with test if we have figured it out already

            if((firstVal==secondByte && firstVal>0) || (secondByte==41 && !secondByteIsEscaped) || (getMappedChar(firstVal, true)!=null && getMappedChar(secondByte,true)!=null)){
                isDouble=0;
             }else{
                isDouble=1;
            }

            isFirstScan=false;
        }

        return isDouble;
    }

    /**
     * flag to show if double (1), single (0) or unset (-1)
     * @return
     */
    public int isDoubleBytes() {
       return isDouble;
    }
}
