// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.image;

import org.apache.batik.transcoder.keys.BooleanKey;
import org.apache.batik.transcoder.keys.PaintKey;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.gvt.renderer.ConcreteImageRendererFactory;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.RenderedImage;
import java.awt.geom.AffineTransform;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Paint;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.apache.batik.transcoder.TranscoderOutput;
import org.w3c.dom.Document;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.SVGAbstractTranscoder;

public abstract class ImageTranscoder extends SVGAbstractTranscoder
{
    public static final TranscodingHints.Key KEY_BACKGROUND_COLOR;
    public static final TranscodingHints.Key KEY_FORCE_TRANSPARENT_WHITE;
    
    protected ImageTranscoder() {
    }
    
    protected void transcode(final Document document, final String s, final TranscoderOutput transcoderOutput) throws TranscoderException {
        super.transcode(document, s, transcoderOutput);
        final int n = (int)(this.width + 0.5);
        final int n2 = (int)(this.height + 0.5);
        final ImageRenderer renderer = this.createRenderer();
        renderer.updateOffScreen(n, n2);
        renderer.setTransform(this.curTxf);
        renderer.setTree(this.root);
        this.root = null;
        try {
            renderer.repaint(this.curTxf.createInverse().createTransformedShape(new Rectangle2D.Float(0.0f, 0.0f, this.width, this.height)));
            final BufferedImage offScreen = renderer.getOffScreen();
            final BufferedImage image = this.createImage(n, n2);
            final Graphics2D graphics = GraphicsUtil.createGraphics(image);
            if (this.hints.containsKey(ImageTranscoder.KEY_BACKGROUND_COLOR)) {
                final Paint paint = (Paint)this.hints.get(ImageTranscoder.KEY_BACKGROUND_COLOR);
                graphics.setComposite(AlphaComposite.SrcOver);
                graphics.setPaint(paint);
                graphics.fillRect(0, 0, n, n2);
            }
            if (offScreen != null) {
                graphics.drawRenderedImage(offScreen, new AffineTransform());
            }
            graphics.dispose();
            this.writeImage(image, transcoderOutput);
        }
        catch (Exception ex) {
            throw new TranscoderException(ex);
        }
    }
    
    protected ImageRenderer createRenderer() {
        return new ConcreteImageRendererFactory().createStaticImageRenderer();
    }
    
    protected void forceTransparentWhite(final BufferedImage bufferedImage, final SinglePixelPackedSampleModel singlePixelPackedSampleModel) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)bufferedImage.getRaster().getDataBuffer();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int offset = dataBufferInt.getOffset();
        final int[] array = dataBufferInt.getBankData()[0];
        int n = offset;
        final int n2 = scanlineStride - width;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int n3 = array[n];
                final int n4 = n3 >> 24 & 0xFF;
                array[n++] = ((n4 << 24 & 0xFF000000) | ((255 * (255 - n4) + n4 * (n3 >> 16 & 0xFF)) / 255 << 16 & 0xFF0000) | ((255 * (255 - n4) + n4 * (n3 >> 8 & 0xFF)) / 255 << 8 & 0xFF00) | ((255 * (255 - n4) + n4 * (n3 & 0xFF)) / 255 & 0xFF));
            }
            n += n2;
        }
    }
    
    public abstract BufferedImage createImage(final int p0, final int p1);
    
    public abstract void writeImage(final BufferedImage p0, final TranscoderOutput p1) throws TranscoderException;
    
    static {
        KEY_BACKGROUND_COLOR = new PaintKey();
        KEY_FORCE_TRANSPARENT_WHITE = new BooleanKey();
    }
}
