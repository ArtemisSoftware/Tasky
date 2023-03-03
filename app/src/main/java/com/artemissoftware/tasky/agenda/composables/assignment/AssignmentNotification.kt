package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.TYIcon
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentNotification( // TODO: mudar os text para TYtext e as fontes mudat o icon da esquerda
    modifier: Modifier = Modifier,
    description: String,
    isEditing: Boolean = false
) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(0.9F)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TYIcon(icon = R.drawable.ic_launcher_foreground)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = description)
                }
            }

            Column(
                modifier = Modifier.weight(0.1F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = isEditing,
                    enter = VisibilityTransitions.enterEdition(),
                    exit = VisibilityTransitions.exitEdition()
                ) {
                    TYIcon(icon = R.drawable.ic_launcher_foreground)
                }
            }

        }


        TYDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AssignmentTimePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentNotification(description = "First description")
        AssignmentNotification(isEditing = true, description = "Second descriptionsdçljgjlsdglkdshfgklsdhfgjklhsdflkjghsdlkjfghlkdsfhgklsdfhgjklsdhfgjklshdfkljghsdlkfgjdfhgjklsdfhgjsdfg")
    }

}