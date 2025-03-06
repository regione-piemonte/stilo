package it.eng.utility.conversion;


/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/java-pdf-library-support/
 *
 * (C) Copyright 1997-2013, IDRsolutions and Contributors.
 *
 *   This file is part of JPedal
 *
     This source code is copyright IDRSolutions 2012


 *
 * ---------------
 * ConvertPagesToHiResImages.java
 * ---------------
 */

/**
 * Example to convert PDF to highest possible quality images
 * Also consider org.jpedal.examples.images.ConvertPagesToImages for faster if quality less important
 *
 * Useful blog article at http://www.jpedal.org/PDFblog/2009/07/pdf-to-image-quality/
 *
 * It can run from jar directly using the command
 *
 * java -cp libraries_needed org/jpedal/examples/images/ConvertPagesToHiResImages pdfFilepath inputValues
 *
 * where inputValues is 2 values
 *
 * First Parameter:  The full path including the name and extension of the target PDF file.
 * Second Parameter:  The output image file type. Choices are tif, jpg and png.
 *
 * See also http://www.jpedal.org/javadoc/org/jpedal/constants/JPedalSettings.html for settings to customise
 *
 * There is a list of code examples to convert images at http://www.idrsolutions.com/how-to-convert-pdf-files-to-image
 */

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class ConvertPagesToHiResImages {

    /**correct separator for OS */
    final static String separator = System.getProperty( "file.separator" );
    
    private static Logger aLogger = Logger.getLogger(ConvertPagesToHiResImages.class.getName());

    private static final int defaultDPI = 185;
    
    //only used if between 0 and 1
    //private float JPEGcompression=-1f;

    public ConvertPagesToHiResImages() {}
    
    public  List<File> convertiPdf(File pdfFile,String fileType) throws Exception {
    	String estensioneFile = FilenameUtils.getExtension(pdfFile.getName());   
    	if(estensioneFile.toLowerCase().equalsIgnoreCase("pdf") && (
    			   fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png") || 
    			   fileType.equals("tiff") || fileType.equals("tif"))
    		){
               return convertPagesToHiResImages(fileType, pdfFile);
           } else { //open the directory

//               File testDir=new File(pdfFilePath);
//
//               if(testDir.isDirectory()){
//
//                   /**
//                    * get list of files and check directory
//                    */
//
//                   String[] files = null;
//                   File inputFiles;
//
//                   /**make sure name ends with a deliminator for correct path later*/
//                   if (!pdfFile.endsWith(separator))
//                       pdfFile = pdfFile + separator;
//
//                   try {
//                       inputFiles = new File(pdfFile);
//
//                       files = inputFiles.list();
//                   } catch (Exception ee) {
//                      // LogWriter.writeLog("Exception trying to access file " + ee.getMessage());
//                   }
//
//                   /**now work through all pdf files*/
//                   for (String file : files) {
//
//                       if (file.toLowerCase().endsWith(".pdf"))
//                           return convertPagesToHiResImages(fileType, pdfFile + file);
//                   }
//               }else
//                   aLogger.error("The file to be processed has to be a pdf and the output filetype can only be jpg,png or tiff");
//               return null;
           }
    	   return null;
    }
    
    /**
     * method to convert page in high resolution image
     * @param fileType
     * @param pdfFile
     * @throws Exception
     */
    public List<File> convertPagesToHiResImages(String fileType, File pdfFile) throws Exception {
        long startTime=System.currentTimeMillis();
        List<File> output = new ArrayList<File>();
       
        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int numeroPagine = document.getNumberOfPages();
        File tempPath = pdfFile.getParentFile();
        for(int i=0; i<numeroPagine;i++){
        	File fileOut = convertPageToImage(i, pdfRenderer, tempPath, fileType);
        	output.add(fileOut);
        }
        
        document.close();
                         /**
             * allow output to multiple images with different values on each
             *
             * Note we REMOVE shapes as it is a new feature and we do not want to break existing functions
             */
   
       aLogger.debug("time="+(System.currentTimeMillis()-startTime)/1000);
        return output;

    }
    
    private File convertPageToImage(int pageNumber, PDFRenderer pdfRenderer, File tempPath, String tipoFileOut)
			throws Exception {

    	File imageTemp = File.createTempFile("tempImage", "." +tipoFileOut, tempPath);
 		   
		BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageNumber, defaultDPI, ImageType.RGB);
		ImageIOUtil.writeImage(bImage, imageTemp.getPath(), defaultDPI);

		
		return imageTemp;

	}

    /**
     * convenience method to get a page as a BufferedImage quickly
     * - for bulk conversion, use the other methods
     */
