// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.DataBufferInt;
import java.util.Hashtable;
import java.awt.image.WritableRaster;
import java.awt.RenderingHints;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.Raster;
import java.awt.color.ColorSpace;
import java.awt.image.RasterOp;
import java.awt.image.BufferedImageOp;

public class MorphologyOp implements BufferedImageOp, RasterOp
{
    private int radiusX;
    private int radiusY;
    private boolean doDilation;
    private final int rangeX;
    private final int rangeY;
    private final ColorSpace sRGB;
    private final ColorSpace lRGB;
    
    public MorphologyOp(final int radiusX, final int radiusY, final boolean doDilation) {
        this.sRGB = ColorSpace.getInstance(1000);
        this.lRGB = ColorSpace.getInstance(1004);
        if (radiusX <= 0 || radiusY <= 0) {
            throw new IllegalArgumentException("The radius of X-axis or Y-axis should not be Zero or Negatives.");
        }
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.doDilation = doDilation;
        this.rangeX = 2 * radiusX + 1;
        this.rangeY = 2 * radiusY + 1;
    }
    
    public Rectangle2D getBounds2D(final Raster raster) {
        this.checkCompatible(raster.getSampleModel());
        return new Rectangle(raster.getMinX(), raster.getMinY(), raster.getWidth(), raster.getHeight());
    }
    
    public Rectangle2D getBounds2D(final BufferedImage bufferedImage) {
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
    
    public Point2D getPoint2D(final Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Float();
        }
        point2D2.setLocation(point2D.getX(), point2D.getY());
        return point2D2;
    }
    
    private void checkCompatible(final ColorModel colorModel, final SampleModel sampleModel) {
        final ColorSpace colorSpace = colorModel.getColorSpace();
        if (!colorSpace.equals(this.sRGB) && !colorSpace.equals(this.lRGB)) {
            throw new IllegalArgumentException("Expected CS_sRGB or CS_LINEAR_RGB color model");
        }
        if (!(colorModel instanceof DirectColorModel)) {
            throw new IllegalArgumentException("colorModel should be an instance of DirectColorModel");
        }
        if (sampleModel.getDataType() != 3) {
            throw new IllegalArgumentException("colorModel's transferType should be DataBuffer.TYPE_INT");
        }
        final DirectColorModel directColorModel = (DirectColorModel)colorModel;
        if (directColorModel.getRedMask() != 16711680) {
            throw new IllegalArgumentException("red mask in source should be 0x00ff0000");
        }
        if (directColorModel.getGreenMask() != 65280) {
            throw new IllegalArgumentException("green mask in source should be 0x0000ff00");
        }
        if (directColorModel.getBlueMask() != 255) {
            throw new IllegalArgumentException("blue mask in source should be 0x000000ff");
        }
        if (directColorModel.getAlphaMask() != -16777216) {
            throw new IllegalArgumentException("alpha mask in source should be 0xff000000");
        }
    }
    
    private boolean isCompatible(final ColorModel colorModel, final SampleModel sampleModel) {
        final ColorSpace colorSpace = colorModel.getColorSpace();
        if (colorSpace != ColorSpace.getInstance(1000) && colorSpace != ColorSpace.getInstance(1004)) {
            return false;
        }
        if (!(colorModel instanceof DirectColorModel)) {
            return false;
        }
        if (sampleModel.getDataType() != 3) {
            return false;
        }
        final DirectColorModel directColorModel = (DirectColorModel)colorModel;
        return directColorModel.getRedMask() == 16711680 && directColorModel.getGreenMask() == 65280 && directColorModel.getBlueMask() == 255 && directColorModel.getAlphaMask() == -16777216;
    }
    
