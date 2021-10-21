package org.example.leetcode.q0001t0100.q3;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/10/21 08:56
 * @Description TODO
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * <p>
 * 有效括号组合需满足：左括号必须以正确的顺序闭合。
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：["()"]
 * 提示：
 * <p>
 * 1 <= n <= 8
 * Related Topics
 * 字符串
 * 动态规划
 * 回溯
 */
public class Question0022 {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        // 生成的字符串长度符合要求
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        // 左括号必定在最左侧,不存在乱序
        if (open < max) { // 左括号不大于max,表示可以放入一个左括号
            cur.append('(');
            backtrack(ans, cur, open + 1, close, max);
            // 执行完成后进行cur的清除
            cur.deleteCharAt(cur.length() - 1);
        }
        if (close < open) { // 右括号小于左括号,表示可以放入一个右括号
            cur.append(')');
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}
