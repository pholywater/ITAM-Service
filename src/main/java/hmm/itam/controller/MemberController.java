package hmm.itam.controller;

import hmm.itam.service.MemberService;
import hmm.itam.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService MemberService;

    @GetMapping("/memberList") // 웹 URL 매핑
    public String getMemberList(Model model) {
        List<MemberVo> memberList = MemberService.getMemberList();
        model.addAttribute("list", memberList);
        return "itam/memberList"; // 실제 HTML 경로
    }
}
