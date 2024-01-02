package fpt.com.rest_full_api.request;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private List<String> perfumers;
    private List<String> genders;
    private Integer price = 0;
    private String searchType;
    private String text;
}
