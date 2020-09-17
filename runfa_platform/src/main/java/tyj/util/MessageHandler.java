package tyj.util;

import com.kys.pb.PbGate;
import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler {

    void handle(ChannelHandlerContext ctx, PbGate.S2C message);

    String getName();

    /**
     * ture 逻辑独立于当前io线程
     *
     * @return
     */
    boolean newThread();

}
