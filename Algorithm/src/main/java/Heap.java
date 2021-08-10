public class Heap {
    // 堆的存储结构 - 数组
    private int[] data;

    // 将一个数组传入构造方法，并转换成一个堆
    public Heap(int[] data) {
        this.data = data;
        buildHeap();
    }

    // 将数组转换成堆
    private void buildHeap() {
        for (int i = (data.length) / 2 - 1; i >= 0; i--) {
            // 对有子结点的元素heapify
            heapify(i, data.length);
        }
    }

    private void heapify(int i, int length) {
        // 获取左右结点的数组下标
        int l = left(i);
        int r = right(i);

        // 这是一个临时变量，表示 跟结点、左结点、右结点中最小的值的结点的下标
        int smallest = i;
        // 存在左结点，且左结点的值小于根结点的值
        if (l < length && data[l] < data[smallest])
            smallest = l;
        // 存在右结点，且右结点的值小于以上比较的较小值
        if (r < length && data[r] < data[smallest])
            smallest = r;
        // 左右结点的值都大于根节点，直接return，不做任何操作
        if (i == smallest)
            return;
        // 交换根节点和左右结点中最小的那个值，把根节点的值替换下去
        swap(i, smallest);
        // 由于替换后左右子树会被影响，所以要对受影响的子树再进行heapify
        heapify(smallest, length);
    }

    public void sort() {
        // 交换堆顶元素和最后一个元素,再重新heapify可以排序,堆排序
        for (int i = data.length - 1; i >= 0; i--) {
            System.out.println(data[0]+""+data[1]+""+data[2]+""+data[3]+""+data[4]);
            swap(i, 0);
            heapify(0, i);
        }
    }

    // 获取右结点的数组下标
    private int right(int i) {
        return (i + 1) << 1;
    }

    // 获取左结点的数组下标
    private int left(int i) {
        return ((i + 1) << 1) - 1;
    }

    // 交换元素位置
    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    // 获取堆中的最小的元素，根元素
    public int getRoot() {
        return data[0];
    }

    // 替换根元素，并重新heapify
    public void setRoot(int root) {
        data[0] = root;
        heapify(0, data.length);
    }
}