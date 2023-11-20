/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.common.IdVitalRecordCodeType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.eng.stilo.util.file.FileHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActaCache implements Cacheable {

    public static final String NAME = "acta-cache";
    private static final String RESOURCE_FILE = "/res.properties";
    private static final String CACHE_VALIDITY_PROPERTY = "cache-validity-seconds";
    private LocalDateTime expiration;
    private long validitySeconds;
    private ObjectIdType repository;
    private Map<String, IdVitalRecordCodeType> vitalRecordsMap = new HashMap<>();
    private Map<PrincipalKey, PrincipalIdType> principalIdTypeMap = new HashMap<>();
    private Map<String, PrincipalCache> principalCacheMap = new HashMap<>();

    public ActaCache() {
        validitySeconds = Long.valueOf(FileHandler.getInstance().getPropertyValue(CACHE_VALIDITY_PROPERTY,
            RESOURCE_FILE, getClass()).orElse(CACHE_VALIDITY_DEFAULT));
    }

    public ObjectIdType getRepository() {
        return repository;
    }

    public void setRepository(ObjectIdType repository) {
        this.repository = repository;
    }

    public Map<String, IdVitalRecordCodeType> getVitalRecordsMap() {
        return vitalRecordsMap;
    }

    public Map<PrincipalKey, PrincipalIdType> getPrincipalIdTypeMap() {
        return principalIdTypeMap;
    }

    public Map<String, PrincipalCache> getPrincipalCacheMap() {
        return principalCacheMap;
    }

    @Override
    public void init() {
        this.repository = null;
        this.vitalRecordsMap.clear();
        this.expiration = LocalDateTime.now().plusSeconds(validitySeconds);
        this.principalIdTypeMap.clear();
        this.principalCacheMap.clear();
    }

    @Override
    public boolean isExpired() {
        return expiration == null || expiration.isBefore(LocalDateTime.now());
    }

    public static class PrincipalKey {
        private String docType;
        private String aoo;

        public PrincipalKey(String docType, String aoo) {
            this.docType = docType;
            this.aoo = aoo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PrincipalKey that = (PrincipalKey) o;
            return docType.equals(that.docType) && aoo.equals(that.aoo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(docType, aoo);
        }
    }

}
