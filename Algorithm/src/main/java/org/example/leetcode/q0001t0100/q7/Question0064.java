package org.example.leetcode.q0001t0100.q7;

/**
 * @Author xz
 * @Date 2021/11/8 16:31
 * @Description TODO
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * <p>
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 * 示例 2：
 * <p>
 * 输入：grid = [[1,2,3],[4,5,6]]
 * 输出：12
 * 提示：
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 * Related Topics
 * 数组
 * 动态规划
 * 矩阵
 */
public class Question0064 {
    // 思路,和不同路径1类似,只是思路转换一下
    // 第一列和第一行的初始值不再是1,而是路径和
    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] f = new int[m][n];

        int temp = 0;
        // [[1,3,1]
        // ,[1,5,1]
        // ,[4,2,1]]
        // 将第一行第一列路径和计算出来,然后遍历计算最小路径和
        // [[1,4,5]
        // ,[2,0,0]
        // ,[6,0,0]]
        for (int i = 0; i < m; ++i) {
            f[i][0] = temp + grid[i][0];
            temp += grid[i][0];
        }
        temp = 0;
        for (int j = 0; j < n; ++j) {
            f[0][j] = temp + grid[0][j];
            temp += grid[0][j];
        }
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                f[i][j] = Math.min(f[i - 1][j] + grid[i][j], f[i][j - 1] + grid[i][j]);
            }
        }
        return f[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(minPathSum(grid));
    }
}
