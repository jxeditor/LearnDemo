package org.example.leetcode.q0001t0100.q5;

import java.util.*;

/**
 * @Author xz
 * @Date 2021/11/4 15:36
 * @Description TODO
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母都恰好只用一次。
 * <p>
 * 示例 1:
 * <p>
 * 输入: strs =
 * ["eat", "tea", "tan", "ate", "nat", "bat"]
 * <p>
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 * <p>
 * 输入: strs =
 * [""]
 * <p>
 * 输出: [[""]]
 * 示例 3:
 * <p>
 * 输入: strs =
 * ["a"]
 * <p>
 * 输出: [["a"]]
 * 提示：
 * <p>
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 * Related Topics
 * 哈希表
 * 字符串
 * 排序
 */
public class Question0049 {
    // 了解题意就可以做出来,将用到的相同字母的字符串归为一组
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
