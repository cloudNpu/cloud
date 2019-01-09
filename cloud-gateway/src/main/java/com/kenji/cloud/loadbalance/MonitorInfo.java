package com.kenji.cloud.loadbalance;

import lombok.Data;

/**
 * @author SHI Jing
 * @date 2019/1/7 16:09
 */
@Data
public class MonitorInfo {
    /*
    maxMemory()为JVM的最大可用内存，可通过-Xmx设置，默认值为物理内存的1/4，设置不能高于计算机物理内存；
    totalMemory()为当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和，会随着JVM使用内存的增加而增加；
    freeMemory()为当前JVM空闲内存，因为JVM只有在需要内存时才占用物理内存使用，所以freeMemory()的值一般情况下都很小，而JVM实际可用内存并不等于freeMemory()，
                 而应该等于maxMemory()-totalMemory()+freeMemory()。
     */
    /** 可使用内存. */
    private long totalMemory;

    /** 剩余内存. */
    private long freeMemory;

    /** 最大可使用内存. */
    private long maxMemory;

    /** 操作系统. */
    private String osName;

    /** 总的物理内存. */
    private long totalMemorySize;

    /** 剩余的物理内存. */
    private long freePhysicalMemorySize;

    /** 已使用的物理内存. */
    private long usedMemory;

    /** 线程总数. */
    private int totalThread;

    /** cpu使用率. */
    private double cpuRatio;
}
