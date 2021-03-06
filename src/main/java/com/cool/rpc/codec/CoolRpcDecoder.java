package com.cool.rpc.codec;

import com.cool.rpc.protocol.CoolProtocol;
import com.cool.rpc.serialize.ProtostuffSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public final class CoolRpcDecoder extends ByteToMessageDecoder implements CoolRpcCodec {

    private Class<? extends CoolProtocol> protocolClass;

    public CoolRpcDecoder(Class<? extends CoolProtocol> protocolClass){
        this.protocolClass = protocolClass;
    }

    /**
     *  sign(0x2323)+id(long)+len(short)+data(bytes)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4){
            return;
        }

        int allLen = in.writerIndex();
        if (allLen < 4){
            return;
        }

        int startMarkIdx = 0;
        for (int i = in.readerIndex(); i < allLen - 1; i++){
            byte b = in.getByte(i);
            if (b == MESSAGE_SIGN){
                if (i < allLen && (MESSAGE_SIGN == in.getByte(i + 1))){
                    startMarkIdx = i;
                    break;
                }
            }
        }

        if (startMarkIdx != 0){
            in.readerIndex(startMarkIdx);
            in.discardReadBytes();
        }

        in.skipBytes(2);
        long requestId = in.readLong();
        short len = in.readShort();

        byte[] data = new byte[len];
        in.readBytes(data);

        CoolProtocol protocol = ProtostuffSerialize.deserialize(data, protocolClass);
        if (protocol != null){
            protocol.setRequestId(requestId);
        }

        data = null;
        out.add(protocol);

    }
}
