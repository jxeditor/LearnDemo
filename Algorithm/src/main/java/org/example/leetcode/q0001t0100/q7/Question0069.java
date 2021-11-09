package org.example.leetcode.q0001t0100.q7;

/**
 * @Author xz
 * @Date 2021/11/9 10:44
 * @Description TODO
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * <p>
 * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
 * <p>
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：x = 4
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：x = 8
 * 输出：2
 * 解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
 * 提示：
 * <p>
 * 0 <= x <= 231 - 1
 * Related Topics
 * 数学
 * 二分查找
 */
public class Question0069 {
    public int mySqrt(int x) {
        int left = 0;
        int right = x;
        int ans = -1;
        // 遍历一遍从0到x,找出最近值就完成了
        while (left <= right) {
            int mid = (left + right) / 2;
            if (mid * mid <= x) {
                ans = mid;
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }
}
