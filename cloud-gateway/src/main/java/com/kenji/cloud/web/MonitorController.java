package com.kenji.cloud.web;

import com.netflix.appinfo.InstanceInfo;
import com.kenji.cloud.service.impl.MonitorServiceImpl;
import com.kenji.cloud.vo.ServiceInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 服务状态 服务调用次数 更新服务状态
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:20 PM
 */
@RestController
@RequestMapping("/service")
public class MonitorController {
    @Autowired
    private MonitorServiceImpl monitorService;
    @GetMapping("/status/{serviceName}")
    public ServiceInfoVO getServiceStatus(@PathVariable String serviceName){
        InstanceInfo service = monitorService.findByAppName(serviceName);
        return new ServiceInfoVO(1, "查询成功", service.getStatus());
    }
    @GetMapping("/callNumber/{serviceName}")
    public ServiceInfoVO getServiceCallNumber(@PathVariable String serviceName){
        InstanceInfo service = monitorService.findByAppName(serviceName);
        return new ServiceInfoVO(1, "查询成功", service.getInvokeCount());
    }
    @PutMapping("/update/{serviceName}/{serviceStatus}")
    public ServiceInfoVO updateServiceStatus(@PathVariable String serviceName, @PathVariable String serviceStatus){
        InstanceInfo.InstanceStatus instanceStatus = InstanceInfo.InstanceStatus.toEnum(serviceStatus);
        monitorService.updateStatus(serviceName, instanceStatus);
        return new ServiceInfoVO(1, "更新成功");
    }
}
