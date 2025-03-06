// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.awt.Toolkit;
import java.util.Collection;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWMFReader
{
    public static final float PIXEL_PER_INCH;
    public static final float MM_PER_PIXEL;
    protected int left;
    protected int right;
    protected int top;
    protected int bottom;
    protected int width;
    protected int height;
    protected int inch;
    protected float scaleX;
    protected float scaleY;
    protected float scaleXY;
    protected int vpW;
    protected int vpH;
    protected int vpX;
    protected int vpY;
    protected int xSign;
    protected int ySign;
    protected volatile boolean bReading;
    protected boolean isAldus;
    protected boolean isotropic;
    protected int mtType;
    protected int mtHeaderSize;
    protected int mtVersion;
    protected int mtSize;
    protected int mtNoObjects;
    protected int mtMaxRecord;
    protected int mtNoParameters;
    protected int windowWidth;
    protected int windowHeight;
    protected int numObjects;
    protected List objectVector;
    public int lastObjectIdx;
    
    public AbstractWMFReader() {
        this.xSign = 1;
        this.ySign = 1;
        this.bReading = false;
        this.isAldus = false;
        this.isotropic = true;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.scaleXY = 1.0f;
        this.left = -1;
        this.top = -1;
        this.width = -1;
        this.height = -1;
        this.right = this.left + this.width;
        this.bottom = this.top + this.height;
        this.numObjects = 0;
        this.objectVector = new ArrayList();
    }
    
    public AbstractWMFReader(final int width, final int height) {
        this();
        this.width = width;
        this.height = height;
    }
    
    protected short readShort(final DataInputStream dataInputStream) throws IOException {
        final byte[] b = new byte[2];
        dataInputStream.readFully(b);
        return (short)((short)(0xFFFF & (0xFF & b[1]) << 8) | (0xFF & b[0]));
    }
    
    protected int readInt(final DataInputStream dataInputStream) throws IOException {
        final byte[] b = new byte[4];
        dataInputStream.readFully(b);
        return (0xFF & b[3]) << 24 | (0xFF & b[2]) << 16 | (0xFF & b[1]) << 8 | (0xFF & b[0]);
    }
    
    public float getViewportWidthUnits() {
        return (float)this.vpW;
    }
    
    public float getViewportHeightUnits() {
        return (float)this.vpH;
    }
    
    public float getViewportWidthInch() {
        return this.vpW / (float)this.inch;
    }
    
    public float getViewportHeightInch() {
        return this.vpH / (float)this.inch;
    }
    
    public float getPixelsPerUnit() {
        return AbstractWMFReader.PIXEL_PER_INCH / this.inch;
    }
    
    public int getVpW() {
        return (int)(AbstractWMFReader.PIXEL_PER_INCH * this.vpW / this.inch);
    }
    
    public int getVpH() {
        return (int)(AbstractWMFReader.PIXEL_PER_INCH * this.vpH / this.inch);
    }
    
    public int getLeftUnits() {
        return this.left;
    }
    
    public int getRightUnits() {
        return this.right;
    }
    
    public int getTopUnits() {
        return this.top;
    }
    
    public int getWidthUnits() {
        return this.width;
    }
    
    public int getHeightUnits() {
        return this.height;
    }
    
    public int getBottomUnits() {
        return this.bottom;
    }
    
    public int getMetaFileUnitsPerInch() {
        return this.inch;
    }
    
    public Rectangle getRectangleUnits() {
        return new Rectangle(this.left, this.top, this.width, this.height);
    }
    
    public Rectangle2D getRectanglePixel() {
        final float x = AbstractWMFReader.PIXEL_PER_INCH * this.left / this.inch;
        final float n = AbstractWMFReader.PIXEL_PER_INCH * this.right / this.inch;
        final float y = AbstractWMFReader.PIXEL_PER_INCH * this.top / this.inch;
        return new Rectangle2D.Float(x, y, n - x, AbstractWMFReader.PIXEL_PER_INCH * this.bottom / this.inch - y);
    }
    
    public Rectangle2D getRectangleInch() {
        final float x = this.left / (float)this.inch;
        final float n = this.right / (float)this.inch;
        final float y = this.top / (float)this.inch;
        return new Rectangle2D.Float(x, y, n - x, this.bottom / (float)this.inch - y);
    }
    
    public int getWidthPixels() {
        return (int)(AbstractWMFReader.PIXEL_PER_INCH * this.width / this.inch);
    }
    
    public float getUnitsToPixels() {
        return AbstractWMFReader.PIXEL_PER_INCH / this.inch;
    }
    
    public float getVpWFactor() {
        return AbstractWMFReader.PIXEL_PER_INCH * this.width / this.inch / this.vpW;
    }
    
    public float getVpHFactor() {
        return AbstractWMFReader.PIXEL_PER_INCH * this.height / this.inch / this.vpH;
    }
    
    public int getHeightPixels() {
        return (int)(AbstractWMFReader.PIXEL_PER_INCH * this.height / this.inch);
    }
    
    public int getXSign() {
        return this.xSign;
    }
    
    public int getYSign() {
        return this.ySign;
    }
    
    protected synchronized void setReading(final boolean bReading) {
        this.bReading = bReading;
    }
    
    public synchronized boolean isReading() {
        return this.bReading;
    }
    
    public abstract void reset();
    
    protected abstract boolean readRecords(final DataInputStream p0) throws IOException;
    
    public void read(final DataInputStream dataInputStream) throws IOException {
        this.reset();
        this.setReading(true);
        final int int1 = this.readInt(dataInputStream);
        if (int1 == -1698247209) {
            this.isAldus = true;
            this.readShort(dataInputStream);
            this.left = this.readShort(dataInputStream);
            this.top = this.readShort(dataInputStream);
            this.right = this.readShort(dataInputStream);
            this.bottom = this.readShort(dataInputStream);
            this.inch = this.readShort(dataInputStream);
            this.readInt(dataInputStream);
            this.readShort(dataInputStream);
            if (this.left > this.right) {
                final int right = this.right;
                this.right = this.left;
                this.left = right;
                this.xSign = -1;
            }
            if (this.top > this.bottom) {
                final int bottom = this.bottom;
                this.bottom = this.top;
                this.top = bottom;
                this.ySign = -1;
            }
            this.width = this.right - this.left;
            this.height = this.bottom - this.top;
            this.mtType = this.readShort(dataInputStream);
            this.mtHeaderSize = this.readShort(dataInputStream);
        }
        else {
            this.mtType = int1 << 16 >> 16;
            this.mtHeaderSize = int1 >> 16;
        }
        this.mtVersion = this.readShort(dataInputStream);
        this.mtSize = this.readInt(dataInputStream);
        this.mtNoObjects = this.readShort(dataInputStream);
        this.mtMaxRecord = this.readInt(dataInputStream);
        this.mtNoParameters = this.readShort(dataInputStream);
        this.numObjects = this.mtNoObjects;
        final ArrayList<GdiObject> list = new ArrayList<GdiObject>(this.numObjects);
        for (int i = 0; i < this.numObjects; ++i) {
            list.add(new GdiObject(i, false));
        }
        this.objectVector.addAll(list);
        final boolean records = this.readRecords(dataInputStream);
        dataInputStream.close();
        if (!records) {
            throw new IOException("Unhandled exception while reading records");
        }
    }
    
    public int addObject(final int n, final Object o) {
        for (int i = 0; i < this.numObjects; ++i) {
            final GdiObject gdiObject = this.objectVector.get(i);
            if (!gdiObject.used) {
                gdiObject.Setup(n, o);
                this.lastObjectIdx = i;
                break;
            }
        }
        return this.lastObjectIdx;
    }
    
    public int addObjectAt(final int n, final Object o, final int lastObjectIdx) {
        if (lastObjectIdx == 0 || lastObjectIdx > this.numObjects) {
            this.addObject(n, o);
            return this.lastObjectIdx;
        }
        this.lastObjectIdx = lastObjectIdx;
        for (int i = 0; i < this.numObjects; ++i) {
            final GdiObject gdiObject = this.objectVector.get(i);
            if (i == lastObjectIdx) {
                gdiObject.Setup(n, o);
                break;
            }
        }
        return lastObjectIdx;
    }
    
    public GdiObject getObject(final int n) {
        return this.objectVector.get(n);
    }
    
    public int getNumObjects() {
        return this.numObjects;
    }
    
    static {
        PIXEL_PER_INCH = (float)Toolkit.getDefaultToolkit().getScreenResolution();
        MM_PER_PIXEL = 25.4f / Toolkit.getDefaultToolkit().getScreenResolution();
    }
}
