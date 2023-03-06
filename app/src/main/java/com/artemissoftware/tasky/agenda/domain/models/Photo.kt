package com.artemissoftware.tasky.agenda.domain.models

data class Photo(
    val key: String,
    val url: String
){

    companion object{

        val mockPhotos = listOf(
            Photo("batman", "https://149455152.v2.pressablecdn.com/wp-content/uploads/2023/01/Batman-The-Adventures-Continue-Season-Three-1-1.jpg"),
            Photo("dark knight", "https://filmschoolrejects.com/wp-content/uploads/2022/02/The-Batman-Best-Comics.jpg"),
            Photo("dark 1 knight", "https://example.com/image.jpg")
        )
    }

}
