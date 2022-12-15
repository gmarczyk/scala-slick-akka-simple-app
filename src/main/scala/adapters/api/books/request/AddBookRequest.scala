package adapters.api.books.request

case class AddBookRequest(title: String, author: String, isbn: String, rating: Int)