// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

import org.apache.batik.transcoder.keys.BooleanKey;
import org.apache.batik.transcoder.keys.IntegerKey;
import org.apache.batik.transcoder.keys.FloatKey;
import java.awt.Toolkit;
import java.io.OutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.Writer;
import java.io.OutputStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.DocumentType;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.SVGConstants;

public abstract class ToSVGAbstractTranscoder extends AbstractTranscoder implements SVGConstants
{
    public static float PIXEL_TO_MILLIMETERS;
    public static float PIXEL_PER_INCH;
    public static final int TRANSCODER_ERROR_BASE = 65280;
    public static final int ERROR_NULL_INPUT = 65280;
    public static final int ERROR_INCOMPATIBLE_INPUT_TYPE = 65281;
    public static final int ERROR_INCOMPATIBLE_OUTPUT_TYPE = 65282;
    public static final TranscodingHints.Key KEY_WIDTH;
    public static final TranscodingHints.Key KEY_HEIGHT;
    public static final TranscodingHints.Key KEY_INPUT_WIDTH;
    public static final TranscodingHints.Key KEY_INPUT_HEIGHT;
    public static final TranscodingHints.Key KEY_XOFFSET;
    public static final TranscodingHints.Key KEY_YOFFSET;
    public static final TranscodingHints.Key KEY_ESCAPED;
    protected SVGGraphics2D svgGenerator;
    
    protected Document createDocument(final TranscoderOutput transcoderOutput) {
        Document document;
        if (transcoderOutput.getDocument() == null) {
            document = SVGDOMImplementation.getDOMImplementation().createDocument("http://www.w3.org/2000/svg", "svg", null);
        }
        else {
            document = transcoderOutput.getDocument();
        }
        return document;
    }
    
    public SVGGraphics2D getGraphics2D() {
        return this.svgGenerator;
    }
    
    protected void writeSVGToOutput(final SVGGraphics2D svgGraphics2D, final Element element, final TranscoderOutput transcoderOutput) throws TranscoderException {
        if (transcoderOutput.getDocument() != null) {
            return;
        }
        if (transcoderOutput.getXMLFilter() != null) {
            this.handler.fatalError(new TranscoderException("65282"));
        }
        try {
            boolean booleanValue = false;
            if (this.hints.containsKey(ToSVGAbstractTranscoder.KEY_ESCAPED)) {
                booleanValue = (boolean)this.hints.get(ToSVGAbstractTranscoder.KEY_ESCAPED);
            }
            final OutputStream outputStream = transcoderOutput.getOutputStream();
            if (outputStream != null) {
                svgGraphics2D.stream(element, new OutputStreamWriter(outputStream), false, booleanValue);
                return;
            }
            final Writer writer = transcoderOutput.getWriter();
            if (writer != null) {
                svgGraphics2D.stream(element, writer, false, booleanValue);
                return;
            }
            final String uri = transcoderOutput.getURI();
            if (uri != null) {
                try {
                    svgGraphics2D.stream(element, new OutputStreamWriter(new URL(uri).openConnection().getOutputStream()), false, booleanValue);
                    return;
                }
                catch (MalformedURLException ex) {
                    this.handler.fatalError(new TranscoderException(ex));
                }
                catch (IOException ex2) {
                    this.handler.fatalError(new TranscoderException(ex2));
                }
            }
        }
        catch (IOException ex3) {
            throw new TranscoderException(ex3);
        }
        throw new TranscoderException("65282");
    }
    
    static {
        ToSVGAbstractTranscoder.PIXEL_TO_MILLIMETERS = 25.4f / Toolkit.getDefaultToolkit().getScreenResolution();
        ToSVGAbstractTranscoder.PIXEL_PER_INCH = (float)Toolkit.getDefaultToolkit().getScreenResolution();
        KEY_WIDTH = new FloatKey();
        KEY_HEIGHT = new FloatKey();
        KEY_INPUT_WIDTH = new IntegerKey();
        KEY_INPUT_HEIGHT = new IntegerKey();
        KEY_XOFFSET = new IntegerKey();
        KEY_YOFFSET = new IntegerKey();
        KEY_ESCAPED = new BooleanKey();
    }
}
