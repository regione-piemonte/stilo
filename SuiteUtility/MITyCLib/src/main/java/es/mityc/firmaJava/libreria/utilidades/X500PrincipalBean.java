package es.mityc.firmaJava.libreria.utilidades;

import java.util.StringTokenizer;

public class X500PrincipalBean {

	private String cn;
	private String ou;
	private String o;
	private String c;
	private String l;
	private String st;
	private String street;
	private String uid;
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getOu() {
		return ou;
	}
	public void setOu(String ou) {
		this.ou = ou;
	}
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public static X500PrincipalBean load(String s){
		X500PrincipalBean bean = new X500PrincipalBean();
		
		StringTokenizer tokenizer = new StringTokenizer(s, ",");
		while(tokenizer.hasMoreTokens() ){
			String token = tokenizer.nextToken();
			System.out.println(token);
			
			String[] ss = token.split("=");
			if( ss[0].equalsIgnoreCase("CN")){
				bean.setCn(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("O")){
				bean.setO(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("C")){
				bean.setC(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("OU")){
				bean.setOu(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("L")){
				bean.setL(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("STREET")){
				bean.setStreet(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("UID")){
				bean.setUid(ss[1]);
			}
			if( ss[0].equalsIgnoreCase("ST")){
				bean.setSt(ss[1]);
			}
		}
		
		return bean;
		
	}
	
	@Override
	public String toString() {
		return "X500PrincipalBean [cn=" + cn + ", ou=" + ou + ", o=" + o + ", c=" + c + ", l=" + l + ", st=" + st
				+ ", street=" + street + ", uid=" + uid + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((cn == null) ? 0 : cn.hashCode());
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		result = prime * result + ((o == null) ? 0 : o.hashCode());
		result = prime * result + ((ou == null) ? 0 : ou.hashCode());
		result = prime * result + ((st == null) ? 0 : st.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		X500PrincipalBean other = (X500PrincipalBean) obj;
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
		if (l == null) {
			if (other.l != null)
				return false;
		} else if (!l.equals(other.l))
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
		if (st == null) {
			if (other.st != null)
				return false;
		} else if (!st.equals(other.st))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}
