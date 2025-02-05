package com.lcwd.electronic_store.services;

import com.lcwd.electronic_store.dtos.PageableResponse;
import com.lcwd.electronic_store.dtos.ProductDto;
import com.lcwd.electronic_store.entities.Product;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    // update
    ProductDto update(ProductDto productDto, String productId);


    // delete
    void delete(String productId);

    // get single
    ProductDto get(String productId);

    // get all
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get all : live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    // search product
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    // create product with category

    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    // update category of product
    ProductDto updateCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getAllOfCategory(String categoryId);



}
