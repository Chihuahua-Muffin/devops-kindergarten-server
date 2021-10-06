package devops.kindergarten.server.domain;

import java.util.Optional;

public enum Category {
	PLAN(Long.MAX_VALUE - 1),
	CODE(Long.MAX_VALUE - 2),
	BUILD(Long.MAX_VALUE - 3),
	TEST(Long.MAX_VALUE - 4),
	RELEASE(Long.MAX_VALUE - 5),
	DEPLOY(Long.MAX_VALUE - 6),
	OPERATE(Long.MAX_VALUE - 7),
	MONITOR(Long.MAX_VALUE - 8);

	private Long id;

	Category(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static Optional<Long> categoryToPageId(String pageName) {
		Category[] categories = Category.values();
		for (Category category : categories) {
			if (category.toString().equals(pageName.toUpperCase())) {
				return Optional.of(category.getId());
			}
		}
		return Optional.empty();
	}
}
