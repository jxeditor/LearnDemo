package org.example.leetcode.q0001t0100.q6;

import java.util.*;

/**
 * @Author xz
 * @Date 2021/11/4 15:48
 * @Description TODO
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * <p>
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * <p>
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：[["Q"]]
 * 提示：
 * <p>
 * 1 <= n <= 9
 * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 * Related Topics
 * 数组
 * 回溯
 */
public class Question0051 {
    // 思路,有点像数独那题的解法,n*n的棋盘,皇后不能出现在同一个横线纵线斜线上
    // 判断条件: 放置一个皇后前判断其是否符合上述条件,并在放置之后修改条件状态
    // Set[n] rows每条横线是否有皇后 每个横线上只能放一个皇后,所以以横线为程序退出条件
    // Set[n] cols每条纵线是否有皇后
    // 对于斜线,一个坐标对应着2条斜线,2条斜线都需要没有皇后
    // Set<int> skews对应不同的斜线是否有皇后,对于一个n*n矩阵,斜线有2*(2n-1)条斜线
    // up_set 行-列,down_set 行+列 记录上斜与下斜两种方式
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<List<String>>();
        int[] queens = new int[n];
        Arrays.fill(queens, -1);

        Set<Integer> cols = new HashSet<Integer>();
        Set<Integer> up_set = new HashSet<Integer>();
        Set<Integer> down_set = new HashSet<Integer>();

        backtrack(solutions, queens, n, 0, cols, up_set, down_set);
        return solutions;
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
