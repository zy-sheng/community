package com.hfnu.study.community.controller;

import com.hfnu.study.community.dto.CommentDTO;
import com.hfnu.study.community.dto.QuestionDTO;
import com.hfnu.study.community.enums.CommentTypeEnum;
import com.hfnu.study.community.service.CommentService;
import com.hfnu.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> reactedQuestions = questionService.selectReacted(questionDTO);
        //其中CommentDTO是页面传递过来的DTO需要重构一下
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //传到页面
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("reactedQuestions",reactedQuestions);
        //阅读数累加
       return "question";
    }
}
