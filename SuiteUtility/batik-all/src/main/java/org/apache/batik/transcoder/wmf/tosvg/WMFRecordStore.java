// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.io.IOException;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class WMFRecordStore extends AbstractWMFReader
{
    private URL url;
    protected int numRecords;
    protected float vpX;
    protected float vpY;
    protected List records;
    private boolean _bext;
    
    public WMFRecordStore() {
        this._bext = true;
        this.reset();
    }
    
    public void reset() {
        this.numRecords = 0;
        this.vpX = 0.0f;
        this.vpY = 0.0f;
        this.vpW = 1000;
        this.vpH = 1000;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.scaleXY = 1.0f;
        this.inch = 84;
        this.records = new ArrayList(20);
    }
    
    protected boolean readRecords(final DataInputStream dataInputStream) throws IOException {
        int i = 1;
        this.numRecords = 0;
        while (i > 0) {
            int int1 = this.readInt(dataInputStream);
            int1 -= 3;
            i = this.readShort(dataInputStream);
            if (i <= 0) {
                break;
            }
            MetaRecord metaRecord = new MetaRecord();
            switch (i) {
                case 259: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short1 = this.readShort(dataInputStream);
                    if (short1 == 8) {
                        this.isotropic = false;
                    }
                    metaRecord.addElement(short1);
                    this.records.add(metaRecord);
                    break;
                }
                case 1583: {
                    for (int j = 0; j < int1; ++j) {
                        this.readShort(dataInputStream);
                    }
                    --this.numRecords;
                    break;
                }
                case 2610: {
                    final int n = this.readShort(dataInputStream) * this.ySign;
                    final int n2 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final short short2 = this.readShort(dataInputStream);
                    final short short3 = this.readShort(dataInputStream);
                    int n3 = 4;
                    boolean b = false;
                    int n4 = 0;
                    int n5 = 0;
                    int n6 = 0;
                    int n7 = 0;
                    if ((short3 & 0x4) != 0x0) {
                        n4 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                        n5 = this.readShort(dataInputStream) * this.ySign;
                        n6 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                        n7 = this.readShort(dataInputStream) * this.ySign;
                        n3 += 4;
                        b = true;
                    }
                    final byte[] array = new byte[short2];
                    for (short n8 = 0; n8 < short2; ++n8) {
                        array[n8] = dataInputStream.readByte();
                    }
                    final int n9 = n3 + (short2 + 1) / 2;
                    if (short2 % 2 != 0) {
                        dataInputStream.readByte();
                    }
                    if (n9 < int1) {
                        for (int k = n9; k < int1; ++k) {
                            this.readShort(dataInputStream);
                        }
                    }
                    final MetaRecord.ByteRecord byteRecord = new MetaRecord.ByteRecord(array);
                    byteRecord.numPoints = int1;
                    byteRecord.functionId = i;
                    byteRecord.addElement(n2);
                    byteRecord.addElement(n);
                    byteRecord.addElement(short3);
                    if (b) {
                        byteRecord.addElement(n4);
                        byteRecord.addElement(n5);
                        byteRecord.addElement(n6);
                        byteRecord.addElement(n7);
                    }
                    this.records.add(byteRecord);
                    break;
                }
                case 1313: {
                    final short short4 = this.readShort(dataInputStream);
                    final int n10 = 1;
                    final byte[] array2 = new byte[short4];
                    for (short n11 = 0; n11 < short4; ++n11) {
                        array2[n11] = dataInputStream.readByte();
                    }
                    if (short4 % 2 != 0) {
                        dataInputStream.readByte();
                    }
                    int n12 = n10 + (short4 + 1) / 2;
                    final int n13 = this.readShort(dataInputStream) * this.ySign;
                    final int n14 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    n12 += 2;
                    if (n12 < int1) {
                        for (int l = n12; l < int1; ++l) {
                            this.readShort(dataInputStream);
                        }
                    }
                    final MetaRecord.ByteRecord byteRecord2 = new MetaRecord.ByteRecord(array2);
                    byteRecord2.numPoints = int1;
                    byteRecord2.functionId = i;
                    byteRecord2.addElement(n14);
                    byteRecord2.addElement(n13);
                    this.records.add(byteRecord2);
                    break;
                }
                case 763: {
                    final short short5 = this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final short short6 = this.readShort(dataInputStream);
                    final short short7 = this.readShort(dataInputStream);
                    final short short8 = this.readShort(dataInputStream);
                    final byte byte1 = dataInputStream.readByte();
                    final byte byte2 = dataInputStream.readByte();
                    final byte byte3 = dataInputStream.readByte();
                    final int n15 = dataInputStream.readByte() & 0xFF;
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    dataInputStream.readByte();
                    final int n16 = 2 * (int1 - 9);
                    final byte[] bytes = new byte[n16];
                    for (int n17 = 0; n17 < n16; ++n17) {
                        bytes[n17] = dataInputStream.readByte();
                    }
                    final MetaRecord.StringRecord stringRecord = new MetaRecord.StringRecord(new String(bytes));
                    stringRecord.numPoints = int1;
                    stringRecord.functionId = i;
                    stringRecord.addElement(short5);
                    stringRecord.addElement(byte1);
                    stringRecord.addElement(short8);
                    stringRecord.addElement(n15);
                    stringRecord.addElement(byte2);
                    stringRecord.addElement(byte3);
                    stringRecord.addElement(short7);
                    stringRecord.addElement(short6);
                    this.records.add(stringRecord);
                    break;
                }
                case 523:
                case 524:
                case 525:
                case 526: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    int short9 = this.readShort(dataInputStream);
                    int short10 = this.readShort(dataInputStream);
                    if (short10 < 0) {
                        short10 = -short10;
                        this.xSign = -1;
                    }
                    if (short9 < 0) {
                        short9 = -short9;
                        this.ySign = -1;
                    }
                    metaRecord.addElement((int)(short10 * this.scaleXY));
                    metaRecord.addElement(short9);
                    this.records.add(metaRecord);
                    if (this._bext && i == 524) {
                        this.vpW = short10;
                        this.vpH = short9;
                        if (!this.isotropic) {
                            this.scaleXY = this.vpW / (float)this.vpH;
                        }
                        this.vpW *= (int)this.scaleXY;
                        this._bext = false;
                    }
                    if (!this.isAldus) {
                        this.width = this.vpW;
                        this.height = this.vpH;
                        break;
                    }
                    break;
                }
                case 527:
                case 529: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n18 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                    metaRecord.addElement(n18);
                    this.records.add(metaRecord);
                    break;
                }
                case 1040:
                case 1042: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short11 = this.readShort(dataInputStream);
                    final short short12 = this.readShort(dataInputStream);
                    final short short13 = this.readShort(dataInputStream);
                    final short short14 = this.readShort(dataInputStream);
                    metaRecord.addElement(short13);
                    metaRecord.addElement(short11);
                    metaRecord.addElement(short14);
                    metaRecord.addElement(short12);
                    this.records.add(metaRecord);
                    this.scaleX = this.scaleX * short13 / short14;
                    this.scaleY = this.scaleY * short11 / short12;
                    break;
                }
                case 764: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    metaRecord.addElement(this.readShort(dataInputStream));
                    final int int2 = this.readInt(dataInputStream);
                    final int n19 = int2 & 0xFF;
                    final int n20 = (int2 & 0xFF00) >> 8;
                    final int n21 = (int2 & 0xFF0000) >> 16;
                    metaRecord.addElement(n19);
                    metaRecord.addElement(n20);
                    metaRecord.addElement(n21);
                    metaRecord.addElement(this.readShort(dataInputStream));
                    this.records.add(metaRecord);
                    break;
                }
                case 762: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    metaRecord.addElement(this.readShort(dataInputStream));
                    final int int3 = this.readInt(dataInputStream);
                    final int int4 = this.readInt(dataInputStream);
                    if (int1 == 6) {
                        this.readShort(dataInputStream);
                    }
                    final int n22 = int4 & 0xFF;
                    final int n23 = (int4 & 0xFF00) >> 8;
                    final int n24 = (int4 & 0xFF0000) >> 16;
                    metaRecord.addElement(n22);
                    metaRecord.addElement(n23);
                    metaRecord.addElement(n24);
                    metaRecord.addElement(int3);
                    this.records.add(metaRecord);
                    break;
                }
                case 302: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short15 = this.readShort(dataInputStream);
                    if (int1 > 1) {
                        for (int n25 = 1; n25 < int1; ++n25) {
                            this.readShort(dataInputStream);
                        }
                    }
                    metaRecord.addElement(short15);
                    this.records.add(metaRecord);
                    break;
                }
                case 513:
                case 521: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int int5 = this.readInt(dataInputStream);
                    final int n26 = int5 & 0xFF;
                    final int n27 = (int5 & 0xFF00) >> 8;
                    final int n28 = (int5 & 0xFF0000) >> 16;
                    metaRecord.addElement(n26);
                    metaRecord.addElement(n27);
                    metaRecord.addElement(n28);
                    this.records.add(metaRecord);
                    break;
                }
                case 531:
                case 532: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n29 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                    metaRecord.addElement(n29);
                    this.records.add(metaRecord);
                    break;
                }
                case 262: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short16 = this.readShort(dataInputStream);
                    if (int1 > 1) {
                        for (int n30 = 1; n30 < int1; ++n30) {
                            this.readShort(dataInputStream);
                        }
                    }
                    metaRecord.addElement(short16);
                    this.records.add(metaRecord);
                    break;
                }
                case 1336: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short17 = this.readShort(dataInputStream);
                    final int[] array3 = new int[short17];
                    int n31 = 0;
                    for (short n32 = 0; n32 < short17; ++n32) {
                        array3[n32] = this.readShort(dataInputStream);
                        n31 += array3[n32];
                    }
                    metaRecord.addElement(short17);
                    for (short n33 = 0; n33 < short17; ++n33) {
                        metaRecord.addElement(array3[n33]);
                    }
                    for (short n34 = 0; n34 < short17; ++n34) {
                        for (int n35 = array3[n34], n36 = 0; n36 < n35; ++n36) {
                            metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                            metaRecord.addElement(this.readShort(dataInputStream) * this.ySign);
                        }
                    }
                    this.records.add(metaRecord);
                    break;
                }
                case 804:
                case 805: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final short short18 = this.readShort(dataInputStream);
                    metaRecord.addElement(short18);
                    for (short n37 = 0; n37 < short18; ++n37) {
                        metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                        metaRecord.addElement(this.readShort(dataInputStream) * this.ySign);
                    }
                    this.records.add(metaRecord);
                    break;
                }
                case 1046:
                case 1048:
                case 1051: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n38 = this.readShort(dataInputStream) * this.ySign;
                    final int n39 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n40 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                    metaRecord.addElement(n40);
                    metaRecord.addElement(n39);
                    metaRecord.addElement(n38);
                    this.records.add(metaRecord);
                    break;
                }
                case 1791: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n41 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n42 = this.readShort(dataInputStream) * this.ySign;
                    final int n43 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n44 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement(n41);
                    metaRecord.addElement(n42);
                    metaRecord.addElement(n43);
                    metaRecord.addElement(n44);
                    this.records.add(metaRecord);
                    break;
                }
                case 1564: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n45 = this.readShort(dataInputStream) * this.ySign;
                    final int n46 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n47 = this.readShort(dataInputStream) * this.ySign;
                    final int n48 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n49 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                    metaRecord.addElement(n49);
                    metaRecord.addElement(n48);
                    metaRecord.addElement(n47);
                    metaRecord.addElement(n46);
                    metaRecord.addElement(n45);
                    this.records.add(metaRecord);
                    break;
                }
                case 2071:
                case 2074: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int n50 = this.readShort(dataInputStream) * this.ySign;
                    final int n51 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n52 = this.readShort(dataInputStream) * this.ySign;
                    final int n53 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n54 = this.readShort(dataInputStream) * this.ySign;
                    final int n55 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n56 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement((int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY));
                    metaRecord.addElement(n56);
                    metaRecord.addElement(n55);
                    metaRecord.addElement(n54);
                    metaRecord.addElement(n53);
                    metaRecord.addElement(n52);
                    metaRecord.addElement(n51);
                    metaRecord.addElement(n50);
                    this.records.add(metaRecord);
                    break;
                }
                case 1565: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    final int int6 = this.readInt(dataInputStream);
                    final int n57 = this.readShort(dataInputStream) * this.ySign;
                    final int n58 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n59 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n60 = this.readShort(dataInputStream) * this.ySign;
                    metaRecord.addElement(int6);
                    metaRecord.addElement(n57);
                    metaRecord.addElement(n58);
                    metaRecord.addElement(n60);
                    metaRecord.addElement(n59);
                    this.records.add(metaRecord);
                    break;
                }
                case 258: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    metaRecord.addElement(this.readShort(dataInputStream));
                    if (int1 > 1) {
                        for (int n61 = 1; n61 < int1; ++n61) {
                            this.readShort(dataInputStream);
                        }
                    }
                    this.records.add(metaRecord);
                    break;
                }
                case 260: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    int n62;
                    if (int1 == 1) {
                        n62 = this.readShort(dataInputStream);
                    }
                    else {
                        n62 = this.readInt(dataInputStream);
                    }
                    metaRecord.addElement(n62);
                    this.records.add(metaRecord);
                    break;
                }
                case 2881: {
                    final int n63 = dataInputStream.readInt() & 0xFF;
                    final int n64 = this.readShort(dataInputStream) * this.ySign;
                    final int n65 = this.readShort(dataInputStream) * this.xSign;
                    final int n66 = this.readShort(dataInputStream) * this.ySign;
                    final int n67 = this.readShort(dataInputStream) * this.xSign;
                    final int n68 = this.readShort(dataInputStream) * this.ySign;
                    final int n69 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n70 = this.readShort(dataInputStream) * this.ySign;
                    final int n71 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n72 = 2 * int1 - 20;
                    final byte[] array4 = new byte[n72];
                    for (int n73 = 0; n73 < n72; ++n73) {
                        array4[n73] = dataInputStream.readByte();
                    }
                    final MetaRecord.ByteRecord byteRecord3 = new MetaRecord.ByteRecord(array4);
                    byteRecord3.numPoints = int1;
                    byteRecord3.functionId = i;
                    byteRecord3.addElement(n63);
                    byteRecord3.addElement(n64);
                    byteRecord3.addElement(n65);
                    byteRecord3.addElement(n66);
                    byteRecord3.addElement(n67);
                    byteRecord3.addElement(n68);
                    byteRecord3.addElement(n69);
                    byteRecord3.addElement(n70);
                    byteRecord3.addElement(n71);
                    this.records.add(byteRecord3);
                    break;
                }
                case 3907: {
                    final int n74 = dataInputStream.readInt() & 0xFF;
                    this.readShort(dataInputStream);
                    final int n75 = this.readShort(dataInputStream) * this.ySign;
                    final int n76 = this.readShort(dataInputStream) * this.xSign;
                    final int n77 = this.readShort(dataInputStream) * this.ySign;
                    final int n78 = this.readShort(dataInputStream) * this.xSign;
                    final int n79 = this.readShort(dataInputStream) * this.ySign;
                    final int n80 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n81 = this.readShort(dataInputStream) * this.ySign;
                    final int n82 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n83 = 2 * int1 - 22;
                    final byte[] array5 = new byte[n83];
                    for (int n84 = 0; n84 < n83; ++n84) {
                        array5[n84] = dataInputStream.readByte();
                    }
                    final MetaRecord.ByteRecord byteRecord4 = new MetaRecord.ByteRecord(array5);
                    byteRecord4.numPoints = int1;
                    byteRecord4.functionId = i;
                    byteRecord4.addElement(n74);
                    byteRecord4.addElement(n75);
                    byteRecord4.addElement(n76);
                    byteRecord4.addElement(n77);
                    byteRecord4.addElement(n78);
                    byteRecord4.addElement(n79);
                    byteRecord4.addElement(n80);
                    byteRecord4.addElement(n81);
                    byteRecord4.addElement(n82);
                    this.records.add(byteRecord4);
                    break;
                }
                case 2368: {
                    final int n85 = dataInputStream.readInt() & 0xFF;
                    final short short19 = this.readShort(dataInputStream);
                    final short short20 = this.readShort(dataInputStream);
                    this.readShort(dataInputStream);
                    final short short21 = this.readShort(dataInputStream);
                    final int n86 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final short short22 = this.readShort(dataInputStream);
                    final int n87 = (int)(this.readShort(dataInputStream) * this.xSign * this.scaleXY);
                    final int n88 = 2 * int1 - 18;
                    if (n88 > 0) {
                        final byte[] array6 = new byte[n88];
                        for (int n89 = 0; n89 < n88; ++n89) {
                            array6[n89] = dataInputStream.readByte();
                        }
                        metaRecord = new MetaRecord.ByteRecord(array6);
                        metaRecord.numPoints = int1;
                        metaRecord.functionId = i;
                    }
                    else {
                        metaRecord.numPoints = int1;
                        metaRecord.functionId = i;
                        for (int n90 = 0; n90 < n88; ++n90) {
                            dataInputStream.readByte();
                        }
                    }
                    metaRecord.addElement(n85);
                    metaRecord.addElement(short21);
                    metaRecord.addElement(n86);
                    metaRecord.addElement(short19);
                    metaRecord.addElement(short20);
                    metaRecord.addElement(short22);
                    metaRecord.addElement(n87);
                    this.records.add(metaRecord);
                    break;
                }
                case 322: {
                    final int n91 = dataInputStream.readInt() & 0xFF;
                    final int n92 = 2 * int1 - 4;
                    final byte[] array7 = new byte[n92];
                    for (int n93 = 0; n93 < n92; ++n93) {
                        array7[n93] = dataInputStream.readByte();
                    }
                    final MetaRecord.ByteRecord byteRecord5 = new MetaRecord.ByteRecord(array7);
                    byteRecord5.numPoints = int1;
                    byteRecord5.functionId = i;
                    byteRecord5.addElement(n91);
                    this.records.add(byteRecord5);
                    break;
                }
                default: {
                    metaRecord.numPoints = int1;
                    metaRecord.functionId = i;
                    for (int n94 = 0; n94 < int1; ++n94) {
                        metaRecord.addElement(this.readShort(dataInputStream));
                    }
                    this.records.add(metaRecord);
                    break;
                }
            }
            ++this.numRecords;
        }
        if (!this.isAldus) {
            this.right = (int)this.vpX;
            this.left = (int)(this.vpX + this.vpW);
            this.top = (int)this.vpY;
            this.bottom = (int)(this.vpY + this.vpH);
        }
        this.setReading(false);
        return true;
    }
    
    public URL getUrl() {
        return this.url;
    }
    
    public void setUrl(final URL url) {
        this.url = url;
    }
    
    public MetaRecord getRecord(final int n) {
        return this.records.get(n);
    }
    
    public int getNumRecords() {
        return this.numRecords;
    }
    
    public float getVpX() {
        return this.vpX;
    }
    
    public float getVpY() {
        return this.vpY;
    }
    
    public void setVpX(final float vpX) {
        this.vpX = vpX;
    }
    
    public void setVpY(final float vpY) {
        this.vpY = vpY;
    }
}
