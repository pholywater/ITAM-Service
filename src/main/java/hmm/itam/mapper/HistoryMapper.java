package hmm.itam.mapper;

import hmm.itam.dto.PageDto;
import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {

    // ✅ 기존 기능
    List<HistoryVo> getHistoryListDate(@Param("searchStart") String searchStart,
                                       @Param("searchEnd") String searchEnd); // 전체 이력 조회


    List<HistoryVo> findHistoryByPagination(PageDto<?> pageDto);

    List<HistoryVo> getHistoryList(@Param("tableName") String tableName,
                                   @Param("start") int start,
                                   @Param("length") int length,
                                   @Param("searchType") String searchType,
                                   @Param("navSearch") String navSearch,
                                   @Param("searchValue") String searchValue,
                                   @Param("searchStart") String searchStart,
                                   @Param("searchEnd") String searchEnd,
                                   @Param("orderByColumn") String orderByColumn,
                                   @Param("direction") String direction);

    int countHistoryList(@Param("navSearch") String searchType,
                         @Param("navSearch") String navSearch,
                         @Param("searchValue") String searchValue,
                         @Param("searchStart") String searchStart,
                         @Param("searchEnd") String searchEnd);

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


    /**
     * 페이징 및 검색 조건에 따라 이력 데이터를 조회합니다.
     */
    List<HistoryVo> findHistoryByPagination(
            @Param("pageDto") PageDto<?> pageDto,
            @Param("start") int start,
            @Param("length") int length,
            @Param("navSearch") String navSearch,
            @Param("orderByColumn") String orderByColumn,
            @Param("direction") String direction);

    int countTotalHistory(
            @Param("pageDto") PageDto<?> pageDto,
            @Param("navSearch") String navSearch,
            @Param("searchType") String searchType,
            @Param("viewType") String viewType,
            @Param("tableName") String tableName,
            @Param("searchStart") String searchStart,
            @Param("searchEnd") String searchEnd);

    List<HistoryVo> getHistoryListAll();

    List<HistoryVo> getHistoryListAsset();

    List<HistoryVo> getHistoryListChange();

    List<HistoryVo> getHistoryListConsumables();

    List<HistoryVo> getHistoryListInput();

    List<HistoryVo> getHistoryListOutput();

    List<HistoryVo> getHistoryListRepair();
}
