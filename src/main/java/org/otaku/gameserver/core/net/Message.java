package org.otaku.gameserver.core.net;

import lombok.Data;

@Data
public class Message {
    private int code;
    private MessageFormat format;
    private byte[] content;

    public enum MessageFormat {
        JSON((byte)1);
        private final byte type;

        MessageFormat(byte type) {
            this.type = type;
        }

        public byte getType() {
            return type;
        }

        public static MessageFormat valueOf(byte type) {
            for (var mf : values()) {
                if (mf.type == type) {
                    return mf;
                }
            }
            throw new IllegalArgumentException();
        }

    }
}
