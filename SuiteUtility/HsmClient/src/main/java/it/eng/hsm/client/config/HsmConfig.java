/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class HsmConfig {

	private HsmType hsmType;
	
	private ClientConfig clientConfig;
//	private MedasConfig medasConfig;
//	private ArubaConfig arubaConfig;
//	private InfoCertConfig infoCertConfig;
	
	public HsmType getHsmType() {
		return hsmType;
	}

	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public void setHsmType(HsmType hsmType) {
		this.hsmType = hsmType;
	}
//
//	public MedasConfig getMedasConfig() {
//		return medasConfig;
//	}
//
//	public void setMedasConfig(MedasConfig medasConfig) {
//		this.medasConfig = medasConfig;
//	}
//
//	public ArubaConfig getArubaConfig() {
//		return arubaConfig;
//	}
//
//	public void setArubaConfig(ArubaConfig arubaConfig) {
//		this.arubaConfig = arubaConfig;
//	}
//
//	public InfoCertConfig getInfoCertConfig() {
//		return infoCertConfig;
//	}
//
//	public void setInfoCertConfig(InfoCertConfig infoCertConfig) {
//		this.infoCertConfig = infoCertConfig;
//	}

	
		
}
