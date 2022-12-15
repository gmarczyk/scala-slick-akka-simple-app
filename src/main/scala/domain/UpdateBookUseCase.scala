package domain

import domain.core.{Book, BookValidator}
import domain.port.BookRepository

import scala.concurrent.Future

class UpdateBookUseCase(val bookRepository: BookRepository) {
  def updateBook(id: Long, title: String, author: String, isbn: String, rating: Int): Either[String, Future[Int]] = {
    BookValidator.validate(isbn, rating) match {
      case Left(msg) => return Left(msg)
      case _ =>
    }

    val book = Book(
      id = id,
      title = title,
      author = author,
      isbn = isbn,
      rating = rating,
    )

    Right(bookRepository.updateBook(book))
  }
}
