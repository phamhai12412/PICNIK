package hai.service;

import hai.dto.Commenadd;
import hai.dto.Commentbyidpost;
import hai.dto.Postfindall;
import hai.model.Comment;
import hai.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceIMPL implements IGenericService<Post, Integer> {
    @Autowired
    private DataSource dataSource;

    public List<Postfindall> getAllpost() {
        List<Postfindall> postList = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetPostInfo()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Postfindall post = new Postfindall();
                post.setPostId(rs.getInt("id"));
                post.setUsepostId(rs.getInt("usepost_id"));
                post.setUserName(rs.getString("user_name"));
                post.setUserAvatar(rs.getString("user_avatar"));
                post.setPostContent(rs.getString("content"));
                post.setPostImageUrl(rs.getString("imgUrl"));

                int totalLikes = rs.getInt("total_likes");
                int totalComments = rs.getInt("total_comments");


                post.setTotalLikes(totalLikes);
                post.setTotalComments(totalComments);

                post.setStatus(rs.getBoolean("status"));
                post.setCreatedDate(rs.getDate("createdDate"));
                postList.add(post);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return postList;
    }


    public List<Commentbyidpost> getallcommentbyid(int postId) {
        List<Commentbyidpost> commentbyidpostList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callSt = connection.prepareCall("{CALL GetPostComments(?)}")) {

            callSt.setInt(1, postId);
            try (ResultSet rs = callSt.executeQuery()) {
                while (rs.next()) {
                    Commentbyidpost comment = new Commentbyidpost();
                    comment.setId(rs.getInt("comment_id"));
                    comment.setUserId(rs.getInt("usecomment_id"));
                    comment.setUserName(rs.getString("user_name"));
                    comment.setUserAvatar(rs.getString("user_avatar"));
                    comment.setContent(rs.getString("comment_content"));
                    comment.setCreatedDate(rs.getDate("createdDate"));
                    comment.setTotal_likes(rs.getInt("total_likes"));
                    commentbyidpostList.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return commentbyidpostList;
    }




    //ghi chú kết hợp getAllpost, getallcommentbyid hoàn thành chức năng show bài viết
    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post findById(Integer integer) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetPostById(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setUserId(rs.getInt("usepost_id"));
                post.setContent(rs.getString("content"));
                post.setImgUrl(rs.getString("imgUrl"));
                post.setCreatedDate(rs.getTimestamp("createdDate"));
                post.setStatus(rs.getBoolean("status"));
                return post;
            } else {
                return null; // Bài viết không tồn tại
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void save(Post post) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (post.getId() == 0) {
                // thêm moi
                CallableStatement callSt = conn.prepareCall("{call AddNewPost(?,?,?)}");
                callSt.setInt(1,post.getUserId());
                callSt.setString(2, post.getContent());
                callSt.setString(3, post.getImgUrl());
                callSt.executeUpdate();
            } else {
                // cap nhat
                CallableStatement callSt = conn.prepareCall("{call UpdatePostContent(?,?)}");
                callSt.setInt(1,post.getId());
                callSt.setString(2, post.getContent());

                callSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // Tắt tự động commit
            CallableStatement callSt = conn.prepareCall("{call DeletePostbyid(?)}");
            callSt.setInt(1, integer);
            callSt.executeUpdate();

            conn.commit(); // Commit giao dịch sau khi stored procedure thực hiện thành công
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback giao dịch nếu có lỗi
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Lỗi rollback giao dịch.", rollbackException);
            }
            throw new RuntimeException("Lỗi khi xóa bài viết và dữ liệu liên quan.", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Mở lại tự động commit
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        }
        // lấy danh sách các ID bài post mà một người dùng đã thích
    public List<Integer> getAllLikedPostIdsForUser(int userId) {
        Connection connection = null;
        List<Integer> likedPostIds = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetAllLikedPostIdsForUser(?)}");
            callSt.setInt(1, userId);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                int postId = rs.getInt("LikePostofpost_id");
                likedPostIds.add(postId);
            }

            return likedPostIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //lấy danh sách các ID bình luận mà một người dùng đã thích
    public List<Integer> getAllLikedCommentIdsForUser(int userId) {
        Connection connection = null;
        List<Integer> likedCommentIds = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetAllLikedCommentIdsForUser(?)}");
            callSt.setInt(1, userId);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                int commentId = rs.getInt("likecommentofcomment_id");
                likedCommentIds.add(commentId);
            }

            return likedCommentIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
//thêm lượt thích bài viết theo id use và id post
public void AddPostLike(int postID, int userID) {
    Connection connection = null;

    try {
        connection = dataSource.getConnection();
        CallableStatement callSt = connection.prepareCall("{CALL AddPostLike(?, ?)}");
        callSt.setInt(1, postID);
        callSt.setInt(2, userID);
        callSt.executeUpdate();
    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
//bỏ thích bài viết
public void removePostLike(int postID, int userID) {
    Connection connection = null;

    try {
        connection = dataSource.getConnection();
        CallableStatement callSt = connection.prepareCall("{CALL RemovePostLike(?, ?)}");
        callSt.setInt(1, postID);
        callSt.setInt(2, userID);
        callSt.executeUpdate();
    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
    public List<Postfindall> getPostsByUserId(int userId) {
        List<Postfindall> postList = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetPostInfoByUserID(?)}");
            callSt.setInt(1, userId); // Gán giá trị cho tham số đầu vào của thủ tục
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Postfindall post = new Postfindall();
                post.setPostId(rs.getInt("id"));
                post.setUsepostId(rs.getInt("usepost_id"));
                post.setUserName(rs.getString("user_name"));
                post.setUserAvatar(rs.getString("user_avatar"));
                post.setPostContent(rs.getString("content"));
                post.setPostImageUrl(rs.getString("imgUrl"));

                int totalLikes = rs.getInt("total_likes");
                int totalComments = rs.getInt("total_comments");

                post.setTotalLikes(totalLikes);
                post.setTotalComments(totalComments);

                post.setStatus(rs.getBoolean("status"));
                post.setCreatedDate(rs.getDate("createdDate"));
                postList.add(post);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return postList;
    }

    public List<Postfindall> getAllpostadmin() {
        List<Postfindall> postList = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetPostadmin()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Postfindall post = new Postfindall();
                post.setPostId(rs.getInt("id"));
                post.setUsepostId(rs.getInt("usepost_id"));
                post.setUserName(rs.getString("user_name"));
                post.setUserAvatar(rs.getString("user_avatar"));
                post.setPostContent(rs.getString("content"));
                post.setPostImageUrl(rs.getString("imgUrl"));

                int totalLikes = rs.getInt("total_likes");
                int totalComments = rs.getInt("total_comments");


                post.setTotalLikes(totalLikes);
                post.setTotalComments(totalComments);

                post.setStatus(rs.getBoolean("status"));
                post.setCreatedDate(rs.getDate("createdDate"));
                postList.add(post);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return postList;
    }
    public void changePostStatus(int postId, boolean newStatus) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL ChangePostStatus(?, ?)}");

            // Đặt giá trị cho các tham số đầu vào của stored procedure
            callSt.setInt(1, postId);
            callSt.setBoolean(2, newStatus);

            callSt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
