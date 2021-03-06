package org.example.practice;

/**
 * @Author: xs
 * @Date: 2019-11-28 15:05
 * @Description: 二分查找问题
 */
public class BinarySearch {

    /**
     * 非递归的解决方式,确定最小下标,最大下标,中间下标
     * 当最小下标小于最大下标一直循环
     * 比较中间下标对应值与目标值的大小
     * 相等则找到
     * 比目标值小则最小下标置为中间下标+1
     * 比目标值大则最大下标置为中间下标-1
     *
     * @param arr
     * @param des
     * @return
     */
    public static int binarySearch(int[] arr, int des) {
        int low = 0;
        int height = arr.length - 1;
        while (low <= height) {
            int mid = (low + height) / 2;
            if (des == arr[mid]) {
                return mid;
            } else if (des < arr[mid]) {
                height = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 递归的解决方式,计算中间下标,比较目标值是否在范围内,以及最小下标最大下标是否正确
     * 判断目标值与中间下标对应值,递归函数重新赋值
     *
     * @param arr
     * @param des
     * @param start
     * @param end
     * @return
     */
    private static int binarySearch(int[] arr, int des, int start, int end) {
        int mid = (start + end) / 2;
        if (des < arr[start] || des > arr[end] || start > end) {
            return -1;
        }
        if (des < arr[mid]) {
            return binarySearch(arr, des, start, mid - 1);
        } else if (des > arr[mid]) {
            return binarySearch(arr, des, mid + 1, end);
        } else {
            return mid;
        }
    }



    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        System.out.println(binarySearch(arr, 4));
    }
}