//    public static BufferedImage getHiresPage(int pageNo, int scaling, String pdfFile){
//
//        BufferedImage imageToSave = null;
//
//        final PdfDecoder decoder = new PdfDecoder(true);
//
//        try{
//            //mappings for non-embedded fonts to use
//            FontMappings.setFontReplacements();
//
//            decoder.openPdfFile(pdfFile);
//
//            PdfPageData pageData = decoder.getPdfPageData();
//            int width=scaling*pageData.getCropBoxWidth(pageNo);
//            int height=scaling*pageData.getCropBoxHeight(pageNo);
//
//            imageToSave = decoder.getPageAsImage(pageNo);
//
//        } catch (PdfException e) {
//            e.printStackTrace();
//        } finally {
//
//            decoder.closePdfFile();
//        }
//        return imageToSave;
//    }

    /**
     * actual conversion of a PDF page into an image
     * @param fileType
     * @param outputPath
     * @param decoder
     * @param prefix
     * @param isTransparent
     * @throws PdfException
     * @throws IOException
     */
//    private List<File> extractPageAsImage(String fileType, String outputPath, PdfDecoder decoder, String prefix, boolean isTransparent) throws PdfException, IOException {
//    	List<File> output = new ArrayList<File>();
//
//        //page range
//        int start=1, end=decoder.getPageCount();
//
//        //container if the user is creating a multi-image tiff
//        BufferedImage[] multiPages = new BufferedImage[1 + (end-start)];
//
//        /**
//         * set of JVM flags which allow user control on process
//         */
//
//
//        //////////////////TIFF OPTIONS/////////////////////////////////////////
//
//        String multiPageFlag=System.getProperty("org.jpedal.multipage_tiff");
//        boolean isSingleOutputFile=multiPageFlag!=null && multiPageFlag.toLowerCase().equals("true");
//
//        String tiffFlag=System.getProperty("org.jpedal.compress_tiff");
//        boolean compressTiffs = tiffFlag!=null && tiffFlag.toLowerCase().equals("true");
//
//        //////////////////JPEG OPTIONS/////////////////////////////////////////
//
//        //allow user to specify value
//        String rawJPEGComp=System.getProperty("org.jpedal.compression_jpeg");
//        if(rawJPEGComp!=null){
//            try{
//                JPEGcompression=Float.parseFloat(rawJPEGComp);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            if(JPEGcompression<0 || JPEGcompression>1)
//                throw new RuntimeException("Invalid value for JPEG compression - must be between 0 and 1");
//
//        }
//
//        String jpgFlag=System.getProperty("org.jpedal.jpeg_dpi");
//
//        ///////////////////////////////////////////////////////////////////////
//
//        for (int pageNo = start; pageNo < end+1; pageNo++) {
//
//           
//               aLogger.debug("page : " + pageNo);
//
//            /**
//             * If you are using decoder.getPageAsHiRes() after passing additional parameters into JPedal using the static method
//             * PdfDecoder.modifyJPedalParameters(), then getPageAsHiRes() wont necessarily be thread safe.  If you want to use
//             * getPageAsHiRes() and pass in additional parameters, in a thread safe mannor, please use the method
//             * getPageAsHiRes(int pageIndex, Map params) or getPageAsHiRes(int pageIndex, Map params, boolean isTransparent) and
//             * pass the additional parameters in directly to the getPageAsHiRes() method without calling PdfDecoder.modifyJPedalParameters()
//             * first.
//             *
//             * Please see org/jpedal/examples/images/ConvertPagesToImages.java.html for more details on how to use HiRes image conversion
//             */
//            BufferedImage imageToSave = decoder.getPageAsImage(pageNo);
//
//            String imageFormat = System.getProperty("org.jpedal.imageType");
//            if(imageFormat!=null){
//                if(isNumber(imageFormat)){
//                    int iFormat = Integer.parseInt(imageFormat);
//                    if(iFormat>-1 && iFormat<14){
//                        BufferedImage tempImage = new BufferedImage(imageToSave.getWidth(), imageToSave.getHeight(), iFormat);
//                        Graphics2D g = tempImage.createGraphics();
//                        g.drawImage(imageToSave, null, null);
//
//                        imageToSave = tempImage;
//                    }else{
//                        aLogger.error("Image Type is not valid. Value should be a digit between 0 - 13 based on the BufferedImage TYPE variables.");
//                    }
//                }else{
//                	aLogger.error("Image Type provided is not an Integer. Value should be a digit between 0 - 13 based on the BufferedImage TYPE variables.");
//                }
//            }
//
//            decoder.flushObjectValues(true);
//
//            //System.out.println("w="+imageToSave.getWidth()+" h="+imageToSave.getHeight());
//            //image needs to be sRGB for JPEG
//            if(fileType.equals("jpg"))
//                imageToSave = ColorSpaceConvertor.convertToRGB(imageToSave);
//
//
//            String outputFileName;
//            if(isSingleOutputFile)
//                outputFileName = outputPath+ "allPages"+prefix+ '.' + fileType;
//            else{
//                /**
//                 * create a name with zeros for if more than 9 pages appears in correct order
//                 */
//                String pageAsString=String.valueOf(pageNo);
//                String maxPageSize=String.valueOf(end);
//                int padding=maxPageSize.length()-pageAsString.length();
//                for(int ii=0;ii<padding;ii++)
//                    pageAsString='0'+pageAsString;
//
//                outputFileName = outputPath + "page" + pageAsString +prefix + '.' + fileType;
//            }
//
//                         //if just gray we can reduce memory usage by converting image to Grayscale
//
//            /**
//             * see what Colorspaces used and reduce image if appropriate
//             * (only does Gray at present)
//             *
//             * Can return null value if not sure
//             */
//            Iterator colorspacesUsed=decoder.getPageInfo(PageInfo.COLORSPACES);
//
//            int nextID;
//            boolean isGrayOnly=colorspacesUsed!=null; //assume true and disprove
//
//            while(colorspacesUsed!=null && colorspacesUsed.hasNext()){
//                nextID= (Integer) (colorspacesUsed.next());
//
//                if(nextID!= ColorSpaces.DeviceGray && nextID!=ColorSpaces.CalGray)
//                    isGrayOnly=false;
//            }
//
//            //draw onto GRAY image to reduce colour depth
//            //(converts ARGB to gray)
//            if(isGrayOnly){
//                BufferedImage image_to_save2=new BufferedImage(imageToSave.getWidth(),imageToSave.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//                image_to_save2.getGraphics().drawImage(imageToSave,0,0,null);
//                imageToSave = image_to_save2;
//            }
//
//            //put image in array if multi-images  (we save on last page in code below)
//            if(isSingleOutputFile)
//                multiPages[pageNo-start] = imageToSave;
//
//            //we save the image out here
//            if (imageToSave != null) {
//
//                /**BufferedImage does not support any dpi concept. A higher dpi can be created
//                 * using JAI to convert to a higher dpi image*/
//
//                //shrink the page to 50% with graphics2D transformation
//                //- add your own parameters as needed
//                //you may want to replace null with a hints object if you
//                //want to fine tune quality.
//
//                /** example 1 biliniear scaling
//                 AffineTransform scale = new AffineTransform();
//                 scale.scale(.5, .5); //50% as a decimal
//                 AffineTransformOp scalingOp =new AffineTransformOp(scale, null);
//                 image_to_save =scalingOp.filter(image_to_save, null);
//
//                 */
//
//                if(JAIHelper.isJAIused())
//                    JAIHelper.confirmJAIOnClasspath();
//
//                if(JAIHelper.isJAIused() && fileType.startsWith("tif")){
//
//                    com.sun.media.jai.codec.TIFFEncodeParam params = new com.sun.media.jai.codec.TIFFEncodeParam();
//
//                    if(compressTiffs)
//                        params.setCompression(com.sun.media.jai.codec.TIFFEncodeParam.COMPRESSION_PACKBITS);
//
//                    if(!isSingleOutputFile){
//                        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFileName));
//
//                        javax.media.jai.JAI.create("encode", imageToSave, os, "TIFF", params);
//                    }else if(isSingleOutputFile && pageNo == end){
//                        OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFileName));
//                        com.sun.media.jai.codec.ImageEncoder encoder = com.sun.media.jai.codec.ImageCodec.createImageEncoder("TIFF", out, params);
//                        List vector = new ArrayList();
//                        vector.addAll(Arrays.asList(multiPages).subList(1, multiPages.length));
//
//                        params.setExtraImages(vector.iterator());
//
//                        encoder.encode(multiPages[0]);
//                        out.close();
//                    }
//                }else if(isSingleOutputFile){
//                    //non-JAI
//                } else if ((jpgFlag != null || rawJPEGComp!=null) && fileType.startsWith("jp") && JAIHelper.isJAIused()) {
//
//                    saveAsJPEG(jpgFlag, imageToSave, JPEGcompression, new BufferedOutputStream(new FileOutputStream(outputFileName)));
//                } else {
//
//                    BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(new File(outputFileName)));
//                    ImageIO.write(imageToSave, fileType, bos);
//                    bos.flush();
//                    bos.close();
//                }
//                //if you just want to save the image, use something like
//                //javax.imageio.ImageIO.write((java.awt.image.RenderedImage)image_to_save,"png",new java.io.FileOutputStream(output_dir + page + image_name+".png"));
//
//            }
//
//            imageToSave.flush();
//
//          
//              aLogger.debug("Created : " + outputFileName);
//              output.add(new File(outputFileName));
//        }
//		return output;
//    }

    /**test to see if string or number*/
    /*private static boolean isNumber(String value) {

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
    }*/

