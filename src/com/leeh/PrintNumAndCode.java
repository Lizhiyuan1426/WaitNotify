package com.leeh;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NumAndCode{
	private char code = 'A'-1;
	private int num = 0;
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	
	public void printNum() {
		lock.lock();
		try {
			// 判断
			while(num != 52) {
				// 干活
				System.out.print(++num+""+(++num));
				// 通知
				condition.signalAll();
				condition.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void printCode() {
		lock.lock();
		try {
			// 判断
			while(code != 'Z') {
				// 干活
				code=(char)(code + 1);
				System.out.print(code);
				// 通知
				condition.signalAll();
				condition.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

public class PrintNumAndCode {

	public static void main(String[] args) {
		NumAndCode nc = new NumAndCode();
		
		new Thread(() -> {
//			for (int i = 0; i < 10; i++) {
				nc.printNum();
//			}
		}, "Num:\t").start();
		
		new Thread(() -> {
//			for (char i = 'A'; i < 'Z'; i++) {
				
				nc.printCode();;
//			}
		}, "Code:\t").start();
	}

}
