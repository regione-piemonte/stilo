/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface Cacheable {

    String CACHE_VALIDITY_DEFAULT = "0"; // zero or negative value means no-caching

    void init();

    boolean isExpired();

}
