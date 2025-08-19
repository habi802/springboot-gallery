package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.*;
import kr.co.wikibook.gallery.config.constants.ConstKakao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;

    private final KakaoTokenFeignClient kakaoTokenFeignClient;
    private final KakaoUserFeignClient kakaoUserFeignClient;
    private final ConstKakao constKakao;

    public int join(AccountJoinReq req) {
        String hashedPw = BCrypt.hashpw(req.getLoginPw(), BCrypt.gensalt());

        // 암호화가 된 비밀번호를 갖는 AccountJoinReq 객체를 만들어주세요. (아이디, 이름도 갖고 있고)
        AccountJoinReq changedReq = new AccountJoinReq(req.getName(), req.getLoginId(), hashedPw);
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

    public void kakaoLogin(String code) {
        KakaoTokenReq req = new KakaoTokenReq("authorization_code", constKakao.getAppKey(), constKakao.getRedirectUri(), code);
        log.info("kakao login req: {}", req);
        KakaoTokenResponse kakaoToken = kakaoTokenFeignClient.getToken(req);
        log.info("kakao token: {}", kakaoToken);
        KakaoUserResponse kakaoUser = kakaoUserFeignClient.getUser(String.format("Bearer %s", kakaoToken.getAccessToken()));
        log.info("kakao user: {}", kakaoUser);
    }
}
