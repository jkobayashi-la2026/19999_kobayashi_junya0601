package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;

@Aspect
@Component
public class LoggingAspect {

	private final Account account;
	
	public LoggingAspect(Account account) {
	    this.account = account;
	}

	// ログ出力処理
	// 全Controllerクラスの全メソッド処理前を指定
	@Before("execution(* com.example.demo.controller.*Controller.*(..))")
	public void log(JoinPoint jp) {
		// ログインしたアカウント情報を取得
		if (account == null || account.getName() == null
				|| account.getName().length() == 0) {
			System.out.print("ゲスト：");
		} else {
			System.out.print(account.getName() + "：");
		}
		System.out.println(jp.getSignature().toShortString());
	}
}
