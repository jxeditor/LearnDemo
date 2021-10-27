package org.example.leetcode.q0001t0100.q4;

/**
 * @Author xz
 * @Date 2021/10/25 09:57
 * @Description TODO
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 * <p>
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * <p>
 * 进阶：
 * <p>
 * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
 * 示例 1：
 * <p>
 * 输入：nums = [
 * 5,7,7,8,8,10]
 * , target = 8
 * 输出：[3,4]
 * 示例 2：
 * <p>
 * 输入：nums = [
 * 5,7,7,8,8,10]
 * , target = 6
 * 输出：[-1,-1]
 * 示例 3：
 * <p>
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 * 提示：
 * <p>
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * nums 是一个非递减数组
 * -109 <= target <= 109
 * Related Topics
 * 数组
 * 二分查找
 */
public class Question0034 {
    public static int[] searchRange(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    public static int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 开始位置,当中位数为目标值,记录目标值位置,并且收缩右指针,中位数左移,获得目标值最左侧下标
            // 结束位置,不找目标值,中位数大于目标值,记录位置,收缩右指针,中位数左移,找到比目标值大的下标位置
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                // 收缩左指针,中位数右移
                left = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] input = new int[]{5, 7, 7, 8, 8, 10};
        searchRange(input, 7);
    }
}
