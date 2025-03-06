// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.svg.ICCColor;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPaint;

public class CSSOMSVGPaint extends CSSOMSVGColor implements SVGPaint
{
    public CSSOMSVGPaint(final ValueProvider valueProvider) {
        super(valueProvider);
    }
    
    public void setModificationHandler(final ModificationHandler modificationHandler) {
        if (!(modificationHandler instanceof PaintModificationHandler)) {
            throw new IllegalArgumentException();
        }
        super.setModificationHandler(modificationHandler);
    }
    
    public short getColorType() {
        throw new DOMException((short)15, "");
    }
    
    public short getPaintType() {
        final Value value = this.valueProvider.getValue();
        Label_0270: {
            switch (value.getCssValueType()) {
                case 1: {
                    switch (value.getPrimitiveType()) {
                        case 21: {
                            final String stringValue = value.getStringValue();
                            if (stringValue.equalsIgnoreCase("none")) {
                                return 101;
                            }
                            if (stringValue.equalsIgnoreCase("currentcolor")) {
                                return 102;
                            }
                            return 1;
                        }
                        case 25: {
                            return 1;
                        }
                        case 20: {
                            return 107;
                        }
                        default: {
                            break Label_0270;
                        }
                    }
                    break;
                }
                case 2: {
                    final Value item = value.item(0);
                    final Value item2 = value.item(1);
                    switch (item.getPrimitiveType()) {
                        case 21: {
                            return 2;
                        }
                        case 20: {
                            if (item2.getCssValueType() == 2) {
                                return 106;
                            }
                            switch (item2.getPrimitiveType()) {
                                case 21: {
                                    final String stringValue2 = item2.getStringValue();
                                    if (stringValue2.equalsIgnoreCase("none")) {
                                        return 103;
                                    }
                                    if (stringValue2.equalsIgnoreCase("currentcolor")) {
                                        return 104;
                                    }
                                    return 105;
                                }
                                case 25: {
                                    return 105;
                                }
                                default: {
                                    return 2;
                                }
                            }
                            break;
                        }
                        case 25: {
                            return 2;
                        }
                        default: {
                            break Label_0270;
                        }
                    }
                    break;
                }
            }
        }
        return 0;
    }
    
    public String getUri() {
        switch (this.getPaintType()) {
            case 107: {
                return this.valueProvider.getValue().getStringValue();
            }
            case 103:
            case 104:
            case 105:
            case 106: {
                return this.valueProvider.getValue().item(0).getStringValue();
            }
            default: {
                throw new InternalError();
            }
        }
    }
    
    public void setUri(final String s) {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        ((PaintModificationHandler)this.handler).uriChanged(s);
    }
    
    public void setPaint(final short n, final String s, final String s2, final String s3) {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        ((PaintModificationHandler)this.handler).paintChanged(n, s, s2, s3);
    }
    
    public abstract class AbstractModificationHandler implements PaintModificationHandler
    {
        protected abstract Value getValue();
        
