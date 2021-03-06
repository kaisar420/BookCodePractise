package com.chiclaim.data.structure.tree;

import java.util.*;

/**
 * 二分搜索树（Binary Search Tree）
 */
public class BST<T extends Comparable<T>> {

    private static class Node<T> {
        private T value;
        private Node<T> left, right;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add2(T e) {
        if (root == null) {
            root = new Node<>(e);
            size++;
            return;
        }
        add(root, e);
    }

    public void add(T e) {
        root = _add(root, e);
    }

    private void add(Node<T> node, T e) {

        //递归的终止条件
        if (node.value.compareTo(e) == 0) {
            return;
        } else if (e.compareTo(node.value) < 0 && node.left == null) {
            node.left = new Node<>(e);
            size++;
            return;
        } else if (e.compareTo(node.value) > 0 && node.right == null) {
            node.right = new Node<>(e);
            size++;
            return;
        }

        //如果添加的值小于节点的值，且节点的左孩子不为空
        if (e.compareTo(node.value) < 0) {
            add(node.left, e);
        }
        //如果添加的值大于节点的值，且节点的右孩子不为空
        else if (e.compareTo(node.value) > 0) {
            add(node.right, e);
        }

    }

    /**
     * 对add递归方法优化
     *
     * @param node
     * @param e
     * @return
     */
    private Node<T> _add(Node<T> node, T e) {
        if (node == null) {
            size++;
            return new Node<>(e);
        }

        if (e.compareTo(node.value) < 0)
            node.left = _add(node.left, e);
        else if (e.compareTo(node.value) > 0)
            node.right = _add(node.right, e);

        return node;
    }


    public boolean contains(T e) {
        return contains(root, e);
    }

    private boolean contains(Node<T> node, T e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.value) < 0) {
            return contains(node.left, e);
        } else if (e.compareTo(node.value) > 0) {
            return contains(node.right, e);
        }
        return true;
    }


    /**
     * 前序遍历
     */
    public void preorder() {
        preorder(root);
    }

    private void preorder(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preorder(node.left);
        preorder(node.right);
    }


    /**
     * 前序遍历的非递归实现
     */
    public void preorderNoRecurse() {
        if (root == null)
            return;
        Stack<Node<T>> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node<T> n = stack.pop();
            System.out.println(n.value);
            if (n.right != null)
                stack.push(n.right);
            if (n.left != null)
                stack.push(n.left);
        }
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node<T> node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.println(node.value);
        inorder(node.right);
    }


    public void postorder() {
        postorder(root);
    }

    private void postorder(Node<T> node) {
        if (node == null) {
            return;
        }
        postorder(node.left);
        postorder(node.right);
        System.out.println(node.value);
    }

    public void levelorder() {
        if (root == null)
            return;
        Deque<Node<T>> queue = new ArrayDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.removeFirst();
            System.out.println(node.value);
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }
    }

    public T getMax() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        return getMax(root).value;
    }

    private Node<T> getMax(Node<T> node) {
        if (node.right == null) {
            return node;
        }
        return getMax(node.right);
    }

    public T getMin() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        return getMin(root).value;
    }

    private Node<T> getMin(Node<T> node) {
        if (node.left == null) {
            return node;
        }
        return getMin(node.left);
    }

    public T removeMin() {
        T delete = getMin();
        //因为可能只有一个节点，所以需要root接收removeMin的返回值null
        root = removeMin(root);
        return delete;
    }

    private Node<T> removeMin(Node<T> node) {
        if (node.left == null) {
            Node<T> rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        //把要删除节点的右节点赋值给 父节点的左节点
        node.left = removeMin(node.left);
        return node;

    }

    public T removeMax() {
        T delete = getMax();
        //因为可能只有一个节点，所以需要root接收removeMin的返回值null
        root = removeMax(root);
        return delete;
    }

    public Node<T> removeMax(Node<T> node) {
        if (node.right == null) {
            Node<T> leftNode = node.left;
            size--;
            node.left = null;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 删除任意节点
     *
     * @param e
     */
    public void remove(T e) {
        root = remove(root, e);
    }

    private Node<T> remove(Node<T> node, T element) {
        if (node == null) {
            return null;
        }

        //如果要删除的节点小于当前节点，继续查询其左子树
        if (element.compareTo(node.value) < 0) {
            node.left = remove(node.left, element);
            return node;
        }
        //如果要删除的节点大于当前节点，继续查询其右子树
        if (element.compareTo(node.value) > 0) {
            node.right = remove(node.right, element);
            return node;
        }

        //=======要删除的节点就是当前的节点

        //如果要删除节点的左子树为空
        if (node.left == null) {
            Node<T> rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        //如果要删除节点的右子树为空
        if (node.right == null) {
            Node<T> leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        //=======如果要删除的节点左右子树都不为空

        //找到要删除节点的后继，也就是右子树的最小值
        Node<T> successor = getMin(node.right);
        successor.right = removeMin(node.right);
        successor.left = node.left;
        node.left = node.right = null;
        return successor;
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = new int[]{5, 3, 6, 8, 4, 2};
        for (int value : nums) {
            bst.add(value);
        }

        System.out.println("前序遍历（递归）：");
        bst.preorder();
        System.out.println("前序遍历（非递归）：");
        bst.preorderNoRecurse();
        System.out.println("中序遍历");
        bst.inorder();
        System.out.println("后序遍历");
        bst.postorder();
        System.out.println("广度优先遍历");
        bst.levelorder();

        System.out.println("get max: " + bst.getMax());
        System.out.println("get min: " + bst.getMin());

        bst.removeMin();
        bst.removeMax();
        bst.inorder();

        System.out.println("删除元素5");
        bst.remove(5);
        bst.inorder();
    }
}
