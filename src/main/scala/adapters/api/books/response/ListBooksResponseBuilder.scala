package adapters.api.books.response

import domain.core.{Book, BookComment}

case class ListBooksResponse(books: Seq[BookElement])
case class BookElement(id: Long, title: String, author: String, isbn: String, rating: Int, comments: Seq[String])

object ListBooksResponseBuilder {
  def from(books: Seq[(Book, Seq[BookComment])]): ListBooksResponse = {
    ListBooksResponse(
      books.map { book =>
        BookElement(
          book._1.id, book._1.title, book._1.author, book._1.isbn, book._1.rating,
          book._2.map { comment =>
            comment.value
          }
        )
      }
    )
  }
}

