/*
 * Copyright (c) 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed,licensed or intended for use in
 * the design, construction, operation or maintenance of any nuclear facility.
 *
 * Adapted from the JAI codecs TIFFLZWDecoder for itext
 */
package it.eng.hybrid.module.jpedal.sun;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * A class for performing LZW decoding.
 *
 *
 */
public class LZWDecoderStream {
    
	public final static Logger logger = Logger.getLogger(LZWDecoderStream.class);
	
	BufferedInputStream bis=null;

    byte stringTable[][];
    byte data[] = null;
    OutputStream uncompData;
    int tableIndex, bitsToGet = 9;
    int bytePointer, bitPointer;
    int nextData = 0;
    int nextBits = 0;
    
    int andTable[] = {
        511,
        1023,
        2047,
        4095
    };
    
    public LZWDecoderStream() {
    }
    
    /**
     * Method to decode LZW compressed data..
     *
     * @param data            The compressed data.
     */
    public void decode(byte data[], BufferedOutputStream streamCache, BufferedInputStream bis) {
        
        if(data[0] == (byte)0x00 && data[1] == (byte)0x01) {
            throw new RuntimeException("LZW flavour not supported.");
        }
        
        initializeStringTable();
        
        this.bis=bis;
        this.data = data;
        this.uncompData = streamCache;
        
        // Initialize pointers
        bytePointer = 0;
        bitPointer = 0;
        
        nextData = 0;
        nextBits = 0;
        
        int code, oldCode = 0;
        byte string[];
        
        while ((code = getNextCode()) != 257) {
            
            if (code == 256) {
                
                initializeStringTable();
                code = getNextCode();
                
                if (code == 257) {
                    break;
                }
                
                writeString(stringTable[code]);
                oldCode = code;
                
            } else {
                
                if (code < tableIndex) {
                    
                    string = stringTable[code];
                    
                    writeString(string);
                    addStringToTable(stringTable[oldCode], string[0]);
                    oldCode = code;
                    
                } else {
                    
                    string = stringTable[oldCode];
                    string = composeString(string, string[0]);
                    writeString(string);
                    addStringToTable(string);
                    oldCode = code;
                }
            }
        }
    }
    
    
    /**
     * Initialize the string table.
     */
    public void initializeStringTable() {
        
        stringTable = new byte[8192][];
        
        for (int i=0; i<256; i++) {
            stringTable[i] = new byte[1];
            stringTable[i][0] = (byte)i;
        }
        
        tableIndex = 258;
        bitsToGet = 9;
    }
    
    /**
     * Write out the string just uncompressed.
     */
    public void writeString(byte string[]) {
        try {
            uncompData.write(string);
        }
        catch (IOException e) {
            logger.info("Exception "+e+" with LZW decoder");
        }
    }
    
    /**
     * Add a new string to the string table.
     */
    public void addStringToTable(byte oldString[], byte newString) {
        int length = oldString.length;
        byte string[] = new byte[length + 1];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;
        
        // Add this new String to the table
        stringTable[tableIndex++] = string;
        
        if (tableIndex == 511) {
            bitsToGet = 10;
        } else if (tableIndex == 1023) {
            bitsToGet = 11;
        } else if (tableIndex == 2047) {
            bitsToGet = 12;
        }
    }
    
    /**
     * Add a new string to the string table.
     */
    public void addStringToTable(byte string[]) {
        
        // Add this new String to the table
        stringTable[tableIndex++] = string;
        
        if (tableIndex == 511) {
            bitsToGet = 10;
        } else if (tableIndex == 1023) {
            bitsToGet = 11;
        } else if (tableIndex == 2047) {
            bitsToGet = 12;
        }
    }
    
    /**
     * Append <code>newString</code> to the end of <code>oldString</code>.
     */
    public static byte[] composeString(byte oldString[], byte newString) {
        int length = oldString.length;
        byte string[] = new byte[length + 1];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;
        
        return string;
    }
    
    // Returns the next 9, 10, 11 or 12 bits
    public int getNextCode() {
        // Attempt to get the next code. The exception is caught to make
        // this robust to cases wherein the EndOfInformation code has been
        // omitted from a strip. Examples of such cases have been observed
        // in practice.
        try {
        	int nextByte=bis.read();
        	
        	if(data==null)
        		bytePointer++;
        	
            nextData = (nextData << 8) | nextByte;
            nextBits += 8;
            
            if (nextBits < bitsToGet) {
            	
            	nextByte=bis.read();
            	if(data==null)
            		bytePointer++;
            	
                nextData = (nextData << 8) | nextByte;
                nextBits += 8;
            }
            
            int code =
            (nextData >> (nextBits - bitsToGet)) & andTable[bitsToGet-9];
            nextBits -= bitsToGet;
            
            return code;
        } catch(Exception e) {
            // Strip not terminated as expected: return EndOfInformation code.
            return 257;
        }
    }
}
