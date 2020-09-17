package tyj.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * 功能描述: <br>
 * 〈channel绑定uid和roomId，及后续调用方法〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class ChannelAttributeUtil {
    private static String PLAYER_ID = "uid";
    public static AttributeKey<Integer> playerIdKey = AttributeKey.valueOf(PLAYER_ID);

    public static void clear(Channel channel, AttributeKey<?> key) {
        channel.attr(key).set(null);
    }

    public static void setPlayerId(Channel channel, int playerId) {
        channel.attr(playerIdKey).set(playerId);
    }

    public static Integer getPlayerId(Channel channel) {
        return channel.attr(playerIdKey).get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(ChannelHandlerContext ctx, AttributeKey<T> key) {
        return (T) ctx.channel().attr(key);
    }

    public static <T> void set(ChannelHandlerContext ctx, AttributeKey<T> key, T value) {
        ctx.channel().attr(key).set(value);
    }
}
