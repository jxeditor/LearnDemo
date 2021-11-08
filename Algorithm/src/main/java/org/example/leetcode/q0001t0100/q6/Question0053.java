package org.example.leetcode.q0001t0100.q6;

/**
 * @Author xz
 * @Date 2021/11/4 16:39
 * @Description TODO
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 示例 2：
 * <p>
 * 输入：nums = [1]
 * 输出：1
 * 示例 3：
 * <p>
 * 输入：nums = [0]
 * 输出：0
 * 示例 4：
 * <p>
 * 输入：nums = [-1]
 * 输出：-1
 * 示例 5：
 * <p>
 * 输入：nums = [-100000]
 * 输出：-100000
 * 提示：
 * <p>
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 * <p>
 * Related Topics
 * 数组
 * 分治
 * 动态规划
 */
public class Question0053 {
    public int maxSubArray(int[] nums) {
        int sum = 0;
        int max = nums[0];
        // 和值,极值
        //
        // 当前值加上原先和值反而比当前值小,那么计算位置当从该位置进行重新计算
        // 极值用于对比历史已经出现过的最大值
        for (int i = 0; i < nums.length; i++) {
            sum = Math.max(nums[i], sum + nums[i]);
            max = Math.max(sum, max);
        }
        return max;
    }
}
