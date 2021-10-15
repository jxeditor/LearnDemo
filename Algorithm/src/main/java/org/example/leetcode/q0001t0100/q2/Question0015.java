package org.example.leetcode.q0001t0100.q2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author xz
 * @Date 2021/10/15 14:24
 * @Description TODO
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 *
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 示例 2：
 *
 * 输入：nums = []
 * 输出：[]
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：[]
 * 提示：
 *
 * 0 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */
public class Question0015 {
    public static ArrayList<ArrayList<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        // 锚点遍历一遍数组
        for (int i = 0; i < nums.length - 2; i++) {
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            // 指定两个指针进行滑动
            for (int j = i + 1, k = nums.length - 1; j < k; ) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    // 如果符合条件,结果保存,左指针指针向右移到与符合值不一致的位置
                    ArrayList<Integer> index = new ArrayList<>();
                    index.add(nums[i]);
                    index.add(nums[j]);
                    index.add(nums[k]);
                    result.add(index);
                    int temp = nums[j];
                    while(temp == nums[++j]){
                        j++;
                    }
                } else if (nums[i] + nums[j] + nums[k] < 0) {
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
        ArrayList<ArrayList<Integer>> arrayLists = threeSum(input);
        for (ArrayList<Integer> arr:
             arrayLists) {
            System.out.println(arr.toString());
        }
        System.out.println();
    }
}
