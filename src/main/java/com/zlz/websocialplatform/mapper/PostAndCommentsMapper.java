package com.zlz.websocialplatform.mapper;

import com.zlz.websocialplatform.entity.MyInteger;
import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PostAndCommentsMapper {

    @Insert("insert into posts(from_user_email,content,time_stamp) values(#{userEmail},#{content},#{timeStamp})")
    void createPost(@Param("userEmail") String userEmail,@Param("content") String content,@Param("timeStamp") String timeStamp);

    @Insert("insert into comments(from_user_email,post_id,content,time_stamp) values(#{userEmail},#{postID},#{content},#{timeStamp})")
    void createComment(@Param("userEmail") String userEmail,@Param("postID")int postID,@Param("content") String content,@Param("timeStamp") String timeStamp);

    @Select("select * from posts where post_id=#{postID}")
    @Results({
            @Result(id = true,column = "post_id",property = "postID"),
            @Result(column = "from_user_email",property = "userEmail"),
            @Result(column = "content",property = "content"),
            @Result(column = "ups",property = "ups"),
            @Result(column = "downs",property = "downs"),
            @Result(column = "comment_closed",property = "commentClosed"),
            @Result(column = "time_stamp",property = "timeStamp"),
            @Result(column = "post_id",property = "comments",javaType = List.class,many = @Many(select = "getCommentsIDsOfPost"))
    })
    Post getOnePost(@Param("postID") int postID);

    @Select("select comment_id from comments where post_id=#{postID}")
    @ResultType(List.class)
    @Result(column = "comment_id",property = "id")
    List<MyInteger> getCommentsIDsOfPost(@Param("postID") int postID);

    @Select("select post_id from posts where from_user_email=#{userEmail}")
    @ResultType(List.class)
    @Result(column = "post_id",property = "id")
    List<MyInteger> getPostsList(@Param("userEmail") String userEmail);

    @Select("select * from comments where comment_id=#{commentID}")
    @Results({
            @Result(id = true,column = "comment_id",property = "commentID"),
            @Result(column = "from_user_email",property = "userEmail"),
            @Result(column = "post_id",property = "postID"),
            @Result(column = "content",property = "content"),
            @Result(column = "ups",property = "ups"),
            @Result(column = "downs",property = "downs"),
            @Result(column = "time_stamp",property = "timeStamp")
    })
    Comment getOneComment(@Param("commentID")int commentID);

    @Select("select comment_closed from posts where post_id=#{postID}")
    String getPostClosedState(@Param("postID")String postID);

    @Update("update posts set comment_closed='true' where post_id=#{postID}")
    void setPostClosed(@Param("postID")String postID);

    @Select("<script>\n" +
            "    select\n" +
            "    <choose>\n" +
            "        <when test=\"type=='up'\">ups_list</when>\n" +
            "        <when test=\"type=='down'\">downs_list</when>\n" +
            "    </choose>\n" +
            "    from\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">posts</when>\n" +
            "        <when test=\"table=='comments'\">comments</when>\n" +
            "    </choose>\n" +
            "    where\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">post_id=#{id}</when>\n" +
            "        <when test=\"table=='comments'\">comment_id=#{id}</when>\n" +
            "    </choose>\n" +
            "</script>")
    String getUpsOrDownsList(@Param("type")String type,@Param("table")String table,@Param("id")int id);

    @Update("<script>\n" +
            "    update\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">posts</when>\n" +
            "        <when test=\"table=='comments'\">comments</when>\n" +
            "    </choose>\n" +
            "    set\n" +
            "    <choose>\n" +
            "        <when test=\"type=='up'\">ups_list=#{list}</when>\n" +
            "        <when test=\"type=='down'\">downs_list=#{list}</when>\n" +
            "    </choose>\n" +
            "    where\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">post_id=#{id}</when>\n" +
            "        <when test=\"table=='comments'\">comment_id=#{id}</when>\n" +
            "    </choose>\n" +
            "</script>")
    void setUpsOrDownsList(@Param("type")String type,@Param("table")String table,@Param("id")int id,@Param("list")String list);

    @Select("<script>\n" +
            "    select\n" +
            "    <choose>\n" +
            "        <when test=\"type=='up'\">ups</when>\n" +
            "        <when test=\"type=='down'\">downs</when>\n" +
            "    </choose>\n" +
            "    from\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">posts</when>\n" +
            "        <when test=\"table=='comments'\">comments</when>\n" +
            "    </choose>\n" +
            "    where\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">post_id=#{id}</when>\n" +
            "        <when test=\"table=='comments'\">comment_id=#{id}</when>\n" +
            "    </choose>\n" +
            "</script>")
    int getUpsOrDowns(@Param("type")String type,@Param("table")String table,@Param("id")int id);

    @Update("<script>\n" +
            "    update\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">posts</when>\n" +
            "        <when test=\"table=='comments'\">comments</when>\n" +
            "    </choose>\n" +
            "    set\n" +
            "    <choose>\n" +
            "        <when test=\"type=='up'\">ups=#{num}</when>\n" +
            "        <when test=\"type=='down'\">downs=#{num}</when>\n" +
            "    </choose>\n" +
            "    where\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">post_id=#{id}</when>\n" +
            "        <when test=\"table=='comments'\">comment_id=#{id}</when>\n" +
            "    </choose>\n" +
            "</script>")
    void setUpsOrDowns(@Param("type")String type,@Param("table")String table,@Param("id")int id,@Param("num")int num);

    @Delete("<script>\n" +
            "    delete from\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">posts</when>\n" +
            "        <when test=\"table=='comments'\">comments</when>\n" +
            "    </choose>\n" +
            "    where\n" +
            "    <choose>\n" +
            "        <when test=\"table=='posts'\">post_id=#{id}</when>\n" +
            "        <when test=\"table=='comments'\">comment_id=#{id}</when>\n" +
            "    </choose>\n" +
            "</script>")
    void deleteByID(@Param("table")String table,@Param("id")int id);

}
