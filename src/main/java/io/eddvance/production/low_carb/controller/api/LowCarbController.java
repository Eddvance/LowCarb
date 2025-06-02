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

  //  private final LowCarbService lowCarbService;

   // public LowCarbController(LowCarbService lowCarbService) {
    //    this.lowCarbService = lowCarbService;
  //  }

   /* @GetMapping("/lowcarb")
    public ResponseEntity<UserResponseDto> getConsumptionEstimate(
            @RequestParam("email") String email,
            @RequestParam("ratingRequest") Double ratingRequest) {

        UserResponseDto response = lowCarbService.rating(email, ratingRequest);
        return ResponseEntity.ok(response);
    }
*/
}