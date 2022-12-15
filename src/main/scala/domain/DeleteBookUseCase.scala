package domain

import domain.port.BookRepository

import scala.concurrent.Future

class DeleteBookUseCase(val bookRepository: BookRepository) {
  def deleteBook(bookId: Long): Either[String, Future[Int]] = {
    Right(bookRepository.deleteBook(bookId))
  }
}
