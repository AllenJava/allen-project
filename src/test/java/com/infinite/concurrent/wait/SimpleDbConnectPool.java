package com.infinite.concurrent.wait;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    
    public DbConnection getConnection() throws Exception{
        synchronized (lock) {
            //不符合条件，wait
            while(!hashFreeConnection()){
                lock.wait(); 
            }
            
            //符合条件
            Optional<DbConnection> optional=minPool.stream().filter(e ->e.isFree).findFirst();
            if(optional.isPresent()){
                return optional.get();
            }
            return null;
        }
    }
    
    private boolean hashFreeConnection(){
        return minPool.stream().anyMatch(connection -> connection.isFree);
    }
    
    
    static class DbConnection{
        
        private boolean isFree;
        
        public DbConnection(){}
        
        public DbConnection(boolean isFree){this.isFree=isFree;}
    }

}
