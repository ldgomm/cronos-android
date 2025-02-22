package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.Codes
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Category
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Specifications
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.ProductsState
import com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase.AddProductUseCase
import com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase.GetGroupsUseCase
import com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase.GetProductsUseCase
import com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase.UpdateProductUseCase
import com.premierdarkcoffee.sales.cronos.util.function.getUrlFor
import com.premierdarkcoffee.sales.cronos.util.key.getCronosKey
import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PutProductRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(application: Application,
                                           private val getProductsUseCase: GetProductsUseCase,
                                           private val addEditedProductUseCase: AddProductUseCase,
                                           private val updateProductUseCase: UpdateProductUseCase,
                                           private val getGroupsUseCase: GetGroupsUseCase) : AndroidViewModel(application) {

    // Store key associated with the application instance
    private val cronosKey = getCronosKey(getApplication<Application>().applicationContext)

    // StateFlow to manage the list of products and their loading state
    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState

    // StateFlow to manage the list of products and their loading state
    private var _allProductsState = MutableStateFlow(ProductsState())

    // StateFlow to manage the Product and its loading state
    private var _addEditProductState = MutableStateFlow(AddEditProductState())
    var addEditProductState: StateFlow<AddEditProductState> = _addEditProductState

    // StateFlow to hold a list of information results related to a product
    private val _informationResultStateList = MutableStateFlow<List<InformationResultState>>(emptyList())
    val informationResultStateList: StateFlow<List<InformationResultState>> = _informationResultStateList.asStateFlow()

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    // StateFlow to manage the search text input
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun initData(apiKey: String) {
        getProducts(apiKey)
        observeSearchTextChanges(apiKey)
        getGroups()
    }

    private fun getGroups() {
        val url = getUrlFor(endpoint = "data/groups")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getGroupsUseCase(url).collect { result ->
                    result.onSuccess { groups ->
                        _groups.value = groups
                    }.onFailure { exception ->
                        Log.e(TAG, "Error in getting groups: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getting groups: ${e.message}")
            }
        }
    }

    /**
     * Observes changes in the search text input and triggers a product search
     * if the text meets the required criteria.
     *
     * @param storeId The ID of the store associated with the search.
     */
    private fun observeSearchTextChanges(apiKey: String) {
        viewModelScope.launch {
            _searchText.collect { searchStoreProduct(text = it, apiKey) }
        }
    }

    /**
     * Triggers a search for products in the store based on the search text.
     *
     * @param storeId The ID of the store to search within.
     * @param text The search text input.
     */
    private fun searchStoreProduct(text: String, apiKey: String) {
        if (text.isEmpty()) {
            // When the search text is empty, display the original product list
            getProducts(apiKey)
        } else {
            // Perform the local search using regex
            getProductByKeywords(text)
        }
    }


    /**
     * Fetches products from the store or all stores if no storeId is provided.
     *
     * @param storeId The ID of the store to fetch products from, or null to fetch all products.
     */
    private fun getProducts(apiKey: String) {
        executeProductSearch(apiKey = apiKey)
    }

    fun onRefresh(apiKey: String) {
        _productsState.update { state ->
            state.copy(products = emptyList())
        }
        executeProductSearch(apiKey = apiKey)
    }

    /**
     * Call this once you’ve fetched products from your API.
     */
    private fun executeProductSearch(apiKey: String) {
        val url = getUrlFor(endpoint = "cronos-brandoun-products")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getProductsUseCase(url, apiKey).collect { result ->
                    result.onSuccess { products ->
                        // Map your domain objects to the Product model if needed
                        val mappedAndSorted = products.map { it.toProduct() }.sortedByDescending { it.date }

                        // 1) Store them in the "all" state
                        _allProductsState.update { state ->
                            state.copy(products = mappedAndSorted)
                        }

                        // 2) Also make them the "current/filtered" list by default
                        _productsState.update { state ->
                            state.copy(products = mappedAndSorted)
                        }

                    }.onFailure { exception ->
                        Log.e(TAG, "Error in product search: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in executeProductSearch: ${e.message}")
            }
        }
    }

    /**
     * Resets the current/filtered list back to the full list.
     */
    private fun restoreAllProducts() {
        val allProducts = _allProductsState.value.products
        _productsState.update { state ->
            // If `allProducts` is null, fall back to emptyList()
            state.copy(products = allProducts ?: emptyList())
        }
    }

    /**
     * Updates the search text value.
     *
     * @param text The new search text input.
     */
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    /**
     * Clears the current search text.
     */
    fun clearSearchText(apiKey: String) {
        _searchText.value = ""
        getProducts(apiKey) // Restore the original product list
    }

    /**
     * Filters the product list using “digit check” logic:
     *   - If the search starts with a digit => treat the entire string as one term.
     *   - Otherwise => split by whitespace into multiple terms.
     * Then a product must match **all** terms (name, label, owner, year, or model).
     *
     * Note: Model is always searched now (no more "Ferretería" check).
     * Also, model is “space-normalized” so that strings like "0 601 5B3" can be matched.
     */
    private fun getProductByKeywords(searchText: String) {
        val trimmed = searchText.trim()
        if (trimmed.isEmpty()) {
            restoreAllProducts()
            return
        }

        // 1) Single term if it starts with a digit; otherwise split
        val firstCharIsDigit = trimmed.firstOrNull()?.isDigit() ?: false
        val terms = if (firstCharIsDigit) {
            listOf(trimmed) // one term
        } else {
            trimmed.split("\\s+".toRegex()) // multiple terms
        }

        // 2) Filter from the full list
        val fullList = _allProductsState.value.products ?: emptyList()
        val filtered = fullList.filter { product ->
            // A product must match *all* terms
            terms.all { term -> productMatchesTerm(product, term) }
        }

        // 3) Publish the filtered list
        _productsState.value = ProductsState(products = filtered)
    }

    /**
     * Returns true if [product] matches [term] in:
     *   - name, label, owner, year, OR
     *   - model (with spaces removed),
     * using case-insensitive matching.
     */
    private fun productMatchesTerm(product: Product, term: String): Boolean {
        // We'll do a normal regex for the plain fields
        val normalRegex = Regex(Regex.escape(term), RegexOption.IGNORE_CASE)

        // Check name, label, owner, year “as is”
        val basicFields = buildList {
            add(product.name)
            product.label?.let { add(it) }
            product.owner?.let { add(it) }
            product.year?.let { add(it) }
        }

        // If any basic field matches the term, we’re good
        if (basicFields.any { field -> normalRegex.containsMatchIn(field) }) {
            return true
        }

        // Otherwise, let's check the model with space normalization
        val normalizedTerm = term.lowercase().replace(" ", "")
        val normalizedModel = product.model.lowercase().replace(" ", "")
        val modelRegex = Regex(Regex.escape(normalizedTerm), RegexOption.IGNORE_CASE)

        return modelRegex.containsMatchIn(normalizedModel)
    }

    /**
     * Sets the current product to the provided product and updates the corresponding states.
     *
     * @param product The product to set.
     */
    fun setProduct(product: Product) {
        viewModelScope.launch {
            setId(product.id)
            setName(product.name)
            product.label?.let { setLabel(it) }
            product.owner?.let { setOwner(it) }
            product.year?.let { setYear(it) }
            setModel(product.model)
            setDescription(product.description)
            setCategory(Category(product.category.group, product.category.domain, product.category.subclass))
            setPrice(product.price)
            setStock(product.stock)
            setImage(product.image)
            setOrigin(product.origin)
            setDate(product.date)
            product.overview.forEach { result ->
                result.image.path?.let { path ->
                    val informationResult = InformationResultState(id = result.id,
                                                                   title = result.title,
                                                                   subtitle = result.subtitle,
                                                                   description = result.description,
                                                                   path = path,
                                                                   url = result.image.url,
                                                                   belongs = result.image.belongs,
                                                                   place = result.place)
                    addInformationResultState(informationResult)
                }
            }
            product.keywords?.let { setKeywords(it) }
            product.codes?.let { setCodes(it) }
            setSpecifications(product.specifications)
            setWarranty(product.warranty)
            setLegal(product.legal)
            setWarning(product.warning)
        }
    }

    /**
     * Sets the product ID in the add/edit product state.
     *
     * @param id The product ID.
     */
    private fun setId(id: String) {
        _addEditProductState.value = _addEditProductState.value.copy(id = id)
    }

    /**
     * Sets the product name in the add/edit product state.
     *
     * @param name The product name.
     */
    fun setName(name: String) {
        _addEditProductState.value = _addEditProductState.value.copy(name = name)
    }

    fun setLabel(label: String) {
        _addEditProductState.value = _addEditProductState.value.copy(label = label)
    }

    fun setOwner(owner: String) {
        _addEditProductState.value = _addEditProductState.value.copy(owner = owner)
    }

    fun setYear(year: String) {
        _addEditProductState.value = _addEditProductState.value.copy(year = year)
    }

    /**
     * Sets the product model in the add/edit product state.
     *
     * @param model The product model.
     */
    fun setModel(model: String) {
        _addEditProductState.value = _addEditProductState.value.copy(model = model)
    }

    /**
     * Sets the product description in the add/edit product state.
     *
     * @param description The product description.
     */
    fun setDescription(description: String) {
        _addEditProductState.value = _addEditProductState.value.copy(description = description)
    }

    /**
     * Sets the product category in the add/edit product state.
     *
     * @param category The product category.
     */
    fun setCategory(category: Category) {
        _addEditProductState.value = _addEditProductState.value.copy(category = category)
    }

    /**
     * Sets the product price in the add/edit product state.
     *
     * @param price The product price.
     */
    fun setPrice(price: Price) {
        _addEditProductState.value = _addEditProductState.value.copy(price = price)
    }

    /**
     * Sets the product stock quantity in the add/edit product state.
     *
     * @param stock The product stock quantity.
     */
    fun setStock(stock: Int) {
        _addEditProductState.value = _addEditProductState.value.copy(stock = stock)
    }

    /**
     * Sets the product image in the add/edit product state.
     *
     * @param image The product image.
     */
    fun setImage(image: Image) {
        _addEditProductState.value = _addEditProductState.value.copy(image = image)
    }

    /**
     * Sets the product origin in the add/edit product state.
     *
     * @param origin The product origin.
     */
    fun setOrigin(origin: String) {
        _addEditProductState.value = _addEditProductState.value.copy(origin = origin)
    }

    /**
     * Sets the product date in the add/edit product state.
     *
     * @param date The product date.
     */
    private fun setDate(date: Long) {
        _addEditProductState.value = _addEditProductState.value.copy(date = date)
    }

    /**
     * Sets the product keywords in the add/edit product state.
     *
     * @param keywords The product keywords.
     */
    private fun setKeywords(keywords: List<String>) {
        _addEditProductState.value = _addEditProductState.value.copy(keywords = keywords.toMutableList())
    }

    private fun setCodes(codes: Codes) {
        _addEditProductState.value = _addEditProductState.value.copy(codes = codes)
    }

    fun addKeyword(keyword: String) {
        val updatedKeywords = _addEditProductState.value.keywords.toMutableList().apply {
            add(keyword)
        }
        _addEditProductState.value = _addEditProductState.value.copy(keywords = updatedKeywords)
    }

    fun deleteKeyword(index: Int) {
        val updatedKeywords = _addEditProductState.value.keywords.toMutableList().apply {
            removeAt(index)
        }
        _addEditProductState.value = _addEditProductState.value.copy(keywords = updatedKeywords)
    }

    /**
     * Sets the product specifications in the add/edit product state.
     *
     * @param specifications The product specifications, if any.
     */
    private fun setSpecifications(specifications: Specifications?) {
        _addEditProductState.value = _addEditProductState.value.copy(specifications = specifications)
    }

    /**
     * Sets the product warranty information in the add/edit product state.
     *
     * @param warranty The product warranty information, if any.
     */
    fun setWarranty(warranty: String?) {
        _addEditProductState.value = _addEditProductState.value.copy(warranty = warranty)
    }

    /**
     * Sets the product legal information in the add/edit product state.
     *
     * @param legal The product legal information, if any.
     */
    fun setLegal(legal: String?) {
        _addEditProductState.value = _addEditProductState.value.copy(legal = legal)
    }

    /**
     * Sets the product warning information in the add/edit product state.
     *
     * @param warning The product warning information, if any.
     */
    fun setWarning(warning: String?) {
        _addEditProductState.value = _addEditProductState.value.copy(warning = warning)
    }

    /**
     * Adds an information result to the list of information result states.
     *
     * @param informationResult The information result to add.
     */
    fun addInformationResultState(informationResult: InformationResultState) {
        _informationResultStateList.value += informationResult
    }

    /**
     * Adds a new product to the store.
     *
     * @param product The product to add.
     * @param onSuccess A callback invoked upon successful addition.
     * @param onFailure A callback invoked when the addition fails.
     */
    fun addProduct(product: Product, apiKey: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addEditedProductUseCase(getUrlFor(endpoint = "cronos-products"),
                                        PostProductRequest(key = cronosKey, product = product.toProductDto()),
                                        apiKey).collect { result ->
                    result.onSuccess {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }.onFailure {
                        withContext(Dispatchers.Main) {
                            onFailure()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    /**
     * Updates an existing product in the store.
     *
     * @param product The product to update.
     * @param onSuccess A callback invoked upon successful update.
     * @param onFailure A callback invoked when the update fails.
     */
    fun updateProduct(product: Product, apiKey: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateProductUseCase(getUrlFor(endpoint = "cronos-products"),
                                     PutProductRequest(key = cronosKey, product = product.toProductDto()),
                                     apiKey).collect { result ->
                    result.onSuccess {
                        withContext(Dispatchers.Main) {
                            Log.d(TAG, "updateProduct: Product updated")
                            onSuccess()
                        }
                    }.onFailure {
                        withContext(Dispatchers.Main) {
                            onFailure()
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }
}
