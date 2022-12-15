package domain.port

import domain.core.{Book, BookComment}

import scala.concurrent.Future

trait BookRepository {
  def addBook(book: Book): Future[Int]
  def updateBook(book: Book): Future[Int]
  def deleteBook(bookId: Long): Future[Int]
  def findBooks(maxComments: Int): Future[Seq[(Book, Seq[BookComment])]]

  def addComment(comment: BookComment): Future[Int]
}
