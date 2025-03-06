/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2012, IDRsolutions and Contributors.
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

  * SwingPainter.java
  * ---------------
  * (C) Copyright 2012, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.io;

import it.eng.hybrid.module.jpedal.acroforms.AcroRenderer;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.external.ExternalHandlers;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.parser.DecoderOptions;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;
import it.eng.hybrid.module.jpedal.render.SwingDisplay;
import it.eng.hybrid.module.jpedal.render.TextLines;
import it.eng.hybrid.module.jpedal.ui.Display;
import it.eng.hybrid.module.jpedal.ui.DisplayOffsets;
import it.eng.hybrid.module.jpedal.util.ScalingFactory;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * handle drawing of content in Swing panel for screen display
 */
class SwingPainter {

    protected int lastFormPage=-1,lastStart=-1,lastEnd=-1;

    /**allows user to create viewport on page and scale to this*/
    public Rectangle viewableArea=null;

    private int pageUsedForTransform;

    /**tracks indent so changing to continuous does not disturb display*/
    private int lastIndent=-1;

    /**rectangle drawn on screen by user*/
    private Rectangle cursorBoxOnScreen = null;
    private Rectangle lastCursorBoxOnScreen=null;

    /**colour of highlighted rectangle*/
    private Color outlineColor;

    private AffineTransform cursorAf;

    private double indent=0;

    /**
     * used to apply the imageable area to the displayscaling, used instead of
     * displayScaling, as to preserve displayScaling
     */
    private AffineTransform viewScaling=null;
    private PdfDecoder pdfDecoder;

    DisplayOffsets displayOffsets;

    DecoderOptions options=new DecoderOptions();

    public SwingPainter(PdfDecoder pdfDecoder,DecoderOptions options) {
        this.pdfDecoder = pdfDecoder;
        this.options=options;

        displayOffsets=options.getDisplayOffsets();
    }

