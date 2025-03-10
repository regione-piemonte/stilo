package it.eng.hybrid.module.jpedal.parser;

import it.eng.hybrid.module.jpedal.io.ErrorTracker;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.GraphicsState;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;
import it.eng.hybrid.module.jpedal.util.Options;
import it.eng.hybrid.module.jpedal.viewer.ValueTypes;

import java.awt.*;

/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 * <p/>
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2011, IDRsolutions and Contributors.
 * <p/>
 * This file is part of JPedal
 *
 *     This library is free software; you can redistribute it and/or
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


 * ---------------
 * BaseDecoder.java
 * ---------------
 */
public class BaseDecoder {

    final public static int SamplingUsed=12;
    final public static int ImageCount=14;
    final public static int ImagesProcessedFully=15;
    final public static int HasYCCKimages=16;
    final public static int Multiplier=17;
    final public static int TokenNumber=18;
    final public static int FormLevel=19;
    final public static int RenderDirectly=19;
    final public static int TextPrint=20;
    final public static int GenerateGlyphOnRender=21;
    final public static int PageNumber=22;
    final public static int GroupObj=23;
    final public static int IsFlattenedForm=24;
    final public static int IsPrinting=25;
    final public static int IsImage=26;

    //flag for some routines to show image or viewer
    int imageStatus=0;
    final public static int IMAGE_getImageFromPdfObject=1;
    final public static int SCREEN_getImageFromPdfObject=2;

    CommandParser parser;

    PdfObjectCache cache;

    //save font info and generate glyph on first render
    boolean generateGlyphOnRender=false;

    boolean isPageContent;
    boolean renderPage;
    int renderMode;
    int extractionMode;


    DynamicVectorRenderer current;

    LayerDecoder layerDecoder=null;


    int textPrint=0;

    PdfObjectReader currentPdfFile;

    GraphicsState gs;

    float multiplyer = 1;

    boolean renderDirectly;

    int formLevel;

    float samplingUsed=-1;

    int tokenNumber;

    String fileName="";

     /**clip if we render directly*/
    Shape defaultClip;

    boolean imagesProcessedFully;

    /**flag to show if YCCK images*/
    boolean hasYCCKimages=false;

    int streamType= ValueTypes.UNSET;

    /**images on page*/
    int imageCount=0;

    public ErrorTracker errorTracker;

    int pageNum;

    PdfPageData pageData;


    DynamicVectorRenderer HTMLInvisibleTextHandler;

    public void setHandlerValue(int id, Object obj){
        switch(id){


            case Options.ErrorTracker:
                this.errorTracker=(ErrorTracker) obj;
                break;
        }
    }
    
    public void setPdfData(PdfPageData pageData) {
            this.pageData=pageData;
        }

    public void setRes(PdfObjectCache cache) {
        this.cache=cache;
    }


    public void setParameters(boolean isPageContent, boolean renderPage, int renderMode, int extractionMode) {

        this.isPageContent=isPageContent;
        this.renderPage=renderPage;
        this.renderMode=renderMode;
        this.extractionMode=extractionMode;
    }

    /**custom upscale val for JPedal settings*/

    public void setFloatValue(int key,float value) {

        switch(key){

            case Multiplier:

                multiplyer=value;
                break;


            case SamplingUsed:

                samplingUsed=value;
                break;
        }
    }

    public void setCommands(CommandParser parser){
        this.parser=parser;
    }

    public void setCache(PdfObjectCache cache) {
        this.cache=cache;
    }

    public int getIntValue(int key) {

        int status=0;

        switch(key){

            case ImageCount:
                status=imageCount;
                break;

        }

        return status;
    }

    public boolean getBooleanValue(int key) {

        boolean status=false;

        switch(key){

            case ImagesProcessedFully:
                status=imagesProcessedFully;
                break;

            case HasYCCKimages:
                status=hasYCCKimages;
                break;

        }

        return status;
    }

    public float getFloatValue(int key) {

        float status=0;

        switch(key){

            case SamplingUsed:
                status=samplingUsed;
                break;
        }

        return status;
    }

    public void setBooleanValue(int key, boolean value) {

        switch(key){

            case RenderDirectly:
                renderDirectly=value;
                break;
        }
    }

    public void setIntValue(int key, int value) {

        switch(key){

            case IsImage:
                imageStatus=value;
                break;

            case FormLevel:
                formLevel=value;
                break;

            case ImageCount:
                imageCount=value;
                break;

            case PageNumber:
                pageNum=value;
                break;

            /**
             * define stream as PATTERN or POSTSCRIPT or TYPE3 fonts
             */
            case ValueTypes.StreamType://static imports are not allowed in java me 1.3
                streamType=value;
                break;

            case TokenNumber:
                tokenNumber=value;
                break;
        }
    }

    public void setName(String name) {
        if(name!=null){
            this.fileName=name.toLowerCase();

            /**check no separators*/
            int sep=fileName.lastIndexOf(47); // '/'=47
            if(sep!=-1)
                fileName=fileName.substring(sep+1);
            sep=fileName.lastIndexOf(92); // '\\'=92
            if(sep!=-1)
                fileName=fileName.substring(sep+1);
            sep=fileName.lastIndexOf(46); // "."=46
            if(sep!=-1)
                fileName=fileName.substring(0,sep);
        }
    }

    public void setLayerValues(LayerDecoder layerDecoder){

        if(layerDecoder!=null)
        this.layerDecoder=layerDecoder;

    }

    public void setGS(GraphicsState gs) {
        this.gs=gs;
    }

    public void setFileHandler(PdfObjectReader currentPdfFile){
        this.currentPdfFile=currentPdfFile;
    }

     public void setRenderer(DynamicVectorRenderer current) {
       this.current =current;
    }

    public Object getObjectValue(int key){

        Object  obj=null;

        switch(key){

            case ValueTypes.HTMLInvisibleTextHandler:
                obj=HTMLInvisibleTextHandler;
                break;
        }

        return obj;
    }

        public void setObjectValue(int key, Object  obj){

          switch(key){


              case ValueTypes.HTMLInvisibleTextHandler:
                  HTMLInvisibleTextHandler=(DynamicVectorRenderer)obj;
                  break;



          }
      }


}
