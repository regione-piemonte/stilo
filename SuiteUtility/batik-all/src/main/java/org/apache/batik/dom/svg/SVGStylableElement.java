// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.css.dom.CSSOMValue;
import org.apache.batik.css.dom.CSSOMSVGColor;
import org.apache.batik.css.dom.CSSOMSVGPaint;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.StyleDeclaration;
import org.apache.batik.css.dom.CSSOMStoredStyleDeclaration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.svg.SVGColorManager;
import org.apache.batik.css.engine.value.svg.SVGPaintManager;
import org.w3c.dom.css.CSSValue;
import org.apache.batik.dom.anim.AnimationTargetListener;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.css.engine.StyleDeclarationProvider;
import org.w3c.dom.Node;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.css.engine.CSSStylableElement;

public abstract class SVGStylableElement extends SVGOMElement implements CSSStylableElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected StyleMap computedStyleMap;
    protected OverrideStyleDeclaration overrideStyleDeclaration;
    protected SVGOMAnimatedString className;
    protected StyleDeclaration style;
    
    protected SVGStylableElement() {
    }
    
    protected SVGStylableElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.className = this.createLiveAnimatedString(null, "class");
    }
    
    public CSSStyleDeclaration getOverrideStyle() {
        if (this.overrideStyleDeclaration == null) {
            this.overrideStyleDeclaration = new OverrideStyleDeclaration(((SVGOMDocument)this.getOwnerDocument()).getCSSEngine());
        }
        return this.overrideStyleDeclaration;
    }
    
    public StyleMap getComputedStyleMap(final String s) {
        return this.computedStyleMap;
    }
    
    public void setComputedStyleMap(final String s, final StyleMap computedStyleMap) {
        this.computedStyleMap = computedStyleMap;
    }
    
    public String getXMLId() {
        return this.getAttributeNS(null, "id");
    }
    
    public String getCSSClass() {
        return this.getAttributeNS(null, "class");
    }
    
    public ParsedURL getCSSBase() {
        if (this.getXblBoundElement() != null) {
            return null;
        }
        final String baseURI = this.getBaseURI();
        return (baseURI == null) ? null : new ParsedURL(baseURI);
    }
    
    public boolean isPseudoInstanceOf(final String s) {
        if (s.equals("first-child")) {
            Node node;
            for (node = this.getPreviousSibling(); node != null && node.getNodeType() != 1; node = node.getPreviousSibling()) {}
            return node == null;
        }
        return false;
    }
    
    public StyleDeclarationProvider getOverrideStyleDeclarationProvider() {
        return (StyleDeclarationProvider)this.getOverrideStyle();
    }
    
    public void updatePropertyValue(final String s, final AnimatableValue animatableValue) {
        final CSSStyleDeclaration overrideStyle = this.getOverrideStyle();
        if (animatableValue == null) {
            overrideStyle.removeProperty(s);
        }
        else {
            overrideStyle.setProperty(s, animatableValue.getCssText(), "");
        }
    }
    
    public boolean useLinearRGBColorInterpolation() {
        return ((SVGOMDocument)this.getOwnerDocument()).getCSSEngine().getComputedStyle(this, null, 6).getStringValue().charAt(0) == 'l';
    }
    
    public void addTargetListener(final String s, final String s2, final boolean b, final AnimationTargetListener animationTargetListener) {
        if (b && this.svgContext != null) {
            ((SVGAnimationTargetContext)this.svgContext).addTargetListener(s2, animationTargetListener);
        }
        else {
            super.addTargetListener(s, s2, b, animationTargetListener);
        }
    }
    
    public void removeTargetListener(final String s, final String s2, final boolean b, final AnimationTargetListener animationTargetListener) {
        if (b) {
            ((SVGAnimationTargetContext)this.svgContext).removeTargetListener(s2, animationTargetListener);
        }
        else {
            super.removeTargetListener(s, s2, b, animationTargetListener);
        }
    }
    
    public CSSStyleDeclaration getStyle() {
        if (this.style == null) {
            this.putLiveAttributeValue(null, "style", this.style = new StyleDeclaration(((SVGOMDocument)this.getOwnerDocument()).getCSSEngine()));
        }
        return this.style;
    }
    
    public CSSValue getPresentationAttribute(final String s) {
        Object o = this.getLiveAttributeValue(null, s);
        if (o != null) {
            return (CSSValue)o;
        }
        final CSSEngine cssEngine = ((SVGOMDocument)this.getOwnerDocument()).getCSSEngine();
        final int propertyIndex = cssEngine.getPropertyIndex(s);
        if (propertyIndex == -1) {
            return null;
        }
        if (propertyIndex > 59) {
            if (cssEngine.getValueManagers()[propertyIndex] instanceof SVGPaintManager) {
                o = new PresentationAttributePaintValue(cssEngine, s);
            }
            if (cssEngine.getValueManagers()[propertyIndex] instanceof SVGColorManager) {
                o = new PresentationAttributeColorValue(cssEngine, s);
            }
        }
        else {
            switch (propertyIndex) {
                case 15:
                case 45: {
                    o = new PresentationAttributePaintValue(cssEngine, s);
                    break;
                }
                case 19:
                case 33:
                case 43: {
                    o = new PresentationAttributeColorValue(cssEngine, s);
                    break;
                }
                default: {
                    o = new PresentationAttributeValue(cssEngine, s);
                    break;
                }
            }
        }
        this.putLiveAttributeValue(null, s, (LiveAttributeValue)o);
        if (this.getAttributeNS(null, s).length() == 0) {
            return null;
        }
        return (CSSValue)o;
    }
    
    public SVGAnimatedString getClassName() {
        return (SVGAnimatedString)this.className;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGStylableElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "class", new TraitInformation(true, 16));
        SVGStylableElement.xmlTraitInformation = xmlTraitInformation;
    }
    
    protected class OverrideStyleDeclaration extends CSSOMStoredStyleDeclaration
    {
        protected OverrideStyleDeclaration(final CSSEngine cssEngine) {
            super(cssEngine);
            this.declaration = new org.apache.batik.css.engine.StyleDeclaration();
        }
        
        public void textChanged(final String s) throws DOMException {
            ((SVGOMDocument)SVGStylableElement.this.ownerDocument).overrideStyleTextChanged(SVGStylableElement.this, s);
        }
        
        public void propertyRemoved(final String s) throws DOMException {
            ((SVGOMDocument)SVGStylableElement.this.ownerDocument).overrideStylePropertyRemoved(SVGStylableElement.this, s);
        }
        
        public void propertyChanged(final String s, final String s2, final String s3) throws DOMException {
            ((SVGOMDocument)SVGStylableElement.this.ownerDocument).overrideStylePropertyChanged(SVGStylableElement.this, s, s2, s3);
        }
    }
    
    public class StyleDeclaration extends CSSOMStoredStyleDeclaration implements LiveAttributeValue, CSSEngine.MainPropertyReceiver
    {
        protected boolean mutate;
        
        public StyleDeclaration(final CSSEngine cssEngine) {
            super(cssEngine);
            this.declaration = this.cssEngine.parseStyleDeclaration(SVGStylableElement.this, SVGStylableElement.this.getAttributeNS(null, "style"));
        }
        
        public void attrAdded(final Attr attr, final String s) {
            if (!this.mutate) {
                this.declaration = this.cssEngine.parseStyleDeclaration(SVGStylableElement.this, s);
            }
        }
        
        public void attrModified(final Attr attr, final String s, final String s2) {
            if (!this.mutate) {
                this.declaration = this.cssEngine.parseStyleDeclaration(SVGStylableElement.this, s2);
            }
        }
        
        public void attrRemoved(final Attr attr, final String s) {
            if (!this.mutate) {
                this.declaration = new org.apache.batik.css.engine.StyleDeclaration();
            }
        }
        
        public void textChanged(final String s) throws DOMException {
            this.declaration = this.cssEngine.parseStyleDeclaration(SVGStylableElement.this, s);
            this.mutate = true;
            SVGStylableElement.this.setAttributeNS(null, "style", s);
            this.mutate = false;
        }
        
        public void propertyRemoved(final String s) throws DOMException {
            final int propertyIndex = this.cssEngine.getPropertyIndex(s);
            for (int i = 0; i < this.declaration.size(); ++i) {
                if (propertyIndex == this.declaration.getIndex(i)) {
                    this.declaration.remove(i);
                    this.mutate = true;
                    SVGStylableElement.this.setAttributeNS(null, "style", this.declaration.toString(this.cssEngine));
                    this.mutate = false;
                    return;
                }
            }
        }
        
        public void propertyChanged(final String s, final String s2, final String s3) throws DOMException {
            this.cssEngine.setMainProperties(SVGStylableElement.this, this, s, s2, s3 != null && s3.length() > 0);
            this.mutate = true;
            SVGStylableElement.this.setAttributeNS(null, "style", this.declaration.toString(this.cssEngine));
            this.mutate = false;
        }
        
        public void setMainProperty(final String s, final Value value, final boolean b) {
            final int propertyIndex = this.cssEngine.getPropertyIndex(s);
            if (propertyIndex == -1) {
                return;
            }
            int n;
            for (n = 0; n < this.declaration.size() && propertyIndex != this.declaration.getIndex(n); ++n) {}
            if (n < this.declaration.size()) {
                this.declaration.put(n, value, propertyIndex, b);
            }
            else {
                this.declaration.append(value, propertyIndex, b);
            }
        }
    }
    
    public class PresentationAttributePaintValue extends CSSOMSVGPaint implements LiveAttributeValue, ValueProvider
    {
        protected CSSEngine cssEngine;
        protected String property;
        protected Value value;
        protected boolean mutate;
        private final /* synthetic */ SVGStylableElement this$0;
        
        public PresentationAttributePaintValue(final CSSEngine cssEngine, final String property) {
            super(null);
            ((CSSOMSVGPaint)(this.valueProvider = this)).setModificationHandler(new CSSOMSVGPaint.AbstractModificationHandler() {
                private final /* synthetic */ PresentationAttributePaintValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    this.this$1.value = this.this$1.cssEngine.parsePropertyValue(this.this$1.this$0, this.this$1.property, s);
                    this.this$1.mutate = true;
                    this.this$1.this$0.setAttributeNS(null, this.this$1.property, s);
                    this.this$1.mutate = false;
                }
            });
            this.cssEngine = cssEngine;
            this.property = property;
            final Attr attributeNodeNS = SVGStylableElement.this.getAttributeNodeNS(null, property);
            if (attributeNodeNS != null) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, property, attributeNodeNS.getValue());
            }
        }
        
        public Value getValue() {
            if (this.value == null) {
                throw new DOMException((short)11, "");
            }
            return this.value;
        }
        
        public void attrAdded(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s);
            }
        }
        
        public void attrModified(final Attr attr, final String s, final String s2) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s2);
            }
        }
        
        public void attrRemoved(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = null;
            }
        }
    }
    
    public class PresentationAttributeColorValue extends CSSOMSVGColor implements LiveAttributeValue, ValueProvider
    {
        protected CSSEngine cssEngine;
        protected String property;
        protected Value value;
        protected boolean mutate;
        private final /* synthetic */ SVGStylableElement this$0;
        
        public PresentationAttributeColorValue(final CSSEngine cssEngine, final String property) {
            super(null);
            ((CSSOMSVGColor)(this.valueProvider = this)).setModificationHandler(new AbstractModificationHandler() {
                private final /* synthetic */ PresentationAttributeColorValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    this.this$1.value = this.this$1.cssEngine.parsePropertyValue(this.this$1.this$0, this.this$1.property, s);
                    this.this$1.mutate = true;
                    this.this$1.this$0.setAttributeNS(null, this.this$1.property, s);
                    this.this$1.mutate = false;
                }
            });
            this.cssEngine = cssEngine;
            this.property = property;
            final Attr attributeNodeNS = SVGStylableElement.this.getAttributeNodeNS(null, property);
            if (attributeNodeNS != null) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, property, attributeNodeNS.getValue());
            }
        }
        
        public Value getValue() {
            if (this.value == null) {
                throw new DOMException((short)11, "");
            }
            return this.value;
        }
        
        public void attrAdded(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s);
            }
        }
        
        public void attrModified(final Attr attr, final String s, final String s2) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s2);
            }
        }
        
        public void attrRemoved(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = null;
            }
        }
    }
    
    public class PresentationAttributeValue extends CSSOMValue implements LiveAttributeValue, ValueProvider
    {
        protected CSSEngine cssEngine;
        protected String property;
        protected Value value;
        protected boolean mutate;
        private final /* synthetic */ SVGStylableElement this$0;
        
        public PresentationAttributeValue(final CSSEngine cssEngine, final String property) {
            super(null);
            ((CSSOMValue)(this.valueProvider = this)).setModificationHandler(new AbstractModificationHandler() {
                private final /* synthetic */ PresentationAttributeValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    this.this$1.value = this.this$1.cssEngine.parsePropertyValue(this.this$1.this$0, this.this$1.property, s);
                    this.this$1.mutate = true;
                    this.this$1.this$0.setAttributeNS(null, this.this$1.property, s);
                    this.this$1.mutate = false;
                }
            });
            this.cssEngine = cssEngine;
            this.property = property;
            final Attr attributeNodeNS = SVGStylableElement.this.getAttributeNodeNS(null, property);
            if (attributeNodeNS != null) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, property, attributeNodeNS.getValue());
            }
        }
        
        public Value getValue() {
            if (this.value == null) {
                throw new DOMException((short)11, "");
            }
            return this.value;
        }
        
        public void attrAdded(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s);
            }
        }
        
        public void attrModified(final Attr attr, final String s, final String s2) {
            if (!this.mutate) {
                this.value = this.cssEngine.parsePropertyValue(SVGStylableElement.this, this.property, s2);
            }
        }
        
        public void attrRemoved(final Attr attr, final String s) {
            if (!this.mutate) {
                this.value = null;
            }
        }
    }
}
