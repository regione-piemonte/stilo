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

  * PDFtoImageConvertor.java
  * ---------------
  * (C) Copyright 2012, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.parser;

import it.eng.hybrid.module.jpedal.acroforms.AcroRenderer;
import it.eng.hybrid.module.jpedal.color.ColorSpaces;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.external.ExternalHandlers;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.objects.PdfResources;
import it.eng.hybrid.module.jpedal.objects.raw.PageObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;
import it.eng.hybrid.module.jpedal.render.ImageDisplay;
import it.eng.hybrid.module.jpedal.viewer.ValueTypes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PDFtoImageConvertor {

    //force to generate images smaller than page size
    public static Boolean allowPagesSmallerThanPageSize=Boolean.FALSE;

    //stop scaling to silly figures
    public static Integer bestQualityMaxScaling=null;

    /**custom upscale val for JPedal settings*/
    private float multiplyer = 1;

    Boolean instance_allowPagesSmallerThanPageSize;

    /**
     * used by PDF2HTML to add invisible text
     */
    DynamicVectorRenderer htmlDisplay;

    //non-static version
    private Integer instance_bestQualityMaxScaling=null;

    public PDFtoImageConvertor(float multiplyer, DecoderOptions options) {
        this.multiplyer=multiplyer;
        this.instance_allowPagesSmallerThanPageSize=options.getInstance_allowPagesSmallerThanPageSize();
        this.instance_bestQualityMaxScaling=options.getInstance_bestQualityMaxScaling();
    }


    public BufferedImage convert(DecoderResults resultsFromDecode, int displayRotation, PdfResources res, int displayView,
                                     ExternalHandlers externalHandlers, int renderMode, PdfPageData pageData,
                                     AcroRenderer formRenderer, float scaling, PdfObjectReader currentPdfFile, int pageIndex,
                                     boolean imageIsTransparent,
                                     String currentPageOffset) throws PdfException  {

        /** read page or next pages */
        PdfObject pdfObject=new PageObject(currentPageOffset);
        currentPdfFile.readObject(pdfObject);

        currentPdfFile.checkParentForResources(pdfObject);

        BufferedImage image;
        Graphics2D g2;

        PdfObject Resources=pdfObject.getDictionary(PdfDictionary.Resources);

        ObjectStore localStore = new ObjectStore();
        DynamicVectorRenderer imageDisplay = new ImageDisplay(pageIndex,false, 5000, localStore);

        if(htmlDisplay!=null){
            imageDisplay.writeCustom(ValueTypes.HTMLInvisibleTextHandler,htmlDisplay);
        }

        PdfStreamDecoder currentImageDecoder = new PdfStreamDecoder(currentPdfFile);

        if(htmlDisplay==null){
            currentImageDecoder.setParameters(true, true, renderMode,0);
        }else{ //we need text extracted in this mode for invisible text over image
            currentImageDecoder.setParameters(true, true, renderMode,PdfDecoder.TEXT);
        }

        externalHandlers.addHandlers(currentImageDecoder);

        //currentImageDecoder.setObjectValue(ValueTypes.Name, filename);
        currentImageDecoder.setObjectValue(ValueTypes.ObjectStore,localStore);
        currentImageDecoder.setFloatValue(BaseDecoder.Multiplier, multiplyer);
        currentImageDecoder.setObjectValue(ValueTypes.PDFPageData,pageData);
        currentImageDecoder.setIntValue(ValueTypes.PageNum, pageIndex);

        currentImageDecoder.setObjectValue(ValueTypes.DynamicVectorRenderer,imageDisplay);

        res.setupResources(currentImageDecoder, true, Resources,pageIndex,currentPdfFile);

        //can for max
        if(multiplyer==-2){

            multiplyer=-1;
            currentImageDecoder.setFloatValue(BaseDecoder.Multiplier, multiplyer);

            PdfStreamDecoderForSampling currentImageDecoder2 = new PdfStreamDecoderForSampling(currentPdfFile);
            currentImageDecoder2.setParameters(true, true, renderMode,0);

            //currentImageDecoder2.setObjectValue(ValueTypes.Name, filename);
            currentImageDecoder2.setObjectValue(ValueTypes.ObjectStore,localStore);
            currentImageDecoder2.setFloatValue(BaseDecoder.Multiplier, multiplyer);
            currentImageDecoder2.setObjectValue(ValueTypes.PDFPageData,pageData);
            currentImageDecoder2.setIntValue(ValueTypes.PageNum, pageIndex);
            currentImageDecoder2.setObjectValue(ValueTypes.DynamicVectorRenderer,imageDisplay);

            res.setupResources(currentImageDecoder2, true, Resources,pageIndex,currentPdfFile);

            /** bare minimum to get value*/
            multiplyer=currentImageDecoder2.decodePageContentForImageSampling(pdfObject);

            int bestQualityMaxScalingToUse = 0;
            if(instance_bestQualityMaxScaling != null)
                bestQualityMaxScalingToUse = instance_bestQualityMaxScaling;
            else if (bestQualityMaxScaling != null)
                bestQualityMaxScalingToUse = bestQualityMaxScaling;

            if (bestQualityMaxScalingToUse > 0 && multiplyer > bestQualityMaxScalingToUse) {
                multiplyer = bestQualityMaxScalingToUse;
            }

            currentImageDecoder2.setFloatValue(BaseDecoder.Multiplier, multiplyer);
            currentImageDecoder.setFloatValue(BaseDecoder.Multiplier, multiplyer);
        }

        if(!allowPagesSmallerThanPageSize &&
                !instance_allowPagesSmallerThanPageSize &&
                multiplyer<1 && multiplyer>0)
            multiplyer=1;

        //allow for value not set
        if(multiplyer==-1)
            multiplyer=1;

        /**
         * setup transformations and image
         */


        AffineTransform imageScaling = setPageParametersForImage(scaling*multiplyer, pageIndex,pageData);

        //include scaling in size
        int mediaH = (int) (scaling*pageData.getMediaBoxHeight(pageIndex));
        int rotation = pageData.getRotation(pageIndex);

        int crw = (int) (scaling*pageData.getCropBoxWidth(pageIndex));
        int crh = (int) (scaling*pageData.getCropBoxHeight(pageIndex));
        int crx = (int) (scaling*pageData.getCropBoxX(pageIndex));
        int cry = (int) (scaling*pageData.getCropBoxY(pageIndex));

        boolean rotated = false;
        int w, h;
        if ((rotation == 90) || (rotation == 270)) {
            h = (int) (crw*multiplyer); // * scaling);
            w = (int) (crh*multiplyer); // * scaling);
            rotated = true;
        } else {
            w = (int) (crw*multiplyer); // * scaling);
            h = (int) (crh*multiplyer); // * scaling);
        }

        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = image.getGraphics();

        g2 = (Graphics2D) graphics;

        if (!imageIsTransparent) {
            g2.setColor(Color.white);
            g2.fillRect(0, 0, w, h);
        }

        /**
         * adjustment for upside down images
         */
        if(rotation==180){
            g2.translate(crx*2*multiplyer, -(cry*2*multiplyer));
        }

        /**
         * pass in values as needed for patterns
         */
        ((DynamicVectorRenderer)currentImageDecoder.getObjectValue(ValueTypes.DynamicVectorRenderer)).setScalingValues(crx*multiplyer, (crh*multiplyer) + cry, multiplyer*scaling);


        g2.setRenderingHints(ColorSpaces.hints);
        g2.transform(imageScaling);

        if (rotated){

            if(rotation==90){//90

                if(multiplyer<1){
                    cry = (int)(imageScaling.getTranslateX() + cry);
                    crx = (int)(imageScaling.getTranslateY() + crx);

                }else{
                    cry = (int)((imageScaling.getTranslateX()/multiplyer) + cry);
                    crx = (int)((imageScaling.getTranslateY()/multiplyer) + crx);
                }
                g2.translate(-crx, -cry);

            }else{ //270
                if(cry<0)
                    g2.translate(-crx, mediaH-crh+cry);
                else
                    g2.translate(-crx,mediaH-crh-cry);
            }
        }

        /** decode and print in 1 go */
        currentImageDecoder.setObjectValue(ValueTypes.DirectRendering, g2);//(Graphics2D) graphics);
        imageDisplay.setG2(g2);
        if (pdfObject != null){

            /**
             * pass in HTML object so we can add in invisible text
             */
            currentImageDecoder.setObjectValue(ValueTypes.HTMLInvisibleTextHandler, htmlDisplay);

            currentImageDecoder.decodePageContent(pdfObject);
        }

        g2.setClip(null);


        resultsFromDecode.update(currentImageDecoder, false);

        /**
         * draw acroform data onto Panel
         */
        if (formRenderer != null && formRenderer.hasFormsOnPage(pageIndex) && !formRenderer.ignoreForms()) {

            resultsFromDecode.resetColorSpaces();

            formRenderer.createDisplayComponentsForPage(pageIndex,currentImageDecoder);

            formRenderer.getCompData().renderFormsOntoG2(g2,pageIndex, scaling, 0, displayRotation,
                    null, null,currentPdfFile, pageData.getMediaBoxHeight(pageIndex));

        }


        localStore.flush();
        return image;
    }

    public float getMultiplyer() {
        
        return multiplyer;
        
    }

    private static AffineTransform setPageParametersForImage(float scaling, int pageNumber, PdfPageData pageData) {

        //create scaling factor to use
        AffineTransform imageScaling = new AffineTransform();

        int crw = pageData.getCropBoxWidth(pageNumber);
        int crh = pageData.getCropBoxHeight(pageNumber);
        int crx = pageData.getCropBoxX(pageNumber);
        int cry = pageData.getCropBoxY(pageNumber);


        int image_x_size =(int) ((crw)*scaling);
        int image_y_size =(int) ((crh)*scaling);

        int raw_rotation = pageData.getRotation(pageNumber);

        imageScaling.translate(-crx*scaling,+cry*scaling);

        if (raw_rotation == 270) {

            imageScaling.rotate(-Math.PI / 2.0, image_x_size/ 2, image_y_size / 2);

            double x_change = (imageScaling.getTranslateX());
            double y_change = (imageScaling.getTranslateY());
            imageScaling.translate((image_y_size - y_change), -x_change);

            imageScaling.translate(2*cry*scaling,0);
            imageScaling.translate(0,-scaling*(pageData.getCropBoxHeight(pageNumber)-pageData.getMediaBoxHeight(pageNumber)));
        } else if (raw_rotation == 180) {

            imageScaling.rotate(Math.PI, image_x_size / 2, image_y_size / 2);

        } else if (raw_rotation == 90) {

            imageScaling.rotate(Math.PI / 2.0,  image_x_size / 2,  image_y_size / 2);

            double x_change =(imageScaling.getTranslateX());
            double y_change = (imageScaling.getTranslateY());
            imageScaling.translate(-y_change, image_x_size - x_change);

        }

        if(scaling<1){
            imageScaling.translate(image_x_size, image_y_size);
            imageScaling.scale(1, -1);
            imageScaling.translate(-image_x_size, 0);

            imageScaling.scale(scaling,scaling);
        }else{
            imageScaling.translate(image_x_size, image_y_size);
            imageScaling.scale(1, -1);
            imageScaling.translate(-image_x_size, 0);

            imageScaling.scale(scaling,scaling);
        }

        return imageScaling;
    }

    public void setHTMLInvisibleTextHandler(DynamicVectorRenderer htmlDisplay) {
       this.htmlDisplay=htmlDisplay;
    }
}
