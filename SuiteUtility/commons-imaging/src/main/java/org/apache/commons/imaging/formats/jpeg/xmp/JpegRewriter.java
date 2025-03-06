// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.xmp;

import org.apache.commons.imaging.formats.jpeg.iptc.IptcParser;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.io.DataOutputStream;
import java.io.OutputStream;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Collection;
import java.util.Iterator;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.util.List;
import org.apache.commons.imaging.formats.jpeg.JpegUtils;
import java.util.ArrayList;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFileParser;

public class JpegRewriter extends BinaryFileParser
{
    private static final ByteOrder JPEG_BYTE_ORDER;
    private static final SegmentFilter EXIF_SEGMENT_FILTER;
    private static final SegmentFilter XMP_SEGMENT_FILTER;
    private static final SegmentFilter PHOTOSHOP_APP13_SEGMENT_FILTER;
    
    public JpegRewriter() {
        this.setByteOrder(JpegRewriter.JPEG_BYTE_ORDER);
    }
    
    protected JFIFPieces analyzeJFIF(final ByteSource byteSource) throws ImageReadException, IOException {
        final List<JFIFPiece> pieces = new ArrayList<JFIFPiece>();
        final List<JFIFPiece> segmentPieces = new ArrayList<JFIFPiece>();
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
            public boolean visitSegment(final int marker, final byte[] markerBytes, final int segmentLength, final byte[] segmentLengthBytes, final byte[] segmentData) throws ImageReadException, IOException {
                final JFIFPiece piece = new JFIFPieceSegment(marker, markerBytes, segmentLengthBytes, segmentData);
                pieces.add(piece);
                segmentPieces.add(piece);
                return true;
            }
        };
        new JpegUtils().traverseJFIF(byteSource, visitor);
        return new JFIFPieces(pieces, segmentPieces);
    }
    
    protected <T extends JFIFPiece> List<T> removeXmpSegments(final List<T> segments) {
        return this.filterSegments(segments, JpegRewriter.XMP_SEGMENT_FILTER);
    }
    
    protected <T extends JFIFPiece> List<T> removePhotoshopApp13Segments(final List<T> segments) {
        return this.filterSegments(segments, JpegRewriter.PHOTOSHOP_APP13_SEGMENT_FILTER);
    }
    
    protected <T extends JFIFPiece> List<T> findPhotoshopApp13Segments(final List<T> segments) {
        return this.filterSegments(segments, JpegRewriter.PHOTOSHOP_APP13_SEGMENT_FILTER, true);
    }
    
    protected <T extends JFIFPiece> List<T> removeExifSegments(final List<T> segments) {
        return this.filterSegments(segments, JpegRewriter.EXIF_SEGMENT_FILTER);
    }
    
    protected <T extends JFIFPiece> List<T> filterSegments(final List<T> segments, final SegmentFilter filter) {
        return this.filterSegments(segments, filter, false);
    }
    
    protected <T extends JFIFPiece> List<T> filterSegments(final List<T> segments, final SegmentFilter filter, final boolean reverse) {
        final List<T> result = new ArrayList<T>();
        for (final T piece : segments) {
            if (piece instanceof JFIFPieceSegment) {
                if (!(filter.filter((JFIFPieceSegment)piece) ^ !reverse)) {
                    continue;
                }
                result.add(piece);
            }
            else {
                if (reverse) {
                    continue;
                }
                result.add(piece);
            }
        }
        return result;
    }
    
    protected <T extends JFIFPiece, U extends JFIFPiece> List<JFIFPiece> insertBeforeFirstAppSegments(final List<T> segments, final List<U> newSegments) throws ImageWriteException {
        int firstAppIndex = -1;
        for (int i = 0; i < segments.size(); ++i) {
            final JFIFPiece piece = segments.get(i);
            if (piece instanceof JFIFPieceSegment) {
                final JFIFPieceSegment segment = (JFIFPieceSegment)piece;
                if (segment.isAppSegment() && firstAppIndex == -1) {
                    firstAppIndex = i;
                }
            }
        }
        final List<JFIFPiece> result = new ArrayList<JFIFPiece>(segments);
        if (firstAppIndex == -1) {
            throw new ImageWriteException("JPEG file has no APP segments.");
        }
        result.addAll(firstAppIndex, newSegments);
        return result;
    }
    
    protected <T extends JFIFPiece, U extends JFIFPiece> List<JFIFPiece> insertAfterLastAppSegments(final List<T> segments, final List<U> newSegments) throws ImageWriteException {
        int lastAppIndex = -1;
        for (int i = 0; i < segments.size(); ++i) {
            final JFIFPiece piece = segments.get(i);
            if (piece instanceof JFIFPieceSegment) {
                final JFIFPieceSegment segment = (JFIFPieceSegment)piece;
                if (segment.isAppSegment()) {
                    lastAppIndex = i;
                }
            }
        }
        final List<JFIFPiece> result = new ArrayList<JFIFPiece>(segments);
        if (lastAppIndex == -1) {
            if (segments.size() < 1) {
                throw new ImageWriteException("JPEG file has no APP segments.");
            }
            result.addAll(1, newSegments);
        }
        else {
            result.addAll(lastAppIndex + 1, newSegments);
        }
        return result;
    }
    
    protected void writeSegments(final OutputStream outputStream, final List<? extends JFIFPiece> segments) throws IOException {
        try (final DataOutputStream os = new DataOutputStream(outputStream)) {
            JpegConstants.SOI.writeTo(os);
            for (final JFIFPiece piece : segments) {
                piece.write(os);
            }
        }
    }
    
    static {
        JPEG_BYTE_ORDER = ByteOrder.BIG_ENDIAN;
        EXIF_SEGMENT_FILTER = new SegmentFilter() {
            @Override
            public boolean filter(final JFIFPieceSegment segment) {
                return segment.isExifSegment();
            }
        };
        XMP_SEGMENT_FILTER = new SegmentFilter() {
            @Override
            public boolean filter(final JFIFPieceSegment segment) {
                return segment.isXmpSegment();
            }
        };
        PHOTOSHOP_APP13_SEGMENT_FILTER = new SegmentFilter() {
            @Override
            public boolean filter(final JFIFPieceSegment segment) {
                return segment.isPhotoshopApp13Segment();
            }
        };
    }
    
    protected static class JFIFPieces
    {
        public final List<JFIFPiece> pieces;
        public final List<JFIFPiece> segmentPieces;
        
        public JFIFPieces(final List<JFIFPiece> pieces, final List<JFIFPiece> segmentPieces) {
            this.pieces = pieces;
            this.segmentPieces = segmentPieces;
        }
    }
    
    protected abstract static class JFIFPiece
    {
        protected abstract void write(final OutputStream p0) throws IOException;
        
        @Override
        public String toString() {
            return "[" + this.getClass().getName() + "]";
        }
    }
    
    protected static class JFIFPieceSegment extends JFIFPiece
    {
        public final int marker;
        private final byte[] markerBytes;
        private final byte[] segmentLengthBytes;
        private final byte[] segmentData;
        
        public JFIFPieceSegment(final int marker, final byte[] segmentData) {
            this(marker, ByteConversions.toBytes((short)marker, JpegRewriter.JPEG_BYTE_ORDER), ByteConversions.toBytes((short)(segmentData.length + 2), JpegRewriter.JPEG_BYTE_ORDER), segmentData);
        }
        
        JFIFPieceSegment(final int marker, final byte[] markerBytes, final byte[] segmentLengthBytes, final byte[] segmentData) {
            this.marker = marker;
            this.markerBytes = markerBytes;
            this.segmentLengthBytes = segmentLengthBytes;
            this.segmentData = segmentData;
        }
        
        @Override
        public String toString() {
            return "[" + this.getClass().getName() + " (0x" + Integer.toHexString(this.marker) + ")]";
        }
        
        @Override
        protected void write(final OutputStream os) throws IOException {
            os.write(this.markerBytes);
            os.write(this.segmentLengthBytes);
            os.write(this.segmentData);
        }
        
        public boolean isApp1Segment() {
            return this.marker == 65505;
        }
        
        public boolean isAppSegment() {
            return this.marker >= 65504 && this.marker <= 65519;
        }
        
        public boolean isExifSegment() {
            return this.marker == 65505 && BinaryFunctions.startsWith(this.segmentData, JpegConstants.EXIF_IDENTIFIER_CODE);
        }
        
        public boolean isPhotoshopApp13Segment() {
            return this.marker == 65517 && new IptcParser().isPhotoshopJpegSegment(this.segmentData);
        }
        
        public boolean isXmpSegment() {
            return this.marker == 65505 && BinaryFunctions.startsWith(this.segmentData, JpegConstants.XMP_IDENTIFIER);
        }
        
        public byte[] getSegmentData() {
            return this.segmentData;
        }
    }
    
    static class JFIFPieceImageData extends JFIFPiece
    {
        private final byte[] markerBytes;
        private final byte[] imageData;
        
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
    
    public static class JpegSegmentOverflowException extends ImageWriteException
    {
        private static final long serialVersionUID = -1062145751550646846L;
        
        public JpegSegmentOverflowException(final String message) {
            super(message);
        }
    }
    
    private interface SegmentFilter
    {
        boolean filter(final JFIFPieceSegment p0);
    }
}
