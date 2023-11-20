/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TParametersBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private Date dateValue;
	private BigDecimal numValue;
	private String parKey;
	private Date settingTime;
	private String strValue;
	private Date tsIns;

	public Date getDateValue(){ return dateValue;}

	public void setDateValue(Date dateValue){ this.dateValue=dateValue;}

	public BigDecimal getNumValue(){ return numValue;}

	public void setNumValue(BigDecimal numValue){ this.numValue=numValue;}

	public String getParKey(){ return parKey;}

	public void setParKey(String parKey){ this.parKey=parKey;}

	public Date getSettingTime(){ return settingTime;}

	public void setSettingTime(Date settingTime){ this.settingTime=settingTime;}

	public String getStrValue(){ return strValue;}

	public void setStrValue(String strValue){ this.strValue=strValue;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}


}    