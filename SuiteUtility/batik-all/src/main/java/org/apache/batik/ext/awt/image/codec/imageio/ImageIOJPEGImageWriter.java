// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.ImageWriteParam;
import java.awt.image.RenderedImage;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import org.w3c.dom.Node;
import javax.imageio.metadata.IIOMetadataNode;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import javax.imageio.metadata.IIOMetadata;

public class ImageIOJPEGImageWriter extends ImageIOImageWriter
{
    private static final String JPEG_NATIVE_FORMAT = "javax_imageio_jpeg_image_1.0";
    
    public ImageIOJPEGImageWriter() {
        super("image/jpeg");
    }
    
    protected IIOMetadata updateMetadata(IIOMetadata addAdobeTransform, final ImageWriterParams imageWriterParams) {
        if ("javax_imageio_jpeg_image_1.0".equals(addAdobeTransform.getNativeMetadataFormatName())) {
            addAdobeTransform = addAdobeTransform(addAdobeTransform);
            final IIOMetadataNode root = (IIOMetadataNode)addAdobeTransform.getAsTree("javax_imageio_jpeg_image_1.0");
            IIOMetadataNode childNode = ImageIOImageWriter.getChildNode(root, "JPEGvariety");
            if (childNode == null) {
                childNode = new IIOMetadataNode("JPEGvariety");
                root.appendChild(childNode);
            }
            if (imageWriterParams.getResolution() != null) {
                IIOMetadataNode childNode2 = ImageIOImageWriter.getChildNode(childNode, "app0JFIF");
                if (childNode2 == null) {
                    childNode2 = new IIOMetadataNode("app0JFIF");
                    childNode.appendChild(childNode2);
                }
                childNode2.setAttribute("majorVersion", null);
                childNode2.setAttribute("minorVersion", null);
                childNode2.setAttribute("resUnits", "1");
                childNode2.setAttribute("Xdensity", imageWriterParams.getResolution().toString());
                childNode2.setAttribute("Ydensity", imageWriterParams.getResolution().toString());
                childNode2.setAttribute("thumbWidth", null);
                childNode2.setAttribute("thumbHeight", null);
            }
            try {
                addAdobeTransform.setFromTree("javax_imageio_jpeg_image_1.0", root);
            }
            catch (IIOInvalidTreeException cause) {
                throw new RuntimeException("Cannot update image metadata: " + cause.getMessage(), cause);
            }
        }
        return addAdobeTransform;
    }
    
    private static IIOMetadata addAdobeTransform(final IIOMetadata iioMetadata) {
        final IIOMetadataNode root = (IIOMetadataNode)iioMetadata.getAsTree("javax_imageio_jpeg_image_1.0");
        final IIOMetadataNode childNode = ImageIOImageWriter.getChildNode(root, "markerSequence");
        if (childNode == null) {
            throw new RuntimeException("Invalid metadata!");
        }
        final IIOMetadataNode childNode2 = ImageIOImageWriter.getChildNode(childNode, "app14Adobe");
        if (childNode2 == null) {
            final IIOMetadataNode newChild = new IIOMetadataNode("app14Adobe");
            newChild.setAttribute("transform", "1");
            newChild.setAttribute("version", "101");
            newChild.setAttribute("flags0", "0");
            newChild.setAttribute("flags1", "0");
            childNode.appendChild(newChild);
        }
        else {
            childNode2.setAttribute("transform", "1");
        }
        try {
            iioMetadata.setFromTree("javax_imageio_jpeg_image_1.0", root);
        }
        catch (IIOInvalidTreeException cause) {
            throw new RuntimeException("Cannot update image metadata: " + cause.getMessage(), cause);
        }
        return iioMetadata;
    }
    
    protected ImageWriteParam getDefaultWriteParam(final javax.imageio.ImageWriter imageWriter, final RenderedImage renderedImage, final ImageWriterParams imageWriterParams) {
        return new JPEGImageWriteParam(imageWriter.getLocale());
    }
}
