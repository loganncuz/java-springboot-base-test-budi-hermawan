package act.base.test.budi.hermawan.controller;

import act.base.test.budi.hermawan.payload.UserPayload;
import act.base.test.budi.hermawan.service.UsersService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Operation(summary = "users", description = "Find All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully retrieved list"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@Parameter(name = "max_records of parameters to be obtained.", required = false, example = "5")
                                          @RequestParam(value = "max_records", required = false) Integer maxRecords,
                                      @Parameter(name = "offset of parameters to be obtained.", required = false, example = "0")
                                          @RequestParam(value = "offset", required = false) Integer offset) throws Exception {
        return usersService.findUsers(maxRecords,offset);
    }

    @Operation(summary = "user", description = "Find User By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully retrieved User"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id,
                                     @Parameter(name = "max_records of parameters to be obtained.", required = false, example = "5")
                                     @RequestParam(value = "max_records", required = false) Integer maxRecords,
                                     @Parameter(name = "offset of parameters to be obtained.", required = false, example = "0")
                                     @RequestParam(value = "offset", required = false) Integer offset) throws Exception {
        return usersService.findUserById(id,maxRecords,offset);
    }

    @Operation(summary = "user", description = "Create New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully create user"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @PostMapping("/users")
    @Produces("application/json")
    public ResponseEntity<?> createUser(@RequestBody UserPayload body) throws Exception {
        return usersService.createUser(body);
    }

    @Operation(summary = "user", description = "Update User By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully update user"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @PutMapping("/users/{id}")
    @Produces("application/json")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id,@RequestBody UserPayload body) throws Exception {
        System.out.println("body :"+body.getSsn()+"|"+id);
        return usersService.updateUser(id,body);
    }

    @Operation(summary = "user settings", description = "Update User Settings By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully update user setting"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @PutMapping("/users/{id}/settings")
    @Produces("application/json")
    public ResponseEntity<?> updateUserSettings(@PathVariable("id") int id,@RequestBody List<Map<String, Object>> body) throws Exception {

        return usersService.updateUserSetting(id,body);
    }

    @Operation(summary = "user", description = "Deactivate User By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully deactivate user"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) throws Exception {
        return usersService.deleteUser(id);
    }

    @Operation(summary = "user", description = "Activate User Settings By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "201", description  = "Successfully activate user"),
            @ApiResponse(responseCode  = "404", description  = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode  = "409", description  = "Conflicting data (SSN not unique, etc) "),
            @ApiResponse(responseCode  = "422", description  = "Invalid request"),
            @ApiResponse(responseCode  = "500", description  = "Fatal error"),
    }
    )
    @PutMapping("/users/{id}/refresh")
    @Produces("application/json")
    public ResponseEntity<?> refreshUser(@PathVariable("id") int id) throws Exception {
        return usersService.refreshUser(id);
    }
}
