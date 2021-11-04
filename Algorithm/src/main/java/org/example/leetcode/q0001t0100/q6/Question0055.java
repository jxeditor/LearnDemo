package org.example.leetcode.q0001t0100.q6;

/**
 * @Author xz
 * @Date 2021/11/4 13:59
 * @Description TODO
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 判断你是否能够到达最后一个下标。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,1,0,4]
 * 输出：false
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
 * 提示：
 * <p>
 * 1 <= nums.length <= 3 * 104
 * 0 <= nums[i] <= 105
 * Related Topics
 * 贪心
 * 数组
 * 动态规划
 */
public class Question0055 {
    public static boolean canJump(int[] nums) {
        int end = 0;
        int max = 0;
        // 一步步跳,跳到最大路径位置,如果最终没有到最后一个下标,说明失败
        for (int i = 0; i < nums.length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                end = max;
            }
        }
        System.out.println(end);
        return end >= nums.length - 1;
    }

    public static void main(String[] args) {
        int[] inputs = {2, 2, 1, 0, 4};
        System.out.println(canJump(inputs));
    }
}
