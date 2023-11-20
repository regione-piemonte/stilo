/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class NumericItem extends com.smartgwt.client.widgets.form.fields.TextItem {
	
	private Integer nroDecimali; 

	public NumericItem() {
		this(true);
	}
	
	public NumericItem(String name) {
		this(name, true);	            
    }

    public NumericItem(String name, String title) {
    	this(name, title, true);
    }
    
    public NumericItem(boolean hasFloat) {
		this(hasFloat, null);
	}
	
	public NumericItem(String name, boolean hasFloat) {
		this(name, hasFloat, null);	            
    }

    public NumericItem(String name, String title, boolean hasFloat) {
    	this(name, title, hasFloat, null);
    }
    
    public NumericItem(boolean hasFloat, Integer nroDecimali) {
//		this.setHeight(20);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setTabIndex(null);
    		setCanFocus(true);
 		}
		this.setWidth(100);   
		if(hasFloat) {
			this.setKeyPressFilter("[0-9,]");
			if(nroDecimali != null) {
				this.nroDecimali = nroDecimali;		
				setValidators(buildFloatPrecisionValidator(nroDecimali));		    	
			}
		} else {			
			this.setKeyPressFilter("[0-9]");
		}
		this.setTextAlign(Alignment.RIGHT);	
//		setTextBoxStyle(it.eng.utility.Styles.textItemNumber);
	}
	
	public NumericItem(String name, boolean hasFloat, Integer nroDecimali) {
		this(hasFloat, nroDecimali);
	    setName(name);        
    }

    public NumericItem(String name, String title, boolean hasFloat, Integer nroDecimali) {
    	this(hasFloat, nroDecimali);
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
 	//    	setCanFocus(canEdit ? true : false); 			
    		setCanFocus(true);
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}    	
    }
    
    @Override
    public void setValidators(Validator... validators) {
    	if(nroDecimali != null && nroDecimali.intValue() > 0) {  
    		List<Validator> listValidators = new ArrayList<Validator>();
        	if(validators != null && validators.length > 0) {
        		listValidators.addAll(Arrays.asList(validators));
        	}
    		listValidators.add(buildFloatPrecisionValidator(nroDecimali));
    		super.setValidators(listValidators.toArray(new Validator[0]));
    	} else {
    		super.setValidators(validators);
    	}
    }
    
    private RegExpValidator buildFloatPrecisionValidator(Integer nroDecimali) {
		RegExpValidator precisionValidator = new RegExpValidator();
		precisionValidator.setExpression("^([0-9]+(\\,[0-9]{1," + nroDecimali + "})?)$");
		precisionValidator.setErrorMessage("Valore non valido: " + nroDecimali + " cifre decimali");
		return precisionValidator;
	}    
	
}
