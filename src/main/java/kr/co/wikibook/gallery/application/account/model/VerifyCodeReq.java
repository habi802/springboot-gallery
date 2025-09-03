package kr.co.wikibook.gallery.application.account.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerifyCodeReq {
    private String email;
    private String code;
}
