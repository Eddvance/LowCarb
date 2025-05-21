package io.eddvance.production.low_carb.controller.api;

import io.eddvance.production.low_carb.dto.user_response_dto.UserResponseDto;
import io.eddvance.production.low_carb.service.LowCarbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class LowCarbController {

    public LowCarbController(LowCarbService lowCarbService) {
        this.lowCarbService = lowCarbService;
    }

    private final LowCarbService lowCarbService;

    @GetMapping("/lowcarb")
    public ResponseEntity<UserResponseDto> getConsumptionEstimate(
            @RequestParam("email") String email,
            @RequestParam("ratingRequest") Double ratingRequest) {

        // return ResponseEntity.ok(lowCarbService.??(email, ratingRequest));
    }

    //@PostMapping("/lowcarb/save")
    //public ResponseEntity<ConsumptionResponse> saveConsumption(@RequestBody ConsumptionRequest request) {
    //    return ResponseEntity.ok(lowCarbService.saveConsumptionData(request));
    // }
}