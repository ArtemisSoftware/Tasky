package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Upsert
    fun upsert(pictures: List<PictureEntity>)

    @Query("DELETE FROM pictureEntity WHERE id in (:idList)")
    suspend fun deletePictures(idList: List<String>)

    @Transaction
    suspend fun upsertPictures(deletedPictures: List<String>, pictures: List<PictureEntity>) {
        deletePictures(deletedPictures)
        upsert(pictures)
    }
}
