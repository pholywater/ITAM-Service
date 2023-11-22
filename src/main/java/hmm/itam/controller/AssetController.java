package hmm.itam.controller;

import hmm.itam.service.AssetService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AssetController {

    @Autowired
    private AssetService AssetService;

    @GetMapping("/assetList") // 웹 URL 매핑
    public String getAssetList(Model model) {
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("list", assetList);
        return "itam/asset/assetList"; // 실제 HTML 경로
    }

    @GetMapping("/assetAdd") // 자산 등록 화면
    public String toAssetaddPage()
    {
        return "/itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리
    public String assetadd(AssetVo assetVo) {
        try {
            AssetService.assetAdd(assetVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/assetadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/assetadd?error_code=-99";
        }
        return "redirect:assetResult"; // 자산 등록 후 보여질 화면
    }
    @GetMapping("/assetResult") // 자산 등록 후 화면
    public String assetResult() {
        return "/itam/asset/assetResult";
    }


}
