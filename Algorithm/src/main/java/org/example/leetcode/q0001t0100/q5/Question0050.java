package org.example.leetcode.q0001t0100.q5;

/**
 * @Author xz
 * @Date 2021/11/4 15:41
 * @Description TODO
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。
 * <p>
 * 示例 1：
 * <p>
 * 输入：x = 2.00000, n = 10
 * 输出：1024.00000
 * 示例 2：
 * <p>
 * 输入：x = 2.10000, n = 3
 * 输出：9.26100
 * 示例 3：
 * <p>
 * 输入：x = 2.00000, n = -2
 * 输出：0.25000
 * 解释：2-2 = 1/22 = 1/4 = 0.25
 * 提示：
 * <p>
 * -100.0 < x < 100.0
 * -231 <= n <= 231-1
 * -104 <= xn <= 104
 * Related Topics
 * 递归
 * 数学
 */
public class Question0050 {
    // 这是最简单的递归了...
    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        return x * myPow(x, n - 1);
    }

    public static void main(String[] args) {
        double v = myPow(2.0, 10);
        System.out.println(v);
    }
}
