package org.example.leetcode.q0001t0100.q7;

import java.util.List;

/**
 * @Author xz
 * @Date 2021/11/8 09:38
 * @Description TODO
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[4,5,1,2,3]
 * 示例 2：
 * <p>
 * <p>
 * 输入：head = [0,1,2], k = 4
 * 输出：[2,0,1]
 * 提示：
 * <p>
 * 链表中节点的数目在范围 [0, 500] 内
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 109
 * Related Topics
 * 链表
 * 双指针
 */
public class Question0061 {
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

    public static ListNode rotateRight(ListNode head, int k) {
        // 思路,遍历一遍链表得出长度,然后进行头节点和部分尾结点互移动即可
        ListNode temp = head;
        int len = 1;
        while (temp.next != null) {
            temp = temp.next;
            len++;
        }
        // 这里解释一下为啥需要对k进行取余操作,长度为3的链表,旋转4次,其实就相当于旋转1次
        int n = len - k % len;
        // 这里需要使用temp.next接入head是因为后续好进行链表分割,目前其实已经连成了一个环
        // 找到断开位置,赋给新的链表,将断开位置的下个节点置为null
        temp.next = head;
        for (int i = 0; i < n; i++) {
            temp = temp.next;
        }
        // 尾部节点
        ListNode res = temp.next;
        temp.next = null;
        return res;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        ListNode listNode = rotateRight(head, 2);
        System.out.println(listNode.val + "" + listNode.next.val + "" + listNode.next.next.val + "" + listNode.next.next.next.val);
    }
}
