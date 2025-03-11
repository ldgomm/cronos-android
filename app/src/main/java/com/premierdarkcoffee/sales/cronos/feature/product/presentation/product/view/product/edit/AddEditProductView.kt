package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.premierdarkcoffee.sales.cronos.R
import com.premierdarkcoffee.sales.cronos.R.string.add_information_label
import com.premierdarkcoffee.sales.cronos.R.string.category_label
import com.premierdarkcoffee.sales.cronos.R.string.create_product_label
import com.premierdarkcoffee.sales.cronos.R.string.description_label
import com.premierdarkcoffee.sales.cronos.R.string.group_label
import com.premierdarkcoffee.sales.cronos.R.string.label_label
import com.premierdarkcoffee.sales.cronos.R.string.legal_information_label
import com.premierdarkcoffee.sales.cronos.R.string.model_label
import com.premierdarkcoffee.sales.cronos.R.string.name_label
import com.premierdarkcoffee.sales.cronos.R.string.origin_label
import com.premierdarkcoffee.sales.cronos.R.string.owner_label
import com.premierdarkcoffee.sales.cronos.R.string.subcategory_label
import com.premierdarkcoffee.sales.cronos.R.string.update_product_label
import com.premierdarkcoffee.sales.cronos.R.string.warning_information_label
import com.premierdarkcoffee.sales.cronos.R.string.warranty_information_label
import com.premierdarkcoffee.sales.cronos.R.string.year_label
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Category
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Domain
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Subclass
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common.InformationListView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common.SectionView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit.components.IntTextFieldCardWithStepper
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit.components.KeywordBubble
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit.components.SmartDoubleTextFieldCard
import com.premierdarkcoffee.sales.cronos.util.constant.Constant.eleven
import com.premierdarkcoffee.sales.cronos.util.constant.Constant.four
import com.premierdarkcoffee.sales.cronos.util.constant.Constant.six
import com.premierdarkcoffee.sales.cronos.util.constant.paises
import com.premierdarkcoffee.sales.cronos.util.function.uploadImageToFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductView(addEditProductState: AddEditProductState,
                       product: Product?,
                       setName: (String) -> Unit,
                       setLabel: (String) -> Unit,
                       setOwner: (String) -> Unit,
                       setYear: (String) -> Unit,
                       setModel: (String) -> Unit,
                       setDescription: (String) -> Unit,
                       groups: List<Group>,
                       setCategory: (Category) -> Unit,
                       setPrice: (Price) -> Unit,
                       setStock: (Int) -> Unit,
                       setImage: (Image) -> Unit,
                       setOrigin: (String) -> Unit,
                       informationResultStateList: List<InformationResultState>,
                       addInformationResultState: (InformationResultState) -> Unit,
                       addKeyword: (String) -> Unit,
                       deleteKeyword: (Int) -> Unit,
                       setWarranty: (String?) -> Unit,
                       setLegal: (String?) -> Unit,
                       setWarning: (String?) -> Unit,
                       addProduct: (Product) -> Unit,
                       updateProduct: (Product) -> Unit) {

    val context = LocalContext.current
    var mainImageHasChanged by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(contract = PickVisualMedia()) { uri ->
        selectedImageUri = uri
        mainImageHasChanged = true
    }

    var origin: String by remember { mutableStateOf(addEditProductState.origin) }
    var isOriginExpanded by remember { mutableStateOf(false) }

    var areGroupsExpanded by remember { mutableStateOf(false) }
    var areDomainsExpanded by remember { mutableStateOf(false) }
    var areSubclassesExpanded by remember { mutableStateOf(false) }

    var group: Group by remember { mutableStateOf(Group(addEditProductState.category.group, emptyList())) }
    var domain: Domain by remember { mutableStateOf(Domain(addEditProductState.category.domain, emptyList())) }
    var subclass: Subclass by remember { mutableStateOf(Subclass(addEditProductState.category.subclass)) }
    val domains: List<Domain> = group.domains
    val subclasses: List<Subclass> = domain.subclasses

    var amountText by remember { mutableStateOf(product?.price?.amount?.toString() ?: "0.0") }
    var amount by remember { mutableDoubleStateOf(product?.price?.amount ?: 0.0) }

    var isOfferActive: Boolean by remember { mutableStateOf(product?.price?.offer?.isActive ?: false) }
    var discount: Int by remember { mutableIntStateOf(product?.price?.offer?.discount ?: 0) }

    var withoutInterest: Int by remember { mutableIntStateOf(product?.price?.creditCard?.withoutInterest ?: 0) }
    var withInterest: Int by remember { mutableIntStateOf(product?.price?.creditCard?.withInterest ?: 0) }
    var freeMonths: Int by remember { mutableIntStateOf(product?.price?.creditCard?.freeMonths ?: 0) }

    var word: String by remember { mutableStateOf("") }

    val isUpdatingProduct: Boolean = product != null

    val scope = rememberCoroutineScope()
    val scrollBehavior = rememberScrollState()

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

//    LaunchedEffect(Unit) {
//        Log.d(TAG, "AddEditProductView: Price: ${addEditProductState.price}")
//    }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding(),
             topBar = { TopBar(addEditProductState.name) }) { paddingValues ->
        Column(Modifier
                   .fillMaxSize()
                   .verticalScroll(scrollBehavior)
                   .padding(top = paddingValues.calculateTopPadding())
                   .padding(horizontal = paddingValues.calculateLeftPadding(LayoutDirection.Ltr))
                   .padding(horizontal = paddingValues.calculateLeftPadding(LayoutDirection.Rtl))
                   .padding(bottom = paddingValues.calculateBottomPadding())) {
            ImageCard(product?.image?.url, selectedImageUri) {
                photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
            TextFieldCard(stringResource(id = name_label), addEditProductState.name, setName)

            TextFieldCard(stringResource(id = label_label), addEditProductState.label, setLabel)
            TextFieldCard(stringResource(id = owner_label), addEditProductState.owner, setOwner)

            TextFieldCard(stringResource(id = year_label), addEditProductState.year, setYear)

            TextFieldCard(stringResource(id = model_label), addEditProductState.model, setModel)

            TextFieldCard(stringResource(id = description_label), addEditProductState.description, setDescription)

            ElevatedCard(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = origin_label))
                    Text(text = origin)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { isOriginExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                        }
                        DropdownMenu(expanded = isOriginExpanded, onDismissRequest = {
                            isOriginExpanded = false
                        }) {
                            paises.sorted().forEach { country ->
                                DropdownMenuItem(text = { Text(country) }, onClick = {
                                    origin = country
                                    setOrigin(country)
                                    isOriginExpanded = false
                                })
                            }
                        }
                    }
                }
            }

            // Category UI
            ElevatedCard(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                // Row for Group
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(group_label))
                    Text(text = group.name)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { areGroupsExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                        }
                        DropdownMenu(expanded = areGroupsExpanded, onDismissRequest = {
                            areGroupsExpanded = false
                        }) {
                            groups.forEach { g ->
                                DropdownMenuItem(text = { Text(g.name) }, onClick = {
                                    group = g
                                    domain = g.domains.firstOrNull() ?: Domain("", emptyList())
                                    subclass = Subclass("")
                                    setCategory(Category(group.name, domain.name, subclass.name))
                                    areGroupsExpanded = false
                                })
                            }
                        }
                    }
                }

                // Row for Domain
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(category_label))
                    Text(text = domain.name)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { areDomainsExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                        }
                        DropdownMenu(expanded = areDomainsExpanded, onDismissRequest = {
                            areDomainsExpanded = false
                        }) {
                            domains.forEach { d ->
                                DropdownMenuItem(text = { Text(d.name) }, onClick = {
                                    domain = d
                                    subclass = d.subclasses.firstOrNull() ?: Subclass("")
                                    setCategory(Category(group.name, domain.name, subclass.name))
                                    areDomainsExpanded = false
                                })
                            }
                        }
                    }
                }

                // Row for Subclass
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(subcategory_label))
                    Text(text = subclass.name)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { areSubclassesExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                        }
                        DropdownMenu(expanded = areSubclassesExpanded, onDismissRequest = {
                            areSubclassesExpanded = false
                        }) {
                            subclasses.forEach { s ->
                                DropdownMenuItem(text = { Text(s.name) }, onClick = {
                                    subclass = s
                                    setCategory(Category(group.name, domain.name, subclass.name))
                                    areSubclassesExpanded = false
                                })
                            }
                        }
                    }
                }
            }

            SmartDoubleTextFieldCard(label = stringResource(R.string.price_label),
                                     value = addEditProductState.price.amount, // This is your Double
                                     onValueChange = { newAmount ->
                                         setPrice(addEditProductState.price.copy(amount = newAmount))
                                     },
                                     keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))

            // Offer Section
            SectionView(title = stringResource(id = R.string.offer_active_label)) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)) {
                        Text(text = stringResource(id = R.string.offer_active_label), style = MaterialTheme.typography.bodyLarge)
                        Switch(checked = addEditProductState.price.offer.isActive, onCheckedChange = {
                            setPrice(addEditProductState.price.copy(offer = Offer(it, addEditProductState.price.offer.discount)))
                        })
                    }

                    AnimatedVisibility(addEditProductState.price.offer.isActive) {
                        IntTextFieldCardWithStepper(label = stringResource(R.string.enter_discount_label),
                                                    value = addEditProductState.price.offer.discount,
                                                    onValueChange = { newDiscount ->
                                                        setPrice(addEditProductState.price.copy(offer = Offer(true, newDiscount)))
                                                    },
                                                    valueRange = 0..50)
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Stock Section
            IntTextFieldCardWithStepper(label = stringResource(R.string.stock_label),
                                        value = addEditProductState.stock,
                                        onValueChange = { setStock(it) },
                                        valueRange = 0..100)



            ElevatedCard(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Button(onClick = { openBottomSheet = true }) {
                    Text(stringResource(id = add_information_label))
                }
            }

            InformationListView(informationResultStateList)

// Legal
            TextFieldCard(stringResource(id = warranty_information_label), addEditProductState.warranty.orEmpty(), setWarranty)
            TextFieldCard(stringResource(id = legal_information_label), addEditProductState.legal.orEmpty(), setLegal)

// Warning
            TextFieldCard(stringResource(id = warning_information_label), addEditProductState.warning.orEmpty(), setWarning)

            KeywordSection(addEditProductState, word, onWordChange = { word = it }, addKeyword, deleteKeyword)

            SubmitButton(mainImageHasChanged,
                         addEditProductState,
                         selectedImageUri,
                         context,
                         scope,
                         setImage,
                         informationResultStateList,
                         isUpdatingProduct,
                         addProduct,
                         updateProduct)
        }
    }

    if (openBottomSheet) {
        ModalBottomSheet(onDismissRequest = { openBottomSheet = false }, sheetState = bottomSheetState) {
            AddEditInformationView(onAddInformationResultStateButtonClick = {
//                Log.d(TAG, "AddEditProductView >> InformationResultState: $it")
                addInformationResultState(it)
                openBottomSheet = false
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(title = {
        Text(text = title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    })
}

@Composable
fun ImageCard(imageUrl: String?, selectedImageUri: Uri?, onClick: () -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .padding(vertical = six)
        .padding(horizontal = eleven),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(four)) {
        imageUrl?.let {
            AsyncImage(model = selectedImageUri ?: imageUrl,
                       contentDescription = null,
                       modifier = Modifier
                           .fillMaxWidth()
                           .clip(MaterialTheme.shapes.medium)
                           .padding(16.dp)
                           .clickable { onClick() },
                       contentScale = ContentScale.FillWidth)
        } ?: run {
            AsyncImage(model = selectedImageUri,
                       contentDescription = "",
                       modifier = Modifier
                           .fillMaxWidth()
                           .clip(MaterialTheme.shapes.medium)
                           .padding(16.dp)
                           .clickable {
                               onClick()
                           },
                       contentScale = ContentScale.FillWidth)
        }
    }
}

@Composable
fun TextFieldCard(label: String,
                  text: String,
                  onTextChanged: (String) -> Unit,
                  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                  onClick: (() -> Unit)? = null) {

    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = six)
        .padding(horizontal = eleven),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(four)) {
        TextField(value = text,
                  onValueChange = onTextChanged,
                  label = { Text(label) },
                  keyboardOptions = keyboardOptions,
                  modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SubmitButton(mainImageHasChanged: Boolean,
                 addEditProductState: AddEditProductState,
                 selectedImageUri: Uri?,
                 context: Context,
                 scope: CoroutineScope,
                 setImage: (Image) -> Unit,
                 informationResultStateList: List<InformationResultState>,
                 isUpdatingProduct: Boolean,
                 addProduct: (Product) -> Unit,
                 updateProduct: (Product) -> Unit) {
    var isProcessing by remember { mutableStateOf(false) }

    Button(onClick = {
        if (isProcessing) return@Button
        isProcessing = true
        if (!mainImageHasChanged) {
            scope.launch(Dispatchers.Main) {
                try {
                    val product = addEditProductState.toProduct(addEditProductState.image,
                                                                overview = informationResultStateList.map { it.toInformation() })
                    if (isUpdatingProduct) {
                        updateProduct(product)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "SubmitButton: Error")
                } finally {
                    isProcessing = false
                }
            }
        } else {
            selectedImageUri?.let { uri ->
                uploadImageToFirebase(context, context.contentResolver, uri = uri, path = "main") { imageInfo ->
                    val image = Image(path = imageInfo.path, url = imageInfo.url, belongs = false)

                    addEditProductState.image.path?.let { oldPath ->
                        scope.launch(Dispatchers.Main) {
                            try {
                                setImage(image)
                                delay(1000)
                                val product = addEditProductState.toProduct(image,
                                                                            overview = informationResultStateList.map { it.toInformation() })
                                if (isUpdatingProduct) {
                                    updateProduct(product)
                                    if (oldPath.isNotEmpty()) {
                                        FirebaseStorage.getInstance().reference.child(oldPath).delete().addOnSuccessListener {
                                            Log.d(TAG, "updateProduct: Image deleted")
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                Log.d(TAG, "SubmitButton: Error")
                            } finally {
                                isProcessing = false
                            }
                        }
                    } ?: run {
                        scope.launch(Dispatchers.Main) {
                            try {
                                setImage(image)
                                val product: Product = addEditProductState.toProduct(image = image,
                                                                                     overview = informationResultStateList.map { it.toInformation() })
                                if (!isUpdatingProduct) {
                                    addProduct(product)
                                }
                            } catch (e: Exception) {
                                Log.d(TAG, "SubmitButton: Error")
                            } finally {
                                isProcessing = false // Reset the processing state
                            }
                        }
                    }
                }
            } ?: run {
                isProcessing = false // Reset if no URI is selected
            }
        }
    }, enabled = !isProcessing // Disable the button when processing
    ) {
        if (!isProcessing) {
            Text(text = if (isUpdatingProduct) {
                stringResource(id = update_product_label)
            } else {
                stringResource(id = create_product_label)
            })
        } else {
            Text("Por favor espere")
        }
    }
}

@Composable
fun KeywordSection(addEditProductState: AddEditProductState,
                   word: String,
                   onWordChange: (String) -> Unit,
                   addKeyword: (String) -> Unit,
                   deleteKeyword: (Int) -> Unit) {
    Column {
        // Header
        Text(text = stringResource(id = R.string.keywords_label),
             style = MaterialTheme.typography.titleMedium,
             modifier = Modifier.padding(bottom = 8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {
            TextField(value = word,
                      onValueChange = onWordChange,
                      label = { Text(stringResource(id = R.string.enter_main_words)) },
                      modifier = Modifier
                          .weight(1f)
                          .padding(end = 8.dp))

            Button(onClick = {
                addKeyword(word)
                onWordChange("")
            }, enabled = word.isNotEmpty(), modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(stringResource(id = R.string.add_label))
            }
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(addEditProductState.keywords) { keyword ->
                val index = addEditProductState.keywords.indexOf(keyword)
                KeywordBubble(keyword = keyword) {
                    deleteKeyword(index)
                }
            }
        }

        // Footer
        Text(text = stringResource(id = R.string.add_main_words_footer),
             style = MaterialTheme.typography.labelSmall,
             modifier = Modifier.padding(top = 8.dp))
    }
}
