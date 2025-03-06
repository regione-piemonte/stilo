// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.bridge.RelaxedExternalResourceSecurity;
import org.apache.batik.bridge.ExternalResourceSecurity;
import org.apache.batik.bridge.RelaxedScriptSecurity;
import org.apache.batik.bridge.ScriptSecurity;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Element;
import org.apache.batik.util.XMLResourceDescriptor;

public class SVGUserAgentAdapter implements SVGUserAgent
{
    public void displayError(final String x) {
        System.err.println(x);
    }
    
    public void displayError(final Exception ex) {
        ex.printStackTrace();
    }
    
    public void displayMessage(final String x) {
        System.out.println(x);
    }
    
    public void showAlert(final String x) {
        System.err.println(x);
    }
    
    public String showPrompt(final String s) {
        return "";
    }
    
    public String showPrompt(final String s, final String s2) {
        return s2;
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
        return "Serif";
    }
    
    public float getMediumFontSize() {
        return 228.59999f / (72.0f * this.getPixelUnitToMillimeter());
    }
    
    public float getLighterFontWeight(final float f) {
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
    
    public float getBolderFontWeight(final float f) {
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
    
    public String getLanguages() {
        return "en";
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
    
    public String getMedia() {
        return "screen";
    }
    
    public String getAlternateStyleSheet() {
        return null;
    }
    
    public void openLink(final String s, final boolean b) {
    }
    
    public boolean supportExtension(final String s) {
        return false;
    }
    
    public void handleElement(final Element element, final Object o) {
    }
    
    public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        return new RelaxedScriptSecurity(s, parsedURL, parsedURL2);
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
}
