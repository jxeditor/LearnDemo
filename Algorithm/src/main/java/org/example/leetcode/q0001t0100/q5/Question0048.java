package org.example.leetcode.q0001t0100.q5;

import java.util.Arrays;

/**
 * @Author xz
 * @Date 2021/11/4 14:58
 * @Description TODO
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * <p>
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[[7,4,1],[8,5,2],[9,6,3]]
 * 示例 2：
 * <p>
 * <p>
 * 输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * 输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 * 示例 3：
 * <p>
 * 输入：matrix = [[1]]
 * 输出：[[1]]
 * 示例 4：
 * <p>
 * 输入：matrix = [[1,2],[3,4]]
 * 输出：[[3,1],[4,2]]
 * 提示：
 * <p>
 * matrix.length == n
 * matrix[i].length == n
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 * Related Topics
 * 数组
 * 数学
 * 矩阵
 */
public class Question0048 {
    // 思路,emm,对角以1,5,9互换下,然后进行左右互换
    // [i][j]与[j][i]互换
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        /**
         * [1, 2, 3]
         * [4, 5, 6]
         * [7, 8, 9]
         *
         * [1, 4, 7]
         * [2, 5, 8]
         * [3, 6, 9]
         *
         * [7, 4, 1]
         * [8, 5, 2]
         * [9, 6, 3]
         */
        System.out.println(Arrays.deepToString(matrix));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(inputs);
        System.out.println(Arrays.deepToString(inputs));
    }
}
