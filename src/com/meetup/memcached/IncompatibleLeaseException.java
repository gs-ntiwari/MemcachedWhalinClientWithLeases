package com.meetup.memcached;

public class IncompatibleLeaseException extends RuntimeException{
    public IncompatibleLeaseException(String errMsg)
    {
        super(errMsg);
    }
}