//    private static void saveAsJPEG(String jpgFlag,BufferedImage image_to_save, float JPEGcompression, BufferedOutputStream fos) throws IOException {
//
//        //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
//        //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data
//
//        //old jpeg class
//        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
//        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);
//
//        // Image writer
////        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
////        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
////        imageWriter.setOutput(ios);
//
//        //and metadata
//        //IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
//
//        if (jpgFlag != null){
//
//            int dpi = 96;
//
//            try {
//                dpi = Integer.parseInt(jpgFlag);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            //old metadata
//            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//            //jpegEncodeParam.setXDensity(dpi);
//            //jpegEncodeParam.setYDensity(dpi);
//
//            //new metadata
//            //Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
//            jfif.setAttribute("Xdensity", Integer.toString(dpi));
//            jfif.setAttribute("Ydensity", Integer.toString(dpi));
//
//        }
//
//        if(JPEGcompression>=0 && JPEGcompression<=1f){
//
//            //old compression
//            //jpegEncodeParam.setQuality(JPEGcompression,false);
//
//            // new Compression
//            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
//            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
//            jpegParams.setCompressionQuality(JPEGcompression);
//
//        }
//
//        //old write and clean
//        //jpegEncoder.encode(image_to_save, jpegEncodeParam);
//
//        //new Write and clean up
//        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
//        ios.close();
//        imageWriter.dispose();
//
//    }
}
