package kr.co.wikibook.gallery.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountJoinReq {
    private int id;
    private String name;
    private String loginId;
    private String loginPw;
    private String loginType;
    private long socialId;
}