    public void paintPage(Graphics2D g2, PdfDecoder pdfDecoder, Display pages, PdfPageData pageData, int pageNumber,
                          DynamicVectorRenderer currentDisplay, int displayView, int displayRotation, int insetW, int insetH,
                          TextLines textLines, Border myBorder, PageOffsets currentOffset, ExternalHandlers externalHandlers){

        AcroRenderer formRenderer=pdfDecoder.getFormRenderer();
        Rectangle visibleRect=pdfDecoder.getVisibleRect();
        int pageCount=pdfDecoder.getPageCount();
        float scaling=pdfDecoder.getScaling();

        pages.init(scaling, pageCount, displayRotation, pageNumber, currentDisplay, false, pageData, insetW, insetH);

        //remember so we can put it back
        final AffineTransform rawAf=g2.getTransform();

        //include any user trnaslation
        g2.translate(displayOffsets.getUserOffsetX(), displayOffsets.getUserOffsetY());

        //track all changes
        int start=pageNumber,end=pageNumber;

        //control if we display forms on multiple pages
        if(displayView!=Display.SINGLE_PAGE && displayView!=Display.PAGEFLOW){
            start= pages.getStartPage();
            end= pages.getEndPage();
            if(start==0 || end==0 || lastEnd!=end || lastStart!=start)
                lastFormPage=-1;

            lastEnd=end;
            lastStart=start;

        }

        if(lastFormPage!=pageNumber){

            if (formRenderer != null){
                formRenderer.displayComponentsOnscreen(start,end);

                //switch off if forms for this page found
                if(formRenderer.getCompData().getStartComponentCountForPage(pageNumber)!=-1)
                    lastFormPage=pageNumber; //ensure not called too early
            }
        }

        //center if required
        if(pdfDecoder.alignment==Display.DISPLAY_CENTERED){
            double width=pdfDecoder.getBounds().getWidth();

            if(displayView==Display.PAGEFLOW)
                width=visibleRect.getWidth();

            int pdfWidth=pdfDecoder.getPDFWidth();
            if(displayView!=Display.SINGLE_PAGE)
                pdfWidth=(int) pages.getPageSize(displayView).getWidth();

            //we indent it here so selected page is  in middle of panel and now make rest of co-ords relative which is all much easier....
            if(displayView==Display.PAGEFLOW){

                indent=((visibleRect.width- pages.getWidthForPage(pageNumber))/2)-insetW-pdfDecoder.getBounds().x;

            } else if (displayView==Display.FACING) {

                int page = pageNumber;
                if (pages.getBoolean(Display.BoolValue.SEPARATE_COVER) && (page & 1)==1)
                    page--;
                else if (!pages.getBoolean(Display.BoolValue.SEPARATE_COVER) && (page & 1)==0)
                    page--;

                //Get widths of pages
                int firstW,secondW;
                if ((displayRotation + pageData.getRotation(page))%180==90)
                    firstW = pageData.getCropBoxHeight(page);
                else
                    firstW = pageData.getCropBoxWidth(page);

                if (page+1 > pageCount) {
                    secondW = firstW;
                } else {
                    if ((displayRotation + pageData.getRotation(page + 1))%180==90)
                        secondW = pageData.getCropBoxHeight(page + 1);
                    else
                        secondW = pageData.getCropBoxWidth(page + 1);
                }

                //set pageGap
                int pageGap = 0;
                if (!pages.getBoolean(Display.BoolValue.TURNOVER_ON) || pageData.hasMultipleSizes() || pageCount==2)
                    pageGap = PageOffsets.pageGap /2;

                //set indent
                indent = (((width - ((firstW + secondW) * scaling)) / 2) - pageGap - insetW);
            } else
                indent=((width-pdfWidth)/2);

            if(displayView==Display.SINGLE_PAGE)
                lastIndent=(int)indent;
            else if((displayView==Display.CONTINUOUS || displayView==Display.PAGEFLOW) && lastIndent!=-1){
                indent=lastIndent;
                lastIndent = -1;
            }else
                lastIndent=-1;

            g2.translate(indent,0);

            if(formRenderer!=null && currentOffset !=null){ //if all forms flattened, we can get a null value for currentOffset so avoid this case

                if(displayView==Display.PAGEFLOW)
                    indent= indent-((pageNumber-1)*(PageOffsets.getPageFlowPageWidth((int)(pageData.getCropBoxWidth(pageNumber)*scaling),scaling)));

                formRenderer.getCompData().setPageValues(scaling,displayRotation,(int)indent,displayOffsets.getUserOffsetX(), displayOffsets.getUserOffsetY(),displayView,currentOffset.widestPageNR, currentOffset.widestPageR);
                formRenderer.getCompData().resetScaledLocation(scaling,displayRotation,(int)indent);//indent here does nothing.
            }
        }else if(formRenderer!=null && currentOffset !=null){
            lastIndent=-1;
            formRenderer.getCompData().setPageValues(scaling,displayRotation,(int)indent,displayOffsets.getUserOffsetX(), displayOffsets.getUserOffsetY(),displayView, currentOffset.widestPageNR, currentOffset.widestPageR);
            formRenderer.getCompData().resetScaledLocation(scaling,displayRotation,(int)indent);
        }

        pages.initRenderer(textLines.areas, g2, myBorder, (int) indent);

        pages.drawPage(viewScaling, pdfDecoder.displayScaling, pageUsedForTransform);

        /**/
        //disabled if not in Single PAGE
             if(displayView==Display.SINGLE_PAGE){
             /**/
            /**
             * draw highlighted text boxes
             */


            // add any viewport
            if(viewScaling!=null)
                g2.transform(viewScaling);

            /**add any highlighted rectangle on screen*/
            if (cursorBoxOnScreen != null)
                cursorAf=g2.getTransform();

            pages.resetToDefaultClip();

        }

        if(displayView==Display.SINGLE_PAGE){
            drawHighlightsForImage(g2, pages.getHighlightedImage(),scaling,displayRotation, insetW, insetH, pdfDecoder.max_x, pdfDecoder.max_y);
        }else{
            pages.setHighlightedImage(null);
        }

        /**
         * draw other pages if not in SINGLE mode
         **/
        pages.drawBorder();

        //restore affine settings
        g2.setTransform(rawAf);

        //draw facing mode turnover

        //draw preview on page if set
        if(displayView == Display.SINGLE_PAGE)
            pages.drawPreviewImage(g2, visibleRect);

    }

