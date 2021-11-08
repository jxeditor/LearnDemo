package org.example.leetcode.q0001t0100.q7;

/**
 * @Author xz
 * @Date 2021/11/8 11:26
 * @Description TODO
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * <p>
 * 问总共有多少条不同的路径？
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：m = 3, n = 7
 * 输出：28
 * 示例 2：
 * <p>
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 * 示例 3：
 * <p>
 * 输入：m = 7, n = 3
 * 输出：28
 * 示例 4：
 * <p>
 * 输入：m = 3, n = 3
 * 输出：6
 * 提示：
 * <p>
 * 1 <= m, n <= 100
 * 题目数据保证答案小于等于 2 * 109
 * Related Topics
 * 数学
 * 动态规划
 * 组合数学
 */
public class Question0062 {
    // 了解一个规律,一个2*2的格子中,从左上角到右下角,只有2种走法
    // 那么给第一行,第一列给出基准值
    public int uniquePaths(int m, int n) {
        int[][] f = new int[m][n];
        for (int i = 0; i < m; ++i) {
            f[i][0] = 1;
        }
        for (int j = 0; j < n; ++j) {
            f[0][j] = 1;
        }
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                f[i][j] = f[i - 1][j] + f[i][j - 1];
            }
        }
        return f[m - 1][n - 1];
    }

    public int uniquePaths2(int m, int n) {
        long ans = 1;
        for (int x = n, y = 1; y < m; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }
}
