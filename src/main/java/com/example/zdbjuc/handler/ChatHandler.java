package com.example.zdbjuc.handler;

import java.nio.charset.StandardCharsets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaodb 2023/2/23
 * @since 3.0.1
 */
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String o) throws Exception {
        String msg = o.toString();
        if (!msg.contains("@")) {
            System.out.println("receive login msg = " + msg);
            ChatUserContainer.addUser(msg, ctx.channel());
            return;
        }
        System.out.println("received msg from channel:" + ctx.channel() + "msg = " + msg);
        String[] split = msg.split("@");
        String message = split[0];
        String toUser = split[1];
        Channel userChannel = ChatUserContainer.getUser(toUser);
        if (userChannel != null) {
            userChannel.writeAndFlush(message);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChatUserContainer.removeByChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error = ");
        ctx.close();
    }
}
