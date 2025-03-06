// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.Map;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.filter.GraphicsNodeRable8Bit;
import org.apache.batik.gvt.CompositeGraphicsNode;
import java.util.HashMap;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.ext.awt.image.spi.DefaultBrokenLinkProvider;

public class SVGBrokenLinkProvider extends DefaultBrokenLinkProvider implements ErrorConstants
{
    public Filter getBrokenLinkImage(final Object o, final String s, final Object[] array) {
        final String formatMessage = DefaultBrokenLinkProvider.formatMessage(o, s, array);
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("org.apache.batik.BrokenLinkImage", formatMessage);
        return new GraphicsNodeRable8Bit((GraphicsNode)new CompositeGraphicsNode(), hashMap);
    }
}
