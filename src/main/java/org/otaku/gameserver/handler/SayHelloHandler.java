package org.otaku.gameserver.handler;

import org.otaku.gameserver.core.domain.MessageCodes;
import org.otaku.gameserver.core.domain.MessageProcessor;
import org.otaku.gameserver.core.domain.RequestContext;
import org.otaku.gameserver.core.domain.RequestHandler;

@MessageProcessor(value = MessageCodes.SAY_HELLO, executor = "default")
public class SayHelloHandler implements RequestHandler {

    @Override
    public void handle(RequestContext requestContext) {
        //...
    }

}
