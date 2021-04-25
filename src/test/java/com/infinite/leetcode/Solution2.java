package com.infinite.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author allen
 *题目（两数相加）：
 *给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *示例：
 *输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 *输出：7 -> 0 -> 8
 *原因：342 + 465 = 807
 */
public class Solution2 {

	 public static class ListNode {
	     int val;
	     ListNode next;
	     ListNode(int x) { val = x; }
	 }
	 
	 public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		 StringBuilder data1=new StringBuilder();
		 StringBuilder data2=new StringBuilder();
		 
		 ListNode newl1=reverseLink(l1);
		 ListNode newl2=reverseLink(l2);
		 do {
			 data1.append(newl1.val);
			 newl1=newl1.next;
		 } while (newl1!=null);
		 
		 do {
			 data2.append(newl2.val);
			 newl2=newl2.next;
		 } while (newl2!=null);
		
		 Integer total=Integer.parseInt(data1.toString())+Integer.parseInt(data2.toString());
		 
		 char[] nodeValArr=String.valueOf(total).toCharArray();
		 System.out.println(nodeValArr);
		 int length=nodeValArr.length;
		 ListNode header=new ListNode(Integer.parseInt(String.valueOf(nodeValArr[length-1])));
		 ListNode p=header;
		 for(int i=length-2;i>=0;i--){
			 ListNode temp=new ListNode(Integer.parseInt(String.valueOf(nodeValArr[i])));
			 p.next=temp;
			 p=temp;
		 }
		 return header;
	 }
	 
	 public static ListNode reverseLink(ListNode node){
		 List<ListNode> nodeList=new ArrayList<>();
         do {
        	 nodeList.add(node);
        	 node=node.next;
		 } while (node!=null);
         
         int size=nodeList.size();
         for(int i=size-1;i>0;i--){
        	 nodeList.get(i).next=nodeList.get(i-1);
        	 if(i==1){
        		 nodeList.get(i-1).next=null;
        	 }
         }
         
         return nodeList.get(size-1);
	 }
	 
	 public static void main(String[] args) {
		 ListNode node1=new ListNode(2);
		 node1.next=new ListNode(4);
		 node1.next.next=new ListNode(3);	
		 
		 ListNode node2=new ListNode(5);
		 node2.next=new ListNode(6);
		 node2.next.next=new ListNode(4);	
		 
		 printLink(addTwoNumbers(node1, node2));
		 
		 
	}
	 
	public static void printLink(ListNode link){
		do{
			 
			 System.out.print(link.val);
			 if(link.next!=null){
				 System.out.print("->");
			 }
			 link=link.next;
		 }while(link!=null);
	} 
}
