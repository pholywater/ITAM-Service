package hmm.itam.controller;

import hmm.itam.service.MemberService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import hmm.itam.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private MemberService MemberService;

    @GetMapping("/memberList") // 직원 리스트
    public String getMemberList(Model model) {
        List<MemberVo> memberList = MemberService.getMemberList();
        /*System.out.println(memberList);*/
        log.info("직원 리스트 조회");
        model.addAttribute("list", memberList);
        return "itam/member/memberList"; // 실제 HTML 경로
    }

    @GetMapping("/departmentList") // 부서 리스트 불러오기
    public String getDepartmentList(Model model) {
        List<MemberVo> departmentList = MemberService.getDepartmentList();
        model.addAttribute("list", departmentList);
        return "itam/member/departmentList";
    }

    @GetMapping("/memberAdd") // 멤버 등록 화면
    public String toMemberaddPage(Model model) {
        /*datalist 멤버 검색 자동완성*/
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*datalist 부서 검색 자동완성*/
        List<MemberVo> departmentList = MemberService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        return "itam/member/memberAdd";
    }

    @PostMapping("/memberAdd") // 멤버 등록 입력 처리
    public String memberAdd(MemberVo memberVo, Model model, String memberId) {
        if (memberId == null || memberId.isEmpty() || memberId.isBlank()) {
            /*System.out.println("NullPointerException err : " + memberId); // null 값 입력 확인
            System.out.println("memberId.isEmpty() : " + memberId.isEmpty()); // "" 빈 값 입력
            System.out.println("memberId.isBlank() : " + memberId.isBlank()); // "   "공백 입력 체크
            */
            log.info("입력 값 없음 오류");
            return "redirect:memberAdd"; // null & 빈 값 처리 반환
        }
        try {
            MemberService.memberAdd(memberVo);
        } catch (DuplicateKeyException e) {
            log.info("중복 값 오류");
            /*return "redirect:/memberAdd?error_code=-1";*/
            return "redirect:memberAdd";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("기타 오류");
            /*return "redirect:/memberAdd?error_code=-99";*/
            return "redirect:memberAdd";
        }
        model.addAttribute("member", memberVo);
        return "itam/member/memberAdd"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/memberSearch") // 자산 등록 후 화면
    public String memberSearchPage(MemberVo memberVo, Model model) {
        log.info("멤버 정보 조회 화면입니다.");
        /*datalist 멤버 검색 자동완성*/
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("memberSearch", memberList);

        return "itam/member/memberSearch";
    }

    @PostMapping("/memberSearch") // 자산 내역 검색 및 수정 처리 화면
    public String memberSearch(MemberVo memberVo, String memberId, Model model) {
        /*datalist 멤버 검색 자동완성*/
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("memberSearch", memberList);

        MemberVo memberIdCheck = MemberService.memberSearch(memberId);
        if (memberIdCheck == null) { // 사원번호 일치 항목 없을 경우 에러 처리
            log.info("조회하기 : 일치하는 아이디 없음");
            return "redirect:memberSearch";
        }
        model.addAttribute("member", memberIdCheck);
        log.info("멤버 정보를 조회합니다. 사원번호 : {}", memberIdCheck.getMemberId());
        return "itam/member/memberResult"; //

    }

    @PostMapping("/memberUpdate") // 장비 수정 작업 화면
    public String updatePage(String memberId, Model model) {
        /*datalist 멤버 검색 자동완성*/
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("memberSearch", memberList);

        /*datalist 부서 검색 자동완성*/
        List<MemberVo> departmentList = MemberService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        MemberVo memberUpdate = MemberService.memberSearch(memberId);
        if (memberUpdate == null) { // 사원번호 일치 항목 없을 경우 에러 처리
            log.info("조회하기 : 일치하는 아이디 없음");
            return "redirect:memberSearch";
        }

        model.addAttribute("member", memberUpdate);  // 멤버 아이디로 정보 가져오기
        log.info("현재 직원 정보 내역입니다.");
        System.out.println(memberUpdate);
        return "itam/member/memberUpdate";
    }

    @PostMapping("/memberUpdateResult") // 장비 수정 작업 화면
    public String updateResultPage(String memberId, MemberVo memberVo, Model model) {
        MemberService.modifyInfo(memberVo); // 업데이트 처리
        log.info("직원 정보 업데이트를 완료 하였습니다.");

        log.info("수정 완료 화면에 표시될 정보 page 전달");
        MemberVo memberUpdate = MemberService.memberSearch(memberId);
        model.addAttribute("member", memberUpdate);  // 멤버 아이디로 정보 가져오기

        log.info("변경 된 직원 정보 내역입니다.");
        System.out.println(memberVo); //

        /*datalist 멤버 검색 자동완성*/
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("memberSearch", memberList);

        return "itam/member/memberResult";
    }

}
