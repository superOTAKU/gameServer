package org.otaku.gameserver.core.net.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.otaku.gameserver.core.domain.RequestDispatcher;
import org.otaku.gameserver.core.domain.RequestContext;
import org.otaku.gameserver.core.domain.Session;
import org.otaku.gameserver.core.domain.SessionManager;
import org.otaku.gameserver.core.domain.impl.DefaultRequestContext;
import org.otaku.gameserver.core.net.Message;

/**
 * 已经登录完了，分发请求
 */
@Slf4j
public class BusinessHandler extends ChannelInboundHandlerAdapter {
    @Inject
    private RequestDispatcher dispatcher;
    @Inject
    private SessionManager sessionManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message)msg;
        Session session = sessionManager.getSessionByCtx(ctx);
        if (session == null) {
            log.error("channel[{}] not login", ctx.channel());
            ctx.close();
            return;
        }
        RequestContext requestContext = new DefaultRequestContext(message, session);
        dispatcher.dispatch(ctx, requestContext);
    }

}
