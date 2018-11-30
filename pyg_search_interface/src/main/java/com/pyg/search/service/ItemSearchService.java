package com.pyg.search.service;

import java.util.Map;
import java.util.List;

public interface ItemSearchService {

    public Map<String, Object> searchMap(Map searchMap);

    public void importGoodsList(List goodList);
    public void deleteByGoodsIds(List goodsIdList);
}
