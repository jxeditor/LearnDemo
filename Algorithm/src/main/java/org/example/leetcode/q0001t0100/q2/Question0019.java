package org.example.leetcode.q0001t0100.q2;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author xz
 * @Date 2021/10/18 08:44
 * @Description TODO
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * <p>
 * 进阶：你能尝试使用一趟扫描实现吗？
 * <p>
 * 示例 1：
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * <p>
 * 示例 2：
 * 输入：head = [1], n = 1
 * 输出：[]
 * <p>
 * 示例 3：
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 * 提示：
 * <p>
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 */
public class Question0019 {
    static class ListNode {
        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 栈先进后出原则,用来计算倒数N个节点
        Deque<ListNode> deque = new LinkedList<>();
        ListNode temp = new ListNode(0);
        temp.next = head;
        while (temp != null) {
            deque.push(temp);
            temp = temp.next;
        }
        for (int i = 0; i < n; ++i) {
            deque.poll();
        }
        ListNode peek = deque.peek();
        assert peek != null;
        // 链表剔除,下一个节点位置指向下下个
        peek.next = peek.next.next;

        return head;
    }

    public static void main(String[] args) {
        ListNode input = new ListNode(1);
        input.next = new ListNode(2);
        input.next.next = new ListNode(3);
        input.next.next.next = new ListNode(4);

        ListNode result = removeNthFromEnd(input, 2);
        System.out.println(result.val);
        System.out.println(result.next.val);
        System.out.println(result.next.next.val);
    }
}
