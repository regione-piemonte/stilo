/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.util.List;
import java.util.Map;

public class CompilaModelloPerVisureBean {

	private String processId;

	private String uriModello;
	private String tipoModello;
	private String uriModCopertina;
	private String tipoModCopertina;
	private String uriModCopertinaFinale;
	private String tipoModCopertinaFinale;
	private String uriModAppendice;
	private String tipoModAppendice;
	private String estensioneFileDaGenerare;

	private String uri;
	private String nomeFile;
	private MimeTypeFirmaBean infoFile;

	private Boolean flgNoPubblPrimario;
	private AllegatoProtocolloBean filePrimarioVerPubbl;
	private Boolean flgDatiSensibili;
	private DocumentBean filePrimarioOmissis;
	private List<AllegatoProtocolloBean> listaAllegati;

	private String oggetto;
	private List<DestInvioCCBean> listaUoCoinvolte;

	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	private Map<String, String> colonneListe;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getUriModello() {
		return uriModello;
	}

	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getUriModCopertina() {
		return uriModCopertina;
	}

	public void setUriModCopertina(String uriModCopertina) {
		this.uriModCopertina = uriModCopertina;
	}

	public String getTipoModCopertina() {
		return tipoModCopertina;
	}

	public void setTipoModCopertina(String tipoModCopertina) {
		this.tipoModCopertina = tipoModCopertina;
	}

	public String getUriModCopertinaFinale() {
		return uriModCopertinaFinale;
	}

	public void setUriModCopertinaFinale(String uriModCopertinaFinale) {
		this.uriModCopertinaFinale = uriModCopertinaFinale;
	}

	public String getTipoModCopertinaFinale() {
		return tipoModCopertinaFinale;
	}

	public void setTipoModCopertinaFinale(String tipoModCopertinaFinale) {
		this.tipoModCopertinaFinale = tipoModCopertinaFinale;
	}

	public String getUriModAppendice() {
		return uriModAppendice;
	}

	public void setUriModAppendice(String uriModAppendice) {
		this.uriModAppendice = uriModAppendice;
	}

	public String getTipoModAppendice() {
		return tipoModAppendice;
	}

	public void setTipoModAppendice(String tipoModAppendice) {
		this.tipoModAppendice = tipoModAppendice;
	}
	
	public String getEstensioneFileDaGenerare() {
		return estensioneFileDaGenerare;
	}
	
	public void setEstensioneFileDaGenerare(String estensioneFileDaGenerare) {
		this.estensioneFileDaGenerare = estensioneFileDaGenerare;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public List<DestInvioCCBean> getListaUoCoinvolte() {
		return listaUoCoinvolte;
	}

	public void setListaUoCoinvolte(List<DestInvioCCBean> listaUoCoinvolte) {
		this.listaUoCoinvolte = listaUoCoinvolte;
	}

	public Map<String, Object> getValori() {
		return valori;
	}

	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}

	public Map<String, String> getTipiValori() {
		return tipiValori;
	}

	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	
	public Map<String, String> getColonneListe() {
		return colonneListe;
	}
	
	public void setColonneListe(Map<String, String> colonneListe) {
		this.colonneListe = colonneListe;
	}

	public Boolean getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}

	public void setFlgNoPubblPrimario(Boolean flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}

	public AllegatoProtocolloBean getFilePrimarioVerPubbl() {
		return filePrimarioVerPubbl;
	}

	public void setFilePrimarioVerPubbl(AllegatoProtocolloBean filePrimarioVerPubbl) {
		this.filePrimarioVerPubbl = filePrimarioVerPubbl;
	}

	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}

	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}

	public DocumentBean getFilePrimarioOmissis() {
		return filePrimarioOmissis;
	}

	public void setFilePrimarioOmissis(DocumentBean filePrimarioOmissis) {
		this.filePrimarioOmissis = filePrimarioOmissis;
	}
	
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

}
