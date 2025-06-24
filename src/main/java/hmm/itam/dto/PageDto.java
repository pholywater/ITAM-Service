package hmm.itam.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PageDto<T> {

    // DataTables에서 전달되는 draw 값 (요청 순서 식별용)
    private int draw;

    // 한 페이지에 보여줄 데이터 개수
    private int length;

    // 현재 페이지의 시작 인덱스
    private int start;

    // 현재 페이지의 끝 인덱스 (선택적으로 사용)
    private int end;

    // 전체 데이터 개수
    private int total;

    // 필터링 전 전체 레코드 수
    private int recordsTotal;

    // 필터링 후 전체 레코드 수
    private int recordsFiltered;

    // 행 번호 (선택적으로 사용)
    private int rowNo;

    // 해더 상단 조회 검색어 (세션 기반 검색어)
    private String navSearch;

    // 검색어 (DataTables에서 전달된 값)
    private String search;

    // 검색어 (추가적으로 사용할 수 있는 필드)
    private String searchValue;

    // 검색 타입 (예: historyAssetNumber, historyType 등)
    private String searchType;

    // 정렬할 컬럼 인덱스 (DataTables에서 전달됨)
    private Integer orderColumn;

    // 정렬 방향 ("asc" 또는 "desc")
    private String orderDir;

    // searchType 테이블 이름
    private String viewType;
    private String tableName;

    // 날짜 검색 시작일
    private String searchStart;

    // 날짜 검색 종료일
    private String searchEnd;

    // 실제 데이터 리스트
    private List<T> data;


    // 검색어 설정 메서드
    public void setSearch(String search) {
        this.search = search;
    }

}
