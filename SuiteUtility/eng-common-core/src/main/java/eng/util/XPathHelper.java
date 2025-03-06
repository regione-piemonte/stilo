// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import org.xml.sax.SAXException;
import org.apache.xalan.xpath.XObject;
import org.apache.xalan.xpath.XPath;
import org.apache.xalan.xpath.xml.PrefixResolverDefault;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.apache.xalan.xpath.XPathSupport;
import org.apache.xalan.xpath.XPathProcessorImpl;
import org.apache.xalan.xpath.xml.XMLParserLiaisonDefault;
import org.apache.xalan.xpath.xml.PrefixResolver;
import org.apache.xalan.xpath.XPathProcessor;
import org.apache.xalan.xpath.xml.XMLParserLiaison;

public class XPathHelper
{
    XMLParserLiaison xpathSupport;
    XPathProcessor xpathParser;
    PrefixResolver prefixResolver;
    
    public XPathHelper() {
        this.xpathSupport = null;
        this.xpathParser = null;
        this.prefixResolver = null;
        this.xpathSupport = (XMLParserLiaison)new XMLParserLiaisonDefault();
        this.xpathParser = (XPathProcessor)new XPathProcessorImpl((XPathSupport)this.xpathSupport);
    }
    
    public NodeList processXPath(final String xpath, final Node target) throws SAXException {
        this.prefixResolver = (PrefixResolver)new PrefixResolverDefault(target);
        final XPath xp = new XPath();
        this.xpathParser.initXPath(xp, xpath, this.prefixResolver);
        final XObject list = xp.execute((XPathSupport)this.xpathSupport, target, this.prefixResolver);
        return list.nodeset();
    }
    
    public String processXPathStringValue(final String xpath, final Node target) throws SAXException {
        this.prefixResolver = (PrefixResolver)new PrefixResolverDefault(target);
        final XPath xp = new XPath();
        this.xpathParser.initXPath(xp, xpath, this.prefixResolver);
        final XObject list = xp.execute((XPathSupport)this.xpathSupport, target, this.prefixResolver);
        return list.str();
    }
    
    public double processXPathNumValue(final String xpath, final Node target) throws SAXException {
        this.prefixResolver = (PrefixResolver)new PrefixResolverDefault(target);
        final XPath xp = new XPath();
        this.xpathParser.initXPath(xp, xpath, this.prefixResolver);
        final XObject list = xp.execute((XPathSupport)this.xpathSupport, target, this.prefixResolver);
        return list.num();
    }
}
