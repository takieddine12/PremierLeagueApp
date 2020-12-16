package taki.eddine.premier.league.pro.webauthentification

data class NetworkStatesHandler<out T : Any>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T : Any> success(data: T?): NetworkStatesHandler<T> {
            return NetworkStatesHandler(Status.SUCCESS, data, null)
        }

        fun <T : Any> error(msg: String, data: T?): NetworkStatesHandler<T> {
            return NetworkStatesHandler(Status.ERROR, data, msg)
        }

        fun <T : Any> loading(data: T?): NetworkStatesHandler<T> {
            return NetworkStatesHandler(Status.LOADING, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}