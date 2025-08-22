package kr.co.wikibook.gallery.application.account;

import kr.co.wikibook.gallery.application.account.model.AccountJoinReq;
import kr.co.wikibook.gallery.application.account.model.AccountLoginReq;
import kr.co.wikibook.gallery.application.account.model.AccountLoginRes;
import kr.co.wikibook.gallery.application.account.model.SocialLoginDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int save(AccountJoinReq req);
    AccountLoginRes findByLoginId(AccountLoginReq req);
    Integer findBySocialIdAndLoginIdAndLoginType(SocialLoginDto dto);
}
