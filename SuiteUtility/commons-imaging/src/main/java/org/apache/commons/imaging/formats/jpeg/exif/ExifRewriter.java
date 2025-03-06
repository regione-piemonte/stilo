// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.exif;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import org.apache.commons.imaging.common.ByteConversions;
import java.io.DataOutputStream;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterBase;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterLossy;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterLossless;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.util.List;
import org.apache.commons.imaging.formats.jpeg.JpegUtils;
import java.util.ArrayList;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFileParser;

public class ExifRewriter extends BinaryFileParser
{
    public ExifRewriter() {
        this(ByteOrder.BIG_ENDIAN);
    }
    
    public ExifRewriter(final ByteOrder byteOrder) {
        this.setByteOrder(byteOrder);
    }
    
    private JFIFPieces analyzeJFIF(final ByteSource byteSource) throws ImageReadException, IOException {
        final List<JFIFPiece> pieces = new ArrayList<JFIFPiece>();
        final List<JFIFPiece> exifPieces = new ArrayList<JFIFPiece>();
        final JpegUtils.Visitor visitor = new JpegUtils.Visitor() {
            @Override
            public boolean beginSOS() {
                return true;
            }
            
            @Override
            public void visitSOS(final int marker, final byte[] markerBytes, final byte[] imageData) {
                pieces.add(new JFIFPieceImageData(markerBytes, imageData));
            }
            
            @Override
            public boolean visitSegment(final int marker, final byte[] markerBytes, final int markerLength, final byte[] markerLengthBytes, final byte[] segmentData) throws ImageReadException, IOException {
                if (marker != 65505) {
                    pieces.add(new JFIFPieceSegment(marker, markerBytes, markerLengthBytes, segmentData));
                }
                else if (!BinaryFunctions.startsWith(segmentData, JpegConstants.EXIF_IDENTIFIER_CODE)) {
                    pieces.add(new JFIFPieceSegment(marker, markerBytes, markerLengthBytes, segmentData));
                }
                else {
                    final JFIFPiece piece = new JFIFPieceSegmentExif(marker, markerBytes, markerLengthBytes, segmentData);
                    pieces.add(piece);
                    exifPieces.add(piece);
                }
                return true;
            }
        };
        new JpegUtils().traverseJFIF(byteSource, visitor);
        return new JFIFPieces(pieces, exifPieces);
    }
    
    public void removeExifMetadata(final File src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.removeExifMetadata(byteSource, os);
    }
    
    public void removeExifMetadata(final byte[] src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.removeExifMetadata(byteSource, os);
    }
    
    public void removeExifMetadata(final InputStream src, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.removeExifMetadata(byteSource, os);
    }
    
