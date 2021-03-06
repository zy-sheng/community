package com.hfnu.study.community.controller;

import com.hfnu.study.community.dto.CommentCreateDTO;
import com.hfnu.study.community.dto.CommentDTO;
import com.hfnu.study.community.dto.ResultDTO;
import com.hfnu.study.community.enums.CommentTypeEnum;
import com.hfnu.study.community.exception.CustomizeErrorCode;
import com.hfnu.study.community.mapper.CommentExtMapper;
import com.hfnu.study.community.model.Comment;
import com.hfnu.study.community.model.User;
import com.hfnu.study.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;



    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request
    ) {
        //通过responseBOdy可以将对象自动化为JSON
        //通过RequestBody接受JSON格式的数据，变成对象，在通过ResponseBody可以将对象自动的序列化为JSON发送到前端
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONNENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        comment.setLikeAccount(0);
        commentService.insert(comment , user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id
    ){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

}
