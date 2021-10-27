package org.example.leetcode.q0001t0100.q4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/10/25 17:48
 * @Description TODO
 * 编写一个程序，通过填充空格来解决数独问题。
 * <p>
 * 数独的解法需 遵循如下规则：
 * <p>
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * <p>
 * 示例：
 * <p>
 * <p>
 * 输入：board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
 * 输出：[["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
 * 解释：输入的数独如上图所示，唯一有效的解决方案如下所示：
 * <p>
 * 提示：
 * <p>
 * board.length == 9
 * board[i].length == 9
 * board[i][j] 是一位数字或者 '.'
 * 题目数据 保证 输入数独仅有一个解
 * Related Topics
 * 数组
 * 回溯
 * 矩阵
 */
public class Question0037 {
    private boolean[][] line = new boolean[9][9];
    private boolean[][] column = new boolean[9][9];
    private boolean[][][] block = new boolean[3][3][9];
    private boolean valid = false;
    private List<int[]> spaces = new ArrayList<int[]>();

    // 思路,将数独转换成3个多维数组(行,列,块),已有的数字则对应的哈希下标位置替换为true
    // 空白的部分用下标记录对应坐标(1,1),(3,3)......
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    spaces.add(new int[]{i, j});
                } else {
                    int digit = board[i][j] - '0' - 1;
                    line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = true;
                }
            }
        }
        dfs(board, 0);
    }

    public void dfs(char[][] board, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        // 遍历空白位置
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        for (int digit = 0; digit < 9 && !valid; ++digit) {
            // 遍历1-9的哈希下标值,判断是否符合规则
            if (!line[i][digit] && !column[j][digit] && !block[i / 3][j / 3][digit]) {
                // 符合则将该位置置为true,后面递归时不可使用
                line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = true;
                // 将1-9的数字填入原始数独
                board[i][j] = (char) (digit + '0' + 1);
                // 递归下一个空白符
                dfs(board, pos + 1);
                // 进行for循环下一个可填写值时,需要将该位置的状态置为false
                line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = false;
            }
        }
    }
}
