package org.example.leetcode.q0001t0100.q9;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @Author xz
 * @Date 2021/11/24 11:23
 * @Description TODO
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * <p>
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 示例 1:
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 * 示例 2：
 * 输入： heights = [2,4]
 * 输出： 4
 * 提示：
 * <p>
 * 1 <= heights.length <=105
 * 0 <= heights[i] <= 104
 * Related Topics
 * 栈
 * 数组
 * 单调栈
 */
public class Question0084 {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        Deque<Integer> mono_stack = new ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    // 暴力
    public static int largestRectangleArea1(int[] heights) {
        int n = heights.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int min = heights[i];
            for (int j = i + 1; j < n; j++) {
                min = Math.min(min, heights[j]);
                ans = Math.max(ans, min * (j - i + 1));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] input = {6, 7, 5, 2, 4, 5, 9, 3};
        System.out.println(largestRectangleArea1(input));
    }
}
