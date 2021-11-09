package org.example.leetcode.q0001t0100.q7;

import java.util.Arrays;

/**
 * @Author xz
 * @Date 2021/11/9 09:34
 * @Description TODO
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * <p>
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * <p>
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * <p>
 * 示例 1：
 * <p>
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例 2：
 * <p>
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 * <p>
 * 输入：digits = [0]
 * 输出：[1]
 * 提示：
 * <p>
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 * Related Topics
 * 数组
 * 数学
 */
public class Question0066 {
    // 两种情况,一种后缀为9,需要进位,一种后缀不为9,直接加1返回
    public int[] plusOne1(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; --i) {
            // 反向思考,如果末位等于9,前一位加1,后面都归为0即可
            if (digits[i] != 9) {
                ++digits[i];
                for (int j = i + 1; j < n; ++j) {
                    digits[j] = 0;
                }
                return digits;
            }
        }
        // digits 中所有的元素均为 9
        int[] ans = new int[n + 1];
        ans[0] = 1;
        return ans;
    }

    public static int[] plusOne(int[] digits) {
        int n = digits.length;
        int add = 0;
        int temp = digits[n - 1] + 1;
        add = temp / 10;
        if (add > 0) {
            for (int i = n - 1; i >= 0; i--) {
                if (i != n - 1) {
                    temp = digits[i] + add;
                }
                add = temp / 10;
                if (add == 0) {
                    break;
                } else {
                    digits[i] = temp % 10;
                }
            }
        } else {
            digits[n - 1] = temp;
        }

        return digits;
    }

    public static void main(String[] args) {
        int[] inputs = {9, 9, 9};
        System.out.println(Arrays.toString(plusOne(inputs)));
    }
}
