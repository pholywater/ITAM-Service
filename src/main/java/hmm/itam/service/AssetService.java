package hmm.itam.service;

import hmm.itam.mapper.AssetMapper;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.UserVo;
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
    public AssetVo getAssetById(Long id) { return AssetMapper.getAssetById(id); }

    /*메인 로그인 화면 아이디, 패스워드 확인 처리 작업*/
    public Long search(String asset_number) {

        AssetVo assetVo = AssetMapper.getAssetByAssetnumber(asset_number);

        /* NullPointerException 처리(관리번호 없음 및 빈 값) */
        if (assetVo == null){
            return null;
        }

        if (assetVo.getAsset_number().equals(asset_number)){
            return assetVo.getIdx();
/*
        System.out.println("service.check.hmm_id :"+ hmm_id);
        System.out.println("service.check.password :"+ password);
        System.out.println("service.check.vo.password :"+ userVo.getPassword());
        System.out.println("service.check.getIdx :"+ userVo.getIdx());
*/
        }
        return null;
    }

}
