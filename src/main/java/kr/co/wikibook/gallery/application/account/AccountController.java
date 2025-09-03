package kr.co.wikibook.gallery.application.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.application.account.etc.AccountConstants;
import kr.co.wikibook.gallery.application.account.model.AccountJoinReq;
import kr.co.wikibook.gallery.application.account.model.AccountLoginReq;
import kr.co.wikibook.gallery.application.account.model.AccountLoginRes;
import kr.co.wikibook.gallery.application.account.model.VerifyCodeReq;
import kr.co.wikibook.gallery.config.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinReq req) {
        if (!StringUtils.hasLength(req.getName())
            || !StringUtils.hasLength(req.getLoginId())
            || !StringUtils.hasLength(req.getLoginPw())) {
            return ResponseEntity.badRequest().build();
        }

        Integer result = accountService.join(req);
        return ResponseEntity.ok(result != null ? result : "이메일 인증을 하십시오.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountLoginReq req, HttpServletRequest httpReq) {
        AccountLoginRes result = accountService.login(req);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        // 세션 처리
        HttpUtils.setSession(httpReq, AccountConstants.MEMBER_ID_NAME, result.getId());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest httpReq) {
        Integer id = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("check id={}", id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpReq) {
        HttpUtils.removeSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        return ResponseEntity.ok(1);
    }

    // 카카오 로그인
    @GetMapping("/login/kakao")
    public void kakaoLogin(@RequestParam String code, HttpServletRequest httpReq, HttpServletResponse HttpRes) throws IOException {
        log.info("code: {}", code);
        int result = accountService.kakaoLogin(code);
        log.info("result: {}", result);

        // 세션 처리
        HttpUtils.setSession(httpReq, AccountConstants.MEMBER_ID_NAME, result);

        HttpRes.sendRedirect("http://localhost:5173/login-success?memberId=" + result);
    }

    // 네이버 로그인
    @GetMapping("/login/naver")
    public void naverLogin(@RequestParam String code, HttpServletRequest httpReq, HttpServletResponse HttpRes) throws IOException {
        log.info("code: {}", code);
        int result = accountService.naverLogin(code);
        log.info("result: {}", result);

        // 세션 처리
        HttpUtils.setSession(httpReq, AccountConstants.MEMBER_ID_NAME, result);

        HttpRes.sendRedirect("http://localhost:5173/login-success?memberId=" + result);
    }

    // 이메일 인증 번호 발송
    @PostMapping("/mail/send")
    public ResponseEntity<?> sendMail(@RequestParam String email) {
        log.info("email: {}", email);
        Integer result = accountService.sendVerifyCode(email);

        return ResponseEntity.ok(result != null && result == 1 ? "인증 번호가 전송되었습니다." : "인증 번호를 전송하는 데 실패했습니다.");
    }

    // 이메일 인증 번호 검증
    @PostMapping("/mail/check")
    public ResponseEntity<?> checkVerifyCode(@RequestBody VerifyCodeReq req) {
        log.info("verifyCode req: {}", req);
        Integer result = accountService.checkVerifyCode(req);

        return ResponseEntity.ok(result != null && result == 1 ? "이메일 인증에 성공하였습니다." : "잘못된 인증 번호입니다.");
    }
}
