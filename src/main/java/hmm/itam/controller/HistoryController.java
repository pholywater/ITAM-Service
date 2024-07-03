package hmm.itam.controller;

import hmm.itam.service.HistoryService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.time.LocalTime.now;

@Controller
@Slf4j
public class HistoryController {

    @Autowired
    private HistoryService HistoryService;


    @GetMapping("/historyList") // 웹 URL 매핑(전체 리스트 시간 오래 걸리는 단점. 조회 기간 설정 필요)
    public String getHistoryList(HistoryVo historyVo, String searchStart, String searchEnd, Model model) {

        log.info("searchStart 초기 값 : {}", searchStart);
        log.info("searchEnd 초기 값 : {}", searchEnd);


        List<HistoryVo> historyList = HistoryService.getHistoryList(searchStart, searchEnd);
        model.addAttribute("list", historyList);
        return "itam/history/historyList"; // 실제 HTML 경로
    }


    @GetMapping("/historyAdd") // 자산 등록 화면
    public String toHistoryAddPage(HistoryVo historyVo, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "/itam/history/historyAdd";
    }

    @PostMapping("/historyAdd") // 자산 입출고 관련 이력 입력 처리 // null 관련 처리 추가 해야함.
    public String historyAdd(HistoryVo historyVo, String historyAssetNumber, String historyMemberId, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);
        
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
        String search = historyAssetNumber;
        List<HistoryVo> resultList = HistoryService.historyAddResult(search);
        model.addAttribute("list", resultList);
        /*try { 입출력 이력 등록은 중복 값 체크 하지 않음
            HistoryService.historyAdd(historyVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/historyadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/historyadd?error_code=-99";
        }*/
        return "itam/history/historyAdd"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/historySearch") // 이력 조회 화면(기본 화면)
    public String historySearch(HistoryVo historyVo, Model model, String search, String searchType) {
        log.info("searchType : {}", searchType);
        log.info("search : {}", search);

        List<HistoryVo> resultList = HistoryService.historySearch(search, searchType);
        model.addAttribute("list", resultList);
        log.info("간편 조회하기 : {}", search);
        return "/itam/history/historySearch";
    }

}