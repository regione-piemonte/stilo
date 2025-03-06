// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.xmp;

import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageWriteException;
import java.util.List;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.io.OutputStream;
import java.io.File;

public class JpegXmpRewriter extends JpegRewriter
{
    public void removeXmpXml(final File src, final OutputStream os) throws ImageReadException, IOException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.removeXmpXml(byteSource, os);
    }
    
    public void removeXmpXml(final byte[] src, final OutputStream os) throws ImageReadException, IOException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.removeXmpXml(byteSource, os);
    }
    
    public void removeXmpXml(final InputStream src, final OutputStream os) throws ImageReadException, IOException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.removeXmpXml(byteSource, os);
    }
    
    public void removeXmpXml(final ByteSource byteSource, final OutputStream os) throws ImageReadException, IOException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        List<JFIFPiece> pieces = jfifPieces.pieces;
        pieces = this.removeXmpSegments(pieces);
        this.writeSegments(os, pieces);
    }
    
    public void updateXmpXml(final byte[] src, final OutputStream os, final String xmpXml) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.updateXmpXml(byteSource, os, xmpXml);
    }
    
    public void updateXmpXml(final InputStream src, final OutputStream os, final String xmpXml) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.updateXmpXml(byteSource, os, xmpXml);
    }
    
    public void updateXmpXml(final File src, final OutputStream os, final String xmpXml) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.updateXmpXml(byteSource, os, xmpXml);
    }
    
    public void updateXmpXml(final ByteSource byteSource, final OutputStream os, final String xmpXml) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        List<JFIFPiece> pieces = jfifPieces.pieces;
        pieces = this.removeXmpSegments(pieces);
        final List<JFIFPieceSegment> newPieces = new ArrayList<JFIFPieceSegment>();
        final byte[] xmpXmlBytes = xmpXml.getBytes(StandardCharsets.UTF_8);
        int segmentSize;
        for (int index = 0; index < xmpXmlBytes.length; index += segmentSize) {
            segmentSize = Math.min(xmpXmlBytes.length, 65535);
            final byte[] segmentData = this.writeXmpSegment(xmpXmlBytes, index, segmentSize);
            newPieces.add(new JFIFPieceSegment(65505, segmentData));
        }
        pieces = this.insertAfterLastAppSegments(pieces, newPieces);
        this.writeSegments(os, pieces);
    }
    
    private byte[] writeXmpSegment(final byte[] xmpXmlData, final int start, final int length) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        JpegConstants.XMP_IDENTIFIER.writeTo(os);
        os.write(xmpXmlData, start, length);
        return os.toByteArray();
    }
}
