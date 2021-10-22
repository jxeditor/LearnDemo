package org.example.leetcode.q0001t0100.q4;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author xz
 * @Date 2021/10/22 15:53
 * @Description TODO
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * 示例 2：
 * <p>
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * 示例 3：
 * <p>
 * 输入：s = ""
 * 输出：0
 * 提示：
 * <p>
 * 0 <= s.length <= 3 * 104
 * s[i] 为 '(' 或 ')'
 * Related Topics
 * 栈
 * 字符串
 * 动态规划
 */
public class Question0032 {
    // 思路: 将输入依次入栈,如果栈顶和下个输入可以组成标准括号,注意原始栈中本身需要有-1,好计算长度
    public static int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    // 动态规划
    // 有效括号一定为)结尾,只会有两种可能,...()和...))
    // 有效字符长度只会是连续的只考虑修改)对应下的下标值,作为有效字符长度,(对应的值都是0
    // 当i = ),i - 1 = (时,查看前面是否有有效长度dp[i - 2],再加上2即可
    // 当i = ),i - 1 != (时,说明是...))的格式,需要去查看上一个)是否含有有效长度,i - 1 - dp[i - 1]
    public static int longestValidParentheses_01(String s) {
        int maxans = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    // 左右遍历
    // 从左往右一次,从右往左一次,比较左右括号个数,取最大值就是结果值
    public int longestValidParentheses_02(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxlength;
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses("())((())"));
    }
}
