/**
 *
 * This example opens a pdf file and extracts the JavaFX version of each page
 */

/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2011, IDRsolutions and Contributors.
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
  * ExtractPagesAsJavaFX.java
  * ---------------
 */
package org.jpedal.examples.javafx;

import org.jpedal.PdfDecoder;
import org.jpedal.render.output.GenericFontMapper;
import org.jpedal.external.Options;
import org.jpedal.fonts.FontMappings;
import org.jpedal.io.ObjectStore;
import org.jpedal.render.DynamicVectorRenderer;
import org.jpedal.render.output.OutputDisplay;
import org.jpedal.render.output.javafx.*;
import org.jpedal.utils.LogWriter;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.*;

public class ExtractPagesAsJavaFX {

    /**output where we put files*/
    private String user_dir = System.getProperty("user.dir");

    /**flag to show if we print messages*/
    public static boolean outputMessages = false;

    String output_dir=null;

    /**correct separator for OS */
    String separator = System.getProperty("file.separator");

    /**the decoder object which decodes the pdf and returns a data object*/
    PdfDecoder decode_pdf = null;

    /**flag to show if using images at highest quality -switch on with command line flag Dhires*/
    private boolean useHiresImage=false;

    /**sample file which can be setup - substitute your own.
     * If a directory is given, all the files in the directory will be processed*/
    private String test_file = "/mnt/shared/sample_pdfs/general/World Factbook.pdf";

    /**used as part of test to limit pages to first 10*/
    public static boolean isTest=false;

    /**file password or null*/
    private String password="";


    //alt name for first page (ie index)
    private String firstPageName = null;


    /** Output a XML file contain the foonts in this extraction for user to edit **/
    private static boolean createTemplate = false;
    private static String saveTemplateFileName = "/Users/markee/Desktop/test.xml";

    private static boolean loadTemplate = false;
    private static String loadTemplateFileName;

    //Alternate between JavaFX and FXML
    private boolean outputAsFXML=false;

    int end,page;

    /**used by IDEs to exit on request*/
    private boolean exitRequested;

    /**
     * constructor to provide same functionality as main method
     *
     */
    public ExtractPagesAsJavaFX() {

        init();
    }

    /**
     * constructor to provide same functionality as main method
     *
     */
    public ExtractPagesAsJavaFX(String[] args) {

        init();

        //read all values passed in by user and setup
        String file_name = setParams(args);

        //check file exists
        File pdf_file = new File(file_name);

        //if file exists, open and get number of pages
        if (!pdf_file.exists()) {
            System.out.println("File " + pdf_file + " not found");
            System.out.println("May need full path");

            return;
        }
        
        /**
         * allow user to set a JVM flag to enable JavaFx or FXML
         */
        if(System.getProperty("org.jpedal.pdf2javafx.outputAsFXML")!=null && System.getProperty("org.jpedal.pdf2javafx.outputAsFXML").toLowerCase().equals("true"))
            outputAsFXML=true;

//		System.out.println("testing file="+file_name);
        extraction(file_name, output_dir);
    }

    private static void init() {

        loadTemplateFileName = System.getProperty("org.jpedal.loadXML");
        if(loadTemplateFileName != null && (new File(loadTemplateFileName).exists()))  {
            loadTemplate = true;
        }
        else {
            loadTemplate = false;
        }

        saveTemplateFileName = System.getProperty("org.jpedal.saveXML");
        if(saveTemplateFileName != null) {
            createTemplate = true;
            System.out.println("XXXXXCVBNM<>");
        }
        else {
            createTemplate = false;
        }
    }

