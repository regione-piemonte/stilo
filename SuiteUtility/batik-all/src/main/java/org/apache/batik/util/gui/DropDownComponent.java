// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import java.awt.Graphics;
import javax.swing.UIManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.util.Locale;
import javax.swing.SwingUtilities;
import java.awt.Point;
import javax.swing.border.Border;
import java.awt.GridLayout;
import javax.swing.event.EventListenerList;
import javax.swing.JScrollPane;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import javax.swing.JPopupMenu;
import java.util.EventListener;
import java.util.EventObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DropDownComponent extends JPanel
{
    private JButton mainButton;
    private JButton dropDownButton;
    private Icon enabledDownArrow;
    private Icon disabledDownArrow;
    private ScrollablePopupMenu popupMenu;
    private boolean isDropDownEnabled;
    
    public DropDownComponent(final JButton mainButton) {
        super(new BorderLayout());
        this.popupMenu = this.getPopupMenu();
        this.add(this.mainButton = mainButton, "West");
        this.mainButton.setMaximumSize(new Dimension(24, 24));
        this.mainButton.setPreferredSize(new Dimension(24, 24));
        this.enabledDownArrow = new SmallDownArrow();
        this.disabledDownArrow = new SmallDisabledDownArrow();
        (this.dropDownButton = new JButton(this.disabledDownArrow)).setBorderPainted(false);
        this.dropDownButton.setDisabledIcon(this.disabledDownArrow);
        this.dropDownButton.addMouseListener(new DropDownListener());
        this.dropDownButton.setMaximumSize(new Dimension(18, 24));
        this.dropDownButton.setMinimumSize(new Dimension(18, 10));
        this.dropDownButton.setPreferredSize(new Dimension(18, 10));
        this.dropDownButton.setFocusPainted(false);
        this.add(this.dropDownButton, "East");
        this.setEnabled(false);
    }
    
    public ScrollablePopupMenu getPopupMenu() {
        if (this.popupMenu == null) {
            (this.popupMenu = new ScrollablePopupMenu(this)).setEnabled(false);
            this.popupMenu.addPropertyChangeListener("enabled", new PropertyChangeListener() {
                public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
                    DropDownComponent.this.setEnabled((boolean)propertyChangeEvent.getNewValue());
                }
            });
            this.popupMenu.addListener(new ScrollablePopupMenuAdapter() {
                public void itemsWereAdded(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
                    DropDownComponent.this.updateMainButtonTooltip(scrollablePopupMenuEvent.getDetails());
                }
                
                public void itemsWereRemoved(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
                    DropDownComponent.this.updateMainButtonTooltip(scrollablePopupMenuEvent.getDetails());
                }
            });
        }
        return this.popupMenu;
    }
    
    public void setEnabled(final boolean enabled) {
        this.isDropDownEnabled = enabled;
        this.mainButton.setEnabled(enabled);
        this.dropDownButton.setEnabled(enabled);
        this.dropDownButton.setIcon(enabled ? this.enabledDownArrow : this.disabledDownArrow);
    }
    
    public boolean isEnabled() {
        return this.isDropDownEnabled;
    }
    
    public void updateMainButtonTooltip(final String toolTipText) {
        this.mainButton.setToolTipText(toolTipText);
    }
    
    public static class ScrollablePopupMenuAdapter implements ScrollablePopupMenuListener
    {
        public void itemsWereAdded(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
        }
        
        public void itemsWereRemoved(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
        }
    }
    
    public static class ScrollablePopupMenuEvent extends EventObject
    {
        public static final int ITEMS_ADDED = 1;
        public static final int ITEMS_REMOVED = 2;
        private int type;
        private int itemNumber;
        private String details;
        
        public ScrollablePopupMenuEvent(final Object source, final int n, final int n2, final String s) {
            super(source);
            this.initEvent(n, n2, s);
        }
        
        public void initEvent(final int type, final int itemNumber, final String details) {
            this.type = type;
            this.itemNumber = itemNumber;
            this.details = details;
        }
        
        public String getDetails() {
            return this.details;
        }
        
        public int getItemNumber() {
            return this.itemNumber;
        }
        
        public int getType() {
            return this.type;
        }
    }
    
    public interface ScrollablePopupMenuListener extends EventListener
    {
        void itemsWereAdded(final ScrollablePopupMenuEvent p0);
        
        void itemsWereRemoved(final ScrollablePopupMenuEvent p0);
    }
    
    public static class ScrollablePopupMenu extends JPopupMenu
    {
        private static final String RESOURCES = "org.apache.batik.util.gui.resources.ScrollablePopupMenuMessages";
        private static ResourceBundle bundle;
        private static ResourceManager resources;
        private JPanel menuPanel;
        private JScrollPane scrollPane;
        private int preferredHeight;
        private ScrollablePopupMenuModel model;
        private JComponent ownerComponent;
        private ScrollablePopupMenuItem footer;
        private EventListenerList eventListeners;
        
        public ScrollablePopupMenu(final JComponent ownerComponent) {
            this.menuPanel = new JPanel();
            this.preferredHeight = ScrollablePopupMenu.resources.getInteger("PreferredHeight");
            this.eventListeners = new EventListenerList();
            this.setLayout(new BorderLayout());
            this.menuPanel.setLayout(new GridLayout(0, 1));
            this.ownerComponent = ownerComponent;
            this.init();
        }
        
        private void init() {
            super.removeAll();
            (this.scrollPane = new JScrollPane()).setViewportView(this.menuPanel);
            this.scrollPane.setBorder(null);
            final int integer = ScrollablePopupMenu.resources.getInteger("ScrollPane.minWidth");
            final int integer2 = ScrollablePopupMenu.resources.getInteger("ScrollPane.minHeight");
            final int integer3 = ScrollablePopupMenu.resources.getInteger("ScrollPane.maxWidth");
            final int integer4 = ScrollablePopupMenu.resources.getInteger("ScrollPane.maxHeight");
            this.scrollPane.setMinimumSize(new Dimension(integer, integer2));
            this.scrollPane.setMaximumSize(new Dimension(integer3, integer4));
            this.scrollPane.setHorizontalScrollBarPolicy(31);
            this.add(this.scrollPane, "Center");
            this.addFooter(new DefaultScrollablePopupMenuItem(this, ""));
        }
        
        public void showMenu(final Component invoker, final Component c) {
            this.model.processBeforeShowed();
            final Point point = new Point(0, c.getHeight());
            SwingUtilities.convertPointToScreen(point, c);
            this.setLocation(point);
            this.setInvoker(invoker);
            this.setVisible(true);
            this.revalidate();
            this.repaint();
            this.model.processAfterShowed();
        }
        
        public void add(final ScrollablePopupMenuItem scrollablePopupMenuItem, final int index, final int n, final int n2) {
            this.menuPanel.add((Component)scrollablePopupMenuItem, index);
            if (n == 0) {
                this.setEnabled(true);
            }
        }
        
        public void remove(final ScrollablePopupMenuItem scrollablePopupMenuItem, final int n, final int n2) {
            this.menuPanel.remove((Component)scrollablePopupMenuItem);
            if (n2 == 0) {
                this.setEnabled(false);
            }
        }
        
        private int getPreferredWidth() {
            final Component[] components = this.menuPanel.getComponents();
            int n = 0;
            for (int i = 0; i < components.length; ++i) {
                final int width = components[i].getPreferredSize().width;
                if (n < width) {
                    n = width;
                }
            }
            final int width2 = ((Component)this.footer).getPreferredSize().width;
            if (width2 > n) {
                n = width2;
            }
            return n + 30;
        }
        
        private int getPreferredHeight() {
            if (this.scrollPane.getPreferredSize().height < this.preferredHeight) {
                return this.scrollPane.getPreferredSize().height + ((Component)this.footer).getPreferredSize().height + 10;
            }
            return this.preferredHeight + ((Component)this.footer).getPreferredSize().height;
        }
        
        public Dimension getPreferredSize() {
            return new Dimension(this.getPreferredWidth(), this.getPreferredHeight());
        }
        
        public void selectionChanged(final ScrollablePopupMenuItem scrollablePopupMenuItem, final boolean selected) {
            final Component[] components = this.menuPanel.getComponents();
            final int length = components.length;
            if (!selected) {
                for (int i = length - 1; i >= 0; --i) {
                    ((ScrollablePopupMenuItem)components[i]).setSelected(selected);
                }
            }
            else {
                for (int j = 0; j < length; ++j) {
                    final ScrollablePopupMenuItem scrollablePopupMenuItem2 = (ScrollablePopupMenuItem)components[j];
                    if (scrollablePopupMenuItem2 == scrollablePopupMenuItem) {
                        break;
                    }
                    scrollablePopupMenuItem2.setSelected(true);
                }
            }
            this.footer.setText(this.model.getFooterText() + this.getSelectedItemsCount());
            this.repaint();
        }
        
        public void setModel(final ScrollablePopupMenuModel model) {
            this.model = model;
            this.footer.setText(model.getFooterText());
        }
        
        public ScrollablePopupMenuModel getModel() {
            return this.model;
        }
        
        public int getSelectedItemsCount() {
            int n = 0;
            final Component[] components = this.menuPanel.getComponents();
            for (int i = 0; i < components.length; ++i) {
                if (((ScrollablePopupMenuItem)components[i]).isSelected()) {
                    ++n;
                }
            }
            return n;
        }
        
        public void processItemClicked() {
            this.footer.setText(this.model.getFooterText() + 0);
            this.setVisible(false);
            this.model.processItemClicked();
        }
        
        public JComponent getOwner() {
            return this.ownerComponent;
        }
        
        private void addFooter(final ScrollablePopupMenuItem footer) {
            (this.footer = footer).setEnabled(false);
            this.add((Component)this.footer, "South");
        }
        
        public ScrollablePopupMenuItem getFooter() {
            return this.footer;
        }
        
        public void addListener(final ScrollablePopupMenuListener l) {
            this.eventListeners.add(ScrollablePopupMenuListener.class, l);
        }
        
        public void fireItemsWereAdded(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
            final Object[] listenerList = this.eventListeners.getListenerList();
            for (int length = listenerList.length, i = 0; i < length; i += 2) {
                if (listenerList[i] == ScrollablePopupMenuListener.class) {
                    ((ScrollablePopupMenuListener)listenerList[i + 1]).itemsWereAdded(scrollablePopupMenuEvent);
                }
            }
        }
        
        public void fireItemsWereRemoved(final ScrollablePopupMenuEvent scrollablePopupMenuEvent) {
            final Object[] listenerList = this.eventListeners.getListenerList();
            for (int length = listenerList.length, i = 0; i < length; i += 2) {
                if (listenerList[i] == ScrollablePopupMenuListener.class) {
                    ((ScrollablePopupMenuListener)listenerList[i + 1]).itemsWereRemoved(scrollablePopupMenuEvent);
                }
            }
        }
        
        static {
            ScrollablePopupMenu.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.ScrollablePopupMenuMessages", Locale.getDefault());
            ScrollablePopupMenu.resources = new ResourceManager(ScrollablePopupMenu.bundle);
        }
    }
    
    public interface ScrollablePopupMenuModel
    {
        String getFooterText();
        
        void processItemClicked();
        
        void processBeforeShowed();
        
        void processAfterShowed();
    }
    
    public interface ScrollablePopupMenuItem
    {
        void setSelected(final boolean p0);
        
        boolean isSelected();
        
        String getText();
        
        void setText(final String p0);
        
        void setEnabled(final boolean p0);
    }
    
    public static class DefaultScrollablePopupMenuItem extends JButton implements ScrollablePopupMenuItem
    {
        public static final Color MENU_HIGHLIGHT_BG_COLOR;
        public static final Color MENU_HIGHLIGHT_FG_COLOR;
        public static final Color MENUITEM_BG_COLOR;
        public static final Color MENUITEM_FG_COLOR;
        private ScrollablePopupMenu parent;
        
        public DefaultScrollablePopupMenuItem(final ScrollablePopupMenu parent, final String text) {
            super(text);
            this.parent = parent;
            this.init();
        }
        
        private void init() {
            this.setUI(BasicButtonUI.createUI(this));
            this.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 20));
            this.setMenuItemDefaultColors();
            this.setAlignmentX(0.0f);
            this.setSelected(false);
            this.addMouseListener(new MouseAdapter() {
                private final /* synthetic */ DefaultScrollablePopupMenuItem this$0 = this$0;
                
                public void mouseEntered(final MouseEvent mouseEvent) {
                    if (this.this$0.isEnabled()) {
                        this.this$0.setSelected(true);
                        this.this$0.parent.selectionChanged(this.this$0, true);
                    }
                }
                
                public void mouseExited(final MouseEvent mouseEvent) {
                    if (this.this$0.isEnabled()) {
                        this.this$0.setSelected(false);
                        this.this$0.parent.selectionChanged(this.this$0, false);
                    }
                }
                
                public void mouseClicked(final MouseEvent mouseEvent) {
                    this.this$0.parent.processItemClicked();
                }
            });
        }
        
        private void setMenuItemDefaultColors() {
            this.setBackground(DefaultScrollablePopupMenuItem.MENUITEM_BG_COLOR);
            this.setForeground(DefaultScrollablePopupMenuItem.MENUITEM_FG_COLOR);
        }
        
        public void setSelected(final boolean selected) {
            super.setSelected(selected);
            if (selected) {
                this.setBackground(DefaultScrollablePopupMenuItem.MENU_HIGHLIGHT_BG_COLOR);
                this.setForeground(DefaultScrollablePopupMenuItem.MENU_HIGHLIGHT_FG_COLOR);
            }
            else {
                this.setMenuItemDefaultColors();
            }
        }
        
        public String getText() {
            return super.getText();
        }
        
        public void setText(final String text) {
            super.setText(text);
        }
        
        public void setEnabled(final boolean enabled) {
            super.setEnabled(enabled);
        }
        
        static {
            MENU_HIGHLIGHT_BG_COLOR = UIManager.getColor("MenuItem.selectionBackground");
            MENU_HIGHLIGHT_FG_COLOR = UIManager.getColor("MenuItem.selectionForeground");
            MENUITEM_BG_COLOR = UIManager.getColor("MenuItem.background");
            MENUITEM_FG_COLOR = UIManager.getColor("MenuItem.foreground");
        }
    }
    
    private static class SmallDisabledDownArrow extends SmallDownArrow
    {
        public SmallDisabledDownArrow() {
            this.arrowColor = new Color(140, 140, 140);
        }
        
        public void paintIcon(final Component component, final Graphics graphics, final int n, final int n2) {
            super.paintIcon(component, graphics, n, n2);
            graphics.setColor(Color.white);
            graphics.drawLine(n + 3, n2 + 2, n + 4, n2 + 1);
            graphics.drawLine(n + 3, n2 + 3, n + 5, n2 + 1);
        }
    }
    
    private static class SmallDownArrow implements Icon
    {
        protected Color arrowColor;
        
        private SmallDownArrow() {
            this.arrowColor = Color.black;
        }
        
        public void paintIcon(final Component component, final Graphics graphics, final int n, final int n2) {
            graphics.setColor(this.arrowColor);
            graphics.drawLine(n, n2, n + 4, n2);
            graphics.drawLine(n + 1, n2 + 1, n + 3, n2 + 1);
            graphics.drawLine(n + 2, n2 + 2, n + 2, n2 + 2);
        }
        
        public int getIconWidth() {
            return 6;
        }
        
        public int getIconHeight() {
            return 4;
        }
    }
    
    private class DropDownListener extends MouseAdapter
    {
        public void mousePressed(final MouseEvent mouseEvent) {
            if (DropDownComponent.this.popupMenu.isShowing() && DropDownComponent.this.isDropDownEnabled) {
                DropDownComponent.this.popupMenu.setVisible(false);
            }
            else if (DropDownComponent.this.isDropDownEnabled) {
                DropDownComponent.this.popupMenu.showMenu((Component)mouseEvent.getSource(), DropDownComponent.this);
            }
        }
        
        public void mouseEntered(final MouseEvent mouseEvent) {
            DropDownComponent.this.dropDownButton.setBorderPainted(true);
        }
        
        public void mouseExited(final MouseEvent mouseEvent) {
            DropDownComponent.this.dropDownButton.setBorderPainted(false);
        }
    }
}
