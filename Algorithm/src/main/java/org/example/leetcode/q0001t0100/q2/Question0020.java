package org.example.leetcode.q0001t0100.q2;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author xz
 * @Date 2021/10/18 17:09
 * @Description TODO
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 示例 1：
 * <p>
 * 输入：s = "()"
 * 输出：true
 * 示例 2：
 * <p>
 * 输入：s = "()[]{}"
 * 输出：true
 * 示例 3：
 * <p>
 * 输入：s = "(]"
 * 输出：false
 * 示例 4：
 * <p>
 * 输入：s = "([)]"
 * 输出：false
 * 示例 5：
 * <p>
 * 输入：s = "{[]}"
 * 输出：true
 * 提示：
 * <p>
 * 1 <= s.length <= 104
 * s 仅由括号 '()[]{}' 组成
 * Related Topics
 * 栈
 * 字符串
 */
public class Question0020 {
    public static boolean isValid(String s) {
        HashMap<String, String> str_mapping = new HashMap<>();
        str_mapping.put("(", ")");
        str_mapping.put("{", "}");
        str_mapping.put("[", "]");
        char[] chars = s.toCharArray();
        Deque<String> deque = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            String cur = String.valueOf(chars[i]);
            // 如果栈内有对应值,则弹出,否则入栈
            if (str_mapping.getOrDefault(deque.peek(), "*").equals(cur)) {
                deque.poll();
            } else {
                deque.push(String.valueOf(chars[i]));
            }
        }

        return deque.peek() == null;
    }

    public static void main(String[] args) {
        System.out.println(isValid("[]{}()"));
    }
}
