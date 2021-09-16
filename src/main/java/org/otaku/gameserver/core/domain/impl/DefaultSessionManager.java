package org.otaku.gameserver.core.domain.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.otaku.gameserver.core.domain.Session;
import org.otaku.gameserver.core.domain.SessionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSessionManager implements SessionManager {
    private final Map<ChannelId, Session> channelIdSessionMap = new ConcurrentHashMap<>();
    private final Map<Long, Session> userIdSessionMap = new ConcurrentHashMap<>();

    @Override
    public Session getSessionByCtx(ChannelHandlerContext ctx) {
        return channelIdSessionMap.get(ctx.channel().id());
    }

    @Override
    public Session getSessionByUserId(Long userId) {
        return userIdSessionMap.get(userId);
    }

    @Override
    public Session login(ChannelHandlerContext ctx, String username, String password) {
        if ("admin".equals(username) && "Aa123456".equals(password)) {
            Session session = new DefaultSession(1L, ctx.channel());
            synchronized (channelIdSessionMap) {
                channelIdSessionMap.put(ctx.channel().id(), session);
                userIdSessionMap.put(session.getUserId(), session);
            }
            return session;
        }
        return null;
    }
}
