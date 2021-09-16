package org.otaku.gameserver.core.domain;

import io.netty.channel.ChannelHandlerContext;

public interface SessionManager {
    /**
     * 根据连接拿session
     */
    Session getSessionByCtx(ChannelHandlerContext ctx);

    Session getSessionByUserId(Long userId);

    Session login(ChannelHandlerContext ctx, String username, String password);
}
