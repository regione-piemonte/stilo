// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script;

import java.util.Iterator;
import org.apache.batik.util.Service;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.w3c.dom.Document;
import java.util.HashMap;
import java.util.Map;

public class InterpreterPool
{
    public static final String BIND_NAME_DOCUMENT = "document";
    protected static Map defaultFactories;
    protected Map factories;
    
    public InterpreterPool() {
        (this.factories = new HashMap(7)).putAll(InterpreterPool.defaultFactories);
    }
    
    public Interpreter createInterpreter(final Document document, final String s) {
        final InterpreterFactory interpreterFactory = this.factories.get(s);
        if (interpreterFactory == null) {
            return null;
        }
        Interpreter interpreter = null;
        final SVGOMDocument svgomDocument = (SVGOMDocument)document;
        try {
            interpreter = interpreterFactory.createInterpreter(new URL(svgomDocument.getDocumentURI()), svgomDocument.isSVG12());
        }
        catch (MalformedURLException ex) {}
        if (interpreter == null) {
            return null;
        }
        if (document != null) {
            interpreter.bindObject("document", document);
        }
        return interpreter;
    }
    
    public void putInterpreterFactory(final String s, final InterpreterFactory interpreterFactory) {
        this.factories.put(s, interpreterFactory);
    }
    
    public void removeInterpreterFactory(final String s) {
        this.factories.remove(s);
    }
    
    static {
        InterpreterPool.defaultFactories = new HashMap(7);
        final Iterator providers = Service.providers(InterpreterFactory.class);
        while (providers.hasNext()) {
            final InterpreterFactory interpreterFactory = providers.next();
            final String[] mimeTypes = interpreterFactory.getMimeTypes();
            for (int i = 0; i < mimeTypes.length; ++i) {
                InterpreterPool.defaultFactories.put(mimeTypes[i], interpreterFactory);
            }
        }
    }
}
