// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import org.w3c.dom.Element;
import java.io.DataInputStream;
import java.awt.Dimension;
import java.awt.Graphics;
import org.apache.batik.svggen.SVGGraphics2D;
import java.io.IOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.ToSVGAbstractTranscoder;

public class WMFTranscoder extends ToSVGAbstractTranscoder
{
    public static final String WMF_EXTENSION = ".wmf";
    public static final String SVG_EXTENSION = ".svg";
    
    public void transcode(final TranscoderInput transcoderInput, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final DataInputStream compatibleInput = this.getCompatibleInput(transcoderInput);
        final WMFRecordStore wmfRecordStore = new WMFRecordStore();
        try {
            wmfRecordStore.read(compatibleInput);
        }
        catch (IOException ex) {
            this.handler.fatalError(new TranscoderException(ex));
            return;
        }
        float n = 1.0f;
        float n2;
        if (this.hints.containsKey(WMFTranscoder.KEY_INPUT_WIDTH)) {
            n2 = (float)(int)this.hints.get(WMFTranscoder.KEY_INPUT_WIDTH);
            final float n3 = (float)(int)this.hints.get(WMFTranscoder.KEY_INPUT_HEIGHT);
        }
        else {
            n2 = (float)wmfRecordStore.getWidthPixels();
            final float n3 = (float)wmfRecordStore.getHeightPixels();
        }
        if (this.hints.containsKey(WMFTranscoder.KEY_WIDTH)) {
            n = (float)this.hints.get(WMFTranscoder.KEY_WIDTH) / n2;
        }
        int intValue = 0;
        int intValue2 = 0;
        if (this.hints.containsKey(WMFTranscoder.KEY_XOFFSET)) {
            intValue = (int)this.hints.get(WMFTranscoder.KEY_XOFFSET);
        }
        if (this.hints.containsKey(WMFTranscoder.KEY_YOFFSET)) {
            intValue2 = (int)this.hints.get(WMFTranscoder.KEY_YOFFSET);
        }
        final float n4 = wmfRecordStore.getUnitsToPixels() * n;
        final int i = (int)(wmfRecordStore.getVpX() * n4);
        final int j = (int)(wmfRecordStore.getVpY() * n4);
        int n5;
        int n6;
        if (this.hints.containsKey(WMFTranscoder.KEY_INPUT_WIDTH)) {
            n5 = (int)((int)this.hints.get(WMFTranscoder.KEY_INPUT_WIDTH) * n);
            n6 = (int)((int)this.hints.get(WMFTranscoder.KEY_INPUT_HEIGHT) * n);
        }
        else {
            n5 = (int)(wmfRecordStore.getWidthUnits() * n4);
            n6 = (int)(wmfRecordStore.getHeightUnits() * n4);
        }
        final WMFPainter wmfPainter = new WMFPainter(wmfRecordStore, intValue, intValue2, n);
        this.svgGenerator = new SVGGraphics2D(this.createDocument(transcoderOutput));
        this.svgGenerator.getGeneratorContext().setPrecision(4);
        wmfPainter.paint(this.svgGenerator);
        this.svgGenerator.setSVGCanvasSize(new Dimension(n5, n6));
        final Element root = this.svgGenerator.getRoot();
        root.setAttributeNS(null, "viewBox", String.valueOf(i) + ' ' + j + ' ' + n5 + ' ' + n6);
        this.writeSVGToOutput(this.svgGenerator, root, transcoderOutput);
    }
    
    private DataInputStream getCompatibleInput(final TranscoderInput transcoderInput) throws TranscoderException {
        if (transcoderInput == null) {
            this.handler.fatalError(new TranscoderException(String.valueOf(65280)));
        }
        final InputStream inputStream = transcoderInput.getInputStream();
        if (inputStream != null) {
            return new DataInputStream(new BufferedInputStream(inputStream));
        }
        final String uri = transcoderInput.getURI();
        if (uri != null) {
            try {
                return new DataInputStream(new BufferedInputStream(new URL(uri).openStream()));
            }
            catch (MalformedURLException ex) {
                this.handler.fatalError(new TranscoderException(ex));
            }
            catch (IOException ex2) {
                this.handler.fatalError(new TranscoderException(ex2));
            }
        }
        this.handler.fatalError(new TranscoderException(String.valueOf(65281)));
        return null;
    }
    
    public static void main(final String[] array) throws TranscoderException {
        if (array.length < 1) {
            System.out.println("Usage : WMFTranscoder.main <file 1> ... <file n>");
            System.exit(1);
        }
        final WMFTranscoder wmfTranscoder = new WMFTranscoder();
        for (int length = array.length, i = 0; i < length; ++i) {
            final String pathname = array[i];
            if (!pathname.toLowerCase().endsWith(".wmf")) {
                System.err.println(array[i] + " does not have the " + ".wmf" + " extension. It is ignored");
            }
            else {
                System.out.print("Processing : " + array[i] + "...");
                final String string = pathname.substring(0, pathname.toLowerCase().indexOf(".wmf")) + ".svg";
                final File file = new File(pathname);
                final File file2 = new File(string);
                try {
                    wmfTranscoder.transcode(new TranscoderInput(file.toURL().toString()), new TranscoderOutput(new FileOutputStream(file2)));
                }
                catch (MalformedURLException ex) {
                    throw new TranscoderException(ex);
                }
                catch (IOException ex2) {
                    throw new TranscoderException(ex2);
                }
                System.out.println(".... Done");
            }
        }
        System.exit(0);
    }
}
