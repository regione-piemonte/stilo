// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.Locale;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import javax.swing.JPanel;

public class StatusBar extends JPanel
{
    protected static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.StatusBarMessages";
    protected static ResourceBundle bundle;
    protected static ResourceManager rManager;
    protected JLabel xPosition;
    protected JLabel yPosition;
    protected JLabel zoom;
    protected JLabel message;
    protected String mainMessage;
    protected String temporaryMessage;
    protected DisplayThread displayThread;
    
    public StatusBar() {
        super(new BorderLayout(5, 5));
        final JPanel comp = new JPanel(new BorderLayout(0, 0));
        this.add("West", comp);
        this.xPosition = new JLabel();
        final BevelBorder bevelBorder = new BevelBorder(1, this.getBackground().brighter().brighter(), this.getBackground(), this.getBackground().darker().darker(), this.getBackground());
        this.xPosition.setBorder(bevelBorder);
        this.xPosition.setPreferredSize(new Dimension(110, 16));
        comp.add("West", this.xPosition);
        (this.yPosition = new JLabel()).setBorder(bevelBorder);
        this.yPosition.setPreferredSize(new Dimension(110, 16));
        comp.add("Center", this.yPosition);
        (this.zoom = new JLabel()).setBorder(bevelBorder);
        this.zoom.setPreferredSize(new Dimension(70, 16));
        comp.add("East", this.zoom);
        final JPanel comp2 = new JPanel(new BorderLayout(0, 0));
        (this.message = new JLabel()).setBorder(bevelBorder);
        comp2.add(this.message);
        this.add(comp2);
        this.setMainMessage(StatusBar.rManager.getString("Panel.default_message"));
    }
    
    public void setXPosition(final float f) {
        this.xPosition.setText("x: " + f);
    }
    
    public void setWidth(final float f) {
        this.xPosition.setText(StatusBar.rManager.getString("Position.width_letters") + " " + f);
    }
    
    public void setYPosition(final float f) {
        this.yPosition.setText("y: " + f);
    }
    
    public void setHeight(final float f) {
        this.yPosition.setText(StatusBar.rManager.getString("Position.height_letters") + " " + f);
    }
    
    public void setZoom(float f) {
        f = ((f > 0.0f) ? f : (-f));
        if (f == 1.0f) {
            this.zoom.setText("1:1");
        }
        else if (f >= 1.0f) {
            String str = Float.toString(f);
            if (str.length() > 6) {
                str = str.substring(0, 6);
            }
            this.zoom.setText("1:" + str);
        }
        else {
            String str2 = Float.toString(1.0f / f);
            if (str2.length() > 6) {
                str2 = str2.substring(0, 6);
            }
            this.zoom.setText(str2 + ":1");
        }
    }
    
    public void setMessage(final String temporaryMessage) {
        this.setPreferredSize(new Dimension(0, this.getPreferredSize().height));
        if (this.displayThread != null) {
            this.displayThread.finish();
        }
        this.temporaryMessage = temporaryMessage;
        (this.displayThread = new DisplayThread(this.displayThread)).start();
    }
    
    public void setMainMessage(final String s) {
        this.mainMessage = s;
        this.message.setText(this.mainMessage = s);
        if (this.displayThread != null) {
            this.displayThread.finish();
            this.displayThread = null;
        }
        this.setPreferredSize(new Dimension(0, this.getPreferredSize().height));
    }
    
    static {
        StatusBar.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.StatusBarMessages", Locale.getDefault());
        StatusBar.rManager = new ResourceManager(StatusBar.bundle);
    }
    
    protected class DisplayThread extends Thread
    {
        static final long DEFAULT_DURATION = 5000L;
        long duration;
        Thread toJoin;
        
        public DisplayThread(final StatusBar statusBar) {
            this(statusBar, 5000L, null);
        }
        
        public DisplayThread(final StatusBar statusBar, final long n) {
            this(statusBar, n, null);
        }
        
        public DisplayThread(final StatusBar statusBar, final Thread thread) {
            this(statusBar, 5000L, thread);
        }
        
        public DisplayThread(final long duration, final Thread toJoin) {
            this.duration = duration;
            this.toJoin = toJoin;
            this.setPriority(1);
        }
        
        public synchronized void finish() {
            this.duration = 0L;
            this.notifyAll();
        }
        
        public void run() {
            synchronized (this) {
                if (this.toJoin != null) {
                    while (this.toJoin.isAlive()) {
                        try {
                            this.toJoin.join();
                        }
                        catch (InterruptedException ex) {}
                    }
                    this.toJoin = null;
                }
                StatusBar.this.message.setText(StatusBar.this.temporaryMessage);
                long currentTimeMillis = System.currentTimeMillis();
                while (this.duration > 0L) {
                    try {
                        this.wait(this.duration);
                    }
                    catch (InterruptedException ex2) {}
                    final long currentTimeMillis2 = System.currentTimeMillis();
                    this.duration -= currentTimeMillis2 - currentTimeMillis;
                    currentTimeMillis = currentTimeMillis2;
                }
                StatusBar.this.message.setText(StatusBar.this.mainMessage);
            }
        }
    }
}
