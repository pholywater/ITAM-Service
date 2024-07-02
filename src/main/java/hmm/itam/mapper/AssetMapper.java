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
    List<AssetVo> getAssetList(); //  datalist 자동완성 장비 리스트

    List<AssetVo> getDepartmentList(); // datalist 자동완성 부서 검색 리스트

    List<AssetVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트


    List<AssetVo> searchAssetList(String search, String searchType); // 장비 리스트 조회

    List<AssetVo> searchMemberList(String searchDepart, String searchMember); // 부서 및 직원 장비 리스트 조회 화면

    List<AssetVo> searchPaymentList(String searchStart, String searchEnd); // 신규 및 장비 지급일 리스트 조회

    List<AssetVo> getHistorySearch(String navSearch); // 이력 관리 간편 검색


    AssetVo getAssetByAssetNumber(String assetNumber); // 관리번호로 검색

    void insertAsset(AssetVo assetVo); // 장비 등록

    void insertHistory(AssetVo assetVo); // 이력 관리 등록

    void updateAsset(AssetVo assetVo); // 장비 정보 수정

    void deleteAsset(AssetVo assetVo); // 장비 삭제


    List<AssetVo> findAssetByPagination(int startNo, int length, String navSearch);

    int countTotalAsset(String navSearch);



    /*List<AssetVo> assetHeaderSearch(String statusType, String statusAssetUsage);*/
    //AssetVo assetHeaderSearch(HeaderSearchDto headerSearchDto);
    //List<AssetVo> assetHeaderSearch(HeaderSearchDto headerSearchDto);

}
