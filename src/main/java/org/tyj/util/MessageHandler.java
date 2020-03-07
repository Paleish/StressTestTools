package org.tyj.util;

import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler {

	void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message);

	int getName();

	/**
	 * ture 逻辑独立于当前io线程
	 *
	 * @return
	 */
	boolean newThread();

}
