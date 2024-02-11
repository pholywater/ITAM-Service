package hmm.itam.controller;

import hmm.itam.service.HistoryService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private HistoryService HistoryService;

    @GetMapping("/historyList") // 웹 URL 매핑
    public String getHistoryList(Model model) {
        List<HistoryVo> historyList = HistoryService.getHistoryList();
        model.addAttribute("list", historyList);
        return "itam/history/historyList"; // 실제 HTML 경로
    }

    @GetMapping("/historyAdd") // 자산 등록 화면
    public String toHistoryaddPage(HistoryVo historyVo)
    {
        return "/itam/history/historyAdd";
    }

    @PostMapping("/historyAdd") // 자산 입출고 관련 이력 입력 처리 // null 관련 처리 추가 해야함.
    public String historyadd(HistoryVo historyVo, String historyAssetNumber, String historyMemberId, Model model) {
        if (historyAssetNumber == null || historyAssetNumber.isEmpty() || historyAssetNumber.isBlank() ||
            historyMemberId == null || historyMemberId.isEmpty() || historyMemberId.isBlank()) {
            System.out.println("NullPointerException historyAssetNumber err : " + historyAssetNumber); // null 값 입력 확인
            System.out.println("NullPointerException historyMemberId err : " + historyMemberId); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + historyAssetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isEmpty() : " + historyMemberId.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + historyAssetNumber.isBlank()); // "   "공백 입력 체크
            System.out.println("assetNumber.isBlank() : " + historyMemberId.isBlank()); // "   "공백 입력 체크
            return "redirect:/historyAdd"; // null & 빈 값 처리 반환
        }
        HistoryService.historyAdd(historyVo);
        System.out.println("historyAssetNumber : " + historyAssetNumber);
        List<HistoryVo> resultList = HistoryService.historyAddResult(historyAssetNumber);
        model.addAttribute("list", resultList);
        /*try { 입출력 이력 등록은 중복 값 체크 하지 않음
            HistoryService.historyAdd(historyVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/historyadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/historyadd?error_code=-99";
        }*/
        return "itam/history/historyResult"; // 자산 등록 후 보여질 화면
    }
    @GetMapping("/historySearch") // 자산 등록 후 화면
    public String historySearch(HistoryVo historyVo, Model model, String historyAssetNumber) {
        return "/itam/history/historyResult";
    }


}
