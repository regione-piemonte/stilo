/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

public class FileDaTimbrareBean extends Record {

	public FileDaTimbrareBean(String uri, String nomeFile, boolean remote, String mimetype, String idUd,String rotazioneTimbro,String posizioneTimbro)
	{
		setUri(uri);
		setNomeFile(nomeFile);
		setRemote(remote);
		setMimetype(mimetype);
		setIdUd(idUd);
		setRotazioneTimbroPref(rotazioneTimbro);
		setPosizioneTimbroPref(posizioneTimbro);
	}
	
	public FileDaTimbrareBean(String uri, String nomeFile, boolean remote, String mimetype, String idUd,String idDoc,String rotazioneTimbro,String posizioneTimbro)
	{
		setUri(uri);
		setNomeFile(nomeFile);
		setRemote(remote);
		setMimetype(mimetype);
		setIdUd(idUd);
		setIdDoc(idDoc);
		setRotazioneTimbroPref(rotazioneTimbro);
		setPosizioneTimbroPref(posizioneTimbro);
	}
	
	public FileDaTimbrareBean(String uri, String nomeFile, boolean remote, String mimetype, String idUd,String idDoc,String rotazioneTimbro,String posizioneTimbro,String finalita)
	{
		setUri(uri);
		setNomeFile(nomeFile);
		setRemote(remote);
		setMimetype(mimetype);
		setIdUd(idUd);
		setIdDoc(idDoc);
		setRotazioneTimbroPref(rotazioneTimbro);
		setPosizioneTimbroPref(posizioneTimbro);
		setFinalita(finalita);
	}

	public FileDaTimbrareBean(String uri, String nomeFile, boolean remote, String mimetype, String idUd,String idDoc,String tipoTimbro,String idFolder,
			String rotazioneTimbro,String posizioneTimbro)
	{
		setUri(uri);
		setNomeFile(nomeFile);
		setRemote(remote);
		setMimetype(mimetype);
		setIdUd(idUd);
		setIdDoc(idDoc);
		setTipoTimbro(tipoTimbro);
		setIdFolder(idFolder);
		setRotazioneTimbroPref(rotazioneTimbro);
		setPosizioneTimbroPref(posizioneTimbro);
	}
	
	public FileDaTimbrareBean(String uri, String nomeFile, boolean remote, String mimetype, String idUd,String idDoc,String rotazioneTimbro,String posizioneTimbro, Integer nroProgrAllegato)
	{
		setUri(uri);
		setNomeFile(nomeFile);
		setRemote(remote);
		setMimetype(mimetype);
		setIdUd(idUd);
		setIdDoc(idDoc);
		setRotazioneTimbroPref(rotazioneTimbro);
		setPosizioneTimbroPref(posizioneTimbro);
		setNroProgrAllegato(nroProgrAllegato);
	}
	
	private void setTipoTimbro(String tipoTimbro) {
		setAttribute("tipoTimbro", tipoTimbro);
	}
	private void setIdFolder(String idFolder){
		setAttribute("idFolder", idFolder);
	}
	private void setIdDoc(String idDoc) {
		setAttribute("idDoc", idDoc);
	}
	private void setIdUd(String idUd) {
		setAttribute("idUd", idUd);
	}
	public String getIdUd() {
		return getAttribute("idUd");
	}
	public String getMimetype() {
		return getAttribute("mimetype");
	}
	public void setMimetype(String mimetype) {
		setAttribute("mimetype", mimetype);
	}
	public String getUri() {
		return getAttribute("uri");
	}
	public void setUri(String uri) {
		setAttribute("uri", uri);
	}
	public String getNomeFile() {
		return getAttribute("nomeFile");
	}
	public void setNomeFile(String nomeFile) {
		setAttribute("nomeFile", nomeFile);
	}
	public boolean isRemote() {
		return getAttributeAsBoolean("remote");
	}
	public void setRemote(boolean remote) {
		setAttribute("remote", remote);
	}
	private void setRotazioneTimbroPref(String rotazioneTimbro) {
		setAttribute("rotazioneTimbroPref", rotazioneTimbro);
	}
	private void setPosizioneTimbroPref(String posizioneTimbro) {
		setAttribute("posizioneTimbroPref", posizioneTimbro);
	}
	private void setFinalita(String finalita) {
		setAttribute("finalita", finalita);
	}
	private void setNroProgrAllegato(Integer nroProgrAllegato) {
		setAttribute("nroProgrAllegato", nroProgrAllegato);
	}
}
