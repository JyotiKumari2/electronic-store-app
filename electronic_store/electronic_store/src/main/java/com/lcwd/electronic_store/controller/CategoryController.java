package com.lcwd.electronic_store.controller;

import com.lcwd.electronic_store.dtos.*;
import com.lcwd.electronic_store.services.CategoryService;
import com.lcwd.electronic_store.services.FileService;
import com.lcwd.electronic_store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        // call service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto){

        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is deleted successfully!!").httpStatus(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){

        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    // get single
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    // create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto
    ){
        ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    // update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ){
     ProductDto productDto = productService.updateCategory(productId, categoryId);
     return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // upload category image
@PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException {

    String imageName = fileService.uploadFile(image, imageUploadPath);
    CategoryDto category = categoryService.get(categoryId);
    category.setCoverImage(imageName);
    CategoryDto categoryDto = categoryService.update(category, categoryId);
    ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
}

// serve category image
    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.get(categoryId);
        logger.info("Category image name: {}", category.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}

