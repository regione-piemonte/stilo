/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.layout.HLayout;

public class PortalLayout extends HLayout {  
    
	private List<String> list = new ArrayList<String>();
	
	public PortalLayout(int numColumns) {  
        setMembersMargin(6);  
        for (int i = 0; i < numColumns; i++) {  
            addMember(new PortalColumn());  
        }  
        setBorder("1px solid gray");        
    }  

    public PortalColumn addPortlet(Portlet portlet) {  
        // find the column with the fewest portlets  
        int fewestPortlets = Integer.MAX_VALUE;  
        PortalColumn fewestPortletsColumn = null;  
        for (int i = 0; i < getMembers().length; i++) {  
            int numPortlets = ((PortalColumn) getMember(i)).getMembers().length;  
            if (numPortlets < fewestPortlets) {  
                fewestPortlets = numPortlets;  
                fewestPortletsColumn = (PortalColumn) getMember(i);  
            }  
        }  
        fewestPortletsColumn.addMember(portlet);  
        list.add("SIZE_"+(list.size()+1));
        return fewestPortletsColumn;  
    } 
    
    
    public int viewsize(){
    	return list.size();
    }
 
    
}
