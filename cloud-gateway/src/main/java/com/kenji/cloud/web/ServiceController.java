package com.kenji.cloud.web;

import com.kenji.cloud.entity.ServiceInfo;
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
public class ServiceController {
    @Autowired
    private MonitorServiceImpl monitorService;
    @GetMapping("/status/{serviceName}")
    public ServiceInfoVO getServiceStatus(@PathVariable String serviceName){
        ServiceInfo service = monitorService.findByServiceName(serviceName);
        return new ServiceInfoVO(1, "查询成功", service.getStatus());
    }
    @GetMapping("/callNumber/{serviceName}")
    public ServiceInfoVO getServiceCallNumber(@PathVariable String serviceName){
        ServiceInfo service = monitorService.findByServiceName(serviceName);
        return new ServiceInfoVO(1, "查询成功", service.getCallNumber());
    }
    @PutMapping("/update/{serviceName}/{serviceStatus}")
    public ServiceInfoVO updateServiceStatus(@PathVariable String serviceName, @PathVariable String serviceStatus){
        monitorService.updateServiceStatus(serviceName, serviceStatus);
        return new ServiceInfoVO(1, "更新成功");
    }
}
