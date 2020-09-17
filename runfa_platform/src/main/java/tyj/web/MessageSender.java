package tyj.web;

import com.google.protobuf.ByteString;
import com.kys.pb.PbGate;
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

    public static void sendSingleSuccessMsg(Channel channel, int sid, int cid, ByteString bs) {
        try {
            PbGate.C2S.Builder msg = PbGate.C2S.newBuilder();
            msg.setSid(sid);
            msg.setCid(cid);
            msg.setSequence(1);
            msg.setBody(bs);
            ByteBuf b = UnpooledByteBufAllocator.DEFAULT.buffer();
            // 写入消息
            b.writeBytes(msg.build().toByteArray());
            WebSocketFrame frame = new BinaryWebSocketFrame(b);

            if (channel.isActive()) {
                if (channel.isWritable()) {
                    // 发送
                    channel.writeAndFlush(frame).addListener((ChannelFuture future) -> {
                        //消息发送成功
                        if (!future.isSuccess()) {
                            //消息发送失败
                            logger.error("msg send failed!user ip{},err info{}.", channel.remoteAddress(), future.cause().getMessage());
                        }
                    });
                } else {
                    try {
                        channel.writeAndFlush(frame).sync();
                        logger.info("async send msg success!user ip{}", channel.remoteAddress());
                    } catch (InterruptedException e) {
                    }
                }
            } else {
                ReferenceCountUtil.release(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
