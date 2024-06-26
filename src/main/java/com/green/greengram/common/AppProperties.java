package com.green.greengram.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
// prefix의 app은 yaml 파일의 커스텀 속성 app 을 의미
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Jwt jwt = new Jwt();

//    class명 Jwt는 yaml 파일의 커스텀 속성 jwt 를 의미
//    멤버 필드 명은 yaml 파일의 커스텀 속성의 속성 명과 매칭
//    yaml 파일의 커스텀 속성에서 '-'는 멤버필드에서 카멜케이스 기법과 매칭
    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private String headerSchemaName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private long refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = refreshTokenExpiry / 1000;
        }
    }
}
