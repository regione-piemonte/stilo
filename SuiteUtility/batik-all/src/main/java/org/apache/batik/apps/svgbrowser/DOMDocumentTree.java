// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.Cursor;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetEvent;
import javax.swing.JRootPane;
import java.io.IOException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetDragEvent;
import javax.swing.UIManager;
import java.awt.Graphics;
import javax.swing.tree.TreePath;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Iterator;
import java.util.EventListener;
import java.util.EventObject;
import org.apache.batik.dom.util.DOMUtilities;
import java.util.ArrayList;
import org.w3c.dom.Node;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.SwingUtilities;
import javax.swing.JViewport;
import java.awt.Point;
import java.awt.dnd.DropTargetListener;
import java.awt.Component;
import java.awt.dnd.DropTarget;
import javax.swing.tree.TreeNode;
import java.awt.Insets;
import javax.swing.event.EventListenerList;
import java.awt.dnd.Autoscroll;
import javax.swing.JTree;

public class DOMDocumentTree extends JTree implements Autoscroll
{
    protected EventListenerList eventListeners;
    protected Insets autoscrollInsets;
    protected Insets scrollUnits;
    protected DOMDocumentTreeController controller;
    
    public DOMDocumentTree(final TreeNode root, final DOMDocumentTreeController controller) {
        super(root);
        this.eventListeners = new EventListenerList();
        this.autoscrollInsets = new Insets(20, 20, 20, 20);
        this.scrollUnits = new Insets(25, 25, 25, 25);
        this.controller = controller;
        new TreeDragSource(this, 3);
        new DropTarget(this, new TreeDropTargetListener(this));
    }
    
    public void autoscroll(final Point point) {
        final JViewport viewport = (JViewport)SwingUtilities.getAncestorOfClass(JViewport.class, this);
        if (viewport == null) {
            return;
        }
        final Point viewPosition = viewport.getViewPosition();
        final int height = viewport.getExtentSize().height;
        final int width = viewport.getExtentSize().width;
        if (point.y - viewPosition.y < this.autoscrollInsets.top) {
            viewport.setViewPosition(new Point(viewPosition.x, Math.max(viewPosition.y - this.scrollUnits.top, 0)));
            this.fireOnAutoscroll(new DOMDocumentTreeEvent(this));
        }
        else if (viewPosition.y + height - point.y < this.autoscrollInsets.bottom) {
            viewport.setViewPosition(new Point(viewPosition.x, Math.min(viewPosition.y + this.scrollUnits.bottom, this.getHeight() - height)));
            this.fireOnAutoscroll(new DOMDocumentTreeEvent(this));
        }
        else if (point.x - viewPosition.x < this.autoscrollInsets.left) {
            viewport.setViewPosition(new Point(Math.max(viewPosition.x - this.scrollUnits.left, 0), viewPosition.y));
            this.fireOnAutoscroll(new DOMDocumentTreeEvent(this));
        }
        else if (viewPosition.x + width - point.x < this.autoscrollInsets.right) {
            viewport.setViewPosition(new Point(Math.min(viewPosition.x + this.scrollUnits.right, this.getWidth() - width), viewPosition.y));
            this.fireOnAutoscroll(new DOMDocumentTreeEvent(this));
        }
    }
    
    public Insets getAutoscrollInsets() {
        final int height = this.getHeight();
        final int width = this.getWidth();
        return new Insets(height, width, height, width);
    }
    
    public void addListener(final DOMDocumentTreeListener l) {
        this.eventListeners.add(DOMDocumentTreeListener.class, l);
    }
    
