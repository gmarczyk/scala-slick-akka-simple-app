package domain

import domain.core.{Book, BookComment, BookValidator}
import domain.port.BookRepository

import scala.concurrent.Future

class ListBooksUseCase(val bookRepository: BookRepository) {
  def listBooks(): Future[Seq[(Book, Seq[BookComment])]] = {
    bookRepository.findBooks(5)
  }
}
