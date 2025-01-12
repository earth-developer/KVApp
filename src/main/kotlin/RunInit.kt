object RunInit {
    private val initFunctions = mutableListOf<() -> Unit>()
    var isInit = false

    fun register(initFun: () -> Unit) {
        initFunctions.add(initFun)
    }

    fun run() {
        if (isInit) return
        isInit = true
        initFunctions.forEach { it() }
    }
}