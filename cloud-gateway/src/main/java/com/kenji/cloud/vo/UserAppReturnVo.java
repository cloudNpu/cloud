package com.kenji.cloud.vo;

import com.kenji.cloud.entity.UserApp;
import lombok.Data;

import java.util.Date;

@Data
public class UserAppReturnVo {
        private long id;
        private String appname;
        private long userId;
        private long operatorId;
        private Date createDate;
        private String comment;

        public UserAppReturnVo(UserApp userApp){
                this.id = userApp.getId();
                this.appname = userApp.getAppName();
                this.userId = userApp.getUser().getId();
                this.operatorId = userApp.getOperator().getId();
                this.createDate = userApp.getCreateDate();
                this.comment = userApp.getComment();
        }

        public  UserAppReturnVo(){
                this.id = -1;
                this.appname = null;
                this.userId = -1;
                this.operatorId = -1;
                this.createDate = null;
                this.comment = null;
        }
}
