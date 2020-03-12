package org.tyj.util;

import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MessageHandlerUtil {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerUtil.class.getName());

    private static Map<Integer, MessageHandler> handlers = new HashMap<Integer, MessageHandler>();
    private static ThreadPoolExecutor service;

    static {
        service = new ThreadPoolExecutor(16, Integer.MAX_VALUE, 30l, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        service.prestartAllCoreThreads();
    }

    public static void handle(final ChannelHandlerContext ctx, final ProtoMsg.ServerResponse message) {
        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(message.getMethodId());
        if (funcId != ProtoMsg.MessageTypeEnum.serverSendGameHeartBeat && funcId != ProtoMsg.MessageTypeEnum.serverSendHallHeartBeat) {
            if (funcId == ProtoMsg.MessageTypeEnum.b_AwardDialog) {
                return;
            }
        }
        int methodId = message.getMethodId();
        int moduleId = methodId / 1000;
        final MessageHandler handler = handlers.get(moduleId);
        if (handler == null) {
            logger.warn("没有找到处理程序:" + "ModuleId:" + moduleId + ",MethodId:" + methodId);
            return;
        }
        service.execute(() -> handler.handle(ctx, message));
    }

    public static void putMessageHandler(MessageHandler handler) {
        Assert.notNull(handler.getName(), "name Name cannot be empty");
        Assert.isNull(handlers.get(handler.getName()), "MessageHandler[" + handler.getName() + "] is existing !");

        logger.trace(" 注册 messageHandler [" + handler.getName() + "] > " + handler);
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
