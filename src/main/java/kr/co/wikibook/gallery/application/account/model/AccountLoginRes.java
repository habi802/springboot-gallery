package kr.co.wikibook.gallery.application.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class AccountLoginRes {
    private int id;
    @JsonIgnore
    private String loginPw;
}
