package hmm.itam.service;

import hmm.itam.mapper.AssetMapper;
import hmm.itam.vo.AssetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetMapper AssetMapper;

    public List<AssetVo> getAssetList() {

        return AssetMapper.getAssetList();
    }

    /*장비 정보 등록*/
    public void assetAdd(AssetVo assetVo){
        AssetMapper.insertAsset(assetVo);
       /* AssetMapper.insertModel(assetVo);*/
    }


    /*홈 화면 정보 확인*/
    public AssetVo getAssetByNumber(String asset_number) {

        return AssetMapper.getAssetByNumber(asset_number);
    }


}
