// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.util.Iterator;
import java.util.LinkedList;
import org.apache.batik.dom.anim.AnimationTargetListener;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.anim.values.AnimatableNumberOptionalNumberValue;
import org.w3c.dom.svg.SVGAnimatedInteger;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.AbstractStylableDocument;
import org.w3c.dom.svg.SVGException;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.css.engine.CSSNavigableNode;
import org.w3c.dom.Node;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.svg.SVGFitToViewBox;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.parser.UnitProcessor;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.svg.SVGElement;

public abstract class SVGOMElement extends AbstractElement implements SVGElement, ExtendedTraitAccess, AnimationTarget
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected transient boolean readonly;
    protected String prefix;
    protected transient SVGContext svgContext;
    protected DoublyIndexedTable targetListeners;
    protected UnitProcessor.Context unitContext;
    
    protected SVGOMElement() {
    }
    
    protected SVGOMElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    protected void initializeAllLiveAttributes() {
    }
    
    public String getId() {
        if (((SVGOMDocument)this.ownerDocument).isSVG12) {
            final Attr attributeNodeNS = this.getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "id");
            if (attributeNodeNS != null) {
                return attributeNodeNS.getNodeValue();
            }
        }
        return this.getAttributeNS(null, "id");
    }
    
    public void setId(final String nodeValue) {
        if (((SVGOMDocument)this.ownerDocument).isSVG12) {
            this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "id", nodeValue);
            final Attr attributeNodeNS = this.getAttributeNodeNS(null, "id");
            if (attributeNodeNS != null) {
                attributeNodeNS.setNodeValue(nodeValue);
            }
        }
        else {
            this.setAttributeNS(null, "id", nodeValue);
        }
    }
    
    public String getXMLbase() {
        return this.getAttributeNS("http://www.w3.org/XML/1998/namespace", "base");
    }
    
    public void setXMLbase(final String s) throws DOMException {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", s);
    }
    
    public SVGSVGElement getOwnerSVGElement() {
        for (CSSStylableElement cssStylableElement = CSSEngine.getParentCSSStylableElement(this); cssStylableElement != null; cssStylableElement = CSSEngine.getParentCSSStylableElement(cssStylableElement)) {
            if (cssStylableElement instanceof SVGSVGElement) {
                return (SVGSVGElement)cssStylableElement;
            }
        }
        return null;
    }
    
    public SVGElement getViewportElement() {
        for (CSSStylableElement cssStylableElement = CSSEngine.getParentCSSStylableElement(this); cssStylableElement != null; cssStylableElement = CSSEngine.getParentCSSStylableElement(cssStylableElement)) {
            if (cssStylableElement instanceof SVGFitToViewBox) {
                return (SVGElement)cssStylableElement;
            }
        }
        return null;
    }
    
    public String getNodeName() {
        if (this.prefix == null || this.prefix.equals("")) {
            return this.getLocalName();
        }
        return this.prefix + ':' + this.getLocalName();
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/svg";
    }
    
    public void setPrefix(final String prefix) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (prefix != null && !prefix.equals("") && !DOMUtilities.isValidName(prefix)) {
            throw this.createDOMException((short)5, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), prefix });
        }
        this.prefix = prefix;
    }
    
    protected String getCascadedXMLBase(Node parentNode) {
        String s = null;
        Node node = parentNode.getParentNode();
        while (node != null) {
            if (node.getNodeType() == 1) {
                s = this.getCascadedXMLBase(node);
                break;
            }
            if (node instanceof CSSNavigableNode) {
                node = ((CSSNavigableNode)node).getCSSParentNode();
            }
            else {
                node = node.getParentNode();
            }
        }
        if (s == null) {
            AbstractDocument abstractDocument;
            if (parentNode.getNodeType() == 9) {
                abstractDocument = (AbstractDocument)parentNode;
            }
            else {
                abstractDocument = (AbstractDocument)parentNode.getOwnerDocument();
            }
            s = abstractDocument.getDocumentURI();
        }
        while (parentNode != null && parentNode.getNodeType() != 1) {
            parentNode = parentNode.getParentNode();
        }
        if (parentNode == null) {
            return s;
        }
        final Attr attributeNodeNS = ((Element)parentNode).getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "base");
        if (attributeNodeNS != null) {
            if (s == null) {
                s = attributeNodeNS.getNodeValue();
            }
            else {
                s = new ParsedURL(s, attributeNodeNS.getNodeValue()).toString();
            }
        }
        return s;
    }
    
    public void setSVGContext(final SVGContext svgContext) {
        this.svgContext = svgContext;
    }
    
    public SVGContext getSVGContext() {
        return this.svgContext;
    }
    
    public SVGException createSVGException(final short n, final String s, final Object[] array) {
        try {
            return new SVGOMException(n, this.getCurrentDocument().formatMessage(s, array));
        }
        catch (Exception ex) {
            return new SVGOMException(n, s);
        }
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMElement.xmlTraitInformation;
    }
    
    protected SVGOMAnimatedTransformList createLiveAnimatedTransformList(final String s, final String s2, final String s3) {
        final SVGOMAnimatedTransformList list = new SVGOMAnimatedTransformList(this, s, s2, s3);
        this.liveAttributeValues.put(s, s2, list);
        list.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return list;
    }
    
    protected SVGOMAnimatedBoolean createLiveAnimatedBoolean(final String s, final String s2, final boolean b) {
        final SVGOMAnimatedBoolean svgomAnimatedBoolean = new SVGOMAnimatedBoolean(this, s, s2, b);
        this.liveAttributeValues.put(s, s2, svgomAnimatedBoolean);
        svgomAnimatedBoolean.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedBoolean;
    }
    
    protected SVGOMAnimatedString createLiveAnimatedString(final String s, final String s2) {
        final SVGOMAnimatedString svgomAnimatedString = new SVGOMAnimatedString(this, s, s2);
        this.liveAttributeValues.put(s, s2, svgomAnimatedString);
        svgomAnimatedString.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedString;
    }
    
    protected SVGOMAnimatedPreserveAspectRatio createLiveAnimatedPreserveAspectRatio() {
        final SVGOMAnimatedPreserveAspectRatio svgomAnimatedPreserveAspectRatio = new SVGOMAnimatedPreserveAspectRatio(this);
        this.liveAttributeValues.put(null, "preserveAspectRatio", svgomAnimatedPreserveAspectRatio);
        svgomAnimatedPreserveAspectRatio.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedPreserveAspectRatio;
    }
    
    protected SVGOMAnimatedMarkerOrientValue createLiveAnimatedMarkerOrientValue(final String s, final String s2) {
        final SVGOMAnimatedMarkerOrientValue svgomAnimatedMarkerOrientValue = new SVGOMAnimatedMarkerOrientValue(this, s, s2);
        this.liveAttributeValues.put(s, s2, svgomAnimatedMarkerOrientValue);
        svgomAnimatedMarkerOrientValue.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedMarkerOrientValue;
    }
    
    protected SVGOMAnimatedPathData createLiveAnimatedPathData(final String s, final String s2, final String s3) {
        final SVGOMAnimatedPathData svgomAnimatedPathData = new SVGOMAnimatedPathData(this, s, s2, s3);
        this.liveAttributeValues.put(s, s2, svgomAnimatedPathData);
        svgomAnimatedPathData.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedPathData;
    }
    
    protected SVGOMAnimatedNumber createLiveAnimatedNumber(final String s, final String s2, final float n) {
        return this.createLiveAnimatedNumber(s, s2, n, false);
    }
    
    protected SVGOMAnimatedNumber createLiveAnimatedNumber(final String s, final String s2, final float n, final boolean b) {
        final SVGOMAnimatedNumber svgomAnimatedNumber = new SVGOMAnimatedNumber(this, s, s2, n, b);
        this.liveAttributeValues.put(s, s2, svgomAnimatedNumber);
        svgomAnimatedNumber.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedNumber;
    }
    
    protected SVGOMAnimatedNumberList createLiveAnimatedNumberList(final String s, final String s2, final String s3, final boolean b) {
        final SVGOMAnimatedNumberList list = new SVGOMAnimatedNumberList(this, s, s2, s3, b);
        this.liveAttributeValues.put(s, s2, list);
        list.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return list;
    }
    
    protected SVGOMAnimatedPoints createLiveAnimatedPoints(final String s, final String s2, final String s3) {
        final SVGOMAnimatedPoints svgomAnimatedPoints = new SVGOMAnimatedPoints(this, s, s2, s3);
        this.liveAttributeValues.put(s, s2, svgomAnimatedPoints);
        svgomAnimatedPoints.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedPoints;
    }
    
    protected SVGOMAnimatedLengthList createLiveAnimatedLengthList(final String s, final String s2, final String s3, final boolean b, final short n) {
        final SVGOMAnimatedLengthList list = new SVGOMAnimatedLengthList(this, s, s2, s3, b, n);
        this.liveAttributeValues.put(s, s2, list);
        list.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return list;
    }
    
    protected SVGOMAnimatedInteger createLiveAnimatedInteger(final String s, final String s2, final int n) {
        final SVGOMAnimatedInteger svgomAnimatedInteger = new SVGOMAnimatedInteger(this, s, s2, n);
        this.liveAttributeValues.put(s, s2, svgomAnimatedInteger);
        svgomAnimatedInteger.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedInteger;
    }
    
    protected SVGOMAnimatedEnumeration createLiveAnimatedEnumeration(final String s, final String s2, final String[] array, final short n) {
        final SVGOMAnimatedEnumeration svgomAnimatedEnumeration = new SVGOMAnimatedEnumeration(this, s, s2, array, n);
        this.liveAttributeValues.put(s, s2, svgomAnimatedEnumeration);
        svgomAnimatedEnumeration.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedEnumeration;
    }
    
    protected SVGOMAnimatedLength createLiveAnimatedLength(final String s, final String s2, final String s3, final short n, final boolean b) {
        final SVGOMAnimatedLength svgomAnimatedLength = new SVGOMAnimatedLength(this, s, s2, s3, n, b);
        this.liveAttributeValues.put(s, s2, svgomAnimatedLength);
        svgomAnimatedLength.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedLength;
    }
    
    protected SVGOMAnimatedRect createLiveAnimatedRect(final String s, final String s2, final String s3) {
        final SVGOMAnimatedRect svgomAnimatedRect = new SVGOMAnimatedRect(this, s, s2, s3);
        this.liveAttributeValues.put(s, s2, svgomAnimatedRect);
        svgomAnimatedRect.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
        return svgomAnimatedRect;
    }
    
    public boolean hasProperty(final String s) {
        final CSSEngine cssEngine = ((AbstractStylableDocument)this.ownerDocument).getCSSEngine();
        return cssEngine.getPropertyIndex(s) != -1 || cssEngine.getShorthandIndex(s) != -1;
    }
    
    public boolean hasTrait(final String s, final String s2) {
        return false;
    }
    
    public boolean isPropertyAnimatable(final String s) {
        final CSSEngine cssEngine = ((AbstractStylableDocument)this.ownerDocument).getCSSEngine();
        final int propertyIndex = cssEngine.getPropertyIndex(s);
        if (propertyIndex != -1) {
            return cssEngine.getValueManagers()[propertyIndex].isAnimatableProperty();
        }
        final int shorthandIndex = cssEngine.getShorthandIndex(s);
        return shorthandIndex != -1 && cssEngine.getShorthandManagers()[shorthandIndex].isAnimatableProperty();
    }
    
    public final boolean isAttributeAnimatable(final String s, final String s2) {
        final TraitInformation traitInformation = (TraitInformation)this.getTraitInformationTable().get(s, s2);
        return traitInformation != null && traitInformation.isAnimatable();
    }
    
    public boolean isPropertyAdditive(final String s) {
        final CSSEngine cssEngine = ((AbstractStylableDocument)this.ownerDocument).getCSSEngine();
        final int propertyIndex = cssEngine.getPropertyIndex(s);
        if (propertyIndex != -1) {
            return cssEngine.getValueManagers()[propertyIndex].isAdditiveProperty();
        }
        final int shorthandIndex = cssEngine.getShorthandIndex(s);
        return shorthandIndex != -1 && cssEngine.getShorthandManagers()[shorthandIndex].isAdditiveProperty();
    }
    
    public boolean isAttributeAdditive(final String s, final String s2) {
        return true;
    }
    
    public boolean isTraitAnimatable(final String s, final String s2) {
        return false;
    }
    
    public boolean isTraitAdditive(final String s, final String s2) {
        return false;
    }
    
    public int getPropertyType(final String s) {
        final CSSEngine cssEngine = ((AbstractStylableDocument)this.ownerDocument).getCSSEngine();
        final int propertyIndex = cssEngine.getPropertyIndex(s);
        if (propertyIndex != -1) {
            return cssEngine.getValueManagers()[propertyIndex].getPropertyType();
        }
        return 0;
    }
    
    public final int getAttributeType(final String s, final String s2) {
        final TraitInformation traitInformation = (TraitInformation)this.getTraitInformationTable().get(s, s2);
        if (traitInformation != null) {
            return traitInformation.getType();
        }
        return 0;
    }
    
    public Element getElement() {
        return this;
    }
    
    public void updatePropertyValue(final String s, final AnimatableValue animatableValue) {
    }
    
    public void updateAttributeValue(final String s, final String s2, final AnimatableValue animatableValue) {
        ((AbstractSVGAnimatedValue)this.getLiveAttributeValue(s, s2)).updateAnimatedValue(animatableValue);
    }
    
    public void updateOtherValue(final String s, final AnimatableValue animatableValue) {
    }
    
    public AnimatableValue getUnderlyingValue(final String s, final String s2) {
        final LiveAttributeValue liveAttributeValue = this.getLiveAttributeValue(s, s2);
        if (!(liveAttributeValue instanceof AnimatedLiveAttributeValue)) {
            return null;
        }
        return ((AnimatedLiveAttributeValue)liveAttributeValue).getUnderlyingValue(this);
    }
    
    protected AnimatableValue getBaseValue(final SVGAnimatedInteger svgAnimatedInteger, final SVGAnimatedInteger svgAnimatedInteger2) {
        return new AnimatableNumberOptionalNumberValue(this, (float)svgAnimatedInteger.getBaseVal(), (float)svgAnimatedInteger2.getBaseVal());
    }
    
    protected AnimatableValue getBaseValue(final SVGAnimatedNumber svgAnimatedNumber, final SVGAnimatedNumber svgAnimatedNumber2) {
        return new AnimatableNumberOptionalNumberValue(this, svgAnimatedNumber.getBaseVal(), svgAnimatedNumber2.getBaseVal());
    }
    
    public short getPercentageInterpretation(final String s, final String s2, final boolean b) {
        if ((b || s == null) && (s2.equals("baseline-shift") || s2.equals("font-size"))) {
            return 0;
        }
        if (b) {
            return 3;
        }
        final TraitInformation traitInformation = (TraitInformation)this.getTraitInformationTable().get(s, s2);
        if (traitInformation != null) {
            return traitInformation.getPercentageInterpretation();
        }
        return 3;
    }
    
    protected final short getAttributePercentageInterpretation(final String s, final String s2) {
        return 3;
    }
    
    public boolean useLinearRGBColorInterpolation() {
        return false;
    }
    
    public float svgToUserSpace(final float n, final short n2, final short n3) {
        if (this.unitContext == null) {
            this.unitContext = new UnitContext();
        }
        if (n3 == 0 && n2 == 2) {
            return 0.0f;
        }
        return UnitProcessor.svgToUserSpace(n, n2, (short)(3 - n3), this.unitContext);
    }
    
    public void addTargetListener(final String s, final String s2, final boolean b, final AnimationTargetListener e) {
        if (!b) {
            if (this.targetListeners == null) {
                this.targetListeners = new DoublyIndexedTable();
            }
            LinkedList<AnimationTargetListener> list = (LinkedList<AnimationTargetListener>)this.targetListeners.get(s, s2);
            if (list == null) {
                list = new LinkedList<AnimationTargetListener>();
                this.targetListeners.put(s, s2, list);
            }
            list.add(e);
        }
    }
    
    public void removeTargetListener(final String s, final String s2, final boolean b, final AnimationTargetListener o) {
        if (!b) {
            ((LinkedList)this.targetListeners.get(s, s2)).remove(o);
        }
    }
    
    void fireBaseAttributeListeners(final String s, final String s2) {
        if (this.targetListeners != null) {
            final Iterator iterator = ((LinkedList)this.targetListeners.get(s, s2)).iterator();
            while (iterator.hasNext()) {
                iterator.next().baseValueChanged(this, s, s2, false);
            }
        }
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        final SVGOMElement svgomElement = (SVGOMElement)node;
        svgomElement.prefix = this.prefix;
        svgomElement.initializeAllLiveAttributes();
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        final SVGOMElement svgomElement = (SVGOMElement)node;
        svgomElement.prefix = this.prefix;
        svgomElement.initializeAllLiveAttributes();
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final SVGOMElement svgomElement = (SVGOMElement)node;
        svgomElement.prefix = this.prefix;
        svgomElement.initializeAllLiveAttributes();
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final SVGOMElement svgomElement = (SVGOMElement)node;
        svgomElement.prefix = this.prefix;
        svgomElement.initializeAllLiveAttributes();
        return node;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable();
        xmlTraitInformation.put(null, "id", new TraitInformation(false, 16));
        xmlTraitInformation.put("http://www.w3.org/XML/1998/namespace", "base", new TraitInformation(false, 10));
        xmlTraitInformation.put("http://www.w3.org/XML/1998/namespace", "space", new TraitInformation(false, 15));
        xmlTraitInformation.put("http://www.w3.org/XML/1998/namespace", "id", new TraitInformation(false, 16));
        xmlTraitInformation.put("http://www.w3.org/XML/1998/namespace", "lang", new TraitInformation(false, 45));
        SVGOMElement.xmlTraitInformation = xmlTraitInformation;
    }
    
    protected class UnitContext implements UnitProcessor.Context
    {
        public Element getElement() {
            return SVGOMElement.this;
        }
        
        public float getPixelUnitToMillimeter() {
            return SVGOMElement.this.getSVGContext().getPixelUnitToMillimeter();
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public float getFontSize() {
            return SVGOMElement.this.getSVGContext().getFontSize();
        }
        
        public float getXHeight() {
            return 0.5f;
        }
        
        public float getViewportWidth() {
            return SVGOMElement.this.getSVGContext().getViewportWidth();
        }
        
        public float getViewportHeight() {
            return SVGOMElement.this.getSVGContext().getViewportHeight();
        }
    }
}
