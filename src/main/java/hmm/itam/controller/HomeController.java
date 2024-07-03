package hmm.itam.controller;

import hmm.itam.service.MemberService;
import hmm.itam.service.UserService;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String home(HttpSession session, UserVo userVo, Model model /*, String authority*/) {
        Long idx = (Long) session.getAttribute("userId");
        if (idx != null) {
            UserVo userList = userService.getUserById(idx);
            model.addAttribute("user", userList);
            /*HEADER 화면 권한 체크*/
            /*th:if="${#strings.equals(user.authority, 'admin')}"*/
            /*authority = userList.getAuthority();
            log.info("화면 권한 : {}", authority);*/
            /*차트 연동 테스트*/
            String label[] = {"a", "b", "c", "d", "e", "f", "g"};
            int point[] = {5, 3, 7, 1, 8, 3, 4,};
            model.addAttribute("label", label);
            model.addAttribute("point", point);
            return "home";
        }
        return "redirect:/login"; // 정상 작동
    }


    @GetMapping("/headerDepartmentList") // 부서 리스트 불러오기
    public String getHeaderDepartmentList(Model model) {
        List<MemberVo> headerDepartmentList = MemberService.getHeaderDepartmentList();
        model.addAttribute("list", headerDepartmentList);
        return "home";
    }

}