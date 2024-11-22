package hmm.itam.service;

import hmm.itam.mapper.MemberMapper;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberMapper MemberMapper;


    public void memberAdd(MemberVo memberVo) {
        MemberMapper.insertMember(memberVo);
    }


    /*부서 리스트 자동완성*/
    public List<MemberVo> getDepartmentList() {
        return MemberMapper.getDepartmentList();
    }

    /*멤버 리스트 자동완성*/
    public List<MemberVo> getMemberList() {
        return MemberMapper.getMemberList();
    }

    /*멤버 조회*/
    public MemberVo memberSearch(String memberId) {
        return MemberMapper.getMemberByMemberId(memberId);
    }

    /*멤버 정보 수정*/
    public void modifyInfo(MemberVo memberVo) {
        MemberMapper.updateMember(memberVo);
    }


    public List<MemberVo> getHeaderDepartmentList() {
        return MemberMapper.getHeaderDepartmentList();
    }
}
