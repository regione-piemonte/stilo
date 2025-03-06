// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.Action;

public interface Application
{
    JSVGViewerFrame createAndShowJSVGViewerFrame();
    
    void closeJSVGViewerFrame(final JSVGViewerFrame p0);
    
    Action createExitAction(final JSVGViewerFrame p0);
    
    void openLink(final String p0);
    
    String getXMLParserClassName();
    
    boolean isXMLParserValidating();
    
    void showPreferenceDialog(final JSVGViewerFrame p0);
    
    String getLanguages();
    
    String getUserStyleSheetURI();
    
    String getDefaultFontFamily();
    
    String getMedia();
    
    boolean isSelectionOverlayXORMode();
    
    boolean canLoadScriptType(final String p0);
    
    int getAllowedScriptOrigin();
    
    int getAllowedExternalResourceOrigin();
    
    void addVisitedURI(final String p0);
    
    String[] getVisitedURIs();
    
    String getUISpecialization();
}
