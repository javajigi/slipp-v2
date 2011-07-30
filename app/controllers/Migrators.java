package controllers;

import java.util.List;

import models.Diary;
import play.mvc.Controller;
import play.mvc.With;
import supports.web.Check;
import supports.web.GAESecure;
import supports.web.Role;

@With(GAESecure.class)
@Check(Role.ROLE_ADMIN_USER)
public class Migrators extends Controller {
	public static void diaries() {
		List<Diary> diaries = Diary.finds(50);
		for (Diary diary : diaries) {
			diary.diaryToThread();
		}
		Threads.list(1);
	}
}
