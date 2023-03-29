package com.mapper;


import com.entity.UserInfo;
import com.entity.chat.Friends;

import java.util.List;

public interface FriendsMapper {

    void insertFriend(Friends friends);

    Integer JustTwoUserIsFriend(Friends friends);

    List<UserInfo> LookUserFriend(String userid);

    UserInfo LookUserMine(String userid);
}