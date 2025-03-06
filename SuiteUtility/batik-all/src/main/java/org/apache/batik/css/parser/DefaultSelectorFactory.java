// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.ProcessingInstructionSelector;
import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.NegativeSelector;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.SelectorFactory;

public class DefaultSelectorFactory implements SelectorFactory
{
    public static final SelectorFactory INSTANCE;
    
    protected DefaultSelectorFactory() {
    }
    
    public ConditionalSelector createConditionalSelector(final SimpleSelector simpleSelector, final Condition condition) throws CSSException {
        return (ConditionalSelector)new DefaultConditionalSelector(simpleSelector, condition);
    }
    
    public SimpleSelector createAnyNodeSelector() throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public SimpleSelector createRootNodeSelector() throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public NegativeSelector createNegativeSelector(final SimpleSelector simpleSelector) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public ElementSelector createElementSelector(final String s, final String s2) throws CSSException {
        return (ElementSelector)new DefaultElementSelector(s, s2);
    }
    
    public CharacterDataSelector createTextNodeSelector(final String s) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public CharacterDataSelector createCDataSectionSelector(final String s) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public ProcessingInstructionSelector createProcessingInstructionSelector(final String s, final String s2) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public CharacterDataSelector createCommentSelector(final String s) throws CSSException {
        throw new CSSException("Not implemented in CSS2");
    }
    
    public ElementSelector createPseudoElementSelector(final String s, final String s2) throws CSSException {
        return (ElementSelector)new DefaultPseudoElementSelector(s, s2);
    }
    
    public DescendantSelector createDescendantSelector(final Selector selector, final SimpleSelector simpleSelector) throws CSSException {
        return (DescendantSelector)new DefaultDescendantSelector(selector, simpleSelector);
    }
    
    public DescendantSelector createChildSelector(final Selector selector, final SimpleSelector simpleSelector) throws CSSException {
        return (DescendantSelector)new DefaultChildSelector(selector, simpleSelector);
    }
    
    public SiblingSelector createDirectAdjacentSelector(final short n, final Selector selector, final SimpleSelector simpleSelector) throws CSSException {
        return (SiblingSelector)new DefaultDirectAdjacentSelector(n, selector, simpleSelector);
    }
    
    static {
        INSTANCE = (SelectorFactory)new DefaultSelectorFactory();
    }
}
