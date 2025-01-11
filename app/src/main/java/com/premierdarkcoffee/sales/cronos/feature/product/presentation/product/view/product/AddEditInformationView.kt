package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.cronos.util.function.ImageInfo
import com.premierdarkcoffee.sales.cronos.util.function.uploadImageToFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun AddEditInformationView(onAddInformationResultStateButtonClick: (informationResultState: InformationResultState) -> Unit) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        selectedImageUri = uri
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isProcessing by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(16.dp)
        .semantics { contentDescription = "Form for adding or editing information" }) {

        // Image Selection Card with Improved Accessibility
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(horizontal = 11.dp),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(4.dp)) {
            selectedImageUri?.let { image ->
                AsyncImage(model = image,
                           contentDescription = "Selected image preview. Tap to change image",
                           modifier = Modifier
                               .fillMaxWidth()
                               .clip(MaterialTheme.shapes.medium)
                               .padding(16.dp)
                               .clickable {
                                   photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                               },
                           contentScale = ContentScale.FillWidth)
            } ?: run {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp)
                    .clickable {
                        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .semantics { contentDescription = "Tap to select an image" }, contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // Title TextField
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(horizontal = 11.dp),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(4.dp)) {
            TextField(value = title,
                      onValueChange = { title = it },
                      label = { Text(text = "Title") },
                      modifier = Modifier
                          .fillMaxWidth()
                          .semantics { contentDescription = "Title input field" })
        }

        // Subtitle TextField
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(horizontal = 11.dp),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(4.dp)) {
            TextField(value = subtitle,
                      onValueChange = { subtitle = it },
                      label = { Text(text = "Subtitle") },
                      modifier = Modifier
                          .fillMaxWidth()
                          .semantics { contentDescription = "Subtitle input field" })
        }

        // Description TextField
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(horizontal = 11.dp),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(4.dp)) {
            TextField(value = description,
                      onValueChange = { description = it },
                      label = { Text(text = "Description") },
                      modifier = Modifier
                          .fillMaxWidth()
                          .semantics { contentDescription = "Description input field" })
        }

        // Submit Button with Feedback Handling
        Button(onClick = {
            if (isProcessing) return@Button // Prevent multiple clicks
            isProcessing = true
            scope.launch(Dispatchers.Main) {
                selectedImageUri?.let { uri ->
                    try {
                        uploadImageToFirebase(context = context,
                                              contentResolver = context.contentResolver,
                                              uri = uri,
                                              path = "information") { imageInfo: ImageInfo ->
                            val informationResultState = InformationResultState(id = UUID.randomUUID().toString(),
                                                                                title = title,
                                                                                subtitle = subtitle,
                                                                                description = description,
                                                                                image = uri,
                                                                                path = imageInfo.path,
                                                                                url = imageInfo.url,
                                                                                belongs = false,
                                                                                place = 0,
                                                                                isCreated = true,
                                                                                isDeleted = false)
                            onAddInformationResultStateButtonClick(informationResultState)
                        }
                    } finally {
                        isProcessing = false
                    }
                }
            }
        },
               modifier = Modifier
                   .padding(16.dp)
                   .semantics { contentDescription = "Submit button. Tap to save the information" },
               enabled = !isProcessing) {
            Text("Submit")
        }
    }
}
