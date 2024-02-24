package hmm.itam.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    private int draw;
    private int length;
    private int start;
    private int end;
    private int total;
    private int recordsTotal;
    private int recordsFiltered;
    private int rowNo;
    private String navSearch;
    private String search;

    private List<T> data;
}