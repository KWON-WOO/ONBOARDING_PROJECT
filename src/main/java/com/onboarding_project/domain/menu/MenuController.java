package com.onboarding_project.domain.menu;

import com.onboarding_project.common.CommonResponse;
import com.onboarding_project.common.JwtUtil;
import com.onboarding_project.common.SuccessMessage;
import com.onboarding_project.common.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final JwtUtil jwtUtil;

    @PostMapping("/menu")
    public ResponseEntity<CommonResponse<MenuCreateResponse>> createMenu(@RequestHeader(JwtUtil.AUTHORIZATION_HEADER) String bearerToken,
                                                                         @Valid @RequestBody MenuCreateRequest request) {
        String token = jwtUtil.substringToken(bearerToken);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        String username = claims.getSubject();
        UserRole role = jwtUtil.getUserRole(claims);

        MenuCreateResponse response = menuService.createMenu(username, role, request);

        CommonResponse<MenuCreateResponse> result = CommonResponse.success(SuccessMessage.MENU_CREATE_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

//    @GetMapping("/menu")
//    public ResponseEntity<CommonResponse<GetMenuResponse>> getMenu(@RequestParam Integer request) {
//        GetMenuResponse response = menuService.getMenu(request);
//
//        CommonResponse<GetMenuResponse> result = CommonResponse.success(SuccessMessage.MENU_GET_SUCCESS, response);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
//
//    @GetMapping("/menu/list")
//    public ResponseEntity<CommonResponse<GetMenuListResponse>> getMenuList(@Valid @RequestBody GetMenuListRequest request) {
//        GetMenuListResponse response = menuService.getMenuList(request);
//
//        CommonResponse<GetMenuListResponse> result = CommonResponse.success(SuccessMessage.MENU_LIST_GET_SUCCESS, response);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
//
//    @PatchMapping("/menu")
//    public ResponseEntity<CommonResponse<UpdateMenuResponse>> updateMenu(UpdateMenuRequest request) {
//        UpdateMenuResponse response = menuService.updateMenu(request);
//
//        CommonResponse<UpdateMenuResponse> result = CommonResponse.success(SuccessMessage.MENU_UPDATE_SUCCESS, response);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
//
//    @PostMapping("/menu/delete")
//    public ResponseEntity<CommonResponse<Void>> deleteMenu(DeleteMenuRequest request) {
//        menuService.deleteMenu(request);
//
//        CommonResponse<Void> result = CommonResponse.successNoData(SuccessMessage.MENU_DELETE_SUCCESS);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
//    }

}
