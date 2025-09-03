package kr.co.wikibook.gallery.application.account;

import jakarta.mail.internet.MimeMessage;
import kr.co.wikibook.gallery.application.account.etc.AuthCode;
import kr.co.wikibook.gallery.application.account.etc.MailCheck;
import kr.co.wikibook.gallery.application.account.model.*;
import kr.co.wikibook.gallery.config.constants.ConstKakaoLogin;
import kr.co.wikibook.gallery.config.constants.ConstNaver;
import kr.co.wikibook.gallery.openfeign.account.KakaoTokenFeignClient;
import kr.co.wikibook.gallery.openfeign.account.KakaoUserFeignClient;
import kr.co.wikibook.gallery.openfeign.account.NaverTokenFeignClient;
import kr.co.wikibook.gallery.openfeign.account.NaverUserFeignClient;
import kr.co.wikibook.gallery.openfeign.account.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;

    private final KakaoTokenFeignClient kakaoTokenFeignClient;
    private final KakaoUserFeignClient kakaoUserFeignClient;
    private final NaverTokenFeignClient naverTokenFeignClient;
    private final NaverUserFeignClient naverUserFeignClient;
    private final ConstKakaoLogin constKakao;
    private final ConstNaver constNaver;

    private final JavaMailSender mailSender;
    public static Map<String, String> codes = new HashMap<>();
    public static Map<String, Boolean> mailChecked = new HashMap<>();

    public Integer join(AccountJoinReq req) {
        String hashedPw = BCrypt.hashpw(req.getLoginPw(), BCrypt.gensalt());

        // 암호화가 된 비밀번호를 갖는 AccountJoinReq 객체를 만들어주세요. (아이디, 이름도 갖고 있고)
        AccountJoinReq changedReq = AccountJoinReq.builder()
                .name(req.getName())
                .loginId(req.getLoginId())
                .loginPw(hashedPw)
                .build();

        if (!mailChecked.getOrDefault(req.getLoginId(), false)) {
            return null;
        }

        return accountMapper.save(changedReq);
    }

    public AccountLoginRes login(AccountLoginReq req) {
        AccountLoginRes res = accountMapper.findByLoginId(req);

        // 비밀번호 체크
        if (res == null || !BCrypt.checkpw(req.getLoginPw(), res.getLoginPw())) {
            return null; // 아이디가 없거나 비밀번호가 다르면 return null; 처리
        }

        return res;
    }

    @Transactional
    public Integer kakaoLogin(String code) {
        KakaoTokenReq req = new KakaoTokenReq("authorization_code", constKakao.getAppKey(), constKakao.getRedirectUri(), code);
        log.info("kakao login req: {}", req);
        KakaoTokenRes kakaoToken = kakaoTokenFeignClient.getToken(req);
        log.info("kakao token: {}", kakaoToken.getAccessToken());
        KakaoUserRes kakaoUser = kakaoUserFeignClient.getUser(String.format("Bearer %s", kakaoToken.getAccessToken()));
        log.info("kakao user: {}", kakaoUser);

        SocialLoginDto dto = SocialLoginDto.builder()
                .socialId(kakaoUser.getId().toString())
                .loginId(kakaoUser.getKakaoAccount().getEmail())
                .loginType("KAKAO")
                .build();
        log.info("social login dto: {}", dto);

        Integer id = accountMapper.findBySocialIdAndLoginIdAndLoginType(dto);
        log.info("user id: {}", id);

        if (id == null) {
            AccountJoinReq joinReq = AccountJoinReq.builder()
                    .name(kakaoUser.getKakaoAccount().getProfile().getNickname())
                    .loginId(dto.getLoginId())
                    .loginType("KAKAO")
                    .socialId(dto.getSocialId())
                    .build();

            accountMapper.save(joinReq);

            return joinReq.getId();
        }

        return id;
    }

    @Transactional
    public Integer naverLogin(String code) {
        NaverTokenReq req = new NaverTokenReq("authorization_code", constNaver.getAppKey(), constNaver.getSecretKey(), code);
        log.info("naver login req: {}", req);
        NaverTokenRes naverToken = naverTokenFeignClient.getToken(req);
        log.info("naver token: {}", naverToken);
        NaverUserRes naverUser = naverUserFeignClient.getUser(String.format("Bearer %s", naverToken.getAccessToken()));
        log.info("naver user: {}", naverUser);

        SocialLoginDto dto = SocialLoginDto.builder()
                .socialId(naverUser.getResponse().getId())
                .loginId(naverUser.getResponse().getEmail())
                .loginType("NAVER")
                .build();
        log.info("social login dto: {}", dto);

        Integer id = accountMapper.findBySocialIdAndLoginIdAndLoginType(dto);
        log.info("user id: {}", id);

        if (id == null) {
            AccountJoinReq joinReq = AccountJoinReq.builder()
                    .name(naverUser.getResponse().getName())
                    .loginId(dto.getLoginId())
                    .loginType("NAVER")
                    .socialId(dto.getSocialId())
                    .build();

            accountMapper.save(joinReq);

            return joinReq.getId();
        }

        return id;
    }

    public Integer sendVerifyCode(String email) {
        // 인증 번호(4자리) 생성
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        codes.put(email, code);

        // 인증 번호 유지 시간(5분) 설정
        new Thread(new AuthCode(email)).start();

        try {
            // 메일 전송
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("from", "Gallery");
            helper.setTo(email);
            helper.setSubject("Gallery 이메일 인증 코드 안내");

            String htmlContent = "<div style='font-family:Arial,sans-serif; padding:20px;'>" +
                    "<h2 style='color:#4CAF50;'>이메일 인증</h2>" +
                    "<p>인증 코드는 아래와 같습니다:</p>" +
                    "<h1 style='color:#FF5722;'>" + code + "</h1>" +
                    "<p style='font-size:12px; color:gray;'>5분 내 입력해주세요.</p>" +
                    "</div>";
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return 1;
    }

    public Integer checkVerifyCode(VerifyCodeReq req) {
        // 입력한 메일의 인증 번호를 가져옴
        // getOrDefault: 첫 번째 인자의 값이 null이면 두 번째 인자를 return
        String savedCode = codes.getOrDefault(req.getEmail(), "");

        // 입력한 인증 번호와 일치하지 않을 경우
        if (!savedCode.equals(req.getCode())) {
            return null;
        }

        // 입력한 인증 번호와 일치할 경우
        codes.remove(req.getEmail());
        mailChecked.put(req.getEmail(), true);

        // 해당 메일에 대한 인증 확인 유지 시간(5분) 설정
        new Thread(new MailCheck(req.getEmail())).start();

        return 1;
    }
}
