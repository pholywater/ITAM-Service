package hmm.itam.controller;

import hmm.itam.dto.AssetSupplies;
import hmm.itam.dto.PageDto;
import hmm.itam.service.HistoryService;
import hmm.itam.service.MemberService;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@Slf4j
public class HistoryController {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/historyList")
    public String getHistoryList(HttpSession session,
                                 PageDto dto,
                                 @RequestParam(value = "navSearch", required = false) String navSearch,
                                 @RequestParam(value = "navSearchHistory", required = false) String navSearchHistory,
                                 @RequestParam(value = "searchType", required = false) String searchType,
                                 @RequestParam(value = "viewType", required = false) String viewType,
                                 String search,
                                 @RequestParam(value = "searchStart", required = false) String searchStart,
                                 @RequestParam(value = "searchEnd", required = false) String searchEnd,
                                 Model model) {


        if (viewType != null && !viewType.isEmpty()) {
            session.setAttribute("navSearch", null); // 세션에서 초기화
            session.setAttribute("navSearchHistory", null); // 세션에서 초기화
            dto.setNavSearch(null); // DTO에서 초기화
            dto.setNavSearchHistory(null); // DTO에서 초기화
            log.info("(/historyList) 초기화 navSearch: {}", dto.getNavSearch());
            log.info("(/historyList) 초기화 navSearchHistory: {}", dto.getNavSearch());
            String tableName;
            switch (viewType) {
                case "All":
                    tableName = "history_list_all";
                    break;
                case "Asset":
                    tableName = "history_list_asset";
                    break;
                case "Change":
                    tableName = "history_list_change";
                    break;
                case "Consumables":
                    tableName = "history_list_consumables";
                    break;
                case "Input":
                    tableName = "history_list_input";
                    break;
                case "Output":
                    tableName = "history_list_output";
                    break;
                case "Repair":
                    tableName = "history_list_repair";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid history viewType: " + viewType);
            }

            session.setAttribute("viewType", viewType.trim());
            model.addAttribute("viewType", viewType.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(viewType) 확인: {}", viewType);
            session.setAttribute("tableName", tableName.trim());
            model.addAttribute("tableName", tableName.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(tableName) 확인: {}", tableName);
        } else {
            log.warn("(getHistoryList) viewType 값이 null입니다. 세션에 저장하지 않습니다.");
            log.warn("(getHistoryList) tableName 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (searchType != null) {
            session.setAttribute("searchType", searchType.trim());
            model.addAttribute("searchType", searchType.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchType) 확인: {}", searchType);
        } else {
            log.warn("(getHistoryList) searchType 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (search != null) {
            session.setAttribute("search", search.trim());
            model.addAttribute("search", search.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(search) 확인: {}", search);
        } else {
            log.warn("(getHistoryList) search 값이 null입니다. 세션에 저장하지 않습니다.");
        }
        if (searchStart != null) {
            session.setAttribute("searchStart", searchStart.trim());
            model.addAttribute("searchStart", searchStart.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchStart) 확인: {}", searchStart);
        } else {
            log.warn("(getHistoryList) searchStart 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (searchEnd != null) {
            session.setAttribute("searchEnd", searchEnd.trim());
            model.addAttribute("searchEnd", searchEnd.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchEnd) 확인: {}", searchEnd);
        } else {
            log.warn("(getHistoryList) searchEnd 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        return "itam/history/historyList";
    }

    @PostMapping("/api/historySearch")
    @ResponseBody
    public PageDto<List<String>> getHistorySearch(@RequestBody PageDto<?> dto, HttpSession session) {

        if (dto.getSearchType() == null || dto.getSearchType().isEmpty()) {
            dto.setSearchType((String) session.getAttribute("searchType"));
        }

        if (dto.getViewType() == null || dto.getViewType().isEmpty()) {
            dto.setViewType((String) session.getAttribute("viewType"));
            if (dto.getNavSearchHistory() == null || dto.getNavSearchHistory().isEmpty()) {
                dto.setNavSearch((String) session.getAttribute("navSearchHistory"));
                dto.setNavSearchHistory((String) session.getAttribute("navSearchHistory"));
                log.info("(/api/historySearch) viewType null navSearch: {}", dto.getNavSearch());
            }
        }

        if (dto.getViewType() != null && !dto.getViewType().isEmpty()) {
            // viewType이 null이 아니고 비어 있지 않을 때 실행
            session.setAttribute("navSearch", null); // 세션에서 초기화
            session.setAttribute("navSearchHistory", null); // 세션에서 초기화
            dto.setNavSearch(null); // DTO에서 초기화
            dto.setNavSearchHistory(null); // DTO에서 초기화
            log.info("(/api/historySearch) 초기화 navSearch: {}", dto.getNavSearch());
            log.info("(/api/historySearch) 초기화 navSearchHistory: {}", dto.getNavSearch());
        }

        if (dto.getTableName() == null || dto.getTableName().isEmpty()) {
            dto.setTableName((String) session.getAttribute("tableName"));
        }

        // 로그 출력
        log.info("(/api/historySearch) 컨트롤러 최종 로그");
        log.info("(/api/historySearch) draw: {}", dto.getDraw());
        log.info("(/api/historySearch) start: {}", dto.getStart());
        log.info("(/api/historySearch) length: {}", dto.getLength());
        log.info("(/api/historySearch) search: {}", dto.getSearch());
        log.info("(/api/historySearch) navSearch: {}", dto.getNavSearch());
        log.info("(/api/historySearch) navSearchHistory: {}", dto.getNavSearchHistory());
        log.info("(/api/historySearch) searchType: {}", dto.getSearchType());
        log.info("(/api/historySearch) viewType: {}", dto.getViewType());
        log.info("(/api/historySearch) tableName: {}", dto.getTableName());
        log.info("(/api/historySearch) orderColumn: {}", dto.getOrderColumn());
        log.info("(/api/historySearch) orderDir: {}", dto.getOrderDir());
        log.info("(/api/historySearch) searchStart: {}", dto.getSearchStart());
        log.info("(/api/historySearch) searchEnd: {}", dto.getSearchEnd());

        // 서비스 계층 호출
        return historyService.findHistoryByPagination(dto);
    }


    @GetMapping("/historyAdd") // 자산 이력 등록 화면
    public String toHistoryAddPage(HistoryVo historyVo, Model model) {

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = historyService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*datalist 장비번호 검색 자동완성*/
        List<HistoryVo> assetList = historyService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "itam/history/historyAdd";
    }

    @GetMapping("/historyAddRepair") // 자산 이력 고장 등록 화면
    public String toHistoryAddRepairPage(HistoryVo historyVo, Model model) {

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = historyService.getMemberList();
        model.addAttribute("memberList", memberList);

        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = historyService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "itam/history/historyAddRepair";
    }


    @ModelAttribute("AssetSupplies")
    public AssetSupplies[] assetSupplies() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return AssetSupplies.values();
    }

    @PostMapping("/historyAdd")
    public String historyAdd(@ModelAttribute HistoryVo historyVo, Model model) {

        log.debug("memberService is null? {}", memberService == null);
        log.debug("historyVo is null? {}", historyVo == null);
        if (historyVo != null) {
            log.debug("historyMemberId: {}", historyVo.getHistoryMemberId());
        }

        // datalist용 정보 조회
        model.addAttribute("memberList", historyService.getMemberList());
        model.addAttribute("assetList", historyService.getAssetList());

        // 필수 입력값 검증
        if (isNullOrBlank(historyVo.getHistoryAssetNumber()) || isNullOrBlank(historyVo.getHistoryMemberId())) {
            model.addAttribute("error", "자산 번호와 실사용자 정보를 모두 입력해주세요.");
            return "itam/history/historyAdd";
        }

        // 서비스 null 체크
        if (memberService == null) {
            model.addAttribute("error", "memberService가 초기화되지 않았습니다.");
            return "itam/history/historyAdd";
        }

        // 실사용자 유효성 검사
        if (!memberService.isValidMemberId(historyVo.getHistoryMemberId())) {
            model.addAttribute("error", "입력한 실사용자 정보가 존재하지 않습니다. 다시 확인해주세요.");
            return "itam/history/historyAdd";
        }

        try {
            historyService.addHistory(historyVo);
            model.addAttribute("message", "자산 이력이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("자산 이력 등록 중 오류 발생", e);
            model.addAttribute("error", "자산 이력 등록 중 오류가 발생했습니다.");
        }

        List<HistoryVo> resultList = historyService.findByAssetNumber(historyVo.getHistoryAssetNumber());
        model.addAttribute("list", resultList);

        return "itam/history/historyAdd";
    }


    // 공백 및 null 체크 유틸 메서드
    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }


    /*@GetMapping("/historyEdit")
    public String editHistory(@RequestParam("idx") Long idx, Model model) {
        HistoryVo history = historyService.findByIdx(idx);
        model.addAttribute("historyVo", history);

        model.addAttribute("memberList", historyService.getMemberList());
        model.addAttribute("assetList", historyService.getAssetList());

        return "itam/history/historyEdit"; // 수정용 폼 페이지
    }
*/
    @GetMapping("/historyEdit")
    public String editHistory(@RequestParam("idx") Long idx, Model model) {
        HistoryVo history = historyService.findByIdx(idx);
        model.addAttribute("historyVo", history);

        model.addAttribute("memberList", historyService.getMemberList());
        model.addAttribute("assetList", historyService.getAssetList());

        // ✅ 리스트 추가 (수정 대상 자산 번호 기준)
        if (history.getHistoryAssetNumber() != null) {
            List<HistoryVo> resultList = historyService.findByAssetNumber(history.getHistoryAssetNumber());
            model.addAttribute("list", resultList);
        }

        return "itam/history/historyEdit";
    }


    @PostMapping("/historyUpdate")
    public String updateHistory(@ModelAttribute HistoryVo historyVo, Model model, RedirectAttributes redirectAttributes) {

        // 유효성 검사
        if (isNullOrBlank(historyVo.getHistoryAssetNumber()) || isNullOrBlank(historyVo.getHistoryMemberId())) {
            model.addAttribute("error", "자산 번호와 실사용자 정보를 모두 입력해주세요.");
            model.addAttribute("memberList", historyService.getMemberList());
            model.addAttribute("assetList", historyService.getAssetList());
            return "itam/history/historyEdit";
        }

        if (!memberService.isValidMemberId(historyVo.getHistoryMemberId())) {
            model.addAttribute("error", "입력한 실사용자 정보가 존재하지 않습니다.");
            model.addAttribute("memberList", historyService.getMemberList());
            model.addAttribute("assetList", historyService.getAssetList());
            return "itam/history/historyEdit";
        }

        try {
            historyService.updateHistory(historyVo);
            redirectAttributes.addFlashAttribute("message", "자산 이력(Code: " + historyVo.getIdx() + ")이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            log.error("자산 이력 수정 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "자산 이력(Code: " + historyVo.getIdx() + ") 수정 중 오류가 발생했습니다.");
            return "redirect:/historyEdit?idx=" + historyVo.getIdx(); // 오류 시에도 리다이렉트
        }

// 수정 후 등록 화면으로 이동하면서 리스트 유지
        redirectAttributes.addFlashAttribute("historyVo", historyVo);
        return "redirect:/historyEdit?idx=" + historyVo.getIdx();

    }


    @PostMapping("/historyDelete")
    public String deleteHistory(@RequestParam("idx") Long idx,
                                @ModelAttribute HistoryVo historyVo,
                                Model model) {
        try {
            historyService.deleteByIdx(idx);
            model.addAttribute("message", "자산 이력(Code: " + idx + ")이 삭제되었습니다.");
        } catch (Exception e) {
            log.error("삭제 중 오류 발생", e);
            model.addAttribute("error", "자산 이력(Code: " + idx + ") 삭제 중 오류가 발생했습니다.");
        }


        // ✅ 삭제 후에도 입력값 유지
        model.addAttribute("historyVo", historyVo);

        // ✅ 리스트 재조회 (삭제된 항목 반영됨)
        List<HistoryVo> resultList = historyService.findByAssetNumber(historyVo.getHistoryAssetNumber());
        model.addAttribute("list", resultList);

        // ✅ datalist 재조회
        model.addAttribute("memberList", historyService.getMemberList());
        model.addAttribute("assetList", historyService.getAssetList());

        return "itam/history/historyAdd"; // redirect 대신 직접 뷰 반환
    }


}