// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.print;

import org.apache.batik.transcoder.keys.StringKey;
import org.apache.batik.transcoder.keys.LengthKey;
import org.apache.batik.transcoder.keys.BooleanKey;
import java.util.StringTokenizer;
import java.io.File;
import org.apache.batik.transcoder.Transcoder;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.util.Collection;
import java.awt.Graphics;
import java.awt.print.PrinterException;
import java.awt.print.Paper;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import org.apache.batik.transcoder.TranscoderException;
import org.w3c.dom.Document;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderInput;
import java.util.ArrayList;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.bridge.BridgeContext;
import java.util.List;
import java.awt.print.Printable;
import org.apache.batik.transcoder.SVGAbstractTranscoder;

public class PrintTranscoder extends SVGAbstractTranscoder implements Printable
{
    public static final String KEY_AOI_STR = "aoi";
    public static final String KEY_HEIGHT_STR = "height";
    public static final String KEY_LANGUAGE_STR = "language";
    public static final String KEY_MARGIN_BOTTOM_STR = "marginBottom";
    public static final String KEY_MARGIN_LEFT_STR = "marginLeft";
    public static final String KEY_MARGIN_RIGHT_STR = "marginRight";
    public static final String KEY_MARGIN_TOP_STR = "marginTop";
    public static final String KEY_PAGE_HEIGHT_STR = "pageHeight";
    public static final String KEY_PAGE_ORIENTATION_STR = "pageOrientation";
    public static final String KEY_PAGE_WIDTH_STR = "pageWidth";
    public static final String KEY_PIXEL_TO_MM_STR = "pixelToMm";
    public static final String KEY_SCALE_TO_PAGE_STR = "scaleToPage";
    public static final String KEY_SHOW_PAGE_DIALOG_STR = "showPageDialog";
    public static final String KEY_SHOW_PRINTER_DIALOG_STR = "showPrinterDialog";
    public static final String KEY_USER_STYLESHEET_URI_STR = "userStylesheet";
    public static final String KEY_WIDTH_STR = "width";
    public static final String KEY_XML_PARSER_CLASSNAME_STR = "xmlParserClassName";
    public static final String VALUE_MEDIA_PRINT = "print";
    public static final String VALUE_PAGE_ORIENTATION_LANDSCAPE = "landscape";
    public static final String VALUE_PAGE_ORIENTATION_PORTRAIT = "portrait";
    public static final String VALUE_PAGE_ORIENTATION_REVERSE_LANDSCAPE = "reverseLandscape";
    private List inputs;
    private List printedInputs;
    private int curIndex;
    private BridgeContext theCtx;
    public static final TranscodingHints.Key KEY_SHOW_PAGE_DIALOG;
    public static final TranscodingHints.Key KEY_SHOW_PRINTER_DIALOG;
    public static final TranscodingHints.Key KEY_PAGE_WIDTH;
    public static final TranscodingHints.Key KEY_PAGE_HEIGHT;
    public static final TranscodingHints.Key KEY_MARGIN_TOP;
    public static final TranscodingHints.Key KEY_MARGIN_RIGHT;
    public static final TranscodingHints.Key KEY_MARGIN_BOTTOM;
    public static final TranscodingHints.Key KEY_MARGIN_LEFT;
    public static final TranscodingHints.Key KEY_PAGE_ORIENTATION;
    public static final TranscodingHints.Key KEY_SCALE_TO_PAGE;
    public static final String USAGE = "java org.apache.batik.transcoder.print.PrintTranscoder <svgFileToPrint>";
    
    public PrintTranscoder() {
        this.inputs = new ArrayList();
        this.printedInputs = null;
        this.curIndex = -1;
        this.hints.put(PrintTranscoder.KEY_MEDIA, "print");
    }
    
    public void transcode(final TranscoderInput transcoderInput, final TranscoderOutput transcoderOutput) {
        if (transcoderInput != null) {
            this.inputs.add(transcoderInput);
        }
    }
    
    protected void transcode(final Document document, final String s, final TranscoderOutput transcoderOutput) throws TranscoderException {
        super.transcode(document, s, transcoderOutput);
        this.theCtx = this.ctx;
        this.ctx = null;
    }
    