    public void removeExifMetadata(final ByteSource byteSource, final OutputStream os) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        final List<JFIFPiece> pieces = jfifPieces.pieces;
        this.writeSegmentsReplacingExif(os, pieces, null);
    }
    
    public void updateExifMetadataLossless(final File src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.updateExifMetadataLossless(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossless(final byte[] src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.updateExifMetadataLossless(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossless(final InputStream src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.updateExifMetadataLossless(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossless(final ByteSource byteSource, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        final List<JFIFPiece> pieces = jfifPieces.pieces;
        TiffImageWriterBase writer;
        if (jfifPieces.exifPieces.size() > 0) {
            JFIFPieceSegment exifPiece = null;
            exifPiece = jfifPieces.exifPieces.get(0);
            byte[] exifBytes = exifPiece.segmentData;
            exifBytes = BinaryFunctions.remainingBytes("trimmed exif bytes", exifBytes, 6);
            writer = new TiffImageWriterLossless(outputSet.byteOrder, exifBytes);
        }
        else {
            writer = new TiffImageWriterLossy(outputSet.byteOrder);
        }
        final boolean includeEXIFPrefix = true;
        final byte[] newBytes = this.writeExifSegment(writer, outputSet, true);
        this.writeSegmentsReplacingExif(os, pieces, newBytes);
    }
    
    public void updateExifMetadataLossy(final byte[] src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceArray(src);
        this.updateExifMetadataLossy(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossy(final InputStream src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceInputStream(src, null);
        this.updateExifMetadataLossy(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossy(final File src, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final ByteSource byteSource = new ByteSourceFile(src);
        this.updateExifMetadataLossy(byteSource, os, outputSet);
    }
    
    public void updateExifMetadataLossy(final ByteSource byteSource, final OutputStream os, final TiffOutputSet outputSet) throws ImageReadException, IOException, ImageWriteException {
        final JFIFPieces jfifPieces = this.analyzeJFIF(byteSource);
        final List<JFIFPiece> pieces = jfifPieces.pieces;
        final TiffImageWriterBase writer = new TiffImageWriterLossy(outputSet.byteOrder);
        final boolean includeEXIFPrefix = true;
        final byte[] newBytes = this.writeExifSegment(writer, outputSet, true);
        this.writeSegmentsReplacingExif(os, pieces, newBytes);
    }
    
    private void writeSegmentsReplacingExif(final OutputStream outputStream, final List<JFIFPiece> segments, final byte[] newBytes) throws ImageWriteException, IOException {
        try (final DataOutputStream os = new DataOutputStream(outputStream)) {
            JpegConstants.SOI.writeTo(os);
            boolean hasExif = false;
            for (final JFIFPiece piece : segments) {
                if (piece instanceof JFIFPieceSegmentExif) {
                    hasExif = true;
                    break;
                }
            }
            if (!hasExif && newBytes != null) {
                final byte[] markerBytes = ByteConversions.toBytes((short)(-31), this.getByteOrder());
                if (newBytes.length > 65535) {
                    throw new ExifOverflowException("APP1 Segment is too long: " + newBytes.length);
                }
                final int markerLength = newBytes.length + 2;
                final byte[] markerLengthBytes = ByteConversions.toBytes((short)markerLength, this.getByteOrder());
                int index = 0;
                final JFIFPieceSegment firstSegment = segments.get(index);
                if (firstSegment.marker == 65504) {
                    index = 1;
                }
                segments.add(index, new JFIFPieceSegmentExif(65505, markerBytes, markerLengthBytes, newBytes));
            }
            boolean APP1Written = false;
            for (final JFIFPiece piece2 : segments) {
                if (piece2 instanceof JFIFPieceSegmentExif) {
                    if (APP1Written) {
                        continue;
                    }
                    APP1Written = true;
                    if (newBytes == null) {
                        continue;
                    }
                    final byte[] markerBytes2 = ByteConversions.toBytes((short)(-31), this.getByteOrder());
                    if (newBytes.length > 65535) {
                        throw new ExifOverflowException("APP1 Segment is too long: " + newBytes.length);
                    }
                    final int markerLength2 = newBytes.length + 2;
                    final byte[] markerLengthBytes2 = ByteConversions.toBytes((short)markerLength2, this.getByteOrder());
                    os.write(markerBytes2);
                    os.write(markerLengthBytes2);
                    os.write(newBytes);
                }
                else {
                    piece2.write(os);
                }
            }
        }
    }
    
    private byte[] writeExifSegment(final TiffImageWriterBase writer, final TiffOutputSet outputSet, final boolean includeEXIFPrefix) throws IOException, ImageWriteException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (includeEXIFPrefix) {
            JpegConstants.EXIF_IDENTIFIER_CODE.writeTo(os);
            os.write(0);
            os.write(0);
        }
        writer.write(os, outputSet);
        return os.toByteArray();
    }
    
    private static class JFIFPieces
    {
        public final List<JFIFPiece> pieces;
        public final List<JFIFPiece> exifPieces;
        
        JFIFPieces(final List<JFIFPiece> pieces, final List<JFIFPiece> exifPieces) {
            this.pieces = pieces;
            this.exifPieces = exifPieces;
        }
    }
    
    private abstract static class JFIFPiece
    {
        protected abstract void write(final OutputStream p0) throws IOException;
    }
    
    private static class JFIFPieceSegment extends JFIFPiece
    {
        public final int marker;
        public final byte[] markerBytes;
        public final byte[] markerLengthBytes;
        public final byte[] segmentData;
        
        JFIFPieceSegment(final int marker, final byte[] markerBytes, final byte[] markerLengthBytes, final byte[] segmentData) {
            this.marker = marker;
            this.markerBytes = markerBytes;
            this.markerLengthBytes = markerLengthBytes;
            this.segmentData = segmentData;
        }
        
        @Override
        protected void write(final OutputStream os) throws IOException {
            os.write(this.markerBytes);
            os.write(this.markerLengthBytes);
            os.write(this.segmentData);
        }
    }
    
    private static class JFIFPieceSegmentExif extends JFIFPieceSegment
    {
        JFIFPieceSegmentExif(final int marker, final byte[] markerBytes, final byte[] markerLengthBytes, final byte[] segmentData) {
            super(marker, markerBytes, markerLengthBytes, segmentData);
        }
    }
    
    private static class JFIFPieceImageData extends JFIFPiece
    {
        public final byte[] markerBytes;
        public final byte[] imageData;
        
        JFIFPieceImageData(final byte[] markerBytes, final byte[] imageData) {
            this.markerBytes = markerBytes;
            this.imageData = imageData;
        }
        
        @Override
        protected void write(final OutputStream os) throws IOException {
            os.write(this.markerBytes);
            os.write(this.imageData);
        }
    }
    
    public static class ExifOverflowException extends ImageWriteException
    {
        private static final long serialVersionUID = 1401484357224931218L;
        
        public ExifOverflowException(final String message) {
            super(message);
        }
    }
}
