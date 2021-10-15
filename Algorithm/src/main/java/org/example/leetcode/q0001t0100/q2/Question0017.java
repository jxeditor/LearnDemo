package org.example.leetcode.q0001t0100.q2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/10/15 16:00
 * @Description TODO
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * <p>
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 2->abc
 * 3->edf
 * ....
 * <p>
 * 示例 1：
 * <p>
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 */
public class Question0017 {

    static String[] letter_map = {" ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    static List<String> res = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {
        //注意边界条件
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        iterStr(digits, new StringBuilder(), 0);
        return res;
    }

    private static void iterStr(String src, StringBuilder des, int index) {
        if (index == src.length()) {
            // 达到长度时才会返回结果
            res.add(des.toString());
            return;
        }
        // 获取下标对应字母集,用char类型数字-char类型0得到下标对应字母
        char c = src.charAt(index);
        int pos = c - '0';
        String string_arr = letter_map[pos];
        // 遍历字母
        for (int i = 0; i < string_arr.length(); i++) {
            des.append(string_arr.charAt(i));
            iterStr(src, des, index + 1);
            des.deleteCharAt(des.length() - 1);
        }
    }

    public static void main(String[] args) {
        List<String> strings = letterCombinations("23");
        System.out.println(strings.toString());
    }
}
