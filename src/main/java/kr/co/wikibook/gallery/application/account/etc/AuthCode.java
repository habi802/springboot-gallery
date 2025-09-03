package kr.co.wikibook.gallery.application.account.etc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthCode implements Runnable {
    private final String email;

    public AuthCode(String email) {
        this.email = email;
    }

    // 스레드를 5분 동안 일시 정지
    public void run() {
        try {
            Thread.sleep(300_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