    public void fireDropCompleted(final DOMDocumentTreeEvent domDocumentTreeEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == DOMDocumentTreeListener.class) {
                ((DOMDocumentTreeListener)listenerList[i + 1]).dropCompleted(domDocumentTreeEvent);
            }
        }
    }
    
    public void fireOnAutoscroll(final DOMDocumentTreeEvent domDocumentTreeEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == DOMDocumentTreeListener.class) {
                ((DOMDocumentTreeListener)listenerList[i + 1]).onAutoscroll(domDocumentTreeEvent);
            }
        }
    }
    
    protected Node getDomNodeFromTreeNode(final DefaultMutableTreeNode defaultMutableTreeNode) {
        if (defaultMutableTreeNode == null) {
            return null;
        }
        if (defaultMutableTreeNode.getUserObject() instanceof DOMViewer.NodeInfo) {
            return ((DOMViewer.NodeInfo)defaultMutableTreeNode.getUserObject()).getNode();
        }
        return null;
    }
    
    protected ArrayList getNodeListForParent(final ArrayList list, final Node node) {
        final ArrayList<Node> list2 = new ArrayList<Node>();
        for (int size = list.size(), i = 0; i < size; ++i) {
            final Node e = list.get(i);
            if (DOMUtilities.canAppend(e, node)) {
                list2.add(e);
            }
        }
        return list2;
    }
    
    public static class DropCompletedInfo
    {
        protected Node parent;
        protected ArrayList children;
        protected Node sibling;
        
        public DropCompletedInfo(final Node parent, final Node sibling, final ArrayList children) {
            this.parent = parent;
            this.sibling = sibling;
            this.children = children;
        }
        
        public ArrayList getChildren() {
            return this.children;
        }
        
        public Node getParent() {
            return this.parent;
        }
        
        public Node getSibling() {
            return this.sibling;
        }
    }
    
    public static class DOMDocumentTreeAdapter implements DOMDocumentTreeListener
    {
        public void dropCompleted(final DOMDocumentTreeEvent domDocumentTreeEvent) {
        }
        
        public void onAutoscroll(final DOMDocumentTreeEvent domDocumentTreeEvent) {
        }
    }
    
    public static class DOMDocumentTreeEvent extends EventObject
    {
        public DOMDocumentTreeEvent(final Object source) {
            super(source);
        }
    }
    
    public interface DOMDocumentTreeListener extends EventListener
    {
        void dropCompleted(final DOMDocumentTreeEvent p0);
        
        void onAutoscroll(final DOMDocumentTreeEvent p0);
    }
    
    public static class TransferData
    {
        protected ArrayList nodeList;
        
        public TransferData(final ArrayList nodeList) {
            this.nodeList = nodeList;
        }
        
        public ArrayList getNodeList() {
            return this.nodeList;
        }
        
        public String getNodesAsXML() {
            String string = "";
            final Iterator<Node> iterator = this.nodeList.iterator();
            while (iterator.hasNext()) {
                string += DOMUtilities.getXML(iterator.next());
            }
            return string;
        }
    }
    
    public static class TransferableTreeNode implements Transferable
    {
        protected static final DataFlavor NODE_FLAVOR;
        protected static final DataFlavor[] FLAVORS;
        protected TransferData data;
        
        public TransferableTreeNode(final TransferData data) {
            this.data = data;
        }
        
        public synchronized DataFlavor[] getTransferDataFlavors() {
            return TransferableTreeNode.FLAVORS;
        }
        
        public boolean isDataFlavorSupported(final DataFlavor dataFlavor) {
            for (int i = 0; i < TransferableTreeNode.FLAVORS.length; ++i) {
                if (dataFlavor.equals(TransferableTreeNode.FLAVORS[i])) {
                    return true;
                }
            }
            return false;
        }
        
        public synchronized Object getTransferData(final DataFlavor dataFlavor) {
            if (!this.isDataFlavorSupported(dataFlavor)) {
                return null;
            }
            if (dataFlavor.equals(TransferableTreeNode.NODE_FLAVOR)) {
                return this.data;
            }
            if (dataFlavor.equals(DataFlavor.stringFlavor)) {
                return this.data.getNodesAsXML();
            }
            return null;
        }
        
        static {
            NODE_FLAVOR = new DataFlavor(TransferData.class, "TransferData");
            FLAVORS = new DataFlavor[] { TransferableTreeNode.NODE_FLAVOR, DataFlavor.stringFlavor };
        }
    }
    
    public class TreeDropTargetListener implements DropTargetListener
    {
        private static final int BEFORE = 1;
        private static final int AFTER = 2;
        private static final int CURRENT = 3;
        private TransferData transferData;
        private Component originalGlassPane;
        private int visualTipOffset;
        private int visualTipThickness;
        private int positionIndicator;
        private Point startPoint;
        private Point endPoint;
        protected JPanel visualTipGlassPane;
        private Timer expandControlTimer;
        private int expandTimeout;
        private TreePath dragOverTreePath;
        private TreePath treePathToExpand;
        
        public TreeDropTargetListener(final DOMDocumentTree domDocumentTree) {
            this.visualTipOffset = 5;
            this.visualTipThickness = 2;
            this.visualTipGlassPane = new JPanel() {
                private final /* synthetic */ TreeDropTargetListener this$1 = this$1;
                
                public void paint(final Graphics graphics) {
                    graphics.setColor(UIManager.getColor("Tree.selectionBackground"));
                    if (this.this$1.startPoint == null || this.this$1.endPoint == null) {
                        return;
                    }
                    final int x = this.this$1.startPoint.x;
                    final int x2 = this.this$1.endPoint.x;
                    final int y = this.this$1.startPoint.y;
                    for (int i = -this.this$1.visualTipThickness / 2 + ((this.this$1.visualTipThickness % 2 == 0) ? 1 : 0); i <= this.this$1.visualTipThickness / 2; ++i) {
                        graphics.drawLine(x + 2, y + i, x2 - 2, y + i);
                    }
                }
            };
            this.expandTimeout = 1500;
            this.addOnAutoscrollListener(domDocumentTree);
        }
        
        public void dragEnter(final DropTargetDragEvent dropTargetDragEvent) {
            final JTree tree = (JTree)dropTargetDragEvent.getDropTargetContext().getComponent();
            final JRootPane rootPane = tree.getRootPane();
            this.originalGlassPane = rootPane.getGlassPane();
            rootPane.setGlassPane(this.visualTipGlassPane);
            this.visualTipGlassPane.setOpaque(false);
            this.visualTipGlassPane.setVisible(true);
            this.updateVisualTipLine(tree, null);
            try {
                final Transferable transferable = new DropTargetDropEvent(dropTargetDragEvent.getDropTargetContext(), dropTargetDragEvent.getLocation(), 0, 0).getTransferable();
                final DataFlavor[] transferDataFlavors = transferable.getTransferDataFlavors();
                for (int i = 0; i < transferDataFlavors.length; ++i) {
                    if (transferable.isDataFlavorSupported(transferDataFlavors[i])) {
                        this.transferData = (TransferData)transferable.getTransferData(transferDataFlavors[i]);
                        return;
                    }
                }
            }
            catch (UnsupportedFlavorException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        
        public void dragOver(final DropTargetDragEvent dropTargetDragEvent) {
            final JTree tree = (JTree)dropTargetDragEvent.getDropTargetContext().getComponent();
            if (this.getNode(dropTargetDragEvent) != null) {
                this.updatePositionIndicator(dropTargetDragEvent);
                final Point location = dropTargetDragEvent.getLocation();
                final TreePath pathForLocation = tree.getPathForLocation(location.x, location.y);
                final TreeNode nodeForPath = this.getNodeForPath(this.getParentPathForPosition(pathForLocation));
                final TreeNode nodeForPath2 = this.getNodeForPath(this.getSiblingPathForPosition(pathForLocation));
                final Node domNodeFromTreeNode = DOMDocumentTree.this.getDomNodeFromTreeNode((DefaultMutableTreeNode)nodeForPath);
                final Node domNodeFromTreeNode2 = DOMDocumentTree.this.getDomNodeFromTreeNode((DefaultMutableTreeNode)nodeForPath2);
                if (DOMUtilities.canAppendAny(this.transferData.getNodeList(), domNodeFromTreeNode) && !this.transferData.getNodeList().contains(domNodeFromTreeNode2)) {
                    dropTargetDragEvent.acceptDrag(dropTargetDragEvent.getDropAction());
                    this.updateVisualTipLine(tree, pathForLocation);
                    this.dragOverTreePath = pathForLocation;
                    if (!tree.isExpanded(pathForLocation)) {
                        this.scheduleExpand(pathForLocation, tree);
                    }
                }
                else {
                    dropTargetDragEvent.rejectDrag();
                }
            }
            else {
                dropTargetDragEvent.rejectDrag();
            }
        }
        
        public void dropActionChanged(final DropTargetDragEvent dropTargetDragEvent) {
        }
        
        public void drop(final DropTargetDropEvent dropTargetDropEvent) {
            final Point location = dropTargetDropEvent.getLocation();
            final JTree originalGlassPane = (JTree)dropTargetDropEvent.getDropTargetContext().getComponent();
            this.setOriginalGlassPane(originalGlassPane);
            this.dragOverTreePath = null;
            final TreePath pathForLocation = originalGlassPane.getPathForLocation(location.x, location.y);
            final Node domNodeFromTreeNode = DOMDocumentTree.this.getDomNodeFromTreeNode((DefaultMutableTreeNode)this.getNodeForPath(this.getParentPathForPosition(pathForLocation)));
            final Node domNodeFromTreeNode2 = DOMDocumentTree.this.getDomNodeFromTreeNode((DefaultMutableTreeNode)this.getNodeForPath(this.getSiblingPathForPosition(pathForLocation)));
            if (this.transferData != null) {
                DOMDocumentTree.this.fireDropCompleted(new DOMDocumentTreeEvent(new DropCompletedInfo(domNodeFromTreeNode, domNodeFromTreeNode2, DOMDocumentTree.this.getNodeListForParent(this.transferData.getNodeList(), domNodeFromTreeNode))));
                dropTargetDropEvent.dropComplete(true);
                return;
            }
            dropTargetDropEvent.rejectDrop();
        }
        
        public void dragExit(final DropTargetEvent dropTargetEvent) {
            this.setOriginalGlassPane((JTree)dropTargetEvent.getDropTargetContext().getComponent());
            this.dragOverTreePath = null;
        }
        
        private void updatePositionIndicator(final DropTargetDragEvent dropTargetDragEvent) {
            final Point location = dropTargetDragEvent.getLocation();
            final JTree tree = (JTree)dropTargetDragEvent.getDropTargetContext().getComponent();
            final Rectangle pathBounds = tree.getPathBounds(tree.getPathForLocation(location.x, location.y));
            if (location.y <= pathBounds.y + this.visualTipOffset) {
                this.positionIndicator = 1;
            }
            else if (location.y >= pathBounds.y + pathBounds.height - this.visualTipOffset) {
                this.positionIndicator = 2;
            }
            else {
                this.positionIndicator = 3;
            }
        }
        
        private TreePath getParentPathForPosition(final TreePath treePath) {
            if (treePath == null) {
                return null;
            }
            TreePath treePath2 = null;
            if (this.positionIndicator == 2) {
                treePath2 = treePath.getParentPath();
            }
            else if (this.positionIndicator == 1) {
                treePath2 = treePath.getParentPath();
            }
            else if (this.positionIndicator == 3) {
                treePath2 = treePath;
            }
            return treePath2;
        }
        
        private TreePath getSiblingPathForPosition(final TreePath treePath) {
            final TreePath parentPathForPosition = this.getParentPathForPosition(treePath);
            TreePath pathByAddingChild = null;
            if (this.positionIndicator == 2) {
                final TreeNode nodeForPath = this.getNodeForPath(parentPathForPosition);
                final TreeNode nodeForPath2 = this.getNodeForPath(treePath);
                if (parentPathForPosition != null && nodeForPath != null && nodeForPath2 != null) {
                    final int n = nodeForPath.getIndex(nodeForPath2) + 1;
                    if (nodeForPath.getChildCount() > n) {
                        pathByAddingChild = parentPathForPosition.pathByAddingChild(nodeForPath.getChildAt(n));
                    }
                }
            }
            else if (this.positionIndicator == 1) {
                pathByAddingChild = treePath;
            }
            else if (this.positionIndicator == 3) {
                pathByAddingChild = null;
            }
            return pathByAddingChild;
        }
        
        private TreeNode getNodeForPath(final TreePath treePath) {
            if (treePath == null || treePath.getLastPathComponent() == null) {
                return null;
            }
            return (TreeNode)treePath.getLastPathComponent();
        }
        
        private TreeNode getNode(final DropTargetDragEvent dropTargetDragEvent) {
            final Point location = dropTargetDragEvent.getLocation();
            final TreePath pathForLocation = ((JTree)dropTargetDragEvent.getDropTargetContext().getComponent()).getPathForLocation(location.x, location.y);
            if (pathForLocation == null || pathForLocation.getLastPathComponent() == null) {
                return null;
            }
            return (TreeNode)pathForLocation.getLastPathComponent();
        }
        
        private void updateVisualTipLine(final JTree tree, final TreePath path) {
            if (path == null) {
                this.startPoint = null;
                this.endPoint = null;
            }
            else {
                final Rectangle pathBounds = tree.getPathBounds(path);
                if (this.positionIndicator == 1) {
                    this.startPoint = pathBounds.getLocation();
                    this.endPoint = new Point(this.startPoint.x + pathBounds.width, this.startPoint.y);
                }
                else if (this.positionIndicator == 2) {
                    this.startPoint = new Point(pathBounds.x, pathBounds.y + pathBounds.height);
                    this.endPoint = new Point(this.startPoint.x + pathBounds.width, this.startPoint.y);
                    this.positionIndicator = 2;
                }
                else if (this.positionIndicator == 3) {
                    this.startPoint = null;
                    this.endPoint = null;
                }
                if (this.startPoint != null && this.endPoint != null) {
                    this.startPoint = SwingUtilities.convertPoint(tree, this.startPoint, this.visualTipGlassPane);
                    this.endPoint = SwingUtilities.convertPoint(tree, this.endPoint, this.visualTipGlassPane);
                }
            }
            this.visualTipGlassPane.getRootPane().repaint();
        }
        
        private void addOnAutoscrollListener(final DOMDocumentTree domDocumentTree) {
            domDocumentTree.addListener(new DOMDocumentTreeAdapter() {
                private final /* synthetic */ TreeDropTargetListener this$1 = this$1;
                
                public void onAutoscroll(final DOMDocumentTreeEvent domDocumentTreeEvent) {
                    this.this$1.startPoint = null;
                    this.this$1.endPoint = null;
                }
            });
        }
        
        private void setOriginalGlassPane(final JTree tree) {
            final JRootPane rootPane = tree.getRootPane();
            rootPane.setGlassPane(this.originalGlassPane);
            this.originalGlassPane.setVisible(false);
            rootPane.repaint();
        }
        
        private void scheduleExpand(final TreePath treePathToExpand, final JTree tree) {
            if (treePathToExpand != this.treePathToExpand) {
                this.getExpandTreeTimer(tree).stop();
                this.treePathToExpand = treePathToExpand;
                this.getExpandTreeTimer(tree).start();
            }
        }
        
        private Timer getExpandTreeTimer(final JTree tree) {
            if (this.expandControlTimer == null) {
                this.expandControlTimer = new Timer(this.expandTimeout, new ActionListener() {
                    private final /* synthetic */ TreeDropTargetListener this$1 = this$1;
                    
                    public void actionPerformed(final ActionEvent actionEvent) {
                        if (this.this$1.treePathToExpand != null && this.this$1.treePathToExpand == this.this$1.dragOverTreePath) {
                            tree.expandPath(this.this$1.treePathToExpand);
                        }
                        this.this$1.getExpandTreeTimer(tree).stop();
                    }
                });
            }
            return this.expandControlTimer;
        }
    }
    
    public class TreeDragSource implements DragSourceListener, DragGestureListener
    {
        protected DragSource source;
        protected DragGestureRecognizer recognizer;
        protected TransferableTreeNode transferable;
        protected DOMDocumentTree sourceTree;
        
        public TreeDragSource(final DOMDocumentTree sourceTree, final int actions) {
            this.sourceTree = sourceTree;
            this.source = new DragSource();
            this.recognizer = this.source.createDefaultDragGestureRecognizer(this.sourceTree, actions, this);
        }
        
        public void dragGestureRecognized(final DragGestureEvent trigger) {
            if (!DOMDocumentTree.this.controller.isDNDSupported()) {
                return;
            }
            final TreePath[] selectionPaths = this.sourceTree.getSelectionPaths();
            if (selectionPaths == null) {
                return;
            }
            final ArrayList<Node> list = new ArrayList<Node>();
            for (int i = 0; i < selectionPaths.length; ++i) {
                final TreePath treePath = selectionPaths[i];
                if (treePath.getPathCount() > 1) {
                    final Node domNodeFromTreeNode = DOMDocumentTree.this.getDomNodeFromTreeNode((DefaultMutableTreeNode)treePath.getLastPathComponent());
                    if (domNodeFromTreeNode != null) {
                        list.add(domNodeFromTreeNode);
                    }
                }
            }
            if (list.isEmpty()) {
                return;
            }
            this.transferable = new TransferableTreeNode(new TransferData(list));
            this.source.startDrag(trigger, null, this.transferable, this);
        }
        
        public void dragEnter(final DragSourceDragEvent dragSourceDragEvent) {
        }
        
        public void dragExit(final DragSourceEvent dragSourceEvent) {
        }
        
        public void dragOver(final DragSourceDragEvent dragSourceDragEvent) {
        }
        
        public void dropActionChanged(final DragSourceDragEvent dragSourceDragEvent) {
        }
        
        public void dragDropEnd(final DragSourceDropEvent dragSourceDropEvent) {
        }
    }
}
