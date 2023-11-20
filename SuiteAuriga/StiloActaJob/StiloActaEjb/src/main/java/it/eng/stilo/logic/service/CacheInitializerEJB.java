/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.stilo.logic.service.cache.ActaCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class CacheInitializerEJB {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EJB
    private CacheEJB<ActaCache> cacheEJB;

    @PostConstruct
    public void initCache() {
        cacheEJB.add(ActaCache.NAME, new ActaCache());
    }

}
