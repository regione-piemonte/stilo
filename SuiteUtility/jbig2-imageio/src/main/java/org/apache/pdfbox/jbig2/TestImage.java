// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.ImageObserver;
import java.awt.MediaTracker;
import javax.swing.JComponent;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.Image;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.DataBufferByte;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.IndexColorModel;
import javax.swing.JFrame;

public class TestImage extends JFrame
{
    private static final long serialVersionUID = 7353175320371957550L;
    
    public static void main(final String[] array) {
        final int n = 250;
        final int n2 = 250;
        final int n3 = (n + 7) / 8;
        final byte[] array2 = new byte[n2 * n3];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = (byte)i;
        }
        new TestImage(array2, n, n2, n3);
    }
    
    public TestImage(final byte[] dataArray, final int w, final int h, final int scanlineStride) {
        super("Demobild");
        this.setContentPane(new JScrollPane(new ImageComponent(new BufferedImage(new IndexColorModel(1, 2, new byte[] { -1, 0 }, new byte[] { -1, 0 }, new byte[] { -1, 0 }), Raster.createWritableRaster(new MultiPixelPackedSampleModel(0, w, h, 1, scanlineStride, 0), new DataBufferByte(dataArray, dataArray.length), new Point(0, 0)), false, null))));
        this.pack();
        this.setSize(new Dimension(1600, 900));
        this.setVisible(true);
        try {
            System.in.read();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public TestImage(final BufferedImage bufferedImage) {
        super("Demobild");
        this.setDefaultCloseOperation(3);
        final ImageComponent view = new ImageComponent(bufferedImage);
        view.setScale(1);
        this.setContentPane(new JScrollPane(view));
        this.pack();
        this.setSize(new Dimension(1600, 900));
        this.setVisible(true);
        try {
            System.in.read();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static class ImageComponent extends JComponent
    {
        private static final long serialVersionUID = -5921296548288376287L;
        Image myImage;
        int imgWidth;
        int imgHeight;
        Dimension prefSize;
        private int scale;
        
        protected ImageComponent() {
            this.imgWidth = -1;
            this.imgHeight = -1;
            this.prefSize = null;
            this.scale = 1;
        }
        
        public ImageComponent(final Image image) {
            this.imgWidth = -1;
            this.imgHeight = -1;
            this.prefSize = null;
            this.scale = 1;
            this.setImage(image);
        }
        
        @Override
        public Dimension getPreferredSize() {
            if (this.prefSize != null) {
                return this.prefSize;
            }
            return super.getPreferredSize();
        }
        
        @Override
        public Dimension getMinimumSize() {
            if (this.prefSize != null) {
                return this.prefSize;
            }
            return super.getMinimumSize();
        }
        
        public void setImage(final Image myImage) {
            if (this.myImage != null) {
                this.myImage.flush();
            }
            this.myImage = myImage;
            if (this.myImage != null) {
                final MediaTracker mediaTracker = new MediaTracker(this);
                mediaTracker.addImage(this.myImage, 0);
                try {
                    mediaTracker.waitForAll();
                }
                catch (Exception ex) {}
                this.imgWidth = this.myImage.getWidth(this);
                this.imgHeight = this.myImage.getHeight(this);
                this.setSize(this.imgWidth * this.scale, this.imgHeight * this.scale);
                this.prefSize = this.getSize();
                this.invalidate();
                this.validate();
                this.repaint();
            }
        }
        
        @Override
        public Insets getInsets() {
            return new Insets(1, 1, 1, 1);
        }
        
        @Override
        protected void paintComponent(final Graphics graphics) {
            final Graphics2D graphics2D = (Graphics2D)graphics;
            if (this.myImage != null) {
                graphics2D.scale(this.scale, this.scale);
                graphics2D.drawImage(this.myImage, 1, 1, this.imgWidth, this.imgHeight, this);
            }
        }
        
        public void setScale(final int scale) {
            this.scale = scale;
            this.setSize(this.imgWidth * scale, this.imgHeight * scale);
            this.prefSize = this.getSize();
            this.revalidate();
            this.repaint();
        }
        
        public int getScale() {
            return this.scale;
        }
    }
}
