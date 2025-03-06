// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import org.apache.commons.imaging.common.BinaryOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import org.apache.commons.imaging.internal.Debug;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.util.Map;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.nio.ByteOrder;
import java.util.logging.Logger;
import org.apache.commons.imaging.common.BinaryFileParser;

public class IptcParser extends BinaryFileParser
{
    private static final Logger LOGGER;
    private static final ByteOrder APP13_BYTE_ORDER;
    
    public IptcParser() {
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    public boolean isPhotoshopJpegSegment(final byte[] segmentData) {
        if (!BinaryFunctions.startsWith(segmentData, JpegConstants.PHOTOSHOP_IDENTIFICATION_STRING)) {
            return false;
        }
        final int index = JpegConstants.PHOTOSHOP_IDENTIFICATION_STRING.size();
        return index + 4 <= segmentData.length && ByteConversions.toInt(segmentData, index, IptcParser.APP13_BYTE_ORDER) == JpegConstants.CONST_8BIM;
    }
    
    public PhotoshopApp13Data parsePhotoshopSegment(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        final boolean strict = params != null && Boolean.TRUE.equals(params.get("STRICT"));
        return this.parsePhotoshopSegment(bytes, strict);
    }
    
    public PhotoshopApp13Data parsePhotoshopSegment(final byte[] bytes, final boolean strict) throws ImageReadException, IOException {
        final List<IptcRecord> records = new ArrayList<IptcRecord>();
        final List<IptcBlock> blocks = this.parseAllBlocks(bytes, strict);
        for (final IptcBlock block : blocks) {
            if (!block.isIPTCBlock()) {
                continue;
            }
            records.addAll(this.parseIPTCBlock(block.blockData));
        }
        return new PhotoshopApp13Data(records, blocks);
    }
    
    protected List<IptcRecord> parseIPTCBlock(final byte[] bytes) throws IOException {
        final List<IptcRecord> elements = new ArrayList<IptcRecord>();
        int index = 0;
        while (index + 1 < bytes.length) {
            final int tagMarker = 0xFF & bytes[index++];
            Debug.debug("tagMarker: " + tagMarker + " (0x" + Integer.toHexString(tagMarker) + ")");
            if (tagMarker != 28) {
                if (IptcParser.LOGGER.isLoggable(Level.FINE)) {
                    IptcParser.LOGGER.fine("Unexpected record tag marker in IPTC data.");
                }
                return elements;
            }
            final int recordNumber = 0xFF & bytes[index++];
            Debug.debug("recordNumber: " + recordNumber + " (0x" + Integer.toHexString(recordNumber) + ")");
            final int recordType = 0xFF & bytes[index];
            Debug.debug("recordType: " + recordType + " (0x" + Integer.toHexString(recordType) + ")");
            ++index;
            final int recordSize = ByteConversions.toUInt16(bytes, index, this.getByteOrder());
            index += 2;
            final boolean extendedDataset = recordSize > 32767;
            final int dataFieldCountLength = recordSize & 0x7FFF;
            if (extendedDataset) {
                Debug.debug("extendedDataset. dataFieldCountLength: " + dataFieldCountLength);
            }
            if (extendedDataset) {
                return elements;
            }
            final byte[] recordData = BinaryFunctions.slice(bytes, index, recordSize);
            index += recordSize;
            if (recordNumber != 2) {
                continue;
            }
            if (recordType == 0) {
                if (!IptcParser.LOGGER.isLoggable(Level.FINE)) {
                    continue;
                }
                IptcParser.LOGGER.fine("ignore record version record! " + elements.size());
            }
            else {
                final String value = new String(recordData, StandardCharsets.ISO_8859_1);
                final IptcType iptcType = IptcTypeLookup.getIptcType(recordType);
                final IptcRecord element = new IptcRecord(iptcType, value);
                elements.add(element);
            }
        }
        return elements;
    }
    
    protected List<IptcBlock> parseAllBlocks(final byte[] bytes, final boolean strict) throws ImageReadException, IOException {
        final List<IptcBlock> blocks = new ArrayList<IptcBlock>();
        try (final InputStream bis = new ByteArrayInputStream(bytes)) {
            final byte[] idString = BinaryFunctions.readBytes("", bis, JpegConstants.PHOTOSHOP_IDENTIFICATION_STRING.size(), "App13 Segment missing identification string");
            if (!JpegConstants.PHOTOSHOP_IDENTIFICATION_STRING.equals(idString)) {
                throw new ImageReadException("Not a Photoshop App13 Segment");
            }
            while (true) {
                int imageResourceBlockSignature;
                try {
                    imageResourceBlockSignature = BinaryFunctions.read4Bytes("", bis, "Image Resource Block missing identification string", IptcParser.APP13_BYTE_ORDER);
                }
                catch (IOException ioEx3) {
                    break;
                }
                if (imageResourceBlockSignature != JpegConstants.CONST_8BIM) {
                    throw new ImageReadException("Invalid Image Resource Block Signature");
                }
                final int blockType = BinaryFunctions.read2Bytes("", bis, "Image Resource Block missing type", IptcParser.APP13_BYTE_ORDER);
                Debug.debug("blockType: " + blockType + " (0x" + Integer.toHexString(blockType) + ")");
                final int blockNameLength = BinaryFunctions.readByte("Name length", bis, "Image Resource Block missing name length");
                if (blockNameLength > 0) {
                    Debug.debug("blockNameLength: " + blockNameLength + " (0x" + Integer.toHexString(blockNameLength) + ")");
                }
                byte[] blockNameBytes;
                if (blockNameLength == 0) {
                    BinaryFunctions.readByte("Block name bytes", bis, "Image Resource Block has invalid name");
                    blockNameBytes = new byte[0];
                }
                else {
                    try {
                        blockNameBytes = BinaryFunctions.readBytes("", bis, blockNameLength, "Invalid Image Resource Block name");
                    }
                    catch (IOException ioEx) {
                        if (strict) {
                            throw ioEx;
                        }
                        break;
                    }
                    if (blockNameLength % 2 == 0) {
                        BinaryFunctions.readByte("Padding byte", bis, "Image Resource Block missing padding byte");
                    }
                }
                final int blockSize = BinaryFunctions.read4Bytes("", bis, "Image Resource Block missing size", IptcParser.APP13_BYTE_ORDER);
                Debug.debug("blockSize: " + blockSize + " (0x" + Integer.toHexString(blockSize) + ")");
                if (blockSize > bytes.length) {
                    throw new ImageReadException("Invalid Block Size : " + blockSize + " > " + bytes.length);
                }
                byte[] blockData;
                try {
                    blockData = BinaryFunctions.readBytes("", bis, blockSize, "Invalid Image Resource Block data");
                }
                catch (IOException ioEx2) {
                    if (strict) {
                        throw ioEx2;
                    }
                    break;
                }
                blocks.add(new IptcBlock(blockType, blockNameBytes, blockData));
                if (blockSize % 2 == 0) {
                    continue;
                }
                BinaryFunctions.readByte("Padding byte", bis, "Image Resource Block missing padding byte");
            }
            return blocks;
        }
    }
    
    public byte[] writePhotoshopApp13Segment(final PhotoshopApp13Data data) throws IOException, ImageWriteException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final BinaryOutputStream bos = new BinaryOutputStream(os);
        JpegConstants.PHOTOSHOP_IDENTIFICATION_STRING.writeTo(bos);
        final List<IptcBlock> blocks = data.getRawBlocks();
        for (final IptcBlock block : blocks) {
            bos.write4Bytes(JpegConstants.CONST_8BIM);
            if (block.blockType < 0 || block.blockType > 65535) {
                throw new ImageWriteException("Invalid IPTC block type.");
            }
            bos.write2Bytes(block.blockType);
            if (block.blockNameBytes.length > 255) {
                throw new ImageWriteException("IPTC block name is too long: " + block.blockNameBytes.length);
            }
            bos.write(block.blockNameBytes.length);
            bos.write(block.blockNameBytes);
            if (block.blockNameBytes.length % 2 == 0) {
                bos.write(0);
            }
            if (block.blockData.length > 32767) {
                throw new ImageWriteException("IPTC block data is too long: " + block.blockData.length);
            }
            bos.write4Bytes(block.blockData.length);
            bos.write(block.blockData);
            if (block.blockData.length % 2 != 1) {
                continue;
            }
            bos.write(0);
        }
        bos.flush();
        return os.toByteArray();
    }
    
