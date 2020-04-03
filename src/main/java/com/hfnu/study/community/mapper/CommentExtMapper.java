package com.hfnu.study.community.mapper;

import com.hfnu.study.community.model.Comment;
import com.hfnu.study.community.model.CommentExample;
import com.hfnu.study.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}