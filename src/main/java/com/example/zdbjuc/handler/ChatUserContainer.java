package com.example.zdbjuc.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.channel.Channel;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaodb 2023/2/23
 * @since 3.0.1
 */
public class ChatUserContainer {

    private static Map<String, Channel> userMap = new HashMap<>();

    public static Map<String, Channel> addUser(String userName, Channel channel) {
        userMap.put(userName, channel);
        return userMap;
    }

    public static Channel getUser(String userName) {
        Channel channel = userMap.get(userName);
        return channel;
    }

    public static void removeByUser(String userName) {
        userMap.remove(userName);
    }

    public static void removeByChannel(Channel channel) {
        for (Entry<String, Channel> entry : userMap.entrySet()) {
            if (entry.getValue().equals(channel)) {
                userMap.remove(entry.getKey());
            }
        }
    }
}
