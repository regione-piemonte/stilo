// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import java.util.StringTokenizer;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SelectorList;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.CSSException;
import java.util.Locale;
import org.w3c.css.sac.Parser;

public class ExtendedParserWrapper implements ExtendedParser
{
    public Parser parser;
    
    public static ExtendedParser wrap(final Parser parser) {
        if (parser instanceof ExtendedParser) {
            return (ExtendedParser)parser;
        }
        return new ExtendedParserWrapper(parser);
    }
    
    public ExtendedParserWrapper(final Parser parser) {
        this.parser = parser;
    }
    
    public String getParserVersion() {
        return this.parser.getParserVersion();
    }
    
    public void setLocale(final Locale locale) throws CSSException {
        this.parser.setLocale(locale);
    }
    
    public void setDocumentHandler(final DocumentHandler documentHandler) {
        this.parser.setDocumentHandler(documentHandler);
    }
    
    public void setSelectorFactory(final SelectorFactory selectorFactory) {
        this.parser.setSelectorFactory(selectorFactory);
    }
    
    public void setConditionFactory(final ConditionFactory conditionFactory) {
        this.parser.setConditionFactory(conditionFactory);
    }
    
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.parser.setErrorHandler(errorHandler);
    }
    
    public void parseStyleSheet(final InputSource inputSource) throws CSSException, IOException {
        this.parser.parseStyleSheet(inputSource);
    }
    
    public void parseStyleSheet(final String s) throws CSSException, IOException {
        this.parser.parseStyleSheet(s);
    }
    
    public void parseStyleDeclaration(final InputSource inputSource) throws CSSException, IOException {
        this.parser.parseStyleDeclaration(inputSource);
    }
    
    public void parseStyleDeclaration(final String s) throws CSSException, IOException {
        this.parser.parseStyleDeclaration(new InputSource((Reader)new StringReader(s)));
    }
    
    public void parseRule(final InputSource inputSource) throws CSSException, IOException {
        this.parser.parseRule(inputSource);
    }
    
    public void parseRule(final String s) throws CSSException, IOException {
        this.parser.parseRule(new InputSource((Reader)new StringReader(s)));
    }
    
    public SelectorList parseSelectors(final InputSource inputSource) throws CSSException, IOException {
        return this.parser.parseSelectors(inputSource);
    }
    
    public SelectorList parseSelectors(final String s) throws CSSException, IOException {
        return this.parser.parseSelectors(new InputSource((Reader)new StringReader(s)));
    }
    
    public LexicalUnit parsePropertyValue(final InputSource inputSource) throws CSSException, IOException {
        return this.parser.parsePropertyValue(inputSource);
    }
    
    public LexicalUnit parsePropertyValue(final String s) throws CSSException, IOException {
        return this.parser.parsePropertyValue(new InputSource((Reader)new StringReader(s)));
    }
    
    public boolean parsePriority(final InputSource inputSource) throws CSSException, IOException {
        return this.parser.parsePriority(inputSource);
    }
    
    public SACMediaList parseMedia(final String s) throws CSSException, IOException {
        final CSSSACMediaList list = new CSSSACMediaList();
        if (!"all".equalsIgnoreCase(s)) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s, " ,");
            while (stringTokenizer.hasMoreTokens()) {
                list.append(stringTokenizer.nextToken());
            }
        }
        return (SACMediaList)list;
    }
    
    public boolean parsePriority(final String s) throws CSSException, IOException {
        return this.parser.parsePriority(new InputSource((Reader)new StringReader(s)));
    }
}
