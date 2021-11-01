package org.example.leetcode.q0001t0100.q5;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author xz
 * @Date 2021/10/28 10:20
 * @Description TODO
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 * 示例 2：
 * <p>
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 * 提示：
 * <p>
 * n == height.length
 * 1 <= n <= 2 * 104
 * 0 <= height[i] <= 105
 * Related Topics
 * 栈
 * 数组
 * 双指针
 * 动态规划
 * 单调栈
 */
public class Question0042 {
    // 思路: 从左及从右遍历数值,得出两边能接雨水量,再重新遍历结果集,取最小值
    public static int trap(int[] height) {
        int n = height.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int sum = 0;
        left[0] = height[0];
        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1], height[i]);
        }

        right[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], height[i]);
        }

        for (int i = 0; i < n; i++) {
            sum += Math.min(left[i], right[i]) - height[i];
        }

        return sum;
    }


    // 单调栈
    // [3,1,2,1,3] 将下标入栈,可以看做入栈找比栈顶大的元素,找到则将栈顶出栈,计算出栈之后栈顶与当前元素的最小值减去中间挡板的高度,乘上之间的宽度
    // 入栈,出栈计算明细
    // 0
    // ->01  不符合比栈顶元素大并且栈不为空
    // ->02  2比1大所以1出栈计算(Min(num[2] ,num[0]) - num[1]) * (2 - 0 - 1)
    // ->023
    // ->02  3比1大所以1出栈计算(Min(num[4] ,num[2]) - num[3]) * (4 - 2 - 1)
    // ->0   3比2大所以2出栈计算(Min(num[4] ,num[0]) - num[2]) * (4 - 0 - 1)
    // ->04   栈内还有一个0,但是并不符合条件,所以4入栈
    public static int trap1(int[] height) {
        int ans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        int n = height.length;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                // 记录中间间隔
                int currWidth = i - left - 1;
                // 记录中间高度,最小挡板-中间挡板高度,就是雨水接收的大小
                int currHeight = Math.min(height[left], height[i]) - height[top];
                ans += currWidth * currHeight;
            }
            stack.push(i);
        }
        return ans;
    }


    // 双指针
    public static int trap3(int[] height) {
        int ans = 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            // 获取左右最长挡板
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            // 如果左挡板小于右挡板
            if (height[left] < height[right]) {
                // 左最长挡板-当前挡板长度
                ans += leftMax - height[left];
                ++left;
            } else {
                // 右最长挡板-当前挡板长度
                ans += rightMax - height[right];
                --right;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] input = {4, 2, 0, 3, 2, 5};
        System.out.println(trap(input));
    }
}
