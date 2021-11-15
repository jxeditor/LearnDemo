package org.example.leetcode.q0001t0100.q8;

import java.util.Arrays;

/**
 * @Author xz
 * @Date 2021/11/15 17:47
 * @Description TODO
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * <p>
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * 示例 2：
 * <p>
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 * 示例 3：
 * <p>
 * 输入：nums = [0]
 * 输出：[0]
 * 示例 4：
 * <p>
 * 输入：nums = [1]
 * 输出：[1]
 * 提示：
 * <p>
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] 为 0、1 或 2
 * 进阶：
 * <p>
 * 你可以不使用代码库中的排序函数来解决这道题吗？
 * 你能想出一个仅使用常数空间的一趟扫描算法吗？
 * Related Topics
 * 数组
 * 双指针
 * 排序
 */
public class Question0075 {
    public static void sortColors(int[] nums) {
        int start = 0, l = 0;
        int end = nums.length - 1, r = nums.length - 1;
        while (l <= r) {
            int tmp = -1;
            if (nums[l] == 0) {
                tmp = nums[l];
                nums[l] = nums[start];
                nums[start] = tmp;
                start++;
            } else if (nums[l] == 2) {
                tmp = nums[l];
                nums[l] = nums[end];
                nums[end] = tmp;
                end--;
            }
            l++;
            r--;
        }
    }

    // 单指针
    public void sortColors1(int[] nums) {
        int n = nums.length;
        int ptr = 0;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = temp;
                ++ptr;
            }
        }
        for (int i = ptr; i < n; ++i) {
            if (nums[i] == 1) {
                int temp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = temp;
                ++ptr;
            }
        }
    }

    // 双指针进阶
    public void sortColors2(int[] nums) {
        int n = nums.length;
        int p0 = 0, p2 = n - 1;
        for (int i = 0; i <= p2; ++i) {
            while (i <= p2 && nums[i] == 2) {
                int temp = nums[i];
                nums[i] = nums[p2];
                nums[p2] = temp;
                --p2;
            }
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[p0];
                nums[p0] = temp;
                ++p0;
            }
        }
    }

    public static void main(String[] args) {
        int[] inputs = {2, 0, 2, 1, 1, 0};
        sortColors(inputs);
        System.out.println(Arrays.toString(inputs));
    }
}
