package org.example.leetcode.q0001t0100.q6;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/11/5 15:09
 * @Description TODO
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 * 示例 2：
 * <p>
 * <p>
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 * 提示：
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 * Related Topics
 * 数组
 * 矩阵
 * 模拟
 */
public class Question0054 {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }
        int rows = matrix.length, columns = matrix[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int total = rows * columns;
        int row = 0, column = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;
        for (int i = 0; i < total; i++) {
            order.add(matrix[row][column]);
            visited[row][column] = true;
            int nextRow = row + directions[directionIndex][0], nextColumn = column + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
                directionIndex = (directionIndex + 1) % 4;
            }
            row += directions[directionIndex][0];
            column += directions[directionIndex][1];
        }
        return order;
    }


    public List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }
        int rows = matrix.length, columns = matrix[0].length;
        // 设置 左,右,上,下四个边界
        int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
        // 条件为左小于右,上小于下
        while (left <= right && top <= bottom) {
            // 由于顺时针循环的原因,轮询都是上->右->下->左
            for (int column = left; column <= right; column++) {
                // 上: 上节点不变,下标从左->右
                order.add(matrix[top][column]);
            }
            for (int row = top + 1; row <= bottom; row++) {
                // 右: 右节点不变,下标从top+1移到bottom
                order.add(matrix[row][right]);
            }
            // 如果是相等,那么意味最后一个点是留给上这一步进行添加的
            if (left < right && top < bottom) {
                for (int column = right - 1; column > left; column--) {
                    // 下: 下节点不变,从right-1移到left
                    order.add(matrix[bottom][column]);
                }
                for (int row = bottom; row > top; row--) {
                    // 左: 左节点不动,从bottom移动到top
                    order.add(matrix[row][left]);
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return order;
    }
}
