// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.bridge.ExternalResourceSecurity;
import org.apache.batik.bridge.ScriptSecurity;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Element;

public interface SVGUserAgent
{
    void displayError(final String p0);
    
    void displayError(final Exception p0);
    
    void displayMessage(final String p0);
    
    void showAlert(final String p0);
    
    String showPrompt(final String p0);
    
    String showPrompt(final String p0, final String p1);
    
    boolean showConfirm(final String p0);
    
    float getPixelUnitToMillimeter();
    
    float getPixelToMM();
    
    String getDefaultFontFamily();
    
    float getMediumFontSize();
    
    float getLighterFontWeight(final float p0);
    
    float getBolderFontWeight(final float p0);
    
    String getLanguages();
    
    String getUserStyleSheetURI();
    
    String getXMLParserClassName();
    
    boolean isXMLParserValidating();
    
    String getMedia();
    
    String getAlternateStyleSheet();
    
    void openLink(final String p0, final boolean p1);
    
    boolean supportExtension(final String p0);
    
    void handleElement(final Element p0, final Object p1);
    
    ScriptSecurity getScriptSecurity(final String p0, final ParsedURL p1, final ParsedURL p2);
    
    void checkLoadScript(final String p0, final ParsedURL p1, final ParsedURL p2) throws SecurityException;
    
    ExternalResourceSecurity getExternalResourceSecurity(final ParsedURL p0, final ParsedURL p1);
    
    void checkLoadExternalResource(final ParsedURL p0, final ParsedURL p1) throws SecurityException;
}
