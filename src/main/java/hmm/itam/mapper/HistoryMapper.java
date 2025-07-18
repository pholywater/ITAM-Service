package hmm.itam.mapper;

import hmm.itam.dto.PageDto;
import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {

    // ✅ 기본 CRUD
    void insertHistory(HistoryVo historyVo);

    void updateHistory(HistoryVo historyVo);

    void deleteHistoryByIdx(@Param("idx") Long idx);

    HistoryVo selectHistoryByIdx(@Param("idx") Long idx);

    // ✅ 등록 후 관리번호로 조회
    List<HistoryVo> selectByAssetNumber(@Param("assetNumber") String assetNumber);

    // ✅ 상세 검색
    List<HistoryVo> searchHistory(
            @Param("search") String search,
            @Param("searchType") String searchType
    );

    // ✅ 자동완성용 리스트
    List<HistoryVo> selectMemberList();

    List<HistoryVo> selectAssetList();

    // ✅ 서버사이드 페이징 + 검색 + 정렬
    List<HistoryVo> findHistoryByPagination(
            @Param("pageDto") PageDto<?> pageDto,
            @Param("tableName") String tableName,
            @Param("start") int start,
            @Param("length") int length,
            @Param("searchType") String searchType,
            @Param("navSearch") String navSearch,
            @Param("navSearchHistory") String navSearchHistory,
            @Param("search") String search,
            @Param("searchStart") String searchStart,
            @Param("searchEnd") String searchEnd,
            @Param("orderByColumn") String orderByColumn,
            @Param("direction") String direction
    );

    int countTotalHistory(
            @Param("pageDto") PageDto<?> pageDto,
            @Param("searchType") String searchType,
            @Param("navSearch") String navSearch,
            @Param("search") String search,
            @Param("viewType") String viewType,
            @Param("tableName") String tableName,
            @Param("searchStart") String searchStart,
            @Param("searchEnd") String searchEnd
    );

    // ✅ 뷰 타입별 이력 리스트
    List<HistoryVo> getHistoryListAll();

    List<HistoryVo> getHistoryListAsset();

    List<HistoryVo> getHistoryListChange();

    List<HistoryVo> getHistoryListConsumables();

    List<HistoryVo> getHistoryListInput();

    List<HistoryVo> getHistoryListOutput();

    List<HistoryVo> getHistoryListRepair();
}
