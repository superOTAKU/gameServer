package org.otaku.gameserver.core.domain.impl;

import io.netty.channel.Channel;
import org.otaku.gameserver.core.domain.Session;

public class DefaultSession implements Session {
    private long userId;
    private Channel channel;
    public DefaultSession(long userId, Channel channel) {
        this.userId = userId;
        this.channel = channel;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }
}
