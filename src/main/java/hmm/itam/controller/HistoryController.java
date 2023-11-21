package hmm.itam.controller;

import hmm.itam.service.HistoryService;
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
        return "itam/historyList"; // 실제 HTML 경로
    }

    @GetMapping("/historyAdd") // 자산 등록 화면
    public String toHistoryaddPage()
    {
        return "/itam/historyAdd";
    }

    @PostMapping("/historyAdd") // 자산 등록 입력 처리
    public String historyadd(HistoryVo historyVo) {
        try {
            HistoryService.historyAdd(historyVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/historyadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/historyadd?error_code=-99";
        }
        return "redirect:historyResult"; // 자산 등록 후 보여질 화면
    }
    @GetMapping("/historyResult") // 자산 등록 후 화면
    public String historyResult() {
        return "/itam/historyResult";
    }


}
