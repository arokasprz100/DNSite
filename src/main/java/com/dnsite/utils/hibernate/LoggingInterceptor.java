package com.dnsite.utils.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class LoggingInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = 1L;
    // Define a static logger
    private static Logger logger = Logger.getLogger(LoggingInterceptor.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) {
        logger.info("DUPA JEST LOGOWANA");
        return super.onSave(entity, id, state, propertyNames, types);
    }
    // Logging SQL statement
    @Override
    public String onPrepareStatement(String sql) {
        logger.info(sql);
        return super.onPrepareStatement(sql);
    }

}