    public void extraction(String file_name, String output_dir) {

        this.output_dir=output_dir;
        //check output dir has separator
        if (user_dir.endsWith(separator) == false)
            user_dir = user_dir + separator;

        //System.out.println("output_dir: " + output_dir);


        /**
         * allow user to set a JVM flag to enable first name page
         * (null if not set)
         */
        firstPageName =System.getProperty("org.jpedal.pdf2javafx.firstPageName");


        /**
         * if file name ends pdf, do the file otherwise
         * do every pdf file in the directory. We already know file or
         * directory exists so no need to check that, but we do need to
         * check its a directory
         */
        if (file_name.toLowerCase().endsWith(".pdf")) {

            decodeFile(file_name,output_dir);
        } else {

            /**
             * get list of files and check directory
             */

            String[] files = null;
            File inputFiles;

            /**make sure name ends with a deliminator for correct path later*/
            if (!file_name.endsWith(separator))
                file_name = file_name + separator;

            try {
                inputFiles = new File(file_name);

                if (!inputFiles.isDirectory()) {
                    System.err.println(
                            file_name + " is not a directory. Exiting program");

                }else
                    files = inputFiles.list();
            } catch (Exception ee) {
                LogWriter.writeLog(
                        "Exception trying to access file " + ee.getMessage());

            }

            if(files!=null){
                /**now work through all pdf files*/
                for (String file : files) {

                    if (file.toLowerCase().endsWith(".pdf") && !file.startsWith(".")) {
                        if (outputMessages)
                            System.out.println(file_name + file);

                        decodeFile(file_name + file, output_dir);

                    }
                }
            }
        }

        /**tell user*/
        if(outputMessages)
            System.out.println("JavaFX created");
    }

    /**
     * routine to decode a file
     */
    private void decodeFile(String file_name,String output_dir) {

        /**get just the name of the file without
         * the path to use as a sub-directory
         */

        String name = "demo"; //set a default just in case

        int pointer = file_name.lastIndexOf(separator);

        if(pointer==-1)
            pointer = file_name.lastIndexOf('/');

        if (pointer != -1){
            name = file_name.substring(pointer + 1, file_name.length() - 4);
        }else if((!ExtractPagesAsJavaFX.isTest)&&(file_name.toLowerCase().endsWith(".pdf"))){
            name=file_name.substring(0,file_name.length()-4);
        }

        name = getStrippedText(name); // changes the name to a java safe name

        //PdfDecoder returns a PdfException if there is a problem
        try {
            decode_pdf = new PdfDecoder(true);

            /**
             * font mappings
             */
            if(!isTest){

                //mappings for non-embedded fonts to use
                FontMappings.setFontReplacements();

            }


            /**
             * open the file (and read metadata including pages in  file)
             */
            if(password!=null)
                decode_pdf.openPdfFile(file_name,password);
            else
                decode_pdf.openPdfFile(file_name);

        } catch (Exception e) {

            System.err.println("8.Exception " + e + " in pdf code in "+file_name);
        }

        /**
         * extract data from pdf (if allowed).
         */

        if(decode_pdf.isEncrypted() && !decode_pdf.isFileViewable()){
            //exit with error if not test
            if(!isTest)
                throw new RuntimeException("Wrong password password used=>"+password+ '<');
        }else if ((decode_pdf.isEncrypted()&&(!decode_pdf.isPasswordSupplied()))
                && (!decode_pdf.isExtractionAllowed())) {
            throw new RuntimeException("Extraction not allowed");
        } else {

        	if (!outputAsFXML) {
        		//Added the name of the file to the output path so that a folder containing all the elements for the pdf is created.
        		extractPageAsJavaFX(file_name, output_dir+"FX"+name+separator, "FX"+name);
        	} else {
        		extractPageAsJavaFX(file_name, output_dir+"FXML"+name+separator, "FXML"+name);
        	}

        }

        /**close the pdf file*/
        decode_pdf.closePdfFile();

    }

    public int getPageCount(){
        return end;
    }

    public int getPageReached(){
        return page;
    }

