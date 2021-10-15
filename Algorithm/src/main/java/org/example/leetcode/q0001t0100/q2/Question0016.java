package org.example.leetcode.q0001t0100.q2;

import scala.Int;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author xz
 * @Date 2021/10/15 15:15
 * @Description TODO
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。
 * 找出 nums 中的三个整数，使得它们的和与 target 最接近。
 * 返回这三个数的和。假定每组输入只存在唯一答案。
 * <p>
 * 示例：
 * <p>
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 * 提示：
 * <p>
 * 3 <= nums.length <= 10^3
 * -10^3 <= nums[i] <= 10^3
 * -10^4 <= target <= 10^4
 * Related Topics
 * 数组
 * 双指针
 * 排序
 */
public class Question0016 {
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int result = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1, k = nums.length - 1; j < k; ) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    return target;
                } else if (sum < target) {
                    if (Math.abs(target - sum) < Math.abs(target - result)) {
                        result = sum;
                    }
                    j++;
                } else {
                    if (Math.abs(target - sum) < Math.abs(target - result)) {
                        result = sum;
                    }
                    k--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = {-1, 0, 1, 4, -1, -10};
        System.out.println(threeSumClosest(input,-8));
    }
}
