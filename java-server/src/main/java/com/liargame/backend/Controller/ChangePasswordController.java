package com.liargame.backend.Controller;

import com.liargame.backend.Service.CommonLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/changepw")
@Tag(name = "Change Password API", description = "비밀번호 변경 기능")
@RequiredArgsConstructor
public class ChangePasswordController {
    private final CommonLoginService commonLoginService;

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호를 확인 후 새 비밀번호로 변경합니다.")
    @PostMapping
    public ResponseEntity<String> changePassword(
            @RequestParam String phoneNumber,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        commonLoginService.changePassword(phoneNumber, currentPassword, newPassword);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}