    public byte[] writeIPTCBlock(List<IptcRecord> elements) throws ImageWriteException, IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final BinaryOutputStream bos = new BinaryOutputStream(baos, this.getByteOrder())) {
            bos.write(28);
            bos.write(2);
            bos.write(IptcTypes.RECORD_VERSION.type);
            bos.write2Bytes(2);
            bos.write2Bytes(2);
            elements = new ArrayList<IptcRecord>(elements);
            final Comparator<IptcRecord> comparator = new Comparator<IptcRecord>() {
                @Override
                public int compare(final IptcRecord e1, final IptcRecord e2) {
                    return e2.iptcType.getType() - e1.iptcType.getType();
                }
            };
            Collections.sort(elements, comparator);
            for (final IptcRecord element : elements) {
                if (element.iptcType == IptcTypes.RECORD_VERSION) {
                    continue;
                }
                bos.write(28);
                bos.write(2);
                if (element.iptcType.getType() < 0 || element.iptcType.getType() > 255) {
                    throw new ImageWriteException("Invalid record type: " + element.iptcType.getType());
                }
                bos.write(element.iptcType.getType());
                final byte[] recordData = element.getValue().getBytes(StandardCharsets.ISO_8859_1);
                if (!new String(recordData, StandardCharsets.ISO_8859_1).equals(element.getValue())) {
                    throw new ImageWriteException("Invalid record value, not ISO-8859-1");
                }
                bos.write2Bytes(recordData.length);
                bos.write(recordData);
            }
        }
        final byte[] blockData = baos.toByteArray();
        return blockData;
    }
    
    static {
        LOGGER = Logger.getLogger(IptcParser.class.getName());
        APP13_BYTE_ORDER = ByteOrder.BIG_ENDIAN;
    }
}
