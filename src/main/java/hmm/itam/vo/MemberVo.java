package hmm.itam.vo;

import lombok.Data;

@Data
public class MemberVo {
    private String member_id;
    private String member_name;
    private String member_rank;
    private String member_work;
    private String member_working_status;
    private String member_etc1;
    private String member_etc2;
    private String tgate_member_email; //tgate 인사 db 연결
    private String tgate_member_department; //tgate 인사 db 연결
}
