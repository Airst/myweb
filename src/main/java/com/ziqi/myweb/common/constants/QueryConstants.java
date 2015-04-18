package com.ziqi.myweb.common.constants;

/**
 * Description: QueryConstants
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class QueryConstants {
    public static class Base {
        public static String id = "id";
        public static String feature = "feature";
        public static String options = "options";
        public static String isDeleted = "isDeleted";
        public static String version = "version";
        public static String fromCreate = "fromCreate";
        public static String toCreate = "toCreate";
        public static String fromModified = "fromModified";
        public static String toModified = "toModified";
        public static String start = "start";
        public static String limit = "limit";
        public static String orderField = "orderField";
        public static String groupField = "groupField";
    }

    public static class Content {
    }
    public static class Reply {
      public static String contentId = "contentId";
      public static String authorId = "authorId";
      public static String floor = "floor";
      public static String parentId = "parentId";
      public static String replyType = "replyType";
    }
    public static class Thread {
      public static String title = "title";
      public static String contentId = "contentId";
      public static String authorId = "authorId";
      public static String hit = "hit";
      public static String replyCount = "replyCount";
      public static String lastReplyDate = "lastReplyDate";
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
}
