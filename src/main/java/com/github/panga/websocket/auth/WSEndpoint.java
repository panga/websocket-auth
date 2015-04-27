package com.github.panga.websocket.auth;

import com.github.panga.jboss.security.websocket.WebsocketSecurityConfigurator;
import com.github.panga.jboss.security.websocket.WebsocketSecurityInterceptor;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Leonardo Zanivan
 */
@Interceptors({WebsocketSecurityInterceptor.class})
@ServerEndpoint(value = "/ws", configurator = WebsocketSecurityConfigurator.class)
public class WSEndpoint {

    @Inject
    private SecuredEJB ejb;

    @OnOpen
    public void open(Session client) {
        System.out.println("onOpen = " + ejb.call("onOpen"));
    }

    @OnMessage
    public void message(String text, Session client) {
        System.out.println("OnMessage = " + ejb.call("OnMessage"));
        client.getAsyncRemote().sendText(ejb.call(text));
    }

    @OnClose
    public void close(Session client) {
        System.out.println("OnClose = " + ejb.call("OnClose"));
    }

}
