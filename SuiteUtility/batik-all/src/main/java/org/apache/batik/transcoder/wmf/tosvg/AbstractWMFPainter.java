// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.awt.Graphics2D;
import java.awt.image.WritableRaster;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;

public class AbstractWMFPainter
{
    public static final String WMF_FILE_EXTENSION = ".wmf";
    protected WMFFont wmfFont;
    protected int currentHorizAlign;
    protected int currentVertAlign;
    public static final int PEN = 1;
    public static final int BRUSH = 2;
    public static final int FONT = 3;
    public static final int NULL_PEN = 4;
    public static final int NULL_BRUSH = 5;
    public static final int PALETTE = 6;
    public static final int OBJ_BITMAP = 7;
    public static final int OBJ_REGION = 8;
    protected WMFRecordStore currentStore;
    protected transient boolean bReadingWMF;
    protected transient BufferedInputStream bufStream;
    
    public AbstractWMFPainter() {
        this.wmfFont = null;
        this.currentHorizAlign = 0;
        this.currentVertAlign = 0;
        this.bReadingWMF = true;
        this.bufStream = null;
    }
    
    protected BufferedImage getImage(final byte[] array, final int n, final int n2) {
        final int n3 = (array[7] & 0xFF) << 24 | (array[6] & 0xFF) << 16 | (array[5] & 0xFF) << 8 | (array[4] & 0xFF);
        final int n4 = (array[11] & 0xFF) << 24 | (array[10] & 0xFF) << 16 | (array[9] & 0xFF) << 8 | (array[8] & 0xFF);
        if (n != n3 || n2 != n4) {
            return null;
        }
        return this.getImage(array);
    }
    
    protected Dimension getImageDimension(final byte[] array) {
        return new Dimension((array[7] & 0xFF) << 24 | (array[6] & 0xFF) << 16 | (array[5] & 0xFF) << 8 | (array[4] & 0xFF), (array[11] & 0xFF) << 24 | (array[10] & 0xFF) << 16 | (array[9] & 0xFF) << 8 | (array[8] & 0xFF));
    }
    
    protected BufferedImage getImage(final byte[] array) {
        final int n = (array[7] & 0xFF) << 24 | (array[6] & 0xFF) << 16 | (array[5] & 0xFF) << 8 | (array[4] & 0xFF);
        final int n2 = (array[11] & 0xFF) << 24 | (array[10] & 0xFF) << 16 | (array[9] & 0xFF) << 8 | (array[8] & 0xFF);
        final int[] inData = new int[n * n2];
        final BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        final WritableRaster raster = bufferedImage.getRaster();
        final int n3 = (array[3] & 0xFF) << 24 | (array[2] & 0xFF) << 16 | (array[1] & 0xFF) << 8 | (array[0] & 0xFF);
        final int n4 = (array[13] & 0xFF) << 8 | (array[12] & 0xFF);
        final int n5 = (array[15] & 0xFF) << 8 | (array[14] & 0xFF);
        int n6 = (array[23] & 0xFF) << 24 | (array[22] & 0xFF) << 16 | (array[21] & 0xFF) << 8 | (array[20] & 0xFF);
        if (n6 == 0) {
            n6 = ((n * n5 + 31 & 0xFFFFFFE0) >> 3) * n2;
        }
        final int n7 = (array[35] & 0xFF) << 24 | (array[34] & 0xFF) << 16 | (array[33] & 0xFF) << 8 | (array[32] & 0xFF);
        if (n5 == 24) {
            final int n8 = n6 / n2 - n * 3;
            int n9 = n3;
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < n; ++j) {
                    inData[n * (n2 - i - 1) + j] = (0xFF000000 | (array[n9 + 2] & 0xFF) << 16 | (array[n9 + 1] & 0xFF) << 8 | (array[n9] & 0xFF));
                    n9 += 3;
                }
                n9 += n8;
            }
        }
        else if (n5 == 8) {
            int n10;
            if (n7 > 0) {
                n10 = n7;
            }
            else {
                n10 = 256;
            }
            int n11 = n3;
            final int[] array2 = new int[n10];
            for (int k = 0; k < n10; ++k) {
                array2[k] = (0xFF000000 | (array[n11 + 2] & 0xFF) << 16 | (array[n11 + 1] & 0xFF) << 8 | (array[n11] & 0xFF));
                n11 += 4;
            }
            final int n12 = (array.length - n11) / n2 - n;
            for (int l = 0; l < n2; ++l) {
                for (int n13 = 0; n13 < n; ++n13) {
                    inData[n * (n2 - l - 1) + n13] = array2[array[n11] & 0xFF];
                    ++n11;
                }
                n11 += n12;
            }
        }
        else if (n5 == 1) {
            final int n14 = 2;
            int n15 = n3;
            final int[] array3 = new int[n14];
            for (int n16 = 0; n16 < n14; ++n16) {
                array3[n16] = (0xFF000000 | (array[n15 + 2] & 0xFF) << 16 | (array[n15 + 1] & 0xFF) << 8 | (array[n15] & 0xFF));
                n15 += 4;
            }
            int n17 = 7;
            byte b = array[n15];
            final int n18 = n6 / n2 - n / 8;
            for (int n19 = 0; n19 < n2; ++n19) {
                for (int n20 = 0; n20 < n; ++n20) {
                    if ((b & 1 << n17) != 0x0) {
                        inData[n * (n2 - n19 - 1) + n20] = array3[1];
                    }
                    else {
                        inData[n * (n2 - n19 - 1) + n20] = array3[0];
                    }
                    if (--n17 == -1) {
                        n17 = 7;
                        ++n15;
                        b = array[n15];
                    }
                }
                n15 += n18;
                n17 = 7;
                if (n15 < array.length) {
                    b = array[n15];
                }
            }
        }
        raster.setDataElements(0, 0, n, n2, inData);
        return bufferedImage;
    }
    
    protected AttributedCharacterIterator getCharacterIterator(final Graphics2D graphics2D, final String s, final WMFFont wmfFont) {
        return this.getAttributedString(graphics2D, s, wmfFont).getIterator();
    }
    
    protected AttributedCharacterIterator getCharacterIterator(final Graphics2D graphics2D, final String s, final WMFFont wmfFont, final int n) {
        return this.getAttributedString(graphics2D, s, wmfFont).getIterator();
    }
    
    protected AttributedString getAttributedString(final Graphics2D graphics2D, final String text, final WMFFont wmfFont) {
        final AttributedString attributedString = new AttributedString(text);
        final Font font = graphics2D.getFont();
        attributedString.addAttribute(TextAttribute.SIZE, new Float(font.getSize2D()));
        attributedString.addAttribute(TextAttribute.FONT, font);
        if (this.wmfFont.underline != 0) {
            attributedString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        }
        if (this.wmfFont.italic != 0) {
            attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        }
        else {
            attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
        }
        if (this.wmfFont.weight > 400) {
            attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        }
        else {
            attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
        }
        return attributedString;
    }
    
    public void setRecordStore(final WMFRecordStore currentStore) {
        if (currentStore == null) {
            throw new IllegalArgumentException();
        }
        this.currentStore = currentStore;
    }
    
    public WMFRecordStore getRecordStore() {
        return this.currentStore;
    }
    
    protected int addObject(final WMFRecordStore wmfRecordStore, final int n, final Object o) {
        return this.currentStore.addObject(n, o);
    }
    
    protected int addObjectAt(final WMFRecordStore wmfRecordStore, final int n, final Object o, final int n2) {
        return this.currentStore.addObjectAt(n, o, n2);
    }
}
