// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import java.util.ListIterator;
import org.apache.batik.util.Service;
import org.w3c.dom.Document;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Element;
import org.w3c.dom.css.ViewCSS;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.css.parser.ExtendedParserWrapper;
import org.w3c.dom.DOMException;
import org.w3c.css.sac.Parser;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.css.engine.value.ShorthandManager;
import java.util.LinkedList;
import org.apache.batik.css.engine.value.ValueManager;
import java.util.MissingResourceException;
import java.util.Locale;
import java.util.Iterator;
import org.apache.batik.i18n.LocalizableSupport;
import java.util.List;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.i18n.Localizable;
import org.w3c.dom.css.DOMImplementationCSS;

public abstract class ExtensibleDOMImplementation extends AbstractDOMImplementation implements DOMImplementationCSS, StyleSheetFactory, Localizable
{
    protected DoublyIndexedTable customFactories;
    protected List customValueManagers;
    protected List customShorthandManagers;
    protected static final String RESOURCES = "org.apache.batik.dom.resources.Messages";
    protected LocalizableSupport localizableSupport;
    protected static List extensions;
    
    public ExtensibleDOMImplementation() {
        this.initLocalizable();
        final Iterator<DomExtension> iterator = getDomExtensions().iterator();
        while (iterator.hasNext()) {
            iterator.next().registerTags(this);
        }
    }
    
    public void setLocale(final Locale locale) {
        this.localizableSupport.setLocale(locale);
    }
    
    public Locale getLocale() {
        return this.localizableSupport.getLocale();
    }
    
    protected void initLocalizable() {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
    }
    
    public String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return this.localizableSupport.formatMessage(s, array);
    }
    
    public void registerCustomElementFactory(final String s, final String s2, final ElementFactory elementFactory) {
        if (this.customFactories == null) {
            this.customFactories = new DoublyIndexedTable();
        }
        this.customFactories.put(s, s2, elementFactory);
    }
    
    public void registerCustomCSSValueManager(final ValueManager valueManager) {
        if (this.customValueManagers == null) {
            this.customValueManagers = new LinkedList();
        }
        this.customValueManagers.add(valueManager);
    }
    
    public void registerCustomCSSShorthandManager(final ShorthandManager shorthandManager) {
        if (this.customShorthandManagers == null) {
            this.customShorthandManagers = new LinkedList();
        }
        this.customShorthandManagers.add(shorthandManager);
    }
    
    public CSSEngine createCSSEngine(final AbstractStylableDocument abstractStylableDocument, final CSSContext cssContext) {
        final String cssParserClassName = XMLResourceDescriptor.getCSSParserClassName();
        Parser parser;
        try {
            parser = (Parser)Class.forName(cssParserClassName).newInstance();
        }
        catch (ClassNotFoundException ex) {
            throw new DOMException((short)15, this.formatMessage("css.parser.class", new Object[] { cssParserClassName }));
        }
        catch (InstantiationException ex2) {
            throw new DOMException((short)15, this.formatMessage("css.parser.creation", new Object[] { cssParserClassName }));
        }
        catch (IllegalAccessException ex3) {
            throw new DOMException((short)15, this.formatMessage("css.parser.access", new Object[] { cssParserClassName }));
        }
        final ExtendedParser wrap = ExtendedParserWrapper.wrap(parser);
        ValueManager[] array;
        if (this.customValueManagers == null) {
            array = new ValueManager[0];
        }
        else {
            array = new ValueManager[this.customValueManagers.size()];
            final Iterator<ValueManager> iterator = this.customValueManagers.iterator();
            int n = 0;
            while (iterator.hasNext()) {
                array[n++] = iterator.next();
            }
        }
        ShorthandManager[] array2;
        if (this.customShorthandManagers == null) {
            array2 = new ShorthandManager[0];
        }
        else {
            array2 = new ShorthandManager[this.customShorthandManagers.size()];
            final Iterator<ShorthandManager> iterator2 = this.customShorthandManagers.iterator();
            int n2 = 0;
            while (iterator2.hasNext()) {
                array2[n2++] = iterator2.next();
            }
        }
        final CSSEngine cssEngine = this.createCSSEngine(abstractStylableDocument, cssContext, wrap, array, array2);
        abstractStylableDocument.setCSSEngine(cssEngine);
        return cssEngine;
    }
    
    public abstract CSSEngine createCSSEngine(final AbstractStylableDocument p0, final CSSContext p1, final ExtendedParser p2, final ValueManager[] p3, final ShorthandManager[] p4);
    
    public abstract ViewCSS createViewCSS(final AbstractStylableDocument p0);
    
    public Element createElementNS(final AbstractDocument abstractDocument, String s, final String s2) {
        if (s != null && s.length() == 0) {
            s = null;
        }
        if (s == null) {
            return new GenericElement(s2.intern(), abstractDocument);
        }
        if (this.customFactories != null) {
            final ElementFactory elementFactory = (ElementFactory)this.customFactories.get(s, DOMUtilities.getLocalName(s2));
            if (elementFactory != null) {
                return elementFactory.create(DOMUtilities.getPrefix(s2), abstractDocument);
            }
        }
        return new GenericElementNS(s.intern(), s2.intern(), abstractDocument);
    }
    
    protected static synchronized List getDomExtensions() {
        if (ExtensibleDOMImplementation.extensions != null) {
            return ExtensibleDOMImplementation.extensions;
        }
        ExtensibleDOMImplementation.extensions = new LinkedList();
        final Iterator providers = Service.providers(DomExtension.class);
    Label_0045:
        while (providers.hasNext()) {
            final DomExtension domExtension = providers.next();
            final float priority = domExtension.getPriority();
            final ListIterator<DomExtension> listIterator = (ListIterator<DomExtension>)ExtensibleDOMImplementation.extensions.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.next().getPriority() > priority) {
                    listIterator.previous();
                    listIterator.add(domExtension);
                    continue Label_0045;
                }
            }
            listIterator.add(domExtension);
        }
        return ExtensibleDOMImplementation.extensions;
    }
    
    static {
        ExtensibleDOMImplementation.extensions = null;
    }
    
    public interface ElementFactory
    {
        Element create(final String p0, final Document p1);
    }
}
