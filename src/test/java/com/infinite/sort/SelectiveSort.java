package com.infinite.sort;

/**
 * 
* @ClassName: SelectiveSort
* @Description: 选择排序
* @author chenliqiao
* @date 2019年3月11日 下午4:55:37
*
 */
public class SelectiveSort {
    
    private static int[] sort(int[] dataArray){
        if(dataArray==null||dataArray.length==0){
            return dataArray;
        }
        
        int length=dataArray.length;
        //i<length-1，因为最后一个元素往后没有与它比较的元素了
        for(int i=0;i<length-1;i++){
            //假定minDataIndex是最小的数的下标索引
            int minDataIndex=i;
            //j=i+1，因为每完成一次外部i的循环，i左边的元素比较完毕，可以忽略不比较
            for(int j=i+1;j<length;j++){
                //j下标索引对应的数比minDataIndex下标对应的数据小，则将minDataIndex替换j
                if(dataArray[minDataIndex]>dataArray[j]){
                    minDataIndex=j;
                }
            }
            
            //i对应的内部循环完成
            //minDataIndex不等于一开始假定的最小数据下标i,交换minDataIndex下标索引和i下标索引对应的元素的位置
            if(minDataIndex!=i){
                int temp=dataArray[i];
                dataArray[i]=dataArray[minDataIndex];
                dataArray[minDataIndex]=temp;
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
