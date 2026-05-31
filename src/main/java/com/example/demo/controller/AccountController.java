package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Account;
import com.example.demo.repository.ItemRepository;

@Controller
public class AccountController {

	private final HttpSession session;
	private final Account account;

	private final ItemRepository itemRepository;

	public AccountController(HttpSession session, Account account, ItemRepository itemRepository) {
		this.session = session;
		this.account = account;
		this.itemRepository = itemRepository;
	}

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index(Model model) {
		// セッション情報を全てクリアする
		session.invalidate();

		List<Item> itemList = itemRepository.findAll();

		Collections.shuffle(itemList);
		//		//		Collections.shuffle(itemList);
		//		2でコミット
		itemList = itemList.subList(0, 2);

		model.addAttribute("items", itemList);

		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam String name,
			Model model) {
		// 名前が空の場合にエラーとする
		if (name == null || name.length() == 0) {
			model.addAttribute("message", "名前を入力してください");
			return "login";
		}

		// セッション管理されたアカウント情報に名前をセット
		account.setName(name);

		// 「/items」へのリダイレクト
		return "redirect:/items";
	}
}
