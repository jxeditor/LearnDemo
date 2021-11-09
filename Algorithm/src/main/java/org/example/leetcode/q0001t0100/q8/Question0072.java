package org.example.leetcode.q0001t0100.q8;

/**
 * @Author xz
 * @Date 2021/11/9 14:33
 * @Description TODO
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * 示例 1：
 * <p>
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * 示例 2：
 * <p>
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 * 提示：
 * <p>
 * 0 <= word1.length, word2.length <= 500
 * word1 和 word2 由小写英文字母组成
 * Related Topics
 * 字符串
 * 动态规划
 */
public class Question0072 {
    // 动态规划题的特点,画个矩阵

    /**
     * horse 和 ros 赋初始值
     * e 5 4 4 3
     * s 4 3 3 2
     * r 3 2 2 2
     * o 2 2 1 2
     * h 1 1 2 3
     * # 0 1 2 3
     * * # r o s
     * ----------
     * 计算hr转换步骤次数 因为hr两字符不相同 1 + Math.min(hr[左],hr[下],hr[左下])
     * 遇到两者字符相同,说明步骤可以减一     1 + Math.min(rr[左],rr[下],rr[左下]-1)
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }
        // DP 数组
        int[][] D = new int[n + 1][m + 1];
        // 边界状态初始化
        for (int i = 0; i < n + 1; i++) {
            D[i][0] = i;
        }
        for (int j = 0; j < m + 1; j++) {
            D[0][j] = j;
        }
        // 计算所有 DP 值
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int left = D[i - 1][j] + 1;
                int down = D[i][j - 1] + 1;
                int left_down = D[i - 1][j - 1];
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    left_down += 1;
                }
                D[i][j] = Math.min(left, Math.min(down, left_down));
            }
        }
        return D[n][m];
    }
}
