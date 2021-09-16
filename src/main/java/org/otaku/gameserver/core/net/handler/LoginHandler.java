package org.otaku.gameserver.core.net.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.otaku.gameserver.core.domain.MessageCodes;
import org.otaku.gameserver.core.domain.Session;
import org.otaku.gameserver.core.domain.SessionManager;
import org.otaku.gameserver.core.net.Message;
import org.otaku.gameserver.util.JacksonUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 在MessageHandler之前，如果是登录包，先处理登录
 */
@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {
    @Inject
    private SessionManager sessionManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Session session = sessionManager.getSessionByCtx(ctx);
        if (session != null) {
            ctx.fireChannelRead(msg);
            return;
        }
        Message message = (Message)msg;
        if (message.getCode() != MessageCodes.LOGIN) {
            log.error("channel[{}] request code[{}] without login", ctx.channel(), message.getCode());
            ctx.close();
            return;
        }
        processLogin(ctx, message);
    }

    private void processLogin(ChannelHandlerContext ctx, Message message) {
        Map<String, Object> params = JacksonUtil.toMap(new String(message.getContent(), StandardCharsets.UTF_8));
        String username = (String)params.get("username");
        String password = (String)params.get("password");
        Session session = sessionManager.login(ctx, username, password);
        if (session == null) {
            log.error("login fail, channel[{}]", ctx.channel());
            ctx.close();
            return;
        }
        ctx.fireChannelRead(message);
    }

}
