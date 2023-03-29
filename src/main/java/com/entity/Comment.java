package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

	private String cid;

	private String commid;

	private String cuserid;

	private String cusername;

    private String cuimage;

	private String spuserid;

	private String content;

	private Date commtime;

	private Integer commstatus;

    private List<Reply> replyLsit;
}
