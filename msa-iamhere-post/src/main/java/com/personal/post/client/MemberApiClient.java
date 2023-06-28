package com.personal.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msa-iamhere-member", path = "/api/v1/members")
public interface MemberApiClient {
    @GetMapping(value = "/nickname")
    public String getNickname(@RequestParam("id") Long id);
}