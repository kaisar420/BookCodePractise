package com.chiclaim.data.structure.leetcode.tree;
public class LeetCode303 {
    class NumArray {

        private int tree[];
        private int[] data;

        public NumArray(int[] arr) {
            if (arr == null || arr.length == 0) {
                return;
            }
            data = new int[arr.length];
            for (int i = 0; i < data.length; i++) {
                data[i] = arr[i];
            }

            this.tree = new int[data.length * 4];
            buildSegmentTree(0, 0, data.length - 1);
        }

        private void buildSegmentTree(int treeIndex, int treeLeft, int treeRight) {
            if (treeLeft == treeRight) {
                tree[treeIndex] = data[treeLeft];
                return;
            }
            //当前节点左子树索引
            int leftTreeIndex = getLeft(treeIndex);
            //当前节点右子树索引
            int rightTreeIndex = getRight(treeIndex);
            //int mid = (left+right)/2;
            int mid = treeLeft + (treeRight - treeLeft) / 2;
            //构建左子树
            buildSegmentTree(leftTreeIndex, treeLeft, mid);
            //构建右子树
            buildSegmentTree(rightTreeIndex, mid + 1, treeRight);
            //当前节点存放的值
            tree[treeIndex] = (tree[leftTreeIndex] + tree[rightTreeIndex]);

        }

        public int query(int start, int end) {
            return query(0, 0, data.length - 1, start, end);
        }

        private int query(int treeIndex, int treeLeft, int treeRight, int queryL, int queryR) {

            //1, 需要查找的范围完刚好在这个treeIndex节点的区间
            if (treeLeft == queryL && treeRight == queryR) {
                return tree[treeIndex];
            }

            //当前节点的区间的中间点
            int mid = treeLeft + (treeRight - treeLeft) / 2;
            //左子树索引
            int leftTreeIndex = getLeft(treeIndex);
            //右子树索引
            int rightTreeIndex = getRight(treeIndex);


            //2, 需要查找的范围完全在左子树的区间里
            if (queryR <= mid) {
                return query(leftTreeIndex, treeLeft, mid, queryL, queryR);
            }
            //3, 需要查找的范围完全在右子树区间里
            if (queryL >= mid + 1) {
                return query(rightTreeIndex, mid + 1, treeRight, queryL, queryR);
            }

            //需要查找的范围一部分在左子树里，一部分在右子树中
            int left = query(leftTreeIndex, treeLeft, mid, queryL, mid);
            int right = query(rightTreeIndex, mid + 1, treeRight, mid + 1, queryR);
            return left + right;
        }


        public int getLeft(int index) {
            return index * 2 + 1;
        }

        public int getRight(int index) {
            return index * 2 + 2;
        }

        public int sumRange(int i, int j) {
            return query(i, j);
        }

    }

    public static void main(String[] args) {
        //int[] numbers = new int[]{};
        int[] numbers = new int[]{2, 1, 4, 88, 1};
        NumArray array = new LeetCode303().new NumArray(numbers);
        System.out.println(array.sumRange(0, 3));
    }

}
