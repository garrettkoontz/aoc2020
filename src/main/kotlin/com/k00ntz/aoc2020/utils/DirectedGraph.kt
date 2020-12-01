package com.k00ntz.aoc2020.utils

class DirectedGraph<T>(private val nodes: MutableMap<T, DGNode<T>> = mutableMapOf()) {
    fun addNode(t: T): DGNode<T> =
        DGNode(t).also {
            nodes[t] = it
        }


    fun addFromTo(t1: T, t2: T) {
        val node1 = nodes[t1] ?: addNode(t1)
        val node2 = nodes[t2] ?: addNode(t2)
        node1.children.add(node2)
        node2.parents.add(node1)
    }

    fun findNode(t: T): DGNode<T>? =
        nodes[t]

}

class DGNode<T>(
    val value: T,
    val children: MutableList<DGNode<T>> = mutableListOf(),
    val parents: MutableList<DGNode<T>> = mutableListOf()
) {
    override fun toString(): String {
        return "Node(value=$value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DGNode<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

}