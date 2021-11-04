package org.example.leetcode.q0001t0100.q5;

/**
 * @Author xz
 * @Date 2021/11/4 11:25
 * @Description TODO
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * <p>
 * 假设你总是可以到达数组的最后一个位置。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是
 * 2
 * 。
 * 从下标为 0 跳到下标为 1 的位置，跳
 * 1
 * 步，然后跳
 * 3
 * 步到达数组的最后一个位置。
 * 示例 2:
 * <p>
 * 输入: nums = [2,3,0,1,4]
 * 输出: 2
 * 提示:
 * <p>
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 1000
 * Related Topics
 * 贪心
 * 数组
 * 动态规划
 */
public class Question0045 {
    public int jump(int[] nums) {
        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                // 反向查找可以到达最后位置的步骤
                // 当前下标+当前下标值如果比剩余长度相等或者大,则说明可以直接从该步结束
                // 再将长度设置为当前下标,从头开始找到该位置的最佳位置
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }

    public static int jump2(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            // 就一个一个跳,跳完之后更新从起始位置到该位置,能够跳跃的最大步骤
            // 当跳跃到该位置之后,下一个再次计算需要跳跃的步骤
            // 相当于先跳了再说,每次都往最大的跳
            maxPosition = Math.max(maxPosition, i + nums[i]);
            System.out.println(i + ":" + end);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        int[] inputs = {2, 3, 0, 1, 4};
        System.out.println(jump2(inputs));
    }
}
