package org.example.leetcode.q0001t0100.q4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xz
 * @Date 2021/10/28 09:02
 * @Description TODO
 * 给定一个无重复元素的正整数数组 candidates 和一个正整数 target ，
 * 找出 candidates 中所有可以使数字和为目标数 target 的唯一组合。
 * <p>
 * candidates 中的数字可以无限制重复被选取。如果至少一个所选数字数量不同，则两种组合是唯一的。
 * <p>
 * 对于给定的输入，保证和为 target 的唯一组合数少于 150 个。
 * <p>
 * 示例 1：
 * <p>
 * 输入: candidates =
 * [2,3,6,7],
 * target =
 * 7
 * <p>
 * 输出: [[7],[2,2,3]]
 * 示例 2：
 * <p>
 * 输入: candidates = [2,3,5]
 * ,
 * target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 * 示例 3：
 * <p>
 * 输入: candidates =
 * [2],
 * target = 1
 * 输出: []
 * 示例 4：
 * <p>
 * 输入: candidates =
 * [1],
 * target =
 * 1
 * <p>
 * 输出: [[1]]
 * 示例 5：
 * <p>
 * 输入: candidates =
 * [1],
 * target =
 * 2
 * <p>
 * 输出: [[1,1]]
 * 提示：
 * <p>
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 * Related Topics
 * 数组
 * 回溯
 */
public class Question0039 {
    // 两点考虑,每次去使用无重复数组时,存在的选择方式
    // 选择使用元素后,目标值应当发生改变
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> combine = new ArrayList<Integer>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    public void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<Integer>(combine));
            return;
        }
        // 递归方法,每次执行都可以选择两种方案,使用当前值或不使用当前值
        // 直接跳过
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            // 递归下一个目标值
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }
}
