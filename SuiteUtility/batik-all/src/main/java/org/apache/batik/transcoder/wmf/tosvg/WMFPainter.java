// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.awt.Toolkit;
import java.util.Iterator;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.awt.Dimension;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.font.FontRenderContext;
import java.awt.geom.Arc2D;
import java.awt.font.TextLayout;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.geom.Polyline2D;
import java.awt.geom.Line2D;
import java.awt.Shape;
import java.util.List;
import org.apache.batik.ext.awt.geom.Polygon2D;
import java.util.ArrayList;
import java.awt.Paint;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.util.Stack;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.awt.image.ImageObserver;
import java.awt.BasicStroke;
import java.awt.Color;

public class WMFPainter extends AbstractWMFPainter
{
    private static final int INPUT_BUFFER_SIZE = 30720;
    private static final Integer INTEGER_0;
    private float scale;
    private float scaleX;
    private float scaleY;
    private float conv;
    private float xOffset;
    private float yOffset;
    private float vpX;
    private float vpY;
    private float vpW;
    private float vpH;
    private Color frgdColor;
    private Color bkgdColor;
    private boolean opaque;
    private transient boolean firstEffectivePaint;
    private static BasicStroke solid;
    private static BasicStroke textSolid;
    private transient ImageObserver observer;
    private transient BufferedInputStream bufStream;
    
    public WMFPainter(final WMFRecordStore wmfRecordStore, final float n) {
        this(wmfRecordStore, 0, 0, n);
    }
    
    public WMFPainter(final WMFRecordStore recordStore, final int n, final int n2, final float conv) {
        this.opaque = false;
        this.firstEffectivePaint = true;
        this.observer = new ImageObserver() {
            public boolean imageUpdate(final Image image, final int n, final int n2, final int n3, final int n4, final int n5) {
                return false;
            }
        };
        this.bufStream = null;
        this.setRecordStore(recordStore);
        TextureFactory.getInstance().reset();
        this.conv = conv;
        this.xOffset = (float)(-n);
        this.yOffset = (float)(-n2);
        this.scale = recordStore.getWidthPixels() / (float)recordStore.getWidthUnits() * conv;
        this.scale = this.scale * recordStore.getWidthPixels() / recordStore.getVpW();
        final float n3 = recordStore.getVpW() / (float)recordStore.getWidthPixels() * recordStore.getWidthUnits() / recordStore.getWidthPixels();
        final float n4 = recordStore.getVpH() / (float)recordStore.getHeightPixels() * recordStore.getHeightUnits() / recordStore.getHeightPixels();
        this.xOffset *= n3;
        this.yOffset *= n4;
        this.scaleX = this.scale;
        this.scaleY = this.scale;
    }
    
