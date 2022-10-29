package com.kemaltalas.fakeshop.domain.implementation

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.domain.repository.FakeShopRepository
import com.kemaltalas.fakeshop.domain.repository.LocalDataSource
import com.kemaltalas.fakeshop.domain.repository.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class FakeShopRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : FakeShopRepository{
    override suspend fun gelAllProducts(): Resource<ArrayList<Product>> {
        return responseToProductListResult(remoteDataSource.getAllProducts())
    }

    override suspend fun getProduct(itemId: Int): Resource<Product> {
        return responseToProductResult(remoteDataSource.getProduct(itemId))
    }

    override suspend fun gelAllCategories(): Resource<ArrayList<String>> {
        return responseToCategoryResult(remoteDataSource.gelAllCategories())
    }

    override suspend fun getCategoryProducts(category: String): Resource<Product> {
        return responseToProductResult(remoteDataSource.getCategoryProducts(category))
    }

    override suspend fun addToCart(product: Product) {
        return localDataSource.addToCart(product)
    }

    override fun getCartItems(): LiveData<List<Product>> {
        return localDataSource.getCartItems()
    }

//    override suspend fun updateCartItems(product: Product) {
//        return
//    }

    override suspend fun deleteCartItems(product: Product) {
        return localDataSource.deleteCartItems(product)
    }

    override suspend fun addToFavorites(product: Product) {
        return localDataSource.addToFavorites(product)
    }

    override suspend fun deleteFavorites(product: Product) {
        return localDataSource.deleteFavoritesItem(product)
    }

    override fun getFavoriteItems(): LiveData<List<Product>> {
        return localDataSource.getFavoritesItems()
    }

    private fun responseToProductResult(response: Response<Product>) : Resource<Product>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = "Error In Product")
    }

    private fun responseToProductListResult(response: Response<ArrayList<Product>>) : Resource<ArrayList<Product>>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = "Error In Product List")
    }
    private fun responseToCategoryResult(response: Response<ArrayList<String>>) : Resource<ArrayList<String>>{
      if (response.isSuccessful){
          response.body()?.let {
              return Resource.Success(it)
          }
      }
        return Resource.Error(message = "Error In Categories")
    }


}