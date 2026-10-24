package ecommerce.backend.controller;

import ecommerce.backend.constant.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping
    public Map<String ,Object> getAllEnums(){

        return Map.of(
                "category", CategoryStatus.values(),
                "subCategory", SubCategoryStatus.values(),
                "brands", BrandStatus.values(),
                "sellerApplicationStatus", SellerApplicationStatus.values(),
                "users", UserStatus.values()

        );

    }
}
