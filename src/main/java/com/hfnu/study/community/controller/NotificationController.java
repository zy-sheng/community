package com.hfnu.study.community.controller;


import com.hfnu.study.community.dto.NotificationDTO;
import com.hfnu.study.community.enums.NotificationTypeEnums;
import com.hfnu.study.community.model.User;
import com.hfnu.study.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(
            HttpServletRequest request,
            @PathVariable(name = "id") Long id

    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnums.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnums.REPLY_QUESTION.getType() == notificationDTO.getType()) {

            return "redirect:/question/" + notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }
    }
}
