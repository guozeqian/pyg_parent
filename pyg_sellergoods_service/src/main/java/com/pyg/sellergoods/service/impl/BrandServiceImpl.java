package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbBrandExample;
import com.pyg.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private TbBrandMapper brandMapper;

    /*
   * 查询所有品牌数据
   * */
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int page, int rows) {
        //影响下面的查询，影响sql语句，影响返回参数
        PageHelper.startPage(page, rows);
        Page<TbBrand> pageResult = (Page<TbBrand>) brandMapper
                .selectByExample(null);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());

    }

    @Override
    public void add(TbBrand tbBrand) {
        brandMapper.insert(tbBrand);
    }

    @Override
    public void update(TbBrand entity) {
        brandMapper.updateByPrimaryKey(entity);
    }

    @Override
    public void delete(Long[] ids) {
        System.out.println(ids);
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }

    }

    @Override
    public TbBrand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }




    @Override
    public PageResult findPage(TbBrand model, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public PageResult search(TbBrand brand, int page, int rows) {
        //影响下面的查询，影响sql语句，影响返回参数
        PageHelper.startPage(page, rows);
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        if (brand != null) {
            if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andNameLike("%" + brand.getName() + "%");
            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar
                    ())) {
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            }
        }

        Page<TbBrand> pageResult = (Page<TbBrand>) brandMapper
                .selectByExample(example);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());

    }
    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }


}
