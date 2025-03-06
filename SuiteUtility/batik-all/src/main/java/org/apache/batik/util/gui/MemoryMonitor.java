// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.LinkedList;
import java.awt.geom.GeneralPath;
import java.awt.Color;
import java.awt.Stroke;
import java.util.Iterator;
import java.awt.EventQueue;
import java.util.List;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import org.apache.batik.util.gui.resource.ButtonFactory;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JFrame;

public class MemoryMonitor extends JFrame implements ActionMap
{
    protected static final String RESOURCE = "org.apache.batik.util.gui.resources.MemoryMonitorMessages";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected Map listeners;
    protected Panel panel;
    
    public MemoryMonitor() {
        this(1000L);
    }
    
    public MemoryMonitor(final long n) {
        super(MemoryMonitor.resources.getString("Frame.title"));
        (this.listeners = new HashMap()).put("CollectButtonAction", new CollectButtonAction());
        this.listeners.put("CloseButtonAction", new CloseButtonAction());
        this.panel = new Panel(n);
        this.getContentPane().add(this.panel);
        this.panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), MemoryMonitor.resources.getString("Frame.border_title")));
        final JPanel comp = new JPanel(new FlowLayout(2));
        final ButtonFactory buttonFactory = new ButtonFactory(MemoryMonitor.bundle, this);
        comp.add(buttonFactory.createJButton("CollectButton"));
        comp.add(buttonFactory.createJButton("CloseButton"));
        this.getContentPane().add(comp, "South");
        this.pack();
        this.addWindowListener(new WindowAdapter() {
            public void windowActivated(final WindowEvent windowEvent) {
                final RepaintThread repaintThread = MemoryMonitor.this.panel.getRepaintThread();
                if (!repaintThread.isAlive()) {
                    repaintThread.start();
                }
                else {
                    repaintThread.safeResume();
                }
            }
            
            public void windowClosing(final WindowEvent windowEvent) {
                MemoryMonitor.this.panel.getRepaintThread().safeSuspend();
            }
            
            public void windowDeiconified(final WindowEvent windowEvent) {
                MemoryMonitor.this.panel.getRepaintThread().safeResume();
            }
            
            public void windowIconified(final WindowEvent windowEvent) {
                MemoryMonitor.this.panel.getRepaintThread().safeSuspend();
            }
        });
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    static {
        MemoryMonitor.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.MemoryMonitorMessages", Locale.getDefault());
        MemoryMonitor.resources = new ResourceManager(MemoryMonitor.bundle);
    }
    
    public static class RepaintThread extends Thread
    {
        protected long timeout;
        protected List components;
        protected Runtime runtime;
        protected boolean suspended;
        protected UpdateRunnable updateRunnable;
        
        public RepaintThread(final long timeout, final List components) {
            this.runtime = Runtime.getRuntime();
            this.timeout = timeout;
            this.components = components;
            this.updateRunnable = this.createUpdateRunnable();
            this.setPriority(1);
        }
        
        public void run() {
            while (true) {
                try {
                    while (true) {
                        synchronized (this.updateRunnable) {
                            if (!this.updateRunnable.inEventQueue) {
                                EventQueue.invokeLater(this.updateRunnable);
                            }
                            this.updateRunnable.inEventQueue = true;
                        }
                        Thread.sleep(this.timeout);
                        synchronized (this) {
                            while (this.suspended) {
                                this.wait();
                            }
                        }
                    }
                }
                catch (InterruptedException ex) {
                    continue;
                }
                break;
            }
        }
        
        protected UpdateRunnable createUpdateRunnable() {
            return new UpdateRunnable();
        }
        
        public synchronized void safeSuspend() {
            if (!this.suspended) {
                this.suspended = true;
            }
        }
        
        public synchronized void safeResume() {
            if (this.suspended) {
                this.suspended = false;
                this.notify();
            }
        }
        
        protected class UpdateRunnable implements Runnable
        {
            public boolean inEventQueue;
            
            protected UpdateRunnable() {
                this.inEventQueue = false;
            }
            
            public void run() {
                final long freeMemory = RepaintThread.this.runtime.freeMemory();
                final long totalMemory = RepaintThread.this.runtime.totalMemory();
                for (final Component component : RepaintThread.this.components) {
                    ((MemoryChangeListener)component).memoryStateChanged(totalMemory, freeMemory);
                    component.repaint();
                }
                synchronized (this) {
                    this.inEventQueue = false;
                }
            }
        }
    }
    
    public interface MemoryChangeListener
    {
        void memoryStateChanged(final long p0, final long p1);
    }
    
    public static class History extends JPanel implements MemoryChangeListener
    {
        public static final int PREFERRED_WIDTH = 200;
        public static final int PREFERRED_HEIGHT = 100;
        protected static final Stroke GRID_LINES_STROKE;
        protected static final Stroke CURVE_STROKE;
        protected static final Stroke BORDER_STROKE;
        protected Color gridLinesColor;
        protected Color curveColor;
        protected Color borderColor;
        protected List data;
        protected int xShift;
        protected long totalMemory;
        protected long freeMemory;
        protected GeneralPath path;
        
        public History() {
            this.gridLinesColor = new Color(0, 130, 0);
            this.curveColor = Color.yellow;
            this.borderColor = Color.green;
            this.data = new LinkedList();
            this.xShift = 0;
            this.path = new GeneralPath();
            this.setBackground(Color.black);
            this.setPreferredSize(new Dimension(200, 100));
        }
        
        public void memoryStateChanged(final long totalMemory, final long freeMemory) {
            this.totalMemory = totalMemory;
            this.freeMemory = freeMemory;
            this.data.add(new Long(this.totalMemory - this.freeMemory));
            if (this.data.size() > 190) {
                this.data.remove(0);
                this.xShift = (this.xShift + 1) % 10;
            }
            final Iterator<Long> iterator = (Iterator<Long>)this.data.iterator();
            final GeneralPath path = new GeneralPath();
            path.moveTo(5.0f, (this.totalMemory - iterator.next()) / (float)this.totalMemory * 80.0f + 10.0f);
            int n = 6;
            while (iterator.hasNext()) {
                path.lineTo((float)n, (this.totalMemory - iterator.next()) / (float)this.totalMemory * 80.0f + 10.0f);
                ++n;
            }
            this.path = path;
        }
        
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            final Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final Dimension size = this.getSize();
            graphics2D.transform(AffineTransform.getScaleInstance(size.width / 200.0, size.height / 100.0));
            graphics2D.setPaint(this.gridLinesColor);
            graphics2D.setStroke(History.GRID_LINES_STROKE);
            for (int i = 1; i < 20; ++i) {
                final int n = i * 10 + 5 - this.xShift;
                graphics2D.draw(new Line2D.Double(n, 5.0, n, 95.0));
            }
            for (int j = 1; j < 9; ++j) {
                final int n2 = j * 10 + 5;
                graphics2D.draw(new Line2D.Double(5.0, n2, 195.0, n2));
            }
            graphics2D.setPaint(this.curveColor);
            graphics2D.setStroke(History.CURVE_STROKE);
            graphics2D.draw(this.path);
            graphics2D.setStroke(History.BORDER_STROKE);
            graphics2D.setPaint(this.borderColor);
            graphics2D.draw(new Rectangle2D.Double(5.0, 5.0, 190.0, 90.0));
        }
        
        static {
            GRID_LINES_STROKE = new BasicStroke(1.0f);
            CURVE_STROKE = new BasicStroke(2.0f, 1, 1);
            BORDER_STROKE = new BasicStroke(2.0f);
        }
    }
    
    public static class Usage extends JPanel implements MemoryChangeListener
    {
        public static final int PREFERRED_WIDTH = 90;
        public static final int PREFERRED_HEIGHT = 100;
        protected static final String UNITS;
        protected static final String TOTAL;
        protected static final String USED;
        protected static final boolean POSTFIX;
        protected static final int FONT_SIZE = 9;
        protected static final int BLOCK_MARGIN = 10;
        protected static final int BLOCKS = 15;
        protected static final double BLOCK_WIDTH = 70.0;
        protected static final double BLOCK_HEIGHT = 3.8666666666666667;
        protected static final int[] BLOCK_TYPE;
        protected Color[] usedColors;
        protected Color[] freeColors;
        protected Font font;
        protected Color textColor;
        protected long totalMemory;
        protected long freeMemory;
        
        public Usage() {
            this.usedColors = new Color[] { Color.red, new Color(255, 165, 0), Color.green };
            this.freeColors = new Color[] { new Color(130, 0, 0), new Color(130, 90, 0), new Color(0, 130, 0) };
            this.font = new Font("SansSerif", 1, 9);
            this.textColor = Color.green;
            this.setBackground(Color.black);
            this.setPreferredSize(new Dimension(90, 100));
        }
        
        public void memoryStateChanged(final long totalMemory, final long freeMemory) {
            this.totalMemory = totalMemory;
            this.freeMemory = freeMemory;
        }
        
        public void setTextColor(final Color textColor) {
            this.textColor = textColor;
        }
        
        public void setLowUsedMemoryColor(final Color color) {
            this.usedColors[2] = color;
        }
        
        public void setMediumUsedMemoryColor(final Color color) {
            this.usedColors[1] = color;
        }
        
        public void setHighUsedMemoryColor(final Color color) {
            this.usedColors[0] = color;
        }
        
        public void setLowFreeMemoryColor(final Color color) {
            this.freeColors[2] = color;
        }
        
        public void setMediumFreeMemoryColor(final Color color) {
            this.freeColors[1] = color;
        }
        
        public void setHighFreeMemoryColor(final Color color) {
            this.freeColors[0] = color;
        }
        
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            final Graphics2D graphics2D = (Graphics2D)g;
            final Dimension size = this.getSize();
            graphics2D.transform(AffineTransform.getScaleInstance(size.width / 90.0, size.height / 100.0));
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final int n = (int)Math.round(15.0 * this.freeMemory / this.totalMemory);
            for (int i = 0; i < n; ++i) {
                final Rectangle2D.Double double1 = new Rectangle2D.Double(10.0, i * 3.8666666666666667 + i + 9.0 + 5.0, 70.0, 3.8666666666666667);
                graphics2D.setPaint(this.freeColors[Usage.BLOCK_TYPE[i]]);
                graphics2D.fill(double1);
            }
            for (int j = n; j < 15; ++j) {
                final Rectangle2D.Double double2 = new Rectangle2D.Double(10.0, j * 3.8666666666666667 + j + 9.0 + 5.0, 70.0, 3.8666666666666667);
                graphics2D.setPaint(this.usedColors[Usage.BLOCK_TYPE[j]]);
                graphics2D.fill(double2);
            }
            graphics2D.setPaint(this.textColor);
            graphics2D.setFont(this.font);
            final long n2 = this.totalMemory / 1024L;
            final long n3 = (this.totalMemory - this.freeMemory) / 1024L;
            String s;
            String s2;
            if (Usage.POSTFIX) {
                s = n2 + Usage.UNITS + " " + Usage.TOTAL;
                s2 = n3 + Usage.UNITS + " " + Usage.USED;
            }
            else {
                s = Usage.TOTAL + " " + n2 + Usage.UNITS;
                s2 = Usage.USED + " " + n3 + Usage.UNITS;
            }
            graphics2D.drawString(s, 10, 10);
            graphics2D.drawString(s2, 10, 97);
        }
        
        static {
            UNITS = MemoryMonitor.resources.getString("Usage.units");
            TOTAL = MemoryMonitor.resources.getString("Usage.total");
            USED = MemoryMonitor.resources.getString("Usage.used");
            POSTFIX = MemoryMonitor.resources.getBoolean("Usage.postfix");
            BLOCK_TYPE = new int[] { 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2 };
        }
    }
    
    public static class Panel extends JPanel
    {
        protected RepaintThread repaintThread;
        
        public Panel() {
            this(1000L);
        }
        
        public Panel(final long n) {
            super(new GridBagLayout());
            final ExtendedGridBagConstraints extendedGridBagConstraints = new ExtendedGridBagConstraints();
            extendedGridBagConstraints.insets = new Insets(5, 5, 5, 5);
            final ArrayList<Usage> list = new ArrayList<Usage>();
            final JPanel comp = new JPanel(new BorderLayout());
            comp.setBorder(BorderFactory.createLoweredBevelBorder());
            final Usage comp2 = new Usage();
            comp.add(comp2);
            extendedGridBagConstraints.weightx = 0.3;
            extendedGridBagConstraints.weighty = 1.0;
            extendedGridBagConstraints.setGridBounds(0, 0, extendedGridBagConstraints.fill = 1, 1);
            this.add(comp, extendedGridBagConstraints);
            list.add(comp2);
            final JPanel comp3 = new JPanel(new BorderLayout());
            comp3.setBorder(BorderFactory.createLoweredBevelBorder());
            final History comp4 = new History();
            comp3.add(comp4);
            extendedGridBagConstraints.weightx = 0.7;
            extendedGridBagConstraints.setGridBounds(1, 0, 1, 1);
            this.add(comp3, extendedGridBagConstraints);
            list.add((Usage)comp4);
            this.repaintThread = new RepaintThread(n, list);
        }
        
        public RepaintThread getRepaintThread() {
            return this.repaintThread;
        }
    }
    
    protected class CloseButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            MemoryMonitor.this.panel.getRepaintThread().safeSuspend();
            MemoryMonitor.this.dispose();
        }
    }
    
    protected class CollectButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            System.gc();
        }
    }
}
