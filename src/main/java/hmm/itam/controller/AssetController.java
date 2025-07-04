package hmm.itam.controller;

import hmm.itam.dto.*;
import hmm.itam.service.AssetService;
import hmm.itam.vo.AssetVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@Slf4j
public class AssetController {

    @Autowired
    private AssetService AssetService;


    @GetMapping("/assetList")
    public String getAssetList(HttpSession session,
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
            log.info("(/assetList) 초기화 navSearch: {}", dto.getNavSearch());
            log.info("(/assetList) 초기화 navSearchHistory: {}", dto.getNavSearch());
            String tableName;
            switch (viewType) {
                case "All":
                    tableName = "asset_list_all";
                    break;
                case "BusanInventory":
                    tableName = "asset_list_busan_inventory";
                    break;
                case "Input":
                    tableName = "asset_list_input";
                    break;
                case "InputL":
                    tableName = "asset_list_input_l";
                    break;
                case "InputM":
                    tableName = "asset_list_input_m";
                    break;
                case "New":
                    tableName = "asset_list_new";
                    break;
                case "Output":
                    tableName = "asset_list_output";
                    break;
                case "OutputWork":
                    tableName = "asset_list_output_work";
                    break;
                case "Public":
                    tableName = "asset_list_public";
                    break;
                case "Rent":
                    tableName = "asset_list_rent";
                    break;
                case "UpdateToday":
                    tableName = "asset_list_update_today";
                    break;
                case "Work":
                    tableName = "asset_list_work";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid history viewType: " + viewType);
            }

            session.setAttribute("viewType", viewType.trim());
            model.addAttribute("viewType", viewType.trim());
            log.info("(getAssetList) 장비 조회 검색어(viewType) 확인: {}", viewType);
            session.setAttribute("tableName", tableName.trim());
            model.addAttribute("tableName", tableName.trim());
            log.info("(getAssetList) 장비 조회 검색어(tableName) 확인: {}", tableName);
        } else {
            log.warn("(getAssetList) viewType 값이 null입니다. 세션에 저장하지 않습니다.");
            log.warn("(getAssetList) tableName 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (searchType != null) {
            session.setAttribute("searchType", searchType.trim());
            model.addAttribute("searchType", searchType.trim());
            log.info("(getAssetList) 장비 조회 검색어(searchType) 확인: {}", searchType);
        } else {
            log.warn("(getAssetList) searchType 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (search != null) {
            session.setAttribute("search", search.trim());
            model.addAttribute("search", search.trim());
            log.info("(getAssetList) 장비 조회 검색어(search) 확인: {}", search);
        } else {
            log.warn("(getAssetList) search 값이 null입니다. 세션에 저장하지 않습니다.");
        }
        if (searchStart != null) {
            session.setAttribute("searchStart", searchStart.trim());
            model.addAttribute("searchStart", searchStart.trim());
            log.info("(getAssetList) 장비 조회 검색어(searchStart) 확인: {}", searchStart);
        } else {
            log.warn("(getAssetList) searchStart 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (searchEnd != null) {
            session.setAttribute("searchEnd", searchEnd.trim());
            model.addAttribute("searchEnd", searchEnd.trim());
            log.info("(getAssetList) 장비 조회 검색어(searchEnd) 확인: {}", searchEnd);
        } else {
            log.warn("(getAssetList) searchEnd 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        return "itam/asset/assetList";
    }

    @PostMapping("/assets")
    @ResponseBody
    public PageDto<List<String>> getAssetSearch(@RequestBody PageDto<?> dto, HttpSession session) {

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
        return AssetService.findAssetByPagination(dto);
    }


    @GetMapping("/searchAssetPage") // 자산 등록 후 화면
    public String searchAssetDetailPage(AssetVo assetVo, Model model) {
        log.info("장비 리스트 조회 화면입니다.");

        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetList";
    }

    @PostMapping("/headerSearch") // 상단 해더 조회 (25.07.04)
    public String searchAssetDetail(AssetVo assetVo, HttpSession session, String searchType, String search, String navSearch, Model model) {
        log.info("searchType : {}", searchType);
        log.info("navSearch : {}", navSearch);
        log.info("search : {}", search);
        search = navSearch;

        /*상단 검색에서 장비 조회 시*/
/*        if (Objects.equals(searchType, "assetAll")) {
            log.info("장비 전체 리스트 조회");
            List<AssetVo> searchAssetList = AssetService.searchAssetList(search, searchType);
            model.addAttribute("list", searchAssetList);
            return "itam/asset/assetList"; //
        }*/


        /*상단 검색에서 history 이력 관리 조회 시*/
        if (Objects.equals(searchType, "history")) {
            /*상단 해더 조회의 경우 viewType 값 초기화 진행*/
            String viewType = "";
            session.setAttribute("viewType", viewType);
            model.addAttribute("viewType", viewType);
            session.setAttribute("navSearch", navSearch);
            model.addAttribute("navSearch", navSearch);
            session.setAttribute("navSearchHistory", navSearch);
            model.addAttribute("navSearchHistory", navSearch.trim());
            model.addAttribute("searchType", searchType.trim());

            log.info("Asset history 해더 검색창 viewType : {}", viewType);
            log.info("Asset history 해더 검색창 navSearch : {}", navSearch);
            log.info("Asset history 해더 검색창 searchType : {}", searchType);

            return "redirect:/historyList";
            /*return "itam/history/historySearch";*/
        }

        /*상단 검색에서 serverSide 백앤드 장비 조회 시*/
        if (Objects.equals(searchType, "serverSide")) {
            /*상단 해더 조회의 경우 viewType 값 초기화 진행*/
            String viewType = "";
            session.setAttribute("viewType", viewType);
            model.addAttribute("viewType", viewType);
            session.setAttribute("navSearch", navSearch);
            model.addAttribute("navSearch", navSearch);
            session.setAttribute("navSearchHistory", navSearch);
            model.addAttribute("navSearchHistory", navSearch.trim());
            model.addAttribute("searchType", searchType.trim());

            log.info("Asset history 해더 검색창 viewType : {}", viewType);
            log.info("Asset history 해더 검색창 navSearch : {}", navSearch);
            log.info("Asset history 해더 검색창 searchType : {}", searchType);

            return "redirect:/assetList";

            /*return "itam/asset/headerSearchList"; // html 불러온 후 js ajax 호출*/
        }

        /*빈 값 입력 시*/
/*        if (search == "") {
            log.info("검색 창 빈값 처리");
            return "redirect:assetUpdateToday";
        }*/

        /*상단 통합 검색에서 Client 장비 조회 시*/
        if (Objects.equals(searchType, "easySearch")) {
            log.info("통합 조회 : {}", navSearch);
            search = navSearch;
            /*조회 한 값 넘겨주기*/
            List<AssetVo> searchAssetDetail = AssetService.searchAssetList(search, searchType);
            model.addAttribute("list", searchAssetDetail);
        }


        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetList"; //
    }

    @GetMapping("/searchMemberUpdatePage") // 부서 및 직원 장비 리스트 조회 화면
    public String searchMemberListUpdatePage(AssetVo assetVo, Model model) {
        log.info("직원 장비 검색 조회 화면입니다.");

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/searchMemberList";
    }

    @PostMapping("/searchMemberList") // 부서 및 직원 장비 리스트 조회 화면(처리)
    public String searchMemberList(AssetVo assetVo, String searchMember, Model model) {
        log.info("searchMember : {}", searchMember);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchMemberList = AssetService.searchMemberList(searchMember);
        model.addAttribute("list", searchMemberList);
        return "itam/asset/searchMemberList"; //
    }

    @GetMapping("/searchMemberUpdate") // 부서 및 직원 장비 리스트 조회 화면(처리)
    public String searchMemberUpdate(AssetVo assetVo, String searchMember, Model model, String updateCheck, String assetNumber) {
        log.info("searchMember : {}", searchMember);
        log.info("업데이트 체크박스 : {}", updateCheck);
        log.info("업데이트 장비번호 : {}", assetNumber);
        if (Objects.equals(updateCheck, "on")) {
            log.info("업데이트 장비번호 : {}", assetNumber);
            AssetService.modifyInfo(assetVo);
            AssetVo assetNum = AssetService.assetSearch(assetNumber);
            model.addAttribute("asset", assetNum);
            log.info("업데이트 체크박스 정보를 변경 하였습니다.");
        } else {
            log.info("정보를 변경 하지 않았습니다.");
        }

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchMemberList = AssetService.searchMemberList(searchMember);
        model.addAttribute("list", searchMemberList);
        return "itam/asset/searchMemberList"; //
    }

    @GetMapping("/searchPaymentList") // 장비 지급일 리스트 조회 화면(처리)
    public String searchPaymentList(AssetVo assetVo, String searchStart, String searchEnd, Model model) {
        log.info("searchStart : {}", searchStart);
        log.info("searchEnd : {}", searchEnd);
        log.info("장비 지급일 리스트 조회 화면입니다.");
        /*조회 한 값 넘겨주기*/
        List<AssetVo> assetPaymentList = AssetService.assetPaymentList(searchStart, searchEnd);
        model.addAttribute("list", assetPaymentList);
        if (Objects.equals(searchStart, "change")) {
            /*return "itam/asset/searchAssetChangeList";*/
            return "itam/asset/searchPaymentList";
        }
        return "itam/asset/searchPaymentList";
    }

    @GetMapping("/searchNewPaymentList") // 신규 장비 출고 리스트 조회 화면(처리)
    public String searchNewPaymentList(AssetVo assetVo, String searchStart, String searchEnd, Model model) {
        log.info("신규 장비 출고 리스트 조회 화면입니다.");
        log.info("searchStart : {}", searchStart);
        log.info("searchEnd : {}", searchEnd);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> assetPaymentList = AssetService.assetPaymentList(searchStart, searchEnd);
        model.addAttribute("list", assetPaymentList);
        return "itam/asset/searchNewPaymentList"; //
    }

    @GetMapping("/assetAdd") // 자산 등록 화면
    public String toAssetAddPage(AssetVo assetVo, Model model) {
        /* datalist 직원(사번) 검색 자동완성 */
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);
        log.info("장비 등록 화면입니다.");
        return "itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리(간단하게 정리해야함)
    public String assetAdd(AssetVo assetVo, String assetNumber, Model model) {
        if (assetNumber == null || assetNumber.isEmpty() || assetNumber.isBlank()) {
            System.out.println("NullPointerException err : " + assetNumber); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + assetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + assetNumber.isBlank()); // "   "공백 입력 체크
            return "redirect:assetAdd"; // null & 빈 값 처리 반환
        }
        try {
            AssetService.assetAdd(assetVo);
            System.out.println("controll.check.assetNumber.addComplete:" + assetNumber);
        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException err : " + assetNumber); // 중복값 입력 확인
            return "redirect:assetAdd"; // 장비 추가 중복값 처리 반환
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception err" + assetNumber); // 중복값 입력 확인
            return "redirect:assetAdd"; // assetadd?error_code=-99";
        }
        model.addAttribute("asset", assetVo);
        return "itam/asset/assetResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/assetSearch") // 자산 등록 후 화면 & 검색 화면(조회를 먼저 하고 수정하는 방향)
    public String searchPage(AssetVo assetVo, Model model) {
        log.info("장비 정보 조회 화면입니다.");
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);
        return "itam/asset/assetSearch";
    }

    @PostMapping("/assetSearch") // 자산 내역 검색 및 수정 처리 화면
    public String searchResult(AssetVo assetVo, String assetNumber, String memberId, Model model) {
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);

        if (assetNum == null) { // 관리번호 일치 항목 없을 경우 에러 처리
            log.info("조회하기 : 일치하는 관리번호 없음");
            return "redirect:assetSearch";
        }
        model.addAttribute("asset", assetNum);
        log.info("장비 정보를 조회합니다. 관리번호 : {}", assetNum.getAssetNumber());

        log.info("사번 정보 : {}", assetNum.getMemberId());
        String search = assetNum.getMemberId();
        String searchType = "memberId";
        log.info("search : {}", search);
        log.info("searchType : {}", searchType);
        List<AssetVo> searchAssetList = AssetService.searchAssetList(search, searchType);
        model.addAttribute("list", searchAssetList);


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

    @ModelAttribute("AssetSupplies")
    public AssetSupplies[] assetSupplies() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return AssetSupplies.values();
    }

    @PostMapping("/assetUpdate") // 장비 수정 작업 화면
    public String updatePage(AssetVo assetVo, String assetNumber, Model model) {
        /*AssetService.modifyInfo(assetVo);*/
        AssetVo asset = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기
        log.info("장비 정보 수정 화면입니다.");

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetUpdate";
    }

    @PostMapping("/assetUpdateResult") // 자산 내역 검색 및 수정 후 결과 화면 + 이력관리 체크박스 검증 및 추가
    public String modifyInfo(AssetVo assetVo, String assetNumber, Model model, String historyCheck) {
        AssetService.modifyInfo(assetVo);
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetNum);
        log.info("이력 관리 체크박스 : {}", historyCheck);
        if (Objects.equals(historyCheck, "on")) {
            AssetService.historyAdd(assetVo);
            log.info("이력 관리 정보를 추가 하였습니다.");
        } else {
            log.info("이력 관리 정보를 추가 하지 않았습니다.");
        }
        log.info("장비 정보를 수정하였습니다. 관리번호 : {}", assetNum.getAssetNumber());

        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);

        AssetVo asset = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기


        return "itam/asset/assetSearch";
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
        return "redirect:assetSearch";
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // 아무것도 반환하지 않음 (404 방지)
    }

}


