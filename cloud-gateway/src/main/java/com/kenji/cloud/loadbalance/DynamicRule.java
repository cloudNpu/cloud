package com.kenji.cloud.loadbalance;

import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.impl.ApplicationServiceImpl;
import com.kenji.cloud.util.HttpUtils;
import com.kenji.cloud.util.SpringContextUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        List<MonitorInfo> monitorInfoList = new ArrayList<>();

        for(Server s : allServers){
            String host = s.getHost();
            int port = s.getPort();

            //getIP
            ApplicationService applicationService = (ApplicationService) SpringContextUtils.getBean("applicationService");
            String ip = applicationService.getIpAddrByHostAndPort(host, port);

            String url = "http://"+ip+":"+port+"/monitor/monitorInfo";
            System.out.println(url);
            //Http Request
            String response = HttpUtils.sendGet(url);
            System.out.println(response);

            //反序列化
            // 这里不能用 JsonHierarchicalStreamDriver 了，它只能用于 JavaBean->JSON
            XStream xstream = new XStream(new JettisonMappedXmlDriver());
            // 设置了这个别名才能识别下面 xml 中的 product 节点，否则要用类全限名称
            xstream.alias("monitorInfo", MonitorInfo.class);
            String json = "{'monitorInfo':"+response+"}";
            MonitorInfo monitorInfo = (MonitorInfo) xstream.fromXML(json);
            //System.out.println(monitorInfo.getCpuRatio());

            monitorInfoList.add(monitorInfo);

        }
        //根据负载算法，选择合适的server
        List<Float> wi = new ArrayList<>();
        List<Float> wl = new ArrayList<>();
        List<Float> w = new ArrayList<>();

        int serverNumber = allServers.size();
        double cpuRatioSum = 0;
        long usedMemorySum = 0;
        for(int i = 0 ; i < serverNumber ; i++){
            cpuRatioSum += monitorInfoList.get(i).getCpuRatio();
            usedMemorySum += monitorInfoList.get(i).getUsedMemory();
        }

        for(int i = 0 ; i < serverNumber ; i++){
            float temp = (float) (1.0 /(float) serverNumber);
            wi.add(temp);

            float kc = (float) (monitorInfoList.get(i).getCpuRatio() / cpuRatioSum);
            float ku = monitorInfoList.get(i).getUsedMemory() / usedMemorySum;
            wl.add(kc+ku);

            w.add(wi.get(i)*(1- wl.get(i)));
        }

        float max = 0;
        int sv = 0;
        for(int i = 0 ; i < serverNumber ; i++){
            if(max < w.get(i)){
                max = w.get(i);
                sv = i;
            }
        }
        server = allServers.get(sv);


//        server = allServers.get(0);
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

        return choose(getLoadBalancer(), key);
    }
}
