package work.jimmmy.allinsearch.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {
    private Integer page = 1;

    private Integer size = 20;
}
