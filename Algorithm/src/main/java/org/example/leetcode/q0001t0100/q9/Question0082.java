package org.example.leetcode.q0001t0100.q9;

/**
 * @Author xz
 * @Date 2021/11/24 10:36
 * @Description TODO
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。
 * <p>
 * 返回同样按升序排列的结果链表。
 * <p>
 * 示例 1：
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 * 示例 2：
 * 输入：head = [1,1,1,2,3]
 * 输出：[2,3]
 * 提示：
 * <p>
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序排列
 * Related Topics
 * 链表
 * 双指针
 */
public class Question0082 {
    public class ListNode {
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

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        // 下一个以及下两个都不允许为null
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                // 相等
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    // 删除中间节点
                    cur.next = cur.next.next;
                }
            } else {
                // 不相等,移到下一个节点
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
