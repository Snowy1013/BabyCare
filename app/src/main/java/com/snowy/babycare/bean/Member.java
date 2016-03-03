package com.snowy.babycare.bean;

/**
 * Created by snowy on 16/3/3.
 */
public class Member {
    private int city_id;
    private int id;
    private String sid;
    private String member_icon;
    private int sex;
    private int points_nums;
    private int province_id;
    private String nickname;
    private int msg_unread_nums;

    public Member() {
    }

    public Member(int id) {
        this.id = id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setMember_icon(String member_icon) {
        this.member_icon = member_icon;
    }

    public void setPoints_nums(int points_nums) {
        this.points_nums = points_nums;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMsg_unread_nums(int msg_unread_nums) {
        this.msg_unread_nums = msg_unread_nums;
    }

    public int getCity_id() {
        return city_id;
    }

    public int getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getMember_icon() {
        return member_icon;
    }

    public int getSex() {
        return sex;
    }

    public int getProvince_id() {
        return province_id;
    }

    public int getPoints_nums() {
        return points_nums;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMsg_unread_nums() {
        return msg_unread_nums;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", sid='" + sid + '\'' +
                ", sex=" + sex +
                ", member_icon='" + member_icon + '\'' +
                ", city_id=" + city_id +
                ", points_nums=" + points_nums +
                ", province_id=" + province_id +
                ", msg_unread_nums=" + msg_unread_nums +
                '}';
    }
}
