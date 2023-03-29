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
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

	private String senduserid;

	private String reciveuserid;

	private String content;

	private Date sendtime;

	private String msgtype;


}
