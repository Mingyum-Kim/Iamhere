package com.personal.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msa-iamhere-member")
@Component
public interface MemberApiClient {
    @GetMapping(value = "/nickname")
    public String getNickname(@RequestParam("id") Long id);
}