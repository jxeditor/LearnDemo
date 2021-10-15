package org.example.leetcode.q0001t0100.q1;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xs
 * @Date: 2019-12-19 11:06
 * @Description: 给定一个整数数组 nums 和一个目标值 target，
 * 请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class Question0001 {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 18;
        solution01(nums, target);
    }

    private static void solution01(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length - i; j++) {
                int temp = nums[i] + nums[j];
                if (target == temp) {
                    System.out.println(i + "-" + j);
                }
            }
        }
    }

    private static int[] solution02(int[] nums, int target) {
        // 思路: 目标值减去数组值,如果与另一个数组相等,就符合两数之和
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
}
