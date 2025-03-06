// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.PositionalCondition;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionFactory;

public class DefaultConditionFactory implements ConditionFactory
{
    public static final ConditionFactory INSTANCE;
    
    protected DefaultConditionFactory() {
    }
    
    public CombinatorCondition createAndCondition(final Condition condition, final Condition condition2) throws CSSException {
        return (CombinatorCondition)new DefaultAndCondition(condition, condition2);
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
        return (AttributeCondition)new DefaultAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createIdCondition(final String s) throws CSSException {
        return (AttributeCondition)new DefaultIdCondition(s);
    }
    
    public LangCondition createLangCondition(final String s) throws CSSException {
        return (LangCondition)new DefaultLangCondition(s);
    }
    
    public AttributeCondition createOneOfAttributeCondition(final String s, final String s2, final boolean b, final String s3) throws CSSException {
        return (AttributeCondition)new DefaultOneOfAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createBeginHyphenAttributeCondition(final String s, final String s2, final boolean b, final String s3) throws CSSException {
        return (AttributeCondition)new DefaultBeginHyphenAttributeCondition(s, s2, b, s3);
    }
    
    public AttributeCondition createClassCondition(final String s, final String s2) throws CSSException {
        return (AttributeCondition)new DefaultClassCondition(s, s2);
    }
    
    public AttributeCondition createPseudoClassCondition(final String s, final String s2) throws CSSException {
        return (AttributeCondition)new DefaultPseudoClassCondition(s, s2);
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
    
    static {
        INSTANCE = (ConditionFactory)new DefaultConditionFactory();
    }
}
