/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigInteger;

public class ContabilitaSicraCustomMapper {

	public it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Testata.Parte asParteSetMovimentiAtto(String source) {
		if (source == null)
			return null;
		return Enum.valueOf(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Testata.Parte.class, source);
	}

	public it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta.TipoEU asTipoEUArchiviaAtto(String source) {
		if (source == null)
			return null;
		return Enum.valueOf(it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta.TipoEU.class, source);
	}

	// default
	public Integer asInt(Boolean source) {
		if (source == null)
			return null;
		return source.booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0);
	}

	// default
	public BigInteger asBigInt(Boolean source) {
		if (source == null)
			return null;
		return source.booleanValue() ? BigInteger.valueOf(1) : BigInteger.valueOf(0);
	}

	// default
	public Boolean asBoolean(BigInteger source) {
		if (source == null)
			return null;
		return source.equals(BigInteger.ONE);
	}

	// default
	public Boolean asBoolean(Integer source) {
		if (source == null)
			return null;
		return source.intValue() == 1;
	}
	
	public it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.TipoOrdinativo asTipoOrdinativoRicercaOrdinativoLiquidazione(String source) {
		if (source == null)
			return null;
		return Enum.valueOf(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.TipoOrdinativo.class, source);
	}

}
