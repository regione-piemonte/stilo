// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;
import org.apache.batik.util.ParsedURL;

public abstract class AbstractValueFactory
{
    public abstract String getPropertyName();
    
    protected static String resolveURI(final ParsedURL parsedURL, final String s) {
        return new ParsedURL(parsedURL, s).toString();
    }
    
    protected DOMException createInvalidIdentifierDOMException(final String s) {
        return new DOMException((short)12, Messages.formatMessage("invalid.identifier", new Object[] { this.getPropertyName(), s }));
    }
    
    protected DOMException createInvalidLexicalUnitDOMException(final short value) {
        return new DOMException((short)9, Messages.formatMessage("invalid.lexical.unit", new Object[] { this.getPropertyName(), new Integer(value) }));
    }
    
    protected DOMException createInvalidFloatTypeDOMException(final short value) {
        return new DOMException((short)15, Messages.formatMessage("invalid.float.type", new Object[] { this.getPropertyName(), new Integer(value) }));
    }
    
    protected DOMException createInvalidFloatValueDOMException(final float value) {
        return new DOMException((short)15, Messages.formatMessage("invalid.float.value", new Object[] { this.getPropertyName(), new Float(value) }));
    }
    
    protected DOMException createInvalidStringTypeDOMException(final short value) {
        return new DOMException((short)15, Messages.formatMessage("invalid.string.type", new Object[] { this.getPropertyName(), new Integer(value) }));
    }
    
    protected DOMException createMalformedLexicalUnitDOMException() {
        return new DOMException((short)15, Messages.formatMessage("malformed.lexical.unit", new Object[] { this.getPropertyName() }));
    }
    
    protected DOMException createDOMException() {
        return new DOMException((short)9, Messages.formatMessage("invalid.access", new Object[] { this.getPropertyName() }));
    }
}
