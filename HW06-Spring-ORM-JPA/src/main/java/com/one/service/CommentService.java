package com.one.service;

public interface CommentService {

    void changeCommentText(long idComment, String nextText);

    void removeComment(long idComment);
}
