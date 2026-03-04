package com.amalitech.data;

public class TestData {

    // ----------- POSTS -------------
    public static String createPostBody() {
        return """
        {
          "title": "AmaliTech Test Post",
          "body": "This is a test post body",
          "userId": 1
        }
        """;
    }

    public static String updatePostBody() {
        return """
        {
          "id": 1,
          "title": "Updated Title",
          "body": "Updated body content",
          "userId": 1
        }
        """;
    }

    // ----------- COMMENTS -------------
    public static String createCommentBody() {
        return """
        {
          "postId": 1,
          "name": "AmaliTech Comment",
          "email": "test@amalitech.com",
          "body": "This is a test comment"
        }
        """;
    }

    public static String updateCommentBody() {
        return """
        {
          "id": 1,
          "postId": 1,
          "name": "Updated Comment",
          "email": "updated@amalitech.com",
          "body": "Updated comment body"
        }
        """;
    }

    // ----------- ALBUMS -------------
    public static String createAlbumBody() {
        return """
        {
          "userId": 1,
          "title": "AmaliTech Test Album"
        }
        """;
    }

    public static String updateAlbumBody() {
        return """
        {
          "id": 1,
          "userId": 1,
          "title": "Updated Album Title"
        }
        """;
    }

    // ----------- PHOTOS -------------
    public static String createPhotoBody() {
        return """
        {
          "albumId": 1,
          "title": "AmaliTech Test Photo",
          "url": "https://via.placeholder.com/600/test",
          "thumbnailUrl": "https://via.placeholder.com/150/test"
        }
        """;
    }

    public static String updatePhotoBody() {
        return """
        {
          "id": 1,
          "albumId": 1,
          "title": "Updated Photo Title",
          "url": "https://via.placeholder.com/600/updated",
          "thumbnailUrl": "https://via.placeholder.com/150/updated"
        }
        """;
    }

    // ----------- TODOS -------------
    public static String createTodoBody() {
        return """
        {
          "userId": 1,
          "title": "AmaliTech Test Todo",
          "completed": false
        }
        """;
    }

    public static String updateTodoBody() {
        return """
        {
          "id": 1,
          "userId": 1,
          "title": "Updated Todo Title",
          "completed": true
        }
        """;
    }

    // ----------- USERS -------------
    public static String createUserBody() {
        return """
        {
          "name": "Lennox Owusu",
          "username": "lennox",
          "email": "lennox@amalitech.com",
          "phone": "1-234-567-8900",
          "website": "amalitech.com"
        }
        """;
    }

    public static String updateUserBody() {
        return """
        {
          "id": 1,
          "name": "Updated User",
          "username": "updateduser",
          "email": "updated@amalitech.com",
          "phone": "1-234-567-8900",
          "website": "amalitech.com"
        }
        """;
    }
}