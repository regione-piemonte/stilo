
package it.eng.client.applet.operation.xml;



import java.io.FileInputStream;



import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
//import org.apache.xml.utils.URI;
import org.w3c.dom.Attr;


/**
 * This class helps us home users to resolve http URIs without a network
 * connection
 *
 * @author $Author: blautenb $
 */
public class URIOfflineResolver extends ResourceResolverSpi {
  //private static Logger log = Logger.getLogger(URIOfflineResolver.class);
   /**
    * Method engineResolve
    *
    * @param uri
    * @param BaseURI
    *
    * @throws ResourceResolverException
    */
   @Override
  public XMLSignatureInput engineResolve(Attr uri, String BaseURI)  throws ResourceResolverException {

      try {
         String URI = uri.getNodeValue();

         if (URIOfflineResolver._uriMap.containsKey(URI)) {
            String newURI = (String) URIOfflineResolver._uriMap.get(URI);

            //log.debug("Mapped " + URI + " to " + newURI);

            InputStream is = new FileInputStream(newURI);

           // log.debug("Available bytes = " + is.available());

            XMLSignatureInput result = new XMLSignatureInput(is);

            // XMLSignatureInput result = new XMLSignatureInput(inputStream);
            result.setSourceURI(URI);
            result.setMIMEType((String) URIOfflineResolver._mimeMap.get(URI));

            return result;
         } else {
            Object exArgs[] = {
               "The URI " + URI + " is not configured for offline work" };

            throw new ResourceResolverException("generic.EmptyMessage", exArgs, uri, BaseURI);
         }
      } catch (IOException ex) {
         throw new ResourceResolverException("generic.EmptyMessage", ex, uri, BaseURI);
      }
   }

   /**
    * We resolve http URIs <I>without</I> fragment...
    *
    * @param uri
    * @param BaseURI
    *
    */
   @Override
  public boolean engineCanResolve(Attr uri, String BaseURI) {

      String uriNodeValue = uri.getNodeValue();

      if (uriNodeValue.equals("") || uriNodeValue.startsWith("#")) {
         return false;
      }

//      try {
//         URI uriNew = new URI(new URI(BaseURI), uri.getNodeValue());
//
//         if (uriNew.getScheme().equals("http")) {
//            log.debug("I state that I can resolve " + uriNew.toString());
//
//            return true;
//         }
//
//         log.debug("I state that I can't resolve " + uriNew.toString());
//      } catch (URI.MalformedURIException ex) {}

      return false;
   }

   /** Field _uriMap */
   static Map _uriMap = null;

   /** Field _mimeMap */
   static Map _mimeMap = null;

   /**
    * Method register
    *
    * @param URI
    * @param filename
    * @param MIME
    */
   private static void register(String URI, String filename, String MIME) {
      URIOfflineResolver._uriMap.put(URI, filename);
      URIOfflineResolver._mimeMap.put(URI, MIME);
   }

   static {
      org.apache.xml.security.Init.init();

      URIOfflineResolver._uriMap = new HashMap();
      URIOfflineResolver._mimeMap = new HashMap();

      URIOfflineResolver.register("http://www.w3.org/TR/xml-stylesheet",
                               "data/org/w3c/www/TR/xml-stylesheet.html",
                               "text/html");
      URIOfflineResolver.register("http://www.w3.org/TR/2000/REC-xml-20001006",
                               "data/org/w3c/www/TR/2000/REC-xml-20001006",
                               "text/xml");
      URIOfflineResolver.register("http://www.nue.et-inf.uni-siegen.de/index.html",
                               "data/org/apache/xml/security/temp/nuehomepage",
                               "text/html");
      URIOfflineResolver.register(
         "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/id2.xml",
         "data/org/apache/xml/security/temp/id2.xml", "text/xml");
      URIOfflineResolver.register(
         "http://xmldsig.pothole.com/xml-stylesheet.txt",
         "data/com/pothole/xmldsig/xml-stylesheet.txt", "text/xml");
      URIOfflineResolver.register(
         "http://www.w3.org/Signature/2002/04/xml-stylesheet.b64",
         "data/ie/baltimore/merlin-examples/merlin-xmldsig-twenty-three/xml-stylesheet.b64", "text/plain");
   }
}
