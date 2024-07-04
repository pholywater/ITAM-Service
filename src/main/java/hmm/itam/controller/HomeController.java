package hmm.itam.controller;

import hmm.itam.service.AssetService;
import hmm.itam.service.MemberService;
import hmm.itam.service.UserService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static hmm.itam.service.AssetService.*;

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
    public String home(HttpSession session, UserVo userVo, Model model/*, String authority*/) {
        Long idx = (Long) session.getAttribute("userId");
        if (idx != null) {
            UserVo userList = userService.getUserById(idx);
            model.addAttribute("user", userList);

            List<AssetVo> chart1Label = userService.getChart1List();
            model.addAttribute("label", chart1Label);
            List<AssetVo> chart1Point = userService.getChart2List();
            model.addAttribute("point", chart1Point);
            log.info("label : {}", chart1Label);
            log.info("point : {}", chart1Point);

            /*HEADER 화면 권한 체크*/
            /*th:if="${#strings.equals(user.authority, 'admin')}"*/
            /*authority = userList.getAuthority();
            log.info("화면 권한 : {}", authority);*/

            return "/home";
        }
        return "redirect:/login"; // 정상 작동
    }

}