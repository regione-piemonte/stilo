/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.archive.IdFormaDocumentariaType;
import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.acaris.common.IdNodoType;
import it.doqui.acta.acaris.common.IdUtenteType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.sms.IdTipoSmistamentoType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrincipalCache {

	private IdUtenteType userId;
	private IdTipoSmistamentoType switchingTypeId;
	private Map<String, IdStatoDiEfficaciaType> efficacyMap = new HashMap<>();
	private Map<String, IdFormaDocumentariaType> formatMap = new HashMap<>();
	private Map<String, ObjectIdType> dossierMap = new HashMap<>();
	private Map<SerieKey, ObjectIdType> serieMap = new HashMap<>();
	private Map<VolumeKey, ObjectIdType> volumeMap = new HashMap<>();
	private Map<String, List<IdNodoType>> receiverMap = new HashMap<>();

	public Map<String, IdStatoDiEfficaciaType> getEfficacyMap() {
		return efficacyMap;
	}

	public Map<String, IdFormaDocumentariaType> getFormatMap() {
		return formatMap;
	}

	public Map<String, ObjectIdType> getDossierMap() {
		return dossierMap;
	}

	public Map<SerieKey, ObjectIdType> getSerieMap() {
		return serieMap;
	}

	public Map<VolumeKey, ObjectIdType> getVolumeMap() {
		return volumeMap;
	}

	public Map<String, List<IdNodoType>> getReceiverMap() {
		return receiverMap;
	}

	public IdUtenteType getUserId() {
		return userId;
	}

	public void setUserId(IdUtenteType userId) {
		this.userId = userId;
	}

	public IdTipoSmistamentoType getSwitchingTypeId() {
		return switchingTypeId;
	}

	public void setSwitchingTypeId(IdTipoSmistamentoType switchingTypeId) {
		this.switchingTypeId = switchingTypeId;
	}

	public static class SerieKey {
		private String serieCode;
		private String serieDescription;

		public SerieKey(String serieCode, String serieDescription) {
			this.serieCode = serieCode;
			this.serieDescription = serieDescription;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			SerieKey serieKey = (SerieKey) o;
			return serieCode.equals(serieKey.serieCode) && serieDescription.equals(serieKey.serieDescription);
		}

		@Override
		public int hashCode() {
			return Objects.hash(serieCode, serieDescription);
		}
	}

	public static class VolumeKey {
		private String parentId;
		private String volumeDescription;
		private String status;

		public VolumeKey(String parentId, String volumeDescription, String status) {
			this.parentId = parentId;
			this.volumeDescription = volumeDescription;
			this.status = status;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			VolumeKey volumeKey = (VolumeKey) o;
			return parentId.equals(volumeKey.parentId) && volumeDescription.equals(volumeKey.volumeDescription)
					&& status.equals(volumeKey.status);
		}

		@Override
		public int hashCode() {
			return Objects.hash(parentId, volumeDescription, status);
		}
	}

}
