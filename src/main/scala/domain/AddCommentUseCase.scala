package domain

import domain.core.{Book, BookComment, BookValidator}
import domain.port.BookRepository

import scala.concurrent.Future

class AddCommentUseCase(val bookRepository: BookRepository) {
  def addComment(bookId: Long, comment: String): Either[String, Future[Int]] = {
    val bookComment = BookComment(
      id = 0l,
      value = comment,
      bookId = bookId,
    )
    Right(bookRepository.addComment(bookComment))
  }
}
