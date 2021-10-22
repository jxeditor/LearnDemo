package org.example.leetcode.q0001t0100.q3;

/**
 * @Author xz
 * @Date 2021/10/21 17:38
 * @Description TODO
 * 实现 strStr() 函数。
 * <p>
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回 -1 。
 * <p>
 * 说明：
 * <p>
 * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 * <p>
 * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java 的 indexOf() 定义相符。
 * <p>
 * 示例 1：
 * <p>
 * 输入：haystack = "hello", needle = "ll"
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：haystack = "aaaaa", needle = "bba"
 * 输出：-1
 * 示例 3：
 * <p>
 * 输入：haystack = "", needle = ""
 * 输出：0
 * 提示：
 * <p>
 * 0 <= haystack.length, needle.length <= 5 * 104
 * haystack 和 needle 仅由小写英文字符组成
 * Related Topics
 * 双指针
 * 字符串
 * 字符串匹配
 */
public class Question0028 {
    public static int strStr(String haystack, String needle) {
        char[] a = haystack.toCharArray();
        char[] b = needle.toCharArray();

        int j = 0;
        while (j < a.length - b.length) {
            boolean flag = true;
            for (int i = 0, k = j; i < b.length; i++, k++) {
                if (a[k] != b[i]) flag = false;
            }
            if (flag) return j;
            j++;
        }
        return j;
    }


    public static int kmp(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) {
            return 0;
        }
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String haystack = "hello";
        String needle = "ll";

        System.out.println(strStr(haystack, needle));
    }
}
