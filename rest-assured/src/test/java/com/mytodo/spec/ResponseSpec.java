package com.mytodo.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

/**
 * REST Assured Response Specification sınıfı
 * Ortak response konfigürasyonları için kullanılır
 */
public class ResponseSpec {

    private static ResponseSpecification baseResponseSpec;

    /**
     * Temel response specification'ı oluştur
     */
    public static ResponseSpecification getBaseResponseSpec() {
        if (baseResponseSpec == null) {
            baseResponseSpec = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .build();
        }
        return baseResponseSpec;
    }

    /**
     * Başarılı response specification'ı oluştur (200 OK)
     */
    public static ResponseSpecification getSuccessResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }

    /**
     * Created response specification'ı oluştur (201 Created)
     */
    public static ResponseSpecification getCreatedResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .build();
    }

    /**
     * Error response specification'ı oluştur (4xx, 5xx)
     */
    public static ResponseSpecification getErrorResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    /**
     * Temel response specification'ı sıfırla
     */
    public static void resetBaseResponseSpec() {
        baseResponseSpec = null;
    }

    /**
     * Tüm response specification'ları sıfırla
     */
    public static void resetAllSpecs() {
        resetBaseResponseSpec();
    }
} 