package com.kenji.cloud.loadbalance;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SHI Jing
 * @date 2019/1/7 20:46
 */
public class DynamicRule extends AbstractLoadBalancerRule {
    private AtomicInteger nextServerCyclicCounter;
    private static final boolean AVAILABLE_ONLY_SERVERS = true;
    private static final boolean ALL_SERVERS = false;

    private static Logger log = LoggerFactory.getLogger(DynamicRule.class);

    public Server choose(ILoadBalancer lb, Object key){
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;

        List<Server> allServers = lb.getAllServers();

//        while (server == null && count++ < 10) {
//            List<Server> reachableServers = lb.getReachableServers();
//            List<Server> allServers = lb.getAllServers();
//            int upCount = reachableServers.size();
//            int serverCount = allServers.size();
//
//            if ((upCount == 0) || (serverCount == 0)) {
//                log.warn("No up servers available from load balancer: " + lb);
//                return null;
//            }
//
//            int nextServerIndex = incrementAndGetModulo(serverCount);
//            server = allServers.get(nextServerIndex);
//
//            if (server == null) {
//                /* Transient. */
//                Thread.yield();
//                continue;
//            }
//
//            if (server.isAlive() && (server.isReadyToServe())) {
//                return (server);
//            }
//
//            // Next.
//            server = null;
//        }
//
//        if (count >= 10) {
//            log.warn("No available alive servers after 10 tries from load balancer: "
//                    + lb);
//        }



        return server;
    }

    public DynamicRule(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    public DynamicRule( ) {
        this.nextServerCyclicCounter = new AtomicInteger(0);;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {

        return null;
    }
}
