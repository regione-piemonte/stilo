/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Defines a set of utility methods for compress and uncompress contents.
 */
public class ZipUtil {

    private static final int BUFFER = 1024;
    protected static Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * This method compress a string and retrieve the byte array.
     *
     * @param text The text to be compressed.
     * @return The byte array.
     */
    public static byte[] compress(final String text) {
        final byte[] bytes = text.getBytes();
        logger.info("Length-BeforeCompressing[" + bytes.length + "]");
        final Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();

        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length)) {
            byte[] buffer = new byte[BUFFER];
            while (!deflater.finished()) {
                int bytesCompressed = deflater.deflate(buffer);
                bos.write(buffer, 0, bytesCompressed);
            }
            byte[] compressedArray = bos.toByteArray();
            logger.info("Length-AfterCompressing[" + compressedArray.length + "]");
            return compressedArray;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * This method uncompress a binary compressed version of the content.
     *
     * @param compressed The bytes that represents the zipped content.
     * @return The uncompressed text.
     */
    public static String uncompress(final byte[] compressed) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(compressed);
            final byte[] buf = new byte[BUFFER];
            while (!decompressor.finished()) {
                int count = 0;
                try {
                    count = decompressor.inflate(buf);
                } catch (DataFormatException e) {
                    logger.error("", e);
                    return null;
                }
                bos.write(buf, 0, count);
            }
        } finally {
            decompressor.end();
        }

        return bos.toString();
    }

    /*public static void main(String[] args) {
        String text = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap" +
                ".org/soap/envelope/\"><soap:Header/><soap:Body><ns4:queryResponse xmlns:ns4=\"common.acaris.acta" +
                ".doqui.it\" xmlns:ns2=\"archive.acaris.acta.doqui.it\" xmlns:ns3=\"objectservice.acaris.acta.doqui" +
                ".it\"><object><hasMoreItems>false</hasMoreItems></object></ns4:queryResponse></soap:Body></soap" +
                ":Envelope>";

        byte[] out = compress(text);
        System.out.println(out.length);
        String str = uncompress(out);
        System.out.println(str);
    }*/

}
