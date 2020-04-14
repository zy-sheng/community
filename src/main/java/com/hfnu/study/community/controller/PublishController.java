package com.hfnu.study.community.controller;

import com.hfnu.study.community.dto.QuestionDTO;
import com.hfnu.study.community.mapper.QuestionMapper;
import com.hfnu.study.community.mapper.UserMapper;
import com.hfnu.study.community.model.Question;
import com.hfnu.study.community.model.User;
import com.hfnu.study.community.service.QuestionService;
import com.hfnu.study.community.tagCashe.tagBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;



    @GetMapping("/publish/{id}")
    public String edit(
            @PathVariable(name = "id") Long id,
            Model model
    ){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTags());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", tagBase.get());

        return "publish";
    }



    @GetMapping("/publish")
    public String publish( Model model) {
        model.addAttribute("tags",tagBase.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false)Long id,
            HttpServletRequest request,
            Model model
    ) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags",tagBase.get());
        if (title == null || title == "") {
            model.addAttribute("error", "标题为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签为空");
            return "publish";
        }

        String invalid = tagBase.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签:"+invalid);
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tag);
        question.setCreateor(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";

    }
}