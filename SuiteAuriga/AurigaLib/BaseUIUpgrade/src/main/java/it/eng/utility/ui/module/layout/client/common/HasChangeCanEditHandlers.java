/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasChangeCanEditHandlers extends HasHandlers {
   
    HandlerRegistration addChangeCanEditHandler(ChangeCanEditHandler handler);
    
}
