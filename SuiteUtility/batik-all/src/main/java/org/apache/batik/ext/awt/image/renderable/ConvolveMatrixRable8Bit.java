// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.BufferedImageCachableRed;
import java.awt.Rectangle;
import java.awt.image.Raster;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.color.ColorSpace;
import java.util.Hashtable;
import java.awt.image.ConvolveOp;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.Point;
import java.awt.image.Kernel;

public class ConvolveMatrixRable8Bit extends AbstractColorInterpolationRable implements ConvolveMatrixRable
{
    Kernel kernel;
    Point target;
    float bias;
    boolean kernelHasNegValues;
    PadMode edgeMode;
    float[] kernelUnitLength;
    boolean preserveAlpha;
    
    public ConvolveMatrixRable8Bit(final Filter filter) {
        super(filter);
        this.kernelUnitLength = new float[2];
        this.preserveAlpha = false;
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter);
    }
    
    public Kernel getKernel() {
        return this.kernel;
    }
    
    public void setKernel(final Kernel kernel) {
        this.touch();
        this.kernel = kernel;
        this.kernelHasNegValues = false;
        final float[] kernelData = kernel.getKernelData(null);
        for (int i = 0; i < kernelData.length; ++i) {
            if (kernelData[i] < 0.0f) {
                this.kernelHasNegValues = true;
                break;
            }
        }
    }
    
    public Point getTarget() {
        return (Point)this.target.clone();
    }
    
    public void setTarget(final Point point) {
        this.touch();
        this.target = (Point)point.clone();
    }
    
    public double getBias() {
        return this.bias;
    }
    
    public void setBias(final double n) {
        this.touch();
        this.bias = (float)n;
    }
    
    public PadMode getEdgeMode() {
        return this.edgeMode;
    }
    
    public void setEdgeMode(final PadMode edgeMode) {
        this.touch();
        this.edgeMode = edgeMode;
    }
    
    public double[] getKernelUnitLength() {
        if (this.kernelUnitLength == null) {
            return null;
        }
        return new double[] { this.kernelUnitLength[0], this.kernelUnitLength[1] };
    }
    
    public void setKernelUnitLength(final double[] array) {
        this.touch();
        if (array == null) {
            this.kernelUnitLength = null;
            return;
        }
        if (this.kernelUnitLength == null) {
            this.kernelUnitLength = new float[2];
        }
        this.kernelUnitLength[0] = (float)array[0];
        this.kernelUnitLength[1] = (float)array[1];
    }
    
    public boolean getPreserveAlpha() {
        return this.preserveAlpha;
    }
    
    public void setPreserveAlpha(final boolean preserveAlpha) {
        this.touch();
        this.preserveAlpha = preserveAlpha;
    }
    
    public void fixAlpha(final BufferedImage bufferedImage) {
        if (!bufferedImage.getColorModel().hasAlpha() || !bufferedImage.isAlphaPremultiplied()) {
            return;
        }
        if (GraphicsUtil.is_INT_PACK_Data(bufferedImage.getSampleModel(), true)) {
            this.fixAlpha_INT_PACK(bufferedImage.getRaster());
        }
        else {
            this.fixAlpha_FALLBACK(bufferedImage.getRaster());
        }
    }
    
    public void fixAlpha_INT_PACK(final WritableRaster writableRaster) {
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int width = writableRaster.getWidth();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        for (int i = 0; i < writableRaster.getHeight(); ++i) {
            for (int j = n + i * scanlineStride; j < j + width; ++j) {
                final int n2 = array[j];
                int n3 = n2 >>> 24;
                final int n4 = n2 >> 16 & 0xFF;
                if (n3 < n4) {
                    n3 = n4;
                }
                final int n5 = n2 >> 8 & 0xFF;
                if (n3 < n5) {
                    n3 = n5;
                }
                final int n6 = n2 & 0xFF;
                if (n3 < n6) {
                    n3 = n6;
                }
                array[j] = ((n2 & 0xFFFFFF) | n3 << 24);
            }
        }
    }
    
    public void fixAlpha_FALLBACK(final WritableRaster writableRaster) {
        final int minX = writableRaster.getMinX();
        final int width = writableRaster.getWidth();
        final int minY = writableRaster.getMinY();
        final int n = minY + writableRaster.getHeight() - 1;
        final int numBands = writableRaster.getNumBands();
        int[] pixels = null;
        for (int i = minY; i <= n; ++i) {
            pixels = writableRaster.getPixels(minX, i, width, 1, pixels);
            int n2 = 0;
            for (int j = 0; j < width; ++j) {
                int n3 = pixels[n2];
                for (int k = 1; k < numBands; ++k) {
                    if (pixels[n2 + k] > n3) {
                        n3 = pixels[n2 + k];
                    }
                }
                pixels[n2 + numBands - 1] = n3;
                n2 += numBands;
            }
            writableRaster.setPixels(minX, i, width, 1, pixels);
        }
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final AffineTransform transform = renderContext.getTransform();
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double shearX = transform.getShearX();
        final double shearY = transform.getShearY();
        final double translateX = transform.getTranslateX();
        final double translateY = transform.getTranslateY();
        double sqrt = Math.sqrt(scaleX * scaleX + shearY * shearY);
        double sqrt2 = Math.sqrt(scaleY * scaleY + shearX * shearX);
        if (this.kernelUnitLength != null) {
            if (this.kernelUnitLength[0] > 0.0) {
                sqrt = 1.0f / this.kernelUnitLength[0];
            }
            if (this.kernelUnitLength[1] > 0.0) {
                sqrt2 = 1.0f / this.kernelUnitLength[1];
            }
        }
        Shape pSrc = renderContext.getAreaOfInterest();
        if (pSrc == null) {
            pSrc = this.getBounds2D();
        }
        final Rectangle2D bounds2D = pSrc.getBounds2D();
        final int width = this.kernel.getWidth();
        final int height = this.kernel.getHeight();
        final int x = this.target.x;
        final int y = this.target.y;
        final double n = bounds2D.getX() - x / sqrt;
        final double n2 = bounds2D.getY() - y / sqrt2;
        final Rectangle2D.Double aoi = new Rectangle2D.Double(Math.floor(n), Math.floor(n2), Math.ceil(n + bounds2D.getWidth() + (width - 1) / sqrt - Math.floor(n)), Math.ceil(n2 + bounds2D.getHeight() + (height - 1) / sqrt2 - Math.floor(n2)));
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(sqrt, sqrt2);
        final AffineTransform affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
        final RenderedImage rendering = this.getSource().createRendering(new RenderContext(scaleInstance, aoi, renderingHints));
        if (rendering == null) {
            return null;
        }
        CachableRed convertSourceCS = this.convertSourceCS(rendering);
        final Rectangle2D bounds2D2;
        final Rectangle2D rectangle2D = bounds2D2 = scaleInstance.createTransformedShape(pSrc).getBounds2D();
        final Rectangle2D.Double double1 = new Rectangle2D.Double(Math.floor(bounds2D2.getX() - x), Math.floor(bounds2D2.getY() - y), Math.ceil(bounds2D2.getX() + bounds2D2.getWidth()) - Math.floor(bounds2D2.getX()) + (width - 1), Math.ceil(bounds2D2.getY() + bounds2D2.getHeight()) - Math.floor(bounds2D2.getY()) + (height - 1));
        if (!double1.getBounds().equals(convertSourceCS.getBounds())) {
            if (this.edgeMode == PadMode.WRAP) {
                throw new IllegalArgumentException("edgeMode=\"wrap\" is not supported by ConvolveMatrix.");
            }
            convertSourceCS = new PadRed(convertSourceCS, double1.getBounds(), this.edgeMode, renderingHints);
        }
        if (this.bias != 0.0) {
            throw new IllegalArgumentException("Only bias equal to zero is supported in ConvolveMatrix.");
        }
        final ConvolveOp convolveOp = new ConvolveOp(this.kernel, 1, renderingHints);
        final ColorModel colorModel = convertSourceCS.getColorModel();
        final WritableRaster rasterWritable = GraphicsUtil.makeRasterWritable(convertSourceCS.getData(), 0, 0);
        final int n3 = this.target.x - this.kernel.getXOrigin();
        final int n4 = this.target.y - this.kernel.getYOrigin();
        final int n5 = (int)(double1.getX() + n3);
        final int n6 = (int)(double1.getY() + n4);
        BufferedImage filter;
        if (!this.preserveAlpha) {
            final ColorModel coerceData = GraphicsUtil.coerceData(rasterWritable, colorModel, true);
            filter = convolveOp.filter(new BufferedImage(coerceData, rasterWritable, coerceData.isAlphaPremultiplied(), null), null);
            if (this.kernelHasNegValues) {
                this.fixAlpha(filter);
            }
        }
        else {
            final BufferedImage bufferedImage = new BufferedImage(colorModel, rasterWritable, colorModel.isAlphaPremultiplied(), null);
            final DirectColorModel directColorModel = new DirectColorModel(ColorSpace.getInstance(1004), 24, 16711680, 65280, 255, 0, false, 3);
            final BufferedImage bufferedImage2 = new BufferedImage(directColorModel, directColorModel.createCompatibleWritableRaster(rasterWritable.getWidth(), rasterWritable.getHeight()), directColorModel.isAlphaPremultiplied(), null);
            GraphicsUtil.copyData(bufferedImage, bufferedImage2);
            final ColorModel linear_sRGB_Unpre = GraphicsUtil.Linear_sRGB_Unpre;
            filter = new BufferedImage(linear_sRGB_Unpre, linear_sRGB_Unpre.createCompatibleWritableRaster(rasterWritable.getWidth(), rasterWritable.getHeight()), linear_sRGB_Unpre.isAlphaPremultiplied(), null);
            convolveOp.filter(bufferedImage2, new BufferedImage(directColorModel, Raster.createWritableRaster(directColorModel.createCompatibleSampleModel(rasterWritable.getWidth(), rasterWritable.getHeight()), filter.getRaster().getDataBuffer(), new Point(0, 0)), directColorModel.isAlphaPremultiplied(), null));
            final Rectangle bounds = rasterWritable.getBounds();
            GraphicsUtil.copyBand(rasterWritable, bounds, rasterWritable.getNumBands() - 1, filter.getRaster(), new Rectangle(bounds.x - n3, bounds.y - n4, bounds.width, bounds.height), filter.getRaster().getNumBands() - 1);
        }
        AbstractRed abstractRed = new PadRed(new BufferedImageCachableRed(filter, n5, n6), rectangle2D.getBounds(), PadMode.ZERO_PAD, renderingHints);
        if (!affineTransform.isIdentity()) {
            abstractRed = new AffineRed(abstractRed, affineTransform, null);
        }
        return abstractRed;
    }
}
