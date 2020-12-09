package com.k00ntz.aoc2020.utils

class WeightedDirectedGraph<T, W>(private val nodes: MutableMap<T, WeightedNode<W, T>> = mutableMapOf()) {

    companion object {
        fun <T, W> buildFromMap(input: Map<T, Map<T, W>>): WeightedDirectedGraph<T, W> {
            val graph = WeightedDirectedGraph<T, W>()
            input.forEach { (bag, children) ->
                children.forEach { (b, n) ->
                    graph.addFromTo(bag, b, n)
                }
            }
            return graph
        }
    }

    fun filter(predicate: (WeightedNode<W, T>) -> Boolean): List<WeightedNode<W, T>> =
        nodes.values.filter(predicate)


    fun addNode(t: T): WeightedNode<W, T> =
        WeightedNode<W, T>(t).also {
            nodes[t] = it
        }

    fun addFromTo(t1: T, t2: T, w: W) {
        val node1 = nodes[t1] ?: addNode(t1)
        val node2 = nodes[t2] ?: addNode(t2)
        node1.children[node2] = w
        node2.parents[node1] = w
    }

    fun findNode(t: T): WeightedNode<W, T>? =
        nodes[t]

}

class WeightedNode<W, T>(
    val value: T,
    val children: MutableMap<WeightedNode<W, T>, W> = mutableMapOf(),
    val parents: MutableMap<WeightedNode<W, T>, W> = mutableMapOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeightedNode<*, *>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "WeightedNode(value=$value)"
    }
}

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

open class DGNode<T>(
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