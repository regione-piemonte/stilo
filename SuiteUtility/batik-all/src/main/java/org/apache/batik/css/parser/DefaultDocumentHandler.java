// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SelectorList;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.DocumentHandler;

public class DefaultDocumentHandler implements DocumentHandler
{
    public static final DocumentHandler INSTANCE;
    
    protected DefaultDocumentHandler() {
    }
    
    public void startDocument(final InputSource inputSource) throws CSSException {
    }
    
    public void endDocument(final InputSource inputSource) throws CSSException {
    }
    
    public void comment(final String s) throws CSSException {
    }
    
    public void ignorableAtRule(final String s) throws CSSException {
    }
    
    public void namespaceDeclaration(final String s, final String s2) throws CSSException {
    }
    
    public void importStyle(final String s, final SACMediaList list, final String s2) throws CSSException {
    }
    
    public void startMedia(final SACMediaList list) throws CSSException {
    }
    
    public void endMedia(final SACMediaList list) throws CSSException {
    }
    
    public void startPage(final String s, final String s2) throws CSSException {
    }
    
    public void endPage(final String s, final String s2) throws CSSException {
    }
    
    public void startFontFace() throws CSSException {
    }
    
    public void endFontFace() throws CSSException {
    }
    
    public void startSelector(final SelectorList list) throws CSSException {
    }
    
    public void endSelector(final SelectorList list) throws CSSException {
    }
    
    public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) throws CSSException {
    }
    
    static {
        INSTANCE = (DocumentHandler)new DefaultDocumentHandler();
    }
}
