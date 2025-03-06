// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.OutputStream;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Node;
import javax.imageio.metadata.IIOMetadata;

public class ImageIODebugUtil
{
    public static void dumpMetadata(final IIOMetadata iioMetadata) {
        dumpNode(iioMetadata.getAsTree(iioMetadata.getNativeMetadataFormatName()));
    }
    
    public static void dumpNode(final Node n) {
        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(n), new StreamResult(System.out));
            System.out.println();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
