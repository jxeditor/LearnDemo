package org.example.leetcode.q0001t0100.q3;

/**
 * @Author xz
 * @Date 2021/10/21 14:40
 * @Description TODO
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * <p>
 * k 是一个正整数，它的值小于或等于链表的长度。
 * <p>
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * 进阶：
 * <p>
 * 你可以设计一个只使用常数额外空间的算法来解决此问题吗？
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * 示例 1：
 * <p>
 * <p>
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 * 示例 2：
 * <p>
 * <p>
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 * 示例 3：
 * <p>
 * 输入：head = [1,2,3,4,5], k = 1
 * 输出：[1,2,3,4,5]
 * 示例 4：
 * <p>
 * 输入：head = [1], k = 1
 * 输出：[1]
 * 提示：
 * <p>
 * 列表中节点的数量在范围 sz 内
 * 1 <= sz <= 5000
 * 0 <= Node.val <= 1000
 * 1 <= k <= sz
 * Related Topics
 * 递归
 * 链表
 */
public class Question0025 {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        // 截取K个节点进行翻转
        while (end.next != null) {
            // 结束节点为下一次翻转的pre,不足k个节点,直接跳出并返回
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) break;
            // 总链表开始位置
            ListNode start = pre.next;
            // 保存下一段链表的开始节点
            ListNode next = end.next;
            // 将需要翻转的start链表切割开来
            end.next = null;
            // 翻转好的链表拼接到pre下
            pre.next = reverse(start);

            // 下一个开始节点(头结点保留一个节点就好)
            start.next = next;
            pre = start;
            end = pre;
        }
        return dummy.next;
    }

    private static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            // 实现思路: 头的下一个节点为null,原先第二个节点的下一个节点为头结点
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(4);
        a.next.next = new ListNode(5);
        a.next.next.next = new ListNode(7);

        ListNode temp = reverseKGroup(a,2); //45
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }
}
