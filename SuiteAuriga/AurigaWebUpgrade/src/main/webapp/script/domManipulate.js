/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
	     var holder;
		 var tag;
		 var eventSomething;
	     //IE uses this
	     if(window.event){    	 	
	     	holder=window.event.keyCode;
			eventSomething = window.event;				
	     }
	     //FF uses this
	     else{
	        holder=event.which;
			eventSomething = event;				
	     } 
		 var x = eventSomething.target||eventSomething.srcElement;
		 var tagName = x.tagName;		
		 if (holder == 8) {			
		 	if (tagName.toUpperCase()!="INPUT" && tagName.toUpperCase()!="TEXTAREA") {
		 		return false;	
			} else {
				if (x.className=="readonlyItem" || x.className=="readonlyItemFocused") return false;
			}			
		 }
}