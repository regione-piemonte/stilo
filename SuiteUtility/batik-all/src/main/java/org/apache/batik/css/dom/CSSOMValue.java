// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.CSSPrimitiveValue;

public class CSSOMValue implements CSSPrimitiveValue, CSSValueList, Counter, Rect, RGBColor
{
    protected ValueProvider valueProvider;
    protected ModificationHandler handler;
    protected LeftComponent leftComponent;
    protected RightComponent rightComponent;
    protected BottomComponent bottomComponent;
    protected TopComponent topComponent;
    protected RedComponent redComponent;
    protected GreenComponent greenComponent;
    protected BlueComponent blueComponent;
    protected CSSValue[] items;
    
    public CSSOMValue(final ValueProvider valueProvider) {
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
        this.handler.textChanged(s);
    }
    
    public short getCssValueType() {
        return this.valueProvider.getValue().getCssValueType();
    }
    
    public short getPrimitiveType() {
        return this.valueProvider.getValue().getPrimitiveType();
    }
    
    public void setFloatValue(final short n, final float n2) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.handler.floatValueChanged(n, n2);
    }
    
    public float getFloatValue(final short n) throws DOMException {
        return convertFloatValue(n, this.valueProvider.getValue());
    }
    
    public static float convertFloatValue(final short n, final Value value) {
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 18: {
                if (value.getPrimitiveType() == n) {
                    return value.getFloatValue();
                }
                break;
            }
            case 6: {
                return toCentimeters(value);
            }
            case 7: {
                return toMillimeters(value);
            }
            case 8: {
                return toInches(value);
            }
            case 9: {
                return toPoints(value);
            }
            case 10: {
                return toPicas(value);
            }
            case 11: {
                return toDegrees(value);
            }
            case 12: {
                return toRadians(value);
            }
            case 13: {
                return toGradians(value);
            }
            case 14: {
                return toMilliseconds(value);
            }
            case 15: {
                return toSeconds(value);
            }
            case 16: {
                return toHertz(value);
            }
            case 17: {
                return tokHertz(value);
            }
        }
        throw new DOMException((short)15, "");
    }
    
    protected static float toCentimeters(final Value value) {
        switch (value.getPrimitiveType()) {
            case 6: {
                return value.getFloatValue();
            }
            case 7: {
                return value.getFloatValue() / 10.0f;
            }
            case 8: {
                return value.getFloatValue() * 2.54f;
            }
            case 9: {
                return value.getFloatValue() * 2.54f / 72.0f;
            }
            case 10: {
                return value.getFloatValue() * 2.54f / 6.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toInches(final Value value) {
        switch (value.getPrimitiveType()) {
            case 6: {
                return value.getFloatValue() / 2.54f;
            }
            case 7: {
                return value.getFloatValue() / 25.4f;
            }
            case 8: {
                return value.getFloatValue();
            }
            case 9: {
                return value.getFloatValue() / 72.0f;
            }
            case 10: {
                return value.getFloatValue() / 6.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toMillimeters(final Value value) {
        switch (value.getPrimitiveType()) {
            case 6: {
                return value.getFloatValue() * 10.0f;
            }
            case 7: {
                return value.getFloatValue();
            }
            case 8: {
                return value.getFloatValue() * 25.4f;
            }
            case 9: {
                return value.getFloatValue() * 25.4f / 72.0f;
            }
            case 10: {
                return value.getFloatValue() * 25.4f / 6.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toPoints(final Value value) {
        switch (value.getPrimitiveType()) {
            case 6: {
                return value.getFloatValue() * 72.0f / 2.54f;
            }
            case 7: {
                return value.getFloatValue() * 72.0f / 25.4f;
            }
            case 8: {
                return value.getFloatValue() * 72.0f;
            }
            case 9: {
                return value.getFloatValue();
            }
            case 10: {
                return value.getFloatValue() * 12.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toPicas(final Value value) {
        switch (value.getPrimitiveType()) {
            case 6: {
                return value.getFloatValue() * 6.0f / 2.54f;
            }
            case 7: {
                return value.getFloatValue() * 6.0f / 25.4f;
            }
            case 8: {
                return value.getFloatValue() * 6.0f;
            }
            case 9: {
                return value.getFloatValue() / 12.0f;
            }
            case 10: {
                return value.getFloatValue();
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toDegrees(final Value value) {
        switch (value.getPrimitiveType()) {
            case 11: {
                return value.getFloatValue();
            }
            case 12: {
                return (float)Math.toDegrees(value.getFloatValue());
            }
            case 13: {
                return value.getFloatValue() * 9.0f / 5.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toRadians(final Value value) {
        switch (value.getPrimitiveType()) {
            case 11: {
                return value.getFloatValue() * 5.0f / 9.0f;
            }
            case 12: {
                return value.getFloatValue();
            }
            case 13: {
                return (float)(value.getFloatValue() * 100.0f / 3.141592653589793);
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toGradians(final Value value) {
        switch (value.getPrimitiveType()) {
            case 11: {
                return (float)(value.getFloatValue() * 3.141592653589793 / 180.0);
            }
            case 12: {
                return (float)(value.getFloatValue() * 3.141592653589793 / 100.0);
            }
            case 13: {
                return value.getFloatValue();
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toMilliseconds(final Value value) {
        switch (value.getPrimitiveType()) {
            case 14: {
                return value.getFloatValue();
            }
            case 15: {
                return value.getFloatValue() * 1000.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toSeconds(final Value value) {
        switch (value.getPrimitiveType()) {
            case 14: {
                return value.getFloatValue() / 1000.0f;
            }
            case 15: {
                return value.getFloatValue();
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float toHertz(final Value value) {
        switch (value.getPrimitiveType()) {
            case 16: {
                return value.getFloatValue();
            }
            case 17: {
                return value.getFloatValue() / 1000.0f;
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    protected static float tokHertz(final Value value) {
        switch (value.getPrimitiveType()) {
            case 16: {
                return value.getFloatValue() * 1000.0f;
            }
            case 17: {
                return value.getFloatValue();
            }
            default: {
                throw new DOMException((short)15, "");
            }
        }
    }
    
    public void setStringValue(final short n, final String s) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.handler.stringValueChanged(n, s);
    }
    
    public String getStringValue() throws DOMException {
        return this.valueProvider.getValue().getStringValue();
    }
    
    public Counter getCounterValue() throws DOMException {
        return this;
    }
    
    public Rect getRectValue() throws DOMException {
        return this;
    }
    
    public RGBColor getRGBColorValue() throws DOMException {
        return this;
    }
    
    public int getLength() {
        return this.valueProvider.getValue().getLength();
    }
    
    public CSSValue item(final int n) {
        final int length = this.valueProvider.getValue().getLength();
        if (n < 0 || n >= length) {
            return null;
        }
        if (this.items == null) {
            this.items = new CSSValue[this.valueProvider.getValue().getLength()];
        }
        else if (this.items.length < length) {
            final CSSValue[] items = new CSSValue[length];
            System.arraycopy(this.items, 0, items, 0, this.items.length);
            this.items = items;
        }
        CSSValue cssValue = this.items[n];
        if (cssValue == null) {
            cssValue = (this.items[n] = new ListComponent(n));
        }
        return cssValue;
    }
    
    public String getIdentifier() {
        return this.valueProvider.getValue().getIdentifier();
    }
    
    public String getListStyle() {
        return this.valueProvider.getValue().getListStyle();
    }
    
    public String getSeparator() {
        return this.valueProvider.getValue().getSeparator();
    }
    
    public CSSPrimitiveValue getTop() {
        this.valueProvider.getValue().getTop();
        if (this.topComponent == null) {
            this.topComponent = new TopComponent();
        }
        return this.topComponent;
    }
    
    public CSSPrimitiveValue getRight() {
        this.valueProvider.getValue().getRight();
        if (this.rightComponent == null) {
            this.rightComponent = new RightComponent();
        }
        return this.rightComponent;
    }
    
    public CSSPrimitiveValue getBottom() {
        this.valueProvider.getValue().getBottom();
        if (this.bottomComponent == null) {
            this.bottomComponent = new BottomComponent();
        }
        return this.bottomComponent;
    }
    
    public CSSPrimitiveValue getLeft() {
        this.valueProvider.getValue().getLeft();
        if (this.leftComponent == null) {
            this.leftComponent = new LeftComponent();
        }
        return this.leftComponent;
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
    
    protected class ListComponent extends AbstractComponent
    {
        protected int index;
        
        public ListComponent(final int index) {
            this.index = index;
        }
        
        protected Value getValue() {
            if (this.index >= CSSOMValue.this.valueProvider.getValue().getLength()) {
                throw new DOMException((short)7, "");
            }
            return CSSOMValue.this.valueProvider.getValue().item(this.index);
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.listTextChanged(this.index, s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.listFloatValueChanged(this.index, n, n2);
        }
        
        public void setStringValue(final short n, final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.listStringValueChanged(this.index, n, s);
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
            return CSSOMValue.this.valueProvider.getValue().getStringValue();
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
        
        void floatValueChanged(final short p0, final float p1) throws DOMException;
        
        void stringValueChanged(final short p0, final String p1) throws DOMException;
        
        void leftTextChanged(final String p0) throws DOMException;
        
        void leftFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void topTextChanged(final String p0) throws DOMException;
        
        void topFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void rightTextChanged(final String p0) throws DOMException;
        
        void rightFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void bottomTextChanged(final String p0) throws DOMException;
        
        void bottomFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void redTextChanged(final String p0) throws DOMException;
        
        void redFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void greenTextChanged(final String p0) throws DOMException;
        
        void greenFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void blueTextChanged(final String p0) throws DOMException;
        
        void blueFloatValueChanged(final short p0, final float p1) throws DOMException;
        
        void listTextChanged(final int p0, final String p1) throws DOMException;
        
        void listFloatValueChanged(final int p0, final short p1, final float p2) throws DOMException;
        
        void listStringValueChanged(final int p0, final short p1, final String p2) throws DOMException;
    }
    
    protected class BlueComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getBlue();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.blueTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.blueFloatValueChanged(n, n2);
        }
    }
    
    protected abstract class FloatComponent extends AbstractComponent
    {
        public void setStringValue(final short n, final String s) throws DOMException {
            throw new DOMException((short)15, "");
        }
    }
    
    protected class GreenComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getGreen();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.greenTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.greenFloatValueChanged(n, n2);
        }
    }
    
    protected class RedComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getRed();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.redTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.redFloatValueChanged(n, n2);
        }
    }
    
    protected class BottomComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getBottom();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.bottomTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.bottomFloatValueChanged(n, n2);
        }
    }
    
    protected class RightComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getRight();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.rightTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.rightFloatValueChanged(n, n2);
        }
    }
    
    protected class TopComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getTop();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.topTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.topFloatValueChanged(n, n2);
        }
    }
    
    protected class LeftComponent extends FloatComponent
    {
        protected Value getValue() {
            return CSSOMValue.this.valueProvider.getValue().getLeft();
        }
        
        public void setCssText(final String s) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.leftTextChanged(s);
        }
        
        public void setFloatValue(final short n, final float n2) throws DOMException {
            if (CSSOMValue.this.handler == null) {
                throw new DOMException((short)7, "");
            }
            this.getValue();
            CSSOMValue.this.handler.leftFloatValueChanged(n, n2);
        }
    }
    
    public abstract class AbstractModificationHandler implements ModificationHandler
    {
        protected abstract Value getValue();
        
        public void floatValueChanged(final short n, final float n2) throws DOMException {
            this.textChanged(FloatValue.getCssText(n, n2));
        }
        
        public void stringValueChanged(final short n, final String s) throws DOMException {
            this.textChanged(StringValue.getCssText(n, s));
        }
        
        public void leftTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rect(" + value.getTop().getCssText() + ", " + value.getRight().getCssText() + ", " + value.getBottom().getCssText() + ", " + string + ')';
            this.textChanged(string);
        }
        
        public void leftFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rect(" + value.getTop().getCssText() + ", " + value.getRight().getCssText() + ", " + value.getBottom().getCssText() + ", " + FloatValue.getCssText(n, n2) + ')');
        }
        
        public void topTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rect(" + string + ", " + value.getRight().getCssText() + ", " + value.getBottom().getCssText() + ", " + value.getLeft().getCssText() + ')';
            this.textChanged(string);
        }
        
        public void topFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rect(" + FloatValue.getCssText(n, n2) + ", " + value.getRight().getCssText() + ", " + value.getBottom().getCssText() + ", " + value.getLeft().getCssText() + ')');
        }
        
        public void rightTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rect(" + value.getTop().getCssText() + ", " + string + ", " + value.getBottom().getCssText() + ", " + value.getLeft().getCssText() + ')';
            this.textChanged(string);
        }
        
        public void rightFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rect(" + value.getTop().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + value.getBottom().getCssText() + ", " + value.getLeft().getCssText() + ')');
        }
        
        public void bottomTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rect(" + value.getTop().getCssText() + ", " + value.getRight().getCssText() + ", " + string + ", " + value.getLeft().getCssText() + ')';
            this.textChanged(string);
        }
        
        public void bottomFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rect(" + value.getTop().getCssText() + ", " + value.getRight().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + value.getLeft().getCssText() + ')');
        }
        
        public void redTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rgb(" + string + ", " + value.getGreen().getCssText() + ", " + value.getBlue().getCssText() + ')';
            this.textChanged(string);
        }
        
        public void redFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rgb(" + FloatValue.getCssText(n, n2) + ", " + value.getGreen().getCssText() + ", " + value.getBlue().getCssText() + ')');
        }
        
        public void greenTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rgb(" + value.getRed().getCssText() + ", " + string + ", " + value.getBlue().getCssText() + ')';
            this.textChanged(string);
        }
        
        public void greenFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rgb(" + value.getRed().getCssText() + ", " + FloatValue.getCssText(n, n2) + ", " + value.getBlue().getCssText() + ')');
        }
        
        public void blueTextChanged(String string) throws DOMException {
            final Value value = this.getValue();
            string = "rgb(" + value.getRed().getCssText() + ", " + value.getGreen().getCssText() + ", " + string + ')';
            this.textChanged(string);
        }
        
        public void blueFloatValueChanged(final short n, final float n2) throws DOMException {
            final Value value = this.getValue();
            this.textChanged("rgb(" + value.getRed().getCssText() + ", " + value.getGreen().getCssText() + ", " + FloatValue.getCssText(n, n2) + ')');
        }
        
        public void listTextChanged(final int n, String string) throws DOMException {
            final ListValue listValue = (ListValue)this.getValue();
            final int length = listValue.getLength();
            final StringBuffer sb = new StringBuffer(length * 8);
            for (int i = 0; i < n; ++i) {
                sb.append(listValue.item(i).getCssText());
                sb.append(listValue.getSeparatorChar());
            }
            sb.append(string);
            for (int j = n + 1; j < length; ++j) {
                sb.append(listValue.getSeparatorChar());
                sb.append(listValue.item(j).getCssText());
            }
            string = sb.toString();
            this.textChanged(string);
        }
        
        public void listFloatValueChanged(final int n, final short n2, final float n3) throws DOMException {
            final ListValue listValue = (ListValue)this.getValue();
            final int length = listValue.getLength();
            final StringBuffer sb = new StringBuffer(length * 8);
            for (int i = 0; i < n; ++i) {
                sb.append(listValue.item(i).getCssText());
                sb.append(listValue.getSeparatorChar());
            }
            sb.append(FloatValue.getCssText(n2, n3));
            for (int j = n + 1; j < length; ++j) {
                sb.append(listValue.getSeparatorChar());
                sb.append(listValue.item(j).getCssText());
            }
            this.textChanged(sb.toString());
        }
        
        public void listStringValueChanged(final int n, final short n2, final String s) throws DOMException {
            final ListValue listValue = (ListValue)this.getValue();
            final int length = listValue.getLength();
            final StringBuffer sb = new StringBuffer(length * 8);
            for (int i = 0; i < n; ++i) {
                sb.append(listValue.item(i).getCssText());
                sb.append(listValue.getSeparatorChar());
            }
            sb.append(StringValue.getCssText(n2, s));
            for (int j = n + 1; j < length; ++j) {
                sb.append(listValue.getSeparatorChar());
                sb.append(listValue.item(j).getCssText());
            }
            this.textChanged(sb.toString());
        }
    }
}
