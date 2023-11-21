package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper {
    List<AssetVo> getAssetList();

    void insertAsset(AssetVo assetVo); // 장비 등록
    void insertModel(AssetVo assetVo); // 신규 납품 모델 등록
    AssetVo getAssetByNumber(String asset_number);

}
