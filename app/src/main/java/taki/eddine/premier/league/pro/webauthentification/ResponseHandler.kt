package taki.eddine.premier.league.pro.webauthentification

import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {

    companion object {
        fun <T : Any> handleSuccess(data: T): NetworkStatesHandler<T> {
            return NetworkStatesHandler.success(data)
        }

        fun <T : Any> handleException(e: Exception): NetworkStatesHandler<T> {
            return when (e) {
                is HttpException -> NetworkStatesHandler.error(getErrorMessage(e.code()), null)
                is SocketTimeoutException -> NetworkStatesHandler.error(getErrorMessage(502), null)
                else -> NetworkStatesHandler.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }
        private fun getErrorMessage(code: Int): String {
            return when (code) {
                502 -> "Timeout"
                401 -> "Unauthorised"
                404 -> "Not found"
                else -> "Something went wrong"
            }
        }
    }

}