    public void print() throws PrinterException {
        final PrinterJob printerJob = PrinterJob.getPrinterJob();
        final PageFormat defaultPage = printerJob.defaultPage();
        final Paper paper = defaultPage.getPaper();
        final Float n = (Float)this.hints.get(PrintTranscoder.KEY_PAGE_WIDTH);
        final Float n2 = (Float)this.hints.get(PrintTranscoder.KEY_PAGE_HEIGHT);
        if (n != null) {
            paper.setSize(n, paper.getHeight());
        }
        if (n2 != null) {
            paper.setSize(paper.getWidth(), n2);
        }
        float floatValue = 0.0f;
        float floatValue2 = 0.0f;
        float n3 = (float)paper.getWidth();
        float n4 = (float)paper.getHeight();
        final Float n5 = (Float)this.hints.get(PrintTranscoder.KEY_MARGIN_LEFT);
        final Float n6 = (Float)this.hints.get(PrintTranscoder.KEY_MARGIN_TOP);
        final Float n7 = (Float)this.hints.get(PrintTranscoder.KEY_MARGIN_RIGHT);
        final Float n8 = (Float)this.hints.get(PrintTranscoder.KEY_MARGIN_BOTTOM);
        if (n5 != null) {
            floatValue = n5;
            n3 -= n5;
        }
        if (n6 != null) {
            floatValue2 = n6;
            n4 -= n6;
        }
        if (n7 != null) {
            n3 -= n7;
        }
        if (n8 != null) {
            n4 -= n8;
        }
        paper.setImageableArea(floatValue, floatValue2, n3, n4);
        final String anotherString = (String)this.hints.get(PrintTranscoder.KEY_PAGE_ORIENTATION);
        if ("portrait".equalsIgnoreCase(anotherString)) {
            defaultPage.setOrientation(1);
        }
        else if ("landscape".equalsIgnoreCase(anotherString)) {
            defaultPage.setOrientation(0);
        }
        else if ("reverseLandscape".equalsIgnoreCase(anotherString)) {
            defaultPage.setOrientation(2);
        }
        defaultPage.setPaper(paper);
        PageFormat validatePage = printerJob.validatePage(defaultPage);
        final Boolean b = (Boolean)this.hints.get(PrintTranscoder.KEY_SHOW_PAGE_DIALOG);
        if (b != null && b) {
            final PageFormat pageDialog = printerJob.pageDialog(validatePage);
            if (pageDialog == validatePage) {
                return;
            }
            validatePage = pageDialog;
        }
        printerJob.setPrintable(this, validatePage);
        final Boolean b2 = (Boolean)this.hints.get(PrintTranscoder.KEY_SHOW_PRINTER_DIALOG);
        if (b2 != null && b2 && !printerJob.printDialog()) {
            return;
        }
        printerJob.print();
    }
    
