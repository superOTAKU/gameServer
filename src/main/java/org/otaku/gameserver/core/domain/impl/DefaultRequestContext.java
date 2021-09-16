package org.otaku.gameserver.core.domain.impl;

import org.otaku.gameserver.core.domain.RequestContext;
import org.otaku.gameserver.core.domain.Session;
import org.otaku.gameserver.core.net.Message;

public class DefaultRequestContext implements RequestContext {
    private Message message;
    private Session session;
    public DefaultRequestContext(Message message, Session session) {
        this.message = message;
        this.session = session;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public Session getSession() {
        return session;
    }
}
