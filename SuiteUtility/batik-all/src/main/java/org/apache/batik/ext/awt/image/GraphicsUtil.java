// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.image.DirectColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import org.apache.batik.ext.awt.image.rendered.Any2sRGBRed;
import org.apache.batik.ext.awt.image.rendered.Any2LsRGBRed;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.apache.batik.ext.awt.image.renderable.PaintRable;
import java.awt.RenderingHints;
import java.util.Map;
import java.awt.image.renderable.RenderContext;
import java.awt.image.renderable.RenderableImage;
import java.awt.image.SampleModel;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.Raster;
import java.awt.Point;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.Composite;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import org.apache.batik.ext.awt.image.rendered.FormatRed;
import java.awt.image.ImageObserver;
import java.awt.Image;
import org.apache.batik.ext.awt.image.rendered.BufferedImageCachableRed;
import java.awt.color.ColorSpace;
import org.apache.batik.ext.awt.image.rendered.TranslateRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.image.RenderedImage;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.geom.AffineTransform;

public class GraphicsUtil
{
    public static AffineTransform IDENTITY;
    public static final boolean WARN_DESTINATION;
    public static final ColorModel Linear_sRGB;
    public static final ColorModel Linear_sRGB_Pre;
    public static final ColorModel Linear_sRGB_Unpre;
    public static final ColorModel sRGB;
    public static final ColorModel sRGB_Pre;
    public static final ColorModel sRGB_Unpre;
    
    public static void drawImage(final Graphics2D graphics2D, final RenderedImage renderedImage) {
        drawImage(graphics2D, wrap(renderedImage));
    }
    
