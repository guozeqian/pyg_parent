package com.pyg.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.content.service.ContentService;
import com.pyg.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference(timeout = 500000)
    private ContentService contentService;

    @RequestMapping("/findListByCategoryId")
    public List<TbContent> findListByCategoryId(Long categoryId) {
        return contentService.findListByCategoryId(categoryId);
    }
}
