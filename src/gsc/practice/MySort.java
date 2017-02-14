package gsc.practice;

import java.util.List;
/**
 * 
 * @author acm0
 * @version 1.0
 * 各类排序算法，所有算法排序结果非降序。
 */
public class MySort {

	public static void main(String[] args) {
		
		int[] arr={6,9,14,25,3,65,21,0,14};
		printIntArray(arr);
		
		//insertSort(arr);
		//bInsertSort(arr);
		//twoWayInsertSort(arr);
		//shellSort(arr);
		//partition(arr,0,arr.length-1);
//		quickSort(arr);
		insertSort2(arr);
		
		printIntArray(arr);
	}
	private static void printIntArray(int[] arr) {
		for(int i=0;i<arr.length;i++)
		{
			if(i!=arr.length)
				System.out.print(arr[i]+" ");
			else
				System.out.print(arr[i]);
		}
		System.out.println();
	}
	/**
	 * 简单插入排序
	 * @param array 需要排序的int数组
	 */
	public static void insertSort (int[] arr)
	{
		int temp;
		for(int i=1;i<arr.length;i++)
		{
			temp=arr[i];
			int j=i;
			for(;j>0;j--)
			{
				if(temp<arr[j-1])
				{
					arr[j]=arr[j-1];
				}
				else
					break;
			}
			arr[j]=temp;
		}
	}
	/**
	 * 简单插入排序，换一种实现方式
	 * @param array 需要排序的int数组
	 */
	public static void insertSort2 (int[] arr)
	{
		for(int i = 1; i <arr.length; i++) {
			int temp = arr[i];
			int j = i - 1;
			while(j>=0 && arr[j]> temp) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = temp;
		}
	}
	/**
	 * 折半插入排序
	 * @param array 需要排序的int数组
	 */
	public static void bInsertSort (int[] arr)
	{
		int temp;
		int low=0,mid=0,high=0;
		for(int i=1;i<arr.length;i++)
		{
			low=0;
			high=i-1;
			while(low<=high)
			{
				mid=(low+high)/2;
				if(arr[mid]>arr[i])
				{
					high=mid-1;
				}
				else 
				{
					low=mid+1;
				}
			}
			temp=arr[i];
			for(int j=i;j>=high+1 && j>0;j--) //hight+1 ，或者low
			{
				arr[j]=arr[j-1];
			}
			arr[high+1]=temp;				  //hight+1 ，或者low
		}
		
	}
	/**
	 * 折半插入排序
	 * 需要一个同样大小的辅助空间
	 * 把arr[0]放在辅助空间的第一个位置上，将arr中比它小的数放在它的前边（前面没有空间，从后边放），比它大的数放在后边，
	 * 可以减少移动次数
	 * @param array 需要排序的int数组
	 */
	public static void twoWayInsertSort (int[] arr)
    {
		int [] arrT = new int [arr.length];
		arrT[0] = arr[0];
		int first = arr.length-1;
		int fin = 0;   //final
		
		for(int i=1;i<arr.length;i++)
		{
			//？不需要检查first和fin冲突
			//本函数未检查
			
			if(arr[i]<arrT[0])
			{
				int j=first;
				for(;j<arr.length;j++)
				{
					if(arr[i]>arrT[j])
					{
						arrT[j-1] = arrT[j];
					}
					else
						break;
				}
				arrT[j-1] = arr[i];
				first--;
			}
			else
			{
				int j=fin;
				for(;j>=0;j--)
				{
					if(arr[i]<arrT[j])
					{
						arrT[j+1] = arrT[j];
					}
					else
						break;
				}
				arrT[j+1] = arr[i];
				fin++;
			}
			
		}
		for(int i=first+1,j=0;j<arr.length;i++,j++)//first位置多了1
		{
			arr[j]=arrT[i%arr.length];
		}
	}
	
	/**
	 * 希尔排序
	 * 普通的希尔排序，没什么可说的
	 */
	public static void shellSort(int[] arr)
	{
		int[] dlta = {5, 3, 1};   //增量序列
		for(int i=0;i<dlta.length;i++) //对每一个增量做一次插入排序
		{
			int dk=dlta[i];
	//		System.out.println(dk);
			for(int j=dk;j<arr.length;j++)//对数据进行插入排序
			{
				//System.out.println(j-dk);
				if(arr[j-dk]>arr[j])
				{
					int temp = arr[j];
					int k=j-dk;
//					System.out.println(j+"  "+k);
//					System.out.println(arr[j]+"  "+arr[k]);
					for(;k>=0 && temp<arr[k] ;k-=dk) //这里和temp比较而不是和arr[j]比较，因为arr[j]的数字会因为数据后移而
					{								 //改变，从而导致比较的错误
						arr[k+dk] = arr[k];
					}

					arr[k+dk] = temp;
				//	printIntArray(arr);
				}
			}
		}
	}
	/**
	 * 将一个int数组区间根据原区间的第一个数划分，划分效果为它左边的数都小于它
	 * 		右边的数都大于它。
	 * 本函数被用于快速排序调用
	 * 区间为开区间
	 * @return
	 */
	public static int partition(int[] arr,int low,int high)
	{
		int k = arr[low];
		while(low<high)
		{
			while(low<high && arr[high]>=k) high--;
			arr[low] = arr[high];
			while(low<high && arr[low]<=k) low++;
			arr[high] = arr[low];
		}
		arr[low] = k;
		return low;
	}
	/**
	 * 快速排序的递归的递归调用
	 * @param arr
	 * @return
	 */
	private static void qSort(int[] arr,int low,int high)
	{
		int pivotloc = partition(arr,low,high);
		if(low<pivotloc-1)
			qSort(arr,low,pivotloc-1);
		if(pivotloc+1<high)
			qSort(arr,pivotloc+1,high);
	}
	/**
	 * 递归的快速排序
	 * @param arr
	 */
	public static void quickSort(int[] arr)
	{
		qSort(arr,0,arr.length-1);
	}
	
}











