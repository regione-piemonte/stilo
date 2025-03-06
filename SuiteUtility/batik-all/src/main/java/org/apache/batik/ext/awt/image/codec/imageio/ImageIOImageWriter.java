// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import javax.imageio.stream.ImageInputStream;
import org.w3c.dom.NodeList;
import javax.imageio.metadata.IIOInvalidTreeException;
import org.w3c.dom.Node;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.ImageWriteParam;
import java.util.Iterator;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageIO;
import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import javax.imageio.event.IIOWriteWarningListener;
import org.apache.batik.ext.awt.image.spi.ImageWriter;

public class ImageIOImageWriter implements ImageWriter, IIOWriteWarningListener
{
    private String targetMIME;
    
    public ImageIOImageWriter(final String targetMIME) {
        this.targetMIME = targetMIME;
    }
    
    public void writeImage(final RenderedImage renderedImage, final OutputStream outputStream) throws IOException {
        this.writeImage(renderedImage, outputStream, null);
    }
    
    public void writeImage(final RenderedImage renderedImage, final OutputStream output, final ImageWriterParams imageWriterParams) throws IOException {
        final Iterator<javax.imageio.ImageWriter> imageWritersByMIMEType = ImageIO.getImageWritersByMIMEType(this.getMIMEType());
        javax.imageio.ImageWriter imageWriter = null;
        try {
            imageWriter = imageWritersByMIMEType.next();
            if (imageWriter != null) {
                imageWriter.addIIOWriteWarningListener(this);
                Object imageOutputStream = null;
                try {
                    imageOutputStream = ImageIO.createImageOutputStream(output);
                    final ImageWriteParam defaultWriteParam = this.getDefaultWriteParam(imageWriter, renderedImage, imageWriterParams);
                    ImageTypeSpecifier imageTypeSpecifier;
                    if (defaultWriteParam.getDestinationType() != null) {
                        imageTypeSpecifier = defaultWriteParam.getDestinationType();
                    }
                    else {
                        imageTypeSpecifier = ImageTypeSpecifier.createFromRenderedImage(renderedImage);
                    }
                    IIOMetadata metadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier, defaultWriteParam);
                    if (imageWriterParams != null && metadata != null) {
                        metadata = this.updateMetadata(metadata, imageWriterParams);
                    }
                    imageWriter.setOutput(imageOutputStream);
                    imageWriter.write(null, new IIOImage(renderedImage, null, metadata), defaultWriteParam);
                    return;
                }
                finally {
                    if (imageOutputStream != null) {
                        System.err.println("closing");
                        ((ImageInputStream)imageOutputStream).close();
                    }
                }
                throw new UnsupportedOperationException("No ImageIO codec for writing " + this.getMIMEType() + " is available!");
            }
            throw new UnsupportedOperationException("No ImageIO codec for writing " + this.getMIMEType() + " is available!");
        }
        finally {
            if (imageWriter != null) {
                System.err.println("disposing");
                imageWriter.dispose();
            }
        }
    }
    
    protected ImageWriteParam getDefaultWriteParam(final javax.imageio.ImageWriter imageWriter, final RenderedImage renderedImage, final ImageWriterParams obj) {
        final ImageWriteParam defaultWriteParam = imageWriter.getDefaultWriteParam();
        System.err.println("Param: " + obj);
        if (obj != null && obj.getCompressionMethod() != null) {
            defaultWriteParam.setCompressionMode(2);
            defaultWriteParam.setCompressionType(obj.getCompressionMethod());
        }
        return defaultWriteParam;
    }
    
    protected IIOMetadata updateMetadata(final IIOMetadata iioMetadata, final ImageWriterParams imageWriterParams) {
        if (iioMetadata.isStandardMetadataFormatSupported()) {
            final IIOMetadataNode iioMetadataNode = (IIOMetadataNode)iioMetadata.getAsTree("javax_imageio_1.0");
            final IIOMetadataNode childNode = getChildNode(iioMetadataNode, "Dimension");
            if (imageWriterParams.getResolution() != null) {
                IIOMetadataNode childNode2 = getChildNode(childNode, "HorizontalPixelSize");
                if (childNode2 == null) {
                    childNode2 = new IIOMetadataNode("HorizontalPixelSize");
                    childNode.appendChild(childNode2);
                }
                childNode2.setAttribute("value", Double.toString(imageWriterParams.getResolution() / 25.4));
                IIOMetadataNode childNode3 = getChildNode(childNode, "VerticalPixelSize");
                if (childNode3 == null) {
                    childNode3 = new IIOMetadataNode("VerticalPixelSize");
                    childNode.appendChild(childNode3);
                }
                childNode3.setAttribute("value", Double.toString(imageWriterParams.getResolution() / 25.4));
            }
            try {
                iioMetadata.mergeTree("javax_imageio_1.0", iioMetadataNode);
            }
            catch (IIOInvalidTreeException ex) {
                throw new RuntimeException("Cannot update image metadata: " + ex.getMessage());
            }
        }
        return iioMetadata;
    }
    
    protected static IIOMetadataNode getChildNode(final Node node, final String s) {
        final NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            final Node item = childNodes.item(i);
            if (s.equals(item.getNodeName())) {
                return (IIOMetadataNode)item;
            }
        }
        return null;
    }
    
    public String getMIMEType() {
        return this.targetMIME;
    }
    
    public void warningOccurred(final javax.imageio.ImageWriter imageWriter, final int n, final String str) {
        System.err.println("Problem while writing image using ImageI/O: " + str);
    }
}
