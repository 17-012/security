package com.green.greengram.userfollow.intf;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

//이거 없어도 빈 등록은 되지만, 싱글톤을 보장하지 않음
@Configuration
public class CharEncodingConfiguration {

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }

}

