package com.artemissoftware.tasky.agenda.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.*
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.PhotoGallery
import com.artemissoftware.tasky.authentication.presentation.register.RegisterEvents

@Composable
fun DetailScreen(
    state: DetailState,
    events: (DetailEvents) -> Unit
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                onBackClicked = {
                    events(DetailEvents.PopBackStack)
                },
                backGroundColor = Black,
                title = "Date",
                toolbarActions = { color->

                    if(state.isEditing){
                        TaskyToolBarAction(
                            text = stringResource(id = R.string.save),
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Save)
                            }
                        )
                    }
                    else {
                        TaskyToolBarAction(
                            iconId = R.drawable.ic_edit,
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Edit)
                            }
                        )
                    }



                }
            )
        },
        content = {

            TaskyContentSurface(

                content = {

                    state.agendaItemType?.let{ item->

                        Box(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                AssignmentHeader(
                                    agendaItemType = item,
                                    title = "First title",
                                    modifier = Modifier.fillMaxWidth(),
                                    isEditing = state.isEditing,
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentDescription(
                                    isEditing = state.isEditing,
                                    description = "Second description of a really long one to prove that size is important for the space available",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                PhotoGallery(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(112.dp),
                                    isEditing = true,
                                    onAddPhotoClick = {},
                                    photos = Photo.mockPhotos
                                )

                                TaskyDivider(top = 20.dp, bottom = 28.dp)

                                AssignmentTime(
                                    isEditing = state.isEditing,
                                    title = R.string.from,
                                    day = "Jul 21 2022",
                                    hour = "08:00",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentNotification(
                                    isEditing = state.isEditing,
                                    description = "First description",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TaskyDivider(top = 20.dp, bottom = 30.dp)

                                VisitorsHeader(
                                    isEditing = state.isEditing,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                VisitorList(
                                    modifier = Modifier.padding(top = 20.dp),
                                    isEditing = state.isEditing
                                )


                            }



                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TaskyDivider(top = 20.dp, bottom = 20.dp)
                                TaskyTextButton(text = stringResource(id = item.title), onClick = {})
                            }

                        }

                    }



                }
            )
        }
    )
}

@Composable
private fun TaskyDivider (top: Dp, bottom: Dp = 0.dp) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = top, bottom = bottom),
        color = Light
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        state = DetailState(
            agendaItemType = AgendaItemType.Reminder()
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenEditingPreview() {
    DetailScreen(
        state = DetailState(
            isEditing = true,
            agendaItemType = AgendaItemType.Reminder()
        )
    )
}