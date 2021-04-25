package com.infinite.concurrent.wait;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SimpleDbConnectPool {
    
    private Object lock=new Object();
    
    private int minSize=5;
    
    private int maxSize=10;
    
    private int queueSize=50;
    
    private int maxTimeout=30000;
    
    private List<DbConnection> minPool=new ArrayList<>();
    
    private List<DbConnection> maxPool=new ArrayList<>();
    
    /**
     * init
     */
    public SimpleDbConnectPool(){
        for (int i = 0; i < minSize; i++) {
            minPool.add(new DbConnection(true));
        }
    }
    
    
    public DbConnection getConnectionFromPool() throws Exception{
        synchronized (lock) {
            //不符合条件，wait
            while(!hashFreeConnection()){
                lock.wait(); 
            }
            
            //符合条件
            Optional<DbConnection> optional=minPool.stream().filter(e ->e.isFree).findFirst();
            DbConnection connection=null;
            if(optional.isPresent()){
            	connection=optional.get();
            	connection.setFree(true);
            }
            return connection;
        }
    }
    
    public void returnConnection(DbConnection connection){
    	connection.setFree(true);
    	lock.notifyAll();
    }
    
    private boolean hashFreeConnection(){
        return minPool.stream().anyMatch(connection -> connection.isFree);
    }
    
    
    static class DbConnection{
        
        private boolean isFree;
        
        public DbConnection(){}
        
        public DbConnection(boolean isFree){this.isFree=isFree;}

		public boolean isFree() {
			return isFree;
		}

		public void setFree(boolean isFree) {
			this.isFree = isFree;
		}
    }
    
    public static void main(String[] args) {
    	SimpleDbConnectPool pool=new SimpleDbConnectPool();
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				DbConnection connection=null;
				try {
					connection=pool.getConnectionFromPool();
					System.out.println("获取连接对象->"+connection);
					TimeUnit.SECONDS.sleep(2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					pool.returnConnection(connection);
				}
			}).start();
		}
	}

}
