package hmm.itam.service;

import hmm.itam.mapper.UserMapper;
import hmm.itam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserVo> getUserList() {

        return userMapper.getUserList();
    }

    public Long login(String hmm_id, String password) {
        UserVo userVo = userMapper.getUserByHmm_id(hmm_id);

        System.out.println("service.check.hmm_id :"+ hmm_id);
        System.out.println("service.check.password :"+ password);
        System.out.println("service.check.vo.password :"+ userVo.getPassword());
        System.out.println("service.check.getidx :"+ userVo.getIdx());

        if (userVo.getPassword().equals(password)){
            return userVo.getIdx();
        }
        return null;
    }

    public void signup(UserVo userVo){

        userMapper.insertUser(userVo);
    }

    public UserVo getUserById(Long id) {
        return userMapper.getUserById(id);
    }

}
