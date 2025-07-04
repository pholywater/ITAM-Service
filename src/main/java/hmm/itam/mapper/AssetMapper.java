package hmm.itam.mapper;

import hmm.itam.dto.PageDto;
import hmm.itam.vo.AssetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetMapper {

    List<AssetVo> getAssetList(); // datalist 자동완성 장비 리스트

    List<AssetVo> getDepartmentList(); // datalist 자동완성 부서 검색 리스트

    List<AssetVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트

    List<AssetVo> getAssetListAll(); // 전체 장비 리스트

    List<AssetVo> getAssetListOutput(); // 출고 장비 리스트

    List<AssetVo> getAssetListWork(); // 업무용 장비 리스트

    List<AssetVo> getAssetListRent(); // 대여 장비 리스트

    List<AssetVo> getAssetListPublic(); // 공용 장비 리스트

    List<AssetVo> getAssetListInput(); // 재고 장비 리스트

    List<AssetVo> getAssetListInputL(); // 재고 노트북 장비 리스트

    List<AssetVo> getAssetListInputM(); // 재고 모니터 장비 리스트

    List<AssetVo> getAssetListNew(); // 신규 장비 리스트

    List<AssetVo> getAssetListBusanInventory(); // 장비 리스트 부산 재고

    List<AssetVo> getAssetListUpdateToday(); // 오늘 업데이트된 장비 리스트

    List<AssetVo> searchAssetList(String search, String searchType); // 장비 리스트 조회

    List<AssetVo> searchMemberList(String searchMember); // 부서 및 직원 장비 리스트 조회

    List<AssetVo> searchPaymentList(String searchStart, String searchEnd); // 장비 지급일 조회

    List<AssetVo> getHistorySearch(String navSearch); // 이력 관리 간편 검색

    AssetVo getAssetByAssetNumber(String assetNumber); // 관리번호로 검색

    void insertAsset(AssetVo assetVo); // 장비 등록

    void insertHistory(AssetVo assetVo); // 이력 관리 등록

    void updateAsset(AssetVo assetVo); // 장비 정보 수정

    void deleteAsset(AssetVo assetVo); // 장비 삭제

    /**
     * 페이징, 검색, 정렬 조건에 따라 자산 목록을 조회합니다.
     */
    List<AssetVo> findAssetByPagination(@Param("pageDto") PageDto<?> pageDto,
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
                                        @Param("direction") String direction);

    /**
     * 검색 조건에 따라 전체 자산 개수를 조회합니다.
     */
    int countTotalAsset(@Param("pageDto") PageDto<?> pageDto,
                        @Param("searchType") String searchType,
                        @Param("navSearch") String navSearch,
                        @Param("search") String search,
                        @Param("viewType") String viewType,
                        @Param("tableName") String tableName,
                        @Param("searchStart") String searchStart,
                        @Param("searchEnd") String searchEnd);
}
