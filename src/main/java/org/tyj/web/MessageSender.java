package org.tyj.web;

import com.kys.util.netty.proto.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: <br>
 * 〈发送消息类〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class.getName());

    public static void sendSingleSuccessMsg(Channel channel, ProtoMsg.ClientRequest.Builder message,
                                            com.kys.util.netty.proto.ProtoMsg.MessageTypeEnum msgType) {
        try {
            message.setMethodId(msgType.getNumber());
            ByteBuf b = UnpooledByteBufAllocator.DEFAULT.buffer();
            // 写入消息
            b.writeBytes(message.build().toByteArray());
            WebSocketFrame frame = new BinaryWebSocketFrame(b);

            if (channel.isActive()) {
                if (channel.isWritable()) {
                    // 发送
                    channel.writeAndFlush(frame).addListener((ChannelFuture future) -> {
                        //消息发送成功
                        if (!future.isSuccess()) {
                            //消息发送失败
                            logger.error("msg send failed!msg type:{},user ip{},err info{}.", msgType, channel.remoteAddress(), future.cause().getMessage());
                        }
                    });
                } else {
                    try {
                        channel.writeAndFlush(frame).sync();
                        logger.info("async send msg{} success!user ip{}", msgType, channel.remoteAddress());
                    } catch (InterruptedException e) {
                        logger.error("write and flush msg exception. msg:[{}]", msgType, e);
                    }
                }
            } else {
                ReferenceCountUtil.release(b);
                logger.error("send message{} channel{} is already close!", msgType, channel.remoteAddress());
            }
        } catch (Exception e) {
            logger.error("class{}-message type:{},error:", MessageSender.class.getName(), msgType, e);
            e.printStackTrace();
        }
    }
}
