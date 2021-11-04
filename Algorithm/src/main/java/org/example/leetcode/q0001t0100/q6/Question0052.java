package org.example.leetcode.q0001t0100.q6;

import java.util.*;

/**
 * @Author xz
 * @Date 2021/11/4 16:36
 * @Description TODO
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * <p>
 * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：n = 4
 * 输出：2
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：1
 * 提示：
 * <p>
 * 1 <= n <= 9
 * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 * Related Topics
 * 回溯
 */
public class Question0052 {
    // 这题没啥意义....得出51题结果的数量即可
    public int totalNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        int[] queens = new int[n];
        Arrays.fill(queens, -1);

        Set<Integer> cols = new HashSet<Integer>();
        Set<Integer> up_set = new HashSet<Integer>();
        Set<Integer> down_set = new HashSet<Integer>();

        backtrack(solutions, queens, n, 0, cols, up_set, down_set);
        return solutions.size();
    }

    public void backtrack(List<List<String>> solutions, int[] queens, int n, int row, Set<Integer> cols, Set<Integer> up_set, Set<Integer> down_set) {
        if (row == n) {
            List<String> board = generateBoard(queens, n);
            solutions.add(board);
        } else {
            // 遍历列
            for (int i = 0; i < n; i++) {
                // 列已经放置过皇后,则跳过该列并判断斜线
                int up = row - i;
                int down = row + i;
                if (cols.contains(i) || up_set.contains(up) || down_set.contains(down)) {
                    continue;
                }

                // 放置皇后
                queens[row] = i;

                // 更改状态值
                cols.add(i);
                up_set.add(up);
                down_set.add(down);

                // 递归下一行
                backtrack(solutions, queens, n, row + 1, cols, up_set, down_set);

                // 递归之后需要将状态值复原
                queens[row] = -1;
                cols.remove(i);
                up_set.remove(up);
                down_set.remove(down);
            }
        }
    }

    public List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
