package org.example.practice;

/**
 * @Author: xs
 * @Date: 2019-11-27 15:21
 * @Description: 排序问题
 */
public class Sort {

    /**
     * 冒泡排序: 两层for循环,外层i让每一个元素都能遍历到,内层j控制相邻的元素进行大小对比
     * 注意if判断,对比相邻的两个元素,将大的放到右边,那么右边在第一次遍历后会产生一个最大元素
     * i值的逐渐变大,那么依次右边的的元素也确定了
     *
     * @param arr
     * @return
     */
    private static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 选择排序: i控制的for循环遍历当前元素,j控制的for循环每次都去找当前元素右边的最小元素,和当前元素互换位置
     *
     * @param arr
     * @return
     */
    private static int[] selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }
            if (index != i) {
                int temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        }
        return arr;
    }

    /**
     * 插入排序:从第二个元素开始,j控制的for循环内只进行替换,当替换条件不成立后,跳出j循环进行交换
     *
     * @param arr
     * @return
     */
    private static int[] insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j;
            if (arr[i] < arr[i - 1]) {
                int temp = arr[i];
                for (j = i - 1; j >= 0 && temp < arr[j]; j--) {
                    arr[j + 1] = arr[j];
                }
                arr[j + 1] = temp;
            }
        }
        return arr;
    }

//    public static void insertSort(int[] arr) {
//        int temp;
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = i; j > 0; j--) {
//                if (arr[j - 1] > arr[j]) {
//                    temp = arr[j - 1];
//                    arr[j - 1] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
//    }

    /**
     * 希尔排序
     *
     * @param arr
     * @return
     */
    public static int[] shellSort(int[] arr) {
        int increasement = arr.length;
        int i, j, k;
        do {
            // 确定分组的增量
            increasement = increasement / 3 + 1;
            for (i = 0; i < increasement; i++) {
                for (j = i + increasement; j < arr.length; j += increasement) {
                    if (arr[j] < arr[j - increasement]) {
                        int temp = arr[j];
                        for (k = j - increasement; k >= 0 && temp < arr[k]; k -= increasement) {
                            arr[k + increasement] = arr[k];
                        }
                        arr[k + increasement] = temp;
                    }
                }
            }
        } while (increasement > 1);
        return arr;
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param start
     * @param end
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start >= end)
            return;
        int i = start;
        int j = end;
        // 基准数
        int baseval = arr[start];
        while (i < j) {
            // 从右向左找比基准数小的数
            while (i < j && arr[j] >= baseval) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }
            // 从左向右找比基准数大的数
            while (i < j && arr[i] < baseval) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        // 把基准数放到i的位置
        arr[i] = baseval;
        // 递归
        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
    }

    /**
     * 归并排序
     *
     * @param arr
     * @param start
     * @param end
     * @param temp
     */
    private static void mergeSort(int arr[], int start, int end, int... temp) {
        if (start >= end)
            return;
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid, temp);
        mergeSort(arr, mid + 1, end, temp);

        // 合并两个有序序列
        int length = 0; // 表示辅助空间有多少个元素
        int i_start = start;
        int i_end = mid;
        int j_start = mid + 1;
        int j_end = end;
        while (i_start <= i_end && j_start <= j_end) {
            if (arr[i_start] < arr[j_start]) {
                temp[length] = arr[i_start];
                length++;
                i_start++;
            } else {
                temp[length] = arr[j_start];
                length++;
                j_start++;
            }
        }
        while (i_start <= i_end) {
            temp[length] = arr[i_start];
            i_start++;
            length++;
        }
        while (j_start <= j_end) {
            temp[length] = arr[j_start];
            length++;
            j_start++;
        }
        // 把辅助空间的数据放到原空间
        for (int i = 0; i < length; i++) {
            arr[start + i] = temp[i];
        }
    }

    private static void heapAdjust(int arr[], int i, int length) {
        // 调整i位置的结点
        // 先保存当前结点的下标
        int max = i;
        // 当前结点左右孩子结点的下标
        int lchild = i * 2 + 1;
        int rchild = i * 2 + 2;
        if (lchild < length && arr[lchild] > arr[max]) {
            max = lchild;
        }
        if (rchild < length && arr[rchild] > arr[max]) {
            max = rchild;
        }
        // 若i处的值比其左右孩子结点的值小，就将其和最大值进行交换
        if (max != i) {
            int temp;
            temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;
            // 递归
            heapAdjust(arr, max, length);
        }
    }

    // 堆排序
    private static void heapSort(int arr[], int length) {
        // 初始化堆
        // length / 2 - 1是二叉树中最后一个非叶子结点的序号
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapAdjust(arr, i, length);
        }
        // 交换堆顶元素和最后一个元素
        for (int i = length - 1; i >= 0; i--) {
            int temp;
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            heapAdjust(arr, 0, i);
        }
    }


    private static void printArr(int[] arr) {
        for (int i :
                arr) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 4, 2, 1, 9, 8, 6, 7};
        int[] temp = {0, 0, 0, 0, 0, 0, 0, 0, 0};
//         printArr(insertSort(arr));
//         quickSort(arr, 0, arr.length - 1);
//         heapSort(arr, arr.length);
        mergeSort(arr, 0, arr.length - 1, temp);
        printArr(arr);
    }
}
