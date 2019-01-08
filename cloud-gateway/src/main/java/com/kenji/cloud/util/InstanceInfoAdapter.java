package com.kenji.cloud.util;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.BeanUtils;


public class InstanceInfoAdapter {
    public static boolean copyProperties(InstanceInfo source, com.kenji.cloud.entity.InstanceInfo target) {
        BeanUtils.copyProperties(source, target );
        String dataCenterInfo;
        String metadata;
        return true;
    }
}