    public void setCursorBoxOnScreen(Rectangle cursorBoxOnScreen, boolean isSamePage, AcroRenderer formRenderer) {
        this.cursorBoxOnScreen=cursorBoxOnScreen;

        if (!isSamePage && formRenderer != null){
            formRenderer.removeDisplayComponentsFromScreen();
            lastFormPage=-1; //reset so will appear on reparse
        }
    }

    public Rectangle getCursorBoxOnScreen() {
        return cursorBoxOnScreen;
    }

    public void drawCursor(Graphics g, int alignment) {

        /**add any highlighted rectangle on screen*/
        if (cursorBoxOnScreen != null){
            Graphics2D g2=(Graphics2D) g;
            AffineTransform defaultAf=g2.getTransform();

            if(cursorAf!=null){
                g2.setTransform(cursorAf);

                Shape clip=g2.getClip();

                //remove clip for drawing outline
                if(alignment==Display.DISPLAY_CENTERED && clip!=null)
                    g2.setClip(null);

                //<start-adobe>
                if(cursorBoxOnScreen!=null && PdfDecoder.showMouseBox)
                    paintRectangle(g2, cursorBoxOnScreen, pdfDecoder.scaling, outlineColor);
                lastCursorBoxOnScreen=cursorBoxOnScreen;
                //<end-adobe>

                g2.setClip(clip);

                g2.setTransform(defaultAf);

            }
        }
    }

    final public void updateCursorBoxOnScreen(Rectangle newOutlineRectangle, Color outlineColor, PdfDecoder pdfDecoder, int pageNumber) {

        PdfPageData pageData=pdfDecoder.getPdfPageData();

        //area to reapint
        int x_size=pdfDecoder.x_size;
        int y_size=pdfDecoder.y_size;

        if(newOutlineRectangle!=null){

            int x=newOutlineRectangle.x;
            int y=newOutlineRectangle.y;
            int w=newOutlineRectangle.width;
            int h=newOutlineRectangle.height;

            int cropX=pageData.getCropBoxX(pageNumber);
            int cropY=pageData.getCropBoxY(pageNumber);
            int cropW=pageData.getCropBoxWidth(pageNumber);
            int cropH=pageData.getCropBoxHeight(pageNumber);

            //allow for odd crops and correct
            if(y>0 && y<(cropY))
                y=y+cropY;

            if(x<cropX){
                int diff=cropX-x;
                w=w-diff;
                x=cropX;
            }

            if(y<cropY){
                int diff=cropY-y;
                h=h-diff;
                y=y+diff;
            }
            if((x+w)>cropW+cropX)
                w=cropX+cropW-x;
            if((y+h)>(cropY+cropH))
                h=cropY+cropH-y;

            cursorBoxOnScreen = new Rectangle(x,y,w,h);

        }else
            cursorBoxOnScreen=null;

        this.outlineColor = outlineColor;

        int strip=30;

        /**allow offset from page being centered*/
        int dx=0;
        //center if required
        if(pdfDecoder.alignment==Display.DISPLAY_CENTERED){
            int width=pdfDecoder.getBounds().width;
            int pdfWidth=pdfDecoder.getPDFWidth();

            if(pdfDecoder.displayView!=Display.SINGLE_PAGE)
                pdfWidth=(int) pdfDecoder.pages.getPageSize(pdfDecoder.displayView).getWidth();

            dx=((width-pdfWidth)/2);
        }

        /**repaint manager*/
        RepaintManager currentManager=RepaintManager.currentManager(pdfDecoder);

        if(lastCursorBoxOnScreen!=null){
            if(pdfDecoder.displayRotation==0 || pdfDecoder.displayRotation==180)
                currentManager.addDirtyRegion(pdfDecoder, pdfDecoder.insetW+dx, pdfDecoder.insetH,x_size+5,y_size);
            else
                currentManager.addDirtyRegion(pdfDecoder, pdfDecoder.insetH+dx, pdfDecoder.insetW,y_size+5,x_size);

            lastCursorBoxOnScreen=null;
        }

        if(cursorBoxOnScreen!=null){
            currentManager.addDirtyRegion(pdfDecoder,(int)(cursorBoxOnScreen.x* pdfDecoder.scaling)-strip,
                    (int)(((pdfDecoder.max_y)-cursorBoxOnScreen.y-cursorBoxOnScreen.height)* pdfDecoder.scaling)-strip,
                    (int)(cursorBoxOnScreen.width* pdfDecoder.scaling)+strip+strip,
                    (int)(cursorBoxOnScreen.height* pdfDecoder.scaling)+strip+strip);
        }

        if(viewScaling!=null)
            currentManager.markCompletelyDirty(pdfDecoder);

        //force repaint
        pdfDecoder.repaint();
    }
    //<end-adobe>

