// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.xml;

import java.io.IOException;
import org.apache.batik.util.io.UTF16Decoder;
import java.io.PushbackInputStream;
import java.io.InputStream;
import org.apache.batik.util.io.StreamNormalizingReader;

public class XMLStreamNormalizingReader extends StreamNormalizingReader
{
    public XMLStreamNormalizingReader(final InputStream in, String s) throws IOException {
        final PushbackInputStream pushbackInputStream = new PushbackInputStream(in, 128);
        final byte[] array = new byte[4];
        final int read = pushbackInputStream.read(array);
        if (read > 0) {
            pushbackInputStream.unread(array, 0, read);
        }
        if (read == 4) {
            switch (array[0] & 0xFF) {
                case 0: {
                    if (array[1] == 60 && array[2] == 0 && array[3] == 63) {
                        this.charDecoder = new UTF16Decoder(pushbackInputStream, true);
                        return;
                    }
                    break;
                }
                case 60: {
                    switch (array[1] & 0xFF) {
                        case 0: {
                            if (array[2] == 63 && array[3] == 0) {
                                this.charDecoder = new UTF16Decoder(pushbackInputStream, false);
                                return;
                            }
                            break;
                        }
                        case 63: {
                            if (array[2] == 120 && array[3] == 109) {
                                this.charDecoder = this.createCharDecoder(pushbackInputStream, XMLUtilities.getXMLDeclarationEncoding(XMLUtilities.createXMLDeclarationReader(pushbackInputStream, "UTF8"), "UTF-8"));
                                return;
                            }
                            break;
                        }
                    }
                    break;
                }
                case 76: {
                    if (array[1] == 111 && (array[2] & 0xFF) == 0xA7 && (array[3] & 0xFF) == 0x94) {
                        this.charDecoder = this.createCharDecoder(pushbackInputStream, XMLUtilities.getXMLDeclarationEncoding(XMLUtilities.createXMLDeclarationReader(pushbackInputStream, "CP037"), "EBCDIC-CP-US"));
                        return;
                    }
                    break;
                }
                case 254: {
                    if ((array[1] & 0xFF) == 0xFF) {
                        this.charDecoder = this.createCharDecoder(pushbackInputStream, "UTF-16");
                        return;
                    }
                    break;
                }
                case 255: {
                    if ((array[1] & 0xFF) == 0xFE) {
                        this.charDecoder = this.createCharDecoder(pushbackInputStream, "UTF-16");
                        return;
                    }
                    break;
                }
            }
        }
        s = ((s == null) ? "UTF-8" : s);
        this.charDecoder = this.createCharDecoder(pushbackInputStream, s);
    }
}
