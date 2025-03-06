// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import org.apache.batik.Version;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Frame;
import javax.swing.JWindow;

public class AboutDialog extends JWindow
{
    public static final String ICON_BATIK_SPLASH = "AboutDialog.icon.batik.splash";
    
    public AboutDialog() {
        this.buildGUI();
    }
    
    public AboutDialog(final Frame owner) {
        super(owner);
        this.buildGUI();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 27) {
                    AboutDialog.this.setVisible(false);
                    AboutDialog.this.dispose();
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(final MouseEvent mouseEvent) {
                AboutDialog.this.setVisible(false);
                AboutDialog.this.dispose();
            }
        });
    }
    
    public void setLocationRelativeTo(final Frame frame) {
        final Dimension size = frame.getSize();
        final Point location = frame.getLocation();
        final Point point = new Point(location.x, location.y);
        final Rectangle bounds = this.getBounds();
        int x = point.x + (size.width - bounds.width) / 2;
        int y = point.y + (size.height - bounds.height) / 2;
        final Dimension screenSize = this.getToolkit().getScreenSize();
        if (y + bounds.height > screenSize.height) {
            y = screenSize.height - bounds.height;
            x = ((point.x < screenSize.width >> 1) ? (point.x + size.width) : (point.x - bounds.width));
        }
        if (x + bounds.width > screenSize.width) {
            x = screenSize.width - bounds.width;
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        this.setLocation(x, y);
    }
    
    protected void buildGUI() {
        this.getContentPane().setBackground(Color.white);
        final ImageIcon image = new ImageIcon(this.getClass().getClassLoader().getResource(Resources.getString("AboutDialog.icon.batik.splash")));
        final int iconWidth = image.getIconWidth();
        final int iconHeight = image.getIconHeight();
        final JLayeredPane comp = new JLayeredPane();
        comp.setSize(600, 425);
        this.getContentPane().add(comp);
        final JLabel comp2 = new JLabel(image);
        comp2.setBounds(0, 0, iconWidth, iconHeight);
        comp.add(comp2, new Integer(0));
        final JLabel comp3 = new JLabel("Batik " + Version.getVersion());
        comp3.setForeground(new Color(232, 232, 232, 255));
        comp3.setOpaque(false);
        comp3.setBackground(new Color(0, 0, 0, 0));
        comp3.setHorizontalAlignment(4);
        comp3.setVerticalAlignment(3);
        comp3.setBounds(iconWidth - 320, iconHeight - 117, 300, 100);
        comp.add(comp3, new Integer(2));
        ((JComponent)this.getContentPane()).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black), BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), BorderFactory.createLineBorder(Color.black)), BorderFactory.createEmptyBorder(10, 10, 10, 10))));
    }
}
