//package com.itsu.threedays.config.kakao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//@Slf4j
//public class KakaoOAuth2 {
//
//    public KakaoUserInfo getUserInfo(String token) {
//        return getUserInfoByToken(token);
//    }
//
//    private KakaoUserInfo getUserInfoByToken(String accesstoken){
//        String KAKAO_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + accesstoken);
//        //Bearer 이후 한칸 띄기(kakao) +  accesstoken 헤더에 넣어야함
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate(); //서버에서 다른서버로 연결할 때 쓰는 RestTemplate
//
//
//        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
//        //카카오에서 받은 거를 response로 담김
//        log.info("response : {}", response);
//        log.info("response.getBody() : {}", response.getBody());
//
//        JSONObject jsonObject = new JSONObject(response.getBody());
//        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");//kakao-account : 키값
//
//        String email = kakao_account.getString("email");
//        log.info("email : {}", email);
//        String nickname = kakao_account.getJSONObject("profile").getString("nickname");
//        log.info("nickname : {}", nickname);
//
//        return new KakaoUserInfo(email,nickname);
//
//    }
//}
