// 测试,此时堆中是没有顺序的
public class Temp {
    public static void main(String[] args) {
        // 源数据
        int[] data = {1, 4, 3, 5, 2};

        // 获取Top5
        int[] top5 = topK(data, 5);

        for (int i = 0; i < top5.length; i++) {
            System.out.println(top5[i]);
        }
    }

    // 从data数组中获取最大的k个数
    private static int[] topK(int[] data, int k) {
        // 先取K个元素放入一个数组topk中
        int[] topk = new int[k];
        for (int i = 0; i < k; i++) {
            topk[i] = data[i];
        }

        Heap heap = new Heap(topk);

        // 从k开始，遍历data
        for (int i = k; i < data.length; i++) {
            heap.sort();
            int root = heap.getRoot();

            // 当数据大于堆中最小的数（根节点）时，替换堆中的根节点，再转换成堆
            if (data[i] > root) {
                heap.setRoot(data[i]);
            }
        }

        heap.sort();
        return topk;
    }
}