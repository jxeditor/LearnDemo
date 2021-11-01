package org.example.leetcode.q0401t0500.q2;

/**
 * @Author xz
 * @Date 2021/10/29 16:34
 * @Description TODO
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
 * <p>
 * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
 * <p>
 * 示例 1：
 * <p>
 * 输入：num1 = "11", num2 = "123"
 * 输出："134"
 * 示例 2：
 * <p>
 * 输入：num1 = "456", num2 = "77"
 * 输出："533"
 * 示例 3：
 * <p>
 * 输入：num1 = "0", num2 = "0"
 * 输出："0"
 * 提示：
 * <p>
 * 1 <= num1.length, num2.length <= 104
 * num1 和num2 都只包含数字 0-9
 * num1 和num2 都不包含任何前导零
 * Related Topics
 * 数学
 * 字符串
 * 模拟
 */
public class Question0415 {
    // 乘法类似,拿乘数的其中一位对另一乘数的每一位进行相乘,也是三个值进行计算,x*y+add
    public static String addStrings(String num1, String num2) {
        // 相加,并且需要一个值存储溢出值
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int add = 0;

        StringBuffer sum = new StringBuffer();

        // 只要三者都符合条件,证明未计算到最大位
        while (i >= 0 || j >= 0 || add != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;

            // 做加法
            int result = x + y + add;

            // 余数是需要加入的值
            sum.append(result % 10);
            // 除数是溢出值,参与下次计算
            add = result / 10;
            i--;
            j--;
        }
        return sum.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addStrings("123", "4567"));
    }
}
