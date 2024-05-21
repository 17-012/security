package com.green.greengram.feed.model;

import com.green.greengram.common.GlobalConst;
import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
public class FeedGetReq extends Paging {
    @Parameter(name = "signed_user_id")
    private long signedUserId;

    public FeedGetReq(Integer page, Integer size, @BindParam("signed_user_id") long signedUserId) {
        super(page, size == null ? GlobalConst.FEED_PAGING_SIZE : size);
        this.signedUserId = signedUserId;
    }
}
