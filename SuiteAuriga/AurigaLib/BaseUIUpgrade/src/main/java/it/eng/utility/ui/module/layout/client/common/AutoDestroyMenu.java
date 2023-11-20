/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.menu.Menu;

public class AutoDestroyMenu extends Menu {
	
	private AutoDestroyMenu instance;
	private Timer timer;
	private boolean afterShow = false;

	public AutoDestroyMenu() {
		instance = this;
		addVisibilityChangedHandler(new VisibilityChangedHandler() {
			
			@Override
			public void onVisibilityChanged(final VisibilityChangedEvent event) {
				if(afterShow) {									
					if(!instance.isVisible()) {
						// Destroy menu when hidden after show
						instance.markForDestroy();
					}
				} else {
					if(timer == null) {
						timer = new Timer() {
							
							@Override
							public void run() {
								if(instance.isVisible()) {
									// Show menu (last event)
									afterShow = true;								
								} else {
									// Destroy menu when hidden before show
									instance.markForDestroy();
								}
							}
						};	
					}
					if(timer.isRunning()) {
						timer.cancel();							
					} 
					timer.schedule(1000);														
				}
			}
		});
	}
}
