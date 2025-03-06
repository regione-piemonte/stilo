// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.Reader;
import org.apache.batik.util.ParsedURL;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SelectorList;
import org.w3c.css.sac.CSSParseException;
import java.io.IOException;
import org.w3c.css.sac.InputSource;
import java.util.MissingResourceException;
import org.w3c.css.sac.CSSException;
import java.util.Locale;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.DocumentHandler;
import org.apache.batik.i18n.LocalizableSupport;
import org.apache.batik.i18n.Localizable;

public class Parser implements ExtendedParser, Localizable
{
    public static final String BUNDLE_CLASSNAME = "org.apache.batik.css.parser.resources.Messages";
    protected LocalizableSupport localizableSupport;
    protected Scanner scanner;
    protected int current;
    protected DocumentHandler documentHandler;
    protected SelectorFactory selectorFactory;
    protected ConditionFactory conditionFactory;
    protected ErrorHandler errorHandler;
    protected String pseudoElement;
    protected String documentURI;
    
    public Parser() {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.css.parser.resources.Messages", Parser.class.getClassLoader());
        this.documentHandler = DefaultDocumentHandler.INSTANCE;
        this.selectorFactory = DefaultSelectorFactory.INSTANCE;
        this.conditionFactory = DefaultConditionFactory.INSTANCE;
        this.errorHandler = DefaultErrorHandler.INSTANCE;
    }
    
    public String getParserVersion() {
        return "http://www.w3.org/TR/REC-CSS2";
    }
    
    public void setLocale(final Locale locale) throws CSSException {
        this.localizableSupport.setLocale(locale);
    }
    
    public Locale getLocale() {
        return this.localizableSupport.getLocale();
    }
    
