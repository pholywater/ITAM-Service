package hmm.itam.controller;

import hmm.itam.service.MemberService;
import hmm.itam.vo.HistoryVo;
import hmm.itam.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService MemberService;

    @GetMapping("/memberList") // 웹 URL 매핑
    public String getMemberList(Model model) {
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("list", memberList);
        return "itam/member/memberList"; // 실제 HTML 경로
    }

    @GetMapping("/departmentList") // 부서 리스트 불러오기
    public String getDepartmentList(Model model) {
        List<MemberVo> departmentList = MemberService.getDepartmentList();
        model.addAttribute("list", departmentList);
        return "itam/member/departmentList";
    }

    @GetMapping("/memberAdd") // 자산 등록 화면
    public String toMemberaddPage() {
        return "/itam/member/memberAdd";
    }

    @PostMapping("/memberAdd") // 자산 등록 입력 처리
    public String memberAdd(MemberVo memberVo) {
        try {
            MemberService.memberAdd(memberVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/memberAdd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/memberAdd?error_code=-99";
        }
        return "redirect:memberResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/memberResult") // 자산 등록 후 화면
    public String memberResult() {
        return "/itam/member/memberResult";
    }
}
