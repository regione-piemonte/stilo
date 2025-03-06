package it.eng.utility.cryptosigner.storage.impl.filesystem;

import java.io.Serializable;
import java.util.StringTokenizer;

public class CertBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2067913353268175274L;
	
	private String cn;
	private String o;
	private String ou;
	private String c;
	private String country;
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getOu() {
		return ou;
	}
	public void setOu(String ou) {
		this.ou = ou;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((cn == null) ? 0 : cn.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((o == null) ? 0 : o.hashCode());
		result = prime * result + ((ou == null) ? 0 : ou.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CertBean other = (CertBean) obj;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (cn == null) {
			if (other.cn != null)
				return false;
		} else if (!cn.equals(other.cn))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (o == null) {
			if (other.o != null)
				return false;
		} else if (!o.equals(other.o))
			return false;
		if (ou == null) {
			if (other.ou != null)
				return false;
		} else if (!ou.equals(other.ou))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CertBean [country=" + country + " cn=" + cn + ", o=" + o + ", ou=" + ou + ", c=" + c + " ]";
	}
	
	public static CertBean parse(String s, String country){
		CertBean bean = new CertBean();
		StringTokenizer tokenizer = new StringTokenizer(s, ",");
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken();
			if( token!=null){
				String[] chiaveValore = token.trim().split("=");
				if( chiaveValore!=null && chiaveValore.length==2){
					String chiave = chiaveValore[0];
					String valore = chiaveValore[1];
					
					if(chiave.toLowerCase().equalsIgnoreCase("cn")){
						bean.setCn(valore);
					}
					if(chiave.toLowerCase().equalsIgnoreCase("o")){
						bean.setO(valore);
					}
					if(chiave.toLowerCase().equalsIgnoreCase("ou")){
						bean.setOu(valore);
					}
					if(chiave.toLowerCase().equalsIgnoreCase("c")){
						bean.setC(valore);
					}
				}
			}
		}
		if( country!=null ){
			bean.setCountry(country);
		}
		
		return bean;
	}
	
	public static void main(String[] args) {
		String s = "CN=ArubaPEC S.p.A. NG TSA 1 EIDAS, OU=Qualified Time Stamping Authority, O=ArubaPEC S.p.A., C=IT";
		CertBean bean = parse(s, "IT");
		System.out.println(bean);
		
	}
	
	
}