    public void paint(final Graphics graphics) {
        float floatValue = 0.0f;
        float floatValue2 = 0.0f;
        float floatValue3 = 0.0f;
        final Stack<Float> stack = new Stack<Float>();
        final int numRecords = this.currentStore.getNumRecords();
        final int numObjects = this.currentStore.getNumObjects();
        this.vpX = this.currentStore.getVpX() * this.scale;
        this.vpY = this.currentStore.getVpY() * this.scale;
        this.vpW = this.currentStore.getVpW() * this.scale;
        this.vpH = this.currentStore.getVpH() * this.scale;
        if (!this.currentStore.isReading()) {
            graphics.setPaintMode();
            final Graphics2D graphics2D = (Graphics2D)graphics;
            graphics2D.setStroke(WMFPainter.solid);
            int intValue = -1;
            int intValue2 = -1;
            int intValue3 = -1;
            this.frgdColor = null;
            this.bkgdColor = Color.white;
            for (int i = 0; i < numObjects; ++i) {
                this.currentStore.getObject(i).clear();
            }
            final float vpW = this.vpW;
            final float vpH = this.vpH;
            graphics2D.setColor(Color.black);
            for (int j = 0; j < numRecords; ++j) {
                final MetaRecord record = this.currentStore.getRecord(j);
                switch (record.functionId) {
                    case 523: {
                        this.currentStore.setVpX(this.vpX = -(float)record.elementAt(0));
                        this.currentStore.setVpY(this.vpY = -(float)record.elementAt(1));
                        this.vpX *= this.scale;
                        this.vpY *= this.scale;
                        break;
                    }
                    case 0:
                    case 524: {
                        this.vpW = (float)record.elementAt(0);
                        this.vpH = (float)record.elementAt(1);
                        this.scaleX = this.scale;
                        this.scaleY = this.scale;
                        WMFPainter.solid = new BasicStroke(this.scaleX * 2.0f, 0, 1);
                    }
                    case 525:
                    case 526:
                    case 527:
                    case 529:
                    case 1040:
                    case 1042: {}
                    case 762: {
                        final int n = 0;
                        final int element = record.elementAt(0);
                        if (element == 5) {
                            this.addObjectAt(this.currentStore, 4, Color.white, n);
                            break;
                        }
                        floatValue = (float)record.elementAt(4);
                        this.setStroke(graphics2D, element, floatValue, this.scaleX);
                        this.addObjectAt(this.currentStore, 1, new Color(record.elementAt(1), record.elementAt(2), record.elementAt(3)), n);
                        break;
                    }
                    case 764: {
                        final int n2 = 0;
                        final int element2 = record.elementAt(0);
                        final Color color = new Color(record.elementAt(1), record.elementAt(2), record.elementAt(3));
                        if (element2 == 0) {
                            this.addObjectAt(this.currentStore, 2, color, n2);
                            break;
                        }
                        if (element2 != 2) {
                            this.addObjectAt(this.currentStore, 5, Color.black, n2);
                            break;
                        }
                        final int element3 = record.elementAt(4);
                        Paint paint;
                        if (!this.opaque) {
                            paint = TextureFactory.getInstance().getTexture(element3, color);
                        }
                        else {
                            paint = TextureFactory.getInstance().getTexture(element3, color, this.bkgdColor);
                        }
                        if (paint != null) {
                            this.addObjectAt(this.currentStore, 2, paint, n2);
                            break;
                        }
                        this.addObjectAt(this.currentStore, 5, Color.black, n2);
                        break;
                    }
                    case 763: {
                        float size = (float)(int)(this.scaleY * record.elementAt(0));
                        final int element4 = record.elementAt(3);
                        final int element5 = record.elementAt(1);
                        final int element6 = record.elementAt(2);
                        final int style = ((element5 > 0) ? 2 : 0) | ((element6 > 400) ? 1 : 0);
                        String text;
                        int endIndex;
                        for (text = ((MetaRecord.StringRecord)record).text, endIndex = 0; endIndex < text.length() && (Character.isLetterOrDigit(text.charAt(endIndex)) || Character.isWhitespace(text.charAt(endIndex))); ++endIndex) {}
                        String substring;
                        if (endIndex > 0) {
                            substring = text.substring(0, endIndex);
                        }
                        else {
                            substring = "System";
                        }
                        if (size < 0.0f) {
                            size = -size;
                        }
                        this.addObjectAt(this.currentStore, 3, new WMFFont(new Font(substring, style, (int)size).deriveFont(size), element4, record.elementAt(4), record.elementAt(5), element5, element6, record.elementAt(6), record.elementAt(7)), 0);
                        break;
                    }
                    case 248:
                    case 505:
                    case 765:
                    case 1790:
                    case 1791: {
                        this.addObjectAt(this.currentStore, 6, WMFPainter.INTEGER_0, 0);
                        break;
                    }
                    case 247: {
                        this.addObjectAt(this.currentStore, 8, WMFPainter.INTEGER_0, 0);
                    }
                    case 301: {
                        final int element7 = record.elementAt(0);
                        if ((element7 & Integer.MIN_VALUE) != 0x0) {
                            break;
                        }
                        if (element7 >= numObjects) {
                            switch (element7 - numObjects) {
                                case 5: {
                                    intValue = -1;
                                    break;
                                }
                                case 8: {
                                    intValue2 = -1;
                                    break;
                                }
                            }
                            break;
                        }
                        final GdiObject object = this.currentStore.getObject(element7);
                        if (!object.used) {
                            break;
                        }
                        switch (object.type) {
                            case 1: {
                                graphics2D.setColor((Color)object.obj);
                                intValue2 = element7;
                                break;
                            }
                            case 2: {
                                if (object.obj instanceof Color) {
                                    graphics2D.setColor((Color)object.obj);
                                }
                                else if (object.obj instanceof Paint) {
                                    graphics2D.setPaint((Paint)object.obj);
                                }
                                else {
                                    graphics2D.setPaint(this.getPaint((byte[])object.obj));
                                }
                                intValue = element7;
                                break;
                            }
                            case 3: {
                                this.wmfFont = (WMFFont)object.obj;
                                graphics2D.setFont(this.wmfFont.font);
                                intValue3 = element7;
                                break;
                            }
                            case 4: {
                                intValue2 = -1;
                                break;
                            }
                            case 5: {
                                intValue = -1;
                                break;
                            }
                        }
                        break;
                    }
                    case 496: {
                        final int element8 = record.elementAt(0);
                        final GdiObject object2 = this.currentStore.getObject(element8);
                        if (element8 == intValue) {
                            intValue = -1;
                        }
                        else if (element8 == intValue2) {
                            intValue2 = -1;
                        }
                        else if (element8 == intValue3) {
                            intValue3 = -1;
                        }
                        object2.clear();
                        break;
                    }
                    case 1336: {
                        final int element9 = record.elementAt(0);
                        final int[] array = new int[element9];
                        for (int k = 0; k < element9; ++k) {
                            array[k] = record.elementAt(k + 1);
                        }
                        int n3 = element9 + 1;
                        final ArrayList list = new ArrayList<Polygon2D>(element9);
                        for (final int n4 : array) {
                            final float[] array2 = new float[n4];
                            final float[] array3 = new float[n4];
                            for (int n5 = 0; n5 < n4; ++n5) {
                                array2[n5] = this.scaleX * (this.vpX + this.xOffset + record.elementAt(n3 + n5 * 2));
                                array3[n5] = this.scaleY * (this.vpY + this.yOffset + record.elementAt(n3 + n5 * 2 + 1));
                            }
                            n3 += n4 * 2;
                            list.add(new Polygon2D(array2, array3, n4));
                        }
                        if (intValue >= 0) {
                            this.setBrushPaint(this.currentStore, graphics2D, intValue);
                            this.fillPolyPolygon(graphics2D, list);
                            this.firstEffectivePaint = false;
                        }
                        if (intValue2 >= 0) {
                            this.setPenColor(this.currentStore, graphics2D, intValue2);
                            this.drawPolyPolygon(graphics2D, list);
                            this.firstEffectivePaint = false;
                            break;
                        }
                        break;
                    }
                    case 804: {
                        final int element10 = record.elementAt(0);
                        final float[] array4 = new float[element10];
                        final float[] array5 = new float[element10];
                        for (int n6 = 0; n6 < element10; ++n6) {
                            array4[n6] = this.scaleX * (this.vpX + this.xOffset + record.elementAt(n6 * 2 + 1));
                            array5[n6] = this.scaleY * (this.vpY + this.yOffset + record.elementAt(n6 * 2 + 2));
                        }
                        this.paint(intValue, intValue2, new Polygon2D(array4, array5, element10), graphics2D);
                        break;
                    }
                    case 532: {
                        floatValue2 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        floatValue3 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        break;
                    }
                    case 531: {
                        final float x2 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final float y2 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        this.paintWithPen(intValue2, new Line2D.Float(floatValue2, floatValue3, x2, y2), graphics2D);
                        floatValue2 = x2;
                        floatValue3 = y2;
                        break;
                    }
                    case 805: {
                        final int element11 = record.elementAt(0);
                        final float[] array6 = new float[element11];
                        final float[] array7 = new float[element11];
                        for (int n7 = 0; n7 < element11; ++n7) {
                            array6[n7] = this.scaleX * (this.vpX + this.xOffset + record.elementAt(n7 * 2 + 1));
                            array7[n7] = this.scaleY * (this.vpY + this.yOffset + record.elementAt(n7 * 2 + 2));
                        }
                        this.paintWithPen(intValue2, new Polyline2D(array6, array7, element11), graphics2D);
                        break;
                    }
                    case 1051: {
                        final float x3 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final float n8 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(2));
                        final float y3 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        this.paint(intValue, intValue2, new Rectangle2D.Float(x3, y3, n8 - x3, this.scaleY * (this.vpY + this.yOffset + record.elementAt(3)) - y3), graphics2D);
                        break;
                    }
                    case 1564: {
                        final float x4 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final float n9 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(2));
                        final float arcw = this.scaleX * record.elementAt(4);
                        final float y4 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        this.paint(intValue, intValue2, new RoundRectangle2D.Float(x4, y4, n9 - x4, this.scaleY * (this.vpY + this.yOffset + record.elementAt(3)) - y4, arcw, this.scaleY * record.elementAt(5)), graphics2D);
                        break;
                    }
                    case 1048: {
                        final float x5 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final float n10 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(2));
                        final float y5 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        this.paint(intValue, intValue2, new Ellipse2D.Float(x5, y5, n10 - x5, this.scaleY * (this.vpY + this.yOffset + record.elementAt(3)) - y5), graphics2D);
                        break;
                    }
                    case 302: {
                        this.currentHorizAlign = WMFUtilities.getHorizontalAlignment(record.elementAt(0));
                        this.currentVertAlign = WMFUtilities.getVerticalAlignment(record.elementAt(0));
                        break;
                    }
                    case 521: {
                        graphics2D.setColor(this.frgdColor = new Color(record.elementAt(0), record.elementAt(1), record.elementAt(2)));
                        break;
                    }
                    case 513: {
                        graphics2D.setColor(this.bkgdColor = new Color(record.elementAt(0), record.elementAt(1), record.elementAt(2)));
                        break;
                    }
                    case 2610: {
                        try {
                            final String decodeString = WMFUtilities.decodeString(this.wmfFont, ((MetaRecord.ByteRecord)record).bstr);
                            final float n11 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                            final float n12 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                            if (this.frgdColor != null) {
                                graphics2D.setColor(this.frgdColor);
                            }
                            else {
                                graphics2D.setColor(Color.black);
                            }
                            final FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
                            final Point2D.Double double1 = new Point2D.Double(0.0, 0.0);
                            final GeneralPath generalPath = new GeneralPath(1);
                            final TextLayout textLayout = new TextLayout(decodeString, graphics2D.getFont(), fontRenderContext);
                            final int element12 = record.elementAt(2);
                            boolean b = false;
                            Shape clip = null;
                            if ((element12 & 0x4) != 0x0) {
                                b = true;
                                final int element13 = record.elementAt(3);
                                final int element14 = record.elementAt(4);
                                final int element15 = record.elementAt(5);
                                final int element16 = record.elementAt(6);
                                clip = graphics2D.getClip();
                                graphics2D.setClip(element13, element14, element15, element16);
                            }
                            this.firstEffectivePaint = false;
                            this.drawString(element12, graphics2D, this.getCharacterIterator(graphics2D, decodeString, this.wmfFont, this.currentHorizAlign), n11, n12 + this.getVerticalAlignmentValue(textLayout, this.currentVertAlign), textLayout, this.wmfFont, this.currentHorizAlign);
                            if (b) {
                                graphics2D.setClip(clip);
                            }
                        }
                        catch (Exception ex) {}
                        break;
                    }
                    case 1313:
                    case 1583: {
                        try {
                            final String decodeString2 = WMFUtilities.decodeString(this.wmfFont, ((MetaRecord.ByteRecord)record).bstr);
                            final float n13 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                            final float n14 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                            if (this.frgdColor != null) {
                                graphics2D.setColor(this.frgdColor);
                            }
                            else {
                                graphics2D.setColor(Color.black);
                            }
                            final FontRenderContext fontRenderContext2 = graphics2D.getFontRenderContext();
                            final Point2D.Double double2 = new Point2D.Double(0.0, 0.0);
                            final GeneralPath generalPath2 = new GeneralPath(1);
                            final TextLayout textLayout2 = new TextLayout(decodeString2, graphics2D.getFont(), fontRenderContext2);
                            this.firstEffectivePaint = false;
                            this.drawString(-1, graphics2D, this.getCharacterIterator(graphics2D, decodeString2, this.wmfFont), n13, n14 + this.getVerticalAlignmentValue(textLayout2, this.currentVertAlign), textLayout2, this.wmfFont, this.currentHorizAlign);
                        }
                        catch (Exception ex2) {}
                        break;
                    }
                    case 2071:
                    case 2074: {
                        final double x6 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final double y6 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        final double n15 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(2));
                        final double n16 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(3));
                        final double n17 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(4));
                        final double n18 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(5));
                        final double n19 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(6));
                        final double n20 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(7));
                        this.setBrushPaint(this.currentStore, graphics2D, intValue);
                        final double n21 = x6 + (n15 - x6) / 2.0;
                        final double n22 = y6 + (n16 - y6) / 2.0;
                        double start = -Math.toDegrees(Math.atan2(n18 - n22, n17 - n21));
                        double extent = -Math.toDegrees(Math.atan2(n20 - n22, n19 - n21)) - start;
                        if (extent < 0.0) {
                            extent += 360.0;
                        }
                        if (start < 0.0) {
                            start += 360.0;
                        }
                        final Arc2D.Double double3 = new Arc2D.Double(x6, y6, n15 - x6, n16 - y6, start, extent, 0);
                        if (record.functionId == 2071) {
                            graphics2D.draw(double3);
                        }
                        else {
                            graphics2D.fill(double3);
                        }
                        this.firstEffectivePaint = false;
                        break;
                    }
                    case 2096: {
                        final double x7 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(0));
                        final double y7 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(1));
                        final double n23 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(2));
                        final double n24 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(3));
                        final double n25 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(4));
                        final double n26 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(5));
                        final double n27 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(6));
                        final double n28 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(7));
                        this.setBrushPaint(this.currentStore, graphics2D, intValue);
                        final double n29 = x7 + (n23 - x7) / 2.0;
                        final double n30 = y7 + (n24 - y7) / 2.0;
                        double start2 = -Math.toDegrees(Math.atan2(n26 - n30, n25 - n29));
                        double extent2 = -Math.toDegrees(Math.atan2(n28 - n30, n27 - n29)) - start2;
                        if (extent2 < 0.0) {
                            extent2 += 360.0;
                        }
                        if (start2 < 0.0) {
                            start2 += 360.0;
                        }
                        this.paint(intValue, intValue2, new Arc2D.Double(x7, y7, n23 - x7, n24 - y7, start2, extent2, 1), graphics2D);
                        this.firstEffectivePaint = false;
                        break;
                    }
                    case 30: {
                        stack.push(new Float(floatValue));
                        stack.push(new Float(floatValue2));
                        stack.push(new Float(floatValue3));
                        stack.push((Float)(Object)new Integer(intValue));
                        stack.push((Float)(Object)new Integer(intValue2));
                        stack.push((Float)(Object)new Integer(intValue3));
                        stack.push((Float)this.frgdColor);
                        stack.push((Float)this.bkgdColor);
                        break;
                    }
                    case 295: {
                        this.bkgdColor = (Color)stack.pop();
                        this.frgdColor = (Color)stack.pop();
                        intValue3 = (Integer)(Object)stack.pop();
                        intValue2 = (Integer)(Object)stack.pop();
                        intValue = (Integer)(Object)stack.pop();
                        floatValue3 = stack.pop();
                        floatValue2 = stack.pop();
                        floatValue = stack.pop();
                        break;
                    }
                    case 4096: {
                        try {
                            this.setPenColor(this.currentStore, graphics2D, intValue2);
                            final int n31 = (record.elementAt(0) - 1) / 3;
                            final float x8 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(1));
                            final float y8 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(2));
                            final GeneralPath generalPath3 = new GeneralPath(1);
                            generalPath3.moveTo(x8, y8);
                            for (int n32 = 0; n32 < n31; ++n32) {
                                final int n33 = n32 * 6;
                                generalPath3.curveTo(this.scaleX * (this.vpX + this.xOffset + record.elementAt(n33 + 3)), this.scaleY * (this.vpY + this.yOffset + record.elementAt(n33 + 4)), this.scaleX * (this.vpX + this.xOffset + record.elementAt(n33 + 5)), this.scaleY * (this.vpY + this.yOffset + record.elementAt(n33 + 6)), this.scaleX * (this.vpX + this.xOffset + record.elementAt(n33 + 7)), this.scaleY * (this.vpY + this.yOffset + record.elementAt(n33 + 8)));
                            }
                            graphics2D.setStroke(WMFPainter.solid);
                            graphics2D.draw(generalPath3);
                            this.firstEffectivePaint = false;
                        }
                        catch (Exception ex3) {}
                    }
                    case 258: {
                        this.opaque = (record.elementAt(0) == 2);
                        break;
                    }
                    case 260: {
                        final float n34 = record.ElementAt(0);
                        Paint paint2 = null;
                        boolean b2 = false;
                        if (n34 == 66.0f) {
                            paint2 = Color.black;
                            b2 = true;
                        }
                        else if (n34 == 1.6711778E7f) {
                            paint2 = Color.white;
                            b2 = true;
                        }
                        else if (n34 == 1.5728673E7f && intValue >= 0) {
                            paint2 = this.getStoredPaint(this.currentStore, intValue);
                            b2 = true;
                        }
                        if (!b2) {
                            break;
                        }
                        if (paint2 != null) {
                            graphics2D.setPaint(paint2);
                            break;
                        }
                        this.setBrushPaint(this.currentStore, graphics2D, intValue);
                        break;
                    }
                    case 1565: {
                        final float n35 = (float)record.elementAt(0);
                        final float h = this.scaleY * record.elementAt(1);
                        final float w = this.scaleX * record.elementAt(2);
                        final float x9 = this.scaleX * (this.vpX + this.xOffset + record.elementAt(3));
                        final float y9 = this.scaleY * (this.vpY + this.yOffset + record.elementAt(4));
                        Paint paint3 = null;
                        boolean b3 = false;
                        if (n35 == 66.0f) {
                            paint3 = Color.black;
                            b3 = true;
                        }
                        else if (n35 == 1.6711778E7f) {
                            paint3 = Color.white;
                            b3 = true;
                        }
                        else if (n35 == 1.5728673E7f && intValue >= 0) {
                            paint3 = this.getStoredPaint(this.currentStore, intValue);
                            b3 = true;
                        }
                        if (b3) {
                            final Color color2 = graphics2D.getColor();
                            if (paint3 != null) {
                                graphics2D.setPaint(paint3);
                            }
                            else {
                                this.setBrushPaint(this.currentStore, graphics2D, intValue);
                            }
                            graphics2D.fill(new Rectangle2D.Float(x9, y9, w, h));
                            graphics2D.setColor(color2);
                            break;
                        }
                        break;
                    }
                    case 2881: {
                        final int element17 = record.elementAt(1);
                        final int element18 = record.elementAt(2);
                        final int element19 = record.elementAt(3);
                        final int element20 = record.elementAt(4);
                        final float n36 = this.conv * this.currentStore.getVpWFactor() * (this.vpY + this.yOffset + record.elementAt(7));
                        final float n37 = this.conv * this.currentStore.getVpHFactor() * (this.vpX + this.xOffset + record.elementAt(8));
                        final float n38 = (float)record.elementAt(5);
                        final float n39 = record.elementAt(6) * this.conv * this.currentStore.getVpWFactor();
                        final float n40 = n38 * this.conv * this.currentStore.getVpHFactor();
                        final BufferedImage image = this.getImage(((MetaRecord.ByteRecord)record).bstr, element18, element17);
                        if (image != null) {
                            graphics2D.drawImage(image, (int)n37, (int)n36, (int)(n37 + n39), (int)(n36 + n40), element20, element19, element20 + element18, element19 + element17, this.bkgdColor, this.observer);
                            break;
                        }
                        break;
                    }
                    case 3907: {
                        final int element21 = record.elementAt(1);
                        final int element22 = record.elementAt(2);
                        final int element23 = record.elementAt(3);
                        final int element24 = record.elementAt(4);
                        final float n41 = this.conv * this.currentStore.getVpWFactor() * (this.vpY + this.yOffset + record.elementAt(7));
                        final float n42 = this.conv * this.currentStore.getVpHFactor() * (this.vpX + this.xOffset + record.elementAt(8));
                        final float n43 = (float)record.elementAt(5);
                        final float n44 = record.elementAt(6) * this.conv * this.currentStore.getVpWFactor();
                        final float n45 = n43 * this.conv * this.currentStore.getVpHFactor();
                        final BufferedImage image2 = this.getImage(((MetaRecord.ByteRecord)record).bstr, element22, element21);
                        if (image2 == null) {
                            break;
                        }
                        if (this.opaque) {
                            graphics2D.drawImage(image2, (int)n42, (int)n41, (int)(n42 + n44), (int)(n41 + n45), element24, element23, element24 + element22, element23 + element21, this.bkgdColor, this.observer);
                            break;
                        }
                        graphics2D.drawImage(image2, (int)n42, (int)n41, (int)(n42 + n44), (int)(n41 + n45), element24, element23, element24 + element22, element23 + element21, this.observer);
                        break;
                    }
                    case 2368: {
                        record.ElementAt(0);
                        final float h2 = record.ElementAt(1) * this.conv * this.currentStore.getVpWFactor();
                        final float w2 = record.ElementAt(2) * this.conv * this.currentStore.getVpHFactor();
                        final int intValue4 = record.ElementAt(3);
                        final int intValue5 = record.ElementAt(4);
                        final float y10 = this.conv * this.currentStore.getVpWFactor() * (this.vpY + this.yOffset + record.ElementAt(5));
                        final float x10 = this.conv * this.currentStore.getVpHFactor() * (this.vpX + this.xOffset + record.ElementAt(6));
                        if (record instanceof MetaRecord.ByteRecord) {
                            final BufferedImage image3 = this.getImage(((MetaRecord.ByteRecord)record).bstr);
                            if (image3 == null) {
                                break;
                            }
                            final int width = image3.getWidth();
                            final int height = image3.getHeight();
                            if (this.opaque) {
                                graphics2D.drawImage(image3, (int)x10, (int)y10, (int)(x10 + w2), (int)(y10 + h2), intValue5, intValue4, intValue5 + width, intValue4 + height, this.bkgdColor, this.observer);
                                break;
                            }
                            graphics2D.drawImage(image3, (int)x10, (int)y10, (int)(x10 + w2), (int)(y10 + h2), intValue5, intValue4, intValue5 + width, intValue4 + height, this.observer);
                            break;
                        }
                        else {
                            if (this.opaque) {
                                final Color color3 = graphics2D.getColor();
                                graphics2D.setColor(this.bkgdColor);
                                graphics2D.fill(new Rectangle2D.Float(x10, y10, w2, h2));
                                graphics2D.setColor(color3);
                                break;
                            }
                            break;
                        }
                        break;
                    }
                    case 322: {
                        this.addObjectAt(this.currentStore, 2, ((MetaRecord.ByteRecord)record).bstr, 0);
                        break;
                    }
                }
            }
        }
    }
    
    private Paint getPaint(final byte[] array) {
        final Dimension imageDimension = this.getImageDimension(array);
        return new TexturePaint(this.getImage(array), new Rectangle2D.Float(0.0f, 0.0f, (float)imageDimension.width, (float)imageDimension.height));
    }
    
    private void drawString(final int n, final Graphics2D graphics2D, final AttributedCharacterIterator attributedCharacterIterator, final float n2, final float n3, final TextLayout textLayout, final WMFFont wmfFont, final int n4) {
        if (wmfFont.escape == 0) {
            if (n != -1) {
                this.fillTextBackground(-1, n, graphics2D, n2, n3, 0.0f, textLayout);
            }
            final float n5 = (float)textLayout.getBounds().getWidth();
            if (n4 == 6) {
                graphics2D.drawString(attributedCharacterIterator, n2 - n5 / 2.0f, n3);
            }
            else if (n4 == 2) {
                graphics2D.drawString(attributedCharacterIterator, n2 - n5, n3);
            }
            else {
                graphics2D.drawString(attributedCharacterIterator, n2, n3);
            }
        }
        else {
            final AffineTransform transform = graphics2D.getTransform();
            final float n6 = -(float)(wmfFont.escape * 3.141592653589793 / 1800.0);
            final float n7 = (float)textLayout.getBounds().getWidth();
            final float n8 = (float)textLayout.getBounds().getHeight();
            if (n4 == 6) {
                graphics2D.translate(-n7 / 2.0f, n8 / 2.0f);
                graphics2D.rotate(n6, n2 - n7 / 2.0f, n3);
            }
            else if (n4 == 2) {
                graphics2D.translate(-n7 / 2.0f, n8 / 2.0f);
                graphics2D.rotate(n6, n2 - n7, n3);
            }
            else {
                graphics2D.translate(0.0, n8 / 2.0f);
                graphics2D.rotate(n6, n2, n3);
            }
            if (n != -1) {
                this.fillTextBackground(n4, n, graphics2D, n2, n3, n7, textLayout);
            }
            final Stroke stroke = graphics2D.getStroke();
            graphics2D.setStroke(WMFPainter.textSolid);
            graphics2D.drawString(attributedCharacterIterator, n2, n3);
            graphics2D.setStroke(stroke);
            graphics2D.setTransform(transform);
        }
    }
    
    private void fillTextBackground(final int n, final int n2, final Graphics2D graphics2D, final float n3, final float n4, final float n5, final TextLayout textLayout) {
        float n6 = n3;
        if (n == 6) {
            n6 = n3 - n5 / 2.0f;
        }
        else if (n == 2) {
            n6 = n3 - n5;
        }
        if ((n2 & 0x2) != 0x0) {
            final Color color = graphics2D.getColor();
            final AffineTransform transform = graphics2D.getTransform();
            graphics2D.setColor(this.bkgdColor);
            graphics2D.translate(n6, n4);
            graphics2D.fill(textLayout.getBounds());
            graphics2D.setColor(color);
            graphics2D.setTransform(transform);
        }
        else if (this.opaque) {
            final Color color2 = graphics2D.getColor();
            final AffineTransform transform2 = graphics2D.getTransform();
            graphics2D.setColor(this.bkgdColor);
            graphics2D.translate(n6, n4);
            graphics2D.fill(textLayout.getBounds());
            graphics2D.setColor(color2);
            graphics2D.setTransform(transform2);
        }
    }
    
    private void drawPolyPolygon(final Graphics2D graphics2D, final List list) {
        final Iterator<Polygon2D> iterator = list.iterator();
        while (iterator.hasNext()) {
            graphics2D.draw(iterator.next());
        }
    }
    
    private void fillPolyPolygon(final Graphics2D graphics2D, final List list) {
        if (list.size() == 1) {
            graphics2D.fill(list.get(0));
        }
        else {
            final GeneralPath generalPath = new GeneralPath(0);
            for (int i = 0; i < list.size(); ++i) {
                generalPath.append(list.get(i), false);
            }
            graphics2D.fill(generalPath);
        }
    }
    
    private void setStroke(final Graphics2D graphics2D, final int n, final float n2, final float n3) {
        float n4;
        if (n2 == 0.0f) {
            n4 = 1.0f;
        }
        else {
            n4 = n2;
        }
        final float n5 = Toolkit.getDefaultToolkit().getScreenResolution() / (float)this.currentStore.getMetaFileUnitsPerInch();
        final float n6 = n4 * n5 * (n3 / n5);
        final float n7 = this.currentStore.getWidthPixels() * 1.0f / 350.0f;
        if (n == 0) {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1));
        }
        else if (n == 2) {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1, 10.0f * n7, new float[] { 1.0f * n7, 5.0f * n7 }, 0.0f));
        }
        else if (n == 1) {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1, 10.0f * n7, new float[] { 5.0f * n7, 2.0f * n7 }, 0.0f));
        }
        else if (n == 3) {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1, 10.0f * n7, new float[] { 5.0f * n7, 2.0f * n7, 1.0f * n7, 2.0f * n7 }, 0.0f));
        }
        else if (n == 4) {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1, 15.0f * n7, new float[] { 5.0f * n7, 2.0f * n7, 1.0f * n7, 2.0f * n7, 1.0f * n7, 2.0f * n7 }, 0.0f));
        }
        else {
            graphics2D.setStroke(new BasicStroke(n6, 0, 1));
        }
    }
    
    private void setPenColor(final WMFRecordStore wmfRecordStore, final Graphics2D graphics2D, int n) {
        if (n >= 0) {
            graphics2D.setColor((Color)wmfRecordStore.getObject(n).obj);
            n = -1;
        }
    }
    
    private int getHorizontalAlignement(final int n) {
        final int n2 = n % 24 % 8;
        if (n2 >= 6) {
            return 6;
        }
        if (n2 >= 2) {
            return 2;
        }
        return 0;
    }
    
    private void setBrushPaint(final WMFRecordStore wmfRecordStore, final Graphics2D graphics2D, int n) {
        if (n >= 0) {
            final GdiObject object = wmfRecordStore.getObject(n);
            if (object.obj instanceof Color) {
                graphics2D.setColor((Color)object.obj);
            }
            else if (object.obj instanceof Paint) {
                graphics2D.setPaint((Paint)object.obj);
            }
            else {
                graphics2D.setPaint(this.getPaint((byte[])object.obj));
            }
            n = -1;
        }
    }
    
    private Paint getStoredPaint(final WMFRecordStore wmfRecordStore, final int n) {
        if (n < 0) {
            return null;
        }
        final GdiObject object = wmfRecordStore.getObject(n);
        if (object.obj instanceof Paint) {
            return (Paint)object.obj;
        }
        return this.getPaint((byte[])object.obj);
    }
    
    private void paint(final int n, final int n2, final Shape shape, final Graphics2D graphics2D) {
        if (n >= 0) {
            final Paint storedPaint = this.getStoredPaint(this.currentStore, n);
            if (!this.firstEffectivePaint || !storedPaint.equals(Color.white)) {
                this.setBrushPaint(this.currentStore, graphics2D, n);
                graphics2D.fill(shape);
                this.firstEffectivePaint = false;
            }
        }
        if (n2 >= 0) {
            final Paint storedPaint2 = this.getStoredPaint(this.currentStore, n2);
            if (!this.firstEffectivePaint || !storedPaint2.equals(Color.white)) {
                this.setPenColor(this.currentStore, graphics2D, n2);
                graphics2D.draw(shape);
                this.firstEffectivePaint = false;
            }
        }
    }
    
    private void paintWithPen(final int n, final Shape shape, final Graphics2D graphics2D) {
        if (n >= 0) {
            final Paint storedPaint = this.getStoredPaint(this.currentStore, n);
            if (!this.firstEffectivePaint || !storedPaint.equals(Color.white)) {
                this.setPenColor(this.currentStore, graphics2D, n);
                graphics2D.draw(shape);
                this.firstEffectivePaint = false;
            }
        }
    }
    
    private float getVerticalAlignmentValue(final TextLayout textLayout, final int n) {
        if (n == 8) {
            return -textLayout.getDescent();
        }
        if (n == 0) {
            return textLayout.getAscent();
        }
        return 0.0f;
    }
    
    public WMFRecordStore getRecordStore() {
        return this.currentStore;
    }
    
    static {
        INTEGER_0 = new Integer(0);
        WMFPainter.solid = new BasicStroke(1.0f, 0, 1);
        WMFPainter.textSolid = new BasicStroke(1.0f, 0, 1);
    }
}
