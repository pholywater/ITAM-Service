package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper {
    List<AssetVo> getAssetList();
    AssetVo getAssetByAssetNumber(String assetNumber); // 관리번호로 검색

    void insertAsset(AssetVo assetVo); // 장비 등록

    void updateAsset(AssetVo assetVo); // 장비 정보 수정

    void deleteAsset(AssetVo assetVo); // 장비 삭제
}
