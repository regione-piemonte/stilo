// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.internal.Debug;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFileParser;

public class JpegUtils extends BinaryFileParser
{
    public JpegUtils() {
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    public void traverseJFIF(final ByteSource byteSource, final Visitor visitor) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            BinaryFunctions.readAndVerifyBytes(is, JpegConstants.SOI, "Not a Valid JPEG File: doesn't begin with 0xffd8");
            int markerCount = 0;
            while (true) {
                final byte[] markerBytes = new byte[2];
                do {
                    markerBytes[0] = markerBytes[1];
                    markerBytes[1] = BinaryFunctions.readByte("marker", is, "Could not read marker");
                } while ((0xFF & markerBytes[0]) != 0xFF || (0xFF & markerBytes[1]) == 0xFF);
                final int marker = (0xFF & markerBytes[0]) << 8 | (0xFF & markerBytes[1]);
                if (marker == 65497 || marker == 65498) {
                    if (!visitor.beginSOS()) {
                        return;
                    }
                    final byte[] imageData = BinaryFunctions.getStreamBytes(is);
                    visitor.visitSOS(marker, markerBytes, imageData);
                    Debug.debug(Integer.toString(markerCount) + " markers");
                    break;
                }
                else {
                    final byte[] segmentLengthBytes = BinaryFunctions.readBytes("segmentLengthBytes", is, 2, "segmentLengthBytes");
                    final int segmentLength = ByteConversions.toUInt16(segmentLengthBytes, this.getByteOrder());
                    if (segmentLength < 2) {
                        throw new ImageReadException("Invalid segment size");
                    }
                    final byte[] segmentData = BinaryFunctions.readBytes("Segment Data", is, segmentLength - 2, "Invalid Segment: insufficient data");
                    if (!visitor.visitSegment(marker, markerBytes, segmentLength, segmentLengthBytes, segmentData)) {
                        return;
                    }
                    ++markerCount;
                }
            }
        }
    }
    
    public static String getMarkerName(final int marker) {
        switch (marker) {
            case 65498: {
                return "SOS_MARKER";
            }
            case 65505: {
                return "JPEG_APP1_MARKER";
            }
            case 65506: {
                return "JPEG_APP2_MARKER";
            }
            case 65517: {
                return "JPEG_APP13_MARKER";
            }
            case 65518: {
                return "JPEG_APP14_MARKER";
            }
            case 65519: {
                return "JPEG_APP15_MARKER";
            }
            case 65504: {
                return "JFIF_MARKER";
            }
            case 65472: {
                return "SOF0_MARKER";
            }
            case 65473: {
                return "SOF1_MARKER";
            }
            case 65474: {
                return "SOF2_MARKER";
            }
            case 65475: {
                return "SOF3_MARKER";
            }
            case 65476: {
                return "SOF4_MARKER";
            }
            case 65477: {
                return "SOF5_MARKER";
            }
            case 65478: {
                return "SOF6_MARKER";
            }
            case 65479: {
                return "SOF7_MARKER";
            }
            case 65480: {
                return "SOF8_MARKER";
            }
            case 65481: {
                return "SOF9_MARKER";
            }
            case 65482: {
                return "SOF10_MARKER";
            }
            case 65483: {
                return "SOF11_MARKER";
            }
            case 65484: {
                return "DAC_MARKER";
            }
            case 65485: {
                return "SOF13_MARKER";
            }
            case 65486: {
                return "SOF14_MARKER";
            }
            case 65487: {
                return "SOF15_MARKER";
            }
            case 65499: {
                return "DQT_MARKER";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    public void dumpJFIF(final ByteSource byteSource) throws ImageReadException, IOException {
        final Visitor visitor = new Visitor() {
            @Override
            public boolean beginSOS() {
                return true;
            }
            
            @Override
            public void visitSOS(final int marker, final byte[] markerBytes, final byte[] imageData) {
                Debug.debug("SOS marker.  " + imageData.length + " bytes of image data.");
                Debug.debug("");
            }
            
            @Override
            public boolean visitSegment(final int marker, final byte[] markerBytes, final int segmentLength, final byte[] segmentLengthBytes, final byte[] segmentData) {
                Debug.debug("Segment marker: " + Integer.toHexString(marker) + " (" + JpegUtils.getMarkerName(marker) + "), " + segmentData.length + " bytes of segment data.");
                return true;
            }
        };
        this.traverseJFIF(byteSource, visitor);
    }
    
    public interface Visitor
    {
        boolean beginSOS();
        
        void visitSOS(final int p0, final byte[] p1, final byte[] p2);
        
        boolean visitSegment(final int p0, final byte[] p1, final int p2, final byte[] p3, final byte[] p4) throws ImageReadException, IOException;
    }
}
