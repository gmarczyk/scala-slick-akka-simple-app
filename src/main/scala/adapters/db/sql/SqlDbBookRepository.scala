package adapters.db.sql


import config.ExecutorsConfig.defaultExecutionCtx
import domain.core.{Book, BookComment}
import domain.port.BookRepository
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future


class SqlDbBookRepository extends BookRepository {

  import config.DatabaseConfig._

  override def addBook(book: Book): Future[Int] = {
    val addBookQuery = BookTable.query += book
    postgresDb.run(addBookQuery).map(r => r)
  }

  override def updateBook(book: Book): Future[Int] = {
    val updateBookQuery = BookTable.query.filter(_.id === book.id).update(book)
    postgresDb.run(updateBookQuery).map(r => r)
  }

  override def deleteBook(bookId: Long): Future[Int] = {
    val deleteBookQuery = BookTable.query.filter(_.id === bookId).delete
    postgresDb.run(deleteBookQuery).map(r => r)
  }

  /**
   * Could be also done via SQL instead with plain query executed in slick
   * https://stackoverflow.com/questions/59898198/join-top-n-records-from-child-table
   */
  //  SELECT b.*, comms.*
  //  FROM books b
  //  LEFT JOIN
  //    (
  //      select *
  //        from (
  //          select comms.*, row_number() over(partition by book_id order by id) rn
  //      from comments comms
  //    ) comms
  //    where rn <= 2
  //  order by id, book_id
  //  ) comms ON comms.book_id = b.id
  override def findBooks(maxComments: Int): Future[Seq[(Book, Seq[BookComment])]] = {
    val query = BookTable.query
      .joinLeft(CommentTable.query)
      .on({ case (book, comments) => book.id === comments.bookId })

    config.DatabaseConfig.postgresDb.run(query.result)
      .map { booksCommentsJoin =>
        booksCommentsJoin
          .groupBy(_._1)
          .map { case (book, commentsWithBook) => (book, commentsWithBook.flatMap(_._2).slice(0, maxComments)) }
          .toSeq.sortBy(_._1.id)
      }
  }

  override def addComment(bookComment: BookComment): Future[Int] = {
    val addCommentQuery = CommentTable.query += bookComment
    postgresDb.run(addCommentQuery).map{r => r}
  }
}

object BookTable {
  class BookTable(tag: Tag) extends Table[Book](tag, Some("public"), "books") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def author = column[String]("author")
    def isbn = column[String]("isbn")
    def rating = column[Int]("rating")

    override def * = (id, title, author, isbn, rating) <> (Book.tupled, Book.unapply)
  }

  lazy val query = TableQuery[BookTable]
}

object CommentTable {
  class CommentTable(tag: Tag) extends Table[BookComment](tag, Some("public"), "comments") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def value = column[String]("value")

    def bookId = column[Long]("book_id")

    override def * = (id, value, bookId) <> (BookComment.tupled, BookComment.unapply)
  }

  lazy val query = TableQuery[CommentTable]
}
