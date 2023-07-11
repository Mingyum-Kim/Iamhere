package com.personal.post.client;

import feign.Feign;
import feign.Headers;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "MSA-IAMHERE-MEMBER")
@Headers("Accept-Language: en-US")
@Component
public interface MemberApiClient {
    @GetMapping(value = "/get-current-member")
    @Headers("Content-Type: application/json")
    public Long getCurrentMember();
}