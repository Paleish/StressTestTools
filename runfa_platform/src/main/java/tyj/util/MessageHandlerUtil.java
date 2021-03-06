package tyj.util;

import com.kys.pb.PbGate;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: <br>
 * 〈线程池-对请求进行处理〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
@Slf4j
public class MessageHandlerUtil {

    private static Map<Integer, MessageHandler> handlers = new HashMap<>();
    private static ThreadPoolExecutor service;

    static {
        service = new ThreadPoolExecutor(16, Integer.MAX_VALUE, 30l, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        service.prestartAllCoreThreads();
    }

    public static void handle(final ChannelHandlerContext ctx, final PbGate.S2C message) {
        final MessageHandler handler = handlers.get(message.getSid());
        service.execute(() -> handler.handle(ctx, message));
    }

    public static void putMessageHandler(MessageHandler handler) {
        Assert.notNull(handler.getName(), "name Name cannot be empty");
        Assert.isNull(handlers.get(handler.getName()), "MessageHandler[" + handler.getName() + "] is existing !");

        log.trace(" 注册 messageHandler [" + handler.getName() + "] > " + handler);
        handlers.put(handler.getName(), handler);
    }

    public static void putMessageHandler(int handlerId, MessageHandler handler) {
        handlers.put(handlerId, handler);
    }

    public static MessageHandler getMessageHandler(String handlerName) {
        MessageHandler handler = handlers.get(handlerName);
        Assert.notNull(handler, "MessageHandler[" + handlerName + "] non-existent !");
        return handler;
    }

}
