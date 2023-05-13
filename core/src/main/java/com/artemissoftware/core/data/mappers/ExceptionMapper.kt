package com.artemissoftware.core.data.mappers

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource

fun<T> TaskyNetworkException?.toResource(defaultException: ValidationException): Resource<T>{
    return this?.let {
        if(code == 401){
            Resource.NotAuthenticated()
        }
        else{
            Resource.Error(ValidationException.DataError(description))
        }
    } ?: run { Resource.Error(defaultException)  }
}