    public String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return this.localizableSupport.formatMessage(s, array);
    }
    
    public void setDocumentHandler(final DocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
    }
    
    public void setSelectorFactory(final SelectorFactory selectorFactory) {
        this.selectorFactory = selectorFactory;
    }
    
    public void setConditionFactory(final ConditionFactory conditionFactory) {
        this.conditionFactory = conditionFactory;
    }
    
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    public void parseStyleSheet(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        try {
            this.documentHandler.startDocument(inputSource);
            switch (this.current = this.scanner.next()) {
                case 30: {
                    if (this.nextIgnoreSpaces() != 19) {
                        this.reportError("charset.string");
                        break;
                    }
                    if (this.nextIgnoreSpaces() != 8) {
                        this.reportError("semicolon");
                    }
                    this.next();
                    break;
                }
                case 18: {
                    this.documentHandler.comment(this.scanner.getStringValue());
                    break;
                }
            }
            this.skipSpacesAndCDOCDC();
            while (this.current == 28) {
                this.nextIgnoreSpaces();
                this.parseImportRule();
                this.nextIgnoreSpaces();
            }
        Label_0252:
            while (true) {
                switch (this.current) {
                    case 33: {
                        this.nextIgnoreSpaces();
                        this.parsePageRule();
                        break;
                    }
                    case 32: {
                        this.nextIgnoreSpaces();
                        this.parseMediaRule();
                        break;
                    }
                    case 31: {
                        this.nextIgnoreSpaces();
                        this.parseFontFaceRule();
                        break;
                    }
                    case 29: {
                        this.nextIgnoreSpaces();
                        this.parseAtRule();
                        break;
                    }
                    case 0: {
                        break Label_0252;
                    }
                    default: {
                        this.parseRuleSet();
                        break;
                    }
                }
                this.skipSpacesAndCDOCDC();
            }
        }
        finally {
            this.documentHandler.endDocument(inputSource);
            this.scanner = null;
        }
    }
    
    public void parseStyleSheet(final String s) throws CSSException, IOException {
        this.parseStyleSheet(new InputSource(s));
    }
    
    public void parseStyleDeclaration(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        this.parseStyleDeclarationInternal();
    }
    
    protected void parseStyleDeclarationInternal() throws CSSException, IOException {
        this.nextIgnoreSpaces();
        try {
            this.parseStyleDeclaration(false);
        }
        catch (CSSParseException ex) {
            this.reportError(ex);
        }
        finally {
            this.scanner = null;
        }
    }
    
    public void parseRule(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        this.parseRuleInternal();
    }
    
    protected void parseRuleInternal() throws CSSException, IOException {
        this.nextIgnoreSpaces();
        this.parseRule();
        this.scanner = null;
    }
    
    public SelectorList parseSelectors(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        return this.parseSelectorsInternal();
    }
    
    protected SelectorList parseSelectorsInternal() throws CSSException, IOException {
        this.nextIgnoreSpaces();
        final SelectorList selectorList = this.parseSelectorList();
        this.scanner = null;
        return selectorList;
    }
    
    public LexicalUnit parsePropertyValue(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        return this.parsePropertyValueInternal();
    }
    
    protected LexicalUnit parsePropertyValueInternal() throws CSSException, IOException {
        this.nextIgnoreSpaces();
        LexicalUnit expression;
        try {
            expression = this.parseExpression(false);
        }
        catch (CSSParseException ex) {
            this.reportError(ex);
            throw ex;
        }
        CSSParseException cssParseException = null;
        if (this.current != 0) {
            cssParseException = this.createCSSParseException("eof.expected");
        }
        this.scanner = null;
        if (cssParseException != null) {
            this.errorHandler.fatalError(cssParseException);
        }
        return expression;
    }
    
    public boolean parsePriority(final InputSource inputSource) throws CSSException, IOException {
        this.scanner = this.createScanner(inputSource);
        return this.parsePriorityInternal();
    }
    
    protected boolean parsePriorityInternal() throws CSSException, IOException {
        this.nextIgnoreSpaces();
        this.scanner = null;
        switch (this.current) {
            case 0: {
                return false;
            }
            case 28: {
                return true;
            }
            default: {
                this.reportError("token", new Object[] { new Integer(this.current) });
                return false;
            }
        }
    }
    
    protected void parseRule() {
        switch (this.scanner.getType()) {
            case 28: {
                this.nextIgnoreSpaces();
                this.parseImportRule();
                break;
            }
            case 29: {
                this.nextIgnoreSpaces();
                this.parseAtRule();
                break;
            }
            case 31: {
                this.nextIgnoreSpaces();
                this.parseFontFaceRule();
                break;
            }
            case 32: {
                this.nextIgnoreSpaces();
                this.parseMediaRule();
                break;
            }
            case 33: {
                this.nextIgnoreSpaces();
                this.parsePageRule();
                break;
            }
            default: {
                this.parseRuleSet();
                break;
            }
        }
    }
    
    protected void parseAtRule() {
        this.scanner.scanAtRule();
        this.documentHandler.ignorableAtRule(this.scanner.getStringValue());
        this.nextIgnoreSpaces();
    }
    
    protected void parseImportRule() {
        switch (this.current) {
            default: {
                this.reportError("string.or.uri");
            }
            case 19:
            case 51: {
                final String stringValue = this.scanner.getStringValue();
                this.nextIgnoreSpaces();
                CSSSACMediaList mediaList;
                if (this.current != 20) {
                    mediaList = new CSSSACMediaList();
                    mediaList.append("all");
                }
                else {
                    mediaList = this.parseMediaList();
                }
                this.documentHandler.importStyle(stringValue, (SACMediaList)mediaList, (String)null);
                if (this.current != 8) {
                    this.reportError("semicolon");
                }
                else {
                    this.next();
                }
            }
        }
    }
    
    protected CSSSACMediaList parseMediaList() {
        final CSSSACMediaList list = new CSSSACMediaList();
        list.append(this.scanner.getStringValue());
        this.nextIgnoreSpaces();
        while (this.current == 6) {
            this.nextIgnoreSpaces();
            switch (this.current) {
                default: {
                    this.reportError("identifier");
                    continue;
                }
                case 20: {
                    list.append(this.scanner.getStringValue());
                    this.nextIgnoreSpaces();
                    continue;
                }
            }
        }
        return list;
    }
    
    protected void parseFontFaceRule() {
        try {
            this.documentHandler.startFontFace();
            if (this.current != 1) {
                this.reportError("left.curly.brace");
            }
            else {
                this.nextIgnoreSpaces();
                try {
                    this.parseStyleDeclaration(true);
                }
                catch (CSSParseException ex) {
                    this.reportError(ex);
                }
            }
        }
        finally {
            this.documentHandler.endFontFace();
        }
    }
    
    protected void parsePageRule() {
        String stringValue = null;
        String stringValue2 = null;
        if (this.current == 20) {
            stringValue = this.scanner.getStringValue();
            this.nextIgnoreSpaces();
            if (this.current == 16) {
                this.nextIgnoreSpaces();
                if (this.current != 20) {
                    this.reportError("identifier");
                    return;
                }
                stringValue2 = this.scanner.getStringValue();
                this.nextIgnoreSpaces();
            }
        }
        try {
            this.documentHandler.startPage(stringValue, stringValue2);
            if (this.current != 1) {
                this.reportError("left.curly.brace");
            }
            else {
                this.nextIgnoreSpaces();
                try {
                    this.parseStyleDeclaration(true);
                }
                catch (CSSParseException ex) {
                    this.reportError(ex);
                }
            }
        }
        finally {
            this.documentHandler.endPage(stringValue, stringValue2);
        }
    }
    
    protected void parseMediaRule() {
        if (this.current != 20) {
            this.reportError("identifier");
            return;
        }
        final CSSSACMediaList mediaList = this.parseMediaList();
        try {
            this.documentHandler.startMedia((SACMediaList)mediaList);
            if (this.current != 1) {
                this.reportError("left.curly.brace");
            }
            else {
                this.nextIgnoreSpaces();
            Label_0084:
                while (true) {
                    switch (this.current) {
                        case 0:
                        case 2: {
                            break Label_0084;
                        }
                        default: {
                            this.parseRuleSet();
                            continue;
                        }
                    }
                }
                this.nextIgnoreSpaces();
            }
        }
        finally {
            this.documentHandler.endMedia((SACMediaList)mediaList);
        }
    }
    
    protected void parseRuleSet() {
        SelectorList selectorList;
        try {
            selectorList = this.parseSelectorList();
        }
        catch (CSSParseException ex) {
            this.reportError(ex);
            return;
        }
        try {
            this.documentHandler.startSelector(selectorList);
            if (this.current != 1) {
                this.reportError("left.curly.brace");
                if (this.current == 2) {
                    this.nextIgnoreSpaces();
                }
            }
            else {
                this.nextIgnoreSpaces();
                try {
                    this.parseStyleDeclaration(true);
                }
                catch (CSSParseException ex2) {
                    this.reportError(ex2);
                }
            }
        }
        finally {
            this.documentHandler.endSelector(selectorList);
        }
    }
    
    protected SelectorList parseSelectorList() {
        final CSSSelectorList list = new CSSSelectorList();
        list.append(this.parseSelector());
        while (this.current == 6) {
            this.nextIgnoreSpaces();
            list.append(this.parseSelector());
        }
        return (SelectorList)list;
    }
    
    protected Selector parseSelector() {
        this.pseudoElement = null;
        Object o = this.parseSimpleSelector();
        while (true) {
            switch (this.current) {
                default: {
                    if (this.pseudoElement != null) {
                        o = this.selectorFactory.createChildSelector((Selector)o, (SimpleSelector)this.selectorFactory.createPseudoElementSelector((String)null, this.pseudoElement));
                    }
                    return (Selector)o;
                }
                case 7:
                case 11:
                case 13:
                case 16:
                case 20:
                case 27: {
                    if (this.pseudoElement != null) {
                        throw this.createCSSParseException("pseudo.element.position");
                    }
                    o = this.selectorFactory.createDescendantSelector((Selector)o, this.parseSimpleSelector());
                    continue;
                }
                case 4: {
                    if (this.pseudoElement != null) {
                        throw this.createCSSParseException("pseudo.element.position");
                    }
                    this.nextIgnoreSpaces();
                    o = this.selectorFactory.createDirectAdjacentSelector((short)1, (Selector)o, this.parseSimpleSelector());
                    continue;
                }
                case 9: {
                    if (this.pseudoElement != null) {
                        throw this.createCSSParseException("pseudo.element.position");
                    }
                    this.nextIgnoreSpaces();
                    o = this.selectorFactory.createChildSelector((Selector)o, this.parseSimpleSelector());
                    continue;
                }
            }
        }
    }
    
    protected SimpleSelector parseSimpleSelector() {
        Object o = null;
        Label_0075: {
            switch (this.current) {
                case 20: {
                    o = this.selectorFactory.createElementSelector((String)null, this.scanner.getStringValue());
                    this.next();
                    break Label_0075;
                }
                case 13: {
                    this.next();
                    break;
                }
            }
            o = this.selectorFactory.createElementSelector((String)null, (String)null);
        }
        Object andCondition = null;
        while (true) {
            Object o2 = null;
            Label_0651: {
                switch (this.current) {
                    case 27: {
                        o2 = this.conditionFactory.createIdCondition(this.scanner.getStringValue());
                        this.next();
                        break;
                    }
                    case 7: {
                        if (this.next() != 20) {
                            throw this.createCSSParseException("identifier");
                        }
                        o2 = this.conditionFactory.createClassCondition((String)null, this.scanner.getStringValue());
                        this.next();
                        break;
                    }
                    case 11: {
                        if (this.nextIgnoreSpaces() != 20) {
                            throw this.createCSSParseException("identifier");
                        }
                        final String stringValue = this.scanner.getStringValue();
                        final int nextIgnoreSpaces = this.nextIgnoreSpaces();
                        switch (nextIgnoreSpaces) {
                            default: {
                                throw this.createCSSParseException("right.bracket");
                            }
                            case 12: {
                                this.nextIgnoreSpaces();
                                o2 = this.conditionFactory.createAttributeCondition(stringValue, (String)null, false, (String)null);
                                break Label_0651;
                            }
                            case 3:
                            case 25:
                            case 26: {
                                switch (this.nextIgnoreSpaces()) {
                                    default: {
                                        throw this.createCSSParseException("identifier.or.string");
                                    }
                                    case 19:
                                    case 20: {
                                        final String stringValue2 = this.scanner.getStringValue();
                                        this.nextIgnoreSpaces();
                                        if (this.current != 12) {
                                            throw this.createCSSParseException("right.bracket");
                                        }
                                        this.next();
                                        switch (nextIgnoreSpaces) {
                                            case 3: {
                                                o2 = this.conditionFactory.createAttributeCondition(stringValue, (String)null, false, stringValue2);
                                                break Label_0651;
                                            }
                                            case 26: {
                                                o2 = this.conditionFactory.createOneOfAttributeCondition(stringValue, (String)null, false, stringValue2);
                                                break Label_0651;
                                            }
                                            default: {
                                                o2 = this.conditionFactory.createBeginHyphenAttributeCondition(stringValue, (String)null, false, stringValue2);
                                                break Label_0651;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case 16: {
                        switch (this.nextIgnoreSpaces()) {
                            case 20: {
                                final String stringValue3 = this.scanner.getStringValue();
                                if (this.isPseudoElement(stringValue3)) {
                                    if (this.pseudoElement != null) {
                                        throw this.createCSSParseException("duplicate.pseudo.element");
                                    }
                                    this.pseudoElement = stringValue3;
                                }
                                else {
                                    o2 = this.conditionFactory.createPseudoClassCondition((String)null, stringValue3);
                                }
                                this.next();
                                break Label_0651;
                            }
                            case 52: {
                                final String stringValue4 = this.scanner.getStringValue();
                                if (this.nextIgnoreSpaces() != 20) {
                                    throw this.createCSSParseException("identifier");
                                }
                                final String stringValue5 = this.scanner.getStringValue();
                                if (this.nextIgnoreSpaces() != 15) {
                                    throw this.createCSSParseException("right.brace");
                                }
                                if (!stringValue4.equalsIgnoreCase("lang")) {
                                    throw this.createCSSParseException("pseudo.function");
                                }
                                o2 = this.conditionFactory.createLangCondition(stringValue5);
                                this.next();
                                break Label_0651;
                            }
                            default: {
                                throw this.createCSSParseException("identifier");
                            }
                        }
                        break;
                    }
                    default: {
                        this.skipSpaces();
                        if (andCondition != null) {
                            o = this.selectorFactory.createConditionalSelector((SimpleSelector)o, (Condition)andCondition);
                        }
                        return (SimpleSelector)o;
                    }
                }
            }
            if (o2 != null) {
                if (andCondition == null) {
                    andCondition = o2;
                }
                else {
                    andCondition = this.conditionFactory.createAndCondition((Condition)andCondition, (Condition)o2);
                }
            }
        }
    }
    
    protected boolean isPseudoElement(final String s) {
        switch (s.charAt(0)) {
            case 'A':
            case 'a': {
                return s.equalsIgnoreCase("after");
            }
            case 'B':
            case 'b': {
                return s.equalsIgnoreCase("before");
            }
            case 'F':
            case 'f': {
                return s.equalsIgnoreCase("first-letter") || s.equalsIgnoreCase("first-line");
            }
            default: {
                return false;
            }
        }
    }
    
    protected void parseStyleDeclaration(final boolean b) throws CSSException {
        while (true) {
            switch (this.current) {
                case 0: {
                    if (b) {
                        throw this.createCSSParseException("eof");
                    }
                }
                case 2: {
                    if (!b) {
                        throw this.createCSSParseException("eof.expected");
                    }
                    this.nextIgnoreSpaces();
                }
                case 8: {
                    this.nextIgnoreSpaces();
                    continue;
                }
                default: {
                    throw this.createCSSParseException("identifier");
                }
                case 20: {
                    final String stringValue = this.scanner.getStringValue();
                    if (this.nextIgnoreSpaces() != 16) {
                        throw this.createCSSParseException("colon");
                    }
                    this.nextIgnoreSpaces();
                    LexicalUnit expression = null;
                    try {
                        expression = this.parseExpression(false);
                    }
                    catch (CSSParseException ex) {
                        this.reportError(ex);
                    }
                    if (expression != null) {
                        boolean b2 = false;
                        if (this.current == 23) {
                            b2 = true;
                            this.nextIgnoreSpaces();
                        }
                        this.documentHandler.property(stringValue, expression, b2);
                        continue;
                    }
                    continue;
                }
            }
        }
    }
    
    protected LexicalUnit parseExpression(final boolean b) {
        Object o;
        final LexicalUnit lexicalUnit = (LexicalUnit)(o = this.parseTerm(null));
        while (true) {
            boolean b2 = false;
            switch (this.current) {
                case 6: {
                    b2 = true;
                    o = CSSLexicalUnit.createSimple((short)0, (LexicalUnit)o);
                    this.nextIgnoreSpaces();
                    break;
                }
                case 10: {
                    b2 = true;
                    o = CSSLexicalUnit.createSimple((short)4, (LexicalUnit)o);
                    this.nextIgnoreSpaces();
                    break;
                }
            }
            if (b) {
                if (this.current == 15) {
                    if (b2) {
                        throw this.createCSSParseException("token", new Object[] { new Integer(this.current) });
                    }
                    return lexicalUnit;
                }
                else {
                    o = this.parseTerm((LexicalUnit)o);
                }
            }
            else {
                switch (this.current) {
                    case 0:
                    case 2:
                    case 8:
                    case 23: {
                        if (b2) {
                            throw this.createCSSParseException("token", new Object[] { new Integer(this.current) });
                        }
                        return lexicalUnit;
                    }
                    default: {
                        o = this.parseTerm((LexicalUnit)o);
                        continue;
                    }
                }
            }
        }
    }
    
    protected LexicalUnit parseTerm(final LexicalUnit lexicalUnit) {
        boolean b = true;
        boolean b2 = false;
        switch (this.current) {
            case 5: {
                b = false;
            }
            case 4: {
                this.next();
                b2 = true;
                break;
            }
        }
        switch (this.current) {
            case 24: {
                String s = this.scanner.getStringValue();
                if (!b) {
                    s = "-" + s;
                }
                final long long1 = Long.parseLong(s);
                if (long1 >= -2147483648L && long1 <= 2147483647L) {
                    final int n = (int)long1;
                    this.nextIgnoreSpaces();
                    return (LexicalUnit)CSSLexicalUnit.createInteger(n, lexicalUnit);
                }
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)14, this.number(b), lexicalUnit);
            }
            case 54: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)14, this.number(b), lexicalUnit);
            }
            case 42: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)23, this.number(b), lexicalUnit);
            }
            case 45: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)21, this.number(b), lexicalUnit);
            }
            case 44: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)22, this.number(b), lexicalUnit);
            }
            case 46: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)17, this.number(b), lexicalUnit);
            }
            case 37: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)19, this.number(b), lexicalUnit);
            }
            case 38: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)20, this.number(b), lexicalUnit);
            }
            case 39: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)18, this.number(b), lexicalUnit);
            }
            case 36: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)15, this.number(b), lexicalUnit);
            }
            case 35: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)16, this.number(b), lexicalUnit);
            }
            case 47: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)28, this.number(b), lexicalUnit);
            }
            case 48: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)30, this.number(b), lexicalUnit);
            }
            case 49: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)29, this.number(b), lexicalUnit);
            }
            case 43: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)32, this.number(b), lexicalUnit);
            }
            case 40: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)31, this.number(b), lexicalUnit);
            }
            case 41: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)33, this.number(b), lexicalUnit);
            }
            case 50: {
                return (LexicalUnit)CSSLexicalUnit.createFloat((short)34, this.number(b), lexicalUnit);
            }
            case 34: {
                return this.dimension(b, lexicalUnit);
            }
            case 52: {
                return this.parseFunction(b, lexicalUnit);
            }
            default: {
                if (b2) {
                    throw this.createCSSParseException("token", new Object[] { new Integer(this.current) });
                }
                switch (this.current) {
                    case 19: {
                        final String stringValue = this.scanner.getStringValue();
                        this.nextIgnoreSpaces();
                        return (LexicalUnit)CSSLexicalUnit.createString((short)36, stringValue, lexicalUnit);
                    }
                    case 20: {
                        final String stringValue2 = this.scanner.getStringValue();
                        this.nextIgnoreSpaces();
                        if (stringValue2.equalsIgnoreCase("inherit")) {
                            return (LexicalUnit)CSSLexicalUnit.createSimple((short)12, lexicalUnit);
                        }
                        return (LexicalUnit)CSSLexicalUnit.createString((short)35, stringValue2, lexicalUnit);
                    }
                    case 51: {
                        final String stringValue3 = this.scanner.getStringValue();
                        this.nextIgnoreSpaces();
                        return (LexicalUnit)CSSLexicalUnit.createString((short)24, stringValue3, lexicalUnit);
                    }
                    case 27: {
                        return this.hexcolor(lexicalUnit);
                    }
                    default: {
                        throw this.createCSSParseException("token", new Object[] { new Integer(this.current) });
                    }
                }
                break;
            }
        }
    }
    
    protected LexicalUnit parseFunction(final boolean b, final LexicalUnit lexicalUnit) {
        final String stringValue = this.scanner.getStringValue();
        this.nextIgnoreSpaces();
        final LexicalUnit expression = this.parseExpression(true);
        if (this.current != 15) {
            throw this.createCSSParseException("token", new Object[] { new Integer(this.current) });
        }
        this.nextIgnoreSpaces();
        Label_1814: {
            switch (stringValue.charAt(0)) {
                case 'R':
                case 'r': {
                    if (stringValue.equalsIgnoreCase("rgb")) {
                        final LexicalUnit lexicalUnit2 = expression;
                        if (lexicalUnit2 == null) {
                            break;
                        }
                        switch (lexicalUnit2.getLexicalUnitType()) {
                            default: {
                                break Label_1814;
                            }
                            case 13:
                            case 23: {
                                final LexicalUnit nextLexicalUnit = lexicalUnit2.getNextLexicalUnit();
                                if (nextLexicalUnit == null) {
                                    break Label_1814;
                                }
                                switch (nextLexicalUnit.getLexicalUnitType()) {
                                    default: {
                                        break Label_1814;
                                    }
                                    case 0: {
                                        final LexicalUnit nextLexicalUnit2 = nextLexicalUnit.getNextLexicalUnit();
                                        if (nextLexicalUnit2 == null) {
                                            break Label_1814;
                                        }
                                        switch (nextLexicalUnit2.getLexicalUnitType()) {
                                            default: {
                                                break Label_1814;
                                            }
                                            case 13:
                                            case 23: {
                                                final LexicalUnit nextLexicalUnit3 = nextLexicalUnit2.getNextLexicalUnit();
                                                if (nextLexicalUnit3 == null) {
                                                    break Label_1814;
                                                }
                                                switch (nextLexicalUnit3.getLexicalUnitType()) {
                                                    default: {
                                                        break Label_1814;
                                                    }
                                                    case 0: {
                                                        final LexicalUnit nextLexicalUnit4 = nextLexicalUnit3.getNextLexicalUnit();
                                                        if (nextLexicalUnit4 == null) {
                                                            break Label_1814;
                                                        }
                                                        switch (nextLexicalUnit4.getLexicalUnitType()) {
                                                            default: {
                                                                break Label_1814;
                                                            }
                                                            case 13:
                                                            case 23: {
                                                                if (nextLexicalUnit4.getNextLexicalUnit() != null) {
                                                                    break Label_1814;
                                                                }
                                                                return (LexicalUnit)CSSLexicalUnit.createPredefinedFunction((short)27, expression, lexicalUnit);
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    else {
                        if (!stringValue.equalsIgnoreCase("rect")) {
                            break;
                        }
                        final LexicalUnit lexicalUnit3 = expression;
                        if (lexicalUnit3 == null) {
                            break;
                        }
                        LexicalUnit lexicalUnit4 = null;
                        switch (lexicalUnit3.getLexicalUnitType()) {
                            default: {
                                break Label_1814;
                            }
                            case 13: {
                                if (lexicalUnit3.getIntegerValue() != 0) {
                                    break Label_1814;
                                }
                                lexicalUnit4 = lexicalUnit3.getNextLexicalUnit();
                                break;
                            }
                            case 35: {
                                if (!lexicalUnit3.getStringValue().equalsIgnoreCase("auto")) {
                                    break Label_1814;
                                }
                                lexicalUnit4 = lexicalUnit3.getNextLexicalUnit();
                                break;
                            }
                            case 15:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23: {
                                lexicalUnit4 = lexicalUnit3.getNextLexicalUnit();
                                break;
                            }
                        }
                        if (lexicalUnit4 == null) {
                            break;
                        }
                        switch (lexicalUnit4.getLexicalUnitType()) {
                            default: {
                                break Label_1814;
                            }
                            case 0: {
                                final LexicalUnit nextLexicalUnit5 = lexicalUnit4.getNextLexicalUnit();
                                if (nextLexicalUnit5 == null) {
                                    break Label_1814;
                                }
                                LexicalUnit lexicalUnit5 = null;
                                switch (nextLexicalUnit5.getLexicalUnitType()) {
                                    default: {
                                        break Label_1814;
                                    }
                                    case 13: {
                                        if (nextLexicalUnit5.getIntegerValue() != 0) {
                                            break Label_1814;
                                        }
                                        lexicalUnit5 = nextLexicalUnit5.getNextLexicalUnit();
                                        break;
                                    }
                                    case 35: {
                                        if (!nextLexicalUnit5.getStringValue().equalsIgnoreCase("auto")) {
                                            break Label_1814;
                                        }
                                        lexicalUnit5 = nextLexicalUnit5.getNextLexicalUnit();
                                        break;
                                    }
                                    case 15:
                                    case 16:
                                    case 17:
                                    case 18:
                                    case 19:
                                    case 20:
                                    case 21:
                                    case 22:
                                    case 23: {
                                        lexicalUnit5 = nextLexicalUnit5.getNextLexicalUnit();
                                        break;
                                    }
                                }
                                if (lexicalUnit5 == null) {
                                    break Label_1814;
                                }
                                switch (lexicalUnit5.getLexicalUnitType()) {
                                    default: {
                                        break Label_1814;
                                    }
                                    case 0: {
                                        final LexicalUnit nextLexicalUnit6 = lexicalUnit5.getNextLexicalUnit();
                                        if (nextLexicalUnit6 == null) {
                                            break Label_1814;
                                        }
                                        LexicalUnit lexicalUnit6 = null;
                                        switch (nextLexicalUnit6.getLexicalUnitType()) {
                                            default: {
                                                break Label_1814;
                                            }
                                            case 13: {
                                                if (nextLexicalUnit6.getIntegerValue() != 0) {
                                                    break Label_1814;
                                                }
                                                lexicalUnit6 = nextLexicalUnit6.getNextLexicalUnit();
                                                break;
                                            }
                                            case 35: {
                                                if (!nextLexicalUnit6.getStringValue().equalsIgnoreCase("auto")) {
                                                    break Label_1814;
                                                }
                                                lexicalUnit6 = nextLexicalUnit6.getNextLexicalUnit();
                                                break;
                                            }
                                            case 15:
                                            case 16:
                                            case 17:
                                            case 18:
                                            case 19:
                                            case 20:
                                            case 21:
                                            case 22:
                                            case 23: {
                                                lexicalUnit6 = nextLexicalUnit6.getNextLexicalUnit();
                                                break;
                                            }
                                        }
                                        if (lexicalUnit6 == null) {
                                            break Label_1814;
                                        }
                                        switch (lexicalUnit6.getLexicalUnitType()) {
                                            default: {
                                                break Label_1814;
                                            }
                                            case 0: {
                                                final LexicalUnit nextLexicalUnit7 = lexicalUnit6.getNextLexicalUnit();
                                                if (nextLexicalUnit7 == null) {
                                                    break Label_1814;
                                                }
                                                LexicalUnit lexicalUnit7 = null;
                                                switch (nextLexicalUnit7.getLexicalUnitType()) {
                                                    default: {
                                                        break Label_1814;
                                                    }
                                                    case 13: {
                                                        if (nextLexicalUnit7.getIntegerValue() != 0) {
                                                            break Label_1814;
                                                        }
                                                        lexicalUnit7 = nextLexicalUnit7.getNextLexicalUnit();
                                                        break;
                                                    }
                                                    case 35: {
                                                        if (!nextLexicalUnit7.getStringValue().equalsIgnoreCase("auto")) {
                                                            break Label_1814;
                                                        }
                                                        lexicalUnit7 = nextLexicalUnit7.getNextLexicalUnit();
                                                        break;
                                                    }
                                                    case 15:
                                                    case 16:
                                                    case 17:
                                                    case 18:
                                                    case 19:
                                                    case 20:
                                                    case 21:
                                                    case 22:
                                                    case 23: {
                                                        lexicalUnit7 = nextLexicalUnit7.getNextLexicalUnit();
                                                        break;
                                                    }
                                                }
                                                if (lexicalUnit7 != null) {
                                                    break Label_1814;
                                                }
                                                return (LexicalUnit)CSSLexicalUnit.createPredefinedFunction((short)38, expression, lexicalUnit);
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                case 'C':
                case 'c': {
                    if (stringValue.equalsIgnoreCase("counter")) {
                        final LexicalUnit lexicalUnit8 = expression;
                        if (lexicalUnit8 == null) {
                            break;
                        }
                        switch (lexicalUnit8.getLexicalUnitType()) {
                            default: {
                                break Label_1814;
                            }
                            case 35: {
                                final LexicalUnit nextLexicalUnit8 = lexicalUnit8.getNextLexicalUnit();
                                if (nextLexicalUnit8 == null) {
                                    break Label_1814;
                                }
                                switch (nextLexicalUnit8.getLexicalUnitType()) {
                                    default: {
                                        break Label_1814;
                                    }
                                    case 0: {
                                        final LexicalUnit nextLexicalUnit9 = nextLexicalUnit8.getNextLexicalUnit();
                                        if (nextLexicalUnit9 == null) {
                                            break Label_1814;
                                        }
                                        switch (nextLexicalUnit9.getLexicalUnitType()) {
                                            default: {
                                                break Label_1814;
                                            }
                                            case 35: {
                                                if (nextLexicalUnit9.getNextLexicalUnit() != null) {
                                                    break Label_1814;
                                                }
                                                return (LexicalUnit)CSSLexicalUnit.createPredefinedFunction((short)25, expression, lexicalUnit);
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    else {
                        if (!stringValue.equalsIgnoreCase("counters")) {
                            break;
                        }
                        final LexicalUnit lexicalUnit9 = expression;
                        if (lexicalUnit9 == null) {
                            break;
                        }
                        switch (lexicalUnit9.getLexicalUnitType()) {
                            default: {
                                break Label_1814;
                            }
                            case 35: {
                                final LexicalUnit nextLexicalUnit10 = lexicalUnit9.getNextLexicalUnit();
                                if (nextLexicalUnit10 == null) {
                                    break Label_1814;
                                }
                                switch (nextLexicalUnit10.getLexicalUnitType()) {
                                    default: {
                                        break Label_1814;
                                    }
                                    case 0: {
                                        final LexicalUnit nextLexicalUnit11 = nextLexicalUnit10.getNextLexicalUnit();
                                        if (nextLexicalUnit11 == null) {
                                            break Label_1814;
                                        }
                                        switch (nextLexicalUnit11.getLexicalUnitType()) {
                                            default: {
                                                break Label_1814;
                                            }
                                            case 36: {
                                                final LexicalUnit nextLexicalUnit12 = nextLexicalUnit11.getNextLexicalUnit();
                                                if (nextLexicalUnit12 == null) {
                                                    break Label_1814;
                                                }
                                                switch (nextLexicalUnit12.getLexicalUnitType()) {
                                                    default: {
                                                        break Label_1814;
                                                    }
                                                    case 0: {
                                                        final LexicalUnit nextLexicalUnit13 = nextLexicalUnit12.getNextLexicalUnit();
                                                        if (nextLexicalUnit13 == null) {
                                                            break Label_1814;
                                                        }
                                                        switch (nextLexicalUnit13.getLexicalUnitType()) {
                                                            default: {
                                                                break Label_1814;
                                                            }
                                                            case 35: {
                                                                if (nextLexicalUnit13.getNextLexicalUnit() != null) {
                                                                    break Label_1814;
                                                                }
                                                                return (LexicalUnit)CSSLexicalUnit.createPredefinedFunction((short)26, expression, lexicalUnit);
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                case 'A':
                case 'a': {
                    if (!stringValue.equalsIgnoreCase("attr")) {
                        break;
                    }
                    final LexicalUnit lexicalUnit10 = expression;
                    if (lexicalUnit10 == null) {
                        break;
                    }
                    switch (lexicalUnit10.getLexicalUnitType()) {
                        default: {
                            break Label_1814;
                        }
                        case 35: {
                            if (lexicalUnit10.getNextLexicalUnit() != null) {
                                break Label_1814;
                            }
                            return (LexicalUnit)CSSLexicalUnit.createString((short)37, expression.getStringValue(), lexicalUnit);
                        }
                    }
                    break;
                }
            }
        }
        return (LexicalUnit)CSSLexicalUnit.createFunction(stringValue, expression, lexicalUnit);
    }
    
    protected LexicalUnit hexcolor(final LexicalUnit lexicalUnit) {
        final String stringValue = this.scanner.getStringValue();
        CSSLexicalUnit cssLexicalUnit = null;
        switch (stringValue.length()) {
            case 3: {
                final char lowerCase = Character.toLowerCase(stringValue.charAt(0));
                final char lowerCase2 = Character.toLowerCase(stringValue.charAt(1));
                final char lowerCase3 = Character.toLowerCase(stringValue.charAt(2));
                if (!ScannerUtilities.isCSSHexadecimalCharacter(lowerCase) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase2) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase3)) {
                    throw this.createCSSParseException("rgb.color", new Object[] { stringValue });
                }
                final int n2;
                final int n = (n2 = ((lowerCase >= '0' && lowerCase <= '9') ? (lowerCase - '0') : (lowerCase - 'a' + 10))) | n2 << 4;
                final int n4;
                final int n3 = (n4 = ((lowerCase2 >= '0' && lowerCase2 <= '9') ? (lowerCase2 - '0') : (lowerCase2 - 'a' + 10))) | n4 << 4;
                final int n6;
                final int n5 = (n6 = ((lowerCase3 >= '0' && lowerCase3 <= '9') ? (lowerCase3 - '0') : (lowerCase3 - 'a' + 10))) | n6 << 4;
                cssLexicalUnit = CSSLexicalUnit.createInteger(n, null);
                CSSLexicalUnit.createInteger(n5, (LexicalUnit)CSSLexicalUnit.createSimple((short)0, (LexicalUnit)CSSLexicalUnit.createInteger(n3, (LexicalUnit)CSSLexicalUnit.createSimple((short)0, (LexicalUnit)cssLexicalUnit))));
                break;
            }
            case 6: {
                final char lowerCase4 = Character.toLowerCase(stringValue.charAt(0));
                final char lowerCase5 = Character.toLowerCase(stringValue.charAt(1));
                final char lowerCase6 = Character.toLowerCase(stringValue.charAt(2));
                final char lowerCase7 = Character.toLowerCase(stringValue.charAt(3));
                final char lowerCase8 = Character.toLowerCase(stringValue.charAt(4));
                final char lowerCase9 = Character.toLowerCase(stringValue.charAt(5));
                if (!ScannerUtilities.isCSSHexadecimalCharacter(lowerCase4) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase5) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase6) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase7) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase8) || !ScannerUtilities.isCSSHexadecimalCharacter(lowerCase9)) {
                    throw this.createCSSParseException("rgb.color");
                }
                final int n7 = ((lowerCase4 >= '0' && lowerCase4 <= '9') ? (lowerCase4 - '0') : (lowerCase4 - 'a' + 10)) << 4 | ((lowerCase5 >= '0' && lowerCase5 <= '9') ? (lowerCase5 - '0') : (lowerCase5 - 'a' + 10));
                final int n8 = ((lowerCase6 >= '0' && lowerCase6 <= '9') ? (lowerCase6 - '0') : (lowerCase6 - 'a' + 10)) << 4 | ((lowerCase7 >= '0' && lowerCase7 <= '9') ? (lowerCase7 - '0') : (lowerCase7 - 'a' + 10));
                final int n9 = ((lowerCase8 >= '0' && lowerCase8 <= '9') ? (lowerCase8 - '0') : (lowerCase8 - 'a' + 10)) << 4 | ((lowerCase9 >= '0' && lowerCase9 <= '9') ? (lowerCase9 - '0') : (lowerCase9 - 'a' + 10));
                cssLexicalUnit = CSSLexicalUnit.createInteger(n7, null);
                CSSLexicalUnit.createInteger(n9, (LexicalUnit)CSSLexicalUnit.createSimple((short)0, (LexicalUnit)CSSLexicalUnit.createInteger(n8, (LexicalUnit)CSSLexicalUnit.createSimple((short)0, (LexicalUnit)cssLexicalUnit))));
                break;
            }
            default: {
                throw this.createCSSParseException("rgb.color", new Object[] { stringValue });
            }
        }
        this.nextIgnoreSpaces();
        return (LexicalUnit)CSSLexicalUnit.createPredefinedFunction((short)27, (LexicalUnit)cssLexicalUnit, lexicalUnit);
    }
    
    protected Scanner createScanner(final InputSource inputSource) {
        this.documentURI = inputSource.getURI();
        if (this.documentURI == null) {
            this.documentURI = "";
        }
        final Reader characterStream = inputSource.getCharacterStream();
        if (characterStream != null) {
            return new Scanner(characterStream);
        }
        final InputStream byteStream = inputSource.getByteStream();
        if (byteStream != null) {
            return new Scanner(byteStream, inputSource.getEncoding());
        }
        final String uri = inputSource.getURI();
        if (uri == null) {
            throw new CSSException(this.formatMessage("empty.source", null));
        }
        try {
            return new Scanner(new ParsedURL(uri).openStreamRaw("text/css"), inputSource.getEncoding());
        }
        catch (IOException ex) {
            throw new CSSException((Exception)ex);
        }
    }
    
    protected int skipSpaces() {
        int i;
        for (i = this.scanner.getType(); i == 17; i = this.next()) {}
        return i;
    }
    
    protected int skipSpacesAndCDOCDC() {
        while (true) {
            switch (this.current) {
                default: {
                    return this.current;
                }
                case 17:
                case 18:
                case 21:
                case 22: {
                    this.scanner.clearBuffer();
                    this.next();
                    continue;
                }
            }
        }
    }
    
    protected float number(final boolean b) {
        try {
            final float n = b ? 1.0f : -1.0f;
            final String stringValue = this.scanner.getStringValue();
            this.nextIgnoreSpaces();
            return n * Float.parseFloat(stringValue);
        }
        catch (NumberFormatException ex) {
            throw this.createCSSParseException("number.format");
        }
    }
    
    protected LexicalUnit dimension(final boolean b, final LexicalUnit lexicalUnit) {
        try {
            final float n = b ? 1.0f : -1.0f;
            final String stringValue = this.scanner.getStringValue();
            int i = 0;
        Label_0113:
            while (i < stringValue.length()) {
                switch (stringValue.charAt(i)) {
                    default: {
                        break Label_0113;
                    }
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': {
                        ++i;
                        continue;
                    }
                }
            }
            this.nextIgnoreSpaces();
            return (LexicalUnit)CSSLexicalUnit.createDimension(n * Float.parseFloat(stringValue.substring(0, i)), stringValue.substring(i), lexicalUnit);
        }
        catch (NumberFormatException ex) {
            throw this.createCSSParseException("number.format");
        }
    }
    
    protected int next() {
        try {
            while (true) {
                this.scanner.clearBuffer();
                this.current = this.scanner.next();
                if (this.current != 18) {
                    break;
                }
                this.documentHandler.comment(this.scanner.getStringValue());
            }
            return this.current;
        }
        catch (ParseException ex) {
            this.reportError(ex.getMessage());
            return this.current;
        }
    }
    
    protected int nextIgnoreSpaces() {
        try {
            while (true) {
                this.scanner.clearBuffer();
                switch (this.current = this.scanner.next()) {
                    case 18: {
                        this.documentHandler.comment(this.scanner.getStringValue());
                        continue;
                    }
                    default: {
                        return this.current;
                    }
                    case 17: {
                        continue;
                    }
                }
            }
        }
        catch (ParseException ex) {
            this.errorHandler.error(this.createCSSParseException(ex.getMessage()));
            return this.current;
        }
    }
    
    protected void reportError(final String s) {
        this.reportError(s, null);
    }
    
    protected void reportError(final String s, final Object[] array) {
        this.reportError(this.createCSSParseException(s, array));
    }
    
    protected void reportError(final CSSParseException ex) {
        this.errorHandler.error(ex);
        int n = 1;
        while (true) {
            switch (this.current) {
                case 0: {
                    return;
                }
                case 2:
                case 8: {
                    if (--n == 0) {
                        this.nextIgnoreSpaces();
                        return;
                    }
                }
                case 1: {
                    ++n;
                    break;
                }
            }
            this.nextIgnoreSpaces();
        }
    }
    
    protected CSSParseException createCSSParseException(final String s) {
        return this.createCSSParseException(s, null);
    }
    
    protected CSSParseException createCSSParseException(final String s, final Object[] array) {
        return new CSSParseException(this.formatMessage(s, array), this.documentURI, this.scanner.getLine(), this.scanner.getColumn());
    }
    
    public void parseStyleDeclaration(final String s) throws CSSException, IOException {
        this.scanner = new Scanner(s);
        this.parseStyleDeclarationInternal();
    }
    
    public void parseRule(final String s) throws CSSException, IOException {
        this.scanner = new Scanner(s);
        this.parseRuleInternal();
    }
    
    public SelectorList parseSelectors(final String s) throws CSSException, IOException {
        this.scanner = new Scanner(s);
        return this.parseSelectorsInternal();
    }
    
    public LexicalUnit parsePropertyValue(final String s) throws CSSException, IOException {
        this.scanner = new Scanner(s);
        return this.parsePropertyValueInternal();
    }
    
    public boolean parsePriority(final String s) throws CSSException, IOException {
        this.scanner = new Scanner(s);
        return this.parsePriorityInternal();
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
}
