/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;


public class HashCalculatorUtil {
	
	private static final int STREAM_BUFFER_LENGTH = 1024;

    public static byte[] checksum(final String algorithm, final String data) throws NoSuchAlgorithmException {
        return checksum(algorithm, StringUtils.getBytesUtf8(data));
    }
	
    public static byte[] checksum(final String algorithm, final InputStream data) throws NoSuchAlgorithmException, IOException {
        return digest(MessageDigest.getInstance(algorithm), data);
    }
    
    public static byte[] checksum(final String algorithm, final byte[] data) throws NoSuchAlgorithmException {
    	final MessageDigest msgDigest = MessageDigest.getInstance(algorithm);
    	return msgDigest.digest(data);
    }

    
    public static String checksumHexString(final String algorithm, final byte[] data) throws IOException, NoSuchAlgorithmException {
    	return Hex.encodeHexString( checksum(algorithm, data) );
    }

    public static String checksumHexString(final String algorithm, final InputStream data) throws IOException, NoSuchAlgorithmException {
    	return Hex.encodeHexString( checksum(algorithm, data) );
    }
    
    public static String checksumHexString(final String algorithm, final String data) throws IOException, NoSuchAlgorithmException {
    	return Hex.encodeHexString( checksum(algorithm, data) );
    }

    public static String checksumBase64String(final String algorithm, final byte[] data) throws IOException, NoSuchAlgorithmException {
    	return Base64.encodeBase64String( checksum(algorithm, data) );
    }

    public static String checksumBase64String(final String algorithm, final InputStream data) throws IOException, NoSuchAlgorithmException {
    	return Base64.encodeBase64String( checksum(algorithm, data) );
    }
    
    public static String checksumBase64String(final String algorithm, final String data) throws IOException, NoSuchAlgorithmException  {
    	return Base64.encodeBase64String( checksum(algorithm, data) );
    }
    
    private static byte[] digest(final MessageDigest digest, final InputStream data) throws IOException {
        return updateDigest(digest, data).digest();
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final String valueToDigest) {
        messageDigest.update(StringUtils.getBytesUtf8(valueToDigest));
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final byte[] valueToDigest) {
        messageDigest.update(valueToDigest);
        return messageDigest;
    }

    public static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }

}
