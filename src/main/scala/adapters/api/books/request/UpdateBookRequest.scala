package adapters.api.books.request

case class UpdateBookRequest(id: Long, title: String, author: String, isbn: String, rating: Int)