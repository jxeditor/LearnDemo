package org.example.leetcode.q0001t0100.q6;

/**
 * @Author xz
 * @Date 2021/11/8 09:10
 * @Description TODO
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。
 * <p>
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "Hello World"
 * 输出：5
 * 示例 2：
 * <p>
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 * 示例 3：
 * <p>
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 * 提示：
 * <p>
 * 1 <= s.length <= 104
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 * Related Topics
 * 字符串
 */
public class Question0058 {
    public int lengthOfLastWord(String s) {
        int index = s.length() - 1;
        // 把最后的空格剔除掉
        while (s.charAt(index) == ' ') {
            index--;
        }
        // 开始遍历,遍历到再次为空格的位置
        int wordLength = 0;
        while (index >= 0 && s.charAt(index) != ' ') {
            wordLength++;
            index--;
        }
        return wordLength;
    }
}
