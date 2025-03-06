// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.parser.ParseException;
import org.w3c.dom.Element;

public abstract class UnitProcessor extends org.apache.batik.parser.UnitProcessor
{
    public static Context createContext(final BridgeContext bridgeContext, final Element element) {
        return new DefaultContext(bridgeContext, element);
    }
    
    public static float svgHorizontalCoordinateToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgToObjectBoundingBox(s, s2, (short)2, context);
    }
    
    public static float svgVerticalCoordinateToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgToObjectBoundingBox(s, s2, (short)1, context);
    }
    
    public static float svgOtherCoordinateToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgToObjectBoundingBox(s, s2, (short)0, context);
    }
    
    public static float svgHorizontalLengthToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgLengthToObjectBoundingBox(s, s2, (short)2, context);
    }
    
    public static float svgVerticalLengthToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgLengthToObjectBoundingBox(s, s2, (short)1, context);
    }
    
    public static float svgOtherLengthToObjectBoundingBox(final String s, final String s2, final Context context) {
        return svgLengthToObjectBoundingBox(s, s2, (short)0, context);
    }
    
    public static float svgLengthToObjectBoundingBox(final String s, final String s2, final short n, final Context context) {
        final float svgToObjectBoundingBox = svgToObjectBoundingBox(s, s2, n, context);
        if (svgToObjectBoundingBox < 0.0f) {
            throw new BridgeException(getBridgeContext(context), context.getElement(), "length.negative", new Object[] { s2, s });
        }
        return svgToObjectBoundingBox;
    }
    
    public static float svgToObjectBoundingBox(final String s, final String s2, final short n, final Context context) {
        try {
            return org.apache.batik.parser.UnitProcessor.svgToObjectBoundingBox(s, s2, n, context);
        }
        catch (ParseException ex) {
            throw new BridgeException(getBridgeContext(context), context.getElement(), ex, "attribute.malformed", new Object[] { s2, s, ex });
        }
    }
    
    public static float svgHorizontalLengthToUserSpace(final String s, final String s2, final Context context) {
        return svgLengthToUserSpace(s, s2, (short)2, context);
    }
    
    public static float svgVerticalLengthToUserSpace(final String s, final String s2, final Context context) {
        return svgLengthToUserSpace(s, s2, (short)1, context);
    }
    
    public static float svgOtherLengthToUserSpace(final String s, final String s2, final Context context) {
        return svgLengthToUserSpace(s, s2, (short)0, context);
    }
    
    public static float svgHorizontalCoordinateToUserSpace(final String s, final String s2, final Context context) {
        return svgToUserSpace(s, s2, (short)2, context);
    }
    
    public static float svgVerticalCoordinateToUserSpace(final String s, final String s2, final Context context) {
        return svgToUserSpace(s, s2, (short)1, context);
    }
    
    public static float svgOtherCoordinateToUserSpace(final String s, final String s2, final Context context) {
        return svgToUserSpace(s, s2, (short)0, context);
    }
    
    public static float svgLengthToUserSpace(final String s, final String s2, final short n, final Context context) {
        final float svgToUserSpace = svgToUserSpace(s, s2, n, context);
        if (svgToUserSpace < 0.0f) {
            throw new BridgeException(getBridgeContext(context), context.getElement(), "length.negative", new Object[] { s2, s });
        }
        return svgToUserSpace;
    }
    
    public static float svgToUserSpace(final String s, final String s2, final short n, final Context context) {
        try {
            return org.apache.batik.parser.UnitProcessor.svgToUserSpace(s, s2, n, context);
        }
        catch (ParseException ex) {
            throw new BridgeException(getBridgeContext(context), context.getElement(), ex, "attribute.malformed", new Object[] { s2, s, ex });
        }
    }
    
    protected static BridgeContext getBridgeContext(final Context context) {
        if (context instanceof DefaultContext) {
            return ((DefaultContext)context).ctx;
        }
        return null;
    }
    
    public static class DefaultContext implements Context
    {
        protected Element e;
        protected BridgeContext ctx;
        
        public DefaultContext(final BridgeContext ctx, final Element e) {
            this.ctx = ctx;
            this.e = e;
        }
        
        public Element getElement() {
            return this.e;
        }
        
        public float getPixelUnitToMillimeter() {
            return this.ctx.getUserAgent().getPixelUnitToMillimeter();
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public float getFontSize() {
            return CSSUtilities.getComputedStyle(this.e, 22).getFloatValue();
        }
        
        public float getXHeight() {
            return 0.5f;
        }
        
        public float getViewportWidth() {
            return this.ctx.getViewport(this.e).getWidth();
        }
        
        public float getViewportHeight() {
            return this.ctx.getViewport(this.e).getHeight();
        }
    }
}
