package org.otaku.gameserver.core.domain;

import org.otaku.gameserver.core.net.Message;

/**
 * 封装一次请求的上下文
 */
public interface RequestContext {
    //从请求上下文可以找到请求数据
    Message getMessage();
    //从请求上下文可以找到登录信息
    Session getSession();
}
