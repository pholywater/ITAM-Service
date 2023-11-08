package hmm.itam.service;

import hmm.itam.mapper.AssetMapper;
import hmm.itam.mapper.MemberMapper;
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
}
