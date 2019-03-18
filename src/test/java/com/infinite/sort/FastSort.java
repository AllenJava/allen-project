package com.infinite.sort;

public class FastSort {
    
    private static void sort(int[] dataArray,int i,int j){
        if(dataArray==null||dataArray.length==0){
            return ;
        }
        
        int startPointIndex=i;
        int endPointIndex=j;
        int baseData=dataArray[i];
//        for (;endPointIndex >i;endPointIndex--) {
//            if(endPointIndex>startPointIndex){
//                if(baseData>dataArray[endPointIndex]){
//                    int temp=dataArray[endPointIndex];
//                    dataArray[endPointIndex]=baseData;
//                    dataArray[startPointIndex]=temp;
//                }else{
//                    continue;
//                }
//                
//                for(;startPointIndex<j;startPointIndex++){
//                    if(baseData<dataArray[startPointIndex]){
//                        int temp1=dataArray[startPointIndex];
//                        dataArray[startPointIndex]=baseData;
//                        dataArray[endPointIndex]=temp1;
//                        break;
//                    }
//                }
//            }
//        }
        while(endPointIndex>startPointIndex){
            
        }
        
        printArray(dataArray);
        System.out.println("startPointIndex:"+startPointIndex+" endPointIndex:"+endPointIndex);
        
        if(startPointIndex>i){
            sort(dataArray, i, startPointIndex-1);
        }
        if(endPointIndex<j){
            sort(dataArray, endPointIndex+1, j);
        }
    }
    
    private static void printArray(int[] dataArray){
        for (int i = 0; i < dataArray.length; i++) {
            System.out.print(dataArray[i]+" ");
        }
    }
    
    public static void main(String[] args) {
        int[] dataArray={58,5,2,6,3,56,24,89,15,62,55,54,96,88,77};
        printArray(dataArray);
        System.out.println();
        sort(dataArray, 0, dataArray.length-1);
        printArray(dataArray);
    }
//    public static void main(String []args){
//        int[] a = {12,20,5,16,15,1,30,45,23,9};
//        int start = 0;
//        int end = a.length-1;
//        sort(a,start,end);
//        for(int i = 0; i<a.length; i++){
//             System.out.print(a[i]+" ");
//         }
//        
//     }
//     
//     public static void sort(int[] a,int low,int high){
//         int start = low;
//         int end = high;
//         int key = a[low];
//         
//         
//         while(end>start){
//             //从后往前比较
//             while(end>start&&a[end]>=key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
//                 end--;
//             if(a[end]<=key){
//                 int temp = a[end];
//                 a[end] = a[start];
//                 a[start] = temp;
//             }
//             //从前往后比较
//             while(end>start&&a[start]<=key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
//                start++;
//             if(a[start]>=key){
//                 int temp = a[start];
//                 a[start] = a[end];
//                 a[end] = temp;
//             }
//         //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
//         }
//         //递归
//         if(start>low) sort(a,low,start-1);//左边序列。第一个索引位置到关键值索引-1
//         if(end<high) sort(a,end+1,high);//右边序列。从关键值索引+1到最后一个
//     }

}
