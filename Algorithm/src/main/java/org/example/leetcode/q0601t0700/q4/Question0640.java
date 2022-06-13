package org.example.leetcode.q0601t0700.q4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2022/6/7 08:54
 * @Description TODO
 * 求解一个给定的方程，将x以字符串 "x=#value" 的形式返回。该方程仅包含 '+' ， '-' 操作，变量 x 和其对应系数。
 * <p>
 * 如果方程没有解，请返回 "No solution" 。如果方程有无限解，则返回 “Infinite solutions” 。
 * <p>
 * 如果方程中只有一个解，要保证返回值 'x' 是一个整数。
 * <p>
 * 示例 1：
 * <p>
 * 输入: equation = "x+5-3+x=6+x-2"
 * 输出: "x=2"
 * 示例 2:
 * <p>
 * 输入: equation = "x=x"
 * 输出: "Infinite solutions"
 * 示例 3:
 * <p>
 * 输入: equation = "2x=x"
 * 输出: "x=0"
 * 提示:
 * <p>
 * 3 <= equation.length <= 1000
 * equation 只有一个 '='.
 * equation 方程由整数组成，其绝对值在 [0, 100] 范围内，不含前导零和变量 'x' 。
 * ​​​
 * Related Topics
 * 数学
 * 字符串
 * 模拟
 */
public class Question0640 {
    public static String coeff(String x) {
        // 将左侧x的表达式进行数值处理比如3x转化为3
        if (x.length() > 1 && x.charAt(x.length() - 2) >= '0' && x.charAt(x.length() - 2) <= '9')
            return x.replace("x", "");
        return x.replace("x", "1");
    }

    public static String solveEquation(String equation) {
        // 思路:左侧lhs记录x的个数,右侧记录数值,最终使用rhs/lhs计算x的值
        String[] lr = equation.split("=");
        int lhs = 0, rhs = 0;
        for (String x : breakIt(lr[0])) {
            System.out.println(x);
            if (x.indexOf("x") >= 0) {
                lhs += Integer.parseInt(coeff(x));
            } else rhs -= Integer.parseInt(x);
        }
        for (String x : breakIt(lr[1])) {
            if (x.indexOf("x") >= 0) lhs -= Integer.parseInt(coeff(x));
            else rhs += Integer.parseInt(x);
        }
        if (lhs == 0) {
            if (rhs == 0) return "Infinite solutions";
            else return "No solution";
        }
        return "x=" + rhs / lhs;
    }

    public static List<String> breakIt(String s) {
        List<String> res = new ArrayList<>();
        String r = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                if (r.length() > 0) res.add(r);
                // 将方程拆解为一段段的运算符+数值的形式
                r = "" + s.charAt(i);
            } else r += s.charAt(i);
        }

        res.add(r);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(solveEquation("x=1"));
    }
}
