// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.FloatValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.Rect;
import org.w3c.dom.css.Counter;
import org.w3c.dom.svg.SVGNumber;
import org.apache.batik.css.engine.value.svg.ICCColor;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.DOMException;
import java.util.ArrayList;
import org.w3c.dom.svg.SVGNumberList;
import org.w3c.dom.svg.SVGICCColor;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.svg.SVGColor;

public class CSSOMSVGColor implements SVGColor, RGBColor, SVGICCColor, SVGNumberList
{
    protected ValueProvider valueProvider;
    protected ModificationHandler handler;
    protected RedComponent redComponent;
    protected GreenComponent greenComponent;
    protected BlueComponent blueComponent;
    protected ArrayList iccColors;
    
    public CSSOMSVGColor(final ValueProvider valueProvider) {
        this.valueProvider = valueProvider;
    }
    
    public void setModificationHandler(final ModificationHandler handler) {
        this.handler = handler;
    }
    
    public String getCssText() {
        return this.valueProvider.getValue().getCssText();
    }
    
    public void setCssText(final String s) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.iccColors = null;
        this.handler.textChanged(s);
    }
    
    public short getCssValueType() {
        return 3;
    }
    
    public short getColorType() {
        final Value value = this.valueProvider.getValue();
        final short cssValueType = value.getCssValueType();
        switch (cssValueType) {
            case 1: {
                final short primitiveType = value.getPrimitiveType();
                switch (primitiveType) {
                    case 21: {
                        if (value.getStringValue().equalsIgnoreCase("currentcolor")) {
                            return 3;
                        }
                        return 1;
                    }
                    case 25: {
                        return 1;
                    }
                    default: {
                        throw new IllegalStateException("Found unexpected PrimitiveType:" + primitiveType);
                    }
                }
                break;
            }
            case 2: {
                return 2;
            }
            default: {
                throw new IllegalStateException("Found unexpected CssValueType:" + cssValueType);
            }
        }
    }
    
    public RGBColor getRGBColor() {
        return this;
    }
    
    public RGBColor getRgbColor() {
        return this;
    }
    
    public void setRGBColor(final String s) {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.handler.rgbColorChanged(s);
    }
    
    public SVGICCColor getICCColor() {
        return (SVGICCColor)this;
    }
    
    public SVGICCColor getIccColor() {
        return (SVGICCColor)this;
    }
    
    public void setRGBColorICCColor(final String s, final String s2) {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.iccColors = null;
        this.handler.rgbColorICCColorChanged(s, s2);
    }
    
    public void setColor(final short n, final String s, final String s2) {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.iccColors = null;
        this.handler.colorChanged(n, s, s2);
    }
    
    public CSSPrimitiveValue getRed() {
        this.valueProvider.getValue().getRed();
        if (this.redComponent == null) {
            this.redComponent = new RedComponent();
        }
        return this.redComponent;
    }
    
    public CSSPrimitiveValue getGreen() {
        this.valueProvider.getValue().getGreen();
        if (this.greenComponent == null) {
            this.greenComponent = new GreenComponent();
        }
        return this.greenComponent;
    }
    
    public CSSPrimitiveValue getBlue() {
        this.valueProvider.getValue().getBlue();
        if (this.blueComponent == null) {
            this.blueComponent = new BlueComponent();
        }
        return this.blueComponent;
    }
    
    public String getColorProfile() {
        if (this.getColorType() != 2) {
            throw new DOMException((short)12, "");
        }
        return ((ICCColor)this.valueProvider.getValue().item(1)).getColorProfile();
    }
    
    public void setColorProfile(final String s) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.handler.colorProfileChanged(s);
    }
    
    public SVGNumberList getColors() {
        return (SVGNumberList)this;
    }
    
    public int getNumberOfItems() {
        if (this.getColorType() != 2) {
            throw new DOMException((short)12, "");
        }
        return ((ICCColor)this.valueProvider.getValue().item(1)).getNumberOfColors();
    }
    
    public void clear() throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.iccColors = null;
        this.handler.colorsCleared();
    }
    
    public SVGNumber initialize(final SVGNumber svgNumber) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        final float value = svgNumber.getValue();
        this.iccColors = new ArrayList();
        final ColorNumber e = new ColorNumber(value);
        this.iccColors.add(e);
        this.handler.colorsInitialized(value);
        return (SVGNumber)e;
    }
    
    public SVGNumber getItem(final int index) throws DOMException {
        if (this.getColorType() != 2) {
            throw new DOMException((short)1, "");
        }
        final int numberOfItems = this.getNumberOfItems();
        if (index < 0 || index >= numberOfItems) {
            throw new DOMException((short)1, "");
        }
        if (this.iccColors == null) {
            this.iccColors = new ArrayList(numberOfItems);
            for (int i = this.iccColors.size(); i < numberOfItems; ++i) {
                this.iccColors.add(null);
            }
        }
        final ColorNumber element = new ColorNumber(((ICCColor)this.valueProvider.getValue().item(1)).getColor(index));
        this.iccColors.set(index, element);
        return (SVGNumber)element;
    }
    
    public SVGNumber insertItemBefore(final SVGNumber svgNumber, final int index) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        final int numberOfItems = this.getNumberOfItems();
        if (index < 0 || index > numberOfItems) {
            throw new DOMException((short)1, "");
        }
        if (this.iccColors == null) {
            this.iccColors = new ArrayList(numberOfItems);
            for (int i = this.iccColors.size(); i < numberOfItems; ++i) {
                this.iccColors.add(null);
            }
        }
        final float value = svgNumber.getValue();
        final ColorNumber element = new ColorNumber(value);
        this.iccColors.add(index, element);
        this.handler.colorInsertedBefore(value, index);
        return (SVGNumber)element;
    }
    
    public SVGNumber replaceItem(final SVGNumber svgNumber, final int index) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        final int numberOfItems = this.getNumberOfItems();
        if (index < 0 || index >= numberOfItems) {
            throw new DOMException((short)1, "");
        }
        if (this.iccColors == null) {
            this.iccColors = new ArrayList(numberOfItems);
            for (int i = this.iccColors.size(); i < numberOfItems; ++i) {
                this.iccColors.add(null);
            }
        }
        final float value = svgNumber.getValue();
        final ColorNumber element = new ColorNumber(value);
        this.iccColors.set(index, element);
        this.handler.colorReplaced(value, index);
        return (SVGNumber)element;
    }
    
    public SVGNumber removeItem(final int index) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        final int numberOfItems = this.getNumberOfItems();
        if (index < 0 || index >= numberOfItems) {
            throw new DOMException((short)1, "");
        }
        Object o = null;
        if (this.iccColors != null) {
            o = this.iccColors.get(index);
        }
        if (o == null) {
            o = new ColorNumber(((ICCColor)this.valueProvider.getValue().item(1)).getColor(index));
        }
        this.handler.colorRemoved(index);
        return (SVGNumber)o;
    }
    
    public SVGNumber appendItem(final SVGNumber svgNumber) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        if (this.iccColors == null) {
            final int numberOfItems = this.getNumberOfItems();
            this.iccColors = new ArrayList(numberOfItems);
            for (int i = 0; i < numberOfItems; ++i) {
                this.iccColors.add(null);
            }
        }
        final float value = svgNumber.getValue();
        final ColorNumber e = new ColorNumber(value);
        this.iccColors.add(e);
        this.handler.colorAppend(value);
        return (SVGNumber)e;
    }
    
    protected class BlueComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMSVGColor.this.valueProvider.getValue().getBlue();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.blueTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.blueFloatValueChanged(n, n2);
        }
    }
    
    protected abstract class FloatComponent extends AbstractComponent
    {
        public void setStringValue(final short n, final String s) throws DOMException {
            throw new DOMException((short)15, "");
        }
    }
    
    protected abstract class AbstractComponent implements CSSPrimitiveValue
    {
        protected abstract Value getValue();
        
        public String getCssText() {
            return this.getValue().getCssText();
        }
        
        public short getCssValueType() {
            return this.getValue().getCssValueType();
        }
        
        public short getPrimitiveType() {
            return this.getValue().getPrimitiveType();
        }
        
        public float getFloatValue(final short n) throws DOMException {
            return CSSOMValue.convertFloatValue(n, this.getValue());
        }
        
        public String getStringValue() throws DOMException {
            return CSSOMSVGColor.this.valueProvider.getValue().getStringValue();
        }
        
        public Counter getCounterValue() throws DOMException {
            throw new DOMException((short)15, "");
        }
        
        public Rect getRectValue() throws DOMException {
            throw new DOMException((short)15, "");
        }
        
        public RGBColor getRGBColorValue() throws DOMException {
            throw new DOMException((short)15, "");
        }
        
        public int getLength() {
            throw new DOMException((short)15, "");
        }
        
        public CSSValue item(final int n) {
            throw new DOMException((short)15, "");
        }
    }
    
    public interface ValueProvider
    {
        Value getValue();
    }
    
    public interface ModificationHandler
    {
        void textChanged(final String p0) throws DOMException;
        
        void redTextChanged(final String p0) throws DOMException;
        
        void redFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void greenTextChanged(final String p0) throws DOMException;
        
        void greenFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void blueTextChanged(final String p0) throws DOMException;
        
        void blueFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void rgbColorChanged(final String p0) throws DOMException;
        
        void rgbColorICCColorChanged(final String p0, final String p1) throws DOMException;
        
        void colorChanged(final short p0, final String p1, final String p2) throws DOMException;
        
        void colorProfileChanged(final String p0) throws DOMException;
        
        void colorsCleared() throws DOMException;
        
        void colorsInitialized(final float p0) throws DOMException;
        
        void colorInsertedBefore(final float p0, final int p1) throws DOMException;
        
        void colorReplaced(final float p0, final int p1) throws DOMException;
        
        void colorRemoved(final int p0) throws DOMException;
        
        void colorAppend(final float p0) throws DOMException;
    }
    
    protected class GreenComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMSVGColor.this.valueProvider.getValue().getGreen();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.greenTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.greenFloatValueChanged(n, n2);
        }
    }
    
    protected class RedComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMSVGColor.this.valueProvider.getValue().getRed();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.redTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMSVGColor.this.handler.redFloatValueChanged(n, n2);
        }
    }
    
    public abstract class AbstractModificationHandler implements ModificationHandler
    {
        protected abstract Value getValue();
        
        public void redTextChanged(final String s) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(s);
                    sb.append(',');
                    sb.append(value.getGreen().getCssText());
                    sb.append(',');
                    sb.append(value.getBlue().getCssText());
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(s);
                    sb.append(',');
                    sb.append(value.item(0).getGreen().getCssText());
                    sb.append(',');
                    sb.append(value.item(0).getBlue().getCssText());
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void redFloatValueChanged(final short n, final float n2) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(',');
                    sb.append(value.getGreen().getCssText());
                    sb.append(',');
                    sb.append(value.getBlue().getCssText());
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(',');
                    sb.append(value.item(0).getGreen().getCssText());
                    sb.append(',');
                    sb.append(value.item(0).getBlue().getCssText());
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void greenTextChanged(final String s) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(value.getRed().getCssText());
                    sb.append(',');
                    sb.append(s);
                    sb.append(',');
                    sb.append(value.getBlue().getCssText());
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(value.item(0).getRed().getCssText());
                    sb.append(',');
                    sb.append(s);
                    sb.append(',');
                    sb.append(value.item(0).getBlue().getCssText());
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void greenFloatValueChanged(final short n, final float n2) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(value.getRed().getCssText());
                    sb.append(',');
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(',');
                    sb.append(value.getBlue().getCssText());
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(value.item(0).getRed().getCssText());
                    sb.append(',');
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(',');
                    sb.append(value.item(0).getBlue().getCssText());
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void blueTextChanged(final String s) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(value.getRed().getCssText());
                    sb.append(',');
                    sb.append(value.getGreen().getCssText());
                    sb.append(',');
                    sb.append(s);
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(value.item(0).getRed().getCssText());
                    sb.append(',');
                    sb.append(value.item(0).getGreen().getCssText());
                    sb.append(',');
                    sb.append(s);
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void blueFloatValueChanged(final short n, final float n2) throws DOMException {
            final StringBuffer sb = new StringBuffer(40);
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    sb.append("rgb(");
                    sb.append(value.getRed().getCssText());
                    sb.append(',');
                    sb.append(value.getGreen().getCssText());
                    sb.append(',');
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(')');
                    break;
                }
                case 2: {
                    sb.append("rgb(");
                    sb.append(value.item(0).getRed().getCssText());
                    sb.append(',');
                    sb.append(value.item(0).getGreen().getCssText());
                    sb.append(',');
                    sb.append(FloatValue.getCssText(n, n2));
                    sb.append(')');
                    sb.append(value.item(1).getCssText());
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(sb.toString());
        }
        
        public void rgbColorChanged(String string) throws DOMException {
            switch (CSSOMSVGColor.this.getColorType()) {
                case 1: {
                    break;
                }
                case 2: {
                    string += this.getValue().item(1).getCssText();
                    break;
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
            this.textChanged(string);
        }
        
        public void rgbColorICCColorChanged(final String str, final String str2) throws DOMException {
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    this.textChanged(str + ' ' + str2);
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorChanged(final short n, final String str, final String str2) throws DOMException {
            switch (n) {
                case 3: {
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
        
        public void colorProfileChanged(final String str) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(str);
                    final ICCColor iccColor = (ICCColor)value.item(1);
                    for (int i = 0; i < iccColor.getLength(); ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorsCleared() throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(((ICCColor)value.item(1)).getColorProfile());
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorsInitialized(final float f) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    sb.append(((ICCColor)value.item(1)).getColorProfile());
                    sb.append(',');
                    sb.append(f);
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorInsertedBefore(final float f, final int n) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)value.item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < n; ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(f);
                    for (int j = n; j < iccColor.getLength(); ++j) {
                        sb.append(',');
                        sb.append(iccColor.getColor(j));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorReplaced(final float f, final int n) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)value.item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < n; ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(f);
                    for (int j = n + 1; j < iccColor.getLength(); ++j) {
                        sb.append(',');
                        sb.append(iccColor.getColor(j));
                    }
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorRemoved(final int n) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)value.item(1);
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
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
        
        public void colorAppend(final float f) throws DOMException {
            final Value value = this.getValue();
            switch (CSSOMSVGColor.this.getColorType()) {
                case 2: {
                    final StringBuffer sb = new StringBuffer(value.item(0).getCssText());
                    sb.append(" icc-color(");
                    final ICCColor iccColor = (ICCColor)value.item(1);
                    sb.append(iccColor.getColorProfile());
                    for (int i = 0; i < iccColor.getLength(); ++i) {
                        sb.append(',');
                        sb.append(iccColor.getColor(i));
                    }
                    sb.append(',');
                    sb.append(f);
                    sb.append(')');
                    this.textChanged(sb.toString());
                }
                default: {
                    throw new DOMException((short)7, "");
                }
            }
        }
    }
    
    protected class ColorNumber implements SVGNumber
    {
        protected float value;
        
        public ColorNumber(final float value) {
            this.value = value;
        }
        
        public float getValue() {
            if (CSSOMSVGColor.this.iccColors == null) {
                return this.value;
            }
            final int index = CSSOMSVGColor.this.iccColors.indexOf(this);
            if (index == -1) {
                return this.value;
            }
            return ((ICCColor)CSSOMSVGColor.this.valueProvider.getValue().item(1)).getColor(index);
        }
        
        public void setValue(final float value) {
            this.value = value;
            if (CSSOMSVGColor.this.iccColors == null) {
                return;
            }
            final int index = CSSOMSVGColor.this.iccColors.indexOf(this);
            if (index == -1) {
                return;
            }
            if (CSSOMSVGColor.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            CSSOMSVGColor.this.handler.colorReplaced(value, index);
        }
    }
}
