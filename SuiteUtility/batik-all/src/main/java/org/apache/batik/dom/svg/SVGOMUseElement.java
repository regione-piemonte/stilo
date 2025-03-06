// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGElementInstance;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGUseElement;

public class SVGOMUseElement extends SVGURIReferenceGraphicsElement implements SVGUseElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMUseShadowRoot shadowTree;
    
    protected SVGOMUseElement() {
    }
    
    public SVGOMUseElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLength(null, "x", "0", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "0", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", null, (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", null, (short)1, true);
    }
    
    public String getLocalName() {
        return "use";
    }
    
    public SVGAnimatedLength getX() {
        return (SVGAnimatedLength)this.x;
    }
    
    public SVGAnimatedLength getY() {
        return (SVGAnimatedLength)this.y;
    }
    
    public SVGAnimatedLength getWidth() {
        return (SVGAnimatedLength)this.width;
    }
    
    public SVGAnimatedLength getHeight() {
        return (SVGAnimatedLength)this.height;
    }
    
    public SVGElementInstance getInstanceRoot() {
        throw new UnsupportedOperationException("SVGUseElement.getInstanceRoot is not implemented");
    }
    
    public SVGElementInstance getAnimatedInstanceRoot() {
        throw new UnsupportedOperationException("SVGUseElement.getAnimatedInstanceRoot is not implemented");
    }
    
    public Node getCSSFirstChild() {
        if (this.shadowTree != null) {
            return this.shadowTree.getFirstChild();
        }
        return null;
    }
    
    public Node getCSSLastChild() {
        return this.getCSSFirstChild();
    }
    
    public boolean isHiddenFromSelectors() {
        return true;
    }
    
    public void setUseShadowTree(final SVGOMUseShadowRoot shadowTree) {
        this.shadowTree = shadowTree;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMUseElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMUseElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMUseElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGURIReferenceGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        SVGOMUseElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMUseElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMUseElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "embed");
        SVGOMUseElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
