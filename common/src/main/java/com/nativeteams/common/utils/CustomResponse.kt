package com.nativeteams.common.utils

sealed class CustomResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : CustomResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : CustomResponse<T>(data, message)
    class Loading<T>(data: T? = null) : CustomResponse<T>(data)
}
