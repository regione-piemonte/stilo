package org.jpedal.examples.viewer.javabean;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;
import org.jpedal.examples.viewer.Values;

public class ViewerBean extends JPanel {
	private Viewer viewer;
	
	private File document = null;
	private Integer pageNumber = null;
	private Integer rotation = null;
	private Integer zoom = null;

	private Boolean isMenuBarVisible = null;
	private Boolean isToolBarVisible = null;
	private Boolean isDisplayOptionsBarVisible = null;
	private Boolean isSideTabBarVisible = null;
	private Boolean isNavigationBarVisible = null;
	
	public ViewerBean() {
        viewer = new Viewer(this, Viewer.PREFERENCES_BEAN);
        viewer.setupViewer();
	}
	
    public Viewer getViewer() {
    	return viewer;
    }
    
    // Document ////////
	public void setDocument(final File document) {
		this.document = document;
		
		excuteCommand(Commands.OPENFILE, new String[] { 
				String.valueOf(document) });
		
		if(pageNumber != null) {
			excuteCommand(Commands.GOTO, new String[] { 
				String.valueOf(pageNumber) });
		}
		
		if(rotation != null) {
			excuteCommand(Commands.ROTATION, new String[] { 
				String.valueOf(rotation) });
		}
		
		if(zoom != null) {
			excuteCommand(Commands.SCALING, new String[] { 
				String.valueOf(zoom) });
		} else {
			excuteCommand(Commands.SCALING, new String[] { 
					String.valueOf(100) });
		}
		
		if(isMenuBarVisible != null) {
			setMenuBar(isMenuBarVisible);
		}
		
		if(isToolBarVisible != null) {
			setToolBar(isToolBarVisible);
		}
		
		if(isDisplayOptionsBarVisible != null) {
			setDisplayOptionsBar(isDisplayOptionsBarVisible);
		}
		
		if(isSideTabBarVisible != null) {
			setSideTabBar(isSideTabBarVisible);
		}
		
		if(isNavigationBarVisible != null) {
			setNavigationBar(isNavigationBarVisible);
		}
	}
	
	// Page Number ////////
	public int getPageNumber() {
		if(pageNumber == null)
			return 1;
		else
			return pageNumber;
	}
	
	public void setPageNumber(final int pageNumber) {
		this.pageNumber = pageNumber;
		
		if(document != null) {
			excuteCommand(Commands.GOTO, new String[] { 
				String.valueOf(pageNumber) });
		}
	}

	// Rotation ////////
	public int getRotation() {
		if(rotation == null)
			return 0;
		else
			return rotation;
	}

	public void setRotation(final int rotation) {
		this.rotation = rotation;

		if(document != null) {
			excuteCommand(Commands.ROTATION, new String[] { 
				String.valueOf(rotation) });
		}
	}
	
	// Zoom ////////
	public int getZoom() {
		if(zoom == null)
			return 100;
		else
			return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		
		if(document != null) {
			excuteCommand(Commands.SCALING, new String[] { 
				String.valueOf(zoom) });
		}
	}

	//setToolBar, setDisplayOptionsBar, setSideTabBar, setNavigationBar, 
	public void setMenuBar(boolean visible) {
		this.isMenuBarVisible = visible;
		
		//if(document != null)
			viewer.executeCommand(Commands.UPDATEGUILAYOUT, new Object[] {"ShowMenubar", visible});
	}
	
	public boolean getMenuBar() {
		if(isMenuBarVisible == null)
			return true;
		else
			return isMenuBarVisible;
	}
	
	public void setToolBar(boolean visible) {
		this.isToolBarVisible = visible;
		
		//@kieran
        //I did not write this class so not familiar with it
        //Did you write or or Simon?
        //is a null document goint to cause any issues in MAtisse?
		//if(document != null)
			viewer.executeCommand(Commands.UPDATEGUILAYOUT, new Object[] {"ShowButtons", visible});
	}
	
	public boolean getToolBar() {
		if(isToolBarVisible == null)
			return true;
		else
			return isToolBarVisible;
	}
	
	public void setDisplayOptionsBar(boolean visible) {
		this.isDisplayOptionsBarVisible = visible;
		
		//if(document != null)
			viewer.executeCommand(Commands.UPDATEGUILAYOUT, new Object[] {"ShowDisplayoptions", visible});
	}
	
	public boolean getDisplayOptionsBar() {
		if(isDisplayOptionsBarVisible == null)
			return true;
		else
			return isDisplayOptionsBarVisible;
	}
	
	public void setSideTabBar(boolean visible) {
		this.isSideTabBarVisible = visible;
		
		//if(document != null)
			viewer.executeCommand(Commands.UPDATEGUILAYOUT, new Object[] {"ShowSidetabbar", visible});
	}
	
	public boolean getSideTabBar() {
		if(isSideTabBarVisible == null)
			return true;
		else
			return isSideTabBarVisible;
	}
	
	public void setNavigationBar(boolean visible) {
		this.isNavigationBarVisible = visible;
		
		//if(document != null)
			viewer.executeCommand(Commands.UPDATEGUILAYOUT, new Object[] {"ShowNavigationbar", visible});
	}
	
	public boolean getNavigationBar() {
		if(isNavigationBarVisible == null)
			return true;
		else
			return isNavigationBarVisible;
	}
	
	private void excuteCommand(final int command, final Object[] input) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				viewer.executeCommand(command, input);
				
				while(Values.isProcessing()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				repaint();
			}
		});
	}
	
//	// Page Layout ////////
//	private String pageLayout = "Single";
//	
//	public String getPageLayout() {
//		return pageLayout;
//	}
//
//	public void setPageLayout(String pageLayout) {
//		this.pageLayout = pageLayout;
//	}
}