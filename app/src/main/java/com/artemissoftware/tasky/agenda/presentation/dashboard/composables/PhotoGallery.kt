package com.artemissoftware.tasky.agenda.presentation.dashboard.composables

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.artemissoftware.tasky.BuildConfig
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun PhotoGallery(
    pictures: List<Picture>,
    onAddPicturesClick: () -> Unit,
    onPictureClick: (Picture) -> Unit,
    modifier: Modifier = Modifier,
    maxPictures: Int = BuildConfig.DEFAULT_MAX_PICTURES_PER_EVENT,
    backgroundColor: Color = Light2,
    isEditing: Boolean = false,
) {
    val display = Modifier.padding(vertical = 20.dp)
    val placeHolder = Modifier.clickable { onAddPicturesClick() }

    val galleryModifier = Modifier
        .background(color = backgroundColor)
        .then(if (pictures.isEmpty()) placeHolder else display).then(modifier)

    Row(
        modifier = galleryModifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (pictures.isEmpty()) {
            AddPhotoPlaceHolder(
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            PhotoGalleryDisplay(
                photos = pictures,
                isEditing = isEditing,
                maxPictures = maxPictures,
                onAddPicturesClick = onAddPicturesClick,
                onPictureClick = onPictureClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoGalleryPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PhotoGallery(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            onAddPicturesClick = {},
            onPictureClick = {},
            pictures = emptyList(),
        )
        PhotoGallery(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            isEditing = true,
            onAddPicturesClick = {},
            onPictureClick = {},
            pictures = emptyList(),
        )
    }
}

@Composable
private fun AddPhotoPlaceHolder(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        TaskyIcon(
            color = Gray,
            icon = R.drawable.ic_add,
        )
        Spacer(modifier = Modifier.width(22.dp))
        TaskyText(
            style = MaterialTheme.typography.subtitle2,
            color = Gray,
            text = stringResource(id = R.string.add_photos),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddPhotoPlaceHolderPreview() {
    AddPhotoPlaceHolder(
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun PhotoGalleryDisplay(
    photos: List<Picture>,
    onAddPicturesClick: () -> Unit,
    onPictureClick: (Picture) -> Unit,
    maxPictures: Int,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
) {
    Column(
        modifier = modifier,

    ) {
        TaskyText(
            style = MaterialTheme.typography.h5,
            text = stringResource(id = R.string.photos),
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = photos,
                itemContent = { item ->

                    PhotoDisplay(
                        isEditing = isEditing,
                        onPictureClick = onPictureClick,
                        picture = item,
                    )
                },
            )

            item {
                if (photos.size <= maxPictures) {
                    AnimatedVisibility(
                        visible = isEditing,
                        enter = VisibilityTransitions.enterEdition(),
                        exit = VisibilityTransitions.exitEdition(),
                    ) {
                        TaskySquareIcon(
                            modifier = Modifier
                                .clickable {
                                    onAddPicturesClick()
                                },
                            icon = R.drawable.ic_add,
                            borderWidth = 2.dp,
                            iconColor = LightBlue,
                            borderColor = LightBlue,
                            padding = 20.dp,
                            size = 60.dp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoDisplay(
    picture: Picture,
    isEditing: Boolean,
    onPictureClick: (Picture) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (picture) {
        is Picture.Local -> {
            TaskySquareIcon(
                source = Uri.parse(picture.uri),
                borderWidth = 2.dp,
                iconColor = LightBlue,
                borderColor = LightBlue,
                padding = 8.dp,
                size = 60.dp,
                modifier = modifier
                    .clickable {
                        if (isEditing) onPictureClick(picture)
                    },
            )
        }
        is Picture.Remote -> {
            TaskySquareIcon(
                source = picture.url,
                borderWidth = 2.dp,
                iconColor = LightBlue,
                borderColor = LightBlue,
                padding = 8.dp,
                size = 60.dp,
                modifier = modifier
                    .clickable {
                        onPictureClick(picture)
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoGalleryDisplayPreview() {
    PhotoGalleryDisplay(
        photos = emptyList(),
        maxPictures = 2,
        onAddPicturesClick = {},
        onPictureClick = {},
    )
}