    public static void drawImage(final Graphics2D graphics2D, CachableRed cachableRed) {
        AffineTransform transform = null;
        while (true) {
            if (cachableRed instanceof AffineRed) {
                final AffineRed affineRed = (AffineRed)cachableRed;
                if (transform == null) {
                    transform = affineRed.getTransform();
                }
                else {
                    transform.concatenate(affineRed.getTransform());
                }
                cachableRed = affineRed.getSource();
            }
            else {
                if (!(cachableRed instanceof TranslateRed)) {
                    break;
                }
                final TranslateRed translateRed = (TranslateRed)cachableRed;
                final int deltaX = translateRed.getDeltaX();
                final int deltaY = translateRed.getDeltaY();
                if (transform == null) {
                    transform = AffineTransform.getTranslateInstance(deltaX, deltaY);
                }
                else {
                    transform.translate(deltaX, deltaY);
                }
                cachableRed = translateRed.getSource();
            }
        }
        final AffineTransform transform2 = graphics2D.getTransform();
        if (transform == null || transform.isIdentity()) {
            transform = transform2;
        }
        else {
            transform.preConcatenate(transform2);
        }
        final ColorModel colorModel = cachableRed.getColorModel();
        final ColorModel destinationColorModel = getDestinationColorModel(graphics2D);
        ColorSpace colorSpace = null;
        if (destinationColorModel != null) {
            colorSpace = destinationColorModel.getColorSpace();
        }
        if (colorSpace == null) {
            colorSpace = ColorSpace.getInstance(1000);
        }
        ColorModel srgb_Unpre = destinationColorModel;
        if (destinationColorModel == null || !destinationColorModel.hasAlpha()) {
            srgb_Unpre = GraphicsUtil.sRGB_Unpre;
        }
        if (cachableRed instanceof BufferedImageCachableRed && colorSpace.equals(colorModel.getColorSpace()) && srgb_Unpre.equals(colorModel)) {
            graphics2D.setTransform(transform);
            final BufferedImageCachableRed bufferedImageCachableRed = (BufferedImageCachableRed)cachableRed;
            graphics2D.drawImage(bufferedImageCachableRed.getBufferedImage(), bufferedImageCachableRed.getMinX(), bufferedImageCachableRed.getMinY(), null);
            graphics2D.setTransform(transform2);
            return;
        }
        final double determinant = transform.getDeterminant();
        if (!transform.isIdentity() && determinant <= 1.0) {
            if (transform.getType() != 1) {
                cachableRed = new AffineRed(cachableRed, transform, graphics2D.getRenderingHints());
            }
            else {
                cachableRed = new TranslateRed(cachableRed, cachableRed.getMinX() + (int)transform.getTranslateX(), cachableRed.getMinY() + (int)transform.getTranslateY());
            }
        }
        if (colorSpace != colorModel.getColorSpace()) {
            if (colorSpace == ColorSpace.getInstance(1000)) {
                cachableRed = convertTosRGB(cachableRed);
            }
            else if (colorSpace == ColorSpace.getInstance(1004)) {
                cachableRed = convertToLsRGB(cachableRed);
            }
        }
        if (!srgb_Unpre.equals(cachableRed.getColorModel())) {
            cachableRed = FormatRed.construct(cachableRed, srgb_Unpre);
        }
        if (!transform.isIdentity() && determinant > 1.0) {
            cachableRed = new AffineRed(cachableRed, transform, graphics2D.getRenderingHints());
        }
        graphics2D.setTransform(GraphicsUtil.IDENTITY);
        final Composite composite = graphics2D.getComposite();
        if (graphics2D.getRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING) == "Printing" && SVGComposite.OVER.equals(composite)) {
            graphics2D.setComposite(SVGComposite.OVER);
        }
        final Rectangle bounds = cachableRed.getBounds();
        final Shape clip = graphics2D.getClip();
        try {
            Rectangle rectangle;
            if (clip == null) {
                rectangle = bounds;
            }
            else {
                final Rectangle bounds2 = clip.getBounds();
                if (!bounds2.intersects(bounds)) {
                    return;
                }
                rectangle = bounds2.intersection(bounds);
            }
            final Rectangle destinationBounds = getDestinationBounds(graphics2D);
            if (destinationBounds != null) {
                if (!rectangle.intersects(destinationBounds)) {
                    return;
                }
                rectangle = rectangle.intersection(destinationBounds);
            }
            boolean b = false;
            final ColorModel colorModel2 = cachableRed.getColorModel();
            final SampleModel sampleModel = cachableRed.getSampleModel();
            if (sampleModel.getWidth() * sampleModel.getHeight() >= rectangle.width * rectangle.height) {
                b = true;
            }
            final Object renderingHint = graphics2D.getRenderingHint(RenderingHintsKeyExt.KEY_AVOID_TILE_PAINTING);
            if (renderingHint == RenderingHintsKeyExt.VALUE_AVOID_TILE_PAINTING_ON) {
                b = true;
            }
            if (renderingHint == RenderingHintsKeyExt.VALUE_AVOID_TILE_PAINTING_OFF) {
                b = false;
            }
            if (b) {
                graphics2D.drawImage(new BufferedImage(colorModel2, ((WritableRaster)cachableRed.getData(rectangle)).createWritableChild(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 0, 0, null), colorModel2.isAlphaPremultiplied(), null), rectangle.x, rectangle.y, null);
            }
            else {
                final WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, new Point(0, 0));
                final BufferedImage bufferedImage = new BufferedImage(colorModel2, writableRaster, colorModel2.isAlphaPremultiplied(), null);
                final int minTileX = cachableRed.getMinTileX();
                final int n = minTileX + cachableRed.getNumXTiles();
                final int minTileY = cachableRed.getMinTileY();
                final int n2 = minTileY + cachableRed.getNumYTiles();
                final int width = sampleModel.getWidth();
                final int height = sampleModel.getHeight();
                final Rectangle src2 = new Rectangle(0, 0, width, height);
                final Rectangle dest = new Rectangle(0, 0, 0, 0);
                int n3 = (rectangle.y - (minTileY * height + cachableRed.getTileGridYOffset())) / height;
                if (n3 < 0) {
                    n3 = 0;
                }
                final int n4 = minTileY + n3;
                int n5 = (rectangle.x - (minTileX * width + cachableRed.getTileGridXOffset())) / width;
                if (n5 < 0) {
                    n5 = 0;
                }
                final int n6 = minTileX + n5;
                final int n7 = rectangle.x + rectangle.width - 1;
                final int n8 = rectangle.y + rectangle.height - 1;
                int y = n4 * height + cachableRed.getTileGridYOffset();
                final int n9 = n6 * width + cachableRed.getTileGridXOffset();
                int n10 = width;
                int x = n9;
                for (int i = n4; i < n2; ++i, y += height) {
                    if (y > n8) {
                        break;
                    }
                    for (int n11 = n6; n11 < n && x >= n9 && x <= n7; ++n11, x += n10) {
                        src2.x = x;
                        src2.y = y;
                        Rectangle2D.intersect(bounds, src2, dest);
                        cachableRed.copyData(writableRaster.createWritableChild(0, 0, dest.width, dest.height, dest.x, dest.y, null));
                        graphics2D.drawImage(bufferedImage.getSubimage(0, 0, dest.width, dest.height), dest.x, dest.y, null);
                    }
                    n10 = -n10;
                    x += n10;
                }
            }
        }
        finally {
            graphics2D.setTransform(transform2);
            graphics2D.setComposite(composite);
        }
    }
    
    public static void drawImage(final Graphics2D graphics2D, final RenderableImage renderableImage, final RenderContext renderContext) {
        final AffineTransform transform = graphics2D.getTransform();
        final Shape clip = graphics2D.getClip();
        final RenderingHints renderingHints = graphics2D.getRenderingHints();
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        if (areaOfInterest != null) {
            graphics2D.clip(areaOfInterest);
        }
        graphics2D.transform(renderContext.getTransform());
        graphics2D.setRenderingHints(renderContext.getRenderingHints());
        drawImage(graphics2D, renderableImage);
        graphics2D.setTransform(transform);
        graphics2D.setClip(clip);
        graphics2D.setRenderingHints(renderingHints);
    }
    
    public static void drawImage(final Graphics2D graphics2D, final RenderableImage renderableImage) {
        if (renderableImage instanceof PaintRable && ((PaintRable)renderableImage).paintRable(graphics2D)) {
            return;
        }
        final AffineTransform transform = graphics2D.getTransform();
        final RenderedImage rendering = renderableImage.createRendering(new RenderContext(transform, graphics2D.getClip(), graphics2D.getRenderingHints()));
        if (rendering == null) {
            return;
        }
        graphics2D.setTransform(GraphicsUtil.IDENTITY);
        drawImage(graphics2D, wrap(rendering));
        graphics2D.setTransform(transform);
    }
    
    public static Graphics2D createGraphics(final BufferedImage referent, final RenderingHints renderingHints) {
        final Graphics2D graphics = referent.createGraphics();
        if (renderingHints != null) {
            graphics.addRenderingHints(renderingHints);
        }
        graphics.setRenderingHint(RenderingHintsKeyExt.KEY_BUFFERED_IMAGE, new WeakReference(referent));
        graphics.clip(new Rectangle(0, 0, referent.getWidth(), referent.getHeight()));
        return graphics;
    }
    
    public static Graphics2D createGraphics(final BufferedImage referent) {
        final Graphics2D graphics = referent.createGraphics();
        graphics.setRenderingHint(RenderingHintsKeyExt.KEY_BUFFERED_IMAGE, new WeakReference(referent));
        graphics.clip(new Rectangle(0, 0, referent.getWidth(), referent.getHeight()));
        return graphics;
    }
    
    public static BufferedImage getDestination(final Graphics2D graphics2D) {
        final Object renderingHint = graphics2D.getRenderingHint(RenderingHintsKeyExt.KEY_BUFFERED_IMAGE);
        if (renderingHint != null) {
            return ((Reference<BufferedImage>)renderingHint).get();
        }
        final GraphicsConfiguration deviceConfiguration = graphics2D.getDeviceConfiguration();
        if (deviceConfiguration == null) {
            return null;
        }
        final GraphicsDevice device = deviceConfiguration.getDevice();
        if (GraphicsUtil.WARN_DESTINATION && device.getType() == 2 && graphics2D.getRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING) != "Printing") {
            System.err.println("Graphics2D from BufferedImage lacks BUFFERED_IMAGE hint");
        }
        return null;
    }
    
    public static ColorModel getDestinationColorModel(final Graphics2D graphics2D) {
        final BufferedImage destination = getDestination(graphics2D);
        if (destination != null) {
            return destination.getColorModel();
        }
        final GraphicsConfiguration deviceConfiguration = graphics2D.getDeviceConfiguration();
        if (deviceConfiguration == null) {
            return null;
        }
        if (deviceConfiguration.getDevice().getType() != 2) {
            return deviceConfiguration.getColorModel();
        }
        if (graphics2D.getRenderingHint(RenderingHintsKeyExt.KEY_TRANSCODING) == "Printing") {
            return GraphicsUtil.sRGB_Unpre;
        }
        return null;
    }
    
    public static ColorSpace getDestinationColorSpace(final Graphics2D graphics2D) {
        final ColorModel destinationColorModel = getDestinationColorModel(graphics2D);
        if (destinationColorModel != null) {
            return destinationColorModel.getColorSpace();
        }
        return null;
    }
    
    public static Rectangle getDestinationBounds(final Graphics2D graphics2D) {
        final BufferedImage destination = getDestination(graphics2D);
        if (destination != null) {
            return new Rectangle(0, 0, destination.getWidth(), destination.getHeight());
        }
        final GraphicsConfiguration deviceConfiguration = graphics2D.getDeviceConfiguration();
        if (deviceConfiguration == null) {
            return null;
        }
        if (deviceConfiguration.getDevice().getType() == 2) {
            return null;
        }
        return null;
    }
    
    public static ColorModel makeLinear_sRGBCM(final boolean b) {
        return b ? GraphicsUtil.Linear_sRGB_Pre : GraphicsUtil.Linear_sRGB_Unpre;
    }
    
    public static BufferedImage makeLinearBufferedImage(final int w, final int h, final boolean isRasterPremultiplied) {
        final ColorModel linear_sRGBCM = makeLinear_sRGBCM(isRasterPremultiplied);
        return new BufferedImage(linear_sRGBCM, linear_sRGBCM.createCompatibleWritableRaster(w, h), isRasterPremultiplied, null);
    }
    
    public static CachableRed convertToLsRGB(final CachableRed cachableRed) {
        if (cachableRed.getColorModel().getColorSpace() == ColorSpace.getInstance(1004)) {
            return cachableRed;
        }
        return new Any2LsRGBRed(cachableRed);
    }
    
    public static CachableRed convertTosRGB(final CachableRed cachableRed) {
        if (cachableRed.getColorModel().getColorSpace() == ColorSpace.getInstance(1000)) {
            return cachableRed;
        }
        return new Any2sRGBRed(cachableRed);
    }
    
    public static CachableRed wrap(final RenderedImage renderedImage) {
        if (renderedImage instanceof CachableRed) {
            return (CachableRed)renderedImage;
        }
        if (renderedImage instanceof BufferedImage) {
            return new BufferedImageCachableRed((BufferedImage)renderedImage);
        }
        return new RenderedImageCachableRed(renderedImage);
    }
    
    public static void copyData_INT_PACK(final Raster raster, final WritableRaster writableRaster) {
        int n = writableRaster.getMinX();
        if (n < raster.getMinX()) {
            n = raster.getMinX();
        }
        int n2 = writableRaster.getMinY();
        if (n2 < raster.getMinY()) {
            n2 = raster.getMinY();
        }
        int n3 = writableRaster.getMinX() + writableRaster.getWidth() - 1;
        if (n3 > raster.getMinX() + raster.getWidth() - 1) {
            n3 = raster.getMinX() + raster.getWidth() - 1;
        }
        int n4 = writableRaster.getMinY() + writableRaster.getHeight() - 1;
        if (n4 > raster.getMinY() + raster.getHeight() - 1) {
            n4 = raster.getMinY() + raster.getHeight() - 1;
        }
        final int n5 = n3 - n + 1;
        final int n6 = n4 - n2 + 1;
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
        final int[] array = dataBufferInt.getBankData()[0];
        final int n7 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(n - raster.getSampleModelTranslateX(), n2 - raster.getSampleModelTranslateY());
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)writableRaster.getDataBuffer();
        final int[] array2 = dataBufferInt2.getBankData()[0];
        final int n8 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(n - writableRaster.getSampleModelTranslateX(), n2 - writableRaster.getSampleModelTranslateY());
        if (scanlineStride == scanlineStride2 && scanlineStride == n5) {
            System.arraycopy(array, n7, array2, n8, n5 * n6);
        }
        else if (n5 > 128) {
            int n9 = n7;
            int n10 = n8;
            for (int i = 0; i < n6; ++i) {
                System.arraycopy(array, n9, array2, n10, n5);
                n9 += scanlineStride;
                n10 += scanlineStride2;
            }
        }
        else {
            for (int j = 0; j < n6; ++j) {
                int n11 = n7 + j * scanlineStride;
                int n12 = n8 + j * scanlineStride2;
                for (int k = 0; k < n5; ++k) {
                    array2[n12++] = array[n11++];
                }
            }
        }
    }
    
    public static void copyData_FALLBACK(final Raster raster, final WritableRaster writableRaster) {
        int n = writableRaster.getMinX();
        if (n < raster.getMinX()) {
            n = raster.getMinX();
        }
        int n2 = writableRaster.getMinY();
        if (n2 < raster.getMinY()) {
            n2 = raster.getMinY();
        }
        int n3 = writableRaster.getMinX() + writableRaster.getWidth() - 1;
        if (n3 > raster.getMinX() + raster.getWidth() - 1) {
            n3 = raster.getMinX() + raster.getWidth() - 1;
        }
        int n4 = writableRaster.getMinY() + writableRaster.getHeight() - 1;
        if (n4 > raster.getMinY() + raster.getHeight() - 1) {
            n4 = raster.getMinY() + raster.getHeight() - 1;
        }
        final int n5 = n3 - n + 1;
        int[] pixels = null;
        for (int i = n2; i <= n4; ++i) {
            pixels = raster.getPixels(n, i, n5, 1, pixels);
            writableRaster.setPixels(n, i, n5, 1, pixels);
        }
    }
    
    public static void copyData(final Raster raster, final WritableRaster writableRaster) {
        if (is_INT_PACK_Data(raster.getSampleModel(), false) && is_INT_PACK_Data(writableRaster.getSampleModel(), false)) {
            copyData_INT_PACK(raster, writableRaster);
            return;
        }
        copyData_FALLBACK(raster, writableRaster);
    }
    
    public static WritableRaster copyRaster(final Raster raster) {
        return copyRaster(raster, raster.getMinX(), raster.getMinY());
    }
    
    public static WritableRaster copyRaster(final Raster raster, final int childMinX, final int childMinY) {
        final WritableRaster writableChild = Raster.createWritableRaster(raster.getSampleModel(), new Point(0, 0)).createWritableChild(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY(), raster.getWidth(), raster.getHeight(), childMinX, childMinY, null);
        final DataBuffer dataBuffer = raster.getDataBuffer();
        final DataBuffer dataBuffer2 = writableChild.getDataBuffer();
        if (dataBuffer.getDataType() != dataBuffer2.getDataType()) {
            throw new IllegalArgumentException("New DataBuffer doesn't match original");
        }
        final int size = dataBuffer.getSize();
        final int numBanks = dataBuffer.getNumBanks();
        final int[] offsets = dataBuffer.getOffsets();
        for (int i = 0; i < numBanks; ++i) {
            switch (dataBuffer.getDataType()) {
                case 0: {
                    System.arraycopy(((DataBufferByte)dataBuffer).getData(i), offsets[i], ((DataBufferByte)dataBuffer2).getData(i), offsets[i], size);
                    break;
                }
                case 3: {
                    System.arraycopy(((DataBufferInt)dataBuffer).getData(i), offsets[i], ((DataBufferInt)dataBuffer2).getData(i), offsets[i], size);
                    break;
                }
                case 2: {
                    System.arraycopy(((DataBufferShort)dataBuffer).getData(i), offsets[i], ((DataBufferShort)dataBuffer2).getData(i), offsets[i], size);
                    break;
                }
                case 1: {
                    System.arraycopy(((DataBufferUShort)dataBuffer).getData(i), offsets[i], ((DataBufferUShort)dataBuffer2).getData(i), offsets[i], size);
                    break;
                }
            }
        }
        return writableChild;
    }
    
    public static WritableRaster makeRasterWritable(final Raster raster) {
        return makeRasterWritable(raster, raster.getMinX(), raster.getMinY());
    }
    
    public static WritableRaster makeRasterWritable(final Raster raster, final int childMinX, final int childMinY) {
        return Raster.createWritableRaster(raster.getSampleModel(), raster.getDataBuffer(), new Point(0, 0)).createWritableChild(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY(), raster.getWidth(), raster.getHeight(), childMinX, childMinY, null);
    }
    
    public static ColorModel coerceColorModel(final ColorModel colorModel, final boolean isAlphaPremultiplied) {
        if (colorModel.isAlphaPremultiplied() == isAlphaPremultiplied) {
            return colorModel;
        }
        return colorModel.coerceData(colorModel.createCompatibleWritableRaster(1, 1), isAlphaPremultiplied);
    }
    
    public static ColorModel coerceData(final WritableRaster writableRaster, final ColorModel colorModel, final boolean b) {
        if (!colorModel.hasAlpha()) {
            return colorModel;
        }
        if (colorModel.isAlphaPremultiplied() == b) {
            return colorModel;
        }
        if (b) {
            multiplyAlpha(writableRaster);
        }
        else {
            divideAlpha(writableRaster);
        }
        return coerceColorModel(colorModel, b);
    }
    
    public static void multiplyAlpha(final WritableRaster writableRaster) {
        if (is_BYTE_COMP_Data(writableRaster.getSampleModel())) {
            mult_BYTE_COMP_Data(writableRaster);
        }
        else if (is_INT_PACK_Data(writableRaster.getSampleModel(), true)) {
            mult_INT_PACK_Data(writableRaster);
        }
        else {
            int[] pixel = null;
            final int numBands = writableRaster.getNumBands();
            final float n = 0.003921569f;
            final int minX = writableRaster.getMinX();
            final int n2 = minX + writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n3 = minY + writableRaster.getHeight(), i = minY; i < n3; ++i) {
                for (int j = minX; j < n2; ++j) {
                    pixel = writableRaster.getPixel(j, i, pixel);
                    final int n4 = pixel[numBands - 1];
                    if (n4 >= 0 && n4 < 255) {
                        final float n5 = n4 * n;
                        for (int k = 0; k < numBands - 1; ++k) {
                            pixel[k] = (int)(pixel[k] * n5 + 0.5f);
                        }
                        writableRaster.setPixel(j, i, pixel);
                    }
                }
            }
        }
    }
    
    public static void divideAlpha(final WritableRaster writableRaster) {
        if (is_BYTE_COMP_Data(writableRaster.getSampleModel())) {
            divide_BYTE_COMP_Data(writableRaster);
        }
        else if (is_INT_PACK_Data(writableRaster.getSampleModel(), true)) {
            divide_INT_PACK_Data(writableRaster);
        }
        else {
            final int numBands = writableRaster.getNumBands();
            int[] pixel = null;
            final int minX = writableRaster.getMinX();
            final int n = minX + writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n2 = minY + writableRaster.getHeight(), i = minY; i < n2; ++i) {
                for (int j = minX; j < n; ++j) {
                    pixel = writableRaster.getPixel(j, i, pixel);
                    final int n3 = pixel[numBands - 1];
                    if (n3 > 0 && n3 < 255) {
                        final float n4 = 255.0f / n3;
                        for (int k = 0; k < numBands - 1; ++k) {
                            pixel[k] = (int)(pixel[k] * n4 + 0.5f);
                        }
                        writableRaster.setPixel(j, i, pixel);
                    }
                }
            }
        }
    }
    
    public static void copyData(final BufferedImage bufferedImage, final BufferedImage bufferedImage2) {
        copyData(bufferedImage, new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()), bufferedImage2, new Point(0, 0));
    }
    
    public static void copyData(final BufferedImage bufferedImage, final Rectangle rectangle, final BufferedImage bufferedImage2, final Point point) {
        final boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();
        final boolean hasAlpha2 = bufferedImage2.getColorModel().hasAlpha();
        if (hasAlpha == hasAlpha2 && (!hasAlpha || bufferedImage.isAlphaPremultiplied() == bufferedImage2.isAlphaPremultiplied())) {
            copyData(bufferedImage.getRaster(), bufferedImage2.getRaster());
            return;
        }
        int[] array = null;
        final WritableRaster raster = bufferedImage.getRaster();
        final WritableRaster raster2 = bufferedImage2.getRaster();
        final int numBands = raster2.getNumBands();
        final int n = point.x - rectangle.x;
        final int n2 = point.y - rectangle.y;
        final int width = rectangle.width;
        final int x = rectangle.x;
        final int y = rectangle.y;
        final int n3 = y + rectangle.height - 1;
        if (!hasAlpha) {
            final int[] iArray = new int[numBands * width];
            for (int i = width * numBands - 1; i >= 0; i -= numBands) {
                iArray[i] = 255;
            }
            for (int j = y; j <= n3; ++j) {
                array = raster.getPixels(x, j, width, 1, array);
                int k = width * (numBands - 1) - 1;
                int n4 = width * numBands - 2;
                switch (numBands) {
                    case 4: {
                        while (k >= 0) {
                            final int[] array2 = iArray;
                            final int n5 = n4;
                            final int n6 = n5 - 1;
                            final int[] array3 = array;
                            final int n7 = k;
                            final int n8 = n7 - 1;
                            array2[n5] = array3[n7];
                            final int[] array4 = iArray;
                            final int n9 = n6;
                            final int n10 = n9 - 1;
                            final int[] array5 = array;
                            final int n11 = n8;
                            final int n12 = n11 - 1;
                            array4[n9] = array5[n11];
                            final int[] array6 = iArray;
                            final int n13 = n10;
                            n4 = n13 - 1;
                            final int[] array7 = array;
                            final int n14 = n12;
                            k = n14 - 1;
                            array6[n13] = array7[n14];
                            --n4;
                        }
                        break;
                    }
                    default: {
                        while (k >= 0) {
                            for (int l = 0; l < numBands - 1; ++l) {
                                iArray[n4--] = array[k--];
                            }
                            --n4;
                        }
                        break;
                    }
                }
                raster2.setPixels(x + n, j + n2, width, 1, iArray);
            }
        }
        else if (hasAlpha2 && bufferedImage2.isAlphaPremultiplied()) {
            final int n15 = 65793;
            final int n16 = 8388608;
            for (int y2 = y; y2 <= n3; ++y2) {
                array = raster.getPixels(x, y2, width, 1, array);
                int n17 = numBands * width - 1;
                switch (numBands) {
                    case 4: {
                        while (n17 >= 0) {
                            final int n18 = array[n17];
                            if (n18 == 255) {
                                n17 -= 4;
                            }
                            else {
                                --n17;
                                final int n19 = n15 * n18;
                                array[n17] = array[n17] * n19 + n16 >>> 24;
                                --n17;
                                array[n17] = array[n17] * n19 + n16 >>> 24;
                                --n17;
                                array[n17] = array[n17] * n19 + n16 >>> 24;
                                --n17;
                            }
                        }
                        break;
                    }
                    default: {
                        while (n17 >= 0) {
                            final int n20 = array[n17];
                            if (n20 == 255) {
                                n17 -= numBands;
                            }
                            else {
                                --n17;
                                final int n21 = n15 * n20;
                                for (int n22 = 0; n22 < numBands - 1; ++n22) {
                                    array[n17] = array[n17] * n21 + n16 >>> 24;
                                    --n17;
                                }
                            }
                        }
                        break;
                    }
                }
                raster2.setPixels(x + n, y2 + n2, width, 1, array);
            }
        }
        else if (hasAlpha2 && !bufferedImage2.isAlphaPremultiplied()) {
            final int n23 = 16711680;
            final int n24 = 32768;
            for (int y3 = y; y3 <= n3; ++y3) {
                array = raster.getPixels(x, y3, width, 1, array);
                int n25 = numBands * width - 1;
                switch (numBands) {
                    case 4: {
                        while (n25 >= 0) {
                            final int n26 = array[n25];
                            if (n26 <= 0 || n26 >= 255) {
                                n25 -= 4;
                            }
                            else {
                                --n25;
                                final int n27 = n23 / n26;
                                array[n25] = array[n25] * n27 + n24 >>> 16;
                                --n25;
                                array[n25] = array[n25] * n27 + n24 >>> 16;
                                --n25;
                                array[n25] = array[n25] * n27 + n24 >>> 16;
                                --n25;
                            }
                        }
                        break;
                    }
                    default: {
                        while (n25 >= 0) {
                            final int n28 = array[n25];
                            if (n28 <= 0 || n28 >= 255) {
                                n25 -= numBands;
                            }
                            else {
                                --n25;
                                final int n29 = n23 / n28;
                                for (int n30 = 0; n30 < numBands - 1; ++n30) {
                                    array[n25] = array[n25] * n29 + n24 >>> 16;
                                    --n25;
                                }
                            }
                        }
                        break;
                    }
                }
                raster2.setPixels(x + n, y3 + n2, width, 1, array);
            }
        }
        else if (bufferedImage.isAlphaPremultiplied()) {
            final int[] iArray2 = new int[numBands * width];
            final int n31 = 16711680;
            final int n32 = 32768;
            for (int y4 = y; y4 <= n3; ++y4) {
                array = raster.getPixels(x, y4, width, 1, array);
                int n33 = (numBands + 1) * width - 1;
                int n34 = numBands * width - 1;
                while (n33 >= 0) {
                    final int n35 = array[n33];
                    --n33;
                    if (n35 > 0) {
                        if (n35 < 255) {
                            final int n36 = n31 / n35;
                            for (int n37 = 0; n37 < numBands; ++n37) {
                                iArray2[n34--] = array[n33--] * n36 + n32 >>> 16;
                            }
                        }
                        else {
                            for (int n38 = 0; n38 < numBands; ++n38) {
                                iArray2[n34--] = array[n33--];
                            }
                        }
                    }
                    else {
                        n33 -= numBands;
                        for (int n39 = 0; n39 < numBands; ++n39) {
                            iArray2[n34--] = 255;
                        }
                    }
                }
                raster2.setPixels(x + n, y4 + n2, width, 1, iArray2);
            }
        }
        else {
            final Rectangle rectangle2 = new Rectangle(point.x, point.y, rectangle.width, rectangle.height);
            for (int n40 = 0; n40 < numBands; ++n40) {
                copyBand(raster, rectangle, n40, raster2, rectangle2, n40);
            }
        }
    }
    
    public static void copyBand(final Raster raster, final int n, final WritableRaster writableRaster, final int n2) {
        final Rectangle intersection = raster.getBounds().intersection(writableRaster.getBounds());
        copyBand(raster, intersection, n, writableRaster, intersection, n2);
    }
    
    public static void copyBand(final Raster raster, Rectangle intersection, final int b, final WritableRaster writableRaster, Rectangle intersection2, final int b2) {
        final int n = intersection2.y - intersection.y;
        final int n2 = intersection2.x - intersection.x;
        intersection = intersection.intersection(raster.getBounds());
        intersection2 = intersection2.intersection(writableRaster.getBounds());
        int n3;
        if (intersection2.width < intersection.width) {
            n3 = intersection2.width;
        }
        else {
            n3 = intersection.width;
        }
        int n4;
        if (intersection2.height < intersection.height) {
            n4 = intersection2.height;
        }
        else {
            n4 = intersection.height;
        }
        final int x = intersection.x + n2;
        int[] samples = null;
        for (int i = intersection.y; i < intersection.y + n4; ++i) {
            samples = raster.getSamples(intersection.x, i, n3, 1, b, samples);
            writableRaster.setSamples(x, i + n, n3, 1, b2, samples);
        }
    }
    
    public static boolean is_INT_PACK_Data(final SampleModel sampleModel, final boolean b) {
        if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
            return false;
        }
        if (sampleModel.getDataType() != 3) {
            return false;
        }
        final int[] bitMasks = ((SinglePixelPackedSampleModel)sampleModel).getBitMasks();
        if (bitMasks.length == 3) {
            if (b) {
                return false;
            }
        }
        else if (bitMasks.length != 4) {
            return false;
        }
        return bitMasks[0] == 16711680 && bitMasks[1] == 65280 && bitMasks[2] == 255 && (bitMasks.length != 4 || bitMasks[3] == -16777216);
    }
    
    public static boolean is_BYTE_COMP_Data(final SampleModel sampleModel) {
        return sampleModel instanceof ComponentSampleModel && sampleModel.getDataType() == 0;
    }
    
    protected static void divide_INT_PACK_Data(final WritableRaster writableRaster) {
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int width = writableRaster.getWidth();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        for (int i = 0; i < writableRaster.getHeight(); ++i) {
            for (int j = n + i * scanlineStride; j < j + width; ++j) {
                final int n2 = array[j];
                final int n3 = n2 >>> 24;
                if (n3 <= 0) {
                    array[j] = 16777215;
                }
                else if (n3 < 255) {
                    final int n4 = 16711680 / n3;
                    array[j] = (n3 << 24 | (((n2 & 0xFF0000) >> 16) * n4 & 0xFF0000) | (((n2 & 0xFF00) >> 8) * n4 & 0xFF0000) >> 8 | ((n2 & 0xFF) * n4 & 0xFF0000) >> 16);
                }
            }
        }
    }
    
    protected static void mult_INT_PACK_Data(final WritableRaster writableRaster) {
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int width = writableRaster.getWidth();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        for (int i = 0; i < writableRaster.getHeight(); ++i) {
            for (int j = n + i * scanlineStride; j < j + width; ++j) {
                final int n2 = array[j];
                final int n3 = n2 >>> 24;
                if (n3 >= 0 && n3 < 255) {
                    array[j] = (n3 << 24 | ((n2 & 0xFF0000) * n3 >> 8 & 0xFF0000) | ((n2 & 0xFF00) * n3 >> 8 & 0xFF00) | ((n2 & 0xFF) * n3 >> 8 & 0xFF));
                }
            }
        }
    }
    
    protected static void divide_BYTE_COMP_Data(final WritableRaster writableRaster) {
        final ComponentSampleModel componentSampleModel = (ComponentSampleModel)writableRaster.getSampleModel();
        final int width = writableRaster.getWidth();
        final int scanlineStride = componentSampleModel.getScanlineStride();
        final int pixelStride = componentSampleModel.getPixelStride();
        final int[] bandOffsets = componentSampleModel.getBandOffsets();
        final DataBufferByte dataBufferByte = (DataBufferByte)writableRaster.getDataBuffer();
        final int n = dataBufferByte.getOffset() + componentSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int n2 = bandOffsets[bandOffsets.length - 1];
        final int n3 = bandOffsets.length - 1;
        final byte[] array = dataBufferByte.getBankData()[0];
        for (int i = 0; i < writableRaster.getHeight(); ++i) {
            for (int j = n + i * scanlineStride; j < j + width * pixelStride; j += pixelStride) {
                final int n4 = array[j + n2] & 0xFF;
                if (n4 == 0) {
                    for (int k = 0; k < n3; ++k) {
                        array[j + bandOffsets[k]] = -1;
                    }
                }
                else if (n4 < 255) {
                    final int n5 = 16711680 / n4;
                    for (int l = 0; l < n3; ++l) {
                        final int n6 = j + bandOffsets[l];
                        array[n6] = (byte)((array[n6] & 0xFF) * n5 >>> 16);
                    }
                }
            }
        }
    }
    
    protected static void mult_BYTE_COMP_Data(final WritableRaster writableRaster) {
        final ComponentSampleModel componentSampleModel = (ComponentSampleModel)writableRaster.getSampleModel();
        final int width = writableRaster.getWidth();
        final int scanlineStride = componentSampleModel.getScanlineStride();
        final int pixelStride = componentSampleModel.getPixelStride();
        final int[] bandOffsets = componentSampleModel.getBandOffsets();
        final DataBufferByte dataBufferByte = (DataBufferByte)writableRaster.getDataBuffer();
        final int n = dataBufferByte.getOffset() + componentSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int n2 = bandOffsets[bandOffsets.length - 1];
        final int n3 = bandOffsets.length - 1;
        final byte[] array = dataBufferByte.getBankData()[0];
        for (int i = 0; i < writableRaster.getHeight(); ++i) {
            for (int j = n + i * scanlineStride; j < j + width * pixelStride; j += pixelStride) {
                final int n4 = array[j + n2] & 0xFF;
                if (n4 != 255) {
                    for (int k = 0; k < n3; ++k) {
                        final int n5 = j + bandOffsets[k];
                        array[n5] = (byte)((array[n5] & 0xFF) * n4 >> 8);
                    }
                }
            }
        }
    }
    
    static {
        GraphicsUtil.IDENTITY = new AffineTransform();
        boolean booleanValue = true;
        try {
            booleanValue = Boolean.valueOf(System.getProperty("org.apache.batik.warn_destination", "true"));
        }
        catch (SecurityException ex) {}
        catch (NumberFormatException ex2) {}
        finally {
            WARN_DESTINATION = booleanValue;
        }
        Linear_sRGB = new DirectColorModel(ColorSpace.getInstance(1004), 24, 16711680, 65280, 255, 0, false, 3);
        Linear_sRGB_Pre = new DirectColorModel(ColorSpace.getInstance(1004), 32, 16711680, 65280, 255, -16777216, true, 3);
        Linear_sRGB_Unpre = new DirectColorModel(ColorSpace.getInstance(1004), 32, 16711680, 65280, 255, -16777216, false, 3);
        sRGB = new DirectColorModel(ColorSpace.getInstance(1000), 24, 16711680, 65280, 255, 0, false, 3);
        sRGB_Pre = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
        sRGB_Unpre = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, false, 3);
    }
}
