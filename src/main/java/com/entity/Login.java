package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;

	private String userid;

	private Integer roleid;

	private String username;

	private String password;

    private String mobilephone;

	private Integer userstatus;

    private String vercode;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
