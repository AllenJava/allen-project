package com.infinite.concurrent.wait;

/**
 * 等待超时模式：
 * 
 * @author allen
 *
 */
public class WaitTimeOutModel {
	
	private Object lock=new Object();
	
	/**
	 * 
	 * @param mills 过期时间，单位毫秒
	 * @return
	 * @throws Exception 
	 */
	public Object get(long mills) throws Exception{
		//过期的时间点
		long futureTime=System.currentTimeMillis()+mills;
		//超时时间
		long remainingTime=mills;
		//结果
		Object result=null;
		
		//当超时时间>0 并且 result不满足要求时
		synchronized (lock) {
			//不符合条件
			while(result==null && remainingTime>0){
				//wait至remainingTime毫秒后，程序往下执行				
				lock.wait(remainingTime);
				
				//remainingTime是作为跳出循环的条件
				remainingTime=futureTime-System.currentTimeMillis();
			}
			
			//符合条件
			return "get success";
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		WaitTimeOutModel wtm=new WaitTimeOutModel();
		System.out.println(wtm.get(5000));
	}

}
