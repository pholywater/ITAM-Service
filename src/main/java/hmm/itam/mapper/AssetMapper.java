package hmm.itam.mapper;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import hmm.itam.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper {

    List<AssetVo> getAssetList();

    /*List<AssetVo> assetHeaderSearch(String statusType, String statusAssetUsage);*/
    //AssetVo assetHeaderSearch(HeaderSearchDto headerSearchDto);
    //List<AssetVo> assetHeaderSearch(HeaderSearchDto headerSearchDto);

    List<AssetVo> findAssetByPagination(int startNo, int length, String navSearch);

    int countTotalAsset(String navSearch);
/*
    아래 상세 검색과 통합 작업 완료.
    List<AssetVo> searchByNavbar(String navSearch); // NavbarSearch 메인 화면 우측 상단 검색
*/

    List<AssetVo> searchAssetDetail(String search, String searchType); // 장비 리스트 조회

    List<AssetVo> searchPaymentList(String searchStart, String searchEnd); // 신규 장비 출고 리스트 조

    List<AssetVo> getHistorySearch(String navSearch); // 간편 검색

    AssetVo getAssetByAssetNumber(String assetNumber); // 관리번호로 검색

    List<AssetVo> getDepartmentList(); // datalist 자동완성 부서 검색 리스트

    List<AssetVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트

    void insertAsset(AssetVo assetVo); // 장비 등록

    void insertHistory(AssetVo assetVo); // 이력 관리 등록

    void updateAsset(AssetVo assetVo); // 장비 정보 수정

    void deleteAsset(AssetVo assetVo); // 장비 삭제


}
