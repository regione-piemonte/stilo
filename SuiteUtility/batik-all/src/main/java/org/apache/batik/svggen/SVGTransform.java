// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.Stack;
import org.apache.batik.ext.awt.g2d.TransformStackElement;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGTransform extends AbstractSVGConverter
{
    private static double radiansToDegrees;
    
    public SVGTransform(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return new SVGTransformDescriptor(this.toSVGTransform(graphicContext));
    }
    
    public final String toSVGTransform(final GraphicContext graphicContext) {
        return this.toSVGTransform(graphicContext.getTransformStack());
    }
    
    public final String toSVGTransform(final TransformStackElement[] array) {
        int length;
        Stack stack;
        int i;
        TransformStackElement transformStackElement;
        int n;
        int n2;
        for (length = array.length, stack = new Stack() {
            public Object push(final Object item) {
                Object pop;
                if (((TransformStackElement)item).isIdentity()) {
                    pop = this.pop();
                }
                else {
                    super.push(item);
                    pop = null;
                }
                return pop;
            }
            
            public Object pop() {
                Object pop = null;
                if (!super.empty()) {
                    pop = super.pop();
                }
                return pop;
            }
        }, i = 0, transformStackElement = null; i < length; i = n2, transformStackElement = stack.push(transformStackElement)) {
            n = i;
            if (transformStackElement == null) {
                transformStackElement = (TransformStackElement)array[i].clone();
                ++n;
            }
            for (n2 = n; n2 < length && transformStackElement.concatenate(array[n2]); ++n2) {}
        }
        if (transformStackElement != null) {
            stack.push(transformStackElement);
        }
        final int size = stack.size();
        final StringBuffer sb = new StringBuffer(size * 8);
        for (int j = 0; j < size; ++j) {
            sb.append(this.convertTransform((TransformStackElement)stack.get(j)));
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    
    final String convertTransform(final TransformStackElement transformStackElement) {
        final StringBuffer sb = new StringBuffer();
        final double[] transformParameters = transformStackElement.getTransformParameters();
        switch (transformStackElement.getType().toInt()) {
            case 0: {
                if (!transformStackElement.isIdentity()) {
                    sb.append("translate");
                    sb.append("(");
                    sb.append(this.doubleString(transformParameters[0]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[1]));
                    sb.append(")");
                    break;
                }
                break;
            }
            case 1: {
                if (!transformStackElement.isIdentity()) {
                    sb.append("rotate");
                    sb.append("(");
                    sb.append(this.doubleString(SVGTransform.radiansToDegrees * transformParameters[0]));
                    sb.append(")");
                    break;
                }
                break;
            }
            case 2: {
                if (!transformStackElement.isIdentity()) {
                    sb.append("scale");
                    sb.append("(");
                    sb.append(this.doubleString(transformParameters[0]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[1]));
                    sb.append(")");
                    break;
                }
                break;
            }
            case 3: {
                if (!transformStackElement.isIdentity()) {
                    sb.append("matrix");
                    sb.append("(");
                    sb.append(1);
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[1]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[0]));
                    sb.append(",");
                    sb.append(1);
                    sb.append(",");
                    sb.append(0);
                    sb.append(",");
                    sb.append(0);
                    sb.append(")");
                    break;
                }
                break;
            }
            case 4: {
                if (!transformStackElement.isIdentity()) {
                    sb.append("matrix");
                    sb.append("(");
                    sb.append(this.doubleString(transformParameters[0]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[1]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[2]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[3]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[4]));
                    sb.append(",");
                    sb.append(this.doubleString(transformParameters[5]));
                    sb.append(")");
                    break;
                }
                break;
            }
            default: {
                throw new Error();
            }
        }
        return sb.toString();
    }
    
    static {
        SVGTransform.radiansToDegrees = 57.29577951308232;
    }
}
