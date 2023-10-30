package hmm.itam.vo;

import lombok.Data;

@Data
public class UserVo {

    private Long id;
    private String hmm_id;
    private String name;
    private String department;
    private String password;
}
