import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:30
 */
@Data
public class Hotel {
    @Field
    private int id;
    @Field
    private String address;
    @Field
    private String hotelName;
}
