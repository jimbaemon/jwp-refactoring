package kitchenpos.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import kitchenpos.product.domain.Product;
import org.springframework.http.MediaType;

public class ProductFixture {

    public static ExtractableResponse<Response> 상품_등록(String name, BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return RestAssured
            .given().log().all()
            .body(product)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/products")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회() {

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/products")
            .then().log().all()
            .extract();
    }
}
