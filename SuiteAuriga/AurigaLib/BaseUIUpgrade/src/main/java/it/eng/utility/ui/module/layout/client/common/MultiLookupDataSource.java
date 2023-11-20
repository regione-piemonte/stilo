/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.*;

public class MultiLookupDataSource extends DataSource {

    public MultiLookupDataSource() {
    	
    	DataSourceTextField id = new DataSourceTextField("id");   
 	    id.setHidden(true);   
 	    id.setPrimaryKey(true);    
 	    
 	    DataSourceImageField icona = new DataSourceImageField("icona");   
 	    
 	    DataSourceTextField nome = new DataSourceTextField("nome");  
 	    
 	    setFields(id, icona, nome);   	    
 	    
        setClientOnly(true);
    }
}
 