package com.example.backend.product.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.product.dto.ProductCreateRequest;
import com.example.backend.product.dto.ProductDetailResponse;
import com.example.backend.product.dto.ProductImageRequest;
import com.example.backend.product.dto.ProductImageResponse;
import com.example.backend.product.dto.ProductResponse;
import com.example.backend.product.dto.ProductVariantRequest;
import com.example.backend.product.dto.ProductVariantResponse;
import com.example.backend.product.entity.Product;
import com.example.backend.product.entity.ProductImage;
import com.example.backend.product.entity.ProductStatus;
import com.example.backend.product.entity.ProductVariant;
import com.example.backend.product.repository.ProductImageRepository;
import com.example.backend.product.repository.ProductRepository;
import com.example.backend.product.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final ProductRepository productRepository;
        private final ProductVariantRepository variantRepository;
        private final ProductImageRepository imageRepository;

        /*
         * =========================
         * Product
         * =========================
         */

        public Long createProduct(ProductCreateRequest request) {

                Product product = Product.create(
                                request.getName(),
                                request.getDescription());

                return productRepository.save(product).getId();
        }

        // 取得所有 ACTIVE 商品（回傳 Entity）
        public List<Product> listActiveProducts() {
                return productRepository.findByStatus(ProductStatus.ACTIVE);
        }

        // 取得所有 ACTIVE 商品（回傳 DTO）
        public List<ProductResponse> getProducts() {
                return listActiveProducts()
                                .stream()
                                .map(ProductResponse::fromEntity)
                                .toList();
        }

        public List<ProductResponse> getArchivedProducts() {
                return productRepository.findByStatus(ProductStatus.ARCHIVED)
                                .stream()
                                .map(ProductResponse::fromEntity)
                                .toList();
        }

        public ProductDetailResponse getProductDetail(Long productId) {

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));

                if (!product.getStatus().isVisible()) {
                        throw new IllegalStateException("商品目前未上架");
                }

                List<ProductVariantResponse> variants = variantRepository
                                .findByProductId(productId)
                                .stream()
                                .map(ProductVariantResponse::fromEntity)
                                .toList();

                List<ProductImageResponse> images = imageRepository
                                .findByProductIdOrderBySortOrderAsc(productId)
                                .stream()
                                .map(i -> new ProductImageResponse(
                                                i.getImageUrl(),
                                                i.getSortOrder()))
                                .toList();

                return new ProductDetailResponse(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                variants,
                                images);
        }

        /*
         * =========================
         * Variant
         * =========================
         */

        public void addVariant(Long productId, ProductVariantRequest request) {

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));

                if (!product.getStatus().isEditable()) {
                        throw new IllegalStateException("此商品狀態不可新增規格");
                }

                if (variantRepository.findBySku(request.getSku()).isPresent()) {
                        throw new IllegalStateException("SKU 已存在");
                }

                ProductVariant variant = ProductVariant.create(
                                productId,
                                request.getSku(),
                                request.getVariantName(),
                                request.getPrice(),
                                request.getStockQuantity());

                variantRepository.save(variant);
        }

        /*
         * =========================
         * Image
         * =========================
         */

        public void addImage(Long productId, ProductImageRequest request) {

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));

                if (!product.getStatus().isEditable()) {
                        throw new IllegalStateException("此商品狀態不可新增圖片");
                }

                ProductImage image = ProductImage.create(
                                productId,
                                request.getImageUrl(),
                                request.getSortOrder());

                imageRepository.save(image);
        }

        /*
         * =========================
         * Status
         * =========================
         */

        @Transactional
        public void activateProduct(Long productId) {

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));

                product.activate();
        }

        @Transactional
        public void deactivateProduct(Long productId) {

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));

                product.deactivate();
        }

        @Transactional
        public Product archiveProduct(Long productId) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("找不到商品"));

                if (product.getStatus() == ProductStatus.ARCHIVED) {
                        throw new IllegalStateException("商品已封存");
                }

                product.archive();
                return product;
        }

        @Transactional
        public Product unarchiveProduct(Long productId) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("找不到商品：" + productId));

                if (product.getStatus() != ProductStatus.ARCHIVED) {
                        throw new IllegalStateException("只能解除封存 ARCHIVED 的商品");
                }

                product.unarchive();
                return product;
        }

        @Transactional
        public ProductVariantResponse disableVariant(Long variantId) {
                ProductVariant variant = variantRepository.findById(variantId)
                                .orElseThrow(() -> new NoSuchElementException("找不到商品變體：" + variantId));

                variant.disable();

                variantRepository.save(variant);

                return ProductVariantResponse.fromEntity(variant);
        }
}
