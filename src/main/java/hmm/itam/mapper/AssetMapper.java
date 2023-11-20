package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper {
    List<AssetVo> getAssetList();

    void insertAsset(AssetVo assetVo); // 자산 등록
    void insertStatus(AssetVo assetVo); // 자산 등록

    AssetVo getAssetByNumber(String asset_number);

}
