package com.android.moviewdemo.data.remote.error

import com.google.gson.annotations.SerializedName

/**
 * Data Model class which represents the error response
 * Author: Maraj Hussain
 **/
data class ErrorResponse (@SerializedName("status") var errorCode:  String,
                          @SerializedName("title") var errorMessage: String){
    init {
        errorCode = ""
        errorMessage = ""
    }
}