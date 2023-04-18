package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.util.fakes.data.FakePictureData
import org.junit.Assert
import org.junit.Test

class PictureMapperTest {

    @Test
    fun `map PictureEntity with remote origin to Picture Remote`() {
        val picture = FakePictureData.pictureEntityRemote.toPicture()
        Assert.assertTrue(picture is Picture.Remote)
        Assert.assertEquals(FakePictureData.pictureRemote, picture)
    }

    @Test
    fun `map PictureEntity with local origin to Picture Local`() {
        val picture = FakePictureData.pictureEntityLocal.toPicture()
        Assert.assertTrue(picture is Picture.Local)
        Assert.assertEquals(FakePictureData.pictureLocal, picture)
    }

    @Test
    fun `map PhotoDto to PictureEntity Remote`() {
        val picture = FakePictureData.pictureDto.toEntity("eventId")
        Assert.assertEquals(FakePictureData.pictureEntityRemote, picture)
    }

    @Test
    fun `map Picture to PictureEntity`() {
        val picture = FakePictureData.pictures.map { it.toEntity("eventId") }
        Assert.assertEquals(FakePictureData.pictureEntities, picture)
    }
}
