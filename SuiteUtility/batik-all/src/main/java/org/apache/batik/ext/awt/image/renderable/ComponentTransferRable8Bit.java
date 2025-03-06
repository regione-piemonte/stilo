// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.GammaTransfer;
import org.apache.batik.ext.awt.image.LinearTransfer;
import org.apache.batik.ext.awt.image.DiscreteTransfer;
import org.apache.batik.ext.awt.image.TableTransfer;
import org.apache.batik.ext.awt.image.IdentityTransfer;
import org.apache.batik.ext.awt.image.rendered.ComponentTransferRed;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import org.apache.batik.ext.awt.image.TransferFunction;
import org.apache.batik.ext.awt.image.ComponentTransferFunction;

public class ComponentTransferRable8Bit extends AbstractColorInterpolationRable implements ComponentTransferRable
{
    public static final int ALPHA = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    private ComponentTransferFunction[] functions;
    private TransferFunction[] txfFunc;
    
    public ComponentTransferRable8Bit(final Filter filter, final ComponentTransferFunction alphaFunction, final ComponentTransferFunction redFunction, final ComponentTransferFunction greenFunction, final ComponentTransferFunction blueFunction) {
        super(filter, null);
        this.functions = new ComponentTransferFunction[4];
        this.txfFunc = new TransferFunction[4];
        this.setAlphaFunction(alphaFunction);
        this.setRedFunction(redFunction);
        this.setGreenFunction(greenFunction);
        this.setBlueFunction(blueFunction);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public ComponentTransferFunction getAlphaFunction() {
        return this.functions[0];
    }
    
    public void setAlphaFunction(final ComponentTransferFunction componentTransferFunction) {
        this.touch();
        this.functions[0] = componentTransferFunction;
        this.txfFunc[0] = null;
    }
    
    public ComponentTransferFunction getRedFunction() {
        return this.functions[1];
    }
    
    public void setRedFunction(final ComponentTransferFunction componentTransferFunction) {
        this.touch();
        this.functions[1] = componentTransferFunction;
        this.txfFunc[1] = null;
    }
    
    public ComponentTransferFunction getGreenFunction() {
        return this.functions[2];
    }
    
    public void setGreenFunction(final ComponentTransferFunction componentTransferFunction) {
        this.touch();
        this.functions[2] = componentTransferFunction;
        this.txfFunc[2] = null;
    }
    
    public ComponentTransferFunction getBlueFunction() {
        return this.functions[3];
    }
    
    public void setBlueFunction(final ComponentTransferFunction componentTransferFunction) {
        this.touch();
        this.functions[3] = componentTransferFunction;
        this.txfFunc[3] = null;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final RenderedImage rendering = this.getSource().createRendering(renderContext);
        if (rendering == null) {
            return null;
        }
        return new ComponentTransferRed(this.convertSourceCS(rendering), this.getTransferFunctions(), renderContext.getRenderingHints());
    }
    
    private TransferFunction[] getTransferFunctions() {
        final TransferFunction[] array = new TransferFunction[4];
        System.arraycopy(this.txfFunc, 0, array, 0, 4);
        final ComponentTransferFunction[] array2 = new ComponentTransferFunction[4];
        System.arraycopy(this.functions, 0, array2, 0, 4);
        for (int i = 0; i < 4; ++i) {
            if (array[i] == null) {
                array[i] = getTransferFunction(array2[i]);
                synchronized (this.functions) {
                    if (this.functions[i] == array2[i]) {
                        this.txfFunc[i] = array[i];
                    }
                }
            }
        }
        return array;
    }
    
    private static TransferFunction getTransferFunction(final ComponentTransferFunction componentTransferFunction) {
        TransferFunction transferFunction = null;
        if (componentTransferFunction == null) {
            transferFunction = new IdentityTransfer();
        }
        else {
            switch (componentTransferFunction.getType()) {
                case 0: {
                    transferFunction = new IdentityTransfer();
                    break;
                }
                case 1: {
                    transferFunction = new TableTransfer(tableFloatToInt(componentTransferFunction.getTableValues()));
                    break;
                }
                case 2: {
                    transferFunction = new DiscreteTransfer(tableFloatToInt(componentTransferFunction.getTableValues()));
                    break;
                }
                case 3: {
                    transferFunction = new LinearTransfer(componentTransferFunction.getSlope(), componentTransferFunction.getIntercept());
                    break;
                }
                case 4: {
                    transferFunction = new GammaTransfer(componentTransferFunction.getAmplitude(), componentTransferFunction.getExponent(), componentTransferFunction.getOffset());
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }
        return transferFunction;
    }
    
    private static int[] tableFloatToInt(final float[] array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = (int)(array[i] * 255.0f);
        }
        return array2;
    }
}
