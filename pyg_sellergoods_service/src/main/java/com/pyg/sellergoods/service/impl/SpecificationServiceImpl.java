package com.pyg.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationExample;
import com.pyg.pojo.TbSpecificationExample.Criteria;
import com.pyg.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>)
                specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Specification specification) {
        specificationMapper.insert(specification.getSpecification());
        for (TbSpecificationOption specificationOption : specification
                .getSpecificationOptionList()) {
            specificationOption.setSpecId(specification.getSpecification()
                    .getId());//设置外键
            specificationOptionMapper.insert(specificationOption);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Specification specification) {
        specificationMapper.updateByPrimaryKey(specification.getSpecification
                ());

        TbSpecificationOptionExample example = new
                TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example
                .createCriteria();
        criteria.andSpecIdEqualTo(specification.getSpecification().getId());
        specificationOptionMapper.deleteByExample(example);

        for (TbSpecificationOption specificationOption : specification
                .getSpecificationOptionList()) {
            specificationOption.setSpecId(specification.getSpecification()
                    .getId());//设置外键
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();
        //规格
        specification.setSpecification(specificationMapper.selectByPrimaryKey
                (id));
        //规格选项列表
        TbSpecificationOptionExample example = new
                TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example
                .createCriteria();
        criteria.andSpecIdEqualTo(id);
        specification.setSpecificationOptionList(specificationOptionMapper
                .selectByExample(example));
        return specification;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbSpecification specification, int pageNum,
                               int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbSpecificationExample example = new TbSpecificationExample();
        Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification
                    .getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() +
                        "%");
            }

        }
        Page<TbSpecification> page = (Page<TbSpecification>)
                specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
