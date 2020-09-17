package tyj.ddz.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public final class HostAndPortUtil {
    private static List<HostAndPort> redisHostAndPortList = new ArrayList<HostAndPort>();
    private static List<HostAndPort> sentinelHostAndPortList = new ArrayList<HostAndPort>();
    private static List<HostAndPort> clusterHostAndPortList = new ArrayList<HostAndPort>();

    static {
        ResourceBundle resource = ResourceBundle.getBundle("redis");
        String redisAddr = resource.getString("game.redis.host");
        int redisPort = Integer.valueOf(resource.getString("game.redis.port"));
        redisHostAndPortList.add(new HostAndPort(redisAddr, redisPort));
        String envRedisHosts = System.getProperty("redis-hosts");
        String envSentinelHosts = System.getProperty("sentinel-hosts");
        String envClusterHosts = System.getProperty("cluster-hosts");

        redisHostAndPortList = parseHosts(envRedisHosts, redisHostAndPortList);
        sentinelHostAndPortList = parseHosts(envSentinelHosts, sentinelHostAndPortList);
        clusterHostAndPortList = parseHosts(envClusterHosts, clusterHostAndPortList);
    }

    private HostAndPortUtil() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static List<HostAndPort> parseHosts(String envHosts,
                                               List<HostAndPort> existingHostsAndPorts) {

        if (null != envHosts && 0 < envHosts.length()) {

            String[] hostDefs = envHosts.split(",");

            if (null != hostDefs && 2 <= hostDefs.length) {

                List<HostAndPort> envHostsAndPorts = new ArrayList<HostAndPort>(hostDefs.length);

                for (String hostDef : hostDefs) {

                    String[] hostAndPortParts = HostAndPort.extractParts(hostDef);

                    if (null != hostAndPortParts && 2 == hostAndPortParts.length) {
                        String host = hostAndPortParts[0];
                        int port = Protocol.DEFAULT_PORT;

                        try {
                            port = Integer.parseInt(hostAndPortParts[1]);
                        } catch (final NumberFormatException nfe) {
                        }

                        envHostsAndPorts.add(new HostAndPort(host, port));
                    }
                }

                return envHostsAndPorts;
            }
        }

        return existingHostsAndPorts;
    }

    public static List<HostAndPort> getRedisServers() {
        return redisHostAndPortList;
    }

    public static List<HostAndPort> getSentinelServers() {
        return sentinelHostAndPortList;
    }

    public static List<HostAndPort> getClusterServers() {
        return clusterHostAndPortList;
    }
}
