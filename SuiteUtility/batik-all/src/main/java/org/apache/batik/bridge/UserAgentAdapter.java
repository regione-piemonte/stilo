// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Element;
import java.util.Iterator;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.text.Mark;
import java.awt.Cursor;
import org.w3c.dom.svg.SVGAElement;
import org.apache.batik.gvt.event.EventDispatcher;
import org.apache.batik.util.XMLResourceDescriptor;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import org.apache.batik.util.SVGFeatureStrings;
import java.util.HashSet;
import java.util.Set;

public class UserAgentAdapter implements UserAgent
{
    protected Set FEATURES;
    protected Set extensions;
    protected BridgeContext ctx;
    
    public UserAgentAdapter() {
        this.FEATURES = new HashSet();
        this.extensions = new HashSet();
    }
    
    public void setBridgeContext(final BridgeContext ctx) {
        this.ctx = ctx;
    }
    
    public void addStdFeatures() {
        SVGFeatureStrings.addSupportedFeatureStrings(this.FEATURES);
    }
    
    public Dimension2D getViewportSize() {
        return new Dimension(1, 1);
    }
    
    public void displayMessage(final String s) {
    }
    
    public void displayError(final String s) {
        this.displayMessage(s);
    }
    
    public void displayError(final Exception ex) {
        this.displayError(ex.getMessage());
    }
    
    public void showAlert(final String s) {
    }
    
    public String showPrompt(final String s) {
        return null;
    }
    
    public String showPrompt(final String s, final String s2) {
        return null;
    }
    
    public boolean showConfirm(final String s) {
        return false;
    }
    
    public float getPixelUnitToMillimeter() {
        return 0.26458332f;
    }
    
    public float getPixelToMM() {
        return this.getPixelUnitToMillimeter();
    }
    
    public String getDefaultFontFamily() {
        return "Arial, Helvetica, sans-serif";
    }
    
    public float getMediumFontSize() {
        return 228.59999f / (72.0f * this.getPixelUnitToMillimeter());
    }
    
    public float getLighterFontWeight(final float n) {
        return getStandardLighterFontWeight(n);
    }
    
    public float getBolderFontWeight(final float n) {
        return getStandardBolderFontWeight(n);
    }
    
    public String getLanguages() {
        return "en";
    }
    
    public String getMedia() {
        return "all";
    }
    
    public String getAlternateStyleSheet() {
        return null;
    }
    
    public String getUserStyleSheetURI() {
        return null;
    }
    
    public String getXMLParserClassName() {
        return XMLResourceDescriptor.getXMLParserClassName();
    }
    
    public boolean isXMLParserValidating() {
        return false;
    }
    
    public EventDispatcher getEventDispatcher() {
        return null;
    }
    
    public void openLink(final SVGAElement svgaElement) {
    }
    
    public void setSVGCursor(final Cursor cursor) {
    }
    
    public void setTextSelection(final Mark mark, final Mark mark2) {
    }
    
    public void deselectAll() {
    }
    
    public void runThread(final Thread thread) {
    }
    
    public AffineTransform getTransform() {
        return null;
    }
    
    public void setTransform(final AffineTransform affineTransform) {
    }
    
    public Point getClientAreaLocationOnScreen() {
        return new Point();
    }
    
    public boolean hasFeature(final String s) {
        return this.FEATURES.contains(s);
    }
    
    public boolean supportExtension(final String s) {
        return this.extensions.contains(s);
    }
    
    public void registerExtension(final BridgeExtension bridgeExtension) {
        final Iterator implementedExtensions = bridgeExtension.getImplementedExtensions();
        while (implementedExtensions.hasNext()) {
            this.extensions.add(implementedExtensions.next());
        }
    }
    
    public void handleElement(final Element element, final Object o) {
    }
    
    public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        return new DefaultScriptSecurity(s, parsedURL, parsedURL2);
    }
    
    public void checkLoadScript(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
        final ScriptSecurity scriptSecurity = this.getScriptSecurity(s, parsedURL, parsedURL2);
        if (scriptSecurity != null) {
            scriptSecurity.checkLoadScript();
        }
    }
    
    public ExternalResourceSecurity getExternalResourceSecurity(final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        return new RelaxedExternalResourceSecurity(parsedURL, parsedURL2);
    }
    
    public void checkLoadExternalResource(final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
        final ExternalResourceSecurity externalResourceSecurity = this.getExternalResourceSecurity(parsedURL, parsedURL2);
        if (externalResourceSecurity != null) {
            externalResourceSecurity.checkLoadExternalResource();
        }
    }
    
    public static float getStandardLighterFontWeight(final float f) {
        switch ((int)((f + 50.0f) / 100.0f) * 100) {
            case 100: {
                return 100.0f;
            }
            case 200: {
                return 100.0f;
            }
            case 300: {
                return 200.0f;
            }
            case 400: {
                return 300.0f;
            }
            case 500: {
                return 400.0f;
            }
            case 600: {
                return 400.0f;
            }
            case 700: {
                return 400.0f;
            }
            case 800: {
                return 400.0f;
            }
            case 900: {
                return 400.0f;
            }
            default: {
                throw new IllegalArgumentException("Bad Font Weight: " + f);
            }
        }
    }
    
    public static float getStandardBolderFontWeight(final float f) {
        switch ((int)((f + 50.0f) / 100.0f) * 100) {
            case 100: {
                return 600.0f;
            }
            case 200: {
                return 600.0f;
            }
            case 300: {
                return 600.0f;
            }
            case 400: {
                return 600.0f;
            }
            case 500: {
                return 600.0f;
            }
            case 600: {
                return 700.0f;
            }
            case 700: {
                return 800.0f;
            }
            case 800: {
                return 900.0f;
            }
            case 900: {
                return 900.0f;
            }
            default: {
                throw new IllegalArgumentException("Bad Font Weight: " + f);
            }
        }
    }
    
    public SVGDocument getBrokenLinkDocument(final Element element, final String s, final String s2) {
        throw new BridgeException(this.ctx, element, "uri.image.broken", new Object[] { s, s2 });
    }
}
