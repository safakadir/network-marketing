package com.vose.voseengine.service.helper;

import com.vose.voseengine.model.service.PageResult;
import org.springframework.data.domain.Page;

public class PageResultConverter {
    public <T> PageResult<T> convert(Page<T> springPage) {
        PageResult<T> result = new PageResult<>();
        result.setStatus(true);
        result.setData(springPage.getContent());
        result.setCurrent_page(springPage.getPageable().getPageNumber()+1);
        result.setPer_page(springPage.getPageable().getPageSize());
        result.setTotal(springPage.getTotalElements());
        result.setLast_page(springPage.getTotalPages());
        result.setFrom((result.getCurrent_page()-1)*result.getPer_page()+1);
        result.setTo(result.getCurrent_page()*result.getPer_page());
        return result;
    }
}
