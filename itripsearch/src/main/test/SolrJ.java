import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:30
 */
public class SolrJ {
    @Test
    public void test1() {
        //构建solr客户端
        HttpSolrClient httpSolrClient = new HttpSolrClient("http://localhost:8080/solr/test");
        //设置超时时间
        httpSolrClient.setConnectionTimeout(500);
        //设置数据格式，solr默认是xml格式
        httpSolrClient.setParser(new XMLResponseParser());

        //solr查询对象
        SolrQuery solrQuery = new SolrQuery();
        //q
        solrQuery.setQuery("*:*");

        try {
            //从solr中获取到数据
            QueryResponse query = httpSolrClient.query(solrQuery);
            List<Hotel> beans = query.getBeans(Hotel.class);
            for (Hotel hotel : beans) {
                System.out.println(hotel);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}