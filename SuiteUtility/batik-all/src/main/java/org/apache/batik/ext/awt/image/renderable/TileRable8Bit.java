// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Graphics2D;
import org.apache.batik.ext.awt.image.rendered.BufferedImageCachableRed;
import java.awt.geom.Point2D;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.TileRed;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Map;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;

public class TileRable8Bit extends AbstractColorInterpolationRable implements TileRable
{
    private Rectangle2D tileRegion;
    private Rectangle2D tiledRegion;
    private boolean overflow;
    
    public Rectangle2D getTileRegion() {
        return this.tileRegion;
    }
    
    public void setTileRegion(final Rectangle2D tileRegion) {
        if (tileRegion == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.tileRegion = tileRegion;
    }
    
    public Rectangle2D getTiledRegion() {
        return this.tiledRegion;
    }
    
    public void setTiledRegion(final Rectangle2D tiledRegion) {
        if (tiledRegion == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.tiledRegion = tiledRegion;
    }
    
    public boolean isOverflow() {
        return this.overflow;
    }
    
    public void setOverflow(final boolean overflow) {
        this.touch();
        this.overflow = overflow;
    }
    
    public TileRable8Bit(final Filter filter, final Rectangle2D tiledRegion, final Rectangle2D tileRegion, final boolean overflow) {
        super(filter);
        this.setTileRegion(tileRegion);
        this.setTiledRegion(tiledRegion);
        this.setOverflow(overflow);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter);
    }
    
    public Filter getSource() {
        return this.srcs.get(0);
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.tiledRegion.clone();
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
        final double sqrt = Math.sqrt(scaleX * scaleX + shearY * shearY);
        final double sqrt2 = Math.sqrt(scaleY * scaleY + shearX * shearX);
        final Rectangle2D bounds2D = this.getBounds2D();
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        Rectangle2D bounds2D2;
        if (areaOfInterest == null) {
            bounds2D2 = bounds2D;
        }
        else {
            bounds2D2 = areaOfInterest.getBounds2D();
            if (!bounds2D.intersects(bounds2D2)) {
                return null;
            }
            Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D);
        }
        final Rectangle2D tileRegion = this.tileRegion;
        final int n = (int)Math.ceil(tileRegion.getWidth() * sqrt);
        final int n2 = (int)Math.ceil(tileRegion.getHeight() * sqrt2);
        final double sx = n / tileRegion.getWidth();
        final double sy = n2 / tileRegion.getHeight();
        final int n3 = (int)Math.floor(tileRegion.getX() * sx);
        final int n4 = (int)Math.floor(tileRegion.getY() * sy);
        final double tx = n3 - tileRegion.getX() * sx;
        final double ty = n4 - tileRegion.getY() * sy;
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(tx, ty);
        translateInstance.scale(sx, sy);
        final Filter source = this.getSource();
        Rectangle2D bounds2D3;
        if (this.overflow) {
            bounds2D3 = source.getBounds2D();
        }
        else {
            bounds2D3 = tileRegion;
        }
        final RenderedImage rendering = source.createRendering(new RenderContext(translateInstance, bounds2D3, renderingHints));
        if (rendering == null) {
            return null;
        }
        Rectangle bounds = translateInstance.createTransformedShape(bounds2D2).getBounds();
        if (bounds.width == Integer.MAX_VALUE || bounds.height == Integer.MAX_VALUE) {
            bounds = new Rectangle(-536870912, -536870912, 1073741823, 1073741823);
        }
        final TileRed tileRed = new TileRed(this.convertSourceCS(rendering), bounds, n, n2);
        final AffineTransform affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
        affineTransform.scale(sqrt / sx, sqrt2 / sy);
        affineTransform.translate(-tx, -ty);
        AbstractRed abstractRed = tileRed;
        if (!affineTransform.isIdentity()) {
            abstractRed = new AffineRed(tileRed, affineTransform, renderingHints);
        }
        return abstractRed;
    }
    
    public Rectangle2D getActualTileBounds(final Rectangle2D rectangle2D) {
        final Rectangle2D rectangle2D2 = (Rectangle2D)this.tileRegion.clone();
        if (rectangle2D2.getWidth() <= 0.0 || rectangle2D2.getHeight() <= 0.0 || rectangle2D.getWidth() <= 0.0 || rectangle2D.getHeight() <= 0.0) {
            return null;
        }
        return new Rectangle2D.Double(rectangle2D2.getX(), rectangle2D2.getY(), Math.min(rectangle2D2.getWidth(), rectangle2D.getWidth()), Math.min(rectangle2D2.getHeight(), rectangle2D.getHeight()));
    }
    
