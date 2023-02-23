package com.example.zdbjuc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaodb 2023/2/16
 * @since 3.0.1
 */
public class MyTestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("received msg from client:{}" + ctx.channel() + "msg:" + msg.toString());
        int index = 0;
        ctx.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        index++;
    }
}
