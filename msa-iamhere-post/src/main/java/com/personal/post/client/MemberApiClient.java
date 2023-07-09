package com.personal.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "MSA-IAMHERE-MEMBER")
@Component
public interface MemberApiClient {
    @GetMapping(value = "/get-current-member")
    public Long getCurrentMember();
}