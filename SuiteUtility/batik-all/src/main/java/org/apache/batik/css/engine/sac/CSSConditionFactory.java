// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.PositionalCondition;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionFactory;

public class CSSConditionFactory implements ConditionFactory
{
    protected String classNamespaceURI;
    protected String classLocalName;
    protected String idNamespaceURI;
    protected String idLocalName;
    
    public CSSConditionFactory(final String classNamespaceURI, final String classLocalName, final String idNamespaceURI, final String idLocalName) {
        this.classNamespaceURI = classNamespaceURI;
        this.classLocalName = classLocalName;
        this.idNamespaceURI = idNamespaceURI;
        this.idLocalName = idLocalName;
    }
    
    public CombinatorCondition createAndCondition(final Condition condition, final Condition condition2) throws CSSException {
        return (CombinatorCondition)new CSSAndCondition(condition, condition2);
    }
    
    public CombinatorCondition createOrCondition(final Condition condition, final Condition condition2) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public NegativeCondition createNegativeCondition(final Condition condition) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public PositionalCondition createPositionalCondition(final int n, final boolean b, final boolean b2) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public AttributeCondition createAttributeCondition(final String s, final String s2, final boolean b, final String s3) throws CSSException {
        return (AttributeCondition)new CSSAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createIdCondition(final String s) throws CSSException {
        return (AttributeCondition)new CSSIdCondition(this.idNamespaceURI, this.idLocalName, s);
    }
    
    public LangCondition createLangCondition(final String s) throws CSSException {
        return (LangCondition)new CSSLangCondition(s);
    }
    
    public AttributeCondition createOneOfAttributeCondition(final String s, final String s2, final boolean b, final String s3) throws CSSException {
        return (AttributeCondition)new CSSOneOfAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createBeginHyphenAttributeCondition(final String s, final String s2, final boolean b, final String s3) throws CSSException {
        return (AttributeCondition)new CSSBeginHyphenAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createClassCondition(final String s, final String s2) throws CSSException {
        return (AttributeCondition)new CSSClassCondition(this.classLocalName, this.classNamespaceURI, s2);
    }
    
    public AttributeCondition createPseudoClassCondition(final String s, final String s2) throws CSSException {
        return (AttributeCondition)new CSSPseudoClassCondition(s, s2);
    }
    
    public Condition createOnlyChildCondition() throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public Condition createOnlyTypeCondition() throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public ContentCondition createContentCondition(final String s) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
}
