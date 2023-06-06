package util

interface ResourceProvider {
    fun getString(id: Int): String
}