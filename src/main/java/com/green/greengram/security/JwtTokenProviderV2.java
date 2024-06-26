package com.green.greengram.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProviderV2 {
    private final ObjectMapper om;
    private final AppProperties appProperties;
    private final SecretKey secretKey;
    private final String user = "signedUser";

    public JwtTokenProviderV2(ObjectMapper om, AppProperties appProperties) {
        this.om = om;
        this.appProperties = appProperties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
//      암-복호화 할때 사용하는 키를 생성하는 부분, decode메소드에 보내는 아규먼트값은 우리가 설정한 문자열
    }

    public String generateAccessToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getRefreshTokenExpiry());
    }

    public String generateToken(MyUser myUser, long tokenValidMilliSecond) {
        return Jwts.builder()
//                payload
                // JWT 생성일시
                .issuedAt(new Date(System.currentTimeMillis()))

//                payload
                // JWT 만료 일시
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))

//                payload
                //claims는 payload에 저장하고 싶은 내용을 저장
                .claims(createClaims(myUser))

//                signature
                // 서명 (JWT 암호화 선택)
                .signWith(secretKey, Jwts.SIG.HS512)

                // 토큰 생성
                .compact();

        // 메소드 호출, 메소드 호출, 메소드 호출 >> 체이닝 기법 ( 원리는 메서드 호출시 자신의 주소값 리턴을 하기 때문 )

    }

    private Claims createClaims(MyUser myUser) {
        try {
            String json = om.writeValueAsString(myUser);
            return Jwts.claims().add(user, json).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey) //똑같은 키로 복호화
                .build()
                .parseSignedClaims(token) // JWT 안에 들어있는 payload를 리턴
                .getPayload();
    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token); // JWT 인증코드에 저장되어 있는 Claims를 얻어온다
            String json = (String) claims.get(user); // Claims에 저장되어 있는 값을 얻어온다
            MyUser myUser = om.readValue(json, MyUser.class);
            MyUserDetails myUserDetails = new MyUserDetails();
            myUserDetails.setMyUser(myUser);
            return om.readValue(json, MyUserDetails.class); // JSON > 객체 변환
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidateToken(String token) {
        try {
//            ( 오리지널 ) 만료 시간이 안지났으면 false, 지났으면 true

//            ( 커스텀 ) 만료시간이 안지났으면 true, 지났으면 false
            return !getAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //            요청이 오면 JWT를 열어보는 부분 > 헤더에서 토큰을 꺼낸다.
    public String resolveToken(HttpServletRequest req) {

//        front가 back에 요청을 보낼때 ( 로그인을 했다면 )항상 JWT를 보낼껀데
//        header에 저장해서 보낸다.
        String jwt = req.getHeader(appProperties.getJwt().getHeaderSchemaName());
//        헤더에 값이 없다 -> 프론트가 헤더에 안담았다 or 로그인 전이다.
        if (jwt == null) {
            return null;
        }
//        프론트가 header에 authorization 키에 데이터를 담아서 보냈다는 뜻.
//        authorization에는 "Bearer JWT" 문자열이 있을 것이다. 문자열이 Bearer로 시작하는지 체크
//        authorization에 저장된 물자열이 Bearer로 식작하면 true, 아니면 false
//        front와의 통신에서 authorization에 "Bearer " 를 넣고 그 뒤에 jwt 문자열을 넣어라! 라고 말해야함
//        but, 앞에 ! 가 있으니 반대로 돌아감
        if (!jwt.startsWith(appProperties.getJwt().getTokenType())) {
            return null;
        }
//        trim() 은 양쪽(처음과 끝 ) (중간은 안됨)으로 빈칸이 있으면 제거하는 메서드
        return jwt.substring(appProperties.getJwt().getTokenType().length()).trim();
    }

//    spring contextholder에 저장할 자료를 세팅 ( 나중에 service단에서 빼서 쓸 값, 로그인 처리, 인가 처리를 위해 )
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token); // MyUserDetails 객체 주소값
        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());

//        UserNamePasswordAuthenticationToken 객체를 spring contextholder에 저장하는 자체만으로도 인증 완료
//        UserDetails는 로그인한 사용자의 정보를 controller or service단에서 빼서 사용하기 위함
//        UserDetails.getAuthorities()는 인가(권한)부분 세팅, 현재는 권한 하나만 가져올 수 있음
    }


    //    @PostConstruct //생성자 호출 이후에 한번 실행하는 메소드
//    public void init() {
//        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
////        this.secretKey = new SecretKeySpec(
////                appProperties.getJwt().getSecret().getBytes(),
////                SignatureAlgorithm.HS256.getJcaName());
//    }

//    public String generateAccessToken() {
//
//    }

}
