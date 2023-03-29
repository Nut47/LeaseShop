package com.service;

import com.entity.UserInfo;
import com.entity.chat.Friends;
import com.mapper.FriendsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendsService {

    @Resource
    FriendsMapper friendsMapper;

    public void insertFriend(Friends friends){
        friendsMapper.insertFriend(friends);
    }

    public Integer JustTwoUserIsFriend(Friends friends){
        return friendsMapper.JustTwoUserIsFriend(friends);
    }

    public List<UserInfo> LookUserFriend(String userid){
        return friendsMapper.LookUserFriend(userid);
    }

    public UserInfo LookUserMine(String userid){
        return friendsMapper.LookUserMine(userid);
    }
}
