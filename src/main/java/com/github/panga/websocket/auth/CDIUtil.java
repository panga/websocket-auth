package com.github.panga.websocket.auth;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Leonardo Zanivan
 */
public class CDIUtil {

    private static BeanManager _manager;

    public static BeanManager getBeanManager() {
        if (_manager != null) {
            return _manager;
        }
        try {
            final InitialContext initialContext = new InitialContext();
            _manager = (BeanManager) initialContext.lookup("java:comp/env/BeanManager");
        } catch (NamingException e) {
            try {
                final InitialContext initialContext = new InitialContext();
                _manager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
            } catch (NamingException e1) {
                throw new IllegalStateException("No BeanManager implementation found", e1);
            }
        }
        return _manager;
    }

    public static <B> B getContextualBeanInstance(Class<B> type, Annotation... qualifiers) {
        BeanManager beanManager = getBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(type, qualifiers);
        Bean<?> bean = beanManager.resolve(beans);
        CreationalContext<?> cc = beanManager.createCreationalContext(bean);
        return (B) beanManager.getReference(bean, type, cc);
    }
}
