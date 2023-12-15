package hmm.itam.vo;

import lombok.Data;

@Data
public class MemberVo {
    private String memberId;
    private String memberName;
    private String memberRank;
    private String memberWork;
    private String memberWorkingStatus;
    private String memberEtc1;
    private String memberEtc2;
    private String tgateMemberEmail; //tgate 인사 db 연결
    private String tgateMemberDepartment; //tgate 인사 db 연결
}
