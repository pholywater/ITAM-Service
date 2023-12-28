package hmm.itam.controller;


import hmm.itam.dto.StatusAssetStatus;
import hmm.itam.dto.StatusAssetUsage;
import hmm.itam.dto.StatusType;
import hmm.itam.service.AssetService;
import hmm.itam.vo.AssetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public String toAssetAddPage(AssetVo assetVo) {
        return "/itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리
    public String assetAdd(AssetVo assetVo, String assetNumber, Model model) {
        if (assetNumber == null || assetNumber.isEmpty() || assetNumber.isBlank()) {
            System.out.println("NullPointerException err : " + assetNumber); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + assetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + assetNumber.isBlank()); // "   "공백 입력 체크
            return "redirect:/assetAdd"; // null & 빈 값 처리 반환
        }
        try {
            AssetService.assetAdd(assetVo);
            System.out.println("controll.check.assetNumber.addComplete:" + assetNumber);
        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException err : " + assetNumber); // 중복값 입력 확인
            return "redirect:/assetAdd"; // 장비 추가 중복값 처리 반환
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception err" + assetNumber); // 중복값 입력 확인
            return "redirect:/assetAdd"; // assetadd?error_code=-99";
        }
        model.addAttribute("asset", assetVo);
        return "itam/asset/assetResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/assetSearch") // 자산 등록 후 화면
    public String searchPage(AssetVo assetVo) {
        return "/itam/asset/assetSearch";
    }

    @PostMapping("/assetSearch") // 자산 내역 검색 및 수정 화면
    public String searchResult(AssetVo assetVo, String assetNumber, Model model){
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        if (assetNum == null) { // 관리번호 일치 항목 없을 경우 에러 처리
            return "redirect:assetSearch";
        }
        model.addAttribute("asset", assetNum);
        return "itam/asset/assetResult"; //
    }

    @ModelAttribute("statusType")
    public StatusType[] statusType() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return StatusType.values();
    }

    @ModelAttribute("statusAssetStatus")
    public StatusAssetStatus[] statusAssetStatus() {
        return StatusAssetStatus.values();
    }

    @ModelAttribute("statusAssetUsage")
    public StatusAssetUsage[] statusAssetUsage() {
        return StatusAssetUsage.values();
    }


    @PostMapping("/assetUpdate") // 자산 수정 및 이후 수정 후 화면 처리
    public String updatePage(AssetVo assetVo,String asset_number, Model model) {
        /*AssetService.modifyInfo(assetVo);*/
        AssetVo asset = AssetService.assetSearch(asset_number);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기
        return "itam/asset/assetUpdate";
    }

    @PostMapping("/assetUpdateResult") // 자산 내역 검색 및 수정 후 결과 화면
    public String modifyInfo(AssetVo assetVo, String assetNumber, Model model){
        AssetService.modifyInfo(assetVo);
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetNum);
        return "itam/asset/assetResult";
    }

    @PostMapping("/assetLogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/assetDelete")
    public String delete(String assetNumber, Model model) {
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetNum);
        AssetService.withdraw(assetNum);
        return "itam/asset/assetResult";
    }
}
