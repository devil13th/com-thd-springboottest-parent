package com.thd.springboottest.optimize.controller;

import com.thd.springboottest.optimize.dto.MyBean;
import com.thd.springboottest.optimize.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/optimize")
public class OptimizeController {
    @Autowired
    private MyService myService;


    private Object lockA = new Object();
    private Object lockB = new Object();

    private ReentrantLock lockC = new ReentrantLock();
    private ReentrantLock lockD = new ReentrantLock();

    @RequestMapping(value="/normal")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/optimize/normal
    public String normal(){
        return "normal ";
    }

    /**
     * 死锁例子
     * 1.访问http://127.0.0.1:8899/thd/optimize/deadLockForReentrantLock
     * 2.使用jstack [jvm线程号] 查看线程会显示出死锁线程
     * @return
     */
    //url : http://127.0.0.1:8899/thd/optimize/deadLockForReentrantLock
    @ResponseBody
    @RequestMapping(value="/deadLockForReentrantLock")
    public String deadLockForReentrantLock(){


        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    lockC.lock();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("C");
                    lockD.lock();
                    System.out.println("D");
                }catch(Exception e){
                    lockC.unlock();
                    lockD.unlock();
                }

            }
        }).start();


        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    lockD.lock();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("D");
                    lockC.lock();
                    System.out.println("C");
                }catch(Exception e){
                    lockD.unlock();
                    lockC.unlock();
                }

            }
        }).start();

        return "deallock for reentrant lock";

    }

    /**
     * 死锁例子
     * 1.访问http://127.0.0.1:8899/thd/optimize/deadLockForSynchronizedBlock
     * 2.使用jstack [jvm线程号] 查看线程会显示出死锁线程
     * @return
     */
    //url : http://127.0.0.1:8899/thd/optimize/deadLockForSynchronizedBlock
    @ResponseBody
    @RequestMapping(value="/deadLockForSynchronizedBlock")
    public String deadLockForSynchronizedBlock(){

        new Thread(new Runnable(){
            @Override
            public void run() {
                synchronized(lockA){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("A");
                    synchronized(lockB){
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("B");

                    }
                }
            }
        }).start();


        new Thread(new Runnable(){
            @Override
            public void run() {
                synchronized(lockB){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("A");
                    synchronized(lockA){
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("A");

                    }
                }
            }
        }).start();


        return "deadlock for synchronized ";
    }

    /**
     * 栈溢出
     *
     */
    //url : http://127.0.0.1:8899/thd/optimize/stackOver
    @RequestMapping(value="/stackOver")
    @ResponseBody
    public String stackOver(){
        myService.stackOver(0);
        return "stack over";
    }

    /**
     * 访问此方法,用jstact 查看线程
     * 或者用arthas 的 thread -n 3查看3个最忙的线程
     * @return
     */
    //url : http://127.0.0.1:8899/thd/optimize/monitorThread
    @RequestMapping(value="/monitorThread")
    @ResponseBody
    public String monitorThread(){
        int i = 0;
        while(i < 999999999){
            System.out.println(Thread.currentThread().getName() + ":" + i++);
        }
        return "monitorThread";
    }


    /**
     * jvm参数:-Xms100m -Xmx100m -XX:SurvivorRatio=8 -XX:NewRatio=2
     * 创建大对象造成内存溢出
     * @return
     */
    //url : http://127.0.0.1:8899/thd/optimize/bigObject
    @RequestMapping(value="/bigObject")
    @ResponseBody
    public String bigObject(){
        int i = 0;
        List l = new ArrayList();
        while(i < 999999999){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyBean mb = new MyBean();
            l.add(mb);
        }
        return "big Object";
    }

    /**
     * jvm参数:-Xms100m -Xmx100m -XX:SurvivorRatio=8 -XX:NewRatio=2
     */
    public void headSize(){

    }


}
