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
import java.util.List;

@Controller
@Slf4j
public class AssetController {

    @Autowired
    private AssetService AssetService;

    @GetMapping("/assetList") // Service 처리로 변경(24.02) 미사용
    public String getAssetList(Model model) {
        //List<AssetVo> assetList = AssetService.getAssetList();
        //System.out.println(assetList);
        //model.addAttribute("list", assetList);
        return "itam/asset/assetList";
    }


    @GetMapping("/headerSearch") // 해더 드롭다운 href Server-Side 검색
    public String HeaderSearch(HttpSession session, String navSearch, Model model) {
        session.setAttribute("navSearch", navSearch);
        //model.addAttribute("Searh", "navSearch");
        //String navSearch = headerSearchDto.getNavSearch();
        //List<AssetVo> navSearchList = AssetService.navbarSearch(navSearch);

        if (navSearch == "null") {
            return "redirect:/";
        }
        log.info("드롭다운 해더 검색어 NavSearch Controller 체크 : {}", navSearch);

        return "itam/asset/headerSearchList"; // html 불러온 후 js ajax 호출
    }


    @ResponseBody // Data로 받아오는 선언
    @PostMapping("/assets") // js ajax 호출로 Data로 들어갈 PageDto 값 정의
    public PageDto getAsset(int draw, int length, int start, String sSearch, HttpSession session) {
        String navSearch = (String) session.getAttribute("navSearch");
        //String searchValue = Request["search[value]"];
        //String search = Request.Form.GetValues("search[value]").FirstOrDefault();
        //String search = Request.QueryString["(search[value])"];
        //String search = (String) session.getAttribute("search[value]");
        //String search = Request.Form.GetValues("search[value]")[0];
        //String navSearch = (String) headerSearchDto.getNavSearch();
        String search = navSearch;

        log.info("ajax: '/assets' 실행 후 js에서 받아오는 draw 값 {} ", draw);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 start 값 {} ", start);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 length 값 {} ", length);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 search 값 {} ", search);
        log.info("getNavSearch 값 {} ", navSearch);

        PageDto rs = new PageDto();

        rs.setDraw(draw);
        rs.setStart(start);
        rs.setLength(length);
        rs.setSearch(search);
        rs.setNavSearch(navSearch);


        return AssetService.findAssetByPagination(rs);
    }

    @GetMapping("/navbarSearch") // Navbar 우측 Get 클라이언트 검색
    public String navGetSearch(AssetVo assetVo, String navbarSearch, Model model) {
        List<AssetVo> navbarGetSearch = AssetService.navbarSearch(navbarSearch);
        log.info("검색어 : {}", navbarSearch);
        if (navbarGetSearch == null) { // 일치 항목 없을 경우 에러 처리
            return "redirect:/";
        }
        model.addAttribute("list", navbarGetSearch);
        return "itam/asset/navbarSearch"; //
    }

    @PostMapping("/navbarSearch") // Navbar 우측 "Post" 검색(현재 미사용)
    public String navHeaderSearch(String navSearch, Model model) {
        List<AssetVo> navbarSearch = AssetService.navbarSearch(navSearch);
        log.info("검색어 : {}", navSearch);
        if (navbarSearch == null) { // 일치 항목 없을 경우 에러 처리
            return "redirect:/";
        }
        System.out.println(navbarSearch);
        model.addAttribute("list", navbarSearch);
        return "itam/asset/navbarSearch"; //
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
    public String searchResult(AssetVo assetVo, String assetNumber, Model model) {
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
    public String updatePage(AssetVo assetVo, String asset_number, Model model) {
        /*AssetService.modifyInfo(assetVo);*/
        AssetVo asset = AssetService.assetSearch(asset_number);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기
        return "itam/asset/assetUpdate";
    }

    @PostMapping("/assetUpdateResult") // 자산 내역 검색 및 수정 후 결과 화면
    public String modifyInfo(AssetVo assetVo, String assetNumber, Model model) {
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
        return "redirect:assetSearch";
    }
}
