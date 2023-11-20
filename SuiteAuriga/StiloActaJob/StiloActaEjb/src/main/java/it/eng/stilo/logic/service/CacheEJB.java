/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.stilo.logic.service.cache.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class CacheEJB<T extends Cacheable> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, T> cacheMap = new HashMap<>();

    public void add(final String name, final T cache) {
        logger.info(getClass().getSimpleName() + "-add[" + name + "]");
        cacheMap.put(name, cache);
    }

    public T get(final String name) {
        logger.info(getClass().getSimpleName() + "-get[" + name + "]");
        final T cache = cacheMap.get(name);
        if (cache != null && cache.isExpired()) {
            cache.init();
        }

        return cache;
    }

}
