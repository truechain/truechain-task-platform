package com.truechain.task.util;

import com.truechain.task.core.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 验证token合法性
     *
     * @param token
     * @param salt
     * @return
     */
    public static boolean verifyToken(String token, String salt) {
        Key key = generalKey(salt);
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            logger.error("---方法verifyToken---错误信息：" + e.getMessage(), e);
            if (e.getMessage().contains("JWT expired")) {
                throw new BusinessException("JWT expired");
            }
            if (e.getMessage().contains("not match locally computed signature")) {
                throw new BusinessException("keyNotMatch");
            }
            return false;
        }
    }

    /**
     * 解析被压缩的token值并获取redisKey
     *
     * @param token
     * @param salt
     * @return
     */
    public static String getRedisKeyByToken(String token, String salt) {
        // 得到Key
        Key key = generalKey(salt);
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return (String) claims.get("payload");
        } catch (Exception e) {
            logger.error("---getSessionPoJoByToken---错误信息：" + e.getMessage(), e);
            if (e.getMessage().contains("JWT expired")) {
                throw new BusinessException("登录信息失效");
            }
            if (e.getMessage().contains("not match locally computed signature")) {
                throw new BusinessException("签名不匹配");
            }
            throw e;
        }
    }

    /**
     * createToken
     *
     * @param salt
     * @param redisKey
     * @param expireDay
     * @return
     */
    public static String createToken(String salt, String redisKey, long expireDay) {
        String issuer = "truechain";
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date nbf = new Date(nowMillis - 180000L);
        Instant instant = now.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        localDate = localDate.plusDays(expireDay);
        Date exp = Date.from(localDate.atStartOfDay(zoneId).toInstant());
        Key signingKey = generalKey(salt);
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                .setIssuedAt(now).setIssuer(issuer).claim("payload", redisKey).signWith(SignatureAlgorithm.HS512, signingKey);
        builder.setExpiration(exp).setNotBefore(nbf);
        return builder.compact();
    }

    /**
     * 根据自定义的Constant的字符串生成的Key
     *
     * @param salt
     * @return
     */
    private static Key generalKey(String salt) {
        if (null == salt || "".equals(salt))
            return null;
        salt = "truechain" + salt;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        String printBase64Binary = DatatypeConverter.printBase64Binary(salt.getBytes());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(printBase64Binary);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }
}