    public double getIndent() {
        return indent;
    }

    /**
     * initialise panel and set size to display during updates and update the AffineTransform to new values<br>
     */
    public void setPageRotation(int newRotation, PdfPageData pageData) {

        pdfDecoder.displayRotation = newRotation;

        //assume unrotated for multiple views and rotate on a page basis
        if(pdfDecoder.displayView!=Display.SINGLE_PAGE)
            newRotation=0;

        pageUsedForTransform= pdfDecoder.pageNumber;
        if(pdfDecoder.displayView!=Display.SINGLE_PAGE && pdfDecoder.displayView!=Display.FACING)
            pdfDecoder.displayScaling = ScalingFactory.getScalingForImage(1, 0, pdfDecoder.scaling,pageData);//(int)(pageData.getCropBoxWidth(pageNumber)*scaling),(int)(pageData.getCropBoxHeight(pageNumber)*scaling),
        else
            pdfDecoder.displayScaling = ScalingFactory.getScalingForImage(pdfDecoder.pageNumber,newRotation, pdfDecoder.scaling, pageData);//(int)(pageData.getCropBoxWidth(pageNumber)*scaling),(int)(pageData.getCropBoxHeight(pageNumber)*scaling),

        if(newRotation == 90){
            pdfDecoder.displayScaling.translate(pdfDecoder.insetH/ pdfDecoder.scaling, pdfDecoder.insetW/ pdfDecoder.scaling);
        }else if(newRotation == 270){
            pdfDecoder.displayScaling.translate(-pdfDecoder.insetH/ pdfDecoder.scaling,-pdfDecoder.insetW/ pdfDecoder.scaling);
        }else if(newRotation == 180){
            pdfDecoder.displayScaling.translate(-pdfDecoder.insetW/ pdfDecoder.scaling, pdfDecoder.insetH/(pdfDecoder.scaling));
        }else{
            pdfDecoder.displayScaling.translate(pdfDecoder.insetW/ pdfDecoder.scaling,-pdfDecoder.insetH/ pdfDecoder.scaling);
        }

        //force redraw if screen being cached
        pdfDecoder.pages.refreshDisplay();

        /**
         * now apply any viewport scaling
         */
        if(viewableArea!=null){

            viewScaling=new AffineTransform();

            /**workout scaling and choose larger*/
            double dx=(double)viewableArea.width/(double) pageData.getCropBoxWidth(pdfDecoder.pageNumber);
            double dy=(double)viewableArea.height/(double) pageData.getCropBoxHeight(pdfDecoder.pageNumber);
            double viewScale=dx;
            if(dy<dx)
                viewScale=dy;

            /**workout any translation*/
            double x=viewableArea.x;//left align
            double y=viewableArea.y+(viewableArea.height-(pageData.getCropBoxHeight(pdfDecoder.pageNumber)*viewScale));//top align

            viewScaling.translate(x,y);
            viewScaling.scale(viewScale,viewScale);

        }else
            viewScaling=null;
    }

