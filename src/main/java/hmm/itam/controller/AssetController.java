package hmm.itam.controller;

import hmm.itam.service.AssetService;
import hmm.itam.vo.AssetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AssetController {

    @Autowired
    private AssetService AssetService;

    @GetMapping("/assetList") // 웹 URL 매핑
    public String getAssetList(Model model) {
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("list", assetList);
        return "itam/assetList"; // 실제 HTML 경로
    }
}