    private void extractPageAsJavaFX(String file_name, String output_dir, String name) {

        //create a directory if it doesn't exist
        if(output_dir!=null){
            File output_path = new File(output_dir);
            if (!output_path.exists())
                output_path.mkdirs();
        }

        //page range
        int start = 1;
        end = decode_pdf.getPageCount();

        //limit to 1st ten pages in testing
        if(end>10 && isTest)
            end=10;

        /**
         * extract data from pdf and then write out the pages as javaFX
         */

        if (outputMessages)
            System.out.println("JavaFX file will be in  " + output_dir);

        try {

            GenericFontMapper.setXMLTemplate(createTemplate);

            if(loadTemplate)
            	GenericFontMapper.loadCustomFontMappings(new FileInputStream(new File(loadTemplateFileName)));

            //add the icons to the directory
            File iconDir=new File(output_dir+"/icons");
            if(!iconDir.exists())
                iconDir.mkdirs();

            /**
             * copy all images
             */
            String[]  images=new String[]{"smstart.gif","smback.gif","smfback.gif","smforward.gif","smfforward.gif","smend.gif","logo.gif"};

            for (String image : images) {

                //data for each file in turn
                InputStream is = getClass().getResourceAsStream("/org/jpedal/examples/javafx/icons/" + image);
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(iconDir + separator + image));
                byte[] buffer = new byte[65536];//Not sure about this line
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.close();
                is.close();
            }


            for (page = start; page < end + 1; page++) { //read pages


                /**
                 * create a name with zeros for if more than 9 pages appears in correct order
                 */
                String pageAsString=String.valueOf(page);

                if(firstPageName !=null && page==start){
                    pageAsString= firstPageName;
                }else{
                    String maxPageSize=String.valueOf(end);
                    int padding=maxPageSize.length()-pageAsString.length();
                    for(int ii=0;ii<padding;ii++)
                        pageAsString='0'+pageAsString;
                }

                if (outputMessages)
                    System.out.println("Page " + pageAsString);


//				String outputName =name +"page" + pageAsString; /*//use to debug multiple documents
//				System.out.println("========================================================"+outputName);

                int cropX = decode_pdf.getPdfPageData().getCropBoxX(page);
                int cropY = decode_pdf.getPdfPageData().getCropBoxY(page);
                int cropW = decode_pdf.getPdfPageData().getCropBoxWidth(page);
                int cropH = decode_pdf.getPdfPageData().getCropBoxHeight(page);

                //Create Rectangle object to match width and height of cropbox
                Rectangle cropBox = new Rectangle(0, 0, cropW, cropH);
                //Find middle of cropbox in Pdf Coordinates
                Point2D midPoint = new Point2D.Double((cropW / 2) + cropX, (cropH / 2) + cropY);

                DynamicVectorRenderer JavaFXOutput;

                //our debug code to add FXML - not live yet
                //we will need FXMLDisplay and FXMLShape
                if(outputAsFXML){
                    JavaFXOutput =new FXMLDisplay(page, midPoint, cropBox ,false, 100, new ObjectStore());
                }else
                    JavaFXOutput =new JavaFXDisplay(page, midPoint, cropBox ,false, 100, new ObjectStore());

                //have a scaling factor so we can alter the page size
                float scaling=1.0f;

                /**
                 * if you want to fit it to a certain size, use this code
                 * work out max possible scaling for both width and height
                 * and use smaller to get max possible size but retain aspect ratio
                 * - will not always be exact match as preserves aspect ratio
                 *
                 float preferredWidth=1000,preferredHeight=1000;

                 float scalingX=preferredWidth/cropW; // scaling we need to scale w up to our value
                 float scalingY=preferredHeight/cropH; // scaling we need to scale w up to our value

                 if(scalingX>scalingY)
                 scaling=scalingY;
                 else
                 scaling=scalingX;
                 /**/

                JavaFXOutput.setValue(OutputDisplay.PercentageScaling, (int) (scaling*100)); //set page scaling (default is 100%)
                JavaFXOutput.writeCustom(OutputDisplay.PAGEDATA,decode_pdf.getPdfPageData()); //pass in PageData object so we c
                JavaFXOutput.setValue(OutputDisplay.MaxNumberOfDecimalPlaces, 0);   //let use select max number of decimal places
                JavaFXOutput.setOutputDir(output_dir, name, pageAsString); //root for output

                decode_pdf.addExternalHandler(JavaFXOutput, Options.CustomOutput); //custom object to draw PDF


                //Set page range - Start and end of page decode
                JavaFXOutput.setValue(JavaFXDisplay.StartOfDecode, start);
                JavaFXOutput.setValue(JavaFXDisplay.EndOfDecode, end);

                /**
                 * This allows the user to have a nav bar on page
                 */
                JavaFXOutput.setBooleanValue(OutputDisplay.AddNavBar, true);
                
                /**
                 * include irregular curved clips. (As used in SVG & HTML)
                 */
                JavaFXOutput.setBooleanValue(OutputDisplay.IncludeClip, true);

                /**
                 * useful config options
                 */
                //JavaFXOutput.writeCustom(OutputDisplay.SET_ENCODING_USED, new String[]{"UTF-16","utf-16"}); //java/output string value

                /**
                 * get the current page as JavaFX
                 */
                decode_pdf.decodePage(page);

                //flush images in case we do more than 1 page so only contains
                //images from current page
                decode_pdf.flushObjectValues(true);
                //flush any text data read

                if(exitRequested){
                    end=page;
                }
            }
        } catch (Exception e) {

            decode_pdf.closePdfFile();
            throw new RuntimeException("Exception " + e.getMessage()+" with thumbnails on File="+file_name);
        }

