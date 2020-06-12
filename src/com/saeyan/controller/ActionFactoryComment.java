package com.saeyan.controller;

import com.saeyan.controller.action.Action;
import com.saeyan.controller.action.BoardCheckPassAction;
import com.saeyan.controller.action.BoardCheckPassFormAction;
import com.saeyan.controller.action.BoardDeleteAction;
import com.saeyan.controller.action.BoardListAction;
import com.saeyan.controller.action.BoardUpdateAction;
import com.saeyan.controller.action.BoardUpdateFormAction;
import com.saeyan.controller.action.BoardViewAction;
import com.saeyan.controller.action.BoardWriteAction;
import com.saeyan.controller.action.BoardWriteFormAction;
import com.saeyan.controller.commentAction.CommentAction;
import com.saeyan.controller.commentAction.CommentWriteAction;

public class ActionFactoryComment {
	private static ActionFactoryComment instance = new ActionFactoryComment();

	private ActionFactoryComment() {
		super();
	}

	public static ActionFactoryComment getInstance() {
		return instance;
	}

	public CommentAction getAction(String command) {
		CommentAction action = null;
		System.out.println("ActionFactory :" + command);
		/* 추가된 부분 */
		if (command.equals("comment_write")) {
			action = new CommentWriteAction();
		} 
		return action;
	}
}
