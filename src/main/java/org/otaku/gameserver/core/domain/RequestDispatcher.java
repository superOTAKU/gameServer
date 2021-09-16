package org.otaku.gameserver.core.domain;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息分发机制，包括线程模型的处理。
 */
public interface RequestDispatcher {
    void dispatch(ChannelHandlerContext ctx, RequestContext requestContext);
}
