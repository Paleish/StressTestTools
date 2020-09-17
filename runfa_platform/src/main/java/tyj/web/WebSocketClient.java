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
package tyj.web;

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
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class WebSocketClient implements Runnable {

    public Channel getChannel() {
        return channel;
    }

    private Channel channel;
    private String url;
    private String token;

    public WebSocketClient(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            URI uri = new URI(url);
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
//                    ChannelAttributeUtil.setPlayerId(channel, userId);
//                    ChannelUtil.setHallChannel(userId, channel);
//                    log.info("AI{}connect hall success！", userId);
                }
                channel.closeFuture().sync();
            }
        } catch (Exception e) {
            log.error("create web socket connection err!", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}