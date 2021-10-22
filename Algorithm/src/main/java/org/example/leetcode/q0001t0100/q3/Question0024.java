package org.example.leetcode.q0001t0100.q3;

/**
 * @Author xz
 * @Date 2021/10/21 10:49
 * @Description TODO
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 * 示例 2：
 * <p>
 * 输入：head = []
 * 输出：[]
 * 示例 3：
 * <p>
 * 输入：head = [1]
 * 输出：[1]
 * 提示：
 * <p>
 * 链表中节点的数目在范围 [0, 100] 内
 * 0 <= Node.val <= 100
 * 进阶：你能在不修改链表节点值的情况下解决这个问题吗?（也就是说，仅修改节点本身。）
 * <p>
 * Related Topics
 * 递归
 * 链表
 */
public class Question0024 {
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

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 使用递归的思想,生成一个新的链表,头结点是第二个节点,第二个节点是下下个节点
        // 其中刚好两两交换,1457,方法第一次是1和4互换,第二次5和7互换
        // 1->4->null
        ListNode newHead = head.next; // 4
        head.next = swapPairs(newHead.next);// 1->null
        newHead.next = head;// 4->1->null
        return newHead;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(4);
        a.next.next = new ListNode(5);
        a.next.next.next = new ListNode(7);

        ListNode temp = swapPairs(a); //45
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }
}
