package main;

import java.util.Arrays;
import java.util.Scanner;

public class Question3 {
	
	public static int[] getRandomNumbersList() {
		int[] ranNum = new int[500];
		
		for(int i = 0; i < ranNum.length; i++) {
			ranNum[i] = (int) (Math.random() * 1000);
		}
		return ranNum;
	}
	
	public Object[] partition(int[] array, int pivotIndex) {
		int left = 0;
		int right = array.length-1;
		
		int temp = array[pivotIndex];
		array[pivotIndex] = array[right];
		array[right] = temp;
		
		int pivot = array[right];
		int swapIndex = left;
		
		for(int i = left; i < right; i++) {
			if(array[i] < pivot) {
				temp = array[swapIndex];
				array[swapIndex] = array[i];
				array[i] = temp;
				swapIndex++;
			}
		}
		
		temp = array[swapIndex];
		array[swapIndex] = array[right];
		array[right] = temp;
		pivotIndex = swapIndex;

		return new Object[]{
			Arrays.copyOfRange(array, 0, pivotIndex), 
			pivotIndex, 
			Arrays.copyOfRange(array, pivotIndex+1, array.length)
		};
	}
	
	
	public Integer findKthSmallestElement(int[] array, int k) {
		if(k < 1 || k > array.length || array.length < 1)
			return null;
		if(array.length < 2)
			return array[0];
		
		int pivotIndex = (int) (Math.random()*(array.length-1));
		Object[] partitionResults = this.partition(array, pivotIndex);
		
		pivotIndex = (int) partitionResults[1];
		int[] leftSubArray = (int[]) partitionResults[0];
		int[] rightSubArray = (int[]) partitionResults[2];
		
//		System.out.println(Arrays.toString(leftSubArray));
//		System.out.println(Arrays.toString(rightSubArray));
//		System.out.println(array[pivotIndex]);
//		System.out.println(pivotIndex);
//		System.out.println(k-1);

		if(pivotIndex == k-1)
			return array[pivotIndex];
		else if(pivotIndex > k-1)
			return findKthSmallestElement(leftSubArray, k);
		else
			return findKthSmallestElement(rightSubArray, k-leftSubArray.length-1);
	}
	
	public static void main(String[] args) {
		
		Question3 test = new Question3();
		int[] nums = Question3.getRandomNumbersList();
		
		System.out.println("Enter the value for k to find the kth smallest value");

		Scanner scan = new Scanner(System.in);
		int k = scan.nextInt();
		scan.close();
		
		System.out.println(Arrays.toString(nums));
		System.out.println("The " + k + "th smallest value in the array is " + test.findKthSmallestElement(nums, k));

	}
}
