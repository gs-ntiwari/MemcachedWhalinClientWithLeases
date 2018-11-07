package com.meetup.memcached.test;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

public class ShashwatTest {

    public static void println(Object args) {
        System.out.println(args);
    }

    public static void main(String[] args) {
        String[] serverlist = {"localhost:11211"};

        // initialize the pool for memcache servers
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers( serverlist );

        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(50);
        pool.setMaintSleep(30);

        pool.setNagle(false);
        pool.initialize();

        MemcachedClient mc = new MemcachedClient();
        mc.setCompressEnable(false);
        mc.setCompressThreshold(0);
        long start = System.currentTimeMillis();
        long start_nano = System.nanoTime();

//        println(mc.get("hello"));
        mc.set("hello", "world");
        println(mc.get("hello"));
//        mc.set("hello", "world");
//        println(mc.get("hello"));
//        mc.set("hello", "world");
//        println(mc.get("hello"));
//        mc.set("hello", "world");
//        println(mc.get("hello"));

        long time_nano = System.nanoTime() - start_nano;
        long time = System.currentTimeMillis() - start;
        println("Result in " + (time) + " ms " + (time_nano) + " ns");
    }
}
