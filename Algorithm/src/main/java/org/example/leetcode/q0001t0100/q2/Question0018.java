package org.example.leetcode.q0001t0100.q2;

import java.util.*;

/**
 * @Author xz
 * @Date 2021/10/15 16:25
 * @Description TODO
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] ：
 * <p>
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 * 提示：
 * <p>
 * 1 <= nums.length <= 200
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 */
public class Question0018 {
    // 借鉴3数之和,目标值减去遍历值,为3数目标值
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            while (temp == nums[++i]) {
                i++;
            }
            LinkedList<LinkedList<Integer>> arrayLists = threeSum(nums, target - nums[i], i);
            for (LinkedList<Integer> arr : arrayLists) {
                arr.add(nums[i]);
                result.add(arr);
            }
        }
        return result;
    }

    public static LinkedList<LinkedList<Integer>> threeSum(int[] nums, int target, int pos) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();

        // 锚点遍历一遍数组
        for (int i = pos + 1; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 指定两个指针进行滑动
            for (int j = i + 1, k = nums.length - 1; j < k; ) {
                if (nums[i] + nums[j] + nums[k] == target) {
                    // 如果符合条件,结果保存,左指针指针向右移到与符合值不一致的位置
                    LinkedList<Integer> index = new LinkedList<>();
                    index.add(nums[i]);
                    index.add(nums[j]);
                    index.add(nums[k]);
                    result.add(index);
                    int temp = nums[j];
                    while (temp == nums[++j]) {
                        j++;
                    }
                } else if (nums[i] + nums[j] + nums[k] < target) {
                    // 如果合值小于0,说明左右指针值相加太小,左指针向右移
                    j++;
                } else {
                    // 如果合值大于0,说明左右指针值相加太大,右指针向左移
                    k--;
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = {-1, 0, 1, 2, -1, -4};
        Arrays.sort(input);
        List<List<Integer>> lists = fourSum(input, 2);
        for (List<Integer> arr :
                lists) {
            System.out.println(arr.toString());
        }
    }


    public List<List<Integer>> fourSum_(int[] nums, int target) {
        List<List<Integer>> quadruplets = new ArrayList<List<Integer>>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if (nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if (nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int left = j + 1, right = length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        quadruplets.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return quadruplets;
    }
}
