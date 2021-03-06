package com.hfnu.study.community.mapper;

import com.hfnu.study.community.dto.QuestionQueryDTO;
import com.hfnu.study.community.model.Question;
import com.hfnu.study.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectReacted(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> SelectBySearch(QuestionQueryDTO questionQueryDTO);
}