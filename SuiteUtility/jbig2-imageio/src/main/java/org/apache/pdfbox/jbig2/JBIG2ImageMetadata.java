// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import javax.imageio.metadata.IIOMetadataNode;
import org.w3c.dom.Node;
import javax.imageio.metadata.IIOMetadata;

public class JBIG2ImageMetadata extends IIOMetadata
{
    static final String IMAGE_METADATA_FORMAT_NAME = "jbig2";
    private final JBIG2Page page;
    
    public JBIG2ImageMetadata(final JBIG2Page page) {
        super(true, "jbig2", JBIG2ImageMetadataFormat.class.getName(), null, null);
        if (page == null) {
            throw new IllegalArgumentException("page must not be null");
        }
        this.page = page;
    }
    
    @Override
    public boolean isReadOnly() {
        return true;
    }
    
    @Override
    public Node getAsTree(final String s) {
        if (s.equals(this.nativeMetadataFormatName)) {
            return this.getNativeTree();
        }
        if (s.equals("javax_imageio_1.0")) {
            return this.getStandardTree();
        }
        throw new IllegalArgumentException("Not a recognized format!");
    }
    
    private Node getNativeTree() {
        final IIOMetadataNode iioMetadataNode = new IIOMetadataNode(this.nativeMetadataFormatName);
        iioMetadataNode.appendChild(this.getStandardDimensionNode());
        return iioMetadataNode;
    }
    
    public IIOMetadataNode getStandardDimensionNode() {
        final IIOMetadataNode iioMetadataNode = new IIOMetadataNode("Dimension");
        final IIOMetadataNode newChild = new IIOMetadataNode("PixelAspectRatio");
        newChild.setAttribute("value", "1.0");
        iioMetadataNode.appendChild(newChild);
        final IIOMetadataNode newChild2 = new IIOMetadataNode("ImageOrientation");
        newChild2.setAttribute("value", "Normal");
        iioMetadataNode.appendChild(newChild2);
        if (this.page.getResolutionX() != 0) {
            final String string = Float.toString(25.4f / (this.page.getResolutionX() / 39.3701f));
            final IIOMetadataNode newChild3 = new IIOMetadataNode("HorizontalPixelSize");
            newChild3.setAttribute("value", string);
            iioMetadataNode.appendChild(newChild3);
        }
        if (this.page.getResolutionY() != 0) {
            final String string2 = Float.toString(25.4f / (this.page.getResolutionY() / 39.3701f));
            final IIOMetadataNode newChild4 = new IIOMetadataNode("VerticalPixelSize");
            newChild4.setAttribute("value", string2);
            iioMetadataNode.appendChild(newChild4);
        }
        return iioMetadataNode;
    }
    
    @Override
    public void mergeTree(final String s, final Node node) {
        throw new IllegalStateException("Metadata is read-only!");
    }
    
    @Override
    public void reset() {
        throw new IllegalStateException("Metadata is read-only!");
    }
}
