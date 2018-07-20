package com.truechain.task.api.service.weixin;

import com.truechain.task.api.model.dto.AccessTokenDTO;
import com.truechain.task.api.model.dto.JsapiTicketDTO;
import com.truechain.task.api.repository.RedisRepository;
import com.truechain.task.util.CommonUtil;
import com.truechain.task.util.DateUtil;
import com.truechain.task.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeiXinService {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinService.class);

    //redis key
    private static final String wxTicketRedisKey = "wx_access_ticket";

    private static final String wxTokenRedisKey = "wx_access_token";
    //appId
    private static final String appId = "wx595ba6d1669c4532";
    //tokenUrl
    private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    //公众号私钥
    private static final String appsecret = "75288033b96bca42496acf728d8d84a1";
    //ticketUrl
    private static final String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取微信签名
     *
     * @param url
     * @return
     */
    public Map<String, String> getSign(String url) {
        String jsapi_ticket = stringRedisTemplate.opsForValue().get(wxTicketRedisKey);
        if (StringUtils.isBlank(jsapi_ticket)) {
            getWxTokenToRedis();
        }
        Map<String, String> resMap = null;
        if (StringUtils.isNotBlank(jsapi_ticket)) {
            resMap = new HashMap<>();
            String nonce_str = CommonUtil.getRandomString(32);
            String timestamp = Long.toString(DateUtil.getCurrentTime("S"));
            String signature = "";
            //注意这里参数名必须全部小写，且必须有序
            String resStr = "jsapi_ticket=" + jsapi_ticket +
                    "&noncestr=" + nonce_str +
                    "&timestamp=" + timestamp +
                    "&url=" + url;
            logger.info("--方法--sign,签名加密原始串--" + resStr);
            try {
                MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                crypt.reset();
                crypt.update(resStr.getBytes("UTF-8"));
                signature = byteToHex(crypt.digest());
            } catch (Exception e) {
                logger.error("--方法--sign,签名加密出现异常", e);
                throw new RuntimeException("微信签名失败");
            }
            resMap.put("url", url);
            resMap.put("jsapi_ticket", jsapi_ticket);
            resMap.put("nonceStr", nonce_str);
            resMap.put("timestamp", timestamp);
            resMap.put("signature", signature);
            resMap.put("appId", appId);
            logger.info("--方法--sign,签名加密返回map值--" + new JSONObject(resMap).toString());
        }
        return resMap;
    }

    /**
     * 获取微信签名
     */
    public void getWxTokenToRedis() {
        String tokenUrlPath = tokenUrl + "&appid=" + appId + "&secret=" + appsecret;
        //获取微信access_token
        AccessTokenDTO accessTokenVo = getAccessTokenVo(tokenUrlPath);
        if (null != accessTokenVo && StringUtils.isNotBlank(accessTokenVo.getAccess_token())) {
            logger.info("--方法--getWxTokenToRedis,请求返回结果--获取access_token成功，有效时长expires_in:{}秒 token:{}",
                    accessTokenVo.getExpires_in(), accessTokenVo.getAccess_token());
            stringRedisTemplate.opsForValue().set(wxTokenRedisKey, accessTokenVo.getAccess_token(), 2, TimeUnit.HOURS);
            logger.info("--方法--getWxTokenToRedis,微信token保存到Redis成功");
            String ticketUrlReq = ticketUrl + "&access_token=" + accessTokenVo.getAccess_token();
            JsapiTicketDTO jsapiTicketVo = getJsapiTicketDTO(ticketUrlReq);
            ticketUrlReq = "";
            if (null != jsapiTicketVo && StringUtils.isNotBlank(jsapiTicketVo.getTicket())) {
                logger.info("--方法--getWxTokenToRedis,请求返回结果--获取jsapi_ticket成功，有效时长expires_in:{}秒 ticket:{}",
                        jsapiTicketVo.getExpires_in(), jsapiTicketVo.getTicket());
                stringRedisTemplate.opsForValue().set(wxTicketRedisKey, jsapiTicketVo.getTicket(), 2, TimeUnit.HOURS);
                logger.info("--方法--getWxTokenToRedis,微信ticket保存到Redis成功");
            }
        }
    }

    /**
     * getJsapiTicketDTO
     *
     * @param ticketUrl
     * @return
     */
    private JsapiTicketDTO getJsapiTicketDTO(String ticketUrl) {
        JsapiTicketDTO ticketDTO = null;
        logger.info("--方法--getJsapiTicketVo,请求参数--" + ticketUrl);
        String ticketJson = restTemplate.getForObject(ticketUrl, String.class);
        logger.info("--方法--getJsapiTicketVo,请求返回结果--" + ticketJson);
        if (StringUtils.isNotBlank(ticketJson)) {
            ticketDTO = JsonUtil.parseObject(ticketJson, JsapiTicketDTO.class);
        }
        return ticketDTO;
    }

    /**
     * getAccessTokenVo
     *
     * @param tokenUrl
     * @return
     */
    private AccessTokenDTO getAccessTokenVo(String tokenUrl) {
        AccessTokenDTO tokenDTO = null;
        logger.info("--方法--getAccessTokenVo,请求参数--" + tokenUrl);
        String tokenJson = restTemplate.getForObject(tokenUrl, String.class);
        logger.info("--方法--getAccessTokenVo,请求返回结果--" + tokenJson);
        if (StringUtils.isNotBlank(tokenJson)) {
            tokenDTO = JsonUtil.parseObject(tokenJson, AccessTokenDTO.class);
        }
        return tokenDTO;
    }

    /**
     * 微信签名格式化
     *
     * @param hash
     * @return
     * @description:
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
