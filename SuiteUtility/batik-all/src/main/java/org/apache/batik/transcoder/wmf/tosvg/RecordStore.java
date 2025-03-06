// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.io.IOException;
import java.io.DataInputStream;
import java.util.Vector;
import java.net.URL;

public class RecordStore
{
    private transient URL url;
    protected transient int numRecords;
    protected transient int numObjects;
    public transient int lastObjectIdx;
    protected transient int vpX;
    protected transient int vpY;
    protected transient int vpW;
    protected transient int vpH;
    protected transient Vector records;
    protected transient Vector objectVector;
    protected transient boolean bReading;
    
    public RecordStore() {
        this.bReading = false;
        this.reset();
    }
    
    public void reset() {
        this.numRecords = 0;
        this.vpX = 0;
        this.vpY = 0;
        this.vpW = 1000;
        this.vpH = 1000;
        this.numObjects = 0;
        this.records = new Vector(20, 20);
        this.objectVector = new Vector();
    }
    
    synchronized void setReading(final boolean bReading) {
        this.bReading = bReading;
    }
    
    synchronized boolean isReading() {
        return this.bReading;
    }
    
    public boolean read(final DataInputStream dataInputStream) throws IOException {
        this.setReading(true);
        this.reset();
        short short1 = 0;
        this.numRecords = 0;
        this.numObjects = dataInputStream.readShort();
        this.objectVector.ensureCapacity(this.numObjects);
        for (int i = 0; i < this.numObjects; ++i) {
            this.objectVector.add(new GdiObject(i, false));
        }
        while (short1 != -1) {
            short1 = dataInputStream.readShort();
            if (short1 == -1) {
                break;
            }
            MetaRecord e = null;
            switch (short1) {
                case 763:
                case 1313:
                case 1583:
                case 2610: {
                    final short short2 = dataInputStream.readShort();
                    final byte[] bytes = new byte[short2];
                    for (short n = 0; n < short2; ++n) {
                        bytes[n] = dataInputStream.readByte();
                    }
                    e = new MetaRecord.StringRecord(new String(bytes));
                    break;
                }
                default: {
                    e = new MetaRecord();
                    break;
                }
            }
            final short short3 = dataInputStream.readShort();
            e.numPoints = short3;
            e.functionId = short1;
            for (short n2 = 0; n2 < short3; ++n2) {
                e.AddElement(new Integer(dataInputStream.readShort()));
            }
            this.records.add(e);
            ++this.numRecords;
        }
        this.setReading(false);
        return true;
    }
    
    public void addObject(final int n, final Object o) {
        for (int i = 0; i < this.numObjects; ++i) {
            final GdiObject gdiObject = this.objectVector.get(i);
            if (!gdiObject.used) {
                gdiObject.Setup(n, o);
                this.lastObjectIdx = i;
                break;
            }
        }
    }
    
    public void addObjectAt(final int n, final Object o, final int lastObjectIdx) {
        if (lastObjectIdx == 0 || lastObjectIdx > this.numObjects) {
            this.addObject(n, o);
            return;
        }
        this.lastObjectIdx = lastObjectIdx;
        for (int i = 0; i < this.numObjects; ++i) {
            final GdiObject gdiObject = this.objectVector.get(i);
            if (i == lastObjectIdx) {
                gdiObject.Setup(n, o);
                break;
            }
        }
    }
    
    public URL getUrl() {
        return this.url;
    }
    
    public void setUrl(final URL url) {
        this.url = url;
    }
    
    public GdiObject getObject(final int index) {
        return this.objectVector.get(index);
    }
    
    public MetaRecord getRecord(final int index) {
        return this.records.get(index);
    }
    
    public int getNumRecords() {
        return this.numRecords;
    }
    
    public int getNumObjects() {
        return this.numObjects;
    }
    
    public int getVpX() {
        return this.vpX;
    }
    
    public int getVpY() {
        return this.vpY;
    }
    
    public int getVpW() {
        return this.vpW;
    }
    
    public int getVpH() {
        return this.vpH;
    }
    
    public void setVpX(final int vpX) {
        this.vpX = vpX;
    }
    
    public void setVpY(final int vpY) {
        this.vpY = vpY;
    }
    
    public void setVpW(final int vpW) {
        this.vpW = vpW;
    }
    
    public void setVpH(final int vpH) {
        this.vpH = vpH;
    }
}
