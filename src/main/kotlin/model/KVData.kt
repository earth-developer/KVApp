package model

data class Group(
    var name: String,
    var id: String = java.util.UUID.randomUUID().toString()
){
    constructor() : this("")
}

data class KVItem(
    var title: String,
    var key: String,
    var value: String,
    var groupId: String,
    var id: String = java.util.UUID.randomUUID().toString()
){
    constructor() : this("", "", "", "")
}