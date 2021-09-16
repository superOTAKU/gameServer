package org.otaku.gameserver.core.inject;

import com.google.inject.AbstractModule;
import org.otaku.gameserver.core.net.NettyBootstrap;

public class CoreModule extends AbstractModule {
    public final int port;

    public CoreModule(int port) {
        this.port = port;
    }

    @Override
    protected void configure() {
        bind(NettyBootstrap.class).toProvider(() -> new NettyBootstrap(port));
    }
}