        if(createTemplate) {
        	GenericFontMapper.createXMLTemplate(saveTemplateFileName);
        }

    }
    //////////////////////////////////////////////////////////////////////////
    /**
     * main routine which checks for any files passed and runs the demo
     *
     */
    public static void main(String[] args) {


        if(outputMessages)
            System.out.println("Simple demo to extract JavaFX version of a page");

            new ExtractPagesAsJavaFX(args);
    }

    private String setParams(String[] args) {
        //set to default
        String file_name = test_file;

        //check user has passed us a filename and use default if none
        int len=args.length;
        if (len == 0){
            showCommandLineValues();
        }else if(len == 1){
            file_name = args[0];
        }else if(len <6){

            //input
            file_name = args[0];

            for(int j=1;j<args.length;j++){
                String value=args[j];

                //assume password if no / or \
                if(value.endsWith("/") || value.endsWith("\\"))
                    output_dir=value;
                else
                    password=value;

            }
        }
        return file_name;
    }

    private static void showCommandLineValues() {

        System.out.println("Example takes 2 or 3 parameters");
        System.out.println("Value 1 is the file name or directory of PDF files to process");
        System.out.println("Value 2 is the pass to write out JavaFX and directories and must end with / or \\ character)");
        System.out.println("Value 3 (optional) password for PDF file");

        System.exit(0);
    }

    /**test to see if string or number*/
    private static boolean isNumber(String value) {

        //assume true and see if proved wrong
        boolean isNumber=true;

        int charCount=value.length();
        for(int i=0;i<charCount;i++){
            char c=value.charAt(i);
            if((c<'0')|(c>'9')){
                isNumber=false;
                i=charCount;
            }
        }

        return isNumber;
    }

    /**
     * @return Returns the output_dir.
     */
    public String getOutputDir() {
        return output_dir;
    }

    /**
     * used by IDEs to exit before end of file if requested
     */
    public void stopConversion(){
        exitRequested=true;
    }

    /**
     * Returns the stripped out, java coding friendly, version of input
     * @param input
     * @return
     */
    private static String getStrippedText(String input) {

        String output = "";
        char illegalCharacters[] = {'<', '>', '\\', ':', ';', '*', '^', '@', '?', '=', '[', ']', '`'  };
        char minVal = 48; // 0
        char maxVal = 122; // z
        for(int i = 0; i < input.length(); i ++) {

            if(input.charAt(i) < minVal || input.charAt(i) > maxVal) {
                continue;
            }

            boolean foundIllegal = false;
            for (char illegalCharacter : illegalCharacters) {
                if (input.charAt(i) == illegalCharacter) {
                    foundIllegal = true;
                    break;
                }
            }
            if(foundIllegal) {
                continue;
            }

            output += input.charAt(i);
        }

        return output;
    }

}