    public RenderedImage createTile(final RenderContext renderContext) {
        final AffineTransform transform = renderContext.getTransform();
        final RenderingHints renderingHints = renderContext.getRenderingHints();
        final RenderingHints renderingHints2 = new RenderingHints(null);
        if (renderingHints != null) {
            renderingHints2.add(renderingHints);
        }
        final Rectangle2D bounds2D = this.getBounds2D();
        final Rectangle2D bounds2D2 = renderContext.getAreaOfInterest().getBounds2D();
        if (!bounds2D.intersects(bounds2D2)) {
            return null;
        }
        Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D);
        final Rectangle2D rectangle2D = (Rectangle2D)this.tileRegion.clone();
        if (rectangle2D.getWidth() <= 0.0 || rectangle2D.getHeight() <= 0.0 || bounds2D.getWidth() <= 0.0 || bounds2D.getHeight() <= 0.0) {
            return null;
        }
        final double x = rectangle2D.getX();
        final double y = rectangle2D.getY();
        final double width = rectangle2D.getWidth();
        final double height = rectangle2D.getHeight();
        final double x2 = bounds2D.getX();
        final double y2 = bounds2D.getY();
        final double width2 = bounds2D.getWidth();
        final double height2 = bounds2D.getHeight();
        final double min = Math.min(width, width2);
        final double min2 = Math.min(height, height2);
        final double n = (x2 - x) % width;
        final double n2 = (y2 - y) % height;
        double n3;
        if (n > 0.0) {
            n3 = width - n;
        }
        else {
            n3 = n * -1.0;
        }
        double n4;
        if (n2 > 0.0) {
            n4 = height - n2;
        }
        else {
            n4 = n2 * -1.0;
        }
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double floor = Math.floor(scaleX * n3);
        final double floor2 = Math.floor(scaleY * n4);
        final double n5 = floor / scaleX;
        final double n6 = floor2 / scaleY;
        final Rectangle2D.Double pSrc = new Rectangle2D.Double(x + width - n5, y + height - n6, n5, n6);
        final Rectangle2D.Double pSrc2 = new Rectangle2D.Double(x, y + height - n6, min - n5, n6);
        final Rectangle2D.Double pSrc3 = new Rectangle2D.Double(x + width - n5, y, n5, min2 - n6);
        final Rectangle2D.Double pSrc4 = new Rectangle2D.Double(x, y, min - n5, min2 - n6);
        final Rectangle2D.Double pSrc5 = new Rectangle2D.Double(bounds2D.getX(), bounds2D.getY(), min, min2);
        RenderedImage rendering = null;
        RenderedImage rendering2 = null;
        RenderedImage rendering3 = null;
        RenderedImage rendering4 = null;
        final Filter source = this.getSource();
        if (pSrc.getWidth() > 0.0 && pSrc.getHeight() > 0.0) {
            final Rectangle bounds = transform.createTransformedShape(pSrc).getBounds();
            if (bounds.width > 0 && bounds.height > 0) {
                final AffineTransform usr2dev = new AffineTransform(transform);
                usr2dev.translate(-pSrc.x + x2, -pSrc.y + y2);
                Rectangle2D.Double double1 = pSrc;
                if (this.overflow) {
                    double1 = new Rectangle2D.Double(pSrc.x, pSrc.y, width2, height2);
                }
                renderingHints2.put(RenderingHintsKeyExt.KEY_AREA_OF_INTEREST, double1);
                rendering = source.createRendering(new RenderContext(usr2dev, double1, renderingHints2));
            }
        }
        if (pSrc2.getWidth() > 0.0 && pSrc2.getHeight() > 0.0) {
            final Rectangle bounds2 = transform.createTransformedShape(pSrc2).getBounds();
            if (bounds2.width > 0 && bounds2.height > 0) {
                final AffineTransform usr2dev2 = new AffineTransform(transform);
                usr2dev2.translate(-pSrc2.x + (x2 + n5), -pSrc2.y + y2);
                Rectangle2D.Double double2 = pSrc2;
                if (this.overflow) {
                    double2 = new Rectangle2D.Double(pSrc2.x - width2 + min - n5, pSrc2.y, width2, height2);
                }
                renderingHints2.put(RenderingHintsKeyExt.KEY_AREA_OF_INTEREST, double2);
                rendering2 = source.createRendering(new RenderContext(usr2dev2, double2, renderingHints2));
            }
        }
        if (pSrc3.getWidth() > 0.0 && pSrc3.getHeight() > 0.0) {
            final Rectangle bounds3 = transform.createTransformedShape(pSrc3).getBounds();
            if (bounds3.width > 0 && bounds3.height > 0) {
                final AffineTransform usr2dev3 = new AffineTransform(transform);
                usr2dev3.translate(-pSrc3.x + x2, -pSrc3.y + (y2 + n6));
                Rectangle2D.Double double3 = pSrc3;
                if (this.overflow) {
                    double3 = new Rectangle2D.Double(pSrc3.x, pSrc3.y - height + min2 - n6, width2, height2);
                }
                renderingHints2.put(RenderingHintsKeyExt.KEY_AREA_OF_INTEREST, double3);
                rendering3 = source.createRendering(new RenderContext(usr2dev3, double3, renderingHints2));
            }
        }
        if (pSrc4.getWidth() > 0.0 && pSrc4.getHeight() > 0.0) {
            final Rectangle bounds4 = transform.createTransformedShape(pSrc4).getBounds();
            if (bounds4.width > 0 && bounds4.height > 0) {
                final AffineTransform usr2dev4 = new AffineTransform(transform);
                usr2dev4.translate(-pSrc4.x + (x2 + n5), -pSrc4.y + (y2 + n6));
                Rectangle2D.Double double4 = pSrc4;
                if (this.overflow) {
                    double4 = new Rectangle2D.Double(pSrc4.x - width + min - n5, pSrc4.y - height + min2 - n6, width2, height2);
                }
                renderingHints2.put(RenderingHintsKeyExt.KEY_AREA_OF_INTEREST, double4);
                rendering4 = source.createRendering(new RenderContext(usr2dev4, double4, renderingHints2));
            }
        }
        final Rectangle bounds5 = transform.createTransformedShape(pSrc5).getBounds();
        if (bounds5.width == 0 || bounds5.height == 0) {
            return null;
        }
        final BufferedImage bufferedImage = new BufferedImage(bounds5.width, bounds5.height, 2);
        final Graphics2D graphics = GraphicsUtil.createGraphics(bufferedImage, renderContext.getRenderingHints());
        graphics.translate(-bounds5.x, -bounds5.y);
        final AffineTransform affineTransform = new AffineTransform();
        final Point2D.Double double5 = new Point2D.Double();
        RenderedImage renderedImage = null;
        if (rendering != null) {
            graphics.drawRenderedImage(rendering, affineTransform);
            renderedImage = rendering;
        }
        if (rendering2 != null) {
            if (renderedImage == null) {
                renderedImage = rendering2;
            }
            double5.x = n5;
            double5.y = 0.0;
            transform.deltaTransform(double5, double5);
            double5.x = Math.floor(double5.x) - (rendering2.getMinX() - renderedImage.getMinX());
            double5.y = Math.floor(double5.y) - (rendering2.getMinY() - renderedImage.getMinY());
            graphics.drawRenderedImage(rendering2, affineTransform);
        }
        if (rendering3 != null) {
            if (renderedImage == null) {
                renderedImage = rendering3;
            }
            double5.x = 0.0;
            double5.y = n6;
            transform.deltaTransform(double5, double5);
            double5.x = Math.floor(double5.x) - (rendering3.getMinX() - renderedImage.getMinX());
            double5.y = Math.floor(double5.y) - (rendering3.getMinY() - renderedImage.getMinY());
            graphics.drawRenderedImage(rendering3, affineTransform);
        }
        if (rendering4 != null) {
            if (renderedImage == null) {
                renderedImage = rendering4;
            }
            double5.x = n5;
            double5.y = n6;
            transform.deltaTransform(double5, double5);
            double5.x = Math.floor(double5.x) - (rendering4.getMinX() - renderedImage.getMinX());
            double5.y = Math.floor(double5.y) - (rendering4.getMinY() - renderedImage.getMinY());
            graphics.drawRenderedImage(rendering4, affineTransform);
        }
        return new BufferedImageCachableRed(bufferedImage, bounds5.x, bounds5.y);
    }
}
