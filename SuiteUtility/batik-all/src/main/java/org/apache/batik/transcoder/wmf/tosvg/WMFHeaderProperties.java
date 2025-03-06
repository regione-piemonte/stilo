// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.geom.Polyline2D;
import java.awt.Shape;
import org.apache.batik.ext.awt.geom.Polygon2D;
import java.awt.Font;
import java.awt.font.TextLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.awt.font.FontRenderContext;
import java.io.DataInputStream;

public class WMFHeaderProperties extends AbstractWMFReader
{
    private static final Integer INTEGER_0;
    protected DataInputStream stream;
    private int _bleft;
    private int _bright;
    private int _btop;
    private int _bbottom;
    private int _bwidth;
    private int _bheight;
    private int _ileft;
    private int _iright;
    private int _itop;
    private int _ibottom;
    private float scale;
    private int startX;
    private int startY;
    private int currentHorizAlign;
    private int currentVertAlign;
    private WMFFont wf;
    private static final FontRenderContext fontCtx;
    private transient boolean firstEffectivePaint;
    public static final int PEN = 1;
    public static final int BRUSH = 2;
    public static final int FONT = 3;
    public static final int NULL_PEN = 4;
    public static final int NULL_BRUSH = 5;
    public static final int PALETTE = 6;
    public static final int OBJ_BITMAP = 7;
    public static final int OBJ_REGION = 8;
    
