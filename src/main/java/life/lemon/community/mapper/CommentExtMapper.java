package life.lemon.community.mapper;

import life.lemon.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}