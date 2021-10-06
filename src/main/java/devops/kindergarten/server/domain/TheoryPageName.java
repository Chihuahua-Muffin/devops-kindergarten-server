package devops.kindergarten.server.domain;

import java.util.Optional;

public enum TheoryPageName {
	PLAN(Long.MAX_VALUE - 1),
	CODE(Long.MAX_VALUE - 2),
	BUILD(Long.MAX_VALUE - 3),
	TEST(Long.MAX_VALUE - 4),
	RELEASE(Long.MAX_VALUE - 5),
	DEPLOY(Long.MAX_VALUE - 6),
	OPERATE(Long.MAX_VALUE - 7),
	MONITOR(Long.MAX_VALUE - 8);

	private Long id;

	TheoryPageName(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static boolean isCorrectPageName(String pageName) {
		TheoryPageName[] pageNames = TheoryPageName.values();
		for (TheoryPageName name : pageNames) {
			if (name.toString().equals(pageName.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}
