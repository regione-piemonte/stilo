// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.util.ArrayList;

public class TileBlock
{
    int occX;
    int occY;
    int occW;
    int occH;
    int xOff;
    int yOff;
    int w;
    int h;
    int benefit;
    boolean[] occupied;
    
    TileBlock(final int occX, final int occY, final int occW, final int occH, final boolean[] occupied, final int xOff, final int yOff, final int w, final int h) {
        this.occX = occX;
        this.occY = occY;
        this.occW = occW;
        this.occH = occH;
        this.xOff = xOff;
        this.yOff = yOff;
        this.w = w;
        this.h = h;
        this.occupied = occupied;
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (!occupied[j + xOff + occW * (i + yOff)]) {
                    ++this.benefit;
                }
            }
        }
    }
    
    public String toString() {
        String str = "";
        for (int i = 0; i < this.occH; ++i) {
            for (int j = 0; j < this.occW + 1; ++j) {
                if (j == this.xOff || j == this.xOff + this.w) {
                    if (i == this.yOff || i == this.yOff + this.h - 1) {
                        str += "+";
                    }
                    else if (i > this.yOff && i < this.yOff + this.h - 1) {
                        str += "|";
                    }
                    else {
                        str += " ";
                    }
                }
                else if (i == this.yOff && j > this.xOff && j < this.xOff + this.w) {
                    str += "-";
                }
                else if (i == this.yOff + this.h - 1 && j > this.xOff && j < this.xOff + this.w) {
                    str += "_";
                }
                else {
                    str += " ";
                }
                if (j != this.occW) {
                    if (this.occupied[j + i * this.occW]) {
                        str += "*";
                    }
                    else {
                        str += ".";
                    }
                }
            }
            str += "\n";
        }
        return str;
    }
    
    int getXLoc() {
        return this.occX + this.xOff;
    }
    
    int getYLoc() {
        return this.occY + this.yOff;
    }
    
    int getWidth() {
        return this.w;
    }
    
    int getHeight() {
        return this.h;
    }
    
    int getBenefit() {
        return this.benefit;
    }
    
    int getWork() {
        return this.w * this.h + 1;
    }
    
    static int getWork(final TileBlock[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            n += array[i].getWork();
        }
        return n;
    }
    
    TileBlock[] getBestSplit() {
        if (this.simplify()) {
            return null;
        }
        if (this.benefit == this.w * this.h) {
            return new TileBlock[] { this };
        }
        return this.splitOneGo();
    }
    
    public TileBlock[] splitOneGo() {
        final boolean[] array = this.occupied.clone();
        final ArrayList list = new ArrayList<TileBlock>();
        for (int i = this.yOff; i < this.yOff + this.h; ++i) {
            for (int j = this.xOff; j < this.xOff + this.w; ++j) {
                if (!array[j + i * this.occW]) {
                    int n = this.xOff + this.w - j;
                    for (int k = j; k < j + n; ++k) {
                        if (array[k + i * this.occW]) {
                            n = k - j;
                        }
                        else {
                            array[k + i * this.occW] = true;
                        }
                    }
                    int n2 = 1;
                    for (int l = i + 1; l < this.yOff + this.h; ++l) {
                        int n3;
                        for (n3 = j; n3 < j + n && !array[n3 + l * this.occW]; ++n3) {}
                        if (n3 != j + n) {
                            break;
                        }
                        for (int n4 = j; n4 < j + n; ++n4) {
                            array[n4 + l * this.occW] = true;
                        }
                        ++n2;
                    }
                    list.add(new TileBlock(this.occX, this.occY, this.occW, this.occH, this.occupied, j, i, n, n2));
                    j += n - 1;
                }
            }
        }
        final TileBlock[] array2 = new TileBlock[list.size()];
        list.toArray(array2);
        return array2;
    }
    
    public boolean simplify() {
        final boolean[] occupied = this.occupied;
        for (int i = 0; i < this.h; --i, --this.h, ++i) {
            int n;
            for (n = 0; n < this.w && occupied[n + this.xOff + this.occW * (i + this.yOff)]; ++n) {}
            if (n != this.w) {
                break;
            }
            ++this.yOff;
        }
        if (this.h == 0) {
            return true;
        }
        for (int j = this.h - 1; j >= 0; --j) {
            int n2;
            for (n2 = 0; n2 < this.w && occupied[n2 + this.xOff + this.occW * (j + this.yOff)]; ++n2) {}
            if (n2 != this.w) {
                break;
            }
            --this.h;
        }
        for (int k = 0; k < this.w; --k, --this.w, ++k) {
            int n3;
            for (n3 = 0; n3 < this.h && occupied[k + this.xOff + this.occW * (n3 + this.yOff)]; ++n3) {}
            if (n3 != this.h) {
                break;
            }
            ++this.xOff;
        }
        for (int l = this.w - 1; l >= 0; --l) {
            int n4;
            for (n4 = 0; n4 < this.h && occupied[l + this.xOff + this.occW * (n4 + this.yOff)]; ++n4) {}
            if (n4 != this.h) {
                break;
            }
            --this.w;
        }
        return false;
    }
}
