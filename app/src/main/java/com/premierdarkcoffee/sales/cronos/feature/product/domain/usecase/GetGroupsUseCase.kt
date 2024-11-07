package com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase

import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.DataServiceable
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(private val dataServiceable: DataServiceable) {

    operator fun invoke(
        url: String,
    ): Flow<Result<List<Group>>> {
        return dataServiceable.getGroups(url)
    }
}
