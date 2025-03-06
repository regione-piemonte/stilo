// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.Transcoder;

public final class DestinationType
{
    public static final String PNG_STR = "image/png";
    public static final String JPEG_STR = "image/jpeg";
    public static final String TIFF_STR = "image/tiff";
    public static final String PDF_STR = "application/pdf";
    public static final int PNG_CODE = 0;
    public static final int JPEG_CODE = 1;
    public static final int TIFF_CODE = 2;
    public static final int PDF_CODE = 3;
    public static final String PNG_EXTENSION = ".png";
    public static final String JPEG_EXTENSION = ".jpg";
    public static final String TIFF_EXTENSION = ".tif";
    public static final String PDF_EXTENSION = ".pdf";
    public static final DestinationType PNG;
    public static final DestinationType JPEG;
    public static final DestinationType TIFF;
    public static final DestinationType PDF;
    private String type;
    private int code;
    private String extension;
    
    private DestinationType(final String type, final int code, final String extension) {
        this.type = type;
        this.code = code;
        this.extension = extension;
    }
    
    public String getExtension() {
        return this.extension;
    }
    
    public String toString() {
        return this.type;
    }
    
    public int toInt() {
        return this.code;
    }
    
    protected Transcoder getTranscoder() {
        switch (this.code) {
            case 0: {
                return new PNGTranscoder();
            }
            case 1: {
                return new JPEGTranscoder();
            }
            case 2: {
                return new TIFFTranscoder();
            }
            case 3: {
                try {
                    return (Transcoder)Class.forName("org.apache.fop.svg.PDFTranscoder").newInstance();
                }
                catch (Exception ex) {
                    return null;
                }
                break;
            }
        }
        return null;
    }
    
    public DestinationType[] getValues() {
        return new DestinationType[] { DestinationType.PNG, DestinationType.JPEG, DestinationType.TIFF, DestinationType.PDF };
    }
    
    public Object readResolve() {
        switch (this.code) {
            case 0: {
                return DestinationType.PNG;
            }
            case 1: {
                return DestinationType.JPEG;
            }
            case 2: {
                return DestinationType.TIFF;
            }
            case 3: {
                return DestinationType.PDF;
            }
            default: {
                throw new Error("unknown code:" + this.code);
            }
        }
    }
    
    static {
        PNG = new DestinationType("image/png", 0, ".png");
        JPEG = new DestinationType("image/jpeg", 1, ".jpg");
        TIFF = new DestinationType("image/tiff", 2, ".tif");
        PDF = new DestinationType("application/pdf", 3, ".pdf");
    }
}
