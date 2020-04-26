package com.arvato.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 主键生成器
 *
 * @author KARL.ROSE
 * @date 2020/4/23 16:41
 **/
public class NumUtil {

    private static long tmpID = 0;
    private static final long LOCK_TIME = 1;
    private static final long INCREASE_STEP = 1;
    private static final Lock LOCK = new ReentrantLock();

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    private static final Logger log = LoggerFactory.getLogger(NumUtil.class);
    public static Long nextPkId() throws InterruptedException {
        //当前：（年、月、日、时、分、秒、毫秒）
        long timeCount;
        if (LOCK.tryLock(LOCK_TIME, TimeUnit.SECONDS)) {
            timeCount = Long.parseLong(DATE_FORMATTER.format(LocalDateTime.now()));
            try {
                if (tmpID < timeCount) {
                    tmpID = timeCount;
                } else {
                    tmpID += INCREASE_STEP;
                    timeCount = tmpID;
                }
                return timeCount;
            } finally {
                LOCK.unlock();
            }
        } else {
            log.error("lock failed");
            return nextPkId();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Long ss = nextPkId();
        System.out.println(ss);
    }
}
