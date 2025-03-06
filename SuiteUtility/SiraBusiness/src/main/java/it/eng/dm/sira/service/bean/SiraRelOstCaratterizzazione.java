package it.eng.dm.sira.service.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration contenente tutti i catasti
 * @author jacopo
 * 
 */
@XmlRootElement
public enum SiraRelOstCaratterizzazione {

			Unita_Locale(1, 1,"CcostSoggettiGiuridici","CcostUnitaLocali"), 
			Sede_Legale(1, 2, "CcostSoggettiGiuridici", "CcostSediLegali"), 
			Sorgente_Emissione_Puntuale(2, 3,"CcostEs","CcostPuntiEmissioneAtm"), 
			Apparecchio_PCB(2, 4,"CcostEs","CaratterizzazioniOst"), 
			Piezometro( 2, 5,"CcostEs","CcostPiezometri"), 
			Punto_Prelievo_Idrico(2, 6,"","CaratterizzazioniOst"),
			Pozzo( 2, 7,"CcostEs", "CcostPozzi"),
			Scarico_Idrico( 2, 8,"", "CaratterizzazioniOst"), 
			Punto_Controllo_Occasionale(2, 9,"CcostEs","CcostPuntiControlloOcc"), 
			Stazione_Monitoraggio_Qualita_Aria(2, 10,"CcostEs","CcostStazioniCor"), 
			Stazione_Monitoraggio_Qualita_Acque_Superficiali(2,11,"CcostEs","CcostStazioniQualAcque"),
			Sondaggio_Geognostico( 2, 39,"CcostEs","CcostSondaggiGeo"), 
			Opera_Captazione_Idrica(2, 40,"CcostEs","CcostOperaCaptIdr"), 
			Punto_Misura_O_Campionamento(2, 41,"","CaratterizzazioniOst"), 
			Punto_Monitoraggio_Periodico(2, 42,"CcostEs",""), 
			Punto_Monitoraggio_Continuo(2, 43,"CcostEs",""), 
			Stazione_Metereologica(2, 55,"CcostEs",""), 
			Stazione_Monitoraggio_Acque_Sotterranee(2, 56,"CcostEs","CcostStazioniGrass"), 
			Centralina_Fondazione_Bordoni(2, 57,"CcostEs","CcostCentralineBordoni"), 
			Frantoio_OLeario(2, 65,"CcostEs","CcostFrantoiOleari"), 
			Sorgente_Radiazioni_Ionizzanti(2, 68,"CcostEs","CcostSorgRadIonizzanti"), 
			Sorgente_Inquinamento_Acustico( 2, 69,"CcostEs","CcostSorgInqAcustico"), 
			Sorgente_Inquinamento_Acustico_Puntuale(2, 70,"CcostEs","CcostSorgInqAcustico"), 
			Sorgente_Inquinamento_Acustico_Lineare(2, 71,"CcostEs","CcostSorgInqAcustico"), 
			Laghetti_Collinari(2, 85,"","CaratterizzazioniOst"), 
			Aree_Sosta(2, 89,"","CaratterizzazioniOst"), 
			Aree_Verdi_Attrezzate(2, 90,"","CaratterizzazioniOst"), 
			Sorgente_Emissione_Atmosfera(2, 107,"","CaratterizzazioniOst"), 
			Sorgente_Emissione_Areale(2, 108,"CcostEs","CcostAreeEmissioneAtm"), 
			Unita_Tecnica(2, 110,"CcostEs","CcostUnitaTecnica"), 
			Sorgente_Illuminazione_Artificiale(2, 111,"","CaratterizzazioniOst"), 
			Impianto_Trattamento_Acque(3, 14,"","CaratterizzazioniOst"), 
			Impianto_Depurazione(3, 15,"CcostImpdisinquinamento.CcostImptrattacque","CcostImpdepurazione"), 
			Impianto_Gestione_Rifiuti(3, 16,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Inceneritore(3, 17,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Discarica(3, 18,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Coinceneritore(3, 19,"CcostImpdisinquinamento.CcostImpGestioneRifiuti","CcostIgrCoinceneritore"), 
			Impianto_Compostaggio_Stabilizzazione_Aerobica(3, 20,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Impianto_Trattamento_Anaerobico(3, 21,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Altri_Impianti_Trattamento(3, 22,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Altri_Impianti_Recupero(3, 23,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Impianto_Stoccaggio(3,24,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Impianto_Selezione(3, 25,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Impianto_Trattamento_Veicoli_Fuori_Uso(3, 26,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Impianto_Trattamento_Recupero_RAFE(3, 27,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""),
			Impianto_Mobile_Smaltimento_Recupero_Rifiuti(3, 88,"CcostImpdisinquinamento.CcostImpGestioneRifiuti",""), 
			Formazione_Idrogeologica(4, 12,"CcostOggIdrografico","CaratterizzazioniOst"), 
			Sorgente_idrica(4, 13,"CcostOggIdrografico","CcostSorgentiIdr"), 
			Bacino_idrografico(4, 44,"CcostOggIdrografico","CcostBacinoIdrografico"), 
			Bacino_idrogeologico(4, 45,"CcostOggIdrografico","CaratterizzazioniOst"), 
			Complesso_acquifero(4, 46,"CcostOggIdrografico.CcostAcqueSotterranee","CcostComplessoAcquifero"), 
			Corpo_idrico_superficiale(4, 47,"CcostOggIdrografico","CaratterizzazioniOst"), 
			Corso_acqua_naturale_o_artificiale(4, 48,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostArt"), 
			Lago_o_serbatoio(4, 49,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostLa"), 
			Acqua_di_transizione(4, 50,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostActr"), 
			Acqua_marina_costiera(4, 51,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostAmc"), 
			Corpo_idrico_sotterraneo(4, 52,"CcostOggIdrografico.CcostAcqueSotterranee","CcostCorpoIdrSott"), 
			Acquifero(4, 53,"CcostOggIdrografico.CcostAcqueSotterranee","CcostAcquifero"), 
			Acque_sotterranee(4, 80,"CcostOggIdrografico","CcostAcqueSotterranee"), 
			Parte_di_corso_acqua(4, 81,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostParteCorsoAcqua"), 
			Parte_di_lago_o_invaso(4, 82,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostParteLago"), 
			Parte_di_acqua_di_transizione(4, 83,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostParteAcqueTransizione"), 
			Parte_di_acque_marino_costiere(4, 84,"CcostOggIdrografico.CcostCorpoIdricoSup","CcostParteAcqueCostiere"), 
			Diga_sbarramento(4,128,"CcostOggIdrografico",""),
			Sito_Contaminato(5,28,"CcostAmbitoTerritoriale","CcostSiticontaminati"),
			Lotto_Sito_Contaminato(5,29,"CcostAmbitoTerritoriale","CaratterizzazioniOst"),
			Sito_Contaminato_Amianto(5,64,"CcostAmbitoTerritoriale","CcostSitiAmianto"),
			Sito_Spandimento_Acque_Vegetazione_E_Sanse_Umide(5,66,"CcostAmbitoTerritoriale","CcostSitiSpand"),
			Area_Sottoposta_Vincolo_Idrogeologico(5,72,"CcostAmbitoTerritoriale","CcostAreaVincoloIdrogeo"),
			Area_Sottoposta_Pericolo_Idrogeologico(5,73,"CcostAmbitoTerritoriale","CcostAreaPericoloIdrogeo"),
			Area_Sottoposta_Rischio_Idrogeologico(5,74,"CcostAmbitoTerritoriale","CcostAreaRischioIdrogeo"),
			Area_Estrattiva(5,75,"","CcostAmbitoTerritoriale"),
			Spiaggia(5,76,"CcostAmbitoTerritoriale","CcostSpiaggia"),
			Costa_Antropizzata(5,77,"CcostAmbitoTerritoriale","CcostCostaAntropizzata"),
			Costa_Rocciosa(5,78,"CcostAmbitoTerritoriale","CcostCostaRocciosa"),
			Unita_Fisiografica(5,79,"CcostAmbitoTerritoriale","CcostUnitaFisiografica"),
			Sito_Interesse_Comunitario(5,91,"CcostAmbitoTerritoriale","CcostSic"),
			Zona_Protezione_Speciale(5,92,"CcostAmbitoTerritoriale","CcostZps"),
			Area_Protetta(5,93,"","CaratterizzazioniOst"),
			Zona_Umida(5,94,"CcostAmbitoTerritoriale","CcostZoneUmide"),
			Cava(5,95,"","CcostAmbitoTerritoriale"),
			Area_Marina_Protetta(5,96,"CcostAmbitoTerritoriale","CcostAmp"),
			Parco_Nazionale(5,97,"CcostAmbitoTerritoriale","CcostParcoNazionale"),
			RIN(5,98,"","CaratterizzazioniOst"),
			Monumento_Naturale(5,99,"","CaratterizzazioniOst"),
			Grotta(5,100,"","CcostAmbitoTerritoriale"),
			Specie_Endemiche(5,101,"","CaratterizzazioniOst"),
			Oasi_Permanenti_Protezione_Faunistica_Cattura(5,102,"","CaratterizzazioniOst"),
			Zone_Temporanee_Popolamento_Cattura(5,103,"","CaratterizzazioniOst"),
			Zona_Addestramento_Cani(5,104,"","CaratterizzazioniOst"),
			Zona_Concessione_Autogestita(5,105,"","CaratterizzazioniOst"),
			Allevamenti_Fauna_Selvatica(5,106,"","CaratterizzazioniOst"),
			Sito_Spandimento_Fanghi_Depurazione(5,109,"CcostAmbitoTerritoriale","CcostSpandimentoFanghi"),
			Area_Legge_31_89(5,114,"CcostAmbitoTerritoriale",""),
			Zona_Omogenea_Acusticamente(5,115,"CcostAmbitoTerritoriale","CcostZoneOmogeneeAcusticamente"),
			Miniera(5,130,"CcostAmbitoTerritoriale",""),
			Infrastruttura_Per_Radiocomunicazione(6,30,"","CaratterizzazioniOst"),	
			Sito_RC(6,31,"CcostInfrasTerrit.CcostInfrasRc","CcostSitiRc"),
			Impianto_RC(6,32,"CcostInfrasTerrit.CcostInfrasRc","CcostImpiantiRc"),
			Infrastruttura_Per_Distribuzione_Energia(6,33,"","CaratterizzazioniOst"),	
			Linea(6,34,"","CcostInfrasTerrit"),	
			Tronco(6,35,"","CcostInfrasTerrit"),	
			Tratta(6,36,"","CcostInfrasTerrit"),	
			Campata(6,37,"","CcostInfrasTerrit"),	
			Sostegno(6,38,"",""),	
			Impianto(6,54,"","CcostInfrasTerrit"), 
			Infrastruttura_Trasporto_Combustibile(6,112,"","CaratterizzazioniOst"),	
			Gasdotto(6,113,"CcostInfrasTerrit.CcostInfrasTraspComb","CcostGasdotto"),
			Infrastruttura_distribuzione_raccolta_acque(6,124,"",""),
			Elemento_Acquedottistico(6,125,"",""),	
			Condotta(6,126,"",""),	
			Collettore_Fognario(6,127,"",""),
			Discarica_Mineraria	(7,58,"","CaratterizzazioniOst"),
			Bacino_Fanghi(7,59,"","CaratterizzazioniOst"),	
			Scavo_Cielo_Aperto(7,60,"","CaratterizzazioniOst"),	
			Galleria_Mineraria(7,61,"","CaratterizzazioniOst"),	
			Pozzo_Minerario(7,62,"","CaratterizzazioniOst"),	
			Struttura_Mineraria(7,63,"","CaratterizzazioniOst"),	
			Abbancamento_Fini(7,67,"","CaratterizzazioniOst"),
			Impianto_produzione_energia_termica	(8,116,"","CcostImpiantoProdEnergia"),
			Impianto_produzione_energia_termica_da_fonti_tradizionali(8,117,"","CcostImpiantoProdEnergia"),	
			Impianto_termico(8,118,"","CcostImpiantoProdEnergia"),	
			Impianto_produzione_energia_termica_da_fonti_energia_rinnovabile(8,119,"","CcostImpiantoProdEnergia"),	
			Impianto_produzione_energia_termica_da_solare_termico(8,120,"","CcostImpiantoProdEnergia"),	
			Impianto_produzione_energia_termica_da_geotermico(8,121,"","CcostImpiantoProdEnergia"),	
			Impianto_produzione_energia_termica_da_aerotermico(8,122,"","CcostImpiantoProdEnergia"),	
			Impianto_produzione_energia_termica_da_idrotermico(8,123,"","CcostImpiantoProdEnergia"),	
			Soggetto_Fisico(66, 3,"","");		

	private Integer natura;

	private Integer categoria;
	
	private String caratterizzazioneNatura;
	
	private String caratterizzazioneCategoria;

	private SiraRelOstCaratterizzazione( Integer natura, Integer categoria, String caratterizzazioneNatura, String caratterizzazioneCategoria) {
		this.natura = natura;
		this.categoria = categoria;
		this.caratterizzazioneNatura = caratterizzazioneNatura;
		this.caratterizzazioneCategoria = caratterizzazioneCategoria;
	}

	public static SiraRelOstCaratterizzazione getValueForNaturaCategoria(int natura, int categoria) {
		for (SiraRelOstCaratterizzazione rel : SiraRelOstCaratterizzazione.values()) {
				if (rel.natura==natura && rel.categoria== categoria) {
					return rel;
				}
		}
		return null;
	}
  
	public static SiraRelOstCaratterizzazione getValueForCategoria(int categoria) {
		for (SiraRelOstCaratterizzazione rel : SiraRelOstCaratterizzazione.values()) {
				if (rel.categoria== categoria) {
					return rel;
				}
		}
		return null;
	}  
	
	public static List<SiraRelOstCaratterizzazione> getValueForNatura(int natura){
		List<SiraRelOstCaratterizzazione> result = new ArrayList<SiraRelOstCaratterizzazione>();
		for (SiraRelOstCaratterizzazione rel : SiraRelOstCaratterizzazione.values()) {
			if (rel.natura==natura) {
				result.add(rel);
			}
	}
		return result;
	}
	

	public Integer getNatura() {
		return natura;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public String getCaratterizzazioneCategoria() {
		return caratterizzazioneCategoria;
	}

	public String getCaratterizzazioneNatura() {
		return caratterizzazioneNatura;
	}

  

}