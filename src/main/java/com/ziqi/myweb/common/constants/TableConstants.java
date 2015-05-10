package com.ziqi.myweb.common.constants;

/**
 * Description: TableConstants
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class TableConstants {

	public static class Base {
		public static String id = "id";
		public static String feature = "feature";
		public static String options = "options";
		public static String isDeleted = "is_deleted";
		public static String gmtCreate = "gmt_create";
		public static String gmtModified = "gmt_modified";
		public static String version = "version";
	}
	public static class Reply {
		public static String contentPath = "content_path";
		public static String authorId = "author_id";
		public static String floor = "floor";
		public static String threadId = "thread_id";
		public static String parentId = "parent_id";
		public static String replyType = "reply_type";
		public static String replyCount = "reply_count";
	}
	public static class Thread {
		public static String title = "title";
		public static String contentPath = "content_path";
		public static String authorId = "author_id";
		public static String hit = "hit";
		public static String replyCount = "reply_count";
		public static String likeCount = "like_count";
		public static String lastReplyDate = "last_reply_date";
		public static String level = "level";
		public static String tags = "tags";
	}
	public static class User {
		public static String account = "account";
		public static String password = "password";
		public static String name = "name";
		public static String email = "email";
		public static String phone = "phone";
		public static String age = "age";
		public static String gender = "gender";
		public static String level = "level";
		public static String imagePath = "image_path";
	}
	public static class Image {
		public static String filepath = "filepath";
		public static String userId = "user_id";
		public static String type = "type";
		public static String parentId = "parent_id";
	}
	public static class Message {
		public static String fromUserId = "from_user_id";
		public static String fromAccount = "from_account";
		public static String toUserId = "to_user_id";
		public static String toAccount = "to_account";
		public static String content = "content";
		public static String type = "type";
		public static String status = "status";
	}
	public static class Active {
		public static String topBeautyId = "top_beauty_id";
		public static String ownerId = "owner_id";
		public static String count = "count";
		public static String startTime = "start_time";
		public static String address = "address";
		public static String status = "status";
		public static String description = "description";
	}
}
