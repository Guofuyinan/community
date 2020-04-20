package life.lemon.community.controller;

import life.lemon.community.dto.CommentCreateDTO;
import life.lemon.community.dto.CommentDTO;
import life.lemon.community.dto.ResultDTO;
import life.lemon.community.enums.CommentTypeEnum;
import life.lemon.community.exception.CustomizeErrorCode;
import life.lemon.community.model.Comment;
import life.lemon.community.model.User;
import life.lemon.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {//评论功能

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");//通过Session获取用户信息
        if (user == null) {//用户为空，返回未登录错误
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {//评论内容为空
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        if ("郭付一男".equals(commentCreateDTO.getContent())) {//评论内容为作者名字
            return ResultDTO.errorOf(CustomizeErrorCode.GUOFUYINAN);
        }
        if ("王璐瑶".equals(commentCreateDTO.getContent())) {//评论内容为作者名字
            return ResultDTO.errorOf(CustomizeErrorCode.WANGLUYAO);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment, user);
        return ResultDTO.okOf();//评论成功
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {//回复评论
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);//评论成功
    }
}
