/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class LoadComboDominioBean {

	private List<DominioBean> domini = new ArrayList<DominioBean>();
	
	public void add(DominioBean bean){
		domini.add(bean);
	}

	public List<DominioBean> getDomini() {
		return domini;
	}

	public void setDomini(List<DominioBean> domini) {
		this.domini = domini;
	}
	
	
}