    public WMFHeaderProperties(final File file) throws IOException {
        this.scale = 1.0f;
        this.startX = 0;
        this.startY = 0;
        this.currentHorizAlign = 0;
        this.currentVertAlign = 0;
        this.wf = null;
        this.firstEffectivePaint = true;
        this.reset();
        this.read(this.stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file))));
        this.stream.close();
    }
    
    public WMFHeaderProperties() {
        this.scale = 1.0f;
        this.startX = 0;
        this.startY = 0;
        this.currentHorizAlign = 0;
        this.currentVertAlign = 0;
        this.wf = null;
        this.firstEffectivePaint = true;
    }
    
    public void closeResource() {
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        }
        catch (IOException ex) {}
    }
    
    public void setFile(final File file) throws IOException {
        this.read(this.stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file))));
        this.stream.close();
    }
    
    public void reset() {
        this.left = 0;
        this.right = 0;
        this.top = 1000;
        this.bottom = 1000;
        this.inch = 84;
        this._bleft = -1;
        this._bright = -1;
        this._btop = -1;
        this._bbottom = -1;
        this._ileft = -1;
        this._iright = -1;
        this._itop = -1;
        this._ibottom = -1;
        this._bwidth = -1;
        this._bheight = -1;
        this.vpW = -1;
        this.vpH = -1;
        this.vpX = 0;
        this.vpY = 0;
        this.startX = 0;
        this.startY = 0;
        this.scaleXY = 1.0f;
        this.firstEffectivePaint = true;
    }
    
    public DataInputStream getStream() {
        return this.stream;
    }
    
    protected boolean readRecords(final DataInputStream dataInputStream) throws IOException {
        int i = 1;
        short n = -1;
        short n2 = -1;
        short n3 = -1;
        while (i > 0) {
            int int1 = this.readInt(dataInputStream);
            int1 -= 3;
            i = this.readShort(dataInputStream);
            if (i <= 0) {
                break;
            }
            switch (i) {
                case 259: {
                    if (this.readShort(dataInputStream) == 8) {
                        this.isotropic = false;
                        continue;
                    }
                    continue;
                }
                case 523: {
                    this.vpY = this.readShort(dataInputStream);
                    this.vpX = this.readShort(dataInputStream);
                    continue;
                }
                case 524: {
                    this.vpH = this.readShort(dataInputStream);
                    this.vpW = this.readShort(dataInputStream);
                    if (!this.isotropic) {
                        this.scaleXY = this.vpW / (float)this.vpH;
                    }
                    this.vpW *= (int)this.scaleXY;
                    continue;
                }
                case 762: {
                    final int n4 = 0;
                    final short short1 = this.readShort(dataInputStream);
                    this.readInt(dataInputStream);
                    final int int2 = this.readInt(dataInputStream);
                    final Color color = new Color(int2 & 0xFF, (int2 & 0xFF00) >> 8, (int2 & 0xFF0000) >> 16);
                    if (int1 == 6) {
                        this.readShort(dataInputStream);
                    }
                    if (short1 == 5) {
                        this.addObjectAt(4, color, n4);
                        continue;
                    }
                    this.addObjectAt(1, color, n4);
                    continue;
                }
                case 764: {
                    final int n5 = 0;
                    final short short2 = this.readShort(dataInputStream);
                    final int int3 = this.readInt(dataInputStream);
                    final Color color2 = new Color(int3 & 0xFF, (int3 & 0xFF00) >> 8, (int3 & 0xFF0000) >> 16);
                    this.readShort(dataInputStream);
                    if (short2 == 5) {
                        this.addObjectAt(5, color2, n5);
                        continue;
                    }
                    this.addObjectAt(2, color2, n5);
                    continue;
                }
                case 302: {
                    final short short3 = this.readShort(dataInputStream);
                    if (int1 > 1) {
                        for (int j = 1; j < int1; ++j) {
                            this.readShort(dataInputStream);
                        }
                    }
                    this.currentHorizAlign = WMFUtilities.getHorizontalAlignment(short3);
                    this.currentVertAlign = WMFUtilities.getVerticalAlignment(short3);
                    continue;
                }
                case 2610: {
                    final short short4 = this.readShort(dataInputStream);
                    final int n6 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final short short5 = this.readShort(dataInputStream);
                    final short short6 = this.readShort(dataInputStream);
                    int n7 = 4;
                    if ((short6 & 0x4) != 0x0) {
                        final int n8 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                        this.readShort(dataInputStream);
                        final int n9 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                        this.readShort(dataInputStream);
                        n7 += 4;
                    }
                    final byte[] array = new byte[short5];
                    for (short n10 = 0; n10 < short5; ++n10) {
                        array[n10] = dataInputStream.readByte();
                    }
                    final String decodeString = WMFUtilities.decodeString(this.wf, array);
                    final int n11 = n7 + (short5 + 1) / 2;
                    if (short5 % 2 != 0) {
                        dataInputStream.readByte();
                    }
                    if (n11 < int1) {
                        for (int k = n11; k < int1; ++k) {
                            this.readShort(dataInputStream);
                        }
                    }
                    final TextLayout textLayout = new TextLayout(decodeString, this.wf.font, WMFHeaderProperties.fontCtx);
                    final int n12 = (int)textLayout.getBounds().getWidth();
                    final int n13 = (int)textLayout.getBounds().getX();
                    final int n14 = (int)this.getVerticalAlignmentValue(textLayout, this.currentVertAlign);
                    this.resizeBounds(n13, short4);
                    this.resizeBounds(n13 + n12, short4 + n14);
                    this.firstEffectivePaint = false;
                    continue;
                }
                case 1313:
                case 1583: {
                    final short short7 = this.readShort(dataInputStream);
                    final int n15 = 1;
                    final byte[] array2 = new byte[short7];
                    for (short n16 = 0; n16 < short7; ++n16) {
                        array2[n16] = dataInputStream.readByte();
                    }
                    final String decodeString2 = WMFUtilities.decodeString(this.wf, array2);
                    if (short7 % 2 != 0) {
                        dataInputStream.readByte();
                    }
                    int n17 = n15 + (short7 + 1) / 2;
                    final short short8 = this.readShort(dataInputStream);
                    final int n18 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    n17 += 2;
                    if (n17 < int1) {
                        for (int l = n17; l < int1; ++l) {
                            this.readShort(dataInputStream);
                        }
                    }
                    final TextLayout textLayout2 = new TextLayout(decodeString2, this.wf.font, WMFHeaderProperties.fontCtx);
                    final int n19 = (int)textLayout2.getBounds().getWidth();
                    final int n20 = (int)textLayout2.getBounds().getX();
                    final int n21 = (int)this.getVerticalAlignmentValue(textLayout2, this.currentVertAlign);
                    this.resizeBounds(n20, short8);
                    this.resizeBounds(n20 + n19, short8 + n21);
                    continue;
                }
                case 763: {
                    float size = (float)(int)(this.scaleY * this.readShort(dataInputStream));
                    this.readShort(dataInputStream);
                    final short short9 = this.readShort(dataInputStream);
                    final short short10 = this.readShort(dataInputStream);
                    final short short11 = this.readShort(dataInputStream);
                    final byte byte1 = dataInputStream.readByte();
                    final byte byte2 = dataInputStream.readByte();
                    final byte byte3 = dataInputStream.readByte();
                    final int n22 = dataInputStream.readByte() & 0xFF;
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    final int style = ((byte1 > 0) ? 2 : 0) | ((short11 > 400) ? 1 : 0);
                    final int n23 = 2 * (int1 - 9);
                    final byte[] bytes = new byte[n23];
                    for (int n24 = 0; n24 < n23; ++n24) {
                        bytes[n24] = dataInputStream.readByte();
                    }
                    String s;
                    int endIndex;
                    for (s = new String(bytes), endIndex = 0; endIndex < s.length() && (Character.isLetterOrDigit(s.charAt(endIndex)) || Character.isWhitespace(s.charAt(endIndex))); ++endIndex) {}
                    String substring;
                    if (endIndex > 0) {
                        substring = s.substring(0, endIndex);
                    }
                    else {
                        substring = "System";
                    }
                    if (size < 0.0f) {
                        size = -size;
                    }
                    this.addObjectAt(3, new WMFFont(new Font(substring, style, (int)size).deriveFont(size), n22, byte2, byte3, byte1, short11, short10, short9), 0);
                    continue;
                }
                case 1791: {
                    for (int n25 = 0; n25 < int1; ++n25) {
                        this.readShort(dataInputStream);
                    }
                    this.addObjectAt(6, WMFHeaderProperties.INTEGER_0, 0);
                    continue;
                }
                case 247: {
                    for (int n26 = 0; n26 < int1; ++n26) {
                        this.readShort(dataInputStream);
                    }
                    this.addObjectAt(8, WMFHeaderProperties.INTEGER_0, 0);
                    continue;
                }
                case 301: {
                    final short short12 = this.readShort(dataInputStream);
                    if ((short12 & Integer.MIN_VALUE) != 0x0) {
                        continue;
                    }
                    final GdiObject object = this.getObject(short12);
                    if (!object.used) {
                        continue;
                    }
                    switch (object.type) {
                        case 1: {
                            n2 = short12;
                            continue;
                        }
                        case 2: {
                            n = short12;
                            continue;
                        }
                        case 3: {
                            this.wf = (WMFFont)object.obj;
                            n3 = short12;
                            continue;
                        }
                        case 4: {
                            n2 = -1;
                            continue;
                        }
                        case 5: {
                            n = -1;
                            continue;
                        }
                    }
                    continue;
                }
                case 496: {
                    final short short13 = this.readShort(dataInputStream);
                    final GdiObject object2 = this.getObject(short13);
                    if (short13 == n) {
                        n = -1;
                    }
                    else if (short13 == n2) {
                        n2 = -1;
                    }
                    else if (short13 == n3) {
                        n3 = -1;
                    }
                    object2.clear();
                    continue;
                }
                case 531: {
                    final short short14 = this.readShort(dataInputStream);
                    final int startX = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    if (n2 >= 0) {
                        this.resizeBounds(this.startX, this.startY);
                        this.resizeBounds(startX, short14);
                        this.firstEffectivePaint = false;
                    }
                    this.startX = startX;
                    this.startY = short14;
                    continue;
                }
                case 532: {
                    this.startY = this.readShort(dataInputStream);
                    this.startX = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    continue;
                }
                case 1336: {
                    final short short15 = this.readShort(dataInputStream);
                    final int[] array3 = new int[short15];
                    int n27 = 0;
                    for (short n28 = 0; n28 < short15; ++n28) {
                        array3[n28] = this.readShort(dataInputStream);
                        n27 += array3[n28];
                    }
                    for (short n29 = 0; n29 < short15; ++n29) {
                        for (int n30 = 0; n30 < array3[n29]; ++n30) {
                            final int n31 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                            final short short16 = this.readShort(dataInputStream);
                            if (n >= 0 || n2 >= 0) {
                                this.resizeBounds(n31, short16);
                            }
                        }
                    }
                    this.firstEffectivePaint = false;
                    continue;
                }
                case 804: {
                    final short short17 = this.readShort(dataInputStream);
                    final float[] array4 = new float[short17 + 1];
                    final float[] array5 = new float[short17 + 1];
                    for (short n32 = 0; n32 < short17; ++n32) {
                        array4[n32] = this.readShort(dataInputStream) * this.scaleXY;
                        array5[n32] = this.readShort(dataInputStream);
                    }
                    array4[short17] = array4[0];
                    array5[short17] = array5[0];
                    this.paint(n, n2, new Polygon2D(array4, array5, short17));
                    continue;
                }
                case 805: {
                    final short short18 = this.readShort(dataInputStream);
                    final float[] array6 = new float[short18];
                    final float[] array7 = new float[short18];
                    for (short n33 = 0; n33 < short18; ++n33) {
                        array6[n33] = this.readShort(dataInputStream) * this.scaleXY;
                        array7[n33] = this.readShort(dataInputStream);
                    }
                    this.paintWithPen(n2, new Polyline2D(array6, array7, short18));
                    continue;
                }
                case 1046:
                case 1048:
                case 1051: {
                    final short short19 = this.readShort(dataInputStream);
                    final int n34 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final short short20 = this.readShort(dataInputStream);
                    final int n35 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    this.paint(n, n2, new Rectangle2D.Float((float)n35, short20, (float)(n34 - n35), (float)(short19 - short20)));
                    continue;
                }
                case 1564: {
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final short short21 = this.readShort(dataInputStream);
                    final int n36 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final short short22 = this.readShort(dataInputStream);
                    final int n37 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    this.paint(n, n2, new Rectangle2D.Float((float)n37, short22, (float)(n36 - n37), (float)(short21 - short22)));
                    continue;
                }
                case 2071:
                case 2074:
                case 2096: {
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final short short23 = this.readShort(dataInputStream);
                    final int n38 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final short short24 = this.readShort(dataInputStream);
                    final int n39 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    this.paint(n, n2, new Rectangle2D.Float((float)n39, short24, (float)(n38 - n39), (float)(short23 - short24)));
                    continue;
                }
                case 1565: {
                    this.readInt(dataInputStream);
                    final short short25 = this.readShort(dataInputStream);
                    final int n40 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final int n41 = (int)(this.readShort(dataInputStream) * this.scaleXY);
                    final short short26 = this.readShort(dataInputStream);
                    if (n2 >= 0) {
                        this.resizeBounds(n41, short26);
                    }
                    if (n2 >= 0) {
                        this.resizeBounds(n41 + n40, short26 + short25);
                        continue;
                    }
                    continue;
                }
                case 2881: {
                    dataInputStream.readInt();
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final float n42 = this.readShort(dataInputStream);
                    final float n43 = this.readShort(dataInputStream) * this.scaleXY;
                    final float n44 = this.readShort(dataInputStream) * this.getVpWFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    final float n45 = this.readShort(dataInputStream) * this.getVpWFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.scaleXY;
                    final float n46 = n43 * this.getVpWFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    final float n47 = n42 * this.getVpHFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    this.resizeImageBounds((int)n45, (int)n44);
                    this.resizeImageBounds((int)(n45 + n46), (int)(n44 + n47));
                    for (int n48 = 2 * int1 - 20, n49 = 0; n49 < n48; ++n49) {
                        dataInputStream.readByte();
                    }
                    continue;
                }
                case 3907: {
                    dataInputStream.readInt();
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final float n50 = this.readShort(dataInputStream);
                    final float n51 = this.readShort(dataInputStream) * this.scaleXY;
                    final float n52 = this.readShort(dataInputStream) * this.getVpHFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    final float n53 = this.readShort(dataInputStream) * this.getVpHFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.scaleXY;
                    final float n54 = n51 * this.getVpWFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    final float n55 = n50 * this.getVpHFactor() * this.inch / WMFHeaderProperties.PIXEL_PER_INCH;
                    this.resizeImageBounds((int)n53, (int)n52);
                    this.resizeImageBounds((int)(n53 + n54), (int)(n52 + n55));
                    final int n56 = 2 * int1 - 22;
                    final byte[] array8 = new byte[n56];
                    for (int n57 = 0; n57 < n56; ++n57) {
                        array8[n57] = dataInputStream.readByte();
                    }
                    continue;
                }
                case 2368: {
                    dataInputStream.readInt();
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final float n58 = this.readShort(dataInputStream) * (float)this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.getVpHFactor();
                    final float n59 = this.readShort(dataInputStream) * (float)this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.getVpWFactor() * this.scaleXY;
                    final float n60 = this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.getVpHFactor() * this.readShort(dataInputStream);
                    final float n61 = this.inch / WMFHeaderProperties.PIXEL_PER_INCH * this.getVpWFactor() * this.readShort(dataInputStream) * this.scaleXY;
                    this.resizeImageBounds((int)n61, (int)n60);
                    this.resizeImageBounds((int)(n61 + n59), (int)(n60 + n58));
                    continue;
                }
                default: {
                    for (int n62 = 0; n62 < int1; ++n62) {
                        this.readShort(dataInputStream);
                    }
                    continue;
                }
            }
        }
        if (!this.isAldus) {
            this.width = this.vpW;
            this.height = this.vpH;
            this.right = this.vpX;
            this.left = this.vpX + this.vpW;
            this.top = this.vpY;
            this.bottom = this.vpY + this.vpH;
        }
        this.resetBounds();
        return true;
    }
    
    public int getWidthBoundsPixels() {
        return this._bwidth;
    }
    
    public int getHeightBoundsPixels() {
        return this._bheight;
    }
    
    public int getWidthBoundsUnits() {
        return (int)(this.inch * (float)this._bwidth / WMFHeaderProperties.PIXEL_PER_INCH);
    }
    
    public int getHeightBoundsUnits() {
        return (int)(this.inch * (float)this._bheight / WMFHeaderProperties.PIXEL_PER_INCH);
    }
    
    public int getXOffset() {
        return this._bleft;
    }
    
    public int getYOffset() {
        return this._btop;
    }
    
    private void resetBounds() {
        this.scale = this.getWidthPixels() / (float)this.vpW;
        if (this._bright != -1) {
            this._bright = (int)(this.scale * (this.vpX + this._bright));
            this._bleft = (int)(this.scale * (this.vpX + this._bleft));
            this._bbottom = (int)(this.scale * (this.vpY + this._bbottom));
            this._btop = (int)(this.scale * (this.vpY + this._btop));
        }
        if (this._iright != -1) {
            this._iright = (int)(this._iright * (float)this.getWidthPixels() / this.width);
            this._ileft = (int)(this._ileft * (float)this.getWidthPixels() / this.width);
            this._ibottom = (int)(this._ibottom * (float)this.getWidthPixels() / this.width);
            this._itop = (int)(this._itop * (float)this.getWidthPixels() / this.width);
            if (this._bright == -1 || this._iright > this._bright) {
                this._bright = this._iright;
            }
            if (this._bleft == -1 || this._ileft < this._bleft) {
                this._bleft = this._ileft;
            }
            if (this._btop == -1 || this._itop < this._btop) {
                this._btop = this._itop;
            }
            if (this._bbottom == -1 || this._ibottom > this._bbottom) {
                this._bbottom = this._ibottom;
            }
        }
        if (this._bleft != -1 && this._bright != -1) {
            this._bwidth = this._bright - this._bleft;
        }
        if (this._btop != -1 && this._bbottom != -1) {
            this._bheight = this._bbottom - this._btop;
        }
    }
    
    private void resizeBounds(final int n, final int n2) {
        if (this._bleft == -1) {
            this._bleft = n;
        }
        else if (n < this._bleft) {
            this._bleft = n;
        }
        if (this._bright == -1) {
            this._bright = n;
        }
        else if (n > this._bright) {
            this._bright = n;
        }
        if (this._btop == -1) {
            this._btop = n2;
        }
        else if (n2 < this._btop) {
            this._btop = n2;
        }
        if (this._bbottom == -1) {
            this._bbottom = n2;
        }
        else if (n2 > this._bbottom) {
            this._bbottom = n2;
        }
    }
    
    private void resizeImageBounds(final int n, final int n2) {
        if (this._ileft == -1) {
            this._ileft = n;
        }
        else if (n < this._ileft) {
            this._ileft = n;
        }
        if (this._iright == -1) {
            this._iright = n;
        }
        else if (n > this._iright) {
            this._iright = n;
        }
        if (this._itop == -1) {
            this._itop = n2;
        }
        else if (n2 < this._itop) {
            this._itop = n2;
        }
        if (this._ibottom == -1) {
            this._ibottom = n2;
        }
        else if (n2 > this._ibottom) {
            this._ibottom = n2;
        }
    }
    
    private Color getColorFromObject(final int n) {
        if (n >= 0) {
            return (Color)this.getObject(n).obj;
        }
        return null;
    }
    
    private void paint(final int n, final int n2, final Shape shape) {
        if (n >= 0 || n2 >= 0) {
            Color color;
            if (n >= 0) {
                color = this.getColorFromObject(n);
            }
            else {
                color = this.getColorFromObject(n2);
            }
            if (!this.firstEffectivePaint || !color.equals(Color.white)) {
                final Rectangle bounds = shape.getBounds();
                this.resizeBounds((int)bounds.getMinX(), (int)bounds.getMinY());
                this.resizeBounds((int)bounds.getMaxX(), (int)bounds.getMaxY());
                this.firstEffectivePaint = false;
            }
        }
    }
    
    private void paintWithPen(final int n, final Shape shape) {
        if (n >= 0) {
            final Color colorFromObject = this.getColorFromObject(n);
            if (!this.firstEffectivePaint || !colorFromObject.equals(Color.white)) {
                final Rectangle bounds = shape.getBounds();
                this.resizeBounds((int)bounds.getMinX(), (int)bounds.getMinY());
                this.resizeBounds((int)bounds.getMaxX(), (int)bounds.getMaxY());
                this.firstEffectivePaint = false;
            }
        }
    }
    
    private float getVerticalAlignmentValue(final TextLayout textLayout, final int n) {
        if (n == 24) {
            return -textLayout.getAscent();
        }
        if (n == 0) {
            return textLayout.getAscent() + textLayout.getDescent();
        }
        return 0.0f;
    }
    
    static {
        INTEGER_0 = new Integer(0);
        fontCtx = new FontRenderContext(new AffineTransform(), false, true);
    }
}
