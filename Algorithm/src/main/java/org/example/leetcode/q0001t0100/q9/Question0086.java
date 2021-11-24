package org.example.leetcode.q0001t0100.q9;

/**
 * @Author xz
 * @Date 2021/11/24 15:59
 * @Description TODO
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 * <p>
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：head = [1,4,3,2,5,2], x = 3
 * 输出：[1,2,2,4,3,5]
 * 示例 2：
 * <p>
 * 输入：head = [2,1], x = 2
 * 输出：[1,2]
 * 提示：
 * <p>
 * 链表中节点的数目在范围 [0, 200] 内
 * -100 <= Node.val <= 100
 * -200 <= x <= 200
 * Related Topics
 * 链表
 * 双指针
 */
public class Question0086 {
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

    public static ListNode partition(ListNode head, int x) {
        // 思路: 对于 head = [1,4,3,2,5,2], x = 3
        // 判断链表中值是否小于x=3,拆成l1=[1] l2=[4],逐步逐步,将链表拆开来为l1[1,2,2],l2[4,3,5]
        // 最终拼接起来
        // 这里需要了解链表的一个特性,操作l1的时候,l1h的链表元素会发生改变
        // 像l1,从[0]l1当前为0->[0,1,4,3,2,5,2]l1当前为1->[0,1,2,5,2]l1当前为2->[0,1,2,2]l1当前为2
        // 对应l2也是同样的操作
        ListNode l1 = new ListNode(0);
        ListNode l1h = l1;
        ListNode l2 = new ListNode(0);
        ListNode l2h = l2;

        while (head != null) {
            if (head.val < x) {
                l1.next = head;
                l1 = l1.next;
            } else {
                l2.next = head;
                l2 = l2.next;
            }
            head = head.next;
        }
        l2.next = null;
        l1.next = l2h.next;
        return l1h.next;
    }

    public static void main(String[] args) {
        ListNode node = partition(new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2)))))), 3);
        System.out.println(node.val + "-" + node.next.val + "-" + node.next.next.val);
    }
}
