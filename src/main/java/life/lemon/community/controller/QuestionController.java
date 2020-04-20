package life.lemon.community.controller;

import life.lemon.community.dto.CommentDTO;
import life.lemon.community.dto.QuestionDTO;
import life.lemon.community.enums.CommentTypeEnum;
import life.lemon.community.exception.CustomizeErrorCode;
import life.lemon.community.exception.CustomizeException;
import life.lemon.community.service.CommentService;
import life.lemon.community.service.QuestionService;
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

    @GetMapping("/question/{id}")//服务端接到点击某个问题的请求时
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId;
        try {
            questionId = Long.parseLong(id);//将String类型的id转换为Long类型
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);//非法输入异常
        }
        QuestionDTO questionDTO = questionService.getById(questionId);//进入方法，接收到问题详情和作者
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }




}