    public int print(final Graphics graphics, final PageFormat pageFormat, final int curIndex) {
        if (this.printedInputs == null) {
            this.printedInputs = new ArrayList(this.inputs);
        }
        if (curIndex >= this.printedInputs.size()) {
            this.curIndex = -1;
            if (this.theCtx != null) {
                this.theCtx.dispose();
            }
            this.userAgent.displayMessage("Done");
            return 1;
        }
        if (this.curIndex != curIndex) {
            if (this.theCtx != null) {
                this.theCtx.dispose();
            }
            try {
                this.width = (float)(int)pageFormat.getImageableWidth();
                this.height = (float)(int)pageFormat.getImageableHeight();
                super.transcode(this.printedInputs.get(curIndex), null);
                this.curIndex = curIndex;
            }
            catch (TranscoderException ex) {
                this.drawError(graphics, ex);
                return 0;
            }
        }
        final Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING, "Printing");
        final AffineTransform transform = graphics2D.getTransform();
        final Shape clip = graphics2D.getClip();
        graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        graphics2D.transform(this.curTxf);
        try {
            this.root.paint(graphics2D);
        }
        catch (Exception ex2) {
            graphics2D.setTransform(transform);
            graphics2D.setClip(clip);
            this.drawError(graphics, ex2);
        }
        graphics2D.setTransform(transform);
        graphics2D.setClip(clip);
        return 0;
    }
    
    protected void setImageSize(final float n, final float n2) {
        final Boolean b = (Boolean)this.hints.get(PrintTranscoder.KEY_SCALE_TO_PAGE);
        if (b != null && !b) {
            float n3 = n;
            float n4 = n2;
            if (this.hints.containsKey(PrintTranscoder.KEY_AOI)) {
                final Rectangle2D rectangle2D = (Rectangle2D)this.hints.get(PrintTranscoder.KEY_AOI);
                n3 = (float)rectangle2D.getWidth();
                n4 = (float)rectangle2D.getHeight();
            }
            super.setImageSize(n3, n4);
        }
    }
    
    private void drawError(final Graphics graphics, final Exception ex) {
        this.userAgent.displayError(ex);
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length < 1) {
            System.err.println("java org.apache.batik.transcoder.print.PrintTranscoder <svgFileToPrint>");
            System.exit(0);
        }
        final PrintTranscoder printTranscoder = new PrintTranscoder();
        setTranscoderFloatHint(printTranscoder, "language", PrintTranscoder.KEY_LANGUAGE);
        setTranscoderFloatHint(printTranscoder, "userStylesheet", PrintTranscoder.KEY_USER_STYLESHEET_URI);
        setTranscoderStringHint(printTranscoder, "xmlParserClassName", PrintTranscoder.KEY_XML_PARSER_CLASSNAME);
        setTranscoderBooleanHint(printTranscoder, "scaleToPage", PrintTranscoder.KEY_SCALE_TO_PAGE);
        setTranscoderRectangleHint(printTranscoder, "aoi", PrintTranscoder.KEY_AOI);
        setTranscoderFloatHint(printTranscoder, "width", PrintTranscoder.KEY_WIDTH);
        setTranscoderFloatHint(printTranscoder, "height", PrintTranscoder.KEY_HEIGHT);
        setTranscoderFloatHint(printTranscoder, "pixelToMm", PrintTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER);
        setTranscoderStringHint(printTranscoder, "pageOrientation", PrintTranscoder.KEY_PAGE_ORIENTATION);
        setTranscoderFloatHint(printTranscoder, "pageWidth", PrintTranscoder.KEY_PAGE_WIDTH);
        setTranscoderFloatHint(printTranscoder, "pageHeight", PrintTranscoder.KEY_PAGE_HEIGHT);
        setTranscoderFloatHint(printTranscoder, "marginTop", PrintTranscoder.KEY_MARGIN_TOP);
        setTranscoderFloatHint(printTranscoder, "marginRight", PrintTranscoder.KEY_MARGIN_RIGHT);
        setTranscoderFloatHint(printTranscoder, "marginBottom", PrintTranscoder.KEY_MARGIN_BOTTOM);
        setTranscoderFloatHint(printTranscoder, "marginLeft", PrintTranscoder.KEY_MARGIN_LEFT);
        setTranscoderBooleanHint(printTranscoder, "showPageDialog", PrintTranscoder.KEY_SHOW_PAGE_DIALOG);
        setTranscoderBooleanHint(printTranscoder, "showPrinterDialog", PrintTranscoder.KEY_SHOW_PRINTER_DIALOG);
        for (int i = 0; i < array.length; ++i) {
            printTranscoder.transcode(new TranscoderInput(new File(array[i]).toURL().toString()), null);
        }
        printTranscoder.print();
        System.exit(0);
    }
    
    public static void setTranscoderFloatHint(final Transcoder transcoder, final String key, final TranscodingHints.Key key2) {
        final String property = System.getProperty(key);
        if (property != null) {
            try {
                transcoder.addTranscodingHint(key2, new Float(Float.parseFloat(property)));
            }
            catch (NumberFormatException ex) {
                handleValueError(key, property);
            }
        }
    }
    
    public static void setTranscoderRectangleHint(final Transcoder transcoder, final String key, final TranscodingHints.Key key2) {
        final String property = System.getProperty(key);
        if (property != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ,");
            if (stringTokenizer.countTokens() != 4) {
                handleValueError(key, property);
            }
            try {
                transcoder.addTranscodingHint(key2, new Rectangle2D.Float(Float.parseFloat(stringTokenizer.nextToken()), Float.parseFloat(stringTokenizer.nextToken()), Float.parseFloat(stringTokenizer.nextToken()), Float.parseFloat(stringTokenizer.nextToken())));
            }
            catch (NumberFormatException ex) {
                handleValueError(key, property);
            }
        }
    }
    
    public static void setTranscoderBooleanHint(final Transcoder transcoder, final String key, final TranscodingHints.Key key2) {
        final String property = System.getProperty(key);
        if (property != null) {
            transcoder.addTranscodingHint(key2, "true".equalsIgnoreCase(property) ? Boolean.TRUE : Boolean.FALSE);
        }
    }
    
    public static void setTranscoderStringHint(final Transcoder transcoder, final String key, final TranscodingHints.Key key2) {
        final String property = System.getProperty(key);
        if (property != null) {
            transcoder.addTranscodingHint(key2, property);
        }
    }
    
    public static void handleValueError(final String str, final String str2) {
        System.err.println("Invalid " + str + " value : " + str2);
        System.exit(1);
    }
    
    static {
        KEY_SHOW_PAGE_DIALOG = new BooleanKey();
        KEY_SHOW_PRINTER_DIALOG = new BooleanKey();
        KEY_PAGE_WIDTH = new LengthKey();
        KEY_PAGE_HEIGHT = new LengthKey();
        KEY_MARGIN_TOP = new LengthKey();
        KEY_MARGIN_RIGHT = new LengthKey();
        KEY_MARGIN_BOTTOM = new LengthKey();
        KEY_MARGIN_LEFT = new LengthKey();
        KEY_PAGE_ORIENTATION = new StringKey();
        KEY_SCALE_TO_PAGE = new BooleanKey();
    }
}
