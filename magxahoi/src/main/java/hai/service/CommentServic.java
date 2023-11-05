package hai.service;

import hai.dto.Commenadd;
import hai.model.Comment;
import hai.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
public class CommentServic implements IGenericService<Comment, Integer>  {
    @Autowired
    private DataSource dataSource;
    //xóa lượt thích của cmt theo lượt thích
    public void removeCommentLike(int userId, int cmtId) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            CallableStatement callSt = connection.prepareCall("{CALL DeleteLikeComment(?, ?)}");
            callSt.setInt(1, userId);
            callSt.setInt(2, cmtId);
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
    //them lượt thích cmt
    public void addCommentLike(int userId, int cmtId) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL AddLikeToComment(?, ?)}");
            callSt.setInt(1, userId);
            callSt.setInt(2, cmtId);
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
    public int  AddComment(int PostId, int UserId, String Content) {
        Connection connection = null;
        int newCommentID=-1;
        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL AddComment(?, ?, ?,?)}");
            callSt.setInt(1, PostId);
            callSt.setInt(2, UserId);
            callSt.setString(3, Content);
            callSt.registerOutParameter(4, Types.INTEGER);
            callSt.execute();
            newCommentID = callSt.getInt(4);

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
        return newCommentID;
    }
    public int DeleteCommentAndLikesAndGetRemainingCount(int postId, int commentId) {
        Connection connection = null;
        int remainingCommentCount = -10;

        try {
            connection = dataSource.getConnection();

            // Delete the comment and likes using the stored procedure
            CallableStatement deleteCallSt = connection.prepareCall("{CALL DeleteCommentAndLikesAndGetRemainingCount(?, ?, ?)}");
            deleteCallSt.setInt(1, commentId);
            deleteCallSt.setInt(2, postId);
            deleteCallSt.registerOutParameter(3, Types.INTEGER);
            deleteCallSt.executeUpdate();

            // Get the updated remaining comment count from the output parameter
            remainingCommentCount = deleteCallSt.getInt(3);
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

        return remainingCommentCount;
    }


    public Commenadd GetCommentInfo(int commentId) {
        Connection connection = null;
        Commenadd commentInfo = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetCommentInfo(?)}");
            callSt.setInt(1, commentId);
            ResultSet resultSet = callSt.executeQuery();

            if (resultSet.next()) {
                commentInfo = new Commenadd();
                commentInfo.setId(resultSet.getInt("comment_id"));
                commentInfo.setPostId(resultSet.getInt("post_id"));
                commentInfo.setUserId(resultSet.getInt("user_id"));
                commentInfo.setContent(resultSet.getString("comment_content"));
                commentInfo.setCreatedDate(resultSet.getDate("comment_created_date"));
                commentInfo.setUserName(resultSet.getString("username"));
                commentInfo.setTotal_cmt(resultSet.getInt("total_comments_in_post"));
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

        return commentInfo;
    }
    public void editComment(int commentId, String editedContent) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL EditComment(?, ?)}");
            callSt.setInt(1, commentId);
            callSt.setString(2, editedContent);
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
    public Comment getCommentById(int commentId) {
        Connection connection = null;
        Comment comment = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetCommentById(?)}");
            callSt.setInt(1, commentId);
            ResultSet resultSet = callSt.executeQuery();

            while (resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setPostId(resultSet.getInt("Commentofpost_id"));
                comment.setUserId(resultSet.getInt("usecomment_id"));
                comment.setContent(resultSet.getString("content"));
                comment.setCreatedDate(resultSet.getTimestamp("createdDate"));
                // Set các thuộc tính khác của comment tương ứng
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

        return comment;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findById(Integer integer) {
        return null;
    }

    @Override
    public void save(Comment comment) {

    }

    @Override
    public void delete(Integer integer) {

    }
}