    //<start-adobe>
    public AffineTransform setViewableArea(Rectangle viewport, PdfDecoder pdfDecoder, PdfPageData pageData) throws PdfException {
        if (viewport != null) {

            double x = viewport.getX();
            double y = viewport.getY();
            double w = viewport.getWidth();
            double h = viewport.getHeight();

            // double crx = pageData.getCropBoxX(pageNumber);
            // double cry = pageData.getCropBoxY(pageNumber);
            double crw = pageData.getCropBoxWidth(pdfDecoder.pageNumber);
            double crh = pageData.getCropBoxHeight(pdfDecoder.pageNumber);

            // throw exception if viewport cannot fit in cropbox
            if (x < 0 || y < 0 || (x + w) > crw || (y + h) > crh) {
                throw new PdfException("Viewport is not totally enclosed within displayed panel.");
            }

            // if viewport exactlly matches the cropbox
            if (crw == w && crh == h) {
            } else {// else work out scaling ang apply

                viewableArea = viewport;
                setPageRotation(pdfDecoder.displayRotation,pageData);
                pdfDecoder.repaint();
            }
        } else {
            resetViewableArea(pdfDecoder, pageData);
        }

        return viewScaling;
    }
    //<end-adobe>

    public DisplayOffsets getDisplayOffsets() {
        return displayOffsets;
    }

    public void resetViewableArea(PdfDecoder pdfDecoder, PdfPageData pageData) {

        if (viewableArea != null) {
            viewableArea = null;

            setPageRotation(pdfDecoder.displayRotation, pageData);
            pdfDecoder.repaint();
        }
    }

    public void resetMultiPageForms(int page, PdfDecoder pdfDecoder, AcroRenderer formRenderer, Display pages) {

        formRenderer.removeDisplayComponentsFromScreen();
        pdfDecoder.add(pages.getScroll(), BorderLayout.SOUTH);

        lastFormPage=-1; //reset so will appear on reparse

        formRenderer.displayComponentsOnscreen(page,page);

        //switch off if forms for this page found
        if(formRenderer.getCompData().getStartComponentCountForPage(page)!=-1)
            lastFormPage=page; //ensure not called too early
    }

    public void forceRedraw() {

        lastFormPage = -1;
        lastEnd = -1;
        lastStart = -1;
    }


    private static void drawHighlightsForImage(Graphics2D g2, int[] highlightedImage, float scaling, int displayRotation, int insetW,int insetH, int max_x,int max_y) {

        if(highlightedImage!=null){

            //All image highlight coords scaled here to allow for any scaling value

            //Varibles added to make the code more readable
            int x = (int) (highlightedImage[0] * scaling);
            int y = (int) (highlightedImage[1] * scaling);
            int w = (int) (highlightedImage[2] * scaling);
            int h = (int) (highlightedImage[3] * scaling);

            //			//Check for negative values
            if (w < 0) {
                w = -w;
                x = x - w;
            }
            if (h < 0) {
                h = -h;
                y = y - h;
            }

            //Final values to use
            int finalX;
            int finalY;
            int finalW = w;
            int finalH = h;

            //Handle Any Rotation
            if (displayRotation == 90) {

                finalH = w;
                finalW = h;

                finalX = insetW + y;
                finalY = insetH + x;

            } else if (displayRotation == 180) {

                finalX = (int) ((max_x * scaling) - (x) - w) + insetW;
                finalY = (insetH + y);

            } else if (displayRotation == 270) {

                finalH = w;
                finalW = h;

                finalY = (int) ((max_x * scaling) - (x) - w) + insetW;
                finalX = (int) ((max_y * scaling) - (y) - h) + insetH;

            } else {
                finalX = insetW + x;
                finalY = (int) ((max_y * scaling) - (y) - h) + insetH;

            }

            Color oldColor = g2.getColor();
            Composite oldComposite = g2.getComposite();
            Stroke oldStroke = g2.getStroke();

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PdfDecoder.highlightComposite));

