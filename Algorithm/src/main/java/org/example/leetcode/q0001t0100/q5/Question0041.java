package org.example.leetcode.q0001t0100.q5;

/**
 * @Author xz
 * @Date 2021/10/28 09:41
 * @Description TODO
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * <p>
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,0]
 * 输出：3
 * 示例 2：
 * <p>
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 * 示例 3：
 * <p>
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 * 提示：
 * <p>
 * 1 <= nums.length <= 5 * 105
 * -231 <= nums[i] <= 231 - 1
 * Related Topics
 * 数组
 * 哈希表
 */
public class Question0041 {
    // 思路,将nums转化成哈希表,代表着假如长度为4,那么哈希对应着1234这四个值,负数其实不参与计算,将为负数的元素转化为(长度+1)
    // 寻找最小正整数,其实就是看哈希对应位置的值是否正确,将元素中<=长度的数值转换为负数,找第一个不是负数的下标位置对应的哈希值(下标+1)
    public int firstMissingPositive(int[] nums) {
        // [1,2,3,4]的情况下,返回其实就是result+1
        int result = nums.length;
        // 修改负数值
        for (int i = 0; i < result; i++) {
            if (nums[i] <= 0) {
                nums[i] = result + 1;
            }

        }

        // 将对应<=长度的元素对应的哈希值所在位置转化成负数
        for (int i = 0; i < result; i++) {
            if (nums[i] > 0 && nums[i] <= result) {
                nums[nums[i] - 1] = -nums[nums[i] - 1];
            }
        }

        // 寻找第一个正整数的下标
        for (int i = 0; i < result; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }

        return result + 1;
    }
}
