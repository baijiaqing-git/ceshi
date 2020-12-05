package cn.kgc.itrip.search.service.impl;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.kgc.itrip.beans.vo.hotel.SearchHotelVO;
import cn.kgc.itrip.search.dao.Basedao;
import cn.kgc.itrip.search.model.Hotel;
import cn.kgc.itrip.search.service.ISearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:44
 */
@Service("ISearchService")
public class SearchServiceImpl implements ISearchService {
    @Resource
    private Basedao baseDao;

    @Override
    public ServerResponse queryHotCityHotel(SearchHotCityVO searchHotCityVO) {
        //查询全部内容
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        //设置返回条数
        solrQuery.setRows(searchHotCityVO.getCount());
        if (searchHotCityVO.getCityId() != null) {
            solrQuery.addFilterQuery("cityId:" + searchHotCityVO.getCityId());
        }
        return baseDao.queryList(solrQuery, null, null, Hotel.class);
    }

    @Override
    public ServerResponse searchItripHotelPage(SearchHotelVO searchHotelVO) {
        SolrQuery solrQuery = new SolrQuery("*:*");
        //检查页码
        if (searchHotelVO.getPageSize() == null || searchHotelVO.getPageSize() == 0) {
            searchHotelVO.setPageSize(5);
        }
        if (searchHotelVO.getPageNo() == null || searchHotelVO.getPageNo() == 0) {
            searchHotelVO.setPageNo(1);
        }
        //查询目的地
        if (StringUtils.isNotBlank(searchHotelVO.getDestination())) {
            solrQuery.addFilterQuery("destination: " + searchHotelVO.getDestination());
        } else {
            //默认查询北京
            solrQuery.addFilterQuery("destination: 北京");

        }
        //查询关键字
        if (StringUtils.isNotBlank(searchHotelVO.getKeywords())) {
            solrQuery.addFilterQuery("keyword :" + searchHotelVO.getKeywords());
        }
        //查询商圈
        if (StringUtils.isNotBlank(searchHotelVO.getTradeAreaIds())) {
            String[] split = StringUtils.split(searchHotelVO.getTradeAreaIds(), ',');
            StringBuffer areas = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    areas.append("tradingAreaIds : *, " + split[i] + " ,* ");
                } else {
                    areas.append("OR tradingAreaIds : *, " + split[i] + " ,* ");
                }
            }
            solrQuery.addFilterQuery(areas.toString());
        }
        //判断价格
        if (searchHotelVO.getMinPrice() == null && searchHotelVO.getMaxPrice() != null) {
            solrQuery.addFilterQuery(" minPrice:[" + searchHotelVO.getMaxPrice() + "TO *]");
        }
        if (searchHotelVO.getMinPrice() != null && searchHotelVO.getMaxPrice() == null) {
            solrQuery.addFilterQuery(" minPrice:[ * TO" + searchHotelVO.getMinPrice() + "]");
        }
        if (searchHotelVO.getMinPrice() != null && searchHotelVO.getMaxPrice() != null) {
            solrQuery.addFilterQuery(" minPrice:[" + searchHotelVO.getMinPrice() + " TO " +
                    searchHotelVO.getMaxPrice() + "]");
        }
        //查询酒店特色
        if (StringUtils.isNotBlank(searchHotelVO.getFeatureIds())) {
            String[] split = StringUtils.split(searchHotelVO.getFeatureIds(), ",");
            StringBuffer features = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    features.append(" featureIds: *," + split[i] + " ,*");
                } else {
                    features.append(" OR featureIds: *," + split[i] + " ,*");
                }

                solrQuery.addFilterQuery(features.toString());
            }
        }
        //排序
        if (StringUtils.isNotBlank(searchHotelVO.getAscSort())){
            solrQuery.setSort(searchHotelVO.getAscSort(),SolrQuery.ORDER.asc);
        }
        if (StringUtils.isNotBlank(searchHotelVO.getDescSort())){
            solrQuery.setSort(searchHotelVO.getDescSort(),SolrQuery.ORDER.desc);
        }
        //星级查询
        if (searchHotelVO.getHotelLevel()!=null&& searchHotelVO.getHotelLevel()!=0){
            solrQuery.addFilterQuery(" hotelLevel:"+searchHotelVO.getHotelLevel());
        }
        return baseDao.queryList(solrQuery,searchHotelVO.getPageNo(),
                searchHotelVO.getPageSize(),
                Hotel.class);
    }
}

