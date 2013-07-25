

/**
 * Thrift files can namespace, package, or prefix their output in various
 * target languages.
 */
namespace cpp crawler.util
namespace d crawler.util
namespace java crawler.util
namespace php crawler.util
namespace perl crawler.util

struct Item {
	1: i64 count,
	2: string entry,
}

struct TopItems {
	1: list<Item> elements,
}

service CrawlerGUI {
    void updateRankings(1:TopItems top),
    void updateCount(1:i64 count),
}

