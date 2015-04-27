package com.github.panga.websocket.auth;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

/**
 *
 * @author Leonardo Zanivan
 */
@Stateless
@RolesAllowed("websocket")
public class SecuredEJB {

    @Resource
    private EJBContext context;

    public String call(String text) {
        return text + " from " + context.getCallerPrincipal().getName();
    }
}
