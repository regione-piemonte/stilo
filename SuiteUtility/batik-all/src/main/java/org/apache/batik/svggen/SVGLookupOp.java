// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.image.LookupTable;
import java.util.Arrays;
import java.awt.image.ByteLookupTable;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.awt.image.LookupOp;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public class SVGLookupOp extends AbstractSVGFilterConverter
{
    private static final double GAMMA = 0.4166666666666667;
    private static final int[] linearToSRGBLut;
    private static final int[] sRGBToLinear;
    
    public SVGLookupOp(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGFilterDescriptor toSVG(final BufferedImageOp bufferedImageOp, final Rectangle rectangle) {
        if (bufferedImageOp instanceof LookupOp) {
            return this.toSVG((LookupOp)bufferedImageOp);
        }
        return null;
    }
    
    public SVGFilterDescriptor toSVG(final LookupOp lookupOp) {
        SVGFilterDescriptor svgFilterDescriptor = this.descMap.get(lookupOp);
        final Document domFactory = this.generatorContext.domFactory;
        if (svgFilterDescriptor == null) {
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "filter");
            final Element elementNS2 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feComponentTransfer");
            final String[] convertLookupTables = this.convertLookupTables(lookupOp);
            final Element elementNS3 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncR");
            final Element elementNS4 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncG");
            final Element elementNS5 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncB");
            Element elementNS6 = null;
            final String s = "table";
            if (convertLookupTables.length == 1) {
                elementNS3.setAttributeNS(null, "type", s);
                elementNS4.setAttributeNS(null, "type", s);
                elementNS5.setAttributeNS(null, "type", s);
                elementNS3.setAttributeNS(null, "tableValues", convertLookupTables[0]);
                elementNS4.setAttributeNS(null, "tableValues", convertLookupTables[0]);
                elementNS5.setAttributeNS(null, "tableValues", convertLookupTables[0]);
            }
            else if (convertLookupTables.length >= 3) {
                elementNS3.setAttributeNS(null, "type", s);
                elementNS4.setAttributeNS(null, "type", s);
                elementNS5.setAttributeNS(null, "type", s);
                elementNS3.setAttributeNS(null, "tableValues", convertLookupTables[0]);
                elementNS4.setAttributeNS(null, "tableValues", convertLookupTables[1]);
                elementNS5.setAttributeNS(null, "tableValues", convertLookupTables[2]);
                if (convertLookupTables.length == 4) {
                    elementNS6 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncA");
                    elementNS6.setAttributeNS(null, "type", s);
                    elementNS6.setAttributeNS(null, "tableValues", convertLookupTables[3]);
                }
            }
            elementNS2.appendChild(elementNS3);
            elementNS2.appendChild(elementNS4);
            elementNS2.appendChild(elementNS5);
            if (elementNS6 != null) {
                elementNS2.appendChild(elementNS6);
            }
            elementNS.appendChild(elementNS2);
            elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("componentTransfer"));
            svgFilterDescriptor = new SVGFilterDescriptor("url(#" + elementNS.getAttributeNS(null, "id") + ")", elementNS);
            this.defSet.add(elementNS);
            this.descMap.put(lookupOp, svgFilterDescriptor);
        }
        return svgFilterDescriptor;
    }
    
    private String[] convertLookupTables(final LookupOp lookupOp) {
        final LookupTable table = lookupOp.getTable();
        final int numComponents = table.getNumComponents();
        if (numComponents != 1 && numComponents != 3 && numComponents != 4) {
            throw new SVGGraphics2DRuntimeException("BufferedImage LookupOp should have 1, 3 or 4 lookup arrays");
        }
        final StringBuffer[] array = new StringBuffer[numComponents];
        for (int i = 0; i < numComponents; ++i) {
            array[i] = new StringBuffer();
        }
        if (!(table instanceof ByteLookupTable)) {
            final int[] a = new int[numComponents];
            final int[] array2 = new int[numComponents];
            final int offset = table.getOffset();
            for (int j = 0; j < offset; ++j) {
                for (int k = 0; k < numComponents; ++k) {
                    array[k].append(this.doubleString(j / 255.0)).append(" ");
                }
            }
            for (int l = offset; l <= 255; ++l) {
                Arrays.fill(a, l);
                table.lookupPixel(a, array2);
                for (int n = 0; n < numComponents; ++n) {
                    array[n].append(this.doubleString(array2[n] / 255.0)).append(" ");
                }
            }
        }
        else {
            final byte[] array3 = new byte[numComponents];
            final byte[] dst = new byte[numComponents];
            for (int offset2 = table.getOffset(), n2 = 0; n2 < offset2; ++n2) {
                for (int n3 = 0; n3 < numComponents; ++n3) {
                    array[n3].append(this.doubleString(n2 / 255.0)).append(" ");
                }
            }
            for (int n4 = 0; n4 <= 255; ++n4) {
                Arrays.fill(array3, (byte)(0xFF & n4));
                ((ByteLookupTable)table).lookupPixel(array3, dst);
                for (int n5 = 0; n5 < numComponents; ++n5) {
                    array[n5].append(this.doubleString((0xFF & dst[n5]) / 255.0)).append(" ");
                }
            }
        }
        final String[] array4 = new String[numComponents];
        for (int n6 = 0; n6 < numComponents; ++n6) {
            array4[n6] = array[n6].toString().trim();
        }
        return array4;
    }
    
    static {
        linearToSRGBLut = new int[256];
        sRGBToLinear = new int[256];
        for (int i = 0; i < 256; ++i) {
            final float n = i / 255.0f;
            float n2;
            if (n <= 0.0031308) {
                n2 = n * 12.92f;
            }
            else {
                n2 = 1.055f * (float)Math.pow(n, 0.4166666666666667) - 0.055f;
            }
            SVGLookupOp.linearToSRGBLut[i] = Math.round(n2 * 255.0f);
            final float n3 = i / 255.0f;
            float n4;
            if (n3 <= 0.04045) {
                n4 = n3 / 12.92f;
            }
            else {
                n4 = (float)Math.pow((n3 + 0.055f) / 1.055f, 2.4);
            }
            SVGLookupOp.sRGBToLinear[i] = Math.round(n4 * 255.0f);
        }
    }
}
