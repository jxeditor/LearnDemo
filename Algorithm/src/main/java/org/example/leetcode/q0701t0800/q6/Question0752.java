package org.example.leetcode.q0701t0800.q6;

import java.util.*;

/**
 * @Author xz
 * @Date 2021/10/27 09:43
 * @Description TODO
 * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
 * 每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。
 * 每次旋转都只能旋转一个拨轮的一位数字。
 * <p>
 * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
 * <p>
 * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
 * <p>
 * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
 * <p>
 * 示例 1:
 * <p>
 * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * 输出：6
 * 解释：
 * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
 * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
 * 因为当拨动到 "0102" 时这个锁就会被锁定。
 * 示例 2:
 * <p>
 * 输入: deadends = ["8888"], target = "0009"
 * 输出：1
 * 解释：
 * 把最后一位反向旋转一次即可 "0000" -> "0009"。
 * <p>
 * 示例 3:
 * <p>
 * 输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * 输出：-1
 * 解释：
 * 无法旋转到目标数字且不被锁定。
 * 示例 4:
 * <p>
 * 输入: deadends = ["0000"], target = "8888"
 * 输出：-1
 * 提示：
 * <p>
 * 1 <= deadends.length <= 500
 * deadends[i].length == 4
 * target.length == 4
 * target 不在 deadends 之中
 * target 和 deadends[i] 仅由若干位数字组成
 * Related Topics
 * 广度优先搜索
 * 数组
 * 哈希表
 * 字符串
 */
public class Question0752 {
    public static int openLock(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }
        Set<String> dead = new HashSet<String>();
        for (String deadend : deadends) {
            dead.add(deadend);
        }
        if (dead.contains("0000")) {
            return -1;
        }
        int step = 0;
        // 初始
        Queue<String> queue = new LinkedList<String>();
        queue.offer("0000");
        // 已经被遍历过的节点
        Set<String> seen = new HashSet<String>();
        seen.add("0000");
        while (!queue.isEmpty()) {
            ++step;
            int size = queue.size();
            // 遍历需要执行下一步操作的轮盘锁密码
            for (int i = 0; i < size; ++i) {
                String status = queue.poll();
                // 生成该轮盘锁能产生的所有执行一步的结果,初始是0000,下一步的种类有8种
                for (String nextStatus : get(status)) {
                    // 如果下一步没有被遍历过,且不在死亡列表中,添加到需要执行下一步操作的列表中
                    // 并且将该密码放入已遍历列表
                    if (!seen.contains(nextStatus) && !dead.contains(nextStatus)) {
                        if (nextStatus.equals(target)) {
                            return step;
                        }
                        queue.offer(nextStatus);
                        seen.add(nextStatus);
                    }
                }
                // 第一次遍历完,queue待遍历列表大小为8,循环这个操作,直到找到目标值,或者到达不了目标值
            }
        }
        return -1;
    }

    public static char numPrev(char x) {
        return x == '0' ? '9' : (char) (x - 1);
    }

    public static char numSucc(char x) {
        return x == '9' ? '0' : (char) (x + 1);
    } // 枚举 status 通过一次旋转得到的数字

    public static List<String> get(String status) {
        List<String> ret = new ArrayList<String>();
        char[] array = status.toCharArray();
        for (int i = 0; i < 4; ++i) {
            char num = array[i];
            array[i] = numPrev(num);
            ret.add(new String(array));
            array[i] = numSucc(num);
            ret.add(new String(array));
            array[i] = num;
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] deadends = {"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"};

        System.out.println(openLock(deadends, "8888"));
    }
}
