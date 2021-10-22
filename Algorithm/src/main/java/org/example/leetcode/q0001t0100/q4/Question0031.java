package org.example.leetcode.q0001t0100.q4;

/**
 * @Author xz
 * @Date 2021/10/22 15:09
 * @Description TODO
 * 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列（即，组合出下一个更大的整数）。
 * <p>
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * <p>
 * 必须 原地 修改，只允许使用额外常数空间。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * 示例 3：
 * <p>
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * 示例 4：
 * <p>
 * 输入：nums = [1]
 * 输出：[1]
 * 提示：
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 * Related Topics
 * 数组
 * 双指针
 */
public class Question0031 {
    // 思路,从后往前遍历,去找上第一个比当前值小的元素
    // 找到后,再次从后往前找,找到第一个比该值大的元素
    // [4,5,2,6,3,1]
    //
    // 将较大值以及之后的元素移到前面的位置(交换23位置后,将后面的数组反转)
    // *   *
    // 2 6 3 1
    //
    // 3 1 2 6
    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {4, 2, 1};
        nextPermutation(nums);
        for (int i :
                nums) {
            System.out.println(i);
        }
    }
}
