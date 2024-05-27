package com.green.greengram.userfollow.intf;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.FollowPostReq;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

public interface FollowController {

    ResultDto<Integer> postFollow(@RequestBody FollowPostReq p);

    ResultDto<Integer> deleteFollow(@ParameterObject @ModelAttribute FollowPostReq p);

}
