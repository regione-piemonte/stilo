// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Rectangle;
import javax.swing.border.Border;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu;
import javax.swing.AbstractButton;
import javax.swing.plaf.ComponentUI;
import javax.swing.UIManager;
import java.awt.Graphics;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JComponent;

public abstract class SwingSVGPrettyPrint implements SVGSyntax
{
    public static void print(final JComponent component, final SVGGraphics2D svgGraphics2D) {
        if (component instanceof JComboBox || component instanceof JScrollBar) {
            printHack(component, svgGraphics2D);
            return;
        }
        final SVGGraphics2D svgGraphics2D2 = (SVGGraphics2D)svgGraphics2D.create();
        svgGraphics2D2.setColor(component.getForeground());
        svgGraphics2D2.setFont(component.getFont());
        final Element topLevelGroup = svgGraphics2D2.getTopLevelGroup();
        if (component.getWidth() <= 0 || component.getHeight() <= 0) {
            return;
        }
        if (svgGraphics2D2.getClipBounds() == null) {
            svgGraphics2D2.setClip(0, 0, component.getWidth(), component.getHeight());
        }
        paintComponent(component, svgGraphics2D2);
        paintBorder(component, svgGraphics2D2);
        paintChildren(component, svgGraphics2D2);
        final Element topLevelGroup2 = svgGraphics2D2.getTopLevelGroup();
        topLevelGroup2.setAttributeNS(null, "id", svgGraphics2D.getGeneratorContext().idGenerator.generateID(component.getClass().getName()));
        topLevelGroup.appendChild(topLevelGroup2);
        svgGraphics2D.setTopLevelGroup(topLevelGroup);
    }
    
    private static void printHack(final JComponent component, final SVGGraphics2D svgGraphics2D) {
        final SVGGraphics2D g = (SVGGraphics2D)svgGraphics2D.create();
        g.setColor(component.getForeground());
        g.setFont(component.getFont());
        final Element topLevelGroup = g.getTopLevelGroup();
        if (component.getWidth() <= 0 || component.getHeight() <= 0) {
            return;
        }
        if (g.getClipBounds() == null) {
            g.setClip(0, 0, component.getWidth(), component.getHeight());
        }
        component.paint(g);
        final Element topLevelGroup2 = g.getTopLevelGroup();
        topLevelGroup2.setAttributeNS(null, "id", svgGraphics2D.getGeneratorContext().idGenerator.generateID(component.getClass().getName()));
        topLevelGroup.appendChild(topLevelGroup2);
        svgGraphics2D.setTopLevelGroup(topLevelGroup);
    }
    
    private static void paintComponent(final JComponent c, final SVGGraphics2D g) {
        final ComponentUI ui = UIManager.getUI(c);
        if (ui != null) {
            ui.installUI(c);
            ui.update(g, c);
        }
    }
    
    private static void paintBorder(final JComponent component, final SVGGraphics2D svgGraphics2D) {
        final Border border = component.getBorder();
        if (border != null) {
            if (component instanceof AbstractButton || component instanceof JPopupMenu || component instanceof JToolBar || component instanceof JMenuBar || component instanceof JProgressBar) {
                if ((component instanceof AbstractButton && ((AbstractButton)component).isBorderPainted()) || (component instanceof JPopupMenu && ((JPopupMenu)component).isBorderPainted()) || (component instanceof JToolBar && ((JToolBar)component).isBorderPainted()) || (component instanceof JMenuBar && ((JMenuBar)component).isBorderPainted()) || (component instanceof JProgressBar && ((JProgressBar)component).isBorderPainted())) {
                    border.paintBorder(component, svgGraphics2D, 0, 0, component.getWidth(), component.getHeight());
                }
            }
            else {
                border.paintBorder(component, svgGraphics2D, 0, 0, component.getWidth(), component.getHeight());
            }
        }
    }
    
    private static void paintChildren(final JComponent component, final SVGGraphics2D svgGraphics2D) {
        int i = component.getComponentCount() - 1;
        final Rectangle rectangle = new Rectangle();
        while (i >= 0) {
            final Component component2 = component.getComponent(i);
            if (component2 != null && JComponent.isLightweightComponent(component2) && component2.isVisible()) {
                Rectangle bounds;
                if (component2 instanceof JComponent) {
                    bounds = rectangle;
                    ((JComponent)component2).getBounds(bounds);
                }
                else {
                    bounds = component2.getBounds();
                }
                if (svgGraphics2D.hitClip(bounds.x, bounds.y, bounds.width, bounds.height)) {
                    final SVGGraphics2D g = (SVGGraphics2D)svgGraphics2D.create(bounds.x, bounds.y, bounds.width, bounds.height);
                    g.setColor(component2.getForeground());
                    g.setFont(component2.getFont());
                    if (component2 instanceof JComponent) {
                        print((JComponent)component2, g);
                    }
                    else {
                        component2.paint(g);
                    }
                }
            }
            --i;
        }
    }
}
