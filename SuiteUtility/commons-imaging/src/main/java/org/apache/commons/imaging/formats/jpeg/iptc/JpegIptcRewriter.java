// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.ImageWriteException;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.OutputStream;
import java.io.File;
import org.apache.commons.imaging.formats.jpeg.xmp.JpegRewriter;

public class JpegIptcRewriter extends JpegRewriter
{
    public void removeIPTC(final File src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        this.removeIPTC(src, os, false);
    }
    
    public void removeIPTC(final File src, final OutputStream os, final boolean removeSegment) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.removeIPTC(byteSource, os, removeSegment);
    }
    
    public void removeIPTC(final byte[] src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        this.removeIPTC(src, os, false);
    }
    
    public void removeIPTC(final byte[] src, final OutputStream os, final boolean removeSegment) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.removeIPTC(byteSource, os, removeSegment);
    }
    
    public void removeIPTC(final InputStream src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        this.removeIPTC(src, os, false);
    }
    
    public void removeIPTC(final InputStream src, final OutputStream os, final boolean removeSegment) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.removeIPTC(byteSource, os, removeSegment);
    }
    
    public void removeIPTC(final ByteSource byteSource, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        this.removeIPTC(byteSource, os, false);
    }
    
    public void removeIPTC(final ByteSource byteSource, final OutputStream os, final boolean removeSegment) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        final List<JFIFPiece> oldPieces = jfifPieces.pieces;
        final List<JFIFPiece> photoshopApp13Segments = this.findPhotoshopApp13Segments(oldPieces);
        if (photoshopApp13Segments.size() > 1) {
            throw new ImageReadException("Image contains more than one Photoshop App13 segment.");
        }
        final List<JFIFPiece> newPieces = this.removePhotoshopApp13Segments(oldPieces);
        if (!removeSegment && photoshopApp13Segments.size() == 1) {
            final JFIFPieceSegment oldSegment = photoshopApp13Segments.get(0);
            final Map<String, Object> params = new HashMap<String, Object>();
            final PhotoshopApp13Data oldData = new IptcParser().parsePhotoshopSegment(oldSegment.getSegmentData(), params);
            final List<IptcBlock> newBlocks = oldData.getNonIptcBlocks();
            final List<IptcRecord> newRecords = new ArrayList<IptcRecord>();
            final PhotoshopApp13Data newData = new PhotoshopApp13Data(newRecords, newBlocks);
            final byte[] segmentBytes = new IptcParser().writePhotoshopApp13Segment(newData);
            final JFIFPieceSegment newSegment = new JFIFPieceSegment(oldSegment.marker, segmentBytes);
            newPieces.add(oldPieces.indexOf(oldSegment), newSegment);
        }
        this.writeSegments(os, newPieces);
    }
    
    public void writeIPTC(final byte[] src, final OutputStream os, final PhotoshopApp13Data newData) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.writeIPTC(byteSource, os, newData);
    }
    
    public void writeIPTC(final InputStream src, final OutputStream os, final PhotoshopApp13Data newData) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.writeIPTC(byteSource, os, newData);
    }
    
    public void writeIPTC(final File src, final OutputStream os, final PhotoshopApp13Data newData) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.writeIPTC(byteSource, os, newData);
    }
    
    public void writeIPTC(final ByteSource byteSource, final OutputStream os, PhotoshopApp13Data newData) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        final List<JFIFPiece> oldPieces = jfifPieces.pieces;
        final List<JFIFPiece> photoshopApp13Segments = this.findPhotoshopApp13Segments(oldPieces);
        if (photoshopApp13Segments.size() > 1) {
            throw new ImageReadException("Image contains more than one Photoshop App13 segment.");
        }
        List<JFIFPiece> newPieces = this.removePhotoshopApp13Segments(oldPieces);
        final List<IptcBlock> newBlocks = newData.getNonIptcBlocks();
        final byte[] newBlockBytes = new IptcParser().writeIPTCBlock(newData.getRecords());
        final int blockType = 1028;
        final byte[] blockNameBytes = new byte[0];
        final IptcBlock newBlock = new IptcBlock(1028, blockNameBytes, newBlockBytes);
        newBlocks.add(newBlock);
        newData = new PhotoshopApp13Data(newData.getRecords(), newBlocks);
        final byte[] segmentBytes = new IptcParser().writePhotoshopApp13Segment(newData);
        final JFIFPieceSegment newSegment = new JFIFPieceSegment(65517, segmentBytes);
        newPieces = this.insertAfterLastAppSegments(newPieces, (List<JFIFPiece>)Arrays.asList(newSegment));
        this.writeSegments(os, newPieces);
    }
}
