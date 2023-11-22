package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper {
    List<AssetVo> getAssetList();
    AssetVo getAssetById(Long id); // 장비 검색
    AssetVo getAssetByAssetnumber(String asset_number); // 관리번호로 검색

    void insertAsset(AssetVo assetVo); // 장비 등록



}
