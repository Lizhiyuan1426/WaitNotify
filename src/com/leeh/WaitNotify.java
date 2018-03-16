package com.leeh;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Data{
	private int count = 0;
	private Lock lock = new ReentrantLock();
	private Condition codition = lock.newCondition();
	
	public void increment() throws Exception {
		lock.lock();
		try {
			// 判断
			while(count != 0) {
				codition.await();
			}
			// 干活
			++count;
			System.out.println(Thread.currentThread().getName()+ "\t" + count);
			// 通知
			codition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void decrement() throws Exception {
		lock.lock();
		try {
			// 判断
			while(count == 0) {
				codition.await();
			}
			// 干活
			--count;
			System.out.println(Thread.currentThread().getName()+ "\t" + count);
			// 通知
			codition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	/*public synchronized void increment() throws Exception {
		//判断
		while(count != 0) {
			this.wait();
		}
		// 干活
		++count;
		System.out.println(Thread.currentThread().getName()+"\t"+count);
		// 通知
		this.notifyAll();
	}
	
	public synchronized void decrement() throws Exception {
		// 判断
		while(count == 0) {
			this.wait();
		}
		//干活
		--count;
		System.out.println(Thread.currentThread().getName()+"\t"+count);
		this.notifyAll();
	}*/
}

	 /**
	 * @Description: 
	 * 现在两个线程，
	 * 可以操作初始值为零的一个变量，
	 * 实现一个线程对该变量加1，一个线程对该变量减1，
	 * 交替，来10轮，变量初始值为零。
	 * @author Lee
	 * 1 多线程编写套路------上
	 * 		1.1	线程		操作(实例方法)		资源类
	 * 		1.2  高内聚  低耦合
	 * 
	 * 2 多线程编写套路------下
	 * 		2.1 判断
	 * 		2.2 干活
	 * 		2.3 通知
	  *
	  */
public class WaitNotify {

	public static void main(String[] args) {
		Data d = new Data();
		
		new Thread((Runnable) () -> {
			for (int i = 1; i <= 10; i++) {
				try {
					d.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "AA").start();
		
		new Thread((Runnable) () -> {
			for (int i = 1; i <= 10; i++) {
				try {
					d.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "BB").start();
		
		new Thread((Runnable) () -> {
			for (int i = 1; i <= 10; i++) {
				try {
					d.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "CC").start();
		
		new Thread((Runnable) () -> {
			for (int i = 1; i <= 10; i++) {
				try {
					d.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "DD").start();
	}

}
