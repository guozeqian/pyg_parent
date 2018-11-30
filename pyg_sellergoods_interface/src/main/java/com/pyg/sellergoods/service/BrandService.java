package com.pyg.sellergoods.service;

import com.pyg.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {

    public PageResult search(TbBrand brand, int page, int rows);

    public List<Map> selectOptionList();
}
