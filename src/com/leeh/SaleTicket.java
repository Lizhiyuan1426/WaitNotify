package com.leeh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类 = 实例变量 + 实例方法
 * 
 * @author Lee
 *
 */
class Ticket {
	private int number = 30;
	//Lock implementations provide more extensive locking operations
	//than can be obtained using synchronized methods and statements
	private Lock lock = new ReentrantLock();

	public void sale() {
		lock.lock();
		try {
			if (number > 0) {
				System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票, 剩余" + number + "张票");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

/**
 * @description: 卖票: 3个卖票员 卖 30张票
 * @author Lee
 * 
 * 1 多线程编写套路------上 
 *     1.1 线程 操作(实例方法) 资源类 
 *     1.2 高内聚 低耦合
 *
 */
public class SaleTicket {

	public static void main(String[] args) {
		Ticket t = new Ticket();
		
		// 使用lambda表达式
		new Thread(() -> {for (int i = 0; i < 40; i++) t.sale();}, "AA").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) t.sale();}, "BB").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) t.sale();}, "CC").start();
		
		/*new Thread((Runnable) () -> {
			for (int i = 0; i < 40; i++) {
				t.sale();
			}
		}, "AA").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					t.sale();
				}
			}
		}, "BB").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					t.sale();
				}
			}
		}, "CC").start();*/
	}

}
