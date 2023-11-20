/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.FilterTypeBean;

public abstract class CustomDataSourceField extends DataSourceField {
	
	protected Map<String, String> property = new HashMap<String, String>();
	
	public CustomDataSourceField(FieldType type) {
		setType(type);
        init();
        setOperators();        
    }
	
	public CustomDataSourceField(String name, FieldType type) {
        super(name, type);
        init();
        setOperators();        
    }

    public CustomDataSourceField(String name, FieldType type, String title) {
    	super(name, type, title);
    	init();
        setOperators();  
    }
    
    public CustomDataSourceField(String name, FieldType type, String title, Map<String, String> property) {
    	super(name, type, title);
    	this.property = property;
    	init();
        setOperators();  
    }


    public CustomDataSourceField(String name, FieldType type, String title, int length, Map<String, String> property) {
    	super(name, type, title, length );
    	this.property = property;
    	init();
        setOperators();  
    }
    
    public CustomDataSourceField(String name, FieldType type, String title, int length) {
    	super(name, type, title, length );
    	init();
        setOperators();  
    }

    public CustomDataSourceField(String name, FieldType type, String title, int length, boolean required) {
    	super(name, type, title, length, required);
    	init();
        setOperators();  
    }
    
    protected void setOperators() {
    	if(getAttribute("customType") != null && !"".equals(getAttribute("customType"))) {
    		FilterTypeBean config = Layout.getFilterTypeConfig(getAttribute("customType"));
			if(config != null) {
				List<OperatorId> operators = new ArrayList<OperatorId>();
				for(String op : config.getOperators()) {
					operators.add(OperatorId.valueOf(op));
				}
				setValidOperators(operators.toArray(new OperatorId[operators.size()]));    			
    		}  
    	}       			
    }
	
	protected abstract void init();

}
