package com.tseong.learning.advance._08_algorithm;

/**
 * https://www.cnblogs.com/chengxiao/p/6129630.html
 * @author: Shawn ZHONG @date: 2020-10-30 14:29
 */
public class _03_HeapSort {

    public static void main(String[] args) {
        int[] array = { 1,3,4,5,2,6,9,8,0};

        _03_HeapSort heap = new _03_HeapSort();

        System.out.println("before sorted:\t");
        heap.printPart(array, 0, array.length - 1);

        System.out.println("created heap:");
        heap.heapSort(array);
        System.out.println("After sorted:\t");
        heap.printPart(array, 0, array.length - 1);
    }


    public void heapSort(int[] list) {

        // 构建大顶堆；第一个非叶子节点为 length/2 -1
        for (int i=list.length / 2-1; i>=0; i--) {

            // 从第一个非叶子节点从下到上，从右到左 构建大顶堆
            HeapAdjust(list, i, list.length);
            printPart(list, 0, list.length - 1);
        }

        for (int j=list.length-1; j>0; j--) {
            swap(list, 0, j);   // 将堆顶元素与末尾元素swap
            HeapAdjust(list, 0, j);

            System.out.println();
            printPart(list, 0, j-1);
        }
    }



    public void HeapAdjust(int[] array, int node, int length) {
        int temp = array[node];   // temp 保存当前父节点
        int child = 2*node + 1;   // left child

        while (child < length) {
            // 如果有右孩子节点，并且右孩子节点的值大于左孩子节点，则选取右孩子节点
            if (child + 1 < length && array[child] < array[child + 1]) {
                child ++;
            }

            // 如果父节点的值已经大于两个孩子节点的值，则直接结束
            if (temp >= array[child]) {
                break;
            }

            // 把孩子节点的值赋给父节点
            swap(array, node, child);

            // 选取孩子节点的左孩子节点，继续向下筛选
            node = child;
            child = 2*child +1;
        }
    }


    public void printPart(int[] list, int begin, int end) {

        for (int i=begin; i<=end; i++) {
            System.out.print(list[i] + "\t");
        }

        System.out.println();
    }

    public static void swap(int[] list, int a, int b) {
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }

}
