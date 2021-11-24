package org.example.leetcode.q0001t0100.q9;

/**
 * @Author xz
 * @Date 2021/11/24 10:45
 * @Description TODO
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
 * <p>
 * 返回同样按升序排列的结果链表。
 * <p>
 * 示例 1：
 * 输入：head = [1,1,2]
 * 输出：[1,2]
 * 示例 2：
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 * 提示：
 * <p>
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序排列
 * Related Topics
 * 链表
 */
public class Question0083 {
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

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                // 例如1,1,1,3,4
                // 第一次生成的cur是1,1,3,4,当前的cur.val还是1
                cur.next = cur.next.next;
            } else {
                // 等到1,3,4时,cur移到3节点位置
                cur = cur.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode node = deleteDuplicates(new ListNode(1,new ListNode(1,new ListNode(1,new ListNode(2)))));
        while (node != null){
            System.out.println(node.val);
            node = node.next;
        }
    }

}
