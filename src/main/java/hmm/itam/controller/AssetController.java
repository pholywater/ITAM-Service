package hmm.itam.controller;

import hmm.itam.service.AssetService;
import hmm.itam.vo.AssetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Name;
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
    public String toAssetaddPage(AssetVo assetVo) {

        return "/itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리
    public String assetadd(AssetVo assetVo, Model model) {
        try {
            AssetService.assetAdd(assetVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/assetadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/assetadd?error_code=-99";
        }
        model.addAttribute("asset", assetVo);
        return "itam/asset/assetResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/assetSearch") // 자산 등록 후 화면
    public String SearchPage(AssetVo assetVo) {
        return "/itam/asset/assetSearch";
    }

    @PostMapping("/assetSearch") // 자산 내역 검색 및 수정 화면
    public String assetSearch(AssetVo assetVo, String asset_number, Model model){
        AssetVo assetSearch = AssetService.assetSearch(asset_number);
        model.addAttribute("asset", assetSearch);
        return "itam/asset/assetResult"; //
    }

    @PostMapping("/assetUpdate") // 자산 수정 및 이후 수정 후 화면 처리
    public String modifyInfo(AssetVo assetVo, Model model) {
        AssetService.modifyInfo(assetVo);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기
        return "itam/asset/assetResult";
    }

    @PostMapping("/assetLogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/assetDelete")
    public String delete(String asset_number, Model model) {
        AssetVo assetVo = AssetService.assetSearch(asset_number);
        model.addAttribute("asset", assetVo);
        AssetService.withdraw(assetVo);
        return "itam/asset/assetResult";
    }
}