        public void redTextChanged(String s) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + s + ", " + this.getValue().getGreen().getCssText() + ", " + this.getValue().getBlue().getCssText() + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + s + ", " + this.getValue().item(0).getGreen().getCssText() + ", " + this.getValue().item(0).getBlue().getCssText() + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + s + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + this.getValue().item(1).getBlue().getCssText() + ')';
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + s + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + this.getValue().item(1).getBlue().getCssText() + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void redFloatValueChanged(final short n, final float n2) throws DOMException {
            String s = null;
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + FloatValue.getCssText(n, n2) + ", " + this.getValue().getGreen().getCssText() + ", " + this.getValue().getBlue().getCssText() + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(0).getGreen().getCssText() + ", " + this.getValue().item(0).getBlue().getCssText() + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + this.getValue().item(1).getBlue().getCssText() + ')';
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + this.getValue().item(1).getBlue().getCssText() + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void greenTextChanged(String s) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + this.getValue().getRed().getCssText() + ", " + s + ", " + this.getValue().getBlue().getCssText() + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + this.getValue().item(0).getRed().getCssText() + ", " + s + ", " + this.getValue().item(0).getBlue().getCssText() + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + s + ", " + this.getValue().item(1).getBlue().getCssText() + ')';
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + s + ", " + this.getValue().item(1).getBlue().getCssText() + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void greenFloatValueChanged(final short n, final float n2) throws DOMException {
            String s = null;
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + this.getValue().getRed().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + this.getValue().getBlue().getCssText() + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + this.getValue().item(0).getRed().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(0).getBlue().getCssText() + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(1).getBlue().getCssText() + ')';
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + this.getValue().item(1).getBlue().getCssText() + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void blueTextChanged(String s) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + this.getValue().getRed().getCssText() + ", " + this.getValue().getGreen().getCssText() + ", " + s + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + this.getValue().item(0).getRed().getCssText() + ", " + this.getValue().item(0).getGreen().getCssText() + ", " + s + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + s + ")";
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + s + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void blueFloatValueChanged(final short n, final float n2) throws DOMException {
            String s = null;
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    s = "rgb(" + this.getValue().getRed().getCssText() + ", " + this.getValue().getGreen().getCssText() + ", " + FloatValue.getCssText(n, n2) + ')';
                    break;
                }
                case 2: {
                    s = "rgb(" + this.getValue().item(0).getRed().getCssText() + ", " + this.getValue().item(0).getGreen().getCssText() + ", " + FloatValue.getCssText(n, n2) + ") " + this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + FloatValue.getCssText(n, n2) + ')';
                    break;
                }
                case 106: {
                    s = this.getValue().item(0) + " rgb(" + this.getValue().item(1).getRed().getCssText() + ", " + this.getValue().item(1).getGreen().getCssText() + ", " + FloatValue.getCssText(n, n2) + ") " + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(s);
        }
        
        public void rgbColorChanged(String str) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 1: {
                    break;
                }
                case 2: {
                    str += this.getValue().item(1).getCssText();
                    break;
                }
                case 105: {
                    str = this.getValue().item(0).getCssText() + ' ' + str;
                    break;
                }
                case 106: {
                    str = this.getValue().item(0).getCssText() + ' ' + str + ' ' + this.getValue().item(2).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(str);
        }
        
        public void rgbColorICCColorChanged(final String s, final String s2) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    this.textChanged(s + ' ' + s2);
                    break;
                }
                case 106: {
                    this.textChanged(this.getValue().item(0).getCssText() + ' ' + s + ' ' + s2);
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorChanged(final short n, final String str, final String str2) throws DOMException {
            switch (n) {
                case 102: {
                    this.textChanged("currentcolor");
                    break;
                }
                case 1: {
                    this.textChanged(str);
                    break;
                }
                case 2: {
                    this.textChanged(str + ' ' + str2);
                    break;
                }
                default: {
                    throw new DOMException((short)9, "");
                }
            }
        }
        
        public void colorProfileChanged(final String s) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(s);
                    final ICCColor iccColor = (ICCColor)this.getValue().item(1);
                    for (int i = 0; i < iccColor.getLength(); ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    sb2.append(s);
                    final ICCColor iccColor2 = (ICCColor)this.getValue().item(1);
                    for (int j = 0; j < iccColor2.getLength(); ++j) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(j));
                    }
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorsCleared() throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(((ICCColor)this.getValue().item(1)).getColorProfile());
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    sb2.append(((ICCColor)this.getValue().item(1)).getColorProfile());
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorsInitialized(final float n) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(((ICCColor)this.getValue().item(1)).getColorProfile());
                    sb.append(',');
                    sb.append(n);
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    sb2.append(((ICCColor)this.getValue().item(1)).getColorProfile());
                    sb2.append(',');
                    sb2.append(n);
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorInsertedBefore(final float n, final int n2) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)this.getValue().item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < n2; ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(n);
                    for (int j = n2; j < iccColor.getLength(); ++j) {
                        sb.append(',');
                        sb.append(iccColor.getColor(j));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    final ICCColor iccColor2 = (ICCColor)this.getValue().item(1);
                    sb2.append(iccColor2.getColorProfile());
                    for (int k = 0; k < n2; ++k) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(k));
                    }
                    sb2.append(',');
                    sb2.append(n);
                    for (int l = n2; l < iccColor2.getLength(); ++l) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(l));
                    }
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorReplaced(final float n, final int n2) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)this.getValue().item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < n2; ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(n);
                    for (int j = n2 + 1; j < iccColor.getLength(); ++j) {
                        sb.append(',');
                        sb.append(iccColor.getColor(j));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    final ICCColor iccColor2 = (ICCColor)this.getValue().item(1);
                    sb2.append(iccColor2.getColorProfile());
                    for (int k = 0; k < n2; ++k) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(k));
                    }
                    sb2.append(',');
                    sb2.append(n);
                    for (int l = n2 + 1; l < iccColor2.getLength(); ++l) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(l));
                    }
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorRemoved(final int n) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)this.getValue().item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < n; ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    for (int j = n + 1; j < iccColor.getLength(); ++j) {
                        sb.append(',');
                        sb.append(iccColor.getColor(j));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    final ICCColor iccColor2 = (ICCColor)this.getValue().item(1);
                    sb2.append(iccColor2.getColorProfile());
                    for (int k = 0; k < n; ++k) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(k));
                    }
                    for (int l = n + 1; l < iccColor2.getLength(); ++l) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(l));
                    }
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorAppend(final float n) throws DOMException {
            switch (CSSOMSVGPaint.this.getPaintType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(this.getValue().item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)this.getValue().item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < iccColor.getLength(); ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(n);
                    sb.append(')');
                    this.textChanged(sb.toString());
                    break;
                }
                case 106: {
                    final StringBuffer sb2 = new StringBuffer(this.getValue().item(0).getCssText());
                    sb2.append(' ');
                    sb2.append(this.getValue().item(1).getCssText());
                    sb2.append(" icc-color(");
                    final ICCColor iccColor2 = (ICCColor)this.getValue().item(1);
                    sb2.append(iccColor2.getColorProfile());
                    for (int j = 0; j < iccColor2.getLength(); ++j) {
                        sb2.append(',');
                        sb2.append(iccColor2.getColor(j));
                    }
                    sb2.append(',');
                    sb2.append(n);
                    sb2.append(')');
                    this.textChanged(sb2.toString());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void uriChanged(final String str) {
            this.textChanged("url(" + str + ") none");
        }
        
        public void paintChanged(final short n, final String str, final String str2, final String s) {
            switch (n) {
                case 101: {
                    this.textChanged("none");
                    break;
                }
                case 102: {
                    this.textChanged("currentcolor");
                    break;
                }
                case 1: {
                    this.textChanged(str2);
                    break;
                }
                case 2: {
                    this.textChanged(str2 + ' ' + s);
                    break;
                }
                case 107: {
                    this.textChanged("url(" + str + ')');
                    break;
                }
                case 103: {
                    this.textChanged("url(" + str + ") none");
                    break;
                }
                case 104: {
                    this.textChanged("url(" + str + ") currentcolor");
                    break;
                }
                case 105: {
                    this.textChanged("url(" + str + ") " + str2);
                    break;
                }
                case 106: {
                    this.textChanged("url(" + str + ") " + str2 + ' ' + s);
                    break;
                }
            }
        }
    }
    
    public interface PaintModificationHandler extends ModificationHandler
    {
        void uriChanged(final String p0);
        
        void paintChanged(final short p0, final String p1, final String p2, final String p3);
    }
}
