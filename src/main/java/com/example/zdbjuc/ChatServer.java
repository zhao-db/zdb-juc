package com.example.zdbjuc;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.example.zdbjuc.handler.ChatHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaodb 2023/2/23
 * @since 3.0.1
 */
public class ChatServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                            channel.pipeline().addLast(new ChatHandler());
                            channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                        }

                    });
            ChannelFuture future = bootstrap.bind(9800).sync();
            if (future.isSuccess()) {
                System.out.println("startServer success ");
            } else {
                System.out.println("startServer failed ");
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("error  ... e： = " + e);
        } finally {
            System.out.println("213123");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