            //draw border
            if (SwingDisplay.invertHighlight) {
                g2.setColor(Color.WHITE);
                g2.setXORMode(Color.BLACK);
            } else {
                g2.setColor(DecoderOptions.highlightColor);
                g2.drawRect(finalX, finalY, finalW, finalH);
            }

            //fill border
            g2.fillRect(finalX, finalY, finalW, finalH);
            //set back to original setup
            g2.setColor(oldColor);
            g2.setComposite(oldComposite);
            g2.setStroke(oldStroke);
        }
    }


    //<start-adobe>

    /**
     * draw cursorBox on screen with specified color,
     */
    private static void paintRectangle(Graphics2D g2,Rectangle cursorBoxOnScreen, float scaling,Color outlineColor){

        Stroke oldStroke = g2.getStroke();//copy before to stop page border from being dotted
        Stroke lineStroke;

        //allow for negative
        if(scaling<0)
            lineStroke = new BasicStroke(1/-scaling);
        else
            lineStroke = new BasicStroke(1/scaling);

        g2.setStroke(lineStroke);

        g2.setColor(outlineColor);

        //Draw opaque square around highlight area
        g2.draw(cursorBoxOnScreen);

        g2.setStroke(oldStroke);

    }

    //<end-adobe>

    public static BufferedImage getSelectedRectangleOnscreen(float t_x1, float t_y1, float t_x2, float t_y2, float scaling, int pageNumber, PdfPageData pageData, AcroRenderer formRenderer, DynamicVectorRenderer currentDisplay, PdfObjectReader currentPdfFile) {

        /** get page sizes */
        int mediaBoxH = pageData.getMediaBoxHeight(pageNumber);
        int crw = pageData.getCropBoxWidth(pageNumber);
        int crh = pageData.getCropBoxHeight(pageNumber);
        int crx = pageData.getCropBoxX(pageNumber);
        int cry = pageData.getCropBoxY(pageNumber);

        // check values for rotated pages
        if (t_y2 < cry)
            t_y2 = cry;
        if (t_x1 < crx)
            t_x1 = crx;
        if (t_y1 > (crh + cry))
            t_y1 = crh + cry;
        if (t_x2 > (crx + crw))
            t_x2 = crx + crw;

        if ((t_x2 - t_x1) < 1 || (t_y1 - t_y2) < 1)
            return null;

        float scalingFactor = scaling / 100;
        float imgWidth = t_x2 - t_x1;
        float imgHeight = t_y1 - t_y2;

        /**
         * create the image
         */
        BufferedImage img = new BufferedImage((int) (imgWidth * scalingFactor),
                (int) (imgHeight * scalingFactor), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = img.createGraphics();

        /**
         * workout the scaling
         */
        if (cry > 0)// fix for negative pages
            cry = mediaBoxH - crh - cry;

        // use 0 for rotated extraction
        AffineTransform scaleAf = ScalingFactory.getScalingForImage(pageNumber, 0, scalingFactor,pageData);
        int cx = -crx, cy = -cry;

        scaleAf.translate(cx, -cy);
        scaleAf.translate(-(t_x1 - crx), mediaBoxH - t_y1 - cry);

        AffineTransform af = g2.getTransform();

        g2.transform(scaleAf);

        currentDisplay.setG2(g2);
        currentDisplay.paintBackground(new Rectangle(crx, cry, crw, crh));

        currentDisplay.setOptimsePainting(true); //ensure drawn
        currentDisplay.paint(null, null, null);


        /**
         * draw acroform data onto Panel
         */
        if (formRenderer != null && formRenderer.hasFormsOnPage(pageNumber)) {

            //note rotation is hard-coded to zero as rotation on image is zero!
            formRenderer.getCompData().renderFormsOntoG2(g2, pageNumber, scaling, 0,
                    0, null,null, currentPdfFile, pageData.getMediaBoxHeight(pageNumber));


        }

        g2.setTransform(af);

        g2.dispose();

        return img;
    }
}
