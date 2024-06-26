package com.green.greengram.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration  // 빈 등록 시 싱글톤을 보장해 주는 어노테이션
@RequiredArgsConstructor
public class SecurityConfiguration {
//    @Component로 빈등록을 하였기 때문에 ID가 된다.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
    메소드 빈등록 이유
        클래스 기능과 관련된 객체들을
        각각 빈등록 하면서 한곳에서 관리 하기 좋기 때문

        클래스로 빈등록을 하면, 기능마다 클래스를 새로 만들어야 한다.

    */

    @Bean   // 메소드 타입의 빈 등록 ( 파라미터, 리턴타입 중요 ) 파라미터는 빈 등록할 때 필요한 객체
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 파라미터 없이 내가 직접 new 객체화 해서 리턴으로 빈 등록 가능
//        new SecurityFilterChain();

//        생성만 하는 추상 메서드
        new Supplier<HttpSecurity>() {
            @Override
            public HttpSecurity get() {
                return httpSecurity;
            }
        };
//          파라미터를 받는 추상 메서드
        new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return null;
            }
        };


//        람다 식 표현
        return httpSecurity.sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // form 로그인 방식을 사용하지 않는다 라는 셋팅
                // (서버 사이드 랜더링 하지 않는다. 즉 html화면을 백엔드가 만들지 않는다 )
                // 백엔드에서 화면을 만들지 않더라도 위 세팅을 끄지 않아도 괜찮다. 사용하지 않는 걸 끔으로써 리소스 확보
                .httpBasic(http -> http.disable())

                // 시큐리티에서 제공하는 로그인 화면 사용하지 않겠다. ( 안 이쁨 )
                // form 로그인 방식을 사용하지 않는다 라는 셋팅
                .formLogin(form -> form.disable())

                // csrf 와 cors랑 다름
                // csrf 비정상 루트로 요청이 날아가게 만듬
                .csrf(csrf -> csrf.disable())

                // 핵심!! ~~ 했을때만 ~~ 하겠다, ~~ 안했을때도 ~~ 하겠다 설정 해주는 것
                // permitAll => 해당 api는 인증 받지 않은 사용자들도 모두 이용 가능하다.
                // authenticated => 인증이 되어야 이용 가능하다.
                .authorizeHttpRequests(auth ->
                        auth
                                // Api 내용
                                .requestMatchers("/api/user/sign-up",
                                        "/api/user/sign-in",
                                        "/pic/**"
                                        ).permitAll()
                                // 스웨거 정상 작동되게 열어주는 코드
                                .requestMatchers(
                                        "/swagger",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                // front
                                // 프론트에서 사용하는 라우터 주소 모두 다 허용해야함
                                // 프론트에서 사용하는 라우터 주소
                                .requestMatchers("/",
                                        "/index.html",
                                        "/static/**",
                                        "/css/**",
                                        "/js/**",
                                        "/sign-in",
                                        "/sign-up",
                                        "/profile/*",
                                        "/feed"
                                ).permitAll()

                                // 나머지 모든 요청은 막아두는 코드
                                .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

//        일반 코드 표현
//        return http.sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(SessionManagementConfigurer<HttpSecurity> session) {
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            }
//        }).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
