package hmm.itam.mapper;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
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

    List<AssetVo> searchByNavbar(String navSearch); // NavbarSearch 메인 화면 우측 상단 검색

    List<AssetVo> getHistorySearch(String navSearch); // 간편 검색

    AssetVo getAssetByAssetNumber(String assetNumber); // 관리번호로 검색

    void insertAsset(AssetVo assetVo); // 장비 등록

    void updateAsset(AssetVo assetVo); // 장비 정보 수정

    void deleteAsset(AssetVo assetVo); // 장비 삭제


}
