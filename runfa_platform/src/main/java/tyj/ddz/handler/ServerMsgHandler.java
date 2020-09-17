///**
// * Copyright (C), 2015-2019, XXX有限公司
// * FileName: ServerMsgHandler
// * Author:   Administrator
// * Date:     2019/6/21 16:01
// * Description:
// * History:
// * <author>          <time>          <version>          <desc>
// * TanYujie       修改时间           版本号              描述
// */
//package tyj.ddz.handler;
//
//import com.kys.pb.PbGate;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * 〈一句话功能简述〉<br>
// * 〈〉
// *
// * @author Administrator
// * @create 2019/6/21
// * @since 1.0.0
// */
//@Component
//public class ServerMsgHandler extends AbsMessageHandler {
//
//    private static ThreadPoolExecutor service;
//
//    static {
//        service = new ThreadPoolExecutor(1000, Integer.MAX_VALUE, 30l, TimeUnit.SECONDS,
//                new SynchronousQueue<>());
//        service.prestartAllCoreThreads();
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, PbGate.S2C message) {
//
//    }
//
//    @Override
//    public int getName() {
//        return 0;
//    }
//}