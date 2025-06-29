package hmm.itam.controller;

import hmm.itam.service.UserService;
import hmm.itam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.WeakHashMap;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userList") // 웹 URL 매핑
    public String getUserList(Model model) {
        List<UserVo> userList = userService.getUserList();
        model.addAttribute("list", userList);
        return "login/userList"; // 실제 HTML 경로
    }

    @GetMapping("/signup") // 회원가입 화면
    public String toSignupPage(UserVo userVo) {
        return "login/signup";
    }

    @PostMapping("/signup") // 회원가입 입력 처리
    public String signup(UserVo userVo) {
        try {
            userService.signup(userVo);
        } catch (DuplicateKeyException e) {
            return "redirect:signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:signup?error_code=-99";
        }
        return "redirect:login"; // 회원가입 후 보여질 화면
    }

    @GetMapping("/login") // 로그인 화면
    public String toLoginPage(HttpSession session, UserVo userVo) { // 로그인 페이지
        Long id = (Long) session.getAttribute("userId");
        if (id != null) { // 로그인 된 상태
            return "redirect:/";
        }
        return "login/login"; // 로그인되지 않은 상태
    }

    @PostMapping("/login") // 아이디 패스워드 입력 후
    public String login(String hmmId, String password, HttpSession session, UserVo userVo) {
        Long id = userService.login(hmmId, password);
        System.out.println("controll.check.hmmId :" + hmmId);
        System.out.println("controll.check.password :" + password);
        System.out.println("controll.check.getidx :" + id);
        System.out.println("controll.check.httpsession :" + session);
        if (id == null) { // 로그인 실패
            return "redirect:login";
        }
        session.setAttribute("userId", id);
        return "redirect:/"; // 로그인 후 홈 화면
    }


    @GetMapping("/update")
    public String toUpdatePage(HttpSession session, UserVo userVo, Model model) {
        Long id = (Long) session.getAttribute("userId");
        UserVo userUpdate = userService.getUserById(id);
        model.addAttribute("user", userUpdate);
        return "/login/update";
    }

    @PostMapping("/update")
    public String modifyInfo(HttpSession session, UserVo userVo) {
        Long id = (Long) session.getAttribute("userId");
        userVo.setIdx(id);
        userService.modifyInfo(userVo);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        if (id != null) {
            userService.withdraw(id);
        }
        session.invalidate();
        return "redirect:/";
    }
}