    private void checkCompatible(final SampleModel sampleModel) {
        if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
            throw new IllegalArgumentException("MorphologyOp only works with Rasters using SinglePixelPackedSampleModels");
        }
        if (sampleModel.getNumBands() != 4) {
            throw new IllegalArgumentException("MorphologyOp only words with Rasters having 4 bands");
        }
        if (sampleModel.getDataType() != 3) {
            throw new IllegalArgumentException("MorphologyOp only works with Rasters using DataBufferInt");
        }
        final int[] bitOffsets = ((SinglePixelPackedSampleModel)sampleModel).getBitOffsets();
        for (int i = 0; i < bitOffsets.length; ++i) {
            if (bitOffsets[i] % 8 != 0) {
                throw new IllegalArgumentException("MorphologyOp only works with Rasters using 8 bits per band : " + i + " : " + bitOffsets[i]);
            }
        }
    }
    
    public RenderingHints getRenderingHints() {
        return null;
    }
    
    public WritableRaster createCompatibleDestRaster(final Raster raster) {
        this.checkCompatible(raster.getSampleModel());
        return raster.createCompatibleWritableRaster();
    }
    
    public BufferedImage createCompatibleDestImage(final BufferedImage bufferedImage, ColorModel colorModel) {
        if (colorModel == null) {
            colorModel = bufferedImage.getColorModel();
        }
        final WritableRaster compatibleWritableRaster = colorModel.createCompatibleWritableRaster(bufferedImage.getWidth(), bufferedImage.getHeight());
        this.checkCompatible(colorModel, compatibleWritableRaster.getSampleModel());
        return new BufferedImage(colorModel, compatibleWritableRaster, colorModel.isAlphaPremultiplied(), null);
    }
    
    static final boolean isBetter(final int n, final int n2, final boolean b) {
        if (n > n2) {
            return b;
        }
        return n >= n2 || !b;
    }
    
    private void specialProcessRow(final Raster raster, final WritableRaster writableRaster) {
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)writableRaster.getDataBuffer();
        final int n = dataBufferInt.getOffset() + ((SinglePixelPackedSampleModel)raster.getSampleModel()).getOffset(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY());
        final int n2 = dataBufferInt2.getOffset() + ((SinglePixelPackedSampleModel)writableRaster.getSampleModel()).getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int scanlineStride = ((SinglePixelPackedSampleModel)raster.getSampleModel()).getScanlineStride();
        final int scanlineStride2 = ((SinglePixelPackedSampleModel)writableRaster.getSampleModel()).getScanlineStride();
        final int[] array = dataBufferInt.getBankData()[0];
        final int[] array2 = dataBufferInt2.getBankData()[0];
        if (width <= this.radiusX) {
            for (int i = 0; i < height; ++i) {
                int n3 = n + i * scanlineStride;
                int n4 = n2 + i * scanlineStride2;
                final int n5 = array[n3++];
                int n6 = n5 >>> 24;
                int n7 = n5 & 0xFF0000;
                int n8 = n5 & 0xFF00;
                int n9 = n5 & 0xFF;
                for (int j = 1; j < width; ++j) {
                    final int n10 = array[n3++];
                    final int n11 = n10 >>> 24;
                    final int n12 = n10 & 0xFF0000;
                    final int n13 = n10 & 0xFF00;
                    final int n14 = n10 & 0xFF;
                    if (isBetter(n11, n6, this.doDilation)) {
                        n6 = n11;
                    }
                    if (isBetter(n12, n7, this.doDilation)) {
                        n7 = n12;
                    }
                    if (isBetter(n13, n8, this.doDilation)) {
                        n8 = n13;
                    }
                    if (isBetter(n14, n9, this.doDilation)) {
                        n9 = n14;
                    }
                }
                for (int k = 0; k < width; ++k) {
                    array2[n4++] = (n6 << 24 | n7 | n8 | n9);
                }
            }
        }
        else {
            final int[] array3 = new int[width];
            final int[] array4 = new int[width];
            final int[] array5 = new int[width];
            final int[] array6 = new int[width];
            for (int l = 0; l < height; ++l) {
                int n15 = n + l * scanlineStride;
                int n16 = n2 + l * scanlineStride2;
                int n17 = 0;
                int n18 = 0;
                int n19 = 0;
                int n20 = 0;
                int n21 = 0;
                final int n22 = array[n15++];
                int n23 = n22 >>> 24;
                int n24 = n22 & 0xFF0000;
                int n25 = n22 & 0xFF00;
                int n26 = n22 & 0xFF;
                array3[0] = n23;
                array4[0] = n24;
                array5[0] = n25;
                array6[0] = n26;
                for (int n27 = 1; n27 <= this.radiusX; ++n27) {
                    final int n28 = array[n15++];
                    final int n29 = n28 >>> 24;
                    final int n30 = n28 & 0xFF0000;
                    final int n31 = n28 & 0xFF00;
                    final int n32 = n28 & 0xFF;
                    array3[n27] = n29;
                    array4[n27] = n30;
                    array5[n27] = n31;
                    array6[n27] = n32;
                    if (isBetter(n29, n23, this.doDilation)) {
                        n23 = n29;
                        n18 = n27;
                    }
                    if (isBetter(n30, n24, this.doDilation)) {
                        n24 = n30;
                        n19 = n27;
                    }
                    if (isBetter(n31, n25, this.doDilation)) {
                        n25 = n31;
                        n20 = n27;
                    }
                    if (isBetter(n32, n26, this.doDilation)) {
                        n26 = n32;
                        n21 = n27;
                    }
                }
                array2[n16++] = (n23 << 24 | n24 | n25 | n26);
                for (int n33 = 1; n33 <= width - this.radiusX - 1; ++n33) {
                    final int n34 = array[n15++];
                    int n35 = array3[n18];
                    final int n36 = n34 >>> 24;
                    array3[n33 + this.radiusX] = n36;
                    if (isBetter(n36, n35, this.doDilation)) {
                        n35 = n36;
                        n18 = n33 + this.radiusX;
                    }
                    int n37 = array4[n19];
                    final int n38 = n34 & 0xFF0000;
                    array4[n33 + this.radiusX] = n38;
                    if (isBetter(n38, n37, this.doDilation)) {
                        n37 = n38;
                        n19 = n33 + this.radiusX;
                    }
                    int n39 = array5[n20];
                    final int n40 = n34 & 0xFF00;
                    array5[n33 + this.radiusX] = n40;
                    if (isBetter(n40, n39, this.doDilation)) {
                        n39 = n40;
                        n20 = n33 + this.radiusX;
                    }
                    int n41 = array6[n21];
                    final int n42 = n34 & 0xFF;
                    array6[n33 + this.radiusX] = n42;
                    if (isBetter(n42, n41, this.doDilation)) {
                        n41 = n42;
                        n21 = n33 + this.radiusX;
                    }
                    array2[n16++] = (n35 << 24 | n37 | n39 | n41);
                }
                for (int n43 = width - this.radiusX; n43 <= this.radiusX; ++n43) {
                    array2[n16] = array2[n16 - 1];
                    ++n16;
                }
                for (int n44 = this.radiusX + 1; n44 < width; ++n44) {
                    int n45;
                    if (n18 == n17) {
                        n45 = array3[n17 + 1];
                        n18 = n17 + 1;
                        for (int n46 = n17 + 2; n46 < width; ++n46) {
                            final int n47 = array3[n46];
                            if (isBetter(n47, n45, this.doDilation)) {
                                n45 = n47;
                                n18 = n46;
                            }
                        }
                    }
                    else {
                        n45 = array3[n18];
                    }
                    int n48;
                    if (n19 == n17) {
                        n48 = array4[n17 + 1];
                        n19 = n17 + 1;
                        for (int n49 = n17 + 2; n49 < width; ++n49) {
                            final int n50 = array4[n49];
                            if (isBetter(n50, n48, this.doDilation)) {
                                n48 = n50;
                                n19 = n49;
                            }
                        }
                    }
                    else {
                        n48 = array4[n19];
                    }
                    int n51;
                    if (n20 == n17) {
                        n51 = array5[n17 + 1];
                        n20 = n17 + 1;
                        for (int n52 = n17 + 2; n52 < width; ++n52) {
                            final int n53 = array5[n52];
                            if (isBetter(n53, n51, this.doDilation)) {
                                n51 = n53;
                                n20 = n52;
                            }
                        }
                    }
                    else {
                        n51 = array5[n20];
                    }
                    int n54;
                    if (n21 == n17) {
                        n54 = array6[n17 + 1];
                        n21 = n17 + 1;
                        for (int n55 = n17 + 2; n55 < width; ++n55) {
                            final int n56 = array6[n55];
                            if (isBetter(n56, n54, this.doDilation)) {
                                n54 = n56;
                                n21 = n55;
                            }
                        }
                    }
                    else {
                        n54 = array6[n21];
                    }
                    ++n17;
                    array2[n16++] = (n45 << 24 | n48 | n51 | n54);
                }
            }
        }
    }
    
    private void specialProcessColumn(final Raster raster, final WritableRaster writableRaster) {
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int offset = dataBufferInt.getOffset();
        final int scanlineStride = ((SinglePixelPackedSampleModel)writableRaster.getSampleModel()).getScanlineStride();
        final int[] array = dataBufferInt.getBankData()[0];
        if (height <= this.radiusY) {
            for (int i = 0; i < width; ++i) {
                int n = offset + i;
                final int n2 = offset + i;
                final int n3 = array[n2];
                int n4 = n2 + scanlineStride;
                int n5 = n3 >>> 24;
                int n6 = n3 & 0xFF0000;
                int n7 = n3 & 0xFF00;
                int n8 = n3 & 0xFF;
                for (int j = 1; j < height; ++j) {
                    final int n9 = array[n4];
                    n4 += scanlineStride;
                    final int n10 = n9 >>> 24;
                    final int n11 = n9 & 0xFF0000;
                    final int n12 = n9 & 0xFF00;
                    final int n13 = n9 & 0xFF;
                    if (isBetter(n10, n5, this.doDilation)) {
                        n5 = n10;
                    }
                    if (isBetter(n11, n6, this.doDilation)) {
                        n6 = n11;
                    }
                    if (isBetter(n12, n7, this.doDilation)) {
                        n7 = n12;
                    }
                    if (isBetter(n13, n8, this.doDilation)) {
                        n8 = n13;
                    }
                }
                for (int k = 0; k < height; ++k) {
                    array[n] = (n5 << 24 | n6 | n7 | n8);
                    n += scanlineStride;
                }
            }
        }
        else {
            final int[] array2 = new int[height];
            final int[] array3 = new int[height];
            final int[] array4 = new int[height];
            final int[] array5 = new int[height];
            for (int l = 0; l < width; ++l) {
                final int n14 = offset + l;
                final int n15 = offset + l;
                int n16 = 0;
                int n17 = 0;
                int n18 = 0;
                int n19 = 0;
                int n20 = 0;
                final int n21 = array[n15];
                int n22 = n15 + scanlineStride;
                int n23 = n21 >>> 24;
                int n24 = n21 & 0xFF0000;
                int n25 = n21 & 0xFF00;
                int n26 = n21 & 0xFF;
                array2[0] = n23;
                array3[0] = n24;
                array4[0] = n25;
                array5[0] = n26;
                for (int n27 = 1; n27 <= this.radiusY; ++n27) {
                    final int n28 = array[n22];
                    n22 += scanlineStride;
                    final int n29 = n28 >>> 24;
                    final int n30 = n28 & 0xFF0000;
                    final int n31 = n28 & 0xFF00;
                    final int n32 = n28 & 0xFF;
                    array2[n27] = n29;
                    array3[n27] = n30;
                    array4[n27] = n31;
                    array5[n27] = n32;
                    if (isBetter(n29, n23, this.doDilation)) {
                        n23 = n29;
                        n17 = n27;
                    }
                    if (isBetter(n30, n24, this.doDilation)) {
                        n24 = n30;
                        n18 = n27;
                    }
                    if (isBetter(n31, n25, this.doDilation)) {
                        n25 = n31;
                        n19 = n27;
                    }
                    if (isBetter(n32, n26, this.doDilation)) {
                        n26 = n32;
                        n20 = n27;
                    }
                }
                array[n14] = (n23 << 24 | n24 | n25 | n26);
                int n33 = n14 + scanlineStride;
                for (int n34 = 1; n34 <= height - this.radiusY - 1; ++n34) {
                    final int n35 = array[n22];
                    n22 += scanlineStride;
                    int n36 = array2[n17];
                    final int n37 = n35 >>> 24;
                    array2[n34 + this.radiusY] = n37;
                    if (isBetter(n37, n36, this.doDilation)) {
                        n36 = n37;
                        n17 = n34 + this.radiusY;
                    }
                    int n38 = array3[n18];
                    final int n39 = n35 & 0xFF0000;
                    array3[n34 + this.radiusY] = n39;
                    if (isBetter(n39, n38, this.doDilation)) {
                        n38 = n39;
                        n18 = n34 + this.radiusY;
                    }
                    int n40 = array4[n19];
                    final int n41 = n35 & 0xFF00;
                    array4[n34 + this.radiusY] = n41;
                    if (isBetter(n41, n40, this.doDilation)) {
                        n40 = n41;
                        n19 = n34 + this.radiusY;
                    }
                    int n42 = array5[n20];
                    final int n43 = n35 & 0xFF;
                    array5[n34 + this.radiusY] = n43;
                    if (isBetter(n43, n42, this.doDilation)) {
                        n42 = n43;
                        n20 = n34 + this.radiusY;
                    }
                    array[n33] = (n36 << 24 | n38 | n40 | n42);
                    n33 += scanlineStride;
                }
                for (int n44 = height - this.radiusY; n44 <= this.radiusY; ++n44) {
                    array[n33] = array[n33 - scanlineStride];
                    n33 += scanlineStride;
                }
                for (int n45 = this.radiusY + 1; n45 < height; ++n45) {
                    int n46;
                    if (n17 == n16) {
                        n46 = array2[n16 + 1];
                        n17 = n16 + 1;
                        for (int n47 = n16 + 2; n47 < height; ++n47) {
                            final int n48 = array2[n47];
                            if (isBetter(n48, n46, this.doDilation)) {
                                n46 = n48;
                                n17 = n47;
                            }
                        }
                    }
                    else {
                        n46 = array2[n17];
                    }
                    int n49;
                    if (n18 == n16) {
                        n49 = array3[n16 + 1];
                        n18 = n16 + 1;
                        for (int n50 = n16 + 2; n50 < height; ++n50) {
                            final int n51 = array3[n50];
                            if (isBetter(n51, n49, this.doDilation)) {
                                n49 = n51;
                                n18 = n50;
                            }
                        }
                    }
                    else {
                        n49 = array3[n18];
                    }
                    int n52;
                    if (n19 == n16) {
                        n52 = array4[n16 + 1];
                        n19 = n16 + 1;
                        for (int n53 = n16 + 2; n53 < height; ++n53) {
                            final int n54 = array4[n53];
                            if (isBetter(n54, n52, this.doDilation)) {
                                n52 = n54;
                                n19 = n53;
                            }
                        }
                    }
                    else {
                        n52 = array4[n19];
                    }
                    int n55;
                    if (n20 == n16) {
                        n55 = array5[n16 + 1];
                        n20 = n16 + 1;
                        for (int n56 = n16 + 2; n56 < height; ++n56) {
                            final int n57 = array5[n56];
                            if (isBetter(n57, n55, this.doDilation)) {
                                n55 = n57;
                                n20 = n56;
                            }
                        }
                    }
                    else {
                        n55 = array5[n20];
                    }
                    ++n16;
                    array[n33] = (n46 << 24 | n49 | n52 | n55);
                    n33 += scanlineStride;
                }
            }
        }
    }
    
    public WritableRaster filter(final Raster raster, WritableRaster compatibleDestRaster) {
        if (compatibleDestRaster != null) {
            this.checkCompatible(compatibleDestRaster.getSampleModel());
        }
        else {
            if (raster == null) {
                throw new IllegalArgumentException("src should not be null when dest is null");
            }
            compatibleDestRaster = this.createCompatibleDestRaster(raster);
        }
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)compatibleDestRaster.getDataBuffer();
        final int offset = dataBufferInt.getOffset();
        final int offset2 = dataBufferInt2.getOffset();
        final int scanlineStride = ((SinglePixelPackedSampleModel)raster.getSampleModel()).getScanlineStride();
        final int scanlineStride2 = ((SinglePixelPackedSampleModel)compatibleDestRaster.getSampleModel()).getScanlineStride();
        final int[] array = dataBufferInt.getBankData()[0];
        final int[] array2 = dataBufferInt2.getBankData()[0];
        if (width <= 2 * this.radiusX) {
            this.specialProcessRow(raster, compatibleDestRaster);
        }
        else {
            final int[] array3 = new int[this.rangeX];
            final int[] array4 = new int[this.rangeX];
            final int[] array5 = new int[this.rangeX];
            final int[] array6 = new int[this.rangeX];
            for (int i = 0; i < height; ++i) {
                int n = offset + i * scanlineStride;
                int n2 = offset2 + i * scanlineStride2;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                final int n8 = array[n++];
                int n9 = n8 >>> 24;
                int n10 = n8 & 0xFF0000;
                int n11 = n8 & 0xFF00;
                int n12 = n8 & 0xFF;
                array3[0] = n9;
                array4[0] = n10;
                array5[0] = n11;
                array6[0] = n12;
                for (int j = 1; j <= this.radiusX; ++j) {
                    final int n13 = array[n++];
                    final int n14 = n13 >>> 24;
                    final int n15 = n13 & 0xFF0000;
                    final int n16 = n13 & 0xFF00;
                    final int n17 = n13 & 0xFF;
                    array3[j] = n14;
                    array4[j] = n15;
                    array5[j] = n16;
                    array6[j] = n17;
                    if (isBetter(n14, n9, this.doDilation)) {
                        n9 = n14;
                        n4 = j;
                    }
                    if (isBetter(n15, n10, this.doDilation)) {
                        n10 = n15;
                        n5 = j;
                    }
                    if (isBetter(n16, n11, this.doDilation)) {
                        n11 = n16;
                        n6 = j;
                    }
                    if (isBetter(n17, n12, this.doDilation)) {
                        n12 = n17;
                        n7 = j;
                    }
                }
                array2[n2++] = (n9 << 24 | n10 | n11 | n12);
                for (int k = 1; k <= this.radiusX; ++k) {
                    final int n18 = array[n++];
                    n9 = array3[n4];
                    final int n19 = n18 >>> 24;
                    array3[k + this.radiusX] = n19;
                    if (isBetter(n19, n9, this.doDilation)) {
                        n9 = n19;
                        n4 = k + this.radiusX;
                    }
                    n10 = array4[n5];
                    final int n20 = n18 & 0xFF0000;
                    array4[k + this.radiusX] = n20;
                    if (isBetter(n20, n10, this.doDilation)) {
                        n10 = n20;
                        n5 = k + this.radiusX;
                    }
                    n11 = array5[n6];
                    final int n21 = n18 & 0xFF00;
                    array5[k + this.radiusX] = n21;
                    if (isBetter(n21, n11, this.doDilation)) {
                        n11 = n21;
                        n6 = k + this.radiusX;
                    }
                    n12 = array6[n7];
                    final int n22 = n18 & 0xFF;
                    array6[k + this.radiusX] = n22;
                    if (isBetter(n22, n12, this.doDilation)) {
                        n12 = n22;
                        n7 = k + this.radiusX;
                    }
                    array2[n2++] = (n9 << 24 | n10 | n11 | n12);
                }
                for (int l = this.radiusX + 1; l <= width - 1 - this.radiusX; ++l) {
                    final int n23 = array[n++];
                    final int n24 = n23 >>> 24;
                    final int n25 = n23 & 0xFF0000;
                    final int n26 = n23 & 0xFF00;
                    final int n27 = n23 & 0xFF;
                    array3[n3] = n24;
                    array4[n3] = n25;
                    array5[n3] = n26;
                    array6[n3] = n27;
                    if (n4 == n3) {
                        n9 = array3[0];
                        n4 = 0;
                        for (int n28 = 1; n28 < this.rangeX; ++n28) {
                            final int n29 = array3[n28];
                            if (isBetter(n29, n9, this.doDilation)) {
                                n9 = n29;
                                n4 = n28;
                            }
                        }
                    }
                    else {
                        n9 = array3[n4];
                        if (isBetter(n24, n9, this.doDilation)) {
                            n9 = n24;
                            n4 = n3;
                        }
                    }
                    if (n5 == n3) {
                        n10 = array4[0];
                        n5 = 0;
                        for (int n30 = 1; n30 < this.rangeX; ++n30) {
                            final int n31 = array4[n30];
                            if (isBetter(n31, n10, this.doDilation)) {
                                n10 = n31;
                                n5 = n30;
                            }
                        }
                    }
                    else {
                        n10 = array4[n5];
                        if (isBetter(n25, n10, this.doDilation)) {
                            n10 = n25;
                            n5 = n3;
                        }
                    }
                    if (n6 == n3) {
                        n11 = array5[0];
                        n6 = 0;
                        for (int n32 = 1; n32 < this.rangeX; ++n32) {
                            final int n33 = array5[n32];
                            if (isBetter(n33, n11, this.doDilation)) {
                                n11 = n33;
                                n6 = n32;
                            }
                        }
                    }
                    else {
                        n11 = array5[n6];
                        if (isBetter(n26, n11, this.doDilation)) {
                            n11 = n26;
                            n6 = n3;
                        }
                    }
                    if (n7 == n3) {
                        n12 = array6[0];
                        n7 = 0;
                        for (int n34 = 1; n34 < this.rangeX; ++n34) {
                            final int n35 = array6[n34];
                            if (isBetter(n35, n12, this.doDilation)) {
                                n12 = n35;
                                n7 = n34;
                            }
                        }
                    }
                    else {
                        n12 = array6[n7];
                        if (isBetter(n27, n12, this.doDilation)) {
                            n12 = n27;
                            n7 = n3;
                        }
                    }
                    array2[n2++] = (n9 << 24 | n10 | n11 | n12);
                    n3 = (n3 + 1) % this.rangeX;
                }
                final int n36 = (n3 == 0) ? (this.rangeX - 1) : (n3 - 1);
                int n37 = this.rangeX - 1;
                for (int n38 = width - this.radiusX; n38 < width; ++n38) {
                    final int n39 = (n3 + 1) % this.rangeX;
                    if (n4 == n3) {
                        n9 = array3[n36];
                        int n40 = n39;
                        for (int n41 = 1; n41 < n37; ++n41) {
                            final int n42 = array3[n40];
                            if (isBetter(n42, n9, this.doDilation)) {
                                n9 = n42;
                                n4 = n40;
                            }
                            n40 = (n40 + 1) % this.rangeX;
                        }
                    }
                    if (n5 == n3) {
                        n10 = array4[n36];
                        int n43 = n39;
                        for (int n44 = 1; n44 < n37; ++n44) {
                            final int n45 = array4[n43];
                            if (isBetter(n45, n10, this.doDilation)) {
                                n10 = n45;
                                n5 = n43;
                            }
                            n43 = (n43 + 1) % this.rangeX;
                        }
                    }
                    if (n6 == n3) {
                        n11 = array5[n36];
                        int n46 = n39;
                        for (int n47 = 1; n47 < n37; ++n47) {
                            final int n48 = array5[n46];
                            if (isBetter(n48, n11, this.doDilation)) {
                                n11 = n48;
                                n6 = n46;
                            }
                            n46 = (n46 + 1) % this.rangeX;
                        }
                    }
                    if (n7 == n3) {
                        n12 = array6[n36];
                        int n49 = n39;
                        for (int n50 = 1; n50 < n37; ++n50) {
                            final int n51 = array6[n49];
                            if (isBetter(n51, n12, this.doDilation)) {
                                n12 = n51;
                                n7 = n49;
                            }
                            n49 = (n49 + 1) % this.rangeX;
                        }
                    }
                    array2[n2++] = (n9 << 24 | n10 | n11 | n12);
                    n3 = (n3 + 1) % this.rangeX;
                    --n37;
                }
            }
        }
        if (height <= 2 * this.radiusY) {
            this.specialProcessColumn(raster, compatibleDestRaster);
        }
        else {
            final int[] array7 = new int[this.rangeY];
            final int[] array8 = new int[this.rangeY];
            final int[] array9 = new int[this.rangeY];
            final int[] array10 = new int[this.rangeY];
            for (int n52 = 0; n52 < width; ++n52) {
                final int n53 = offset2 + n52;
                final int n54 = offset2 + n52;
                int n55 = 0;
                int n56 = 0;
                int n57 = 0;
                int n58 = 0;
                int n59 = 0;
                final int n60 = array2[n54];
                int n61 = n54 + scanlineStride2;
                int n62 = n60 >>> 24;
                int n63 = n60 & 0xFF0000;
                int n64 = n60 & 0xFF00;
                int n65 = n60 & 0xFF;
                array7[0] = n62;
                array8[0] = n63;
                array9[0] = n64;
                array10[0] = n65;
                for (int n66 = 1; n66 <= this.radiusY; ++n66) {
                    final int n67 = array2[n61];
                    n61 += scanlineStride2;
                    final int n68 = n67 >>> 24;
                    final int n69 = n67 & 0xFF0000;
                    final int n70 = n67 & 0xFF00;
                    final int n71 = n67 & 0xFF;
                    array7[n66] = n68;
                    array8[n66] = n69;
                    array9[n66] = n70;
                    array10[n66] = n71;
                    if (isBetter(n68, n62, this.doDilation)) {
                        n62 = n68;
                        n56 = n66;
                    }
                    if (isBetter(n69, n63, this.doDilation)) {
                        n63 = n69;
                        n57 = n66;
                    }
                    if (isBetter(n70, n64, this.doDilation)) {
                        n64 = n70;
                        n58 = n66;
                    }
                    if (isBetter(n71, n65, this.doDilation)) {
                        n65 = n71;
                        n59 = n66;
                    }
                }
                array2[n53] = (n62 << 24 | n63 | n64 | n65);
                int n72 = n53 + scanlineStride2;
                for (int n73 = 1; n73 <= this.radiusY; ++n73) {
                    final int n74 = n73 + this.radiusY;
                    final int n75 = array2[n61];
                    n61 += scanlineStride2;
                    n62 = array7[n56];
                    final int n76 = n75 >>> 24;
                    array7[n74] = n76;
                    if (isBetter(n76, n62, this.doDilation)) {
                        n62 = n76;
                        n56 = n74;
                    }
                    n63 = array8[n57];
                    final int n77 = n75 & 0xFF0000;
                    array8[n74] = n77;
                    if (isBetter(n77, n63, this.doDilation)) {
                        n63 = n77;
                        n57 = n74;
                    }
                    n64 = array9[n58];
                    final int n78 = n75 & 0xFF00;
                    array9[n74] = n78;
                    if (isBetter(n78, n64, this.doDilation)) {
                        n64 = n78;
                        n58 = n74;
                    }
                    n65 = array10[n59];
                    final int n79 = n75 & 0xFF;
                    array10[n74] = n79;
                    if (isBetter(n79, n65, this.doDilation)) {
                        n65 = n79;
                        n59 = n74;
                    }
                    array2[n72] = (n62 << 24 | n63 | n64 | n65);
                    n72 += scanlineStride2;
                }
                for (int n80 = this.radiusY + 1; n80 <= height - 1 - this.radiusY; ++n80) {
                    final int n81 = array2[n61];
                    n61 += scanlineStride2;
                    final int n82 = n81 >>> 24;
                    final int n83 = n81 & 0xFF0000;
                    final int n84 = n81 & 0xFF00;
                    final int n85 = n81 & 0xFF;
                    array7[n55] = n82;
                    array8[n55] = n83;
                    array9[n55] = n84;
                    array10[n55] = n85;
                    if (n56 == n55) {
                        n62 = array7[0];
                        n56 = 0;
                        for (int n86 = 1; n86 <= 2 * this.radiusY; ++n86) {
                            final int n87 = array7[n86];
                            if (isBetter(n87, n62, this.doDilation)) {
                                n62 = n87;
                                n56 = n86;
                            }
                        }
                    }
                    else {
                        n62 = array7[n56];
                        if (isBetter(n82, n62, this.doDilation)) {
                            n62 = n82;
                            n56 = n55;
                        }
                    }
                    if (n57 == n55) {
                        n63 = array8[0];
                        n57 = 0;
                        for (int n88 = 1; n88 <= 2 * this.radiusY; ++n88) {
                            final int n89 = array8[n88];
                            if (isBetter(n89, n63, this.doDilation)) {
                                n63 = n89;
                                n57 = n88;
                            }
                        }
                    }
                    else {
                        n63 = array8[n57];
                        if (isBetter(n83, n63, this.doDilation)) {
                            n63 = n83;
                            n57 = n55;
                        }
                    }
                    if (n58 == n55) {
                        n64 = array9[0];
                        n58 = 0;
                        for (int n90 = 1; n90 <= 2 * this.radiusY; ++n90) {
                            final int n91 = array9[n90];
                            if (isBetter(n91, n64, this.doDilation)) {
                                n64 = n91;
                                n58 = n90;
                            }
                        }
                    }
                    else {
                        n64 = array9[n58];
                        if (isBetter(n84, n64, this.doDilation)) {
                            n64 = n84;
                            n58 = n55;
                        }
                    }
                    if (n59 == n55) {
                        n65 = array10[0];
                        n59 = 0;
                        for (int n92 = 1; n92 <= 2 * this.radiusY; ++n92) {
                            final int n93 = array10[n92];
                            if (isBetter(n93, n65, this.doDilation)) {
                                n65 = n93;
                                n59 = n92;
                            }
                        }
                    }
                    else {
                        n65 = array10[n59];
                        if (isBetter(n85, n65, this.doDilation)) {
                            n65 = n85;
                            n59 = n55;
                        }
                    }
                    array2[n72] = (n62 << 24 | n63 | n64 | n65);
                    n72 += scanlineStride2;
                    n55 = (n55 + 1) % this.rangeY;
                }
                final int n94 = (n55 == 0) ? (2 * this.radiusY) : (n55 - 1);
                int n95 = this.rangeY - 1;
                for (int n96 = height - this.radiusY; n96 < height - 1; ++n96) {
                    final int n97 = (n55 + 1) % this.rangeY;
                    if (n56 == n55) {
                        n62 = array7[n94];
                        int n98 = n97;
                        for (int n99 = 1; n99 < n95; ++n99) {
                            final int n100 = array7[n98];
                            if (isBetter(n100, n62, this.doDilation)) {
                                n62 = n100;
                                n56 = n98;
                            }
                            n98 = (n98 + 1) % this.rangeY;
                        }
                    }
                    if (n57 == n55) {
                        n63 = array8[n94];
                        int n101 = n97;
                        for (int n102 = 1; n102 < n95; ++n102) {
                            final int n103 = array8[n101];
                            if (isBetter(n103, n63, this.doDilation)) {
                                n63 = n103;
                                n57 = n101;
                            }
                            n101 = (n101 + 1) % this.rangeY;
                        }
                    }
                    if (n58 == n55) {
                        n64 = array9[n94];
                        int n104 = n97;
                        for (int n105 = 1; n105 < n95; ++n105) {
                            final int n106 = array9[n104];
                            if (isBetter(n106, n64, this.doDilation)) {
                                n64 = n106;
                                n58 = n104;
                            }
                            n104 = (n104 + 1) % this.rangeY;
                        }
                    }
                    if (n59 == n55) {
                        n65 = array10[n94];
                        int n107 = n97;
                        for (int n108 = 1; n108 < n95; ++n108) {
                            final int n109 = array10[n107];
                            if (isBetter(n109, n65, this.doDilation)) {
                                n65 = n109;
                                n59 = n107;
                            }
                            n107 = (n107 + 1) % this.rangeY;
                        }
                    }
                    array2[n72] = (n62 << 24 | n63 | n64 | n65);
                    n72 += scanlineStride2;
                    n55 = (n55 + 1) % this.rangeY;
                    --n95;
                }
            }
        }
        return compatibleDestRaster;
    }
    
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage compatibleDestImage) {
        if (bufferedImage == null) {
            throw new NullPointerException("Source image should not be null");
        }
        final BufferedImage bufferedImage2 = bufferedImage;
        Object compatibleDestImage2 = compatibleDestImage;
        if (!this.isCompatible(bufferedImage.getColorModel(), bufferedImage.getSampleModel())) {
            bufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 3);
            GraphicsUtil.copyData(bufferedImage2, bufferedImage);
        }
        else if (!bufferedImage.isAlphaPremultiplied()) {
            bufferedImage = new BufferedImage(GraphicsUtil.coerceColorModel(bufferedImage.getColorModel(), true), bufferedImage.getRaster(), true, null);
            GraphicsUtil.copyData(bufferedImage2, bufferedImage);
        }
        if (compatibleDestImage == null) {
            compatibleDestImage = (compatibleDestImage2 = this.createCompatibleDestImage(bufferedImage, null));
        }
        else if (!this.isCompatible(((BufferedImage)compatibleDestImage).getColorModel(), ((BufferedImage)compatibleDestImage).getSampleModel())) {
            compatibleDestImage = this.createCompatibleDestImage(bufferedImage, null);
        }
        else if (!((BufferedImage)compatibleDestImage).isAlphaPremultiplied()) {
            compatibleDestImage = new BufferedImage(GraphicsUtil.coerceColorModel(((BufferedImage)compatibleDestImage).getColorModel(), true), ((BufferedImage)compatibleDestImage2).getRaster(), true, null);
        }
        this.filter(bufferedImage.getRaster(), ((BufferedImage)compatibleDestImage).getRaster());
        if (bufferedImage.getRaster() == bufferedImage2.getRaster() && bufferedImage.isAlphaPremultiplied() != bufferedImage2.isAlphaPremultiplied()) {
            GraphicsUtil.copyData(bufferedImage, bufferedImage2);
        }
        if (((BufferedImage)compatibleDestImage).getRaster() != ((BufferedImage)compatibleDestImage2).getRaster() || ((BufferedImage)compatibleDestImage).isAlphaPremultiplied() != ((BufferedImage)compatibleDestImage2).isAlphaPremultiplied()) {
            GraphicsUtil.copyData((BufferedImage)compatibleDestImage, (BufferedImage)compatibleDestImage2);
        }
        return (BufferedImage)compatibleDestImage2;
    }
}
