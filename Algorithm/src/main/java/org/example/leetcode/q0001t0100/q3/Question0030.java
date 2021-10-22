package org.example.leetcode.q0001t0100.q3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/10/22 14:28
 * @Description TODO
 * 给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * <p>
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：
 * [0,9]
 * <p>
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 * <p>
 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * <p>
 * 示例 3：
 * <p>
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 * 提示：
 * <p>
 * 1 <= s.length <= 104
 * s 由小写英文字母组成
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] 由小写英文字母组成
 * Related Topics
 * 哈希表
 * 字符串
 * 滑动窗口
 */
public class Question0030 {
    // 思路: 先通过substr方法获取需要匹配的值,然后使用words去指定位置匹配
    // 假如单个word长度为4,单词个数3,那么进行等差遍历即可,0,4,8
    // 判断两个哈希表是否一致,一致则是正常
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int single_len = words[0].length();
        int len = words[0].length() * words.length;
        HashMap<String, Integer> words_map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            words_map.put(words[i], words_map.getOrDefault(words[i], 0) + 1);
        }
        for (int i = 0; i < s.length() - len; i++) {
            HashMap<String, Integer> tmp_map = new HashMap<>();

            String substring = s.substring(i, i + len);
            for (int j = 0; j < substring.length(); j += single_len) {
                String sub = substring.substring(j, j + single_len);
                tmp_map.put(sub, tmp_map.getOrDefault(sub, 0) + 1);
            }
            if (words_map.equals(tmp_map)) {
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar", "foo", "the"};
        List<Integer> result = findSubstring(s, words);
        for (Integer i :
                result) {
            System.out.println(i);
        }
    }
}
