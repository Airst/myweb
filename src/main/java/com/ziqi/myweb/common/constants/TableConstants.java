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
        public static String authorAccount = "author_account";
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
        public static String authorAccount = "author_account";
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
    }

    public static class Image {
        public static String filepath = "filepath";
        public static String userId = "user_id";
        public static String type = "type";
        public static String parentId = "parent_id";
    }
}
