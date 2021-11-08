package org.example.leetcode.q0001t0100.q6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/11/8 09:16
 * @Description TODO
 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，
 * 且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 3
 * 输出：[[1,2,3],[8,9,4],[7,6,5]]
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：[[1]]
 * 提示：
 * <p>
 * 1 <= n <= 20
 * Related Topics
 * 数组
 * 矩阵
 * 模拟
 */
public class Question0059 {
    // 思路和之前螺旋矩阵1类似,只不过之前是将原有值放入另一个数组中
    // 现在是对原有值进行赋值
    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        List<Integer> order = new ArrayList<Integer>();

        int rows = matrix.length, columns = matrix[0].length;
        // 设置 左,右,上,下四个边界
        int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
        // 条件为左小于右,上小于下
        int start = 1;
        while (left <= right && top <= bottom) {
            // 由于顺时针循环的原因,轮询都是上->右->下->左
            for (int column = left; column <= right; column++) {
                // 上: 上节点不变,下标从左->右
                matrix[top][column] = start;
                start++;
            }
            for (int row = top + 1; row <= bottom; row++) {
                // 右: 右节点不变,下标从top+1移到bottom
                matrix[row][right] = start;
                start++;
            }
            // 如果是相等,那么意味最后一个点是留给"上"这一步进行添加的
            if (left < right && top < bottom) {
                for (int column = right - 1; column > left; column--) {
                    // 下: 下节点不变,从right-1移到left
                    matrix[bottom][column] = start;
                    start++;
                }
                for (int row = bottom; row > top; row--) {
                    // 左: 左节点不动,从bottom移动到top
                    matrix[row][left] = start;
                    start++;
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return matrix;
    }

    public static void main(String[] args) {
        int[][] ints = generateMatrix(3);
        System.out.println(Arrays.deepToString(ints));
    }
}
