package org.otaku.gameserver.core.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.otaku.gameserver.core.net.codec.MessageCodec;
import org.otaku.gameserver.core.net.handler.BusinessHandler;
import org.otaku.gameserver.core.net.handler.LoginHandler;

/**
 * 封装netty启动逻辑
 */
@Slf4j
public class NettyBootstrap {
    private static final int MAX_FRAME_LEN = 1000000;
    private static final int LEN_FIELD_OFFSET = 0;
    private static final int LEN_FIELD_LEN = 4;
    private final int port;
    private Channel serverChannel;

    public NettyBootstrap(int port) {
        this.port = port;
    }

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //serverSocket option
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 512); //半连接队列大小
        serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true); //tcp层启动心跳机制
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, true); //重用端口，防止误报端口占用
        //child option
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true); //禁用nagle算法，该算法导致小包缓存在本地，增大时延
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new IdleStateHandler(60, 60, 90));
                    p.addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LEN, LEN_FIELD_OFFSET, LEN_FIELD_LEN));
                    p.addLast(new MessageCodec());
                    p.addLast(new LoginHandler());
                    p.addLast(new BusinessHandler());
                }
            });
            ChannelFuture future = serverBootstrap.bind(port).syncUninterruptibly();
            log.info("server listen at [{}]", port);
            serverChannel = future.channel();
            serverChannel.close().syncUninterruptibly();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        serverChannel.close();
    }

    public int getPort() {
        return port;
    }
}
