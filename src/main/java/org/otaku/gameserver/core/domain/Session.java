package org.otaku.gameserver.core.domain;

import io.netty.channel.Channel;

/**
 * 封装已登录的连接
 */
public interface Session {
    long getUserId();
    Channel getChannel();
}
