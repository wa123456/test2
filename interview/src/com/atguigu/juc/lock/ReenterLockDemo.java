package com.atguigu.juc.lock;

/**
 * 可重入锁（也叫递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * <p>
 * 也就是说：`线程可以进入任何一个它已经拥有的锁所同步的代码块`
 * <p>
 * t1	 invoked sendSMS()      t1线程在外层方法获取锁的时候
 * t1	 invoked sendEmail()    t1在进入内层方法会自动获取锁
 * <p>
 * t2	 invoked sendSMS()      t2线程在外层方法获取锁的时候
 * t2	 invoked sendEmail()    t2在进入内层方法会自动获取锁
 */


class Phone {

    //发送短信

    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        Thread.sleep(3000);
        // 在同步方法中，调用另外一个同步方法
        sendEmail();
    }

    //发邮件
    public synchronized void sendEmail() throws Exception {
        //todo 为什么有个12  13呢？
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");

    }
}

public class ReenterLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        // 两个线程操作资源列
        new Thread(() -> {
            try {
                phone.sendSMS();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}