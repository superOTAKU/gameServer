package org.otaku.gameserver.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.cli.*;
import org.otaku.gameserver.core.inject.CoreModule;
import org.otaku.gameserver.core.net.NettyBootstrap;

public class GameServer {
    private int serverId;

    public static void main(String[] args) throws Exception {
        CommandLine commandLine = parseCommandLine(args);
        String portStr = commandLine.getOptionValue("p");
        int port = Integer.parseInt(portStr);
        Injector injector = Guice.createInjector(new CoreModule(port));
        NettyBootstrap nettyBootstrap = injector.getInstance(NettyBootstrap.class);
        nettyBootstrap.start();
    }

    private static CommandLine parseCommandLine(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("p", "port", true, "port to listen at");
        CommandLine commandLine = parser.parse(options, args);
        return commandLine;
    }

}
