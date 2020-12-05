package cn.kgc.itrip.search.dao;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.search.model.Hotel;
import cn.kgc.itrip.utils.Page;
import cn.kgc.itrip.utils.PropertiesUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:39
 */
@Component
public class Basedao {
    private final String URL = "http://localhost:8080/solr/hotel";
    private HttpSolrClient httpSolrClient;

    public Basedao() {
        httpSolrClient = new HttpSolrClient(PropertiesUtil.getProperty("solr.url"));
        httpSolrClient.setConnectionTimeout(500);
        httpSolrClient.setParser(new XMLResponseParser());
    }

    /**
     * @param solrQuery 查询条件对象
     * @param pageNo    页码 某些业务不需要分页，如果此参数为空表示走默认查询
     * @param pageSize
     * @param clazz     向solr查询实体类的类型
     * @return
     */
    public ServerResponse queryList(SolrQuery solrQuery, Integer pageNo, Integer pageSize, Class clazz) {
        //检查是否分页
        if (pageNo == null || pageSize == null) {
            //默认走全
            try {
                QueryResponse response = httpSolrClient.query(solrQuery);
                List<Hotel> hotels = response.getBeans(clazz);
                return ServerResponse.createBySuccess(hotels);
            } catch (SolrServerException e) {
                e.printStackTrace();
                System.out.println("solr服务异常");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            QueryResponse response = httpSolrClient.query(solrQuery);
            //TODO 获取到所有符合条件的查询
            List<Hotel> hotel = response.getBeans(clazz);
            Page<Hotel> pages = new Page<>(pageNo, pageSize);
            List<Hotel> rowsContent = new ArrayList<>();
            //设置总条数
            pages.setTotal(hotel.size());
            for (int i = (pageNo - 1) * pageSize; i < (pageNo - 1) * pageSize + pageSize; i++) {
                if(i<hotel.size()){
                    rowsContent.add(hotel.get(i));
                }else{
                    break;
                }

            }
            pages.setRows(rowsContent);
            return ServerResponse.createBySuccess(pages);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}