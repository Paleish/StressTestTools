/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.tyj.web;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tyj.util.ChannelAttributeUtil;
import org.tyj.util.ChannelUtil;

import java.net.URI;

public class WebSocketClient implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class.getName());

    //private static String hallUrl = "ws://192.168.0.230:8083";
    private static String hallUrl = "ws://127.0.0.1:8083/ws";
    //private static String hallUrl = "ws://39.107.139.148:8083";
    //private static String hallUrl = "ws://172.17.16.25:8083";

    public Channel getChannel() {
        return channel;
    }

    private Channel channel;

    private int userId;

    private int roomId = 0;

    private String url;

    public WebSocketClient(int userId, int roomId, String url) {
        this.userId = userId;
        this.roomId = roomId;
        this.url = url;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            if (roomId == 0) {
                url = hallUrl;
            }
            URI uri = new URI(System.getProperty("url", url));

            WebSocketClientHandler handler = new WebSocketClientHandler(WebSocketClientHandshakerFactory
                    .newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new HttpClientCodec(), new HttpObjectAggregator(65536),
                            WebSocketClientCompressionHandler.INSTANCE, handler);
                }
            });
            ChannelFuture cf = b.connect(uri.getHost(), uri.getPort()).sync();
            handler.handshakeFuture().sync();
            if (cf.isSuccess()) {
                channel = cf.channel();
                if (null != channel) {
                    //设置上uid等属性
                    ChannelAttributeUtil.setPlayerId(channel, userId);
                    if (roomId == 0) {
                        ChannelUtil.setHallChannel(userId, channel);
                        logger.info("AI{}connect hall success！", userId);
                    } else {
                        ChannelAttributeUtil.setRoomId(channel, roomId);
                        ChannelUtil.setGameChannels(userId, channel);
                        logger.info("AI{}connect game room success！", userId);
                    }
                } else {

                }
                channel.closeFuture().sync();
            }
        } catch (Exception e) {
            logger.error("create web socket connection err!", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}