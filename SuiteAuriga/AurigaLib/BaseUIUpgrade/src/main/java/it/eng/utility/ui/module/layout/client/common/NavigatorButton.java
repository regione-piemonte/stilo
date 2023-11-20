/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class NavigatorButton extends ToolStripButton {
	
	private NavigatorLayout navigator; 
	private TreeNodeBean node;	
	
	public NavigatorButton(String title, NavigatorLayout pNavigator) {		
        super(title);
		this.navigator = pNavigator;
        addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				navigator.getLayout().esplora(node.getIdNode());
			}
		});
        setHeight(28);
        setPadding(1);
        setBaseStyle(it.eng.utility.Styles.navigatorButton);
    }

	public TreeNodeBean getNode() {
		return node;
	}
	public void setNode(TreeNodeBean node) {
		this.node = node;
	}	

}
