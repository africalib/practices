package org.africalib.forms.common.library;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.africalib.forms.common.enumeration.TokenSubject;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenLibrary {

    private final String SECRET_KEY_STRING = "2a89298a289a892289189ashda892891289a8w89a2891893891hawhduahwduiahduih2189283918389298518938912auhahusduha1723178371";
    private final SecretKey SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    // 토큰에서 클레임을 추출하는 메서드
    private Claims getClaims(TokenSubject subject, String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getSubject().equals(subject.name())) {
                return claims;
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    // 토큰 생성
    public String generate(TokenSubject subject, Long id) {
        // 현재 시간을 기준으로 토큰 만료 시간 설정 (예: 1시간)
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * 60 * 24; // 만료 시간

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        return Jwts.builder()
                .setSubject(subject.name()) // 토큰의 주제 설정
                .setIssuedAt(new Date(nowMillis)) // 토큰 발행 시간 설정
                .setExpiration(new Date(expMillis)) // 토큰 만료 시간 설정
                .addClaims(claims) // 클레임 추가
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 토큰 서명 설정
                .compact(); // 토큰 생성
    }

    // 토큰에서 클레임의 아이디 값을 추출하는 메서드
    public Long getId(TokenSubject subject, String token) {
        Claims claims = this.getClaims(subject, token);

        if (claims != null) {
            Object id = claims.get("id");

            if (id != null) {
                return Long.parseLong(id.toString());
            }
        }

        return null;
    }
}
