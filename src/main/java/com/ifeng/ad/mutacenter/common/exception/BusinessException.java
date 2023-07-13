package com.ifeng.ad.mutacenter.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 项目描述: 自定义异常
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -1095797921201166225L;

	/** 错误码 **/
	private int errorCode;

	public BusinessException(int errorCode, String errorMessage, Object... errorParams) {
		// 将错误信息传给RuntimeException
		super(MessageFormatter.arrayFormat(errorMessage, errorParams).getMessage());
		this.errorCode = errorCode;
	}
	
//	public BusinessException(int errorCode, Throwable throwable, String errorMessage, Object... errorParams) {
//		super(MessageFormatter.arrayFormat(errorMessage, errorParams).getMessage(), throwable);
//		this.errorCode = errorCode;
//	}

	public int getErrorCode() {
		return errorCode;
	}
	
}
