package hmm.itam.mapper;

import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {

    // ✅ 기존 기능
    List<HistoryVo> getHistoryList(@Param("searchStart") String searchStart,
                                   @Param("searchEnd") String searchEnd); // 전체 이력 조회

    void insertHistory(HistoryVo historyVo); // 히스토리 등록

    List<HistoryVo> getHistoryAssetNumber(@Param("search") String search); // 관리번호로 검색

    List<HistoryVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트

    List<HistoryVo> getAssetList(); // datalist 자동완성 장비 검색 리스트

    // ✅ 서버사이드 DataTables용 추가 기능

    /**
     * 검색 조건에 따라 전체 이력 개수를 조회합니다.
     */
    List<HistoryVo> getHistorySearch(@Param("search") String search,
                                     @Param("searchType") String searchType); // 이력 관리 상세 검색

    int countTotalHistory(@Param("navSearch") String navSearch,
                          @Param("searchValue") String searchValue,
                          @Param("searchStart") String searchStart,
                          @Param("searchEnd") String searchEnd);

    /**
     * 페이징 및 검색 조건에 따라 이력 데이터를 조회합니다.
     */
    List<HistoryVo> findHistoryByPagination(@Param("start") int start,
                                            @Param("length") int length,
                                            @Param("navSearch") String navSearch,
                                            @Param("searchValue") String searchValue,
                                            @Param("searchStart") String searchStart,
                                            @Param("searchEnd") String searchEnd,
                                            @Param("orderByColumn") String orderByColumn,
                                            @Param("direction") String direction);
}
