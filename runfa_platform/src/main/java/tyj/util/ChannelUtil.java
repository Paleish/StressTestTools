package tyj.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: <br>
 * 〈将channel分为3个大组，方便在统一发送消息时进行处理〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class ChannelUtil {

    private static Map<Integer, Channel> hallChannels = new ConcurrentHashMap<Integer, Channel>();

    private static Map<Integer, Channel> gameChannels = new ConcurrentHashMap<>();

    public static void setHallChannel(Integer key, Channel channel) {
        hallChannels.put(key, channel);
    }

    public static Channel getHallChannel(Integer key) {
        return hallChannels.get(key);
    }

    public static Channel removeGameChannel(Integer key) {
        return gameChannels.remove(key);
    }

    public static void setGameChannels(Integer key, Channel channel) {
        gameChannels.put(key, channel);
    }

    public static Channel getGameChannels(Integer key) {
        return gameChannels.get(key);
    }
}
