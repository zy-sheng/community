package com.hfnu.study.community.tagCashe;

import com.hfnu.study.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class tagBase {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();


        TagDTO tagName = new TagDTO();
        tagName.setCategoryName("编程语言");
        tagName.setTags(Arrays.asList("java","c","c++","php","perl","python","javascript","c#","ruby","node.js","erlang","scala","bash","actionscript","golang","typescript","flutter"));
        tagDTOS.add(tagName);

        TagDTO frameWork = new TagDTO();
        frameWork.setCategoryName("框架語言");
        frameWork.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","struts","tornado"));
        tagDTOS.add(frameWork);

        TagDTO dbSql = new TagDTO();
        dbSql.setCategoryName("数据库");
        dbSql.setTags(Arrays.asList("mysql","sqlite","oracle","sql","nosql","redis","mongodb","memcached","postgresql"));
        tagDTOS.add(dbSql);

        TagDTO ITTech = new TagDTO();
        ITTech.setCategoryName("人工智能");
        ITTech.setTags(Arrays.asList("算法","机器学习","人工智能","深度学习","数据挖掘","tensorflow","神经网络","图像识别","人脸识别","自然语言处理","机器人","pytorch","自动驾驶"));
        tagDTOS.add(ITTech);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("工具");
        tool.setTags(Arrays.asList("git","github","macos","visual-studio-code","windows","vim","sublime-text","intellij-idea","phpstorm","eclipse","webstorm","编辑器","svnvisual-studio","pycharm","emacs"));
        tagDTOS.add(tool);


        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        //获得list对象
        List<TagDTO>tagDTOS = get();
        //flatMap将一个stream元素的集合转化为新的子stream集合，也就是对 各个子stream集合进行二次遍历，融合到一个子集合list中，并且有顺序
        /*->就相当于一个循环*/
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        /*.joining连接每个元素toString生成的字符串*/
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining());
        return invalid;
    }
}
