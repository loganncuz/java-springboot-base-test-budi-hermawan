package act.base.test.budi.hermawan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Operation(summary = "users", description = "Find All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Successfully retrieved list"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() throws Exception {
        return null;
    }
}
