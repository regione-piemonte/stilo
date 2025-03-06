// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.slideshow;

import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;
import java.awt.Shape;
import java.awt.Rectangle;
import org.w3c.dom.Element;
import org.apache.batik.bridge.ViewBox;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.GVTBuilder;
import java.util.ArrayList;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.awt.image.BufferedImageOp;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Component;
import javax.swing.JWindow;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Toolkit;
import org.apache.batik.bridge.UserAgentAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.awt.image.BufferedImage;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.gvt.renderer.StaticRenderer;
import javax.swing.JComponent;

public class Main extends JComponent
{
    StaticRenderer renderer;
    UserAgent userAgent;
    DocumentLoader loader;
    BridgeContext ctx;
    BufferedImage image;
    BufferedImage display;
    File[] files;
    static int duration;
    static int frameDelay;
    volatile boolean done;
    volatile Thread transitionThread;
    long startLastTransition;
    volatile boolean paused;
    
    public Main(final File[] files, Dimension screenSize) {
        this.done = false;
        this.transitionThread = null;
        this.startLastTransition = 0L;
        this.paused = false;
        this.setBackground(Color.black);
        this.files = files;
        final UserAgentAdapter userAgent = new UserAgentAdapter();
        this.renderer = new StaticRenderer();
        this.userAgent = userAgent;
        this.loader = new DocumentLoader(this.userAgent);
        userAgent.setBridgeContext(this.ctx = new BridgeContext(this.userAgent, this.loader));
        if (screenSize == null) {
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        }
        this.setPreferredSize(screenSize);
        this.setDoubleBuffered(false);
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent mouseEvent) {
                if (Main.this.done) {
                    System.exit(0);
                }
                else {
                    Main.this.togglePause();
                }
            }
        });
        final Dimension dimension = screenSize;
        dimension.width += 2;
        final Dimension dimension2 = screenSize;
        dimension2.height += 2;
        this.display = new BufferedImage(screenSize.width, screenSize.height, 4);
        new RenderThread().start();
        final JWindow window = new JWindow();
        window.setBackground(Color.black);
        window.getContentPane().setBackground(Color.black);
        window.getContentPane().add(this);
        window.pack();
        window.setLocation(new Point(-1, -1));
        window.setVisible(true);
    }
    
    public void setTransition(final BufferedImage bufferedImage) {
        synchronized (this) {
            while (this.transitionThread != null) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
            (this.transitionThread = new TransitionThread(bufferedImage)).start();
        }
    }
    
    public void togglePause() {
        synchronized (this) {
            this.paused = !this.paused;
            Cursor cursor;
            if (this.paused) {
                cursor = new Cursor(3);
            }
            else {
                cursor = new Cursor(0);
                if (this.transitionThread != null) {
                    synchronized (this.transitionThread) {
                        this.transitionThread.notifyAll();
                    }
                }
            }
            this.setCursor(cursor);
        }
    }
    
    public void paint(final Graphics graphics) {
        final Graphics2D graphics2D = (Graphics2D)graphics;
        if (this.display == null) {
            return;
        }
        graphics2D.drawImage(this.display, null, 0, 0);
    }
    
    public static void readFileList(final String s, final List list) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(s));
        }
        catch (FileNotFoundException ex) {
            System.err.println("Unable to open file-list: " + s);
            return;
        }
        try {
            final URL url = new File(s).toURL();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String substring = line;
                final int index = substring.indexOf(35);
                if (index != -1) {
                    substring = substring.substring(0, index);
                }
                final String trim = substring.trim();
                if (trim.length() == 0) {
                    continue;
                }
                try {
                    list.add(new URL(url, trim).getFile());
                }
                catch (MalformedURLException ex2) {
                    System.err.println("Can't make sense of line:\n  " + line);
                }
            }
        }
        catch (IOException ex3) {
            System.err.println("Error while reading file-list: " + s);
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch (IOException ex4) {}
        }
    }
    
    public static void main(final String[] array) {
        final ArrayList<Object> list = new ArrayList<Object>();
        Dimension dimension = null;
        if (array.length == 0) {
            showUsage();
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals("-h") || array[i].equals("-help") || array[i].equals("--help")) {
                showUsage();
                return;
            }
            if (array[i].equals("--")) {
                ++i;
                while (i < array.length) {
                    list.add(array[i++]);
                }
                break;
            }
            if (array[i].equals("-fl") || array[i].equals("--file-list")) {
                if (i + 1 == array.length) {
                    System.err.println("Must provide name of file list file after " + array[i]);
                    break;
                }
                readFileList(array[i + 1], list);
                ++i;
            }
            else if (array[i].equals("-ft") || array[i].equals("--frame-time")) {
                if (i + 1 == array.length) {
                    System.err.println("Must provide time in millis after " + array[i]);
                    break;
                }
                try {
                    Main.frameDelay = Integer.decode(array[i + 1]);
                    ++i;
                }
                catch (NumberFormatException ex2) {
                    System.err.println("Can't parse frame time: " + array[i + 1]);
                }
            }
            else if (array[i].equals("-tt") || array[i].equals("--transition-time")) {
                if (i + 1 == array.length) {
                    System.err.println("Must provide time in millis after " + array[i]);
                    break;
                }
                try {
                    Main.duration = Integer.decode(array[i + 1]);
                    ++i;
                }
                catch (NumberFormatException ex3) {
                    System.err.println("Can't parse transition time: " + array[i + 1]);
                }
            }
            else if (array[i].equals("-ws") || array[i].equals("--window-size")) {
                if (i + 1 == array.length) {
                    System.err.println("Must provide window size [w,h] after " + array[i]);
                    break;
                }
                try {
                    final int index = array[i + 1].indexOf(44);
                    int width;
                    int intValue;
                    if (index == -1) {
                        intValue = (width = Integer.decode(array[i + 1]));
                    }
                    else {
                        final String substring = array[i + 1].substring(0, index);
                        final String substring2 = array[i + 1].substring(index + 1);
                        width = Integer.decode(substring);
                        intValue = Integer.decode(substring2);
                    }
                    dimension = new Dimension(width, intValue);
                    ++i;
                }
                catch (NumberFormatException ex4) {
                    System.err.println("Can't parse window size: " + array[i + 1]);
                }
            }
            else {
                list.add(array[i]);
            }
        }
        final File[] array2 = new File[list.size()];
        for (int j = 0; j < list.size(); ++j) {
            try {
                array2[j] = new File(list.get(j));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        new Main(array2, dimension);
    }
    
    public static void showUsage() {
        System.out.println("Options:\n                                 -- : Remaining args are file names\n                         -fl <file>\n                 --file-list <file> : file contains list of images to\n                                      show one per line\n             -ws <width>[,<height>]\n    -window-size <width>[,<height>] : Set the size of slideshow window\n                                      defaults to full screen\n                          -ft <int>\n                 --frame-time <int> : Amount of time in millisecs to\n                                      show each frame.\n                                      Includes transition time.\n                          -tt <int>\n            --transition-time <int> : Amount of time in millisecs to\n                                      transition between frames.\n                             <file> : SVG file to display");
    }
    
    static {
        Main.duration = 3000;
        Main.frameDelay = Main.duration + 7000;
    }
    
    class TransitionThread extends Thread
    {
        BufferedImage src;
        int blockw;
        int blockh;
        
        public TransitionThread(final BufferedImage src) {
            super("TransitionThread");
            this.blockw = 75;
            this.blockh = 75;
            this.setDaemon(true);
            this.src = src;
        }
        
        public void run() {
            final int n = (Main.this.display.getWidth() + this.blockw - 1) / this.blockw;
            final int n2 = (Main.this.display.getHeight() + this.blockh - 1) / this.blockh;
            final int n3 = n * n2;
            final int n4 = Main.duration / n3;
            final Point[] array = new Point[n3];
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < n; ++j) {
                    array[i * n + j] = new Point(j, i);
                }
            }
            final Graphics2D graphics = Main.this.display.createGraphics();
            graphics.setColor(Color.black);
            for (long n5 = System.currentTimeMillis(); n5 - Main.this.startLastTransition < Main.frameDelay; n5 = System.currentTimeMillis()) {
                try {
                    long n6 = Main.frameDelay - (n5 - Main.this.startLastTransition);
                    if (n6 > 500L) {
                        System.gc();
                        n6 = Main.frameDelay - (System.currentTimeMillis() - Main.this.startLastTransition);
                    }
                    if (n6 > 0L) {
                        Thread.sleep(n6);
                    }
                }
                catch (InterruptedException ex) {}
            }
            synchronized (this) {
                while (Main.this.paused) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException ex2) {}
                }
            }
            final Main this$0 = Main.this;
            final long currentTimeMillis = System.currentTimeMillis();
            this$0.startLastTransition = currentTimeMillis;
            long n7 = currentTimeMillis;
            for (int k = 0; k < array.length; ++k) {
                final int n8 = (int)(Math.random() * (array.length - k));
                final Point point = array[n8];
                System.arraycopy(array, n8 + 1, array, n8 + 1 - 1, array.length - k - n8 - 1);
                final int n9 = point.x * this.blockw;
                final int n10 = point.y * this.blockh;
                int blockw = this.blockw;
                int blockh = this.blockh;
                if (n9 + blockw > this.src.getWidth()) {
                    blockw = this.src.getWidth() - n9;
                }
                if (n10 + blockh > this.src.getHeight()) {
                    blockh = this.src.getHeight() - n10;
                }
                synchronized (Main.this.display) {
                    graphics.fillRect(n9, n10, blockw, blockh);
                    graphics.drawImage(this.src.getSubimage(n9, n10, blockw, blockh), null, n9, n10);
                }
                Main.this.repaint(n9, n10, blockw, blockh);
                final long currentTimeMillis2 = System.currentTimeMillis();
                try {
                    final long n11 = currentTimeMillis2 - n7;
                    if (n11 < n4) {
                        Thread.sleep(n4 - n11);
                    }
                }
                catch (InterruptedException ex3) {}
                n7 = currentTimeMillis2;
            }
            synchronized (Main.this) {
                Main.this.transitionThread = null;
                Main.this.notifyAll();
            }
        }
    }
    
    class RenderThread extends Thread
    {
        RenderThread() {
            super("RenderThread");
            this.setDaemon(true);
        }
        
        public void run() {
            Main.this.renderer.setDoubleBuffered(true);
            for (int i = 0; i < Main.this.files.length; ++i) {
                final GVTBuilder gvtBuilder = new GVTBuilder();
                try {
                    final String string = Main.this.files[i].toURL().toString();
                    System.out.println("Reading: " + string);
                    final Document loadDocument = Main.this.loader.loadDocument(string);
                    System.out.println("Building: " + string);
                    final GraphicsNode build = gvtBuilder.build(Main.this.ctx, loadDocument);
                    System.out.println("Rendering: " + string);
                    Main.this.renderer.setTree(build);
                    Main.this.renderer.setTransform(ViewBox.getViewTransform(null, (Element)((SVGDocument)loadDocument).getRootElement(), (float)Main.this.display.getWidth(), (float)Main.this.display.getHeight(), Main.this.ctx));
                    Main.this.renderer.updateOffScreen(Main.this.display.getWidth(), Main.this.display.getHeight());
                    Main.this.renderer.repaint(new Rectangle(0, 0, Main.this.display.getWidth(), Main.this.display.getHeight()));
                    System.out.println("Painting: " + string);
                    Main.this.image = Main.this.renderer.getOffScreen();
                    Main.this.setTransition(Main.this.image);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (Main.this.transitionThread != null) {
                try {
                    Main.this.transitionThread.join();
                }
                catch (InterruptedException ex2) {}
                Main.this.done = true;
                Main.this.setCursor(new Cursor(3));
            }
        }
    }
}
