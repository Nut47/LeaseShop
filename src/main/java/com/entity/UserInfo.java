package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

	private String userid;

    private Integer roleid;

	private String username;

	private String password;

	private String mobilephone;

	private String email;

	private String uimage;

	private String sex;

	private String school;

	private String faculty;

	private String startime;

	private Integer userstatus;

	private Date createtime;

	private String vercode;

    private String status;

    private String id;
    private String sign;
    private String avatar;
    private String content;
    private String type;
    private String toid;
    private Date sendtime;
}
