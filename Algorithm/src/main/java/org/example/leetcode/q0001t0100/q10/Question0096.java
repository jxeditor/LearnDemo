package org.example.leetcode.q0001t0100.q10;

/**
 * @Author xz
 * @Date 2021/11/30 10:18
 * @Description TODO
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：n = 3
 * 输出：5
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：1
 * 提示：
 * <p>
 * 1 <= n <= 19
 * Related Topics
 * 树
 * 二叉搜索树
 * 数学(卡塔兰数)
 * 动态规划
 * 二叉树
 */
public class Question0096 {
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }
}
