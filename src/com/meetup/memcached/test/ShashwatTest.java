package com.meetup.memcached.test;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.meetup.memcached.test.UnitTests.mc;

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

        tryMethod(mc);

        long time_nano = System.nanoTime() - start_nano;
        long time = System.currentTimeMillis() - start;
        println("Result in " + (time) + " ms " + (time_nano) + " ns");
    }

    private static void tryMethod(MemcachedClient mc) {
        SimpleDateFormat sd = new SimpleDateFormat();
        Date currentDate=sd.getCalendar().getTime();
        currentDate.setTime(currentDate.getTime()+1000);
        //String.format( "%s %s %s %d %d %d\r\n","lset", "1", "k1", 0|2, (currentDate.getTime()/ 1000), 10 );
        String sessionID = mc.beginSession();
        mc.lset("key2", "value1", currentDate, sessionID);
        mc.lget("key3", null, true, sessionID);
        mc.endSession(sessionID);
    }
}
