package com.uzikami.api.controller;

import com.uzikami.api.dao.ProductCategoryRepository;
import com.uzikami.api.dao.ProductRepository;
import com.uzikami.api.dto.ProductReturnDto;
import com.uzikami.api.entity.Product;
import com.uzikami.api.entity.ProductCategory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductReturnDto>> getAllProducts(){
       List<Product> products = productRepository.findAll();

       List<ProductReturnDto> result = products.stream().map(i -> new ProductReturnDto(i)).collect(Collectors.toList());

       return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductReturnDto> getProductById(@PathVariable("productId") Long productId){
        Product product = productRepository.findById(productId).get();

        return new ResponseEntity<>(new ProductReturnDto(product), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductReturnDto> addNewProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("unitPrice") String unitPrice,
            @RequestParam("brand") String brand,
            @RequestParam("unitsInStock") int unitsInStock,
            @RequestParam("productCategory") String productCategory,
            @RequestParam(value="productImage", required = false) MultipartFile productImage) throws IOException {

        String imageFileName="";
        if(productImage != null){
            String imageFileExt = FilenameUtils.getExtension((productImage.getOriginalFilename()));
            if(!Arrays.asList(MimeTypeUtils.IMAGE_JPEG_VALUE,
                    MimeTypeUtils.IMAGE_PNG_VALUE,
                    MimeTypeUtils.IMAGE_GIF_VALUE).contains(productImage.getContentType())){
                throw  new RuntimeException(productImage.getOriginalFilename()+ "is not an image file");

            }
            Path fileFolder= Paths.get("upload", "image");

            if(!Files.exists(fileFolder)){
                Files.createDirectories(fileFolder);
            }

            imageFileName = UUID.randomUUID().toString()+'.'+ imageFileExt;
            Path filePath= fileFolder.resolve(imageFileName);
            Files.copy(productImage.getInputStream(),filePath);
        }

        ProductCategory category = productCategoryRepository.findByCategoryName(productCategory);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setUnitPrice( new BigDecimal(unitPrice));
        product.setBrand(brand);
        product.setUnitsInStock(unitsInStock);
        product.setCategory(category);
        product.setImageUrl(imageFileName);

        productRepository.save(product);

        return new ResponseEntity<>( new ProductReturnDto(product), HttpStatus.OK);
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable("productId") Long productId){
        Product product = productRepository.findById(productId).get();
        productRepository.delete(product);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("update/{productId}")
    public ResponseEntity<ProductReturnDto> updateProduct(@PathVariable("productId") Long productId,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("unitPrice") String unitPrice,
                                                          @RequestParam("brand") String brand,
                                                          @RequestParam("unitsInStock") int unitsInStock,
                                                          @RequestParam("productCategory") String productCategory,
                                                          @RequestParam(value="productImage", required = false) MultipartFile productImage) throws IOException{

        String imageFileName="";
        System.out.println(productImage.getOriginalFilename());
        if(productImage != null){
            String imageFileExt = FilenameUtils.getExtension((productImage.getOriginalFilename()));
            if(!Arrays.asList(MimeTypeUtils.IMAGE_JPEG_VALUE,
                    MimeTypeUtils.IMAGE_PNG_VALUE,
                    MimeTypeUtils.IMAGE_GIF_VALUE).contains(productImage.getContentType())){
                throw  new RuntimeException(productImage.getOriginalFilename()+ "is not an image file");

            }
            Path fileFolder= Paths.get("upload", "image");

            if(!Files.exists(fileFolder)){
                Files.createDirectories(fileFolder);
            }
            System.out.println(imageFileExt);
            imageFileName = UUID.randomUUID().toString()+'.'+ imageFileExt;
            Path filePath= fileFolder.resolve(imageFileName);
            Files.copy(productImage.getInputStream(),filePath);
        }

        ProductCategory category = productCategoryRepository.findByCategoryName(productCategory);

        Product product = productRepository.findById(productId).get();
        System.out.println(imageFileName);

        product.setName(name);
        product.setDescription(description);
        product.setUnitPrice( new BigDecimal(unitPrice));
        product.setBrand(brand);
        product.setUnitsInStock(unitsInStock);
        product.setCategory(category);
        product.setImageUrl(imageFileName);

        productRepository.save(product);
        return new ResponseEntity<>( new ProductReturnDto(product), HttpStatus.OK);
    }
}
