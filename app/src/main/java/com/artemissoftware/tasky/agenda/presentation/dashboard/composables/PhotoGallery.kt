package com.artemissoftware.tasky.agenda.presentation.dashboard.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun PhotoGallery(
    photos: List<Photo>,
    onAddPhotoClick: ()->Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Light2,
    isEditing: Boolean = false
) {

    val display = Modifier.padding(vertical = 20.dp).then(modifier)
    val placeHolder = Modifier.clickable { onAddPhotoClick() }.then(modifier)

    val galleryModifier = Modifier
        .background(color = backgroundColor)
        .then(if (photos.isEmpty()) placeHolder  else display)

    Row(
        modifier = galleryModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(photos.isEmpty()){
            PhotoPlaceHolder(
                modifier = Modifier.fillMaxWidth()
            )
        }
        else{
            PhotoGalleryDisplay(
                photos = photos,
                isEditing = isEditing
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun PhotoGalleryPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PhotoGallery(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            onAddPhotoClick = {},
            photos = emptyList()
        )
        PhotoGallery(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            isEditing = true,
            onAddPhotoClick = {},
            photos = Photo.mockPhotos
        )
    }

}


@Composable
private fun PhotoPlaceHolder(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TaskyIcon(
            color = Gray,
            icon = R.drawable.ic_add
        )
        Spacer(modifier = Modifier.width(22.dp))
        TaskyText(
            style = MaterialTheme.typography.subtitle2,
            color = Gray,
            text = stringResource(id = R.string.add_photos)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PhotoPlaceHolderPreview() {
    PhotoPlaceHolder(
        modifier = Modifier.fillMaxWidth()
    )
}





@Composable
private fun PhotoGalleryDisplay(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {

    Column(
        modifier = modifier

    ) {

        TaskyText(
            style = MaterialTheme.typography.h5,
            text = stringResource(id = R.string.photos)
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = photos,
                key = {
                    it.key
                },
                itemContent = { item ->

                    TaskySquareIcon(
                        url = item.url,
                        borderWidth = 2.dp,
                        iconColor = LightBlue,
                        borderColor = LightBlue,
                        padding = 8.dp,
                        size = 60.dp
                    )
                }
            )

            item {

                AnimatedVisibility(
                    visible = isEditing,
                    enter = VisibilityTransitions.enterEdition(),
                    exit = VisibilityTransitions.exitEdition()
                ) {

                    TaskySquareIcon(
                        icon = R.drawable.ic_add,
                        borderWidth = 2.dp,
                        iconColor = LightBlue,
                        borderColor = LightBlue,
                        padding = 20.dp,
                        size = 60.dp
                    )
                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun PhotoGalleryDisplayPreview() {
    PhotoGalleryDisplay(
        photos = Photo.mockPhotos
    )
}