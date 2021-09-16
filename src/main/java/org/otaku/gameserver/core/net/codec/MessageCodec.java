package org.otaku.gameserver.core.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.otaku.gameserver.core.net.Message;
import org.otaku.gameserver.core.net.Message.MessageFormat;

import java.util.List;

/**
 * <pre>
 *     协议格式：
 *
 *     | 4 byte length field | 4 byte message code | 1 byte message format type | message content |
 * </pre>
 */
public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        byte[] data = msg.getContent();
        out.writeInt(data.length + 5);
        out.writeInt(msg.getCode());
        out.writeByte(msg.getFormat().getType());
        out.writeBytes(data);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int len = in.readInt();
        int code = in.readInt();
        MessageFormat format = MessageFormat.valueOf(in.readByte());
        byte[] messageData = new byte[len - 5];
        in.readBytes(messageData, 0, messageData.length);
        Message message = new Message();
        message.setCode(code);
        message.setFormat(format);
        message.setContent(messageData);
        out.add(message);
    }
}
