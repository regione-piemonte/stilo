// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.itu_t4;

import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.commons.imaging.ImageWriteException;

public final class T4AndT6Compression
{
    private static final HuffmanTree<Integer> WHITE_RUN_LENGTHS;
    private static final HuffmanTree<Integer> BLACK_RUN_LENGTHS;
    private static final HuffmanTree<T4_T6_Tables.Entry> CONTROL_CODES;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    
    private T4AndT6Compression() {
    }
    
    private static void compress1DLine(final BitInputStreamFlexible inputStream, final BitArrayOutputStream outputStream, final int[] referenceLine, final int width) throws ImageWriteException {
        int color = 0;
        int runLength = 0;
        for (int x = 0; x < width; ++x) {
            try {
                final int nextColor = inputStream.readBits(1);
                if (referenceLine != null) {
                    referenceLine[x] = nextColor;
                }
                if (color == nextColor) {
                    ++runLength;
                }
                else {
                    writeRunLength(outputStream, runLength, color);
                    color = nextColor;
                    runLength = 1;
                }
            }
            catch (IOException ioException) {
                throw new ImageWriteException("Error reading image to compress", ioException);
            }
        }
        writeRunLength(outputStream, runLength, color);
    }
    
    public static byte[] compressModifiedHuffman(final byte[] uncompressed, final int width, final int height) throws ImageWriteException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(uncompressed));
        try (final BitArrayOutputStream outputStream = new BitArrayOutputStream()) {
            for (int y = 0; y < height; ++y) {
                compress1DLine(inputStream, outputStream, null, width);
                inputStream.flushCache();
                outputStream.flush();
            }
            return outputStream.toByteArray();
        }
    }
    
    public static byte[] decompressModifiedHuffman(final byte[] compressed, final int width, final int height) throws ImageReadException {
        try (final ByteArrayInputStream baos = new ByteArrayInputStream(compressed);
             final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(baos);
             final BitArrayOutputStream outputStream = new BitArrayOutputStream()) {
            for (int y = 0; y < height; ++y) {
                int color = 0;
                int rowLength;
                int runLength;
                for (rowLength = 0; rowLength < width; rowLength += runLength) {
                    runLength = readTotalRunLength(inputStream, color);
                    for (int i = 0; i < runLength; ++i) {
                        outputStream.writeBit(color);
                    }
                    color = 1 - color;
                }
                if (rowLength == width) {
                    inputStream.flushCache();
                    outputStream.flush();
                }
                else if (rowLength > width) {
                    throw new ImageReadException("Unrecoverable row length error in image row " + y);
                }
            }
            final byte[] ret = outputStream.toByteArray();
            return ret;
        }
        catch (IOException ioException) {
            throw new ImageReadException("Error reading image to decompress", ioException);
        }
    }
    
    public static byte[] compressT4_1D(final byte[] uncompressed, final int width, final int height, final boolean hasFill) throws ImageWriteException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(uncompressed));
        try (final BitArrayOutputStream outputStream = new BitArrayOutputStream()) {
            if (hasFill) {
                T4_T6_Tables.EOL16.writeBits(outputStream);
            }
            else {
                T4_T6_Tables.EOL.writeBits(outputStream);
            }
            for (int y = 0; y < height; ++y) {
                compress1DLine(inputStream, outputStream, null, width);
                if (hasFill) {
                    int bitsAvailable = outputStream.getBitsAvailableInCurrentByte();
                    if (bitsAvailable < 4) {
                        outputStream.flush();
                        bitsAvailable = 8;
                    }
                    while (bitsAvailable > 4) {
                        outputStream.writeBit(0);
                        --bitsAvailable;
                    }
                }
                T4_T6_Tables.EOL.writeBits(outputStream);
                inputStream.flushCache();
            }
            return outputStream.toByteArray();
        }
    }
    
    public static byte[] decompressT4_1D(final byte[] compressed, final int width, final int height, final boolean hasFill) throws ImageReadException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(compressed));
        try (final BitArrayOutputStream outputStream = new BitArrayOutputStream()) {
            for (int y = 0; y < height; ++y) {
                int rowLength;
                try {
                    final T4_T6_Tables.Entry entry = T4AndT6Compression.CONTROL_CODES.decode(inputStream);
                    if (!isEOL(entry, hasFill)) {
                        throw new ImageReadException("Expected EOL not found");
                    }
                    int color = 0;
                    int runLength;
                    for (rowLength = 0; rowLength < width; rowLength += runLength) {
                        runLength = readTotalRunLength(inputStream, color);
                        for (int i = 0; i < runLength; ++i) {
                            outputStream.writeBit(color);
                        }
                        color = 1 - color;
                    }
                }
                catch (HuffmanTreeException huffmanException) {
                    throw new ImageReadException("Decompression error", huffmanException);
                }
                if (rowLength == width) {
                    outputStream.flush();
                }
                else if (rowLength > width) {
                    throw new ImageReadException("Unrecoverable row length error in image row " + y);
                }
            }
            final byte[] ret = outputStream.toByteArray();
            return ret;
        }
    }
    
    private static int compressT(final int a0, final int a1, final int b1, final BitArrayOutputStream outputStream, final int codingA0Color, final int[] codingLine) {
        final int a1b1 = a1 - b1;
        if (-3 <= a1b1 && a1b1 <= 3) {
            T4_T6_Tables.Entry entry;
            if (a1b1 == -3) {
                entry = T4_T6_Tables.VL3;
            }
            else if (a1b1 == -2) {
                entry = T4_T6_Tables.VL2;
            }
            else if (a1b1 == -1) {
                entry = T4_T6_Tables.VL1;
            }
            else if (a1b1 == 0) {
                entry = T4_T6_Tables.V0;
            }
            else if (a1b1 == 1) {
                entry = T4_T6_Tables.VR1;
            }
            else if (a1b1 == 2) {
                entry = T4_T6_Tables.VR2;
            }
            else {
                entry = T4_T6_Tables.VR3;
            }
            entry.writeBits(outputStream);
            return a1;
        }
        final int a2 = nextChangingElement(codingLine, 1 - codingA0Color, a1 + 1);
        final int a0a1 = a1 - a0;
        final int a1a2 = a2 - a1;
        T4_T6_Tables.H.writeBits(outputStream);
        writeRunLength(outputStream, a0a1, codingA0Color);
        writeRunLength(outputStream, a1a2, 1 - codingA0Color);
        return a2;
    }
    
    public static byte[] compressT4_2D(final byte[] uncompressed, final int width, final int height, final boolean hasFill, final int parameterK) throws ImageWriteException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(uncompressed));
        final BitArrayOutputStream outputStream = new BitArrayOutputStream();
        int[] referenceLine = new int[width];
        int[] codingLine = new int[width];
        int kCounter = 0;
        if (hasFill) {
            T4_T6_Tables.EOL16.writeBits(outputStream);
        }
        else {
            T4_T6_Tables.EOL.writeBits(outputStream);
        }
        for (int y = 0; y < height; ++y) {
            if (kCounter > 0) {
                outputStream.writeBit(0);
                for (int i = 0; i < width; ++i) {
                    try {
                        codingLine[i] = inputStream.readBits(1);
                    }
                    catch (IOException ioException) {
                        throw new ImageWriteException("Error reading image to compress", ioException);
                    }
                }
                int codingA0Color = 0;
                int referenceA0Color = 0;
                int a1 = nextChangingElement(codingLine, codingA0Color, 0);
                int b1 = nextChangingElement(referenceLine, referenceA0Color, 0);
                int b2 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                int a2 = 0;
                while (a2 < width) {
                    if (b2 < a1) {
                        T4_T6_Tables.P.writeBits(outputStream);
                        a2 = b2;
                    }
                    else {
                        a2 = compressT(a2, a1, b1, outputStream, codingA0Color, codingLine);
                        if (a2 == a1) {
                            codingA0Color = 1 - codingA0Color;
                        }
                    }
                    referenceA0Color = changingElementAt(referenceLine, a2);
                    a1 = nextChangingElement(codingLine, codingA0Color, a2 + 1);
                    if (codingA0Color == referenceA0Color) {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a2 + 1);
                    }
                    else {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a2 + 1);
                        b1 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                    }
                    b2 = nextChangingElement(referenceLine, 1 - codingA0Color, b1 + 1);
                }
                final int[] swap = referenceLine;
                referenceLine = codingLine;
                codingLine = swap;
            }
            else {
                outputStream.writeBit(1);
                compress1DLine(inputStream, outputStream, referenceLine, width);
            }
            if (hasFill) {
                int bitsAvailable = outputStream.getBitsAvailableInCurrentByte();
                if (bitsAvailable < 4) {
                    outputStream.flush();
                    bitsAvailable = 8;
                }
                while (bitsAvailable > 4) {
                    outputStream.writeBit(0);
                    --bitsAvailable;
                }
            }
            T4_T6_Tables.EOL.writeBits(outputStream);
            if (++kCounter == parameterK) {
                kCounter = 0;
            }
            inputStream.flushCache();
        }
        return outputStream.toByteArray();
    }
    
    public static byte[] decompressT4_2D(final byte[] compressed, final int width, final int height, final boolean hasFill) throws ImageReadException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(compressed));
        final BitArrayOutputStream outputStream = new BitArrayOutputStream();
        final int[] referenceLine = new int[width];
        for (int y = 0; y < height; ++y) {
            int rowLength = 0;
            try {
                T4_T6_Tables.Entry entry = T4AndT6Compression.CONTROL_CODES.decode(inputStream);
                if (!isEOL(entry, hasFill)) {
                    throw new ImageReadException("Expected EOL not found");
                }
                final int tagBit = inputStream.readBits(1);
                if (tagBit == 0) {
                    int codingA0Color = 0;
                    int referenceA0Color = 0;
                    int b1 = nextChangingElement(referenceLine, referenceA0Color, 0);
                    int b2 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                    int a0 = 0;
                    while (a0 < width) {
                        entry = T4AndT6Compression.CONTROL_CODES.decode(inputStream);
                        if (entry == T4_T6_Tables.P) {
                            fillRange(outputStream, referenceLine, a0, b2, codingA0Color);
                            a0 = b2;
                        }
                        else if (entry == T4_T6_Tables.H) {
                            final int a0a1 = readTotalRunLength(inputStream, codingA0Color);
                            final int a2 = a0 + a0a1;
                            fillRange(outputStream, referenceLine, a0, a2, codingA0Color);
                            final int a1a2 = readTotalRunLength(inputStream, 1 - codingA0Color);
                            final int a3 = a2 + a1a2;
                            fillRange(outputStream, referenceLine, a2, a3, 1 - codingA0Color);
                            a0 = a3;
                        }
                        else {
                            int a1b1;
                            if (entry == T4_T6_Tables.V0) {
                                a1b1 = 0;
                            }
                            else if (entry == T4_T6_Tables.VL1) {
                                a1b1 = -1;
                            }
                            else if (entry == T4_T6_Tables.VL2) {
                                a1b1 = -2;
                            }
                            else if (entry == T4_T6_Tables.VL3) {
                                a1b1 = -3;
                            }
                            else if (entry == T4_T6_Tables.VR1) {
                                a1b1 = 1;
                            }
                            else if (entry == T4_T6_Tables.VR2) {
                                a1b1 = 2;
                            }
                            else {
                                if (entry != T4_T6_Tables.VR3) {
                                    throw new ImageReadException("Invalid/unknown T.4 control code " + entry.bitString);
                                }
                                a1b1 = 3;
                            }
                            final int a2 = b1 + a1b1;
                            fillRange(outputStream, referenceLine, a0, a2, codingA0Color);
                            a0 = a2;
                            codingA0Color = 1 - codingA0Color;
                        }
                        referenceA0Color = changingElementAt(referenceLine, a0);
                        if (codingA0Color == referenceA0Color) {
                            b1 = nextChangingElement(referenceLine, referenceA0Color, a0 + 1);
                        }
                        else {
                            b1 = nextChangingElement(referenceLine, referenceA0Color, a0 + 1);
                            b1 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                        }
                        b2 = nextChangingElement(referenceLine, 1 - codingA0Color, b1 + 1);
                        rowLength = a0;
                    }
                }
                else {
                    int color = 0;
                    int runLength;
                    for (rowLength = 0; rowLength < width; rowLength += runLength) {
                        runLength = readTotalRunLength(inputStream, color);
                        for (int i = 0; i < runLength; ++i) {
                            outputStream.writeBit(color);
                            referenceLine[rowLength + i] = color;
                        }
                        color = 1 - color;
                    }
                }
            }
            catch (IOException ioException) {
                throw new ImageReadException("Decompression error", ioException);
            }
            catch (HuffmanTreeException huffmanException) {
                throw new ImageReadException("Decompression error", huffmanException);
            }
            if (rowLength == width) {
                outputStream.flush();
            }
            else if (rowLength > width) {
                throw new ImageReadException("Unrecoverable row length error in image row " + y);
            }
        }
        return outputStream.toByteArray();
    }
    
    public static byte[] compressT6(final byte[] uncompressed, final int width, final int height) throws ImageWriteException {
        try (final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(uncompressed))) {
            final BitArrayOutputStream outputStream = new BitArrayOutputStream();
            int[] referenceLine = new int[width];
            int[] codingLine = new int[width];
            for (int y = 0; y < height; ++y) {
                for (int i = 0; i < width; ++i) {
                    try {
                        codingLine[i] = inputStream.readBits(1);
                    }
                    catch (IOException ioException) {
                        throw new ImageWriteException("Error reading image to compress", ioException);
                    }
                }
                int codingA0Color = 0;
                int referenceA0Color = 0;
                int a1 = nextChangingElement(codingLine, codingA0Color, 0);
                int b1 = nextChangingElement(referenceLine, referenceA0Color, 0);
                int b2 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                int a2 = 0;
                while (a2 < width) {
                    if (b2 < a1) {
                        T4_T6_Tables.P.writeBits(outputStream);
                        a2 = b2;
                    }
                    else {
                        a2 = compressT(a2, a1, b1, outputStream, codingA0Color, codingLine);
                        if (a2 == a1) {
                            codingA0Color = 1 - codingA0Color;
                        }
                    }
                    referenceA0Color = changingElementAt(referenceLine, a2);
                    a1 = nextChangingElement(codingLine, codingA0Color, a2 + 1);
                    if (codingA0Color == referenceA0Color) {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a2 + 1);
                    }
                    else {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a2 + 1);
                        b1 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                    }
                    b2 = nextChangingElement(referenceLine, 1 - codingA0Color, b1 + 1);
                }
                final int[] swap = referenceLine;
                referenceLine = codingLine;
                codingLine = swap;
                inputStream.flushCache();
            }
            T4_T6_Tables.EOL.writeBits(outputStream);
            T4_T6_Tables.EOL.writeBits(outputStream);
            final byte[] ret = outputStream.toByteArray();
            return ret;
        }
        catch (IOException ioException2) {
            throw new ImageWriteException("I/O error", ioException2);
        }
    }
    
    public static byte[] decompressT6(final byte[] compressed, final int width, final int height) throws ImageReadException {
        final BitInputStreamFlexible inputStream = new BitInputStreamFlexible(new ByteArrayInputStream(compressed));
        final BitArrayOutputStream outputStream = new BitArrayOutputStream();
        final int[] referenceLine = new int[width];
        for (int y = 0; y < height; ++y) {
            int rowLength = 0;
            try {
                int codingA0Color = 0;
                int referenceA0Color = 0;
                int b1 = nextChangingElement(referenceLine, referenceA0Color, 0);
                int b2 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                int a0 = 0;
                while (a0 < width) {
                    final T4_T6_Tables.Entry entry = T4AndT6Compression.CONTROL_CODES.decode(inputStream);
                    if (entry == T4_T6_Tables.P) {
                        fillRange(outputStream, referenceLine, a0, b2, codingA0Color);
                        a0 = b2;
                    }
                    else if (entry == T4_T6_Tables.H) {
                        final int a0a1 = readTotalRunLength(inputStream, codingA0Color);
                        final int a2 = a0 + a0a1;
                        fillRange(outputStream, referenceLine, a0, a2, codingA0Color);
                        final int a1a2 = readTotalRunLength(inputStream, 1 - codingA0Color);
                        final int a3 = a2 + a1a2;
                        fillRange(outputStream, referenceLine, a2, a3, 1 - codingA0Color);
                        a0 = a3;
                    }
                    else {
                        int a1b1;
                        if (entry == T4_T6_Tables.V0) {
                            a1b1 = 0;
                        }
                        else if (entry == T4_T6_Tables.VL1) {
                            a1b1 = -1;
                        }
                        else if (entry == T4_T6_Tables.VL2) {
                            a1b1 = -2;
                        }
                        else if (entry == T4_T6_Tables.VL3) {
                            a1b1 = -3;
                        }
                        else if (entry == T4_T6_Tables.VR1) {
                            a1b1 = 1;
                        }
                        else if (entry == T4_T6_Tables.VR2) {
                            a1b1 = 2;
                        }
                        else {
                            if (entry != T4_T6_Tables.VR3) {
                                throw new ImageReadException("Invalid/unknown T.6 control code " + entry.bitString);
                            }
                            a1b1 = 3;
                        }
                        final int a2 = b1 + a1b1;
                        fillRange(outputStream, referenceLine, a0, a2, codingA0Color);
                        a0 = a2;
                        codingA0Color = 1 - codingA0Color;
                    }
                    referenceA0Color = changingElementAt(referenceLine, a0);
                    if (codingA0Color == referenceA0Color) {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a0 + 1);
                    }
                    else {
                        b1 = nextChangingElement(referenceLine, referenceA0Color, a0 + 1);
                        b1 = nextChangingElement(referenceLine, 1 - referenceA0Color, b1 + 1);
                    }
                    b2 = nextChangingElement(referenceLine, 1 - codingA0Color, b1 + 1);
                    rowLength = a0;
                }
            }
            catch (HuffmanTreeException huffmanException) {
                throw new ImageReadException("Decompression error", huffmanException);
            }
            if (rowLength == width) {
                outputStream.flush();
            }
            else if (rowLength > width) {
                throw new ImageReadException("Unrecoverable row length error in image row " + y);
            }
        }
        return outputStream.toByteArray();
    }
    
    private static boolean isEOL(final T4_T6_Tables.Entry entry, final boolean hasFill) {
        return entry == T4_T6_Tables.EOL || (hasFill && (entry == T4_T6_Tables.EOL13 || entry == T4_T6_Tables.EOL14 || entry == T4_T6_Tables.EOL15 || entry == T4_T6_Tables.EOL16 || entry == T4_T6_Tables.EOL17 || entry == T4_T6_Tables.EOL18 || entry == T4_T6_Tables.EOL19));
    }
    
    private static void writeRunLength(final BitArrayOutputStream bitStream, int runLength, final int color) {
        T4_T6_Tables.Entry[] makeUpCodes;
        T4_T6_Tables.Entry[] terminatingCodes;
        if (color == 0) {
            makeUpCodes = T4_T6_Tables.WHITE_MAKE_UP_CODES;
            terminatingCodes = T4_T6_Tables.WHITE_TERMINATING_CODES;
        }
        else {
            makeUpCodes = T4_T6_Tables.BLACK_MAKE_UP_CODES;
            terminatingCodes = T4_T6_Tables.BLACK_TERMINATING_CODES;
        }
        while (runLength >= 1792) {
            final T4_T6_Tables.Entry entry = lowerBound(T4_T6_Tables.ADDITIONAL_MAKE_UP_CODES, runLength);
            entry.writeBits(bitStream);
            runLength -= entry.value;
        }
        while (runLength >= 64) {
            final T4_T6_Tables.Entry entry = lowerBound(makeUpCodes, runLength);
            entry.writeBits(bitStream);
            runLength -= entry.value;
        }
        final T4_T6_Tables.Entry terminatingEntry = terminatingCodes[runLength];
        terminatingEntry.writeBits(bitStream);
    }
    
    private static T4_T6_Tables.Entry lowerBound(final T4_T6_Tables.Entry[] entries, final int value) {
        int first = 0;
        int last = entries.length - 1;
        do {
            final int middle = first + last >>> 1;
            if (entries[middle].value <= value && (middle + 1 >= entries.length || value < entries[middle + 1].value)) {
                return entries[middle];
            }
            if (entries[middle].value > value) {
                last = middle - 1;
            }
            else {
                first = middle + 1;
            }
        } while (first < last);
        return entries[first];
    }
    
    private static int readTotalRunLength(final BitInputStreamFlexible bitStream, final int color) throws ImageReadException {
        try {
            int totalLength = 0;
            Integer runLength;
            do {
                if (color == 0) {
                    runLength = T4AndT6Compression.WHITE_RUN_LENGTHS.decode(bitStream);
                }
                else {
                    runLength = T4AndT6Compression.BLACK_RUN_LENGTHS.decode(bitStream);
                }
                totalLength += runLength;
            } while (runLength > 63);
            return totalLength;
        }
        catch (HuffmanTreeException huffmanException) {
            throw new ImageReadException("Decompression error", huffmanException);
        }
    }
    
    private static int changingElementAt(final int[] line, final int position) {
        if (position < 0 || position >= line.length) {
            return 0;
        }
        return line[position];
    }
    
    private static int nextChangingElement(final int[] line, final int currentColour, final int start) {
        int position;
        for (position = start; position < line.length && line[position] == currentColour; ++position) {}
        return (position < line.length) ? position : line.length;
    }
    
    private static void fillRange(final BitArrayOutputStream outputStream, final int[] referenceRow, final int a0, final int end, final int color) {
        for (int i = a0; i < end; ++i) {
            outputStream.writeBit(referenceRow[i] = color);
        }
    }
    
    static {
        WHITE_RUN_LENGTHS = new HuffmanTree<Integer>();
        BLACK_RUN_LENGTHS = new HuffmanTree<Integer>();
        CONTROL_CODES = new HuffmanTree<T4_T6_Tables.Entry>();
        try {
            for (final T4_T6_Tables.Entry entry : T4_T6_Tables.WHITE_TERMINATING_CODES) {
                T4AndT6Compression.WHITE_RUN_LENGTHS.insert(entry.bitString, entry.value);
            }
            for (final T4_T6_Tables.Entry entry : T4_T6_Tables.WHITE_MAKE_UP_CODES) {
                T4AndT6Compression.WHITE_RUN_LENGTHS.insert(entry.bitString, entry.value);
            }
            for (final T4_T6_Tables.Entry entry : T4_T6_Tables.BLACK_TERMINATING_CODES) {
                T4AndT6Compression.BLACK_RUN_LENGTHS.insert(entry.bitString, entry.value);
            }
            for (final T4_T6_Tables.Entry entry : T4_T6_Tables.BLACK_MAKE_UP_CODES) {
                T4AndT6Compression.BLACK_RUN_LENGTHS.insert(entry.bitString, entry.value);
            }
            for (final T4_T6_Tables.Entry entry : T4_T6_Tables.ADDITIONAL_MAKE_UP_CODES) {
                T4AndT6Compression.WHITE_RUN_LENGTHS.insert(entry.bitString, entry.value);
                T4AndT6Compression.BLACK_RUN_LENGTHS.insert(entry.bitString, entry.value);
            }
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL.bitString, T4_T6_Tables.EOL);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL13.bitString, T4_T6_Tables.EOL13);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL14.bitString, T4_T6_Tables.EOL14);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL15.bitString, T4_T6_Tables.EOL15);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL16.bitString, T4_T6_Tables.EOL16);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL17.bitString, T4_T6_Tables.EOL17);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL18.bitString, T4_T6_Tables.EOL18);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.EOL19.bitString, T4_T6_Tables.EOL19);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.P.bitString, T4_T6_Tables.P);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.H.bitString, T4_T6_Tables.H);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.V0.bitString, T4_T6_Tables.V0);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VL1.bitString, T4_T6_Tables.VL1);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VL2.bitString, T4_T6_Tables.VL2);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VL3.bitString, T4_T6_Tables.VL3);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VR1.bitString, T4_T6_Tables.VR1);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VR2.bitString, T4_T6_Tables.VR2);
            T4AndT6Compression.CONTROL_CODES.insert(T4_T6_Tables.VR3.bitString, T4_T6_Tables.VR3);
        }
        catch (HuffmanTreeException cannotHappen) {
            throw new Error(cannotHappen);
        }
    }
}
