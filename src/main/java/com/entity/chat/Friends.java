package com.entity.chat;

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
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;

	private String userid;

	private String fuserid;

	private Date addtime;


}
