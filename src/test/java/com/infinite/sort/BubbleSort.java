package com.infinite.sort;

/**
 * 
* @ClassName: BubbleSort
* @Description: 冒泡排序
* @author chenliqiao
* @date 2019年3月11日 下午4:35:26
*
 */
public class BubbleSort {
    
    private static int[] sort(int[] dataArray){
        if(dataArray==null||dataArray.length==0){
            return dataArray;
        }
        
        int length=dataArray.length;
        //外部循环进行(length-1)趟循环
        for(int i=0;i<length-1;i++){
            //内部循环每次进行(length-1-i)次比较，因为每次比较要忽略尾部已经确定的数
            for(int j=0;j<length-1-i;j++){
                if(dataArray[j]>dataArray[j+1]){
                    int temp=dataArray[j];
                    dataArray[j]=dataArray[j+1];
                    dataArray[j+1]=temp;
                }
            }
        }
        
        return dataArray;
    }
    
    private static void printArray(int[] dataArray){
        for (int i = 0; i < dataArray.length; i++) {
            System.out.print(dataArray[i]+" ");
        }
    }
    
    public static void main(String[] args) {
        int[] dataArray={1,5,2,6,3,56,24,89,15,62,55,54,96,88,77};
        printArray(dataArray);
        System.out.println();
        printArray(sort(dataArray));
    }

}
